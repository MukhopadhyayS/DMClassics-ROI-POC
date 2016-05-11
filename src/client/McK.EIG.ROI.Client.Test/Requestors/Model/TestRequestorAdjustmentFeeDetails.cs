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

using McK.EIG.ROI.Client.Requestors.Model;
//NUnit
using NUnit.Framework;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{
    /// <summary>
    /// Test class for Requestor Adjustment Details
    /// </summary>
    [TestFixture]
    class TestRequestorAdjustmentFeeDetails
    {
        //Holds the object of model class.
        private RequestorAdjustmentsFeeDetails requestorAdjustmentsFeeDetails;

        [SetUp]
        public void Initialize()
        {
            requestorAdjustmentsFeeDetails = new RequestorAdjustmentsFeeDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestorAdjustmentsFeeDetails = null;
        }

        /// <summary>
        /// Test case for Fee Name.
        /// </summary>
        [Test]
        public void TestFeeName()
        {
            string inputFeeName = "Fee Name";
            requestorAdjustmentsFeeDetails.FeeName = inputFeeName;
            string outputFeeName = requestorAdjustmentsFeeDetails.FeeName;
            Assert.AreEqual(inputFeeName, outputFeeName);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestAmount()
        {
            double inputAmount = 100;
            requestorAdjustmentsFeeDetails.Amount = inputAmount;
            double outputAmount = requestorAdjustmentsFeeDetails.Amount;
            Assert.AreEqual(inputAmount, outputAmount);
        }

        /// <summary>
        /// Test case for Sales Tax Amount.
        /// </summary>
        [Test]
        public void TestSalesTaxAmount()
        {
            double inputSalesTaxAmount = 2;
            requestorAdjustmentsFeeDetails.SalesTaxAmount = inputSalesTaxAmount;
            double outputSalesTaxAmount = requestorAdjustmentsFeeDetails.SalesTaxAmount;
            Assert.AreEqual(inputSalesTaxAmount, outputSalesTaxAmount);
        }

        /// <summary>
        /// Test case for Fee Type.
        /// </summary>
        [Test]
        public void TestFeeType()
        {
            string inputFeeType = "Fee Type";
            requestorAdjustmentsFeeDetails.FeeType = inputFeeType;
            string outputFeeType = requestorAdjustmentsFeeDetails.FeeType;
            Assert.AreEqual(inputFeeType, outputFeeType);
        }

        /// <summary>
        /// Test case for Is Taxable.
        /// </summary>
        [Test]
        public void TestIsTaxable()
        {
            bool inputIsTaxable = true;
            requestorAdjustmentsFeeDetails.IsTaxable = inputIsTaxable;
            bool outputIsTaxable = requestorAdjustmentsFeeDetails.IsTaxable;
            Assert.AreEqual(inputIsTaxable, outputIsTaxable);
        }
    }
}
