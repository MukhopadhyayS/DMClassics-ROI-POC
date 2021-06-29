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

using NUnit.Framework;

using McK.EIG.ROI.Client.OverDueInvoice.Model;
namespace McK.EIG.ROI.Client.Test.OverDueInvoice.Model
{
    /// <summary>
    /// Test class for TestOverDueInvoiceSearchCriteria
    /// </summary>
    [TestFixture]
    public class TestOverDueInvoiceSearchCriteria
    {
        private OverDueInvoiceSearchCriteria overDueInvoiceSearchCriteria;
        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            overDueInvoiceSearchCriteria = new OverDueInvoiceSearchCriteria();
        }

        /// <summary>
        /// Dispose OverDue Invoice Search Criteria.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            overDueInvoiceSearchCriteria = null;
        }

        /// <summary>
        /// Test method for Facility Code.
        /// </summary>
        [Test]
        public void TestFaclilityCode()
        {
            overDueInvoiceSearchCriteria.FacilityCode = new List<string>();
            overDueInvoiceSearchCriteria.FacilityCode.Add("AD");
            Assert.IsNotNull(overDueInvoiceSearchCriteria.FacilityCode);
        }

        /// <summary>
        /// Test method for Requestor Type.
        /// </summary>
        [Test]
        public void TestRequestorType()
        {
            overDueInvoiceSearchCriteria.RequestorType = new List<long>();
            overDueInvoiceSearchCriteria.RequestorType.Add(2);
            Assert.IsNotNull(overDueInvoiceSearchCriteria.RequestorType);
        }

        /// <summary>
        /// Test method for Requestor Type Name.
        /// </summary>
        [Test]
        public void TestRequestorTypeName()
        {
            overDueInvoiceSearchCriteria.RequestorTypeName = new List<string>();
            overDueInvoiceSearchCriteria.RequestorTypeName.Add("Insurance");
            Assert.IsNotNull(overDueInvoiceSearchCriteria.RequestorTypeName);
        }

        /// <summary>
        /// Test method for Requestor Name.
        /// </summary>
        [Test]
        public void TestRequestorName()
        {
            string inputRequestorName = "Test Requestor";
            overDueInvoiceSearchCriteria.RequestorName = inputRequestorName;
            string outputRequestorName = overDueInvoiceSearchCriteria.RequestorName;
            Assert.AreEqual(inputRequestorName, outputRequestorName);
        }

        /// <summary>
        /// Test method for From date.
        /// </summary>
        [Test]
        public void TestFrom()
        {
            int inputFrom = 1;
            overDueInvoiceSearchCriteria.From = inputFrom;
            int outputFrom = overDueInvoiceSearchCriteria.From;
            Assert.AreEqual(inputFrom, outputFrom);
        }

        /// <summary>
        /// Test method for To date.
        /// </summary>
        [Test]
        public void TestTo()
        {
            int inputTo = 4;
            overDueInvoiceSearchCriteria.To = inputTo;
            int outputTo = overDueInvoiceSearchCriteria.To;
            Assert.AreEqual(inputTo, outputTo);
        }

        /// <summary>
        /// Test method for Max Record.
        /// </summary>
        [Test]
        public void TestMaxRecord()
        {
            int inputMaxRecord = 10;
            overDueInvoiceSearchCriteria.MaxRecord = inputMaxRecord;
            int outputMaxRecord = overDueInvoiceSearchCriteria.MaxRecord;
            Assert.AreEqual(inputMaxRecord, outputMaxRecord);
        }

        /// <summary>
        /// Test method for Method Normalize.
        /// </summary>
        [Test]
        public void TestNormalize()
        {
            OverDueInvoiceSearchCriteria testOverDueInvoiceSearchCriteria = new OverDueInvoiceSearchCriteria();
            testOverDueInvoiceSearchCriteria.RequestorName = "Test";
            testOverDueInvoiceSearchCriteria.Normalize();
        }
    }
}
