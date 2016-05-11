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
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{
    /// <summary>
    /// Test class for RequestorHistoryDetail
    /// </summary>
    [TestFixture]
    class TestRequestorHistoryDetail
    {
        //Holds the object of model class.
        private RequestorHistoryDetail requestorHistoryDetail;

        [SetUp]
        public void Initialize()
        {
            requestorHistoryDetail = new RequestorHistoryDetail();
        }

        [TearDown]
        public void Dispose()
        {
            requestorHistoryDetail = null;
        }

        #region TestMethods

        /// <summary>
        /// Test case for Request Id.
        /// </summary>
        [Test]
        public void TestRequestId()
        {
            long inputRequestId = 100;
            requestorHistoryDetail.RequestId = inputRequestId;
            long outputRequestId = requestorHistoryDetail.RequestId;
            Assert.AreEqual(inputRequestId, outputRequestId);
        }

        /// <summary>
        /// Test case for Invoice Balance.
        /// </summary>
        [Test]
        public void TestInvoiceBalance()
        {
            double inputInvoiceBalance = 100.00;
            requestorHistoryDetail.InvoiceBalance = inputInvoiceBalance;
            double outputInvoiceBalance = requestorHistoryDetail.InvoiceBalance;
            Assert.AreEqual(inputInvoiceBalance, outputInvoiceBalance);
        }

        /// <summary>
        /// Test case for Prebill Status.
        /// </summary>
        [Test]
        public void TestPrebillStatus()
        {
            string inputPrebillStatus = "testing";
            requestorHistoryDetail.PrebillStatus = inputPrebillStatus;
            string outputPrebillStatus = requestorHistoryDetail.PrebillStatus;
            Assert.AreEqual(inputPrebillStatus, outputPrebillStatus);
        }

        /// <summary>
        /// Test case for Id.
        /// </summary>
        [Test]
        public void TestId()
        {
            long inputId = 101;
            requestorHistoryDetail.Id = inputId;
            long outputId = requestorHistoryDetail.Id;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test case for Type.
        /// </summary>
        [Test]
        public void TestType()
        {
            string inputType = "Test type";
            requestorHistoryDetail.Type = inputType;
            string outputType = requestorHistoryDetail.Type;
            Assert.AreEqual(inputType, outputType);
        }

        /// <summary>
        /// Test case for Creator Name.
        /// </summary>
        [Test]
        public void TestCreatorName()
        {
            string inputCreatorName = "John";
            requestorHistoryDetail.CreatorName = inputCreatorName;
            string outputCreatorName = requestorHistoryDetail.CreatorName;
            Assert.AreEqual(inputCreatorName, outputCreatorName);
        }

        /// <summary>
        /// Test case for Created Date.
        /// </summary>
        [Test]
        public void TestCreatedDate()
        {
            string inputCreatedDate = "07/12/1986";
            requestorHistoryDetail.CreatedDate = inputCreatedDate;
            string outputCreatedDate = requestorHistoryDetail.CreatedDate;
            Assert.AreEqual(inputCreatedDate, outputCreatedDate);
        }

        /// <summary>
        /// Test case for Template.
        /// </summary>
        [Test]
        public void TestTemplate()
        {
            string inputTemplate = "TestTemplate";
            requestorHistoryDetail.Template = inputTemplate;
            string outputTemplate = requestorHistoryDetail.Template;
            Assert.AreEqual(inputTemplate, outputTemplate);
        }

        /// <summary>
        /// Test case for Request Password.
        /// </summary>
        [Test]
        public void TestRequestPassword()
        {
            string inputRequestPassword = "XXXXXXREQUEST";
            requestorHistoryDetail.RequestPassword = inputRequestPassword;
            string outputRequestPassword = requestorHistoryDetail.RequestPassword;
            Assert.AreEqual(inputRequestPassword, outputRequestPassword);
        }

        /// <summary>
        /// Test case for Queue Password.
        /// </summary>
        [Test]
        public void TestQueuePassword()
        {
            string inputQueuePassword = "XXXXXXQUEUE";
            requestorHistoryDetail.QueuePassword = inputQueuePassword;
            string outputQueuePassword = requestorHistoryDetail.QueuePassword;
            Assert.AreEqual(inputQueuePassword, outputQueuePassword);
        }


        /// <summary>
        /// Test case for Invoice Due Date.
        /// </summary>
        [Test]
        public void TestInvoiceDueDate()
        {
            string inputInvoiceDueDate = "01/01/2013";
            requestorHistoryDetail.InvoiceDueDate = inputInvoiceDueDate;
            string outputInvoiceDueDate = requestorHistoryDetail.InvoiceDueDate;
            Assert.AreEqual(inputInvoiceDueDate, outputInvoiceDueDate);
        }


        /// <summary>
        /// Test case for Balance.
        /// </summary>
        [Test]
        public void TestBalance()
        {
            double inputBalance = 999.99;
            requestorHistoryDetail.Balance = inputBalance;
            double outputBalance = requestorHistoryDetail.Balance;
            Assert.AreEqual(inputBalance, outputBalance);
        }

        /// <summary>
        /// Test case for Aging.
        /// </summary>
        [Test]
        public void TestAging()
        {
            string inputAging = "28";
            requestorHistoryDetail.Aging = inputAging;
            string outputAging = requestorHistoryDetail.Aging;
            Assert.AreEqual(inputAging, outputAging);
        }

        /// <summary>
        /// Test case for Status.
        /// </summary>
        [Test]
        public void TestStatus()
        {
            string inputStatus = "Testing";
            requestorHistoryDetail.Status = inputStatus;
            string outputStatus = requestorHistoryDetail.Status;
            Assert.AreEqual(inputStatus, outputStatus);
        }

        /// <summary>
        /// Test case for Is Migrated.
        /// </summary>
        [Test]
        public void TestIsMigrated()
        {
            bool inputIsMigrated = true;
            requestorHistoryDetail.IsMigrated = inputIsMigrated;
            bool outputIsMigrated = requestorHistoryDetail.IsMigrated;
            Assert.AreEqual(inputIsMigrated, outputIsMigrated);
        }
        #endregion
    }
        
}