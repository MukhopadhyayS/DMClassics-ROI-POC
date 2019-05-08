#region Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/
#endregion
using System;

namespace McK.EIG.ROI.Client.Base.Model
{
    /// <summary>
    /// ROI Client Constants
    /// </summary>
    public partial class ROIConstants
    {
        public const string SecurityMenu        = "security.menu";
        public const string ChangePasswordMenu  = "security.changepassword.menu";
        public const string LogOffMenu          = "security.logoff.menu";
        public const string MonitorJobsMenu     = "security.monitorjobs.menu";
        public const string ExitMenu            = "security.exit.menu";

        public const string AdminModuleName     = "admin";
        public const string ReportsModuleName   = "reports";
        public const string PatientsModuleName  = "patients";
        public const string RequestsModuleName  = "requests";
        public const string WorklistModuleName  = "worklist";
        public const string RequestorModuleName = "requestor";
        public const string OverDueInvoiceModuleName = "overDueInvoice";

        public const string AdminBilling                    = AdminModuleName + ".billing";
        public const string AdminBillingMediaType           = AdminBilling + ".mediatype";
        public const string AdminBillingFeeType             = AdminBilling + ".feetype";
        public const string AdminBillingTier                = AdminBilling + ".billingtier";
        public const string AdminBillingTemplate            = AdminBilling + ".billingtemplate";
        public const string AdminBillingRequestorType       = AdminBilling + ".requestortype";
        public const string AdminBillingPageWeight          = AdminBilling + ".pageweight";
        public const string AdminBillingDeliveryMethod      = AdminBilling + ".deliverymethod";
        public const string AdminBillingPaymentMethod       = AdminBilling + ".paymentmethod";
        //ROI15.2 enchancements
        public const string AdminBillingInvoiceDue          = AdminBilling + ".invoicedue";
        public const string AdminBillingSalesTaxPerFacility = AdminBilling + ".salestaxperfacility";
        public const string AdminReasons                 = AdminModuleName + ".reasons";
        public const string AdminReasonAdjustment        = AdminReasons + ".adjustemnt";
        public const string AdminReasonRequest           = AdminReasons + ".request";
        public const string AdminReasonRequestStatus     = AdminReasons + ".requeststatus";
        public const string AdminOthers                  = AdminModuleName + ".others";
        public const string AdminOtherDisclosureDoc      = AdminOthers + ".disclosuredoc";
        public const string AdminOtherLetterTemplate     = AdminOthers + ".lettertemplate";
        public const string AdminOtherConfigureNotes     = AdminOthers + ".configurenotes";
        public const string AdminOtherSsnMasking         = AdminOthers + ".ssnmasking";
        public const string AdminOtherConfigureDefaultUnbillableRequest = AdminOthers + ".configuredefaultunbillablerequest";
        public const string AdminConfigurationExternalSources = AdminOthers + ".externalsources";
        public const string AdminConfigurationTurnAroundTimeDays = AdminOthers + ".turnaroundtimedays";
        //ROI16.0 zipcode
        public const string AdminOtherConfigureCountry = AdminOthers + ".configurecountry";

        public const string AdminConfiguration = AdminModuleName + ".config";
        public const string AdminConfigurationAttachment = AdminConfiguration + ".attachment";

        public const string Yes = "Yes";
        public const string No = "No";

        public const string FindRequestor              = RequestorModuleName + ".findrequestor";
        public const string RequestorInformation       = RequestorModuleName + ".requestorinformation";
        public const string RequestorRequestHistory    = RequestorModuleName + ".requesthistory";
        public const string RequestorCreateRequest     = RequestorModuleName + ".createrequest";
        //public const string RequestorLetterHistory     = RequestorModuleName + ".requestorletter";
        public const string RequestorAccountManagement = RequestorModuleName + ".accountmanagement";
        public const string RequestorAccountHistory = RequestorModuleName + ".accounthistory";
        public const string RequestorInactive          = "Inactive";
        public const string RequestorActive            = "Active";

