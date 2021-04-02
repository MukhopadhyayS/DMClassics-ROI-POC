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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Base.Model
{
    /// <summary>
    ///  Test class for AddressDetails
    /// </summary>
    [TestFixture]
    public class TestAddressDetails
    {
        private AddressDetails addressDetail;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            addressDetail = new AddressDetails();
        }

        /// <summary>
        /// Dispose AddressDetail.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            addressDetail = null;
        }

        /// <summary>
        /// Test method for AddressDetail ID.
        /// </summary>
        [Test]
        public void TestAddressDetailID()
        {
            long inputId = 121;
            addressDetail.Id = inputId;
            long outputId = addressDetail.Id;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test method for AddressDetail Address1.
        /// </summary>
        [Test]
        public void TestAddressDetailsAddress1()
        {
            string inputAddress = "No 1445, 3rd Street";
            addressDetail.Address1 = inputAddress;
            string outputAddress = addressDetail.Address1;
            Assert.AreEqual(inputAddress,outputAddress);
        }

        /// <summary>
        /// Test method for AddressDetail Address2.
        /// </summary>
        [Test]
        public void TestAddressDetailsAddress2()
        {
            string inputAddress = "No 1445, 3rd Street";
            addressDetail.Address2 = inputAddress;
            string outputAddress = addressDetail.Address2;
            Assert.AreEqual(inputAddress, outputAddress);
        }

        /// <summary>
        /// Test method for AddressDetail Address3.
        /// </summary>
        [Test]
        public void TestAddressDetailsAddress3()
        {
            string inputAddress = "No 1445, 3rd Street";
            addressDetail.Address3 = inputAddress;
            string outputAddress = addressDetail.Address3;
            Assert.AreEqual(inputAddress, outputAddress);
        }

        /// <summary>
        /// Test method for AddressDetail City.
        /// </summary>
        [Test]
        public void TestAddressDetailCity()
        {
            string inputCity = "New York";
            addressDetail.City = inputCity;
            string outputCity = addressDetail.City;
            Assert.AreEqual(inputCity, outputCity);
        }

        /// <summary>
        /// Test method for AddressDetail State.
        /// </summary>
        [Test]
        public void TestAddressDetailState()
        {
            string inputState= "New York";
            addressDetail.State = inputState;
            string outputState = addressDetail.State;
            Assert.AreEqual(inputState, outputState);
        }

        /// <summary>
        /// Test method for AddressDetail Postal Code.
        /// </summary>
        [Test]
        public void TestAddressDetailPostalCode()
        {
            string inputPostalCode = "7892";
            addressDetail.PostalCode = inputPostalCode;
            string outputPostalCode = addressDetail.PostalCode;
            Assert.AreEqual(inputPostalCode, outputPostalCode);
        }

        /// <summary>
        /// Test method for AddressDetail Email.
        /// </summary>
        [Test]
        public void TestAddressDetailEmail()
        {
            string inputEmail = "test@test.com";
            addressDetail.Email = inputEmail;
            string outputEmail = addressDetail.Email;
            Assert.AreEqual(inputEmail, outputEmail);
        }

        /// <summary>
        /// Test Case for Address Detail in the form of Comma seperaters.
        /// </summary>
        [Test]
        public void TestAddressDetailToString()
        {
            AddressDetails addressDetails = new AddressDetails();
            addressDetail.Address1 = "No 1445, 3rd Street";
            addressDetail.Address2 = "No 5, 3rd Street";
            addressDetail.Address3 = "No 5, 3rd Street";
            addressDetail.City = "Chennai";
            addressDetail.State = "TN";
            addressDetail.PostalCode = "60510";
            addressDetail.Email = "test@test.com";
            addressDetail.CountryCode = "INDIA";
            addressDetail.CountryName = "INDIA";
            string inputAddress = addressDetail.ToString();
            string outputAddress = inputAddress;
            Assert.AreEqual(inputAddress, outputAddress);
        }
    }
}
