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
using System.Collections.Generic;
using System.Configuration;
using System.Globalization;
using System.Xml;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.View.FindPatient;
using McK.EIG.ROI.Client.Patient.Model;

using McK.EIG.ROI.Client.Web_References.DeficiencyWS;
using McK.EIG.ROI.Client.Web_References.HPFPatientWS;
using McK.EIG.ROI.Client.Web_References.ROIRequestorWS;
using McK.EIG.ROI.Client.Web_References.MedicalRecordWS;
using McK.EIG.ROI.Client.Web_References.ConfigurationWS;
using McK.EIG.ROI.Client.Web_References.DocumentWS;
using McK.EIG.ROI.Client.Web_References.CcdConversionServiceWS;
using McK.EIG.ROI.Client.Web_References.ROISupplementaryWS;

namespace McK.EIG.ROI.Client.Patient.Controller
{   
    public partial class PatientController : ROIController 
    {
        #region Fields

        private static string DEFAULT_AUDIT_FACILITY = "E_P_R_S";
        
        private static object syncRoot = new Object();

        private static volatile PatientController patientController;

        private RequestorServiceWse requestorService;

        //Private variable to create instance of HPFPatientService 
        private PatientServiceWse hpfPatientService;

        //Private variable to create instance of MedicalRecordService 
        private MedicalRecordServiceWse medicalRecordService;

        //Private variable to create instance of DeficiencyService 
        private DeficiencyServiceWse deficiencyService;

        private ConfigurationServiceWse configurationService;

        private DocumentInfoServiceWse documentInfoService;

        private CcdConversionServiceWse ccdConversionService;

        private ROISupplementaryServiceWse supplementaryService;

        #endregion

        #region Constructor

        /// <summary>
        /// PatientController Constructor
        /// </summary>
        private PatientController()
        {        
            hpfPatientService = new PatientServiceWse();
            medicalRecordService = new MedicalRecordServiceWse();
            supplementaryService = new ROISupplementaryServiceWse();
        }

        #endregion
        
        #region Methods
        
        #region Service Methods
  
        public long CreateSupplementalPatient(PatientDetails patient)
        {
            patient.Normalize();
            PatientValidator validator = new PatientValidator();
            if (!validator.Validate(patient))
            {
                throw validator.ClientException;
            }
            SupplementalPatient roiPatient = MapModel(patient);
            object[] requestParams = new object[] { roiPatient };
            object response = ROIHelper.Invoke(supplementaryService, "createSupplementalPatient", requestParams);
            long patientInfoId = (long)response;

            if (!string.IsNullOrEmpty(patient.FacilityCode))
            {
                FacilityDetails fac = FacilityDetails.GetFacility(patient.FacilityCode, patient.FacilityType);
                if (fac == null)
                {
                    FacilityDetails facility = new FacilityDetails(patient.FacilityCode, patient.FacilityCode, FacilityType.NonHpf);
                    UserData.Instance.Facilities.Add(facility);
                }
            }

            return patientInfoId;
        }

        /// <summary>
        /// Returns Patient details for the given patient Id.
        /// </summary>
        /// <param name="PaymentMethodId">The id of the PatientInfo which has to be retrieved</param>
        /// <returns>Returns a PatientInfo details </returns>
        public PatientDetails RetrieveSupplementalPatient(long patientId, bool hasViewPatient)
        {
            object[] requestParams = new object[] { patientId, hasViewPatient };
            object response = ROIHelper.Invoke(supplementaryService, "getSupplementalPatient", requestParams);
            PatientDetails patient = MapModel((SupplementalPatient)response);
            return patient;
        }

        /// <summary>
        /// Method to update the existing patient information
        /// </summary>
        /// <param name="PatientDetails"> PatientInfo details which has to be updates</param>
        public void UpdateSupplementalPatient(PatientDetails patient)
        {
            patient.Normalize();
            PatientValidator validator = new PatientValidator();
            if (!validator.Validate(patient))
            {
                throw validator.ClientException;
            }
            SupplementalPatient roiSupplemental = MapModel(patient);
            object[] requestParams = new object[] { roiSupplemental };
            ROIHelper.Invoke(supplementaryService, "updateSupplementalPatient", requestParams);

            if (!string.IsNullOrEmpty(patient.FacilityCode))
            {
                FacilityDetails fac = FacilityDetails.GetFacility(patient.FacilityCode, patient.FacilityType);
                if (fac == null)
                {
                    FacilityDetails facility = new FacilityDetails(patient.FacilityCode, patient.FacilityCode, FacilityType.NonHpf);
                    UserData.Instance.Facilities.Add(facility);
                }
            }
       }

        /// <summary>
        /// Method to delete the selected patient
        /// </summary>
        /// <param name="PaymentMethodIds">PatientInfo id to delete</param>
        public void DeleteSupplementalPatient(long patientID)
        {
            object[] requestParams = new object[] { patientID };
            ROIHelper.Invoke(supplementaryService, "deleteSupplementalPatient", requestParams);
        }

        /// <summary>
        /// create the nonHPFDocument for the nonHPFPatient
        /// </summary>
        /// <param name="nonHPFdocument"></param>
        /// <returns></returns>
        public long CreateSupplementalDocument(NonHpfDocumentDetails nonHPFDocument)
        {
            SupplementalDocument supplementalDocument = MapModel(nonHPFDocument);
            
            object[] requestParams = new object[] { supplementalDocument };
            object response = ROIHelper.Invoke(supplementaryService, "createSupplementalDocument", requestParams);
            long supplementalID = (long)response;
            PatientDetailsCache.RemoveKey(nonHPFDocument.PatientMrn + nonHPFDocument.PatientFacility);
            return supplementalID;
        }

        /// <summary>
        /// Retrieve Supplemental document for the Non-HPF patient.
        /// </summary>
        /// <param name="nonHpfPatient"></param>
        /// <returns></returns>
        public PatientDetails RetrieveSuppmentalDocuments(PatientDetails nonHpfPatient)
        {
            if (PatientDetailsCache.IsKeyExist(nonHpfPatient.MRN + nonHpfPatient.facilityCode))
            {
                return PatientDetailsCache.GetPatDetails(nonHpfPatient.MRN + nonHpfPatient.facilityCode);
            }
            else
            {
                object[] requestParams = new object[] { nonHpfPatient.Id };
                object response = ROIHelper.Invoke(supplementaryService, "getSupplementalDocuments", requestParams);
                if (response == null) return nonHpfPatient;
                MapModel((SupplementalDocumentResult)response, nonHpfPatient, true);
               // PatientDetailsCache.AddData(nonHpfPatient.MRN + nonHpfPatient.facilityCode, nonHpfPatient);
                return nonHpfPatient;
            }
        }

