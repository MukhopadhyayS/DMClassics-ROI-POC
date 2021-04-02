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
    /// Test case for Test Billing Charge
    /// </summary>
    [TestFixture]
    public class TestBillingCharge
    {
        //Holds the object of model class.
        private BillingCharge billingCharge;

        [SetUp]
        public void Initialize()
        {
            billingCharge = new BillingCharge();
        }

        [TearDown]
        public void Dispose()
        {
            billingCharge = null;
        }

        /// <summary>
        /// Test case for Document Charge.
        /// </summary>
        [Test]
        public void TestDocumentChargeWithEmptyValue()
        {
            List<DocumentChargeDetails> inputDocumentCharge = billingCharge.DocumentCharge;
            List<DocumentChargeDetails> outputDocumentCharge = inputDocumentCharge;
            billingCharge.DocumentCharge = outputDocumentCharge;
            Assert.IsNotNull(outputDocumentCharge);
        }

        /// <summary>
        /// Test case for Document Charge.
        /// </summary>
        [Test]
        public void TestFeeChargeWithEmptyValue()
        {
            List<FeeChargeDetails> inputFeeCharge = billingCharge.FeeCharge;
            List<FeeChargeDetails> outputFeeCharge = inputFeeCharge;
            billingCharge.FeeCharge = outputFeeCharge;
            Assert.IsNotNull(outputFeeCharge);
        }

        /// <summary>
        /// Test case for Document Charge.
        /// </summary>
        [Test]
        public void TestDocumentCharge()
        {
            List<DocumentChargeDetails> inputDocumentCharge = new List<DocumentChargeDetails>();
            billingCharge.DocumentCharge = inputDocumentCharge;
            List<DocumentChargeDetails> outputDocumentCharge = billingCharge.DocumentCharge;
            Assert.IsNotNull(outputDocumentCharge);
        }

        /// <summary>
        /// Test case for Fee Charge.
        /// </summary>
        [Test]
        public void TestFeeCharge()
        {
            List<FeeChargeDetails> inputFeeCharge = new List<FeeChargeDetails>();
            billingCharge.FeeCharge = inputFeeCharge;
            List<FeeChargeDetails> outputFeeCharge = billingCharge.FeeCharge;
            Assert.IsNotNull(outputFeeCharge);
        }
    }
}
