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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test Class for Request Version Details.
    /// </summary>
    [TestFixture]
    class TestInvoiceOrPrebillPreviewInfo
    {
        private InvoiceOrPrebillPreviewInfo invoiceOrPrebillPreviewInfo;

        [SetUp]
        public void Initialize()
        {
            invoiceOrPrebillPreviewInfo = new InvoiceOrPrebillPreviewInfo();
        }

        [TearDown]
        public void Dispose()
        {
            invoiceOrPrebillPreviewInfo = null;
        }
        #region TestMethods

        /// <summary>
        /// Test Case for RequestCoreId
        /// </summary>
        [Test]
        public void TestRequestCoreId()
        {
            long input = 12345;
            invoiceOrPrebillPreviewInfo.RequestCoreId = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.RequestCoreId);
        }

        /// <summary>
        /// Test Case for Type
        /// </summary>
        [Test]
        public void TestType()
        {
            string input = "TEST";
            invoiceOrPrebillPreviewInfo.Type = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.Type);
        }

        /// <summary>
        /// Test Case for InvoiceDueDays
        /// </summary>
        [Test]
        public void TestInvoiceDueDays()
        {
            long input = 987654;
            invoiceOrPrebillPreviewInfo.InvoiceDueDays = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.InvoiceDueDays);
        }

        /// <summary>
        /// Test Case for LetterTemplateFileId
        /// </summary>
        [Test]
        public void TestLetterTemplateFileId()
        {
            long input = 654321;
            invoiceOrPrebillPreviewInfo.LetterTemplateFileId = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.LetterTemplateFileId);
        }

        /// <summary>
        /// Test Case for LetterTemplateName
        /// </summary>
        [Test]
        public void TestLetterTemplateName()
        {
            string input = "TEMP";
            invoiceOrPrebillPreviewInfo.LetterTemplateName = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.LetterTemplateName);
        }

        /// <summary>
        /// Test Case for RequestStatus
        /// </summary>
        [Test]
        public void TestRequestStatus()
        {
            string input = "TESTING";
            invoiceOrPrebillPreviewInfo.RequestStatus = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.RequestStatus);
        }

        /// <summary>
        /// Test Case for RequestDate
        /// </summary>
        [Test]
        public void TestRequestDate()
        {
            DateTime input = DateTime.Now;
            invoiceOrPrebillPreviewInfo.RequestDate = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.RequestDate);
        }

        /// <summary>
        /// Test Case for InvoicePrebillDate
        /// </summary>
        [Test]
        public void TestInvoicePrebillDate()
        {
            DateTime input = DateTime.Now;
            invoiceOrPrebillPreviewInfo.InvoicePrebillDate = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.InvoicePrebillDate);
        }

        /// <summary>
        /// Test Case for InvoiceDueDate
        /// </summary>
        [Test]
        public void TestInvoiceDueDate()
        {
            DateTime input = DateTime.Now;
            invoiceOrPrebillPreviewInfo.InvoiceDueDate = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.InvoiceDueDate);
        }

        /// <summary>
        /// Test Case for ResendDate
        /// </summary>
        [Test]
        public void TestResendDate()
        {
            DateTime input = DateTime.Now;
            invoiceOrPrebillPreviewInfo.ResendDate = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.ResendDate);
        }

        /// <summary>
        /// Test Case for OutputMethod
        /// </summary>
        [Test]
        public void TestOutputMethod()
        {
            string input = "Test";
            invoiceOrPrebillPreviewInfo.OutputMethod = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.OutputMethod);
        }

        /// <summary>
        /// Test Case for QueuePassword
        /// </summary>
        [Test]
        public void TestQueuePassword()
        {
            string input = "XXXX";
            invoiceOrPrebillPreviewInfo.QueuePassword = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.QueuePassword);
        }

        /// <summary>
        /// Test Case for OverwriteDueDate
        /// </summary>
        [Test]
        public void TestOverwriteDueDate()
        {
            bool input = true;
            invoiceOrPrebillPreviewInfo.OverwriteDueDate = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.OverwriteDueDate);
        }

        /// <summary>
        /// Test Case for InvoiceSalesTax
        /// </summary>
        [Test]
        public void TestInvoiceSalesTax()
        {
            double input = 100.00;
            invoiceOrPrebillPreviewInfo.InvoiceSalesTax = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.InvoiceSalesTax);
        }

        /// <summary>
        /// Test Case for BaseCharge
        /// </summary>
        [Test]
        public void TestBaseCharge()
        {
            double input = 200.00;
            invoiceOrPrebillPreviewInfo.BaseCharge = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.BaseCharge);
        }

        /// <summary>
        /// Test Case for InvoiceBillingLocCode
        /// </summary>
        [Test]
        public void TestInvoiceBillingLocCode()
        {
            string input = "abc";
            invoiceOrPrebillPreviewInfo.InvoiceBillingLocCode = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.InvoiceBillingLocCode);
        }

        /// <summary>
        /// Test Case for InvoiceBillinglocName
        /// </summary>
        [Test]
        public void TestInvoiceBillinglocName()
        {
            string input = "John";
            invoiceOrPrebillPreviewInfo.InvoiceBillinglocName = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.InvoiceBillinglocName);
        }

        /// <summary>
        /// Test Case for InvoiceBalanceDue
        /// </summary>
        [Test]
        public void TestInvoiceBalanceDue()
        {
            double input = 300.00;
            invoiceOrPrebillPreviewInfo.InvoiceBalanceDue = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.InvoiceBalanceDue);
        }

        /// <summary>
        /// Test Case for Amountpaid
        /// </summary>
        [Test]
        public void TestAmountpaid()
        {
            double input = 400.00;
            invoiceOrPrebillPreviewInfo.Amountpaid = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.Amountpaid);
        }

        /// <summary>
        /// Test Case for Notes
        /// </summary>
        [Test]
        public void TestNotes()
        {
            string[] input = {"abc","def"};
            invoiceOrPrebillPreviewInfo.Notes = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.Notes);
        }

        /// <summary>
        /// Test Case for LetterType
        /// </summary>
        [Test]
        public void TestLetterType()
        {
            string input = "test";
            invoiceOrPrebillPreviewInfo.LetterType = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.LetterType);
        }

        /// <summary>
        /// Test Case for InvoicePatients
        /// </summary>
        [Test]
        public void TestInvoicePatients()
        {
            List<RequestCoreDeliveryInvoicePatientsList> input = new List<RequestCoreDeliveryInvoicePatientsList>();
            invoiceOrPrebillPreviewInfo.InvoicePatients = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.InvoicePatients);
        }

        /// <summary>
        /// Test Case for RequestTransaction
        /// </summary>
        [Test]
        public void TestRequestTransaction()
        {
            RequestTransaction input = new RequestTransaction();
            invoiceOrPrebillPreviewInfo.RequestTransaction = input;
            Assert.AreEqual(input, invoiceOrPrebillPreviewInfo.RequestTransaction);
        }

        #endregion

    }
}
