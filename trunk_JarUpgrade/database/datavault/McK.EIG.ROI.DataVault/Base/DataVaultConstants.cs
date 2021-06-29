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

using System.Configuration;
using System.Globalization;
using System.IO;

#endregion

namespace McK.EIG.ROI.DataVault.Base
{

    public enum VaultMode
    {
        Create = 0,
        Update = 1
    }

    public enum Operation
    {
        Add = 0,
        Modify = 1,
        Delete = 2
    }

    public enum VaultModule
    {
        Admin = 0,
        Patient = 1,
        Request = 2,
        Requestor = 3,
        Billing =4
    }

    /// <summary>
    /// DataVault Constants
    /// </summary>
    public class DataVaultConstants
    {
        //File Input Type
        internal static bool IsExcelFile = (string.Compare(ConfigurationManager.AppSettings["DataVaultFileType"], "xls", true, CultureInfo.CurrentCulture) == 0);

        //ModuleType
        internal static string Patient   = "Patient";
        internal static string Requestor = "Requestor";

        //Create Module
        internal static string CreateAdminModule     = "01_ROI_Admin_Create.xls";
        internal static string CreatePatientModule   = "02_ROI_Patients_Create.xls";
        internal static string CreateRequestorModule = "03_ROI_Requestor_Create.xls";
        internal static string CreateRequestModule   = "04_ROI_Request_Create.xls";
        internal static string CreateBillPayModule   = "05_ROI_Billing_Payment_Create.xls";

        //Update Module
        internal static string UpdateAdminModule     = "06_ROI_Admin_Update.xls";
        internal static string UpdatePatientModule   = "07_ROI_Patients_Update.xls";
        internal static string UpdateRequestorModule = "08_ROI_Requestor_Update.xls";
        internal static string UpdateRequestModule   = "09_ROI_Request_Update.xls";
        internal static string UpdateBillPayModule   = "10_ROI_Billing_Payment_Update.xls";        

        //Create Admin Entity Types
        internal static string MediaType            = "010_MediaTypes";
        internal static string FeeType              = "040_FeeTypes";
        internal static string PaymentMethod        = "100_PaymentMeth";
        internal static string DeliveryMethod       = "090_DeliveryMeth";
        internal static string BillingTemplate      = "050_BillTemplate";
        internal static string BillingTier          = "020_BillingTier_General";
        internal static string PageLevelTier        = "030_BillingTier_Tiers";
        internal static string RequestReason        = "120_ReqReasons";
        internal static string StatusReason         = "130_ReqStatReasons";
        internal static string AdjustmentReason     = "110_AdjReasons";
        internal static string DisclosureDocType    = "140_DisclDocs";
        internal static string RequestorTypeGeneral = "060_ReqType_General";
        internal static string RequestorTypeBT      = "070_ReqType_BT";
        internal static string LetterTemplate       = "150_LetterTempl";
        internal static string ConfigureNotes       = "160_Configure_Notes";

        //Create Patinet Entity Types
        internal static string PatientInfo    = "010_Sup_Pts";
        internal static string NonHpfDocument = "020_Sup_Docs";

        //Create Requestor Entity Types
        internal static string RequestorInfo = "010_Reqor_Info";

        //Create Request Entity Types
        internal static string RequestInfo         = "010_New_Req_Info";
        internal static string RequestRequorInfo   = "020_Requor_Detail_Chgs";
        internal static string RequestPtsInfo      = "030_Pts_On_Request";
        internal static string RequestSupDocs      = "040_Sup_Docs_Selected";
        internal static string RequestItemDetails  = "050_HPF_Docs_Selected";
        internal static string RequestComment      = "060_Req_Comments";
        internal static string RequestStatusReason = "070_Status_Reasons";

        //Create Billing and Payment Entity Types
        internal static string BillGeneralInfo    = "010_Rel_General";
        internal static string BillDocChargeInfo  = "020_Rel_Doc_Charge";
        internal static string BillFeeInfoGeneral = "030_Rel_Fee_General";
        internal static string BillFeeChargeInfo  = "031_Rel_Fee_Charge";
        internal static string BillShippingInfo   = "040_Rel_Shipping";
        internal static string BillAdjInfo        = "050_Rel_Adj";
        internal static string BillPayInfo        = "060_Rel_Pay";

