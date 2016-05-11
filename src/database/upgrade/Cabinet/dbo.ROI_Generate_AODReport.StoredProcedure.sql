USE [cabinet]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_Generate_AODReport]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_Generate_AODReport]
GO

-- Must be set because of XML datatype
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

/*****************************************************************
* Procedure Name: [ROI_Generate_AODReport] 
* Description: 
*
*		Name			Type		Description
*		---------		-------		----------------
* Database: CABINET
* 
* NOTES: 
* Revision History:
*	Name			Date		Changes
*	---------		-------		-------------
*	RC					created
*	MD Meersma		10/19/2010	Add NOLOCK hint
*	Joseph Chen     12/15/2010  add SET QUOTED_IDENTIFIER ON
*	ROI offshore	12/22/2010	CR 344252
*	MD Meersma/Alabi	6/22/2011	ROI_SearchLOV and exists for Patient.facility
*	Nattershah		02/02/2012	modified for CR 364,715
*	Howard			09/27/2012	modified for ROI-XML conversion to ROI table structure
*	Prasun Kumar	03/05/2013	CR #375,011
*	OFS             04/05/2013  modified for CR#379,098 
*	OFS             04/05/2013  modified for CR#352,377 
*	OFS             08/06/2013  US4859 - TA14124 Altered Stored proc for adding address
*	OFS             08/06/2013  US4860 - TA14135 Alter Store proc. To include self pay on encounter 
*   OFS             08/16/2013  US4859 - TA14124 Altered Stored proc for adding address , formatted 
*										 with Address 1, Adress 2, State, City and Country.
*   OFS             09/06/2013  US4859 - TA14124 and Defect ID DE1498, Issue Fix for the migrated data
*/

CREATE PROCEDURE [dbo].[ROI_Generate_AODReport]
@mrn varchar(800),
@facilityNames varchar(800),
@patientId  varchar(800),
@reqType varchar(800),
@fromDt datetime,
@toDt datetime

AS
--Removed Unused declaration
--Declare @Facilities table (Data varchar(max))
if @reqType = 'both' set @reqType='%'

IF(@patientId > 0)
BEGIN
--For ROI Patient's Non-HPF Documents and Attachments
SELECT DISTINCT
	CAST(Patient_Name as varchar(256)) Patient_Name,
	Patient_DOB,
	CAST(gender as varchar(2)) gender,
	CAST(patient_fac as varchar(256)) patient_fac ,
	CAST(Patient_Mrn as varchar(30)) Patient_Mrn,
	requestor_id,
	requestor_name,
	temp.addressLine1,
    temp.addressLine2,
    temp.addressLine3,	   
    SUBSTRING (temp.city, 1, LEN(temp.city) - 1) RequestorAddress,
	DisclosureDate,
	Discharge_Dt,
	Patient_Type,
	request_reason,
	request_type,
	Encounter_No,
	SelfPay,
	Doc_Type,
	Doc_Name,
	Pg_Number,
	request_id,
	Subtitle,
	Patient_IsVip,
	Patient_IsLocked,
	Patient_Epn
