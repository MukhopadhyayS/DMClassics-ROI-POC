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
    /// Validates media type model data
    /// </summary>
    public partial class BillingAdminValidator
    {
        #region Methods

        /// <summary>
        /// Validates the primary fields for media type 
        /// </summary>
        /// <param name="mediaType"></param>
        public void ValidatePrimaryFields(MediaTypeDetails mediaType)
        {
            if (mediaType.Name.Trim().Length == 0 || (!Validator.Validate(mediaType.Name, ROIConstants.NameValidation)))
            {
                AddError(ROIErrorCodes.MediaTypeNameEmpty);
            }

            if (mediaType.Name.Trim().Length > MediaNameMaxLength)
            {
                AddError(ROIErrorCodes.MediaTypeNameMaxLength);
            }

            if (mediaType.Description.Trim().Length > MediaDescriptionMaxLength)
            {
                AddError(ROIErrorCodes.MediaTypeDescriptionMaxLength);
            }
        }

        /// <summary>
        /// Validates the fields before creating new media type.
        /// </summary>
        /// <param name="mediaType"></param>
        /// <returns></returns>
        public bool ValidateCreate(MediaTypeDetails mediaType)
        {
            ValidatePrimaryFields(mediaType);
            return NoErrors;
        }

        /// <summary>
        /// Validates the fields before updaiing media type.
        /// </summary>
        /// <param name="mediaType"></param>
        /// <returns></returns>
        public bool ValidateUpdate(MediaTypeDetails mediaType)
        {
            ValidatePrimaryFields(mediaType);
            return NoErrors;
        }

        #endregion
    }
}