        public const string FindRequest               = RequestsModuleName + ".findrequest";
        public const string RequestInformation        = RequestsModuleName + ".requestinformation";
        public const string RequestPatientInformation = RequestsModuleName + ".patientinformation";
        public const string BillingAndPayment         = RequestsModuleName + ".billingandpayment";
        public const string ReleaseHistory            = RequestsModuleName + ".releasehistory";
        public const string EventHistory              = RequestsModuleName + ".eventhistory";
        public const string Comments                  = RequestsModuleName + ".comments";
        public const string RequestHistory            = RequestsModuleName + ".requesthistory";
        public const string Letters                   = RequestsModuleName + ".letters";

        public const string AccountingOfDisclosure     = ReportsModuleName + ".accountingofDisclosure";
        public const string AccountReceivableAging     = ReportsModuleName + ".accountReceivableAging";
        public const string DocumentsReleasedByMRN     = ReportsModuleName + ".documentsReleasedbyMRN";
        public const string RequestStatusSummary       = ReportsModuleName + ".requestStatusSummary";
        public const string RequestDetailReport        = ReportsModuleName + ".requestDetailReport";
        public const string OutstandingInvoiceBalances = ReportsModuleName + ".outstandingInvoiceBalances";
        public const string PendingAgingSummary        = ReportsModuleName + ".pendingAgingSummary";
        public const string RequestsWithLoggedStatus   = ReportsModuleName + ".requestswithLoggedStatus";
        public const string ProcessRequestSummary      = ReportsModuleName + ".processRequestSummary";
        public const string PostedPaymentReport       = ReportsModuleName + ".postedPaymentReport";
        public const string TurnaroundTimes            = ReportsModuleName + ".turnAroundTimes";
        public const string SalesTaxReport             = ReportsModuleName + ".salesTaxReport"; 
        public const string MUReleaseTurnaroundTime = ReportsModuleName + ".muReleaseTurnaroundTimeReport";
        public const string PreBillReport=ReportsModuleName+".preBillReport";
        public const string Productivity = ReportsModuleName + ".ProductivityReport";
        //CR#377452
        public const string BillableUnbillable = ReportsModuleName + ".BillableUnbillableReport";
        public const string ROIBilling = ReportsModuleName + ".ROIBillingReport";

        public const string FindOverDueInvoice         = OverDueInvoiceModuleName + ".findOverDueInvoice";

        public const string SelectDate       = "selectDate";
        public const string NoDate           = "noDate";
        public const string CustomDateFormat = "MMMM dd, yyyy";
        public const string PleaseSelect     = "pleaseSelectCombo";
        public const string FeeTypeCombo = "feeTypeCombo";
        public const string DefaultDate      = "defaultdate";

        public const string FindPatients                 = PatientsModuleName + ".findpatient";
        public const string PatientsInformation          = PatientsModuleName + ".patientinformation";
        public const string PatientsRecords              = PatientsModuleName + ".patientrecords";
        public const string PatientsRequestHistory       = PatientsModuleName + ".requesthistory";
        public const string PatientCreateRequest         = PatientsModuleName + ".createrequest";
        public const string PatientRequest               = PatientsModuleName + ".patientrequest";
        public const string EpnLengthWhenEpnConfigure    = "253";
        public const string EpnLengthWhenEpnNotConfigure = "256";
        public const string VersionPrefix                = "Version ";
        public const string PagePrefix                   = "Page ";


        public const string HelpMenu            = "help.menu";
        public const string ROIHelp             = "help.roihelp.menu";
        public const string AboutROI            = "help.aboutroi.menu";
        public const string LanguageMenu        = "lang.menu";
        public const string LanguageEnglishMenu = "en-US";
        public const string LanguageFrenchMenu  = "fr-FR";

        // Seed data
        public const string NonHpf           = "Non-HPF";
        public const string Electronic       = "Electronic";
        public const string RequestorPatient = "Patient";

