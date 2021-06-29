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
using System.Collections;
using System.Collections.ObjectModel;
using System.Configuration;
using System.Globalization;
using System.Net;
//NUnit
using NUnit.Framework;

//McK
using McK.EIG.Common.Utility.Logging;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Reports.Controller;
using McK.EIG.ROI.Client.Reports.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Reports.Controller
{
    /// <summary>
    /// Test case for Report Controller.
    /// </summary>
    [TestFixture]
    [Category("TestROIReportController")]
    public class TestROIReportController : TestBase
    {
        #region Fields

        private ROIReportController roiReportController;

        #endregion

        #region Methods

        /// <summary>
        /// Test Method to Generate report.
        /// </summary>
        [Test]
        public void Test01GenerateReport()
        {
            Hashtable parameters = new Hashtable();

            parameters.Add(ROIConstants.ReportId, Convert.ToInt32(ReportType.RequestStatusSummary, System.Threading.Thread.CurrentThread.CurrentUICulture));
            parameters.Add(ROIConstants.TransId, Guid.NewGuid());
            parameters.Add(ROIConstants.ReportStartDate, DateTime.Now.ToString("MM/dd/yyyy hh:mm:ss tt", CultureInfo.InvariantCulture).Replace("-", "/"));
            parameters.Add(ROIConstants.ReportEndDate, DateTime.Now.ToString("MM/dd/yyyy hh:mm:ss tt", CultureInfo.InvariantCulture).Replace("-", "/"));
            parameters.Add(ROIConstants.ReportRequestorType, "Patient");
            parameters.Add(ROIConstants.UserInstanceId, UserData.Instance.UserInstanceId);
            parameters.Add(ROIConstants.ReportRequestStatus, "Auth-Received,Logged,Completed,Denied,Canceled,Pended,Pre-Billed");
            string csvFilePath = ROIReportController.Instance.GenerateReport(UserData.Instance.UserId, parameters);
            Assert.IsNotNull(csvFilePath);
        }

        /// <summary>
        /// Test Method to Generate report.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test02GenerateReport()
        {
            Hashtable parameters = new Hashtable();

            parameters.Add(ROIConstants.TransId, Guid.NewGuid());            
            parameters.Add(ROIConstants.ReportStartDate, DateTime.Now);
            parameters.Add(ROIConstants.ReportEndDate, DateTime.Today.AddDays(-2));
            parameters.Add(ROIConstants.ReportRequestorType, "patient");
            parameters.Add(ROIConstants.UserInstanceId, UserData.Instance.UserInstanceId);
            
            string csvFilePath = ROIReportController.Instance.GenerateReport(UserData.Instance.UserId, parameters);
            Assert.IsNotNull(csvFilePath);
        }

        [Test]
        public void Test03RetrieveUsers()
        {
            IList list = ROIController.Instance.RetrieveUsers(new int[] { Convert.ToInt32(ROISecurityRights.ROIAccessApplication, System.Threading.Thread.CurrentThread.CurrentUICulture), 
                                                                                                                    Convert.ToInt32(ROISecurityRights.ROIAdministration, System.Threading.Thread.CurrentThread.CurrentUICulture) 
                                                                                                                  }
                                                                                                        ); 
            Assert.IsTrue(list.Count > 0);
        }

        #endregion

        #region NUnit

        [SetUp]
        public void Init()
        {
            roiReportController = ROIReportController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            roiReportController = null;
        }

        #endregion
    }
}