        internal static string UserAppSettings        = "applicationSettings/McK.EIG.ROI.Client.Properties.Settings";
        internal static string LogConfigFileName      = ConfigurationManager.AppSettings["LogConfigFileName"];
        internal static string ValidateLogFileName    = ConfigurationManager.AppSettings["ValidateLogFileName"];

        private static DirectoryInfo info = new DirectoryInfo(ConfigurationManager.AppSettings["DataSet"]);
        internal static string UserId            = ConfigurationManager.AppSettings["UID"];
        internal static string Password          = ConfigurationManager.AppSettings["PWD"];        
        internal static string CsvDataSourceName = ConfigurationManager.AppSettings["CsvDsnName"];
        internal static string XlsDataSourceName = ConfigurationManager.AppSettings["XlsDsnName"];
        internal static string DataSetPath       = info.FullName;
        
        //Hpf Server Info        
        internal static string ProviderName     = ConfigurationManager.ConnectionStrings["DbProvider"].ProviderName;
        internal static string ConnectionString = ConfigurationManager.ConnectionStrings["DbProvider"].ConnectionString;

        internal static string CSVDataQuery     = "SELECT * FROM {0}.csv";                
        internal static string CSVSelectQuery   = CSVDataQuery + " WHERE {1} = '{2}'";

        internal static string ExcelDataQuery   = "SELECT * FROM [{0}]";
        internal static string ExcelSelectQuery = ExcelDataQuery + " WHERE {1} = '{2}'";

        internal static string CSVUpdateQuery   = CSVDataQuery + " WHERE Release_Counter = {1}";
        internal static string ExcelUpdateQuery = ExcelDataQuery + " WHERE Release_Counter = {1}";
        

        internal static string CSVCountQuery    = "SELECT COUNT(*) FROM {0}.csv WHERE {1} = '{2}'";
        internal static string ExcelCountQuery  = "SELECT COUNT(*) FROM [{0}] WHERE {1} = '{2}'";        
        
        //internal static string DsnExist         = "DSN Exist: ";        
        internal static string StartTag         = "Processing Data Vault for ";
        internal static string EndTag           = "Data Vault Successfully inserted for ";
        internal static string ProcessStartTag  = "Processing Record: {0} Start Time: {1}";
        internal static string ProcessEndTag    = "Complete Processing. End Time: ";

        //Letter Template Message
        internal static string TotalFileSize       = "Total File Size";
        internal static string TransferredFileSize = "Transferred File Size";       

        //Request Xml Constants
        internal static string ContentListTag      = "ContentList";
        internal static string ImNetTag            = "Imnet ID";
        internal static string EncounterDomainType = "his.dbo.encounters";
        internal static string ContentMetaData     = "Cabinet.dbo.ROI_ContentMetaData";
        
        //Encounter details        
        internal static string EncounterId      = "ENCOUNTER";
        internal static string EncFacility      = "FACILITY";        
        internal static string EncIsLocked      = "LOCKOUT";
        internal static string EncIsVip         = "VIP";
        internal static string EncDateofService = "ADMITTED";        
        internal static string IsDeficiency     = "DeficencyCount";
        internal static string EncPatientStatus = "SERVICE";
        internal static string EncPatientType   = "PT_TYPE";
        internal static string EncDisChargeDate = "DISCHARGED";

        //Global Document                 
        internal static string Subtitle   = "SUBTITLE";
        internal static string ChartOrder = "ChartOrder";        
        internal static string DocType    = "DOC_NAME";

        //Version details        
        internal static string VersionNumber     = "VersionNumber";

        //Page details
        internal static string PageNumber       = "page";
        internal static string PageImNet        = "imnet";        
        internal static string PageContentCount = "ContentCount";

        //Request Stored Procedure
        internal static string MetadataForImnet = "ROI_GetMetadataForImnet";

        //Log Information.
        internal static string StartTime = "Start Date/Time: ";
        internal static string EndTime   = "Start Date/Time: ";

        internal static string Operation = "Operation";

        //Validation Message.
        internal static string ModuleName   = "Module Name         : ";
        internal static string FileName     = "FileName            : ";
        internal static string UseCase      = "UseCase             : ";
        internal static string SheetName    = "Sheet Name          : ";
    }
}