        /// <summary>
        /// update the nonHPFDcoument for the nonHPFPatinet
        /// </summary>
        /// <param name="nonHPFdocument"></param>
        /// <returns></returns>
        public void UpdateSupplementalDocument(NonHpfDocumentDetails nonHPFDocument)
        {
            SupplementalDocument supplementalDocument = MapModel(nonHPFDocument);

            object[] requestParams = new object[] { supplementalDocument };
            ROIHelper.Invoke(supplementaryService, "updateSupplementalDocument", requestParams);
            PatientDetailsCache.RemoveKey(nonHPFDocument.PatientMrn + nonHPFDocument.PatientFacility);
        }

        /// <summary>
        /// Delete the nonHPFDocument for the nonHPFPatient
        /// </summary>
        /// <param name="nonHPFDocumentID"></param>
        public void DeleteSupplementalDocument(long nonHPFDocumentID, long requestId)
        {
            object[] requestParams = new object[] { nonHPFDocumentID, requestId };
            ROIHelper.Invoke(supplementaryService, "deleteSupplementalDocument", requestParams);
        }

        /// <summary>
        /// create the nonHPFDocument for the HPFPatient
        /// </summary>
        /// <param name="document"></param>
        /// <returns></returns>
        public long CreateSupplementarityDocument(NonHpfDocumentDetails document)
        {
            SupplementarityDocument supplementalDocument = SupplementarityMapModel(document);
            object[] requestParams = new object[] { supplementalDocument };
            object response = ROIHelper.Invoke(supplementaryService, "createSupplementarityDocument", requestParams);
            long supplementarityID = (long)response;
            PatientDetailsCache.RemoveKey(document.PatientMrn + document.PatientFacility);
            return supplementarityID;
        }

        /// <summary>
        /// Retrieve HPF patient's supplemental documents.
        /// </summary>
        /// <param name="hpfPatient"></param>
        /// <returns></returns>
        public PatientDetails RetrieveSuppmentarityDocuments(PatientDetails hpfPatient)
        {
            if (PatientDetailsCache.IsKeyExist(hpfPatient.MRN + hpfPatient.facilityCode))
            {
                return PatientDetailsCache.GetPatDetails(hpfPatient.MRN + hpfPatient.facilityCode);
            }
            else
            {
                object[] requestParams = new object[] { hpfPatient.MRN, hpfPatient.FacilityCode };
                object response = ROIHelper.Invoke(supplementaryService, "getSupplementarityDocuments", requestParams);
                if (response == null) return hpfPatient;
                MapModel((SupplementarityDocumentResult)response, hpfPatient, true);
                //PatientDetailsCache.AddData(hpfPatient.MRN + hpfPatient.facilityCode, hpfPatient);                
                return hpfPatient;
            }
        }

        /// <summary>
        /// update the nonHPFDcoument for the HPFPatinet
        /// </summary>
        /// <param name="document"></param>
        /// <returns></returns>
        public void UpdateSupplementarityDocument(NonHpfDocumentDetails document)
        {
            SupplementarityDocument supplementalDocument = SupplementarityMapModel(document);
            object[] requestParams = new object[] { supplementalDocument };
            ROIHelper.Invoke(supplementaryService, "updateSupplementarityDocument", requestParams);
            PatientDetailsCache.RemoveKey(document.PatientMrn + document.PatientFacility);
        }

        /// <summary>
        /// Delete the nonHPFDocument for the HPFPatient
        /// </summary>
        /// <param name="nonHPFDocumentID"></param>
        public void DeleteSupplementarityDocument(long nonHPFDocumentID, long requestId, long patientSeq)
        {
            object[] requestParams = new object[] { nonHPFDocumentID,requestId,patientSeq };
            ROIHelper.Invoke(supplementaryService, "deleteSupplementarityDocument", requestParams);
        }

        /// <summary>
        /// create the attachment for the nonHPFPatient
        /// </summary>
        /// <param name="attachment"></param>
        /// <returns></returns>
        public AttachmentDetails CreateSupplementalAttachment(AttachmentDetails attachment)
        {
            SupplementalAttachment supplementalAttachment = MapModel(attachment);
            object[] requestParams = new object[] { supplementalAttachment };
            object response = ROIHelper.Invoke(supplementaryService, "createSupplementalAttachment", requestParams);
            attachment = MapModel(attachment, (SupplementalAttachmentResult)response);
            PatientDetailsCache.RemoveKey(attachment.PatientMrn + attachment.PatientFacility);

            return attachment;
        }

        /// <summary>
        /// Retrieve Non-HPF patient's attachments
        /// </summary>
        /// <param name="nonHpfPatient"></param>
        /// <returns></returns>
        public PatientDetails RetrieveSuppmentalAttachments(PatientDetails nonHpfPatient)
        {
            if (PatientDetailsCache.IsKeyExist(nonHpfPatient.MRN + nonHpfPatient.facilityCode))
            {
                return PatientDetailsCache.GetPatDetails(nonHpfPatient.MRN + nonHpfPatient.facilityCode);
            }
            else
            {
                object[] requestParams = new object[] { nonHpfPatient.Id };
                object response = ROIHelper.Invoke(supplementaryService, "getSupplementalAttachments", requestParams);
                if (response == null) return nonHpfPatient;
                MapModel((SupplementalAttachmentResult)response, nonHpfPatient, true);
                PatientDetailsCache.AddData(nonHpfPatient.MRN + nonHpfPatient.facilityCode, nonHpfPatient);
                return nonHpfPatient;
            }
        }

        /// <summary>
        /// update the attachment for the nonHPFPatient
        /// </summary>
        /// <param name="attachment"></param>
        /// <returns></returns>
        public AttachmentDetails UpdateSupplementalAttachment(AttachmentDetails attachment)
        {
            SupplementalAttachment supplementalAttachment = MapModel(attachment);
            object[] requestParams = new object[] { supplementalAttachment };
            object response = ROIHelper.Invoke(supplementaryService, "updateSupplementalAttachment", requestParams);
            attachment = MapModel(attachment, (SupplementalAttachmentResult)response);
            PatientDetailsCache.RemoveKey(attachment.PatientMrn + attachment.PatientFacility);
            return attachment;
        }

        /// <summary>
        /// delete the attachment for the nonHPFPatient
        /// </summary>
        /// <param name="attachmentID"></param>
        public void DeleteSupplementalAttachment(long attachmentID, long requestId)
        {
            object[] requestParams = new object[] { attachmentID,requestId };
            ROIHelper.Invoke(supplementaryService, "deleteSupplementalAttachment", requestParams);
        }

        /// <summary>
        /// create the attachment for the HPFPatient
        /// </summary>
        /// <param name="attachment"></param>
        /// <returns></returns>
        public AttachmentDetails CreateSupplementarityAttachment(AttachmentDetails attachment)
        {
            SupplementarityAttachment supplementalAttachment = SupplementarityMapModel(attachment);
            object[] requestParams = new object[] { supplementalAttachment };
            object response = ROIHelper.Invoke(supplementaryService, "createSupplementarityAttachment", requestParams);
            attachment = MapModel(attachment, (SupplementarityAttachmentResult)response);
            PatientDetailsCache.RemoveKey(attachment.PatientMrn + attachment.PatientFacility);
            return attachment;
        }

