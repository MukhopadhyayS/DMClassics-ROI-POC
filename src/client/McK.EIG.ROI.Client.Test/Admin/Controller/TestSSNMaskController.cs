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
    /// <summary>
    /// Test Cases for SSN Mask Controller.
    /// </summary>
    [TestFixture]
    public class TestSSNMaskController : TestBase
    {
        #region Fields

        private ROIAdminController roiAdminController;
        private SSNMaskDetails maskDetails;

        #endregion

        #region Constructor

        public TestSSNMaskController()
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

        #endregion

        #region TestMethods

        /// <summary>
        /// Retrieve the mask Details.
        /// </summary>
        [Test]
        public void Test01RetrieveSsnMask()
        {
            maskDetails = roiAdminController.RetrieveSsnMask();
            if (maskDetails != null)
            {
                Assert.IsInstanceOfType(typeof(bool), maskDetails.IsMasking);
            }
        }
       
        /// <summary>
        /// Update the mask  details with proper input.
        /// </summary>
        [Test]
        public void Test03UpdateSsnMask()
        {
            maskDetails.IsMasking = false;           
            roiAdminController.UpdateSsnMask(maskDetails);
        }
        
        #endregion
    }
}
