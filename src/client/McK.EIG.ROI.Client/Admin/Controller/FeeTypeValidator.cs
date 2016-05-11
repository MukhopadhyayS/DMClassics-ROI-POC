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
using System.Globalization;
using System.Text.RegularExpressions;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Validates fee type model data
    /// </summary>
    public partial class BillingAdminValidator
    {
        #region Methods

        /// <summary>
        /// Validates the primary fields for fee type 
        /// </summary>
        /// <param name="feeType"></param>
        public void ValidatePrimaryFields(FeeTypeDetails feeType)
        {
            if (feeType.Name.Trim().Length == 0 || (!Validator.Validate(feeType.Name, ROIConstants.NameValidation)))
            {
                AddError(ROIErrorCodes.FeeTypeNameEmpty);
            }

            if (feeType.Name.Trim().Length > FeeNameMaxLength)
            {
                AddError(ROIErrorCodes.FeeTypeNameMaxLength);
            }

            if (feeType.Amount !=  0)
            {
                bool ismatch;
                ismatch = Regex.IsMatch(Convert.ToString(feeType.Amount,System.Threading.Thread.CurrentThread.CurrentCulture), 
                                        "^((\\d{1,3})|(\\d{1,3}\\.\\d{1,2}))$");
                if (!ismatch)
                {
                    AddError(ROIErrorCodes.FeeTypeAmountIsNotValid);
                }
            }
        }

        /// <summary>
        /// Validates the fields before creating new fee type.
        /// </summary>
        /// <param name="feeType"></param>
        /// <returns></returns>
        public bool ValidateCreate(FeeTypeDetails feeType)
        {
            ValidatePrimaryFields(feeType);
            return NoErrors;
        }

        /// <summary>
        /// Validates the fields before updating fee type.
        /// </summary>
        /// <param name="feeType"></param>
        /// <returns></returns>
        public bool ValidateUpdate(FeeTypeDetails feeType)
        {
            ValidatePrimaryFields(feeType);
            return NoErrors;
        }

        #endregion
    }
}
