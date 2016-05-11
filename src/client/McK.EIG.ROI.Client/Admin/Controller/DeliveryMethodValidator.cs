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
    public partial class ROIAdminValidator
    {
        #region Methods

        /// <summary>
        /// Validates the primary fields for delivery method
        /// </summary>
        /// <param name="deliveryMethod"></param>
        public void ValidatePrimaryFields(DeliveryMethodDetails deliveryMethod)
        {
            if (deliveryMethod.Name.Length == 0 || (!Validator.Validate(deliveryMethod.Name, ROIConstants.NameValidation)))
            {
                AddError(ROIErrorCodes.DeliveryMethodNameEmpty);
            }

            if (deliveryMethod.Name.Length > DeliveryNameMaxLength)
            {
                AddError(ROIErrorCodes.DeliveryMethodNameMaxLength);
            }

            if (deliveryMethod.Url != null)
            {
                if (deliveryMethod.Url.OriginalString.Length > DeliveryUrlMaxLength)
                {
                    AddError(ROIErrorCodes.DeliveryMethodUrlMaxLength);
                }
            }
        }

        /// <summary>
        /// Validates the fields before creating new delivery method.
        /// </summary>
        /// <param name="deliveryMethod"></param>
        /// <returns></returns>
        public bool ValidateCreate(DeliveryMethodDetails deliveryMethod)
        {
            ValidatePrimaryFields(deliveryMethod);
            return NoErrors;
        }

        /// <summary>
        /// Validates the fields before updaiing media type.
        /// </summary>
        /// <param name="deliveryMethod"></param>
        /// <returns></returns>
        public bool ValidateUpdate(DeliveryMethodDetails deliveryMethod)
        {
            ValidatePrimaryFields(deliveryMethod);
            return NoErrors;
        }

        #endregion
    }
}
