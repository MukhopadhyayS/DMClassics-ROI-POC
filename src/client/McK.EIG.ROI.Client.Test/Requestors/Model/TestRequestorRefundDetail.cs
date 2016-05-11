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
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{
    /// <summary>
    /// Test case for Requestor Refund Details
    /// </summary>
    [TestFixture]
    class TestRequestorRefundDetail
    {
        private RequestorRefundDetail requestorRefundDetail;

        [SetUp]
        public void Initialize()
        {
            requestorRefundDetail = new RequestorRefundDetail();
        }

        [TearDown]
        public void Dispose()
        {
            requestorRefundDetail = null;
        }

        /// <summary>
        /// Test case for Requestor Id.
        /// </summary>
        [Test]
        public void TestRequestorID()
        {
            long inputRequestorId = 100;
            requestorRefundDetail.RequestorID = inputRequestorId;
            long outputRequestorId = requestorRefundDetail.RequestorID;
            Assert.AreEqual(inputRequestorId, outputRequestorId);
        }

        /// <summary>
        /// Test case for Requestor Name.
        /// </summary>
        [Test]
        public void TestRequestorName()
        {
            string inputRequestorName = "Req Name";
            requestorRefundDetail.RequestorName = inputRequestorName;
            string outputRequestorName = requestorRefundDetail.RequestorName;
            Assert.AreEqual(inputRequestorName, outputRequestorName);
        }

        /// <summary>
        /// Test case for Requestor Type.
        /// </summary>
        [Test]
        public void TestRequestorType()
        {
            string inputRequestorType = "Test Req Name";
            requestorRefundDetail.RequestorType = inputRequestorType;
            string outputRequestorType = requestorRefundDetail.RequestorType;
            Assert.AreEqual(inputRequestorType, outputRequestorType);
        }

        /// <summary>
        /// Test case for Note.
        /// </summary>
        [Test]
        public void TestNote()
        {
            string inputNote = "Test Note";
            requestorRefundDetail.Note = inputNote;
            string outputNote = requestorRefundDetail.Note;
            Assert.AreEqual(inputNote, outputNote);
        }

        /// <summary>
        /// Test case for Refund Date.
        /// </summary>
        [Test]
        public void TestRefundDate()
        {
            DateTime inputRefundDate = DateTime.Now;
            requestorRefundDetail.RefundDate = inputRefundDate;
            DateTime outputRefundDate = requestorRefundDetail.RefundDate;
            Assert.AreEqual(inputRefundDate, outputRefundDate);
        }

        /// <summary>
        /// Test case for Refund Amount.
        /// </summary>
        [Test]
        public void TestRefundAmount()
        {
            double inputRefundAmount = 100.00;
            requestorRefundDetail.RefundAmount = inputRefundAmount;
            double outputRefundAmount = requestorRefundDetail.RefundAmount;
            Assert.AreEqual(inputRefundAmount, outputRefundAmount);
        }


        /// <summary>
        /// Test case for Notes
        /// </summary>
        [Test]
        public void TestNotes()
        {
            string[] inputNotes = {"val1","val2"};
            requestorRefundDetail.Notes = inputNotes;
            string[] outputNotes = requestorRefundDetail.Notes;
            Assert.AreEqual(inputNotes, outputNotes);
        }

        /// <summary>
        /// Test case for RequestorStatementDetail
        /// </summary>
        [Test]
        public void TestRequestorStatementDetail()
        {
            RequestorStatementDetail inputRequestorStatementDetail = new Client.Requestors.Model.RequestorStatementDetail();
            requestorRefundDetail.RequestorStatementDetail = inputRequestorStatementDetail;
            RequestorStatementDetail outputRequestorStatementDetail = requestorRefundDetail.RequestorStatementDetail;
            Assert.AreEqual(inputRequestorStatementDetail, outputRequestorStatementDetail);
        }

        /// <summary>
        /// Test case for Template Id
        /// </summary>
        [Test]
        public void TestTemplateId()
        {
            long inputTemplateId = 100;
            requestorRefundDetail.TemplateId = inputTemplateId;
            long outputTemplateId = requestorRefundDetail.TemplateId;
            Assert.AreEqual(inputTemplateId, outputTemplateId);
        }

        /// <summary>
        /// Test case for Template Name
        /// </summary>
        [Test]
        public void TestTemplateName()
        {
            string inputTemplateName = "template Name";
            requestorRefundDetail.TemplateName = inputTemplateName;
            string outputTemplateName = requestorRefundDetail.TemplateName;
            Assert.AreEqual(inputTemplateName, outputTemplateName);
        }


        /// <summary>
        /// Test case for Output Method
        /// </summary>
        [Test]
        public void TestOutputMethod()
        {
            string inputOutputMethod = "Test Method";
            requestorRefundDetail.OutputMethod = inputOutputMethod;
            string outputOutputMethod = requestorRefundDetail.OutputMethod;
            Assert.AreEqual(inputOutputMethod, outputOutputMethod);
        }

        /// <summary>
        /// Test case for Queue Password
        /// </summary>
        [Test]
        public void TestQueuePassword()
        {
            string inputQueuePassword = "PWD";
            requestorRefundDetail.QueuePassword = inputQueuePassword;
            string outputQueuePassword = requestorRefundDetail.QueuePassword;
            Assert.AreEqual(inputQueuePassword, outputQueuePassword);
        }

    }

}
