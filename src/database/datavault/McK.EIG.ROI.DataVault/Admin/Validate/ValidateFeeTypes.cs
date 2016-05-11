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
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Validate
{
    public class ValidateFeeTypes : IVault
    {
        #region Fields

        //DBFields
        private const string RefId          = "Ref_ID";
        private const string FTName         = "Fee_Name";
        private const string FTChargeAmount = "Amount";
        private const string FTDescription  = "Description";
        
        private VaultMode vaultModeType;

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Fee Type Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            FeeTypeDetails feeType;

            long recordCount           = 1;
            bool isHeaderExist         = false;
            StringBuilder errorMessage = new StringBuilder();
            ArrayList feeList          = new ArrayList();
            string feetypeRefID        = string.Empty;

            while (reader.Read())
            {
                errorMessage.Remove(0, errorMessage.ToString().Length);
                try
                {
                    try
                    {                        
                        feetypeRefID        = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);
                        feeType             = new FeeTypeDetails();
                        feeType.Name        = Convert.ToString(reader[FTName], CultureInfo.CurrentCulture);
                        feeType.Amount      = (Convert.ToString(reader[FTChargeAmount], CultureInfo.CurrentCulture).Trim().Length == 0) ? 0 :
                                               Convert.ToDouble(reader[FTChargeAmount], CultureInfo.CurrentCulture);
                        feeType.Description = Convert.ToString(reader[FTDescription], CultureInfo.CurrentCulture);
                        if (vaultModeType == VaultMode.Create)
                        {
                            if (feeList.Contains(feetypeRefID))
                            {
                                errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                            }
                            else
                            {
                                feeList.Add(feetypeRefID);
                            }
                        }
                        else
                        {
                            bool isFeeTypeExist = ValidateFeeTypeExist(feetypeRefID);
                            if (!isFeeTypeExist)
                            {
                                errorMessage.Append(string.Format(CultureInfo.CurrentCulture,
                                                    DataVaultErrorCodes.UnknownObject,
                                                    feetypeRefID,
                                                    EntityName + "_" + VaultMode.Create));
                            }
                        }

                        //FeeType Validation
                        BillingAdminValidator validator = new BillingAdminValidator();
                        bool check = validator.ValidateCreate(feeType);
                        if (!check)
                        {
                            errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                        }
                    }
                    catch (InvalidCastException cause)
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
                            "Fee Types",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(feetypeRefID, recordCount, cause.Message);
                }               
                recordCount++;
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            return null;
        }

        private bool ValidateFeeTypeExist(string feetypeRefID)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            long count = Utility.GetCount(EntityName + "_" + VaultMode.Create, RefId, feetypeRefID);
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
                return DataVaultConstants.FeeType;
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
