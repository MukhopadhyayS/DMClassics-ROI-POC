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
using System.Drawing;
using System.Collections.Generic;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Admin.Model;


namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for DocumentDetails
    /// </summary>
    [TestFixture]
    public class TestDocumentDetails
    {
        private DocumentDetails documentDetails;

        [SetUp]
        public void Initialize()
        {
            documentDetails = new DocumentDetails();
        }

        [TearDown]
        public void Dispose()
        {
            documentDetails = null;
        }

        /// <summary>
        /// Test method for DocumentType.
        /// </summary>
        [Test]
        public void TestDocumentType()
        {            
            DocumentType inputDocumentType = new DocumentType();
            inputDocumentType.Name = "Test Document";
            inputDocumentType.ChartOrder = "1";
            documentDetails.DocumentType = inputDocumentType;
            Assert.AreEqual(inputDocumentType, documentDetails.DocumentType);
            Assert.AreEqual(inputDocumentType.Name, documentDetails.Name);
            Assert.AreEqual(inputDocumentType.Name, documentDetails.CompareProperty);
            Assert.AreEqual(inputDocumentType.Key, documentDetails.Key);
        }

        /// <summary>
        /// Test method for Versions.
        /// </summary>
        [Test]
        public void TestVersions()
        {
            VersionDetails inputversionDetails = new VersionDetails();
            inputversionDetails.Pages.Add(1, new PageDetails("Test", 1, 1));
            inputversionDetails.VersionNumber = 1;
            documentDetails.Versions.Add(1, inputversionDetails);
            SortedList<int, VersionDetails> sortedVersion = documentDetails.Versions;
            VersionDetails outputVersionDetails = sortedVersion[1];
            Assert.AreEqual(inputversionDetails.VersionNumber, outputVersionDetails.VersionNumber);
        }

        /// <summary>
        /// Test case for document created datetime.
        /// </summary>
        [Test]
        public void TestDocumentDateTime()
        {
            Nullable<DateTime> inputDocumentDateTime = DateTime.Now;
            documentDetails.DocumentDateTime = inputDocumentDateTime;
            Nullable<DateTime> outputDocumentDateTime = documentDetails.DocumentDateTime;
            Assert.AreEqual(inputDocumentDateTime, outputDocumentDateTime);
        }

        [Test]
        public void TestCompare()
        {
            DocumentType obj1 = new DocumentType();
            obj1.Id = 1;
            obj1.Name = "101 Cold Datavault";
            DocumentType obj2 = new DocumentType();
            obj2.Name = "103 Cold Datavault";
            obj2.Id = 3;
            int result = DocumentType.Sorter.Compare(obj1, obj2);
            Assert.AreEqual(-1, result);
        }

        [Test]
        public void TestCreateDocType()
        {
            DocumentType docType1 = DocumentType.CreateDocType(12, "History", "sutitle", "12/12/2009", "0", true, 1234 );
            DocumentType docType2 = DocumentType.CreateDocType(docType1);
            Assert.AreEqual(docType1, docType2);
        }

        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), documentDetails.Icon);
        }

        [Test]
        public void TestDocumentId()
        {
            int documentId = 2;
            DocumentDetails doc = new DocumentDetails();
            doc.DocumentId = documentId;
            Assert.AreEqual(documentId, doc.DocumentId);
        }
    }
}
