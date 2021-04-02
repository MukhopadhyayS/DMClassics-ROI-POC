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
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Globalization;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;

using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.DataVault.Patient.Process;
using McK.EIG.ROI.DataVault.Request.Process;

namespace McK.EIG.ROI.DataVault.Request.Validate
{
    /// <summary>
    /// Class inserts the requestor information.
    /// </summary>
    public partial class ValidateRequestPatientInfo : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(ValidateRequestInformation));

        #region Fields

        //Request Patient Info
        private const string RequestRefId           = "ReqRef_ID";
        private const string PatientRefId           = "SupRef_ID";
        private const string RequestPatientMrn      = "Pt_MRN";
        private const string RequestPatientFacility = "Pt_Facility_Code";

        private const string VIPStatus       = "VIP";
        private const string PatientName     = "Name";
        private const string PatientGender   = "Gender";
        private const string PatientDOB      = "DOB";
        private const string PatientSSN      = "SSN";
        private const string PatientEPN      = "EPN";
        private const string PatientFacility = "Facility_Code";
        private const string PatientMRN      = "MRN";        

        //Supplemental Patient Document Ref ID
        private const string NonHpfDocRefId = "NonHPFDocRef_ID";
        private const string NonHpfBillingTier = "NonHPF_BillingTier";
        private const string Encounter      = "EncAcct";
        private const string DocumentType   = "Doc_Type";
        private const string DateOfService  = "Date_Of_Service";
        private const string Facility       = "Facility_Code";
        private const string Department     = "Department";
        private const string Comment        = "Comment";

        //Global and Encounter Document.
        private const string PageImNetId = "Pg_ImNetId";

        private PatientDetails patientDetails;

        //Parameters
        private const string ParamImNets        = "@ListOfImnets";
        private const string ParamInvalidImNets = "@ListOfInvalidImnets";
        private const string ParamMetaData      = "@Metadata";

        //Query to get Document.
        private static string CSVDocQuery   = "SELECT * FROM {0}.csv";
        private static string CsvHpfPatient = CSVDocQuery + " WHERE " + RequestPatientMrn + "= '{1}' AND " + RequestPatientFacility + " ='{2}' AND " + RequestRefId + "='{3}'";
        private static string CsvSupPatient = CSVDocQuery + " WHERE " + PatientRefId + " = '{1}' AND " + RequestRefId + " ='{2}'";

        private static string ExcelDocQuery   = "SELECT * FROM [{0}$]";
        private static string ExcelHpfPatient = ExcelDocQuery + " WHERE " + RequestPatientMrn + " = '{1}' AND " + RequestPatientFacility + " = '{2}' AND " + RequestRefId + " = '{3}'";
        private static string ExcelSupPatient = ExcelDocQuery + " WHERE " + PatientRefId + " = '{1}' AND " + RequestRefId + "='{2}'";


        StringBuilder errorMessage = new StringBuilder();

        private VaultMode modeType;
        private static string ValidReqPatientInfo = "Validating Request Patient Info: ";
        private static string NonHpfPatientNotFound = "Patient Not found Patient RefId: ";

        #endregion

        #region Method

        /// <summary>
        /// Retrive the Patient Info Details for the specified Request RefId.
        /// Process: Create Request.
        /// </summary>
        /// <param name="requestRefId">Requestor Reference Id.</param>
        /// <param name="requestDetails">Request Details.</param>
        /// 

        /// <summary>
        /// System Load the Requestor Information Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();
            string requestRefId = string.Empty;
            long recordCount = 1;
            bool isHeaderExist = false;
            
            while (reader.Read())
            {
                try
                {
                    log.Debug(ValidReqPatientInfo + recordCount);
                    errorMessage.Remove(0, errorMessage.ToString().Length);
                    requestRefId = Convert.ToString(reader[RequestRefId], CultureInfo.CurrentCulture).Trim();
                    long count = IsValidateRequest(requestRefId);
                    if (count == 0)
                    {
                        string message = string.Format(CultureInfo.CurrentCulture, "Request Not Found RefId="+requestRefId);
                        errorMessage.Append(message);                        
                    }
                    
                    RequestPatientDetails requestPatientDetails;

                    string patientRefId     = Convert.ToString(reader[PatientRefId], CultureInfo.CurrentCulture).Trim();
                    string patientMRN       = Convert.ToString(reader[RequestPatientMrn], CultureInfo.CurrentCulture).Trim();
                    string patientFacility  = Convert.ToString(reader[RequestPatientFacility], CultureInfo.CurrentCulture).Trim();

                    if (patientRefId.Length != 0 && patientRefId != "0")
                    {
                        requestPatientDetails       = GetPatientDetails(patientRefId);
                        requestPatientDetails.IsHpf = false;
                        requestPatientDetails.Id    = recordCount;
                    }
                    else
                    {
                        requestPatientDetails           = GetPatientDetails(requestRefId, patientMRN, patientFacility);
                        requestPatientDetails.MRN       = patientMRN;
                        requestPatientDetails.FacilityCode  = patientFacility;
                        requestPatientDetails.IsHpf = true;
                        GetHpfDocument(requestRefId, patientMRN, patientFacility);
                    }
                    //Appending the supplemental document to the patient.
                    GetSupplementalDocument(requestRefId, patientRefId, requestPatientDetails);
                  
                    if (errorMessage.ToString().Trim().Length > 0)
                    {
                        throw new VaultException(errorMessage.ToString().Trim());
                    }
                }
                catch (VaultException cause)
                {
                    if (!isHeaderExist)
                    {
                        isHeaderExist = true;
                        ValidateUtility.WriteLog("Request",
                            (ModeType == VaultMode.Create) ? DataVaultConstants.CreateRequestModule : DataVaultConstants.UpdateRequestModule,
                            "Request Information",
                            EntityName + "_" + ModeType);
                    }
                    ValidateUtility.WriteLog(requestRefId, recordCount, cause.Message);
                }
                recordCount++;
            }

            if (!reader.IsClosed)
            {
                reader.Close();
            }
            log.ExitFunction();
            return null;

        }

        private long IsValidateRequest(string requestRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
            string query;
            string entity = DataVaultConstants.RequestInfo + "_" + modeType;

            query = (DataVaultConstants.IsExcelFile)
                    ? "SELECT COUNT(*) FROM [" + entity + "$] WHERE Ref_ID= '" + requestRefId + "'"
                    : "SELECT COUNT(*) FROM " + entity + ".csv WHERE Ref_ID= '" + requestRefId + "'";


            IDataReader countReader = Utility.ReadData(entity, query);
            countReader.Read();
            long count = countReader.GetInt64(0);
            countReader.Close();
            if (DataVaultConstants.IsExcelFile)
            {
                if (ModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestModule);
                }
            }
            return count;
        }

        #region PatientInformation

        /// <summary>
        /// Get the Hpf patient details for the specified MRN and Facility.
        /// </summary>
        /// <param name="requestRefId">Request Reference Id.</param>
        /// <param name="patientMRN">Patient MRN.</param>
        /// <param name="patientFacility">Patient Facility.</param>
        /// <param name="requestPatientDetails">RequestPatientDetails.</param>
        private RequestPatientDetails GetPatientDetails(string requestRefId, string patientMRN, string patientFacility)
        {
            log.EnterFunction();

            FindPatientCriteria searchCriteria = new FindPatientCriteria();
            searchCriteria.FirstName = string.Empty;
            searchCriteria.LastName  = string.Empty;
            searchCriteria.SSN       = string.Empty;
            searchCriteria.EPN       = string.Empty;
            searchCriteria.Encounter = string.Empty;
            searchCriteria.MaxRecord = 500;
            searchCriteria.MRN       = patientMRN;
            searchCriteria.FacilityCode = patientFacility;
            FindPatientResult result = PatientController.Instance.FindPatient(searchCriteria);

            RequestPatientDetails requestPatientDetails = new RequestPatientDetails();
            if (result.PatientSearchResult.Count == 0)
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.HpfPatientNotFound, patientMRN, patientFacility, requestRefId);
                errorMessage.Append(message);
            }
            return requestPatientDetails;
        }

        /// <summary>
        /// Get the supplemental patient details for the specified supplemental patient reference Id.
        /// </summary>
        /// <param name="patientRefId">Patient Reference Id</param>
        /// <param name="requestPatientDetails">Request Patient Deatils</param>
        private RequestPatientDetails GetPatientDetails(string patientRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreatePatientModule);

            string entity = DataVaultConstants.PatientInfo +"_" + Mode.Create;
            string query = (DataVaultConstants.IsExcelFile)
                          ? "SELECT Count(*) FROM [" + entity + "$] WHERE  Ref_ID = '" + patientRefId + "'"
                          : "SELECT Count(*) FROM " + entity + ".csv WHERE Ref_ID = '" + patientRefId + "'";

            IDataReader reader = Utility.ReadData(EntityName, query);
            reader.Read();
            long count = reader.GetInt64(0);
            reader.Close();
            
            if (DataVaultConstants.IsExcelFile)
            {
                if (ModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestModule);
                }
            }
            if (count == 0)
            {
                errorMessage.Append(NonHpfPatientNotFound + patientRefId );
                return null;
            }
            else
            {
                return new RequestPatientDetails();
            }
        }

        #endregion

        #region HPF Document

        private void GetHpfDocument(string requestRefId, string patientMRN, string patientFacility)
        {
            List<KeyValuePair<string, Operation>> imNetIdList = GetListofImNetsId(requestRefId, patientMRN, patientFacility);
            if (imNetIdList.Count > 0)
            {
               GetHpfDocument(requestRefId, patientMRN, patientFacility, imNetIdList);
            }
        }

        /// <summary>
        /// Get the Global and Encounter Document details.
        /// </summary>
        /// <param name="requestRefId">Request Reference Id.</param>
        /// <param name="patientMRN">Patient MRN.</param>
        /// <param name="patientFacility">Patient Facility.</param>
        /// <param name="patientDetails">Request Patient Details, Where the 
        /// Global and Encounter Deatails are added.</param>
        private string GetHpfDocument(string requestRefId, string patientMRN, string patientFacility, List<KeyValuePair<string, Operation>> imNetIdList)
        {
            log.EnterFunction();

            DbParameter listofImnets = DBDataAccess.CreateParameter(ParamImNets, DbType.Xml, 5000);
            listofImnets.Direction = ParameterDirection.Input;
            listofImnets.Value = CreateXml(imNetIdList);
            log.Debug(listofImnets.Value);

            DbParameter listOfInvalidImnets = DBDataAccess.CreateParameter(ParamInvalidImNets, DbType.Xml, 5000);
            listOfInvalidImnets.Direction = ParameterDirection.Output;

            DbParameter metadata = DBDataAccess.CreateParameter(ParamMetaData, DbType.Xml, 5000);
            metadata.Direction = ParameterDirection.Output;

            DbParameter[] param = new DbParameter[3];
            param[0] = listofImnets;
            param[1] = listOfInvalidImnets;
            param[2] = metadata;

            IDataReader reader = DBDataAccess.ExecuteStoredProcedure(DataVaultConstants.MetadataForImnet, param);
            reader.Close();
            ValidateImnetsId(requestRefId, patientMRN, patientFacility, param);

            log.Debug(metadata.Value);
            return Convert.ToString(metadata.Value, CultureInfo.CurrentCulture);
        }

        /// <summary>
        /// Validate Whether Invalid ImNetId exist.
        /// </summary>
        /// <param name="requestRefId">Request RefId</param>
        /// <param name="patientMRN">Patient Mrn</param>
        /// <param name="patientFacility">Patient Facility</param>
        /// <param name="listOfInvalidImnets">InvalidImnets</param>
        private void ValidateImnetsId(string requestRefId, string patientMRN, string patientFacility, DbParameter[] param)
        {
            log.EnterFunction();
            log.Debug(param[1].Value);
            string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidImNetId, requestRefId, patientMRN, patientFacility);
            if (param[1].Value.ToString().Length != 0)
            {
                errorMessage.AppendLine(message);
                errorMessage.AppendLine("IMNetIds Details" + param[1].Value);
            }
            if (param[1].Value.ToString().Length != 0 && param[2].Value.ToString().Length != 0)
            {
                errorMessage.AppendLine(message);
                errorMessage.AppendLine("IMNetIds Details" + param[3].Value);
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Construct the ImNetId xml format, where the ImNet Id are obtained from the Excel sheet.
        /// </summary>
        /// <param name="requestRefId">Request Request ReferenceID to map the Excel file</param>
        /// <param name="patientMRN">Patient MRN to map the Excel file</param>
        /// <param name="patientFacility">Patient Facility to map the Excel file</param>
        /// <returns>ImNetID xml</returns>
        private string CreateXml(List<KeyValuePair<string, Operation>> imNetIdList)
        {
            log.EnterFunction();
            //Construct the xml tag for the ImNetId provide for the patient.
            StringBuilder imNetsXml = new StringBuilder();
            imNetsXml.Append("<").Append(DataVaultConstants.ContentListTag).Append(">");
            foreach (KeyValuePair<string, Operation> keyValueObject in imNetIdList)
            {                
                imNetsXml.Append("<").Append(DataVaultConstants.ImNetTag).Append(" = ").Append("\"");
                imNetsXml.Append(keyValueObject.Key);
                imNetsXml.Append("\"/>");
            }
            imNetsXml.Append("</").Append(DataVaultConstants.ContentListTag).Append(">");
            return imNetsXml.ToString();
        }

        /// <summary>
        /// Construct the ImNetId xml format, where the ImNet Id are obtained from the Excel sheet.
        /// </summary>
        /// <param name="requestRefId">Request Request ReferenceID to map the Excel file</param>
        /// <param name="patientMRN">Patient MRN to map the Excel file</param>
        /// <param name="patientFacility">Patient Facility to map the Excel file</param>
        /// <returns>ImNetID xml</returns>
        private List<KeyValuePair<string, Operation>> GetListofImNetsId(string requestRefId, string patientMRN, string patientFacility)
        {            
            log.EnterFunction();
            string query;

            List<KeyValuePair<string, Operation>> imNetIDCollection = new List<KeyValuePair<string, Operation>>();
            query = (DataVaultConstants.IsExcelFile)
                     ? string.Format(CultureInfo.CurrentCulture, ExcelHpfPatient, DataVaultConstants.RequestItemDetails + "_" + ModeType, patientMRN, patientFacility, requestRefId)
                     : string.Format(CultureInfo.CurrentCulture, CsvHpfPatient, DataVaultConstants.RequestItemDetails + "_" + ModeType, patientMRN, patientFacility, requestRefId);

            //Construct the xml tag for the ImNetId provide for the patient.
            using (IDataReader globalDocReader = Utility.ReadData(DataVaultConstants.RequestItemDetails + "_" + ModeType, query))
            {
                while (globalDocReader.Read())
                {                    
                    string docID = Convert.ToString(globalDocReader[PageImNetId], CultureInfo.CurrentCulture).ToString();
                    Operation type = Operation.Add;
                    if (ModeType == VaultMode.Update)
                    {
                        type = (Operation)Enum.Parse(typeof(Operation),
                                Convert.ToString(globalDocReader[DataVaultConstants.Operation], CultureInfo.CurrentCulture).Trim(),
                                true);
                    }
                    KeyValuePair<string, Operation> keyValueObject = new KeyValuePair<string, Operation>(docID, type);                    
                    imNetIDCollection.Add(keyValueObject);                    
                }
                if (!globalDocReader.IsClosed)
                {
                    globalDocReader.Close();
                }
            }
            return imNetIDCollection;
        }


        #endregion

        #region Supplemental Document

        /// <summary>
        /// Retrive the selected Supplemental Document for the Patient Selected
        /// </summary>
        /// <param name="requestRefId"></param>
        /// <param name="requestDetails"></param>
        private void GetSupplementalDocument(string requestRefId, string patientRefId, RequestPatientDetails requestPatientDetails)
        {
            log.EnterFunction();
            string query = string.Empty;
            if (requestPatientDetails.IsHpf)
            {
                query = (DataVaultConstants.IsExcelFile)
                         ? string.Format(CultureInfo.CurrentCulture, ExcelHpfPatient, DataVaultConstants.RequestSupDocs + "_" + ModeType, requestPatientDetails.MRN, requestPatientDetails.FacilityCode, requestRefId)
                         : string.Format(CultureInfo.CurrentCulture, CsvHpfPatient, DataVaultConstants.RequestSupDocs + "_" + ModeType, requestPatientDetails.MRN, requestPatientDetails.FacilityCode, requestRefId);
            }
            else
            {
                query = (DataVaultConstants.IsExcelFile)
                         ? string.Format(CultureInfo.CurrentCulture, ExcelSupPatient, DataVaultConstants.RequestSupDocs + "_" + ModeType, patientRefId, requestRefId)
                         : string.Format(CultureInfo.CurrentCulture, CsvSupPatient, DataVaultConstants.RequestSupDocs + "_" + ModeType, patientRefId, requestRefId);

            }
            using (IDataReader reader = Utility.ReadData(DataVaultConstants.RequestSupDocs + "_" + ModeType, query))
            {
                while (reader.Read())
                {
                    string nonHpfDocRefID        = Convert.ToString(reader[NonHpfDocRefId], CultureInfo.CurrentCulture);
                    string patientId             = Convert.ToString(reader[PatientRefId], CultureInfo.CurrentCulture);
                    string patientMrn            = Convert.ToString(reader[RequestPatientMrn], CultureInfo.CurrentCulture);
                    string patientFacility       = Convert.ToString(reader[RequestPatientFacility], CultureInfo.CurrentCulture);
                    string nonHpfBillingTierId   = Convert.ToString(reader[NonHpfBillingTier], CultureInfo.CurrentCulture);
                    ValidateNonHpfDocument(nonHpfDocRefID, requestPatientDetails.IsHpf, patientId, patientMrn, patientFacility);
                    if (nonHpfBillingTierId.Length != 0)
                    {
                        ValidateNonHpfBillingTier(nonHpfBillingTierId);
                    }                  
                }
                if (!reader.IsClosed)
                {
                    reader.Close();
                }
            }
            
        }

        private void ValidateNonHpfBillingTier(string nonHpfBillingTierId)
        {
            if (ModeType == VaultMode.Create)
            {
                if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            }           
            string entity = DataVaultConstants.BillingTier + "_" + ModeType;
            string query  = (DataVaultConstants.IsExcelFile)
                              ? "SELECT COUNT(*) FROM [" + entity + "$] WHERE  Ref_ID ='" + nonHpfBillingTierId + "'"
                              : "SELECT COUNT(*) FROM " + entity + ".csv WHERE  Ref_ID ='" + nonHpfBillingTierId + "'";
            IDataReader nonHPfBillingTierReader = Utility.ReadData(entity, query);
            nonHPfBillingTierReader.Read();
            long count = Convert.ToInt64(nonHPfBillingTierReader[0], CultureInfo.CurrentCulture);
            nonHPfBillingTierReader.Close();

            if (count == 0)
            {
                string message = "BillingTier=" + nonHpfBillingTierId + " not found" + "in the entity " + entity;
                errorMessage.AppendLine(message);
            }

            if (DataVaultConstants.IsExcelFile)
            {
                if (ModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestModule);
                }
            }
        }

        private void ValidateNonHpfDocument(string nonHpfDocRefID, bool isHPF, string patientRefId, string patientMrn, string patientFacility)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreatePatientModule);
            string entity = DataVaultConstants.NonHpfDocument + "_" + Mode.Create;
            string query;
            if (isHPF)
            {
                query = (DataVaultConstants.IsExcelFile)
                              ? "SELECT COUNT(*) FROM [" + entity + "$] WHERE  Ref_ID ='" + nonHpfDocRefID + "' AND Pt_MRN = '"+patientMrn+"' AND Pt_Facility_Code = '"+patientFacility+"'"
                              : "SELECT COUNT(*) FROM " + entity + ".csv WHERE  Ref_ID ='" + nonHpfDocRefID + "' AND Pt_MRN = '"+patientMrn+"' AND Pt_Facility_Code = '"+patientFacility+"'";
            }
            else
            {
                query = (DataVaultConstants.IsExcelFile)
                              ? "SELECT COUNT(*) FROM [" + entity + "$] WHERE  Ref_ID ='" + nonHpfDocRefID + "' AND SupRef_ID = '" + patientRefId + "'"
                              : "SELECT COUNT(*) FROM " + entity + ".csv WHERE  Ref_ID ='" + nonHpfDocRefID + "' AND SupRef_ID = '" + patientRefId + "'";
            }
            IDataReader nonHPfDocReader = Utility.ReadData(entity, query);
            nonHPfDocReader.Read();
            long count = Convert.ToInt64(nonHPfDocReader[0], CultureInfo.CurrentCulture);
            nonHPfDocReader.Close();

            if (count == 0)
            {
                errorMessage.AppendLine("Non HPf doucment not found. NonHpf Doc Referecne ID=" + nonHpfDocRefID
                                                                                           +" Patient Mrn="+patientMrn
                                                                                           + ", Patient Facility="+ patientFacility
                                                                                           +", Patient Reference ID ="+ patientRefId);
            }

            
            if (DataVaultConstants.IsExcelFile)
            {
                if (ModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestModule);
                }
            }
        }

        #endregion

        #endregion

        #region Properties

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get { return DataVaultConstants.RequestPtsInfo; }
        }

        /// <summary>
        /// Return the mode type.
        /// </summary>
        public VaultMode ModeType
        {
            get
            {
                return modeType;
            }
            set
            {
                modeType = value;
            }
        }

        #endregion
    }
}
