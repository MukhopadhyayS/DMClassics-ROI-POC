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
        /// Validates the primary fields for reasons.
        /// </summary>
        /// <param name="requestReason"></param>
        private void ValidatePrimaryFields(ReasonDetails requestReason)
        {
            requestReason.Name        = requestReason.Name.Trim();
            requestReason.DisplayText = requestReason.DisplayText.Trim();

            if (requestReason.Name.Length == 0 || (!Validator.Validate(requestReason.Name, ROIConstants.NameValidationWithDot)))
            {
                AddError(ROIErrorCodes.ReasonNameEmpty);
            }
            if (requestReason.Name.Length > ReasonNameLength)
            {
                AddError(ROIErrorCodes.ReasonNameMaxLength);
            }
            if (requestReason.DisplayText.Length == 0)
            {
                AddError(ROIErrorCodes.ReasonDisplayTextEmpty);
            }
            if (requestReason.Type == ReasonType.Request)
            {
                if (requestReason.DisplayText.Length > RequestReasonDisplayText)
                {
                    AddError(ROIErrorCodes.RequestReasonDisplayTextMaxLength);
                }
            }
            else
            {
                if (requestReason.DisplayText.Length > ReasonDisplayText)
                {
                    AddError(ROIErrorCodes.ReasonDisplayTextMaxLength);
                }
            }
            if(requestReason.Type == ReasonType.Status)
            {
                if (requestReason.RequestStatus == RequestStatus.None)
                {
                    AddError(ROIErrorCodes.StatusReasonStatusEmpty);
                }
            }
        }

        /// <summary>
        /// Validate the fields before creating request reasons.
        /// </summary>
        /// <param name="requestReason"></param>
        /// <returns></returns>
        public bool ValidateCreate(ReasonDetails requestReason)
        {
            ValidatePrimaryFields(requestReason);
            return NoErrors;
        }

        /// <summary>
        /// Validate the fields before upating request reasons.
        /// </summary>
        /// <param name="requestReason"></param>
        /// <returns></returns>
        public bool ValidateUpdate(ReasonDetails requestReason)
        {
            ValidatePrimaryFields(requestReason);
            return NoErrors;
        }

        #endregion
    }
}
