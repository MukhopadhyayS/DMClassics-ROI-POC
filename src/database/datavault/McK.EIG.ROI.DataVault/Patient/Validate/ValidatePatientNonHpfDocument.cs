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
using System.Text;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.Controller;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Patient.Validate
{

    /// <summary>
    /// Insert the NonHpf Document to the Hpf Patient.
    /// </summary>
    public class ValidatePatientNonHpfDocument : IVault
    {
        #region Fields

        Log log = LogFactory.GetLogger(typeof(ValidatePatientNonHpfDocument));

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
        private VaultMode vaultModeType;

        #endregion

        #region Constructor

        public ValidatePatientNonHpfDocument()
        {
            documentTypeHT = new Hashtable(new VaultComparer());            
            CacheAllDocumentTypes();
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the NonHPF document.
        /// </summary>
        public object Load(IDataReader reader)
        {
            NonHpfDocumentDetails nonHpfDocumentDetails;
            long patientCount;
            long recordCount = 1;
            bool isHeaderExist = false;
            StringBuilder errorMessage = new StringBuilder();
            ArrayList list = new ArrayList();

            string nonHpfDocRefId = string.Empty;

            while (reader.Read())
            {
                try
                {
                    errorMessage.Remove(0, errorMessage.ToString().Length);
                    nonHpfDocRefId = Convert.ToString(reader[ReferenceID], CultureInfo.CurrentCulture);
                    string supRefId = Convert.ToString(reader[SupRefID], CultureInfo.CurrentCulture).Trim();
                    string patientFacility = Convert.ToString(reader[PatientFacility], CultureInfo.CurrentCulture).Trim();
                    string patientMRN = Convert.ToString(reader[PatientMRN], CultureInfo.CurrentCulture).Trim();
                    bool isHpf = false;

                    if (supRefId.Length != 0 && supRefId != "0")
                    {
                        patientCount = RetrivePatient(supRefId);
                        if (patientCount == 0)
                        {
                            errorMessage.Append("Patient not found, Patient RefId=" + supRefId);
                        }
                    }
                    else
                    {
                        isHpf = true;
                        patientCount = RetrivePatient(patientMRN, patientFacility);
                        if (patientCount == 0)
                        {
                            errorMessage.Append("HPF Patient not found, MRN=" + patientMRN + "and Facility =" + patientFacility);
                        }
                    }

                    nonHpfDocumentDetails = new NonHpfDocumentDetails();
                    nonHpfDocumentDetails.PatientId = (isHpf) ? 0 : 1;
                    nonHpfDocumentDetails.PatientMrn = patientMRN;
                    nonHpfDocumentDetails.PatientFacility = patientFacility;
                    nonHpfDocumentDetails.Encounter = Convert.ToString(reader[Encounter], CultureInfo.CurrentCulture);
                    nonHpfDocumentDetails.FacilityCode = Convert.ToString(reader[Facility], CultureInfo.CurrentCulture);
                    nonHpfDocumentDetails.Department = Convert.ToString(reader[Department], CultureInfo.CurrentCulture);
                    nonHpfDocumentDetails.Comment = Convert.ToString(reader[Comment], CultureInfo.CurrentCulture);
                    nonHpfDocumentDetails.PageCount = Convert.ToInt32(reader[PageCount], CultureInfo.CurrentCulture);
                    nonHpfDocumentDetails.Subtitle = Convert.ToString(reader[SubTitle], CultureInfo.CurrentCulture);

                    if (!documentTypeHT.ContainsKey(reader[DocumentType].ToString()))
                    {
                        string message = string.Format(CultureInfo.CurrentCulture,
                                                       DataVaultErrorCodes.InvalidDocumentType,
                                                       reader[DocumentType].ToString());
                        errorMessage.Append(message);
                    }
                    else
                    {
                        nonHpfDocumentDetails.DocType = reader[DocumentType].ToString();
                    }

                    if (reader[DateOfService].ToString().Trim().Length != 0)
                    {
                        nonHpfDocumentDetails.DateOfService = DateTime.Parse(reader[DateOfService].ToString(), CultureInfo.CurrentCulture);
                    }
                    else
                    {
                        nonHpfDocumentDetails.DateOfService = DateTime.Now;
                    }
                    nonHpfDocumentDetails.Id = 1;

                    PatientValidator validator = new PatientValidator();
                    bool check = validator.Validate(nonHpfDocumentDetails);
                    if (!check)
                    {
                        errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                    }

                    if (vaultModeType == VaultMode.Create)
                    {
                        if (list.Contains(nonHpfDocRefId))
                        {
                            errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                        }
                        else
                        {
                            list.Add(nonHpfDocRefId);
                        }
                    }
                    else
                    {
                        bool isDocumentExist = ValidateNonHpfDocumentExist(nonHpfDocRefId);
                        if (!isDocumentExist)
                        {
                            errorMessage.Append(string.Format(CultureInfo.CurrentCulture,
                                                DataVaultErrorCodes.UnknownObject,
                                                nonHpfDocRefId,
                                                EntityName + "_" + Mode.Create));
                        }
                    }

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
                        ValidateUtility.WriteLog("Patient",
                            (vaultModeType == VaultMode.Create) ? DataVaultConstants.CreatePatientModule : DataVaultConstants.UpdatePatientModule,
                            "NonHpf Document",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(nonHpfDocRefId, recordCount, cause.Message);
                }               
                recordCount++;
            }
            return null;
        }

        private bool ValidateNonHpfDocumentExist(string nonHpfDocRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreatePatientModule);
            long count = Utility.GetCount(EntityName + "_" + Mode.Create, ReferenceID, nonHpfDocRefId);
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdatePatientModule);
            return (count > 0);
        }
      
        /// <summary>
        /// Retrive the Supplemental Patient Details.
        /// </summary>
        /// <param name="patientID"></param>
        /// <returns></returns>
        private long RetrivePatient(string patientID)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreatePatientModule);
            long count = Utility.GetCount(DataVaultConstants.PatientInfo + "_" + Mode.Create, ReferenceID, patientID);
            if (vaultModeType == VaultMode.Update)
            {
                if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdatePatientModule);
            }
            return count;
        }

        /// <summary>
        /// Retrive Hpf patient Datails.
        /// </summary>
        /// <param name="patientMrn"></param>
        /// <param name="patientFacility"></param>
        /// <returns></returns>
        private long RetrivePatient(string patientMrn, string patientFacility)
        {
            log.EnterFunction();
            try
            {
                FindPatientCriteria searchCriteria = new FindPatientCriteria();
                searchCriteria.FirstName = string.Empty;
                searchCriteria.LastName  = string.Empty;
                searchCriteria.SSN       = string.Empty;
                searchCriteria.EPN       = string.Empty;
                searchCriteria.Encounter = string.Empty;
                searchCriteria.MaxRecord = 500;
                searchCriteria.MRN       = patientMrn;
                searchCriteria.FacilityCode  = patientFacility;                
                FindPatientResult result = PatientController.Instance.FindPatient(searchCriteria);
                return result.PatientSearchResult.Count;
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
                return vaultModeType;
            }
            set
            {
                vaultModeType = value;
            }
        }

        #endregion
    }
}