        //File Transfer               
        public const string OwnerId         = "OWNER_ID";
        public const string OwnerType       = "OWNER_TYPE";
        public const string User            = "USER";
        public const string UserId          = "USER_ID";
        public const string SecretWord      = "PASSWORD";
        public const string ChunkEnabled    = "CHUNKENABLED";
        public const string Timestamp       = "TIMESTAMP";
        public const string BlockSize       = "BLOCKSIZE";
        public const string Ticket          = "TICKET";
        public const string CacheDirectory  = "CACHEDIR";

        public const string AllFacilities   = "All Facilities";
        public const string DateFormat      = "MM/dd/yyyy";
        public const string DateTimeFormat = "MM/dd/yyyy H:mm:ss";
        public const string DateTimeAMPMDesignateFormat = "MM/dd/yyyy h:mm:ss tt";
        public const string SorterDateFormat  = "yyyy/MM/dd";
        public const string DateNoSeparatorFormat = "MMddyyyy";
        public const string NumberFormat = "N0"; 


        public const string StatusReasonDelimiter = "~@~";
        public const string Delimiter = "~!~";

        //Request Event
        public const string AllEvents = " All Events";

        //Request Comment
        public const string AllOption = " All";
        public const string CustomRange = "Custom Range"; 

        //Request Write Off
        public const string WriteOff = "Write-Off";
        public const string ReverseWriteOff = "Amount of Write-Off has been reversed";

        //Patient Records Tree
        public const string GlobalDocument = "GLOBAL DOCUMENTS";
        public const string NonHpfDocument = "NON MPF DOCUMENTS";
        public const string Attachment = "ATTACHMENTS";

        //Discard Changes Dialog Control's resource specifications
        public const string DiscardChangesDialogTitle               = "DiscardChangesDialog.Title";
        public const string DiscardChangesDialogOkButton            = "DiscardChangesDialog.OkButton";
        public const string DiscardChangesDialogCancelButton        = "DiscardChangesDialog.CancelButton";
        public const string DiscardChangesDialogOkButtonToolTip     = "DiscardChangesDialog.OkButton";
        public const string DiscardChangesDialogCancelButtonToolTip = "DiscardChangesDialog.CancelButton";
        public const string DiscardChangesDialogMessage             = "DiscardChangesDialog.Message";

        //Request XML generalization
        public const string CDataStart = "<![CDATA[";
        public const string CDataEnd = "]]>";

        public const string ItemAttribute = "<item-attribute key=\"{0}\" value=\"{1}\"/>";

        public const string ROIDomainSource = "roi";
        public const string HpfDomainSource = "hpf";
        
        public const string RequestorDomainType = "requestor";
        public const string RequestDomainType   = "request";
        public const string RequestAuthDoc      = "Authdoc";
        public const string RequestAuthDocSubtitle = "AuthdocSubtitle";
        public const string RequestAuthDocName = "AuthdocName";
        public const string RequestAuthDocDate = "AuthdocDatetime";        
        
        public const string PatientDomainType   = "patient";
        public const string EncounterDomainType = "encounter";
        public const string SupplementalEncounterDomainType = "supplemental-encounter";
        public const string AttachmentEncounterDomainType = "attachment-encounter";
        public const string DocumentDomainType = "document";
        public const string AttachmentDomainType = "attachment";
        public const string AttachmentDetailDomainType = "attachment-type-detail";
        public const string GlobalDocumentDomainType = "global-";        
        public const string VersionDomainType   = "version";
        public const string PageDomainType      = "page";
        public const string AddressDomainType   = "address";
        public const string FacilityDomainType  = "facility";

        public const string Element = "<{0}>{1}</{0}>";
        public const string Overpay = "({0})";

        //Monitor Jobs & Configure Output Settings authentication
        public const string Domain         = "DOMAIN";
        public const string DomainUserName = "DOMAINUSERNAME";
        public const string HpfUserName    = "HPFUSERNAME";
        public const string UserName       = "USERNAME";
        public const string UserSecretWord = "PASSWORD";
        public const string AppId          = "AppId";
        public const string AuthTicket     = "AuthTicket=";

        public const string ROI          = "roi";

