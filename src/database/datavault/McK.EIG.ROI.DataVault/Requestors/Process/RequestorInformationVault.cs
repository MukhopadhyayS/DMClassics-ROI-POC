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
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Globalization;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.Controller;

using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Requestors.Process
{
    /// <summary>
    /// Class inserts the requestor information.
    /// </summary>
    public class RequestorInformationVault : IVault
    {
        Log log = LogFactory.GetLogger(typeof(RequestorInformationVault));

        #region Fields

        //DBFields
        private const string RefID               = "Ref_ID";
        private const string Prepayment          = "PrePay_Req";
        private const string CertificationLetter = "CertLet_Req";
        private const string RequestorType       = "Reqor_Type"; 
        private const string RequestorName       = "Reqor_Name";
        private const string RequestorLastName   = "Reqor_LastName";
        private const string RequestorFirstname  = "Reqor_FirstName";
        private const string RequestorActive     = "Reqor_Active";

        private const string PateintEPN = "Pt_EPN";
        private const string PateintDOB = "Pt_DOB";
        private const string PateintSSN = "Pt_SSN";
        private const string PatientMRN = "Pt_MRN";
        private const string PatientFacility = "Pt_Facility_Code";

        
        private const string MainAddress1      = "MainAddr1";
        private const string MainAddress2      = "MainAddr2";
        private const string MainAddress3      = "MainAddr3";
        private const string MainCity          = "MainCity";
        private const string MainState         = "MainState";
        private const string MainZIP           = "MainZip";
        private const string AlternateAddress1 = "AltAddr1";
        private const string AlternateAddress2 = "AltAddr2";
        private const string AlternateAddress3 = "AltAddr3";
        private const string AlternateCity     = "AltCity";
        private const string AlternateState    = "AltState";
        private const string AlternateZIP      = "AltZip";

        private const string HomePhone    = "Reqor_Home_Ph";
        private const string WorkPhone    = "Reqor_Work_Ph";
        private const string CellPhone    = "Reqor_Cell_Ph";
        private const string Email        = "Reqor_Email";
        private const string Fax          = "Reqor_Fax";
        private const string ContactName  = "Cont_Name";
        private const string ContactPhone = "Cont_Ph";
        private const string ContactEmail = "Cont_Email";
                
        private Hashtable requestorHT;

        private VaultMode modeType;

        #endregion

        #region Constructor

        public RequestorInformationVault()
        {
            requestorHT = new Hashtable(new VaultComparer());
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Requestor Information Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();            
            long recordCount;
            try
            {
                recordCount = 1;                
                RequestorDetails requestorDetails;

                while (reader.Read())
                {
                    string requestorRefID = Convert.ToString(reader[RefID], CultureInfo.CurrentCulture);
                    if (modeType == VaultMode.Update)
                    {
                        if (!requestorHT.ContainsKey(requestorRefID))
                        {
                            string errorMeg = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, requestorRefID, EntityName);
                            log.Debug(errorMeg);
                            throw new VaultException(errorMeg);
                        }
                        requestorDetails = RetriveRequestor(Convert.ToInt64(requestorHT[requestorRefID], CultureInfo.CurrentCulture));
                    }
                    else
                    {
                        requestorDetails = new RequestorDetails();
                        RequestorTypeDetails typeDetails = GetRequestorTypeId(reader[RequestorType].ToString());
                        requestorDetails.Type = typeDetails.Id;
                        requestorDetails.TypeName = typeDetails.Name;
                    }
                    
                    requestorDetails.IsActive           = reader[RequestorActive].ToString().Equals("True") ? true : false;
                    requestorDetails.PrepaymentRequired = (reader[Prepayment].ToString().Length == 0) ? false :
                                                          Convert.ToBoolean(reader[Prepayment], CultureInfo.CurrentCulture);
                    requestorDetails.LetterRequired     = (reader[CertificationLetter].ToString().Length == 0) ? false :
                                                          Convert.ToBoolean(reader[CertificationLetter], CultureInfo.CurrentCulture);
                    if (requestorDetails.Type < 0)
                    {
                        requestorDetails.FirstName = reader[RequestorFirstname].ToString();
                        requestorDetails.LastName = reader[RequestorLastName].ToString();
                        requestorDetails.Name = requestorDetails.LastName + ", " + requestorDetails.FirstName;

                        string epn = reader[PateintEPN].ToString().Trim();
                        if (epn.Length > 0)
                        {
                            if (epn.StartsWith(UserData.Instance.EpnPrefix, true, CultureInfo.CurrentCulture))
                            {
                                requestorDetails.PatientEPN = epn;
                            }
                            else
                            {
                                requestorDetails.PatientEPN = UserData.Instance.EpnPrefix.Trim() + epn;
                            }
                        }

                        if (reader[PateintDOB].ToString().Length != 0)
                        {
                            requestorDetails.PatientDob = Convert.ToDateTime(reader[PateintDOB], CultureInfo.CurrentCulture);
                        }
                        requestorDetails.PatientSSN = reader[PateintSSN].ToString();
                        if (reader.FieldCount > 30)
                        {
                            requestorDetails.PatientMRN = reader[PatientMRN].ToString();
                            requestorDetails.PatientFacilityCode = reader[PatientFacility].ToString();
                            FacilityDetails facility = new FacilityDetails(requestorDetails.PatientFacilityCode, FacilityType.Hpf);

                            int index = UserData.Instance.Facilities.IndexOf(facility);
                            requestorDetails.IsFreeformFacility = index > 0 ? false : true;
                        }
                    }
                    else
                    {
                        requestorDetails.Name = reader[RequestorName].ToString();
                    }

                    requestorDetails.HomePhone    = reader[HomePhone].ToString();
                    requestorDetails.WorkPhone    = reader[WorkPhone].ToString();
                    requestorDetails.CellPhone    = reader[CellPhone].ToString();
                    requestorDetails.Email        = reader[Email].ToString();
                    requestorDetails.Fax          = reader[Fax].ToString();
                    requestorDetails.ContactName  = reader[ContactName].ToString();
                    requestorDetails.ContactPhone = reader[ContactPhone].ToString();
                    requestorDetails.ContactEmail = reader[ContactEmail].ToString();
                    //requestorDetails.IsActive     = true;

                    //Main Address
                    AddressDetails mainAddress = new AddressDetails();
                    mainAddress.Address1       = reader[MainAddress1].ToString();
                    mainAddress.Address2       = reader[MainAddress2].ToString();
                    mainAddress.Address3       = reader[MainAddress3].ToString();
                    mainAddress.City           = reader[MainCity].ToString();
                    mainAddress.State          = reader[MainState].ToString();
                    mainAddress.PostalCode     = reader[MainZIP].ToString();

                    //Aternate Address
                    AddressDetails alternateAddress = new AddressDetails();
                    alternateAddress.Address1       = reader[AlternateAddress1].ToString();
                    alternateAddress.Address2       = reader[AlternateAddress2].ToString();
                    alternateAddress.Address3       = reader[AlternateAddress3].ToString();
                    alternateAddress.City           = reader[AlternateCity].ToString();
                    alternateAddress.State          = reader[AlternateState].ToString();
                    alternateAddress.PostalCode     = reader[AlternateZIP].ToString();

                    requestorDetails.MainAddress = mainAddress;
                    requestorDetails.AltAddress  = alternateAddress;

                    long requestorID = 0;
                    if (modeType == VaultMode.Create)
                    {
                        //Save the Requestor Information object.
                        requestorID = SaveRequestorInformation(requestorDetails, recordCount);
                        //Map the patient reference id with patient id
                        requestorHT.Add(requestorRefID, requestorID);
                    }
                    else
                    {
                        UpdateRequestor(requestorDetails, recordCount);
                    }
                    
                    string message = DataVaultErrorCodes.ReferenceID;
                    message = string.Format(CultureInfo.CurrentCulture, 
                                            message, 
                                            DataVaultConstants.Requestor, 
                                            reader[RefID].ToString(), 
                                            requestorID);
                    log.Debug(message);
                    recordCount++;
                }
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
            finally
            {
                reader.Close();
            }
            log.ExitFunction();
            return requestorHT;
        }

        /// <summary>
        /// Return Id for the given Requestor Type Name.
        /// </summary>
        /// <param name="requestorTypeName">Requestor Type Name.</param>
        /// <returns>Requestor Type ID</returns>
        private RequestorTypeDetails GetRequestorTypeId(string requestorTypeRefId)
        {
            log.EnterFunction();
            if (string.IsNullOrEmpty(requestorTypeRefId))
            {
                throw new VaultException(DataVaultErrorCodes.RequestorTypeEmpty);
            }
            RequestorTypeDetails typeDetails = (RequestorTypeDetails)AdminVault.GetEntityObject(DataVaultConstants.RequestorTypeGeneral, requestorTypeRefId);
            log.ExitFunction();
            return typeDetails;   
        }

        private RequestorDetails RetriveRequestor(long requestorId)
        {
            log.EnterFunction();
            try
            {
                return RequestorController.Instance.RetrieveRequestor(requestorId);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
        }

        /// <summary>
        /// Passes the Requestor information object to the RequestorController for further process
        /// </summary>
        /// <param name="patientDetails">Requestor Info Details.</param>
        /// <param name="recordCount">Record Count</param>
        private long SaveRequestorInformation(RequestorDetails requestorDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));

                //Call the RequestorController to save the RequestorInformation.
                long id = RequestorController.Instance.CreateRequestor(requestorDetails);
                requestorDetails.Id = id;

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

        private void UpdateRequestor(RequestorDetails requestorDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));

                //Call the RequestorController to save the RequestorInformation.
                RequestorController.Instance.UpdateRequestor(requestorDetails);                

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
            get { return DataVaultConstants.RequestorInfo; }
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
