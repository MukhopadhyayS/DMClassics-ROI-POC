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
using System.Collections.ObjectModel;
using System.Configuration;
using System.Drawing;
using System.Globalization;

//NUnit
using NUnit.Framework;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Test.Patient.Controller;
using McK.EIG.ROI.Client.Test.Request.Controller;
using McK.EIG.ROI.Client.Web_References.ROIRequestorWS;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.Controller;

namespace McK.EIG.ROI.Client.Test.Requestors.Controller
{
    /// <summary>
    /// Test Case for Requestor Controller.
    /// </summary>
    [TestFixture]
    public class TestRequestorController : TestBase
    {
        #region Fields

        private static Log log = LogFactory.GetLogger(typeof(TestRequestorController));

        private long id;
        private string name;
        private string firstName;
        private string lastName;
        private RequestorController requestorController;
        private RequestorDetails requestorInfo;
        private RequestorDetails newRequestor;
        private RequestorInvoiceList requestorInvoiceList;
        private Client.Request.Model.RequestDetails request;
        private Collection<PastInvoiceDetails> pastInvoices;
        private Collection<LetterTemplateDetails> letterTemplates;
        private DocInfoList docInfoList;

        #endregion

        #region Constructor

        public TestRequestorController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
            name = "Requestor" + GetUniqueId();
            lastName = "LastRequestor" + GetUniqueId();
            firstName = "FirstRequestor" + GetUniqueId();
            requestorInfo = new RequestorDetails();

            requestorInfo.Id = id;
            requestorInfo.Name = name;

            requestorController = RequestorController.Instance;
        }

        #endregion

        #region Nunit

        [SetUp]
        public void Init()
        {
            requestorController = RequestorController.Instance;
            letterTemplates = ROIAdminController.Instance.RetrieveAllLetterTemplates();
        }

        [TearDown]
        public void Dispose()
        {
            requestorController = null;

        }

        #endregion

        #region Methods

        /// <summary>
        /// Test case for create requestor.
        /// </summary>
        [Test]
        public void Test01CreateRequestor()
        {
            requestorController = RequestorController.Instance;
            newRequestor = new RequestorDetails();
            AddressDetails addressDetails = new AddressDetails();
            AddressDetails alternateAddressDetails = new AddressDetails();

            newRequestor.LetterRequired = true;
            newRequestor.PrepaymentRequired = true;

            newRequestor.Type = -1;
            newRequestor.TypeName = "Insurance";
            newRequestor.FirstName  = "FirstRequestor" + GetUniqueId();
            newRequestor.LastName   = "LastRequestor" + GetUniqueId();          
            newRequestor.Name       = newRequestor.LastName + ", " + newRequestor.FirstName;
            //newRequestor.PatientEPN = (UserData.Instance.EpnEnabled) ? GetEPNWithPrefix("12345") : string.Empty;
            newRequestor.PatientEPN = string.Empty;
            newRequestor.PatientDob = DateTime.Now.AddDays(-1);
            newRequestor.PatientSSN = "123-45-6789";
            newRequestor.PatientMRN = "MRN123456789";
            newRequestor.PatientFacilityCode = "AD";
            newRequestor.IsFreeformFacility = false;
            newRequestor.WorkPhone = "8602777790";
            newRequestor.IsActive  = true;

            addressDetails.Address1 = "McKesson Corporation";
            addressDetails.Address2 = "5995, Winward Parkway";
            addressDetails.Address3 = "Alpharetta";
            addressDetails.City = "Alpharetta";
            addressDetails.State = "GA";
            addressDetails.PostalCode = "30005";

            newRequestor.MainAddress = addressDetails;

            alternateAddressDetails.Address1 = "McKesson Corporation";
            alternateAddressDetails.Address2 = "5995, Winward Parkway";
            alternateAddressDetails.Address3 = "Alpharetta";
            alternateAddressDetails.City = "Alpharetta";
            alternateAddressDetails.State = "GA";
            alternateAddressDetails.PostalCode = "30005";

            newRequestor.AltAddress = alternateAddressDetails;

            newRequestor.CellPhone = "2016585182";
            newRequestor.Email = "Smith@yahoo.co.in";
            newRequestor.ContactName = "John";
            newRequestor.ContactPhone = "220998777";
            newRequestor.ContactEmail = "Smith@yahoo.co.in";
            newRequestor.Fax = "23222";
            newRequestor.HomePhone = "8765554222";

            id = requestorController.CreateRequestor(newRequestor);
            newRequestor.Id = id;
            Assert.IsTrue(newRequestor.Id > 0);
        }