        //Email Request Replacement Parameters
        public const string EmailRequestId = "<<REQUEST_ID>>";
        public const string EmailRequestDate = "<<REQUEST_DATE>>";
        public const string EmailReleaseDate = "<<RELEASE_DATE>>";

        //Email constants
        public const string EmailSubjectKey = "emailSubject";
        public const string EmailMessageKey = "emailBody"; 
        public const string EmailSubMgsOverrideKey = "emailSubjectOverride";
        public const string EmailBodyMgsOverrideKey = "emailBodyOverride"; 
        

        public const string OutputMethod = "output-method";
        public const string QueueSecretWord = "queue-password";
        
        //Reports
        public const string ReportLSPTitle      = ReportsModuleName + ".title";
        public const string TransId             = "transId";
        public const string ReportId            = "reportId";
        public const string ReportRequestorType = "requestorTypes";
        public const string ReportMuDocType = "muDocType";
        public const string ReportRequestStatus = "status";
        public const string ReportStartDate     = "fromDt";
        public const string ReportEndDate       = "toDt";
        public const string UserInstanceId      = "userInstanceId";
        public const string RunReports          = "Run Reports";
        public const string ReportFacilities    = "facilities";
        public const string ReportFacilityNames = "facilityNames";
        public const string ReportUserNames = "userNames";
        public const string ReportMRN           = "mrn";
        public const string ActorIds            = "actorIds";
        public const string FromStatus          = "fromStatus";
        public const string ToStatus            = "toStatus";
        public const string ReportDisposition   = "disposition";
        public const string ReportPatientId     = "patientId";
        public const string ReportRequestType   = "requestType";
        public const string ReportPatientName   = "patientName";
        public const string ReportRequestorName = "requestorName";
        public const string ReportInvoiceFromAge = "invoiceFromAge";
        public const string ReportInvoiceToAge = "invoiceToAge";
        public const string ReportBalanceLevel = "balanceLevel";
        public const string ReportBalanceDue = "balanceDue";
        public const string FromStatusValue = "fromStatusValue";
        public const string ToStatusValue = "toStatusValue";
        public const string ReportAgingStart = "agingStart";
        public const string ReportAgingEnd = "agingEnd";
        public const string ReportBalance = "balance";
        public const string ReportBalanceCriterion = "balanceCriterion";
        public const string ReportFromDateRangeValue = "fromDateRangeValue";
        public const string ReportToDateRangeValue = "toDateRangeValue";

        //Reports Constants for sorting

        public const string Facility        = "Facility";        
        public const string RequestorType   = "Requestor Type";
        public const string RequestorName   = "Requestor Name";
        public const string DateCreated     = "Date Created";
        public const string RequestStatus   = "Request Status";
        public const string StatusDate      = "Status Date";
        public const string ROIUser         = "ROI User";
        public const string AverageTat      = "Average TAT (days)";
        public const string TotalProcessed  = "Total Processed";
        public const string TotalCompleted  = "Total Completed";
        public const string TotalPending    = "Total Pending";
        public const string TotalDenied     = "Total Denied";
        public const string TotalCanceled   = "Total Canceled";
        public const string TotalPreBill    = "Total Pre-billed";
        public const string RequestId       = "Request ID";
        public const string RequestsLogged  = "Requests Logged";
        public const string Patient         = "Patient";
        public const string Gender          = "Gender";
        public const string Mrn             = "MRN";
        public const string Epn             = "EPN";
        public const string InvoiceDate     = "Invoice Date";
        public const string InvoiceNo       = "Invoice No.";
        public const string TotalCost       = "Total Cost ($)";
        public const string BalanceDue      = "Balance Due ($)";
        public const string TotalAmountPaid = "Total Amount Paid ($)";
        public const string DaysOutstanding = "Days Outstanding";
        public const string RequestReason   = "Request reason";
        public const string DisclosureDate  = "Disclosure Date";
        public const string Payment         = "Payment ($)";
        public const string Adjustment      = "Adjustment ($)";
        public const string TAHours = "TA Hours";
        public const string PostedBy = "PostedBy";
        public const string BillingLocation = "Billing Location";

