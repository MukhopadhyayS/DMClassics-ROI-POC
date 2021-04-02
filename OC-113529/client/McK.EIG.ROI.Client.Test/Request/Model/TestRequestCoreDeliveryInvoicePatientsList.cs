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
using System.Collections.Generic;
using System.Linq;
using System.Text;

using McK.EIG.ROI.Client.Request.Model;
//NUnit
using NUnit.Framework;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test case for TestRequest Core Delivery Invoice Patients List
    /// </summary>
    [TestFixture]
    public class TestRequestCoreDeliveryInvoicePatientsList
    {
        //Holds the object of model class.
        private RequestCoreDeliveryInvoicePatientsList requestCoreDeliveryInvoicePatientsList;

        [SetUp]
        public void Initialize()
        {
            requestCoreDeliveryInvoicePatientsList = new RequestCoreDeliveryInvoicePatientsList();
        }

        [TearDown]
        public void Dispose()
        {
            requestCoreDeliveryInvoicePatientsList = null;
        }

        /// <summary>
        /// Test case for Request Core Delivery Charges ID.
        /// </summary>
        [Test]
        public void TestRequestCoreDeliveryChargesId()
        {
            long inputRequestCoreDeliveryChargesId = 100;
            requestCoreDeliveryInvoicePatientsList.RequestCoreDeliveryChargesId = inputRequestCoreDeliveryChargesId;
            long outputRequestCoreDeliveryChargesId = requestCoreDeliveryInvoicePatientsList.RequestCoreDeliveryChargesId;
            Assert.AreEqual(inputRequestCoreDeliveryChargesId, outputRequestCoreDeliveryChargesId);
        }

        /// <summary>
        /// Test case for EPN.
        /// </summary>
        [Test]
        public void TestEPN()
        {
            string inputEPN = "EPN1234";
            requestCoreDeliveryInvoicePatientsList.Epn = inputEPN;
            string outputEPN = requestCoreDeliveryInvoicePatientsList.Epn;
            Assert.AreEqual(inputEPN, outputEPN);
        }

        /// <summary>
        /// Test case for MRN.
        /// </summary>
        [Test]
        public void TestMRN()
        {
            string inputMRN = "FralickM01";
            requestCoreDeliveryInvoicePatientsList.Mrn = inputMRN;
            string outputMRN = requestCoreDeliveryInvoicePatientsList.Mrn;
            Assert.AreEqual(inputMRN, outputMRN);
        }

        /// <summary>
        /// Test case for SSN.
        /// </summary>
        [Test]
        public void TestSSN()
        {
            string inputSSN = "123-456-789";
            requestCoreDeliveryInvoicePatientsList.Ssn = inputSSN;
            string outputSSN = requestCoreDeliveryInvoicePatientsList.Ssn;
            Assert.AreEqual(inputSSN, outputSSN);
        }

        /// <summary>
        /// Test case for Name.
        /// </summary>
        [Test]
        public void TestName()
        {
            string inputName = "Fraclick";
            requestCoreDeliveryInvoicePatientsList.Name = inputName;
            string outputName = requestCoreDeliveryInvoicePatientsList.Name;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test case for Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string inputFacility = "AD";
            requestCoreDeliveryInvoicePatientsList.Facility = inputFacility;
            string outputFacility = requestCoreDeliveryInvoicePatientsList.Facility;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test case for Is VIP.
        /// </summary>
        [Test]
        public void TestIsVIP()
        {
            bool inputIsVIP = false;
            requestCoreDeliveryInvoicePatientsList.IsVIP = inputIsVIP;
            bool outputIsVIP = requestCoreDeliveryInvoicePatientsList.IsVIP;
            Assert.AreEqual(inputIsVIP, outputIsVIP);
        }

        /// <summary>
        /// Test case for Encounter Facility.
        /// </summary>
        [Test]
        public void TestEncounterFacility()
        {
            string inputEncounterFacility = "Enc1234";
            requestCoreDeliveryInvoicePatientsList.EncounterFacility = inputEncounterFacility;
            string outputEncounterFacility = requestCoreDeliveryInvoicePatientsList.EncounterFacility;
            Assert.AreEqual(inputEncounterFacility, outputEncounterFacility);
        }
    }
}
