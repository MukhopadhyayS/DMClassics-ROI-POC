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
    /// Test Class for Request Page Details.
    /// </summary>
    [TestFixture]
    public class TestRequestPageDetails
    {
        private RequestPageDetails requestPageDetails;

        [SetUp]
        public void Initialize()
        {
            requestPageDetails = new RequestPageDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestPageDetails = null;
        }

        /// <summary>
        /// Test case for ImNetId.
        /// </summary>
        [Test]
        public void TestIMNetId()
        {
            string inputId = "100";
            requestPageDetails.IMNetId = inputId;
            string outputId = requestPageDetails.IMNetId;
            Assert.AreEqual(inputId, outputId);
        }

        
        /// <summary>
        /// Test Case for Page Number.
        /// </summary>
        [Test]
        public void TestPageNumber()
        {
            int inputPageNumber = 3;
            requestPageDetails.PageNumber = inputPageNumber;
            int outputPageNumber = requestPageDetails.PageNumber;
            Assert.AreEqual(inputPageNumber, outputPageNumber);
        }

        /// <summary>
        /// Test Case for Content Number.
        /// </summary>
        [Test]
        public void TestContentNumber()
        {
            long inputContentNumber = 3;
            requestPageDetails.ContentCount = inputContentNumber;
            long outputContentNumber = requestPageDetails.ContentCount;
            Assert.AreEqual(inputContentNumber, outputContentNumber);
        }

        /// <summary>
        /// Test Case for IsReleased.
        /// </summary>
        [Test]
        public void TestIsReleased()
        {
            bool inputIsReleased = true;
            requestPageDetails.IsReleased = inputIsReleased;
            bool outputIsReleased = requestPageDetails.IsReleased;
            Assert.AreEqual(inputIsReleased, outputIsReleased);
        }

        /// <summary>
        /// Test case for Paramaterized Constructor.
        /// </summary>
        [Test]
        public void TestParamConstructor()
        {
            PageDetails recordPages = new PageDetails();
            recordPages.IMNetId = "1";
            recordPages.PageNumber = 2;
            recordPages.ContentCount = 3;
            recordPages.ContentPageNumber = 4;
            RequestPageDetails requestPages = new RequestPageDetails(recordPages);
            requestPages.IMNetId = recordPages.IMNetId;
            requestPages.PageNumber = recordPages.PageNumber;
            requestPages.ContentCount = recordPages.ContentCount;
            requestPages.PageNumberRequested = recordPages.ContentPageNumber;
            Assert.AreEqual(recordPages.IMNetId, requestPages.IMNetId);
            Assert.AreEqual(recordPages.PageNumber, requestPages.PageNumber);
            Assert.AreEqual(recordPages.ContentCount, requestPages.ContentCount);
            Assert.AreEqual(recordPages.ContentPageNumber, requestPages.PageNumberRequested);
        }


        /// <summary>
        /// Test Case for Selected for release with true.
        /// </summary>
        [Test]
        public void TestSelectedForReleaseTrue()
        {
            requestPageDetails.SelectedForRelease = true;
            Assert.IsTrue(requestPageDetails.SelectedForRelease.Value);
        }

        /// <summary>
        /// Test Case for Selected for release with false.
        /// </summary>
        [Test]
        public void TestSelectedForReleaseFalse()
        {
            requestPageDetails.SelectedForRelease = false;
            Assert.IsFalse(requestPageDetails.SelectedForRelease.Value);
        }

        /// <summary>
        /// Test Case for Name.
        /// </summary>
        [Test]
        public void TestName()
        {
            requestPageDetails.PageNumber = 1;
            Assert.AreEqual(ROIConstants.PagePrefix + requestPageDetails.PageNumber, requestPageDetails.Name);
        }

        /// <summary>
        /// Test Case for Key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            requestPageDetails.PageSeq = 2;
            requestPageDetails.PageNumber = 1;
            requestPageDetails.IMNetId = "A123";
            string inputKey = requestPageDetails.PageSeq + "." + requestPageDetails.PageNumber.ToString() + "." + requestPageDetails.IMNetId;
            Assert.AreEqual(inputKey, requestPageDetails.Key);
        }

        /// <summary>
        /// Test Case for version sequence
        /// </summary>
        [Test]
        public void TestVersionSequence()
        {
            long inputVersionSeq = 1;
            requestPageDetails.VersionSeq = inputVersionSeq;
            long outputVersionSeq = requestPageDetails.VersionSeq;
            Assert.AreEqual(inputVersionSeq, outputVersionSeq);
        }

        /// <summary>
        /// Test Case for page sequence
        /// </summary>
        [Test]
        public void TestPageSequence()
        {
            long inputPageSeq = 1;
            requestPageDetails.PageSeq = inputPageSeq;
            long outputPageSeq = requestPageDetails.PageSeq;
            Assert.AreEqual(inputPageSeq, outputPageSeq);
        }

        /// <summary>
        /// Test Case for page number requested
        /// </summary>
        [Test]
        public void TestPageNumberRequested()
        {
            int inputPageNumberRequested = 1;
            requestPageDetails.PageNumberRequested = inputPageNumberRequested;
            int outputPageNumberRequested = requestPageDetails.PageNumberRequested;
            Assert.AreEqual(inputPageNumberRequested, outputPageNumberRequested);
        }

        /// <summary>
        /// Test Case for PatientSeq
        /// </summary>
        [Test]
        public void TestPatientSeq()
        {
            long input = 123456;
            requestPageDetails.PatientSeq = input;
            long output = requestPageDetails.PatientSeq;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test Case for IsGlobalDocumentPage
        /// </summary>
        [Test]
        public void TestIsGlobalDocumentPage()
        {
            bool input = true;
            requestPageDetails.IsGlobalDocumentPage = input;
            bool output = requestPageDetails.IsGlobalDocumentPage;
            Assert.AreEqual(input, output);
        }

    }
}
