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
    /// Billing Template Data Vault
    /// </summary>
    public class BillingTemplateVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(BillingTemplateVault));

        #region Fields

        //DBFields
        private const string RefId         = "Ref_ID";
        private const string BTName        = "Templ_Name";
        private const string BTFeeTypes    = "Fee_Type";

        private Hashtable billingTemplateHT;
        private VaultMode modeType;

        #endregion

        #region Constructor 

        public BillingTemplateVault()
        {
            billingTemplateHT = new Hashtable(new VaultComparer());
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Billing Template Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {   
            log.EnterFunction();
            
            long recordCount;
            try
            {
                recordCount = 1;
                BillingTemplateDetails billingTemplate;
                while (reader.Read())
                {
                    string templateRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);

                    billingTemplate        = new BillingTemplateDetails();
                    billingTemplate.Name   = Convert.ToString(reader[BTName], CultureInfo.CurrentCulture);

                    GetLinkedFeeTypes(billingTemplate , Convert.ToString(reader[BTFeeTypes], CultureInfo.CurrentCulture).Trim());

                    if (modeType == VaultMode.Create)
                    {
                        //Save the BillingTemplate object.
                        billingTemplateHT.Add(templateRefId, SaveBillingTemplateDetail(billingTemplate, recordCount));
                    }
                    else
                    {
                        UpdateBillingTemplate(billingTemplate, templateRefId, recordCount);
                    }

                    recordCount++;
                }
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.OdbcError);
            }
            finally
            {
                reader.Close();
            }
            log.ExitFunction();
            return billingTemplateHT;
        }

        private void UpdateBillingTemplate(BillingTemplateDetails billingTemplate, string templateRefId, long recordCount)
        {
            log.EnterFunction();
            if (!billingTemplateHT.ContainsKey(templateRefId))
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, templateRefId, EntityName + "_" + VaultMode.Create);
                log.Debug(message);
                throw new VaultException(message);
            }

            BillingTemplateDetails templateDetails = billingTemplateHT[templateRefId] as BillingTemplateDetails;            
            templateDetails.Name = billingTemplate.Name;
            templateDetails.AssociatedFeeTypes.Clear();
            foreach (AssociatedFeeType feeType in billingTemplate.AssociatedFeeTypes)
            {
                templateDetails.AssociatedFeeTypes.Add(feeType);
            }
            templateDetails = SaveUpdatedBillingTemplate(templateDetails, recordCount);
            billingTemplateHT[templateRefId] = templateDetails;

            log.ExitFunction();
        }

       

        /// <summary>
        /// Get the associated Fee Types for the Billing template.
        /// </summary>
        /// <param name="feeTypes"></param>
        /// <returns></returns>
        private void GetLinkedFeeTypes(BillingTemplateDetails template, string feeTypes)
        {
            log.EnterFunction();
            template.AssociatedFeeTypes.Clear();
            string[] feeTypeRefId = feeTypes.Split('~');
            
            for(int i = 0; i <feeTypeRefId.Length; i++ )
            {
                FeeTypeDetails feeDetails = (FeeTypeDetails)AdminVault.GetEntityObject(DataVaultConstants.FeeType, feeTypeRefId[i]);
                AssociatedFeeType associatedFeetype = new AssociatedFeeType();
                associatedFeetype.FeeTypeId         = feeDetails.Id;
                associatedFeetype.Name              = feeDetails.Name;
                template.AssociatedFeeTypes.Add(associatedFeetype);                
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Passes the BillingTemplate object to the BillingAdminController for further process
        /// </summary>
        /// <param name="BillingTemplate">BillingTemplate object.</param>
        private BillingTemplateDetails SaveBillingTemplateDetail(BillingTemplateDetails billingTemplate, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));


                //Call the BillingAdminController to save the BillingTemplate Object.
                long id = BillingAdminController.Instance.CreateBillingTemplate(billingTemplate);
                billingTemplate.Id = id;

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return billingTemplate;
        }

        private BillingTemplateDetails SaveUpdatedBillingTemplate(BillingTemplateDetails billingTemplate, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));


                //Call the BillingAdminController to save the BillingTemplate Object.
                billingTemplate = BillingAdminController.Instance.UpdateBillingTemplate(billingTemplate);                

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return billingTemplate;
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
