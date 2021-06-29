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
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for FindRequestCriteria 
    /// </summary>
    [TestFixture]
    public class TestFindRequestCriteria
    {
        #region Fields

        private FindRequestCriteria requestCriteria;
        private IFormatProvider theCultureInfo = new System.Globalization.CultureInfo("en-GB", true);

        #endregion

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            requestCriteria = new FindRequestCriteria();
        }

        [TearDown]
        public void Dispose()
        {
            requestCriteria = null;
        }

        #endregion

        #region Service Methods

        /// <summary>
        /// Test the PatientFirstName.
        /// </summary>
        [Test]
        public void TestPatientFirstName()
        {
            string inputData = "John";
            requestCriteria.PatientFirstName = inputData;
            string outputData = requestCriteria.PatientFirstName;
            Assert.AreEqual(inputData, outputData);
        }

        /// <summary>
        /// Test the PatientLastName.
        /// </summary>
        [Test]
        public void TestPatientLastName()
        {
            string inputData = "Michael";
            requestCriteria.PatientLastName = inputData;
            string outputData = requestCriteria.PatientLastName;
            Assert.AreEqual(inputData, outputData);
        }

        /// <summary>
        /// Test Method for Dob.
        /// </summary>
        [Test]
        public void TestDob()
        {
            DateTime inputDate = DateTime.Now;
            requestCriteria.Dob = inputDate;
            Assert.AreEqual(inputDate, requestCriteria.Dob.Value);
        }

        /// <summary>
        /// Test the PatientSsn.
        /// </summary>
        [Test]
        public void TestPatientSsn()
        {
            string inputData = "123";
            requestCriteria.PatientSsn = inputData;
            string outputData = requestCriteria.PatientSsn;
            Assert.AreEqual(inputData, outputData);
        }

        /// <summary>
        /// Test the EPN.
        /// </summary>
        [Test]
        public void TestEPN()
        {
            string inputData = "123";
            requestCriteria.EPN = inputData;
            string outputData = requestCriteria.EPN;
            Assert.AreEqual(inputData, outputData);
        }

        /// <summary>
        /// Test the Mrn.
        /// </summary>
        [Test]
        public void TestMrn()
        {
            string inputmrn = "123";
            requestCriteria.MRN = inputmrn;
            string outputmrn = requestCriteria.MRN;
            Assert.AreEqual(inputmrn, outputmrn);
        }
        /// <summary>
        /// Test the Encounter.
        /// </summary>
        [Test]
        public void TestEncounter()
        {
            string inputData = "123Encounter";
            requestCriteria.Encounter = inputData;
            string outputData = requestCriteria.Encounter;
            Assert.AreEqual(inputData, outputData);
        }

        /// <summary>
        /// Test the Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string input = "Facility";
            requestCriteria.Facility= input;
            string output = requestCriteria.Facility;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test the RequestStatus.
        /// </summary>
        [Test]
        public void TestRequestStatus()
        {
            string inputData = "Logged";
            requestCriteria.RequestStatus = inputData;
            string outputData = requestCriteria.RequestStatus;
            Assert.AreEqual(inputData, outputData);
        }

        /// <summary>
        /// Test the Status reason.
        /// </summary>
        [Test]
        public void TestStatusReason()
        {
            string inputData = "Revocation";
            requestCriteria.StatusReason = inputData;
            string outputData = requestCriteria.StatusReason;
            Assert.AreEqual(inputData, outputData);
        }

        /// <summary>
        /// Test the RequestorType.
        /// </summary>
        [Test]
        public void TestRequestorType()
        {
            long inputData = 123;
            requestCriteria.RequestorType = inputData;
            long outputData = requestCriteria.RequestorType;
            Assert.AreEqual(inputData, outputData);
        }

        /// <summary>
        /// Test the RequestorName.
        /// </summary>
        [Test]
        public void TestRequestorName()
        {
            string inputData = "ROI0000";
            requestCriteria.RequestorName = inputData;
            string outputData = requestCriteria.RequestorName;
            Assert.AreEqual(inputData, outputData);
        }

        /// <summary>
        /// Test Method for ReceiptDate.
        /// </summary>
        [Test]
        public void TestReceiptDate()
        {
            string inputDate = DateTime.Now.ToString();
            requestCriteria.ReceiptDate = inputDate;
            string outputdate = requestCriteria.ReceiptDate;
            Assert.AreEqual(inputDate, outputdate);
        }

        /// <summary>
        /// Test Method for FromDate.
        /// </summary>
        [Test]
        public void TestFromDate()
        {
            DateTime inputDate = DateTime.Now;
            requestCriteria.FromDate = inputDate;
            Assert.AreEqual(inputDate, requestCriteria.FromDate.Value);
        }

        /// <summary>
        /// Test Method for ToDate.
        /// </summary>
        [Test]
        public void TestToDate()
        {
            DateTime inputDate = DateTime.Now;
            requestCriteria.ToDate = inputDate;
            Assert.AreEqual(inputDate, requestCriteria.ToDate.Value);
        }

        /// <summary>
        /// Test the InvoiceNumber.
        /// </summary>
        [Test]
        public void TestInvoiceNumber()
        {
            string inputData = "123";
            requestCriteria.InvoiceNumber = inputData;
            string outputData = requestCriteria.InvoiceNumber;
            Assert.AreEqual(inputData, outputData);
        }

        /// <summary>
        /// Test the RequestId.
        /// </summary>
        [Test]
        public void TestRequestId()
        {
            string inputRequestId = "123";
            requestCriteria.RequestId = inputRequestId;
            string outputRequestId = requestCriteria.RequestId;
            Assert.AreEqual(inputRequestId, outputRequestId);
        }

        /// <summary>
        /// Test the MaxRecord.
        /// </summary>
        [Test]
        public void TestMaxRecord()
        {
            int inputMaxRecord = 1;
            requestCriteria.MaxRecord = inputMaxRecord;
            int outputMaxRecord = requestCriteria.MaxRecord;
            Assert.AreEqual(inputMaxRecord, outputMaxRecord);
        }

        /// <summary>
        /// Test the IsSearch
        /// </summary>
        [Test]
        public void TestIsSearch()
        {
            requestCriteria.IsSearch = true;
            Assert.IsTrue(requestCriteria.IsSearch);
        }

        /// <summary>
        /// Test the RequestorId.
        /// </summary>
        [Test]
        public void TestRequestorId()
        {
            long inputRequestorId = 1;
            requestCriteria.RequestorId = inputRequestorId;
            long outputRequestorId = requestCriteria.RequestorId;
            Assert.AreEqual(inputRequestorId, outputRequestorId);
        }

        /// <summary>
        /// Test the NonHpfPatientId.
        /// </summary>
        [Test]
        public void TestNonHpfPatientId()
        {
            long inputId = 1;
            requestCriteria.NonHpfPatientId = inputId;
            long outputId = requestCriteria.NonHpfPatientId;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test the Balance Due.
        /// </summary>
        [Test]
        public void TestBalanceDue()
        {
            string inputData = "1";
            requestCriteria.BalanceDue = inputData;
            string outputData = requestCriteria.BalanceDue;
            Assert.AreEqual(inputData, outputData);
        }


        /// <summary>
        /// Test the Request Reason
        /// </summary>
        [Test]
        public void TestRequestReason()
        {
            string inputData = "Reason";
            requestCriteria.RequestReason = inputData;
            string outputData = requestCriteria.RequestReason;
            Assert.AreEqual(inputData, outputData);
        }

        /// <summary>
        /// Test Method for CompletedDate.
        /// </summary>
        [Test]
        public void TestCompletedDate()
        {
            string inputDate = DateTime.Now.ToString();
            requestCriteria.CompletedDate = inputDate;
            string outputdate = requestCriteria.CompletedDate;
            Assert.AreEqual(inputDate, outputdate);
        }

        /// <summary>
        /// Test Method for FromDate.
        /// </summary>
        [Test]
        public void TestCompletedFromDate()
        {
            string inputDate = DateTime.Now.Date.ToString("MM/dd/yyyy");
            requestCriteria.CompletedFromDate = inputDate;
            Assert.AreEqual(inputDate, requestCriteria.CompletedFromDate);
        }

        /// <summary>
        /// Test Method for ToDate.
        /// </summary>
        [Test]
        public void TestCompletedToDate()
        {
            string inputDate = DateTime.Now.Date.ToString("MM/dd/yyyy");
            requestCriteria.CompletedToDate = inputDate;
            Assert.AreEqual(inputDate, requestCriteria.CompletedToDate);
        }

        /// <summary>
        /// Test Method for Receipt To Date.
        /// </summary>
        [Test]
        public void TestReceiptToDate()
        {
            string inputDate = DateTime.Now.Date.ToString("MM/dd/yyyy");
            requestCriteria.ReceiptToDate = inputDate;
            string outputDate = requestCriteria.ReceiptToDate;
            Assert.AreEqual(inputDate, outputDate);
        }

        /// <summary>
        /// Test Method for Custom Date.
        /// </summary>
        [Test]
        public void TestCustomDate()
        {
            string inputCustomDate = DateTime.Now.Date.ToString("MM/dd/yyyy");
            requestCriteria.CustomDate = inputCustomDate;
            string outputCustomDate = requestCriteria.CustomDate;
            Assert.AreEqual(inputCustomDate, outputCustomDate);
        }

        /// <summary>
        /// Test Method for Requestor Type Name.
        /// </summary>
        [Test]
        public void TestRequestorTypeName()
        {
            string inputRequestorTypeName = "Insurance";
            requestCriteria.RequestorTypeName = inputRequestorTypeName;
            string outputRequestorTypeName = requestCriteria.RequestorTypeName;
            Assert.AreEqual(inputRequestorTypeName, outputRequestorTypeName);
        }
        
        #endregion
    }
}
