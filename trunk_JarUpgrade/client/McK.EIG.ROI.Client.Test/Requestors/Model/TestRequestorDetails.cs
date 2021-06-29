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
using System.Collections.Generic;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{
    /// <summary>
    /// Test class for RequestorDetails
    /// </summary>
    [TestFixture]
    public class TestRequestorDetails
    {
        //Holds the object of model class.
        private RequestorDetails requestorDetails;

        [SetUp]
        public void Initialize()
        {
            requestorDetails = new RequestorDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestorDetails = null;
        }

        #region TestMethods

        /// <summary>
        /// Test case for Requestor Id.
        /// </summary>
        [Test]
        public void TestRequestorId()
        {
            long inputRequestorId = 100;
            requestorDetails.Id = inputRequestorId;
            long outputRequestorId = requestorDetails.Id;
            Assert.AreEqual(inputRequestorId, outputRequestorId);
        }

        /// <summary>
        /// Test Case for Requestor Name.
        /// </summary>
        [Test]
        public void TestRequestorName()
        {
            string inputRequestorName = "JohnSmith";
            requestorDetails.Name = inputRequestorName;
            string outputRequestorName = requestorDetails.Name;
            Assert.AreEqual(inputRequestorName, outputRequestorName);
        }

        /// <summary>
        /// Test Case for Requestor Type.
        /// </summary>
        [Test]
        public void TestRequestorType()
        {
            long inputRequestorType = 1;
            requestorDetails.Type = inputRequestorType;
            long outputRequestorType = requestorDetails.Type;
            Assert.AreEqual(inputRequestorType, outputRequestorType);
        }

        /// <summary>
        /// Test Case for Requestor Type Name
        /// </summary>
        [Test]
        public void TestRequestorTypeName()
        {
            string inputName = "test";
            requestorDetails.TypeName = inputName;
            string outputName = requestorDetails.TypeName;
            Assert.AreEqual(inputName, outputName);
        }


        /// <summary>
        /// Test Case for PatientEPN.
        /// </summary>
        [Test]
        public void TestPatientEpn()
        {
            string inputPatientEpn = "xxx345";
            requestorDetails.PatientEPN = inputPatientEpn;
            string outputPatientEpn = requestorDetails.PatientEPN;
            Assert.AreEqual(inputPatientEpn, outputPatientEpn);
        }

        /// <summary>
        /// Test Case for PatientSSN.
        /// </summary>
        [Test]
        public void TestPatientSsn()
        {
            string inputPatientSsn = "123-456-789";
            requestorDetails.PatientSSN = inputPatientSsn;
            string outputPatientSsn = requestorDetails.PatientSSN;
            Assert.AreEqual(inputPatientSsn, outputPatientSsn);
            Assert.IsNotNull(requestorDetails.MaskedPatientSSN);
        }

        /// <summary>
        /// Test Case for PatientMRN.
        /// </summary>
        [Test]
        public void TestPatientMrn()
        {
            string inputPatientMrn = "MRN123456789";
            requestorDetails.PatientMRN = inputPatientMrn;
            string outputPatientMrn = requestorDetails.PatientMRN;
            Assert.AreEqual(inputPatientMrn, outputPatientMrn);
        }

        /// <summary>
        /// Test method for  PatientFacility.
        /// </summary>
        [Test]
        public void TestFindPatientFacility()
        {
            string inputFacility = "User Permission";
            requestorDetails.PatientFacilityCode = inputFacility;
            string outputFacility = requestorDetails.PatientFacilityCode;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test Case for PatientDob.
        /// </summary>
        [Test]
        public void TestPatientDob()
        {
            Nullable<DateTime> inputPatientDob  = DateTime.Now;
            requestorDetails.PatientDob         = inputPatientDob;
            Nullable<DateTime> outputPatientDob = requestorDetails.PatientDob;
            Assert.AreEqual(inputPatientDob, outputPatientDob.Value);
            Assert.AreEqual(outputPatientDob.Value.ToString(ROIConstants.DateFormat), requestorDetails.FormattedDOB);
        }

        /// <summary>
        /// Test Case for Dob.
        /// </summary>
        [Test]
        public void TestDob()
        {
            string inputDob = DateTime.Now.ToString();
            requestorDetails.Dob = inputDob;
            string outputDob = requestorDetails.Dob;
            Assert.AreEqual(inputDob, outputDob);
        }

        /// <summary>
        /// Test method for PatientDetail Data of Birth with null.
        /// </summary>
        [Test]
        public void TestRequestorDetailDOBWithNull()
        {
            requestorDetails.PatientDob = null;
            Assert.IsNull(requestorDetails.FormattedDOB);
        }


        /// <summary>
        /// Test Case for Is PrePaymentRequired.
        /// </summary>
        [Test]
        public void TestPrePaymentRequired()
        {
            bool inputPrePaymentRequired = true;
            requestorDetails.PrepaymentRequired = inputPrePaymentRequired;
            bool outputPrePaymentRequired = requestorDetails.PrepaymentRequired;
            Assert.AreEqual(inputPrePaymentRequired, outputPrePaymentRequired);
        }

        /// <summary>
        /// Test Case for Is LetterRequired.
        /// </summary>
        [Test]
        public void TestLetterRequired()
        {
            bool inputLetterRequired = true;
            requestorDetails.LetterRequired = inputLetterRequired;
            bool outputLetterRequired = requestorDetails.LetterRequired;
            Assert.AreEqual(inputLetterRequired, outputLetterRequired);
        }

        /// <summary>
        /// Test Case for Requestor Home Phone Number.
        /// </summary>
        [Test]
        public void TestHomePhone()
        {
            string inputHomePhone = "8602777790";
            requestorDetails.HomePhone = inputHomePhone;
            string outputHomePhone = requestorDetails.HomePhone;
            Assert.AreEqual(inputHomePhone, outputHomePhone);
        }

        /// <summary>
        /// Test Case for Requestor Work Phone Number.
        /// </summary>
        [Test]
        public void TestWorkPhone()
        {
            string inputWorkPhone = "2016585182";
            requestorDetails.WorkPhone = inputWorkPhone;
            string outputWorkPhone = requestorDetails.WorkPhone;
            Assert.AreEqual(inputWorkPhone, outputWorkPhone);
        }

        /// <summary>
        /// Test Case for Requestor Cell Phone Number.
        /// </summary>
        [Test]
        public void TestCellPhone()
        {
            string inputCellPhone = "2089765432";
            requestorDetails.CellPhone = inputCellPhone;
            string ouputCellPhone = requestorDetails.CellPhone;
            Assert.AreEqual(inputCellPhone, ouputCellPhone);
        }

        /// <summary>
        /// Test Case for Requestor Email.
        /// </summary>
        [Test]
        public void TestEmail()
        {
            string inputEmail = "test@Mckesson.com";
            requestorDetails.Email = inputEmail;
            string outputEmail = requestorDetails.Email;
            Assert.AreEqual(inputEmail, outputEmail);
        }

        /// <summary>
        /// Test Case for Fax Number.
        /// </summary>
        [Test]
        public void TestFax()
        {
            string inputFax = "022-5678903";
            requestorDetails.Fax = inputFax;
            string outputFax = requestorDetails.Fax;
            Assert.AreEqual(inputFax, outputFax);
        }

        /// <summary>
        /// Test Case for Contact Name.
        /// </summary>
        [Test]
        public void TestContactName()
        {
            string inputContactName = "John";
            requestorDetails.ContactName = inputContactName;
            string outputContactName = requestorDetails.ContactName;
            Assert.AreEqual(inputContactName, outputContactName);
        }

        /// <summary>
        /// Test Case for Contact Phone.
        /// </summary>
        [Test]
        public void TestContactPhone()
        {
            string inputContactPhone = "8602777790";
            requestorDetails.ContactPhone = inputContactPhone;
            string outputContactPhone = requestorDetails.ContactPhone;
            Assert.AreEqual(inputContactPhone, outputContactPhone);
        }

        /// <summary>
        /// Test Case for ContactEmail.
        /// </summary>
        [Test]
        public void TestContactEmail()
        {
            string inputContactEmail = "Smith@sify.com";
            requestorDetails.ContactEmail = inputContactEmail;
            string outputContactEmail = requestorDetails.ContactEmail;
            Assert.AreEqual(inputContactEmail, outputContactEmail);
        }

        /// <summary>
        /// Test Case for Main Address. 
        /// </summary>
        [Test]
        public void TestMainAddressDetail()
        {
            requestorDetails.MainAddress = new AddressDetails();
            Assert.IsInstanceOfType(typeof(AddressDetails), requestorDetails.MainAddress);
        }

        /// <summary>
        /// Test Case for Alternate Address
        /// </summary>
        [Test]
        public void TestAltenateAddressDetail()
        {
            requestorDetails.AltAddress = new AddressDetails();
            Assert.IsInstanceOfType(typeof(AddressDetails), requestorDetails.AltAddress);
        }

        

        /// <summary>
        /// Test Case for IsAssociated
        /// </summary>
        [Test]
        public void TestIsAssociated()
        {
            requestorDetails.IsAssociated = true;
            bool inputStatus = requestorDetails.IsAssociated;
            bool outputStatus = inputStatus;
            Assert.AreEqual(inputStatus, outputStatus);
        }

        /// <summary>
        /// Test Case for IsActive status.
        /// </summary>
        [Test]
        public void TestIsActive()
        {
            requestorDetails.IsActive = true;
            bool inputStatus = requestorDetails.IsActive;
            bool outputStatus = inputStatus;
            Assert.AreEqual(inputStatus, outputStatus);
        }

        /// <summary>
        /// Test Case for Status
        /// </summary>
        [Test]
        public void TestStatus()
        {
            requestorDetails.Status = "Active";
            string inputStatus = requestorDetails.Status;
            string outputStatus = inputStatus;
            Assert.AreEqual(inputStatus, outputStatus);
        }

        /// <summary>
        /// Test Case for RequestorDetailsSorter.
        /// </summary>
        [Test]
        public void TestRequestorDetailsSorter()
        {
            FindRequestorResult result = new FindRequestorResult();
            RequestorDetails details = new RequestorDetails();
            details.Name = "Jhon";
            result.RequestorSearchResult.Add(details);
            details = new RequestorDetails();
            details.Name = "Alan";
            result.RequestorSearchResult.Add(details);
            details = new RequestorDetails();
            details.Name = "Rhods";
            result.RequestorSearchResult.Add(details);
            List<RequestorDetails> list = new List<RequestorDetails>(result.RequestorSearchResult);
            list.Sort(RequestorDetails.Sorter);
            Assert.IsNotNull(RequestorDetails.Sorter);
        }


        /// <summary>
        /// Test Case for Patient requestor type
        /// </summary>
        [Test]
        public void TestIsPatientRequestor()
        {
            Assert.AreEqual(false, requestorDetails.IsPatientRequestor);
        }

        /// <summary>
        /// Test Case for contact Phone.
        /// </summary>
        [Test]
        public void TestPhone()
        {
            string homePhone = "8602777790";
            requestorDetails.HomePhone = homePhone;
            requestorDetails.Type = -1;
            Assert.AreEqual(homePhone, requestorDetails.Phone);
        }

        /// <summary>
        /// Test Case for Facility Type.
        /// </summary>
        [Test]
        public void TestFacilityType()
        {   
            requestorDetails.PatientFacilityType = FacilityType.Hpf;
            Assert.AreEqual(requestorDetails.PatientFacilityType, FacilityType.Hpf);
        }

        /// <summary>
        /// Test Case for IsFreeFormFacility.
        /// </summary>
        [Test]
        public void TestIsFreeFormFacility()
        {
            requestorDetails.IsFreeformFacility = true;
            Assert.IsTrue(requestorDetails.IsFreeformFacility);
        }

        #endregion

    }
}
