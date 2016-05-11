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
    ///  Test class for CountryCodeDetails
    /// </summary>
    [TestFixture]
    class TestCountryCodeDetails
    {
        private CountryCodeDetails countryCodeDetails;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            countryCodeDetails = new CountryCodeDetails();
        }

        /// <summary>
        /// Dispose AddressDetail.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            countryCodeDetails = null;
        }

        /// <summary>
        /// Test method for CountryCode.
        /// </summary>
        [Test]
        public void TestCountryCode()
        {
            string inputCountryCode = "IND";
            countryCodeDetails.CountryCode = inputCountryCode;
            string outputCountryCode = countryCodeDetails.CountryCode;
            Assert.AreEqual(inputCountryCode, outputCountryCode);
        }

        /// <summary>
        /// Test method for CountryName.
        /// </summary>
        [Test]
        public void TestCountryName()
        {
            string inputCountryName = "IND";
            countryCodeDetails.CountryName = inputCountryName;
            string outputCountryName = countryCodeDetails.CountryName;
            Assert.AreEqual(inputCountryName, outputCountryName);
        }

        /// <summary>
        /// Test method for DefaultCountry.
        /// </summary>
        [Test]
        public void TestDefaultCountry()
        {
            bool inputDefaultCountry = true;
            countryCodeDetails.DefaultCountry = inputDefaultCountry;
            bool outputDefaultCountry = countryCodeDetails.DefaultCountry;
            Assert.AreEqual(inputDefaultCountry, outputDefaultCountry);
        }

        /// <summary>
        /// Test method for CountrySeq.
        /// </summary>
        [Test]
        public void TestCountrySeq()
        {
            long inputCountrySeq = 12345;
            countryCodeDetails.CountrySeq = inputCountrySeq;
            long outputCountrySeq = countryCodeDetails.CountrySeq;
            Assert.AreEqual(inputCountrySeq, outputCountrySeq);
        }

    }
}
