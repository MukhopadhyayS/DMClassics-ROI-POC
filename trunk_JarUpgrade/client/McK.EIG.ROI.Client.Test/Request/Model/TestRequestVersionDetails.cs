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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test Class for Request Version Details.
    /// </summary>
    [TestFixture]
    public class TestRequestVersionDetails
    {
        private RequestVersionDetails requestVersionDetails;
        private VersionDetails versionDetails;

        [SetUp]
        public void Initialize()
        {
            requestVersionDetails = new RequestVersionDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestVersionDetails = null;
        }

        #region TestMethods

        /// <summary>
        /// Test Case for Version Number.
        /// </summary>
        [Test]
        public void TestVersionNumber()
        {
            long inputVersionNumber = 111;
            requestVersionDetails.VersionNumber = inputVersionNumber;
            long outputVersionNumber = requestVersionDetails.VersionNumber;
            Assert.AreEqual(inputVersionNumber, outputVersionNumber);
        }

        /// <summary>
        /// Test case for parametrized constructor.
        /// </summary>
        [Test]
        public void TestParamaterizedConstructor()
        {
            VersionDetails recordVersion = new VersionDetails();
            recordVersion.VersionNumber = 5;
            RequestVersionDetails requestVersionDetails = new RequestVersionDetails(recordVersion);
            requestVersionDetails.VersionNumber = recordVersion.VersionNumber;
            Assert.AreEqual(requestVersionDetails.VersionNumber, recordVersion.VersionNumber.ToString());
        }
        /// <summary>
        /// Test Case for Name.
        /// </summary>
        [Test]
        public void TestName()
        {
            requestVersionDetails.VersionNumber = 1;
            Assert.AreEqual(ROIConstants.VersionPrefix + requestVersionDetails.VersionNumber, requestVersionDetails.Name);
        }

        /// <summary>
        /// Test Case for Key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            requestVersionDetails.VersionNumber = 1;
            Assert.AreEqual(ROIConstants.VersionPrefix + requestVersionDetails.VersionNumber, requestVersionDetails.Name);
        }

        /// <summary>
        /// Test Case for version sequence
        /// </summary>
        [Test]
        public void TestVersionSequence()
        {
            long inputVersionSeq = 1;
            requestVersionDetails.VersionSeq = inputVersionSeq;
            long outputVersionSeq = requestVersionDetails.VersionSeq;
            Assert.AreEqual(inputVersionSeq, outputVersionSeq);
        }

        /// <summary>
        /// Test Case for document sequence
        /// </summary>
        [Test]
        public void TestDocumentSequence()
        {
            long inputDocumentSeq = 1;
            requestVersionDetails.DocumentSeq = inputDocumentSeq;
            long outputVersionSeq = requestVersionDetails.DocumentSeq;
            Assert.AreEqual(inputDocumentSeq, outputVersionSeq);
        }


        [Test]
        public void TestImage()
        {
            Assert.IsNull(requestVersionDetails.Icon);
        }
        #endregion
    }
}
