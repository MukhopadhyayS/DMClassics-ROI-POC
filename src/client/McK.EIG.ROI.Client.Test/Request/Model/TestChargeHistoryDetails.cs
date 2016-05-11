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
    /// Test class for ChargeHistoryDetails
    /// </summary>
    [TestFixture]
    public class TestChargeHistoryDetails 
    {
        #region Fields

        private ChargeHistoryDetails chargeHistoryDetails;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            chargeHistoryDetails = new ChargeHistoryDetails();
        }

        [TearDown]
        public void Dispose()
        {
            chargeHistoryDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the Document Charges
        /// </summary>
        [Test]
        public void Test01DocumentCharges()
        {
            double input = 10.00;
            chargeHistoryDetails.DocumentChargeTotal = input;
            Assert.AreEqual(input, chargeHistoryDetails.DocumentChargeTotal);
        }

        /// <summary>
        /// Test the Fee Charge Details
        /// </summary>
        [Test]
        public void Test02FeeChargeDetails()
        {
            double input = 10.00;
            chargeHistoryDetails.FeeChargeTotal = input;
            Assert.AreEqual(input, chargeHistoryDetails.FeeChargeTotal);
        }

        /// <summary>
        /// Test the Shipping Charge
        /// </summary>
        [Test]
        public void Test03ShippingCharge()
        {
            double input = 10;
            chargeHistoryDetails.ShippingCharge = input;
            Assert.AreEqual(input, chargeHistoryDetails.ShippingCharge);
        }

        /// <summary>
        /// Test the TotalReleaseCost
        /// </summary>
        [Test]
        public void Test04TotalReleaseCost()
        {
            chargeHistoryDetails.DocumentChargeTotal = 10.00;
            chargeHistoryDetails.FeeChargeTotal = 10.00;
            chargeHistoryDetails.ShippingCharge = 10.00;

            Assert.AreEqual(30.00, chargeHistoryDetails.TotalReleaseCost);
        }

        /// <summary>
        /// Test the Released date
        /// </summary>
        [Test]
        public void Test05ReleasedDate()
        {
            string releasedDate = DateTime.Now.ToShortDateString();;
            chargeHistoryDetails.ReleasedDate = releasedDate;
            Assert.AreEqual(releasedDate, chargeHistoryDetails.ReleasedDate);
        }

        /// <summary>
        /// Test the SalesTax Total
        /// </summary>
        [Test]
        public void Test06SalesTaxTotal()
        {
            double inputSalesTaxTotal = 5;
            chargeHistoryDetails.SalesTaxTotal = inputSalesTaxTotal;
            double outputSalesTaxTotal = chargeHistoryDetails.SalesTaxTotal;
            Assert.AreEqual(inputSalesTaxTotal, outputSalesTaxTotal);
        }
        /// <summary>
        /// Test the UnBillable Amount
        /// </summary>
        [Test]
        public void Test06UnBillableAmount()
        {
            double inputUnBillableAmount = 5;
            chargeHistoryDetails.UnBillableAmount = inputUnBillableAmount;
            double outputUnBillableAmount = chargeHistoryDetails.UnBillableAmount;
            Assert.AreEqual(inputUnBillableAmount, outputUnBillableAmount);
        }

        #endregion
    }    
}
