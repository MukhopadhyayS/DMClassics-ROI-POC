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
using System.Collections.ObjectModel;
using System.Configuration;
using System.Globalization;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Test.Requestors.Controller;

namespace McK.EIG.ROI.Client.Test.Patient.Controller
{
    /// <summary>
    /// Test Case for Requestor Matching Controller.
    /// </summary>
    [TestFixture]
    [Category("TestRequestorMatchingController")]
    public class TestRequestorMatchingController : TestBase
    {
        #region Fields

        private PatientController patientController;

        #endregion

        #region Constructor

        public TestRequestorMatchingController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
            
        }

        #endregion

        #region NUnit

        [SetUp]
        public void Init()
        {
            patientController = PatientController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            patientController = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Test case for retrieve matching requestors with valid input.
        /// </summary>
        [Test]
        public void Test01RetrieveMatchingRequestorsValidInput()
        {
            Collection<PatientDetails> patients = new Collection<PatientDetails>();
            PatientDetails patient = new PatientDetails();

            TestRequestorController requestorController = new TestRequestorController();
            requestorController.LogOff();
            
            //requestorController.Initlogon();
            requestorController.LogOn();
            requestorController.Test01CreateRequestor();
            requestorController.Dispose();

            patient.LastName = requestorController.RequestorDetails.LastName;
            patient.FirstName = requestorController.RequestorDetails.FirstName;
            patient.FullName = patient.LastName + ", " + patient.FirstName;
            patient.SSN = requestorController.RequestorDetails.PatientSSN;
            patient.DOB = requestorController.RequestorDetails.PatientDob;

            patients.Add(patient);

            Assert.IsTrue(patientController.RetrieveMatchingRequestors(patients).Count > 0 );
        }

        /// <summary>
        /// Test Case for retrieve requestors with invalid input.
        /// </summary>
        [Test]
        public void Test02RetrieveMatchingRequestorsInvalidInput()
        {
            int count = 0;
            Collection<PatientDetails> patients = new Collection<PatientDetails>();
            PatientDetails patient = new PatientDetails();
            patient.LastName = "lasttest" + GetUniqueId();
            patient.FirstName = "firsttest" + GetUniqueId();
            patient.FullName = patient.LastName + ", " + patient.FirstName;
            patients.Add(patient);

            Collection<RequestorDetails> requestors = patientController.RetrieveMatchingRequestors(patients);
            int outputCount = requestors.Count;
            Assert.AreEqual(outputCount, count);
        }

        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString();
        }

        #endregion
    }
}
