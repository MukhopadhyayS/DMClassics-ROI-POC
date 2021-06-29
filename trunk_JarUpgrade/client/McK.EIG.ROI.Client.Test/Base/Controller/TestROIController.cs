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
//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Request.Controller;

namespace McK.EIG.ROI.Client.Test.Base.Controller
{
    /// <summary>
    /// Test cases for ROIController
    /// </summary>
    [TestFixture]
    public class TestROIController : TestBase
    {
        #region Fields

        private ROIController roiController;
        UserData userData;

        #endregion Fields

        #region NUnit

        [SetUp]
        public void Init()
        {
            roiController = ROIController.Instance;

            if (UserData.Instance.IsLdapEnabled)
            {
                UserData.Instance.UserId = ConfigurationManager.AppSettings["UserId"];
                UserData.Instance.DomainPassword = ConfigurationManager.AppSettings["DomainPassword"];
                UserData.Instance.Domain = ConfigurationManager.AppSettings["Domain"];
                UserData.Instance.NewPassword = ConfigurationManager.AppSettings["NewPassword"];
            }
            else
            {
                UserData.Instance.UserId = ConfigurationManager.AppSettings["HpfUserId"];
                UserData.Instance.Password = ConfigurationManager.AppSettings["HpfPassword"];
                UserData.Instance.NewPassword = ConfigurationManager.AppSettings["NewPassword"];
            }
            userData = UserData.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            roiController = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Test logon process
        /// </summary>
        [Test]
        public void Test01Logon()
        {
            if (userData.IsLdapEnabled)
            {
                userData = roiController.LogOnLdap();
            }
            else
            {
                userData = roiController.LogOn();
            }
            Assert.IsNotNull(userData);                                  
        }

        /// <summary>
        /// Test logon process with empty username and password
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test01LogonWithEmptyName()
        {
            userData.UserId = string.Empty;
            userData.Password = string.Empty;
            userData = roiController.LogOn();
        }

        /// <summary>
        /// Test logon process with invalid username and password
        /// </summary>
        [Test]
        public void Test01LogonWithInvalidCredentials()
        {
            userData.UserId = "Test$";
            userData.Password = "Test^";
            userData = roiController.LogOn();
            Assert.AreEqual(userData.SignInstate, 1);
            userData.UserId = ConfigurationManager.AppSettings["UserId"];
            userData.Password = ConfigurationManager.AppSettings["Password"];
            userData.SignInstate = 0;
        }   

        /// <summary>
        /// Test configuration data
        /// </summary>
        [Test]
        public void Test02ConfigurationData()
        {  
            roiController.GetConfiguration();
            Assert.IsNotNull(UserData.Instance.ConfigurationData);
        }

        /// <summary>
        /// Test configuration data.
        /// </summary>
        [Test]
        public void Test03FreeformFacilities()
        {
            Test01Logon();
            roiController.RetrieveFreeformFacilities();
            Assert.IsNotNull(UserData.Instance.Facilities);
        }

        /// <summary>
        /// Test LogOff
        /// </summary>
        [Test]
        public void Test07LogOff()
        {
            roiController.LogOff();
            Assert.IsNull(UserData.Instance.ConfigurationData);
        }

        /// <summary>
        /// Test logon process
        /// </summary>
        [Test]
        public void Test08Logon()
        {
            string timeOut = ConfigurationManager.AppSettings["TimeOut"];
            ConfigurationManager.AppSettings["TimeOut"] = "200";
            userData = roiController.LogOn();
            Assert.IsNotNull(userData);
            ConfigurationManager.AppSettings["TimeOut"] = timeOut;
        }   

        /// <summary>
        /// Test case for dispose funtionality
        /// </summary>
        [Test]
        public void TestDispose()
        {
            roiController.Dispose();
        }

        private void SetUserData()
        {
            //UserData userData = UserData.Instance;
            //userData.UserId = ConfigurationManager.AppSettings["UserId"];
            //userData.Password = ConfigurationManager.AppSettings["Password"];
        }
        
        #endregion


    }
}
