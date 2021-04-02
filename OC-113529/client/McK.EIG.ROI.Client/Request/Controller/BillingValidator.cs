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
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.Controller
{
    public class BillingValidator : BaseROIValidator
    {   
        #region Methods

        public bool ValidateShipping(ReleaseDetails release)
        {
            if (release.ShippingDetails.ShippingAddress != null)
            {
                ValidatePrimaryFields(release.ShippingDetails.ShippingAddress.City, ROIConstants.CityValidation, ROIErrorCodes.InvalidMainCity);
                ValidatePrimaryFields(release.ShippingDetails.ShippingAddress.State, ROIConstants.Alphanumeric, ROIErrorCodes.InvalidMainState);
                if (release.ShippingDetails.WillReleaseShipped)
                {
                    ValidatePrimaryFields(release.ShippingDetails.ShippingAddress.PostalCode, ROIConstants.ZipValidation, ROIErrorCodes.InvalidMainZip);
                }
            }
            return NoErrors;
        }
      
        private void ValidatePrimaryFields(string field, string validCharSet, string errorCode)
        {
            if (!Validator.Validate(field, validCharSet))
            {
                AddError(errorCode);
            }
        }

        #endregion
    }
}
