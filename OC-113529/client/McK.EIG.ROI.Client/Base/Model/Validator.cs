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
using System.Text.RegularExpressions;

namespace McK.EIG.ROI.Client.Base.Model
{
    /// <summary>
    /// Validator
    /// </summary>
    public static class Validator
    {
        #region Fields

        private static Regex reg;

        #endregion

        #region Methods

        /// <summary>
        /// Validates the data.
        /// </summary>
        /// <param name="value"></param>
        /// <param name="validChars"></param>
        /// <returns></returns>
        public static bool Validate(string value, string validChars)
        {
            if (string.IsNullOrEmpty(value)) return true;
            reg = new Regex(validChars);
            return reg.IsMatch(value);
        }

        #endregion
    }
}
