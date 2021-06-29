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
using System.Text;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.Controller;

using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Requestors.Validate
{
    /// <summary>
    /// Class inserts the requestor information.
    /// </summary>
    public class ValidateRequestorInformation : IVault
    {
        Log log = LogFactory.GetLogger(typeof(ValidateRequestorInformation));

        #region Fields

        //DBFields
        private const string RefID               = "Ref_ID";
        private const string Prepayment          = "PrePay_Req";
        private const string CertificationLetter = "CertLet_Req";
        private const string RequestorType       = "Reqor_Type"; 
        private const string RequestorName       = "Reqor_Name";
        private const string RequestorLastName   = "Reqor_LastName";
        private const string RequestorFirstname  = "Reqor_FirstName";

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

        private VaultMode vaultModeType;

        #endregion        

        #region Methods

        /// <summary>
        /// System Load the Requestor Information Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();
            RequestorDetails requestorDetails;

            long recordCount           = 1;
            bool isHeaderExist         = false;
            StringBuilder errorMessage = new StringBuilder();
            ArrayList list             = new ArrayList();
            string requestorRefID      = string.Empty;

            while (reader.Read())
            {
                try
                {
                    try
                    {

                        errorMessage.Remove(0, errorMessage.ToString().Length);
                        requestorRefID = Convert.ToString(reader[RefID], CultureInfo.CurrentCulture);
                        requestorDetails = new RequestorDetails();
                        if (vaultModeType == VaultMode.Create)
                        {
                            RequestorTypeDetails typeDetails = GetRequestorTypeId(reader[RequestorType].ToString());
                            requestorDetails.Type = typeDetails.Id;
                            requestorDetails.TypeName = typeDetails.Name;
                        }
                        else
                        {
                            if (!(string.IsNullOrEmpty(reader[RequestorFirstname].ToString())) ||
                                !(string.IsNullOrEmpty(reader[RequestorLastName].ToString())))
                            {
                                requestorDetails.Type = -1;
                            }
                            else
                            {
                                requestorDetails.Type = 1;
                            }
                            requestorDetails.TypeName = "Test";
                        }
                        
                        requestorDetails.PrepaymentRequired = (reader[Prepayment].ToString().Length == 0) ? false :
                                                              Convert.ToBoolean(reader[Prepayment], CultureInfo.CurrentCulture);
                        requestorDetails.LetterRequired = (reader[CertificationLetter].ToString().Length == 0) ? false :
                                                              Convert.ToBoolean(reader[CertificationLetter], CultureInfo.CurrentCulture);
                        if (requestorDetails.Type < 0)
                        {
                            requestorDetails.FirstName = reader[RequestorFirstname].ToString();
                            requestorDetails.LastName  = reader[RequestorLastName].ToString();
                            requestorDetails.Name      = requestorDetails.LastName + ", " + requestorDetails.FirstName;

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
                            }
                        }
                        else
                        {
                            requestorDetails.Name = reader[RequestorName].ToString();
                        }

                        requestorDetails.HomePhone = reader[HomePhone].ToString();
                        requestorDetails.WorkPhone = reader[WorkPhone].ToString();
                        requestorDetails.CellPhone = reader[CellPhone].ToString();
                        requestorDetails.Email = reader[Email].ToString();
                        requestorDetails.Fax = reader[Fax].ToString();
                        requestorDetails.ContactName = reader[ContactName].ToString();
                        requestorDetails.ContactPhone = reader[ContactPhone].ToString();
                        requestorDetails.ContactEmail = reader[ContactEmail].ToString();
                        requestorDetails.IsActive = true;

                        //Main Address
                        AddressDetails mainAddress = new AddressDetails();
                        mainAddress.Address1 = reader[MainAddress1].ToString();
                        mainAddress.Address2 = reader[MainAddress2].ToString();
                        mainAddress.Address3 = reader[MainAddress3].ToString();
                        mainAddress.City = reader[MainCity].ToString();
                        mainAddress.State = reader[MainState].ToString();
                        mainAddress.PostalCode = reader[MainZIP].ToString();

                        //Aternate Address
                        AddressDetails alternateAddress = new AddressDetails();
                        alternateAddress.Address1 = reader[AlternateAddress1].ToString();
                        alternateAddress.Address2 = reader[AlternateAddress2].ToString();
                        alternateAddress.Address3 = reader[AlternateAddress3].ToString();
                        alternateAddress.City = reader[AlternateCity].ToString();
                        alternateAddress.State = reader[AlternateState].ToString();
                        alternateAddress.PostalCode = reader[AlternateZIP].ToString();

                        requestorDetails.MainAddress = mainAddress;
                        requestorDetails.AltAddress = alternateAddress;

                        RequestorValidator validator = new RequestorValidator();
                        bool check = validator.Validate(requestorDetails);
                        if (!check)
                        {
                            errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                        }

                        if (vaultModeType == VaultMode.Create)
                        {
                            if (list.Contains(requestorRefID))
                            {
                                errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                            }
                            else
                            {
                                list.Add(requestorRefID);
                            }
                        }
                        else
                        {
                            bool isRequestorExist = ValidateRequestorExist(requestorRefID);
                            if (!isRequestorExist)
                            {
                                errorMessage.Append(string.Format(CultureInfo.CurrentCulture,
                                                    DataVaultErrorCodes.UnknownObject,
                                                    requestorRefID,
                                                    EntityName + "_" + VaultMode.Create));
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
                        ValidateUtility.WriteLog("Requestor",
                            (vaultModeType == VaultMode.Create) ? DataVaultConstants.CreateRequestorModule : DataVaultConstants.UpdateRequestorModule,
                            "Requestor Information",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(requestorRefID, recordCount, cause.Message);
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

        private bool ValidateRequestorExist(string requestorRefID)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestorModule);
            long count = Utility.GetCount(EntityName + "_" + VaultMode.Create, RefID, requestorRefID);
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestorModule);
            return (count > 0);
        }       

        private RequestorTypeDetails GetRequestorTypeId(string requestorTypeRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);

            string entity = DataVaultConstants.RequestorTypeGeneral + "_" + VaultMode.Create;
            string query = (DataVaultConstants.IsExcelFile)
                          ? "SELECT * FROM [" + entity + "$] WHERE " + RefID + "= '" + requestorTypeRefId + "'"
                          : "SELECT * FROM " + entity + ".csv WHERE " + RefID + "= '" + requestorTypeRefId + "'";
            IDataReader reader = Utility.ReadData(EntityName, query);
            RequestorTypeDetails details = new RequestorTypeDetails();
            details.Id = 0;
            details.Name = string.Empty;
            while (reader.Read())
            {
                string requestor = Convert.ToString(reader["Requestor_Type"], CultureInfo.CurrentCulture);
                details.Id = (requestor == "Patient") ? -1 : 1;
                details.Name = requestor;
            }
            if (!reader.IsClosed)
            {                
                reader.Close();
            }

            if (DataVaultConstants.IsExcelFile)
            {
                if (vaultModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestorModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestorModule);
                }
            }
            
            return details;
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
