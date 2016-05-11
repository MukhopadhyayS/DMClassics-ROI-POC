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
    /// Test class for ReleaseDetails 
    /// </summary>
    [TestFixture]
    public class TestReleaseDetails
    {
        #region Fields

        private ReleaseDetails releaseDetails;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            releaseDetails = new ReleaseDetails();
        }

        [TearDown]
        public void Dispose()
        {
            releaseDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the Release ID.
        /// </summary>
        [Test]
        public void TestId()
        {
            long input = 12345;
            releaseDetails.Id = input;
            Assert.AreEqual(input, releaseDetails.Id);
        }

        /// <summary>
        /// Test the Release Request ID.
        /// </summary>
        [Test]
        public void TestRequestId()
        {
            long input = 12345;
            releaseDetails.RequestId = input;
            Assert.AreEqual(input, releaseDetails.RequestId);
        }

        /// <summary>
        /// Test the Release IsDraft
        /// </summary>
        [Test]
        public void TestIsDraft()
        {
            bool input = true;
            releaseDetails.IsReleased = input;
            Assert.IsTrue(releaseDetails.IsReleased);
        }

        /// <summary>
        /// Test the Release Billing Type.
        /// </summary>
        [Test]
        public void TestBillingType()
        {
            string input = "Attorney";
            releaseDetails.BillingType = input;
            Assert.AreEqual(input, releaseDetails.BillingType);
        }

        /// <summary>
        /// Test the Release Details
        /// </summary>
        [Test]
        public void TestDetails()
        {
            string input = "XML Element";
            releaseDetails.Details = input;
            Assert.AreEqual(input, releaseDetails.Details);
        }

        /// <summary>
        /// Test the Release Shipping Date.
        /// </summary>
        [Test]
        public void TestShippingDate()
        {
            releaseDetails.ShippingDate = DateTime.Today;
            Assert.AreEqual(DateTime.Today, releaseDetails.ShippingDate);
        }

        /// <summary>
        /// Test the Release ShippingInfo
        /// </summary>
        [Test]
        public void TestShippingDetails()
        {
            ShippingInfo input = new ShippingInfo();
            releaseDetails.ShippingDetails = input;
            Assert.IsNotNull(releaseDetails.ShippingDetails);
        }

        /// <summary>
        /// Test the Release DocumentCharges
        /// </summary>
        [Test]
        public void TestDocumentCharges()
        {
            Assert.IsNotNull(releaseDetails.DocumentCharges);
        }

        /// <summary>
        /// Test the Release Details
        /// </summary>
        [Test]
        public void TestFeeChargeDetails()
        {
            Assert.IsNotNull(releaseDetails.DocumentCharges);
        }

        /// <summary>
        /// Test the Release DocumentCharges
        /// </summary>
        [Test]
        public void TestRequestTransaction()
        {
            Assert.IsNotNull(releaseDetails.RequestTransactions);
        }

        /// <summary>
        /// Test the Release DocumentCharges
        /// </summary>
        [Test]
        public void TestReleasedPatientDetails()
        {
            Assert.IsNotNull(releaseDetails.ReleasedPatients);
        }

        /// <summary>
        /// Test the Release Total Page.
        /// </summary>
        [Test]
        public void TestTotalPage()
        {
            int input = 5;
            releaseDetails.TotalPages = input;
            Assert.AreEqual(input, releaseDetails.TotalPages);
        }

        /// <summary>
        /// Test the Release Total Page.
        /// </summary>
        [Test]
        public void TestReleaseCost()
        {
            double input = 5;
            releaseDetails.ReleaseCost = input;
            Assert.AreEqual(input, releaseDetails.ReleaseCost);
        }

        /// <summary>
        /// Test total request cost
        /// </summary>
        [Test]
        public void TestTotalRequestCost()
        {
            double input = 5;
            releaseDetails.TotalRequestCost = input;
            Assert.AreEqual(input, releaseDetails.TotalRequestCost);
        }

        /// <summary>
        /// Test the adjustment total.
        /// </summary>
        [Test]
        public void TestAdjustmentTotal()
        {
            double input = 5.55;
            releaseDetails.AdjustmentTotal = input;
            Assert.AreEqual(input, releaseDetails.AdjustmentTotal);
        }

        /// <summary>
        /// Test the BalanceDue
        /// </summary>
        [Test]
        public void TestBalanceDue()
        {
            double input = 5.55;
            releaseDetails.BalanceDue = input;
            Assert.AreEqual(input, releaseDetails.BalanceDue);
        }


        /// <summary>
        /// Test the PaymenttTotal.
        /// </summary>
        [Test]
        public void TestPaymentTotal()
        {
            double input = 5.55;
            releaseDetails.PaymentTotal = input;
            Assert.AreEqual(input, releaseDetails.PaymentTotal);
        }

        /// <summary>
        /// Test the AdjustmentPaymentTotal.
        /// </summary>
        [Test]
        public void TestAdjustmentPaymentTotal()
        {
            double input = 10;
            releaseDetails.AdjustmentPaymentTotal = input;
            Assert.AreEqual(input, releaseDetails.AdjustmentPaymentTotal);        
        }

        /// <summary>
        /// Test the PreviouslyReleasedCost.
        /// </summary>
        [Test]
        public void TestPreviouslyReleasedCost()
        {
            double input = 20.00;
            releaseDetails.PreviouslyReleasedCost = input;
            Assert.AreEqual(input, releaseDetails.PreviouslyReleasedCost);
        }

        /// <summary>
        /// Test the DocumentChargeTotal
        /// </summary>
        [Test]
        public void TestDocumentChargeTotal()
        {
            double input = 5.55;
            releaseDetails.DocumentChargeTotal = input;
            Assert.AreEqual(input, releaseDetails.DocumentChargeTotal);
        }

        /// <summary>
        /// Test the FeeCharge total.
        /// </summary>
        [Test]
        public void TestFeeChargeTotal()
        {
            double input = 5.55;
            releaseDetails.FeeChargeTotal = input;
            Assert.AreEqual(input, releaseDetails.FeeChargeTotal);
        }

        /// <summary>
        /// Test the releaseDate
        /// </summary>
        [Test]
        public void TestReleaseDate()
        {
            releaseDetails.ReleaseDate = DateTime.Today;
            Assert.AreEqual(DateTime.Today, releaseDetails.ReleaseDate);
        }

        /// <summary>
        /// Test the total page count for all releases
        /// </summary>
        [Test]
        public void TestTotalPageReleased()
        {
            int input = 5;
            releaseDetails.TotalPagesReleased = input;
            Assert.AreEqual(input, releaseDetails.TotalPagesReleased);
        }

        #endregion
    }
}
