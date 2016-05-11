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
    /// Test class for RequestorDetails
    /// </summary>
    [TestFixture]
    public class TestRequestorAdjustmentsPaymentsDetail
    {
        //Holds the object of model class.
        private RequestorAdjustmentsPaymentsDetail requestorAdjustmentsPaymentsDetail;

        [SetUp]
        public void Initialize()
        {
            requestorAdjustmentsPaymentsDetail = new RequestorAdjustmentsPaymentsDetail();
        }

        [TearDown]
        public void Dispose()
        {
            requestorAdjustmentsPaymentsDetail = null;
        }

        /// <summary>
        /// Test case for Invoice ID.
        /// </summary>
        [Test]
        public void TestPaymentId()
        {
            long inputPaymentId = 100;
            requestorAdjustmentsPaymentsDetail.PaymentId = inputPaymentId;
            long outputPaymentId = requestorAdjustmentsPaymentsDetail.PaymentId;
            Assert.AreEqual(inputPaymentId, outputPaymentId);
        }

        /// <summary>
        /// Test case for Date.
        /// </summary>
        [Test]
        public void TestDate()
        {
            string inputDate = DateTime.Now.ToString();
            requestorAdjustmentsPaymentsDetail.Date = inputDate;
            string outputDate = requestorAdjustmentsPaymentsDetail.Date;
            Assert.AreEqual(inputDate, outputDate);
        }

        /// <summary>
        /// Test case for Description.
        /// </summary>
        [Test]
        public void TestDescription()
        {
            string inputDescription = DateTime.Now.ToString();
            requestorAdjustmentsPaymentsDetail.Description = inputDescription;
            string outputDescription = requestorAdjustmentsPaymentsDetail.Description;
            Assert.AreEqual(inputDescription, outputDescription);
        }

        /// <summary>
        /// Test case for Payment Method.
        /// </summary>
        [Test]
        public void TestPaymentMethod()
        {
            string inputPaymentMethod = "Check";
            requestorAdjustmentsPaymentsDetail.PaymentMethod = inputPaymentMethod;
            string outputPaymentMethod = requestorAdjustmentsPaymentsDetail.PaymentMethod;
            Assert.AreEqual(inputPaymentMethod, outputPaymentMethod);
        }

        /// <summary>
        /// Test case for Charges.
        /// </summary>
        [Test]
        public void TestAmount()
        {
            double inputAmount = 100;
            requestorAdjustmentsPaymentsDetail.Amount = inputAmount;
            double outputAmount = requestorAdjustmentsPaymentsDetail.Amount;
            Assert.AreEqual(inputAmount, outputAmount);
        }

        /// <summary>
        /// Test case for UnAppliedAmt.
        /// </summary>
        [Test]
        public void TestUnAppliedAmt()
        {
            double inputUnAppliedAmt = 100;
            requestorAdjustmentsPaymentsDetail.UnAppliedAmt = inputUnAppliedAmt;
            double outputUnAppliedAmt = requestorAdjustmentsPaymentsDetail.UnAppliedAmt;
            Assert.AreEqual(inputUnAppliedAmt, outputUnAppliedAmt);
        }

        /// <summary>
        /// Test case for TxnType.
        /// </summary>
        [Test]
        public void TestTxnType()
        {
            string inputTxnType = "Check";
            requestorAdjustmentsPaymentsDetail.TxnType = inputTxnType;
            string outputTxnType = requestorAdjustmentsPaymentsDetail.PaymentMethod;
            Assert.AreEqual(inputTxnType, outputTxnType);
        }

        /// <summary>
        /// Test case for Invoice ID.
        /// </summary>
        [Test]
        public void TestInvoiceId()
        {
            long inputInvoiceId = 100;
            requestorAdjustmentsPaymentsDetail.Id = inputInvoiceId;
            long outputInvoiceId = requestorAdjustmentsPaymentsDetail.Id;
            Assert.AreEqual(inputInvoiceId, outputInvoiceId);
        }

        /// <summary>
        /// Test case for Applied Amount.
        /// </summary>
        [Test]
        public void TestAppliedAmount()
        {
            double inputAppliedAmount = 100;
            requestorAdjustmentsPaymentsDetail.AppliedAmount = inputAppliedAmount;
            double outputAppliedAmount = requestorAdjustmentsPaymentsDetail.AppliedAmount;
            Assert.AreEqual(inputAppliedAmount, outputAppliedAmount);
        }

        /// <summary>
        /// Test case for Modified Date.
        /// </summary>
        [Test]
        public void TestModifiedDate()
        {
            string inputModifiedDate = DateTime.Now.ToString();
            requestorAdjustmentsPaymentsDetail.ModifiedDate = inputModifiedDate;
            string outputModifiedDate = requestorAdjustmentsPaymentsDetail.ModifiedDate;
            Assert.AreEqual(inputModifiedDate, outputModifiedDate);
        }

        /// <summary>
        /// Test case for Refund Amount.
        /// </summary>
        [Test]
        public void TestRefundAmount()
        {
            double inputRefundAmount = 100;
            requestorAdjustmentsPaymentsDetail.RefundAmount = inputRefundAmount;
            double outputRefundAmount = requestorAdjustmentsPaymentsDetail.RefundAmount;
            Assert.AreEqual(inputRefundAmount, outputRefundAmount);
        }
    }
}
