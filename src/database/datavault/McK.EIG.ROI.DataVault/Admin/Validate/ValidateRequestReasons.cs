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
    public class ValidateRequestReasons:IVault
    {
        #region Fields

        //DataBase Fields
        private const string RefId = "Ref_ID";
        private const string RequestReasonName = "Reason_Name";
        private const string RequestReasonDisplayText = "Display_Text";
        private const string RequestReasonAttribute = "Attribute";

        private VaultMode vaultModeType;

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Request Reasons Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            ArrayList requestReasonsList = new ArrayList();
            StringBuilder errorMessage = new StringBuilder();
            long recordCount = 1;
            bool isHeaderExist = false;
            string requestReasonsRefID = string.Empty;

            while (reader.Read())
            {
                try
                {
                    try
                    {
                        errorMessage.Remove(0, errorMessage.ToString().Length);
                        requestReasonsRefID = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);
                        ReasonDetails reasonDetail = new ReasonDetails();
                        reasonDetail.Type = ReasonType.Request;
                        reasonDetail.Name = reader[RequestReasonName].ToString();
                        reasonDetail.DisplayText = reader[RequestReasonDisplayText].ToString();
                        string attributeType = reader[RequestReasonAttribute].ToString();
                        switch (attributeType)
                        {
                            case "TPO": reasonDetail.Attribute = RequestAttr.Tpo; break;
                            case "Non-TPO": reasonDetail.Attribute = RequestAttr.NonTpo; break;
                            default:                                
                                errorMessage.Append(DataVaultErrorCodes.InvalidRequestAttribute);
                                break;

                        }

                        if (requestReasonsList.Contains(requestReasonsRefID))
                        {
                            errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                        }
                        else
                        {
                            requestReasonsList.Add(requestReasonsRefID);
                        }
                        //Request Reason Validation
                        ROIAdminValidator validator = new ROIAdminValidator();
                        bool check = validator.ValidateCreate(reasonDetail);
                        if (!check)
                        {
                            errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                        }
                    }
                    catch (FormatException cause)
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
                            "Request Reasons",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(requestReasonsRefID, recordCount, cause.Message);
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
                return DataVaultConstants.RequestReason;
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
