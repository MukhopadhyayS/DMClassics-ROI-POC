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
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminValidator
    {
        #region Methods

        /// <summary>
        /// Validates the primary fields for requestor type 
        /// </summary>
        /// <param name="mediaType"></param>
        public void ValidatePrimaryFields(RequestorTypeDetails requestorTypeDetails)
        {
            if (requestorTypeDetails.Name.Length == 0 || (!Validator.Validate(requestorTypeDetails.Name, ROIConstants.NameValidation)))
            {
                AddError(ROIErrorCodes.RequestorTypeNameEmpty);
            }

            if (requestorTypeDetails.Name.Length > RequestorTypeNameMaxLength)
            {
                AddError(ROIErrorCodes.RequestorTypeNameMaxLength);
            }

            if (requestorTypeDetails.HpfBillingTier.Id == 0)
            {
                AddError(ROIErrorCodes.RequestorTypeHpfBillingTierEmpty);
            }

            if (requestorTypeDetails.NonHpfBillingTier.Id == 0)
            {
                AddError(ROIErrorCodes.RequestorTypeNonHpfBillingTierEmpty);
            }

            if (requestorTypeDetails.RecordViewDetails.Name.Length == 0)
            {
                AddError(ROIErrorCodes.RecordViewNameEmpty);
            }
        }

        /// <summary>
        /// Validates the fields before creating new requestor type.
        /// </summary>
        /// <param name="mediaType"></param>
        /// <returns></returns>
        public bool ValidateCreate(RequestorTypeDetails requestorType)
        {
            ValidatePrimaryFields(requestorType);
            return NoErrors;
        }

        /// <summary>
        /// Validates the fields before updaiing requestor type.
        /// </summary>
        /// <param name="mediaType"></param>
        /// <returns></returns>
        public bool ValidateUpdate(RequestorTypeDetails requestorType)
        {
            ValidatePrimaryFields(requestorType);
            return NoErrors;
        }

        #endregion
    }
}
