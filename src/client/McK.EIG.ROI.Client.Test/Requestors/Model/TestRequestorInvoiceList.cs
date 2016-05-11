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
using System.Collections.ObjectModel;
using System.Collections.Generic;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{
     /// <summary>
    /// Test class for Requestor Invoice List
    /// </summary>
    [TestFixture]
    public class  TestRequestorInvoiceList
    {
        //Holds the object of model class.
        private RequestorInvoiceList requestorInvoiceList;

        [SetUp]
        public void Initialize()
        {
            requestorInvoiceList = new RequestorInvoiceList();
        }

        [TearDown]
        public void Dispose()
        {
            requestorInvoiceList = null;
        }

        /// <summary>
        /// Test case for Requestor Id.
        /// </summary>
        [Test]
        public void TestRequestorId()
        {
            long inputRequestorId = 100;
            requestorInvoiceList.RequestorId = inputRequestorId;
            long outputRequestorId = requestorInvoiceList.RequestorId;
            Assert.AreEqual(inputRequestorId, outputRequestorId);
        }


        /// <summary>
        /// Test case for Payment Id.
        /// </summary>
        [Test]
        public void TestPaymentId()
        {
            long inputPaymentId = 100;
            requestorInvoiceList.PaymentId = inputPaymentId;
            long outputPaymentId = requestorInvoiceList.PaymentId;
            Assert.AreEqual(inputPaymentId, outputPaymentId);
        }

        /// <summary>
        /// Test case for Payment Amount.
        /// </summary>
        [Test]
        public void TestPaymentAmount()
        {
            double inputPaymentAmount = 100.00;
            requestorInvoiceList.PaymentAmount = inputPaymentAmount;
            double outputPaymentAmount = requestorInvoiceList.PaymentAmount;
            Assert.AreEqual(inputPaymentAmount, outputPaymentAmount);
        }

        /// <summary>
        /// Test case for UnApplied Amount.
        /// </summary>
        [Test]
        public void TestUnAppliedAmount()
        {
            double inputUnAppliedAmount = 100.00;
            requestorInvoiceList.UnAppliedAmount = inputUnAppliedAmount;
            double outputUnAppliedAmount = requestorInvoiceList.UnAppliedAmount;
            Assert.AreEqual(inputUnAppliedAmount, outputUnAppliedAmount);
        }

        /// <summary>
        /// Test case for Payment Mode.
        /// </summary>
        [Test]
        public void TestPaymentMode()
        {
            string inputPaymentMode = "By check";
            requestorInvoiceList.PaymentMode = inputPaymentMode;
            string outputPaymentMode = requestorInvoiceList.PaymentMode;
            Assert.AreEqual(inputPaymentMode, outputPaymentMode);
        }

        /// <summary>
        /// Test case for Description.
        /// </summary>
        [Test]
        public void TestDescription()
        {
            string inputDescription = "Payment Made";
            requestorInvoiceList.Description = inputDescription;
            string outputDescription = requestorInvoiceList.Description;
            Assert.AreEqual(inputDescription, outputDescription);
        }

        /// <summary>
        /// Test case for Payment Date.
        /// </summary>
        [Test]
        public void TestPaymentDate()
        {
            DateTime inputPaymentDate = DateTime.Now;
            requestorInvoiceList.PaymentDate = inputPaymentDate;
            DateTime outputPaymentDate = (DateTime)requestorInvoiceList.PaymentDate;
            Assert.AreEqual(inputPaymentDate, outputPaymentDate);
        }


        /// <summary>
        /// Test Method for Requestor Invoices.
        /// </summary>
        [Test]
        public void TestRequestorInvoices()
        {
            Assert.IsNotNull(requestorInvoiceList.RequestorInvoices);
        }

        /// <summary>
        /// Test case for Requestor Name.
        /// </summary>
        [Test]
        public void TestRequestorName()
        {
            string inputRequestorName = "Test Requestor";
            requestorInvoiceList.RequestorName = inputRequestorName;
            string outputRequestorName = requestorInvoiceList.RequestorName;
            Assert.AreEqual(inputRequestorName, outputRequestorName);
        }

        /// <summary>
        /// Test case for Requestor Type.
        /// </summary>
        [Test]
        public void TestRequestorType()
        {
            string inputRequestorType = "Insurance";
            requestorInvoiceList.RequestorType = inputRequestorType;
            string outputRequestorType = requestorInvoiceList.RequestorType;
            Assert.AreEqual(inputRequestorType, outputRequestorType);
        }

    }
}
