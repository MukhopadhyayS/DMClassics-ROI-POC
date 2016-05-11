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
using System.Collections.Generic;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for RequestAttachmentEncounterDetails
    /// </summary>
    [TestFixture]
    class TestRequestAttachmentEncounterDetails
    {
        //Holds the object of model class.
        private RequestAttachmentEncounterDetails requestAttachmentEncounterDetails;

        [SetUp]
        public void Initialize()
        {
            requestAttachmentEncounterDetails = new RequestAttachmentEncounterDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestAttachmentEncounterDetails = null;
        }

        #region TestMethods

        /// <summary>
        /// Test case for Encounter.
        /// </summary>
        [Test]
        public void TestEncounter()
        {
            string inputEncounter = "TESTENCOUNTER";
            requestAttachmentEncounterDetails.Encounter = inputEncounter;
            string outputEncounter = requestAttachmentEncounterDetails.Encounter;
            Assert.AreEqual(inputEncounter, outputEncounter);
        }

        /// <summary>
        /// Test case for DateOfService.
        /// </summary>
        [Test]
        public void TestDateOfService()
        {
            Nullable<DateTime> inputDateOfService = DateTime.Now;

            AttachmentEncounterDetails attachmentEncounterDetails = new AttachmentEncounterDetails();
            attachmentEncounterDetails.Encounter = "test";
            attachmentEncounterDetails.Facility = "test";
            attachmentEncounterDetails.DateOfService = DateTime.Now;
            RequestAttachmentEncounterDetails requestAttachmentEncounterDetails1 = new RequestAttachmentEncounterDetails(attachmentEncounterDetails);

            requestAttachmentEncounterDetails.DateOfService = inputDateOfService;
            Nullable<DateTime> outputDateOfService = requestAttachmentEncounterDetails.DateOfService;
            Assert.AreNotEqual(attachmentEncounterDetails, requestAttachmentEncounterDetails1);
        }

        /// <summary>
        /// Test case for Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string inputFacility = "AD";
            requestAttachmentEncounterDetails.Facility = inputFacility;
            string outputFacility = requestAttachmentEncounterDetails.Facility;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test case for PageCount.
        /// </summary>
        [Test]
        public void TestPageCount()
        {
            int inputPageCount = 20;
            requestAttachmentEncounterDetails.PageCount = inputPageCount;
            int outputPageCount = requestAttachmentEncounterDetails.PageCount;
            Assert.AreEqual(inputPageCount, outputPageCount);
        }

        /// <summary>
        /// Test case for Uuid.
        /// </summary>
        [Test]
        public void TestUuid()
        {
            string inputUuid = "1324";
            requestAttachmentEncounterDetails.Uuid = inputUuid;
            string outputUuid = requestAttachmentEncounterDetails.Uuid;
            Assert.AreEqual(inputUuid, outputUuid);
        }

        /// <summary>
        /// Test case for Volume.
        /// </summary>
        [Test]
        public void TestVolume()
        {
            string inputVolume = "D:";
            requestAttachmentEncounterDetails.Volume = inputVolume;
            string outputVolume = requestAttachmentEncounterDetails.Volume;
            Assert.AreEqual(inputVolume, outputVolume);
        }

        /// <summary>
        /// Test case for Path.
        /// </summary>
        [Test]
        public void TestPath()
        {
            string inputPath = @"D:\temp";
            requestAttachmentEncounterDetails.Path = inputPath;
            string outputPath = requestAttachmentEncounterDetails.Path;
            Assert.AreEqual(inputPath, outputPath);
        }

        /// <summary>
        /// Test case for CreateEncounter.
        /// </summary>
        [Test]
        public void TestCreateEncounter()
        {
            RequestAttachmentDetails requestAttDtl = new RequestAttachmentDetails();

            requestAttDtl.Encounter = "test encoutner";
            requestAttDtl.DateOfService = DateTime.Now;
            requestAttDtl.Facility = "AD";
            requestAttDtl.Uuid = "123456";
            requestAttDtl.Volume = "tst volume";
            requestAttDtl.Path = "tstpath";
           RequestAttachmentEncounterDetails.CreateEncounter(requestAttDtl);
           Assert.NotNull(requestAttDtl);

        }

        /// <summary>
        /// Test case for Name.
        /// </summary>
        [Test]
        public void TestName()
        {
            requestAttachmentEncounterDetails.Encounter = "";
            string name1 = requestAttachmentEncounterDetails.Name;
            requestAttachmentEncounterDetails.Encounter = "encoutner";
            string name2 = requestAttachmentEncounterDetails.Name;
            Assert.AreNotEqual(name1, name2);
        }

        /// <summary>
        /// Test case for Key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            requestAttachmentEncounterDetails.DateOfService = null;
            string key1 = requestAttachmentEncounterDetails.Key;
            requestAttachmentEncounterDetails.DateOfService = DateTime.Now;
            string key2 = requestAttachmentEncounterDetails.Key;
            Assert.AreNotEqual(key1, key2);
        }

        /// <summary>
        /// Test case for Icon.
        /// </summary>
        [Test]
        public void TestIcon()
        {
            System.Drawing.Image icon = requestAttachmentEncounterDetails.Icon;
            Assert.NotNull(icon);
        }

        /// <summary>
        /// Test case for ReleasedItems.
        /// </summary>
        [Test]
        public void TestReleasedItems()
        {
            requestAttachmentEncounterDetails = requestAttachmentEncounterDetails.ReleasedItems;
            Assert.NotNull(requestAttachmentEncounterDetails);
        }

        #endregion

    }
}
