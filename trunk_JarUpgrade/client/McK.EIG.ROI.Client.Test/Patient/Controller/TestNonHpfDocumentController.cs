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
using System.Configuration;
using System.Collections.ObjectModel;
using System.Globalization;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Test.Requestors.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Patient.Controller
{
    /// <summary>
    /// Test case for NonHpfDocumentController.
    /// </summary>
    [TestFixture]
    [Category("TestNonHpfDocumentController")]
    public class TestNonHpfDocumentController : TestBase
    {
        #region Fields

        private PatientController patientController;
        private Collection<NonHpfDocumentDetails> nonHpfDocuments;
        private PatientDetails patient;
        private Collection<string> documents;
        private Collection<string> departments;
        private long nonHPFDocumentID;
        private long nonHPFDocumentIDHpfPatient;
        private RequestDetails newRequest;
        private RequestController requestController;
        private string requestPassword;
        private long id;

        #endregion

        #region Methods

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
        
        public void Initlogon()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);

            patientController = PatientController.Instance;
            RetrieveAllDocumentTypes();

            patient = new PatientDetails();

            patient.LastName = "LastPatient" + GetUniqueId();
            patient.FirstName = "FirstPatient" + GetUniqueId();
            patient.FullName = patient.LastName + ", " + patient.FirstName;
            patient.Gender = Gender.Male;
            patient.DOB = DateTime.Now.Date;
            patient.IsVip = false;
            patient.SSN = "111-11-1111";
            patient.EPN = GetEPNWithPrefix("1234");
            patient.FacilityCode = "Test";
            patient.IsFreeformFacility = true;
            patient.MRN = "2222";
            patient.HomePhone = "1111-1111";
            patient.WorkPhone = "2222-2222";

            AddressDetails addressDetail = new AddressDetails();
            addressDetail.Address1 = "Address 1";
            addressDetail.Address2 = "Address 2";
            addressDetail.Address3 = "Address 3";
            addressDetail.City = "Test City";
            addressDetail.State = "Test State";
            addressDetail.PostalCode = "11111";
            patient.Address = addressDetail;

            patient.Id = patientController.CreateSupplementalPatient(patient);
            nonHpfDocuments = new Collection<NonHpfDocumentDetails>();
            
        }
        
        public void RetrieveAllDocumentTypes()
        {
            Collection<RecordViewDetails> recordViews = UserData.Instance.RecordViews;
            documents = new Collection<string>();
            foreach (RecordViewDetails recordViewDetails in recordViews)
            {
                foreach (string docName in recordViewDetails.DocTypes)
                {
                    if (!documents.Contains(docName.Trim()))
                    {
                        documents.Add(docName);
                    }
                }
            }
        }

        /// <summary>
        /// Test case Retirve all document types.
        /// </summary>
        [Test]
        public void Test01RetrieveAllDepartments()
        {
            Initlogon();
            departments = patientController.RetrieveAllDepartments();
            Assert.IsTrue(departments.Count >= 0);
        }

        /// <summary>
        /// Test Case to create NonHpf Document for nonHPF Patient.
        /// </summary>
        [Test]
        public void Test02AddSupplementalDocuments()
        {
            Initlogon();
            NonHpfDocumentDetails nonHpfDocument = new NonHpfDocumentDetails();
            nonHpfDocument.Id = ++patient.NonHpfDocumentRecentSeq;
            nonHpfDocument.DocType = documents[0];
            nonHpfDocument.Encounter = DateTime.Now.Ticks.ToString();
            nonHpfDocument.Subtitle = documents[0];
            nonHpfDocument.DateOfService = DateTime.Now.Date;
            nonHpfDocument.Department = departments[0];
            nonHpfDocument.FacilityCode = "AWL";
            nonHpfDocument.IsFreeformFacility = true;
            nonHpfDocument.PatientId = patient.Id;
            nonHpfDocument.PageCount = 2;    
            nonHpfDocument.Comment = "Document created from Test Case";
            nonHpfDocument.Mode = Mode.Create;
            patient.ModifiedNonHpfDocument = nonHpfDocument;

            patient.NonHpfDocuments.AddDocument(nonHpfDocument);
            patient.Address = null;
            patient.AuditMessage = PatientNonHpfDocument.PrepareAuditMessage(nonHpfDocument, patient.NonHpfDocuments);

            nonHpfDocument.Id = patientController.CreateSupplementalDocument(nonHpfDocument);
            nonHPFDocumentID = nonHpfDocument.Id;
            Assert.IsTrue(nonHpfDocument.Id > 0);
            patient = patientController.RetrieveSuppmentalDocuments(patient);
            Assert.IsTrue(patient.NonHpfDocuments.GetChildren.Count > 0);
        }

        /// <summary>
        /// Test Case to update NonHpf Document for nonHPF patient
        /// </summary>
        [Test]
        public void Test03UpdateSupplementalDocuments()
        {
            Initlogon();
            NonHpfDocumentDetails nonHpfDocument = new NonHpfDocumentDetails();
            //nonHpfDocument.Id = ++patient.NonHpfDocumentRecentSeq;
            nonHpfDocument.Id = nonHPFDocumentID;
            nonHpfDocument.DocType = documents[0];
            nonHpfDocument.Encounter = DateTime.Now.Ticks.ToString();
            nonHpfDocument.Subtitle = documents[0];
            nonHpfDocument.DateOfService = DateTime.Now.Date;
            nonHpfDocument.Department = departments[0];
            nonHpfDocument.FacilityCode = "AWL";
            nonHpfDocument.IsFreeformFacility = false;
            nonHpfDocument.PatientId = patient.Id;
            nonHpfDocument.PatientKey = patient.Id.ToString();
            nonHpfDocument.PageCount = 4;
            nonHpfDocument.Comment = "Document created from Test Case";
            nonHpfDocument.Mode = Mode.Edit;
            patient.ModifiedNonHpfDocument = nonHpfDocument;
            
            patient.NonHpfDocuments.AddDocument(nonHpfDocument);
            patient.Address = null;
            patient.AuditMessage = PatientNonHpfDocument.PrepareAuditMessage(nonHpfDocument, patient.NonHpfDocuments);
            patientController.UpdateSupplementalDocument(nonHpfDocument);
            nonHPFDocumentID = nonHpfDocument.Id;
            Assert.IsTrue(nonHpfDocument.Id > 0);
        }

        /// <summary>
        /// Test Case for Create Request.
        /// </summary>
        [Test]
        public void Test04CreateRequest()
        {
            requestController = RequestController.Instance;

            TestRequestorController requestorController = new TestRequestorController();
            requestorController.Test01CreateRequestor();

            newRequest = new RequestDetails();
            newRequest.RequestPassword = requestPassword;
            newRequest.Requestor = requestorController.RequestorDetails;
            newRequest.Requestor.TypeName = "Insurance";
            newRequest.RequestorName = (requestorController.RequestorDetails.LastName + ", " + requestorController.RequestorDetails.FirstName).Trim(',', ' ');
            newRequest.RequestorTypeName = requestorController.RequestorDetails.Type.ToString();
            newRequest.RequestorWorkPhone = requestorController.RequestorDetails.WorkPhone;
            newRequest.IsReleased = false;
            newRequest.RequestorContactName = "John Smith";
            newRequest.RequestorContactPhone = "20612100";
            newRequest.RequestorFax = "2423423423";
            newRequest.RequestorHomePhone = "2699901";
            newRequest.RequestorWorkPhone = "8990777";
            newRequest.RequestReason = "Migration";
            newRequest.ReceiptDate = new DateTime(2007, 08, 08);
            newRequest.Status = RequestStatus.Logged;
            newRequest.StatusChanged = DateTime.Now.Date;
            newRequest.StatusReason = "Logged Reason1";
            newRequest.RequestorId = requestorController.RequestorDetails.Id;
            newRequest.BillToAddressLine = "No.30:sai colony";
            newRequest.BillToCity = "chennai";
            newRequest.BillToContactNameFirst = "John";
            newRequest.BillToContactNameLast = "Smith";
            newRequest.BillToPostalCode = "605111";
            newRequest.BillToState = "TN";
            newRequest.BalanceDue = 0.0;
            newRequest.AuthRequest = "Auth doc Id for testing purpose.";
            newRequest.AuthRequestSubtitle = "Temp Auth Subtitle.";
            newRequest.AuthRequestDocumentName = "Temp Auth Doc Name.";
            newRequest.AuthRequestDocumentDateTime = "12/12/2005";

            newRequest = requestController.CreateRequest(newRequest);
            id = newRequest.Id;
            Assert.IsTrue(id > 0);
            Assert.IsNotNull(newRequest);
        }

         /// <summary>
        /// Test Case for Delete NonHpf Document for nonHPFPatient.
        /// </summary>
        [Test]
        public void Test05DeleteSupplementalDocuments()
        {
            patientController.DeleteSupplementalDocument(nonHPFDocumentID, id);
        }

        /// <summary>
        /// Test Case to add NonHpf Document for HPFPatient
        /// </summary>
        [Test]
        public void Test06AddSupplementarityDocuments()  
        {
            //Retrieve Document Types
            RetrieveAllDocumentTypes();

            //Retrieve Patient
            FindPatientCriteria patientSearchCriteria = new FindPatientCriteria();
            patient = new PatientDetails();
            PatientDetails oldPatient = new PatientDetails();

            patientSearchCriteria.FirstName = string.Empty;
            patientSearchCriteria.LastName = string.Empty;
            patientSearchCriteria.Dob = null;
            patientSearchCriteria.EPN = string.Empty;
            patientSearchCriteria.FacilityCode = TestBase.PatientFacility;
            patientSearchCriteria.MRN = TestBase.PatientMrn;
            patientSearchCriteria.SSN = string.Empty;
            patientSearchCriteria.Encounter = string.Empty;
            patientSearchCriteria.MaxRecord = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);
            FindPatientResult result = patientController.FindPatient(patientSearchCriteria);
            foreach (PatientDetails patientDetails in result.PatientSearchResult)
            {
                if (patientDetails.IsHpf)
                {
                    this.patient = patientDetails;
                    break;
                }
            }
            patient = PatientController.Instance.RetrieveHpfPatient(patient.MRN, patient.FacilityCode);
            
            NonHpfDocumentDetails nonHpfDoc1 = new NonHpfDocumentDetails();
            nonHpfDoc1.Id = 0;
            nonHpfDoc1.DocType = documents[0];
            nonHpfDoc1.Subtitle = documents[0];
            nonHpfDoc1.PageCount = 3;
            nonHpfDoc1.Encounter = "111";
            nonHpfDoc1.DateOfService = DateTime.Now.Date;
            nonHpfDoc1.Department = departments[0];
            nonHpfDoc1.FacilityCode = UserData.Instance.Facilities[0].Code;
            nonHpfDoc1.FacilityType = FacilityType.NonHpf;
            nonHpfDoc1.PatientId = patient.Id;
            nonHpfDoc1.RecordVersionId = 0;
            nonHpfDoc1.Comment = "Document created from Test Case";
            nonHpfDoc1.Mode = Mode.Create;
            nonHpfDoc1.PatientMrn = TestBase.PatientMrn;
            nonHpfDoc1.PatientFacility = TestBase.PatientFacility;
            nonHpfDocuments.Add(nonHpfDoc1);

            patient.ModifiedNonHpfDocument = nonHpfDoc1;
            patient.NonHpfDocuments.AddDocument(nonHpfDoc1);

            nonHpfDoc1.Id = patientController.CreateSupplementarityDocument(nonHpfDoc1);
            nonHPFDocumentIDHpfPatient = nonHpfDoc1.Id;
            Assert.IsTrue(nonHpfDoc1.Id > 0);
        }

        /// <summary>
        /// Test Case for update NonHpf Document for HPFPatient.
        /// </summary>
        [Test]
        public void Test07UpdateSupplementarityDocuments()
        {
            NonHpfDocumentDetails nonHpfDoc1 = new NonHpfDocumentDetails();
            nonHpfDoc1.Id = nonHPFDocumentIDHpfPatient;
            nonHpfDoc1.DocType = documents[0];
            nonHpfDoc1.Subtitle = documents[0];
            nonHpfDoc1.PageCount = 4;
            nonHpfDoc1.Encounter = "111";
            nonHpfDoc1.DateOfService = DateTime.Now.Date;
            nonHpfDoc1.Department = departments[0];
            nonHpfDoc1.FacilityCode = UserData.Instance.Facilities[0].Code;
            nonHpfDoc1.FacilityType = FacilityType.NonHpf;
            nonHpfDoc1.PatientId = patient.Id;
            nonHpfDoc1.RecordVersionId = 0;
            nonHpfDoc1.Comment = "Document created from Test Case";
            nonHpfDoc1.PatientMrn = TestBase.PatientMrn;
            nonHpfDoc1.PatientFacility = TestBase.PatientFacility;
            nonHpfDoc1.Mode = Mode.Edit;
            nonHpfDocuments.Add(nonHpfDoc1);

            patient.ModifiedNonHpfDocument = nonHpfDoc1;
            patient.NonHpfDocuments.AddDocument(nonHpfDoc1);
            patientController.UpdateSupplementarityDocument(nonHpfDoc1);
            Assert.IsTrue(nonHpfDoc1.Id > 0);
        }

        /// <summary>
        /// Test Case to retrieve NonHPF document for HPFPatient
        /// </summary>
        [Test]
        public void Test08RetrieveSupplementarityDocuments()
        {
            patient = PatientController.Instance.RetrieveSuppmentarityDocuments(patient);
            Assert.IsTrue(patient.NonHpfDocuments.GetChildren.Count > 0);
        }

        /// <summary>
        /// Test Case to delete NonHPF document for HPFPatient
        /// </summary>
        [Test]
        public void Test09DeleteSupplementarityDocuments()
        {
            patientController.DeleteSupplementarityDocument(nonHPFDocumentIDHpfPatient, id, 0);
        }

        #endregion

        private string GetEPNWithPrefix(string epn)
        {
            return (UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix.Trim() + epn : epn;
        }

        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString().Replace('.', ' ');
        }

        #endregion

    }
}
