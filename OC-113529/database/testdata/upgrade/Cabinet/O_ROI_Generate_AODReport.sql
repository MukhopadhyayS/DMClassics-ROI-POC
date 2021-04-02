USE [cabinet]

--/****** Object:  StoredProcedure [dbo].[ROI_Generate_AODReport]    Script Date: 07/06/2009  ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_Generate_AODReport]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_Generate_AODReport]

USE [cabinet]
GO
/****** Object:  StoredProcedure [dbo].[ROI_Generate_AODReport]    Script Date: 07/06/2009 14:57:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ROI_Generate_AODReport]
@mrn varchar(800),
@facilityNames varchar(800),
@patientId  varchar(800),
@reqType varchar(800),
@fromDt datetime,
@toDt datetime

AS
Declare @Facilities table (Data varchar(max))
if @reqType = 'both' set @reqType='%'

--Declare @hpfDetails varchar(max), @hpfInfo varchar(max),@globalDocDetails varchar(max), @globalDocInfo varchar(max), @roiDetails varchar(max), @roiDocInfo varchar(max), @requestor_type varchar(max),@requestor_name varchar(max), @requestor_id varchar(max), @rStatus varchar(max), @payMethod varchar(max),@payAmt varchar(max), @request_id int,@amount_paid varchar(max),@total_cost varchar(max), @invoice_no varchar(max), @invoice_date datetime, @postedBy varchar(max),@paymentMethod varchar(max),@payment varchar(max),@fac varchar(max),@request_reason varchar(max), @disclosureDate datetime, @requestType varchar(max), @nonHPFDocDetails varchar(max), @nonHPFDocInfo varchar(max)
--Declare @TempResults1 table (RequestorName varchar(max),RequestorId varchar(max),RequestId varchar(max),RequestReason varchar(max), ReleaseDt datetime, PatientName varchar(max),EPN varchar(max),Facility varchar(max), MRN varchar(max), Gender varchar(max), Encounter varchar(max),  DocumentType varchar(max), DocName varchar(max), Pages varchar(100), DisclosureDate datetime, PatientId varchar(100), PatientType varchar(100), RequestType varchar(100))

-- data delimiter is @
-- entry delimiter is ;


select

       cast(Patient_Name as varchar(8000)) as Patient_Name,
       cast(Patient_DOB as datetime) as Patient_DOB,
       cast(gender as varchar(8000)) as gender,
       cast(patient_fac as varchar(8000)) as patient_fac,
       cast(Patient_Mrn as varchar(8000)) as Patient_Mrn,
       cast(requestor_id as varchar(8000)) as requestor_id,
       cast(requestor_name as varchar(8000)) as requestor_name,
       cast(DisclosureDate as datetime) as DisclosureDate,
       cast(Discharge_Dt as varchar(8000)) as Discharge_Dt ,
       cast(Patient_Type as varchar(8000)) as Patient_Type,
       cast(request_reason as varchar(8000)) as request_reason,
       cast(request_type as varchar(8000)) as request_type,
       cast(Encounter_No  as varchar(8000)) as Encounter_No,
       cast(Doc_Type as varchar(8000)) as Doc_Type,
       cast(Doc_Name as varchar(8000)) as Doc_Name,
       cast(Pg_Number as varchar(8000)) as Pg_Number,
       cast(request_id as varchar(8000)) as  request_id,
       cast(Subtitle as varchar(8000)) as Subtitle,
       cast(Patient_IsVip as varchar(8000)) as Patient_IsVip
       from  (

--For HPF Patient's Hpf Document
(SELECT
       cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
       cast(pat.aa.value('(patient/dob/text())[1]', 'datetime') as varchar(8000)) as Patient_DOB,
       cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
       cast(enc.aa.value('(facility/text())[1]', 'VARCHAR(max)')  as varchar(8000)) as Patient_Fac,
       cast(pat.aa.value('(patient/@mrn)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Mrn,
       cast(req.RequestMainXML.query('data(request/requestor/@id)') as varchar(8000)) as requestor_id,
       cast(req.RequestMainXML.query('data(request/requestor/name)') as varchar(8000)) as requestor_name,
       cast(del.MODIFIED_DT as datetime) as  DisclosureDate,
       cast(enc.aa.value('(discharge-date/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Discharge_Dt,
       cast(enc.aa.value('(patientType/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Type,
       cast(req.RequestMainXML.query('data(request/request-reason)') as varchar(8000)) as request_reason,
       cast(req.RequestMainXML.query('data(request/request-reason-attribute)') as varchar(8000)) as request_type,
       cast(enc.aa.value('@id', 'VARCHAR(max)') as varchar(8000)) AS Encounter_No,
       cast('Hpf' as varchar(8000)) AS Doc_Type,
       cast(doc.n.value('@type', 'VARCHAR(max)') as varchar(8000)) as Doc_Name,
       cast(doc.n.value('count((version/page/@number))', 'VARCHAR(max)') as varchar(8000)) as Pg_Number,
       cast(req.ROI_RequestMain_Seq as int) as request_id,
       cast(doc.n.value('(subtitle/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Subtitle,
	   cast(pat.aa.value('(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip
--     cast(doc.n.value('(version/@number)[1]', 'VARCHAR(max)') as varchar(8000)) as Version_Number,
--     cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
--     cast(pat.aa.value('(patient/epn/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Epn,
--     cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
--     cast(pat.aa.value('(patient/@id)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Id,

FROM ROI_RequestDelivery as del
CROSS APPLY RequestDeliveryXML.nodes('release/item') pat(aa)
outer APPLY pat.aa.nodes('patient/encounter') enc(aa)
outer APPLY enc.aa.nodes('document') doc(n)
INNER JOIN ROI_RequestMain as req on del.ROI_RequestMain_seq = req.ROI_RequestMain_seq
--INNER JOIN (SELECT MAX(MODIFIED_DT) maxDt, max(roi_requestdelivery_Seq) dId FROM ROI_REQUESTDELIVERY GROUP BY ROI_REQUESTMAIN_SEQ) AS res1
  --  ON del.roi_requestdelivery_seq = res1.dId

WHERE
pat.aa.value('@source', 'VARCHAR(max)') = 'hpf'
AND del.RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True'
AND del.ROI_RequestMain_Seq IN

            (select child from roi_searchlov
                where ((parent=1 and item ='patient.mrn' and value LIKE @mrn+'%')
                or   (parent=1 and item ='patient.facility' and value = @facilityNames))
                or (parent=1 and item ='patient.id' and value = @patientId))

and del.modified_dt between @fromDt and @toDt
and cast(req.RequestMainXML.query('data(request/request-reason-attribute)') as varchar(8000)) LIKE @reqType

)

UNION

--For HPF Patient's Non-Hpf Document
(SELECT
       cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
       cast(pat.aa.value('(patient/dob/text())[1]', 'datetime') as varchar(8000)) as Patient_DOB,
       cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
       cast(doc.aa.value('(facility/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Fac,
       cast(pat.aa.value('(patient/@mrn)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Mrn,
       cast(req.RequestMainXML.query('data(request/requestor/@id)') as varchar(8000)) as requestor_id,
       cast(req.RequestMainXML.query('data(request/requestor/name)') as varchar(8000)) as requestor_name,
       cast(del.MODIFIED_DT as datetime) as  DisclosureDate,
       cast(doc.aa.value('(date-of-service/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Discharge_Dt,
       NULL,
       cast(req.RequestMainXML.query('data(request/request-reason)') as varchar(8000)) as request_reason,
       cast(req.RequestMainXML.query('data(request/request-reason-attribute)') as varchar(8000)) as request_type,
       cast(doc.aa.value('(encounter/text())[1]', 'VARCHAR(max)') as varchar(8000)) AS Encounter_No,
       cast('Non-HPF' as varchar(8000)) AS Doc_Type,
       cast(doc.aa.value('(name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Doc_Name,
       cast(doc.aa.value('(page-count/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Pg_Number,
       cast(req.ROI_RequestMain_Seq as int) as request_id,
       cast(doc.aa.value('(subtitle/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Subtitle,
	   cast(pat.aa.value('(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip

--     cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
--     cast(pat.aa.value('(patient/epn/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Epn,
--     cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
--     NULL,
--     cast(pat.aa.value('(patient/@id)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Id,

FROM ROI_RequestDelivery as del
CROSS APPLY RequestDeliveryXML.nodes('release/item') pat(aa)
outer APPLY pat.aa.nodes('patient/supplemental-encounter/document') doc(aa)
INNER JOIN ROI_RequestMain as req on del.ROI_RequestMain_seq = req.ROI_RequestMain_seq
--INNER JOIN (SELECT MAX(MODIFIED_DT) maxDt, max(roi_requestdelivery_Seq) dId FROM ROI_REQUESTDELIVERY GROUP BY ROI_REQUESTMAIN_SEQ) AS res1
  --  ON del.roi_requestdelivery_seq = res1.dId

WHERE
pat.aa.value('@source', 'VARCHAR(max)') = 'hpf'
AND del.RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True'
AND del.ROI_RequestMain_Seq IN

            (select child from roi_searchlov
                where ((parent=1 and item ='patient.mrn' and value LIKE @mrn+'%')
                or   (parent=1 and item ='patient.facility' and value = @facilityNames))
                or (parent=1 and item ='patient.id' and value = @patientId))

and del.modified_dt between @fromDt and @toDt
and cast(req.RequestMainXML.query('data(request/request-reason-attribute)') as varchar(8000)) LIKE @reqType

)

UNION

--For HPF Patient's Global Document
(SELECT
       cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
       cast(pat.aa.value('(patient/dob/text())[1]', 'datetime') as varchar(8000)) as Patient_DOB,
       cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
       '' as Patient_Fac,
       cast(pat.aa.value('(patient/@mrn)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Mrn,
       cast(req.RequestMainXML.query('data(request/requestor/@id)') as varchar(8000)) as requestor_id,
       cast(req.RequestMainXML.query('data(request/requestor/name)') as varchar(8000)) as requestor_name,
       cast(del.MODIFIED_DT as datetime) as  DisclosureDate,
       NULL,
       NULL,
       cast(req.RequestMainXML.query('data(request/request-reason)') as varchar(8000)) as request_reason,
       cast(req.RequestMainXML.query('data(request/request-reason-attribute)') as varchar(8000)) as request_type,
       cast(global.aa.value('(encounter/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Encounter_No,
       cast('Global' as varchar(8000)) AS Doc_Type,
       cast(global.aa.value('(@type)[1]', 'VARCHAR(max)') as varchar(8000)) as Doc_Name,
       cast(ver.n.value('(page/@number)[1]', 'VARCHAR(max)') as varchar(8000)) as Pg_Number,
       cast(req.ROI_RequestMain_Seq as int) as request_id,
       cast(global.aa.value('(subtitle/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Subtitle,
	   cast(pat.aa.value('(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip

--     cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
--     cast(pat.aa.value('(patient/epn/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Epn,
--     cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
--     cast(ver.n.value('(@number)[1]', 'VARCHAR(max)') as varchar(8000)) as Version_Number,
--     cast(pat.aa.value('(patient/@id)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Id,
--     NULL,

FROM ROI_RequestDelivery as del
CROSS APPLY RequestDeliveryXML.nodes('release/item') pat(aa)
outer APPLY pat.aa.nodes('patient/global-document') global(aa)
outer APPLY global.aa.nodes('version') ver(n)
INNER JOIN ROI_RequestMain as req on del.ROI_RequestMain_seq = req.ROI_RequestMain_seq
--INNER JOIN (SELECT MAX(MODIFIED_DT) maxDt, max(roi_requestdelivery_Seq) dId FROM ROI_REQUESTDELIVERY GROUP BY ROI_REQUESTMAIN_SEQ) AS res1
  --  ON del.roi_requestdelivery_seq = res1.dId

WHERE
pat.aa.value('@source', 'VARCHAR(max)') = 'hpf'
AND del.RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True'
AND del.ROI_RequestMain_Seq IN

            (select child from roi_searchlov
                where ((parent=1 and item ='patient.mrn' and value LIKE @mrn+'%')
                or   (parent=1 and item ='patient.facility' and value = @facilityNames))
                or (parent=1 and item ='patient.id' and value = @patientId))

and del.modified_dt between @fromDt and @toDt
and cast(req.RequestMainXML.query('data(request/request-reason-attribute)') as varchar(8000)) LIKE @reqType

)

UNION

--For ROI Patient's Non-Hpf Document
(SELECT
       cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
       cast(pat.aa.value('(patient/dob/text())[1]', 'datetime') as varchar(8000)) as Patient_DOB,
       cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
       cast(doc.aa.value('(facility/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Fac,
       cast(pat.aa.value('(patient/mrn/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Mrn,
       cast(req.RequestMainXML.query('data(request/requestor/@id)') as varchar(8000)) as requestor_id,
       cast(req.RequestMainXML.query('data(request/requestor/name)') as varchar(8000)) as requestor_name,
       cast(del.MODIFIED_DT as datetime) as  DisclosureDate,
       cast(doc.aa.value('(date-of-service/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Discharge_Dt,
       NULL,
       cast(req.RequestMainXML.query('data(request/request-reason)') as varchar(8000)) as request_reason,
       cast(req.RequestMainXML.query('data(request/request-reason-attribute)') as varchar(8000)) as request_type,
       cast(doc.aa.value('(encounter/text())[1]', 'VARCHAR(max)') as varchar(8000)) AS Encounter_No,
       cast('Non-HPF' as varchar(8000)) AS Doc_Type,
       cast(doc.aa.value('(name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Doc_Name,
       cast(doc.aa.value('(page-count/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Pg_Number,
       cast(req.ROI_RequestMain_Seq as int) as request_id,
       cast(doc.aa.value('(subtitle/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Subtitle,
	   cast(pat.aa.value('(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip

--     cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
--     cast(pat.aa.value('(patient/epn/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Epn,
--     cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
--     cast(pat.aa.value('(patient/@id)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Id,
--     NULL,

FROM ROI_RequestDelivery as del
CROSS APPLY RequestDeliveryXML.nodes('release/item') pat(aa)
outer APPLY pat.aa.nodes('patient/supplemental-encounter/document') doc(aa)
INNER JOIN ROI_RequestMain as req on del.ROI_RequestMain_seq = req.ROI_RequestMain_seq
--INNER JOIN (SELECT MAX(MODIFIED_DT) maxDt, max(roi_requestdelivery_Seq) dId FROM ROI_REQUESTDELIVERY GROUP BY ROI_REQUESTMAIN_SEQ) AS res1
  --  ON del.roi_requestdelivery_seq = res1.dId

WHERE
pat.aa.value('@source', 'VARCHAR(max)') = 'roi'
AND del.RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True'
AND del.ROI_RequestMain_Seq IN

            (select child from roi_searchlov
                where ((parent=1 and item ='patient.mrn' and value LIKE @mrn+'%')
                or   (parent=1 and item ='patient.facility' and value = @facilityNames))
                or (parent=1 and item ='patient.id' and value = @patientId))

and pat.aa.value('(patient/@id)[1]', 'VARCHAR(max)') = @patientId
and del.modified_dt between @fromDt and @toDt
and cast(req.RequestMainXML.query('data(request/request-reason-attribute)') as varchar(8000)) LIKE @reqType

)
) as res
WHERE
-- Patient_Fac = @facilitynames
Patient_Mrn LIKE @mrn+'%'
and Doc_Name != 'NULL'
ORDER BY DisclosureDate DESC, requestor_name,request_type

GO
GRANT EXECUTE ON [dbo].[ROI_Generate_AODReport] TO [IMNET]
GO