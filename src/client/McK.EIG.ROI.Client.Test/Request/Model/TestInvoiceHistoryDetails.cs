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
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test Class for Request Version Details.
    /// </summary>
    [TestFixture]
    public class TestInvoiceHistoryDetails
    {
        private InvoiceHistoryDetails invoiceHistoryDetails;

        [SetUp]
        public void Initialize()
        {
            invoiceHistoryDetails = new InvoiceHistoryDetails();
        }

        [TearDown]
        public void Dispose()
        {
            invoiceHistoryDetails = null;
        }

        #region TestMethods

        /// <summary>
        /// Test Case for Invoice Type Id
        /// </summary>
        [Test]
        public void TestId()
        {
            long input = 12345;
            invoiceHistoryDetails.Id = input;
            Assert.AreEqual(input, invoiceHistoryDetails.Id);
        }

        /// <summary>
        /// Test Case for Invoice Type
        /// </summary>
        [Test]
        public void TestInvoiceType()
        {
            string input = "InvoiceType";
            invoiceHistoryDetails.InvoiceType = input;
            Assert.AreEqual(input, invoiceHistoryDetails.InvoiceType);
        }

        /// <summary>
        /// Test Case for Invoice Due Date
        /// </summary>
        [Test]
        public void TestInvoiceDueDate()
        {
            DateTime inputDate = DateTime.Now;
            invoiceHistoryDetails.InvoiceDueDate = inputDate;
            Assert.AreEqual(inputDate, invoiceHistoryDetails.InvoiceDueDate);
        }

        #endregion
    }
}