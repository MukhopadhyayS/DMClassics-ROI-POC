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
using System.Drawing;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    /// Test Class for Delivery Method DTO
    /// </summary>
    [TestFixture]
    public class TestDeliveryMethodDetails
    {

       private DeliveryMethodDetails deliverymethod;

        [SetUp]
        public void Initialize()
        {
            deliverymethod = new DeliveryMethodDetails();
        }

        [TearDown]
        public void Dispose()
        {
            deliverymethod = null;
        }

        /// <summary>
        /// Test case for delivery method id.
        /// </summary>
        [Test]
        public void TestDeliveryMethodId()
        {
            long inputDeliveryMethodId = 1;
            deliverymethod.Id = inputDeliveryMethodId;
            long outputDeliveryMethodId = deliverymethod.Id;
            Assert.AreEqual(inputDeliveryMethodId, outputDeliveryMethodId);
        }

        /// <summary>
        /// Test case for delivery method name
        /// </summary>
        [Test]
        public void TestDeliveryMethodName()
        {
            string inputDeliveryMethodName = "UPS";
            deliverymethod.Name = inputDeliveryMethodName;
            string outputDeliveryMethodName = deliverymethod.Name;
            Assert.AreEqual(inputDeliveryMethodName, outputDeliveryMethodName);
        }

        /// <summary>
        /// Test case for delivery method description.
        /// </summary>
        [Test]
        public void TestDeliveryMethodDescription()
        {
            string inputDeliveryMethodDescription = "UPS Ground";
            deliverymethod.Description = inputDeliveryMethodDescription;
            string outputDeliveryMethodDescription = deliverymethod.Description;
            Assert.AreEqual(inputDeliveryMethodDescription, outputDeliveryMethodDescription);

        }

        /// <summary>
        /// Test case for delivery method url.
        /// </summary>
        [Test]
        public void TestDeliveryMethodUrl()
        {
            string inputDeliveryMethodUrl = "http://www.aps.ups.com";
            deliverymethod.Url = new Uri(inputDeliveryMethodUrl);
            Uri outputDeliveryMethodUrl = deliverymethod.Url;
            Assert.AreEqual(inputDeliveryMethodUrl, outputDeliveryMethodUrl.OriginalString);
        }

        /// <summary>
        /// Test case for delivery method default status
        /// </summary>
        [Test]
        public void TestDeliveryMethodDefault()
        {
            bool inputIsDefault = true;
            deliverymethod.IsDefault = inputIsDefault;
            bool outputIsDefault = deliverymethod.IsDefault;
            Assert.AreEqual(inputIsDefault, outputIsDefault);
        }

        /// <summary>
        /// Test case for delivery method Image.
        /// </summary>
        [Test]
        public void TestDeliveryMethodImage()
        {
            deliverymethod.Image = null;
            Assert.IsNull(deliverymethod.Image);
        }

        /// <summary>
        /// Test case for DeliveryMethod recordversion.
        /// </summary>
        [Test]
        public void TestDeliveryMethodRecordVersion()
        {
            int recordVersionId = 0;
            deliverymethod.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, deliverymethod.RecordVersionId);
        }

        /// <summary>
        /// Test case for Delivery Method Equals.
        /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(deliverymethod.Equals(deliverymethod));
        }

        /// <summary>
        /// Test case for Getting hash code.
        /// </summary>
        [Test]
        public void TestGethashCode()
        {
            Assert.IsInstanceOfType(typeof(int), deliverymethod.GetHashCode());
        }
    }
}