        public const string FacilityFormula        = "{@FacilityText}";
        public const string RequestorTypeFormula   = "{@RequestorTypeText}";
        public const string RequestorNameFormula   = "{@RequestorNameText}";
        public const string DateCreatedFormula     = "{@DateCreatedText}";
        public const string RequestStatusFormula   = "{@RequestStatusText}";
        public const string StatusDateFormula      = "{@StatusDateText}";
        public const string ROIUserFormula         = "{@UserText}";
        public const string FromStatusFormula      = "{@FromStatusText}";
        public const string ToStatusFormula        = "{@ToStatusText}";
        public const string FromStatusValueFormula = "{@FromStatusValueText}";
        public const string ToStatusValueFormula   = "{@ToStatusValueText}";
        public const string TatFormula             = "{@TATText}";
        public const string TotalProcessedFormula  = "{@TotalProcessedText}";
        public const string TotalCompletedFormula  = "{@TotalCompletedText}";
        public const string TotalPendingFormula    = "{@TotalPendingText}";
        public const string TotalCanceledFormula   = "{@TotalCanceledText}";
        public const string TotalPreBillFormula    = "{@TotalPrebilledText}";
        public const string TotalDeniedFormula     = "{@TotalDeniedText}";
        public const string RequestIdFormula       = "{@RequestIdText}";
        public const string RequestLoggedFormula   = "{@RequestLoggedText}";
        public const string PatientFormula         = "{@PatientText}";
        public const string GenderFormula          = "{@GenderText}";
        public const string EpnFormula             = "{@EPNText}";
        public const string MrnFormula             = "{@MRNText}";
        public const string InvoiceDateFormula     = "{@InvoiceDateText}";
        public const string InvoiceNoFormula       = "{@InvoiceNoText}";
        public const string TotalCostFormula       = "{@TotalCostText}";
        public const string BalanceDueFormula      = "{@BalanceDueText}";
        public const string TotalAmountPaidFormula = "{@AmountPaidText}";
        public const string DaysOutstandingFormula = "{@DaysOutStandingText}";
        public const string RequestReasonFormula   = "{@RequestReasonText}";
        public const string DisclosureDateFormula  = "{@DisclosureDateText}";
        public const string PaymentFormula         = "{@PaymentText}";
        public const string AdjustmentFormula      = "{@AdjustmentText}";
        public const string TAHoursFormula = "{@TAHoursText}";
        public const string PostedByFormula = "{@PostedByText}";

        //In Use
        public const string ApplicationId       = "AppId";

        //Idle time
        public const int WarningSeconds         = 30;// Seconds

        //SSNMask
        public const char MaskChar = '*';        

        //Lov Maxlength
        public const int LovMaxLength = 40;