        /// <summary>
        /// Test Case for Retrieve requesor.
        /// </summary>                                                                                                                                                                                                                                                                                                                                          
        [Test]
        public void Test02RetrieveRequestor()
        {
            bool isEpnEnabled = false;
            if (!UserData.Instance.EpnEnabled)
            {
                isEpnEnabled = true;
                UserData.Instance.EpnEnabled = true;
            }

            RequestorDetails retrievedRequestorDetail = requestorController.RetrieveRequestor(newRequestor.Id, false);
            if (isEpnEnabled) UserData.Instance.EpnEnabled = false;
            Assert.AreEqual(newRequestor.Id, retrievedRequestorDetail.Id);
            Assert.AreEqual(newRequestor.LetterRequired, retrievedRequestorDetail.LetterRequired);
            Assert.AreEqual(newRequestor.PrepaymentRequired, retrievedRequestorDetail.PrepaymentRequired);
            Assert.AreEqual(newRequestor.Type, retrievedRequestorDetail.Type);
            Assert.AreEqual(newRequestor.LastName, retrievedRequestorDetail.LastName);
            Assert.AreEqual(newRequestor.MainAddress, retrievedRequestorDetail.MainAddress);
            Assert.AreEqual(newRequestor.AltAddress, retrievedRequestorDetail.AltAddress);
        }

        /// <summary>
        /// Test Case for Check Duplicate Name.
        /// </summary>
        [Test]
        public void Test03CheckDuplicateName()
        {
            AddressDetails addressDetails = new AddressDetails();
            AddressDetails alternateAddressDetails = new AddressDetails();
            requestorInfo.Id = id;
            requestorInfo.LastName = lastName;
            requestorInfo.FirstName = firstName;
            requestorInfo.Name = lastName + ", " + firstName;
            requestorInfo.Name = name;
            requestorInfo.Type = -1;
            requestorInfo.Type = -1;
            requestorInfo.PatientDob = DateTime.Now.Date;
            requestorInfo.PatientEPN = (UserData.Instance.EpnEnabled) ? GetEPNWithPrefix("123678") : string.Empty;
            newRequestor.PatientMRN = "MRN123456789";
            newRequestor.PatientFacilityCode = "AD";
            newRequestor.IsFreeformFacility = false;
            requestorInfo.PatientSSN = "123-45-6000";
            requestorInfo.WorkPhone = "8602777790";

            addressDetails.Address1 = "No 35,J.N street";
            addressDetails.Address2 = "No 45,Sai baba colony";
            addressDetails.Address3 = "No 30,JJ,colony";
            addressDetails.City = "Kovai";
            addressDetails.State = "TN";
            addressDetails.PostalCode = "60008";

            requestorInfo.MainAddress = addressDetails;
           
            alternateAddressDetails.Address1 = "No 35,J.N street";
            alternateAddressDetails.Address2 = "No 45,Sai baba colony";
            alternateAddressDetails.Address3 = "No 5/360 Rajivigandhi salai";
            alternateAddressDetails.City = "Chennai";
            alternateAddressDetails.State = "TN";
            alternateAddressDetails.PostalCode = "60008";

            requestorInfo.AltAddress = alternateAddressDetails;

            requestorInfo.CellPhone = "2016585182";
            requestorInfo.Email = "Smith@yahoo.co.in";
            requestorInfo.ContactName = "John";
            requestorInfo.ContactPhone = "220998777";
            requestorInfo.ContactEmail = "Smith@yahoo.co.in";
            requestorInfo.Fax = "23222";
            requestorInfo.HomePhone = "8765554222";

            requestorController.CheckDuplicateName(requestorInfo);
            Assert.IsTrue(true);
        }

        /// <summary>
        /// Test Case for Update Requestor.
        /// </summary>
        [Test]
        public void Test04UpdateRequestor()
        {
            RequestorDetails requestorDetail = new RequestorDetails();
            AddressDetails addressDetails = new AddressDetails();
            AddressDetails alternateAddressDetails = new AddressDetails();

            requestorDetail.LetterRequired = true;
            requestorDetail.PrepaymentRequired = true;

            requestorDetail.Id = id;
            requestorDetail.Type = -1;
            requestorDetail.LastName = name + "LastTest";
            requestorDetail.FirstName = name + "FirstTest";
            requestorDetail.Name = requestorDetail.LastName + ", " + requestorDetail.LastName;
            requestorDetail.PatientEPN = (UserData.Instance.EpnEnabled) ? GetEPNWithPrefix("12345") : string.Empty;
            requestorDetail.PatientSSN = "123-45-6789";
            requestorDetail.PatientDob = DateTime.Now.AddDays(-1);
            newRequestor.PatientMRN = "MRN1234567891";
            newRequestor.PatientFacilityCode = "A";
            newRequestor.IsFreeformFacility = false;
            requestorDetail.WorkPhone = "8602777790";

            addressDetails.Address1 = "No 35,J.N street";
            addressDetails.Address2 = "No 45,Sai baba colony";
            addressDetails.Address3 = "No 5/360 Rajivigandhi salai";
            addressDetails.City = "Kovai";
            addressDetails.State = "TN";
            addressDetails.PostalCode = "60008";

            requestorDetail.MainAddress = addressDetails;

            alternateAddressDetails.Address1 = "No 35,J.N street";
            alternateAddressDetails.Address2 = "No 45,Sai baba colony";
            alternateAddressDetails.Address3 = "No 5/360 Rajivigandhi salai";
            alternateAddressDetails.City = "Chennai";
            alternateAddressDetails.State = "TN";
            alternateAddressDetails.PostalCode = "60008";

            requestorDetail.AltAddress = alternateAddressDetails;

            requestorDetail.CellPhone = "2016585182";
            requestorDetail.Email = "Smith@yahoo.co.in";
            requestorDetail.ContactName = "John";
            requestorDetail.ContactPhone = "220998777";
            requestorDetail.ContactEmail = "Smith@yahoo.co.in";
            requestorDetail.Fax = "23222";
            requestorDetail.HomePhone = "8765554222";

            RequestorDetails updateRequestorDetail = requestorController.UpdateRequestor(requestorDetail);
            newRequestor = updateRequestorDetail;
            Assert.AreNotEqual(requestorDetail.RecordVersionId, updateRequestorDetail.RecordVersionId);
        }

