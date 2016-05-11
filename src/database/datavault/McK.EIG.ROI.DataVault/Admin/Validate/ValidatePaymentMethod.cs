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
    public class ValidatePaymentMethod:IVault
    {
        #region Fields

        //DataBase Fields
        private const string RefId = "Ref_ID";
        private const string PMName = "Payment_Name";
        private const string PMDescription = "Description";
        private const string PMIsDisplay = "Form_Field";
        private const string SystemSeed = "IsSystemSeed";

        private VaultMode vaultModeType;

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Payment Method Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            ArrayList paymentMethodList = new ArrayList();
            StringBuilder errorMessage = new StringBuilder();
            long recordCount = 1;
            bool isHeaderExist = false;
            string paymentMethodRefID = string.Empty;

            while (reader.Read())
            {
                try
                {
                    errorMessage.Remove(0, errorMessage.ToString().Length);
                    PaymentMethodDetails paymentDetail = new PaymentMethodDetails();
                    paymentDetail.Name = reader[PMName].ToString();
                    paymentDetail.Description = reader[PMDescription].ToString();
                    paymentDetail.IsDisplay = reader[PMIsDisplay].ToString().Length != 0 ?
                                                Convert.ToBoolean(reader[PMIsDisplay], CultureInfo.CurrentCulture) :
                                                false;
                    paymentMethodRefID = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture).Trim();

                    //Payment Method Validation
                    BillingAdminValidator validator = new BillingAdminValidator();
                    bool check = validator.ValidateCreate(paymentDetail);
                    if (!check)
                    {
                        errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                    }

                    if (vaultModeType == VaultMode.Create)
                    {
                        if (paymentMethodList.Contains(paymentMethodRefID))
                        {
                            errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                        }
                        else
                        {
                            paymentMethodList.Add(paymentMethodRefID);
                        }
                    }
                    else
                    {
                        bool isPaymentMethodExist = ValidatePaymentMethodExist(paymentMethodRefID);
                        if (!isPaymentMethodExist)
                        {
                            errorMessage.Append(string.Format(CultureInfo.CurrentCulture,
                                                          DataVaultErrorCodes.UnknownObject,
                                                          paymentMethodRefID,
                                                          EntityName + "_" + VaultMode.Create));
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
                        ValidateUtility.WriteLog("Admin",
                            (vaultModeType == VaultMode.Create) ? DataVaultConstants.CreateAdminModule : DataVaultConstants.UpdateAdminModule,
                            "Payment Method",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(paymentMethodRefID, recordCount, cause.Message);
                }
                recordCount++;
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            return null;
        }

        private bool ValidatePaymentMethodExist(string paymentMethodRefID)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            long count = Utility.GetCount(EntityName + "_" + VaultMode.Create, RefId, paymentMethodRefID);
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
                return DataVaultConstants.PaymentMethod;
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
