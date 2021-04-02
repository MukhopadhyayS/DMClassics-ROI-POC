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
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for RequestNonHpfEncounterDetails
    /// </summary>
    [TestFixture]
    public class TestRequestNonHpfEncounterDetails
    {
        private RequestNonHpfEncounterDetails nonHpfEncounter, nonHpfEncounterWithParameter;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            nonHpfEncounter = new RequestNonHpfEncounterDetails();
            
            NonHpfEncounterDetails nonHpfEncounterDetails = new NonHpfEncounterDetails();
            nonHpfEncounterDetails.Encounter = "tst";
            nonHpfEncounterDetails.Facility = "AD";
            nonHpfEncounterDetails.Department = "dept";
            nonHpfEncounterDetails.DateOfService = DateTime.Now;
            nonHpfEncounterWithParameter = new RequestNonHpfEncounterDetails(nonHpfEncounterDetails); 
        }

        /// <summary>
        /// Dispose RequestNonHpfEncounterDetails.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            nonHpfEncounter = null;
        }

        /// <summary>
        /// Test method for PageCount
        /// </summary>
        [Test]
        public void TestDocumentPageCount()
        {
            int pageCount = 3;
            nonHpfEncounter.PageCount = pageCount;
            Assert.AreEqual(pageCount, nonHpfEncounter.PageCount);
        }

        /// <summary>
        /// Test method for Encounter.
        /// </summary>
        [Test]
        public void TestEncounter()
        {
            string inputEncounter = "ENC1001";
            nonHpfEncounter.Encounter = inputEncounter;
            string outputEncounter = nonHpfEncounter.Encounter;
            Assert.AreEqual(inputEncounter, outputEncounter);
        }

        /// <summary>
        /// Test method for Data of Service.
        /// </summary>
        [Test]
        public void TestDateOfService()
        {
            DateTime inputDateTime = DateTime.Now;
            nonHpfEncounter.DateOfService = inputDateTime;
            DateTime outputDateTime = nonHpfEncounter.DateOfService.Value;
            Assert.AreEqual(inputDateTime, outputDateTime);
        }

        /// <summary>
        /// Test method for Department.
        /// </summary>
        [Test]
        public void TestDepartment()
        {
            string inputDepartment = "Dept1";
            nonHpfEncounter.Department = inputDepartment;
            string outputDepartment = nonHpfEncounter.Department;
            Assert.AreEqual(inputDepartment, outputDepartment);
        }

        /// <summary>
        /// Test method for Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string inputFacility = "Facility1";
            nonHpfEncounter.Facility = inputFacility;
            string outputFacility = nonHpfEncounter.Facility;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        [Test]
        public void TestName()
        {
            RequestNonHpfEncounterDetails encounter = new RequestNonHpfEncounterDetails();
            Assert.AreEqual(NonHpfEncounterDetails.NotApplicable, encounter.Name);
        }

        /// <summary>
        /// Test Case for Icon.
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), nonHpfEncounter.Icon);
        }

    }
}