        /// <summary>
        /// Test Case for Create Requestor With Empty Name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test05CreateRequestorWithEmptyName()
        {
            RequestorDetails requestorDetail = new RequestorDetails();
            AddressDetails addressDetails = new AddressDetails();
            AddressDetails alternateAddressDetails = new AddressDetails();

            requestorDetail.LetterRequired = true;
            requestorDetail.PrepaymentRequired = true;

            requestorDetail.Type = -1;
            requestorDetail.FirstName = "";
            requestorDetail.LastName = "";
            requestorDetail.PatientDob = DateTime.Now;
            requestorDetail.PatientEPN = (UserData.Instance.EpnEnabled) ? GetEPNWithPrefix("12345") : string.Empty;
            requestorDetail.PatientSSN = "123-45-6789";
            requestorDetail.WorkPhone = "8602777790";

            addressDetails.Address1 = "No 35,J.N street";
            addressDetails.Address2 = "No 45,Sai baba colony";
            addressDetails.Address3 = string.Empty;
            addressDetails.City = "Kovai";
            addressDetails.State = "TN";
            addressDetails.PostalCode = "6000098";

            requestorDetail.MainAddress = addressDetails;

            alternateAddressDetails.Address1 = "No 35,J.N street";
            alternateAddressDetails.Address2 = "No 45,Sai baba colony";
            alternateAddressDetails.Address3 = "No 5/360 Rajivigandhi salai";
            alternateAddressDetails.City = "Chennai";
            alternateAddressDetails.State = "TN";
            alternateAddressDetails.PostalCode = "600008";

            requestorDetail.AltAddress = alternateAddressDetails;

            requestorDetail.CellPhone = "2016585182";
            requestorDetail.Email = "Smith@yahoo.co.in";
            requestorDetail.ContactName = "John";
            requestorDetail.ContactPhone = "220998777";
            requestorDetail.ContactEmail = "Smith@#yahoo.co.in";
            requestorDetail.Fax = "23222";
            requestorDetail.HomePhone = "8765554222";

            requestorController.CreateRequestor(requestorDetail);
        }

        /// <summary>
        /// Test Case for Update Requestor With Empty Name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test06UpdateRequestorWithEmptyName()
        {
            RequestorDetails requestorDetail = new RequestorDetails();
            AddressDetails addressDetails = new AddressDetails();
            AddressDetails alternateAddressDetails = new AddressDetails();

            requestorDetail.LetterRequired = true;
            requestorDetail.PrepaymentRequired = true;

            requestorDetail.Id = id;
            requestorDetail.LastName = "";
            requestorDetail.FirstName = "";
            requestorDetail.Type = -1;
            requestorDetail.PatientDob = DateTime.Now;
            requestorDetail.PatientEPN = (UserData.Instance.EpnEnabled) ? GetEPNWithPrefix("123678") : string.Empty;
            requestorDetail.PatientSSN = "123-45-6000";
            requestorDetail.WorkPhone = "8602777790";

            addressDetails.Address1 = "No 35,J.N street";
            addressDetails.Address2 = "No 45,Sai baba colony";
            addressDetails.Address3 = "No 30,JJ,colony";
            addressDetails.City = "Kovai";
            addressDetails.State = "TN";
            addressDetails.PostalCode = "60008";

            requestorDetail.MainAddress = addressDetails;

            alternateAddressDetails.Address1 = "No 35,J.N street";
            alternateAddressDetails.Address2 = "No 45,Sai baba colony";
            alternateAddressDetails.Address3 = "No 5/360 Rajivigandhi salai";
            alternateAddressDetails.City = "Chennai";
            alternateAddressDetails.State = "TN";
            alternateAddressDetails.PostalCode = "60008";

            requestorDetail.AltAddress = alternateAddressDetails;

            requestorDetail.CellPhone = "2016585182";
            requestorDetail.Email = "Smith@yahoo.co.in";
            requestorDetail.ContactName = "John";
            requestorDetail.ContactPhone = "220998777";
            requestorDetail.ContactEmail = "Smith@yahoo.co.in";
            requestorDetail.Fax = "23222";
            requestorDetail.HomePhone = "8765554222";

            requestorController.UpdateRequestor(requestorDetail);

        }

