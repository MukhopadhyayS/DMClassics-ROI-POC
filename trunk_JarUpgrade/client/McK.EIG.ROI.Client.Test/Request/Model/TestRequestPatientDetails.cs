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
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test Class for Request patient Details.
    /// </summary>
    [TestFixture]
    public class TestRequestPatientDetails
    {
        private RequestPatientDetails requestPatientDetails;

        [SetUp]
        public void Initialize()
        {
            requestPatientDetails = new RequestPatientDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestPatientDetails = null;
        }

        #region TestMethods

        /// <summary>
        /// Test Case for Id.
        /// </summary>
        [Test]
        public void TestRequestPatientDetailsId()
        {
            long inputId = 99;
            requestPatientDetails.Id = inputId;
            long outputId = requestPatientDetails.Id;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test Case for Name.
        /// </summary>
        [Test]
        public void TestName()
        {
            string inputName = "John Smith";
            requestPatientDetails.FullName = inputName;
            string outputName = requestPatientDetails.FullName;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test Case for Gender.
        /// </summary>
        [Test]
        public void TestGender()
        {
            string inputGender = "Male";
            requestPatientDetails.Gender = inputGender;
            string outputGender = requestPatientDetails.Gender;
            Assert.AreEqual(inputGender, outputGender);
        }

        /// <summary>
        /// Test Case for Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string inputFacility = "Test";
            requestPatientDetails.FacilityCode = inputFacility;
            string outputFacility = requestPatientDetails.FacilityCode;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test Case for EPN.
        /// </summary>
        [Test]
        public void TestEpn()
        {
            string inputEpn = "EPN123456";
            requestPatientDetails.EPN = inputEpn;
            string outputEpn = requestPatientDetails.EPN;
            Assert.AreEqual(inputEpn, outputEpn);
        }

        /// <summary>
        /// Test Case for SSN
        /// </summary>
        [Test]
        public void TestSsn()
        {
            string inputSsn = "3454535";
            requestPatientDetails.SSN = inputSsn;
            string outputSsn = requestPatientDetails.SSN;
            Assert.AreEqual(inputSsn, outputSsn);
        }

        /// <summary>
        /// Test Case for MRN
        /// </summary>
        [Test]
        public void TestMrn()
        {
            string inputMrn = "234343";
            requestPatientDetails.MRN = inputMrn;
            string outputMrn = requestPatientDetails.MRN;
            Assert.AreEqual(inputMrn, outputMrn);
        }

        /// <summary>
        /// Test Case for IsHpf
        /// </summary>
        [Test]
        public void TestPatientIsHpf()
        {
            requestPatientDetails.IsHpf = true;
            Assert.IsTrue(requestPatientDetails.IsHpf);
        }

        /// <summary>
        /// Test Case for IsVip
        /// </summary>
        [Test]
        public void TestpaatientIsVip()
        {
            requestPatientDetails.IsVip = true;
            Assert.IsTrue(requestPatientDetails.IsVip);
        }     

        /// <summary>
        /// Test Case for Global Document.
        /// </summary>
        [Test]
        public void TestGlobalDocument()
        {
            requestPatientDetails.GlobalDocument = new RequestGlobalDocuments();
            Assert.IsInstanceOfType(typeof(RequestGlobalDocuments), requestPatientDetails.GlobalDocument);
        }

        /// <summary>
        /// Test Case for Non Hpf Document
        /// </summary>
        [Test]
        public void TestNonHpfDocument()
        {
            requestPatientDetails.NonHpfDocument = new RequestNonHpfDocuments();
            Assert.IsInstanceOfType(typeof(RequestNonHpfDocuments), requestPatientDetails.NonHpfDocument);
        }

        /// <summary>
        /// Test case for IsLocked Patient.
        /// </summary>
        [Test]
        public void TestPatientIsLocked()
        {
            bool inputLocked = true;
            requestPatientDetails.IsLockedPatient = inputLocked;
            bool outputLocked = requestPatientDetails.IsLockedPatient;
            Assert.AreEqual(inputLocked, outputLocked);
        }

        /// <summary>
        /// Test case for Has documents
        /// </summary>
        [Test]
        public void TestHasDocuments()
        {
            RequestDocumentDetails globalDoc = new RequestDocumentDetails();
            globalDoc.DocType = "Global doc";
            globalDoc.DocTypeId = 1;
            requestPatientDetails.GlobalDocument.AddChild(globalDoc);
            Assert.IsTrue(requestPatientDetails.HasDocuments);
        }

        /// <summary>
        /// Test Case for Name.
        /// </summary>
        [Test]
        public void TestBaseName()
        {
            requestPatientDetails.FullName = "John Smith";
            Assert.AreEqual(requestPatientDetails.FullName, requestPatientDetails.Name);
        }

        /// <summary>
        /// Test method for Patient FacilityType.
        /// </summary>
        [Test]
        public void TestPatientFacilityType()
        {
            FacilityType inputFacilityType = FacilityType.NonHpf;
            requestPatientDetails.FacilityType = inputFacilityType;
            FacilityType outputFacilityType = requestPatientDetails.FacilityType;
            Assert.AreEqual(inputFacilityType, outputFacilityType);
        }

        /// <summary>
        /// Test method for Patient FacilityType.
        /// </summary>
        [Test]
        public void TestIsFreeformFacility()
        {
            bool inputIsFreeFrom = true;
            requestPatientDetails.IsFreeformFacility = inputIsFreeFrom;
            bool outputFreeForm = requestPatientDetails.IsFreeformFacility;
            Assert.AreEqual(inputIsFreeFrom, outputFreeForm);
        }


        /// <summary>
        /// Test case for Paramaterized constructor.
        /// </summary>
        [Test]
        public void TestParamaterizedConstructor()
        {
            PatientDetails recordPatient = new PatientDetails();
            recordPatient.Id = 9;
            recordPatient.FullName = "John";
            recordPatient.Gender = Gender.Male;
            recordPatient.SSN = "21313";
            recordPatient.EPN = "123213213";
            RequestPatientDetails requestPatientDetails = new RequestPatientDetails(recordPatient);
            requestPatientDetails.Id = recordPatient.Id;
            requestPatientDetails.FullName = recordPatient.FullName;
            requestPatientDetails.Gender = recordPatient.Gender.ToString();
            requestPatientDetails.MRN = recordPatient.SSN;
            requestPatientDetails.EPN = recordPatient.EPN;
            Assert.AreEqual(requestPatientDetails.Id, recordPatient.Id);
        }

        /// <summary>
        /// Test method for PatientDetail Data of Birth.
        /// </summary>
        [Test]
        public void TestPatientDetailDOB()
        {
            Nullable<DateTime> inputDateTime = DateTime.Now;
            requestPatientDetails.DOB = inputDateTime;
            Nullable<DateTime> outputDateTime = requestPatientDetails.DOB;
            Assert.AreEqual(inputDateTime, outputDateTime.Value);
            Assert.AreEqual(outputDateTime.Value.ToShortDateString(), requestPatientDetails.FormattedDOB);
        }

        [Test]
        public void TestPatientDetailFormattedDOB()
        {
            Assert.IsNull(requestPatientDetails.FormattedDOB);
        }

        [Test]
        public void TestPatientDetailGetHashCode()
        {
            Assert.IsInstanceOfType(typeof(int), requestPatientDetails.GetHashCode());
        }

        [Test]
        public void TestEqualsWithSameobject()
        {
            Assert.IsTrue(requestPatientDetails.Equals(requestPatientDetails));
        }

        [Test]
        public void TestEquals()
        {
            RequestPatientDetails details = new RequestPatientDetails();
            Assert.IsTrue(requestPatientDetails.Equals(details));
        }

        /// <summary>
        /// Test case for HasLockedEncounter
        /// </summary>
        [Test]
        public void TestEncounterLocked()
        {
            bool hasLockedEncounter = true;
            requestPatientDetails.EncounterLocked = hasLockedEncounter;
            Assert.IsTrue(requestPatientDetails.EncounterLocked);            
        }

        #endregion
    }
}
