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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Validate
{
    public class ValidateRequestStatusReasons:IVault
    {
        #region Fields

        //DataBase Fields
        private const string RefId = "Ref_ID";
        private const string StatusReasonName = "Reason_Name";
        private const string StatusReasonDisplayText = "Display_Text";
        private const string StatusReasonStatus = "Request_Status";

        private VaultMode vaultModeType;

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Request Status Reasons Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            ArrayList requestStatusReasonsList = new ArrayList();
            StringBuilder errorMessage = new StringBuilder();
            long recordCount = 1;
            bool isHeaderExist = false;
            string requestStatusReasonsRefID = string.Empty;

            while (reader.Read())
            {
                try
                {
                    try
                    {
                        errorMessage.Remove(0, errorMessage.ToString().Length);
                        requestStatusReasonsRefID = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);
                        ReasonDetails reasonDetail = new ReasonDetails();
                        reasonDetail.Type = ReasonType.Status;
                        reasonDetail.Name = reader[StatusReasonName].ToString();
                        reasonDetail.DisplayText = reader[StatusReasonDisplayText].ToString();
                        reasonDetail.RequestStatus = (RequestStatus)Enum.Parse(typeof(RequestStatus),
                                                                               reader[StatusReasonStatus].ToString(),
                                                                               true);

                        if (requestStatusReasonsList.Contains(requestStatusReasonsRefID))
                        {
                            errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                        }
                        else
                        {
                            requestStatusReasonsList.Add(requestStatusReasonsRefID);
                        }                      

                        //Request Status Reason Validation
                        ROIAdminValidator validator = new ROIAdminValidator();
                        bool check = validator.ValidateCreate(reasonDetail);
                        if (!check)
                        {
                            errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                        }
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
                        ValidateUtility.WriteLog("Admin",
                            (vaultModeType == VaultMode.Create) ? DataVaultConstants.CreateAdminModule : DataVaultConstants.UpdateAdminModule,
                            "Request Status Reasons",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(requestStatusReasonsRefID, recordCount, cause.Message);
                }                
                recordCount++;
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            return null;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get
            {
                return DataVaultConstants.StatusReason;
            }
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
