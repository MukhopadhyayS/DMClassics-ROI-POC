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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for SalesTaxChargeDetails
    /// </summary>
    [TestFixture]
    public class TestSalesTaxReasonComparer : SalesTaxReasonComparer
    {
        private SalesTaxReasons salesTaxReasons;
        [SetUp]
        public void Initialize()
        {
            salesTaxReasons = new SalesTaxReasons();
        }

        [TearDown]
        public void Dispose()
        {
            salesTaxReasons = null;
        }

        /// <summary>
        /// Test the Get Property Value
        /// </summary>
        [Test]
        public void TestGetPropertyValue()
        {
            salesTaxReasons.CreatedDate = DateTime.Now;
            PropertyDescriptorCollection PDC = TypeDescriptor.GetProperties(this);
            PropertyDescriptor propertyDescriptor = PDC[System.Reflection.MethodBase.GetCurrentMethod().Name];
            Object object1 = GetPropertyValue(salesTaxReasons, propertyDescriptor);
            Object object2 = GetPropertyValue(salesTaxReasons, propertyDescriptor);

            Assert.AreSame(object1, object2);
        }
    }


    /// <summary>
    /// Test class for SalesTaxChargeDetails
    /// </summary>
    [TestFixture]
    public class TestSalesTaxReasons
    {
        #region Fields

        private SalesTaxReasons salesTaxReasons;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            salesTaxReasons = new SalesTaxReasons();
        }

        [TearDown]
        public void Dispose()
        {
            salesTaxReasons = null;
        }

        #endregion

        /// <summary>
        /// Test the empty reason
        /// </summary>
        [Test]
        public void TestSalesTaxReasonID()
        {
            long inputID = 10;
            salesTaxReasons.Id = inputID;
            long outputID = salesTaxReasons.Id;
            Assert.AreEqual(inputID, outputID);
        }

        /// <summary>
        /// Test the created date
        /// </summary>
        [Test]
        public void TestCreatedDate()
        {
            DateTime input = DateTime.Now;
            salesTaxReasons.CreatedDate = input;
            Assert.AreEqual(input, salesTaxReasons.CreatedDate);
        }

        /// <summary>
        /// Test the formatted date
        /// </summary>
        [Test]
        public void TestFormattedDate()
        {   
            salesTaxReasons.CreatedDate = DateTime.Now;
            string input = salesTaxReasons.FormattedDate;
            Assert.AreEqual(input, salesTaxReasons.FormattedDate);
        }

        /// <summary>
        /// Test the empty reason
        /// </summary>
        [Test]
        public void TestEmptyReason()
        {
            string input = "";
            salesTaxReasons.Reason = input;
            Assert.AreEqual(input, salesTaxReasons.Reason);
        }

        /// <summary>
        /// Test the null reason
        /// </summary>
        [Test]
        public void TestNullReason()
        {
            string input = null;
            salesTaxReasons.Reason = input;
            Assert.AreEqual(input, salesTaxReasons.Reason);
        }

        /// <summary>
        /// Test the reason
        /// </summary>
        [Test]
        public void TestReason()
        {
            string input = "Charge is higher, hence fee charge is removed";
            salesTaxReasons.Reason = input;
            Assert.AreEqual(input, salesTaxReasons.Reason);
        }
      
        #endregion
    }
}




