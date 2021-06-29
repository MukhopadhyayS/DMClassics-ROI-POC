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
    public class TestInvoiceChargeDetails
    {
        private InvoiceChargeDetails invoiceChargeDetails;

        [SetUp]
        public void Initialize()
        {
            invoiceChargeDetails = new InvoiceChargeDetails();
        }

        [TearDown]
        public void Dispose()
        {
            invoiceChargeDetails = null;
        }

        #region TestMethods

        /// <summary>
        /// Test Case for Invoice Id
        /// </summary>
        [Test]
        public void TestId()
        {
            long input = 12345;
            invoiceChargeDetails.Id = input;
            Assert.AreEqual(input, invoiceChargeDetails.Id);
        }

        /// <summary>
        /// Test Method for Base Charge
        /// </summary>
        [Test]
        public void TestBaseCharge()
        {
            double inputBaseCharge = 20.00;
            invoiceChargeDetails.BaseCharge = inputBaseCharge;
            Assert.AreEqual(inputBaseCharge, invoiceChargeDetails.BaseCharge);            
        }

        /// <summary>
        /// TestCase for Paid Amount
        /// </summary>
        [Test]
        public void TestPaidAmount()
        {
            double inputPaidAmount = 20.00;
            invoiceChargeDetails.PaidAmount = inputPaidAmount;
            Assert.AreEqual(inputPaidAmount, invoiceChargeDetails.PaidAmount);
        }

        /// <summary>
        /// TestCase for Balance Due
        /// </summary>
        [Test]
        public void TestBalanceDue()
        {
            double inputBalanceDue = 20.00;
            invoiceChargeDetails.BalanceDue = inputBalanceDue;
            Assert.AreEqual(inputBalanceDue, invoiceChargeDetails.BalanceDue);
            Assert.AreEqual(inputBalanceDue, invoiceChargeDetails.FormattedBalanceDue);
        }

        /// <summary>
        /// TestCase for Invoice SalesTax
        /// </summary>
        [Test]
        public void TestInvoiceSalesTax()
        {
            double inputInvoiceSalesTax = 20.00;
            invoiceChargeDetails.InvoiceSalesTax = inputInvoiceSalesTax;
            Assert.AreEqual(inputInvoiceSalesTax, invoiceChargeDetails.InvoiceSalesTax);
        }

        /// <summary>
        /// TestCase for Total Credit Adjustment
        /// </summary>
        [Test]
        public void TestTotalCreditAdjustment()
        {
            double inputTotalCreditAdjustment = 20.00;
            invoiceChargeDetails.TotalCreditAdjustment = inputTotalCreditAdjustment;
            Assert.AreEqual(inputTotalCreditAdjustment, invoiceChargeDetails.TotalCreditAdjustment);
        }

        /// <summary>
        /// TestCase for Total Debit Adjustment
        /// </summary>
        [Test]
        public void TestTotalDebitAdjustment()
        {
            double inputTotalDebitAdjustment = 20.00;
            invoiceChargeDetails.TotalDebitAdjustment = inputTotalDebitAdjustment;
            Assert.AreEqual(inputTotalDebitAdjustment, invoiceChargeDetails.TotalDebitAdjustment);
        }

        /// <summary>
        /// TestCase for Total Debit Adjustment
        /// </summary>
        [Test]
        public void TestTotalAdjustmentPayment()
        {
            double inputTotalAdjustmentPayment = 20.00;
            invoiceChargeDetails.TotalAdjustmentPayment = inputTotalAdjustmentPayment;
            Assert.AreEqual(inputTotalAdjustmentPayment, invoiceChargeDetails.TotalAdjustmentPayment);
        }

        /// <summary>
        /// TestCase for Total Taxable Amount
        /// </summary>
        [Test]
        public void TestTaxableAmount()
        {
            double inputTaxableAmount = 20.00;
            invoiceChargeDetails.TaxableAmount = inputTaxableAmount;
            Assert.AreEqual(inputTaxableAmount, invoiceChargeDetails.TaxableAmount);
        }

        /// <summary>
        /// TestCase for Total NonTaxable Amount
        /// </summary>
        [Test]
        public void TestNonTaxableAmount()
        {
            double inputNonTaxableAmount = 20.00;
            invoiceChargeDetails.NonTaxableAmount = inputNonTaxableAmount;
            Assert.AreEqual(inputNonTaxableAmount, invoiceChargeDetails.NonTaxableAmount);
        }

        /// <summary>
        /// Test Case for Tax Charge
        /// </summary>
        [Test]
        public void TestTaxCharge()
        {
            double inputTaxCharge = 20.00;
            invoiceChargeDetails.TaxCharge = inputTaxCharge;
            Assert.AreEqual(inputTaxCharge, invoiceChargeDetails.TaxCharge);
        }

        /// <summary>
        /// Test Case for Created Date
        /// </summary>
        [Test]
        public void TestCreatedDate()
        {
            DateTime inputDate = DateTime.Now;
            invoiceChargeDetails.CreatedDate = inputDate;
            Assert.AreEqual(inputDate, invoiceChargeDetails.CreatedDate);
        }

        /// <summary>
        /// Test Case for Request Transactions
        /// </summary>
        [Test]
        public void TestRequestTransactions()
        {
            List<RequestTransaction> inputRequestTransactions = invoiceChargeDetails.RequestTransactions;
            inputRequestTransactions.Add(new RequestTransaction());
            invoiceChargeDetails.RequestTransactions = inputRequestTransactions;
            List<RequestTransaction> outputRequestTransactions = inputRequestTransactions;
            Assert.AreEqual(inputRequestTransactions, outputRequestTransactions);
        }

        /// <summary>
        /// TestCase for Previous Balance Due
        /// </summary>
        [Test]
        public void TestPrevoiusBalanceDue()
        {
            double inputPrevoiusBalanceDue = 20.00;
            invoiceChargeDetails.PreviousBalanceDue = inputPrevoiusBalanceDue;
            double outputPrevoiusBalanceDue = invoiceChargeDetails.PreviousBalanceDue;
            Assert.AreEqual(inputPrevoiusBalanceDue, outputPrevoiusBalanceDue);
        }

        /// <summary>
        /// TestCase for Total Payment
        /// </summary>
        [Test]
        public void TestTotalPayment()
        {
            double inputTotalPayment = 10.00;
            invoiceChargeDetails.TotalPayment = inputTotalPayment;
            double outputTotalPayment = invoiceChargeDetails.TotalPayment;
            Assert.AreEqual(inputTotalPayment, outputTotalPayment);
        }

        /// <summary>
        /// TestCase for Billing Location Code
        /// </summary>
        [Test]
        public void TestBillingLocationCode()
        {
            string inputBillingLocationCode = "AD";
            invoiceChargeDetails.BillingLocationCode = inputBillingLocationCode;
            string outputBillingLocationCode = invoiceChargeDetails.BillingLocationCode;
            Assert.AreEqual(inputBillingLocationCode, outputBillingLocationCode);
        }

        /// <summary>
        /// TestCase for Billing Location Name
        /// </summary>
        [Test]
        public void TestBillingLocationName()
        {
            string inputBillingLocationName = "AD";
            invoiceChargeDetails.BillingLocationName = inputBillingLocationName;
            string outputBillingLocationName = invoiceChargeDetails.BillingLocationName;
            Assert.AreEqual(inputBillingLocationName, outputBillingLocationName);
        }

        /// <summary>
        /// TestCase for Is Invoiced
        /// </summary>
        [Test]
        public void TestIsInvoiced()
        {
            bool inputIsInvoiced = true;
            invoiceChargeDetails.IsInvoiced = inputIsInvoiced;
            bool outputIsInvoiced = invoiceChargeDetails.IsInvoiced;
            Assert.AreEqual(inputIsInvoiced, outputIsInvoiced);
        }

        #endregion
    }
}
