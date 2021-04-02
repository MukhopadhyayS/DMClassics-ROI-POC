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
using System.Configuration;

using NUnit.Framework;

using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Test.TearDown
{
    /// <summary>
    /// Test class for disposing alert service
    /// </summary>  
    [TestFixture]
    public class TestDisposeAlertService
    {
        #region Fields

        private AlertServiceMQ alertService;

        #endregion

        #region Setup

        [SetUp]
        public void Init()
        {
            alertService = AlertServiceMQ.Instance;
        }

        #endregion

        #region TearDown

        [TearDown]
        public void Dispose()
        {
            alertService = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Test case for disposing alert service queue
        /// </summary>
        [Test]
        public void TestDispose()
        {
            alertService.Dispose();
        }
        #endregion 
    }
}
