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
using System.Drawing;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;


namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test Class for Request Non-Hpf Documnet Details.
    /// </summary>
    [TestFixture]
    public class TestRequestNonHpfDocumentDetails
    {

        private RequestNonHpfDocumentDetails requestNonHpfDetails;

        [SetUp]
        public void Initialize()
        {
            requestNonHpfDetails = new RequestNonHpfDocumentDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestNonHpfDetails = null;
        }

        #region TestMethods

        /// <summary>
        /// Test case for Id.
        /// </summary>
        [Test]
        public void TestId()
        {
            long inputId = 100;
            requestNonHpfDetails.Id = inputId;
            long outputId = requestNonHpfDetails.Id;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test Case for Documnet Type.
        /// </summary>
        [Test]
        public void TestDocType()
        {
            string inputDocType = "test";
            requestNonHpfDetails.DocType = inputDocType;
            string outputDocType = requestNonHpfDetails.DocType;
            Assert.AreEqual(inputDocType, outputDocType);
        }

        /// <summary>
        /// Test method for Subtitle
        /// </summary>
        [Test]
        public void TestDocumentSubtitle()
        {
            string inputName = "History";
            requestNonHpfDetails.Subtitle = inputName;
            string outputName = requestNonHpfDetails.Subtitle;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test method for PageCount
        /// </summary>
        [Test]
        public void TestDocumentPageCount()
        {
            int pageCount = 3;
            requestNonHpfDetails.PageCount = pageCount;
            Assert.AreEqual(pageCount, requestNonHpfDetails.PageCount);
        }

        /// <summary>
        /// Test method for Billing Tier
        /// </summary>
        [Test]
        public void TestDocumentBillingTier()
        {
            long billingTierId = 3;
            requestNonHpfDetails.BillingTier = billingTierId;
            Assert.AreEqual(billingTierId, requestNonHpfDetails.BillingTier);
        }

        /// <summary>
        /// Test Case for Encounter
        /// </summary>
        [Test]
        public void TestEncounter()
        {
            string inputEncounterId = "A12344";
            requestNonHpfDetails.Encounter = inputEncounterId;
            string outputEncounterId = requestNonHpfDetails.Encounter;
            Assert.AreEqual(inputEncounterId, outputEncounterId);
        }

        /// <summary>
        /// Test Case for Patient Date of Service.
        /// </summary>
        [Test]
        public void TestCaseForDateOfService()
        {
            Nullable<DateTime> inputDateOfService = DateTime.Now;
            requestNonHpfDetails.DateOfService = inputDateOfService;
            Nullable<DateTime> outputDateOfService = requestNonHpfDetails.DateOfService;
            Assert.AreEqual(inputDateOfService, outputDateOfService.Value);
        }

        /// <summary>
        /// Test Case for Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string inputFacility = "Test";
            requestNonHpfDetails.Facility = inputFacility;
            string outputFacility = requestNonHpfDetails.Facility;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        
        /// <summary>
        /// Test method for patient key.
        /// </summary>
        [Test]
        public void TestPatientKey()
        {
            string inputName = "123";
            requestNonHpfDetails.PatientKey = inputName;
            string outputName = requestNonHpfDetails.PatientKey;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test Case for Department.
        /// </summary>
        [Test]
        public void TestDepartment()
        {
            string inputDepartment = "CT";
            requestNonHpfDetails.Department = inputDepartment;
            string outputDepartment = requestNonHpfDetails.Department;
            Assert.AreEqual(inputDepartment, outputDepartment);
        }

        /// <summary>
        /// Test Case for Comment
        /// </summary>
        [Test]
        public void TestComment()
        {
            string inputComment = "Injury";
            requestNonHpfDetails.Comment = inputComment;
            string outputComment = requestNonHpfDetails.Comment;
            Assert.AreEqual(inputComment, outputComment);
        }

        /// <summary>
        /// Test Case for sequence Number of a page.
        /// </summary>
        [Test]
        public void TestSequenceNumber()
        {
            long inputSequenceNumber = 90;
            requestNonHpfDetails.SequenceNumber = inputSequenceNumber;
            long outputSequenceNumber = requestNonHpfDetails.SequenceNumber;
            Assert.AreEqual(inputSequenceNumber, outputSequenceNumber);
        }

        /// <summary>
        /// Test Case for selected for release.
        /// </summary>
        [Test]
        public void TestSelectedForRelease()
        {
            requestNonHpfDetails.SelectedForRelease = true;
            Assert.IsTrue(requestNonHpfDetails.SelectedForRelease.Value);            
        }

        /// <summary>
        /// Test Case for Isreleased.
        /// </summary>
        [Test]
        public void TestIsReleased()
        {
            bool inputIsReleased = true;
            requestNonHpfDetails.IsReleased = inputIsReleased;
            bool outputIsReleased = requestNonHpfDetails.IsReleased;
            Assert.AreEqual(inputIsReleased, outputIsReleased);
        }

        /// <summary>
        /// Test case for CreatedBy.
        /// </summary>
        [Test]
        public void TestCreatedBy()
        {
            int inputId = 100;
            requestNonHpfDetails.CreatedBy = inputId;
            int outputId = requestNonHpfDetails.CreatedBy;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test method for GetHashCode with ID = 0.
        /// </summary>
        [Test]
        public void TestGetHashCodeWithoutID()
        {
            Assert.IsInstanceOfType(typeof(int), requestNonHpfDetails.GetHashCode());
        }

        /// <summary>
        /// Test method for GetHashCode with ID != 0.
        /// </summary>
        [Test]
        public void TestGetHashCodeWithID()
        {
            requestNonHpfDetails.Id = 45;
            Assert.IsInstanceOfType(typeof(int), requestNonHpfDetails.GetHashCode());
        }

        /// <summary>
        /// Test method for GetHashCode with ID = 0.
        /// </summary>
        [Test]
        public void TestEqualWithOutID()
        {
            RequestNonHpfDocumentDetails docDetails = new RequestNonHpfDocumentDetails();
            requestNonHpfDetails.Equals(docDetails);
        }

        /// <summary>
        /// Test method for GetHashCode with ID != 0.
        /// </summary>
        [Test]
        public void TestEqualWithID()
        {
            RequestNonHpfDocumentDetails docDetails = new RequestNonHpfDocumentDetails();
            requestNonHpfDetails.Id = 45;
            requestNonHpfDetails.Equals(docDetails);
        }

        /// <summary>
        /// Test Method for Normalize.
        /// </summary>
        [Test]
        public void TestNormalize()
        {
            RequestNonHpfDocumentDetails docDetails = new RequestNonHpfDocumentDetails();
            docDetails.Encounter = "Encounter1";
            docDetails.Department = "Lab";
            docDetails.Facility = "XXX";
            docDetails.Comment = "zzz";
            docDetails.DocType = "test";
            docDetails.Subtitle = "test";
            docDetails.Normalize();
        }

        /// <summary>
        /// Test Case for Name.
        /// </summary>
        [Test]
        public void TestName()
        {
            string inputName = requestNonHpfDetails.Name;
        }

        /// <summary>
        /// Test Case for Key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            requestNonHpfDetails.DateOfService = DateTime.Now.Date;
            requestNonHpfDetails.Id = 5;
            Assert.IsNotNull(requestNonHpfDetails.Key);
        }

        /// <summary>
        /// Test Case for Icon.
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), requestNonHpfDetails.Icon);
        }

        /// <summary>
        /// Test Case for Paramaterized Constructor.
        /// </summary>
        [Test]
        public void TestConstructor()
        {
            NonHpfDocumentDetails nonHpfDocument = new NonHpfDocumentDetails();
            nonHpfDocument.Id = 1;
            nonHpfDocument.DocType = "test";
            nonHpfDocument.Encounter = "encounter";
            nonHpfDocument.FacilityCode = "xxx";
            nonHpfDocument.Department = "test";
            nonHpfDocument.DateOfService = DateTime.Now;
            nonHpfDocument.Subtitle = "test";
            nonHpfDocument.PageCount = 3;
            nonHpfDocument.Comment = "for insurance";
            nonHpfDocument.RecordVersionId = 0;
            RequestNonHpfDocumentDetails requestNonHpf = new RequestNonHpfDocumentDetails(nonHpfDocument);
            Assert.AreEqual(nonHpfDocument.Id, requestNonHpf.Id);
        }

        /// <summary>
        /// Test Case for DocumentSeq
        /// </summary>
        [Test]
        public void TestDocumentSeq()
        { 
            long input = 123465;
            requestNonHpfDetails.DocumentSeq = input;
            long output = requestNonHpfDetails.DocumentSeq;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test Case for SupplementalID
        /// </summary>
        [Test]
        public void TestSupplementalID()
        {
            long input = 123465;
            requestNonHpfDetails.SupplementalID = input;
            long output = requestNonHpfDetails.SupplementalID;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test Case for PatientSeq
        /// </summary>
        [Test]
        public void TestPatientSeq()
        {
            long input = 123465;
            requestNonHpfDetails.PatientSeq = input;
            long output = requestNonHpfDetails.PatientSeq;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test Case for IsHPF
        /// </summary>
        [Test]
        public void TestIsHPF()
        {
            bool input = true;
            requestNonHpfDetails.IsHPF = input;
            bool output = requestNonHpfDetails.IsHPF;
            Assert.AreEqual(input, output);
        }


        /// <summary>
        /// Test Case for PatientID
        /// </summary>
        [Test]
        public void TestPatientID()
        {
            long input = 123465;
            requestNonHpfDetails.PatientID = input;
            long output = requestNonHpfDetails.PatientID;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test Case for PatientMRN
        /// </summary>
        [Test]
        public void TestPatientMRN()
        {
            string input = "123465";
            requestNonHpfDetails.PatientMRN = input;
            string output = requestNonHpfDetails.PatientMRN;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test Case for PatientFacility
        /// </summary>
        [Test]
        public void TestPatientFacility()
        {
            string input = "AD";
            requestNonHpfDetails.PatientFacility = input;
            string output = requestNonHpfDetails.PatientFacility;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test Case for BillingTierStatus
        /// </summary>
        [Test]
        public void TestBillingTierStatus()
        {
            long input = 123465;
            requestNonHpfDetails.BillingTierStatus = input;
            long output = requestNonHpfDetails.BillingTierStatus;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test Case for IsPatientFreeFormFacility
        /// </summary>
        [Test]
        public void TestIsPatientFreeFormFacility()
        {
            bool input = true;
            requestNonHpfDetails.IsPatientFreeFormFacility = input;
            bool output = requestNonHpfDetails.IsPatientFreeFormFacility;
            Assert.AreEqual(input, output);
        }

    #endregion
    }
}
