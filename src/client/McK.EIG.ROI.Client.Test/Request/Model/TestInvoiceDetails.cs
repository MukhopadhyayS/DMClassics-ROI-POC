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
using McK.EIG.ROI.Client.Request.Model;


namespace McK.EIG.ROI.Client.Test.Request.Model
{  
    /// <summary>
    /// Test class for RequestDetails 
    /// </summary>
    [TestFixture]
    public class TestInvoiceDetails
    {
        private InvoiceDet invoiceDetails;

        [SetUp]
        public void Initialize()
        {
            invoiceDetails = new InvoiceDet();
        }

        [TearDown]
        public void Dispose()
        {
            invoiceDetails = null;
        }

        #region Test Methods

        /// <summary>
        /// Test case for billing location code
        /// </summary>
        [Test]
        public void TestBillingLocationCode()
        {
            string inputBillingLocationCode = "AWL";
            invoiceDetails.BillingLocationCode = inputBillingLocationCode;
            string outputBillingLocationCode = invoiceDetails.BillingLocationCode;
            Assert.AreEqual(inputBillingLocationCode, outputBillingLocationCode);
        }

        /// <summary>
        /// Test case for billing location code
        /// </summary>
        [Test]
        public void TestBillingLocationName()
        {
            string inputBillingLocationName = "AWL";
            invoiceDetails.BillingLocationName = inputBillingLocationName;
            string outputBillingLocationName = invoiceDetails.BillingLocationName;
            Assert.AreEqual(inputBillingLocationName, outputBillingLocationName);
        }
       
        /// <summary>
        /// Test case for invoice base charge.
        /// </summary>
        [Test]
        public void TestBaseCharge()
        {
            double inputBaseCharge = 10.50;
            invoiceDetails.BaseCharge = inputBaseCharge;
            double outputBaseCharge = invoiceDetails.BaseCharge;
            Assert.AreEqual(inputBaseCharge, outputBaseCharge);
        }

        /// <summary>
        /// Test case for invoice paid amount.
        /// </summary>
        [Test]
        public void TestAmountPaid()
        {
            double inputAmountPaid = 5.50;
            invoiceDetails.AmountPaid = inputAmountPaid;
            double outputAmountPaid = invoiceDetails.AmountPaid;
            Assert.AreEqual(inputAmountPaid, outputAmountPaid);
        }

        /// <summary>
        /// Test case for invoice balance due amount.
        /// </summary>
        [Test]
        public void TestBalanceDue()
        {
            double inputBalanceDue = 5;
            invoiceDetails.BalanceDue = inputBalanceDue;
            double outputBalanceDue = invoiceDetails.BalanceDue;
            Assert.AreEqual(inputBalanceDue, outputBalanceDue);
        }

        /// <summary>
        /// Test case for invoice sales tax amount.
        /// </summary>
        [Test]
        public void TestInvoiceSalesTax()
        {
            double inputInvoiceSalesTax = 2;
            invoiceDetails.SalesTax = inputInvoiceSalesTax;
            double outputInvoiceSalesTax = invoiceDetails.SalesTax;
            Assert.AreEqual(inputInvoiceSalesTax, outputInvoiceSalesTax);
        }        

        #endregion
    }
}
