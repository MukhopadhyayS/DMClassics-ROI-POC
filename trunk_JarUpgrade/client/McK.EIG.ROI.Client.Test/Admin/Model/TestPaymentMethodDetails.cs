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
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    /// Test class for Payment method details.
    /// </summary>
    [TestFixture]
    public class TestPaymentMethodDetails
    {
        // Holds the model object.
        private PaymentMethodDetails paymentMethod;

        /// <summary>
        /// Create a new PaymentMethodsDetails instance
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            paymentMethod = new PaymentMethodDetails();
        }

        /// <summary>
        /// Dispose the object created 
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            paymentMethod = null;
        }

        /// <summary>
        /// Test case for PaymentMethod Id
        /// </summary>
        [Test]
        public void TestPaymentMethodId()
        { 
            long inputPaymentMethodId = 1000;
            paymentMethod.Id = inputPaymentMethodId;
            long outputPaymentMethodId = paymentMethod.Id;
            Assert.AreEqual(inputPaymentMethodId, outputPaymentMethodId);
        }

        /// <summary>
        /// Test case for PaymentMethod Name.
        /// </summary>
        [Test]
        public void TestPaymentMethodName()
        {
            string inputPaymentMethodName = "SampleName";
            paymentMethod.Name = inputPaymentMethodName;
            string outputPaymentMethodName = paymentMethod.Name;
            Assert.AreEqual(inputPaymentMethodName, outputPaymentMethodName);
        }
       
        /// <summary>
        /// Test case for PaymentMethod Desc.
        /// </summary>
        [Test]
        public void TestPaymentMethodDesc()
        {
            string inputPaymentMethodDesc = "SampleDescription";
            paymentMethod.Description = inputPaymentMethodDesc;
            string outputPaymentMethodDesc = paymentMethod.Description;
            Assert.AreEqual(inputPaymentMethodDesc, outputPaymentMethodDesc);
            paymentMethod.Description = null;
            Assert.AreEqual(string.Empty, paymentMethod.Description);
        }
       

        /// <summary>
        /// Test case for PaymentMethod IsDisplay.
        /// </summary>
        [Test]
        public void TestPaymentMethodIsDisplay()
        {
            bool inputIsDisplay = true;
            paymentMethod.IsDisplay = inputIsDisplay;
            bool outputIsDisplay = paymentMethod.IsDisplay;
            Assert.AreEqual(inputIsDisplay, outputIsDisplay);
        }

        /// <summary>
        /// Test case for payment method recordversion.
        /// </summary>
        [Test]
        public void TestPaymentMethodRecordVersion()
        {
            int recordVersionId = 0;
            paymentMethod.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, paymentMethod.RecordVersionId);
        }

        /// <summary>
        /// Test case for payment method Equals.
        /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(paymentMethod.Equals(paymentMethod));
        }

        /// <summary>
        /// Test case for Getting hash code.
        /// </summary>
        [Test]
        public void TestGethashCode()
        {
            Assert.IsInstanceOfType(typeof(int), paymentMethod.GetHashCode());
        }        
    }
}
