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
    /// Test Class for Request Encounter Details.
    /// </summary>
    [TestFixture]
    public class TestRequestEncounterDetails
    {
        private RequestEncounterDetails requestEncounterDetails;

        [SetUp]
        public void Initialize()
        {
            requestEncounterDetails = new RequestEncounterDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestEncounterDetails = null;
        }

        /// <summary>
        /// Test Case for Encounter Id.
        /// </summary>
        [Test]
        public void TestEncounterId()
        {
            string inputEncounterId = "A12344";
            requestEncounterDetails.EncounterId = inputEncounterId;
            string outputEncounterId = requestEncounterDetails.EncounterId;
            Assert.AreEqual(inputEncounterId, outputEncounterId);
        }

        /// <summary>
        /// Test Case for Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string inputFacility = "Test";
            requestEncounterDetails.Facility = inputFacility;
            string outputFacility = requestEncounterDetails.Facility;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test Case for Patient Type.
        /// </summary>
        [Test]
        public void TestPatientType()
        {
            string inputPatientType = "HPFPatient";
            requestEncounterDetails.PatientType = inputPatientType;
            string outputPatientType = requestEncounterDetails.PatientType;
            Assert.AreEqual(inputPatientType, outputPatientType);
        }

        /// <summary>
        /// Test Case for Patient service.
        /// </summary>
        [Test]
        public void TestPatientService()
        {
            string inputPatientService = "HPFPatient";
            requestEncounterDetails.PatientService = inputPatientService;
            string outputPatientService = requestEncounterDetails.PatientService;
            Assert.AreEqual(inputPatientService, outputPatientService);
        }

        /// <summary>
        /// Test Case for Patient Date of Service.
        /// </summary>
        [Test]
        public void TestAdmitDate()
        {
            Nullable<DateTime> inputDateOfService = DateTime.Now;
            requestEncounterDetails.AdmitDate = inputDateOfService;
            Nullable<DateTime> outputDateOfService = requestEncounterDetails.AdmitDate;
            Assert.AreEqual(inputDateOfService, outputDateOfService.Value);
        }

        /// <summary>
        /// Test Case for IsDeficiency.
        /// </summary>
        [Test]
        public void TestHasDeficiency()
        {
            bool inputIsDeficiency = true;
            requestEncounterDetails.HasDeficiency = inputIsDeficiency;
            bool outputIsDeficiency = requestEncounterDetails.HasDeficiency;
            Assert.AreEqual(inputIsDeficiency, outputIsDeficiency);
        }

        /// <summary>
        /// Test Case for IsVip.
        /// </summary>
        [Test]
        public void TestIsVip()
        {
            bool inputIsVip = false;
            requestEncounterDetails.IsVip = inputIsVip;
            bool outputIsVip = requestEncounterDetails.IsVip;
            Assert.AreEqual(inputIsVip, outputIsVip);
        }

        /// <summary>
        /// Test Case for Created Date.
        /// </summary>
        [Test]
        public void TestCreatedDate()
        {
            requestEncounterDetails.DischargeDate = DateTime.Now;
            Assert.IsTrue(requestEncounterDetails.DischargeDate.HasValue);
        }

        /// <summary>
        /// Test Case for IsLocked.
        /// </summary>
        [Test]
        public void TestIsLocked()
        {

            bool inputIsLocked = false;
            requestEncounterDetails.IsLocked = inputIsLocked;
            Assert.IsFalse(requestEncounterDetails.IsLocked);
        }

        /// <summary>
        /// Test case for parameterized constructor
        /// </summary>
        [Test]
        public void TestParamaterizedConstructor()
        {
            EncounterDetails recordEncounter = new EncounterDetails();
            recordEncounter.EncounterId = "1";
            recordEncounter.Facility = "XXX";
            recordEncounter.PatientType = "Patient";
            recordEncounter.AdmitDate = DateTime.Now;
            recordEncounter.HasDeficiency = true;
            recordEncounter.SelfPayEncounterID = "1";
            recordEncounter.IsSelfPay = true;
            RequestEncounterDetails encounterDetails = new RequestEncounterDetails(recordEncounter);
            encounterDetails.EncounterId = recordEncounter.EncounterId;
            encounterDetails.Facility = recordEncounter.Facility;
            encounterDetails.PatientType = recordEncounter.PatientType;
            encounterDetails.AdmitDate = recordEncounter.AdmitDate;
            encounterDetails.HasDeficiency = recordEncounter.HasDeficiency;
            encounterDetails.SelfPayEncounterID = recordEncounter.SelfPayEncounterID;
            encounterDetails.IsSelfPay = recordEncounter.IsSelfPay;
            Assert.AreEqual(recordEncounter.EncounterId, encounterDetails.EncounterId);
        }

        /// <summary>
        /// Test Case for Parent.
        /// </summary>
        [Test]
        public void TestParent()
        {
            BaseRequestItem record1 = new RequestEncounterDetails();
            requestEncounterDetails.Parent = record1;
            BaseRequestItem record2 = requestEncounterDetails.Parent;
            Assert.AreEqual(record1, record2);
        }

        /// <summary>
        /// Test Case for Record VersionId.
        /// </summary>
        [Test]
        public void TestRecordVersion()
        {
            int recordVersionId = 1;
            requestEncounterDetails.RecordVersionId = recordVersionId;
            int recordVersion = 2;
            Assert.AreNotEqual(requestEncounterDetails.RecordVersionId, recordVersion);
        }

        /// <summary>
        /// Test Case for Name.
        /// </summary>
        [Test]
        public void TestName()
        {
            requestEncounterDetails.EncounterId = "123465";
            requestEncounterDetails.SelfPayEncounterID = "12356";
            string name1 = requestEncounterDetails.Name;
            requestEncounterDetails.IsSelfPay = true;
            string name2 = requestEncounterDetails.Name;

            Assert.AreNotEqual(name1, name2);
        }

        /// <summary>
        /// Test Case for Key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            requestEncounterDetails.EncounterId = "ROI.1.0.2";
            requestEncounterDetails.SelfPayEncounterID = "ROI.1.0.2(selfpay)";
            string key1 = requestEncounterDetails.Key;
            requestEncounterDetails.IsSelfPay = true;
            string key2 = requestEncounterDetails.Key;
            Assert.AreNotEqual(key1, key2);
        }

        /// <summary>
        /// Test Case for ReleasedItems.
        /// </summary>
        [Test]
        public void TestReleasedItems()
        {
            Assert.NotNull(requestEncounterDetails.ReleasedItems);
        }

        /// <summary>
        /// Test Case for patient sequence
        /// </summary>
        [Test]
        public void TestPatientSequence()
        {
            long inputPatientSeq = 1;
            requestEncounterDetails.PatientSeq = inputPatientSeq;
            long outputPatientSeq = requestEncounterDetails.PatientSeq;
            Assert.AreEqual(inputPatientSeq, outputPatientSeq);
        }

        /// <summary>
        /// Test Case for encounter sequence
        /// </summary>
        [Test]
        public void TestEncounterSequence()
        {
            long inputEncounterSeq = 1;
            requestEncounterDetails.EncounterSeq = inputEncounterSeq;
            long outputEncounterSeq = requestEncounterDetails.EncounterSeq;
            Assert.AreEqual(inputEncounterSeq, outputEncounterSeq);
        }

        /// <summary>
        /// Test Case for Encounter Id.
        /// </summary>
        [Test]
        public void TestEncounterIdWithSelfPay()
        {
            string inputEncounterIdWithSelfPay = "A12344";
            requestEncounterDetails.SelfPayEncounterID = inputEncounterIdWithSelfPay;
            string outputEncounterIdWithSelfPay = requestEncounterDetails.SelfPayEncounterID;
            Assert.AreEqual(inputEncounterIdWithSelfPay, outputEncounterIdWithSelfPay);
        }

        /// <summary>
        /// Test Case for IsSelfPay.
        /// </summary>
        [Test]
        public void TestIsSelfPay()
        {
            bool inputIsSelfPay = false;
            requestEncounterDetails.IsSelfPay = inputIsSelfPay;
            bool outputIsSelfPay = requestEncounterDetails.IsSelfPay;
            Assert.AreEqual(inputIsSelfPay, outputIsSelfPay);
        }

        /// <summary>
        /// Test Case for Icon.
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), requestEncounterDetails.Icon);
        }
    }
}