FROM  
(
--For ROI Patient's Non-Hpf Document
SELECT
 	   case  
          when roiPatient.LASTNAME IS NULL then '' 
          else roiPatient.LASTNAME
       end 
       +  case  
		  when roiPatient.FIRSTNAME = '' then ''
          when roiPatient.FIRSTNAME IS NULL then '' 
          else ', ' + roiPatient.FIRSTNAME 
       end as Patient_Name,
       roiPatient.DOB as Patient_DOB,
       roiPatient.gender as gender,
        (isnull(cast(roiPatient.FACILITY as varchar(256)), roiPatient.FreeFormFacility)) as patient_fac,
       roiPatient.MRN as Patient_Mrn,
       cast(requestor.ROI_Requestor_Seq as varchar(50))  as requestor_id,
	   case  
          when requestor.NameLast IS NULL then '' 
          else requestor.NameLast
       end 
       +  case  
		  when requestor.NameFirst = '' then ''
          when requestor.NameFirst IS NULL then '' 
          else ', ' + requestor.NameFirst 
       end 
       +  case  
          when requestor.NameMiddle IS NULL then '' 
          else ' ' + requestor.NameMiddle 
       end as requestor_name, 
	   coreDelivery.Modified_Dt as DisclosureDate,
       supplementalDocsCore.DATEOFSERVICE as Discharge_Dt ,
       cast(NULL as varchar(256)) as Patient_Type,
	   --roiReason.DisplayText as request_reason,
	   requestCore.ROI_Reason as request_reason,
       requestCore.RequestReasonAttribute as request_type,
       supplementalDocsCore.ENCOUNTER  as Encounter_No,
	   0 as SelfPay,
       'Non-HPF' as Doc_Type,
       supplementalDocsCore.DOCNAME as Doc_Name,
       cast(supplementalDocsCore.PAGECOUNT as varchar(20)) as Pg_Number,
       cast(requestCore.ROI_RequestCore_Seq as varchar(50)) as  request_id,
       supplementalDocsCore.SUBTITLE as Subtitle,
	   case
			when roiPatient.VIP = 0 then 'false' else 'true' 
	   end as Patient_IsVip,
	   case
			when roiPatient.patientlocked = 0 then 'false' else 'true'
	   end as Patient_IsLocked,
	   isnull(roiPatient.EPN,'') as Patient_Epn
FROM 
       ROI_SupplementalPatientsCore AS roiPatient,
       ROI_SupplementalDocumentsCore AS supplementalDocsCore,
       ROI_RequestCoreDeliverytoROI_SupplementalDocumentsCore AS coreDeliveryToSupplementalDocs,
       ROI_RequestCoreDelivery AS coreDelivery,
       ROI_RequestCoreRequestor as requestor,
       ROI_RequestCore as requestCore
       --ROI_Reason as roiReason
WHERE     
		roiPatient.ROI_SupplementalPatients_Seq = @patientId
		AND roiPatient.ROI_SupplementalPatientsCore_Seq = supplementalDocsCore.ROI_SupplementalPatientsCore_Seq
		AND supplementalDocsCore.ROI_SupplementalDocumentsCore_Seq = coreDeliveryToSupplementalDocs.ROI_SupplementalDocumentsCore_Seq
		AND coreDeliveryToSupplementalDocs.ROI_RequestCoreDelivery_Seq = coreDelivery.ROI_RequestCoreDelivery_Seq
		AND coreDelivery.ROI_RequestCore_Seq = requestCore.ROI_RequestCore_Seq
		AND requestCore.ROI_RequestCore_Seq = requestor.ROI_RequestCore_Seq
		--AND roiReason.ROI_Reason_Seq = requestCore.ROI_Reason_Seq
		AND requestCore.RequestReasonAttribute like @reqType
		AND coreDelivery.Modified_Dt between @fromDt and @toDt 
		
UNION

--For ROI Patient's Non-Hpf Attachment
SELECT

 	   case  
          when roiPatient.LASTNAME IS NULL then '' 
          else roiPatient.LASTNAME
       end 
       +  case  
		  when roiPatient.FIRSTNAME = '' then ''
          when roiPatient.FIRSTNAME IS NULL then '' 
          else ', ' + roiPatient.FIRSTNAME 
       end as Patient_Name,
       roiPatient.DOB as Patient_DOB,
       roiPatient.gender as gender,
        (isnull(cast(roiPatient.FACILITY as varchar(256)), roiPatient.FreeFormFacility)) as patient_fac,
       roiPatient.MRN as Patient_Mrn,
       cast(requestor.ROI_Requestor_Seq as varchar(50))  as requestor_id,
	   case  
          when requestor.NameLast IS NULL then '' 
          else requestor.NameLast
       end 
       +  case  
		  when requestor.NameFirst = '' then ''
          when requestor.NameFirst IS NULL then '' 
          else ', ' + requestor.NameFirst 
       end 
       +  case  
          when requestor.NameMiddle IS NULL then '' 
          else ' ' + requestor.NameMiddle 
       end as requestor_name, 
	   coreDelivery.Modified_Dt as DisclosureDate,
       supplementalAttachmentsCore.DATEOFSERVICE as Discharge_Dt ,
       cast(NULL as varchar(256))  as Patient_Type,
	   --roiReason.DisplayText as request_reason,
       requestCore.ROI_Reason as request_reason,
       requestCore.RequestReasonAttribute as request_type,
       supplementalAttachmentsCore.ENCOUNTER  as Encounter_No,
	   0 as SelfPay,
       'Attachment' as Doc_Type,
       supplementalAttachmentsCore.FILENAME + '.' + supplementalAttachmentsCore.FILEEXT as Doc_Name,
       cast(supplementalAttachmentsCore.PAGECOUNT as varchar(20))  as Pg_Number,
       cast(requestCore.ROI_RequestCore_Seq as varchar(50))  as  request_id,
       supplementalAttachmentsCore.SUBTITLE as Subtitle,
       case
			when roiPatient.VIP=0 then 'false' else 'true' 
	   end as Patient_IsVip,
	   case
			when roiPatient.patientlocked=0 then 'false' else 'true'
	   end as Patient_IsLocked,
	   isnull(roiPatient.EPN,'') as Patient_Epn
FROM 
       ROI_SupplementalPatientsCore AS roiPatient,
       ROI_SupplementalAttachmentsCore AS supplementalAttachmentsCore,
       ROI_RequestCoreDeliverytoROI_SupplementalAttachmentsCore AS coreDeliveryToSupplementalAttachments,
       ROI_RequestCoreDelivery AS coreDelivery,
       ROI_RequestCoreRequestor as requestor,
       ROI_RequestCore as requestCore
       --ROI_Reason as roiReason
WHERE     
		roiPatient.ROI_SupplementalPatients_Seq = @patientId
		AND roiPatient.ROI_SupplementalPatientsCore_Seq = supplementalAttachmentsCore.ROI_SupplementalPatientsCore_Seq
		AND supplementalAttachmentsCore.ROI_SupplementalAttachmentsCore_Seq = coreDeliveryToSupplementalAttachments.ROI_SupplementalAttachmentsCore_Seq
		AND coreDeliveryToSupplementalAttachments.ROI_RequestCoreDelivery_Seq = coreDelivery.ROI_RequestCoreDelivery_Seq
		AND coreDelivery.ROI_RequestCore_Seq = requestCore.ROI_RequestCore_Seq
		AND requestCore.ROI_RequestCore_Seq = requestor.ROI_RequestCore_Seq
		--AND roiReason.ROI_Reason_Seq = requestCore.ROI_Reason_Seq
		AND requestCore.RequestReasonAttribute like @reqType
		AND coreDelivery.Modified_Dt between @fromDt and @toDt 

) as nonHpf
LEFT JOIN
	(SELECT 
			requestorToAddress.ROI_Requestor_Seq requestorId,
           (CASE WHEN requestorAddress.AddressLine1 IS NOT NULL AND requestorAddress.AddressLine1 <> '' THEN (requestorAddress.AddressLine1) ELSE '' END) addressLine1,
		   (CASE WHEN requestorAddress.AddressLine2 IS NOT NULL AND requestorAddress.AddressLine2 <> '' THEN (requestorAddress.AddressLine2) ELSE '' END) addressLine2, 
		   (CASE WHEN requestorAddress.AddressLine3 IS NOT NULL AND requestorAddress.AddressLine3 <> '' THEN (requestorAddress.AddressLine3) ELSE '' END) addressLine3,
		   ((CASE WHEN requestorAddress.City IS NOT NULL AND requestorAddress.City <> '' THEN (requestorAddress.City + ', ') ELSE '' END) + 
		   (CASE WHEN requestorAddress.State IS NOT NULL AND requestorAddress.State <> '' THEN (requestorAddress.State + ' ') ELSE '' END) + 
		   (CASE WHEN requestorAddress.PostalCode IS NOT NULL AND requestorAddress.PostalCode <> '' THEN (requestorAddress.PostalCode + ', ') ELSE '' END) +
		   COALESCE(((SELECT [country].[Country_Name] FROM [dbo].[ROI_CountryLov] country WHERE country.ROI_CountryLovID = requestorAddress.ROI_CountryLovID) + ', '), ' ')
		   ) city
		FROM  
			[dbo].[ROI_RequestorToAddress] requestorToAddress,
			[dbo].[ROI_Address] requestorAddress
		
		WHERE [requestorToAddress].[ROI_AddressType_Seq] = (SELECT [ROI_AddressType_Seq] FROM [dbo].[ROI_AddressType] WHERE [Name] = 'Main')
			AND [requestorAddress].[ROI_Address_Seq] = [requestorToAddress].[ROI_Address_Seq]) as temp
	ON nonHpf.requestor_id = temp.requestorId
			
