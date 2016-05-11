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

using McK.EIG.ROI.Client.Request.Model;
//NUnit
using NUnit.Framework;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for Request Billing Info
    /// </summary>
    [TestFixture]
    public class TestRequestBillingInfo
    {
        //Holds the object of model class.
        private RequestBillingInfo requestBillingInfo;

        [SetUp]
        public void Initialize()
        {
            requestBillingInfo = new RequestBillingInfo();
        }

        [TearDown]
        public void Dispose()
        {
            requestBillingInfo = null;
        }

        /// <summary>
        /// Test case for ReqCoreChargesInvoiceDetail .
        /// </summary>
        [Test]
        public void TestReqCoreChargesInvoiceDetail()
        {
            List<RequestCoreChargesInvoiceDetail> inputReqCoreChargesInvoiceDetail = new List<RequestCoreChargesInvoiceDetail>();
            requestBillingInfo.ReqCoreChargesInvoiceDetail = null;
            List<RequestCoreChargesInvoiceDetail> outputReqCoreChargesInvoiceDetail = requestBillingInfo.ReqCoreChargesInvoiceDetail;
            Assert.IsNotNull(outputReqCoreChargesInvoiceDetail);
        }

        /// <summary>
        /// Test case for SalesTaxReasonsList .
        /// </summary>
        [Test]
        public void TestSalesTaxReasonsList()
        {
            List<SalesTaxReasons> inputSalesTaxReasonsList = new List<SalesTaxReasons>();
            requestBillingInfo.SalesTaxReasonsList = null;
            List<SalesTaxReasons> outputSalesTaxReasonsList = requestBillingInfo.SalesTaxReasonsList;
            Assert.IsNotNull(outputSalesTaxReasonsList);
        }
    }
}
