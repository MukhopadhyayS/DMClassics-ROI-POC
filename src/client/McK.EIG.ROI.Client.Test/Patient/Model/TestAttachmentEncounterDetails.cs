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
using System.ComponentModel;
using System.Drawing;
using System.Collections.Generic;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for AttachmentEncounterDetails
    /// </summary>
    [TestFixture]
    public class TestAttachmentEncounterDetails
    {
        private AttachmentEncounterDetails attachmentEncounter;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            attachmentEncounter = new AttachmentEncounterDetails();
        }

        /// <summary>
        /// Dispose AttachmentEncounterDetails.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            attachmentEncounter = null;
        }

        /// <summary>
        /// Test method for PageCount
        /// </summary>
        [Test]
        public void TestDocumentPageCount()
        {
            int pageCount = 3;
            attachmentEncounter.PageCount = pageCount;
            Assert.AreEqual(pageCount, attachmentEncounter.PageCount);
        }

        /// <summary>
        /// Test method for Encounter.
        /// </summary>
        [Test]
        public void TestEncounter()
        {
            string inputEncounter = "ENC1001";
            attachmentEncounter.Encounter = inputEncounter;
            string outputEncounter = attachmentEncounter.Encounter;
            Assert.AreEqual(inputEncounter, outputEncounter);
        }

        /// <summary>
        /// Test method for Data of Service.
        /// </summary>
        [Test]
        public void TestDateOfService()
        {
            DateTime inputDateTime = DateTime.Now;
            attachmentEncounter.DateOfService = inputDateTime;
            DateTime outputDateTime = attachmentEncounter.DateOfService.Value;
            Assert.AreEqual(inputDateTime, outputDateTime);
        }

        /// <summary>
        /// Test method for Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string inputFacility = "Facility1";
            attachmentEncounter.Facility = inputFacility;
            string outputFacility = attachmentEncounter.Facility;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test method for document with no emcounter
        /// </summary>
        [Test]
        public void TestNoEncounter()
        {
            Assert.AreEqual(AttachmentEncounterDetails.NotApplicable, attachmentEncounter.Name);
        }

        /// <summary>
        /// Test method for Key.
        /// </summary>
        [Test]
        public void TestCompareProperty()
        {
            attachmentEncounter.Encounter = "Enc001";
            Assert.IsNotNull(attachmentEncounter.CompareProperty);
        }

        /// <summary>
        /// Test method for icon
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), attachmentEncounter.Icon);
        }

    }
}
