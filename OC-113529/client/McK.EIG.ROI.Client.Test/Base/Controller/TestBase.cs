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
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;
using System.Configuration;

//McK

namespace McK.EIG.ROI.Client.Test.Base.Controller
{
    
   // [TestFixture]
    public class TestBase
    {
        #region Fields

        private ROIAdminController roiAdminController;
        public const string DeficiencyPatientMrn = "PurgeM01";
        public const string PatientFirstName = "Purge";
        public const string PatientMrn = "PurgeM01";
        public const string PatientFacility  = "IE";

        #endregion 

        public TestBase()
        {
            roiAdminController = ROIAdminController.Instance;
        }

        #region Methods

        [TestFixtureSetUp]
        public void LogOn()
        {
            roiAdminController.RetrieveLogonInfo();
            UserData userData = UserData.Instance;
            userData.UserId = ConfigurationManager.AppSettings["UserId"];
            userData.Domain = ConfigurationManager.AppSettings["Domain"];
            if (userData.IsLdapEnabled)
            {
                userData.DomainPassword = ConfigurationManager.AppSettings["DomainPassword"];
                userData = roiAdminController.LogOnLdap();
                if (userData.IsSelfMappingEnabled && userData.UserId == ROIConstants.UserMappingRequired)
                {
                    userData.UserId = ConfigurationManager.AppSettings["UserId"];
                    userData.HpfUserId = ConfigurationManager.AppSettings["HpfUserId"];
                    userData.HpfPassword = ConfigurationManager.AppSettings["HpfPassword"];
                    userData = roiAdminController.UserSelfMapping();
                }
            }
            else
            {
                userData.Reset(true);
                userData.UserId = ConfigurationManager.AppSettings["HpfUserId"];
                userData.Password = ConfigurationManager.AppSettings["HpfPassword"];
                userData = roiAdminController.LogOn();
            }

            Assert.IsNotNull(userData);
            Assert.IsTrue(userData.InvalidLogOnCount == 0);
        }

        [TestFixtureTearDown]
        public void LogOff()
        {
            roiAdminController.LogOff();
        }

        #endregion
      
    }
}

