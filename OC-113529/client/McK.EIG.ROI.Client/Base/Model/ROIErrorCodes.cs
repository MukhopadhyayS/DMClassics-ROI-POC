#region Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
    /// To Map error messages in resource file.
    /// </summary>
    public class ROIErrorCodes
    {
        #region Fields

        //System Exceptions
        public const string UnsupportedHelperType   = "A110";
        public const string FileNotFound            = "A108";
        public const string ArgumentIsNull          = "A111";
        public const string Unknown                 = "A104";
        public const string ObjectNotFound          = "A113";
        public const string EnumDescriptionNotFound = "A122";
        public const string AccessDeniedException   = "A125";
        
        //Web Exceptions
        public const string ConnectFailure       = "A106";
        public const string ROIConnectFailure    = "A129";
        public const string LogOff               = "A130";
        public const string OutputConnectFailure = "A11";
        public const string HpfConnectFailure    = "A10";
        public const string Timeout              = "A107";
        public const string ConnectAlertServer   = "S003";

		//MediaType Error Codes
        public const string MediaTypeDeleteMessage        = "A112";
        public const string MediaTypeNameAlreadyExist     = "A01";
        public const string MediaTypeSeedData             = "ROI.1.3.00";
        public const string MediaTypeNameEmpty            = "ROI.1.3.01";
        public const string MediaTypeIsAssociated         = "ROI.1.3.03";
        public const string MediaTypeNameMaxLength        = "ROI.1.3.05";
        public const string MediaTypeDescriptionMaxLength = "ROI.1.3.06";

        //FeeType Error Codes
        public const string FeeTypeDeleteMessage    = "A114";
        public const string FeeTypeNameAlreadyExist = "A05";
        public const string FeeTypeNameEmpty        = "ROI.1.4.01";
        public const string FeeTypeNameMaxLength    = "ROI.1.4.04";
        public const string FeeTypeIsAssociated     = "ROI.1.4.02";
        public const string FeeTypeAmountIsNotValid = "ROI.1.4.05";

        //Delivery Method Error Codes
        public const string DeliveryMethodDeleteMessage    = "A115";
        public const string DeliveryMethodNameAlreadyExist = "A08";
        public const string DeliveryMethodNameEmpty        = "ROI.1.2.01";
        public const string DeliveryMethodNameMaxLength    = "ROI.1.2.04";
        public const string DeliveryMethodUrlMaxLength     = "ROI.1.2.05";
        public const string DeliveryMethodInvalidUrl       = "ROI.1.2.06";

        //PageWeight
        public const string PageWeightFormat               = "ROI.1.7.00";

        //InvoiceDue
        public const string InvoiceDueFormat               = "ROI.1.14.00";

        //Sales Tax Per Facility        
        public const string SalesTaxPercentageEmpty       = "ROI.1.15.05";
        public const string SalesTaxFacilityEmpty         = "ROI.1.15.00";
        public const string SalesTaxPercentageMaxLength   = "ROI.1.15.07";
        public const string SalesTaxDescMaxLength         = "ROI.1.15.04";
        public const string SalesTaxPercentageIsNotValid  = "ROI.1.15.06";
        public const string SalesTaxFacilityDeleteMessage = "ROI.1.15.10";
        public const string SalesTaxFacilityAlreadyExist  = "ROI.1.15.09";

        //Configure Attachment
        public const string UNCPathFormat                  = "ROI.1.7.02";

        //PaymentMethod Error Codes
        public const string PaymentMethodNameEmpty            = "ROI.1.5.01";
        public const string PaymentMethodNameMaxLength        = "ROI.1.5.02";
        public const string PaymentMethodDescriptionMaxLength = "ROI.1.5.05";
        public const string PaymentMethodNameAlreadyExist     = "A09";
        public const string PaymentMethodDeleteMessage        = "A116";
        
        //BillingTemplate Error Codes
        public const string BillingTemplateNameAlreadyExist  = "A06";
        public const string BillingTemplateNameDeleteMessage = "A121";
        public const string BillingTemplateNameMaxLength     = "ROI.1.6.00";
        public const string BillingTemplateNameEmpty         = "ROI.1.6.01";
        public const string BillingTemplateIsAssociated      = "ROI.1.6.02";
        public const string BillingTemplateFeeTypeEmpty      = "ROI.1.6.04";

        //Billing Tier Error Codes
        public const string BillingTierNameAlreadyExist          = "A02";
        public const string BillingTierDeleteMessage             = "A120";
        public const string BillingTierNameEmpty                 = "ROI.1.1.01";
        public const string BillingTierNameMaxLength             = "ROI.1.1.11";
        public const string BillingTierBaseChargeLength          = "ROI.1.1.12";
        public const string BillingTierBaseChargeIsNotValid      = "ROI.1.1.05";
        public const string BillingTierOtherPageChargeIsNotValid = "ROI.1.1.17";

        public const string PageTierInvalidPriceFormat           = "ROI.1.1.13";
        public const string PageTierEmptyOrInvalid               = "ROI.1.1.16";
        public const string PageTiersNotSequential               = "ROI.1.1.14";
        public const string PageTiersExist                       = "A04";
        public const string PageTierPageNotValid                 = "ROI.1.1.18";
        public const string ElectronicMediaType                  = "ROI.1.1.19";

        //Reasons Error Codes
        public const string AdjustmentReasonNameAlreadyExist    = "A010";
		public const string StatusReasonNameAlreadyExist        = "A011";
        public const string StatusReasonDisplayTextAlreadyExist = "A012";
        public const string RequestReasonNameAlreadyExist       = "A013";
        
        public const string ReasonNameEmpty                     = "ROI.1.8.00";
        public const string ReasonDisplayTextEmpty              = "ROI.1.8.01";
        public const string ReasonDisplayTextAlreadyExist       = "ROI.1.8.02";        
        public const string RequestReasonAttributeEmpty         = "ROI.1.8.03";
        public const string StatusReasonStatusEmpty             = "ROI.1.8.04";
        public const string ReasonNameMaxLength                 = "ROI.1.8.05";
        public const string ReasonDisplayTextMaxLength          = "ROI.1.8.06";
        public const string ReasonOperationFailed               = "ROI.1.8.07";
        public const string ReasonTypeNotMentioned              = "ROI.1.8.08";
        public const string RequestReasonDisplayTextMaxLength   = "ROI.1.8.09";

        public const string AdjustmentReasonDeleteMessage = "A117";
        public const string RequestReasonDeleteMessage    = "A118";
        public const string StatusReasonDeleteMessage     = "A119";

        //Requestor Type Error Codes
        public const string RequestorTypeNameAlreadyExist       = "A07";
        public const string RequestorTypeDeleteMessage          = "A123";
        public const string RequestorTypeNameEmpty              = "ROI.1.9.01";
        public const string RequestorTypeNameMaxLength          = "ROI.1.9.02";
        public const string RequestorTypeHpfBillingTierEmpty    = "ROI.1.9.03";
        public const string RecordViewNameEmpty                 = "ROI.1.9.04";
        public const string RequestorTypeSeedData               = "ROI.1.9.05";
        public const string RequestorTypeNonHpfBillingTierEmpty = "ROI.1.9.12";
        
        //Disclosure document Types
        public const string CodeSetIdInvalid      = "ROI.1.10.01";
        public const string DocTypeIdInvalid      = "ROI.1.10.02";
        public const string MultipleAuthSelected  = "ROI.1.10.03";
        public const string NoAuthRequestSelected = "ROI.1.10.04";
        public const string DisclosureConfirmSaveDialogKey = "A101";

        public const string SystemError = "S002";

        //Letter Template
        public const string LetterTemplateNameAlreadyExist     = "A014";
        public const string LetterTemplateInvalidFileFormat    = "A015";
        public const string LetterTemplateInvalidFilePath      = "A016";
        public const string LetterTemplateTypeOtherSelected    = "ROI.1.11.00";
        public const string LetterTemplateNameEmpty            = "ROI.1.11.01";
        public const string LetterTemplateNameMaxLength        = "ROI.1.11.02";
        public const string LetterTemplateFileNameMaxLength    = "ROI.1.11.03";
        public const string LetterTemplateProcessFailed        = "ROI.1.11.04";
        public const string LetterTemplateDescriptionMaxLength = "ROI.1.11.05";
        public const string LetterTemplateDeleteMessage        = "ROI.1.11.06";
        public const string LetterTemplateFilePathEmpty        = "ROI.1.11.07";
        public const string LetterTemplateLetterTypeEmpty      = "ROI.1.11.08";
        public const string LetterTemplateInvalidDocumentId    = "ROI.1.11.09";
        public const string FileAlreadyOpen                    = "ROI.1.11.10";
        public const string FileIsReadOnly                     = "ROI.1.11.11";
        public const string LetterTemplateInvalidUploadUrl     = "ROI.1.11.12";
        public const string LetterTemplateInvalidDownloadUrl   = "ROI.1.11.13";
        public const string LetterTemplateUploadFailed         = "ROI.1.11.14";
        public const string FileSizeIsZero                     = "ROI.1.11.15";
        
        
        //Patient Information
        public const string InvalidSearch        = "ROI.3.1.01";
        public const string PatientLastNameEmpty     = "ROI.3.1.02";
        public const string SupplementalOperationFailed  = "ROI.3.1.03";
        public const string PatientLastNameMaxLength = "ROI.3.1.04";
        public const string PatientNonUnique     = "ROI.3.1.05";
        public const string PatientDeleteMessage = "ROI.3.1.06";
        public const string PatientSsnLengthExceedsLimit = "ROI.3.1.07";
        public const string PatientMrnLengthExceedsLimit = "ROI.3.1.08";
        public const string PatientFacilityLengthExceedsLimit = "ROI.3.1.09";
        public const string PatientEpnLengthExceedsLimit = "ROI.3.1.10";
        public const string PatientInUse         = "ROI.3.0.00";
        public const string PatientFirstNameMaxLength = "ROI.3.1.17";
        public const string PatientFirstNameEmpty = "ROI.3.1.18";
        public const string DirectoryAccessDenied = "ROI.1.13.03";

        //Requestor Information
        public const string RequestorInUse = "ROI.2.0.00";

        public const string RequestorNameEmpty     = "ROI.2.1.04";
        public const string RequestorTypeEmpty     = "ROI.2.1.05";
        public const string RequestorCannotDelete  = "ROI.2.1.07";
        public const string RequestorNameMaxLength = "ROI.2.1.11";
        public const string RequestorNameInReport = "ROI.2.5.1";
        public const string RequestorNonUnique     = "ROI.3.1.05";
        public const string RequestorMrnLengthLimit = "ROI.2.1.33";
        public const string RequestorSsnLengthLimit = "ROI.2.1.02";
        public const string RequestorFacilityLengthLimit = "ROI.2.1.34";
        public const string RequestorLastNameEmpty = "ROI.2.1.36";
        public const string RequestorFirstNameEmpty = "ROI.2.1.37";
        public const string RequestorLastNameMaxLength = "ROI.2.1.38";
        public const string RequestorFirstNameMaxLength = "ROI.2.1.39";
	    public const string RequestorPatientEpnLenghtLimit = "ROI.2.1.01";
        
        //Added for CR# 344550
        public const string RequestorFirstNameLength = "ROI.2.1.40";
        public const string RequestorLastNameLength = "ROI.2.1.41";
        public const string RequestorLastFirstNameLength = "ROI.2.1.42";
        public const string RequestorFirstLastNameLength = "ROI.2.1.43";
        // --------------
        public const string NonHpfDocumentNameEmpty             = "ROI.3.3.00";
        public const string NonHpfDocumentDateOfServiceEmpty    = "ROI.3.3.01";
        public const string NonHpfDocumentFacilityEmpty         = "ROI.3.3.02";
        public const string NonHpfDocumentInvalidPageCount      = "ROI.3.3.03";
        public const string NonHpfDocumentHasBeenReleasedEmpty  = "ROI.3.3.04";
        
        //Request
        public const string RequestRequestorNameEmpty = "ROI.4.0.01";
        public const string RequestReasonEmpty        = "ROI.4.0.02";
        public const string RequestReceiptDateEmpty   = "ROI.4.0.03";
        public const string RequestInvalidReceiptDate = "ROI.4.0.04";
        public const string RequestCannotDelete       = "ROI.4.0.05";
        public const string RequestCurrentReasonEmpty = "ROI.4.0.06";
        public const string NotValidNumber            = "ROI.4.0.07";
        public const string FaxNumberEmpty            = "ROI.4.0.09";        
        public const string InvalidInvoiceNumber      = "ROI.4.0.20";
        public const string InvalidRequestId          = "ROI.4.0.21";
        public const string InvalidRequestorName      = "ROI.4.0.22";        
        //public const string RequestHasNoPatient       = "ROI.4.0.09";
        public const string RequestorIsInactive         = "ROI.4.0.23";
        public const string RequestCompletedDateEmpty   = "ROI.4.0.24";
        public const string RequestInvalidCompletedDate = "ROI.4.0.25";
        
        public const string InvalidHpfPatientAssociated = "ROI.4.3.01";

        public const string RecordNotFound              = "IN.USE.0.0.1";

        //Added for CR# 341396 & 346831
        public const string InvalidDirectory            = "INVALID_DIRECTORY";
        public const string InvalidSource               = "SOURCE_INVALID";
        public const string InvalidDiscDevice           = "INVALID_DISC_DEVICE";
        //-------

         //Request Comment
        public const string RequestCommentEmpty     = "ROI.5.0.04";
        public const string RequestCommentMaxLength = "ROI.5.0.05";        
        
        //PresetDate Range
        public const string ToDateInvalid       = "A502";
        public const string FutureDateSelected  = "A503";
        public const string FromDateInvalid     = "A504";
        public const string EndDateInvalid      = "A505";

        public const string StartDateInvalids = "A506";
        public const string EndDateInvalids = "A507";
        
        //Update Request Status
        public const string InvalidStatusForNewRequest = "ROI.4.1.04";
        public const string InvalidStatusChange        = "ROI.4.1.03";

        //Configure Notes
        public const string ConfigureNotesDeleteMessage           = "A124";
        public const string ConfigureNotesNameAlreadyExist        = "ROI.1.12.00";
        public const string ConfigureNotesNameEmpty               = "ROI.1.12.01";
        public const string ConfigureNotesDisplayTextEmpty        = "ROI.1.12.03";
        public const string ConfigureNotesNameMaxLength           = "ROI.1.12.04";
        public const string ConfigureNotesDisplayTextMaxLength    = "ROI.1.12.05";

        //Billing - DocumentCharge errorcodes
        public const string InvalidPage = "ROI.7.1.01";
        public const string InvalidCopy = "ROI.7.1.02";

        //Billing - FeeCharge errorcodes
        public const string InvalidFeeCharge = "ROI.7.2.01";
        public const string InvalidFeeChargeFormat = "ROI.7.2.02";

        //Billing - ShippingInfo errorcodes
        public const string InvalidShippingCharge = "ROI.7.3.01";

        //Billing - AdjustmentInfo errorcodes
        public const string InvalidAdjustmentAmount = "ROI.7.4.01";
        public const string InvalidPaymentAmount = "ROI.7.4.02";
        //Added for CR# 366282
        public const string InvalidAdjustmentAmountFormat = "ROI.7.4.04";
        public const string InvalidPaymentAmountFormat = "ROI.7.4.05";
        //Added for CR# 377173
        public const string InvalidRefundAmoutFormat = "ROI.7.4.06";

        public const string RetrieveCCDFailure = "ROI.7.0.34";
        //Find Request - Balance Due errorcode
        public const string InvalidBalanceDue = "ROI.7.4.03";

        //Validation error codes

        public const string InvalidSsn = "ROI.2.1.19";        
        public const string InvalidEpn = "ROI.2.1.18";
        public const string InvalidSsnSearch = "ROI.2.1.35";

        public const string InvalidAltState     = "ROI.0.0.22";
        public const string InvalidAltCity      = "ROI.0.0.20";
        public const string InvalidAltZip       = "ROI.0.0.21";
        public const string InvalidMainState    = "ROI.10.0.14";
        public const string InvalidMainCity     = "ROI.10.0.15";
        public const string InvalidMainZip      = "ROI.10.0.16";
        public const string InvalidHomePhone    = "ROI.2.1.20";
        public const string InvalidWorkPhone    = "ROI.2.1.22";
        public const string InvalidCellPhone    = "ROI.2.1.23";
        public const string InvalidContactPhone = "ROI.2.1.30";
        public const string InvalidFax          = "ROI.2.1.26";
        public const string InvalidEmail        = "ROI.2.1.24";
        public const string InvalidContactEmail = "ROI.10.0.13";
        public const string InvalidContactName  = "ROI.2.1.28";
        public const string InvalidName         = "ROI.2.1.29";
        // Patient module
        public const string InvalidLastName     = "ROI.10.0.17";
        public const string InvalidFirstName    = "ROI.10.0.18";
        public const string InvalidEncounter    = "ROI.10.0.19";
        public const string InvalidMrn          = "ROI.10.0.20";
        public const string InvalidFacility     = "ROI.10.0.25";
        public const string InvalidPatientName  = "ROI.10.0.27";
        public const string EmptyFacility       = "ROI.10.0.34";
        public const string InvalidPatientEmail = "ROI.10.0.35";

        // Login module
        public const string UserIdEmpty     = "ROI.10.0.21";
        public const string SecretWordEmpty = "ROI.10.0.22";
        public const string InvalidUserId   = "ROI.10.0.23";
        public const string InvalidSecretWord = "ROI.10.0.24";
        public const string InvalidDomain     = "ROI.10.0.30";

        // Output module
        public const string FilePasswordEmpty = "ROI.10.0.26";

        //Report Module
        public const string ReportGenerationFailure = "ROI.11.0.0";
        public const string FileInUse               = "ROI.11.0.1";
        public const string DeviceNotValid          = "ROI.11.0.2";

        public const string PrintFaxAccessDenied = "A126";
        public const string ExportToPdfDenied    = "A127";
        public const string ExportToEmailDenied  = "A128";
        
        public const string IncorrectDate    = "ROI.10.0.28";
        public const string InvalidDate      = "ROI.10.0.29";        
        public const string InvalidDateValue = "ROI.10.0.31";  //Save
        public const string InvalidSqlDate   = "ROI.10.0.32";  //Search
        public const string InvalidDateOfService = "ROI.10.0.33";

        // Attachments
        public const string AttachmentTypeEmpty = "ROI.13.3.00";
        public const string AttachmentDateOfServiceEmpty = "ROI.13.3.01";
        public const string AttachmentFacilityEmpty = "ROI.13.3.02";
        public const string AttachmentInvalidPageCount = "ROI.13.3.03";
        public const string AttachmentHasBeenReleasedEmpty = "ROI.13.3.04";
        public const string CcrCcdRawInvalidPageCount = "ROI.13.3.05";
        public const string CcrCcdFormattedInvalidPageCount = "ROI.13.3.06";
        public const string CcrCcdRawInvalidFile = "ROI.13.3.07";
        public const string CcrCcdFormattedInvalidFile = "ROI.13.3.08";
        public const string AttachmentNoServerFile = "ROI.13.3.09";
        public const string AttachmentFileNameEmpty = "ROI.13.3.10";
        public const string AttachmentExtensionEmpty = "ROI.13.3.11";
        public const string AttachmentInvalidTitle = "ROI.13.3.12";
        public const string AttachmentInvalidEncounter = "ROI.13.3.13";
        public const string AttachmentUploadError = "ROI.13.3.14";
        public const string CCRCCDConvertError = "ROI.13.3.15";
        public const string ErrorAddingFile = "ROI.13.3.22";
        public const string CcdRetrievalFailed = "ROI.7.0.34";
        public const string ExternalSourceNotFound = "ROI.7.0.36";
        
        //Billing Location not selected
        public const string BillingLocationNotSelected = "ROI.1.15.11";
        public const string BillingLocationNotSelectedRelease = "ROI.1.15.13";
        public const string InvoiceNotSelected = "ROI.1.15.12";
        //External Source
        public const string ExternalSourceNameinvalid  = "ROI.14.4.00";
        public const string Descriptioninvalid = "ROI.14.4.01";
        public const string PropertyNameinvalid = "ROI.14.4.02";
        public const string Propertyvalueinvalid = "ROI.14.4.03";
        public const string ExternalSourceConnectionFailed = "ROI.14.4.04";
       

        //Turnaround Time Days
        public const string DayNameinvalid = "ROI.15.5.00";
        public const string DayStatusinvalid = "ROI.15.5.01";

        //MU Release TurnaroundTimeReport
        public const string SubtitleDatasourceFailed = "ROI.16.6.00";

        public const string InvalidCustomFeeDescription = "ROI.1.15.15";

        public const string InvalidPaymentDate = "ROI.17.0.01";
        public const string InvalidPaymentNote = "ROI.17.0.02";
        public const string InvalidPaymentMethod = "ROI.17.0.03";

        public const string InvalidRefundAmount = "ROI.17.0.04";
        public const string InvalidRefundNote = "ROI.17.0.05";
        public const string InvalidRefundDate = "ROI.17.0.06";
    
        //Search Overdue Invoice
        //public const string InvalidRequestorName = "ROI.2.1.00";

		//HPF Docs View
        public const string WinDSSNotFound = "HPF.WinDSSNotFound";

        //Output Error messages
        public const string UnknownObjectException = "UnknownObjectException";
        public const string InvalidReference = "REFERENCE_INVALID";

        public const string TempDirectoryConfiguration = "ROI.1.13.06";

        #endregion
    }
}
