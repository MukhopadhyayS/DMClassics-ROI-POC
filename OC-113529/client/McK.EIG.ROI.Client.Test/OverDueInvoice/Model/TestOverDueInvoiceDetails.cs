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
    /// Test class for TestOverDueInvoiceDetails
    /// </summary>
    [TestFixture]
    public class TestOverDueInvoiceDetails
    {
        private OverDueInvoiceDetails overdueInvoiceDetails;
        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            overdueInvoiceDetails = new OverDueInvoiceDetails();
        }

        /// <summary>
        /// Dispose OverDue Invoice Details.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            overdueInvoiceDetails = null;
        }

        /// <summary>
        /// Test method for overdueInvoice ID.
        /// </summary>
        [Test]
        public void TestOverDueInvoiceId()
        {
            long inputId = 2;
            overdueInvoiceDetails.Id = inputId;
            long outputId = overdueInvoiceDetails.Id;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test method for Invoice Selected.
        /// </summary>
        [Test]
        public void TestInvoiceSelected()
        {
            bool inputInvoiceSelected = true;
            overdueInvoiceDetails.InvoiceSelected = inputInvoiceSelected;
            bool outputInvoiceSelected = overdueInvoiceDetails.InvoiceSelected;
            Assert.AreEqual(inputInvoiceSelected, outputInvoiceSelected);
        }

        /// <summary>
        /// Test method for Billing Location.
        /// </summary>
        [Test]
        public void TestBillingLocation()
        {
            string inputBillingLocation = "AD";
            overdueInvoiceDetails.BillingLocation = inputBillingLocation;
            string outputBillingLocation = overdueInvoiceDetails.BillingLocation;
            Assert.AreEqual(inputBillingLocation, outputBillingLocation);
        }

        /// <summary>
        /// Test method for Invoice Number.
        /// </summary>
        [Test]
        public void TestInvoiceNumber()
        {
            long inputInvoiceNumber = 3;
            overdueInvoiceDetails.InvoiceNumber = inputInvoiceNumber;
            long outputInvoiceNumber = overdueInvoiceDetails.InvoiceNumber;
            Assert.AreEqual(inputInvoiceNumber, outputInvoiceNumber);
        }

        /// <summary>
        /// Test method for Request ID.
        /// </summary>
        [Test]
        public void TestRequestId()
        {
            long inputRequestId = 3;
            overdueInvoiceDetails.RequestId = inputRequestId;
            long outputRequestId = overdueInvoiceDetails.RequestId;
            Assert.AreEqual(inputRequestId, outputRequestId);
        }

        /// <summary>
        /// Test method for overdueamount.
        /// </summary>
        [Test]
        public void TestOverDueAmount()
        {
            double inputOverDueAmount = 3;
            overdueInvoiceDetails.OverDueAmount = inputOverDueAmount;
            double outputOverDueAmount = overdueInvoiceDetails.OverDueAmount;
            Assert.AreEqual(inputOverDueAmount, outputOverDueAmount);
        }

        /// <summary>
        /// Test method for overduedays.
        /// </summary>
        [Test]
        public void TestOverDueDays()
        {
            long inputOverDueDays = 3;
            overdueInvoiceDetails.OverDueDays = inputOverDueDays;
            long outputOverDueDays = overdueInvoiceDetails.OverDueDays;
            Assert.AreEqual(inputOverDueDays, outputOverDueDays);
        }

        /// <summary>
        /// Test method for requestor Id.
        /// </summary>
        [Test]
        public void TestRequestorID()
        {
            long inputRequestorId = 10;
            overdueInvoiceDetails.RequestorId = inputRequestorId;
            long outputRequestorId = overdueInvoiceDetails.RequestorId;
            Assert.AreEqual(inputRequestorId, outputRequestorId);
        }

        /// <summary>
        /// Test method for requestor type.
        /// </summary>
        [Test]
        public void TestRequestorType()
        {
            string inputRequestorType = "Insurance";
            overdueInvoiceDetails.RequestorType = inputRequestorType;
            string outputRequestorType = overdueInvoiceDetails.RequestorType;
            Assert.AreEqual(inputRequestorType, outputRequestorType);
        }

        /// <summary>
        /// Test method for requestor name.
        /// </summary>
        [Test]
        public void TestRequestorName()
        {
            string inputRequestorName = "Test Requestor";
            overdueInvoiceDetails.RequestorName = inputRequestorName;
            string outputRequestorName = overdueInvoiceDetails.RequestorName;
            Assert.AreEqual(inputRequestorName, outputRequestorName);
        }

        /// <summary>
        /// Test method for phone number.
        /// </summary>
        [Test]
        public void TestPhoneNumber()
        {
            string inputPhoneNumber = "123-345";
            overdueInvoiceDetails.PhoneNumber = inputPhoneNumber;
            string outputPhoneNumber = overdueInvoiceDetails.PhoneNumber;
            Assert.AreEqual(inputPhoneNumber, outputPhoneNumber);
        }

        /// <summary>
        /// Test method for Invoice Age.
        /// </summary>
        [Test]
        public void TestInvoiceAge()
        {
            long inputInvoiceAge = 1;
            overdueInvoiceDetails.InvoiceAge = inputInvoiceAge;
            long outputInvoiceAge = overdueInvoiceDetails.InvoiceAge;
            Assert.AreEqual(inputInvoiceAge, outputInvoiceAge);
        }

        /// <summary>
        /// Test method for Normalize.
        /// </summary>
        [Test]
        public void TestNormalize()
        {
            OverDueInvoiceDetails testoverdueInvoiceDetails = new OverDueInvoiceDetails();
            testoverdueInvoiceDetails.BillingLocation = "AD";
            testoverdueInvoiceDetails.OverDueAmount = 10;
            testoverdueInvoiceDetails.RequestorName = "Test";
            testoverdueInvoiceDetails.PhoneNumber = "123-456-789";
            testoverdueInvoiceDetails.Normalize();
        }
    }
}
