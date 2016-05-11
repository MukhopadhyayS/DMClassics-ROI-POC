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
    /// Test class for NonHpfEncounterDetails
    /// </summary>
    [TestFixture]
    public class TestNonHpfEncounterDetails
    {
        private NonHpfEncounterDetails nonHpfEncounter;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            nonHpfEncounter = new NonHpfEncounterDetails();
        }

        /// <summary>
        /// Dispose NonHpfEncounterDetails.
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
        
        /// <summary>
        /// Test method for document with no emcounter
        /// </summary>
        [Test]
        public void TestNoEncounter()
        {
            Assert.AreEqual(NonHpfEncounterDetails.NotApplicable, nonHpfEncounter.Name);
        }
       
        /// <summary>
        /// Test method for Key.
        /// </summary>
        [Test]
        public void TestCompareProperty()
        {
            nonHpfEncounter.Encounter = "Enc001";
            Assert.IsNotNull(nonHpfEncounter.CompareProperty);
        }

        /// <summary>
        /// Test method for icon
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), nonHpfEncounter.Icon);
        }

        [Test]
        public void TestSorter()
        {
            NonHpfEncounterDetails obj = new NonHpfEncounterDetails();
            obj.Facility = "AWL";
            obj.Encounter = "ENC1001";
            obj.DateOfService = DateTime.Now;
            obj.Department = "Physician";

            NonHpfEncounterDetails obj2 = new NonHpfEncounterDetails();
            obj2.Facility = "AWL";
            obj2.Encounter = "ENC1002";
            obj2.DateOfService = DateTime.Now.AddMonths(-1);
            obj2.Department = "Physician";

            int result = NonHpfDocumentDetails.Sorter.Compare(obj.Key, obj2.Key);
            Assert.AreEqual(1, result);
        }
    }
}
