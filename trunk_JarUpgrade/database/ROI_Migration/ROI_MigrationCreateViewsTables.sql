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
* Procedure             : ROI_MigrateCreateViewsTables
* Creation Date         : 03/30/2009
* Copyright				: COPYRIGHT MCKESSON 2009
*						: ALL RIGHTS RESERVED
*
*                       : The copyright to the computer program(s) herein
*                       : is the property of McKesson. The program(s)
*                       : may be used and/or copied only with the written
*                       : permission of McKesson or in accordance with
*                       : the terms and conditions stipulated in the     
*                       : agreement/contract under which the program(s)
*                       : have been supplied.
*					
* Written by            : RC	
*									
* Purpose               : Prepare data from classic ROI for migration
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
* Usage                 : ROI_MigrateSingleServerCreateViewsTables = run migration
*																
* Called By             : manual execution
*									
* Tables used           : 
*			
* Data Modifications	: none
*									
* Updates: 	
* Name      	Date        Purpose
* ---------	----------- ----------------
* MD Meersma	10/21/2010	  ROI Migration Indexes for the AA_ intermediate tables
*
***************************************************************************************************************************************/
SET NOCOUNT ON
Use CABINET;
Select 'Starting ROI_MigrationCreateViewsTables at '+ Cast(getdate() as varchar(20));

/* These are the views need by the ROI migration program */
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicInvoice_View]'))
DROP VIEW [dbo].[ROI_ClassicInvoice_View]

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicInvoice_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[ROI_ClassicInvoice_View]
AS
SELECT     dbo.ROI_Invoice.Request_Id AS RoiInvoice_RequestId, dbo.ROI_Invoice.Invoice_Id AS RoiInvoice_InvoiceId, 
                      dbo.ROI_Invoice.InvoiceNumber AS RoiInvoice_InvoiceNumber, dbo.ROI_Invoice.Facility AS RoiInvoice_Facility, 
                      dbo.ROI_Invoice.InvoiceDate AS RoiInvoice_InvoiceDate, dbo.ROI_Invoice.Base AS RoiInvoice_Base, dbo.ROI_Invoice.HardCopy AS RoiInvoice_HardCopy, 
                      dbo.ROI_Invoice.Microfilm AS RoiInvoice_Microfilm, dbo.ROI_Invoice.Postage AS RoiInvoice_Postage, dbo.ROI_Invoice.TaxAmount AS RoiInvoice_TaxAmount, 
                      dbo.ROI_Invoice.AdjustAmount AS RoiInvoice_AdjustAmount, dbo.ROI_Invoice.AdjustTotalCharges AS RoiInvoice_AdjustTotalCharges, 
                      dbo.ROI_Invoice.AdjustReason AS RoiInvoice_AdjustReason, dbo.ROI_Invoice.OutputType AS RoiInvoice_OutputType, 
                      dbo.ROI_Invoice.NumberOfCopies AS RoiInvoice_NumberOfCopies, dbo.ROI_Invoice.HardCopyPages AS RoiInvoice_HardCopyPages, 
                      dbo.ROI_Invoice.MicroFilmPages AS RoiInvoice_MicroFilmPages, dbo.ROI_Invoice.Add_Adjustment_Amt AS RoiInvoice_AddAdjustmentAmt, 
                      dbo.ROI_Invoice_Payment.Payment_Id AS RoiInvPayment_PaymentId, dbo.ROI_Invoice_Payment.Invoice_Id AS RoiInvPayment_InvoiceId, 
                      dbo.ROI_Invoice_Payment.PaymentDate AS RoiInvPayment_PaymentDate, dbo.ROI_Invoice_Payment.CheckNumber AS RoiInvPayment_CheckNumber, 
                      dbo.ROI_Invoice_Payment.PaymentAmount AS RoiInvPayment_PaymentAmount, dbo.ROI_Invoice_Payment.PostedBy AS RoiInvPayment_PostedBy
FROM         cabinet.dbo.ROI_Invoice LEFT OUTER JOIN
                      cabinet.dbo.ROI_Invoice_Payment ON dbo.ROI_Invoice.Invoice_Id = dbo.ROI_Invoice_Payment.Invoice_Id
' 

IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicRequestHeader_View]'))
DROP VIEW [dbo].[ROI_ClassicRequestHeader_View]

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicRequestHeader_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[ROI_ClassicRequestHeader_View]
AS
SELECT     TOP (100) PERCENT dbo.ROI_Request.Request_Id AS Request_RequestId, dbo.ROI_Request.Patient_Name AS Request_PatName, 
                      dbo.ROI_Request.Facility AS Request_Fac, dbo.ROI_Request.MRN AS Request_MRN, dbo.ROI_Request.Requester_Id AS Request_RequesterId, 
                      dbo.ROI_Request.RequesterContact AS Request_RequesterContact, dbo.ROI_Request.LogDate AS Request_LogDate, dbo.ROI_Request.Status AS Request_Status, 
                      dbo.ROI_Request.StatusDesc_Id AS Request_StatusDesc_Id, dbo.ROI_Request.StatusDate AS Request_StatusDate, 
                      dbo.ROI_Request.ReceiptDate AS Request_ReceiptDate, dbo.ROI_Request.UpdatedBy AS Request_UpdatedBy, dbo.ROI_Request.Priority AS Request_Priority, 
                      dbo.ROI_Request.Comments AS Request_Comments, dbo.ROI_Request.Autype_Code AS Request_AutypeCode, dbo.ROI_Request.TotalPages AS Request_TotalPages,
                       dbo.ROI_Autype_File.AutypeCode AS Autype_Code, dbo.ROI_Autype_File.AutypeName AS Autype_Name, dbo.ROI_Autype_File.AutypeActive AS Autype_Active, 
                      dbo.ROI_Requester.Requester_Id AS Requester_RequesterId, dbo.ROI_Requester.RequesterName AS Requester_RequesterName, 
                      dbo.ROI_Requester.Address1 AS Requester_Address1, dbo.ROI_Requester.Address2 AS Requester_Address2, dbo.ROI_Requester.Address3 AS Requester_Address3, 
                      dbo.ROI_Requester.City AS Requester_City, dbo.ROI_Requester.State AS Requester_State, dbo.ROI_Requester.Zip AS Requester_Zip, 
                      dbo.ROI_Requester.Phone AS Requester_Phone, dbo.ROI_Requester.Fax AS Requester_Fax, dbo.ROI_Requester.BillingType_Id AS Requester_BillingTypeId, 
                      dbo.ROI_Requester.RequesterType_Id AS Requester_RequesterTypeId, dbo.ROI_Requester.RecordView AS Requester_RecordView, 
                      dbo.ROI_Requester.Set_Id AS Requester_SetId, dbo.ROI_Requester.DefaultFlag AS Requester_DefaultFlag, 
                      dbo.ROI_Requester.UpdatedDate AS Requester_UpdatedDate, dbo.ROI_Requester.UpdateBy AS Requester_UpdateBy, 
                      dbo.ROI_Requester.RequesterActive AS Requester_RequesterActive, dbo.ROI_Requester_Type.RequesterType_Id AS RequesterType_RequesterTypeId, 
                      dbo.ROI_Requester_Type.RequesterType_Name AS RequesterType_RequesterTypeName, 
                      dbo.ROI_Requester_Type.RequesterType_Active AS RequesterType_RequesterTypeActive, dbo.ROI_Billing_Type.BillingType_Id AS ROIBillingType_BillingTypeId, 
                      dbo.ROI_Billing_Type.BillingType_Name AS ROIBillingType_BillingTypeName, dbo.ROI_Billing_Type.BillingType_Type AS ROIBillingType_BillingTypeType, 
                      dbo.ROI_Billing_Type.FlatFee AS ROIBillingType_FlatFee, dbo.ROI_Billing_Type.PerPage AS ROIBillingType_PerPage, 
                      dbo.ROI_Billing_Type.Minimum AS ROIBillingType_Minimum, dbo.ROI_Billing_Type.Research AS ROIBillingType_Research, 
                      dbo.ROI_Billing_Type.Preparation AS ROIBillingType_Preparation, dbo.ROI_Billing_Type.Certification AS ROIBillingType_Certification, 
                      dbo.ROI_Billing_Type.HardCopiesPP AS ROIBillingType_HardCopiesPP, dbo.ROI_Billing_Type.Postage AS ROIBillingType_Postage, 
                      dbo.ROI_Billing_Type.TaxRate AS ROIBillingType_TaxType, dbo.ROI_Billing_Type.BillType_Active AS ROIBillingType_BillTypeActive, 
                      dbo.ROI_Billing_Type.Set_Id AS ROIBillingType_SetId, dbo.ROI_Billing_Type.RemainderFee AS ROIBillingType_RemainderFee, 
                      dbo.ROI_Disposition_Reason.StatusDesc_Id AS Disposition_StatusDescId, dbo.ROI_Disposition_Reason.Desc_Name AS Disposition_DescName, 
                      dbo.ROI_Disposition_Reason.Desc_Active AS Disposition_DescActive, dbo.vw_patients.MRN AS vwMRN_mrn, dbo.vw_patients.OLD_MRN AS vwMRN_OldMRN, 
                      dbo.vw_patients.NAME AS vwMRN_Name, dbo.vw_patients.SSN AS vwMRN_SSN, dbo.vw_patients.ADDRESS AS vwMRN_Address, 
                      dbo.vw_patients.CITY AS vwMRN_City, dbo.vw_patients.STATE AS vwMRN_State, dbo.vw_patients.ZIP AS vwMRN_ZIP, 
                      dbo.vw_patients.HM_PHONE AS vwMRN_HmPhone, dbo.vw_patients.WK_PHONE AS vwMRN_WkPhone, dbo.vw_patients.SEX AS vwMRN_Sex, 
                      dbo.vw_patients.DOB AS vwMRN_DOB, dbo.vw_patients.AGE AS vwMRN_Age, dbo.vw_patients.RACE AS vwMRN_Race, dbo.vw_patients.MARITAL AS vwMRN_Marital,
                       dbo.vw_patients.EMPLOYER AS vwMRN_Employer, dbo.vw_patients.SMOKER AS vwMRN_Smoker, dbo.vw_patients.RELIGION AS vwMRN_Religion, 
                      dbo.vw_patients.CHURCH AS vwMRN_Church, dbo.vw_patients.EMERGENCY_CONTACT AS vwMRN_EmergContact, 
                      dbo.vw_patients.BIRTH_PLACE AS vwMRN_BirthPlace, dbo.vw_patients.LOCKOUT AS vwMRN_Lockout, dbo.vw_patients.REMARK AS vwMRN_Remark, 
                      dbo.vw_patients.FACILITY AS vwMRN_Facility, dbo.vw_patients.LNSSN AS vwMRN_LNSSN, dbo.vw_patients.GPI AS vwMRN_GPI, 
                      dbo.vw_patients.ENTERPRISE_PI AS vwMRN_EnterprisePI, dbo.vw_patients.ENTERPRISE_AI AS vwMRN_EnterpriseAI, 
                      dbo.vw_patients.ADDRESS2 AS vwMRN_Address2, dbo.vw_patients.ADDRESS3 AS vwMRN_Address3, dbo.vw_patients.DOMAIN_ID AS vwMRN_DomainId, 
                      dbo.vw_patients.EPN AS vwMRN_EPN, dbo.vw_patients.LOCKOUT AS vwMRN_VIP
