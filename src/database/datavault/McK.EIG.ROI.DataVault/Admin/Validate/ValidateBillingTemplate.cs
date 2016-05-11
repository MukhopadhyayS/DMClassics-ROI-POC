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
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Validate
{
    public class ValidateBillingTemplate:IVault
    {
        #region Fields

        //DBFields
        private const string RefId      = "Ref_ID";
        private const string BTName     = "Templ_Name";
        private const string BTFeeTypes = "Fee_Type";

        private const string FTName     = "Fee_Name";
        
        private VaultMode vaultModeType;

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Billing Template Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            BillingTemplateDetails billingTemplate;

            long recordCount           = 1;            
            bool isHeaderExist         = false;
            StringBuilder errorMessage = new StringBuilder(); 
            ArrayList billingList      = new ArrayList();
            string templateRefId       = string.Empty;
            

            while (reader.Read())
            {
                errorMessage.Remove(0, errorMessage.ToString().Length);
                try
                {
                    templateRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);
                    billingTemplate = new BillingTemplateDetails();
                    billingTemplate.Name = Convert.ToString(reader[BTName], CultureInfo.CurrentCulture);

                    GetLinkedFeeTypes(billingTemplate, Convert.ToString(reader[BTFeeTypes], CultureInfo.CurrentCulture).Trim(), errorMessage);

                    BillingAdminValidator validator = new BillingAdminValidator();
                    bool check = validator.ValidateCreate(billingTemplate);
                    if (!check)
                    {
                        errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                    }

                    if (vaultModeType == VaultMode.Create)
                    {
                        if (billingList.Contains(templateRefId))
                        {
                            errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                        }
                        else
                        {
                            billingList.Add(templateRefId);
                        }
                    }
                    else
                    {
                        bool isTemplateExist = ValidateTemplateExist(templateRefId);
                        if (!isTemplateExist)
                        {
                            errorMessage.Append(string.Format(CultureInfo.CurrentCulture,
                                                DataVaultErrorCodes.UnknownObject,
                                                templateRefId,
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
                            "Billing Template",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(templateRefId, recordCount, cause.Message);
                }              
                recordCount++;
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            return null;
        }

        private bool ValidateTemplateExist(string templateRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            long count = Utility.GetCount(EntityName + "_" + VaultMode.Create, RefId, templateRefId);
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateAdminModule);
            return (count > 0);
        }

        /// <summary>
        /// Get the associated Fee Types for the Billing template.
        /// </summary>
        /// <param name="feeTypes"></param>
        /// <returns></returns>
        private void GetLinkedFeeTypes(BillingTemplateDetails template, string feeTypes, StringBuilder errorMessage)
        {   
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            template.AssociatedFeeTypes.Clear();
            string[] feeTypeRefId = feeTypes.Split('~');
            ArrayList feeTypeList = new ArrayList(feeTypeRefId);

            string entity = DataVaultConstants.FeeType + "_" + VaultMode.Create;
            string query = (DataVaultConstants.IsExcelFile)
                            ? "SELECT * FROM [" + entity + "$] WHERE "+RefId+" IN ("
                            : "SELECT * FROM " + entity + ".csv WHERE "+RefId+" IN (";
            foreach (string feeId in feeTypeList)
            {
                query += "'" + feeId + "',";
            }
            query = query.Substring(0, query.Length - 1);
            query += ")";

            using (IDataReader feeTypeReader = Utility.ReadData(EntityName + "_" + vaultModeType, query))
            {
                long count = 1;
                while (feeTypeReader.Read())
                {
                    string feeID = Convert.ToString(feeTypeReader[RefId], CultureInfo.CurrentCulture);
                    if (feeTypeList.Contains(feeID))
                    {
                        feeTypeList.Remove(feeID);
                    }

                    AssociatedFeeType associatedFeetype = new AssociatedFeeType();
                    associatedFeetype.FeeTypeId = count;
                    associatedFeetype.Name = Convert.ToString(feeTypeReader[FTName], CultureInfo.CurrentCulture);
                    template.AssociatedFeeTypes.Add(associatedFeetype);
                    count++;
                }
                if (!feeTypeReader.IsClosed)
                {
                    feeTypeReader.Close();
                }
            }

            foreach (string feeId in feeTypeList)
            {
                errorMessage.Append(string.Format(CultureInfo.CurrentCulture,
                                                  DataVaultErrorCodes.UnknownObject,
                                                  feeId,
                                                  entity));
            }
            if (vaultModeType == VaultMode.Update)
            {
                if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateAdminModule);
            }
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
                return DataVaultConstants.BillingTemplate;
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
