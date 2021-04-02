SET QUOTED_IDENTIFIER ON
GO

/* BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!
*
* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.
*
END-COPYRIGHT-COMMENT  Do not remove or modify this line! */

/***************************************************************************************************************
* Procedure             : ROI_MigrationDeliveryData.sql
* Creation Date         : 03/30/2009
*					
* Written by            : RC	
*									
* Purpose               : Move data from Classic ROI to ROI13.5
*		
* Database              : CABINET 
*									
* Input Parameters      : NONE 
*	Name		Type		Description
* 	---------	-------		----------------
*
* Output Parameters     : NONE 
*   Name		Type        Description
*   ---------	----------- ----------------
*									
* Return Status         : <none>		
*									
* Usage                 : ROI_MigrationRequestData.sql 
*																
* Called By             : manual execution
*									
* Tables used           : [Cabinet].[dbo].[AA_ROI_CLassicRequestHeader]
*                       : [Cabinet].[dbo].[AA_ROI_CLassicRequestDetail]
*                       : [Cabinet].[dbo].[AA_ROI_ClassicReleaseDetail]
*                       : [Cabinet].[dbo].[AA_ROI_ClassicInvoice]
*			
* Data Modifications	: Creates data in ROI 13.5 tables
*									
* Updates: 	
* 	Name      	Date        	Purpose
* 	---------	----------- 	----------------
* 	RC        	03/30/2009	Created   
*	MD Meersma	07/09/2009	Changed Selected_for_Release to Selected-for-release
*	MD Meersma	09/17/2010	Do not insert comments into XML due to performance issues
*	MD Meersma	10/28/2010	Set Created_Dt, Modified_Dt in ROI_RequestDelivery to values from ROI_RequestMain
***************************************************************************************************************************************/
SET NOCOUNT ON


Do_Released:

