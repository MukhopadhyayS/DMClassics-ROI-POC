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
using System.Collections.ObjectModel;
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
    ///  Test class for DocumentTypeDetails
    /// </summary>
    [TestFixture]
    public class TestDocumentTypeDetails
    {
        private DocumentType documentType;

        [SetUp]
        public void Initialize()
        {
            documentType = new DocumentType();
        }

        [TearDown]
        public void Dispose()
        {
            documentType = null;
        }

        /// <summary>
        /// Test method for TestDocumentTypeId.
        /// </summary>
        [Test]
        public void Test01DocumentTypeId()
        {
            long inputDocumentTypeId = 1;
            documentType.Id = inputDocumentTypeId;            
            Assert.AreEqual(inputDocumentTypeId, documentType.Id);
        }

        /// <summary>
        /// Test method for TestDocTypeSubTitle.
        /// </summary>
        [Test]
        public void Test02DocTypeName()
        {
            string inputName = "Test";
            documentType.Name = inputName;            
            Assert.AreEqual(inputName, documentType.Name);
        }

        /// <summary>
        /// Test method for TestDocTypeSubTitle.
        /// </summary>
        [Test]
        public void Test03DocTypeSubTitle()
        {
            string inputSubTitle = "AWD";
            documentType.Subtitle = inputSubTitle;            
            Assert.AreEqual(inputSubTitle, documentType.Subtitle);
        }

        /// <summary>
        /// Test method for TestDocTypeChartOrder.
        /// </summary>
        [Test]
        public void Test04DocTypeDateOfService()
        {
            DateTime inputDateOfService = DateTime.Now;
            documentType.DateOfService = inputDateOfService;            
            Assert.AreEqual(inputDateOfService, documentType.DateOfService);
        }

        /// <summary>
        /// Test method for TestDocTypeChartOrder.
        /// </summary>
        [Test]
        public void Test05DocTypeChartOrder()
        {
            string inputChartOrder = "0";
            documentType.ChartOrder = inputChartOrder;
            Assert.AreEqual(inputChartOrder, documentType.ChartOrder);
        }

        /// <summary>
        /// Test method for TestIsDisclosure.
        /// </summary>
        [Test]
        public void Test06IsDisclosure()
        {
            bool inputIsDisclosure = true;
            documentType.IsDisclosure = inputIsDisclosure;            
            Assert.AreEqual(inputIsDisclosure, documentType.IsDisclosure);
        }

        /// <summary>
        /// Test method for getting hash code.
        /// </summary>
        [Test]
        public void Test07GethashCode()
        {
            documentType.Id = 1;
            documentType.Name = "137 Cold Datavault";
            documentType.Subtitle = "ROI Testing";
            Assert.IsInstanceOfType(typeof(int), documentType.GetHashCode());
        }

        /// <summary>
        /// Test method for icon
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), DocumentType.Icon);
        }

        /// <summary>
        /// Test Case for PatientDetailsSorter.
        /// </summary>
        [Test]
        public void Test08DocumentTypeSorter()
        {
            Collection<DocumentType> docType = new Collection<DocumentType>();
            DocumentType type1 = new DocumentType();
            type1.Id = 1;
            type1.Name = "Document1";
            type1.IsDisclosure = true;
            type1.Subtitle = "Doc";
            docType.Add(type1);
            DocumentType type2 = new DocumentType();
            type2.Id = 2;
            type2.Name = "Patient";
            type2.IsDisclosure = true;
            type2.Subtitle = "Pat";
            docType.Add(type2);
            List<DocumentType> list = new List<DocumentType>(docType);
            list.Sort(DocumentType.Sorter);
        }

        /// <summary>
        /// Test case for Document Type Equals
        /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(documentType.Equals(documentType));
        }

    }
}
