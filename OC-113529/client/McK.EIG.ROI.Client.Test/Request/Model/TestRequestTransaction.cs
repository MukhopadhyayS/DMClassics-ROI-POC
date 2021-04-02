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
using McK.EIG.ROI.Client.Base.Model;
using System.ComponentModel;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for RequestTransaction 
    /// </summary>
    [TestFixture]
    public class TestRequestTransaction
    {
        #region Fields

        private RequestTransaction requestTransaction;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            requestTransaction = new RequestTransaction();
        }

        [TearDown]
        public void Dispose()
        {
            requestTransaction = null;
        }

        #endregion

        /// <summary>
        /// Test the Amount
        /// </summary>
        [Test]
        public void TestAmount()
        {
            double input = 2;
            requestTransaction.Amount = input;
            Assert.AreEqual(input, requestTransaction.Amount);
            Assert.IsNotNull(requestTransaction.FormattedAmount);
        }

        /// <summary>
        /// Test the Reason Name
        /// </summary>
        [Test]
        public void TestReasonName()
        {
            string input = "Tax";
            requestTransaction.ReasonName = input;
            Assert.AreEqual(input, requestTransaction.ReasonName);
        }

        /// <summary>
        /// Test the Description
        /// </summary>
        [Test]
        public void TestDescription()
        {
            string input = "Certification Fee";
            requestTransaction.Description= input;
            Assert.AreEqual(input, requestTransaction.Description);
        }

        /// <summary>
        /// Test the IsCustomFee
        /// </summary>
        [Test]
        public void TestIsDebit()
        {
            bool input = true;
            requestTransaction.IsDebit = input;
            Assert.IsTrue(requestTransaction.IsDebit);
        }

        /// <summary>
        /// Test the TransactionType
        /// </summary>
        [Test]
        public void TestTransactionType()
        {
            TransactionType input = TransactionType.Adjustment;
            requestTransaction.TransactionType = input;
            Assert.AreEqual(input, requestTransaction.TransactionType);
            Assert.AreEqual(EnumUtilities.GetDescription(input), requestTransaction.Transaction);
        }

        /// <summary>
        /// Test the TransactionType
        /// </summary>
        [Test]
        public void TestCreatedDate()
        {            
            DateTime inputDate = DateTime.Today;
            requestTransaction.CreatedDate = inputDate;
            Assert.AreEqual(inputDate, requestTransaction.CreatedDate);
            Assert.IsNotNull(requestTransaction.FormattedDate);
        }

        [Test]
        public void TestRequestTxnComparer()
        {
            RequestTransaction txn1 = new RequestTransaction();
            txn1.CreatedDate = DateTime.Now;
            txn1.Description = "Check";
            txn1.IsDebit = false;
            txn1.Amount = 20;

            RequestTransaction txn2 = new RequestTransaction();
            txn2.CreatedDate = DateTime.Now.AddDays(-1);
            txn2.Description = "Check";
            txn2.IsDebit = false;
            txn2.Amount = 20;

            ListSortDescription listSortDescription = new ListSortDescription(TypeDescriptor.GetProperties(typeof(RequestTransaction))["FormattedDate"], ListSortDirection.Ascending);
            ListSortDescription[] listSortDescriptions = new ListSortDescription[] { listSortDescription };
            ListSortDescriptionCollection sorts = new ListSortDescriptionCollection(listSortDescriptions);
            RequestTransactionComparer comparer = new RequestTransactionComparer();
            comparer.SetSortDescriptions(sorts);
            Assert.AreEqual(1, comparer.Compare(txn1, txn2));   
        }

        #endregion
    }
}
