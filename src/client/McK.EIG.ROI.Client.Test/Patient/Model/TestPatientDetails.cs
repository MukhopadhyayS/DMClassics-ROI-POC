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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Drawing;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    [TestFixture]
    public class TestPatientDeatails
    {
        private PatientDetails patientDetail;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            patientDetail = new PatientDetails();
        }

        /// <summary>
        /// Dispose PatientDetail.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            patientDetail = null;
        }

        /// <summary>
        /// Test method for Patient ID.
        /// </summary>
        [Test]
        public void TestPatientID()
        {
            long inputId = 121;
            patientDetail.Id = inputId;
            long outputId = patientDetail.Id;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test method for Patient Name.
        /// </summary>
        [Test]
        public void TestPatientFirstName()
        {
            string inputName = "Smith";
            patientDetail.FullName = inputName;
            string outputName = patientDetail.Name;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test method for Patient Home Phone.
        /// </summary>
        [Test]
        public void TestPatientHomePhone()
        {
            string inputHomePhone = "453785";
            patientDetail.HomePhone = inputHomePhone;
            string outputHomePhone = patientDetail.HomePhone;
            Assert.AreEqual(inputHomePhone, outputHomePhone);
        }

        /// <summary>
        /// Test method for Patient Work Phone.
        /// </summary>
        [Test]
        public void TestPatientWorkPhone()
        {
            string inputWorkPhone = "445512";
            patientDetail.WorkPhone = inputWorkPhone;
            string outputWorkPhone = patientDetail.WorkPhone;
            Assert.AreEqual(inputWorkPhone, outputWorkPhone);
        }

        /// <summary>
        /// Test method for Patient IsVIP.
        /// </summary>
        [Test]
        public void TestPatientIsVip()
        {
            bool inputVipStatus = true;
            patientDetail.IsVip = inputVipStatus;
            bool outputVipStatus = patientDetail.IsVip;
            Assert.AreEqual(inputVipStatus, outputVipStatus);
        }

        /// <summary>
        /// Test method for Patient Gender.
        /// </summary>
        [Test]
        public void TestPatientGender()
        {
            patientDetail.Gender = Gender.Male;
            Gender outputGender = patientDetail.Gender;
            Assert.AreEqual(Gender.Male, outputGender);
        }       

        /// <summary>
        /// Test method for Patient SSN.
        /// </summary>
        [Test]
        public void TestPatientSSN()
        {
            string inputSSN = "4578";
            patientDetail.SSN = inputSSN;
            string outputSSN = patientDetail.SSN;
            Assert.AreEqual(inputSSN, outputSSN);
            Assert.IsNotNull(patientDetail.MaskedSSN);
        }

        /// <summary>
        /// Test method for Patient EPN.
        /// </summary>
        [Test]
        public void TestPatientEPN()
        {
            string inputEPN = "1234";
            patientDetail.EPN = inputEPN;
            string outputEPN = patientDetail.EPN;
            Assert.AreEqual(inputEPN, outputEPN);
        }

        /// <summary>
        /// Test method for Patient Facility.
        /// </summary>
        [Test]
        public void TestPatientFacility()
        {
            string inputFacility = "User Permission";
            patientDetail.FacilityCode = inputFacility;
            string outputFacility = patientDetail.FacilityCode;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test method for Patient FacilityType.
        /// </summary>
        [Test]
        public void TestPatientFacilityType()
        {
            FacilityType inputFacilityType = FacilityType.NonHpf;
            patientDetail.FacilityType = inputFacilityType;
            FacilityType outputFacilityType = patientDetail.FacilityType;
            Assert.AreEqual(inputFacilityType, outputFacilityType);
        }

        
        
        /// <summary>
        /// Test method for Patient FacilityType.
        /// </summary>
        [Test]
        public void TestIsFreeformFacility()
        {
            bool inputIsFreeFrom = true;
            patientDetail.IsFreeformFacility = inputIsFreeFrom;
            bool outputFreeForm = patientDetail.IsFreeformFacility;
            Assert.AreEqual(inputIsFreeFrom, outputFreeForm);
        }

        
        /// <summary>
        /// Test method for Patient MRN.
        /// </summary>
        [Test]
        public void TestPatientMRN()
        {
            string inputMRN = "7889";
            patientDetail.MRN = inputMRN;
            string outputMRN = patientDetail.MRN;
            Assert.AreEqual(inputMRN, outputMRN);
        }

        /// <summary>
        /// Test method for Patient IsHpf.
        /// </summary>
        [Test]
        public void TestPatientIsHpf()
        {
            bool inputHpfStatus = true;
            patientDetail.IsHpf = inputHpfStatus;
            bool outputHpfStatus = patientDetail.IsHpf;
            Assert.AreEqual(inputHpfStatus, outputHpfStatus);
        }

        /// <summary>
        /// Test method for Patient HasRequest.
        /// </summary>
        [Test]
        public void TestPatientHasRequests()
        {
            bool inputHasRequestsStatus = true;
            patientDetail.HasRequests = inputHasRequestsStatus;
            bool outputHasRequestsStatus = patientDetail.HasRequests;
            Assert.AreEqual(inputHasRequestsStatus, outputHasRequestsStatus);
        }

        /// <summary>
        /// Test method for Patient Address Detail.
        /// </summary>
        [Test]
        public void TestPatientAddressDetail()
        {
            patientDetail.Address = new AddressDetails();
            Assert.IsInstanceOfType(typeof(AddressDetails), patientDetail.Address);
        }

        /// <summary>
        /// Test Case for PatientDetailsSorter.
        /// </summary>
        [Test]
        public void TestPatientsSorter()
        {
            FindPatientResult result = new FindPatientResult();
            PatientDetails details = new PatientDetails();
            details.FullName = "Jhon";
            result.PatientSearchResult.Add(details);
            details = new PatientDetails();
            details.FullName = "Alan";
            result.PatientSearchResult.Add(details);
            details = new PatientDetails();
            details.FullName = "Rhods";
            result.PatientSearchResult.Add(details);
            List<PatientDetails> list = new List<PatientDetails>(result.PatientSearchResult);
            list.Sort(PatientDetails.Sorter);
            Assert.IsNotNull(PatientDetails.Sorter);
        }

        /// <summary>
        /// Test Case for Locked Image.
        /// </summary>
        [Test]
        public void TestPatientImage()
        {
            Assert.IsNull(patientDetail.LockedImage);
        }

        /// <summary>
        /// Test case for Vipimage
        /// </summary>
        [Test]
        public void TestVipImage()
        {
            Assert.IsNull(patientDetail.VipImage);
        }

        /// <summary>
        /// Test method for Patient Data of Birth.
        /// </summary>
        [Test]
        public void TestPatientDOB()
        {
            DateTime inputDateTime = DateTime.Now;
            patientDetail.DOB = inputDateTime;
            Nullable<DateTime> outputDateTime = patientDetail.DOB;
            Assert.AreEqual(inputDateTime, outputDateTime.Value);
            Assert.AreEqual(outputDateTime.Value.ToString(ROIConstants.DateFormat), patientDetail.FormattedDOB);
        }

        /// <summary>
        /// Test method for Patient Data of Birth with null.
        /// </summary>
        [Test]
        public void TestPatientDOBWithNull()
        {   
            patientDetail.DOB = null;            
            Assert.IsNull(patientDetail.FormattedDOB);
        }

        /// <summary>
        /// Test method Patient Locked.
        /// </summary>
        [Test]
        public void TestPatientLocked()
        {
            patientDetail.PatientLocked = true;
            Assert.AreEqual(true, patientDetail.PatientLocked);
        }

        /// <summary>
        /// Test method Patient Locked.
        /// </summary>
        [Test]
        public void TestEncounterLocked()
        {
            patientDetail.EncounterLocked = true;
            Assert.AreEqual(true, patientDetail.EncounterLocked);
        }

        /// <summary>
        /// Test method for TestEncounters.
        /// </summary>
        [Test]
        public void TestEncounters()
        {
            Collection<EncounterDetails> encounters = patientDetail.Encounters;
            Assert.IsInstanceOfType(typeof(Collection<EncounterDetails>), encounters);
        }

        /// <summary>
        /// Test method for TestGlobalDocuments.
        /// </summary>
        [Test]
        public void TestGlobalDocuments()
        {
            patientDetail.GlobalDocument = new PatientGlobalDocument();
            Assert.IsNotNull(patientDetail.GlobalDocument);
        }

        /// <summary>
        /// Test method for Equals.
        /// </summary>
        [Test]
        public void TestEquals()
        {
            PatientDetails details = new PatientDetails();
            details.Id   = 1;
            details.FullName = "Smith";
            details.SSN  = "123";
            patientDetail.Equals(details);
            PatientDetails details1 = new PatientDetails();
            details1.Equals(details1);
        }
        /// <summary>
        /// Test method for TestGlobalDocuments.
        /// </summary>
        [Test]
        public void TestGetHashCode()
        {            
            patientDetail.GetHashCode();
        }

        /// <summary>
        /// Test method for TestGlobalDocuments.
        /// </summary>
        [Test]
        public void TestNonHpfDocuments()
        {
            patientDetail.NonHpfDocuments = new PatientNonHpfDocument();
            Assert.IsNotNull(patientDetail.NonHpfDocuments);
        }        
        
        /// <summary>
        /// Test method for Key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            patientDetail.MRN = "123";
            patientDetail.FacilityCode = "Facility";
            patientDetail.IsHpf = true;
            Assert.AreEqual("123.Facility", patientDetail.Key);
        }

        /// <summary>
        /// Test method for compare Property.
        /// </summary>
        [Test]        
        public void TestCompareProperty()
        {
            Assert.IsNull(patientDetail.CompareProperty);
        }
    }
}
