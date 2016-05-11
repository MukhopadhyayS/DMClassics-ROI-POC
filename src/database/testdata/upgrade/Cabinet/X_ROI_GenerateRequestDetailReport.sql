USE [cabinet]

--/****** Object:  StoredProcedure [dbo].[ROI_Generate_RequestDetailReport]    Script Date: 07/06/2009  ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_Generate_RequestDetailReport]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_Generate_RequestDetailReport]

USE [cabinet]
GO
/****** Object:  StoredProcedure [dbo].[ROI_Generate_RequestDetailReport]    Script Date: 07/06/2009 14:57:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ROI_Generate_RequestDetailReport]
@requestorTypes xml,
@status xml,
@fromDate datetime,
@toDate datetime
AS
--Create a memory table to hold requestor types
Declare @ReqTypes table (Data varchar(max))
--Fill the table with requestor types
Insert @ReqTypes (Data)
SELECT
	Table1.Column1.value('@value','VARCHAR(max)')
FROM
	@requestorTypes.nodes('/ListContent/requestor-type') AS Table1(Column1)

--Create a memory table to hold request status
Declare @ReqStatus table (Data varchar(max))

Declare @requestor_type varchar(max), @requestor_name varchar(max), @created_dt dateTime, @request_status varchar(max), @request_id int, @hpf_patient_details varchar(max), @hpf_patient_non_hpf_doc_details varchar(max), @roi_patient_details varchar(max), @facility varchar(max), @patient_name varchar(max), @encounter_id varchar(max), @admit_date varchar(max), @patien_type varchar(max), @hpfInfo varchar(max), @roiInfo varchar(max), @pages varchar(max), @statusCreated varchar(max), @hpfNonHpfDocInfo varchar(max)
Declare @TempResults table (RequestorType varchar(max), RequestorName varchar(max), CreatedDate dateTime, RequestStatus varchar(max), RequestId int, Facility varchar(max), PatientName varchar(max), EncounterId varchar(max), AdmitDate varchar(max), PatientType varchar(max), Pages varchar(max), StatusCreated varchar(max))
--Fill the table with status
Insert @ReqStatus (Data)
SELECT
	Table1.Column1.value('@value','VARCHAR(max)')
FROM
	@status.nodes('/ListContent/request-status') AS Table1(Column1)

select * from
(
(select
	   cast( RequestMainXML.query( 'data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
	   cast( RequestMainXML.query( 'data(request/requestor/name)') as varchar(8000)) requestor_name,
	   cast(created_dt as datetime) as  Created_Date,
	   cast(RequestMainXML.query( 'data(request/status)') as varchar(8000)) request_status,
	   cast(roi_requestmain_seq as int) as request_id,
	   cast(pat.aa. value( '(patient/@facility)[1]', 'VARCHAR(max)') as varchar(8000)) as fac,
	   cast(pat. aa.value ( '(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name ,
       cast(enc.aa .value ( '@id' , 'VARCHAR(max)' ) as varchar(8000)) AS Encounter_No,
	   cast(enc.aa .value ( '(admitDate)[1]' , 'VARCHAR(max)' ) as varchar(8000)) AS ADMIN_DATE,
	   cast(enc.aa .value ( '(patientType)[1]' , 'VARCHAR(max)' ) as varchar(8000)) AS PATIENT_TYPE,
	   --doc.n.value('(@type)[1]', 'VARCHAR(max)') AS Doc_Name,
	   --doc.n.value('(doc-id)[1]', 'VARCHAR(max)') AS doc_id,
	   --doc.n.value('(version/@number)[1]', 'VARCHAR(max)') AS Version_Number,
	   cast(enc.aa.value('count((document/version/page/@number))', 'VARCHAR(max)') as varchar(8000))  AS Pg_Number,
	   cast(RequestMainXML.query('data(request/status-changed)') as varchar(8000)) statusCreated,
	   cast(pat.aa.value ( '(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip
FROM ROI_requestmain req
      CROSS APPLY RequestMainXML.nodes('request/item') pat(aa)
      outer APPLY pat.aa.nodes( 'patient/encounter' ) enc(aa)
      --outer APPLY enc.aa.nodes('document') doc(n)
	  --outer APPLY doc.n.nodes('version/page') page(n)

WHERE pat.aa.value( '@source', 'VARCHAR(max)') = 'hpf'
	  and RequestMainXML.value('request[1]/status[1]', 'varchar(max)') in (select * from @ReqStatus)
	  and (created_dt between @fromDate and @toDate)
	  and (RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') in (select * from @ReqTypes))

)
UNION
(select cast( RequestMainXML.query( 'data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
	    cast( RequestMainXML.query( 'data(request/requestor/name)') as varchar(8000)) requestor_name,
	    cast(created_dt as datetime) as  Created_Date,
	    cast( RequestMainXML.query( 'data(request/status)') as varchar(8000)) request_status,
	    cast(roi_requestmain_seq as int) as request_id,
		cast(pat.aa.value('(patient/facility/text())[1]', 'VARCHAR(max)') as varchar(8000)) as facility,
		cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
	    cast(doc.aa.value('(encounter)[1]', 'VARCHAR(max)')  as varchar(8000)) as Encounter_No,
	    cast(doc.aa.value('(date-of-service)[1]', 'VARCHAR(max)') as varchar(8000)) as ADMIN_DATE,
		NULL,
	    --doc.aa.value('(name)[1]', 'VARCHAR(max)') as Doc_Name,
		--doc.aa.value('(@id)[1]', 'VARCHAR(max)') as doc_id,
	    --doc.aa.value('(subtitle)[1]', 'VARCHAR(max)') as subtitle,
	    cast(doc.aa.value('(page-count)[1]', 'VARCHAR') as varchar(8000)) as pagecount,
		cast(RequestMainXML.query('data(request/status-changed)') as varchar(8000)) statusCreated,
	    cast(pat.aa.value ( '(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip

 from roi_requestmain req
 CROSS APPLY RequestMainXML.nodes('request/item') pat(aa)
 cross APPLY pat.aa.nodes('patient/supplemental-encounter/document') doc(aa)
WHERE pat.aa.value( '@source', 'VARCHAR(max)') = 'roi'
	  and RequestMainXML.value('request[1]/status[1]', 'varchar(max)') in (select * from @ReqStatus)
	  and (created_dt between @fromDate and @toDate)
	  and (RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') in (select * from @ReqTypes))
)

UNION

(select cast( RequestMainXML.query( 'data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
	    cast( RequestMainXML.query( 'data(request/requestor/name)') as varchar(8000)) requestor_name,
	    cast(created_dt as datetime) as  Created_Date,
	    cast( RequestMainXML.query( 'data(request/status)') as varchar(8000)) request_status,
	    cast(roi_requestmain_seq as int) as request_id,
		cast(pat.aa. value( '(patient/@facility)[1]', 'VARCHAR(max)') as varchar(8000)) as fac,
		cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
	    cast(doc.aa.value('(encounter)[1]', 'VARCHAR(max)') as varchar(8000)) as Encounter_No,
	    cast(doc.aa.value('(date-of-service)[1]', 'VARCHAR(max)') as varchar(8000)) as ADMIN_DATE,
		NULL,
	    --doc.aa.value('(name)[1]', 'VARCHAR(max)') as Doc_Name,
		--doc.aa.value('(@id)[1]', 'VARCHAR(max)') as doc_id,
	   -- doc.aa.value('(subtitle)[1]', 'VARCHAR(max)') as subtitle,
	    cast(doc.aa.value('(page-count)[1]', 'VARCHAR(max)') as varchar(8000)) as pagecount,
		cast(RequestMainXML.query('data(request/status-changed)') as varchar(8000)) statusCreated,
	    cast(pat.aa.value ( '(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip
 from roi_requestmain req
 CROSS APPLY RequestMainXML.nodes('request/item') pat(aa)
 cross APPLY pat.aa.nodes('patient/supplemental-encounter/document') doc(aa)
WHERE pat.aa.value( '@source', 'VARCHAR(max)') = 'hpf'
	  and RequestMainXML.value('request[1]/status[1]', 'varchar(max)') in (select * from @ReqStatus)
	  and (created_dt between @fromDate and @toDate)
	  and (RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') in (select * from @ReqTypes))
)


) outerTable


left join
(

select RequestId ,  max(PrebillId) PrebillId, 
CASE WHEN BalanceDue = '' THEN '$0.00' 
WHEN BalanceDue = null THEN '$0.00' 
ELSE BalanceDue END BalanceDue, Type
from (
		select a.ROI_RequestMain_Seq requestId, b.ROI_RequestDeliveryCharges_Seq PrebillId,
		cast(b.RequestDeliveryChargesXML.query('for $items in release-item/attribute
																		where $items/@name[.="type"]
																	  return string($items/@value)'
																		) as varchar(8000)) Type,
		cast(b.RequestDeliveryChargesXML.query('for $items in release-item/release-item
																		where $items/@type[.="billing-info"]
																		return
																		for $charges in $items/release-item
																		where $charges/@type[.="charges"]
																		return
																		for $releaseInfo in $charges/release-item
																		where $releaseInfo/@type[.="release-info"]
																		return
																		for $attributes in $releaseInfo/attribute
																		where $attributes/@name[.="balance-due"]
																	  return string($attributes/@value)'
																		) as varchar(8000)) BalanceDue

		from dbo.ROI_RequestDelivery a 
		left join dbo.ROI_RequestDeliveryCharges b
		on a.ROI_RequestDelivery_Seq = b.ROI_RequestDelivery_Seq
) tab 
where tab.Type='PreBill'
group by tab.requestID, tab.BalanceDue, tab.Type

)innerTable
on outerTable.request_id = innerTable.requestId



GO

SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

GRANT  EXECUTE  ON [dbo].[ROI_Generate_RequestDetailReport]  TO [IMNET]
GO