FROM         [Cabinet].dbo.vw_patients INNER JOIN
                      [Cabinet].dbo.ROI_Autype_File INNER JOIN
                      [Cabinet].dbo.ROI_Request INNER JOIN
                      [Cabinet].dbo.ROI_Disposition_Reason ON dbo.ROI_Request.StatusDesc_Id = dbo.ROI_Disposition_Reason.StatusDesc_Id ON 
                      [Cabinet].dbo.ROI_Autype_File.AutypeCode = dbo.ROI_Request.Autype_Code ON dbo.vw_patients.MRN = dbo.ROI_Request.MRN AND 
                      [Cabinet].dbo.vw_patients.FACILITY = dbo.ROI_Request.Facility LEFT OUTER JOIN
                      [Cabinet].dbo.ROI_Billing_Type INNER JOIN
                      [Cabinet].dbo.ROI_Requester INNER JOIN
                      [Cabinet].dbo.ROI_Requester_Type ON dbo.ROI_Requester.RequesterType_Id = dbo.ROI_Requester_Type.RequesterType_Id AND 
                      [Cabinet].dbo.ROI_Requester.RequesterType_Id = dbo.ROI_Requester_Type.RequesterType_Id ON 
                      [Cabinet].dbo.ROI_Billing_Type.BillingType_Id = dbo.ROI_Requester.BillingType_Id ON dbo.ROI_Request.Requester_Id = dbo.ROI_Requester.Requester_Id
ORDER BY Request_RequestId
' 

IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicRequestDetail_View]'))
DROP VIEW [dbo].[ROI_ClassicRequestDetail_View]

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicRequestDetail_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[ROI_ClassicRequestDetail_View]
AS
SELECT     TOP (100) PERCENT dbo.ROI_Request_Page.Request_Id AS RequestPage_RequestId, dbo.ROI_Request_Page.Imnet AS RequestPage_Imnet, 
                      dbo.ROI_Request_Page.Page AS RequestPage_Page, dbo.Pages.imnet AS Pages_Imnet, dbo.Pages.version_id AS Pages_VersionId, 
                      dbo.Pages.imagedate AS Pages_imagedate, dbo.Pages.page AS Pages_page, dbo.Pages.filename AS Pages_filename, dbo.Pages.archived AS Pages_archived, 
                      dbo.Pages.deleted AS Pages_deleted, dbo.Pages.ContentCount AS Pages_ContentCount, dbo.Versions.Version_ID AS Versions_VersionId, 
                      dbo.Versions.DOC_ID AS Versions_DOCID, dbo.Versions.VersionNumber AS Versions_VersionNumber, dbo.Versions.VersionDate AS Versions_VersionDate, 
                      dbo.Versions.DocumentStatus AS Versions_DocumentStatus, dbo.Documents.ENCOUNTER AS Documents_encounter, dbo.Documents.MRN AS Documents_MRN, 
                      dbo.Documents.FACILITY AS Documents_fac, dbo.Documents.DOC_NAME AS Documents_DocName, dbo.Documents.SUBTITLE AS Documents_Subtitle, 
                      dbo.Documents.DATETIME AS Documents_DateTime, dbo.Documents.DUID AS Documents_DUID, dbo.Documents.DOC_ID AS Documents_DocId, 
                      dbo.FACILITY_FILE.FACILITY_CODE AS FacFile_FacCode, dbo.FACILITY_FILE.FACILITY_NAME AS FacFile_FacName, 
                      dbo.FACILITY_FILE.FACILITY_ACTIVE AS FacFile_Active, dbo.FACILITY_FILE.FACILITY_ADDR1 AS FacFile_Addr1, 
                      dbo.FACILITY_FILE.FACILITY_ADDR2 AS FacFile_Addr2, dbo.FACILITY_FILE.FACILITY_ADDR3 AS FacFile_Addr3, dbo.FACILITY_FILE.FACILITY_CITY AS FacFile_City, 
                      dbo.FACILITY_FILE.FACILITY_STATE AS FacFile_State, dbo.FACILITY_FILE.FACILITY_ZIP AS FacFile_Zip, dbo.FACILITY_FILE.FACILITY_PHONE1 AS FacFile_Phone1, 
                      dbo.FACILITY_FILE.FACILITY_FAX1 AS FacFile_Fax1, dbo.FACILITY_FILE.PTTYPE_SET AS FacFile_PTTypeSet, 
                      dbo.FACILITY_FILE.SERVICE_SET AS FacFile_ServiceSet, dbo.FACILITY_FILE.PAYOR_SET AS FacFile_PayorSet, dbo.FACILITY_FILE.UNIT_SET AS FacFile_UnitSet, 
                      dbo.FACILITY_FILE.PHYSICIAN_SET AS FacFile_PhySet, dbo.FACILITY_FILE.DOCUMENT_SET AS FacFile_DocSet, 
                      dbo.FACILITY_FILE.ARCHIVE_QUEUE AS FacFile_ArcQueue, dbo.FACILITY_FILE.STUDY_SET AS FacFile_StudySet, 
                      dbo.FACILITY_FILE.AUDITPAGES AS FacFile_AuditPages, dbo.FACILITY_FILE.BILLING_SET AS FacFile_BillingSet, 
                      dbo.FACILITY_FILE.REQUESTER_SET AS FacFile_RequesterSet, dbo.DOCUMENT_NAMES.TAG AS DocNames_Tag, 
                      dbo.DOCUMENT_NAMES.TAGORDER AS DocNames_TagOrder, dbo.DOCUMENT_NAMES.ACTIVE AS DocNames_Active, 
                      dbo.DOCUMENT_NAMES.QUEUE AS DocNames_Queue, dbo.DOCUMENT_NAMES.REASON AS DocNames_Reason, 
                      dbo.DOCUMENT_NAMES.FORM AS DocNames_Form, dbo.DOCUMENT_NAMES.DIRECT_VIEW AS DocNames_DirectView, 
                      dbo.DOCUMENT_NAMES.GLOBAL AS DocNames_Global, dbo.DOCUMENT_NAMES.FU_SIGN AS DocNames_FuSign, 
                      dbo.DOCUMENT_NAMES.SIGN_X AS DocNames_SignX, dbo.DOCUMENT_NAMES.SIGN_Y AS DocNames_SignY, 
                      dbo.DOCUMENT_NAMES.SIGN_W AS DocNames_SignW, dbo.DOCUMENT_NAMES.SIGN_H AS DocNames_SignH, 
                      dbo.DOCUMENT_NAMES.ANNOTATE AS DocNames_Annotate, dbo.DOCUMENT_NAMES.DOCTYPE_ID AS DocNames_DocTypeId, 
                      dbo.DOCUMENT_NAMES.CODE AS DocNames_Code, dbo.DOCUMENT_NAMES.CALCTABLE_ID AS DocNames_CalId, 
                      dbo.DOCUMENT_NAMES.DOC_TYPE AS DocNames_DocType, dbo.DOCUMENT_NAMES.SET_ID AS DocNames_SetId, 
                      dbo.DOCUMENT_NAMES.SUSPEND_DELAY AS DocNames_SuspendDelay, dbo.DOCUMENT_NAMES.Text_Editing AS DocNames_TextEditing, 
                      dbo.DOCUMENT_NAMES.Restrict_Viewing AS DocNames_RestrictViewing, dbo.DOCUMENT_NAMES.Viewing_Expire AS DocNames_ViewingExpire, 
                      dbo.DOCUMENT_NAMES.Viewing_Period AS DocNames_ViewingPeriod, dbo.DOCUMENT_NAMES.DocHdrLength AS DocNames_DocHdrLength, 
                      dbo.DOCUMENT_NAMES.DocHdrSearchString AS DocNames_DocHdrSearchString, dbo.DOCUMENT_NAMES.DocFtrLength AS DocNames_DocFtrLength, 
                      dbo.DOCUMENT_NAMES.DocFtrPageNo AS DocNames_DocFtrPageNo, dbo.DOCUMENT_NAMES.DocRuleId AS DocNames_DocRuleId, 
                      dbo.DOCUMENT_NAMES.Outbound AS DocNames_Outbound, dbo.DOCUMENT_NAMES.SigQueue AS DocNames_SigQueue, 
                      dbo.DOCUMENT_NAMES.SigReason AS DocNames_SigReason, dbo.vw_encounters.ENCOUNTER AS vwEnc_enc, dbo.vw_encounters.MRN AS vwEnc_mrn, 
                      dbo.vw_encounters.NO_PUB AS vwEnc_NoPub, dbo.vw_encounters.ADMITTED AS vwEnc_Admitted, dbo.vw_encounters.DISCHARGED AS vwEnc_Discharged, 
                      dbo.vw_encounters.FACILITY AS vwEnc_fac, dbo.vw_encounters.CLINIC AS vwEnc_clinic, dbo.vw_encounters.PT_TYPE AS vwEnc_PtType, 
                      dbo.vw_encounters.SERVICE AS vwEnc_Service, dbo.vw_encounters.PAYOR_CODE AS vwEnc_PayorCode, dbo.vw_encounters.FIN_CLASS AS vwEnc_FinClass, 
                      dbo.vw_encounters.PRIM_PLAN AS vwEnc_PrimPlan, dbo.vw_encounters.SEC_PLAN AS vwEnc_SecPlan, dbo.vw_encounters.ADMIT_SOURCE AS vwEnc_AdmitSource,
                       dbo.vw_encounters.TOTAL_CHARGES AS vwEnc_TotalCharges, dbo.vw_encounters.BALANCE_CHARGES AS vwEnc_BalanceCharges, 
                      dbo.vw_encounters.LOCKOUT AS vwEnc_Lockout, dbo.vw_encounters.REMARK AS vwEnc_Remaark, dbo.vw_encounters.DISPOSITION AS vwEnc_Disposition, 
                      dbo.vw_encounters.ENC_OPT_OUT AS vwEnc_EncOptOut, dbo.vw_encounters.VIP AS vwEnc_VIP