        /// <summary>
        /// Test Case for Delte Requestor with Non Existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test07DeleteNonExistingRequestor()
        {
            requestorController.DeleteRequestor(0);
        }

        /// <summary>
        /// Test case for find requestors with valid input.
        /// </summary>
        [Test]
        public void Test08FindRequestorTypeWithValidInput()
        {
            FindRequestorCriteria findRequestorCriteria = new FindRequestorCriteria();
            FindRequestorResult requestorResult = new FindRequestorResult();
            findRequestorCriteria.AllRequestors = true;
            findRequestorCriteria.ActiveRequestor = false;
            findRequestorCriteria.LastName = "Requestor";
            findRequestorCriteria.FirstName = "Requestor";
            findRequestorCriteria.RequestorTypeId = -1;
            findRequestorCriteria.PatientSSN = newRequestor.PatientSSN;
            findRequestorCriteria.PatientEPN = newRequestor.PatientEPN;
            findRequestorCriteria.PatientDob = newRequestor.PatientDob;

            findRequestorCriteria.MaxRecord = 500;

            requestorResult = requestorController.FindRequestor(findRequestorCriteria);

            Assert.IsTrue(requestorResult.RequestorSearchResult.Count > 0);
        }

        /// <summary>
        /// Test Case for find requestors with invalid input.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09FindRequestorTypeWithInValidInput()
        {
            FindRequestorCriteria findRequestorCriteria = new FindRequestorCriteria();
            FindRequestorResult requestorResult = new FindRequestorResult();
            findRequestorCriteria.AllRequestors = true;
            findRequestorCriteria.LastName = "L";
            findRequestorCriteria.FirstName = "F";
            findRequestorCriteria.PatientSSN = "1";
            findRequestorCriteria.PatientMRN = string.Empty;
            findRequestorCriteria.PatientFacilityCode = "12";
            findRequestorCriteria.MaxRecord = 500;

            requestorResult = requestorController.FindRequestor(findRequestorCriteria);
        }

        /// <summary>
        /// Test Case for find requestors with invalid input.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test10FindRequestorTypeWithInValidInput()
        {
            FindRequestorCriteria findRequestorCriteria = new FindRequestorCriteria();
            FindRequestorResult requestorResult = new FindRequestorResult();
            findRequestorCriteria.AllRequestors = true;
            findRequestorCriteria.LastName = "L#";
            findRequestorCriteria.FirstName = "F#";
            findRequestorCriteria.PatientMRN = "1";
            findRequestorCriteria.PatientEPN = "12345";
            findRequestorCriteria.PatientSSN = "12";
            RequestorTypeDetails reqDetails = new RequestorTypeDetails();
            reqDetails.Name = "Patient";
            findRequestorCriteria.MaxRecord = 500;

            requestorResult = requestorController.FindRequestor(findRequestorCriteria);
        }

        /// <summary>
        /// Test Case for Find Requestors with no search Found
        /// </summary>
        [Test]
        public void Test11FindRequestorWithNoSearchFound()
        {
            FindRequestorCriteria findRequestorCriteria = new FindRequestorCriteria();
            FindRequestorResult requestorResult = new FindRequestorResult();
            findRequestorCriteria.AllRequestors = true;
            findRequestorCriteria.Name = "oooo";
            findRequestorCriteria.RequestorTypeId = 0;          
            findRequestorCriteria.MaxRecord = 500;        
            requestorResult = requestorController.FindRequestor(findRequestorCriteria);

            Assert.IsTrue(requestorResult.RequestorSearchResult.Count == 0);
        }

