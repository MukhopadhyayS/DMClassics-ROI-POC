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
using System.Data;
using System.Globalization;
using System.Text;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;

using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.Client.Patient.Controller;


namespace McK.EIG.ROI.DataVault.Patient.Validate
{
    /// <summary>
    /// Class inserts the supplemental patient, associated supplemental patient document 
    /// and supplemental patient document for the HPF patient.
    /// </summary>
    public class ValidatePatientInformation : IVault
    {
        #region Fields
        
        private const string ReferenceID      = "Ref_ID";
        private const string VIPStatus        = "VIP";
        private const string PatientLastName  = "LastName";
        private const string PatientFirstName = "FirstName";
        private const string PatientGender = "Gender";
        private const string DOB           = "DOB";
        private const string SSN           = "SSN";
        private const string EPN           = "EPN";
        private const string Facility      = "Facility_Code";
        private const string MRN           = "MRN";
        private const string Address1      = "Pt_Addr1";
        private const string Address2      = "Pt_Addr2";
        private const string Address3      = "Pt_Addr3";
        private const string City          = "Pt_City";
        private const string State         = "Pt_State";
        private const string ZIP           = "Pt_ZIP";
        private const string HomePhone     = "Pt_Home_Phone";
        private const string WorkPhone     = "Pt_Work_Phone";

        private VaultMode vaultModeType;

        #endregion
       

        #region Methods

        /// <summary>
        /// System Load the Patient Information Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            PatientDetails patientDetails;

            long recordCount           = 1;
            bool isHeaderExist         = false;
            StringBuilder errorMessage = new StringBuilder();
            ArrayList list             = new ArrayList();
            string patientRefId        = string.Empty;
            
            while (reader.Read())
            {
                try
                {
                    try
                    {
                        errorMessage.Remove(0, errorMessage.ToString().Length);
                        patientDetails = new PatientDetails();

                        patientRefId = reader[ReferenceID].ToString();

                        patientDetails.IsVip = (reader[VIPStatus].ToString().Length == 0) ? false :
                                                   Convert.ToBoolean(reader[VIPStatus], CultureInfo.CurrentCulture);

                        patientDetails.LastName  = reader[PatientLastName].ToString();
                        patientDetails.FirstName = reader[PatientFirstName].ToString();
                        patientDetails.FullName  = patientDetails.LastName + ", " + patientDetails.FirstName;

                        patientDetails.SSN = reader[SSN].ToString();
                        string epn = reader[EPN].ToString();
                        patientDetails.EPN = (epn.Trim().Length > 0 && UserData.Instance.EpnPrefix != null)
                                                    ? UserData.Instance.EpnPrefix + reader[EPN].ToString()
                                                    : reader[EPN].ToString();

                        patientDetails.FacilityCode = reader[Facility].ToString();
                        patientDetails.MRN = reader[MRN].ToString();
                        patientDetails.HomePhone = reader[HomePhone].ToString();
                        patientDetails.WorkPhone = reader[WorkPhone].ToString();
                        if (reader[DOB].ToString().Trim().Length != 0)
                        {
                            patientDetails.DOB = DateTime.Parse(reader[DOB].ToString(), CultureInfo.CurrentCulture);
                        }
                        switch (reader[PatientGender].ToString())
                        {
                            case "Male": patientDetails.Gender = Gender.Male; break;
                            case "Female": patientDetails.Gender = Gender.Female; break;
                            case "Unknown": patientDetails.Gender = Gender.Unknown; break;
                            default: patientDetails.Gender = Gender.None; break;
                        }

                        AddressDetails addressDetails = new AddressDetails();
                        addressDetails.Address1 = reader[Address1].ToString();
                        addressDetails.Address2 = reader[Address2].ToString();
                        addressDetails.Address3 = reader[Address3].ToString();
                        addressDetails.City = reader[City].ToString();
                        addressDetails.State = reader[State].ToString();
                        addressDetails.PostalCode = reader[ZIP].ToString();

                        patientDetails.Address = addressDetails;

                        PatientValidator validator = new PatientValidator();
                        bool check = validator.Validate(patientDetails);
                        if (!check)
                        {
                            errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                        }


                        if (vaultModeType == VaultMode.Create)
                        {
                            if (list.Contains(patientRefId))
                            {
                                errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                            }
                            else
                            {
                                list.Add(patientRefId);
                            }
                        }
                        else
                        {
                            bool isPatientExist = ValidatePatientExist(patientRefId);
                            if (!isPatientExist)
                            {
                                errorMessage.Append(string.Format(CultureInfo.CurrentCulture,
                                                    DataVaultErrorCodes.UnknownObject,
                                                    patientRefId,
                                                    EntityName + "_" + Mode.Create));
                            }
                        }
                    }
                    catch (FormatException cause)
                    {
                        errorMessage.Append(cause.Message);
                    }
                    catch (InvalidCastException cause)
                    {
                        errorMessage.Append(cause.Message);
                    }
                    catch (ArgumentException cause)
                    {
                        errorMessage.Append(cause.Message);
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
                            "Patient Information",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(patientRefId, recordCount, cause.Message);
                }               
                recordCount++;
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            return null;

        }

        private bool ValidatePatientExist(object patientRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreatePatientModule);
            long count = Utility.GetCount(EntityName + "_" + Mode.Create, ReferenceID, patientRefId);
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdatePatientModule);
            return (count > 0);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get { return DataVaultConstants.PatientInfo; }
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
