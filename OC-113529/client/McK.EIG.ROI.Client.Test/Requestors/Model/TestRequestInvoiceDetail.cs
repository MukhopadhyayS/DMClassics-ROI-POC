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
    /// Test class for RequestInvoiceDetail
    /// </summary>
    [TestFixture]
    public class TestRequestInvoiceDetail
    {
        //Holds the object of model class.
        private RequestInvoiceDetail requestInvoiceDetail;

        [SetUp]
        public void Initialize()
        {
            requestInvoiceDetail = new RequestInvoiceDetail();
        }

        [TearDown]
        public void Dispose()
        {
            requestInvoiceDetail = null;
        }

        /// <summary>
        /// Test case for OriginalTotalAdjPay.
        /// </summary>
        [Test]
        public void TestOriginalTotalAdjPay()
        {
            double inputOriginalTotalAdjPay = 100;
            requestInvoiceDetail.OriginalTotalAdjPay = inputOriginalTotalAdjPay;
            double outputOriginalTotalAdjPay = requestInvoiceDetail.OriginalTotalAdjPay;
            Assert.AreEqual(inputOriginalTotalAdjPay, outputOriginalTotalAdjPay);
        }

        /// <summary>
        /// Test case for ApplyAmt.
        /// </summary>
        [Test]
        public void TestApplyAmt()
        {
            double inputApplyAmt = 100;
            requestInvoiceDetail.ApplyAmt = inputApplyAmt;
            double outputApplyAmt = requestInvoiceDetail.ApplyAmt;
            Assert.AreEqual(inputApplyAmt, outputApplyAmt);
        }

        /// <summary>
        /// Test case for AdjPay.
        /// </summary>
        [Test]
        public void TestAdjPay()
        {
            double inputAdjPay = 100;
            requestInvoiceDetail.AdjPay = inputAdjPay;
            double outputAdjPay = requestInvoiceDetail.AdjPay;
            Assert.AreEqual(inputAdjPay, outputAdjPay);
        }

        /// <summary>
        /// Test case for Invoice Id.
        /// </summary>
        [Test]
        public void TestInvoiceId()
        {
            long inputInvoiceId = 100;
            requestInvoiceDetail.Id = inputInvoiceId;
            long outputInvoiceId = requestInvoiceDetail.Id;
            Assert.AreEqual(inputInvoiceId, outputInvoiceId);
        }

        /// <summary>
        /// Test case for Created Date.
        /// </summary>
        [Test]
        public void TestCreatedDate()
        {
            DateTime inputCreatedDate = DateTime.Now;
            requestInvoiceDetail.CreatedDate = inputCreatedDate;
            DateTime outputCreatedDate = requestInvoiceDetail.CreatedDate;
            Assert.AreEqual(inputCreatedDate, outputCreatedDate);
        }

        /// <summary>
        /// Test case for Request Id.
        /// </summary>
        [Test]
        public void TestRequestId()
        {
            long inputRequestId = 100;
            requestInvoiceDetail.RequestId = inputRequestId;
            long outputRequestId = requestInvoiceDetail.RequestId;
            Assert.AreEqual(inputRequestId, outputRequestId);
        }

        /// <summary>
        /// Test case for Description.
        /// </summary>
        [Test]
        public void TestDescription()
        {
            string inputDescription = DateTime.Now.ToString();
            requestInvoiceDetail.Description = inputDescription;
            string outputDescription = requestInvoiceDetail.Description;
            Assert.AreEqual(inputDescription, outputDescription);
        }

        /// <summary>
        /// Test case for Charges.
        /// </summary>
        [Test]
        public void TestCharges()
        {
            double inputCharges = 100;
            requestInvoiceDetail.Charges = inputCharges;
            double outputCharges = requestInvoiceDetail.Charges;
            Assert.AreEqual(inputCharges, outputCharges);
        }

        /// <summary>
        /// Test case for Adjustments.
        /// </summary>
        [Test]
        public void TestAdjustments()
        {
            double inputAdjustments = 100;
            requestInvoiceDetail.Adjustments = inputAdjustments;
            double outputAdjustments = requestInvoiceDetail.Adjustments;
            Assert.AreEqual(inputAdjustments, outputAdjustments);
        }

        /// <summary>
        /// Test case for Balance.
        /// </summary>
        [Test]
        public void TestBalance()
        {
            double inputBalance = 100;
            requestInvoiceDetail.Balance = inputBalance;
            double outputBalance = requestInvoiceDetail.Balance;
            Assert.AreEqual(inputBalance, outputBalance);
        }

        /// <summary>
        /// Test case for Invoice Status.
        /// </summary>
        [Test]
        public void TestInvoiceStatus()
        {
            string inputInvoiceStatus = DateTime.Now.ToString();
            requestInvoiceDetail.InvoiceStatus = inputInvoiceStatus;
            string outputInvoiceStatus = requestInvoiceDetail.InvoiceStatus;
            Assert.AreEqual(inputInvoiceStatus, outputInvoiceStatus);
        }

        /// <summary>
        /// Test case for UnBillable Amount.
        /// </summary>
        [Test]
        public void TestUnBillableAmount()
        {
            double inputUnBillableAmount = 100;
            requestInvoiceDetail.UnBillableAmount = inputUnBillableAmount;
            double outputUnBillableAmount = requestInvoiceDetail.UnBillableAmount;
            Assert.AreEqual(inputUnBillableAmount, outputUnBillableAmount);
        }

        /// <summary>
        /// Test Method for Requestor Adjustments Payments.
        /// </summary>
        [Test]
        public void TestReqAdjPay()
        {
            requestInvoiceDetail.ReqAdjPay = requestInvoiceDetail.ReqAdjPay;
            Assert.AreEqual(requestInvoiceDetail.ReqAdjPay, requestInvoiceDetail.ReqAdjPay);
        }

        /// <summary>
        /// Test case for Apply Amount.
        /// </summary>
        [Test]
        public void TestApplyAmount()
        {
            double inputApplyAmount = 100;
            requestInvoiceDetail.ApplyAmount = inputApplyAmount;
            double outputApplyAmount = requestInvoiceDetail.ApplyAmount;
            Assert.AreEqual(inputApplyAmount, outputApplyAmount);
        }

        /// <summary>
        /// Test case for  Applied Amount.
        /// </summary>
        [Test]
        public void TestAppliedAmount()
        {
            double inputAppliedAmount = 100;
            requestInvoiceDetail.AppliedAmount = inputAppliedAmount;
            double outputAppliedAmount = requestInvoiceDetail.AppliedAmount;
            Assert.AreEqual(inputAppliedAmount, outputAppliedAmount);
        }

        /// <summary>
        /// Test case for Applied Amount Copy.
        /// </summary>
        [Test]
        public void TestAppliedAmountCopy()
        {
            double inputAppliedAmountCopy = 100;
            requestInvoiceDetail.AppliedAmountCopy = inputAppliedAmountCopy;
            double outputAppliedAmountCopy = requestInvoiceDetail.AppliedAmountCopy;
            Assert.AreEqual(inputAppliedAmountCopy, outputAppliedAmountCopy);
        }

        /// <summary>
        /// Test case for Applied Amount Total.
        /// </summary>
        [Test]
        public void TestAppliedAmountTotal()
        {
            double inputAppliedAmountTotal = 50;
            requestInvoiceDetail.AppliedAmountTotal = inputAppliedAmountTotal;
            double outputAppliedAmountTotal = requestInvoiceDetail.AppliedAmountTotal;
            Assert.AreEqual(inputAppliedAmountTotal, outputAppliedAmountTotal);
        }

        /// <summary>
        /// Test case for Payment Description.
        /// </summary>
        [Test]
        public void TestPaymentDescription()
        {
            string inputPaymentDescription = "By check";
            requestInvoiceDetail.PaymentDescription = inputPaymentDescription;
            string outputPaymentDescription = requestInvoiceDetail.PaymentDescription;
            Assert.AreEqual(inputPaymentDescription, outputPaymentDescription);
        }

        /// <summary>
        /// Test case for Payment Method.
        /// </summary>
        [Test]
        public void TestPaymentMethod()
        {
            string inputPaymentMethod = "Check";
            requestInvoiceDetail.PaymentMethod = inputPaymentMethod;
            string outputPaymentMethod = requestInvoiceDetail.PaymentMethod;
            Assert.AreEqual(inputPaymentMethod, outputPaymentMethod);
        }

        /// <summary>
        /// Test case for ModifiedDate.
        /// </summary>
        [Test]
        public void TestModifiedDate()
        {
            string inputModifiedDate = DateTime.Now.ToString();
            requestInvoiceDetail.ModifiedDate = inputModifiedDate;
            string outputModifiedDate = requestInvoiceDetail.ModifiedDate;
            Assert.AreEqual(inputModifiedDate, outputModifiedDate);
        }

        /// <summary>
        /// Test case for OriginalAdjustment.
        /// </summary>
        [Test]
        public void TestOriginalAdjustment()
        {
            double inputOriginalAdjustment = 1000;
            requestInvoiceDetail.OriginalAdjustment = inputOriginalAdjustment;
            double outputOriginalAdjustment = requestInvoiceDetail.OriginalAdjustment;
            Assert.AreEqual(inputOriginalAdjustment, outputOriginalAdjustment);
        }

        /// <summary>
        /// Test case for InvoiceType.
        /// </summary>
        [Test]
        public void TestInvoiceType()
        {
            string inputInvoiceType = "Test";
            requestInvoiceDetail.InvoiceType = inputInvoiceType;
            string outputInvoiceType = requestInvoiceDetail.InvoiceType;
            Assert.AreEqual(inputInvoiceType, outputInvoiceType);
        }

        /// <summary>
        /// Test case for Payments.
        /// </summary>
        [Test]
        public void TestPayments()
        {
            double inputPayments = 1001;
            requestInvoiceDetail.Payments = inputPayments;
            double outputPayments = requestInvoiceDetail.Payments;
            Assert.AreEqual(inputPayments, outputPayments);
        }

        /// <summary>
        /// Test case for PayAmount.
        /// </summary>
        [Test]
        public void TestPayAmount()
        {
            double inputPayAmount = 1001;
            requestInvoiceDetail.PayAmount = inputPayAmount;
            double outputPayAmount = requestInvoiceDetail.PayAmount;
            Assert.AreEqual(inputPayAmount, outputPayAmount);
        }

        /// <summary>
        /// Test case for AdjAmount.
        /// </summary>
        [Test]
        public void TestAdjAmount()
        {
            double inputAdjAmount = 1001;
            requestInvoiceDetail.AdjAmount = inputAdjAmount;
            double outputAdjAmount = requestInvoiceDetail.AdjAmount;
            Assert.AreEqual(inputAdjAmount, outputAdjAmount);
        }

        /// <summary>
        /// Test case for PayAdjTotal.
        /// </summary>
        [Test]
        public void TestPayAdjTotal()
        {
            double inputPayAdjTotal = 1001;
            requestInvoiceDetail.PayAdjTotal = inputPayAdjTotal;
            double outputPayAdjTotal = requestInvoiceDetail.PayAdjTotal;
            Assert.AreEqual(inputPayAdjTotal, outputPayAdjTotal);
        }

        /// <summary>
        /// Test case for HasMaskedRequestorFacility.
        /// </summary>
        [Test]
        public void TestHasMaskedRequestorFacility()
        {
            bool inputHasMaskedRequestorFacility = true;
            requestInvoiceDetail.HasMaskedRequestorFacility = inputHasMaskedRequestorFacility;
            bool outputHasMaskedRequestorFacility = requestInvoiceDetail.HasMaskedRequestorFacility;
            Assert.AreEqual(inputHasMaskedRequestorFacility, outputHasMaskedRequestorFacility);
        }

        /// <summary>
        /// Test case for HasBlockedRequestorFacility.
        /// </summary>
        [Test]
        public void TestHasBlockedRequestorFacility()
        {
            bool inputHasBlockedRequestorFacility = true;
            requestInvoiceDetail.HasBlockedRequestorFacility = inputHasBlockedRequestorFacility;
            bool outputHasBlockedRequestorFacility = requestInvoiceDetail.HasBlockedRequestorFacility;
            Assert.AreEqual(inputHasBlockedRequestorFacility, outputHasBlockedRequestorFacility);
        }

        /// <summary>
        /// Test case for Unbillable.
        /// </summary>
        [Test]
        public void TestUnbillable()
        {
            string inputUnbillable = "test";
            requestInvoiceDetail.Unbillable = inputUnbillable;
            string outputUnbillable = requestInvoiceDetail.Unbillable;
            Assert.AreEqual(inputUnbillable, outputUnbillable);
        }

        /// <summary>
        /// Test case for BillableIcon.
        /// </summary>
        [Test]
        public void TestBillableIcon()
        {
            System.Drawing.Image inputBillableIcon = System.Drawing.Image.FromFile("");
            requestInvoiceDetail.BillableIcon = inputBillableIcon;
            System.Drawing.Image outputBillableIcon = requestInvoiceDetail.BillableIcon;
            Assert.AreEqual(inputBillableIcon, outputBillableIcon);
        }

        /// <summary>
        /// Test case for RefundAmount.
        /// </summary>
        [Test]
        public void TestRefundAmount()
        {
            double inputRefundAmount = 100.00;
            requestInvoiceDetail.RefundAmount = inputRefundAmount;
            double outputRefundAmount = requestInvoiceDetail.RefundAmount;
            Assert.AreEqual(inputRefundAmount, outputRefundAmount);
        }

        /// <summary>
        /// Test case for Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string inputFacility = "AD";
            requestInvoiceDetail.Facility = inputFacility;
            string outputFacility = requestInvoiceDetail.Facility;
            Assert.AreEqual(inputFacility, outputFacility);
        }
    }
}
