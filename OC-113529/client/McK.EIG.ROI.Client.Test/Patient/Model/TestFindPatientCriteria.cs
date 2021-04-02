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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for FindPatientCriteria
    /// </summary>
    [TestFixture]
    public class TestFindPatientCriteria
    {
        private FindPatientCriteria patientCriteria;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            patientCriteria = new FindPatientCriteria();
        }

        /// <summary>
        /// Dispose FindPatientCriteria.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            patientCriteria = null;
        }

        /// <summary>
        /// Test method for FindPatientCriteria FirstName.
        /// </summary>
        [Test]
        public void TestFindPatientFirstName()
        {
            string inputFirstName = "Smith";
            patientCriteria.FirstName = inputFirstName;
            string outputFirstName = patientCriteria.FirstName;
            Assert.AreEqual(inputFirstName, outputFirstName);
        }

        /// <summary>
        /// Test method for FindPatientCriteria LastName.
        /// </summary>
        [Test]
        public void TestFindPatientLastName()
        {
            string inputLastName = "Jhon";
            patientCriteria.LastName = inputLastName;
            string outputLastName = patientCriteria.LastName;
            Assert.AreEqual(inputLastName, outputLastName);
        }
       
        /// <summary>
        /// Test method for FindPatientCriteria Data of Birth.
        /// </summary>
        [Test]
        public void TestFindPatientDOB()
        {
            DateTime now = DateTime.Now;
            patientCriteria.Dob = now;
            Assert.AreEqual(now, patientCriteria.Dob);
        }

        /// <summary>
        /// Test method for FindPatientCriteria SSN.
        /// </summary>
        [Test]
        public void TestFindPatientSSN()
        {
            string inputSSN = "4578";
            patientCriteria.SSN = inputSSN;
            string outputSSN = patientCriteria.SSN;
            Assert.AreEqual(inputSSN, outputSSN);
        }

        /// <summary>
        /// Test method for FindPatientCriteria EPN.
        /// </summary>
        [Test]
        public void TestFindPatientEPN()
        {
            string inputEPN = "1234";
            patientCriteria.EPN = inputEPN;
            string outputEPN = patientCriteria.EPN;
            Assert.AreEqual(inputEPN, outputEPN);
        }

        /// <summary>
        /// Test method for FindPatientCriteria MRN.
        /// </summary>
        [Test]
        public void TestFindPatientMRN()
        {
            string inputMRN = "7889";
            patientCriteria.MRN = inputMRN;
            string outputMRN = patientCriteria.MRN;
            Assert.AreEqual(inputMRN, outputMRN);
        }

        /// <summary>
        /// Test method for FindPatientCriteria Encounter.
        /// </summary>
        [Test]
        public void TestFindPatientEncounter()
        {
            string inputEncounter = "7861Patint1";
            patientCriteria.Encounter = inputEncounter;
            string outputEncounter = patientCriteria.Encounter;
            Assert.AreEqual(inputEncounter, outputEncounter);
        }

        /// <summary>
        /// Test method for FindPatientCriteria Facility.
        /// </summary>
        [Test]
        public void TestFindPatientFacility()
        {
            string inputFacility = "User Permission";
            patientCriteria.FacilityCode = inputFacility;
            string outputFacility = patientCriteria.FacilityCode;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test method for FindPatientCriteria MaxRecords.
        /// </summary>
        [Test]
        public void TestFindPatientMaxRecords()
        {
            int inputMaxRecords = 500;
            patientCriteria.MaxRecord = inputMaxRecords;
            int outputMaxRecords = patientCriteria.MaxRecord;
            Assert.AreEqual(inputMaxRecords, outputMaxRecords);
        }

        /// <summary>
        /// Test method for Facility Type.
        /// </summary>
        [Test]
        public void TestFacilityType()
        {
            FacilityType inputFaclilityType = FacilityType.Hpf;
            patientCriteria.FacilityType = inputFaclilityType;
            FacilityType outputFacilityType = patientCriteria.FacilityType;
            Assert.AreEqual(inputFaclilityType, outputFacilityType);
        }
    }
}
