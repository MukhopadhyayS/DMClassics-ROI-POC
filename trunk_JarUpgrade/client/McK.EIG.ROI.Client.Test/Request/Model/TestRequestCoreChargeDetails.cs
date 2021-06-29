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

using McK.EIG.ROI.Client.Request.Model;
//NUnit
using NUnit.Framework;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test case for Test Request Core Charge Details
    /// </summary>
    [TestFixture]
    class TestRequestCoreChargeDetails
    {
        //Holds the object of model class.
        private RequestCoreChargeDetails requestCoreChargeDetails;

        [SetUp]
        public void Initialize()
        {
            requestCoreChargeDetails = new RequestCoreChargeDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestCoreChargeDetails = null;
        }

        /// <summary>
        /// Test case for Request ID.
        /// </summary>
        [Test]
        public void TestRequestID()
        {
            long inputRequestID = 100;
            requestCoreChargeDetails.RequestId = inputRequestID;
            long outputRequestID = requestCoreChargeDetails.RequestId;
            Assert.AreEqual(inputRequestID, outputRequestID);
        }

        /// <summary>
        /// Test case for Billing Location.
        /// </summary>
        [Test]
        public void TestBillingLocation()
        {
            string inputBillingLocation = "AD";
            requestCoreChargeDetails.BillingLoc = inputBillingLocation;
            string outputBillingLocation = requestCoreChargeDetails.BillingLoc;
            Assert.AreEqual(inputBillingLocation, outputBillingLocation);
        }

        /// <summary>
        /// Test case for Billing Location.
        /// </summary>
        [Test]
        public void TestBillingCharge()
        {
            BillingCharge inputBillingCharge = new BillingCharge();
            requestCoreChargeDetails.BillingCharge = inputBillingCharge;
            BillingCharge outputBillingCharge = requestCoreChargeDetails.BillingCharge;
            Assert.IsNotNull(outputBillingCharge);
        }

        /// <summary>
        /// Test case for Billing Location.
        /// </summary>
        [Test]
        public void TestShippingInfo()
        {
            ShippingInfo inputShippingInfo = new ShippingInfo();
            requestCoreChargeDetails.ShippingInfo = inputShippingInfo;
            ShippingInfo outputShippingInfo = requestCoreChargeDetails.ShippingInfo;
            Assert.IsNotNull(outputShippingInfo);
        }

        /// <summary>
        /// Test case for Billing Location.
        /// </summary>
        [Test]
        public void TestSalesTax()
        {
            requestCoreChargeDetails.SalesTax = null;
            SalesTaxChargeDetails outputSalesTaxDetails = requestCoreChargeDetails.SalesTax;
            Assert.IsNotNull(outputSalesTaxDetails);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestPreviouslyReleasedCost()
        {
            double inputPreviouslyReleasedCost = 100;
            requestCoreChargeDetails.PreviouslyReleasedCost = inputPreviouslyReleasedCost;
            double outputPreviouslyReleasedCost = requestCoreChargeDetails.PreviouslyReleasedCost;
            Assert.AreEqual(inputPreviouslyReleasedCost, outputPreviouslyReleasedCost);
        }

         /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestPreviouslyReleaseDate()
        {
            Nullable<DateTime> inputReleaseDate = DateTime.Now;
            requestCoreChargeDetails.ReleaseDate = inputReleaseDate;
            Nullable<DateTime> outputReleaseDate = requestCoreChargeDetails.ReleaseDate;
            Assert.AreEqual(inputReleaseDate, outputReleaseDate);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestReleasedCost()
        {
            double inputReleasedCost = 50;
            requestCoreChargeDetails.ReleasedCost = inputReleasedCost;
            double outputReleasedCost = requestCoreChargeDetails.ReleasedCost;
            Assert.AreEqual(inputReleasedCost, outputReleasedCost);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestTotalPages()
        {
            int inputTotalPages = 2;
            requestCoreChargeDetails.TotalPages = inputTotalPages;
            int outputTotalPages = requestCoreChargeDetails.TotalPages;
            Assert.AreEqual(inputTotalPages, outputTotalPages);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestTotalRequestCost()
        {
            double inputTotalRequestCost = 150;
            requestCoreChargeDetails.TotalRequestCost = inputTotalRequestCost;
            double outputTotalRequestCost = requestCoreChargeDetails.TotalRequestCost;
            Assert.AreEqual(inputTotalRequestCost, outputTotalRequestCost);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestBalanceDue()
        {
            double inputBalanceDue = 150;
            requestCoreChargeDetails.BalanceDue = inputBalanceDue;
            double outputBalanceDue = requestCoreChargeDetails.BalanceDue;
            Assert.AreEqual(inputBalanceDue, outputBalanceDue);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestTotalPagesReleased()
        {
            int inputTotalPagesReleased = 4;
            requestCoreChargeDetails.TotalPagesReleased = inputTotalPagesReleased;
            int outputTotalPagesReleased = requestCoreChargeDetails.TotalPagesReleased;
            Assert.AreEqual(inputTotalPagesReleased, outputTotalPagesReleased);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestSalesTaxTotalAmount()
        {
            double inputSalesTaxTotalAmount = 10;
            requestCoreChargeDetails.SalestaxTotalAmount = inputSalesTaxTotalAmount;
            double outputSalesTaxTotalAmount = requestCoreChargeDetails.SalestaxTotalAmount;
            Assert.AreEqual(inputSalesTaxTotalAmount, outputSalesTaxTotalAmount);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestSalestaxPercentageForBillingLoc()
        {
            double inputSalestaxPercentageForBillingLoc = 5;
            requestCoreChargeDetails.SalestaxPercentageForBillingLoc = inputSalestaxPercentageForBillingLoc;
            double outputSalestaxPercentageForBillingLoc = requestCoreChargeDetails.SalestaxPercentageForBillingLoc;
            Assert.AreEqual(inputSalestaxPercentageForBillingLoc, outputSalestaxPercentageForBillingLoc);
        }

        /// <summary>
        /// Test case for Billing Location.
        /// </summary>
        [Test]
        public void TestBillingType()
        {
            string inputBillingType = "Electronic";
            requestCoreChargeDetails.BillingType = inputBillingType;
            string outputBillingType = requestCoreChargeDetails.BillingType;
            Assert.AreEqual(inputBillingType, outputBillingType);
        }

        /// <summary>
        /// Test case for Is Taxable.
        /// </summary>
        [Test]
        public void TestIsInvoiced()
        {
            bool inputIsInvoiced = true;
            requestCoreChargeDetails.IsInvoiced = inputIsInvoiced;
            bool outputIsInvoiced = requestCoreChargeDetails.IsInvoiced;
            Assert.AreEqual(inputIsInvoiced, outputIsInvoiced);
        }

        /// <summary>
        /// Test case for Is Taxable.
        /// </summary>
        [Test]
        public void TestIsUnbillable()
        {
            bool inputIsUnbillable = true;
            requestCoreChargeDetails.IsUnbillable = inputIsUnbillable;
            bool outputIsUnbillable = requestCoreChargeDetails.IsUnbillable;
            Assert.AreEqual(inputIsUnbillable, outputIsUnbillable);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestInvoicesBalanceDue()
        {
            double inputInvoicesBalanceDue = 50;
            requestCoreChargeDetails.InvoicesBalanceDue = inputInvoicesBalanceDue;
            double outputInvoicesBalanceDue = requestCoreChargeDetails.InvoicesBalanceDue;
            Assert.AreEqual(inputInvoicesBalanceDue, outputInvoicesBalanceDue);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestInvoicesSalesTaxAmount()
        {
            double inputInvoicesSalesTaxAmount = 5;
            requestCoreChargeDetails.InvoicesSalesTaxAmount = inputInvoicesSalesTaxAmount;
            double outputInvoicesSalesTaxAmount = requestCoreChargeDetails.InvoicesSalesTaxAmount;
            Assert.AreEqual(inputInvoicesSalesTaxAmount, outputInvoicesSalesTaxAmount);
        }

        /// <summary>
        /// Test case for Billing Location.
        /// </summary>
        [Test]
        public void TestSalesTaxReasonsList()
        {
            List<SalesTaxReasons> inputSalesTaxReasonsList = new List<SalesTaxReasons>();
            requestCoreChargeDetails.SalesTaxReasonsList = inputSalesTaxReasonsList;
            List<SalesTaxReasons> outputSalesTaxReasonsList = requestCoreChargeDetails.SalesTaxReasonsList;
            Assert.IsNotNull(outputSalesTaxReasonsList);
        }

        /// <summary>
        /// Test case for Billing Type Id For FeeCharge.
        /// </summary>
        [Test]
        public void TestBillingTypeIdForFeeCharge()
        {
            long inputBillingTypeIdForFeeCharge = 10;
            requestCoreChargeDetails.BillingTypeIdForFeeCharge = inputBillingTypeIdForFeeCharge;
            long outputBillingTypeIdForFeeCharge = requestCoreChargeDetails.BillingTypeIdForFeeCharge;
            Assert.AreEqual(inputBillingTypeIdForFeeCharge, outputBillingTypeIdForFeeCharge);
        }

        /// <summary>
        /// Test case for Original Balance.
        /// </summary>
        [Test]
        public void TestOriginalBalance()
        {
            double inputOriginalBalance = 30;
            requestCoreChargeDetails.OriginalBalance = inputOriginalBalance;
            double outputOriginalBalance = requestCoreChargeDetails.OriginalBalance;
            Assert.AreEqual(inputOriginalBalance, outputOriginalBalance);
        }

        /// <summary>
        /// Test case for Payment Amount.
        /// </summary>
        [Test]
        public void TestPaymentAmount()
        {
            double inputPaymentAmount = 5;
            requestCoreChargeDetails.PaymentAmount = inputPaymentAmount;
            double outputPaymentAmount = requestCoreChargeDetails.PaymentAmount;
            Assert.AreEqual(inputPaymentAmount, outputPaymentAmount);
        }

        /// <summary>
        /// Test case for Credit Adjustment Amount.
        /// </summary>
        [Test]
        public void TestCreditAdjustmentAmount()
        {
            double inputCreditAdjustmentAmount = 6;
            requestCoreChargeDetails.CreditAdjustmentAmount = inputCreditAdjustmentAmount;
            double outputCreditAdjustmentAmount = requestCoreChargeDetails.CreditAdjustmentAmount;
            Assert.AreEqual(inputCreditAdjustmentAmount, outputCreditAdjustmentAmount);
        }

        /// <summary>
        /// Test case for Debit Adjustment Amount.
        /// </summary>
        [Test]
        public void TestDebitAdjustmentAmount()
        {
            double inputDebitAdjustmentAmount = 3;
            requestCoreChargeDetails.DebitAdjustmentAmount = inputDebitAdjustmentAmount;
            double outputDebitAdjustmentAmount = requestCoreChargeDetails.DebitAdjustmentAmount;
            Assert.AreEqual(inputDebitAdjustmentAmount, outputDebitAdjustmentAmount);
        }

        /// <summary>
        /// Test case for Billing Location Code.
        /// </summary>
        [Test]
        public void TestBillingLocCode()
        {
            string inputBillingLocCode = "AD";
            requestCoreChargeDetails.BillingLocCode = inputBillingLocCode;
            string outputBillingLocCode = requestCoreChargeDetails.BillingLocCode;
            Assert.AreEqual(inputBillingLocCode, outputBillingLocCode);
        }

        /// <summary>
        /// Test case for Billing Location Name.
        /// </summary>
        [Test]
        public void TestBillingLocName()
        {
            string inputBillingLocName = "AD";
            requestCoreChargeDetails.BillingLocName = inputBillingLocName;
            string outputBillingLocName = requestCoreChargeDetails.BillingLocName;
            Assert.AreEqual(inputBillingLocName, outputBillingLocName);
        }

        /// <summary>
        /// Test case for Apply SalesTax.
        /// </summary>
        [Test]
        public void TestApplySalesTax()
        {
            bool inputApplySalesTax = true;
            requestCoreChargeDetails.ApplySalesTax = inputApplySalesTax;
            bool outputApplySalesTax = requestCoreChargeDetails.ApplySalesTax;
            Assert.AreEqual(inputApplySalesTax, outputApplySalesTax);
        }

        /// <summary>
        /// Test case for IsReleased.
        /// </summary>
        [Test]
        public void TestIsReleased()
        {
            bool inputIsReleased = true;
            requestCoreChargeDetails.IsReleased = inputIsReleased;
            bool outputIsReleased = requestCoreChargeDetails.IsReleased;
            Assert.AreEqual(inputIsReleased, outputIsReleased);
        }

        /// <summary>
        /// Test case for Invoice Adjustment Amount.
        /// </summary>
        [Test]
        public void TestInvoiceAutoAdjustment()
        {
            double inputInvoiceAutoAdjustment = 3;
            requestCoreChargeDetails.InvoiceAutoAdjustment = inputInvoiceAutoAdjustment;
            double outputInvoiceAutoAdjustment = requestCoreChargeDetails.InvoiceAutoAdjustment;
            Assert.AreEqual(inputInvoiceAutoAdjustment, outputInvoiceAutoAdjustment);
        }

        /// <summary>
        /// Test case for Invoice Base Charge.
        /// </summary>
        [Test]
        public void TestInvoiceBaseCharge()
        {
            double inputInvoiceBaseCharge = 60;
            requestCoreChargeDetails.InvoiceBaseCharge = inputInvoiceBaseCharge;
            double outputInvoiceBaseCharge = requestCoreChargeDetails.InvoiceBaseCharge;
            Assert.AreEqual(inputInvoiceBaseCharge, outputInvoiceBaseCharge);
        }

        /// <summary>
        /// Test case for UnApplied Amount.
        /// </summary>
        [Test]
        public void TestUnAppliedAmount()
        {
            double inputUnAppliedAmount = 10;
            requestCoreChargeDetails.UnAppliedAmount = inputUnAppliedAmount;
            double outputUnAppliedAmount = requestCoreChargeDetails.UnAppliedAmount;
            Assert.AreEqual(inputUnAppliedAmount, outputUnAppliedAmount);
        }
    }
}
