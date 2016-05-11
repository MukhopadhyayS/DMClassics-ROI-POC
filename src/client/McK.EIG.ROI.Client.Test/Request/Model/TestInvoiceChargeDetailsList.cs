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
    /// Test Class for Invoice Charge Details List.
    /// </summary>
    [TestFixture]
    public class TestInvoiceChargeDetailsList
    {
        private InvoiceChargeDetailsList invoiceChargeDetailsList;

        [SetUp]
        public void Initialize()
        {
            invoiceChargeDetailsList = new InvoiceChargeDetailsList();
        }
        [TearDown]
        public void Dispose()
        {
            invoiceChargeDetailsList = null;
        }

        #region TestMethods

        /// <summary>
        /// Test Case for Invoice Id
        /// </summary>
        [Test]
        public void TestId()
        {
            long input = 12345;
            invoiceChargeDetailsList.Id = input;
            Assert.AreEqual(input, invoiceChargeDetailsList.Id);
        }

        /// <summary>
        /// Test Method for Invoice Charges
        /// </summary>
        [Test]
        public void TestInvoiceCharges()
        {
            List<InvoiceChargeDetails> invoiceCharges = new List<InvoiceChargeDetails>();
            invoiceChargeDetailsList.InvoiceCharges = invoiceCharges;
            Assert.AreEqual(invoiceCharges, invoiceChargeDetailsList.InvoiceCharges);
        }

        /// <summary>
        /// TestCase for Previous Request BalanceDue
        /// </summary>
        [Test]
        public void TestPreviousRequestBalanceDue()
        {
            double inputPreviousRequestBalanceDue = 20.00;
            invoiceChargeDetailsList.PreviousRequestBalanceDue = inputPreviousRequestBalanceDue;
            Assert.AreEqual(inputPreviousRequestBalanceDue, invoiceChargeDetailsList.PreviousRequestBalanceDue);
        }

        /// <summary>
        /// TestCase for Current Request BalanceDue
        /// </summary>
        [Test]
        public void TestCurrentRequestBalanceDue()
        {
            double inputCurrentRequestBalanceDue = 20.00;
            invoiceChargeDetailsList.CurrentRequestBalanceDue = inputCurrentRequestBalanceDue;
            Assert.AreEqual(inputCurrentRequestBalanceDue, invoiceChargeDetailsList.CurrentRequestBalanceDue);
        }

        /// <summary>
        /// TestCase for total credit adjustment
        /// </summary>
        [Test]
        public void TestTotalCreditAdjustment()
        {
            double inputTotalCreditAdjustment = 20.00;
            invoiceChargeDetailsList.TotalCreditAdjustment = inputTotalCreditAdjustment;
            Assert.AreEqual(inputTotalCreditAdjustment, invoiceChargeDetailsList.TotalCreditAdjustment);
        }

        /// <summary>
        /// TestCase for total debit adjustment
        /// </summary>
        [Test]
        public void TestTotalDebditAdjustment()
        {
            double inputTotalDebditAdjustment = 20.00;
            invoiceChargeDetailsList.TotalDebitAdjustment = inputTotalDebditAdjustment;
            Assert.AreEqual(inputTotalDebditAdjustment, invoiceChargeDetailsList.TotalDebitAdjustment);
        }

        /// <summary>
        /// TestCase for total payment amount
        /// </summary>
        [Test]
        public void TestTotalPayment()
        {
            double inputTotalPayment = 20.00;
            invoiceChargeDetailsList.TotalPayment = inputTotalPayment;
            Assert.AreEqual(inputTotalPayment, invoiceChargeDetailsList.TotalPayment);
        }

        #endregion
    }
}
