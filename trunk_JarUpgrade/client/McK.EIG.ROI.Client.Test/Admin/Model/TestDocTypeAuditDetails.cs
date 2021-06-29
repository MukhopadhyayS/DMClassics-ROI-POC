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
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    /// Test class for DocTypeAuditDetails.
    /// </summary>
    [TestFixture]
    public class TestDocTypeAuditDetails
    {
        // Holds the model object.
        private DocTypeAuditDetails docTypeAuditDetails;

        /// <summary>
        /// Create a new PaymentMethodsDetails instance
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            docTypeAuditDetails = new DocTypeAuditDetails();
        }

        /// <summary>
        /// Dispose the object created 
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            docTypeAuditDetails = null;
        }

        /// <summary>
        /// Test case for Id
        /// </summary>
        [Test]
        public void TestId()
        {
            long inputId = 1000;
            docTypeAuditDetails.Id = inputId;
            long outputId = docTypeAuditDetails.Id;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test case for DocType
        /// </summary>
        [Test]
        public void TestDocType()
        {
            string inputdocType = "DocType";
            docTypeAuditDetails.DocType = inputdocType;
            string outputdocType = docTypeAuditDetails.DocType;
            Assert.AreEqual(inputdocType, outputdocType);
        }

        /// <summary>
        /// Test case for DocName
        /// </summary>
        [Test]
        public void TestDocName()
        {
            string inputDocName = "DocName";
            docTypeAuditDetails.DocName = inputDocName;
            string outputDocName = docTypeAuditDetails.DocName;
            Assert.AreEqual(inputDocName, outputDocName);
        }

        /// <summary>
        /// Test case for FromValue
        /// </summary>
        [Test]
        public void TestFromValue()
        {
            string inputFromValue = "FromValue";
            docTypeAuditDetails.FromValue = inputFromValue;
            string outputFromValue = docTypeAuditDetails.FromValue;
            Assert.AreEqual(inputFromValue, outputFromValue);
        }

        /// <summary>
        /// Test case for ToValue
        /// </summary>
        [Test]
        public void TestToValue()
        {
            string inputToValue = "ToValue";
            docTypeAuditDetails.ToValue = inputToValue;
            string outputToValue = docTypeAuditDetails.ToValue;
            Assert.AreEqual(inputToValue, outputToValue);
        }

        /// <summary>
        /// Test case for CodeSetName
        /// </summary>
        [Test]
        public void TestCodeSetName()
        {
            string inputCodeSetName = "CodeSetName";
            docTypeAuditDetails.CodeSetName = inputCodeSetName;
            string outputCodeSetName = docTypeAuditDetails.CodeSetName;
            Assert.AreEqual(inputCodeSetName, outputCodeSetName);
        }

    }
}
