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

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Process
{
    /// <summary>
    /// Billing Tier Data Vault
    /// </summary>
    public class BillingTierVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(BillingTierVault));

        #region Fields

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

        private Hashtable billingTierHT;

        private VaultMode modeType;

        private int releaseCount;

        #endregion

        #region Constructor 

        public BillingTierVault()
        {
            billingTierHT = new Hashtable(new VaultComparer());
            //Get all Media Types.
            CacheAllBillingTiers();
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Billing Tier Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();
            long recordCount;
            try
            {
                recordCount = 1;                
                while (reader.Read())
                {
                    BillingTierDetails billingTierDetails;

                    string tierRefId   = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture).Trim();
                    
                    if (modeType == VaultMode.Create)
                    {
                        billingTierDetails = new BillingTierDetails();                        
                        string mtRefId               = Convert.ToString(reader[BillingTierMediaType], CultureInfo.CurrentCulture).Trim();
                        billingTierDetails.MediaType = (MediaTypeDetails)AdminVault.GetEntityObject(DataVaultConstants.MediaType, mtRefId);                        
                    }
                    else
                    {
                        if (!billingTierHT.ContainsKey(tierRefId))
                        {
                            string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, tierRefId, EntityName + "_" + modeType);
                            log.Debug(message);
                            throw new VaultException(message);
                        }
                        billingTierDetails = billingTierHT[tierRefId] as BillingTierDetails;                        
                    }
                    
                    billingTierDetails.Name            = Convert.ToString(reader[BillingTierName], CultureInfo.CurrentCulture).Trim();
                    billingTierDetails.Description     = Convert.ToString(reader[BillingTierDescription], CultureInfo.CurrentCulture).Trim();
                    billingTierDetails.BaseCharge      = (Convert.ToString(reader[BillingTierBaseCharge], CultureInfo.CurrentCulture).Trim().Length == 0)
                                                          ? 0 : Convert.ToDouble(reader[BillingTierBaseCharge], CultureInfo.CurrentCulture);
                    billingTierDetails.OtherPageCharge = (Convert.ToString(reader[BillingTierOtherCharge], CultureInfo.CurrentCulture).Trim().Length == 0)
                                                          ? 0 : Convert.ToDouble(reader[BillingTierOtherCharge], CultureInfo.CurrentCulture);

                    if (modeType == VaultMode.Create)
                    {
                        bool isSystemSeed = (Convert.ToString(reader[SystemSeed],
                                             CultureInfo.CurrentCulture).Trim().Length == 0) ?
                                             false : Convert.ToBoolean(reader[SystemSeed], CultureInfo.CurrentCulture);
                        CreateBillingTier(billingTierDetails, isSystemSeed, tierRefId, recordCount);
                    }
                    else
                    {
                        //Update the System Seed data.
                        billingTierDetails       = UpdateBillingTier(billingTierDetails, recordCount);
                        billingTierHT[tierRefId] = billingTierDetails;                        
                    }
                    recordCount++;
                }

                if (modeType == VaultMode.Update)
                {
                    UpdatePageLevelTier();
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
            finally
            {
                reader.Close();
            }
            log.ExitFunction();
            return billingTierHT;
        }

        private void CreateBillingTier(BillingTierDetails billingTierDetails, bool isSystemSeed, string tierRefId, long recordCount)
        {
            log.EnterFunction();
            if (isSystemSeed)
            {
                BillingTierDetails details = billingTierHT[billingTierDetails.Name] as BillingTierDetails;
                if (details == null)
                {
                    string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidSystemSeedData, billingTierDetails.Name, EntityName);
                    log.Debug(message);
                    throw new VaultException(message);
                }

                details.Description     = billingTierDetails.Description;
                details.BaseCharge      = billingTierDetails.BaseCharge;
                details.OtherPageCharge = billingTierDetails.OtherPageCharge;
                GetPageLevelTierDetails(tierRefId, details, false);
                //Update the System Seed data.
                details = UpdateBillingTier(details, recordCount);
                billingTierHT.Remove(billingTierDetails.Name);
                billingTierHT.Add(tierRefId, details);
            }
            else
            {
                GetPageLevelTierDetails(tierRefId, billingTierDetails, true);
                //Save the Billing Tier detail.
                billingTierHT.Add(tierRefId, SaveBillingTierDetail(billingTierDetails, recordCount));
            }
            log.ExitFunction();
        }        

        private void UpdatePageLevelTier()
        {
            log.EnterFunction();
            long count;
            string entity = DataVaultConstants.PageLevelTier + "_" + ModeType;
            string query  = (DataVaultConstants.IsExcelFile)
                            ? "SELECT " + MappingTierName + " FROM [" + entity + "$] WHERE Release_Counter = " + releaseCount

                            : "SELECT " + MappingTierName + " FROM " + entity + ".csv WHERE Release_Counter = " + releaseCount;

            using (IDataReader pageTierReader = Utility.ReadData(entity, query))
            {
                count = 1;
                while (pageTierReader.Read())
                {
                    string billTemplRefid= Convert.ToString(pageTierReader[MappingTierName], CultureInfo.CurrentCulture);                    
                    BillingTierDetails billingTierDetails = billingTierHT[billTemplRefid] as BillingTierDetails;
                    if (billingTierDetails == null)
                    {
                        string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, billTemplRefid, EntityName + "_" + VaultMode.Create);
                        log.Debug(message);
                        throw new VaultException(message);
                    }
                    GetUpdatedPageLevelTierDetails(billTemplRefid, billingTierDetails);
                    billingTierDetails = UpdateBillingTier(billingTierDetails, count);
                    billingTierHT[billTemplRefid] = billingTierDetails;                        
                }
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Get all Seed Data.
        /// </summary>
        private void CacheAllBillingTiers()
        {
            try
            {
                log.EnterFunction();
                BillingAdminController billingController = BillingAdminController.Instance;
                Collection<BillingTierDetails> billingTierDetails = billingController.RetrieveAllBillingTiers();
                foreach (BillingTierDetails billingTierDetail in billingTierDetails)
                {
                    BillingTierDetails tierDetail = billingController.GetBillingTier(billingTierDetail.Id);
                    billingTierHT.Add(billingTierDetail.Name.Trim(), tierDetail);
                }
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(cause.Message, cause);
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Return the PageTier Details for the Specifie BillingTier.
        /// </summary>
        /// <param name="tierName">Billing Tier Name</param>        
        private void GetPageLevelTierDetails(string tierRefId, BillingTierDetails billingTierDetails, bool isCreate)
        {
            if (Utility.GetCount(DataVaultConstants.PageLevelTier + "_" + modeType, MappingTierName, tierRefId) == 0)
            {
                if (isCreate)
                {
                    string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.PageTierNotExist, billingTierDetails.Name);
                    log.Debug(message);
                    throw new VaultException(message);
                }
                return;
            }
            GetPageLevelTierDetails(tierRefId, billingTierDetails);
        }

        private void GetPageLevelTierDetails(string tierRefId, BillingTierDetails billingTierDetails)
        {
            using (IDataReader pageTierReader = Utility.ReadData(DataVaultConstants.PageLevelTier + "_" + modeType, MappingTierName, tierRefId))
            {
                billingTierDetails.PageTiers.Clear();
                PageLevelTierDetails pageDetails;
                while (pageTierReader.Read())
                {
                    pageDetails = new PageLevelTierDetails();
                    pageDetails.StartPage    = Convert.ToInt32(pageTierReader[BillingTierStartPage], CultureInfo.CurrentCulture);
                    pageDetails.EndPage      = Convert.ToInt32(pageTierReader[BillingTierEndPage], CultureInfo.CurrentCulture);
                    pageDetails.PricePerPage = (pageTierReader[BillingTierPricePerPage].ToString().Length == 0) ? 0 :
                                               Convert.ToDouble(pageTierReader[BillingTierPricePerPage], CultureInfo.CurrentCulture);
                    billingTierDetails.PageTiers.Add(pageDetails);
                }
            }
        }

        private void GetUpdatedPageLevelTierDetails(string tierRefId, BillingTierDetails billingTierDetails)
        {
            string query;
            string entity = DataVaultConstants.PageLevelTier + "_" + modeType;
            if (DataVaultConstants.IsExcelFile)
            {
                query = "SELECT * FROM [" + entity + "$]  WHERE " + MappingTierName + " = '" + tierRefId + "' AND Release_Counter = " + releaseCount;
            }
            else
            {
                query = "SELECT * FROM " + entity + ".csv  WHERE " + MappingTierName + " = '" + tierRefId + "' AND Release_Counter = " + releaseCount;
            }

            using (IDataReader pageTierReader = Utility.ReadData(EntityName, query))
            {
                billingTierDetails.PageTiers.Clear();
                PageLevelTierDetails pageDetails;
                while (pageTierReader.Read())
                {
                    pageDetails = new PageLevelTierDetails();
                    pageDetails.StartPage = Convert.ToInt32(pageTierReader[BillingTierStartPage], CultureInfo.CurrentCulture);
                    pageDetails.EndPage = Convert.ToInt32(pageTierReader[BillingTierEndPage], CultureInfo.CurrentCulture);
                    pageDetails.PricePerPage = (pageTierReader[BillingTierPricePerPage].ToString().Length == 0) ? 0 :
                                               Convert.ToDouble(pageTierReader[BillingTierPricePerPage], CultureInfo.CurrentCulture);
                    billingTierDetails.PageTiers.Add(pageDetails);
                }
            }
        }

        /// <summary>
        /// Passes the BillingTier object to the ROIAdminController for further process
        /// </summary>
        /// <param name="mediaTypes">BillingTier object.</param>
        private BillingTierDetails SaveBillingTierDetail(BillingTierDetails billingTierDetail, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                       DataVaultConstants.ProcessStartTag,
                                       recordCount,
                                       DateTime.Now));

                //Call the BillingAdminController to save the BillingTier Object.
                billingTierDetail = BillingAdminController.Instance.CreateBillingTier(billingTierDetail);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return billingTierDetail;
        }

        private BillingTierDetails UpdateBillingTier(BillingTierDetails details, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                       DataVaultConstants.ProcessStartTag,
                                       recordCount,
                                       DateTime.Now));

                //Call the BillingAdminController to save the BillingTier Object.
                details = BillingAdminController.Instance.UpdateBillingTier(details);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return details;
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
                return modeType;
            }
            set
            {
                modeType = value;
            }
        }

        public int ReleaseCount
        {
            get { return releaseCount; }
            set { releaseCount = value; }
        }
        
        #endregion
    }
}
