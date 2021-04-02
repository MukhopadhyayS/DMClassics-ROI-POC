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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Configuration;
using System.Drawing;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    [TestFixture]
    [Category("InvoiceDueController")]
    public class TestInvoiceDueController : TestBase
    {
        #region Fields

        private ROIAdminController roiAdminController;
        private InvoiceDueDetails dueDetails;

        #endregion

        #region Constructor

        public TestInvoiceDueController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
        }

        #endregion

        #region NUnit

        [SetUp]
        public void Init()
        {
            roiAdminController = ROIAdminController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            roiAdminController = null;
        }

        [TestFixtureTearDown]
        public void InvoiceDueDispose()
        {
            base.LogOff();
            dueDetails = null;
        }

        #region TestMethods

        /// <summary>
        /// Retrive the invoicedue Details.
        /// </summary>
        [Test]
        public void Test01RetriveDays()
        {
            dueDetails = roiAdminController.RetrieveInvoiceDueDays();
            if (dueDetails != null)
            {
                Assert.IsInstanceOfType(typeof(Int32), dueDetails.DueDateInDays);
            }
        }

        /// <summary>
        /// Update the invoice due details with proper input.
        /// </summary>
        [Test]
        public void Test02UpdateDueDays()
        {
            dueDetails.DueDateInDays = 10;
            InvoiceDueDetails updateDetails = roiAdminController.UpdateInvoiceDueDays(dueDetails);
            Assert.AreNotEqual(dueDetails.RecordVersionId, updateDetails.RecordVersionId);
        }

        /// <summary>
        /// Update the invoice due details with value as zero.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test03UpdateDueDays()
        {
            InvoiceDueDetails dueDetails = new InvoiceDueDetails();
            dueDetails.DueDateInDays = 12345687;
            roiAdminController.UpdateInvoiceDueDays(dueDetails);
        }

        /// <summary>
        /// Update the invoice due details with negative value.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test04UpdateDueDays()
        {
            InvoiceDueDetails dueDetails = new InvoiceDueDetails();
            dueDetails.DueDateInDays = -5;
            roiAdminController.UpdateInvoiceDueDays(dueDetails);
        }

        /// <summary>
        /// Update the invoice due details with value greater than 999.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test05UpdateDueDays()
        {
            InvoiceDueDetails dueDetails = new InvoiceDueDetails();
            dueDetails.DueDateInDays = 1120;
            roiAdminController.UpdateInvoiceDueDays(dueDetails);
        }

        /// <summary>
        /// Update the invoice due days with null value.
        /// </summary>
        [Test]
        [ExpectedException(typeof(NullReferenceException))]
        public void Test06UpdateDueDays()
        {
            roiAdminController.UpdateInvoiceDueDays(null);
        }

        #endregion

        #endregion
    }
}