        /// <summary>
        /// Test Case for Search Patient Matching  with no search Found
        /// </summary>
        [Test]
        public void Test15SearchRequestorMatchingPatients()
        {
            RequestorDetails findPatientCriteria = new RequestorDetails();
            FindPatientResult patientResult = new FindPatientResult();

            TestPatientController testPatientController = new TestPatientController();
            testPatientController.Init();
            testPatientController.Test16RetrieveHPFPatientWithName();
            PatientDetails nonHpfPatient = testPatientController.hpfPatient;

            nonHpfPatient.IsHpf = false;
            nonHpfPatient.SSN = null;
            //nonHpfPatient.EPN = (UserData.Instance.EpnEnabled && UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix + "1234" : string.Empty;
            //nonHpfPatient.DOB = DateTime.Now;
            //nonHpfPatient.SSN = "123-45-6789";
           
            AddressDetails addressDetail = new AddressDetails();

            nonHpfPatient.HomePhone = "1111-1111";
            nonHpfPatient.WorkPhone = "2222-2222";
          
            addressDetail.Address1 = "Address 1";
            addressDetail.Address2 = "Address 2";
            addressDetail.Address3 = "Address 3";
            addressDetail.City = "Test City";
            addressDetail.State = "Test State";
            addressDetail.PostalCode = "11111";

            nonHpfPatient.Address = addressDetail;

            PatientController patientController = PatientController.Instance;
            patientController.CreateSupplementalPatient(nonHpfPatient);

            findPatientCriteria.Name = nonHpfPatient.Name;
            findPatientCriteria.PatientSSN = nonHpfPatient.SSN;
            findPatientCriteria.PatientDob = nonHpfPatient.DOB;
            findPatientCriteria.PatientEPN = nonHpfPatient.EPN;
            findPatientCriteria.Type = -1; 

            patientResult = requestorController.SearchMatchingPatients(findPatientCriteria);

            Assert.IsTrue(patientResult.PatientSearchResult.Count > 0);

            //No matching
            findPatientCriteria = new RequestorDetails();
            findPatientCriteria.LastName  = "LastTest";
            findPatientCriteria.FirstName = "FirstTest";
            findPatientCriteria.PatientSSN = "123-45-6789";
            findPatientCriteria.PatientEPN = "898";
            findPatientCriteria.PatientMRN = "1234";
            findPatientCriteria.PatientFacilityCode = "IE";
            patientResult = requestorController.SearchMatchingPatients(findPatientCriteria);
            Assert.IsFalse(patientResult.PatientSearchResult.Count > 0);

        }

        /// <summary>
        /// Test Case for find Requestors With More Max Count.
        /// </summary>
        [Test]
        public void Test12FindRequestorWithMaxCount()
        {
            FindRequestorCriteria findRequestorCriteria = new FindRequestorCriteria();
            FindRequestorResult requestorResult = new FindRequestorResult();
            findRequestorCriteria.AllRequestors = true;
            findRequestorCriteria.Name = "Requestor";
            findRequestorCriteria.RequestorTypeId = 0;
            findRequestorCriteria.MaxRecord = 0;
            RequestorTypeDetails reqDetails = new RequestorTypeDetails();
            reqDetails.Name = "Patient";
            requestorResult = requestorController.FindRequestor(findRequestorCriteria);

            Assert.IsTrue(requestorResult.RequestorSearchResult.Count == 0);
        }

        /// <summary>
        /// Test case for duplicate name for Non patient Requestor. 
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test13CheckForDuplicateNonPatientRequestorName()
        {
            requestorInfo.Id = id;
            requestorInfo.Type = 0;
            string requestorName = name;          
            while (requestorName.Length <= 256)
            {
                requestorName += requestorName;
            }           
            requestorInfo.Name = requestorName;
           
            requestorController.CheckDuplicateName(requestorInfo);
        }

        /// <summary>
        /// Test case for duplicate name for Non patient Requestor. 
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test14CheckForDuplicatePatientRequestorName()
        {
            requestorInfo.Id = id;
            requestorInfo.Type = -1;
            string requestorFirstName = firstName;
            string requestorLastName = lastName;
            while (requestorFirstName.Length <= 256)
            {
                requestorFirstName += requestorFirstName;
            }
            while (requestorLastName.Length <= 256)
            {
                requestorLastName += requestorLastName;
            }
            requestorInfo.FirstName = requestorFirstName;
            requestorInfo.LastName = requestorLastName;

            requestorController.CheckDuplicateName(requestorInfo);
        }

        /// <summary>
        /// Test Case for Create Non Patient Requestor With Empty Name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test15CreateNonPatientRequestorWithEmptyName()
        {
            RequestorDetails requestorDetail = new RequestorDetails();
            AddressDetails addressDetails = new AddressDetails();
            AddressDetails alternateAddressDetails = new AddressDetails();

            requestorDetail.LetterRequired = true;
            requestorDetail.PrepaymentRequired = true;

            requestorDetail.Type = 0;
            requestorDetail.Name = "";
            requestorDetail.PatientDob = DateTime.Now;
            requestorDetail.PatientEPN = (UserData.Instance.EpnEnabled) ? GetEPNWithPrefix("12345") : string.Empty;
            requestorDetail.PatientSSN = "123-45-6789";
            requestorDetail.WorkPhone = "8602777790";

            requestorDetail.MainAddress = addressDetails;
            requestorDetail.AltAddress = alternateAddressDetails;

            requestorController.CreateRequestor(requestorDetail);
        }

