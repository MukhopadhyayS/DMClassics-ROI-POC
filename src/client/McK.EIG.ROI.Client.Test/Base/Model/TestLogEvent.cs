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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Base.Model
{
    /// <summary>
    ///  Test class for LogEvent.
    /// </summary>
    [TestFixture]
    public class TestLogEvent
    {
        #region Fields

        private LogEvent logEvent = new LogEvent();
        private ROIController roiController;

        #endregion

        #region Setup

        [SetUp]
        public void Init()
        {
            roiController = ROIController.Instance;
        }

        #endregion

        #region TearDown

        [TearDown]
        public void Dispose()
        {
            roiController = null;
        }

        #endregion

        #region TestMethods
        /// <summary>
        /// Unit test case for ErrorMessage
        /// </summary>
        [Test]
        public void TestErrorMessage()
        {
            logEvent.Message = "The remote name could not be resolved: '192.168.0.806'";
            Assert.AreEqual("The remote name could not be resolved: '192.168.0.806'", logEvent.Message);
        }

        /// <summary>
        /// Test case for IpAddress
        /// </summary>
        [Test]
        public void TestIpAddress()
        {
            Assert.IsInstanceOfType(typeof(String), LogEvent.CurrentIPAddress());
        }

        /// <summary>
        /// Test case for AuthUser
        /// </summary>
        [Test]
        public void TestAuthUser()
        {
            logEvent.AuthUser = "admin";
            Assert.AreEqual("admin", logEvent.AuthUser);
        }

        /// <summary>
        /// Test case for Code
        /// </summary>
        [Test]
        public void TestCode()
        {
            logEvent.Code = "Connect Failure";
            Assert.AreEqual("Connect Failure", logEvent.Code);
        }

        /// <summary>
        /// Test case for Message
        /// </summary>
        [Test]
        public void TestMessage()
        {
            logEvent.Message = "Unable to connect to remote server";
            Assert.AreEqual("Unable to connect to remote server", logEvent.Message);
        }

        /// <summary>
        /// Test case for Details
        /// </summary>
        [Test]
        public void TestDetails()
        {
            logEvent.Details = "Unable to connect to remote server";
            Assert.AreEqual("Unable to connect to remote server", logEvent.Details);
        }

        /// <summary>
        /// Test case for Timestamp
        /// </summary>
        [Test]
        public void TestTimeStamp()
        {
            Assert.IsInstanceOfType(typeof(DateTime), logEvent.Timestamp);
        }

        /// <summary>
        /// Test case for ToXml
        /// </summary>
        [Test]
        public void TestToXml()
        {
            logEvent.Message = "The remote name could not be resolved: '192.168.0.806'";
            string xml = logEvent.ToXml();
        }

        #endregion
    }
}
