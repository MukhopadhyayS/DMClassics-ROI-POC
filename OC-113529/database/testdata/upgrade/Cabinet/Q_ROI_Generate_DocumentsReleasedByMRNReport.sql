USE [cabinet]

--/****** Object:  StoredProcedure [dbo].[ROI_Generate_DocumentsReleasedByMRNReport]    Script Date: 07/06/2009  ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_Generate_DocumentsReleasedByMRNReport]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_Generate_DocumentsReleasedByMRNReport]

USE [cabinet]
GO
/****** Object:  StoredProcedure [dbo].[ROI_Generate_DocumentsReleasedByMRNReport]    Script Date: 07/06/2009 14:57:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ROI_Generate_DocumentsReleasedByMRNReport]
@mrn varchar(800),
@facilityNames xml
AS

Declare @Facilities table (Data varchar(max))

Declare @hpf_patient_details varchar(max), @hpfInfo varchar(max),@Global_Doc_Details varchar(max), @globalDocInfo varchar(max), @roi_patient_details varchar(max), @roiDocInfo varchar(max), @requestor_type varchar(max),@requestor_name varchar(max), @requestor_id varchar(max), @request_id int, @release_dt datetime, @nonHPF_Doc_Details varchar(max), @nonHPFDocInfo varchar(max)
Declare @TempResults1 table (RequestorType varchar(max),RequestorName varchar(max),RequestorId varchar(max),RequestId varchar(max),Release_Dt datetime, Patient varchar(max), EPN varchar(max),Facility varchar(max), MRN varchar(max), Gender varchar(max), Encounter varchar(max),  DocumentType varchar(max), DocName varchar(max), Pages varchar(100), PatientId varchar(100))
--Fill the table with requestor types
Insert @Facilities (Data)
SELECT
    FacilityTable.Facility.value('@value','VARCHAR(max)')
FROM
    @facilityNames.nodes('/ListContent/facility') AS FacilityTable(Facility)
-- data delimiter is @
-- entry delimiter is ;

SELECT
    cast(Patient_Id as varchar(8000)) as Patient_Id,
    cast(Patient_Name as varchar(8000)) as Patient_Name,
    cast(Patient_Epn as varchar(8000)) as Patient_Epn,
    cast(Patient_Fac as varchar(8000)) as Patient_Fac,
    cast(Patient_Mrn as varchar(8000)) as Patient_Mrn,
    cast(gender as varchar(8000)) as gender,
    cast(requestor_type as varchar(8000)) as requestor_type,
    cast(requestor_name as varchar(8000)) as requestor_name,
    cast(request_id as varchar(8000)) as request_id,
    cast(Encounter_No as varchar(8000)) as Encounter_No,
    cast(Doc_Type as varchar(8000)) as Doc_Type,
    cast(Doc_Name as varchar(8000)) as Doc_Name,
    cast(Pg_Number as varchar(8000)) as Pg_Number,
    cast(release_dt as datetime) as release_dt,
    cast(Subtitle as varchar(8000)) as Subtitle,
    cast(Patient_IsVip as varchar(8000)) as Patient_IsVip
    from  (

--For HPF Patient's Hpf Document
(SELECT

        cast('0' as varchar) AS Patient_Id,
--      NULL,
        cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
        cast(pat.aa.value('(patient/epn/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Epn,
        cast(pat.aa.value('(patient/@facility)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Fac,
        cast(pat.aa.value('(patient/@mrn)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Mrn,
        cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
        cast(req.RequestMainXML.query('data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
        cast(req.RequestMainXML.query('data(request/requestor/name)') as varchar(8000)) requestor_name,
        cast(req.ROI_RequestMain_Seq as int) as request_id,
        cast(enc.aa.value('@id', 'VARCHAR(max)') as varchar(8000)) AS Encounter_No,
        cast('Hpf' as varchar(8000)) AS Doc_Type,
        cast(doc.n.value('@type', 'VARCHAR(max)') as varchar(8000)) as Doc_Name,
        cast(doc.n.value('count((version/page/@number))', 'VARCHAR(max)') as varchar(8000)) as Pg_Number,
        cast(res1.maxDt as datetime) as  release_dt,
        cast(doc.n.value('(subtitle/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Subtitle,
		cast(pat.aa.value('(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip
        --cast(req.RequestMainXML.query('data(request/requestor/@id)') as varchar(8000)) requestor_id,
        --cast(doc.n.value('(version/@number)[1]', 'VARCHAR(max)') as varchar(8000)) as Version_Number,


FROM ROI_RequestDelivery as del
CROSS APPLY RequestDeliveryXML.nodes('release/item') pat(aa)
outer APPLY pat.aa.nodes('patient/encounter') enc(aa)
outer APPLY enc.aa.nodes('document') doc(n)
INNER JOIN ROI_RequestMain as req on del.ROI_RequestMain_seq = req.ROI_RequestMain_seq
INNER JOIN (SELECT MAX(MODIFIED_DT) maxDt, roi_requestdelivery_Seq dId, roi_requestmain_seq FROM ROI_REQUESTDELIVERY where  RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True' GROUP BY ROI_REQUESTMAIN_SEQ, roi_requestdelivery_Seq ) AS res1
    ON del.roi_requestdelivery_seq = res1.dId

WHERE
pat.aa.value('@source', 'VARCHAR(max)') = 'hpf'
AND del.RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True'
AND del.ROI_RequestMain_Seq IN (SELECT child FROM ROI_SearchLov WHERE parent=1 AND item ='patient.mrn' AND VALUE LIKE @mrn+'%')
AND pat.aa.value('(patient/@facility)[1]', 'VARCHAR(max)') IN (select * from @Facilities)
)

UNION


--For HPF Patient's Non Hpf Document
(
SELECT

        cast('0' as varchar) AS Patient_Id,
--      NULL,
        cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
        cast(pat.aa.value('(patient/epn/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Epn,
        cast(pat.aa.value('(patient/@facility)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Fac,
        cast(pat.aa.value('(patient/@mrn)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Mrn,
        cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
        cast(req.RequestMainXML.query('data(request/requestor/type-name)') as varchar(8000)) requestor_type,
        cast(req.RequestMainXML.query('data(request/requestor/name)') as varchar(8000)) requestor_name,
        cast(req.ROI_RequestMain_Seq as int) as request_id,
        cast(doc.aa.value('(encounter/text())[1]', 'VARCHAR(max)') as varchar(8000)) AS Encounter_No,
        cast('Non-HPF' as varchar(8000)) AS Doc_Type,
        cast(doc.aa.value('(name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Doc_Name,
        cast(doc.aa.value('(page-count/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Pg_Number,
        cast(res1.maxDt as datetime) as  release_dt,
        cast(doc.aa.value('(subtitle/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Subtitle,
        cast(pat.aa.value('(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip
        --cast(req.RequestMainXML.query('data(request/requestor/@id)') as varchar(8000)) requestor_id,
        --NULL,


FROM ROI_RequestDelivery as del
CROSS APPLY RequestDeliveryXML.nodes('release/item') pat(aa)
outer APPLY pat.aa.nodes('patient/supplemental-encounter/document') doc(aa)

INNER JOIN ROI_RequestMain as req on del.ROI_RequestMain_seq = req.ROI_RequestMain_seq
INNER JOIN (SELECT MAX(MODIFIED_DT) maxDt, roi_requestdelivery_Seq dId, roi_requestmain_seq FROM ROI_REQUESTDELIVERY where  RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True' GROUP BY ROI_REQUESTMAIN_SEQ, roi_requestdelivery_Seq ) AS res1
    ON del.roi_requestdelivery_seq = res1.dId

WHERE
pat.aa.value('@source', 'VARCHAR(max)') = 'hpf'
AND del.RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True'
AND del.ROI_RequestMain_Seq IN (SELECT child FROM ROI_SearchLov WHERE parent=1 AND item ='patient.mrn' AND VALUE LIKE @mrn+'%')
AND pat.aa.value('(patient/@facility)[1]', 'VARCHAR(max)') IN (select * from @Facilities)
)

UNION


--For HPF Patient's Global Document
(
SELECT

        cast('0' as varchar) AS Patient_Id,
--      NULL,
        cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
        cast(pat.aa.value('(patient/epn/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Epn,
        cast(pat.aa.value('(patient/@facility)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Fac,
        cast(pat.aa.value('(patient/@mrn)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Mrn,
        cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
        cast(req.RequestMainXML.query('data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
        cast(req.RequestMainXML.query('data(request/requestor/name)') as varchar(8000)) requestor_name,
        cast(req.ROI_RequestMain_Seq as int) as request_id,
        cast(global.aa.value('(encounter/text())[1]', 'VARCHAR(max)') as varchar(8000)) AS Encounter_No,
        cast('Global' as varchar(8000)) AS Doc_Type,
        cast(global.aa.value('(@type)[1]', 'VARCHAR(max)') as varchar(8000)) as Doc_Name,
        cast(ver.n.value('(page/@number)[1]', 'VARCHAR(max)') as varchar(8000)) as Pg_Number,
        cast(res1.maxDt as datetime) as  release_dt,
        cast(global.aa.value('(subtitle/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Subtitle,
		cast(pat.aa.value('(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip
        --cast(req.RequestMainXML.query('data(request/requestor/@id)') as varchar(8000)) requestor_id,
        --cast(ver.n.value('(@number)[1]', 'VARCHAR(max)') as varchar(8000)) as Version_Number,

FROM ROI_RequestDelivery as del
CROSS APPLY RequestDeliveryXML.nodes('release/item') pat(aa)
outer APPLY pat.aa.nodes('patient/global-document') global(aa)
outer APPLY global.aa.nodes('version') ver(n)
INNER JOIN ROI_RequestMain as req on del.ROI_RequestMain_seq = req.ROI_RequestMain_seq
INNER JOIN (SELECT MAX(MODIFIED_DT) maxDt, roi_requestdelivery_Seq dId, roi_requestmain_seq FROM ROI_REQUESTDELIVERY where  RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True' GROUP BY ROI_REQUESTMAIN_SEQ, roi_requestdelivery_Seq ) AS res1
    ON del.roi_requestdelivery_seq = res1.dId


WHERE
pat.aa.value('@source', 'VARCHAR(max)') = 'hpf'
AND del.RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True'
AND del.ROI_RequestMain_Seq IN (SELECT child FROM ROI_SearchLov WHERE parent=1 AND item ='patient.mrn' AND VALUE LIKE @mrn+'%')
AND pat.aa.value('(patient/@facility)[1]', 'VARCHAR(max)') IN (select * from @Facilities)
)


UNION

--For ROI Patient's Non Hpf Document
(
SELECT
        cast(pat.aa.value('(patient/@id)[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Id,
        cast(pat.aa.value('(patient/name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Name,
        cast(pat.aa.value('(patient/epn/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Epn,
        cast(pat.aa.value('(patient/facility/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Fac,
        cast(pat.aa.value('(patient/mrn/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_Mrn,
        cast(pat.aa.value('(patient/gender/text())[1]', 'VARCHAR(max)') as varchar(8000)) as gender,
        cast(req.RequestMainXML.query('data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
        cast(req.RequestMainXML.query('data(request/requestor/name)') as varchar(8000)) requestor_name,
        cast(req.ROI_RequestMain_Seq as int) as request_id,
        cast(doc.aa.value('(encounter/text())[1]', 'VARCHAR(max)') as varchar(8000)) AS Encounter_No,
        cast('Non-HPF' as varchar(8000)) AS Doc_Type,
        cast(doc.aa.value('(name/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Doc_Name,
        cast(doc.aa.value('(page-count/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Pg_Number,
        cast(res1.maxDt as datetime) as  release_dt,
        cast(doc.aa.value('(subtitle/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Subtitle,
		cast(pat.aa.value('(patient/is-vip/text())[1]', 'VARCHAR(max)') as varchar(8000)) as Patient_IsVip
        --cast(req.RequestMainXML.query('data(request/requestor/@id)') as varchar(8000)) requestor_id,
        --NULL,


FROM ROI_RequestDelivery as del
CROSS APPLY RequestDeliveryXML.nodes('release/item') pat(aa)
outer APPLY pat.aa.nodes('patient/supplemental-encounter/document') doc(aa)

INNER JOIN ROI_RequestMain as req on del.ROI_RequestMain_seq = req.ROI_RequestMain_seq
INNER JOIN (SELECT MAX(MODIFIED_DT) maxDt, roi_requestdelivery_Seq dId, roi_requestmain_seq FROM ROI_REQUESTDELIVERY where  RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True' GROUP BY ROI_REQUESTMAIN_SEQ, roi_requestdelivery_Seq ) AS res1
    ON del.roi_requestdelivery_seq = res1.dId


WHERE
pat.aa.value('@source', 'VARCHAR(max)') = 'roi'
AND del.RequestDeliveryXML.value('release[1]/@is-released', 'varchar(max)') = 'True'
AND del.ROI_RequestMain_Seq IN (SELECT child FROM ROI_SearchLov WHERE parent=1 AND item ='patient.mrn' AND VALUE LIKE @mrn+'%')
AND pat.aa.value('(patient/facility/text())[1]', 'VARCHAR(max)') IN (select * from @Facilities)
)

) as res
where Patient_Fac in (select * from @Facilities) and Patient_Mrn like @mrn+'%' and Doc_Name != 'NULL'

GO
GRANT EXECUTE ON [dbo].[ROI_Generate_DocumentsReleasedByMRNReport] TO [IMNET]
GO