        /// <summary>
        /// Test Case for Delete Requestor With Existing Id.
        /// </summary>
        [Test]
        public void Test16DeleteRequestor()
        {
            requestorController.DeleteRequestor(id);
            Assert.IsTrue(true);
        }

        /// <summary>
        /// Test case for find requestors with valid input.
        /// </summary>
        [Test]
        public void Test17FindNonPatientRequestorWithValidInput()
        {
            RequestorTypeDetails requestorTypeDetails = new RequestorTypeDetails();
            ROIAdminController roiAdminController = ROIAdminController.Instance;
            requestorTypeDetails.HpfBillingTier = new BillingTierDetails();
            requestorTypeDetails.NonHpfBillingTier = new BillingTierDetails();
            requestorTypeDetails.RecordViewDetails = new RecordViewDetails();

            requestorTypeDetails.Name = name;
            requestorTypeDetails.HpfBillingTier.Id = -1;
            requestorTypeDetails.NonHpfBillingTier.Id = -1;
            requestorTypeDetails.RecordViewDetails.Name = "Test";

            requestorTypeDetails = roiAdminController.CreateRequestorType(requestorTypeDetails);



            requestorController = RequestorController.Instance;
            newRequestor = new RequestorDetails();
            AddressDetails addressDetails = new AddressDetails();
            AddressDetails alternateAddressDetails = new AddressDetails();

            newRequestor.LetterRequired = true;
            newRequestor.PrepaymentRequired = true;

            newRequestor.Name = "Requestor" + GetUniqueId();
            newRequestor.Type = requestorTypeDetails.Id;
            newRequestor.WorkPhone = "8602777790";
            newRequestor.IsActive = true;

            newRequestor.MainAddress = addressDetails;
            newRequestor.AltAddress = alternateAddressDetails;
            requestorController.CreateRequestor(newRequestor);

            FindRequestorCriteria findRequestorCriteria = new FindRequestorCriteria();
            FindRequestorResult requestorResult = new FindRequestorResult();
            findRequestorCriteria.AllRequestors = true;
            findRequestorCriteria.ActiveRequestor = true;
            findRequestorCriteria.Name = "Requestor";
            findRequestorCriteria.RequestorTypeId = 0;
            findRequestorCriteria.MaxRecord = 500;

            requestorResult = requestorController.FindRequestor(findRequestorCriteria);
            requestorController.RetrieveRequestor(requestorResult.RequestorSearchResult[0].Id, false);

            Assert.IsTrue(requestorResult.RequestorSearchResult.Count > 0);
        }

        /// <summary>
        /// Test case for Create invoice payment
        /// </summary>
        [Test]
        public void Test18CreateInvoicePayment()
        {
            TestBillingController testBillingController = new TestBillingController();
            request = testBillingController.CreateRequest();
            testBillingController.Test01SaveRequestCoreCharges();
            testBillingController.Test02CreateRelease();
            testBillingController.Test03CreateInvoiceOrPrebill();

            RequestorInvoiceList requestorInvoiceList = new RequestorInvoiceList();
            requestorInvoiceList.Description = "Description";
            requestorInvoiceList.PaymentAmount = 100;
            requestorInvoiceList.PaymentDate = DateTime.Now;
            requestorInvoiceList.PaymentId = 0;
            requestorInvoiceList.PaymentMode = "Check";
            requestorInvoiceList.RequestorId = request.Requestor.Id;
            RequestorInvoiceDetails requestorInvoiceDetails = new RequestorInvoiceDetails();
            requestorInvoiceDetails.AmountPaid = 10;
            requestorInvoiceDetails.CurrentAppliedAmount = 10;
            requestorInvoiceDetails.PaymentTotal = 10;
            requestorInvoiceDetails.InvoiceId = testBillingController.InvoiceAndDocumentDetails.DocumentInfos.DocumentInfoCollection[0].Id;
            requestorInvoiceDetails.BaseCharge = 20;
            requestorInvoiceDetails.Balance = 10;
            requestorInvoiceDetails.ApplyAmount = 10;
            requestorInvoiceDetails.RequestId = request.Id;
            requestorInvoiceDetails.Facility = "AD";
            requestorInvoiceList.RequestorInvoices.Add(requestorInvoiceDetails);
            requestorInvoiceList.UnAppliedAmount = 90;
            RequestorController.Instance.CreateInvoicePayment(requestorInvoiceList);
            AdjustmentInfoDetail adjInfoSave = new AdjustmentInfoDetail();
            RequestorAdjustmentDetails reqAdjustmentDetails = new RequestorAdjustmentDetails();
            reqAdjustmentDetails.AdjustmentDate = DateTime.Now.ToString();
            reqAdjustmentDetails.Note = "test";

            reqAdjustmentDetails.Reason = "test";
                
            reqAdjustmentDetails.RequestorSeq = request.Requestor.Id;
            reqAdjustmentDetails.UnappliedAmount = 80;
            reqAdjustmentDetails.AdjustmentType = McK.EIG.ROI.Client.Requestors.Model.AdjustmentType.BILLING_ADJUSTMENT;
            reqAdjustmentDetails.Amount = 10;
            Assert.IsTrue(requestorInvoiceList != null);
        }

