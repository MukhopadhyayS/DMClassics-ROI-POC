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

using NUnit.Framework;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test cases for Admin Controller
    /// </summary>
    [TestFixture]
    public class TestBiilingAdminController
    {
        #region Fields
        private BillingAdminController biilingAdminController;
        #endregion 

        #region NUnit

        [SetUp]
        public void Init()
        {
            biilingAdminController = BillingAdminController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            biilingAdminController = null;
        }

        public static string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString().Replace('.', ' ');
        }

        #endregion

        #region TestMethods
        
        #endregion


    }
}
