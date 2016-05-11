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
    /// Test class for TestRequestCoreChargesInvoiceDetail
    /// </summary>
    [TestFixture]
    public class TestRequestCoreChargesInvoiceDetail
    {
        private RequestCoreChargesInvoiceDetail requestCoreChargesInvoiceDetail;
        [SetUp]
        public void Initialize()
        {
            requestCoreChargesInvoiceDetail = new RequestCoreChargesInvoiceDetail();
        }

        [TearDown]
        public void Dispose()
        {
            requestCoreChargesInvoiceDetail = null;
        }

        /// <summary>
        /// Test the Request Transaction
        /// </summary>
        [Test]
        public void TestRequestTransaction()
        {
            List<RequestTransaction> inputRequestTransaction = new List<RequestTransaction>();
            requestCoreChargesInvoiceDetail.ReqTransaction = inputRequestTransaction;
            List<RequestTransaction> outputRequestTransaction = requestCoreChargesInvoiceDetail.ReqTransaction;
            Assert.IsNotNull(outputRequestTransaction);
        }

        /// <summary>
        /// Test case for Test.
        /// </summary>
        [Test]
        public void Test()
        {
            string inputTest = "Test";
            requestCoreChargesInvoiceDetail.test = inputTest;
            string outputTest = requestCoreChargesInvoiceDetail.test;
            Assert.AreEqual(inputTest, outputTest);
        }

        /// <summary>
        /// Test case for Request Core Delivery Charges ID.
        /// </summary>
        [Test]
        public void TestRequestCoreDeliveryChargesId()
        {
            long inputRequestCoreDeliveryChargesId = 100;
            requestCoreChargesInvoiceDetail.requestCoreDeliveryChargesId = inputRequestCoreDeliveryChargesId;
            long outputRequestCoreDeliveryChargesId = requestCoreChargesInvoiceDetail.requestCoreDeliveryChargesId;
            Assert.AreEqual(inputRequestCoreDeliveryChargesId, outputRequestCoreDeliveryChargesId);
        }

        /// <summary>
        /// Test case for Invoice Created Date.
        /// </summary>
        [Test]
        public void TestInvoiceCreatedDate()
        {
            Nullable<DateTime> inputInvoiceCreatedDate = DateTime.Now;
            requestCoreChargesInvoiceDetail.invoiceCreatedDt = inputInvoiceCreatedDate;
            Nullable<DateTime> outputInvoiceCreatedDate = requestCoreChargesInvoiceDetail.invoiceCreatedDt;
            Assert.AreEqual(inputInvoiceCreatedDate, outputInvoiceCreatedDate);
        }

        /// <summary>
        /// Test case for Release Cost.
        /// </summary>
        [Test]
        public void TestReleaseCost()
        {
            double inputReleaseCost = 50;
            requestCoreChargesInvoiceDetail.releaseCost = inputReleaseCost;
            double outputReleaseCost = requestCoreChargesInvoiceDetail.releaseCost;
            Assert.AreEqual(inputReleaseCost, outputReleaseCost);
        }

        /// <summary>
        /// Test case for Payment Amount.
        /// </summary>
        [Test]
        public void TestPaymentAmount()
        {
            double inputPaymentAmount = 20;
            requestCoreChargesInvoiceDetail.paymentAmount = inputPaymentAmount;
            double outputPaymentAmount = requestCoreChargesInvoiceDetail.paymentAmount;
            Assert.AreEqual(inputPaymentAmount, outputPaymentAmount);
        }

        /// <summary>
        /// Test case for Is Invoiced.
        /// </summary>
        [Test]
        public void TestIsInvoiced()
        {
            bool inputIsInvoiced = true;
            requestCoreChargesInvoiceDetail.IsInvoiced = inputIsInvoiced;
            bool outputIsInvoiced = requestCoreChargesInvoiceDetail.IsInvoiced;
            Assert.AreEqual(inputIsInvoiced, outputIsInvoiced);
        }
    }
}
