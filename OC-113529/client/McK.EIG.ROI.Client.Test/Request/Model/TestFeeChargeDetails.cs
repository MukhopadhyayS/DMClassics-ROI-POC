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
    /// Test class for FeeChargeDetails 
    /// </summary>
    [TestFixture]
    public class TestFeeChargeDetails
    {
        #region Fields

        private FeeChargeDetails feeCharge;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            feeCharge = new FeeChargeDetails();
        }

        [TearDown]
        public void Dispose()
        {
            feeCharge = null;
        }

        #endregion

        /// <summary>
        /// Test the Amount
        /// </summary>
        [Test]
        public void TestAmount()
        {
            double input = 2;
            feeCharge.Amount = input;
            Assert.AreEqual(input, feeCharge.Amount);
        }

        /// <summary>
        /// Test the feeType
        /// </summary>
        [Test]
        public void TestFeeType()
        {
            string input = "Certification Fee";
            feeCharge.FeeType = input;
            Assert.AreEqual(input, feeCharge.FeeType);
        }

        /// <summary>
        /// Test the IsCustomFee
        /// </summary>
        [Test]
        public void TestIsCustomFee()
        {
            bool input = true;
            feeCharge.IsCustomFee = input;
            Assert.IsTrue(feeCharge.IsCustomFee);
        }

        /// <summary>
        /// Test the Tax Amount
        /// </summary>
        [Test]
        public void TestTaxAmount()
        {
            double inputTaxAmount = 5;
            feeCharge.TaxAmount = inputTaxAmount;
            double outputTaxAmount = feeCharge.TaxAmount;
            Assert.AreEqual(inputTaxAmount, outputTaxAmount);
        }

        /// <summary>
        /// Test the Has Sales Tax
        /// </summary>
        [Test]
        public void TestHasSalesTax()
        {
            bool inputHasSalesTax = true;
            feeCharge.HasSalesTax = inputHasSalesTax;
            bool outputHasSalesTax = feeCharge.HasSalesTax;
            Assert.AreEqual(inputHasSalesTax, outputHasSalesTax);
        }

        /// <summary>
        /// Test the Key
        /// </summary>
        [Test]
        public void TestKey()
        {
            string inputKey = "Fee Name";
            feeCharge.Key = inputKey;
            string outputKey = feeCharge.Key;
            Assert.AreEqual(inputKey, outputKey);
        }
        #endregion
    }
}
