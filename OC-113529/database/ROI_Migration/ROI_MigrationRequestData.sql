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
* Procedure             : ROI_MigrationRequestData.sql
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
* Tables used           : [Cabinet].[dbo].[AA_ROI_ClassicRequestHeader]
*                       : [Cabinet].[dbo].[AA_ROI_ClassicRequestDetail]
*                       : [Cabinet].[dbo].[AA_ROI_ClassicReleaseDetail]
*                       : [Cabinet].[dbo].[AA_ROI_ClassicInvoice]
*			
* Data Modifications	: Creates data in ROI 13.5 tables
*									
* Updates: 	
* Name      	Date        	Purpose
* ---------	----------- 	----------------
* RC        	03/30/2009  	Created 
* RC        	05/01/2009  	added group by to documents and versions  
* MDMeersma	07/08/2009	
*				<is-patient-locked>False</is-patient-locked> should be changed to <patientLocked>false</patientLocked>
*				<patient-type>IP</patient-type> should be changed to <patientType>ER</patientType>
*				<date-of-service>09/25/1972</date-of-service> should be changed to <admitDate>09/25/1972</admitDate>
*            			
*				<page number="1" imnet-id="IMAGES1   01889900001" content-count="1" selected_for_release="False" /> should be changed to
*				<page number="1" imnet-id="IMAGES1   01889900001" content-count="1" selected-for-release="true" is-released="false">
*
* MD Meersma	07/09/2009	Added @Is-Released
* MD Meersma	07/10/2009	Change "Request-Reason" value to "Migration" from ''
* RC			01/05/2010	Added element encounterLockout
*							updated False to false
* RC			01/06/2010	added attribute to the element doc-id
* MD Meersma	09/17/2010	Do not insert comments into XML due to performance issues
* MD Meersma	10/25/2010	Use RequesterName for Migrated Requests
***************************************************************************************************************************************/

SET NOCOUNT ON


Do_Released:

--/* this script will create the XML for any released requests from the ROI classic system
declare @l_CounterI int; Set @l_CounterI = 1
Declare @l_RequestId int
Declare @l_RequestXML xml
Declare @l_Message varchar(255)
Declare @l_requestor_seq int
Declare @l_NameLast varchar(256)
Declare @l_RequestorType int
Declare @l_RequestorTypeName varchar(256)
declare @l_logdate datetime
declare @l_receiptdate datetime

--fill the requestor information from the database IF there is a requestor with the last name = 'Migration'
--fill the requestor information from the database IF there is a requestor with the last name = 'Migration'
IF EXISTS (Select 1 from ROI_Requestor WHERE NameLast = 'Migration')
BEGIN
	SELECT 
		@l_requestor_seq = roi_requestor_seq 
		, @l_NameLast = '' --NameLast 
		, @l_RequestorType = ROI_Requestor.ROI_RequestorType_Seq 
		, @l_RequestorTypeName = ROI_RequestorType.name 
	FROM 
		ROI_Requestor
		inner join ROI_RequestorType
		on ROI_Requestor.ROI_RequestorType_Seq = ROI_RequestorType.ROI_RequestorType_Seq
	WHERE 
		NameLast = 'Migration'
END



Declare @l_ROI_RequestID table
(
rrid int identity(1,1) PRIMARY KEY
,requestId int
)

--GOTO Do_unReleased --uncomment to migrate only unreleased requests

--report the number of records to process
Set @l_Message =  (Select count(distinct(ReqRel_RequestId)) AS 'Released requests to process' FROM [Cabinet].[dbo].AA_ROI_ClassicReleaseDetail RD
INNER JOIN AA_ROI_ClassicRequestHeader RH on RD.ReqRel_RequestId = RH.Request_RequestId) ;

Print 'Starting to process ' + @l_Message + ' released requests'; 

--put a list of the classic requests into the memory table
Insert @l_ROI_RequestID
Select distinct(ReqRel_RequestId) FROM [Cabinet].[dbo].AA_ROI_ClassicReleaseDetail RD
INNER JOIN AA_ROI_ClassicRequestHeader RH 
on RD.ReqRel_RequestId = RH.Request_RequestId 
ORDER BY RD.ReqRel_RequestId
--select * from @l_ROI_RequestID
--start on the first record
Set @l_CounterI = (Select min(rrid) from @l_ROI_RequestID)

--loop through each record in the table
While @l_CounterI <= (Select max(rrid) from @l_ROI_RequestID)
BEGIN

--get the classic request id from the memory table
Set @l_RequestId = (Select requestID from @l_ROI_RequestID WHERE rrid = @l_CounterI) 

--begin the conversion for this classic request
BEGIN TRAN MIgrateReleased01

-- Set RequesterName from Classic ROI for Migrated Requests 
-- even though the New ROI is linked to the Migration Requestor

set @l_NameLast = (select RequesterName from ROI_Requester where Requester_ID = (select Requester_ID from ROI_Request where Request_ID = @l_RequestID))

/*
-- Replace Special Characters 

Classic ROI Value	New ROI Value

,					{space}
&					{space}and{space}
‘					{blank}
“					{blank}
.					{space}
(					{blank}
)					{blank}
/					{blank}
-					{blank}

*/

