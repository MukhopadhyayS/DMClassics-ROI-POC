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
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for External Source Attachment Details
    /// </summary>
    [TestFixture]
    public class TestExternalSourceAttachmentDetails
    {
        private ExternalSourceAttachmentDetails externalSourceAttachmentDetails;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            externalSourceAttachmentDetails = new ExternalSourceAttachmentDetails();
        }

        /// <summary>
        /// Dispose PatientDetail.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            externalSourceAttachmentDetails = null;
        }

        /// <summary>
        /// Test method for Encounter.
        /// </summary>
        [Test]
        public void TestEncounter()
        {
            string inputEncounter = "E1234";
            externalSourceAttachmentDetails.Encounter = inputEncounter;
            string outputEncounter = externalSourceAttachmentDetails.Encounter;
            Assert.AreEqual(inputEncounter, outputEncounter);
        }

        /// <summary>
        /// Test method for Attachment Files.
        /// </summary>
        [Test]
        public void TestAttachmentFiles()
        {
            List<object> inputAttachmentFiles = new List<object>();
            inputAttachmentFiles.Add(10);
            externalSourceAttachmentDetails.AttachmentFiles = inputAttachmentFiles;
            List<object> outputAttachmentFiles = externalSourceAttachmentDetails.AttachmentFiles;
            Assert.IsNotNull(outputAttachmentFiles);
        }
    }
}
