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
using McK.EIG.ROI.Client.Base.Model;

using Microsoft.Web.Services3;

namespace McK.EIG.ROI.Client.Test.Base.Controller
{
    /// <summary>
    /// Test class for AlertServiceMQ 
    /// </summary>  
    [TestFixture]
    public class TestAlertServiceMQ
    {
        #region Fields

        private AlertServiceMQ alertService;
        private LogEvent logEvent;

        #endregion

        #region Setup
        
        [SetUp]
        public void Init()
        {
            alertService = AlertServiceMQ.Instance;
            logEvent = new LogEvent();
        }

        #endregion

        #region TearDown

        [TearDown]
        public void Dispose()
        {
            alertService = null;
            logEvent = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// test case for create alert
        /// </summary>
        [Test]
        public void TestCreateAlertEntry()
        {
            logEvent.Message = "The remote name could not be resolved: '192.168.0.806'";
            Assert.IsTrue(alertService.CreateAlertEntry(logEvent));
        }

        /// <summary>
        /// test case for create alert with fail
        /// </summary>
        [Test]
        public void TestCreateAlertEntryWithFail()
        {
            Assert.IsFalse(alertService.CreateAlertEntry(null));
        }

        /// <summary>
        /// test case for create alert with fail
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestAlertServiceWithInvalidIp()
        {
            string orginalIP = McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue("ROI", "ip");
            try
            {
                alertService.Dispose();
                ConfigurationManager.AppSettings["AlertServer"] = "192.159.325.4";
                alertService = AlertServiceMQ.Instance;
            }
            finally
            {
                ConfigurationManager.AppSettings["AlertServer"] = orginalIP;
            }
        }
        #endregion 

    }
}