set @l_NameLast =  replace(@l_NameLast, ',', ' ')
set @l_NameLast =  replace(@l_NameLast, '&', ' and ')
set @l_NameLast =  replace(@l_NameLast, '''', '')
set @l_NameLast =  replace(@l_NameLast, '"', '')
set @l_NameLast =  replace(@l_NameLast, '.', ' ')
set @l_NameLast =  replace(@l_NameLast, '(', '')
set @l_NameLast =  replace(@l_NameLast, ')', '')
set @l_NameLast =  replace(@l_NameLast, '/', '')
set @l_NameLast =  replace(@l_NameLast, '-', ' ')

Set @l_RequestXML = 
(
SELECT --start of request general information
--@l_RequestId as "@origional-id", --testing use only
CASE Request_Status
    WHEN 'Log' THEN 'Logged'
    WHEN 'PrintFax' THEN 'Completed'
    ELSE Request_Status 
    END AS "status"
,convert(char(10), Request_ReceiptDate, 101) AS "receipt-date"
,'Migration' AS "status-reason"
,'Migration' AS "request-reason"
,'TPO' AS "request-reason-attribute"
,'0.00' AS "balance-due"
,convert(char(10), Request_StatusDate, 101)  AS "status-changed"
--start of requestor information
,@l_requestor_seq AS "requestor/@id"
,@l_NameLast AS "requestor/name"
,@l_RequestorType AS "requestor/type"
,@l_RequestorTypeName AS "requestor/type-name"
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
            ,RTRIM(CRD2.Documents_DocId) as "doc-id/@value"
            ,RTRIM(CRD2.DocNames_DocTypeId) as "doc-id"
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
            Group by CRD2.documents_DocName, CRD2.documents_Subtitle,CRD2.DocNames_Tagorder,CRD2.Documents_DocId ,CRD2.DocNames_DocTypeId
            FOR XML PATH ('global-document'), TYPE
        )--end of global document 
        ,(SELECT --encounter --start of non global document
            RTRIM(vwEnc_enc) AS "@id"
            ,RTRIM(vwEnc_fac) AS "facility"
            ,RTRIM(vwEnc_PtType) AS "patientType"
            ,convert(char(10), vwEnc_Admitted, 101) AS "admitDate"
            ,convert(char(10), vwenc_discharged, 101) AS "discharge-date"
            ,(Select --document
                RTRIM(CRD2.documents_DocName) as "@type"
                ,RTRIM(CRD2.documents_Subtitle) as "subtitle"
                ,RTRIM(CRD2.DocNames_Tagorder) as "chart-order"
                ,RTRIM(CRD2.Documents_DocId) as "doc-id/@value"
	            ,RTRIM(CRD2.DocNames_DocTypeId) as "doc-id"
                    ,(Select --version
                    CRD1.Versions_VersionNumber as "@number"
                    ,CRD1.Versions_VersionId as "@ID"
                    ,(SELECT --page
                        CRD.Pages_Page as "@number" 
                        , RTRIM(CRD.Pages_Imnet) as "@imnet-id"
                        , CRD.Pages_ContentCount as "@content-count"
                        , 'False' as "@selected-for-release"
	                , 'True' AS "@is-released"
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
                Group by CRD2.documents_DocName, CRD2.documents_Subtitle,CRD2.DocNames_Tagorder,CRD2.Documents_DocId,CRD2.DocNames_DocTypeId
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
FROM [Cabinet].[dbo].AA_ROI_ClassicRequestHeader CRH3
WHERE CRH3.Request_RequestId = @l_RequestId
FOR XML Path('request') 
)
--add the comments to the xml
--Set @l_RequestXML.modify('insert <!--Request general information--> before (/request/status) [1] ')
--Set @l_RequestXML.modify('insert <!--Requestor information--> before (/request/requestor) [1] ')
--Set @l_RequestXML.modify('insert <!--Patients--> before (/request/item) [1] ')
--Set @l_RequestXML.modify('insert <!--HPF Patient--> before (/request/item) [1] ')
--Set @l_RequestXML.modify('insert <!--Global Document--> after (/request/item/patient/dob) [1] ')
--Set @l_RequestXML.modify('insert <!--Hpf Document--> before (/request/item/patient/encounter) [1] ')

-- Set the created_dt and modified_dt for NEW ROI

select @l_logdate = logdate from ROI_Request where Request_ID = @l_RequestID
select @l_receiptdate = ReceiptDate from ROI_Request where Request_ID = @l_RequestID

if @l_logdate is null set @l_logdate = getdate()
if @l_receiptdate is null set @l_receiptdate = getdate()

--place the data into the RequestMain table

INSERT [Cabinet].[dbo].ROI_RequestMain (Created_By_Seq, RequestMainXML, Created_Dt, Modified_Dt) 
Values(@l_RequestId * -1, @l_RequestXML, @l_logdate, @l_receiptdate)
COMMIT TRAN MIgrateReleased01

--display the data for troubleshooting
--Select @l_CounterI AS 'Counter',@l_RequestId AS 'RequestID', @l_RequestXML AS 'Request XML';
--Select * from ROI_RequestMain WHERE Created_by_seq = @l_RequestId * -1

--update the number of records processed
IF @l_CounterI % 100 = 0  Print CAST(@l_CounterI as varchar(10)) + ' released records processed on ' + cast(getdate() as varchar(20));

--increment the counter
Set @l_CounterI = @l_CounterI + 1
END --processing is complet for classic released requests

Print CAST(@l_CounterI - 1 as varchar(10)) + ' released records processed';



--uncomment to bypass processing classic unreleased requests
--GOTO Finished

Do_unReleased:

--set the message to the number of records to process
Set @l_Message =  (Select count(distinct(RequestPage_RequestId)) AS 'Unreleased requests to process' FROM [Cabinet].[dbo].AA_ROI_ClassicRequestDetail RD
INNER JOIN AA_ROI_CLassicRequestHeader RH on RD.RequestPage_RequestId = RH.Request_RequestId ) ;
Print 'Starting to process ' + @l_Message + ' unreleased requests'; 

--clean the memory table
delete from @l_ROI_RequestID

--fill the table with a list of classic unreleased requests
Insert @l_ROI_RequestID
Select distinct(RequestPage_RequestId) FROM [Cabinet].[dbo].AA_ROI_ClassicRequestDetail RD
INNER JOIN AA_ROI_ClassicRequestHeader RH 
on RD.RequestPage_RequestId = RH.Request_RequestId 

--start at the lowest request
Set @l_CounterI = (Select min(rrid) from @l_ROI_RequestID)

While @l_CounterI <=(Select max(rrid) from @l_ROI_RequestID)
BEGIN --processing the classic unreleased requests

--get the next classic request id
Set @l_RequestId = (Select requestID from @l_ROI_RequestID WHERE rrid = @l_CounterI) 

--start the conversion for this request
BEGIN TRAN MigrateUnReleased01
Set @l_RequestXML = 
(
SELECT --request tag starts here
--start of request general information
CASE Request_Status
    WHEN 'LOG' THEN 'Logged' 
    WHEN 'PrintFax' THEN 'Completed'
    END AS "status"
,convert(char(10), Request_ReceiptDate, 101) AS "receipt-date"
,'Migration' AS "request-reason"
,RTRIM(Disposition_DescName) AS "status-reason"
,'TPO' AS "request-reason-attribute"
,'0.00' AS "balance-due"
--start of requestor information
,@l_requestor_seq AS "requestor/@id"
,@l_NameLast AS "requestor/name"
,@l_RequestorType AS "requestor/type"
,@l_RequestorTypeName AS "requestor/type-name"
,(Select --item start of tag
    'hpf' AS "@source"
    ,'patient' AS "@type"
    ,(Select --patient start of tag
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
            ,RTRIM(CRD2.Documents_DocId) as "doc-id/@value"
            ,RTRIM(CRD2.DocNames_DocTypeId) as "doc-id"
                ,(Select --version
                CRD1.Versions_VersionNumber as "@number"
                ,CRD1.Versions_VersionId as "@ID"
                ,(SELECT --page
                    CRD.Pages_Page as "@number" 
                    , RTRIM(CRD.Pages_Imnet) as "@imnet-id"
                    , CRD.Pages_ContentCount as "@content-count"
                    , 'False' as "@selected-for-release"
                    , 'False' AS "@is-released"
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
        FROM [Cabinet].[dbo].AA_ROI_ClassicRequestDetail CRD2
        WHERE RequestPage_RequestId = @l_RequestId 
        AND DocNames_Global = 'Y'
        Group by CRD2.documents_DocName, CRD2.documents_Subtitle,CRD2.DocNames_Tagorder,CRD2.Documents_DocId ,CRD2.DocNames_DocTypeId
        FOR XML PATH ('global-document'), TYPE
        )--end of global document 
        ,(SELECT --encounter --start of non global document
            RTRIM(vwEnc_enc) AS "@id"
            ,RTRIM(vwEnc_fac) AS "facility"
            ,RTRIM(vwEnc_PtType) AS "patientType"
            ,convert(char(10), vwEnc_Admitted, 101) AS "admitDate"
            ,convert(char(10), vwenc_discharged, 101) AS "discharge-date"
            ,(Select --document
                RTRIM(CRD2.documents_DocName) as "@type"
                ,RTRIM(CRD2.documents_Subtitle) as "subtitle"
                ,CRD2.DocNames_Tagorder as "chart-order"
                ,CRD2.Documents_DocId as "doc-id/@value"
	            ,RTRIM(CRD2.DocNames_DocTypeId) as "doc-id"
                    ,(Select --version
                    CRD1.Versions_VersionNumber as "@number"
                    ,CRD1.Versions_VersionId as "@ID"
                    ,(SELECT --page
                        CRD.Pages_Page as "@number" 
                        , RTRIM(CRD.Pages_Imnet) as "@imnet-id"
                        , CRD.Pages_ContentCount as "@content-count"
                        , 'true' as "@selected-for-release"
	                , 'false' AS "@is-released"
                        FROM [Cabinet].[dbo].AA_ROI_ClassicRequestDetail CRD
                        WHERE CRD.Pages_VersionID = CRD1.Versions_VersionID
                        AND CRD.Pages_Imnet IS NOT NULL
                        AND CRD.RequestPage_RequestId = @l_RequestId
                        ORDER BY CRD.Pages_Page
                        FOR XML PATH ('page'), TYPE
                        )
                    FROM [Cabinet].[dbo].AA_ROI_ClassicRequestDetail CRD1
                    WHERE CRD1.Versions_DOCID = CRD2.Documents_DocId
                    AND CRD1.RequestPage_RequestId = @l_RequestId
                    GROUP BY CRD1.Versions_VersionNumber, CRD1.Versions_VersionId
                    FOR XML PATH ('version'), TYPE
                    )
                FROM [Cabinet].[dbo].AA_ROI_ClassicRequestDetail CRD2
                WHERE CRD2.RequestPage_RequestId = @l_RequestId
                AND CRD2.DocNames_Global = 'N'
                AND CRD2.vwEnc_enc = CRD3.vwEnc_enc
                Group by CRD2.documents_DocName, CRD2.documents_Subtitle,CRD2.DocNames_Tagorder,CRD2.Documents_DocId,CRD2.DocNames_DocTypeId 
                FOR XML PATH ('document'), TYPE
                )
            FROM [Cabinet].[dbo].AA_ROI_ClassicRequestDetail CRD3
            WHERE CRD3.RequestPage_RequestId = CRH1.Request_RequestId AND vwEnc_enc IS NOT NULL
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
FROM [Cabinet].[dbo].AA_ROI_ClassicRequestHeader CRH3
WHERE CRH3.Request_RequestId = @l_RequestId
FOR XML Path('request') 
)

--insert the comments
--Set @l_RequestXML.modify('insert <!--Request general information--> before (/request/status) [1] ')
--Set @l_RequestXML.modify('insert <!--Requestor information--> before (/request/requestor) [1] ')
--Set @l_RequestXML.modify('insert <!--Patients--> before (/request/item) [1] ')
--Set @l_RequestXML.modify('insert <!--HPF Patient--> before (/request/item) [1] ')
--Set @l_RequestXML.modify('insert <!--Global Document--> after (/request/item/patient/dob) [1] ')
--Set @l_RequestXML.modify('insert <!--Hpf Document--> before (/request/item/patient/encounter) [1] ')

--place the data into the RequestMain table
--
INSERT [Cabinet].[dbo].ROI_RequestMain (Created_By_Seq, RequestMainXML) Values(@l_RequestId * -1, @l_RequestXML)
COMMIT TRAN MigrateUnReleased01

--show data for testing
--Select @l_CounterI AS 'Counter',@l_RequestId AS 'RequestID', @l_RequestXML AS 'Request XML';
--Select * from ROI_RequestMain WHERE Created_by_seq = @l_RequestId * -1

--update the number of records processed
IF @l_CounterI % 100 = 0  Print CAST(@l_CounterI as varchar(10)) + ' unreleased records processed on ' + cast(getdate() as varchar(20));

Set @l_CounterI = @l_CounterI + 1
END --conversion of classic unreleased requests

Print @l_Message + ' unreleased records processed';


--select * from ROI_RequestMain where created_by_seq < 0
--select count(*) from ROI_RequestMain where created_by_seq < 0
--Delete from ROI_RequestMain where created_by_seq < 0

Finished:
GO
