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
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{
     /// <summary>
    /// Test class for RequestorInvoice
    /// </summary>
    [TestFixture]
    public class TestRequestorInvoice
    {
        //Holds the object of model class.
        private RequestorInvoiceDetails requestorInvoiceDetails;

        [SetUp]
        public void Initialize()
        {
            requestorInvoiceDetails = new RequestorInvoiceDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestorInvoiceDetails = null;
        }

        /// <summary>
        /// Test case for Invoice ID.
        /// </summary>
        [Test]
        public void TestInvoiceId()
        {
            long inputInvoiceId = 100;
            requestorInvoiceDetails.InvoiceId = inputInvoiceId;
            long outputInvoiceId = requestorInvoiceDetails.InvoiceId;
            Assert.AreEqual(inputInvoiceId, outputInvoiceId);
        }

        /// <summary>
        /// Test case for Payment ID.
        /// </summary>
        [Test]
        public void TestPaymentId()
        {
            long inputPaymentId = 10;
            requestorInvoiceDetails.PaymentId = inputPaymentId;
            long outputPaymentId = requestorInvoiceDetails.PaymentId;
            Assert.AreEqual(inputPaymentId, outputPaymentId);
        }

        /// <summary>
        /// Test case for ApplyAmount.
        /// </summary>
        [Test]
        public void TestApplyAmount()
        {
            double inputApplyAmount = 80;
            requestorInvoiceDetails.ApplyAmount = inputApplyAmount;
            double outputApplyAmount = requestorInvoiceDetails.ApplyAmount;
            Assert.AreEqual(inputApplyAmount, outputApplyAmount);
        }

        /// <summary>
        /// Test case for Base Charge.
        /// </summary>
        [Test]
        public void TestBaseCharge()
        {
            double inputBaseCharge = 100;
            requestorInvoiceDetails.BaseCharge = inputBaseCharge;
            double outputBaseCharge = requestorInvoiceDetails.BaseCharge;
            Assert.AreEqual(inputBaseCharge, outputBaseCharge);
        }

        /// <summary>
        /// Test case for Balance.
        /// </summary>
        [Test]
        public void TestBalance()
        {
            double inputBalance = 100;
            requestorInvoiceDetails.Balance = inputBalance;
            double outputBalance = requestorInvoiceDetails.Balance;
            Assert.AreEqual(inputBalance, outputBalance);
        }

        /// <summary>
        /// Test case for Amount Paid.
        /// </summary>
        [Test]
        public void TestAmountPaid()
        {
            double inputAmountPaid = 80;
            requestorInvoiceDetails.AmountPaid = inputAmountPaid;
            double outputAmountPaid = requestorInvoiceDetails.AmountPaid;
            Assert.AreEqual(inputAmountPaid, outputAmountPaid);
        }

        /// <summary>
        /// Test case for Payment Total.
        /// </summary>
        [Test]
        public void TestPaymentTotal()
        {
            double inputPaymentTotal = 80;
            requestorInvoiceDetails.PaymentTotal = inputPaymentTotal;
            double outputPaymentTotal = requestorInvoiceDetails.PaymentTotal;
            Assert.AreEqual(inputPaymentTotal, outputPaymentTotal);
        }

        /// <summary>
        /// Test case for RequestID.
        /// </summary>
        [Test]
        public void TestRequestId()
        {
            long inputRequestId = 80;
            requestorInvoiceDetails.RequestId = inputRequestId;
            long outputRequestId = requestorInvoiceDetails.RequestId;
            Assert.AreEqual(inputRequestId, outputRequestId);
        }

        /// <summary>
        /// Test case for Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string inputFacility = "A";
            requestorInvoiceDetails.Facility = inputFacility;
            string outputFacility = requestorInvoiceDetails.Facility;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test case for CurrentAppliedAmount.
        /// </summary>
        [Test]
        public void TestCurrentAppliedAmount()
        {
            double inputCurrentAppliedAmount = 100;
            requestorInvoiceDetails.CurrentAppliedAmount = inputCurrentAppliedAmount;
            double outputCurrentAppliedAmount = requestorInvoiceDetails.CurrentAppliedAmount;
            Assert.AreEqual(inputCurrentAppliedAmount, outputCurrentAppliedAmount);
        }
    }
}
