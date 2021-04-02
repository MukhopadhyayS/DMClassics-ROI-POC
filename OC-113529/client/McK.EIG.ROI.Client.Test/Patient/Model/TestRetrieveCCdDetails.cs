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


//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for RetrieveCCdDetails
    /// </summary>
    [TestFixture]
    public class TestRetrieveCCdDetails
    {
        private RetrieveCCDDetails retrieveCCDDetails;
        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            retrieveCCDDetails = new RetrieveCCDDetails("LocalFile", "CCD");
        }

        /// <summary>
        /// Dispose PatientDetail.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            retrieveCCDDetails = null;
        }

        /// <summary>
        /// Test method for RetrieveCCDKey
        /// </summary>
        [Test]
        public void TestRetrieveCCDKey()
        {
            string inputRetrieveCCDKey = "CCD";
            retrieveCCDDetails.retrieveCCDKey = inputRetrieveCCDKey;
            string outputRetrieveCCDKey = retrieveCCDDetails.retrieveCCDKey;
            Assert.AreEqual(inputRetrieveCCDKey, outputRetrieveCCDKey);
        }

        /// <summary>
        /// Test method for RetrieveCCDValue
        /// </summary>
        [Test]
        public void TestRetrieveCCDValue()
        {
            string inputRetriveCCDValue = "CCD";
            retrieveCCDDetails.retriveCCDValue = inputRetriveCCDValue;
            string outputRetriveCCDValue = retrieveCCDDetails.retriveCCDValue;
            Assert.AreEqual(inputRetriveCCDValue, outputRetriveCCDValue);
        }

        /// <summary>
        /// Test method for RetrieveCCDKey
        /// </summary>
        [Test]
        public void TestClone()
        {
            retrieveCCDDetails = retrieveCCDDetails.Clone();
            Assert.IsNotNull(retrieveCCDDetails);
        }
    }
}
