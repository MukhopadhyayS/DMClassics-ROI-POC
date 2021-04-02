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
    /// Test class for RequestDetails 
    /// </summary>
    [TestFixture]
    public class TestPastInvoiceDetails
    {
        private PastInvoiceDetails pastInvoiceDetails;

        [SetUp]
        public void Initialize()
        {
            pastInvoiceDetails = new PastInvoiceDetails();
        }

        [TearDown]
        public void Dispose()
        {
            pastInvoiceDetails = null;
        }

        #region Test Methods        

        /// <summary>
        /// Test case for Invoice Id.
        /// </summary>
        [Test]
        public void TestInvoiceId()
        {
            long inputInvoiceId = 10;
            pastInvoiceDetails.InvoiceId = inputInvoiceId;
            long outputInvoiceId = pastInvoiceDetails.InvoiceId;
            Assert.AreEqual(inputInvoiceId, outputInvoiceId);
        }

        /// <summary>
        /// Test case for created date.
        /// </summary>
        [Test]
        public void TestCreatedDate()
        {
            DateTime inputDate = DateTime.Now;
            pastInvoiceDetails.CreatedDate = inputDate;
            Assert.AreEqual(inputDate, pastInvoiceDetails.CreatedDate);
        }

        /// <summary>
        /// Test case for invoice amount.
        /// </summary>
        [Test]
        public void TestInvoiceAmount()
        {
            double inputInvoiceAmount = 10.50;
            pastInvoiceDetails.Amount = inputInvoiceAmount;
            double outputInvoiceAmount = pastInvoiceDetails.Amount;
            Assert.AreEqual(inputInvoiceAmount, inputInvoiceAmount);
        }

        /// <summary>
        /// Test case for Invoice Selected
        /// </summary>
        [Test]
        public void TestInvoiceSelected()
        {
            bool inputInvoiceSelected = true;
            pastInvoiceDetails.InvoiceSelected = inputInvoiceSelected;
            Assert.AreEqual(inputInvoiceSelected, pastInvoiceDetails.InvoiceSelected);
        }

        #endregion
    }
}

