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
using System.Configuration;
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Globalization;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.Controller;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Patient.Process
{

    /// <summary>
    /// Insert the NonHpf Document to the Hpf Patient.
    /// </summary>
    public class PatientNonHpfDocumentVault : IVault
    {
        #region Fields

        Log log = LogFactory.GetLogger(typeof(PatientNonHpfDocumentVault));

        //DBFields
        private const string ReferenceID     = "Ref_ID";
        private const string SupRefID        = "SupRef_ID";
        private const string PatientMRN      = "Pt_MRN";
        private const string PatientFacility = "Pt_Facility_Code";        
        private const string Encounter       = "EncAcct";
        private const string DocumentType    = "Doc_Type";
        private const string DateOfService   = "Date_Of_Service";
        private const string Facility        = "Facility_Code";
        private const string Department      = "Department";
        private const string Comment         = "Comment";
        private const string PageCount       = "Page_Count";
        private const string SubTitle        = "SubTitle";

        private Hashtable documentTypeHT;
        private Hashtable nonHpfDocumentHT;

        private VaultMode modeType;

        #endregion

        #region Constructor

        public PatientNonHpfDocumentVault()
        {
            documentTypeHT = new Hashtable(new VaultComparer());
            nonHpfDocumentHT = new Hashtable(new VaultComparer());
            CacheAllDocumentTypes();
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the NonHPF document.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();
            long recordCount;
            try
            {
                recordCount = 1;
                PatientDetails patientDetails;
                while (reader.Read())
                {   
                    string nonHpfDocRefId  = Convert.ToString(reader[ReferenceID], CultureInfo.CurrentCulture);
                    string supRefId        = Convert.ToString(reader[SupRefID], CultureInfo.CurrentCulture).Trim();
                    string patientFacility = Convert.ToString(reader[PatientFacility], CultureInfo.CurrentCulture).Trim();
                    string patientMRN      = Convert.ToString(reader[PatientMRN], CultureInfo.CurrentCulture).Trim();
                    long patientId = 0;
                    bool isHPF = false;

                    if (supRefId.Length != 0 && supRefId != "0")
                    {
                        patientId = Convert.ToInt64(PatientVault.GetEntityObject(supRefId, DataVaultConstants.PatientInfo), CultureInfo.CurrentCulture);
                        patientDetails = RetrivePatient(patientId);
                    }
                    else
                    {
                        isHPF = true;
                        patientDetails = RetrivePatient(patientMRN, patientFacility);
                        patientDetails = RetriveSupplementalDoc(patientDetails);
                    }
                    
                    NonHpfDocumentDetails nonHpfDocumentDetails = new NonHpfDocumentDetails();
                    nonHpfDocumentDetails.PatientId       = patientId;
                    nonHpfDocumentDetails.PatientMrn      = patientMRN;
                    nonHpfDocumentDetails.PatientFacility = patientFacility;
                    nonHpfDocumentDetails.Encounter       = Convert.ToString(reader[Encounter], CultureInfo.CurrentCulture);
                    nonHpfDocumentDetails.FacilityCode    = Convert.ToString(reader[Facility], CultureInfo.CurrentCulture);

                    FacilityDetails facility = new FacilityDetails(nonHpfDocumentDetails.FacilityCode, FacilityType.Hpf);

                    int index = UserData.Instance.Facilities.IndexOf(facility);
                    nonHpfDocumentDetails.IsFreeformFacility = index > 0 ? false : true;
                    nonHpfDocumentDetails.Department      = Convert.ToString(reader[Department], CultureInfo.CurrentCulture);
                    nonHpfDocumentDetails.Comment         = Convert.ToString(reader[Comment], CultureInfo.CurrentCulture);
                    nonHpfDocumentDetails.PageCount       = Convert.ToInt32(reader[PageCount], CultureInfo.CurrentCulture);
                    nonHpfDocumentDetails.Subtitle        = Convert.ToString(reader[SubTitle], CultureInfo.CurrentCulture);

                    if (!documentTypeHT.ContainsKey(reader[DocumentType].ToString()))
                    {
                        string message = string.Format(CultureInfo.CurrentCulture,
                                                       DataVaultErrorCodes.InvalidDocumentType,
                                                       reader[DocumentType].ToString());
                        log.Debug(message);
                        throw new VaultException(message);
                    }
                    string docType = Convert.ToString(reader[DocumentType], CultureInfo.CurrentCulture);
                    if (docType != null)
                    {
                        nonHpfDocumentDetails.DocType = documentTypeHT[docType].ToString();
                    }

                    if (reader[DateOfService].ToString().Trim().Length != 0)
                    {
                        nonHpfDocumentDetails.DateOfService = DateTime.Parse(reader[DateOfService].ToString(), CultureInfo.CurrentCulture);
                    }
                    else
                    {
                        nonHpfDocumentDetails.DateOfService = DateTime.Now;
                    }
                    patientDetails.ModifiedNonHpfDocument = nonHpfDocumentDetails;
                    if (modeType == VaultMode.Create)
                    {
                        //patientDetails.NonHpfDocumentId = ++patientDetails.NonHpfDocumentId;
                        //nonHpfDocumentDetails.Id = patientDetails.NonHpfDocumentId;
                        patientDetails.NonHpfDocumentRecentSeq = ++patientDetails.NonHpfDocumentRecentSeq;
                        nonHpfDocumentDetails.Id = patientDetails.NonHpfDocumentRecentSeq;
                        patientDetails.NonHpfDocuments.AddDocument(nonHpfDocumentDetails);
                        //patientDetails.NonHpfDocuments.AddChild(nonHpfDocumentDetails.Key, nonHpfDocumentDetails);
                        CreateNonHpfDocument(patientDetails, isHPF, recordCount);
                        //Map the patient reference id with patient id
                        nonHpfDocumentHT.Add(nonHpfDocRefId, nonHpfDocumentDetails.Key);
                    }
                    else
                    {
                        UpdateNonHpfDocument(nonHpfDocumentDetails, patientDetails, nonHpfDocRefId, recordCount);
                    }
                    recordCount++;         
                    string logMessage = string.Format(CultureInfo.CurrentCulture,
                                                      DataVaultErrorCodes.ReferenceID,
                                                      DataVaultConstants.NonHpfDocument,
                                                      nonHpfDocRefId,
                                                      nonHpfDocumentDetails.Id);
                    log.Debug(logMessage);                    
                }
                log.ExitFunction();
                return nonHpfDocumentHT;
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.OdbcError);
            }
            catch (FormatException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
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
            finally
            {
                reader.Close();
            }
        }

        private void CreateNonHpfDocument(PatientDetails patientDetails, bool isHPF, long recordCount)
        {
            log.EnterFunction();
            if (isHPF)
            {
                SaveSupplemental(patientDetails, recordCount);
            }
            else
            {
                UpdateSupplemental(patientDetails, recordCount);
            }
            log.ExitFunction();
        }


        private void UpdateNonHpfDocument(NonHpfDocumentDetails nonHpfDocumentDetails, PatientDetails patientDetails, string nonHpfDocRefId, long recordCount)
        {
            if (!nonHpfDocumentHT.ContainsKey(nonHpfDocRefId))
            {
                string errorMeg = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, nonHpfDocRefId, EntityName);
                log.Debug(errorMeg);
                throw new VaultException(errorMeg);
            }

            //string key = Convert.ToString(nonHpfDocumentHT[nonHpfDocRefId], CultureInfo.CurrentCulture);
            nonHpfDocumentHT[nonHpfDocRefId] = nonHpfDocumentDetails.Key;
            //NonHpfDocumentDetails docDetails = patientDetails.NonHpfDocuments.GetChild(key) as NonHpfDocumentDetails;
            //nonHpfDocumentDetails.Id = docDetails.Id;
            //patientDetails.NonHpfDocuments.AddChild(nonHpfDocumentDetails.Key, nonHpfDocumentDetails);
            patientDetails.NonHpfDocuments.AddDocument(nonHpfDocumentDetails);
            UpdateSupplemental(patientDetails, recordCount);
        }

        /// <summary>
        /// Save the Supplemental Document details for the HPF Patient
        /// </summary>
        /// <param name="patientDetails"></param>
        /// <param name="recordCount"></param>
        private void SaveSupplemental(PatientDetails patientDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                        DataVaultConstants.ProcessStartTag,
                                        recordCount,
                                        DateTime.Now));

                PatientController.Instance.CreateSupplemental(patientDetails);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);

            }
            catch (ROIException cause)
            {
                log.Debug(cause);
                throw;
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Update the Supplemental Document details for the Supplemental Patient
        /// </summary>
        /// <param name="patientDetails"></param>
        /// <param name="recordCount"></param>
        private void UpdateSupplemental(PatientDetails patientDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                        DataVaultConstants.ProcessStartTag,
                                        recordCount,
                                        DateTime.Now));

                PatientController.Instance.UpdateSupplemental(patientDetails);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);

            }
            catch (ROIException cause)
            {
                log.Debug(cause);
                throw new VaultException(cause.Message, cause);
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Retrive the Supplemental Patient Details.
        /// </summary>
        /// <param name="patientID"></param>
        /// <returns></returns>
        private PatientDetails RetrivePatient(long patientID)
        {
            log.EnterFunction();
            try
            {
                return PatientController.Instance.RetrieveSupplementalInfo(patientID);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
        }

        /// <summary>
        /// Retrive Hpf patient Datails.
        /// </summary>
        /// <param name="patientMrn"></param>
        /// <param name="patientFacility"></param>
        /// <returns></returns>
        private PatientDetails RetrivePatient(string patientMrn, string patientFacility)
        {
            log.EnterFunction();
            try
            {
                return PatientController.Instance.RetrieveHpfPatient(patientMrn, patientFacility);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
        }

        /// <summary>
        /// Retrive Hpf patient Datails.
        /// </summary>
        /// <param name="patientMrn"></param>
        /// <param name="patientFacility"></param>
        /// <returns></returns>
        private PatientDetails RetriveSupplementalDoc(PatientDetails hpfPatient)
        {
            log.EnterFunction();
            try
            {
                return PatientController.Instance.RetrieveSupplementalDocuments(hpfPatient);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
        }

        /// <summary>
        /// Get all Document Types.
        /// </summary>
        private void CacheAllDocumentTypes()
        {
            log.EnterFunction();
            Collection<RecordViewDetails> recordViews = UserData.Instance.RecordViews;

            foreach (RecordViewDetails recordViewDetails in recordViews)
            {
                foreach (string docName in recordViewDetails.DocTypes)
                {
                    if (!documentTypeHT.ContainsKey(docName.Trim()))
                    {
                        documentTypeHT.Add(docName.Trim(), docName.Trim());
                    }
                }
            }
            log.ExitFunction();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get { return DataVaultConstants.NonHpfDocument; }
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
