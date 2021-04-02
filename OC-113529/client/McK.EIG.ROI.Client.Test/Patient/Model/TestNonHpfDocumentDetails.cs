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

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for NonHpfDocumentDetails
    /// </summary>
    [TestFixture]
    public class TestNonHpfDocumentDetails
    {
        private NonHpfDocumentDetails nonHpfDocument;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            nonHpfDocument = new NonHpfDocumentDetails();
        }

        /// <summary>
        /// Dispose PatientDetail.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            nonHpfDocument = null;
        }

        /// <summary>
        /// Test method for Document ID.
        /// </summary>
        [Test]
        public void TestDocumentId()
        {
            long inputId = 2;
            nonHpfDocument.Id = inputId;
            long outputId = nonHpfDocument.Id;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test method for Patient ID.
        /// </summary>
        [Test]
        public void TestPatientId()
        {
            long inputPatientId = 121;
            nonHpfDocument.PatientId = inputPatientId;
            long outputPatientId = nonHpfDocument.PatientId;
            Assert.AreEqual(inputPatientId, outputPatientId);
        }

        /// <summary>
        /// Test method for Document Type.
        /// </summary>
        [Test]
        public void TestDocumentType()
        {
            string inputName = "History";
            nonHpfDocument.DocType = inputName;
            string outputName = nonHpfDocument.DocType;
            Assert.AreEqual(inputName, outputName);
            Assert.AreEqual(inputName, nonHpfDocument.Name);
        }

        /// <summary>
        /// Test method for Subtitle
        /// </summary>
        [Test]
        public void TestDocumentSubtitle()
        {
            string inputName = "History";
            nonHpfDocument.Subtitle = inputName;
            string outputName = nonHpfDocument.Subtitle;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test method for PageCount
        /// </summary>
        [Test]
        public void TestDocumentPageCount()
        {
            int pageCount = 3;
            nonHpfDocument.PageCount = pageCount;
            Assert.AreEqual(pageCount, nonHpfDocument.PageCount);
        }

        /// <summary>
        /// Test method for Document Encounter.
        /// </summary>
        [Test]
        public void TestDocumentEncounter()
        {
            string inputEncounter = "ENC1001";
            nonHpfDocument.Encounter = inputEncounter;
            string outputEncounter = nonHpfDocument.Encounter;
            Assert.AreEqual(inputEncounter, outputEncounter);
        }

        /// <summary>
        /// Test method for Document Data of Service.
        /// </summary>
        [Test]
        public void TestDocumentDateOfService()
        {
            DateTime inputDateTime = DateTime.Now;
            nonHpfDocument.DateOfService = inputDateTime;
            DateTime outputDateTime = nonHpfDocument.DateOfService.Value;
            Assert.AreEqual(inputDateTime, outputDateTime);
        }

        /// <summary>
        /// Test method for Department.
        /// </summary>
        [Test]
        public void TestDocumentDepartment()
        {
            string inputDepartment = "Dept1";
            nonHpfDocument.Department = inputDepartment;
            string outputDepartment = nonHpfDocument.Department;
            Assert.AreEqual(inputDepartment, outputDepartment);
        }

        /// <summary>
        /// Test method for Facility.
        /// </summary>
        [Test]
        public void TestDocumentFacility()
        {
            string inputFacility = "Facility1";
            nonHpfDocument.FacilityCode = inputFacility;
            string outputFacility = nonHpfDocument.FacilityCode;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test method for Comment.
        /// </summary>
        [Test]
        public void TestDocumentComment()
        {
            string inputComment = "Comment1";
            nonHpfDocument.Comment = inputComment;
            string outputComment = nonHpfDocument.Comment;
            Assert.AreEqual(inputComment, outputComment);
        }

        /// <summary>
        /// Test method for GetHashCode with ID = 0.
        /// </summary>
        [Test]
        public void TestGetHashCodeWithoutID()
        {
            Assert.IsInstanceOfType(typeof(int), nonHpfDocument.GetHashCode());
        }

        /// <summary>
        /// Test method for GetHashCode with ID != 0.
        /// </summary>
        [Test]
        public void TestGetHashCodeWithID()
        {
            nonHpfDocument.Id = 23;
            Assert.IsInstanceOfType(typeof(int), nonHpfDocument.GetHashCode());
        }

        /// <summary>
        /// Test method for GetHashCode with ID = 0.
        /// </summary>
        [Test]
        public void TestEqualWithOutID()
        {
            NonHpfDocumentDetails docDetails = new NonHpfDocumentDetails();
            nonHpfDocument.Equals(docDetails);
        }

        /// <summary>
        /// Test method for GetHashCode with ID != 0.
        /// </summary>
        [Test]
        public void TestEqualWithID()
        {
            NonHpfDocumentDetails docDetails = new NonHpfDocumentDetails();
            nonHpfDocument.Id = 23;
            nonHpfDocument.Equals(docDetails);
        }

        /// <summary>
        /// Test method for PatientFacility .
        /// </summary>
        [Test]
        public void TestPatientFacility()
        {
            string inputName = "IE";
            nonHpfDocument.PatientFacility = inputName;
            string outputName = nonHpfDocument.PatientFacility;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test method for Non-HPF FacilityType.
        /// </summary>
        [Test]
        public void TestPatientFacilityType()
        {
            FacilityType inputFacilityType = FacilityType.NonHpf;
            nonHpfDocument.FacilityType = inputFacilityType;
            FacilityType outputFacilityType = nonHpfDocument.FacilityType;
            Assert.AreEqual(inputFacilityType, outputFacilityType);
        }

    
        /// <summary>
        /// Test method for Is Free Form Facility.
        /// </summary>
        [Test]
        public void TestIsFreeformFacility()
        {
            bool inputIsFreeFrom = true;
            nonHpfDocument.IsFreeformFacility = inputIsFreeFrom;
            bool outputFreeForm = nonHpfDocument.IsFreeformFacility;
            Assert.AreEqual(inputIsFreeFrom, outputFreeForm);
        }

        /// <summary>
        /// Test method for patient mrn.
        /// </summary>
        [Test]
        public void TestMrn()
        {
            string inputName = "123";
            nonHpfDocument.PatientMrn = inputName;
            string outputName = nonHpfDocument.PatientMrn;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test method for patient key.
        /// </summary>
        [Test]
        public void TestPatientKey()
        {
            string inputName = "123";
            nonHpfDocument.PatientKey = inputName;
            string outputName = nonHpfDocument.PatientKey;
            Assert.AreEqual(inputName, outputName);
        }

        [Test]
        public void TestSorter()
        {
            NonHpfDocumentDetails obj = new NonHpfDocumentDetails();
            obj.DocType = "History & Physical";
            obj.Id = 1;
            obj.Encounter = "ENC1001";
            obj.DateOfService = DateTime.Now;
            
            NonHpfDocumentDetails obj2 = new NonHpfDocumentDetails();
            obj2.DocType = "001 Cold DataVault";
            obj2.Id = 2;
            obj2.Encounter = "ENC1002";
            obj2.DateOfService = DateTime.Now.AddMonths(-1);

            int result = NonHpfDocumentDetails.Sorter.Compare(obj.Key, obj2.Key);
            Assert.AreEqual(1, result);
        }

        /// <summary>
        /// Test method for Key.
        /// </summary>
        [Test]
        public void TestCompareProperty()
        {
            nonHpfDocument.DateOfService = DateTime.Now.Date;
            Assert.IsNotNull(nonHpfDocument.CompareProperty);
        }

        /// <summary>
        /// Test method for icon
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), nonHpfDocument.Icon);
        }

        /// <summary>
        /// Test case for document Released
        /// </summary>
        [Test]
        public void TestIsReleased()
        {
            nonHpfDocument.IsReleased = true;
            bool inputIsReleased = nonHpfDocument.IsReleased;
            Assert.AreEqual(inputIsReleased, nonHpfDocument.IsReleased);
        }

        /// <summary>
        /// Test case for Method Normalize
        /// </summary>
        [Test]
        public void TestNormalize()
        {
            NonHpfDocumentDetails nonHpfDocumentDetails = new NonHpfDocumentDetails();
            nonHpfDocumentDetails.Encounter = "E1234";
            nonHpfDocumentDetails.DocType = "Cold Data Vault";
            nonHpfDocumentDetails.Department = "Lab";
            nonHpfDocumentDetails.FacilityCode = "AD";
            nonHpfDocumentDetails.Comment = "Test";
            nonHpfDocumentDetails.Subtitle = "Test";
            nonHpfDocumentDetails.Normalize();
        }

        /// <summary>
        /// Test method for Document Sequence.
        /// </summary>
        [Test]
        public void TestDocumentSeq()
        {
            long inputDocumentSeq = 2;
            nonHpfDocument.DocumentSeq = inputDocumentSeq;
            long outputDocumentSeq = nonHpfDocument.DocumentSeq;
            Assert.AreEqual(inputDocumentSeq, outputDocumentSeq);
        }

        /// <summary>
        /// Test method for IsPatient Free Form Facility.
        /// </summary>
        [Test]
        public void TestIsPatinetFreeformFacility()
        {
            bool inputIsPatientFreeFormFacility = true;
            nonHpfDocument.IsPatientFreeFormFacility = inputIsPatientFreeFormFacility;
            bool outputIsPatientFreeFormFacility = nonHpfDocument.IsFreeformFacility;
            Assert.AreEqual(inputIsPatientFreeFormFacility, outputIsPatientFreeFormFacility);
        }

        /// <summary>
        /// Test method for Mode.
        /// </summary>
        [Test]
        public void TestMode()
        {
            Mode inputMode = Mode.Create;
            nonHpfDocument.Mode = inputMode;
            Mode outputMode = nonHpfDocument.Mode;
            Assert.AreEqual(inputMode, outputMode);
        }
    }
}
