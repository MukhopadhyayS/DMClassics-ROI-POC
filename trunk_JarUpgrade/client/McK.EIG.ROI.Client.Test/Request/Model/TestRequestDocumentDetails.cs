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
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using System.Drawing;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test Class for Request Document Details.
    /// </summary>
    [TestFixture]
    public class TestRequestDocumentDetails
    {
        private RequestDocumentDetails requestDocumentDetails;

        [SetUp]
        public void Initialize()
        {
            requestDocumentDetails = new RequestDocumentDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestDocumentDetails = null;
        }

        #region TestMethods

        /// <summary>
        /// Test Case for Documnet Type.
        /// </summary>
        [Test]
        public void TestDocType()
        {
            string inputDocType = "Patient Records";
            requestDocumentDetails.DocType = inputDocType;
            string outputDocType = requestDocumentDetails.DocType;
            Assert.AreEqual(inputDocType, outputDocType);
        }

        /// <summary>
        /// Test Case for SubTitle.
        /// </summary>
        [Test]
        public void TestSubTitle()
        {
            string inputSubTitle = "Health Records";
            requestDocumentDetails.Subtitle = inputSubTitle;
            string outputSubTitle = requestDocumentDetails.Subtitle;
            Assert.AreEqual(inputSubTitle, outputSubTitle);
        }

        /// <summary>
        /// Test Case for DocId.
        /// </summary>
        [Test]
        public void TestDocId()
        {
            requestDocumentDetails.DocTypeId = 12;
            Assert.AreEqual(12, requestDocumentDetails.DocTypeId);
        }

        /// <summary>
        /// Test Case for ChartOrder.
        /// </summary>
        [Test]
        public void TestChartOrder()
        {
            requestDocumentDetails.ChartOrder = "1";
            Assert.AreEqual("1", requestDocumentDetails.ChartOrder);
        }

        /// <summary>
        /// Test case for IsDeficient.
        /// </summary>
        [Test]
        public void TestIsDeficient()
        {
            bool inputIsDeficient = true;
            requestDocumentDetails.IsDeficient = inputIsDeficient;
            bool outputIsDeficient = requestDocumentDetails.IsDeficient;
            Assert.AreEqual(inputIsDeficient, outputIsDeficient);
        }

        
        /// <summary>
        /// Test Case for Patient Date of Service.
        /// </summary>
        [Test]
        public void TestPatientDateOfService()
        {
            Nullable<DateTime>  inputDateTime = DateTime.Now;
            requestDocumentDetails.DateOfService = inputDateTime;
            Nullable<DateTime> outputDateTime = requestDocumentDetails.DateOfService;
            Assert.AreEqual(inputDateTime, outputDateTime);
        }

        /// <summary>
        /// Test Case for Name.
        /// </summary>
        [Test]
        public void TestName()
        {
            requestDocumentDetails.DocType = "History and Physical";
            Assert.AreEqual(requestDocumentDetails.DocType, requestDocumentDetails.Name);
        }

        /// <summary>
        /// Test Case for Key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            requestDocumentDetails.DocType = "History and Physical";
            Assert.AreEqual(requestDocumentDetails.DocType, requestDocumentDetails.Name);
        }

        /// <summary>
        /// Test Case for document sequence
        /// </summary>
        [Test]
        public void TestDocumentSequence()
        {
            long inputDocumentSeq = 1;
            requestDocumentDetails.DocumentSeq = inputDocumentSeq;
            long outputDocumentSeq = requestDocumentDetails.DocumentSeq;
            Assert.AreEqual(inputDocumentSeq, outputDocumentSeq);
        }

        /// <summary>
        /// Test Case for encounter sequence
        /// </summary>
        [Test]
        public void TestEncounterSequence()
        {
            long inputEncounterSeq = 1;
            requestDocumentDetails.EncounterSeq = inputEncounterSeq;
            long outputEncounterSeq = requestDocumentDetails.EncounterSeq;
            Assert.AreEqual(inputEncounterSeq, outputEncounterSeq);
        }

        /// <summary>
        /// Test Case for Paramaterized Constructor
        /// </summary>
        [Test]
        public void TestParamConstructor()
        {
            DocumentDetails recordDoc = new DocumentDetails();
            DocumentType docType = new DocumentType();
            docType.Name = "test";
            docType.Subtitle = "test1";
            recordDoc.DocumentType = docType;
            recordDoc.DocumentType.DateOfService = DateTime.Now;
            RequestDocumentDetails requestDocuments = new RequestDocumentDetails(recordDoc);
            string doctype = recordDoc.DocumentType.Name;
            string subtitle = recordDoc.DocumentType.Subtitle;
            Assert.AreEqual(recordDoc.DocumentType.Name, doctype);
        }

        /// <summary>
        /// Test Case for Icon.
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), requestDocumentDetails.Icon);
        }

        [Test]
        public void TestDocumentId()
        {
            requestDocumentDetails.DocumentId = 12;
            Assert.AreEqual(12, requestDocumentDetails.DocumentId);            
        }

        /// <summary>
        /// Test case for document created datetime.
        /// </summary>
        [Test]
        public void TestDocumentDateTime()
        {
            Nullable<DateTime> inputDocumentDateTime = DateTime.Now;
            requestDocumentDetails.DocumentDateTime = inputDocumentDateTime;
            Nullable<DateTime> outputDocumentDateTime = requestDocumentDetails.DocumentDateTime;
            Assert.AreEqual(inputDocumentDateTime, outputDocumentDateTime);
        }

        /// <summary>
        /// Test Case for Output key.
        /// </summary>
        [Test]
        public void TestOutputKey()
        {
            string inputOutputKey = "Output.Key";
            requestDocumentDetails.OutputKey = inputOutputKey;
            requestDocumentDetails.ChartOrder = "tst";
            string outputOutputKey = requestDocumentDetails.OutputKey;
            Assert.AreEqual(inputOutputKey, outputOutputKey);
        }

        /// <summary>
        /// Test Case for PatientSeq.
        /// </summary>
        [Test]
        public void TestPatientSeq()
        {
            long input = 123465;
            requestDocumentDetails.PatientSeq = input;
            long output = requestDocumentDetails.PatientSeq;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test Case for IsGlobalDocument.
        /// </summary>
        [Test]
        public void TestIsGlobalDocumentq()
        {
            bool input = true;
            requestDocumentDetails.IsGlobalDocument = input;
            bool output = requestDocumentDetails.IsGlobalDocument;
            Assert.AreEqual(input, output);
        }

        #endregion

    }
}