        //Special characters
        public const string AllCharacters      = "";
        public const string Numeric            = "^[0-9]+$";
        public const string Alphabets          = "^[ a-zA-Z]+$";
        public const string Alphanumeric       = "^[ a-zA-Z0-9]+$";
        public const string NameValidation     = "^[ a-zA-Z0-9,_-]+$";
        public const string NameValidationWithSlash = "^[ a-zA-Z0-9,/_-]+$";
        public const string NameValidationWithDot = "^[ a-zA-Z0-9,._-]+$";
        public const string NumericWithHyphen  = "^[0-9-]+$";
        public const string PhoneValidation    = "";
        public const string ZipValidation      = @"(^[abceghjklmnprstvxyABCEGHJKLMNPRSTVXY]{1}\d{1}[a-zA-Z]{1} *\d{1}[a-zA-Z]{1}\d{1}$)|(^\d{5}(-\d{4})?$)";
        public const string FaxValidation      = "^\\+?[ \\d\\(\\)\\-\\.,#*]{2,36}$";
        public const string EmailValidation = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
        public const string SsnEpnValidation   = "^[a-zA-Z0-9-]+$";
        public const string SsnValidation      = "^\\d{1,3}-\\d{1,2}-\\d{1,4}$";
        public const string UserIdValidation   = "^[a-zA-Z0-9`~@#%^&*()-_=+{}\\|;:\\[\\]'\",.<>/?]+$";
        public const string SecretWordValidation = "^[a-zA-Z0-9!()-.?\\[\\]_`~]+$";
        public const string CityValidation     = "^[ a-zA-Z0-9.]+$";
        //Expression accept all character expect comma.
        public const string FacilityValidation  = "^[ a-zA-Z0-9~`@#$%^&*()_+=}{\\[\\]|:;\"'?/>.<!\\-\\\\]+$";
        public const string EncounterValidation = "^[ a-zA-Z0-9,_/-]+$";
        public const string DateValidation      = @"((^(10|12|0?[13578])([/])(3[01]|[12][0-9]|0?[1-9])([/])((1[8-9]\d{2})|([2-9]\d{3}))$)|(^(11|0?[469])([/])(30|[12][0-9]|0?[1-9])([/])((1[8-9]\d{2})|([2-9]\d{3}))$)|(^(0?2)([/])(2[0-8]|1[0-9]|0?[1-9])([/])((1[8-9]\d{2})|([2-9]\d{3}))$)|(^(0?2)([/])(29)([/])([2468][048]00)$)|(^(0?2)([/])(29)([/])([3579][26]00)$)|(^(0?2)([/])(29)([/])([1][89][0][48])$)|(^(0?2)([/])(29)([/])([2-9][0-9][0][48])$)|(^(0?2)([/])(29)([/])([1][89][2468][048])$)|(^(0?2)([/])(29)([/])([2-9][0-9][2468][048])$)|(^(0?2)([/])(29)([/])([1][89][13579][26])$)|(^(0?2)([/])(29)([/])([2-9][0-9][13579][26])$))";
        //Configure Attachment 
        public const string UNCPathValidation = "(\\\\\\\\(\\d{1,3}\\.){3,3}(\\d{1,3})|(\\\\\\\\)[^\\/:*|<>?\"]+)(\\\\[^\\/:*|<>?\"])*";

        //Record View Message
        public const string RecordViewTitle = "RecordViewDialog.Title";
        public const string RecordViewMessage = "RecordViewDialog.Message";
        public const string RecordViewOkButton = "RecordViewDialog.OkButton";
        public const string RecordViewOkButtonToolTip = "RecordViewDialog.OkButton";

        //Release audit message
        //CR#365599
        public const string NumberOfPages = "{0} pages ";
        public const string SinglePage = "{0} page ";
        public const string Printed = "printed ";
        public const string Emailed = "emailed ";
        public const string Faxed = "faxed ";
        public const string ExportToPDF = "Export to PDF ";
        public const string FaxedTo = "{0} Fax Number {1} ";
        public const string WrittenToDisc = "written to disc. ";

        //Release audit message
        public const string ReleaseAuditMessage = " Release event for Request ID {0} with {1} to {2} ; ";

        //Release audit message with invoice
        public const string ReleaseAuditMessageWithInvoice = " Release event for Request ID {0} with {1} to {2} ; invoice has been generated, ";

        //Re-Release audit message
        public const string RereleaseAuditMessage = " Re-release event for Request ID {0} with {1} to {2} ; ";

        //Re-Release audit message with invoice
        public const string RereleaseAuditMessageWithInvoice = " Re-release event for Request ID {0} with {1} to {2} ; invoice has been generated, ";

        //Resend audit message for HPF patient
        public const string ResendAuditMessage = " Resend event for Request ID {0} with {1} to {2} ; ";

        //Resend audit message for non HPF patient
        public const string ResendAuditMessageForNonHpf = " Resend event for  Request ID {0} with {1} to {2} ; ";   

        //Fax audit message
        public const string FaxAuditmessage = " Sent to fax number ";

        //Email audit message
        public const string EmailAuditmessage = " Sent to email address ";

        public const string LetterGenerated = " has been generated for Request ID ";

        public const string FacilityName = "E_P_R_S";

