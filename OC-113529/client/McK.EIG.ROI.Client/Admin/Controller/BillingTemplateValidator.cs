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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Validates billing Template model data
    /// </summary>
    public partial class BillingAdminValidator
    {

        #region Methods

        /// <summary>
        /// Validates the primary fields for billing Template 
        /// </summary>
        /// <param name="billingTemplate"></param>
        public void ValidatePrimaryFields(BillingTemplateDetails billingTemplate)
        {
            if (billingTemplate.Name.Trim().Length == 0 || (!Validator.Validate(billingTemplate.Name, ROIConstants.NameValidation)))
            {
                AddError(ROIErrorCodes.BillingTemplateNameEmpty);
            }           

            if (billingTemplate.Name.Trim().Length > BillingTemplateMaxLength)
            {
                AddError(ROIErrorCodes.BillingTemplateNameMaxLength);
            }

            if (billingTemplate.AssociatedFeeTypes.Count == 0)
            {
                AddError(ROIErrorCodes.BillingTemplateFeeTypeEmpty);
            }

        }

        /// <summary>
        /// Validates the fields before creating new billing Template.
        /// </summary>
        /// <param name="billingTemplate"></param>
        /// <returns></returns>
        public bool ValidateCreate(BillingTemplateDetails billingTemplate)
        {
            ValidatePrimaryFields(billingTemplate);
            return NoErrors;
        }

        /// <summary>
        /// Validates the fields before updaiing billing Template.
        /// </summary>
        /// <param name="billingTemplate"></param>
        /// <returns></returns>
        public bool ValidateUpdate(BillingTemplateDetails billingTemplate)
        {
            ValidatePrimaryFields(billingTemplate);
            return NoErrors;
        }

        #endregion

    }
}
