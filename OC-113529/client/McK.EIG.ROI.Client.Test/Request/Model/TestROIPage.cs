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
using System.Linq;
using System.Text;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for ROI Page
    /// </summary>
    [TestFixture]
    public class TestROIPage
    {
        private ROIPage roiPage;

        [SetUp]
        public void Initialize()
        {
            roiPage = new ROIPage();
        }

        [TearDown]
        public void Dispose()
        {
            roiPage = null;
        }

        /// <summary>
        /// Test the Is Self Encounter
        /// </summary>
        [Test]
        public void TestIsSelfPayEncounter()
        {
            bool inputIsSelfPayEncounter = true;
            roiPage.IsSelfEncounter = inputIsSelfPayEncounter;
            bool outputIsSelfPayEncounter = roiPage.IsSelfEncounter;
            Assert.AreEqual(inputIsSelfPayEncounter, outputIsSelfPayEncounter);
        }

        /// <summary>
        /// Test the Is Self Encounter
        /// </summary>
        [Test]
        public void TestPageSequence()
        {
            long inputPageSequence = 10;
            roiPage.PageSequence = inputPageSequence;
            long outputPageSequence = roiPage.PageSequence;
            Assert.AreEqual(inputPageSequence, outputPageSequence);
        }
    }
}
