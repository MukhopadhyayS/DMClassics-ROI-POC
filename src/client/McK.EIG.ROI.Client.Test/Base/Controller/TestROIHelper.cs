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
using McK.EIG.ROI.Client.Patient.Model;

using McK.EIG.ROI.Client.Web_References.BillingAdminWS;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

using Microsoft.Web.Services3;


namespace McK.EIG.ROI.Client.Test.Base.Controller
{
    /// <summary>
    ///  Test class for ROIHelper
    /// </summary>  
    [TestFixture]
    public class TestROIHelper
    {
        #region Fields

        private ROIController roiController;               
        private BillingAdminServiceWse billingAdminService;
        private ROIAdminServiceWse roiAdminService;
        private const string FacilityKey = PatientDetails.FreeformFacilityKey;
        private int timeOut = 100000;

        #endregion

        #region Setup

        [SetUp]
        public void Init()
        {
            roiController = ROIController.Instance;            
            billingAdminService = new BillingAdminServiceWse();
            roiAdminService = new ROIAdminServiceWse();
            SetUserData();
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
        /// Invoke with Timeout exception
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestInvoke01WithTimeOutException()
        {
            ROIAdminServiceWse roiAdminService = new ROIAdminServiceWse();
            object[] requestParams = new object[] { true };
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.RoiServiceType);
            ROIController.Timeout = 1;
            roiAdminService.Url = "http://200.128.56.89/roi/services/ROIAdminService88";
            object response = helper.Invoke(roiAdminService, "retrieveAllRequestorTypes", requestParams);
        }

        /// <summary>
        /// Invoke with invalid URL
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestInvoke02WithWebException()
        {
            ROIController.Timeout = timeOut;
            object[] requestParams = new object[] { FacilityKey };
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.RoiServiceType);
            roiAdminService.Url = "http://192.168.0.211:7080/roi/services/securitylogon";
            object response = helper.Invoke(roiAdminService, "retrieveROIAppData", requestParams);
        }

        /// <summary>
        /// Invoke with invalid host name
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestInvoke03WithInvalidHostName()
        {
            object[] requestParams = new object[] { FacilityKey };
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.RoiServiceType);
            roiAdminService.Url = "http://atlantis:7080/roi/services/ROIAdminService";
            object response = helper.Invoke(roiAdminService, "retrieveROIAppData", requestParams);
        }

        /// <summary>
        /// Invoke with invalid params
        /// </summary>
        [Test]
        [ExpectedException(typeof(MissingMethodException))]
        public void TestInvoke04WithGeneralException()
        {
            object[] requestParams = new object[] { FacilityKey };
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.RoiServiceType);
            object response = helper.Invoke(billingAdminService, "retrieveFreeFormFacilitie", requestParams);
        }
        
        /// <summary>
        /// Invoke the ROI service method
        /// </summary>
        [Test]
        public void TestInvoke05ROIService()
        {
            roiController.LogOnLdap();
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.RoiServiceType);
            object response = helper.Invoke(billingAdminService, "retrieveAllMediaTypes", new object[] { false });
            Assert.IsNotNull(response);
        }

        #endregion 

        private void SetUserData()
        {
            UserData userData = UserData.Instance;
            userData.UserId = ConfigurationManager.AppSettings["UserId"];
            userData.Password = ConfigurationManager.AppSettings["Password"];
        }
    }
}
