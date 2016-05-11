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
using System.Globalization;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.Client.Reports.Controller
{
    /// <summary>
    /// //ReportValidator
    /// </summary>
    public class ReportValidator : BaseROIValidator
    {
        #region Methods

         /// <summary>
         /// 
         /// </summary>
         /// <param name="parameters"></param>
         /// <returns></returns>
        public bool Validate(Hashtable parameters)
        {
            ValidatePrimaryFields(parameters);
            return NoErrors;
        }
        /// <summary>
        /// ValidatePrimaryFields
        /// </summary>
        /// <param name="parameters"></param>
        public void ValidatePrimaryFields(Hashtable parameters)
        {
            if (Convert.ToDateTime(parameters[ROIConstants.ReportStartDate], CultureInfo.InvariantCulture) > Convert.ToDateTime(parameters[ROIConstants.ReportEndDate], CultureInfo.InvariantCulture))
            {
                AddError(ROIErrorCodes.EndDateInvalid);
            }
        }

        #endregion
    }
}