        /// <summary>
        /// Retrieve HPF patient's attachments.
        /// </summary>
        /// <param name="hpfPatient"></param>
        /// <returns></returns>
        public PatientDetails RetrieveSuppmentarityAttachments(PatientDetails hpfPatient)
        {

            if (PatientDetailsCache.IsKeyExist(hpfPatient.MRN + hpfPatient.facilityCode))
            {
                return PatientDetailsCache.GetPatDetails(hpfPatient.MRN + hpfPatient.facilityCode);
            }
            else
            {
                object[] requestParams = new object[] { hpfPatient.MRN, hpfPatient.FacilityCode };
                object response = ROIHelper.Invoke(supplementaryService, "getSupplementarityAttachments", requestParams);
                if (response == null) return hpfPatient;
                MapModel((SupplementarityAttachmentResult)response, hpfPatient, true);
               // PatientDetailsCache.AddData(hpfPatient.MRN + hpfPatient.facilityCode, hpfPatient);
                return hpfPatient;
            }
        }
       
        /// <summary>
        /// update the attachment for the HPFPatient
        /// </summary>
        /// <param name="attachment"></param>
        /// <returns></returns>
        public AttachmentDetails UpdateSupplementarityAttachment(AttachmentDetails attachment)
        {
            SupplementarityAttachment supplementalAttachment = SupplementarityMapModel(attachment);
            object[] requestParams = new object[] { supplementalAttachment };
            object response = ROIHelper.Invoke(supplementaryService, "updateSupplementarityAttachment", requestParams);
            attachment = MapModel(attachment, (SupplementarityAttachmentResult)response);
            PatientDetailsCache.RemoveKey(attachment.PatientMrn + attachment.PatientFacility);
            return attachment;
        }

        /// <summary>
        /// delete the attachment for the HPFPatient
        /// </summary>
        /// <param name="attachmentID"></param>
        public void DeleteSupplementarityAttachment(long attachmentID, long requestId, long patientSeq)
        {
            object[] requestParams = new object[] { attachmentID, requestId,patientSeq };
            ROIHelper.Invoke(supplementaryService, "deleteSupplementarityAttachment", requestParams);
        }

        /// <summary>
        /// Method to check for duplicate patient name
        /// </summary>
        /// <param name="patientName"></param>
        /// <returns></returns>
        public bool CheckDuplicateName(PatientDetails patient)
        {
            string lastName = patient.lastName;
            string firstName = patient.firstName;
            object[] requestParams = new object[] { lastName, firstName, patient.Id };
            object response = ROIHelper.Invoke(supplementaryService, "checkDuplicates", requestParams);
            return (bool)response;
        }

        /// <summary>
        /// Returns the FindPatient result for the specified search criteria.
        /// </summary>
        /// <param name="searchCriteria">FindPatientCriteria</param>
        /// <returns>FindPatientResult</returns>
        public FindPatientResult FindPatient(FindPatientCriteria searchCriteria)
        {
            FindPatientResult result = new FindPatientResult();
            searchCriteria.Normalize();
            if (!PatientValidator.ValidatePrimarySearchFields(searchCriteria))
            {
                throw new ROIException(ROIErrorCodes.InvalidSearch);
            }

            PatientValidator validator = new PatientValidator();

            //Added for special character validation.
            if (!validator.ValidateSearchFields(searchCriteria) ||
                PatientSearchUI.IsError)
            {
                throw validator.ClientException;
            }

            object[] hpfRequestParams = new object[] {searchCriteria.LastName, 
                                                       searchCriteria.FirstName,
                                                       searchCriteria.Dob.HasValue ? searchCriteria.Dob.Value.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture) : null,
                                                       ROIConstants.DateFormat,
                                                       (searchCriteria.SSN != null) ? searchCriteria.SSN.Trim('-',' ') : searchCriteria.SSN ,
                                                       searchCriteria.EPN,
                                                       searchCriteria.MRN,
                                                       (searchCriteria.FacilityCode == null) ? "All" : searchCriteria.FacilityCode,
                                                       searchCriteria.Encounter };

            object hpfResponse = HPFWHelper.Invoke(hpfPatientService, "searchPatients", PrepareHPFWParams(hpfRequestParams));
            MapModel((patientList)hpfResponse, result);

            SearchCriteria supplementalSearchCriteria = MapModel(searchCriteria);
            object[] roiRequestParams = new object[] { supplementalSearchCriteria };
            object roiResponse = ROIHelper.Invoke(supplementaryService, "searchSupplementalPatient", roiRequestParams);
            MapModel(roiResponse, result);

            result.MaxCountExceeded = (result.PatientSearchResult.Count > searchCriteria.MaxRecord);

            List<PatientDetails> list = new List<PatientDetails>(result.PatientSearchResult);
            list.Sort(PatientDetails.Sorter);
            if (result.MaxCountExceeded)
            {
                list.RemoveRange(searchCriteria.MaxRecord, list.Count - searchCriteria.MaxRecord);
            }

            result.PatientSearchResult.Clear();
            list.ForEach(result.PatientSearchResult.Add);

            return result;
        }

        /// <summary>
        /// Returns PatientInfo details for the given patient MRN and Facility detail.
        /// </summary>
        /// <param name="PaymentMethodId">The id of the PatientInfo which has to be retrieved</param>
        /// <returns>Returns a PatientInfo details </returns>
        public PatientDetails RetrieveHpfPatient(string mrn, string facility)
        {
            object[] requestParams = new object[] { mrn, facility, ROIConstants.DateFormat };
            object response = HPFWHelper.Invoke(hpfPatientService, "getPatientDetail", PrepareHPFWParams(requestParams));
            PatientDetails clientPatientDetails = MapModel((facilityPatientDetail)response);

            return clientPatientDetails;
        }

        #endregion

        #region Model Mapper

        /// <summary>
        /// Convert server to Client HPF FindPatientResult.
        /// </summary>
        /// <param name="searchCriteria">Server HpfSearchResult</param>
        /// <returns>Client FindPatientResult</returns>
        public static void MapModel(patientList hpfSearchResult, FindPatientResult searchResult)
        {
            if (UserData.Instance.EpnEnabled)
            {
                if (hpfSearchResult.enterprisePatients == null) return;

                foreach (enterprisePatient enterprisePatient in hpfSearchResult.enterprisePatients)
                {
                    if (enterprisePatient.facilityPatients != null)
                    {
                        MapModel(enterprisePatient.facilityPatients, searchResult);
                    }
                }
            }
            else
            {
                if (hpfSearchResult.facilityPatients == null) return;
                MapModel(hpfSearchResult.facilityPatients, searchResult);
            }            
        }

