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
    public class ValidateBillingTier: IVault
    {
        #region Fields

        private VaultMode vaultModeType;

        //BillingTier Details
        private const string RefId                  = "Ref_ID";
        private const string BillingTierName        = "BT_Name";
        private const string BillingTierMediaType   = "MTRef_Id";
        private const string BillingTierDescription = "BT_Desc";
        private const string BillingTierBaseCharge  = "Base_Charge";
        private const string BillingTierOtherCharge = "Additional_Pages";
        private const string SystemSeed             = "IsSystemSeed";

        //PageTier Details
        private const string MappingTierName         = "BTRef_ID";
        private const string BillingTierStartPage    = "First_Page";
        private const string BillingTierEndPage      = "Through_Page";
        private const string BillingTierPricePerPage = "Cost";
        
        private StringBuilder errorMessage;

        #endregion

        #region Constructor

        public ValidateBillingTier()
        {
            errorMessage = new StringBuilder();
        }

        #endregion

        #region Methods

        public Object Load(IDataReader reader)
        {   
            long recordCount   = 1;
            bool isHeaderExist = false;
            string tierRefId   = string.Empty;
            BillingTierDetails billingTierDetails;

            while (reader.Read())
            {
                try
                {
                    try
                    {
                        errorMessage.Remove(0, errorMessage.ToString().Length);
                        billingTierDetails = new BillingTierDetails();
                        tierRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture).Trim();

                        if (vaultModeType == VaultMode.Create)
                        {

                            string mtRefId = Convert.ToString(reader[BillingTierMediaType], CultureInfo.CurrentCulture).Trim();
                            long mediaCount = IsMediaTypeExist(mtRefId);
                            MediaTypeDetails mediaDetails = new MediaTypeDetails();
                            mediaDetails.Id = mediaCount;
                            billingTierDetails.MediaType = mediaDetails;
                        }
                        else
                        {
                            bool isbillingTierExist = BillingTierExist(tierRefId);
                            if (!isbillingTierExist)
                            {
                                errorMessage.Append(string.Format(CultureInfo.CurrentCulture,
                                                                  DataVaultErrorCodes.UnknownObject,
                                                                  tierRefId,
                                                                  EntityName + "_" + VaultMode.Create));
                            }
                        }

                        billingTierDetails.Name            = Convert.ToString(reader[BillingTierName], CultureInfo.CurrentCulture).Trim();
                        billingTierDetails.Description     = Convert.ToString(reader[BillingTierDescription], CultureInfo.CurrentCulture).Trim();
                        billingTierDetails.BaseCharge      = (Convert.ToString(reader[BillingTierBaseCharge], CultureInfo.CurrentCulture).Trim().Length == 0)
                                                              ? 0 : Convert.ToDouble(reader[BillingTierBaseCharge], CultureInfo.CurrentCulture);
                        billingTierDetails.OtherPageCharge = (Convert.ToString(reader[BillingTierOtherCharge], CultureInfo.CurrentCulture).Trim().Length == 0)
                                                              ? 0 : Convert.ToDouble(reader[BillingTierOtherCharge], CultureInfo.CurrentCulture);
                        if (vaultModeType == VaultMode.Create)
                        {
                            bool isSystemSeed = (Convert.ToString(reader[SystemSeed],
                                                 CultureInfo.CurrentCulture).Trim().Length == 0) ?
                                                 false : Convert.ToBoolean(reader[SystemSeed], CultureInfo.CurrentCulture);
                            CreateBillingTier(billingTierDetails, isSystemSeed, tierRefId);
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
                                                 "Billing Tier",
                                                 EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(tierRefId, recordCount, cause.Message);
                }               
                recordCount++;
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            //Update Mode.
            if (vaultModeType == VaultMode.Update)
            {
                UpdatePageLevelTier();
            }           
            return null;
        }        

        /// <summary>
        /// Validate weather the billing tier exist or not.
        /// </summary>
        /// <param name="tierRefId"></param>
        /// <returns></returns>
        private bool BillingTierExist(string tierRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            long count = Utility.GetCount(EntityName + "_" + VaultMode.Create, RefId, tierRefId);
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateAdminModule);
            return (count > 0);
        }

        /// <summary>
        /// Check weather media type exist or not.
        /// </summary>
        /// <param name="mtRefId"></param>
        /// <returns></returns>
        private static long IsMediaTypeExist(string mtRefId)
        {
            long count = Utility.GetCount(DataVaultConstants.MediaType + "_" + VaultMode.Create, RefId, mtRefId);
            return count;
        }

        /// <summary>
        /// Validate the billing Tier.
        /// </summary>
        /// <param name="billingTierDetails"></param>
        /// <param name="isSystemSeed"></param>
        /// <param name="tierRefId"></param>
        /// <param name="recordCount"></param>
        private void CreateBillingTier(BillingTierDetails billingTierDetails, bool isSystemSeed, string tierRefId)
        {   
            if (isSystemSeed)
            {
                GetPageLevelTierDetails(tierRefId, billingTierDetails, false);
                if (billingTierDetails.PageTiers.Count == 0)
                {
                    PageLevelTierDetails pageDetails = new PageLevelTierDetails();
                    pageDetails.StartPage = 1;
                    pageDetails.EndPage = 2;
                    pageDetails.PricePerPage = 3;
                    billingTierDetails.PageTiers.Add(pageDetails);
                }
            }
            else
            {
                GetPageLevelTierDetails(tierRefId, billingTierDetails, true);
            }
            BillingAdminValidator validator = new BillingAdminValidator();
            bool check = validator.ValidateCreate(billingTierDetails);
            if (!check)
            {
                errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));                
            }
        }
        
        private void GetPageLevelTierDetails(string tierRefId, BillingTierDetails billingTierDetails, bool isSeed)
        {
            if (Utility.GetCount(DataVaultConstants.PageLevelTier + "_" + vaultModeType, MappingTierName, tierRefId) == 0)
            {
                if (isSeed)
                {   
                    string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.PageTierNotExist, tierRefId);
                    errorMessage.Append(message);                               
                }
                return;
            }
            GetPageLevelTierDetails(tierRefId, billingTierDetails);
        }

        private void GetPageLevelTierDetails(string tierRefId, BillingTierDetails billingTierDetails)
        {
            using (IDataReader pageTierReader = Utility.ReadData(DataVaultConstants.PageLevelTier + "_" + vaultModeType, MappingTierName, tierRefId))
            {
                billingTierDetails.PageTiers.Clear();
                PageLevelTierDetails pageDetails;
                while (pageTierReader.Read())
                {
                    try
                    {
                        pageDetails = new PageLevelTierDetails();
                        pageDetails.StartPage    = Convert.ToInt32(pageTierReader[BillingTierStartPage], CultureInfo.CurrentCulture);
                        pageDetails.EndPage      = Convert.ToInt32(pageTierReader[BillingTierEndPage], CultureInfo.CurrentCulture);
                        pageDetails.PricePerPage = (pageTierReader[BillingTierPricePerPage].ToString().Length == 0) ? 0 :
                                                   Convert.ToDouble(pageTierReader[BillingTierPricePerPage], CultureInfo.CurrentCulture);
                        billingTierDetails.PageTiers.Add(pageDetails);
                    }
                    catch (InvalidCastException cause)
                    {
                        errorMessage.Append(cause.Message);
                        throw new VaultException(errorMessage.ToString());
                    }
                    catch (ArgumentException cause)
                    {
                        errorMessage.Append(cause.Message);
                        throw new VaultException(errorMessage.ToString());
                    }
                    catch (FormatException cause)
                    {
                        errorMessage.Append(cause.Message);
                        throw new VaultException(errorMessage.ToString());
                    }
                }
                if (!pageTierReader.IsClosed)
                {
                    pageTierReader.Close();
                }
            }
        }

        private void UpdatePageLevelTier()
        {   
            string entity = DataVaultConstants.PageLevelTier + "_" + ModeType;
            string query = (DataVaultConstants.IsExcelFile)
                            ? "SELECT * FROM [" + entity + "$]"
                            : "SELECT * FROM " + entity + ".csv";

            using (IDataReader pageTierReader = Utility.ReadData(entity, query))
            {
                long count = 1;
                bool isHeaderExist = false;
                string billingTierRefId = string.Empty;

                while (pageTierReader.Read())
                {
                    try
                    {
                        try
                        {
                            errorMessage.Remove(0, errorMessage.Length);
                            billingTierRefId = Convert.ToString(pageTierReader[MappingTierName], CultureInfo.CurrentCulture);

                            bool isbillingTierExist = BillingTierExist(billingTierRefId);
                            if (!isbillingTierExist)
                            {
                                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, billingTierRefId, EntityName + "_" + VaultMode.Create);
                                errorMessage.Append(message);
                            }
                            PageLevelTierDetails pageDetails = new PageLevelTierDetails();
                            pageDetails.StartPage            = Convert.ToInt32(pageTierReader[BillingTierStartPage], CultureInfo.CurrentCulture);
                            pageDetails.EndPage              = Convert.ToInt32(pageTierReader[BillingTierEndPage], CultureInfo.CurrentCulture);
                            pageDetails.PricePerPage         = (pageTierReader[BillingTierPricePerPage].ToString().Length == 0) ? 0 :
                                                                Convert.ToDouble(pageTierReader[BillingTierPricePerPage], CultureInfo.CurrentCulture);

                            if (errorMessage.ToString().Trim().Length > 0)
                            {                                
                                throw new VaultException(errorMessage.ToString().Trim());
                            }                            
                        }
                        catch (InvalidCastException cause)
                        {
                            errorMessage.Append(cause.Message);
                            throw new VaultException(errorMessage.ToString());
                        }
                        catch (ArgumentException cause)
                        {
                            errorMessage.Append(cause.Message);
                            throw new VaultException(errorMessage.ToString());
                        }
                        catch (FormatException cause)
                        {
                            errorMessage.Append(cause.Message);
                            throw new VaultException(errorMessage.ToString());
                        }
                    }
                    catch (VaultException cause)
                    {
                        if (!isHeaderExist)
                        {
                            isHeaderExist = true;
                            ValidateUtility.WriteLog("Admin",
                                                     (vaultModeType == VaultMode.Create) ? DataVaultConstants.CreateAdminModule : DataVaultConstants.UpdateAdminModule,
                                                     "Billing Tier",
                                                     entity);
                        }
                        ValidateUtility.WriteLog(billingTierRefId, count, cause.Message);
                    }
                    count++;
                }
                if (!pageTierReader.IsClosed)
                {
                    pageTierReader.Close();
                }
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
                return DataVaultConstants.BillingTier;
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
