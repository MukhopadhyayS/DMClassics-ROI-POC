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

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Process
{
    /// <summary>
    /// Admin data vault
    /// </summary>
    public class AdminVault
    {
        Log log = LogFactory.GetLogger(typeof(AdminVault));

        #region Fields

        private static Hashtable adminHT;

        private MediaTypeVault mediaTypeVault;
        private BillingTierVault billingTierVault;
        private FeeTypeVault feeTypeVault;
        private BillingTemplateVault billingTemplate;
        private RequestorTypeVault requesotrTypeVault;
        private DeliveryMethodVault deliveryMethVault;
        private PaymentMethodVault paymentMethVault;
        private AdjustmentReasonsVault adjustmentMethVault;
        private RequestReasonsVault requestReasonVault;
        private RequestStatusReasonsVault reqStatusVault;
        private DisclosureDocTypesVault disclosureDocTypeVault;
        private LetterTemplatesVault letterTemplateVault;
        private ConfigureNotesVault configureNoteVault;

        private int releaseCount;

        #endregion

        #region Constructor

        public AdminVault()
        {
            adminHT = new Hashtable(new VaultComparer());
        }

        #endregion

        #region Methods

        /// <summary>
        /// Call the Entity level load method.
        /// </summary>
        public void LoadData()
        {
            log.EnterFunction();
            log.Debug(DataVaultConstants.StartTime + DateTime.Now);

            //Load MediaType vault
            mediaTypeVault = new MediaTypeVault();
            mediaTypeVault.ModeType = VaultMode.Create;
            Load(mediaTypeVault);

            //Load BillingTier vault
            billingTierVault = new BillingTierVault();
            billingTierVault.ModeType = VaultMode.Create;
            Load(billingTierVault);

            //Load FeeType vault
            feeTypeVault = new FeeTypeVault();
            feeTypeVault.ModeType = VaultMode.Create;
            Load(feeTypeVault);

            //Load BillingTemplate vault.
            billingTemplate = new BillingTemplateVault();
            billingTemplate.ModeType = VaultMode.Create;
            Load(billingTemplate);

            //Load RequestorType Valult
            requesotrTypeVault = new RequestorTypeVault();
            requesotrTypeVault.ModeType = VaultMode.Create;
            Load(requesotrTypeVault);

            //Load DeliveryMethod vault.
            deliveryMethVault = new DeliveryMethodVault();
            deliveryMethVault.ModeType = VaultMode.Create;
            Load(deliveryMethVault);

            //Load DeliveryMethod vault.
            paymentMethVault = new PaymentMethodVault();
            paymentMethVault.ModeType = VaultMode.Create;
            Load(paymentMethVault);

            //Load AdjustmentReason vault
            adjustmentMethVault = new AdjustmentReasonsVault();
            adjustmentMethVault.ModeType = VaultMode.Create;
            Load(adjustmentMethVault);

            //Load Request Reason vault
            requestReasonVault = new RequestReasonsVault();
            requestReasonVault.ModeType = VaultMode.Create;
            Load(requestReasonVault);

            //Load Request Status Reason vault
            reqStatusVault = new RequestStatusReasonsVault();
            requesotrTypeVault.ModeType = VaultMode.Create;
            Load(reqStatusVault);

            //Load DisclosureDocTypesValult
            disclosureDocTypeVault = new DisclosureDocTypesVault();
            disclosureDocTypeVault.ModeType = VaultMode.Create;
            Load(disclosureDocTypeVault);

            //Load LetterTemplate Valult
            letterTemplateVault = new LetterTemplatesVault();
            letterTemplateVault.ModeType = VaultMode.Create;
            Load(letterTemplateVault);

            //Load ConfigureNotes Valult
            configureNoteVault = new ConfigureNotesVault();
            configureNoteVault.ModeType = VaultMode.Create;
            Load(configureNoteVault);

            log.Debug(DataVaultConstants.EndTime + DateTime.Now);

            log.ExitFunction();
        }


        public void Reload()
        {
            log.EnterFunction();
            log.Debug(DataVaultConstants.StartTime + DateTime.Now);

            //Load MediaType vault            
            mediaTypeVault.ModeType = VaultMode.Update;
            Load(mediaTypeVault);

            //Load BillingTier vault            
            billingTierVault.ModeType = VaultMode.Update;
            Load(billingTierVault);

            //Load FeeType vault            
            feeTypeVault.ModeType = VaultMode.Update;
            Load(feeTypeVault);

            //Load BillingTemplate vault.            
            billingTemplate.ModeType = VaultMode.Update;
            Load(billingTemplate);

            //Load RequestorType Valult            
            requesotrTypeVault.ModeType = VaultMode.Update;
            Load(requesotrTypeVault);

            //Load DeliveryMethod vault.            
            deliveryMethVault.ModeType = VaultMode.Update;
            Load(deliveryMethVault);

            //Load DeliveryMethod vault.            
            paymentMethVault.ModeType = VaultMode.Update;
            Load(paymentMethVault);          

            log.Debug(DataVaultConstants.EndTime + DateTime.Now);

            log.ExitFunction();
        }

        private void Load(IVault vault)
        {
            log.EnterFunction();
            log.Debug(DataVaultConstants.StartTag + vault.EntityName + "_" + vault.ModeType);
            log.Debug(DataVaultConstants.StartTime + DateTime.Now);

            if (vault.EntityName == DataVaultConstants.BillingTier
                || vault.EntityName == DataVaultConstants.RequestorTypeGeneral)            
            {
                if (vault.EntityName == DataVaultConstants.BillingTier)
                {
                    ((BillingTierVault)vault).ReleaseCount = releaseCount;
                }
                else
                {
                    ((RequestorTypeVault)vault).ReleaseCount = releaseCount;
                }
            }

            IDataReader reader;
            if (vault.ModeType == VaultMode.Create)
            {
                reader = Utility.ReadData(vault.EntityName + "_" + vault.ModeType);
            }
            else
            {
                reader = Utility.ReadData(vault.EntityName + "_" + vault.ModeType, releaseCount);
            }

            object entityobject   = vault.Load(reader);
            if (entityobject != null)
            {
                if (adminHT.ContainsKey(vault.EntityName))
                {
                    adminHT[vault.EntityName] = entityobject;
                }
                else
                {
                    adminHT.Add(vault.EntityName, entityobject);
                }
            }

            log.Debug(DataVaultConstants.EndTime + DateTime.Now);
            log.Debug(DataVaultConstants.EndTag + vault.EntityName + "_" + vault.ModeType);

            log.ExitFunction();
        }

        
        /// <summary>
        /// Return the Specified Object for the object name given.
        /// </summary>
        /// <param name="entityType">Type of Entity</param>
        /// <param name="objectName">Object Name</param>
        /// <returns>Object</returns>
        public static object GetEntityObject(string entityType, string objectName)
        {
            Hashtable entityTable = (Hashtable)adminHT[entityType];
            if (entityTable == null)
            {
                throw new VaultException(string.Format(CultureInfo.CurrentCulture,
                                                       DataVaultErrorCodes.EntityNotFound,
                                                       entityType));
            }
            object entityObject = entityTable[objectName];
            if (entityObject == null)
            {                
                throw new VaultException(string.Format(CultureInfo.CurrentCulture,
                                                       DataVaultErrorCodes.UnknownObject,
                                                       objectName, entityType));
            }
            return entityObject;
        }

        #endregion

        #region Property

        public int ReleaseCount
        {
            get { return releaseCount; }
            set { releaseCount = value;}
        }

        #endregion
    }
}
