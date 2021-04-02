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
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Drawing;
using System.Collections.Generic;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for VersionDetails
    /// </summary>
    [TestFixture]
    public class TestVersionDetails
    {
        private VersionDetails versionDetails;

        [SetUp]
        public void Initialize()
        {
            versionDetails = new VersionDetails();
        }

        [TearDown]
        public void Dispose()
        {
            versionDetails = null;
        }

        /// <summary>
        /// Test method for VersionNumber.
        /// </summary>
        [Test]
        public void TestVersionNumber()
        {
            int inputVersionNumber = 121;
            versionDetails.VersionNumber = inputVersionNumber;
            int outputVersionNumber = versionDetails.VersionNumber;
            Assert.AreEqual(inputVersionNumber, outputVersionNumber);
            Assert.AreEqual(ROIConstants.VersionPrefix + inputVersionNumber.ToString(), versionDetails.Name);
            versionDetails.VersionNumber = 9999999;
            Assert.AreEqual(ROIConstants.VersionPrefix + "0", versionDetails.Name);
        }

        /// <summary>
        /// Test method for Pages.
        /// </summary>
        [Test]
        public void TestPages()
        {
            SortedList<int, PageDetails> pages;
            pages = versionDetails.Pages;
            Assert.IsInstanceOfType(typeof(SortedList<int, PageDetails>), pages);
        }

        [Test]
        public void TestKey()
        {
            int inputVersionNumber = 121;
            versionDetails = new VersionDetails(inputVersionNumber);
            Assert.AreEqual(inputVersionNumber.ToString(), versionDetails.Key);
            Assert.AreEqual(inputVersionNumber, versionDetails.CompareProperty);
        }

        [Test]
        public void TestImage()
        {            
            Assert.IsNull(versionDetails.Icon);
        }

        [Test]
        public void TestSorter()
        {
           int[] versionNumbers = { 3,1,2,0 };
           List<int> versionList = new List<int>(versionNumbers);
           versionList.Sort(VersionDetails.CustomSorter);           
        }
    }
}