        public static void MapModel(facilityPatient[] searchResult, FindPatientResult appendTo)
        {
            PatientDetails patientDetails;

            int index = 0;
            foreach (facilityPatient hpfPatient in searchResult)
            {
                if (hpfPatient.patientLocked)
                {
                    if (!UserData.Instance.HasAccess(ROISecurityRights.AccessLockedRecords)) continue;
                }

                patientDetails = new PatientDetails();
                if (hpfPatient.dob != null && hpfPatient.dob.Length != 0)
                {
                    patientDetails.DOB = DateTime.ParseExact(hpfPatient.dob, ROIConstants.DateFormat, CultureInfo.InvariantCulture);
                }
                patientDetails.EPN               = hpfPatient.epn;
                patientDetails.FacilityCode      = hpfPatient.facility;
                patientDetails.FullName          = hpfPatient.fullName;
                
                index = hpfPatient.fullName.IndexOf(",");

                patientDetails.LastName = (index > 0) ? hpfPatient.fullName.Substring(0, index).Trim() : string.Empty;
                patientDetails.FirstName = (index > 0) ? hpfPatient.fullName.Substring(index + 1).Trim() : hpfPatient.fullName;

                patientDetails.MRN               = hpfPatient.mrn;
                patientDetails.SSN               = hpfPatient.ssn;
                patientDetails.IsVip             = hpfPatient.vip;
                patientDetails.PatientLocked   = hpfPatient.patientLocked;
                patientDetails.EncounterLocked = hpfPatient.encounterLocked;
                patientDetails.IsHpf             = true;

                switch (hpfPatient.gender)
                {
                    case "M": patientDetails.Gender = Gender.Male; break;
                    case "F": patientDetails.Gender = Gender.Female; break;
                    case "U": patientDetails.Gender = Gender.Unknown; break;
                }

                appendTo.PatientSearchResult.Add(patientDetails);
            }
        }

        /// <summary>
        /// Convert server to Client HPF Patient Details.
        /// </summary>
        /// <param name="searchCriteria">Server facilityPatientDetail</param>
        /// <returns>Client PatientDetails</returns>
        public static PatientDetails MapModel(facilityPatientDetail server)
        {
            PatientDetails client = new PatientDetails();
            //CR# 375064 - In ROI, for RM destructed patient request, 
            //throws exception while user navigate into "Request-Patient Information" screen.
            if (!string.IsNullOrEmpty(server.fullName))
            {
                int index = server.fullName.IndexOf(",");

                client.FullName = server.fullName;
                client.LastName = (index > 0) ? server.fullName.Substring(0, index).Trim() : string.Empty;
                client.FirstName = (index > 0) ? server.fullName.Substring(index + 1).Trim() : server.fullName;
            }
            if (server.dob != null && server.dob.Trim().Length > 0)
            {
                client.DOB = DateTime.ParseExact(server.dob, ROIConstants.DateFormat, CultureInfo.InvariantCulture);
            }            
            client.Address           = GetAddress(server);            
            client.HomePhone         = server.homephone;
            client.WorkPhone         = server.workphone;
            client.EPN               = server.epn;
            client.FacilityCode      = server.facility;
            client.MRN               = server.mrn;
            client.SSN               = server.ssn;
            client.IsVip             = server.vip;
            client.PatientLocked   = server.patientLocked;
            client.EncounterLocked = server.encounterLocked;
            client.IsHpf             = true;
            switch (server.gender)
            {
                case "M": client.Gender = Gender.Male; break;
                case "F": client.Gender = Gender.Female; break;
                case "U": client.Gender = Gender.Unknown; break;
            }

            return client;
        }

        /// <summary>
        /// Method returns lov criteria to search a supplemental
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        private static SearchCriteria MapModel(FindPatientCriteria searchCriteria)
        {

            SearchCriteria suppementalSearchCriteria = new SearchCriteria();
            suppementalSearchCriteria.maxCount = searchCriteria.MaxRecord;

            suppementalSearchCriteria.conditions = PrepareSearchSupplementalPatient(searchCriteria);

            return suppementalSearchCriteria;
        }

        /// <summary>
        /// Convert server to Client HPF Patient Address Details.
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        private static AddressDetails GetAddress(facilityPatientDetail server)
        {
            AddressDetails client = new AddressDetails();
            client.Address1   = server.address1;
            client.Address2   = server.address2;
            client.Address3   = server.address3;
            client.City       = server.city;
            client.State      = server.state;
            client.PostalCode = server.zip;
            return client;
        }

        /// <summary>
        /// Prepares supplemental lovs to search
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        private static SearchCondition[] PrepareSearchSupplementalPatient(FindPatientCriteria searchCriteria)
        {
            List<SearchCondition> searchConditions = new List<SearchCondition>();
            if (!string.IsNullOrEmpty(searchCriteria.LastName))
            {
                searchConditions.Add(CreateSearchCondition(PatientDetails.LastNameKey, searchCriteria.LastName));
            }

            if (!string.IsNullOrEmpty(searchCriteria.FirstName))
            {
                searchConditions.Add(CreateSearchCondition(PatientDetails.FirstNameKey, searchCriteria.FirstName));
            }

            if (searchCriteria.Dob.HasValue)
            {
                searchConditions.Add(CreateSearchCondition(PatientDetails.DOBKey,
                                       searchCriteria.Dob.Value.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture),
                                       Condition.Equal.ToString()));
            }

            if (!string.IsNullOrEmpty(searchCriteria.SSN))
            {
                searchConditions.Add(CreateSearchCondition(PatientDetails.SSNKey, searchCriteria.SSN.Trim('-', ' ')));
            }

            if (!string.IsNullOrEmpty(searchCriteria.EPN))
            {
                searchConditions.Add(CreateSearchCondition(PatientDetails.EPNKey, searchCriteria.EPN));
            }

            if (!string.IsNullOrEmpty(searchCriteria.MRN))
            {
                searchConditions.Add(CreateSearchCondition(PatientDetails.MRNKey, searchCriteria.MRN));
            }

            string facilities = searchCriteria.FacilityCode;
            if (searchCriteria.FacilityCode == null)
            {
                facilities = ROIViewUtility.ConvertToCsv(UserData.Instance.Facilities);
                //facilities += ROIViewUtility.ConvertToCsv(UserData.Instance.FreeformFacilities.Keys);
                facilities = facilities.TrimEnd(',');
            }
            if (searchCriteria.FacilityCode != null)
            {
                if (searchCriteria.FacilityType == FacilityType.Hpf)
                {
                   searchConditions.Add(CreateSearchCondition(PatientDetails.FacilityKey, facilities, Condition.In.ToString()));
                }
                else
                {
                   searchConditions.Add(CreateSearchCondition(PatientDetails.FreeformFacilityKey, facilities, Condition.In.ToString()));
                }
            }

            if (!string.IsNullOrEmpty(searchCriteria.Encounter))
            {
                searchConditions.Add(CreateSearchCondition(PatientDetails.EncounterKey, searchCriteria.Encounter));
            }

            if (!UserData.Instance.HasAccess(ROISecurityRights.ROIVipStatus))
            {
                searchConditions.Add(CreateSearchCondition(PatientDetails.IsVipKey, "0"));
            }

