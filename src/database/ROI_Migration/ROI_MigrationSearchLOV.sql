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

--request.balance-due             
/***************************************************************************************************************
* Procedure             : ROI_MigrationSearchLOV.sql
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
*                       : [Cabinet].[dbo].[ROI_RequestMain]
*                       : [Cabinet].[dbo].[ROI_SearchLOV]
*			
* Data Modifications	: Creates data in ROI 13.5 tables
*									
* Updates: 	
* Name      Date        Purpose
* ---------	----------- ----------------
* RC        03/30/2009  Created   
* RC		01/05/2010	added search values encounter, request, request-reason
* MD Meersma	10/01/2010	Change requestor.type insert into ROI_SearchLOV to use ROI_RequestorType_Seq instead of classic ROI Value
***************************************************************************************************************************************/
USE CABINET
GO
Print 'Starting Search LOV at ' + cast(getdate() as varchar(20));

SET NOCOUNT ON

Print 'Starting encounter.id entry in Search LOV '+ cast(getdate() as varchar(20));
INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select DISTINCT    
			Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'encounter.id' AS [Item]
           ,vwEnc_enc AS [Value]
           ,'encounter' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    AA_ROI_CLassicRequestDetail
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = AA_ROI_CLassicRequestDetail.RequestPage_RequestId
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwEnc_enc is not NULL
)
Print ' Completed processing encounter.id at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'encounter.id' and created_by_seq < 1


--do request.id
Print 'Starting request_seq entry in Search LOV '+ cast(getdate() as varchar(20));
INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'ROI' AS [Application]
           ,'request.id' AS [Item]
           ,ROI_RequestMain_Seq AS [Value]
           ,'request' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwMRN_mrn is not NULL
)
Print ' Completed processing request.id at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'request.id' and created_by_seq < 1


--do request.request-reason
Print 'Starting request.request-reason entry in Search LOV '+ cast(getdate() as varchar(20));
INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'request.request-reason' AS [Item]
           ,'Migration' AS [Value]
           ,'request' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwMRN_mrn is not NULL
)
Print ' Completed processing request.request-reason at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'request.request-reason' and created_by_seq < 1



--do mrn
Print 'Starting MRN entry in Search LOV '+ cast(getdate() as varchar(20));
--BEGIN TRAN InsertSearchLOV_MRN
INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'patient.mrn' AS [Item]
           ,vwMRN_mrn AS [Value]
           ,'patient' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwMRN_mrn is not NULL
)
Print ' Completed processing MRN at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'patient.mrn' and created_by_seq < 1

--do name
Print 'Starting name entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'patient.name' AS [Item]
           ,vwMRN_name AS [Value]
           ,'patient' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwMRN_name is not NULL
)
Print ' Completed processing name at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'patient.name' and created_by_seq < 1

--do last name
Print 'Starting lastname entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'patient.lastname' AS [Item]
           ,RTRIM(Substring(vwMRN_Name,1, Charindex(',', vwMRN_Name)-1)) AS [Value]
           ,'patient' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwMRN_Name is not NULL
)
Print ' Completed processing lastname at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'patient.lastname' and created_by_seq < 1

--do first name
Print 'Starting firstname entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'patient.firstname' AS [Item]
           ,RTRIM(Substring(vwMRN_Name, Charindex(',', vwMRN_Name)+2,40)) AS [Value]
           ,'patient' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwMRN_Name is not NULL
)
Print ' Completed processing firstname at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'patient.firstname' and created_by_seq < 1

--do ssn
Print 'Starting ssn entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'patient.ssn' AS [Item]
           ,vwMRN_ssn AS [Value]
           ,'patient' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwMRN_ssn is not NULL
)
Print ' Completed processing ssn at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'patient.ssn' and created_by_seq < 1

--do epn
Print 'Starting epn entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'patient.epn' AS [Item]
           ,vwMRN_epn AS [Value]
           ,'patient' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwMRN_epn is not NULL
)
Print ' Completed processing epn at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'patient.epn' and created_by_seq < 1

--do facility
Print 'Starting patient.facility entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'patient.facility' AS [Item]
           ,vwMRN_facility AS [Value]
           ,'patient' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwMRN_facility is not NULL
)
Print ' Completed processing patient.facility at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'patient.facility' and created_by_seq < 1

--do patient is-vip
Print 'Starting patient.is-vip entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'patient.is-vip' AS [Item]
           ,vwMRN_vip AS [Value]
           ,'patient' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwMRN_vip is not NULL
)
Print ' Completed processing patient.is-vip at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'patient.is-vip' and created_by_seq < 1

--do status
Print 'Starting request.status entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'ROI' AS [Application]
           ,'request.status' AS [Item]
    , CASE Request_Status --
        WHEN 'Log' THEN '2'
        WHEN 'PrintFax' THEN '3'
        ELSE Request_Status 
        END 
           ,'request' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    Request_Status is not NULL
)
Print ' Completed processing request.status at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'request.status' and created_by_seq < 1

--do receipt-date
Print 'Starting request.receipt-date entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'request.receipt-date' AS [Item] 
           ,Convert(char(10),Request_ReceiptDate,101)
           ,'request' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    Request_ReceiptDate is not NULL
)
Print ' Completed processing request.receipt-date at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'request.receipt-date' and created_by_seq < 1

--do requestor name
Print 'Starting requestor.name entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'requestor.name' AS [Item] 
           ,RTRIM(Substring(requester_requestername,1,40))
           ,'requestor' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    requester_requestername is not NULL
)
Print ' Completed processing requestor.name at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'requestor.name' and created_by_seq < 1

--do requestor type
Print 'Starting requestor.type entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'requestor.type' AS [Item] 
--           ,RequesterType_RequesterTypeId
	,isnull((select min(ROI_RequestorType_Seq) from ROI_RequestorType where name like '%' + RequesterType_RequesterTypeName + '%'), 
	(select ROI_RequestorType_Seq from ROI_RequestorType where name = 'ROI Other'))
           ,'requestor' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    RequesterType_RequesterTypeId is not NULL
)
Print ' Completed processing requestor.type at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'requestor.type' and created_by_seq < 1

--do patient dob
Print 'Starting dob entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'HPF' AS [Application]
           ,'patient.dob' AS [Item] 
           ,Convert(char(10),vwMRN_DOB,101)
           ,'requestor' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
WHERE
    vwMRN_DOB is not NULL
)
Print ' Completed processing dob at '+ cast(getdate() as varchar(20));
--Select * from ROI_SearchLOV where item = 'patient.dob and created_by_seq < 1

--do balancd due
Print 'Starting balancd due entry in Search LOV '+ cast(getdate() as varchar(20));

INSERT Roi_SearchLOV
           ([Created_By_Seq]
           ,[Organization_Seq]
           ,[Parent]
           ,[Child]
           ,[Application]
           ,[Item]
           ,[Value]
           ,[Description])
(
Select     Request_RequestId * -1
           ,1 AS [Organization_Seq]
           ,1 AS [Parent]
           ,ROI_RequestMain_Seq
           ,'roi' AS [Application]
           ,'request.balance-due' AS [Item] 
           ,'0'
           ,'balance due' AS [Description]
FROM 
    AA_ROI_CLassicRequestHeader
INNER JOIN 
    ROI_RequestMain
ON 
    AA_ROI_CLassicRequestHeader.Request_RequestId = ROI_RequestMain.Created_By_Seq * -1
)
Print ' Completed processing balancd due at '+ cast(getdate() as varchar(20));


Print 'Completed Search LOV at ' + cast(getdate() as varchar(20));