FROM         [Cabinet].dbo.Versions RIGHT OUTER JOIN
                      [Cabinet].dbo.Pages ON dbo.Versions.Version_ID = dbo.Pages.version_id RIGHT OUTER JOIN
                      [Cabinet].dbo.ROI_Request_Page ON dbo.Pages.imnet = dbo.ROI_Request_Page.Imnet LEFT OUTER JOIN
                      [Cabinet].dbo.vw_encounters RIGHT OUTER JOIN
                      [Cabinet].dbo.FACILITY_FILE INNER JOIN
                      [Cabinet].dbo.DOCUMENT_NAMES INNER JOIN
                      [Cabinet].dbo.Documents ON dbo.DOCUMENT_NAMES.TAG = dbo.Documents.DOC_NAME ON dbo.FACILITY_FILE.DOCUMENT_SET = dbo.DOCUMENT_NAMES.SET_ID AND 
                      [Cabinet].dbo.FACILITY_FILE.FACILITY_CODE = dbo.Documents.FACILITY ON dbo.vw_encounters.FACILITY = dbo.Documents.FACILITY AND 
                      [Cabinet].dbo.vw_encounters.MRN = dbo.Documents.MRN AND dbo.vw_encounters.ENCOUNTER = dbo.Documents.ENCOUNTER ON 
                      [Cabinet].dbo.Versions.DOC_ID = dbo.Documents.DOC_ID
' 

IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicReleaseDetail_View]'))
DROP VIEW [dbo].[ROI_ClassicReleaseDetail_View]

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicReleaseDetail_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[ROI_ClassicReleaseDetail_View]
AS
SELECT     TOP (100) PERCENT dbo.ROI_Request_Release.Release_Id AS ReqRel_ReleaseId, dbo.ROI_Request_Release.Request_Id AS ReqRel_RequestId, 
                      dbo.ROI_Request_Release.ReleaseDate AS ReqRel_ReleaseDate, dbo.ROI_Request_Release.OutputType AS ReqRel_OutputType, 
                      dbo.ROI_Req_Release_Page.Release_Id AS ReqRelPage_ReleaseId, dbo.ROI_Req_Release_Page.Imnet AS ReqRelPage_Imnet, 
                      dbo.ROI_Req_Release_Page.Page AS ReqRelPage_Page, dbo.Pages.imnet AS Pages_Imnet, dbo.Pages.version_id AS Pages_VersionId, 
                      dbo.Pages.imagedate AS Pages_imagedate, dbo.Pages.page AS Pages_page, dbo.Pages.filename AS Pages_filename, dbo.Pages.archived AS Pages_archived, 
                      dbo.Pages.deleted AS Pages_deleted, dbo.Pages.ContentCount AS Pages_ContentCount, dbo.Versions.Version_ID AS Versions_VersionId, 
                      dbo.Versions.DOC_ID AS Versions_DOCID, dbo.Versions.VersionNumber AS Versions_VersionNumber, dbo.Versions.VersionDate AS Versions_VersionDate, 
                      dbo.Versions.DocumentStatus AS Versions_DocumentStatus, dbo.Documents.ENCOUNTER AS Documents_encounter, dbo.Documents.MRN AS Documents_MRN, 
                      dbo.Documents.FACILITY AS Documents_fac, dbo.Documents.DOC_NAME AS Documents_DocName, dbo.Documents.SUBTITLE AS Documents_Subtitle, 
                      dbo.Documents.DATETIME AS Documents_DateTime, dbo.Documents.DUID AS Documents_DUID, dbo.Documents.DOC_ID AS Documents_DocId, 
                      dbo.FACILITY_FILE.FACILITY_CODE AS FacFile_FacCode, dbo.FACILITY_FILE.FACILITY_NAME AS FacFile_FacName, 
                      dbo.FACILITY_FILE.FACILITY_ACTIVE AS FacFile_Active, dbo.FACILITY_FILE.FACILITY_ADDR1 AS FacFile_Addr1, 
                      dbo.FACILITY_FILE.FACILITY_ADDR2 AS FacFile_Addr2, dbo.FACILITY_FILE.FACILITY_ADDR3 AS FacFile_Addr3, dbo.FACILITY_FILE.FACILITY_CITY AS FacFile_City, 
                      dbo.FACILITY_FILE.FACILITY_STATE AS FacFile_State, dbo.FACILITY_FILE.FACILITY_ZIP AS FacFile_Zip, dbo.FACILITY_FILE.FACILITY_PHONE1 AS FacFile_Phone1, 
                      dbo.FACILITY_FILE.FACILITY_FAX1 AS FacFile_Fax1, dbo.FACILITY_FILE.PTTYPE_SET AS FacFile_PTTypeSet, 
                      dbo.FACILITY_FILE.SERVICE_SET AS FacFile_ServiceSet, dbo.FACILITY_FILE.PAYOR_SET AS FacFile_PayorSet, dbo.FACILITY_FILE.UNIT_SET AS FacFile_UnitSet, 
                      dbo.FACILITY_FILE.PHYSICIAN_SET AS FacFile_PhySet, dbo.FACILITY_FILE.DOCUMENT_SET AS FacFile_DocSet, 
                      dbo.FACILITY_FILE.ARCHIVE_QUEUE AS FacFile_ArcQueue, dbo.FACILITY_FILE.STUDY_SET AS FacFile_StudySet, 
                      dbo.FACILITY_FILE.AUDITPAGES AS FacFile_AuditPages, dbo.FACILITY_FILE.BILLING_SET AS FacFile_BillingSet, 
                      dbo.FACILITY_FILE.REQUESTER_SET AS FacFile_RequesterSet, dbo.DOCUMENT_NAMES.TAG AS DocNames_Tag, 
                      dbo.DOCUMENT_NAMES.TAGORDER AS DocNames_TagOrder, dbo.DOCUMENT_NAMES.ACTIVE AS DocNames_Active, 
                      dbo.DOCUMENT_NAMES.QUEUE AS DocNames_Queue, dbo.DOCUMENT_NAMES.REASON AS DocNames_Reason, 
                      dbo.DOCUMENT_NAMES.FORM AS DocNames_Form, dbo.DOCUMENT_NAMES.DIRECT_VIEW AS DocNames_DirectView, 
                      dbo.DOCUMENT_NAMES.GLOBAL AS DocNames_Global, dbo.DOCUMENT_NAMES.FU_SIGN AS DocNames_FuSign, 
                      dbo.DOCUMENT_NAMES.SIGN_X AS DocNames_SignX, dbo.DOCUMENT_NAMES.SIGN_Y AS DocNames_SignY, 
                      dbo.DOCUMENT_NAMES.SIGN_W AS DocNames_SignW, dbo.DOCUMENT_NAMES.SIGN_H AS DocNames_SignH, 
                      dbo.DOCUMENT_NAMES.ANNOTATE AS DocNames_Annotate, dbo.DOCUMENT_NAMES.DOCTYPE_ID AS DocNames_DocTypeId, 
                      dbo.DOCUMENT_NAMES.CODE AS DocNames_Code, dbo.DOCUMENT_NAMES.CALCTABLE_ID AS DocNames_CalId, 
                      dbo.DOCUMENT_NAMES.DOC_TYPE AS DocNames_DocType, dbo.DOCUMENT_NAMES.SET_ID AS DocNames_SetId, 
                      dbo.DOCUMENT_NAMES.SUSPEND_DELAY AS DocNames_SuspendDelay, dbo.DOCUMENT_NAMES.Text_Editing AS DocNames_TextEditing, 
                      dbo.DOCUMENT_NAMES.Restrict_Viewing AS DocNames_RestrictViewing, dbo.DOCUMENT_NAMES.Viewing_Expire AS DocNames_ViewingExpire, 
                      dbo.DOCUMENT_NAMES.Viewing_Period AS DocNames_ViewingPeriod, dbo.DOCUMENT_NAMES.DocHdrLength AS DocNames_DocHdrLength, 
                      dbo.DOCUMENT_NAMES.DocHdrSearchString AS DocNames_DocHdrSearchString, dbo.DOCUMENT_NAMES.DocFtrLength AS DocNames_DocFtrLength, 
                      dbo.DOCUMENT_NAMES.DocFtrPageNo AS DocNames_DocFtrPageNo, dbo.DOCUMENT_NAMES.DocRuleId AS DocNames_DocRuleId, 
                      dbo.DOCUMENT_NAMES.Outbound AS DocNames_Outbound, dbo.DOCUMENT_NAMES.SigQueue AS DocNames_SigQueue, 
                      dbo.DOCUMENT_NAMES.SigReason AS DocNames_SigReason, dbo.vw_encounters.ENCOUNTER AS vwEnc_enc, dbo.vw_encounters.MRN AS vwEnc_mrn, 
                      dbo.vw_encounters.NO_PUB AS vwEnc_NoPub, dbo.vw_encounters.ADMITTED AS vwEnc_Admitted, dbo.vw_encounters.DISCHARGED AS vwEnc_Discharged, 
                      dbo.vw_encounters.FACILITY AS vwEnc_fac, dbo.vw_encounters.CLINIC AS vwEnc_clinic, dbo.vw_encounters.PT_TYPE AS vwEnc_PtType, 
                      dbo.vw_encounters.SERVICE AS vwEnc_Service, dbo.vw_encounters.PAYOR_CODE AS vwEnc_PayorCode, dbo.vw_encounters.FIN_CLASS AS vwEnc_FinClass, 
                      dbo.vw_encounters.PRIM_PLAN AS vwEnc_PrimPlan, dbo.vw_encounters.SEC_PLAN AS vwEnc_SecPlan, dbo.vw_encounters.ADMIT_SOURCE AS vwEnc_AdmitSource,
                       dbo.vw_encounters.TOTAL_CHARGES AS vwEnc_TotalCharges, dbo.vw_encounters.BALANCE_CHARGES AS vwEnc_BalanceCharges, 
                      dbo.vw_encounters.LOCKOUT AS vwEnc_Lockout, dbo.vw_encounters.REMARK AS vwEnc_Remaark, dbo.vw_encounters.DISPOSITION AS vwEnc_Disposition, 
                      dbo.vw_encounters.ENC_OPT_OUT AS vwEnc_EncOptOut, dbo.vw_encounters.VIP AS vwEnc_VIP