        public const string PrintActioncode = "RP";
        public const string FaxActioncode   = "RF";
        public const string FileActioncode  = "RD";
        public const string EmailActioncode = "RE";
        public const string DiscActionCode = "RC";
        public const string ViewActionCode = "V";
        public const string AuthRequestPrintActionCode = "P";
        public const string ConfigurationChangeActionCode = "I";
        public const string uploadedActionCode = "N";

        public const string ReleasedEventmessage = " Patient(s) : {0} , Document(s) : {1} , OutputMethod : {2}";
        public const string RereleasedEventmessage = " Patient(s) : {0} , Document(s) : {1} , OutputMethod : {2}";
        public const string ResendEventmessage = " Patient(s) : {0} , Document(s) : {1} , OutputMethod : {2}";

        public const string UserMappingRequired = "UserMappingRequired";

        //sales tax audit and event message
        public const string SalesTaxMessage = "Sales Tax was {0} for {1} {2}";        
        public const string SalesTaxDocMessage = "Sales tax was {0}: {1}";        
        public const string SalesTaxStdFeeMessage = "Fee level sales tax was {0}: {1}";        
        public const string SalesTaxCusFeeMessage = "Custom fee level sales tax was {0}: {1}";
        public const string Applied = "applied to billing tier";
        public const string BillingTierRemoved = "removed from billing tier";
        public const string Selected = "selected";
        public const string Removed = "removed";
        public const string ChangeOfBillingLocationMessage = "Billing location selection for sales tax and invoice association was changed from {0} to {1}";

        //sales tax constants
        public const string SalesTaxDocFeeKey = ".Doc";
        public const string SalesTaxStdFeeKey = ".Std";
        public const string SalesTaxCusFeeKey = ".Cus.";
        
        //Invoice due days custom attribute
        public const string InvoiceDueCustomAttribute = "Custom";
        public const int InvoiceDueCustomValue = 121;

        //overwrite invoice due days audit and event message
        public const string OverwriteInvoiceDueDaysMessage = "Invoice Payment due days is changed from {0} to {1}";
        public const string RequestModificationActionCode = "2";

        //Audit and event message for sales tax elements while invoice creation
        public const string SalesTaxApplyMsgForInvoice = "Sales tax amount {0} for {1} was applied";
        public const string SalesTaxAmountMsgForInvoice = "Sales tax of {0} was applied for this invoice {1}.";
        public const string InvoiceResentMsg = "Copy of existing invoice(s) {0} were resent on {1}";

        public const string CoverLetterType = "letter";
		
		//CR#359276 and CR#359249 - Add description for automatic transaction
        public const string AutoCreditAdjustmentText = "Automatic credit adjustment";
        public const string AutoDebitAdjustmentText = "Automatic debit adjustment";

		//CR#365143 - Add masked text to display in FindRequest result
        public const string MaskedText = "****** *****";

        public const string addressDivider = ", ";

        public const string RequestorLetter = "requestorLetter";

        //Audit code and remarks for viewing the patient search record.
        public const string ViewPatientRecordRemarks = "Application : ROI. The entry with {0} was selected from the search results.";
        public const string ViewPatientRecordActionCode = "VD";

        public const string ROIConstant = "ROI";
        public const string MPFConstant = "MPF";

        public const string MigrationRequest = "Migration";

        public const string ReleaseAuditMessageForDisc = "Release event for Request ID {0} with DISC to {1} using {2} and disc label {3}; ";
        public const string RereleaseAuditMessageForDisc = "Re-Release event for Request ID {0} with DISC to {1} using {2} and disc label {3}; ";
        public const string ResendAuditMessageForDisc = "Resend event for Request ID {0} with DISC to {1} using {2} and disc label {3}; ";
        public const string InvoiceGenerated = "invoice has been generated. ";

        public const string notApplicable = "n/a";

        //File path
        public const string FilepathValidation = @"^(([a-zA-Z]\:)|(\\))(\\{1}|((\\{1})[^\\]([^/:*?<>""|]*))+)$";

        public const string Encryption_Key = "OUTPUT_AES_KEY";
        public const string Encryption_IV = "OUTPUT_AES_KEY";
    }
}
