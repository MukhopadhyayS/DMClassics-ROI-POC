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
    /// Test case for Test Invoice And Document Details
    /// </summary>
    [TestFixture]
    public class TestInvoiceAndDocumentDetails
    {
        //Holds the object of model class.
        private InvoiceAndDocumentDetails invoiceAndDocumentDetails;

        [SetUp]
        public void Initialize()
        {
            invoiceAndDocumentDetails = new InvoiceAndDocumentDetails();
        }

        [TearDown]
        public void Dispose()
        {
            invoiceAndDocumentDetails = null;
        }

        /// <summary>
        /// Test case for Document Info List.
        /// </summary>
        [Test]
        public void TestDocumentInfoList()
        {
            DocumentInfoList inputDocumentInfoList = new DocumentInfoList();
            invoiceAndDocumentDetails.DocumentInfos = inputDocumentInfoList;
            DocumentInfoList outputDocumentInfoList = invoiceAndDocumentDetails.DocumentInfos;
            Assert.IsNotNull(outputDocumentInfoList);
        }

        /// <summary>
        /// Test case for Invoice Charge Details.
        /// </summary>
        [Test]
        public void TestInvoiceChargeDetails()
        {
            InvoiceChargeDetails inputInvoiceChargeDetails = new InvoiceChargeDetails();
            invoiceAndDocumentDetails.InvoiceChargeDetailsInfo = inputInvoiceChargeDetails;
            InvoiceChargeDetails outputInvoiceChargeDetails = invoiceAndDocumentDetails.InvoiceChargeDetailsInfo;
            Assert.IsNotNull(outputInvoiceChargeDetails);
        }

        /// <summary>
        /// Test case for Encounter Facility.
        /// </summary>
        [Test]
        public void TestEncounterFacility()
        {
            string inputDocumentName = "101 Cold Data Vault";
            invoiceAndDocumentDetails.DocumentName = inputDocumentName;
            string outputDocumentName = invoiceAndDocumentDetails.DocumentName;
            Assert.AreEqual(inputDocumentName, outputDocumentName);
        }

        /// <summary>
        /// Test case for Document Info List.
        /// </summary>
        [Test]
        public void TestDocumentInfoListWithNullValue()
        {
            DocumentInfoList inputDocumentInfoList = null;
            invoiceAndDocumentDetails.DocumentInfos = inputDocumentInfoList;
            DocumentInfoList outputDocumentInfoList = invoiceAndDocumentDetails.DocumentInfos;
            Assert.IsNotNull(outputDocumentInfoList);
        }

        /// <summary>
        /// Test case for Invoice Charge Details.
        /// </summary>
        [Test]
        public void TestInvoiceChargeDetailsWithNullValue()
        {
            InvoiceChargeDetails inputInvoiceChargeDetails = null;
            invoiceAndDocumentDetails.InvoiceChargeDetailsInfo = inputInvoiceChargeDetails;
            InvoiceChargeDetails outputInvoiceChargeDetails = invoiceAndDocumentDetails.InvoiceChargeDetailsInfo;
            Assert.IsNotNull(outputInvoiceChargeDetails);
        }
    }
}
