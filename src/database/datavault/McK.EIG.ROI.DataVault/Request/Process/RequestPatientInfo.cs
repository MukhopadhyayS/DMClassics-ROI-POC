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
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;

using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.DataVault.Patient.Process;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.DataVault.Admin.Process;

namespace McK.EIG.ROI.DataVault.Request.Process
{
    /// <summary>
    /// Class inserts the requestor information.
    /// </summary>
    public partial class RequestPatientInfo : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(RequestPatientInfo));

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

        //Query to get Document.
        private static string CSVDocQuery = "SELECT * FROM {0}.csv";
        private static string CsvHpfPatient = CSVDocQuery + " WHERE " + RequestPatientMrn + "= '{1}' AND " + RequestPatientFacility + " ='{2}' AND " + RequestRefId + "='{3}'";
        private static string CsvSupPatient = CSVDocQuery + " WHERE " + PatientRefId + " = '{1}' AND " + RequestRefId + " ='{2}'";

        private static string ExcelDocQuery = "SELECT * FROM [{0}$]";
        private static string ExcelHpfPatient = ExcelDocQuery + " WHERE " + RequestPatientMrn + " = '{1}' AND " + RequestPatientFacility + " = '{2}' AND " + RequestRefId + " = '{3}'";
        private static string ExcelSupPatient = ExcelDocQuery + " WHERE " + PatientRefId + " = '{1}' AND " + RequestRefId + "='{2}'";

        //Parameters
        private const string ParamImNets = "@ListOfImnets";
        private const string ParamInvalidImNets = "@ListOfInvalidImnets";
        private const string ParamMetaData = "@Metadata";

        private PatientDetails patientDetails;

        private KeyValuePair<string, Operation> imNetIDOpearationType;

        private VaultMode modeType;

        private int releaseCount;

        #endregion

        #region Method

        /// <summary>
        /// Retrive the Patient Info Details for the specified Request RefId.
        /// Process: Create Request.
        /// </summary>
        /// <param name="requestRefId">Requestor Reference Id.</param>
        /// <param name="requestDetails">Request Details.</param>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();

            long recordCount;

            try
            {
                recordCount = 1;
                RequestDetails requestDetails;
                
                while (reader.Read())
                {                    
                    //Refernce Id
                    string requestRefId = Convert.ToString(reader[RequestRefId], CultureInfo.CurrentCulture).Trim();                                        
                    long requestId      = Convert.ToInt64(RequestVault.GetEntityObject(DataVaultConstants.RequestInfo, requestRefId), CultureInfo.CurrentCulture);
                    
                    requestDetails = GetRequestInfo(requestId);
                    requestDetails.Requestor = GetRequestorInfo(requestDetails.Requestor.Id);
                    RequestPatientDetails requestPatientDetails;

                    string patientRefId    = Convert.ToString(reader[PatientRefId], CultureInfo.CurrentCulture).Trim();
                    string patientMRN      = Convert.ToString(reader[RequestPatientMrn], CultureInfo.CurrentCulture).Trim();
                    string patientFacility = Convert.ToString(reader[RequestPatientFacility], CultureInfo.CurrentCulture).Trim();

                    requestPatientDetails = null;

                    if (patientRefId.Length != 0 && patientRefId != "0")
                    {
                        requestPatientDetails = GetPatientDetails(patientRefId);                        
                    }
                    else if (patientMRN.Length != 0 && patientFacility.Length != 0)
                    {
                        requestPatientDetails = GetPatientDetails(requestRefId, patientMRN, patientFacility);
                    }
                    else
                    {
                        string errorMeg = string.Format(CultureInfo.CurrentCulture, "Invalid Patient RefId="+patientRefId+" MRN="+patientMRN+" Facility="+patientFacility);
                        log.Debug(errorMeg);
                        throw new VaultException(errorMeg);
                    }


                    if (modeType == VaultMode.Create)
                    {
                        CreateRequestPatient(requestRefId, requestDetails, requestPatientDetails, patientRefId);
                    }
                    if(modeType == VaultMode.Update)
                    {
                        Operation operationType = (Operation)Enum.Parse(typeof(Operation),
                                                    Convert.ToString(reader[DataVaultConstants.Operation], CultureInfo.CurrentCulture).Trim(),
                                                    true);                        
                        switch (operationType)
                        {
                            case Operation.Add:
                                CreateRequestPatient(requestRefId, requestDetails, requestPatientDetails, patientRefId);
                                break;
                            case Operation.Modify:
                                UpdateRequestPatient(requestRefId, requestDetails, requestDetails.Patients[requestPatientDetails.Key], patientRefId);
                                break;
                            case Operation.Delete:
                                RemoveRequestPatient(requestRefId, requestDetails, requestPatientDetails, patientRefId);
                                break;                            
                        }
                    }                                         
                    requestDetails = UpdateRequestDetails(requestDetails, recordCount);
                    recordCount++;
                }
                log.ExitFunction();
                return null;
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.OdbcError);
            }
            catch (InvalidCastException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            catch (ArgumentException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            catch (Exception cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(cause.Message);
            }
            finally
            {
                reader.Close();
            }
        }

        private void RemoveRequestPatient(string requestRefId, RequestDetails requestDetails, RequestPatientDetails requestPatientDetails, string patientRefId)
        {
            if (requestDetails.Patients.ContainsKey(requestPatientDetails.Key))
            {
                RequestPatientDetails patientDetails = requestDetails.Patients[requestPatientDetails.Key];
                if (patientDetails.IsReleased)
                {
                    string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.RoiPatientCannotbeRemoved, patientRefId, requestRefId);
                    log.Debug(message);
                    throw new VaultException(message);
                }
                else
                {
                    requestDetails.Patients.Remove(requestPatientDetails.Key);
                }
            }
            else
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.RoiPatientNotforRequest, patientRefId, requestRefId);
                log.Debug(message);
                throw new VaultException(message);
            }            
        }

        private void UpdateRequestPatient(string requestRefId, RequestDetails requestDetails, RequestPatientDetails requestPatientDetails, string patientRefId)
        {
            if (requestDetails.Patients.ContainsKey(requestPatientDetails.Key))
            {
                RequestPatientDetails patientDetails = requestDetails.Patients[requestPatientDetails.Key];                
                patientDetails.DOB                = requestPatientDetails.DOB;
                patientDetails.EncounterLocked    = requestPatientDetails.EncounterLocked;
                patientDetails.EPN                = requestPatientDetails.EPN;
                patientDetails.FacilityCode       = requestPatientDetails.FacilityCode;
                patientDetails.FullName           = requestPatientDetails.FullName;
                patientDetails.Gender             = requestPatientDetails.Gender;
                patientDetails.GlobalDocument     = requestPatientDetails.GlobalDocument;
                patientDetails.Id                 = requestPatientDetails.Id;
                patientDetails.IsHpf              = requestPatientDetails.IsHpf;
                patientDetails.IsLockedPatient    = requestPatientDetails.IsLockedPatient;
                patientDetails.IsReleased         = requestPatientDetails.IsReleased;
                patientDetails.IsVip              = requestPatientDetails.IsVip;
                patientDetails.MRN                = requestPatientDetails.MRN;
                patientDetails.NonHpfDocument     = requestPatientDetails.NonHpfDocument;
                patientDetails.RecordVersionId    = requestPatientDetails.RecordVersionId;
                patientDetails.SelectedForRelease = requestPatientDetails.SelectedForRelease;
                patientDetails.SSN                = requestPatientDetails.SSN;

                if (requestPatientDetails.IsHpf)
                {
                    GetHpfDocument(requestRefId, patientDetails.MRN, patientDetails.FacilityCode, requestPatientDetails);
                }
                GetSupplementalDocument(requestRefId, patientRefId, requestPatientDetails);
            }
            else
            {
                string errorMeg = string.Format(CultureInfo.CurrentCulture, "Patient Not found for Update.", requestRefId, EntityName + "_" + modeType);
                log.Debug(errorMeg);
                throw new VaultException(errorMeg);
            }
            
        }

        private void CreateRequestPatient(string requestRefId, RequestDetails requestDetails, RequestPatientDetails requestPatientDetails, string patientRefId)
        {
            if (requestPatientDetails.IsHpf)
            {
                GetHpfDocument(requestRefId, requestPatientDetails.MRN, requestPatientDetails.FacilityCode, requestPatientDetails);
            }
            GetSupplementalDocument(requestRefId, patientRefId, requestPatientDetails);
            requestDetails.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
        }       
            
        /// <summary>
        /// Passes the Request information object to the RequestorController for further process
        /// </summary>
        /// <param name="requestDetails">Requestor Info Details.</param>        
        private RequestDetails UpdateRequestDetails(RequestDetails requestDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                      DataVaultConstants.ProcessStartTag,
                                      recordCount,
                                      DateTime.Now));

                //Call the RequestorController to save the RequestorInformation.
                requestDetails = RequestController.Instance.UpdateRequest(requestDetails);

                log.Debug(DataVaultConstants.ProcessEndTag);
                log.ExitFunction();
                return requestDetails;
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
        }
        
        /// <summary>
        /// Retrive the request for the given request id, this is invoked
        /// during update process of request.
        /// </summary>
        /// <param name="requestId"></param>
        /// <returns></returns>
        private RequestDetails RetriveRequest(long requestId)
        {
            log.EnterFunction();
            try
            {   
                return RequestController.Instance.RetrieveRequest(requestId);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
        }      

        private void UpdateRequestPatientDetails(RequestPatientDetails requestPatientDetails, RequestPatientDetails recordPatient)
        {
            log.EnterFunction();
            requestPatientDetails.Id                 = recordPatient.Id;
            requestPatientDetails.FullName           = recordPatient.FullName;
            requestPatientDetails.Gender             = recordPatient.Gender;
            requestPatientDetails.MRN                = recordPatient.MRN;
            requestPatientDetails.EPN                = recordPatient.EPN;
            requestPatientDetails.SSN                = recordPatient.SSN;
            requestPatientDetails.DOB                = recordPatient.DOB;
            requestPatientDetails.FacilityCode       = recordPatient.FacilityCode;
            requestPatientDetails.IsHpf              = recordPatient.IsHpf;
            requestPatientDetails.IsVip              = recordPatient.IsVip;
            requestPatientDetails.IsLockedPatient    = recordPatient.IsLockedPatient;
            //requestPatientDetails.HasLockedEncounter = recordPatient.HasLockedEncounter;
            log.ExitFunction();
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
            patientDetails = PatientController.Instance.RetrieveHpfPatient(patientMRN, patientFacility);
            patientDetails = PatientController.Instance.RetrieveSupplementalDocuments(patientDetails);

            if (patientDetails != null)
            {
                RequestPatientDetails requestPatientDetails = new RequestPatientDetails(patientDetails);
                return requestPatientDetails;
            }
            else
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.HpfPatientNotFound,patientMRN, patientFacility, requestRefId);
                log.Debug(message);
                throw new VaultException(message);
            }
        }

        /// <summary>
        /// Get the supplemental patient details for the specified supplemental patient reference Id.
        /// </summary>
        /// <param name="patientRefId">Patient Reference Id</param>
        /// <param name="requestPatientDetails">Request Patient Deatils</param>
        private RequestPatientDetails GetPatientDetails(string patientRefId)
        {
            log.EnterFunction();
            try
            {
                long patientId = Convert.ToInt64(PatientVault.GetEntityObject(patientRefId, DataVaultConstants.PatientInfo), CultureInfo.CurrentCulture);
                patientDetails = PatientController.Instance.RetrieveSupplementalInfo(patientId);                
                RequestPatientDetails requestPatientDetails = new RequestPatientDetails(patientDetails);
                return requestPatientDetails;

            }
            catch (ROIException cause)
            {
                log.Debug(cause);
                throw new VaultException(cause.Message, cause);
            }
        }

        #endregion

        #region HPF Document

        private void GetHpfDocument(string requestRefId, string patientMRN, string patientFacility, RequestPatientDetails requestPatientDetails)
        {
            List<KeyValuePair<string, Operation>> imNetIdList = GetListofImNetsId(requestRefId, patientMRN, patientFacility);
            if (imNetIdList.Count > 0)
            {
                string metadata = GetHpfDocument(requestRefId, patientMRN, patientFacility, imNetIdList);
                if (metadata != null)
                {
                    XmlDocument doc = new XmlDocument();
                    doc.LoadXml(metadata.Trim());
                    //Collection of Encounter Level Node.
                    XmlNodeList list = doc.GetElementsByTagName(DataVaultConstants.EncounterDomainType);
                    if (list.Count > 0)
                    {
                        foreach (XmlNode node in list)
                        {
                            ParseXml(node, requestPatientDetails, imNetIdList);
                        }
                    }
                }
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
            ValidateImnetsId(requestRefId, patientMRN, patientFacility, listOfInvalidImnets);

            log.Debug(metadata.Value);
            return Convert.ToString(metadata.Value, CultureInfo.CurrentCulture);
        }

       
        /// <summary>
        /// /// Get Encounter details info from the xmlElement and append to the patient detils.
        /// </summary>
        /// <param name="xmlElement">XmlElement Node.</param>
        /// <param name="patientDetails">RequestPatientDetails.</param>
        private void ParseXml(IXPathNavigable xmlpath, RequestPatientDetails requestPatientDetails, List<KeyValuePair<string, Operation>> imNetIdList)
        {
            log.EnterFunction();

            //Validate xml element has value.
            if (xmlpath == null)
            {
                return;
            }
            //Element Contains the Encounter Level Deails
            XmlElement xmlElement = (XmlElement)xmlpath;
            //Collection of ContentMetaData Node.
            XmlNodeList docList   = xmlElement.GetElementsByTagName(DataVaultConstants.ContentMetaData);

            string encounterId = ValidateAtrribute(xmlElement, DataVaultConstants.EncounterId);

            //if encounter Id length is zero than it is Global document.
            if (encounterId.Length > 0)
            {
                RequestEncounterDetails encounterDetails;

                RequestEncounterDetails encounter = GetEncounterDeatils(xmlElement);
                encounterDetails = (requestPatientDetails.GetChild(encounter.Key) == null)
                                    ? encounter
                                    : requestPatientDetails.GetChild(encounter.Key) as RequestEncounterDetails;
                if (requestPatientDetails.GetChild(encounter.Key) == null)
                {
                    requestPatientDetails.AddChild(encounterDetails);
                }
                foreach (XmlNode node in docList)
                {
                    //Encounter Level Document is added as Child for RequestEncounterDetails.
                    GetDocumentDetails(node, encounterDetails, imNetIdList);
                }
            }
            else
            {
                foreach (XmlNode node in docList)
                {
                    //Global Document is added as Child for RequestPatientDeatils.
                    GetGlobalDocumentDetails(node, requestPatientDetails, imNetIdList);
                }
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Retrieve the encounter details from the XML Node.
        /// </summary>
        /// <param name="node">XML Element node containing the encounter details.</param>
        /// <returns>EncounterDetails.</returns>
        private RequestEncounterDetails GetEncounterDeatils(XmlNode node)
        {
            log.EnterFunction();
            EncounterDetails encounter = new EncounterDetails();
            encounter.EncounterId      = node.Attributes[DataVaultConstants.EncounterId].Value.Trim();
            encounter.Facility         = ValidateAtrribute(node, DataVaultConstants.EncFacility);
            encounter.PatientType      = ValidateAtrribute(node, DataVaultConstants.EncPatientType);
            encounter.IsLocked         = (ValidateAtrribute(node, DataVaultConstants.EncIsLocked) == "N") ? false : true;
            //encounter.IsDeficiency     = GetDeficiency(node);
            //encounter.PatientStatus    = ValidateAtrribute(node, DataVaultConstants.EncPatientStatus);            
            encounter.HasDeficiency    = GetDeficiency(node);
            encounter.PatientService   = ValidateAtrribute(node, DataVaultConstants.EncPatientStatus);            
            encounter.IsVip            = (ValidateAtrribute(node, DataVaultConstants.EncIsVip).Length > 0);            
            //encounterDetails.CreatedDate    = (ValidateAtrribute(node, DataVaultConstants);

            string dateOfService = ValidateAtrribute(node, DataVaultConstants.EncDateofService);                                              
            if (dateOfService.Length > 0)
            {
                encounter.AdmitDate = Convert.ToDateTime(dateOfService, CultureInfo.CurrentCulture);
            }
            string dischargeDate = ValidateAtrribute(node, DataVaultConstants.EncDisChargeDate);
            if (dischargeDate.Length > 0)
            {
                encounter.DischargeDate = Convert.ToDateTime(dischargeDate, CultureInfo.CurrentCulture);
            }
            log.ExitFunction();
            return new RequestEncounterDetails(encounter);
        }

        /// <summary>
        /// Check Dificiency Exit for the encounter.
        /// </summary>
        /// <param name="node">XML Element node containing the encounter details.</param>
        /// <returns>Returns true if the Deficiency Count Greater than zero.</returns>
        private bool GetDeficiency(XmlNode node)
        {
            log.EnterFunction();
            if (node.HasChildNodes)
            {
                XmlNodeList list = node.ChildNodes;
                if (ValidateAtrribute(list[0], DataVaultConstants.IsDeficiency) != "0")
                {
                    log.ExitFunction();
                    return true;
                }
            }
            log.ExitFunction();
            return false;
        }

        /// <summary>
        /// Get the Request Document Deatils info for the selected encounter.
        /// </summary>
        /// <param name="node">XML Node containing the Content MetaData</param>
        /// <param name="RequestEncounterDetails">RequestEncounterDetails </param>
        private void GetDocumentDetails(XmlNode node, RequestEncounterDetails encounterDetails, List<KeyValuePair<string, Operation>> imNetIdList)
        {
            log.EnterFunction();

            RequestDocumentDetails docDetails;
            RequestDocumentDetails document = GetDocumentDetails(node);
            docDetails = (encounterDetails.GetChild(document.Key) == null)
                          ? document
                          : encounterDetails.GetChild(document.Key) as RequestDocumentDetails;
            if (encounterDetails.GetChild(document.Key) == null)
            {
                encounterDetails.AddChild(docDetails);
            }
            GetVersionDetails(node, docDetails, imNetIdList);           
            log.ExitFunction();
        }

        /// <summary>
        /// Get the Request Document Deatils info for the golbal document.
        /// </summary>
        /// <param name="node">XML Node containing the Content MetaData</param>        
        /// <param name="RequestPatientDetails">RequestPatientDetails</param>
        private void GetGlobalDocumentDetails(XmlNode node, RequestPatientDetails requestPatientDetails, List<KeyValuePair<string, Operation>> imNetIdList)
        {
            log.EnterFunction();
            RequestGlobalDocuments globalDocDetails = (requestPatientDetails.GetChild(ROIConstants.GlobalDocument) == null)
                                                       ? new RequestGlobalDocuments()
                                                       : requestPatientDetails.GetChild(ROIConstants.GlobalDocument) as RequestGlobalDocuments;

            RequestDocumentDetails childDocDetails;
            RequestDocumentDetails document = GetDocumentDetails(node);
            childDocDetails = (globalDocDetails.GetChild(document.Key) == null)
                                ? document
                                : globalDocDetails.GetChild(document.Key) as RequestDocumentDetails;
            if (globalDocDetails.GetChild(document.Key) == null)
            {
                requestPatientDetails.AddChild(globalDocDetails);
            }
            GetVersionDetails(node, childDocDetails, imNetIdList);

            log.ExitFunction();
        }


        /// <summary>
        /// Returnt the RequestDocument Details Info.
        /// </summary>
        /// <param name="node">XML Node containing the Content MetaData</param>
        /// <returns>RequestDocumentDetails</returns>
        private RequestDocumentDetails GetDocumentDetails(XmlNode node)
        {
            log.EnterFunction();
            RequestDocumentDetails docDetails = new RequestDocumentDetails();
            docDetails.Subtitle   = ValidateAtrribute(node, DataVaultConstants.Subtitle);                                    
            //docDetails.DocId      = ValidateAtrribute(node, DataVaultConstants.Subtitle);
            docDetails.DocType    = ValidateAtrribute(node, DataVaultConstants.DocType);
            docDetails.ChartOrder = ValidateAtrribute(node, DataVaultConstants.ChartOrder);

            string dobService = ValidateAtrribute(node, DataVaultConstants.EncDateofService);
            if (dobService.Length > 0)
            {
                docDetails.DateOfService = Convert.ToDateTime(dobService, CultureInfo.CurrentCulture);
            }
            if (ValidateAtrribute(node, DataVaultConstants.IsDeficiency) != "0")
            {
                docDetails.IsDeficient = true;
            }
            log.ExitFunction();
            return docDetails;
        }

        /// <summary>
        /// Get the Request Version Deatils info from the selected Node.
        /// </summary>
        /// <param name="node">XML Node containing the Content MetaData</param>        
        private void GetVersionDetails(XmlNode node, RequestDocumentDetails docDetails, List<KeyValuePair<string, Operation>> imNetIdList)
        {
            log.EnterFunction();

            RequestVersionDetails versionDetails;

            RequestVersionDetails version = new RequestVersionDetails();
            version.VersionNumber = ValidateAtrribute(node, DataVaultConstants.VersionNumber);

            versionDetails = (docDetails.GetChild(version.Key) == null)
                               ? version
                               : docDetails.GetChild(version.Key) as RequestVersionDetails;
            if (docDetails.GetChild(version.Key) == null)
            {
                docDetails.AddChild(versionDetails);
            }
            GetPageDeatils(node, versionDetails, imNetIdList);
           
            log.ExitFunction();
        }

        /// <summary>
        /// Get the Request Page Deatils info from the selected Node.
        /// </summary>
        /// <param name="node">XML Node containing the Content MetaData</param>        
        private void GetPageDeatils(XmlNode node, RequestVersionDetails versionDetails, List<KeyValuePair<string, Operation>> imNetIdList)
        {
            log.EnterFunction();
            RequestPageDetails pageDetails = new RequestPageDetails();

            string pagenumber   = ValidateAtrribute(node, DataVaultConstants.PageNumber);
            string contentCount = ValidateAtrribute(node, DataVaultConstants.PageContentCount);

            pageDetails.IMNetId      = ValidateAtrribute(node, DataVaultConstants.PageImNet);
            pageDetails.PageNumber   = (pagenumber.Length > 0) ? Convert.ToInt32(pagenumber, CultureInfo.CurrentCulture) : 0;
            pageDetails.ContentCount = (contentCount.Length > 0)? Convert.ToInt32(node.Attributes[DataVaultConstants.PageContentCount].Value.Trim(), CultureInfo.CurrentCulture): 0;
            pageDetails.SelectedForRelease = true;
            pageDetails.IsReleased = false;
            
            imNetIDOpearationType = imNetIdList.Find(delegate(KeyValuePair<string, Operation> item) { return pageDetails.IMNetId == item.Key; });

            RequestPageDetails page = versionDetails.GetChild(pageDetails.Key) as RequestPageDetails;

            if (imNetIDOpearationType.Value == Operation.Delete)
            {
                if (page.IsReleased)
                {
                    string message = "Page is already Released. The IMNetId is" + page.IMNetId;
                    log.Debug(message);
                    throw new VaultException(message);
                }
                else
                {   
                    versionDetails.RemoveChild(pageDetails);
                }
            }
            else
            {   
                if (page == null)
                {
                    //pageDetailsHT.Add(versionDetails.Key, pageDetails.IMNetId);
                    versionDetails.AddChild(pageDetails);
                }
                else
                {
                    log.Debug("ImNet Id already exist " + pageDetails.IMNetId);
                    throw new VaultException("ImNet Id already exist " + pageDetails.IMNetId);
                }
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Validate Whether Invalid ImNetId exist.
        /// </summary>
        /// <param name="requestRefId">Request RefId</param>
        /// <param name="patientMRN">Patient Mrn</param>
        /// <param name="patientFacility">Patient Facility</param>
        /// <param name="listOfInvalidImnets">InvalidImnets</param>
        private void ValidateImnetsId(string requestRefId, string patientMRN, string patientFacility, DbParameter listOfInvalidImnets)
        {
            log.EnterFunction();
            log.Debug(listOfInvalidImnets.Value);
            if (listOfInvalidImnets.Value == null)
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidImNetId, requestRefId, patientMRN, patientFacility);
                log.Debug(message);
                throw new VaultException(message);
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

            if (modeType == VaultMode.Create)
            {
                query = (DataVaultConstants.IsExcelFile)
                         ? string.Format(CultureInfo.CurrentCulture, ExcelHpfPatient, DataVaultConstants.RequestItemDetails + "_" + modeType, patientMRN, patientFacility, requestRefId)
                         : string.Format(CultureInfo.CurrentCulture, CsvHpfPatient, DataVaultConstants.RequestItemDetails + "_" + modeType, patientMRN, patientFacility, requestRefId);
            }
            else
            {
                query = (DataVaultConstants.IsExcelFile)
                         ? string.Format(CultureInfo.CurrentCulture, ExcelHpfPatient, DataVaultConstants.RequestItemDetails + "_" + modeType, patientMRN, patientFacility, requestRefId)
                         : string.Format(CultureInfo.CurrentCulture, CsvHpfPatient, DataVaultConstants.RequestItemDetails + "_" + modeType, patientMRN, patientFacility, requestRefId);
                query = query + " AND Release_Counter = " + releaseCount;
            }
            

            //Construct the xml tag for the ImNetId provide for the patient.
            using (IDataReader globalDocReader = Utility.ReadData(DataVaultConstants.RequestItemDetails + "_" + modeType, query))
            {
                while (globalDocReader.Read())
                {                    
                    string docID = Convert.ToString(globalDocReader[PageImNetId], CultureInfo.CurrentCulture).ToString();
                    Operation type = Operation.Add;
                    if (modeType == VaultMode.Update)
                    {
                        type = (Operation)Enum.Parse(typeof(Operation),
                                Convert.ToString(globalDocReader[DataVaultConstants.Operation], CultureInfo.CurrentCulture).Trim(),
                                true);
                    }
                    KeyValuePair<string, Operation> keyValueObject = new KeyValuePair<string, Operation>(docID, type);                    
                    imNetIDCollection.Add(keyValueObject);
                    
                }                
            }
            return imNetIDCollection;
        }

        private static string ValidateAtrribute(IXPathNavigable xpath, string attributeName)
        {
            XmlElement element = (XmlElement)xpath;
            if (element != null)
            {
                if (element.HasAttribute(attributeName))
                {
                    return element.Attributes[attributeName].Value.Trim();
                }
            }
            return string.Empty;
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
                if (modeType == VaultMode.Create)
                {
                    query = (DataVaultConstants.IsExcelFile)
                             ? string.Format(CultureInfo.CurrentCulture, ExcelHpfPatient, DataVaultConstants.RequestSupDocs + "_" + modeType, requestPatientDetails.MRN, requestPatientDetails.FacilityCode, requestRefId)
                             : string.Format(CultureInfo.CurrentCulture, CsvHpfPatient, DataVaultConstants.RequestSupDocs + "_" + modeType, requestPatientDetails.MRN, requestPatientDetails.FacilityCode, requestRefId);
                }
                else
                {
                    query = (DataVaultConstants.IsExcelFile)
                             ? string.Format(CultureInfo.CurrentCulture, ExcelHpfPatient, DataVaultConstants.RequestSupDocs + "_" + modeType, requestPatientDetails.MRN, requestPatientDetails.FacilityCode, requestRefId)
                             : string.Format(CultureInfo.CurrentCulture, CsvHpfPatient, DataVaultConstants.RequestSupDocs + "_" + modeType, requestPatientDetails.MRN, requestPatientDetails.FacilityCode, requestRefId);
                    query = query + " AND Release_Counter = " + releaseCount;
                }
            }
            else
            {
                if (modeType == VaultMode.Create)
                {
                    query = (DataVaultConstants.IsExcelFile)
                             ? string.Format(CultureInfo.CurrentCulture, ExcelSupPatient, DataVaultConstants.RequestSupDocs + "_" + modeType, patientRefId, requestRefId)
                             : string.Format(CultureInfo.CurrentCulture, CsvSupPatient, DataVaultConstants.RequestSupDocs + "_" + modeType, patientRefId, requestRefId);
                }
                else
                {
                    query = (DataVaultConstants.IsExcelFile)
                             ? string.Format(CultureInfo.CurrentCulture, ExcelSupPatient, DataVaultConstants.RequestSupDocs + "_" + modeType, patientRefId, requestRefId)
                             : string.Format(CultureInfo.CurrentCulture, CsvSupPatient, DataVaultConstants.RequestSupDocs + "_" + modeType, patientRefId, requestRefId);
                    query = query + " AND Release_Counter = " + releaseCount;
                }
            }
            using (IDataReader reader = Utility.ReadData(DataVaultConstants.RequestSupDocs + "_" + modeType, query))
            {
                while (reader.Read())
                {
                    string nonHpfDocRefID = Convert.ToString(reader[NonHpfDocRefId], CultureInfo.CurrentCulture);
                    string nonHpfBillingTierId = Convert.ToString(reader[NonHpfBillingTier], CultureInfo.CurrentCulture);
                    Operation operationType;
                    if (modeType == VaultMode.Create)
                    {
                        operationType = Operation.Add;
                    }
                    else
                    {
                        operationType = (Operation)Enum.Parse(typeof(Operation),
                                         Convert.ToString(reader[DataVaultConstants.Operation], CultureInfo.CurrentCulture).Trim(),
                                         true);
                    }
                    GetSupDocumentDetails(nonHpfDocRefID, requestPatientDetails, operationType, nonHpfBillingTierId);                
                }
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Get the NonHpf Document details for the specified NonHpf document Id
        /// </summary>
        /// <param name="nonHPfDocRefId">NonHpf Document Reference Id</param>
        /// <param name="patientDetails">PatientDetails to append the NonHpf Document details.</param>
        private void GetSupDocumentDetails(string nonHPfDocRefId, RequestPatientDetails requestPatientDetails, Operation operationType, string nonHpfBillingTierId)
        {
            log.EnterFunction();
            string key = Convert.ToString(PatientVault.GetEntityObject(nonHPfDocRefId, DataVaultConstants.NonHpfDocument), CultureInfo.CurrentCulture);
            //Get the NonHpfDocumentDetails from the patientDetails object.
            //if (!patientDetails.NonHpfDocuments.ChildrenKeys.Contains(key))
            //{
            //    string message = "NonHpf Document '"+nonHPfDocRefId+"' not associated with the patient Mrn='"+requestPatientDetails.MRN+"' ,Facility='"+requestPatientDetails.FacilityCode;
            //    log.Debug(message);
            //    throw new VaultException(message);
            //}

            NonHpfDocumentDetails nonHpfDocDetails = new NonHpfDocumentDetails();
            bool isKeyExists = false;
            foreach (NonHpfEncounterDetails nonHpfEncounter in patientDetails.NonHpfDocuments.GetChildren)
            {
                if (nonHpfEncounter.ChildrenKeys.Contains(key))
                {
                    isKeyExists = true;
                    nonHpfDocDetails = nonHpfEncounter.GetChild(key) as NonHpfDocumentDetails;
                    continue;
                }                
            }

            if (!isKeyExists)
            {
                string message = "NonHpf Document '" + nonHPfDocRefId + "' not associated with the patient Mrn='" + requestPatientDetails.MRN + "' ,Facility='" + requestPatientDetails.FacilityCode;
                log.Debug(message);
                throw new VaultException(message);
            }
            
            RequestNonHpfDocumentDetails requestNonHpfDocDetails = new RequestNonHpfDocumentDetails(nonHpfDocDetails);
            BillingTierDetails billingTierDetails;
            if (nonHpfBillingTierId.Length != 0)          
            {
                billingTierDetails = (BillingTierDetails)AdminVault.GetEntityObject(DataVaultConstants.BillingTier, nonHpfBillingTierId);
                requestNonHpfDocDetails.BillingTier = billingTierDetails.Id;
            }            
            requestNonHpfDocDetails.SelectedForRelease = true;

            switch (operationType)
            {
                case Operation.Add:
                    if (requestPatientDetails.NonHpfDocument.GetChild(requestNonHpfDocDetails.Key) == null)
                    {
                        requestPatientDetails.NonHpfDocument.AddDocument(requestNonHpfDocDetails);
                    }                    
                    break;
                case Operation.Modify:
                    if (requestPatientDetails.NonHpfDocument.GetChild(requestNonHpfDocDetails.Key) != null)
                    {
                        RequestNonHpfDocumentDetails tempRequestPatient = (RequestNonHpfDocumentDetails)requestPatientDetails.NonHpfDocument.GetChild(requestNonHpfDocDetails.Key);
                        if (!tempRequestPatient.IsReleased)
                        {
                            ((RequestNonHpfDocumentDetails)requestPatientDetails.NonHpfDocument.GetChild(requestNonHpfDocDetails.Key)).BillingTier = requestNonHpfDocDetails.BillingTier;
                        }
                    }
                    break;  
                case Operation.Delete:
                    if (requestPatientDetails.NonHpfDocument.GetChild(requestNonHpfDocDetails.Key) != null)
                    {
                        requestPatientDetails.NonHpfDocument.RemoveChild(requestNonHpfDocDetails);
                    }                    
                    break;  
            }            
            log.ExitFunction();
        }

        /// <summary>
        /// Get the request details for the given requestID.
        /// </summary>
        /// <param name="requestId">Request ID.</param>        
        private RequestDetails GetRequestInfo(long requestId)
        {
            log.EnterFunction();
            RequestDetails requestDetails;
            try
            {
                requestDetails = RequestController.Instance.RetrieveRequest(requestId);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return requestDetails;
        }

        /// <summary>
        /// Get the requestor details for the given requestorID.
        /// </summary>
        /// <param name="requestId">Request ID.</param>        
        private RequestorDetails GetRequestorInfo(long requestorId)
        {
            log.EnterFunction();
            RequestorDetails requestorDetails;
            try
            {
                requestorDetails = RequestorController.Instance.RetrieveRequestor(requestorId);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return requestorDetails;
        }

        #endregion

        #endregion


        #region Properties

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get
            {
                return DataVaultConstants.RequestPtsInfo;
            }
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

        public int ReleaseCount
        {
            get { return releaseCount; }
            set { releaseCount = value; }
        }

        #endregion

    }
}