        /// <summary>
        /// Test case for Update invoice payment
        /// </summary>
        [Test]
        public void Test19UpdateInvoicePayment()
        {
            Collection<RequestInvoiceDetail> reqInvoiceList = RequestorController.Instance.RetrieveRequestorInvoices(request.Requestor.Id);
            requestorInvoiceList.RequestorInvoices[0].PaymentId = reqInvoiceList[0].ReqAdjPay[0].PaymentId;
            RequestorController.Instance.UpdateInvoicePayment(requestorInvoiceList);
        }

        /// <summary>
        /// Test case for Retrieve past invoice
        /// </summary>
        [Test]
        public void Test20RetrieveRequestorPastInvoice()
        {
            pastInvoices = RequestorController.Instance.RetrieveRequestorPastInvoiceDetails(request.Requestor.Id);
            Assert.IsTrue(pastInvoices.Count > 0);
        }

        /// <summary>
        /// Test case for Retrieve past invoice
        /// </summary>
        [Test]
        public void Test21CreateAndGenerateRequestorStatement()
        {
            RequestorStatementDetail requestorStatementDetail = new RequestorStatementDetail();
            TestBillingController testBillingController = new TestBillingController();
            testBillingController.Initlogon();
            LetterTemplateDetails requestorLetterTemplate = testBillingController.RetrieveDocumentID(LetterType.RequestorStatement);
            requestorStatementDetail.TemplateFileId = requestorLetterTemplate.DocumentId;
            requestorStatementDetail.TemplateName = requestorLetterTemplate.FileName;
            requestorStatementDetail.DateRange = (McK.EIG.ROI.Client.Requestors.Model.DateRange)Enum.Parse(typeof(McK.EIG.ROI.Client.Requestors.Model.DateRange), McK.EIG.ROI.Client.Requestors.Model.DateRange.DAYS_120.ToString());
            requestorStatementDetail.Notes = new string[] {"Notes1", "Notes2"};
            List<long> regeneratedInvoiceIds = new List<long>();
            requestorStatementDetail.PastInvIds = regeneratedInvoiceIds.ToArray();
            requestorStatementDetail.RequestorId = request.Requestor.Id;

            DocumentInfoList documentInfoList = RequestorController.Instance.GenerateRequestorStatement(requestorStatementDetail);
            Assert.IsNotNull(documentInfoList);
            requestorStatementDetail.OutputMethod = OutputMethod.SaveAsFile.ToString();
            requestorStatementDetail.QueuePassword = "1234";
            long docId = RequestorController.Instance.CreateRequestorStatement(requestorStatementDetail);
            Assert.IsTrue(docId > 0);
        }

        /// <summary>
        /// Test case for Create unapplied invoice payment
        /// </summary>
        [Test]
        public void Test22CreateUnAppliedInvoicePayment()
        {
            Test01CreateRequestor();
            RequestorInvoiceList requestorInvoiceList = new RequestorInvoiceList();
            requestorInvoiceList.Description = "Description";
            requestorInvoiceList.PaymentAmount = 100;
            requestorInvoiceList.PaymentDate = DateTime.Now;
            requestorInvoiceList.PaymentId = 0;
            requestorInvoiceList.PaymentMode = "Check";
            requestorInvoiceList.RequestorId = newRequestor.Id;
            requestorInvoiceList.UnAppliedAmount = 90;
            RequestorController.Instance.CreateInvoicePayment(requestorInvoiceList);
        }

        /// <summary>
        /// Test case for Create requestor refund
        /// </summary>
        [Test]
        public void Test23CreateRequestorRefund()
        {
            RequestorRefundDetail requestorRefundDetail = new RequestorRefundDetail();
            requestorRefundDetail.RequestorID = newRequestor.Id;
            requestorRefundDetail.RequestorName = newRequestor.LastName;
            requestorRefundDetail.RequestorType = newRequestor.TypeName;
            requestorRefundDetail.RefundAmount = 10;
            requestorRefundDetail.Note = "test";
            requestorRefundDetail.RefundDate = DateTime.Now;
            docInfoList = RequestorController.Instance.CreateRequestorRefund(requestorRefundDetail, false);
        }

