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

#region Namespace

using McK.EIG.ROI.Client.Base.Model;

#endregion

namespace McK.EIG.ROI.DataVault.Base
{
    public class DataVaultErrorCodes : ROIErrorCodes
    {
        #region Fields

        //DataVault
        internal static string OdbcError                  = "Error Occured while reading file or Sheet {0}.";
        internal static string AccessPermissionToRegistry = "Security Error is Detected.";
        internal static string InvalidArgument            = "Argument is not in Proper Format.";
        internal static string ObjectDispose              = "Error Occured while disposing object.";
        internal static string DirectoryNotFound          = "Directory not found in the specified path: ";
        //internal static string InValidFileType            = "Input file should be either “csv” or “xls”. Configure the file type properly.";

        internal static string EntityNotFound = "Entity of '{0}' not found.";
        internal static string UnknownObject  = "Unknown entity object '{0}' not found in '{1}'.";

        //Disclosure Document Types
        internal static string UnknownCodeSet      = "CodeSet '{0}' not found at line {1}";
        internal static string UnknownDocumentName = "Document '{0}' not found at line {1}";

        //Requestor Type.                
        internal static string BillingTemplateNotAssociated = "Please associate the Billing Template, than specify the Default Billing template for the Requestor Type RefID: ";
        internal static string ElectronicBillingTier        = "Billing Tier associated with Requestor Type is not Electronic.";
        internal static string RecordViewNotFound           = "'{0}' Record view not found.";

        //Billing Tier
        internal static string PageTierNotExist = "BillingTier '{0}' does not have any associated page tiers.";

        //Request Reason
        internal static string InvalidRequestAttribute = "Invalid Request Attribute {0}";

        //NonHPF Document
        internal static string InvalidDocumentType = "Document Type not found '{0}'.";

        //Common        
        internal static string ReferenceID     = "{0} Reference ID : {1}, DB ID : {2}";

        //Request Error Code
        internal static string RequestorNotFound      = "Requestor not found for the Reference ID : {0}";
        //internal static string PatientNotFound        = "Patient not found for the Reference ID : {0}";
        internal static string MultipleRequestorFound = "More than one Requestor found for the Reference ID : {0}";
        internal static string HpfPatientNotFound     = "Hpf patient for the specified MRN {0} and Facility {1} for the request  reference Id {2} not found.";

        internal static string InvalidImNetId = "Invalid ImNet Id found for the Patient Request RefId={0}, MRN = {1} and Facility = {2}";

        //File Not found
        internal static string CSVFileNotFound = "Below mentioned file not found";
        internal static string ExcelSheetNotFound = "Below mentioned sheets are not found";        

        //Release
        
        internal static string InvalidAdjType = "Invalid Adjustment Type {0} at line {1}";
        internal static string InvalidPayType = "Invalid Payment Method {0} at line {1}";

        internal static string InvalidAmount = "Amount cannot be zero or Empty at line {0}";
        internal static string InvalidDesc   = "Description cannot be Empty at line {0}";

        internal static string InvalidShippingInfo = "More then one shipping Information encountered for the release {0}";

        internal static string FeeChargeRepeated = "Fee Charge '{0}' Repeated  for the release RefID {1}";

        internal static string InvalidSystemSeedData = "Entity '{0}' in {1} is not of type system seed data";
        internal static string InvalidSeedData       = "Entity '{0}' in {1} is not of type seed data";
        internal static string InvalidData           = "Reference id {0} not found in {1}";

        internal static string RequestorCannotChange = "Requestor cannot be changed for the request RefID {0}, since request have been released.";

        internal static string OutputMethodNotSpecified = "Request Cannot be released since, no output method specified for the release ref id {0}";

        internal static string RequestReasonRequired = "Atleast one reason required for the selected status '{0}' for the request RefID '{1}'";

        internal static string RoiPatientNotforRequest = "Patient RefID '{0}' is not associated with the request {1}";
        //internal static string HpfPatientNotforRequest = "Patient MRN '{0}' and with Facility '{1}' is not associated with the request {2}";
        internal static string RoiPatientCannotbeRemoved = "Patient RefID '{0}' is cannot be removed from the request {1}, since request is already released.";
        //internal static string HpfPatientCannotbeRemoved = "Patient MRN '{0}' and with Facility '{1}' is cannot be removed from the request {1}, since request is already released.";

        internal static string ReferenceIdRepeated = "Reference Id repeated.";

        //validation error message.
        internal static string ErrorMessage = "Error @ {0} : {1}, {2}";

        internal static string InvalidCodeSetId = "Invalid Code Set";

        #endregion
    }
}
