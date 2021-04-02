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
    /// Validates the Payment Method model data.
    /// </summary>
    public partial class BillingAdminValidator
    {

        #region Methods

        /// <summary>
        /// Validates the PaymentMethod primary fields.
        /// </summary>
        /// <param name="paymentMethod"></param>
        public void ValidatePrimaryFields(PaymentMethodDetails paymentMethod)
        {
            if (paymentMethod.Name.Trim().Length == 0 || (!Validator.Validate(paymentMethod.Name, ROIConstants.NameValidation)))
            {
                AddError(ROIErrorCodes.PaymentMethodNameEmpty);
            }

            if (paymentMethod.Name.Trim().Length > PaymentNameMaxLength)
            {
                AddError(ROIErrorCodes.PaymentMethodNameMaxLength);
            }

            if (paymentMethod.Description.Trim().Trim().Length > PaymentDescriptionMaxLength)
            {
                AddError(ROIErrorCodes.PaymentMethodDescriptionMaxLength);
            }
        }

        /// <summary>
        /// Validates the fields before creating payment method.
        /// </summary>
        /// <param name="paymentMethod"></param>
        /// <returns></returns>
        public bool ValidateCreate(PaymentMethodDetails paymentMethod)
        {
            ValidatePrimaryFields(paymentMethod);
            return NoErrors;
        }

        /// <summary>
        /// Validates the fields before updating payment method.
        /// </summary>
        /// <param name="paymentMethod"></param>
        /// <returns></returns>
        public bool ValidateUpdate(PaymentMethodDetails paymentMethod)
        {
            ValidatePrimaryFields(paymentMethod);
            return NoErrors;
        }

        #endregion

    }
}