--/* this script will create the XML for any released requests from the ROI classic system
declare @l_CounterI int; Set @l_CounterI = 1
Declare @l_RequestId int
Declare @l_requestmainseq int
Declare @l_RequestXML xml
--Select distinct(ReqRel_RequestId) FROM [Cabinet].[dbo].AA_ROI_ClassicReleaseDetail
--Select distinct(ReqRel_RequestId) FROM [Cabinet].[dbo].AA_ROI_CLassicReleaseDetail

Declare @l_ROI_RequestID table
(
rrid int identity(1,1)
,requestId int
,requestmainseq int
)
--
Insert @l_ROI_RequestID
select distinct  created_by_seq * -1, ROI_RequestMain_Seq
FROM ROI_RequestMain
INNER JOIN AA_ROI_ClassicReleaseDetail
ON created_By_Seq * -1 = reqRel_RequestID


--Select * from @l_ROI_RequestID --check the data
--Select * from ROI_RequestMain --check the data
--loop through the table
While @l_CounterI <= (Select max(rrid) from @l_ROI_RequestID)
BEGIN
Set @l_RequestId = (Select requestID from @l_ROI_RequestID WHERE rrid = @l_CounterI) --12
Set @l_requestmainseq = (Select requestmainseq from @l_ROI_RequestID WHERE rrid = @l_CounterI) --12

Set @l_RequestXML = 
(
SELECT --start of request general information
'true' AS "@is-released"
,(Select --item
    'hpf' AS "@source"
    ,'patient' AS "@type"
    ,(Select --patient
        RTRIM(vwMRN_mrn) AS "@mrn"
        ,RTRIM(vwMRN_Facility) AS "@facility"
        ,RTRIM(vwMRN_Name) AS "name"
        ,CASE vwMRN_Sex 
            WHEN 'M' THEN 'Male'
            ELSE 'Female'
            END AS "gender"
        ,RTRIM(vwMRN_EPN) AS "epn"
        ,RTRIM(vwMRN_SSN) AS "ssn"
        --,0 AS "supplemental-id"
        ,CASE vwMRN_VIP 
            WHEN '1' THEN 'true'
            ELSE 'false'
            END AS "is-vip"
        ,CASE vwMRN_Lockout 
            WHEN 'N' THEN 'false'
            ELSE 'true'
            END AS "patientLocked"
		, 'false' AS "encounterLocked"
        ,convert(char(10), vwMRN_DOB, 101) AS "dob"
        ,(Select --document global
            RTRIM(CRD2.documents_DocName) as "@type"
            ,RTRIM(CRD2.documents_Subtitle) as "subtitle"
            ,RTRIM(CRD2.DocNames_Tagorder) as "chart-order"
            ,RTRIM(CRD2.Documents_DocId) as "doc-id"
                ,(Select --version
                CRD1.Versions_VersionNumber as "@number"
                ,CRD1.Versions_VersionId as "@ID"
                ,(SELECT --page
                    CRD.Pages_Page as "@number" 
                    , RTRIM(CRD.Pages_Imnet) as "@imnet-id"
                    , CRD.Pages_ContentCount as "@content-count"
                    , 'false' as "@selected-for-release"
                    , 'true' AS "@is-released"
                    FROM [Cabinet].[dbo].AA_ROI_ClassicReleaseDetail CRD
                    WHERE CRD.Pages_VersionID = CRD1.Versions_VersionID
                    AND CRD.Pages_Imnet IS NOT NULL
                    AND CRD.ReqRel_RequestId = @l_RequestId
                    ORDER BY CRD.Pages_Page
                    FOR XML PATH ('page'), TYPE
                    )
                FROM [Cabinet].[dbo].AA_ROI_ClassicReleaseDetail CRD1
                WHERE CRD1.Versions_DOCID = CRD2.Documents_DocId
                AND CRD1.ReqRel_RequestId = @l_RequestId
                GROUP BY CRD1.Versions_VersionNumber, CRD1.Versions_VersionId
                FOR XML PATH ('version'), TYPE
                )
            FROM [Cabinet].[dbo].AA_ROI_ClassicReleaseDetail CRD2
            WHERE CRD2.ReqRel_RequestId = @l_RequestId 
            AND CRD2.DocNames_Global = 'Y'
            Group by CRD2.documents_DocName, CRD2.documents_Subtitle,CRD2.DocNames_Tagorder,CRD2.Documents_DocId 
            FOR XML PATH ('global-document'), TYPE
        )--end of global document 
        ,(SELECT --encounter --start of non global document
            RTRIM(vwEnc_enc) AS "@id"
            ,RTRIM(vwEnc_fac) AS "facility"
            ,RTRIM(vwEnc_PtType) AS "patienttype"
            ,convert(char(10), vwEnc_Admitted, 101) AS "admitDate"
            ,convert(char(10), vwenc_discharged, 101) AS "discharge-date"
            ,(Select --document
                RTRIM(CRD2.documents_DocName) as "@type"
                ,RTRIM(CRD2.documents_Subtitle) as "subtitle"
                ,RTRIM(CRD2.DocNames_Tagorder) as "chart-order"
                ,RTRIM(CRD2.Documents_DocId) as "doc-id"
                    ,(Select --version
                    CRD1.Versions_VersionNumber as "@number"
                    ,CRD1.Versions_VersionId as "@ID"
                    ,(SELECT --page
                        CRD.Pages_Page as "@number" 
                        , RTRIM(CRD.Pages_Imnet) as "@imnet-id"
                        , CRD.Pages_ContentCount as "@content-count"
                        , 'false' as "@selected-for-release"
	                , 'true' AS "@is-released"
                        FROM [Cabinet].[dbo].AA_ROI_ClassicReleaseDetail CRD
                        WHERE CRD.Pages_VersionID = CRD1.Versions_VersionID
                        AND CRD.Pages_Imnet IS NOT NULL
                        AND CRD.ReqRel_RequestId = @l_RequestId
                        ORDER BY CRD.Pages_Page
                        FOR XML PATH ('page'), TYPE
                        )
                    FROM [Cabinet].[dbo].AA_ROI_ClassicReleaseDetail CRD1
                    WHERE CRD1.Versions_DOCID = CRD2.Documents_DocId
                    AND CRD1.ReqRel_RequestId = @l_RequestId
                    GROUP BY CRD1.Versions_VersionNumber, CRD1.Versions_VersionId
                    FOR XML PATH ('version'), TYPE
                    )
                FROM [Cabinet].[dbo].AA_ROI_ClassicReleaseDetail CRD2
                WHERE CRD2.ReqRel_RequestId = @l_RequestId 
                AND CRD2.DocNames_Global = 'N'
                AND CRD2.vwEnc_enc = CRD3.vwEnc_enc
                Group by CRD2.documents_DocName, CRD2.documents_Subtitle,CRD2.DocNames_Tagorder,CRD2.Documents_DocId 
                FOR XML PATH ('document'), TYPE
                )
            FROM [Cabinet].[dbo].AA_ROI_ClassicReleaseDetail CRD3
            WHERE CRD3.ReqRel_RequestId = CRH1.Request_RequestId AND vwEnc_enc IS NOT NULL
            Group By vwEnc_enc,vwEnc_fac,vwEnc_PtType,vwEnc_Admitted,vwenc_discharged
            FOR XML PATH ('encounter'), TYPE
            )--end of non global document
    FROM [Cabinet].[dbo].AA_ROI_ClassicRequestHeader CRH1
    WHERE CRH1.Request_RequestId = CRH2.Request_RequestId
    FOR XML Path('patient'), TYPE
    )
FROM [Cabinet].[dbo].AA_ROI_ClassicRequestHeader CRH2
WHERE CRH2.Request_RequestId = CRH3.Request_RequestId
FOR XML Path('item'), TYPE 
)
FROM [Cabinet].[dbo].AA_ROI_CLassicRequestHeader CRH3
WHERE CRH3.Request_RequestId = @l_RequestId
FOR XML Path('release') 
)

--Select @l_RequestXML

--Set @l_RequestXML.modify('insert <!--Release general information--> before (/release/status) [1] ')
--Set @l_RequestXML.modify('insert <!--Requestor information--> before (/release/requestor) [1] ')
--Set @l_RequestXML.modify('insert <!--Patients--> before (/release/item) [1] ')
--Set @l_RequestXML.modify('insert <!--HPF Patient--> before (/release/item) [1] ')
--Set @l_RequestXML.modify('insert <!--Global Document--> after (/release/item/patient/dob) [1] ')
--Set @l_RequestXML.modify('insert <!--Hpf Document--> before (/release/item/patient/encounter) [1] ')
--select @l_RequestId * -1,@l_RequestXML
--

declare @l_created_dt datetime, @l_modified_dt datetime

select @l_created_dt = Created_Dt, @l_modified_dt = Modified_Dt from ROI_RequestMain where ROI_RequestMain_Seq = @l_requestmainseq

INSERT [Cabinet].[dbo].ROI_RequestDelivery (Created_By_Seq, ROI_RequestMain_Seq, RequestDeliveryXML, Created_Dt, Modified_Dt) 
Values(@l_RequestId * -1, @l_requestmainseq, @l_RequestXML, @l_created_dt, @l_modified_dt )

--update the number of records processed
IF @l_CounterI % 100 = 0  Print CAST(@l_CounterI as varchar(10)) + ' released records processed on ' + cast(getdate() as varchar(20));

--increment the counter
Set @l_CounterI = @l_CounterI + 1
END --processing is complet for classic released requests
Print CAST(@l_CounterI - 1 as varchar(10)) + ' released records processed';

--select * from [Cabinet].[dbo].ROI_RequestDelivery where created_by_seq < 0
--Delete from ROI_RequestDelivery where created_by_seq < 0

