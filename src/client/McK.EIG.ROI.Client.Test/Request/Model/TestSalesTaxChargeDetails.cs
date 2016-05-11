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
    /// Test class for SalesTaxChargeDetails
    /// </summary>
    [TestFixture]
    public class TestSalesTaxChargeDetails
    {
        #region Fields

        private SalesTaxChargeDetails salesTaxChargeDetails;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            salesTaxChargeDetails = new SalesTaxChargeDetails();
        }

        [TearDown]
        public void Dispose()
        {
            salesTaxChargeDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the hasSalesTax
        /// </summary>
        [Test]
        public void TestHasSalesTax()
        {
            bool input = true;
            salesTaxChargeDetails.HasSalesTax = input;
            Assert.AreEqual(input, salesTaxChargeDetails.HasSalesTax);
        }

        /// <summary>
        /// Test the isCustomFee
        /// </summary>
        [Test]
        public void TestIsCustomFee()
        {
            bool input = true;
            salesTaxChargeDetails.IsCustomFee = input;
            Assert.AreEqual(input, salesTaxChargeDetails.IsCustomFee);
        }

        /// <summary>
        /// Test the charge name.
        /// </summary>
        [Test]
        public void TestChargeName()
        {
            string input = "DocumentCharge";
            salesTaxChargeDetails.ChargeName = input;
            Assert.AreEqual(input, salesTaxChargeDetails.ChargeName);
        }

        /// <summary>
        /// Test the key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            string input = "DocumentCharge.key";
            salesTaxChargeDetails.Key = input;
            Assert.AreEqual(input, salesTaxChargeDetails.Key);
        }

        /// <summary>
        /// Test the amount.
        /// </summary>
        [Test]
        public void TestAmount()
        {
            double input = 10.00;
            salesTaxChargeDetails.Amount = input;
            Assert.AreEqual(input, salesTaxChargeDetails.Amount);
        }

        /// <summary>
        /// Test the tax amount.
        /// </summary>
        [Test]
        public void TestTaxAmount()
        {
            double input = 0.10;
            salesTaxChargeDetails.TaxAmount = input;
            Assert.AreEqual(input, salesTaxChargeDetails.TaxAmount);
        }

        /// <summary>
        /// Test the HasBillingTier.
        /// </summary>
        [Test]
        public void TestHasBillingTier()
        {
            bool input = true;
            salesTaxChargeDetails.HasBillingTier = input;
            Assert.AreEqual(input, salesTaxChargeDetails.HasBillingTier);
        }

        #endregion
    }
}



