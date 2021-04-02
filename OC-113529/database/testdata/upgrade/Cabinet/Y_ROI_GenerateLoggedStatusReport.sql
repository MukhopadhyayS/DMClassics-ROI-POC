USE [cabinet]

--/****** Object:  StoredProcedure [dbo].[ROI_Generate_LoggedStatusReport]    Script Date: 07/06/2009  ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_Generate_LoggedStatusReport]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_Generate_LoggedStatusReport]

USE [cabinet]
GO
/****** Object:  StoredProcedure [dbo].[ROI_Generate_LoggedStatusReport]    Script Date: 07/06/2009 14:57:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ROI_Generate_LoggedStatusReport]
@requestorTypes xml,
@fromDate datetime,
@toDate datetime
AS

--Create a memory table to hold requestor types
Declare @ReqTypes table (Data varchar(max))

Declare @requestor_type varchar(max), @requestor_name varchar(max), @created_dt datetime, @request_id int, @hpf_patient_details varchar(max), @hpf_patient_non_hpf_doc_details varchar(max), @roi_patient_details varchar(max), @facility varchar(max), @patient_name varchar(max), @encounter_id varchar(max), @admit_date varchar(max), @patien_type varchar(max), @hpfInfo varchar(max), @roiInfo varchar(max), @hpfNonHpfDocInfo varchar(max)
Declare @TempResults table (RequestorType varchar(max), RequestorName varchar(max), CreatedDate datetime, RequestId int, Facility varchar(max), PatientName varchar(max), EncounterId varchar(max), AdmitDate varchar(max), PatientType varchar(max))

--Fill the table with requestor types
Insert @ReqTypes (Data)
SELECT
	ReqTypeTable.ReqTypes.value('@value','VARCHAR(max)')
FROM
	@requestorTypes.nodes('/ListContent/requestor-type') AS ReqTypeTable(ReqTypes)

-- data delimiter is @
-- entry delimiter is ;
(SELECT
	   cast(RequestMainXML.query( 'data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
	   cast(RequestMainXML.query( 'data(request/requestor/name)') as varchar(8000)) requestor_name,
	   cast(created_dt as datetime) as  Created_Date,
	   cast(roi_requestmain_seq as int) as request_id,
	   cast(pat.aa.value('(patient/@facility)[1]', 'VARCHAR(max)') as varchar(8000)) as facility,
	   cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name ,
       cast(enc.aa.value('@id', 'VARCHAR(max)') as varchar(8000)) AS Encounter_No,
	   cast(enc.aa.value('(admitDate/text())[1]', 'VARCHAR(max)') as varchar(8000)) AS ADMIN_DATE,
	   cast(enc.aa.value('(patientType/text())[1]', 'VARCHAR(max)') as varchar(8000)) AS PATIENT_TYPE,
	   cast(pat.aa.value('(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip
FROM ROI_RequestMain req
      CROSS APPLY RequestMainXML.nodes('request/item') pat(aa)
      OUTER APPLY pat.aa.nodes('patient/encounter') enc(aa)

WHERE pat.aa.value('@source', 'VARCHAR(max)') = 'hpf'
	  and (RequestMainXML.value('request[1]/status[1]', 'varchar(max)') = 'Logged')
      and (created_dt between @fromDate and @toDate)
	  and (RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') in (select * from @ReqTypes))
)

UNION
(SELECT
		cast(RequestMainXML.query('data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
	    cast(RequestMainXML.query('data(request/requestor/name)') as varchar(8000)) requestor_name,
	    cast(created_dt as datetime) as  Created_Date,
	    cast(roi_requestmain_seq as int) as request_id,
		cast(pat.aa.value('(patient/facility/text())[1]', 'VARCHAR(max)') as varchar(8000)) as facility,
		cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
	    cast(doc.aa.value('(encounter/text())[1]', 'VARCHAR(max)')  as varchar(8000)) as Encounter_No,
	    cast(doc.aa.value('(date-of-service/text())[1]', 'VARCHAR(max)') as varchar(8000)) as ADMIN_DATE,
		NULL,
	    cast(pat.aa.value('(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip
FROM ROI_RequestMain req
CROSS APPLY RequestMainXML.nodes('request/item') pat(aa)
CROSS APPLY pat.aa.nodes('patient/supplemental-encounter/document') doc(aa)
WHERE pat.aa.value( '@source', 'VARCHAR(max)') = 'roi'
	  and (RequestMainXML.value('request[1]/status[1]', 'varchar(max)') = 'Logged')
	  and (created_dt between @fromDate and @toDate)
	  and (RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') in (select * from @ReqTypes))
)

UNION
(SELECT
		cast( RequestMainXML.query( 'data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
	    cast( RequestMainXML.query( 'data(request/requestor/name)') as varchar(8000)) requestor_name,
	    cast(created_dt as datetime) as  Created_Date,
	    cast(roi_requestmain_seq as int) as request_id,
		cast(pat.aa.value('(patient/@facility)[1]', 'VARCHAR(max)') as varchar(8000)) as fac,
		cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
	    cast(doc.aa.value('(encounter/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Encounter_No,
	    cast(doc.aa.value('(date-of-service/text())[1]', 'VARCHAR(max)') as varchar(8000)) as ADMIN_DATE,
		NULL,
	    cast(pat.aa.value('(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip

FROM roi_requestmain req
CROSS APPLY RequestMainXML.nodes('request/item') pat(aa)
CROSS APPLY pat.aa.nodes('patient/supplemental-encounter/document') doc(aa)
WHERE pat.aa.value( '@source', 'VARCHAR(max)') = 'hpf'
	  and (RequestMainXML.value('request[1]/status[1]', 'varchar(max)') = 'Logged')
      and (created_dt between @fromDate and @toDate)
	  and (RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') in (select * from @ReqTypes)
)
)

GO
GRANT EXECUTE ON [dbo].[ROI_Generate_LoggedStatusReport] TO [IMNET]
GO