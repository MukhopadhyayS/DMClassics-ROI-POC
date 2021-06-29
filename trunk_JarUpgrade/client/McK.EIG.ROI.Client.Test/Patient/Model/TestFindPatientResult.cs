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
using McK.EIG.ROI.Client.Patient.Model;
using System.Collections.ObjectModel;
using System.Collections.Generic;

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for FindPatientResult
    /// </summary>
    [TestFixture]
    public class TestFindPatientResult
    {
        private FindPatientResult patientResult;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            patientResult = new FindPatientResult();
        }

        /// <summary>
        /// Dispose FindPatientCriteria.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            patientResult = null;
        }

        /// <summary>
        /// Test method for FindPatientCriteria SSN.
        /// </summary>
        [Test]
        public void TestPatientResultMaxCountExceeded()
        {
            bool input = true;
            patientResult.MaxCountExceeded = input;
            bool output = patientResult.MaxCountExceeded;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test method for FindPatientCriteria EPN.
        /// </summary>
        [Test]
        public void TestPatientResult()
        {
            //List<PatientDetails> details = patientResult.PatientSearchResult;
            //Assert.IsInstanceOfType(typeof(List<PatientDetails>), details);
        }
    }
}
