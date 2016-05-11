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

using McK.EIG.Common.Utility.WebServices;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Admin Module Level Controller.
    /// </summary>
    public partial class BillingAdminController : ROIController
    {
        #region Fields
        
        private static object syncRoot = new Object();

        //private static variable of BillingAdminController
        private static volatile BillingAdminController billingAdminController;

        //Private variable to create instance of BillingAdminService 
        private BillingAdminServiceWse billingAdminService;

        #endregion

        #region Constructor

        /// <summary>
        /// AdminController Constructor
        /// </summary>
        private BillingAdminController()
        {
            billingAdminService = new BillingAdminServiceWse();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Get the AdminController Instance
        /// </summary>
        public new static BillingAdminController Instance
        {
            get
            {
                lock (syncRoot)
                {
                    if (billingAdminController == null)
                    {
                        billingAdminController = new BillingAdminController();
                    }
                }

                return billingAdminController;
            }
        }

        #endregion
    }
}
