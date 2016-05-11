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
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{
    /// <summary>
    /// Test class for Requestor Adjustment Details
    /// </summary>
    [TestFixture]
    public class TestAdjustmentInfoDetail
    {
        //Holds the object of model class.
        private AdjustmentInfoDetail adjustmentInfoDetail;

        [SetUp]
        public void Initialize()
        {
            adjustmentInfoDetail = new AdjustmentInfoDetail();
        }

        [TearDown]
        public void Dispose()
        {
            adjustmentInfoDetail = null;
        }

        /// <summary>
        /// Test case for Reason List.
        /// </summary>
        [Test]
        public void TestReasonList()
        {
            Collection<ReasonDetails> inputReasonList = new Collection<ReasonDetails>();
            adjustmentInfoDetail.ReasonsList = inputReasonList;
            Collection<ReasonDetails> outputReasonList = adjustmentInfoDetail.ReasonsList;
            Assert.IsNotNull(outputReasonList);
        }

         /// <summary>
        /// Test case for Reason List.
        /// </summary>
        [Test]
        public void TestRequestorAdjusmtmentDetails()
        {
            RequestorAdjustmentDetails inputRequestorAdjustmentDetails = new RequestorAdjustmentDetails();
            adjustmentInfoDetail.ReqAdjustmentDetails = inputRequestorAdjustmentDetails;
            RequestorAdjustmentDetails outputRequestorAdjustmentDetails = inputRequestorAdjustmentDetails;
            Assert.IsNotNull(outputRequestorAdjustmentDetails);
        }

        /// <summary>
        /// Test case for Reason List.
        /// </summary>
        [Test]
        public void TestRequestorInvoiceList()
        {
            Collection<RequestInvoiceDetail> inputRequestorInvoicesList = new Collection<RequestInvoiceDetail>();
            adjustmentInfoDetail.RequestorInvoicesList = inputRequestorInvoicesList;
            Collection<RequestInvoiceDetail> outputRequestorInvoicesList = adjustmentInfoDetail.RequestorInvoicesList;
            Assert.IsNotNull(outputRequestorInvoicesList);
        }
    }
}
