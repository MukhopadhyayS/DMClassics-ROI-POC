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
using System.Data.Common;
using System.Globalization;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.Controller;

using McK.EIG.ROI.DataVault.Base;
using System.Collections.ObjectModel;
using System.Configuration;

namespace McK.EIG.ROI.DataVault.Patient.Process
{
    /// <summary>
    /// Class inserts the supplemental patient, associated supplemental patient document 
    /// and supplemental patient document for the HPF patient.
    /// </summary>
    public class PatientInformationVault : IVault
    {
        #region Fields

        Log log = LogFactory.GetLogger(typeof(PatientInformationVault));

        //DBFields
        private const string ReferenceID   = "Ref_ID";
        private const string VIPStatus     = "VIP";
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

        private VaultMode modeType;
        private Hashtable patientInfoHT;

        #endregion

        #region Constructor

        public PatientInformationVault()
        {
            patientInfoHT = new Hashtable(new VaultComparer());
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Patient Information Vault.
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
                    string patientRefId = reader[ReferenceID].ToString();

                    if (modeType == VaultMode.Update)
                    {
                        if (!patientInfoHT.ContainsKey(patientRefId))
                        {
                            string errorMeg = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, patientRefId, EntityName);
                            log.Debug(errorMeg);
                            throw new VaultException(errorMeg);
                        }                        
                        patientDetails = RetrivePatient(Convert.ToInt64(patientInfoHT[patientRefId], CultureInfo.CurrentCulture));
                    }
                    else
                    {
                        patientDetails = new PatientDetails();
                    }

                    
                    patientDetails.IsVip     = (reader[VIPStatus].ToString().Length == 0) ? false :
                                               Convert.ToBoolean(reader[VIPStatus], CultureInfo.CurrentCulture);

                    patientDetails.LastName  = reader[PatientLastName].ToString();
                    patientDetails.FirstName = reader[PatientFirstName].ToString();
                    patientDetails.FullName  = patientDetails.LastName + ", " + patientDetails.FirstName;
                    patientDetails.SSN       = reader[SSN].ToString();

                    string epn = reader[EPN].ToString();                    
                    patientDetails.EPN       = (epn.Trim().Length > 0 && UserData.Instance.EpnPrefix != null) 
                                                ? UserData.Instance.EpnPrefix + reader[EPN].ToString() 
                                                : reader[EPN].ToString();

                    patientDetails.FacilityCode  = reader[Facility].ToString();
                    FacilityDetails facility = new FacilityDetails(patientDetails.FacilityCode, FacilityType.Hpf);

                    int index = UserData.Instance.Facilities.IndexOf(facility);
                    patientDetails.IsFreeformFacility = index > 0 ? false : true;
                    patientDetails.MRN       = reader[MRN].ToString();
                    patientDetails.HomePhone = reader[HomePhone].ToString();
                    patientDetails.WorkPhone = reader[WorkPhone].ToString();
                    if (reader[DOB].ToString().Trim().Length != 0)
                    {
                        patientDetails.DOB = DateTime.Parse(reader[DOB].ToString(), CultureInfo.CurrentCulture);
                    }
                    switch (reader[PatientGender].ToString())
                    {
                        case "Male"   : patientDetails.Gender = Gender.Male; break;
                        case "Female" : patientDetails.Gender = Gender.Female; break;
                        case "Unknown": patientDetails.Gender = Gender.Unknown; break;
                        default : patientDetails.Gender = Gender.None; break; 
                    }

                    AddressDetails addressDetails = new AddressDetails();
                    addressDetails.Address1   = reader[Address1].ToString();
                    addressDetails.Address2   = reader[Address2].ToString();
                    addressDetails.Address3   = reader[Address3].ToString();
                    addressDetails.City       = reader[City].ToString();
                    addressDetails.State      = reader[State].ToString();
                    addressDetails.PostalCode = reader[ZIP].ToString();

                    patientDetails.Address = addressDetails;

                    long patientId = 0;

                    if (modeType == VaultMode.Create)
                    {
                        //Save the Patient Information object.
                        patientId = SavePatientInformation(patientDetails, recordCount);
                        //Map the patient reference id with patient id
                        patientInfoHT.Add(patientRefId, patientId);
                    }
                    else
                    {
                        UpdatePatient(patientDetails, recordCount);
                    }

                    string message = DataVaultErrorCodes.ReferenceID;
                    message = string.Format(CultureInfo.CurrentCulture,
                                            message,
                                            DataVaultConstants.Patient,
                                            reader[ReferenceID].ToString(),
                                            patientDetails.Id);
                    log.Debug(message);
                    recordCount++;
                }

                log.ExitFunction();
                return patientInfoHT;
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
        /// Passes the patient information object to the PatientController for further process
        /// </summary>
        /// <param name="patientDetails">Patient Info Details.</param>
        /// <param name="recordCount">Record Count</param>
        private long SavePatientInformation(PatientDetails patientDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));

                //Call the PatientController to save the PatientInformation.
                long id = PatientController.Instance.CreateSupplemental(patientDetails);
                patientDetails.Id = id;

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
                log.ExitFunction();
                return id;
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
        }

        private void UpdatePatient(PatientDetails patientDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));

                //Call the PatientController to save the PatientInformation.
                PatientController.Instance.UpdateSupplemental(patientDetails);
                
                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
                log.ExitFunction();                
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
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
