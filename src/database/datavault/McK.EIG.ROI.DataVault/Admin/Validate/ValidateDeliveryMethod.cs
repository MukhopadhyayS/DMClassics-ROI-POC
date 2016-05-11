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

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.DataVault.Base;


namespace McK.EIG.ROI.DataVault.Admin.Validate
{
    public class ValidateDeliveryMethod:IVault
    {
        #region Fields

        private VaultMode vaultModeType;

        private const string RefId = "Ref_ID";
        private const string DMName = "Name";
        private const string DMDescription = "Description";
        private const string DMUrl = "URL";
        private const string DMIsDefault = "Default";
        private const string SystemSeed = "IsSystemSeed";

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Delivery Method Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            ArrayList deliveryMethodList = new ArrayList();
            StringBuilder errorMessage = new StringBuilder();
            long recordCount = 1;
            bool isHeaderExist = false;
            string deliveryMethodRefID = string.Empty;

            while (reader.Read())
            {
                try
                {
                    errorMessage.Remove(0, errorMessage.ToString().Length);
                    string url = reader[DMUrl].ToString();
                    DeliveryMethodDetails deliveryMethod = new DeliveryMethodDetails();
                    deliveryMethod.Name = reader[DMName].ToString();
                    deliveryMethod.Description = reader[DMDescription].ToString();
                    deliveryMethod.Url = (url.Length == 0) ? null : new System.Uri(url);
                    deliveryMethod.IsDefault = reader[DMIsDefault].ToString().Length != 0 ?
                                                 Convert.ToBoolean(reader[DMIsDefault], CultureInfo.CurrentCulture) :
                                                 false;
                    deliveryMethodRefID = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture).Trim();

                    //Delivery Method Validation
                    ROIAdminValidator validator = new ROIAdminValidator();
                    bool check = validator.ValidateCreate(deliveryMethod);
                    if (!check)
                    {
                        errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                    }

                    if (vaultModeType == VaultMode.Create)
                    {
                        if (deliveryMethodList.Contains(deliveryMethodRefID))
                        {
                            errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                        }
                        else
                        {
                            deliveryMethodList.Add(deliveryMethodRefID);
                        }
                    }
                    else
                    {
                        bool isDeliveryMethodExist = ValidateDeliveryMethodExist(deliveryMethodRefID);
                        if (!isDeliveryMethodExist)
                        {
                            errorMessage.Append(string.Format(CultureInfo.CurrentCulture,
                                                          DataVaultErrorCodes.UnknownObject,
                                                          deliveryMethodRefID,
                                                          EntityName + "_" + VaultMode.Create));
                        }
                    }

                    if (errorMessage.ToString().Trim().Length > 0)
                    {
                        throw new VaultException(errorMessage.ToString().Trim());
                    }

                    recordCount++;
                }
                catch (VaultException cause)
                {
                    if (!isHeaderExist)
                    {
                        isHeaderExist = true;
                        ValidateUtility.WriteLog("Admin",
                            (vaultModeType == VaultMode.Create) ? DataVaultConstants.CreateAdminModule : DataVaultConstants.UpdateAdminModule,
                            "Delivery Method",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(deliveryMethodRefID, recordCount, cause.Message);
                }                
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            return null;
        }

        private bool ValidateDeliveryMethodExist(string deliveryMethodRefID)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            long count = Utility.GetCount(EntityName + "_" + VaultMode.Create, RefId, deliveryMethodRefID);
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateAdminModule);
            return (count > 0);
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
                return DataVaultConstants.DeliveryMethod;
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
