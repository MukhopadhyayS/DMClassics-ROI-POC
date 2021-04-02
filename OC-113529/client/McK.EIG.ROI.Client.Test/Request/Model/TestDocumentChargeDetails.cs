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
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for DocumentChargeDetails 
    /// </summary>
    [TestFixture]
    public class TestDocumentChargeDetails
    {
        #region Fields

        private DocumentChargeDetails documentCharge;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            documentCharge = new DocumentChargeDetails();
        }

        [TearDown]
        public void Dispose()
        {
            documentCharge = null;
        }

        #endregion

        /// <summary>
        /// Test the Copies
        /// </summary>
        [Test]
        public void TestCopies()
        {
            int input = 2;
            documentCharge.Copies = input;
            Assert.AreEqual(input, documentCharge.Copies);
        }

        /// <summary>
        /// Test the Pages
        /// </summary>
        [Test]
        public void TestPages()
        {
            int input = 5;
            documentCharge.Pages = input;
            Assert.AreEqual(input, documentCharge.Pages);
        }

        /// <summary>
        /// Test the Amount
        /// </summary>
        [Test]
        public void TestAmount()
        {
            double input = 50.75;
            documentCharge.Amount = input;
            Assert.AreEqual(input, documentCharge.Amount);
        }

        /// <summary>
        /// Test the Billing Tier
        /// </summary>
        [Test]
        public void TestBilllingTier()
        {
            string input = "MicroFilm";
            documentCharge.BillingTier = input;
            Assert.AreEqual(input, documentCharge.BillingTier);
        }

        /// <summary>
        /// Test the Billing Tier ID.
        /// </summary>
        [Test]
        public void TestBillingTierId()
        {
            int input = 1234;
            documentCharge.BillingTierId= input;
            Assert.AreEqual(input, documentCharge.BillingTierId);
        }

        /// <summary>
        /// Test the IsElectronic
        /// </summary>
        [Test]
        public void TestIsElectronic()
        {
            documentCharge.IsElectronic= true;
            Assert.IsTrue(documentCharge.IsElectronic);
        }

        /// <summary>
        /// Test the total pages
        /// </summary>
        [Test]
        public void TestTotalPages()
        {
            documentCharge.TotalPages = 3;
            Assert.IsTrue(documentCharge.TotalPages == 3);
        }

        /// <summary>
        /// Test the release count
        /// </summary>
        [Test]
        public void TestReleaseCount()
        {
            documentCharge.ReleaseCount = 3;
            Assert.IsTrue(documentCharge.ReleaseCount == 3);
        }

        /// <summary>
        /// Test the Remove BaseCharge
        /// </summary>
        [Test]
        public void TestRemoveBaseCharge()
        {   
            documentCharge.RemoveBaseCharge = true;
            Assert.IsTrue(documentCharge.RemoveBaseCharge);
        }

        /// <summary>
        /// Test the Tax Amount
        /// </summary>
        [Test]
        public void TestTaxAmount()
        {
            double inputTaxAmount = 5;
            documentCharge.TaxAmount = inputTaxAmount;
            double outputTaxAmount = documentCharge.TaxAmount;
            Assert.AreEqual(inputTaxAmount, outputTaxAmount);
        }

        /// <summary>
        /// Test the Has Sales Tax
        /// </summary>
        [Test]
        public void TestHasSalesTax()
        {
            bool inputHasSalesTax = true;
            documentCharge.HasSalesTax = inputHasSalesTax;
            bool outputHasSalesTax = documentCharge.HasSalesTax;
            Assert.AreEqual(inputHasSalesTax, outputHasSalesTax);
        }

        /// <summary>
        /// Test the Billing Tier Detail
        /// </summary>
        [Test]
        public void TestBillingTierDetail()
        {
            BillingTierDetails inputBillingTierDetails = new BillingTierDetails();
            documentCharge.BillingTierDetail = inputBillingTierDetails;
            BillingTierDetails outputBillingTierDetails = documentCharge.BillingTierDetail;
            Assert.IsNotNull(outputBillingTierDetails);
        }

        /// <summary>
        /// Test the Billing Tier Detail
        /// </summary>
        [Test]
        public void TestBillingTierDetailWithEmptyValue()
        {
            DocumentChargeDetails docChargeDetails = new DocumentChargeDetails();
            BillingTierDetails inputBillingTierDetails = null;
            docChargeDetails.BillingTierDetail = inputBillingTierDetails;
            BillingTierDetails outputBillingTierDetails = docChargeDetails.BillingTierDetail;
            Assert.IsNotNull(outputBillingTierDetails);
        }

        #endregion
    }
}