        /// <summary>
        /// Test Case for Submit output request
        /// </summary>
        [Test]
        public void Test24SubmitOutputRequest()
        {
            OutputPropertyDetails outputPropertyDetails = OutputController.Instance.RetrieveDestinations(DestinationType.File.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
            OutputRequestDetails outputRequestDetails = new OutputRequestDetails(0, 0, string.Empty, null);
            OutputViewDetails outputViewDetails = new OutputViewDetails();

            outputRequestDetails.OutputDestinationDetails = outputPropertyDetails.OutputDestinationDetails[0];
            outputViewDetails = outputPropertyDetails.OutputViewDetails;
            string outputMethod = outputRequestDetails.OutputDestinationDetails.Type;
            DestinationType destinationType = new DestinationType();
            destinationType = DestinationType.File;
            foreach (DocInfo info in docInfoList.docInfos)
            {
                outputRequestDetails.RequestParts.Add(BuildROIRequestPartDetails(info));
            }
            OutputController.Instance.SubmitOutputRequest(outputRequestDetails, destinationType,
                                                                  outputViewDetails, false, false);
        }

        /// <summary>
        /// Test case for view requestor refund
        /// </summary>
        [Test]
        public void Test25ViewRequestorRefund()
        {
            RequestorRefundDetail requestorRefundDetail = new RequestorRefundDetail();
            requestorRefundDetail.RequestorID = newRequestor.Id;
            requestorRefundDetail.RequestorName = newRequestor.LastName;
            requestorRefundDetail.RequestorType = newRequestor.TypeName;
            requestorRefundDetail.RefundAmount = 10;
            requestorRefundDetail.RefundDate = DateTime.Now;
            requestorRefundDetail.Note = "test";
            TestBillingController testBillingController = new TestBillingController();
            LetterTemplateDetails refundLetterTemplate = RetrieveDocumentID(LetterType.Other);
            requestorRefundDetail.TemplateId = refundLetterTemplate.DocumentId;
            requestorRefundDetail.TemplateName = refundLetterTemplate.FileName;
            requestorRefundDetail.Notes = new string[] { "Notes1", "Notes2" };

            RequestorStatementDetail requestorStatementDetail = new RequestorStatementDetail();

            LetterTemplateDetails summaryLetterTemplate = RetrieveDocumentID(LetterType.RequestorStatement);
            string requestorLetterTemplateName = (summaryLetterTemplate != null) ? summaryLetterTemplate.Name : string.Empty;
            requestorStatementDetail.TemplateFileId = summaryLetterTemplate.DocumentId;
            requestorStatementDetail.TemplateName = summaryLetterTemplate.FileName;
            requestorStatementDetail.DateRange = (McK.EIG.ROI.Client.Requestors.Model.DateRange)Enum.Parse(typeof(McK.EIG.ROI.Client.Requestors.Model.DateRange), McK.EIG.ROI.Client.Requestors.Model.DateRange.DAYS_120.ToString());

            requestorStatementDetail.Notes = new string[] { "Notes1", "Notest" };
                
            requestorStatementDetail.RequestorId = newRequestor.Id;
            requestorRefundDetail.RequestorStatementDetail = requestorStatementDetail;

            DocumentInfoList documentInfoList = RequestorController.Instance.ViewRequestorRefund(requestorRefundDetail);
            Assert.IsTrue(documentInfoList.DocumentInfoCollection.Count > 0);
            
        }

        private RequestPartDetails BuildROIRequestPartDetails(DocInfo documentInfo)
        {
            RequestPartDetails requestPartDetails = new RequestPartDetails();

            //Unique content id is used for coverletter is included or not
            if (string.Compare(documentInfo.type, LetterType.Invoice.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) != 0)
            {
                requestPartDetails.ContentId = documentInfo.id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            else
            {
                requestPartDetails.ContentId = string.Empty;
            }
            requestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

            PropertyDetails propertyDetails = new PropertyDetails();
            propertyDetails.FileIds = documentInfo.type + "." + documentInfo.id;

            requestPartDetails.PropertyLists.Add(propertyDetails);

            return requestPartDetails;
        }

        private string GetEPNWithPrefix(string epn)
        {
            return (!String.IsNullOrEmpty(UserData.Instance.EpnPrefix)) ? UserData.Instance.EpnPrefix.Trim() + epn : epn;
        }

        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString().Replace('.',' ');
        }

        public RequestorDetails RequestorDetails
        {
            get
            {
               return newRequestor;               
            }            
        }
        
        public LetterTemplateDetails RetrieveDocumentID(LetterType letterType)
        {
            foreach (LetterTemplateDetails details in letterTemplates)
            {
                if (details.LetterType == letterType)
                {
                    return details;
                }
            }

            return new LetterTemplateDetails();
        }

        #endregion
    }
}
