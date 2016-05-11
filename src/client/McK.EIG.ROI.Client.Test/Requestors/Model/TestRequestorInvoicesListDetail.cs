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
    /// Test class for RequestorInvoicesListDetail
    /// </summary>
    [TestFixture]
    class TestRequestorInvoicesListDetail
    {
        //Holds the object of model class.
        private RequestorInvoicesListDetail requestorInvoicesListDetail;

        [SetUp]
        public void Initialize()
        {
            requestorInvoicesListDetail = new RequestorInvoicesListDetail();
        }

        [TearDown]
        public void Dispose()
        {
            requestorInvoicesListDetail = null;
        }

        #region TestMethods
        /// <summary>
        /// Test case for ReqInvoice List
        /// </summary>
        [Test]
        public void TestReqInvoiceList()
        {
            List<RequestInvoiceDetail> inputRequestInvoiceDetail = new List<RequestInvoiceDetail>();
            requestorInvoicesListDetail.ReqInvoiceList = inputRequestInvoiceDetail;
            List<RequestInvoiceDetail> outputRequestInvoiceDetail = requestorInvoicesListDetail.ReqInvoiceList;
            Assert.IsNotNull(outputRequestInvoiceDetail);
        }

        /// <summary>
        /// Test case for ReqInvoice List
        /// </summary>
        [Test]
        public void TestRequestInvoiceListWithEmptyData()
        {
            List<RequestInvoiceDetail> inputRequestInvoiceDetail = requestorInvoicesListDetail.ReqInvoiceList;
        }

        #endregion
    }
}
