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
using System.Data;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Validate
{
    public class ValidateAdmin
    {
        Log log = LogFactory.GetLogger(typeof(ValidateAdmin));

        #region Fields

        private ValidateMediaType mediaTypeValidation;
        private ValidateBillingTier billingTierValidation;
        private ValidateFeeTypes feeTypeValidation;
        private ValidateBillingTemplate billingTemplateValidation;
        private ValidateRequestorType requestorType;
        private ValidateDeliveryMethod deliveryMethodValidation;
        private ValidateAdjustmentReasons adjustmentReasonsValidation;
        private ValidateConfigureNotes configureNotesValidation;
        private ValidatePaymentMethod paymentMethodValidation;
        private ValidateRequestReasons requestReasonValidation;
        private ValidateRequestStatusReasons requestStatusReasonValidation;
        private ValidateLetterTemplates letterTemplatesValidation;
        private ValidateDisclosureDocTypes disclosureDocTypesValidation;

        #endregion

        public void LoadData()
        {   
            mediaTypeValidation = new ValidateMediaType();
            mediaTypeValidation.ModeType = VaultMode.Create;            
            Load(mediaTypeValidation);

            billingTierValidation = new ValidateBillingTier();
            billingTierValidation.ModeType = VaultMode.Create;
            Load(billingTierValidation);
            
            feeTypeValidation = new ValidateFeeTypes();
            feeTypeValidation.ModeType = VaultMode.Create;
            Load(feeTypeValidation);

            billingTemplateValidation = new ValidateBillingTemplate();
            billingTemplateValidation.ModeType = VaultMode.Create;
            Load(billingTemplateValidation);

            requestorType = new ValidateRequestorType();
            requestorType.ModeType = VaultMode.Create;
            Load(requestorType);

            deliveryMethodValidation = new ValidateDeliveryMethod();
            deliveryMethodValidation.ModeType = VaultMode.Create;
            Load(deliveryMethodValidation);

            paymentMethodValidation = new ValidatePaymentMethod();
            paymentMethodValidation.ModeType = VaultMode.Create;
            Load(paymentMethodValidation);

            adjustmentReasonsValidation = new ValidateAdjustmentReasons();
            adjustmentReasonsValidation.ModeType = VaultMode.Create;
            Load(adjustmentReasonsValidation);

            requestReasonValidation = new ValidateRequestReasons();
            requestReasonValidation.ModeType = VaultMode.Create;
            Load(requestReasonValidation);

            requestStatusReasonValidation = new ValidateRequestStatusReasons();
            requestStatusReasonValidation.ModeType = VaultMode.Create;
            Load(requestStatusReasonValidation);

            disclosureDocTypesValidation = new ValidateDisclosureDocTypes();
            disclosureDocTypesValidation.ModeType = VaultMode.Create;
            Load(disclosureDocTypesValidation);

            letterTemplatesValidation = new ValidateLetterTemplates();
            letterTemplatesValidation.ModeType = VaultMode.Create;
            Load(letterTemplatesValidation);

            configureNotesValidation = new ValidateConfigureNotes();
            configureNotesValidation.ModeType = VaultMode.Create;
            Load(configureNotesValidation);
        }

        public void Reload()
        {            
            mediaTypeValidation.ModeType = VaultMode.Update;
            Load(mediaTypeValidation);
            
            billingTierValidation.ModeType = VaultMode.Update;
            Load(billingTierValidation);
            
            feeTypeValidation.ModeType = VaultMode.Update;
            Load(feeTypeValidation);

            billingTemplateValidation.ModeType = VaultMode.Update;
            Load(billingTemplateValidation);
            
            requestorType.ModeType = VaultMode.Update;
            Load(requestorType);
            
            deliveryMethodValidation.ModeType = VaultMode.Update;
            Load(deliveryMethodValidation);

            paymentMethodValidation.ModeType = VaultMode.Update;
            Load(paymentMethodValidation);
        }

        private void Load(IVault vault)
        {
            log.EnterFunction();
            log.Debug(vault.EntityName);
            IDataReader reader = Utility.ReadData(vault.EntityName + "_" + vault.ModeType);
            vault.Load(reader);

            log.ExitFunction();
        }
    }
}
