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
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for ReleaseDetails 
    /// </summary>
    [TestFixture]
    public class TestPreBillInvoiceDetails
    {
        #region Fields

        private PreBillInvoiceDetails prebillInvoiceDetails;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            prebillInvoiceDetails = new PreBillInvoiceDetails();
        }

        [TearDown]
        public void Dispose()
        {
            prebillInvoiceDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the Invoice ID.
        /// </summary>
        [Test]
        public void TestId()
        {
            long input = 12345;
            prebillInvoiceDetails.Id = input;
            Assert.AreEqual(input, prebillInvoiceDetails.Id);
        }       
       
        /// <summary>
        /// Test the Letter template ID.
        /// </summary>
        [Test]
        public void TestLetterTemplateId()
        {
            long input = 12345;
            prebillInvoiceDetails.LetterTemplateId = input;
            Assert.AreEqual(input, prebillInvoiceDetails.LetterTemplateId);            
        }

        /// <summary>
        /// Test the Invoice Type
        /// </summary>
        [Test]
        public void TestInvoiceType()
        {
            string input = "Invoice";
            prebillInvoiceDetails.InvoiceType = input;
            Assert.AreEqual(input, prebillInvoiceDetails.InvoiceType);
        }

        /// <summary>
        /// Test the Request Status
        /// </summary>
        [Test]
        public void TestRequestStatus()
        {
            string input = "Logged";
            prebillInvoiceDetails.RequestStatus = input;
            Assert.AreEqual(input, prebillInvoiceDetails.RequestStatus);
        }

        /// <summary>
        /// Test the request status reasons
        /// </summary>
        [Test]
        public void TestStatusReasons()
        {
            string input = "Reason1" + ROIConstants.StatusReasonDelimiter + "Reason2";
            prebillInvoiceDetails.StatusReasons = input;
            Assert.AreEqual(input, prebillInvoiceDetails.StatusReasons);
        }

        /// <summary>
        /// Test balance due
        /// </summary>
        [Test]
        public void TestBalanceDue()
        {
            string input = "123.45";
            prebillInvoiceDetails.BalanceDue = input;
            Assert.AreEqual(input, prebillInvoiceDetails.BalanceDue);
        }

        /// <summary>
        /// Test total pages
        /// </summary>
        [Test]
        public void TestTotalPages()
        {
            int input = 123;
            prebillInvoiceDetails.TotalPagesPerRelease = input;
            Assert.AreEqual(input, prebillInvoiceDetails.TotalPagesPerRelease);
        }

        /// <summary>
        /// Test the notes
        /// </summary>
        [Test]
        public void TestNotes()
        {
            Assert.IsNotNull(prebillInvoiceDetails.Notes);
        }

        /// <summary>
        /// Test the release details
        /// </summary>
        [Test]
        public void TestReleaseDetails()
        {
            Assert.IsNotNull(prebillInvoiceDetails.Release);
            ReleaseDetails releaseDetails = new ReleaseDetails();
            prebillInvoiceDetails.Release = releaseDetails;
            Assert.IsNotNull(prebillInvoiceDetails.Release);
        }

        /// <summary>
        /// Test the requestor details
        /// </summary>
        [Test]
        public void TestRequestorDetails()
        {
            Assert.IsNotNull(prebillInvoiceDetails.Requestor);
            RequestorDetails requestorDetails = new RequestorDetails();
            prebillInvoiceDetails.Requestor = requestorDetails;
            Assert.IsNotNull(prebillInvoiceDetails.Requestor);
        }

        /// <summary>
        /// Test the releaseDate
        /// </summary>
        [Test]
        public void TestReleaseDate()
        {
            prebillInvoiceDetails.ReleaseDate = DateTime.Today;
            Assert.AreEqual(DateTime.Today, prebillInvoiceDetails.ReleaseDate);
        }

        /// <summary>
        /// Test the Invoice Due Date
        /// </summary>
        [Test]
        public void TestInvoiceDueDate()
        {
            Nullable<DateTime> invoiceDueDate = DateTime.Now;
            prebillInvoiceDetails.InvoiceDueDate = invoiceDueDate;
            Assert.AreEqual(invoiceDueDate, prebillInvoiceDetails.InvoiceDueDate);
        }

        /// <summary>
        /// Test the status of overwrite invoice due date
        /// </summary>
        [Test]
        public void TestOverWriteInvoiceDueDate()
        {
            bool input = true;
            prebillInvoiceDetails.OverwriteInvoiceDue = input;
            Assert.AreEqual(input, prebillInvoiceDetails.OverwriteInvoiceDue);
        }

        #endregion
    }
}
