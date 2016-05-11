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

using NUnit.Framework;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Test.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    ///  Test class for FeeTypeDetails.  
    /// </summary>
    [TestFixture]
    public class TestFeeTypeDetails
    {
        private FeeTypeDetails feeTypeDetails;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>

        [SetUp]
        public void Initialize()
        {
            feeTypeDetails = new FeeTypeDetails();
        }

        /// <summary>
        /// Dispose FeeTypeDetails.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            feeTypeDetails = null;
        }


        /// <summary>
        /// Test method for Fee Type Id.
        /// </summary>
        [Test]
        public void TestFeeTypeId()
        {
            long inputFeeTypeId = 500;
            feeTypeDetails.Id = inputFeeTypeId;
            long outputFeeTypeId = feeTypeDetails.Id;
            Assert.AreEqual(inputFeeTypeId, outputFeeTypeId);
        }


        /// <summary>
        /// Test method for Fee Type Name.
        /// </summary>
        [Test]
        public void TestFeeTypeName()
        {
            string inputFeeTypeName = "Test Fee Type";
            feeTypeDetails.Name = inputFeeTypeName;
            string outputFeeTypeName = feeTypeDetails.Name;
            Assert.AreEqual(inputFeeTypeName, outputFeeTypeName);
        }


        /// <summary>
        /// Test method for Fee Type Description.
        /// </summary>
        [Test]
        public void TestFeeTypeDescription()
        {
            string inputFeeTypeDesc = "Test Fee Type Description";
            feeTypeDetails.Description = inputFeeTypeDesc;
            string outputFeeTypeDesc = feeTypeDetails.Description;
            Assert.AreEqual(inputFeeTypeDesc, outputFeeTypeDesc);
        }


        /// <summary>
        /// Test method for Fee Type Amount.
        /// </summary>
        [Test]
        public void TestFeeTypeAmount()
        {
            double inputFeeTypeAmonut = 50.89;
            feeTypeDetails.Amount = inputFeeTypeAmonut;
            double outputFeeTypeAmonut = feeTypeDetails.Amount;
            Assert.AreEqual(inputFeeTypeAmonut, outputFeeTypeAmonut);
        }

        /// <summary>
        /// Test method for Fee Type IsAssocaited.
        /// </summary>
        [Test]
        public void TestIsAssocaited()
        {
            bool inputFeeTypeAssocaited = true;
            feeTypeDetails.IsAssociated = inputFeeTypeAssocaited;
            bool outputFeeTypeAssocaited = feeTypeDetails.IsAssociated;
            Assert.AreEqual(inputFeeTypeAssocaited, outputFeeTypeAssocaited);
        }


        /// <summary>
        /// Test case for MediaType recordversion.
        /// </summary>
        [Test]
        public void TestFeeTypeRecordVersion()
        {
            int recordVersionId = 0;
            feeTypeDetails.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, feeTypeDetails.RecordVersionId);
        }

        /// <summary>
        /// Test case for MediaType Equals.
        /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(feeTypeDetails.Equals(feeTypeDetails));
        }

        /// <summary>
        /// Test case for Getting hash code.
        /// </summary>
        [Test]
        public void TestGethashCode()
        {
            Assert.IsInstanceOfType(typeof(int), feeTypeDetails.GetHashCode());
        }

    }
}