FROM         [Cabinet].dbo.ROI_Req_Release_Page INNER JOIN
                      [Cabinet].dbo.ROI_Request_Release ON dbo.ROI_Req_Release_Page.Release_Id = dbo.ROI_Request_Release.Release_Id LEFT OUTER JOIN
                      [Cabinet].dbo.Pages ON dbo.ROI_Req_Release_Page.Imnet = dbo.Pages.imnet LEFT OUTER JOIN
                      [Cabinet].dbo.Versions ON dbo.Pages.version_id = dbo.Versions.Version_ID LEFT OUTER JOIN
                      [Cabinet].dbo.vw_encounters RIGHT OUTER JOIN
                      [Cabinet].dbo.FACILITY_FILE INNER JOIN
                      [Cabinet].dbo.DOCUMENT_NAMES INNER JOIN
                      [Cabinet].dbo.Documents ON dbo.DOCUMENT_NAMES.TAG = dbo.Documents.DOC_NAME ON dbo.FACILITY_FILE.DOCUMENT_SET = dbo.DOCUMENT_NAMES.SET_ID AND 
                      [Cabinet].dbo.FACILITY_FILE.FACILITY_CODE = dbo.Documents.FACILITY ON dbo.vw_encounters.FACILITY = dbo.Documents.FACILITY AND 
                      [Cabinet].dbo.vw_encounters.MRN = dbo.Documents.MRN AND dbo.vw_encounters.ENCOUNTER = dbo.Documents.ENCOUNTER ON 
                      [Cabinet].dbo.Versions.DOC_ID = dbo.Documents.DOC_ID
' 
;
/* this script will create tables from views.  The tables are used to produce the XML for the new ROI*/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[Cabinet].[dbo].[AA_ROI_ClassicInvoice]') AND type in (N'U'))
DROP TABLE [Cabinet].[dbo].[AA_ROI_ClassicInvoice]

select * into [Cabinet].dbo.AA_ROI_ClassicInvoice from [Cabinet].dbo.ROI_ClassicInvoice_View
select Count(*) AS 'Classic Invoices found' from [Cabinet].dbo.AA_ROI_ClassicInvoice
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[Cabinet].[dbo].[AA_ROI_CLassicRequestHeader]') AND type in (N'U'))
DROP TABLE [Cabinet].[dbo].[AA_ROI_CLassicRequestHeader]


select * into [Cabinet].dbo.AA_ROI_CLassicRequestHeader from [Cabinet].dbo.ROI_CLassicRequestHeader_View
select Count(*) AS 'Classic ROI Request found'  from [Cabinet].dbo.AA_ROI_CLassicRequestHeader
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[Cabinet].[dbo].[AA_ROI_CLassicRequestDetail]') AND type in (N'U'))
DROP TABLE [Cabinet].[dbo].[AA_ROI_CLassicRequestDetail]

select * into [Cabinet].dbo.AA_ROI_CLassicRequestDetail from [Cabinet].dbo.ROI_CLassicRequestDetail_View
select Count(*) AS 'Classic ROI Request images found'  from [Cabinet].dbo.AA_ROI_CLassicRequestDetail
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[Cabinet].[dbo].[AA_ROI_ClassicReleaseDetail]') AND type in (N'U'))
DROP TABLE [Cabinet].[dbo].[AA_ROI_ClassicReleaseDetail]

select * into [Cabinet].dbo.AA_ROI_ClassicReleaseDetail from [Cabinet].dbo.ROI_ClassicReleaseDetail_View;
select Count(*) AS 'Classic ROI Request released images found' from [Cabinet].dbo.AA_ROI_ClassicReleaseDetail;
GO