            return searchConditions.ToArray();

        }

        public static SearchCondition CreateSearchCondition(string key, string value)
        {
            return CreateSearchCondition(key, value, null, Condition.Like.ToString());
        }

        public static SearchCondition CreateSearchCondition(string key, string value, string operation)
        {
            return CreateSearchCondition(key, value, null, operation);
        }

        public static SearchCondition CreateSearchCondition(string key, string value,
                                                            string valueTo, string operation)
        {
            SearchCondition searchCondition = new SearchCondition();

            searchCondition.key = key;
            searchCondition.value = Truncate(value);
            searchCondition.valueTo = valueTo;
            searchCondition.operation = operation;

            return searchCondition;
        }

        /// <summary>
        /// converts the server to client map model
        /// </summary>
        /// <param name="roiSearchResponse"></param>
        /// <param name="appendTo"></param>
        public static void MapModel(object roiSearchResponse, FindPatientResult appendTo)
        {
            PatientsSearchResult searchResult = (PatientsSearchResult)roiSearchResponse;
            if (searchResult.patients == null) return;
            PatientDetails patient;
            foreach (SupplementalPatient supplemental in searchResult.patients)
            {
                patient = MapModel(supplemental, null);
                //CR#365143 - Skip the unauthorized patient
                if (!string.IsNullOrEmpty(patient.FacilityCode) &&
                    !UserData.Instance.Facilities.Contains(FacilityDetails.GetFacility(patient.FacilityCode, patient.FacilityType)))
                {
                    continue;
                }
                if (!patient.IsHpf)
                {
                    appendTo.PatientSearchResult.Add(patient);
                }
            }
        }

        /// <summary>
        /// convert the server to client map model
        /// </summary>
        /// <param name="supplemental"></param>
        /// <param name="patient"></param>
        /// <returns></returns>
        private static PatientDetails MapModel(SupplementalPatient supplemental, PatientDetails patient)
        {
            if (!(patient != null && patient.IsHpf))
            {
                patient = new PatientDetails();
            }
            patient.Id = supplemental.id;
            patient.HasRequests = supplemental.hasRequest;
            if (patient.Id == 0)
            {
                patient.IsHpf = true;
                patient.mrn = supplemental.mrn;
                patient.FacilityCode = supplemental.facility;
            }
            else
            {
                patient.IsHpf = false;
                patient.IsVip = supplemental.vip;
                patient.FullName = supplemental.lastName + ", " + supplemental.firstName;
                patient.LastName = supplemental.lastName;
                patient.FirstName = supplemental.firstName;
                switch (supplemental.gender)
                {
                    case "M": patient.Gender = Gender.Male; break;
                    case "F": patient.Gender = Gender.Female; break;
                    case "U": patient.Gender = Gender.Unknown; break;
                }
                patient.MRN = supplemental.mrn;
                patient.SSN = supplemental.ssn;
                patient.EPN = supplemental.epn;

                if (supplemental.freeformFacility != null)
                {
                    patient.IsFreeformFacility = true;
                    patient.FacilityCode = supplemental.freeformFacility.freeFormFacilityName;
                }
                else
                {
					patient.IsFreeformFacility = false;
                    patient.FacilityCode = supplemental.facility;
                }
                if (!string.IsNullOrEmpty(supplemental.dateOfBirth))
                {
                    patient.DOB = Convert.ToDateTime(supplemental.dateOfBirth, CultureInfo.InvariantCulture).Date;
                }
                patient.HomePhone = supplemental.homephone;
                patient.WorkPhone = supplemental.workphone;

                AddressDetails address = new AddressDetails();
                address.Address1 = supplemental.address1;
                address.Address2 = supplemental.address2;
                address.Address3 = supplemental.address3;
                address.City = supplemental.city;
                address.State = supplemental.state;
                address.PostalCode = supplemental.zip;
                address.CountryCode = supplemental.countryCode;
                patient.Address = address;

            }
            return patient;
        }

        /// <summary>
        /// converts the client patientdetails to server patient details
        /// </summary>
        /// <param name="patientDetail"></param>
        /// <returns></returns>
        private SupplementalPatient MapModel(PatientDetails patientDetail)
        {
            SupplementalPatient patient = new SupplementalPatient();
            patient.id = patientDetail.id;
            patient.firstName = patientDetail.firstName;
            patient.lastName = patientDetail.lastName;
            patient.homephone = patientDetail.homePhone;
            patient.workphone = patientDetail.workPhone;
            patient.vip = patientDetail.isVip;
            switch (patientDetail.gender)
            {
                case Gender.None: patient.gender = "N"; break;
                case Gender.Female: patient.gender = "F"; break;
                case Gender.Male: patient.gender = "M"; break;
                case Gender.Unknown: patient.gender = "U"; break;
            }
            patient.ssn = patientDetail.ssn;
            patient.epn = patientDetail.epn;
			patient.mrn = patientDetail.MRN;
            if (patientDetail.IsFreeformFacility && patientDetail.FacilityCode != null)
            {
                FreeFormFacility freeformFacility = new FreeFormFacility();
                freeformFacility.freeFormFacilityName = patientDetail.facilityCode;
                patient.freeformFacility = freeformFacility;
            }
            else
            {
                patient.facility = patientDetail.facilityCode;
            }            
            patient.address1 = patientDetail.Address.Address1;
            patient.address2 = patientDetail.Address.Address2;
            patient.address3 = patientDetail.Address.Address3;
            patient.state = patientDetail.Address.State;
            patient.zip = patientDetail.Address.PostalCode;
            patient.city = patientDetail.Address.City;
            patient.countryName = patientDetail.Address.CountryName;
            patient.countryCode = patientDetail.Address.CountryCode;
            patient.newCountry = patientDetail.Address.NewCountry;
            if (patientDetail.DOB != null)
            {
                patient.dateOfBirth = Convert.ToString(patientDetail.DOB, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            return patient;
        }
        /// <summary>
        /// Converts the server non-HPF patient to client
        /// </summary>
        /// <param name="supplementalPatient"></param>
        /// <returns></returns>
        private PatientDetails MapModel(SupplementalPatient supplementalPatient)
        {
            PatientDetails patient = new PatientDetails();
            patient.IsHpf = false;
            patient.Id = supplementalPatient.id;
            patient.IsVip = supplementalPatient.vip;
            patient.HasRequests = supplementalPatient.hasRequest;
            patient.FullName = supplementalPatient.lastName + ", " + supplementalPatient.firstName;
            patient.LastName = supplementalPatient.lastName;
            patient.FirstName = supplementalPatient.firstName;
            switch (supplementalPatient.gender)
            {
                case "N": patient.Gender = Gender.None; break;
                case "F": patient.Gender = Gender.Female; break;
                case "M": patient.Gender = Gender.Male; break;
                case "U": patient.Gender = Gender.Unknown; break;
            }
            patient.MRN = supplementalPatient.mrn;
            patient.EPN = supplementalPatient.epn;
            patient.SSN = supplementalPatient.ssn;
            if (supplementalPatient.freeformFacility != null)
            {
                patient.FacilityCode = supplementalPatient.freeformFacility.freeFormFacilityName;
                patient.IsFreeformFacility = true;
            }
            else
            {
                patient.FacilityCode = supplementalPatient.facility;
                patient.IsFreeformFacility = false;
            }            
            if (!string.IsNullOrEmpty(supplementalPatient.dateOfBirth))
            {
                patient.DOB = Convert.ToDateTime(supplementalPatient.dateOfBirth, CultureInfo.InvariantCulture).Date;
            }
            patient.HomePhone = supplementalPatient.homephone;
            patient.WorkPhone = supplementalPatient.workphone;

            AddressDetails address = new AddressDetails();
            address.Address1 = supplementalPatient.address1;
            address.Address2 = supplementalPatient.address2;
            address.Address3 = supplementalPatient.address3;
            address.City = supplementalPatient.city;
            address.State = supplementalPatient.state;
            address.PostalCode = supplementalPatient.zip;
            address.CountryName = supplementalPatient.countryName;
            address.CountryCode = supplementalPatient.countryCode;
            patient.Address = address;

            return patient;
        }

        /// <summary>
        /// Converts the server non-hpf documents to client.
        /// </summary>
        /// <param name="supplementarityDocument"></param>
        /// <param name="patient"></param>
        /// <param name="fetchDocuments"></param>
        private void MapModel(SupplementarityDocumentResult supplementarityDocuments, PatientDetails patient, bool fetchDocuments)
        {
            patient.NonHpfDocuments.RemoveAll();
            NonHpfDocumentDetails nonHpfDocument;
            foreach (SupplementarityDocument supplementarityDocument in supplementarityDocuments.supplementarityDocumentResult)
            {
                nonHpfDocument = new NonHpfDocumentDetails();
                nonHpfDocument.Id = supplementarityDocument.id;
                nonHpfDocument.DocType = supplementarityDocument.docName;
                nonHpfDocument.Encounter = supplementarityDocument.encounter;                
                if (supplementarityDocument.freeformFacility != null)
                {
                    nonHpfDocument.IsFreeformFacility = true;
                    nonHpfDocument.FacilityCode = supplementarityDocument.freeformFacility.freeFormFacilityName;
                }
                else
                {
                    nonHpfDocument.FacilityCode = supplementarityDocument.docFacility;
                }
                nonHpfDocument.PatientFacility = supplementarityDocument.facility;
                nonHpfDocument.Department = supplementarityDocument.department;
                nonHpfDocument.Subtitle = supplementarityDocument.subtitle;
                nonHpfDocument.Comment = supplementarityDocument.comment;
                nonHpfDocument.PageCount = Convert.ToInt32(supplementarityDocument.pageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (supplementarityDocument.dateOfService != null)
                {
                    nonHpfDocument.DateOfService = supplementarityDocument.dateOfService;
                }
                nonHpfDocument.PatientId = patient.Id;
                nonHpfDocument.PatientMrn = patient.MRN;
                nonHpfDocument.PatientFacility = patient.FacilityCode;
                nonHpfDocument.PatientKey = patient.Key;
                patient.NonHpfDocuments.AddDocument(nonHpfDocument);
            }
            patient.AddChild(ROIConstants.NonHpfDocument, patient.NonHpfDocuments);         
        }

        /// <summary>
        /// converts the server non hpf documents to client non hpf documents for non hpf patients
        /// </summary>
        /// <param name="supplementalDocuments"></param>
        /// <param name="patient"></param>
        /// <param name="fetchDocuments"></param>
        private void MapModel(SupplementalDocumentResult supplementalDocuments, PatientDetails patient, bool fetchDocuments)
        {   
            patient.NonHpfDocuments.RemoveAll();
            NonHpfDocumentDetails nonHpfDocument;
            foreach (SupplementalDocument supplementalDocument in supplementalDocuments.supplementalDocumentResult)
            {
                nonHpfDocument = new NonHpfDocumentDetails();
                nonHpfDocument.Id = supplementalDocument.id;
                nonHpfDocument.DocType = supplementalDocument.docName;
                nonHpfDocument.Encounter = supplementalDocument.encounter;               

                if (supplementalDocument.freeformFacility != null)
                {
                    nonHpfDocument.IsFreeformFacility = true;
                    nonHpfDocument.FacilityCode = supplementalDocument.freeformFacility.freeFormFacilityName;
                }
                else
                {
                    nonHpfDocument.FacilityCode = supplementalDocument.docFacility;
                }

                nonHpfDocument.Department = supplementalDocument.department;
                nonHpfDocument.Subtitle = supplementalDocument.subtitle;
                nonHpfDocument.Comment = supplementalDocument.comment;
                nonHpfDocument.PageCount = Convert.ToInt32(supplementalDocument.pageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (supplementalDocument.dateOfService != null)
                {
                    nonHpfDocument.DateOfService = supplementalDocument.dateOfService;
                }
                nonHpfDocument.PatientId = patient.Id;                
                nonHpfDocument.PatientMrn = patient.MRN;
                nonHpfDocument.PatientFacility = patient.FacilityCode;
                nonHpfDocument.PatientKey = patient.Key;
                patient.NonHpfDocuments.AddDocument(nonHpfDocument);
            }

            patient.AddChild(ROIConstants.NonHpfDocument, patient.NonHpfDocuments);
        }

        /// <summary>
        /// Converts the server attachments to client.
        /// </summary>
        /// <param name="supplementarityAttachments"></param>
        /// <param name="patient"></param>
        /// <param name="fetchDocuments"></param>
        private void MapModel(SupplementarityAttachmentResult supplementarityAttachments, PatientDetails patient, bool fetchDocuments)
        {  
            patient.Attachments.RemoveAll();
            AttachmentDetails attachmentDetails = null;
            foreach (SupplementarityAttachment supplementarityAttachment in supplementarityAttachments.supplementarityAttachmentResult)
            {
                attachmentDetails = new AttachmentDetails();
                
                    //DM-3480 - Server is sending the document source column value in external source attribute.
                    // below logic will fetch DocumentSource value from server and populate attachment details object
                    attachmentDetails.DocumentSource = supplementarityAttachment.externalSource;
                
                attachmentDetails.Id = supplementarityAttachment.id;
                attachmentDetails.AttachmentType = supplementarityAttachment.type;
                attachmentDetails.Encounter = supplementarityAttachment.encounter;
                attachmentDetails.FacilityCode = supplementarityAttachment.facility;
                attachmentDetails.IsDeleted = supplementarityAttachment.isDeleted == "0" ? false : true;
                attachmentDetails.Comment = supplementarityAttachment.comment;
                attachmentDetails.Subtitle = supplementarityAttachment.subtitle;
                attachmentDetails.PageCount = Convert.ToInt32(supplementarityAttachment.pageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (supplementarityAttachment.dateOfService != null)
                {
                    attachmentDetails.DateOfService = supplementarityAttachment.dateOfService;
                }
                if (supplementarityAttachment.attachmentDate != null)
                {
                    attachmentDetails.AttachmentDate = supplementarityAttachment.attachmentDate;
                }

                AttachmentFileDetails attachmentFileDetails = new AttachmentFileDetails();
                if (supplementarityAttachment.uuid != null)
                {
                    attachmentFileDetails.Uuid = supplementarityAttachment.uuid;
                    attachmentFileDetails.PageCount = Convert.ToInt32(supplementarityAttachment.pageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
                    attachmentFileDetails.Extension = supplementarityAttachment.fileext;
                    attachmentFileDetails.FileName = supplementarityAttachment.filename;
                    attachmentFileDetails.FileType = supplementarityAttachment.filetype;
                    attachmentFileDetails.DateReceived = supplementarityAttachment.dateReceived;
                    attachmentFileDetails.Printable = supplementarityAttachment.printable == "0" ? false : true;
                }
                attachmentDetails.FileDetails = attachmentFileDetails;

                attachmentDetails.PatientId = patient.Id;
                attachmentDetails.PatientMrn = patient.MRN;
                attachmentDetails.PatientFacility = patient.FacilityCode;
                attachmentDetails.PatientKey = patient.Key;
                if (supplementarityAttachment.volume != null)
                {
                    attachmentDetails.Volume = supplementarityAttachment.volume;
                }
                if (supplementarityAttachment.path != null)
                {
                    attachmentDetails.Path = supplementarityAttachment.path;
                }
                patient.Attachments.AddAttachment(attachmentDetails);
            }
            patient.AddChild(ROIConstants.Attachment, patient.Attachments);
        }

        /// <summary>
        /// converts the server attachments to client attachments for non hpf patients
        /// </summary>
        /// <param name="supplementalAttachments"></param>
        /// <param name="patient"></param>
        /// <param name="fetchDocuments"></param>
        private void MapModel(SupplementalAttachmentResult supplementalAttachments, PatientDetails patient, bool fetchDocuments)
        {  
            patient.Attachments.RemoveAll();
            AttachmentDetails attachmentDetails = null;

            foreach (SupplementalAttachment supplementalAttachment in supplementalAttachments.supplementalAttachmentResult)
            {
                attachmentDetails = new AttachmentDetails();
                attachmentDetails.DocumentSource = supplementalAttachment.externalSource;
                attachmentDetails.Id = supplementalAttachment.id;
                attachmentDetails.AttachmentType = supplementalAttachment.type;
                attachmentDetails.Encounter = supplementalAttachment.encounter;                                
                attachmentDetails.IsDeleted = supplementalAttachment.isDeleted == "0" ? false : true;

                if (supplementalAttachment.freeformFacility != null)
                {
                    attachmentDetails.IsFreeformFacility = true;
                    attachmentDetails.FacilityCode = supplementalAttachment.freeformFacility.freeFormFacilityName;                    
                }
                else
                {
                    attachmentDetails.IsFreeformFacility = false;
                    attachmentDetails.FacilityCode = supplementalAttachment.docFacility;
                }
                attachmentDetails.Comment = supplementalAttachment.comment;
                attachmentDetails.Subtitle = supplementalAttachment.subtitle;
                attachmentDetails.PageCount = Convert.ToInt32(supplementalAttachment.pageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (supplementalAttachment.dateOfService != null)
                {
                    attachmentDetails.DateOfService = supplementalAttachment.dateOfService;
                }
                if (supplementalAttachment.attachmentDate != null)
                {
                    attachmentDetails.AttachmentDate = supplementalAttachment.attachmentDate;
                }

                AttachmentFileDetails attachmentFileDetails = new AttachmentFileDetails();
                if (supplementalAttachment.uuid != null)
                {
                    attachmentFileDetails.Uuid = supplementalAttachment.uuid;
                    attachmentFileDetails.PageCount = Convert.ToInt32(supplementalAttachment.pageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
                    attachmentFileDetails.Extension = supplementalAttachment.fileext;
                    attachmentFileDetails.FileName = supplementalAttachment.filename;
                    attachmentFileDetails.FileType = supplementalAttachment.filetype;
                    attachmentFileDetails.DateReceived = supplementalAttachment.dateReceived;
                    attachmentFileDetails.Printable = supplementalAttachment.printable == "0" ? false : true;
                }
                attachmentDetails.FileDetails = attachmentFileDetails;
                if (supplementalAttachment.volume != null)
                {
                    attachmentDetails.Volume = supplementalAttachment.volume;
                }
                if (supplementalAttachment.path != null)
                {
                    attachmentDetails.Path = supplementalAttachment.path;
                }

                attachmentDetails.PatientId = patient.Id;
                attachmentDetails.PatientMrn = patient.MRN;
                attachmentDetails.PatientFacility = patient.FacilityCode;
                attachmentDetails.PatientKey = patient.Key;

                patient.Attachments.AddAttachment(attachmentDetails);
            }
            patient.AddChild(ROIConstants.Attachment, patient.Attachments);
        }

        /// <summary>
        /// converts the client nonHPFDocument for nonHPFPatient to server nonHPFDcoument
        /// </summary>
        /// <param name="document"></param>
        /// <returns></returns>
        public SupplementalDocument MapModel(NonHpfDocumentDetails document)
        {
            SupplementalDocument supplementalDocument = new SupplementalDocument();
            supplementalDocument.id = document.Id;
            supplementalDocument.docName = document.DocType;
            supplementalDocument.encounter = document.Encounter;            
            if (document.IsFreeformFacility)
            {
                FreeFormFacility freeformFacility = new FreeFormFacility();
                freeformFacility.freeFormFacilityName = document.FacilityCode;
                supplementalDocument.freeformFacility = freeformFacility;
            }
            else
            {
                supplementalDocument.docFacility = document.FacilityCode;
            }
            supplementalDocument.department = document.Department;
            supplementalDocument.subtitle = document.Subtitle;
            if (document.DateOfService != null)
            {
                supplementalDocument.dateOfService = Convert.ToDateTime(document.DateOfService);
            }
            supplementalDocument.comment = document.Comment;
            supplementalDocument.pageCount = document.PageCount.ToString();
            supplementalDocument.patientId = document.PatientId;

            return supplementalDocument;
        }

        /// <summary>
        /// converts the client attachment for nonHPFPatient to server attachment
        /// </summary>
        /// <param name="attachment"></param>
        /// <returns></returns>
        public SupplementalAttachment MapModel(AttachmentDetails attachment)
        {
            SupplementalAttachment supplementalAttachment = new SupplementalAttachment();
            supplementalAttachment.id = attachment.Id;
            supplementalAttachment.type = attachment.AttachmentType;
            supplementalAttachment.encounter = attachment.Encounter;
            supplementalAttachment.submittedBy = UserData.Instance.UserId;        
            if (attachment.IsFreeformFacility)
            {
                FreeFormFacility freeformFacility = new FreeFormFacility();
                freeformFacility.freeFormFacilityName = attachment.FacilityCode;
                supplementalAttachment.freeformFacility = freeformFacility;
            }
            else
            {
                supplementalAttachment.docFacility = attachment.FacilityCode;
            }
            supplementalAttachment.subtitle = attachment.Subtitle;
            supplementalAttachment.isDeleted = attachment.IsDeleted == false ? "0" : "1";
            supplementalAttachment.pageCount = Convert.ToString(attachment.PageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
            if (attachment.DateOfService != null)
            {
                supplementalAttachment.dateOfService = attachment.DateOfService;
            }
            if (attachment.AttachmentDate != null)
            {
                supplementalAttachment.attachmentDate = attachment.AttachmentDate;
            }
            if (attachment.FileDetails != null)
            {
                supplementalAttachment.uuid = attachment.FileDetails.Uuid;
                supplementalAttachment.pageCount = Convert.ToString(attachment.FileDetails.PageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
                supplementalAttachment.filename = attachment.FileDetails.FileName;
                supplementalAttachment.filetype = attachment.FileDetails.FileType;
                supplementalAttachment.fileext = attachment.FileDetails.Extension;
                supplementalAttachment.printable = Convert.ToString(attachment.FileDetails.Printable, System.Threading.Thread.CurrentThread.CurrentUICulture);
                //if (attachment.FileDetails.DateReceived != null)
                //{

                //} TODO
            }
            supplementalAttachment.comment = attachment.Comment;
            supplementalAttachment.patientId = attachment.PatientId;
            supplementalAttachment.externalSource = attachment.DocumentSource;
            return supplementalAttachment;
        }

        /// <summary>
        /// converts the client attachment for HPFPatient to server attachment
        /// </summary>
        /// <param name="attachment"></param>
        /// <returns></returns>
        public SupplementarityAttachment SupplementarityMapModel(AttachmentDetails attachment)
        {
            SupplementarityAttachment supplementarityAttachment = new SupplementarityAttachment();
            supplementarityAttachment.id = attachment.Id;
            supplementarityAttachment.type = attachment.AttachmentType;
            supplementarityAttachment.encounter = attachment.Encounter;
            supplementarityAttachment.facility = attachment.FacilityCode;
            supplementarityAttachment.docFacility = attachment.PatientFacility;
            supplementarityAttachment.isDeleted = attachment.IsDeleted == false ? "0" : "1";            
            supplementarityAttachment.comment = attachment.Comment;
            supplementarityAttachment.subtitle = attachment.Subtitle;
            supplementarityAttachment.submittedBy = UserData.Instance.UserId;
            supplementarityAttachment.pageCount = Convert.ToString(attachment.PageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
            if (attachment.DateOfService != null)
            {
                supplementarityAttachment.dateOfService = attachment.DateOfService;
            }
            if (attachment.AttachmentDate != null)
            {
                supplementarityAttachment.attachmentDate = attachment.AttachmentDate;
            }
            if (attachment.FileDetails != null)
            {
                supplementarityAttachment.uuid = attachment.FileDetails.Uuid;
                supplementarityAttachment.pageCount = Convert.ToString(attachment.FileDetails.PageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
                supplementarityAttachment.filename = attachment.FileDetails.FileName;
                supplementarityAttachment.filetype = attachment.FileDetails.FileType;
                supplementarityAttachment.fileext = attachment.FileDetails.Extension;
                supplementarityAttachment.printable = Convert.ToString(attachment.FileDetails.Printable, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            supplementarityAttachment.mrn = attachment.PatientMrn;
            supplementarityAttachment.documentSource = attachment.DocumentSource;
            supplementarityAttachment.externalSource = attachment.DocumentSource;
            return supplementarityAttachment;

        }

        /// <summary>
        /// converts the client nonHPFDocument for HPFPatient to server nonHPFDcoument
        /// </summary>
        /// <param name="document"></param>
        /// <returns></returns>
        public SupplementarityDocument SupplementarityMapModel(NonHpfDocumentDetails document)
        {
            SupplementarityDocument supplementarityDocument = new SupplementarityDocument();
            supplementarityDocument.id = document.Id;
            supplementarityDocument.docName = document.DocType;
            supplementarityDocument.encounter = document.Encounter;            
            supplementarityDocument.facility = document.PatientFacility;
            if (document.IsFreeformFacility)
            {
                FreeFormFacility freeformFacility = new FreeFormFacility();
                freeformFacility.freeFormFacilityName = document.FacilityCode;
                supplementarityDocument.freeformFacility = freeformFacility;
            }
            else
            {
                supplementarityDocument.docFacility = document.FacilityCode;
            }            
            supplementarityDocument.department = document.Department;
            supplementarityDocument.subtitle = document.Subtitle;
            supplementarityDocument.comment = document.Comment;
            supplementarityDocument.pageCount = Convert.ToString(document.PageCount, System.Threading.Thread.CurrentThread.CurrentUICulture);
            if (document.DateOfService != null)
            {
                supplementarityDocument.dateOfService = document.DateOfService;
            }
            supplementarityDocument.mrn = document.PatientMrn;
            return supplementarityDocument;
        }

        /// <summary>
        /// Method to retreive the supplemental attachment
        /// </summary>
        /// <param name="attachment"></param>
        /// <param name="supplementalAttachments"></param>
        /// <returns></returns>
        private AttachmentDetails MapModel(AttachmentDetails attachment, SupplementalAttachmentResult supplementalAttachments)
        {
            foreach (SupplementalAttachment supplementalAttachment in supplementalAttachments.supplementalAttachmentResult)
            {
                attachment.Id = supplementalAttachment.id;
                if (supplementalAttachment.volume != null)
                {
                    attachment.Volume = supplementalAttachment.volume;
                }
                if (supplementalAttachment.path != null)
                {
                    attachment.Path = supplementalAttachment.path;
                }
            }

            return attachment;

        }

        /// <summary>
        /// Method to retrieve the supplementarity attachment
        /// </summary>
        /// <param name="attachment"></param>
        /// <param name="supplementarityAttachments"></param>
        /// <returns></returns>
        private AttachmentDetails MapModel(AttachmentDetails attachment, SupplementarityAttachmentResult supplementarityAttachments)
        {
            foreach (SupplementarityAttachment supplementarityAttachment in supplementarityAttachments.supplementarityAttachmentResult)
            {
                attachment.Id = supplementarityAttachment.id;
                if (supplementarityAttachment.volume != null)
                {
                    attachment.Volume = supplementarityAttachment.volume;
                }
                if (supplementarityAttachment.path != null)
                {
                    attachment.Path = supplementarityAttachment.path;
                }
            }
            return attachment;

        }

        #endregion

        #endregion

        #region Properties

        /// <summary>
        /// Get the PatientController Instance
        /// </summary>
        public new static PatientController Instance
        {
            get
            {
                if (patientController == null)
                {
                    lock (syncRoot)
                    {
                        if (patientController == null)
                        {
                            patientController = new PatientController();
                        }
                    }
                }
                return patientController;
            }
        }

        #endregion
    }
}