ORDER BY nonHpf.DisclosureDate DESC, nonHpf.requestor_name, nonHpf.request_type

END
ELSE IF(@patientId = 0)
BEGIN

--For Hpf Patient's -Hpf Document, Non-Hpf Documents, Global Documents, and Attachments
SELECT
	cast(Patient_Name as varchar(256)) Patient_Name,
	Patient_DOB,
	cast(gender as varchar(2)) gender,
	cast(patient_fac as varchar(20)) patient_fac,
	cast(Patient_Mrn as varchar(30)) Patient_Mrn,
	requestor_id,
	requestor_name,
	temp.addressLine1,
    temp.addressLine2,
    temp.addressLine3,	   
	SUBSTRING (temp.city, 1, LEN(temp.city) - 1) RequestorAddress,
	DisclosureDate,
	Discharge_Dt ,
	Patient_Type,
	request_reason,
	request_type,
	Encounter_No,
	SelfPay,
	Doc_Type,
	Doc_Name,
	Pg_Number,
	request_id,
	Subtitle,
	Patient_IsVip,
	Patient_IsLocked,
	Patient_Epn
FROM  
(
--For Hpf Patient's Hpf Document
SELECT
       hpfPatient.NAME  as Patient_Name,
       hpfPatient.DOB as Patient_DOB,
       hpfPatient.SEX  as gender,
       hpfPatient.FACILITY  as patient_fac,
       hpfPatient.MRN  as Patient_Mrn,
       cast(requestor.ROI_Requestor_Seq as varchar(50)) as requestor_id,
	   case  
          when requestor.NameLast IS NULL then '' 
          else requestor.NameLast
       end 
       +  case  
		  when requestor.NameFirst = '' then ''
          when requestor.NameFirst IS NULL then '' 
          else ', ' + requestor.NameFirst 
       end 
       +  case  
          when requestor.NameMiddle IS NULL then '' 
          else ' ' + requestor.NameMiddle 
       end as requestor_name, 
       coreDelivery.Modified_Dt as DisclosureDate,
       hpfEncounter.DISCHARGED as Discharge_Dt ,
       cast(hpfEncounter.PT_TYPE as varchar(10)) as Patient_Type,
	   --roiReason.DisplayText as request_reason,
       requestCore.ROI_Reason as request_reason,
       requestCore.RequestReasonAttribute  as request_type,
       hpfEncounter.ENCOUNTER as Encounter_No,
	   coreDeliveryToROIPages.PAYOR_SELF_PAY as SelfPay,
       'Hpf' as Doc_Type,
       hpfDocuments.DOC_NAME as Doc_Name,
       --cast(hpfPages.page as varchar(8000)) as Pg_Number,
	   	cast((SELECT COUNT(page.ROI_Pages_Seq) 
			 FROM ROI_Pages page, ROI_Versions version
			 WHERE page.ROI_Versions_Seq = version.ROI_Versions_Seq 
				AND version.ROI_Documents_Seq = hpfDocuments.ROI_Documents_Seq) as varchar(50)) as Pg_Number,
		
       cast(requestCore.ROI_RequestCore_Seq as varchar(50))  as  request_id,
       hpfDocuments.SUBTITLE as Subtitle,
       case
			when hpfPatient.VIP=0 then 'false' else 'true' 
	   end as Patient_IsVip,
	   case
			when hpfPatient.lockout=0 then 'false' else 'true'
	   end as Patient_IsLocked,
	   isnull(hpfPatient.EPN,'') as Patient_Epn
FROM 
       ROI_Patients AS hpfPatient,
       ROI_Documents AS hpfDocuments,
       ROI_ENCOUNTERS AS hpfEncounter,
       ROI_Pages AS hpfPages,
       ROI_Versions AS hpfVersions,
       ROI_RequestCoreDeliverytoROI_Pages AS coreDeliveryToROIPages,
       ROI_RequestCoreDelivery AS coreDelivery,
       ROI_RequestCoreRequestor as requestor,
       ROI_RequestCore as requestCore
       --ROI_Reason as roiReason
WHERE     
		hpfPatient.MRN = @mrn
		AND hpfPatient.FACILITY = @facilityNames
		and hpfEncounter.ENCOUNTER is not null
		AND hpfPatient.ROI_Patients_Seq = hpfEncounter.ROI_Patients_Seq
		AND hpfEncounter.ROI_Encounters_Seq = hpfDocuments.ROI_Encounters_Seq
		AND hpfVersions.ROI_Documents_Seq = hpfDocuments.ROI_Documents_Seq
		AND hpfPages.ROI_Versions_Seq = hpfVersions.ROI_Versions_Seq
		AND coreDeliveryToROIPages.ROI_Pages_Seq = hpfPages.ROI_Pages_Seq
		AND coreDeliveryToROIPages.ROI_RequestCoreDelivery_Seq = coreDelivery.ROI_RequestCoreDelivery_Seq
		AND coreDelivery.ROI_RequestCore_Seq = requestCore.ROI_RequestCore_Seq
		AND requestCore.ROI_RequestCore_Seq = requestor.ROI_RequestCore_Seq
		--AND roiReason.ROI_Reason_Seq = requestCore.ROI_Reason_Seq
		AND requestCore.RequestReasonAttribute like @reqType
		AND coreDelivery.Modified_Dt between @fromDt and @toDt


UNION

--For Hpf Patient's Non-Hpf Document
SELECT
       hpfPatient.NAME as Patient_Name,
       hpfPatient.DOB as Patient_DOB,
       hpfPatient.SEX as gender,
       hpfPatient.FACILITY as patient_fac,
       hpfPatient.MRN as Patient_Mrn,
       cast(requestor.ROI_Requestor_Seq as varchar(50)) as requestor_id,
	   case  
          when requestor.NameLast IS NULL then '' 
          else requestor.NameLast
       end 
       +  case  
		  when requestor.NameFirst = '' then ''
          when requestor.NameFirst IS NULL then '' 
          else ', ' + requestor.NameFirst 
       end 
       +  case  
          when requestor.NameMiddle IS NULL then '' 
          else ' ' + requestor.NameMiddle 
       end as requestor_name, 
       coreDelivery.Modified_Dt as DisclosureDate,
       supplementalDocsCore.DATEOFSERVICE as Discharge_Dt ,
       cast(NULL as varchar(256)) as Patient_Type,
	   --roiReason.DisplayText as request_reason,
       requestCore.ROI_Reason as request_reason,
       requestCore.RequestReasonAttribute as request_type,
       supplementalDocsCore.ENCOUNTER  as Encounter_No,
	   0 as SelfPay,
       'Non-HPF' as Doc_Type,
       supplementalDocsCore.DOCNAME as Doc_Name,
       cast(supplementalDocsCore.PAGECOUNT as varchar(30)) as Pg_Number,
       cast(requestCore.ROI_RequestCore_Seq as varchar(50)) as  request_id,
       supplementalDocsCore.SUBTITLE as Subtitle,
       case
			when hpfPatient.VIP=0 then 'false' else 'true' 
	   end as Patient_IsVip,
	   case
			when hpfPatient.lockout=0 then 'false' else 'true'
	   end as Patient_IsLocked,
	   isnull(hpfPatient.EPN,'') as Patient_Epn
FROM 
       ROI_Patients AS hpfPatient,
       ROI_SupplementarityDocumentsCore AS supplementalDocsCore,
       ROI_RequestCoreDeliverytoROI_SupplementarityDocumentsCore AS coreDeliveryToSupplementalDocs,
       ROI_RequestCoreDelivery AS coreDelivery,
       ROI_RequestCoreRequestor as requestor,
       ROI_RequestCore as requestCore
       --ROI_Reason as roiReason
WHERE     
		hpfPatient.MRN = @mrn
		AND hpfPatient.FACILITY = @facilityNames
		AND hpfPatient.ROI_Patients_Seq = supplementalDocsCore.ROI_Patients_Seq
		AND supplementalDocsCore.ROI_SupplementarityDocumentsCore_Seq = coreDeliveryToSupplementalDocs.ROI_SupplementarityDocumentsCore_Seq
		AND coreDeliveryToSupplementalDocs.ROI_RequestCoreDelivery_Seq = coreDelivery.ROI_RequestCoreDelivery_Seq
		AND coreDelivery.ROI_RequestCore_Seq = requestCore.ROI_RequestCore_Seq
		AND requestCore.ROI_RequestCore_Seq = requestor.ROI_RequestCore_Seq
		--AND roiReason.ROI_Reason_Seq = requestCore.ROI_Reason_Seq
		AND requestCore.RequestReasonAttribute like @reqType
		AND coreDelivery.Modified_Dt between @fromDt and @toDt
		
UNION

--For Hpf Patient's  Global Hpf Document
SELECT

       hpfPatient.NAME as Patient_Name,
       hpfPatient.DOB as Patient_DOB,
       hpfPatient.SEX as gender,
       hpfPatient.FACILITY as patient_fac,
       hpfPatient.MRN as Patient_Mrn,
       cast(requestor.ROI_Requestor_Seq as varchar(50)) as requestor_id,
	   case  
          when requestor.NameLast IS NULL then '' 
          else requestor.NameLast
       end 
       +  case  
		  when requestor.NameFirst = '' then ''
          when requestor.NameFirst IS NULL then '' 
          else ', ' + requestor.NameFirst 
       end 
       +  case  
          when requestor.NameMiddle IS NULL then '' 
          else ' ' + requestor.NameMiddle 
       end as requestor_name, 
       coreDelivery.Modified_Dt as DisclosureDate,
       NULL as Discharge_Dt ,
       cast(NULL as varchar(256)) as Patient_Type,
	   --roiReason.DisplayText as request_reason,
       requestCore.ROI_Reason as request_reason,
       requestCore.RequestReasonAttribute as request_type,
       null as Encounter_No,
	   0 as SelfPay,
       'Global' as Doc_Type,
       roiDocuments.DOC_NAME as Doc_Name,
       --cast(roiPages.page as varchar(8000)) as Pg_Number,
	   cast((SELECT COUNT(page.ROI_Pages_Seq) 
			 FROM ROI_Pages page, ROI_Versions version
			 WHERE page.ROI_Versions_Seq = version.ROI_Versions_Seq 
				AND version.ROI_Documents_Seq = roiDocuments.ROI_Documents_Seq) as varchar(50)) as Pg_Number,
				
       cast(requestCore.ROI_RequestCore_Seq as varchar(50)) as  request_id,
       roiDocuments.SUBTITLE  as Subtitle,
       case
			when hpfPatient.VIP=0 then 'false' else 'true' 
	   end as Patient_IsVip,
	   case
			when hpfPatient.lockout=0 then 'false' else 'true'
	   end as Patient_IsLocked,
	   isnull(hpfPatient.EPN,'') as Patient_Epn
FROM 
       ROI_Patients AS hpfPatient,
       ROI_Documents AS roiDocuments,
       ROI_Pages AS roiPages,
       ROI_Versions AS roiVersions,
       ROI_RequestCoreDeliverytoROI_Pages AS coreDeliveryToROIPages,
       ROI_RequestCoreDelivery AS coreDelivery,
       ROI_RequestCoreRequestor as requestor,
       ROI_RequestCore as requestCore
       --ROI_Reason as roiReason
WHERE     
		hpfPatient.MRN = @mrn
		AND hpfPatient.FACILITY = @facilityNames
		AND hpfPatient.ROI_Patients_Seq = roiDocuments.ROI_Patients_Seq
		AND roiDocuments.ROI_Encounters_Seq is null
		AND roiVersions.ROI_Documents_Seq = roiDocuments.ROI_Documents_Seq
		AND roiPages.ROI_Versions_Seq = roiVersions.ROI_Versions_Seq
		AND coreDeliveryToROIPages.ROI_Pages_Seq = roiPages.ROI_Pages_Seq
		AND coreDeliveryToROIPages.ROI_RequestCoreDelivery_Seq = coreDelivery.ROI_RequestCoreDelivery_Seq
		AND coreDelivery.ROI_RequestCore_Seq = requestCore.ROI_RequestCore_Seq
		AND requestCore.ROI_RequestCore_Seq = requestor.ROI_RequestCore_Seq
		--AND roiReason.ROI_Reason_Seq = requestCore.ROI_Reason_Seq
		AND requestCore.RequestReasonAttribute like @reqType
		AND coreDelivery.Modified_Dt between @fromDt and @toDt 
UNION
--For Hpf Patient's Non-Hpf Attachment
SELECT

       hpfPatient.NAME as Patient_Name,
       hpfPatient.DOB as Patient_DOB,
       hpfPatient.SEX as gender,
       hpfPatient.FACILITY as patient_fac,
       hpfPatient.MRN as Patient_Mrn,
       cast(requestor.ROI_Requestor_Seq as varchar(50)) as requestor_id,
	   case  
          when requestor.NameLast IS NULL then '' 
          else requestor.NameLast
       end 
       +  case  
		  when requestor.NameFirst = '' then ''
          when requestor.NameFirst IS NULL then '' 
          else ', ' + requestor.NameFirst 
       end 
       +  case  
          when requestor.NameMiddle IS NULL then '' 
          else ' ' + requestor.NameMiddle 
       end as requestor_name, 
       coreDelivery.Modified_Dt as DisclosureDate,
       supplementalAttachmentsCore.DATEOFSERVICE as Discharge_Dt ,
       cast(NULL as varchar(256)) as Patient_Type,
	   requestCore.ROI_Reason as request_reason,
       --roiReason.DisplayText as request_reason,
       requestCore.RequestReasonAttribute as request_type,
       supplementalAttachmentsCore.ENCOUNTER  as Encounter_No,
	   0 as SelfPay,
       'Attachment' as Doc_Type,
       supplementalAttachmentsCore.FILENAME + '.' + supplementalAttachmentsCore.FILEEXT as Doc_Name,
       cast(supplementalAttachmentsCore.PAGECOUNT as varchar(30)) as Pg_Number,
       cast(requestCore.ROI_RequestCore_Seq as varchar(50)) as  request_id,
       supplementalAttachmentsCore.SUBTITLE as Subtitle,
       case
			when hpfPatient.VIP=0 then 'false' else 'true' 
	   end as Patient_IsVip,
	   case
			when hpfPatient.lockout=0 then 'false' else 'true'
	   end as Patient_IsLocked,
	   isnull(hpfPatient.EPN,'') as Patient_Epn
FROM 
       ROI_Patients AS hpfPatient,
       ROI_SupplementarityAttachmentsCore AS supplementalAttachmentsCore,
       ROI_RequestCoreDeliverytoROI_SupplementarityAttachmentsCore AS coreDeliveryToSupplementalAttachments,
       ROI_RequestCoreDelivery AS coreDelivery,
       ROI_RequestCoreRequestor as requestor,
       ROI_RequestCore as requestCore
       --ROI_Reason as roiReason
WHERE     
		hpfPatient.MRN = @mrn
		AND hpfPatient.FACILITY = @facilityNames
		AND hpfPatient.ROI_Patients_Seq = supplementalAttachmentsCore.ROI_Patients_Seq
		AND supplementalAttachmentsCore.ROI_SupplementarityAttachmentsCore_Seq = coreDeliveryToSupplementalAttachments.ROI_SupplementarityAttachmentsCore_Seq
		AND coreDeliveryToSupplementalAttachments.ROI_RequestCoreDelivery_Seq = coreDelivery.ROI_RequestCoreDelivery_Seq
		AND coreDelivery.ROI_RequestCore_Seq = requestCore.ROI_RequestCore_Seq
		and requestCore.ROI_RequestCore_Seq = requestor.ROI_RequestCore_Seq
		--AND roiReason.ROI_Reason_Seq = requestCore.ROI_Reason_Seq
		AND requestCore.RequestReasonAttribute like @reqType
		AND coreDelivery.Modified_Dt between @fromDt and @toDt
) as hpf

