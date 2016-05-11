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

using McK.EIG.ROI.Client.OverDueInvoice.Model;

namespace McK.EIG.ROI.Client.Test.OverDueInvoice.Model
{
    /// <summary>
    /// Test class for Requestor Invoices Details
    /// </summary>
    [TestFixture]
    public class TestRequestorInvoicesDetails
    {
        private RequestorInvoicesDetails requestorInvoicesDetails;
        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            requestorInvoicesDetails = new RequestorInvoicesDetails();
        }

        /// <summary>
        /// Dispose OverDue Invoice Details.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            requestorInvoicesDetails = null;
        }

        /// <summary>
        /// Test method for Invoice ID.
        /// </summary>
        [Test]
        public void TestInvoiceId()
        {
            requestorInvoicesDetails.InvoiceIds.Add(10);
            IList<long> outputInvoiceID = requestorInvoicesDetails.InvoiceIds;
            Assert.IsNotNull(outputInvoiceID);
        }

        /// <summary>
        /// Test method for Requestor Id.
        /// </summary>
        [Test]
        public void TestRequestorId()
        {
            long inputRequestorId = 3;
            requestorInvoicesDetails.RequestorId = inputRequestorId;
            long outputRequestorId = requestorInvoicesDetails.RequestorId;
            Assert.AreEqual(inputRequestorId, outputRequestorId);
        }

        /// <summary>
        /// Test method for Requestor Name.
        /// </summary>
        [Test]
        public void TestRequestorName()
        {
            string inputRequestorName = "Test Requestor";
            requestorInvoicesDetails.RequestorName = inputRequestorName;
            string outputRequestorName = requestorInvoicesDetails.RequestorName;
            Assert.AreEqual(inputRequestorName, outputRequestorName);
        }

        /// <summary>
        /// Test method for Requestor Type.
        /// </summary>
        [Test]
        public void TestRequestorType()
        {
            string inputRequestorType = "Insurance";
            requestorInvoicesDetails.RequestorType = inputRequestorType;
            string outputRequestorType = requestorInvoicesDetails.RequestorType;
            Assert.AreEqual(inputRequestorType, outputRequestorType);
        }

        /// <summary>
        /// Test method for Request Id.
        /// </summary>
        [Test]
        public void TestRequestId()
        {
            long inputRequestId = 6;
            requestorInvoicesDetails.RequestId = inputRequestId;
            long outputRequestId = requestorInvoicesDetails.RequestId;
            Assert.AreEqual(inputRequestId, outputRequestId);
        }

        /// <summary>
        /// Test method for Greatest Invoice Age.
        /// </summary>
        [Test]
        public void TestGreatestInvoiceAge()
        {
            long inputGreatestInvoiceAge = 10;
            requestorInvoicesDetails.GreatestInvoiceAge = inputGreatestInvoiceAge;
            long outputGreatestInvoiceAge = requestorInvoicesDetails.GreatestInvoiceAge;
            Assert.AreEqual(inputGreatestInvoiceAge, outputGreatestInvoiceAge);
        }

    }
}
