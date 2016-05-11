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

using Microsoft.Web.Services3;

namespace McK.EIG.ROI.Client.Base.Controller
{
    /// <summary>
    /// WSHelper interface has been implemented by ROIHelper and HPFWHelper classes
    /// </summary>
    public interface IWsHelper
    {
        #region Methods
        /// <summary>
        /// Invokes the service method after generating security information
        /// </summary>
        /// <param name="serviceProxy">service proxy</param>
        /// <param name="serviceMethod">Method to be invoked</param>
        /// <param name="param">request information</param>
        /// <returns></returns>
        object Invoke(CustomWebServicesClientProtocol serviceProxy, string serviceMethod, object[] requestParams);

        /// <summary>
        /// Invokes the service method without security information
        /// </summary>
        /// <param name="serviceProxy">service proxy</param>
        /// <param name="serviceMethod">Method to be invoked</param>
        /// <param name="param">request information</param>
        /// <returns></returns>
        object Invoke(CustomWebServicesClientProtocol serviceProxy, string serviceMethod, object[] requestParams, bool addSecurityParams);
       
        #endregion
    }
}
