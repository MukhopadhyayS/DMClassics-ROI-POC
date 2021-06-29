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
    [Category("PageWeightController")]
    public class TestPageWeightController : TestBase
    {
        #region Fields

        private ROIAdminController roiAdminController;
        private PageWeightDetails weightDetails;

        #endregion

        #region Constructor

        public TestPageWeightController()
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
        public void PageWeightDispose()
        {
            base.LogOff();
            weightDetails = null;
        }

        #region TestMethods

        /// <summary>
        /// Retrive the PageWeight Details.
        /// </summary>
        [Test]
        public void Test01RetriveWeight()
        {
            weightDetails = roiAdminController.RetrieveWeight();
            if (weightDetails != null)
            {
                Assert.IsInstanceOfType(typeof(Int64), weightDetails.PageWeight);
            }
        }

        /// <summary>
        /// Update the page weight details with proper input.
        /// </summary>
        [Test]
        public void Test02UpdateWeight()
        {
            weightDetails.PageWeight = 10;
            PageWeightDetails updateDetails = roiAdminController.UpdateWeight(weightDetails);
            Assert.AreNotEqual(weightDetails.RecordVersionId, updateDetails.RecordVersionId);
        }

        /// <summary>
        /// Update the page weight details with value as zero.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test03UpdateWeight()
        {
            PageWeightDetails weightDetails = new PageWeightDetails();
            weightDetails.PageWeight = 0;
            roiAdminController.UpdateWeight(weightDetails);
        }

        /// <summary>
        /// Update the page weight details with negative value.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test04UpdateWeight()
        {
            PageWeightDetails weightDetails = new PageWeightDetails();
            weightDetails.PageWeight = -5;
            roiAdminController.UpdateWeight(weightDetails);
        }

        /// <summary>
        /// Update the page weight details with value greater than 100.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test05UpdateWeight()
        {
            PageWeightDetails weightDetails = new PageWeightDetails();
            weightDetails.PageWeight = 120;
            roiAdminController.UpdateWeight(weightDetails);
        }

        /// <summary>
        /// Update the page weight with null value.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test06UpdateWeight()
        {
            roiAdminController.UpdateWeight(null);
        }

        #endregion

        #endregion
    }
}