LEFT JOIN
	(SELECT 
			requestorToAddress.ROI_Requestor_Seq requestorId,
           (CASE WHEN requestorAddress.AddressLine1 IS NOT NULL AND requestorAddress.AddressLine1 <> '' THEN (requestorAddress.AddressLine1) ELSE '' END) addressLine1,
		   (CASE WHEN requestorAddress.AddressLine2 IS NOT NULL AND requestorAddress.AddressLine2 <> '' THEN (requestorAddress.AddressLine2) ELSE '' END) addressLine2, 
		   (CASE WHEN requestorAddress.AddressLine3 IS NOT NULL AND requestorAddress.AddressLine3 <> '' THEN (requestorAddress.AddressLine3) ELSE '' END) addressLine3,
		   ((CASE WHEN requestorAddress.City IS NOT NULL AND requestorAddress.City <> '' THEN (requestorAddress.City + ', ') ELSE '' END) + 
		   (CASE WHEN requestorAddress.State IS NOT NULL AND requestorAddress.State <> '' THEN (requestorAddress.State + ' ') ELSE '' END) + 
		   (CASE WHEN requestorAddress.PostalCode IS NOT NULL AND requestorAddress.PostalCode <> '' THEN (requestorAddress.PostalCode + ', ') ELSE '' END) +
		   COALESCE(((SELECT [country].[Country_Name] FROM [dbo].[ROI_CountryLov] country WHERE country.ROI_CountryLovID = requestorAddress.ROI_CountryLovID) + ', '), ' ')
		   ) city
		FROM  
			[dbo].[ROI_RequestorToAddress] requestorToAddress,
			[dbo].[ROI_Address] requestorAddress
		
		WHERE [requestorToAddress].[ROI_AddressType_Seq] = (SELECT [ROI_AddressType_Seq] FROM [dbo].[ROI_AddressType] WHERE [Name] = 'Main')
			AND [requestorAddress].[ROI_Address_Seq] = [requestorToAddress].[ROI_Address_Seq]) as temp
	ON hpf.requestor_id = temp.requestorId
WHERE
hpf.Doc_Name is not null
ORDER BY hpf.DisclosureDate DESC, hpf.requestor_name, hpf.request_type
END
GO

grant exec on [dbo].[ROI_Generate_AODReport] to [imnet]
go