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

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.Controller
{
    /// <summary>
    /// Returns  ROIHelper or HPFWHelper instance based on helper type passed
    /// </summary>
    public static class HelperFactory
    {

        #region Fields

        //ROI service type
        public const string RoiServiceType  = "ROI";
        //HPFW service type
        public const string HpfwServiceType = "HPFW";

        #endregion
         
        #region Methods
        /// <summary>
        /// This class returns the instance of ROIHelper or HPFWHelper class based on the helper type passed.
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>
        public static IWsHelper GetHelper(string type)
        {
            switch (type)
            {
                case HelperFactory.RoiServiceType  : return new ROIHelper();
                case HelperFactory.HpfwServiceType : return new HPFWHelper();
                default: throw new ROIException(ROIErrorCodes.UnsupportedHelperType);
            }
        }
        #endregion
    }
}
