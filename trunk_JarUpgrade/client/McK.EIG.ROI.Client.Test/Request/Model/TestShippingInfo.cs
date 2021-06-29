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
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for ShippingInfo 
    /// </summary>
    [TestFixture]
    public class TestShippingInfo
    {
        #region Fields

        private ShippingInfo shippingInfo;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            shippingInfo = new ShippingInfo();
        }

        [TearDown]
        public void Dispose()
        {
            shippingInfo = null;
        }

        #endregion        

        /// <summary>
        /// Test the Shipping Address.
        /// </summary>
        [Test]
        public void TestShippingAddress()
        {
            AddressDetails addressDeatils = new AddressDetails();
            addressDeatils.Address1   = "Address1";
            addressDeatils.City       = "City1";
            addressDeatils.State      = "Sate1";
            addressDeatils.PostalCode = "45612";            
            shippingInfo.ShippingAddress = addressDeatils;
            Assert.AreEqual(addressDeatils.Address1, shippingInfo.ShippingAddress.Address1);
            Assert.AreEqual(addressDeatils.City, shippingInfo.ShippingAddress.City);
            Assert.AreEqual(addressDeatils.State, shippingInfo.ShippingAddress.State);
            Assert.AreEqual(addressDeatils.PostalCode, shippingInfo.ShippingAddress.PostalCode);            
        }

        /// <summary>
        /// Test the Shipping Charge.
        /// </summary>
        [Test]
        public void TestShippingCharge()
        {
            double inputCharge = 12.50;
            shippingInfo.ShippingCharge = inputCharge;            
            Assert.AreEqual(inputCharge, shippingInfo.ShippingCharge);
        }

        /// <summary>
        /// Test the Shipping Method.
        /// </summary>
        [Test]
        public void TestshippingMethod()
        {
            string input = "USPS";
            shippingInfo.ShippingMethod = input;
            Assert.AreEqual(input, shippingInfo.ShippingMethod);
        }

        /// <summary>
        /// Test the Shipping Method Id.
        /// </summary>
        [Test]
        public void TestshippingMethodId()
        {
            long input = 10;
            shippingInfo.ShippingMethodId = input;
            Assert.AreEqual(input, shippingInfo.ShippingMethodId);
        }

        /// <summary>
        /// Test the Shipping URL.
        /// </summary>
        [Test]
        public void TestShippingUrl()
        {
            string input = "http://postcalc.USPS.gov/";
            shippingInfo.ShippingWebAddress = input;
            Assert.AreEqual(input, shippingInfo.ShippingWebAddress);
        }

        /// <summary>
        /// Test the Shipping Weight.
        /// </summary>
        [Test]
        public void TestShippingWeight()
        {
            decimal input = 23.20M;
            shippingInfo.ShippingWeight = input;
            Assert.AreEqual(input, shippingInfo.ShippingWeight);
        }

        /// <summary>
        /// Test the Tracking Number.
        /// </summary>
        [Test]
        public void TestTrackingNumber()
        {
            string input = "EZ1237789";
            shippingInfo.TrackingNumber = input;
            Assert.AreEqual(input, shippingInfo.TrackingNumber);
        }

        /// <summary>
        /// Test the Address Type.
        /// </summary>
        [Test]
        public void TestaddressType()
        {
            RequestorAddressType input = RequestorAddressType.Custom;
            shippingInfo.AddressType = input;
            Assert.AreEqual(input, shippingInfo.AddressType);
        }

        /// <summary>
        /// Test the Output Mehtod.
        /// </summary>
        [Test]
        public void TestOutputMethod()
        {
            OutputMethod input = OutputMethod.SaveAsFile;
            shippingInfo.OutputMethod = input;
            Assert.AreEqual(input, shippingInfo.OutputMethod);
        }

        /// <summary>
        /// Test for Will Release shipped.
        /// </summary>
        [Test]
        public void TestWillReleaseShipped()
        {
            bool input = true;
            shippingInfo.WillReleaseShipped= input;
            Assert.IsTrue(shippingInfo.WillReleaseShipped);
        }

        #endregion
    }
}
