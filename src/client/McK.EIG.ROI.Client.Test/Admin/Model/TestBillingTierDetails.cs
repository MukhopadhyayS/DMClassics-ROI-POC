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
using System.ComponentModel;
using System.Drawing;

using NUnit.Framework;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Test.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    ///  Test class for  BillingtierDetails.
    /// </summary>
    [TestFixture]
    public class TestBillingTierDetails
    {
        private BillingTierDetails billingTierDetails;

        [SetUp]
        public void Initialize()
        {
            billingTierDetails = new BillingTierDetails();
        }

        /// <summary>
        /// Dispose Billingtier details.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            billingTierDetails = null;
        }

        /// <summary>
        /// Test case for Billing tier id.
        /// </summary>
        [Test]
        public void TestBillingTierId()
        {
            long inputBillingTierId = 1;
            billingTierDetails.Id = inputBillingTierId;
            long outputBillingTierId = billingTierDetails.Id;
            Assert.AreEqual(inputBillingTierId, outputBillingTierId);
        }

        /// <summary>
        /// Test case for Billing tier name.
        /// </summary>
        [Test]
        public void TestBillingTierName()
        {
            string inputBillingTierName = "Microfilm";
            billingTierDetails.Name = inputBillingTierName;
            string outputBillingTierName = billingTierDetails.Name;
            Assert.AreEqual(inputBillingTierName, outputBillingTierName);
        }

        /// <summary>
        /// Test case for Billing tier description
        /// </summary>
        [Test]
        public void TestBillingTierDescription()
        {
            string inputBillingTierDescription = "Paper hard copy";
            billingTierDetails.Description = inputBillingTierDescription;
            string outputBillingTierDescription = billingTierDetails.Description;
            Assert.AreEqual(inputBillingTierDescription, outputBillingTierDescription);
        }

        /// <summary>
        /// Test case for IsAssociate.
        /// </summary>
        [Test]
        public void TestBillingTierIsAssociated()
        {
            bool inputIsAssociated = true;
            billingTierDetails.IsAssociated = inputIsAssociated;
            bool outputIsAssociated = billingTierDetails.IsAssociated;
            Assert.AreEqual(inputIsAssociated, outputIsAssociated);
        }
        
        /// <summary>
        /// Test case for record version id.
        /// </summary>
        [Test]
        public void TestRecordVersionId()
        {
            int recordVersionId = 0;
            billingTierDetails.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, billingTierDetails.RecordVersionId);
        }

        /// <summary>
        /// Test case for base charge.
        /// </summary>
        [Test]
        public void TestBaseCharge()
        {
            double inputBaseCharge = 30.50;
            billingTierDetails.BaseCharge = inputBaseCharge;
            double outputBaseCharge = billingTierDetails.BaseCharge;
            Assert.AreEqual(inputBaseCharge, outputBaseCharge);
        }

        /// <summary>
        /// Test case for other page charge.
        /// </summary>
        [Test]
        public void TestOtherPageCharge()
        {
            double inputOtherPageCharge = 20.00;
            billingTierDetails.OtherPageCharge = inputOtherPageCharge;
            double outputOtherPageCharge = billingTierDetails.OtherPageCharge;
            Assert.AreEqual(inputOtherPageCharge, outputOtherPageCharge);
        }

        /// <summary>
        /// Test case for other page charge.
        /// </summary>
        [Test]
        public void TestSeedImage()
        {
            billingTierDetails.Image = null;
            Assert.IsInstanceOfType(typeof(Image), billingTierDetails.Image);
        }

        /// <summary>
        /// Test case for Mediatype details.
        /// </summary>
        [Test]
        public void TestMediaTypeDetails()
        {
            MediaTypeDetails inputMediatype = new MediaTypeDetails();
            inputMediatype.Id = 100;
            inputMediatype.Name = "Test";
            billingTierDetails.MediaType = inputMediatype;
            Assert.IsInstanceOfType(typeof(MediaTypeDetails), billingTierDetails.MediaType);
            Assert.AreEqual(inputMediatype.Name, billingTierDetails.MediaTypeName);
        }

        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString().Replace('.', ' ');
        }
    }
}
