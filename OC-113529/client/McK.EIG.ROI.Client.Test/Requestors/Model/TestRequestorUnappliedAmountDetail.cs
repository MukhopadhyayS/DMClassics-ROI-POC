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
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{

    /// <summary>
    /// Test class for Requestor Unapplied Amount Detail
    /// </summary>
    [TestFixture]
    class TestRequestorUnappliedAmountDetail
    {
        private RequestorUnappliedAmountDetail requestorUnappliedAmountDetail;
        [SetUp]
        public void Initialize()
        {
            requestorUnappliedAmountDetail = new RequestorUnappliedAmountDetail();
        }

        [TearDown]
        public void Dispose()
        {
            requestorUnappliedAmountDetail = null;
        }

        /// <summary>
        /// Test case for Type.
        /// </summary>
        [Test]
        public void TestType()
        {
            string inputType = "Test Type";
            requestorUnappliedAmountDetail.Type = inputType;
            string outputType = requestorUnappliedAmountDetail.Type;
            Assert.AreEqual(inputType, outputType);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestAmount()
        {
            double inputAmount = 100;
            requestorUnappliedAmountDetail.Amount = inputAmount;
            double outputAmount = requestorUnappliedAmountDetail.Amount;
            Assert.AreEqual(inputAmount, outputAmount);
        }


    }
}