-- AA_ROI_CLassicRequestHeader

Print 'Creating AA_ROI_CLassicRequestHeader Indexes'
go

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_CLassicRequestHeader') and name = 'IX_AA_ROI_CRH_Request_RequestId')
create clustered index IX_AA_ROI_CRH_Request_RequestId on AA_ROI_CLassicRequestHeader (Request_RequestId)
go

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_CLassicRequestHeader') and name = 'IX_AA_ROI_CRH_MRN_FAC')
create index IX_AA_ROI_CRH_MRN_FAC on AA_ROI_CLassicRequestHeader (vwMRN_MRN, vwMRN_Facility)
go

--Now place the VIP data into the Header table
With l_VIPCalc (l_vip, l_mrn, l_facility)
AS
(Select max (cast(ISNULL(VIP,'0') as int)), mrn, facility
from  HIS.dbo.Encounters inner join [Cabinet].dbo.AA_ROI_CLassicRequestHeader
on vwmrn_mrn = mrn and vwmrn_facility = facility
group by mrn, facility
)
update [Cabinet].dbo.AA_ROI_CLassicRequestHeader set vwMRN_VIP =l_vip
FROM [Cabinet].dbo.AA_ROI_CLassicRequestHeader
JOIN l_VIPCalc on vwMRN_MRN = l_mrn AND vwMRN_Facility = l_facility
--Select vwmrn_mrn, vwmrn_vip from AA_ROI_CLassicRequestHeader
go

-- ROI Migration Indexes for the AA_ intermediate tables


-- AA_ROI_ClassicReleaseDetail

Print 'Creating AA_ROI_ClassicReleaseDetail Indexes'
go

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_ClassicReleaseDetail') and name = 'IX_AA_ROI_CRELD_ReqRel_RequestId')
create clustered index IX_AA_ROI_CRELD_ReqRel_RequestId on AA_ROI_ClassicReleaseDetail (ReqRel_RequestId)

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_ClassicReleaseDetail') and name = 'IX_AA_ROI_CRELD_Versions_DOCID')
create index IX_AA_ROI_CRELD_Versions_DOCID on AA_ROI_ClassicReleaseDetail (Versions_DOCID)

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_ClassicReleaseDetail') and name = 'IX_AA_ROI_CRELD_Documents_DocId')
create index IX_AA_ROI_CRELD_Documents_DocId on AA_ROI_ClassicReleaseDetail (Documents_DocId)

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_ClassicReleaseDetail') and name = 'IX_AA_ROI_CRELD_Pages_VersionID')
create index IX_AA_ROI_CRELD_Pages_VersionID on AA_ROI_ClassicReleaseDetail (Pages_VersionID)

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_ClassicReleaseDetail') and name = 'IX_AA_ROI_CRELD_Versions_VersionID')
create index IX_AA_ROI_CRELD_Versions_VersionID on AA_ROI_ClassicReleaseDetail (Versions_VersionID)

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_ClassicReleaseDetail') and name = 'IX_AA_ROI_CRELD_Pages_Imnet')
create index IX_AA_ROI_CRELD_Pages_Imnet on AA_ROI_ClassicReleaseDetail (Pages_Imnet)
go

-- AA_ROI_CLassicRequestDetail

Print 'Creating AA_ROI_CLassicRequestDetail Indexes'
go

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_CLassicRequestDetail') and name = 'IX_AA_ROI_CREQD_RequestPage_RequestId')
create clustered index IX_AA_ROI_CREQD_RequestPage_RequestId on AA_ROI_CLassicRequestDetail (RequestPage_RequestId)

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_CLassicRequestDetail') and name = 'IX_AA_ROI_CREQD_Versions_DOCID')
create index IX_AA_ROI_CREQD_Versions_DOCID on AA_ROI_CLassicRequestDetail (Versions_DOCID)

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_CLassicRequestDetail') and name = 'IX_AA_ROI_CREQD_Documents_DocId')
create index IX_AA_ROI_CREQD_Documents_DocId on AA_ROI_CLassicRequestDetail (Documents_DocId)

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_CLassicRequestDetail') and name = 'IX_AA_ROI_CREQD_Pages_VersionID')
create index IX_AA_ROI_CREQD_Pages_VersionID on AA_ROI_CLassicRequestDetail (Pages_VersionID)

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_CLassicRequestDetail') and name = 'IX_AA_ROI_CREQD_Versions_VersionID')
create index IX_AA_ROI_CREQD_Versions_VersionID on AA_ROI_CLassicRequestDetail (Versions_VersionID)

IF NOT EXISTS (SELECT 1 from sys.sysindexes where id=object_id('AA_ROI_CLassicRequestDetail') and name = 'IX_AA_ROI_CREQD_Pages_Imnet')
create index IX_AA_ROI_CREQD_Pages_Imnet on AA_ROI_CLassicRequestDetail (Pages_Imnet)
go

Select 'Completed ROI_MigrationCreateViewsTables at '+ Cast(getdate() as varchar(20));
go

