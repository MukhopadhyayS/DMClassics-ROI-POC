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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{
    /// <summary>
    /// Test class for Find Requestor Criteria
    /// </summary>
    [TestFixture]
    public class TestFindRequestorCriteria
    {
        private FindRequestorCriteria findRequestorCriteria;

        [SetUp]
        public void Initialize()
        {
            findRequestorCriteria = new FindRequestorCriteria();
        }

        [TearDown]
        public void Dispose()
        {
            findRequestorCriteria = null;
        }

        #region TestMethods

        /// <summary>
        /// Test case for Filterby option (Whether it is AllRequestors or RecentRequestors)
        /// </summary>
        [Test]
        public void TestAllRequestors()
        {
            bool inputAllRequestors = true;
            findRequestorCriteria.AllRequestors = inputAllRequestors;
            bool outputAllRequestors = findRequestorCriteria.AllRequestors;
            Assert.AreEqual(inputAllRequestors, outputAllRequestors);
        }

      
        /// <summary>
        /// Test Case for Find Requestor Name.
        /// </summary>
        [Test]
        public void TestFindRequestorName()
        {
            string inputRequestorName = "JohnSmith";
            findRequestorCriteria.Name = inputRequestorName;
            string outputRequestorName = findRequestorCriteria.Name;
            Assert.AreEqual(inputRequestorName, outputRequestorName);
        }

        /// <summary>
        /// Test Case for PatientEPN.
        /// </summary>
        [Test]
        public void TestPatientEpn()
        {
            string inputPatientEpn = "xxx345";
            findRequestorCriteria.PatientEPN = inputPatientEpn;
            string outputPatientEpn = findRequestorCriteria.PatientEPN;
            Assert.AreEqual(inputPatientEpn, outputPatientEpn);
        }

        /// <summary>
        /// Test Case for PatientDob.
        /// </summary>
        [Test]
        public void TestPatientDob()
        {
            Nullable<DateTime> inputPatientDob = DateTime.Now;
            findRequestorCriteria.PatientDob = inputPatientDob;
            Nullable<DateTime> outputPatientDob = findRequestorCriteria.PatientDob;
            Assert.AreEqual(inputPatientDob, outputPatientDob);
        }

        /// <summary>
        /// Test Case for PatientSSN.
        /// </summary>
        [Test]
        public void TestPatientSsn()
        {
            string inputPatientSsn = "123-456-789";
            findRequestorCriteria.PatientSSN = inputPatientSsn;
            string outputPatientSsn = findRequestorCriteria.PatientSSN;
            Assert.AreEqual(inputPatientSsn, outputPatientSsn);
        }


        /// <summary>
        /// Test Case for PatientMRN.
        /// </summary>
        [Test]
        public void TestPatientMrn()
        {
            string inputPatientMrn = "MRN123456789";
            findRequestorCriteria.PatientMRN = inputPatientMrn;
            string outputPatientMrn = findRequestorCriteria.PatientMRN;
            Assert.AreEqual(inputPatientMrn, outputPatientMrn);
        }

        /// <summary>
        /// Test method for  PatientFacility.
        /// </summary>
        [Test]
        public void TestFindPatientFacility()
        {
            string inputFacility = "User Permission";
            findRequestorCriteria.PatientFacilityCode = inputFacility;
            string outputFacility = findRequestorCriteria.PatientFacilityCode;
            Assert.AreEqual(inputFacility, outputFacility);
        }


        /// <summary>
        /// Test Case for MaxRecords.
        /// </summary>
        [Test]
        public void TestMaxRecords()
        {
            int inputMaxRecords = 30;
            findRequestorCriteria.MaxRecord = inputMaxRecords;
            int outputMaxRecords = findRequestorCriteria.MaxRecord;
            Assert.AreEqual(inputMaxRecords, outputMaxRecords);
        }

        /// <summary>
        /// Test Case for RequestorType Id.
        /// </summary>
        [Test]
        public void TestRequestorTypeId()
        {
            long inputRequestorTypeId = 5;
            findRequestorCriteria.RequestorTypeId = inputRequestorTypeId;
            long ouputRequestorTypeId = findRequestorCriteria.RequestorTypeId;
            Assert.AreEqual(inputRequestorTypeId, ouputRequestorTypeId);
        }

        /// <summary>
        /// Test Case for Requestor Status.
        /// </summary>
        [Test]
        public void TestRequestorStatus()
        {
            bool inputStatus = true;
            findRequestorCriteria.ActiveRequestor = inputStatus;
            bool outputStatus = findRequestorCriteria.ActiveRequestor.Value;
            Assert.AreEqual(inputStatus, outputStatus);
        }

        /// <summary>
        /// Test Case for Requestor Status.
        /// </summary>
        [Test]
        public void TestIsFreeformFacility()
        {
            bool inputIsFreeformFacility = true;
            findRequestorCriteria.IsFreeformFacility = inputIsFreeformFacility;
            bool outputIsFreeformFacility = findRequestorCriteria.IsFreeformFacility;
            Assert.AreEqual(inputIsFreeformFacility, outputIsFreeformFacility);
        }

        #endregion
    }
}
