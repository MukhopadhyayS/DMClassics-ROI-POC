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

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for CommentDetails 
    /// </summary>
    [TestFixture]
    public class TestPropertyDetails
    {
        #region Fields

        private PropertyDetails propertyDetails;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            propertyDetails = new PropertyDetails();
        }

        [TearDown]
        public void Dispose()
        {
            propertyDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the patient name.
        /// </summary>
        [Test]
        public void TestPatientName()
        {
            string patientName = "Smith";
            propertyDetails.PatientName = patientName;
            Assert.AreEqual(patientName, propertyDetails.PatientName);
        }

        /// <summary>
        /// Test the MRN.
        /// </summary>
        [Test]
        public void TestMrn()
        {
            string mrn = "123456";
            propertyDetails.MRN = mrn;
            Assert.AreEqual(mrn, propertyDetails.MRN);
        }

        /// <summary>
        /// Test the Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string facility = "facility";
            propertyDetails.Facility = facility;
            Assert.AreEqual(facility, propertyDetails.Facility);
        } 

        /// <summary>
        /// Test the EPN.
        /// </summary>
        [Test]
        public void TestEpn()
        {
            string epn = "EPN123456";
            propertyDetails.EPN = epn;
            Assert.AreEqual(epn, propertyDetails.EPN);
        }

        /// <summary>
        /// Testt the Encounter.
        /// </summary>
        [Test]
        public void TestEncounter()
        {
            string encounter = "enc1,enc2";
            propertyDetails.Encounter = encounter;
            Assert.AreEqual(encounter, propertyDetails.Encounter);
        }

        /// <summary>
        /// Test the document name.
        /// </summary>
        [Test]
        public void TestDocumentName()
        {
            string documentName = "doc1";
            propertyDetails.DocumentName = documentName;
            Assert.AreEqual(documentName, propertyDetails.DocumentName);
        }

        /// <summary>
        /// Test the ImnetIds.
        /// </summary>
        [Test]
        public void TestImnetIds()
        {
            string imnetIds = "images1 1234, images2 5678";
            propertyDetails.IMNetIds = imnetIds;
            Assert.AreEqual(imnetIds, propertyDetails.IMNetIds);
        }

        /// <summary>
        /// Test the Pages.
        /// </summary>
        [Test]
        public void TestPages()
        {
            string pages = "Page1, Page2";
            propertyDetails.Pages = pages;
            Assert.AreEqual(pages, propertyDetails.Pages);
        }     

        /// <summary>
        /// Test fileids
        /// </summary>
        [Test]
        public void TestFileIds()
        {
            string fileIds = "File1, File2";
            propertyDetails.FileIds = fileIds;
            Assert.AreEqual(fileIds, propertyDetails.FileIds);
        }

        /// <summary>
        /// Test DocumentSubtitle
        /// </summary>
        [Test]
        public void TestDocumentSubtile()
        {
            string subTitle = "Subtitle1";
            propertyDetails.DocumentSubtitle = subTitle;
            Assert.AreEqual(subTitle, propertyDetails.DocumentSubtitle);
        }

        /// <summary>
        /// Test DocumentType
        /// </summary>
        [Test]
        public void TestDocumentType()
        {
            string docType = "DocumentType1";
            propertyDetails.DocumentType = docType;
            Assert.AreEqual(docType, propertyDetails.DocumentType);
        }

        /// <summary>
        /// Test case for document created datetime.
        /// </summary>
        [Test]
        public void TestDocumentDateTime()
        {
            Nullable<DateTime> inputDocumentDateTime = DateTime.Now;
            propertyDetails.DocumentDateTime = inputDocumentDateTime;
            Nullable<DateTime> outputDocumentDateTime = propertyDetails.DocumentDateTime;
            Assert.AreEqual(inputDocumentDateTime, outputDocumentDateTime);
        }

        /// <summary>
        /// Test Case for key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            string inputOutputKey = "DocumentID-DocTypeID-ChartOrder";
            propertyDetails.Key = inputOutputKey;
            string outputOutputKey = propertyDetails.Key;
            Assert.AreEqual(inputOutputKey, outputOutputKey);
        }

        /// <summary>
        /// Test File Name
        /// </summary>
        [Test]
        public void TestFileName()
        {
            string inputFileName = "Test";
            propertyDetails.FileNames = inputFileName;
            Assert.AreEqual(inputFileName, propertyDetails.FileNames);
        }

        /// <summary>
        /// Test File Exts
        /// </summary>
        [Test]
        public void TestFileExts()
        {
            string inputFileExts = "PDF";
            propertyDetails.FileExts = inputFileExts;
            Assert.AreEqual(inputFileExts, propertyDetails.FileExts);
        }

        /// <summary>
        /// Test File Type
        /// </summary>
        [Test]
        public void TestFileType()
        {
            string inputFileType = "Invoice";
            propertyDetails.FileTypes = inputFileType;
            Assert.AreEqual(inputFileType, propertyDetails.FileTypes);
        }

        /// <summary>
        /// Test ContentType
        /// </summary>
        [Test]
        public void TestContenetType()
        {
            string inputContentType = "ContentType1";
            propertyDetails.ContentType = inputContentType;
            Assert.AreEqual(inputContentType, propertyDetails.ContentType);
        }

        /// <summary>
        /// Test Attached Date
        /// </summary>
        [Test]
        public void TestAttachedDate()
        {
            string inputAttachedDate = DateTime.Now.ToString();
            propertyDetails.AttachDate = inputAttachedDate;
            Assert.AreEqual(inputAttachedDate, propertyDetails.AttachDate);
        }

        /// <summary>
        /// Test case Is Requestor Grouping.
        /// </summary>
        [Test]
        public void TestIsRequestorGrouping()
        {
            propertyDetails.IsRequestorGrouping = true;
            Assert.IsTrue(propertyDetails.IsRequestorGrouping);
        }

        /// <summary>
        /// Test Requestor Grouping
        /// </summary>
        [Test]
        public void TestRequestorGrouping()
        {
            string inputRequestorGrouping = "Test_RequestorName";
            propertyDetails.RequestorGrouping = inputRequestorGrouping;
            Assert.AreEqual(inputRequestorGrouping, propertyDetails.RequestorGrouping);
        }

        /// <summary>
        /// Test case for Date of Birth
        /// </summary>
        [Test]
        public void TestDOB()
        {
            string inputDOB = DateTime.Now.ToString();
            propertyDetails.DOB = inputDOB;
            string outputDOB = propertyDetails.DOB;
            Assert.AreEqual(inputDOB, outputDOB);
        }

        /// <summary>
        /// Test case for Admit Date
        /// </summary>
        [Test]
        public void TestAdmitDate()
        {
            string inputAdmitDate = DateTime.Now.ToString();
            propertyDetails.AdmitDate = inputAdmitDate;
            string outputAdmitDate = propertyDetails.AdmitDate;
            Assert.AreEqual(inputAdmitDate, outputAdmitDate);
        }

        /// <summary>
        /// Test case for Discharge Date
        /// </summary>
        [Test]
        public void TestDischargeDate()
        {
            string inputDischargeDate = DateTime.Now.ToString();
            propertyDetails.DischargeDate = inputDischargeDate;
            string outputDischargeDate = propertyDetails.DischargeDate;
            Assert.AreEqual(inputDischargeDate, outputDischargeDate);
        }

        /// <summary>
        /// Test Patient Type
        /// </summary>
        [Test]
        public void TestPatientType()
        {
            string inputPatientType = "Test";
            propertyDetails.PatientType = inputPatientType;
            Assert.AreEqual(inputPatientType, propertyDetails.PatientType);
        }

        /// <summary>
        /// Test Request ID
        /// </summary>
        [Test]
        public void TestRequestID()
        {
            long inputRequestID = 10;
            propertyDetails.RequestID = inputRequestID;
            Assert.AreEqual(inputRequestID, propertyDetails.RequestID);
        }

        /// <summary>
        /// Test Requestor Name
        /// </summary>
        [Test]
        public void TestRequestorName()
        {
            string inputRequestorName = "Test";
            propertyDetails.RequestorName = inputRequestorName;
            Assert.AreEqual(inputRequestorName, propertyDetails.RequestorName);
        }

        /// <summary>
        /// Test case for Request Created
        /// </summary>
        [Test]
        public void TestRequestCreated()
        {
            Nullable<DateTime> inputRequestCreated = DateTime.Now;
            propertyDetails.RequestCreated = inputRequestCreated;
            Nullable<DateTime> outputRequestCreated = propertyDetails.RequestCreated;
            Assert.AreEqual(inputRequestCreated, outputRequestCreated);
        }

        /// <summary>
        /// Test case for Request Completed
        /// </summary>
        [Test]
        public void TestRequestCompleted()
        {
            string inputRequestCompleted = DateTime.Now.ToString();
            propertyDetails.RequestCompleted = inputRequestCompleted;
            string outputRequestCompleted = propertyDetails.RequestCompleted;
            Assert.AreEqual(inputRequestCompleted, outputRequestCompleted);
        }

        /// <summary>
        /// Test TotalPageCount
        /// </summary>
        [Test]
        public void TestTotalPageCount()
        {
            long inputTotalPageCount = 2;
            propertyDetails.TotalPageCount = inputTotalPageCount;
            Assert.AreEqual(inputTotalPageCount, propertyDetails.TotalPageCount);
        }

        /// <summary>
        /// Test Notes
        /// </summary>
        [Test]
        public void TestNotes()
        {
            string inputNotes = "Notes";
            propertyDetails.OutputNotes = inputNotes;
            Assert.AreEqual(inputNotes, propertyDetails.OutputNotes);
        }

        /// <summary>
        /// Test EncounterCount
        /// </summary>
        [Test]
        public void TestEnounterCount()
        {
            int inputEnounterCount = 2;
            propertyDetails.EncounterCount = inputEnounterCount;
            Assert.AreEqual(inputEnounterCount, propertyDetails.EncounterCount);
        }

        /// <summary>
        /// Test PatientCount
        /// </summary>
        [Test]
        public void TestPatientCount()
        {
            int inputPatientCount = 2;
            propertyDetails.PatientCount = inputPatientCount;
            Assert.AreEqual(inputPatientCount, propertyDetails.PatientCount);
        }


        #endregion
    }
}
