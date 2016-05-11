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
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Test.Requestors.Controller;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Patient.Controller
{
    /// <summary>
    /// Test case for AttachmentController.
    /// </summary>
    [TestFixture]
    [Category("TestAttachmentController")]
    public class TestAttachmentController : TestBase
    {
        #region Fields

        private PatientController patientController;
        private Collection<AttachmentDetails> attachment;
        private PatientDetails patient;
        private Collection<string> documents;
        private Collection<string> departments;
        private long attachmentID;
        private long attachmentIDHpfPatient;
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

            //patient.Id = patientController.CreateSupplemental(patient);
            patient.Id = patientController.CreateSupplementalPatient(patient);
            attachment = new Collection<AttachmentDetails>();

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
        /// Test Case to add Attachment for nonHPFPatient
        /// </summary>
        [Test]
        public void Test01AddSupplementalAttachment()
        {
            Initlogon();
            AttachmentDetails attachment = new AttachmentDetails();
            attachment.AttachmentType = "Local File";
            attachment.Encounter = DateTime.Now.Ticks.ToString();
            attachment.Subtitle = documents[0];
            attachment.FacilityCode = "Apollo" + DateTime.Now.Ticks.ToString();
            attachment.IsDeleted = false;
            attachment.PageCount = 2;
            attachment.DateOfService = DateTime.Now.Date;
            attachment.AttachmentDate = DateTime.Now.Date;
            AttachmentFileDetails attachmentFileDetails = new AttachmentFileDetails();
            attachmentFileDetails.Uuid = "123";
            attachmentFileDetails.PageCount = 2;
            attachmentFileDetails.FileName = "test";
            attachmentFileDetails.FileType = "PDF";
            attachmentFileDetails.Extension = "pdf";
            attachmentFileDetails.Printable = true;
            attachment.FileDetails = attachmentFileDetails;
            attachment.Comment = "Attachment created for test case";
            attachment.PatientId = patient.Id;
               
            patient.Attachments.AddAttachment(attachment);
            patient.Address = null;
            patient.AuditMessage = PatientAttachment.PrepareAuditMessage(attachment);

            //patient = patientController.UpdateSupplemental(patient);
            attachment = patientController.CreateSupplementalAttachment(attachment);
            attachmentID = attachment.Id;
            //Assert.IsTrue(patient.NonHpfDocuments.GetChildren.Count > 0);
            Assert.IsTrue(attachment.Id > 0);
        }

        /// <summary>
        /// Test Case to retrieve Attachment for nonHPFPatient
        /// </summary>
        [Test]
        public void Test02RetrieveSupplementalAttachment()
        {
            Initlogon();
            patient = patientController.RetrieveSuppmentalAttachments(patient);
            Assert.IsTrue(patient.Attachments.GetChildren.Count > 0);
        }

        /// <summary>
        /// Test Case to update Attachment for nonHPFPatient
        /// </summary>
        [Test]
        public void Test03UpdateSupplementalDocument()
        {
            Initlogon();
            AttachmentDetails attachment = new AttachmentDetails();
            attachment.Id = attachmentID;
            attachment.AttachmentType = "Local File";
            attachment.Encounter = DateTime.Now.Ticks.ToString();
            attachment.Subtitle = documents[0];
            attachment.FacilityCode = "Apollo" + DateTime.Now.Ticks.ToString();
            attachment.IsDeleted = false;
            attachment.PageCount = 3;
            attachment.DateOfService = DateTime.Now.Date;
            attachment.AttachmentDate = DateTime.Now.Date;
            AttachmentFileDetails attachmentFileDetails = new AttachmentFileDetails();
            attachmentFileDetails.Uuid = "123";
            attachmentFileDetails.PageCount = 3;
            attachmentFileDetails.FileName = "test";
            attachmentFileDetails.FileType = "PDF";
            attachmentFileDetails.Extension = "pdf";
            attachmentFileDetails.Printable = true;
            attachment.FileDetails = attachmentFileDetails;
            attachment.Comment = "Attachment created for test case";
            attachment.PatientId = patient.Id;

            patient.Attachments.AddAttachment(attachment);
            patient.Address = null;
            patient.AuditMessage = PatientAttachment.PrepareAuditMessage(attachment);
            patientController.UpdateSupplementalAttachment(attachment);
            attachmentID = attachment.Id;
            Assert.IsTrue(attachment.Id > 0);
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
        /// Test Case to delete Attachment for nonHPFPatient
        /// </summary>
        [Test]
        public void Test05DeleteSupplementalDocument()
        {
            patientController.DeleteSupplementalDocument(attachmentID, id);
        }

        /// <summary>
        /// Test Case to add Attachment for HPFPatient
        /// </summary>
        [Test]
        public void Test06AddSupplementarityAttachment()
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

            AttachmentDetails attachment = new AttachmentDetails();
            attachment.AttachmentType = "Local File";
            attachment.Encounter = DateTime.Now.Ticks.ToString();
            attachment.Subtitle = documents[0];
            attachment.FacilityCode = "Apollo" + DateTime.Now.Ticks.ToString();
            attachment.IsDeleted = false;
            attachment.PageCount = 2;
            attachment.DateOfService = DateTime.Now.Date;
            attachment.AttachmentDate = DateTime.Now.Date;
            AttachmentFileDetails attachmentFileDetails = new AttachmentFileDetails();
            attachmentFileDetails.Uuid = "123";
            attachmentFileDetails.PageCount = 2;
            attachmentFileDetails.FileName = "test";
            attachmentFileDetails.FileType = "PDF";
            attachmentFileDetails.Extension = "pdf";
            attachmentFileDetails.Printable = true;
            attachment.FileDetails = attachmentFileDetails;
            attachment.Comment = "Attachment created for test case";
            attachment.PatientId = patient.Id;
            attachment.PatientMrn = TestBase.PatientMrn;

            patient.Attachments.AddAttachment(attachment);
            patient.Address = null;
            patient.AuditMessage = PatientAttachment.PrepareAuditMessage(attachment);

            attachment = patientController.CreateSupplementarityAttachment(attachment);
            attachmentIDHpfPatient = attachment.Id;
            Assert.IsTrue(attachment.Id > 0);

        }

        /// <summary>
        /// Test Case to retrieve Attachment for HPFPatient
        /// </summary>
        [Test]
        public void Test07RetrieveSupplementarityAttachment()
        {
            patient = PatientController.Instance.RetrieveSuppmentarityAttachments(patient);
            Assert.IsTrue(patient.NonHpfDocuments.GetChildren.Count > 0);
        }

        /// <summary>
        /// Test Case to update Attachment for HPFPatient
        /// </summary>
        [Test]
        public void Test08UpdateSupplementarityAttachment()
        {
            AttachmentDetails attachment = new AttachmentDetails();
            attachment.Id = attachmentIDHpfPatient;
            attachment.AttachmentType = "Local File";
            attachment.Encounter = DateTime.Now.Ticks.ToString();
            attachment.Subtitle = documents[0];
            attachment.FacilityCode = "Apollo" + DateTime.Now.Ticks.ToString();
            attachment.IsDeleted = false;
            attachment.PageCount = 2;
            attachment.DateOfService = DateTime.Now.Date;
            attachment.AttachmentDate = DateTime.Now.Date;
            AttachmentFileDetails attachmentFileDetails = new AttachmentFileDetails();
            attachmentFileDetails.Uuid = "123";
            attachmentFileDetails.PageCount = 2;
            attachmentFileDetails.FileName = "test";
            attachmentFileDetails.FileType = "PDF";
            attachmentFileDetails.Extension = "pdf";
            attachmentFileDetails.Printable = true;
            attachment.FileDetails = attachmentFileDetails;
            attachment.Comment = "Attachment created for test case";
            attachment.PatientId = patient.Id;
            attachment.PatientMrn = TestBase.PatientMrn;

            patient.Attachments.AddAttachment(attachment);
            patient.Address = null;
            patient.AuditMessage = PatientAttachment.PrepareAuditMessage(attachment);
            patientController.UpdateSupplementarityAttachment(attachment);
            attachmentIDHpfPatient = attachment.Id;
            Assert.IsTrue(attachment.Id > 0);
        }

        /// <summary>
        /// Test Case to delete Attachment for HPFPatient
        /// </summary>
        [Test]
        public void Test09DeleteSupplementarityAttachment()
        {
            patientController.DeleteSupplementarityAttachment(attachmentIDHpfPatient, id, 0);
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
