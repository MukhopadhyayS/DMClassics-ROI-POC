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
using System.Configuration;
using System.Globalization;

//NUnit
using NUnit.Framework;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;

using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Test.Patient.Controller;
using McK.EIG.ROI.Client.Test.Requestors.Controller;
using McK.EIG.ROI.Client.Web_References.HPFPatientWS;
using McK.EIG.ROI.Client.Request.View.FindRequest;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Request.Controller
{
    /// <summary>
    /// Test Case for Request Patient Controller.
    /// </summary>
    [TestFixture]
    public class TestRequestPatientController : TestBase
    {
        #region Fields

        private RequestController requestController;
        private PatientController patientController;
        private RequestDetails updateRequest;
        private PatientDetails details;
        private long id;
        private string requestPassword;
        private Collection<NonHpfDocumentDetails> nonHpfDocuments;
        private Collection<string> documents;
        private Collection<string> departments;
        private RequestDetails newRequest;
        private string nonHpfPatientkey;
        #endregion

        #region Constructor

        public TestRequestPatientController()
        {
            requestController = RequestController.Instance;
        }

        #endregion

        #region Nunit

        [SetUp]
        public void Init()
        {
            requestController = RequestController.Instance;
            patientController = PatientController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            requestController = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Test case for retrieve request level password for PDF queue.
        /// </summary>
        /// <returns></returns>
        [Test]
        public void Test00RetrieveGeneratedPassword()
        {
            object[] requestParams = new object[0];
            string generatedPassword = requestController.RetrieveGeneratedPassword();
            Assert.IsNotNull(generatedPassword);
        }
        
        /// <summary>
        /// Test Case for Create Request.
        /// </summary>
        [Test]
        public void Test01CreateRequest()
        {
            requestController = RequestController.Instance;

            TestRequestorController requestorController = new TestRequestorController();
            requestorController.Test01CreateRequestor();

            newRequest = new RequestDetails();
            newRequest.RequestPassword = requestPassword;            
            newRequest.Requestor = requestorController.RequestorDetails;
            requestorController.RequestorDetails.LastName = "last name";
            requestorController.RequestorDetails.FirstName = "first name";
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
            newRequest.AuthRequestDocumentName  = "Temp Auth Doc Name.";
            newRequest.AuthRequestDocumentDateTime = "12/12/2005";

            newRequest = requestController.CreateRequest(newRequest);
            id = newRequest.Id;
            Assert.IsTrue(id > 0);
            Assert.IsNotNull(newRequest);
        }

        /// <summary>
        /// Test case to retrieve the request
        /// </summary>
        [Test]
        public void Test02RetrieveRequest()
        {
            RequestDetails request = requestController.RetrieveRequest(id, false);
            Assert.AreEqual(newRequest.Status, request.Status);
            Assert.AreEqual(newRequest.StatusReason, request.StatusReason);
            Assert.AreEqual(newRequest.RequestorId, request.RequestorId);
        }


        /// <summary>
        /// Test Case for Update Requests.
        /// </summary>
        [Test]
        public void Test03UpdateRequests()
        {
            newRequest.RequestorName = "Smith";
            newRequest.RequestorTypeName = "Patient";
            newRequest.RequestorWorkPhone = "+18602777790";
            newRequest.IsReleased = false;
            newRequest.RequestorContactName = "John Smith";
            newRequest.RequestorContactPhone = "20612100";
            newRequest.RequestorFax = "2423423423";
            newRequest.RequestorHomePhone = "2699901";
            newRequest.RequestorWorkPhone = "8990777";
            newRequest.RequestReason = "Migration";
            newRequest.ReceiptDate = new DateTime(2007, 08, 08);
            newRequest.Status = RequestStatus.Logged;
            newRequest.RequestorId = 11;
            newRequest.BillToAddressLine = "No.3o:sai colony";
            newRequest.BillToCity = "Kovai";
            newRequest.BillToContactNameFirst = "John";
            newRequest.BillToContactNameLast = "Smith";
            newRequest.BillToPostalCode = "605111";
            newRequest.BillToState = "TN";
            updateRequest = requestController.UpdateRequest(newRequest);
            Assert.AreNotEqual(newRequest.RecordVersionId, updateRequest.RecordVersionId);
        }

        public long GetRequestId()
        {
            return newRequest.Id;
        }

        /// <summary>
        /// Test case to save the request patient
        /// </summary>
        [Test]
        public void Test04SaveRequestPatient()
        {

            FindPatientCriteria patientSearchCriteria = new FindPatientCriteria();
            patientSearchCriteria.FirstName = string.Empty;
            patientSearchCriteria.LastName = string.Empty;
            patientSearchCriteria.Dob = null;
            patientSearchCriteria.EPN = string.Empty;
            patientSearchCriteria.FacilityCode = TestBase.PatientFacility;
            patientSearchCriteria.MRN = TestBase.PatientMrn;
            patientSearchCriteria.SSN = string.Empty;
            patientSearchCriteria.Encounter = string.Empty;
            patientSearchCriteria.MaxRecord = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);
            if (patientController == null)
            {
                patientController = PatientController.Instance;
            }
            FindPatientResult result = patientController.FindPatient(patientSearchCriteria);
            foreach (PatientDetails patientDetails in result.PatientSearchResult)
            {
                if (patientDetails.IsHpf)
                {
                    details = patientDetails;
                    break;
                }
            }
            PatientDetails retrievedHPFPatientInfo = patientController.RetrieveHpfPatient(details.MRN, details.FacilityCode);

            retrievedHPFPatientInfo = patientController.RetrieveHpfDocuments(details);

            
            RequestPageDetails requestPageDetails = null;
            RequestVersionDetails requestVersionDetails = null;
            RequestDocumentDetails requestDocDetails = null;
            RequestEncounterDetails requestEncounterDetails = null;
            retrievedHPFPatientInfo.Address = new AddressDetails();
            retrievedHPFPatientInfo.Address.Address1 = new RequestPatientDetails().Address1;
            retrievedHPFPatientInfo.Address.Address2 = new RequestPatientDetails().Address2;
            retrievedHPFPatientInfo.Address.Address3 = new RequestPatientDetails().Address3;
            retrievedHPFPatientInfo.Address.City = new RequestPatientDetails().City;
            retrievedHPFPatientInfo.Address.PostalCode = new RequestPatientDetails().PostalCode;
            retrievedHPFPatientInfo.Address.PostalCode = new RequestPatientDetails().HomePhone;
            retrievedHPFPatientInfo.Address.PostalCode = new RequestPatientDetails().WorkPhone;
            retrievedHPFPatientInfo.Address.Address1 = "Address1";
            retrievedHPFPatientInfo.Address.Address2 = "address2";
            retrievedHPFPatientInfo.Address.Address3 = "address3";
            retrievedHPFPatientInfo.Address.City = "city";
            retrievedHPFPatientInfo.Address.State = "state";
            retrievedHPFPatientInfo.Address.PostalCode = "12346";

            RequestPatientDetails requestPatientDetail = new RequestPatientDetails(retrievedHPFPatientInfo);

            foreach (EncounterDetails encounterDetails in retrievedHPFPatientInfo.Encounters)
            {
                requestEncounterDetails = new RequestEncounterDetails(encounterDetails);
                foreach (DocumentDetails docDetails in encounterDetails.Documents.Values)
                {
                    requestDocDetails = new RequestDocumentDetails(docDetails);
                    foreach (VersionDetails versionDetails in docDetails.Versions.Values)
                    {
                        requestVersionDetails = new RequestVersionDetails(versionDetails);
                        foreach (PageDetails pageDetails in versionDetails.Pages.Values)
                        {
                            requestPageDetails = new RequestPageDetails(pageDetails);
                            requestVersionDetails.AddChild(requestPageDetails);
                        }

                        requestDocDetails.AddChild(requestVersionDetails);
                        requestPatientDetail.GlobalDocument.AddChild(requestDocDetails);
                    }
                    requestPatientDetail.GlobalDocument.AddChild(requestDocDetails);
                    requestEncounterDetails.AddChild(requestDocDetails);
                }
                requestPatientDetail.AddChild(requestEncounterDetails);
            }

            UpdateRequestPatients updateRequestPatient = new UpdateRequestPatients();
            DeleteList deleteList = new DeleteList();
                        
            updateRequestPatient.DeleteList = deleteList;
           
            updateRequestPatient.RequestPatients.Add(requestPatientDetail);
            RequestPatients requestPatients = RequestController.Instance.SaveRequestPatients(updateRequestPatient, id);
            foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
            {
                newRequest.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
            }
            Assert.IsTrue(newRequest.Patients.Count > 0);
            
        }

        /// <summary>
        /// Test case Retirve all document types.
        /// </summary>
        [Test]
        public void Test05RetrieveAllDepartments()
        {
            departments = patientController.RetrieveAllDepartments();
            Assert.IsTrue(departments.Count >= 0);
        }

        /// <summary>
        /// Test case to add nonHPFDocument for HPFPatient
        /// </summary>
        [Test]
        public void Test06HPFPatientWithNonHpfDocument()
        {
            RetrieveAllDocumentTypes();
            nonHpfDocuments = new Collection<NonHpfDocumentDetails>();
            NonHpfDocumentDetails nonHpfDoc1 = new NonHpfDocumentDetails();
            nonHpfDoc1.Id = ++details.NonHpfDocumentRecentSeq;
            nonHpfDoc1.DocType = documents[0];
            nonHpfDoc1.Subtitle = documents[0];
            nonHpfDoc1.PageCount = 3;
            nonHpfDoc1.Encounter = "111";
            nonHpfDoc1.DateOfService = DateTime.Now.Date;
            nonHpfDoc1.Department = departments[0];
            nonHpfDoc1.FacilityCode = UserData.Instance.Facilities[0].Code;
            nonHpfDoc1.FacilityType = FacilityType.NonHpf;
            nonHpfDoc1.PatientId = details.Id;
            nonHpfDoc1.RecordVersionId = 0;
            nonHpfDoc1.Comment = "Document created from Test Case";
            nonHpfDoc1.Mode = Mode.Create;
            nonHpfDocuments.Add(nonHpfDoc1);

            details.ModifiedNonHpfDocument = nonHpfDoc1;
            details.NonHpfDocuments.AddDocument(nonHpfDoc1);
            nonHpfDoc1.Id = patientController.CreateSupplementarityDocument(nonHpfDoc1);
            RequestPatientDetails requestPatientDetails = new RequestPatientDetails();
            requestPatientDetails.NonHpfDocument = new RequestNonHpfDocuments();

            IList<BaseRecordItem> li = new List<BaseRecordItem>();
            li.Add(nonHpfDoc1);

            RequestNonHpfDocuments requestNonHpfDoc = new RequestNonHpfDocuments();
            updateRequest.AddNonHpfDocument(li, 0);

            UpdateRequestPatients updateRequestPatient = new UpdateRequestPatients();
            DeleteList deleteList = new DeleteList();

            updateRequestPatient.DeleteList = deleteList;

            updateRequestPatient.RequestPatients.Add(requestPatientDetails);
            RequestPatients requestPatients = RequestController.Instance.SaveRequestPatients(updateRequestPatient, id);
            foreach (RequestPatientDetails requestPatientDetail in requestPatients.RequestPatientList)
            {
                newRequest.Patients.Add(requestPatientDetail.Key, requestPatientDetail);
            }
            Assert.IsTrue(newRequest.Patients.Count > 0);
        }

        /// <summary>
        /// Test case to add attachment for HPFPatient
        /// </summary>
        [Test]
        public void Test07HPFPatientWithAttachment()
        {

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
            attachment.PatientId = details.Id;
            attachment.PatientMrn = TestBase.PatientMrn;

            details.Attachments.AddAttachment(attachment);

            attachment = patientController.CreateSupplementarityAttachment(attachment);
            RequestPatientDetails requestPatientDetails = new RequestPatientDetails();
            requestPatientDetails.Attachment = new RequestAttachments();

            IList<BaseRecordItem> li = new List<BaseRecordItem>();
            li.Add(attachment);

            RequestAttachments requestAttachment = new RequestAttachments();
            updateRequest.AddAttachment(li, 0);

            UpdateRequestPatients updateRequestPatient = new UpdateRequestPatients();
            DeleteList deleteList = new DeleteList();

            updateRequestPatient.DeleteList = deleteList;

            updateRequestPatient.RequestPatients.Add(requestPatientDetails);
            RequestPatients requestPatients = RequestController.Instance.SaveRequestPatients(updateRequestPatient, id);
            foreach (RequestPatientDetails requestPatientDetail in requestPatients.RequestPatientList)
            {
                newRequest.Patients.Add(requestPatientDetail.Key, requestPatientDetail);
            }
            Assert.IsTrue(newRequest.Patients.Count > 0);
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
        /// Test case to add nonHPFDocument for NonHPFPatient
        /// </summary>
        [Test]
        public void Test08NonHPFPatientWithNonHpfDocument()
        {
            RetrieveAllDocumentTypes();

            details = new PatientDetails();

            details.LastName = "LastPatient" + GetUniqueId();
            details.FirstName = "FirstPatient" + GetUniqueId();
            details.FullName = details.LastName + ", " + details.FirstName;
            details.Gender = Gender.Male;
            details.DOB = DateTime.Now.Date;
            details.IsVip = false;
            details.SSN = "111-11-1111";
            details.EPN = GetEPNWithPrefix("1234");
            details.FacilityCode = "Test";
            details.IsFreeformFacility = true;
            details.MRN = "2222";
            details.HomePhone = "1111-1111";
            details.WorkPhone = "2222-2222";

            AddressDetails addressDetail = new AddressDetails();
            addressDetail.Address1 = "Address 1";
            addressDetail.Address2 = "Address 2";
            addressDetail.Address3 = "Address 3";
            addressDetail.City = "Test City";
            addressDetail.State = "Test State";
            addressDetail.PostalCode = "11111";
            details.Address = addressDetail;

            details.Id = patientController.CreateSupplementalPatient(details);

            NonHpfDocumentDetails nonHpfDocument = new NonHpfDocumentDetails();
            nonHpfDocument.Id = ++details.NonHpfDocumentRecentSeq;
            nonHpfDocument.DocType = documents[0];
            nonHpfDocument.Encounter = DateTime.Now.Ticks.ToString();
            nonHpfDocument.Subtitle = documents[0];
            nonHpfDocument.DateOfService = DateTime.Now.Date;
            nonHpfDocument.Department = departments[0];
            nonHpfDocument.FacilityCode = "Apollo" + DateTime.Now.Ticks.ToString();
            nonHpfDocument.IsFreeformFacility = true;
            nonHpfDocument.PatientId = details.Id;
            nonHpfDocument.PageCount = 2;
            nonHpfDocument.Comment = "Document created from Test Case";
            nonHpfDocument.Mode = Mode.Create;
            details.ModifiedNonHpfDocument = nonHpfDocument;

            details.NonHpfDocuments.AddDocument(nonHpfDocument);
            nonHpfDocument.Id = patientController.CreateSupplementalDocument(nonHpfDocument);
            RequestPatientDetails requestPatientDetails = new RequestPatientDetails();
            requestPatientDetails.NonHpfDocument = new RequestNonHpfDocuments();

            IList<BaseRecordItem> li = new List<BaseRecordItem>();
            li.Add(nonHpfDocument);

            RequestNonHpfDocuments requestNonHpfDoc = new RequestNonHpfDocuments();
            updateRequest.AddNonHpfDocument(li, 0);

            UpdateRequestPatients updateRequestPatient = new UpdateRequestPatients();
            DeleteList deleteList = new DeleteList();

            updateRequestPatient.DeleteList = deleteList;

            updateRequestPatient.RequestPatients.Add(requestPatientDetails);
            RequestPatients requestPatients = RequestController.Instance.SaveRequestPatients(updateRequestPatient, id);
            foreach (RequestPatientDetails requestPatientDetail in requestPatients.RequestPatientList)
            {
                newRequest.Patients.Add(requestPatientDetail.Key, requestPatientDetail);
            }
            Assert.IsTrue(newRequest.Patients.Count > 0);
        }

        /// <summary>
        /// Test case to add attachment for NonHPFPatient
        /// </summary>
        [Test]
        public void Test09NonHPFPatientWithAttachment()
        {

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
            attachment.PatientId = details.Id;

            details.Attachments.AddAttachment(attachment);
            attachment = patientController.CreateSupplementalAttachment(attachment);
            RequestPatientDetails requestPatientDetails = new RequestPatientDetails();
            requestPatientDetails.Attachment = new RequestAttachments();

            IList<BaseRecordItem> li = new List<BaseRecordItem>();
            li.Add(attachment);

            RequestAttachments requestAttachment = new RequestAttachments();
            updateRequest.AddAttachment(li, 0);

            UpdateRequestPatients updateRequestPatient = new UpdateRequestPatients();
            DeleteList deleteList = new DeleteList();

            updateRequestPatient.DeleteList = deleteList;

            updateRequestPatient.RequestPatients.Add(requestPatientDetails);
            RequestPatients requestPatients = RequestController.Instance.SaveRequestPatients(updateRequestPatient, id);
            foreach (RequestPatientDetails requestPatientDetail in requestPatients.RequestPatientList)
            {
                newRequest.Patients.Add(requestPatientDetail.Key, requestPatientDetail);
            }
            Assert.IsTrue(newRequest.Patients.Count > 0);
        }

        private string GetEPNWithPrefix(string epn)
        {
            return (UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix.Trim() + epn : epn;
        }


        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString().Replace('.', ' ');
        }


        /// <summary>
        /// Test to update the request patient
        /// </summary>
        [Test]
        public void Test10UpdateRequestPatient()
        {

            FindPatientCriteria patientSearchCriteria = new FindPatientCriteria();

            patientSearchCriteria.FirstName = string.Empty;
            patientSearchCriteria.LastName = string.Empty;
            patientSearchCriteria.Dob = null;
            patientSearchCriteria.EPN = string.Empty;
            patientSearchCriteria.FacilityCode = "AD";
            patientSearchCriteria.MRN = "BrooksM01";
            patientSearchCriteria.SSN = string.Empty;
            patientSearchCriteria.Encounter = string.Empty;
            patientSearchCriteria.MaxRecord = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);
            FindPatientResult result = patientController.FindPatient(patientSearchCriteria);
            foreach (PatientDetails patientDetails in result.PatientSearchResult)
            {
                if (patientDetails.IsHpf)
                {
                    details = patientDetails;
                    break;
                }
            }
            PatientDetails retrievedHPFPatientInfo = patientController.RetrieveHpfPatient(details.MRN, details.FacilityCode);

            retrievedHPFPatientInfo = patientController.RetrieveHpfDocuments(details);

            RequestPageDetails requestPageDetails = null;
            RequestVersionDetails requestVersionDetails = null;
            RequestDocumentDetails requestDocDetails = null;
            RequestEncounterDetails requestEncounterDetails = null;
            RequestPatientDetails requestPatientDetail = new RequestPatientDetails(retrievedHPFPatientInfo);

            foreach (EncounterDetails encounterDetails in retrievedHPFPatientInfo.Encounters)
            {
                requestEncounterDetails = new RequestEncounterDetails(encounterDetails);
                foreach (DocumentDetails docDetails in encounterDetails.Documents.Values)
                {
                    requestDocDetails = new RequestDocumentDetails(docDetails);
                    foreach (VersionDetails versionDetails in docDetails.Versions.Values)
                    {
                        requestVersionDetails = new RequestVersionDetails(versionDetails);
                        foreach (PageDetails pageDetails in versionDetails.Pages.Values)
                        {
                            requestPageDetails = new RequestPageDetails(pageDetails);
                            requestVersionDetails.AddChild(requestPageDetails);
                            break;
                        }

                        requestDocDetails.AddChild(requestVersionDetails);
                        requestPatientDetail.GlobalDocument.AddChild(requestDocDetails);
                        break;
                    }
                    requestPatientDetail.GlobalDocument.AddChild(requestDocDetails);
                    requestEncounterDetails.AddChild(requestDocDetails);
                    break;
                }
                requestPatientDetail.AddChild(requestEncounterDetails);
                break;
            }

            UpdateRequestPatients updateRequestPatient = new UpdateRequestPatients();
            DeleteList deleteList = new DeleteList();

            updateRequestPatient.DeleteList = deleteList;

            updateRequestPatient.RequestPatients.Add(requestPatientDetail);
            RequestPatients requestPatients = RequestController.Instance.SaveRequestPatients(updateRequestPatient, id);
            foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
            {
                newRequest.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
            }
            Assert.IsTrue(newRequest.Patients.Count > 1);
        }

        /// <summary>
        /// Test case to retrieve the request patient
        /// </summary>
        [Test]
        public void Test11RetrieveRequestPatient()
        {
            RequestPatients requestPatients = RequestController.Instance.RetrieveRequestPatients(id);
            foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
            {
                newRequest.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
            }
            Assert.IsTrue(newRequest.Patients.Count > 0);
        }

        private RequestPatientDetails GetRequestPatientWithDocument(PatientDetails patient)
        {
            RequestPageDetails page1 = new RequestPageDetails(new PageDetails("12121", 12, 5));
            page1.SelectedForRelease = true;
            RequestPageDetails page2 = new RequestPageDetails(new PageDetails("12", 12, 6));
            page2.SelectedForRelease = false;
            bool partiallyReleased = page1.PartiallyReleased;
            RequestVersionDetails version = new RequestVersionDetails(new VersionDetails(1));
            version.AddChild(page1);
            version.AddChild(page2);
            DocumentDetails hpfDoc = new DocumentDetails();
            hpfDoc.DocumentType = DocumentType.CreateDocType(12, "History and Physical", "Cardialogy", DateTime.Now.ToShortDateString(), "2", true, 741);
            hpfDoc.DocumentId = 22;
            RequestDocumentDetails reqDoc = new RequestDocumentDetails(hpfDoc);
            reqDoc.AddChild(version);
            EncounterDetails encounter = new EncounterDetails("ROI.1.0.12", "IP", "IP", "temp", DateTime.Now.ToShortDateString(),
                                                             DateTime.Now.ToShortDateString(), true, false, true, "clinic", "desposition", "class", "100", true);
            RequestEncounterDetails reqEncounter = new RequestEncounterDetails(encounter);
            reqEncounter.AddChild(reqDoc);
            RequestPatientDetails reqPatient = new RequestPatientDetails(patient);
            reqPatient.AddChild(reqEncounter);
            page1.SelectedForRelease = false;
            bool? state = reqPatient.CheckedState;
            page1.SelectedForRelease = true;
            state = reqPatient.CheckedState;

            return reqPatient;
        }

       

        [Test]
        public void Test12RetrieveRequestwithNonHpfPatientId()
        {
            RequestDetails request = requestController.RetrieveRequest(id, false);
            //Retrive Patient Request History.
            FindRequestCriteria requestCriteria = new FindRequestCriteria();

            requestCriteria.PatientFirstName = String.Empty;
            requestCriteria.PatientLastName = String.Empty;
            requestCriteria.PatientSsn = String.Empty;
            requestCriteria.Encounter = String.Empty;
            requestCriteria.EPN = String.Empty;
            requestCriteria.MRN = String.Empty;
            requestCriteria.BalanceDueOperator = BalanceDueOperator.AtLeast.ToString();
            requestCriteria.RequestorName = String.Empty;
            requestCriteria.RequestStatus = "Completed";
            requestCriteria.NonHpfPatientId = request.Patients[nonHpfPatientkey].Id;
            requestCriteria.IsSearch = true;
            requestCriteria.MaxRecord = 500;

            FindRequestResult findRequestResult = requestController.FindRequest(requestCriteria, null);
            Assert.AreEqual(1, findRequestResult.RequestSearchResult.Count);
        }

        /// <summary>
        /// Test create request for checking the primary field validation.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test13CreateRequestForValidations()
        {
            RequestDetails requestDetails = new RequestDetails();

            requestDetails.RequestorTypeName = "Patient";
            requestDetails.RequestorWorkPhone = "+18602777790";
            requestDetails.IsReleased = false;
            requestDetails.RequestorContactName = "John Smith";
            requestDetails.RequestorContactPhone = "20612100";
            requestDetails.RequestorFax = "2423423423";
            requestDetails.RequestorHomePhone = "2699901";
            requestDetails.RequestorWorkPhone = "8990777";
            requestDetails.ReceiptDate = new DateTime(2007, 08, 08);
            requestDetails.Status = RequestStatus.AuthReceived;
            requestDetails.RequestorId = 11;
            requestDetails.BillToAddressLine = "No.30:sai colony";
            requestDetails.BillToCity = "chennai";
            requestDetails.BillToContactNameFirst = "John";
            requestDetails.BillToContactNameLast = "Smith";
            requestDetails.BillToPostalCode = "605111";
            requestDetails.BillToState = "TN";
            requestDetails.RecordVersionId = 0;

            RequestPatientDetails patientDetails = new RequestPatientDetails();
            patientDetails.Id = 1;
            patientDetails.FullName = "sample,create";
            patientDetails.Gender = "F";
            patientDetails.FacilityCode = "A";
            patientDetails.MRN = "mrn";
            patientDetails.SSN = "ssn";

            requestDetails.Patients.Add("1", patientDetails);
            
            requestDetails = requestController.CreateRequest(requestDetails);
            id = requestDetails.Id;
        }

        /// <summary>
        /// Test case for checking the primary fields validation while updating.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test14UpdateRequestsForValidations()
        {
            RequestDetails requestDetails = new RequestDetails();

            requestDetails.Id = id;
            requestDetails.RequestorTypeName = "Patient";
            requestDetails.RequestorWorkPhone = "+18602777790";
            requestDetails.IsReleased = false;
            requestDetails.RequestorContactName = "John Smith";
            requestDetails.RequestorContactPhone = "20612100";
            requestDetails.RequestorFax = "2423423423";
            requestDetails.RequestorHomePhone = "2699901";
            requestDetails.RequestorWorkPhone = "8990777";
            requestDetails.ReceiptDate = new DateTime(2007, 08, 08);
            requestDetails.Status = RequestStatus.AuthReceived;
            requestDetails.RequestorId = 11;
            requestDetails.BillToAddressLine = "No.30:sai colony";
            requestDetails.BillToCity = "Kovai";
            requestDetails.BillToContactNameFirst = "John";
            requestDetails.BillToContactNameLast = "Smith";
            requestDetails.BillToPostalCode = "605111";
            requestDetails.BillToState = "TN";
            requestDetails.Requestor = new RequestorDetails();
            requestDetails.Requestor.IsActive = false;

            updateRequest = requestController.UpdateRequest(requestDetails);
        }

        
        /// <summary>
        /// Test Case for Update Request Info.
        /// </summary>
        //[Test]
        //public void Test08UpdateRequestInfo()
        //{

        //    FindPatientCriteria patientInfo = new FindPatientCriteria();
        //    patientInfo.FirstName = TestBase.PatientFirstName;
        //    patientInfo.LastName = string.Empty;
        //    patientInfo.Dob = null;
        //    patientInfo.EPN = string.Empty;
        //    patientInfo.FacilityCode = null;
        //    patientInfo.MRN = string.Empty;
        //    patientInfo.SSN = string.Empty;
        //    patientInfo.Encounter = string.Empty;
        //    patientInfo.MaxRecord = 100;
        //    FindPatientResult result = patientController.FindPatient(patientInfo);
        //    foreach (PatientDetails patient in result.PatientSearchResult)
        //    {
        //        if (patient.IsHpf)
        //        {
        //            details = patient;
        //            break;
        //        }
        //    }

        //    details = patientController.RetrieveHpfDocuments(details);

        //    RequestPatientDetails requestPatientDetails = new RequestPatientDetails();
        //    requestPatientDetails.Id = details.Id;
        //    requestPatientDetails.FullName = details.FullName;
        //    requestPatientDetails.Gender = details.Gender.ToString();
        //    requestPatientDetails.FacilityCode = details.FacilityCode;
        //    requestPatientDetails.MRN = details.MRN;
        //    requestPatientDetails.SSN = details.SSN;
        //    requestPatientDetails.EPN = details.EPN;
        //    requestPatientDetails.IsHpf = details.IsHpf;
           
        //    requestPatientDetails.GlobalDocument = new RequestGlobalDocuments();
        //    updateRequest.AddPatient(details);

        //    if (details.GlobalDocument.Documents.Count > 0)
        //    {
        //        RequestDocumentDetails requestGlobalDoc = new RequestDocumentDetails(details.GlobalDocument.Documents.Values[0]);
        //        requestGlobalDoc.DateOfService = DateTime.Now;
        //        requestPatientDetails.AddChild(requestGlobalDoc);
        //        RequestVersionDetails reqVer = new RequestVersionDetails(new VersionDetails(12));
        //        requestGlobalDoc.AddChild(reqVer);
        //        RequestPageDetails reqpage = new RequestPageDetails(new PageDetails("100", 123, 123));
        //        reqpage.SelectedForRelease = true;
        //        reqpage.IsReleased = true;
        //        bool bResult = reqVer.HasReleasedPage();
        //        reqVer.AddChild(reqpage);
        //        updateRequest.Patients[details.Key].GlobalDocument.AddChild(requestGlobalDoc);

        //        PageDetails page = details.GlobalDocument.Documents.Values[1].Versions.Values[0].Pages.Values[0];
        //        updateRequest.AddPage(page);
        //    }

        //    RequestEncounterDetails requestEnc = new RequestEncounterDetails(details.Encounters[0]);
        //    requestEnc.DischargeDate = DateTime.Now;

        //    requestPatientDetails.AddChild(requestEnc);
        //    requestEnc.Parent = requestPatientDetails;
        //    RequestDocumentDetails requestDoc = new RequestDocumentDetails(details.Encounters[0].Documents.Values[0]);
        //    requestDoc.DateOfService = DateTime.Now;
        //    requestEnc.AddChild(requestDoc);
        //    requestDoc.ClearChildren();
        //    requestEnc.RemoveChild(requestDoc);
        //    requestEnc.AddChild(requestDoc);
                     
        //    requestEnc.IsReleased = true;
        //    bool isReleased = requestEnc.IsReleased;
            
        //    bool flag = updateRequest.AddPage(details.Encounters[0].Documents.Values[0].Versions.Values[0].Pages.Values[0]);
        //    flag = updateRequest.AddPage(details.Encounters[0].Documents.Values[0].Versions.Values[0].Pages.Values[0]);
        //    if (details.GlobalDocument.Documents.Count > 0)
        //    {
        //        flag = updateRequest.AddPage(details.GlobalDocument.Documents.Values[0].Versions.Values[0].Pages.Values[0]);
        //    }

        //    ((RequestEncounterDetails)updateRequest.Patients[details.Key].GetChild(details.Encounters[0].Key)).DischargeDate = DateTime.Now;
        //    //((RequestDocumentDetails)updateRequest.Patients[details.Key].GetChild(details.Encounters[0].Name).GetChild(details.Encounters[0].Documents.Values[0].DocumentType.Name)).DateOfService = DateTime.Now;

        //    NonHpfDocumentDetails nonHpfDocDetails = new NonHpfDocumentDetails();

        //    nonHpfDocDetails.Id = 56;
        //    nonHpfDocDetails.RecordVersionId = 0;
        //    nonHpfDocDetails.Encounter = "12345";
        //    nonHpfDocDetails.Subtitle = "test";
        //    nonHpfDocDetails.PageCount = 3;
        //    nonHpfDocDetails.DocType = "test";
        //    nonHpfDocDetails.DateOfService = DateTime.Now.Date;
        //    nonHpfDocDetails.Department = "xxx";
        //    nonHpfDocDetails.FacilityCode = "yyyy";
        //    nonHpfDocDetails.PatientId = requestPatientDetails.Id;
        //    nonHpfDocDetails.PatientFacility = requestPatientDetails.FacilityCode;
        //    nonHpfDocDetails.PatientMrn = requestPatientDetails.MRN;
        //    nonHpfDocDetails.PatientKey = requestPatientDetails.Key;

        //    details.NonHpfDocuments.AddDocument(nonHpfDocDetails);

        //    requestPatientDetails.NonHpfDocument = new RequestNonHpfDocuments();

        //    IList<BaseRecordItem> li = new List<BaseRecordItem>();
        //    li.Add(nonHpfDocDetails);

        //    RequestNonHpfDocuments requestNonHpfDoc = new RequestNonHpfDocuments();
        //    //requestNonHpfDoc.AddChild(nonHpfDocDetails);
        //    updateRequest.AddNonHpfDocument(li, 0);

        //    bool newlyAdded = updateRequest.AddNonHpfDocument(li, 0);

        //    TestPatientController testPatientController = new TestPatientController();
        //    testPatientController.Test01CreatePatient();
        //    PatientDetails nonHpfPatient = testPatientController.PatientDetails;
        //    updateRequest.AddPatient(nonHpfPatient);
        //    RequestNonHpfDocumentDetails reqNonHpfDoc = new RequestNonHpfDocumentDetails(nonHpfDocDetails);
        //    updateRequest.Patients[nonHpfPatient.Key].NonHpfDocument.AddDocument(reqNonHpfDoc);
            
        //    BaseRequestItem nonHpfDoc = updateRequest.Patients[details.Key].NonHpfDocument.
        //                                GetChild(reqNonHpfDoc.Parent.Key).GetChild(reqNonHpfDoc.Key);
        //    nonHpfDoc.IsReleased = true;

        //    RequestDetails updateRequestInfo = requestController.UpdateRequest(updateRequest);
        //    bool partiallyReleased = updateRequestInfo.IsPartiallyReleased;
        //    bool patientWithoutRelease = updateRequestInfo.HasPatientWithNoReleaseRecords;
        //    bool? checkedState = null;
        //    foreach (RequestPatientDetails patient in updateRequestInfo.Patients.Values)
        //    {
        //        checkedState = patient.CheckedState;
        //    }

        //    RequestDetails requests = requestController.RetrieveRequest(updateRequestInfo.Id);            
        //    requests.Patients.Values[0].ClearChildren();

        //    bool hasReleasedPage = requests.Patients.Values[1].NonHpfDocument.HasReleasedPage();
        //    hasReleasedPage = requests.Patients.Values[1].HasReleasedPage();

        //    if(requests.Patients.ContainsKey(nonHpfPatient.Key))
        //    {
        //        requests.Patients[nonHpfPatient.Key].NonHpfDocument.ClearChildren();
        //    }

        //    Assert.AreNotEqual(updateRequest.RecordVersionId, updateRequestInfo.RecordVersionId);
        //}

        [Test]
        public void Test15ReleasedItems()
        {
            Assert.IsTrue(updateRequest.ReleasedItems.Count > 0);
        }


        /// <summary>
        /// Test Case for Update Request Info.
        /// </summary>
        [Test]
        public void Test16UpdateRequestInfo()
        {

            FindPatientCriteria patientInfo = new FindPatientCriteria();
            patientInfo.FirstName = TestBase.PatientFirstName;
            patientInfo.LastName = string.Empty;
            patientInfo.Dob = null;
            patientInfo.EPN = string.Empty;
            patientInfo.FacilityCode = null;
            patientInfo.MRN = string.Empty;
            patientInfo.SSN = string.Empty;
            patientInfo.Encounter = string.Empty;
            patientInfo.MaxRecord = 100;            
            FindPatientResult result = patientController.FindPatient(patientInfo);
            foreach (PatientDetails patient in result.PatientSearchResult)
            {
                if (patient.IsHpf)
                {
                    details = patient;
                    break;
                }
            }

            //Retrive all details
            details = patientController.RetrieveHpfDocuments(details);

            updateRequest.Patients.Clear();
            updateRequest.AddPatient(details);

            Collection<EncounterDetails> encounters = details.Encounters;

            SortedList<DocumentType, DocumentDetails> documents = new SortedList<DocumentType,DocumentDetails>();
            
            foreach (EncounterDetails enc in encounters)
            {
                documents = enc.Documents;

                Collection<PageDetails> pages = new Collection<PageDetails>();

                foreach (DocumentType docType in documents.Keys)
                {   
                    SortedList<int, VersionDetails> versions = documents[docType].Versions;
                    foreach (VersionDetails ver in versions.Values)
                    {   
                        foreach (PageDetails pg in ver.Pages.Values)
                        {   
                            pages.Add(pg);
                        }
                    }
                }

                foreach (PageDetails pg in pages)
                {
                    updateRequest.AddPage(pg);
                }
            }

             updateRequest.Patients[details.Key].SelectedForRelease = true;

             int counter=1;
             RequestDocumentDetails requestDocDetails1 = new RequestDocumentDetails();
             RequestDocumentDetails requestDocDetails2 = new RequestDocumentDetails();

             RequestEncounterDetails enc1 = new RequestEncounterDetails();
             RequestEncounterDetails enc2 = new RequestEncounterDetails();

             foreach (BaseRequestItem item in updateRequest.Patients[details.Key].GetChildren)
             {
                 foreach (BaseRequestItem baseItem in item.GetChildren)
                 {
                     if (counter == 1)
                     {
                         requestDocDetails1 = (RequestDocumentDetails)baseItem;
                         enc1 = (RequestEncounterDetails)item;
                     }
                     if (counter == 2)
                     {
                         requestDocDetails2 = (RequestDocumentDetails)baseItem;
                         enc2 = (RequestEncounterDetails)item;
                     }
                     counter++;
                 }
             }           

            requestDocDetails1.SelectedForRelease = false;
            Assert.IsNull(enc1.SelectedForRelease);
            Assert.IsNull(updateRequest.Patients[details.Key].SelectedForRelease);

            enc1.SelectedForRelease = true;
            requestDocDetails2.SelectedForRelease = true;
            Assert.IsTrue(updateRequest.Patients[details.Key].SelectedForRelease.Value);

            requestDocDetails2.SelectedForRelease = false;
            Assert.IsNull(updateRequest.Patients[details.Key].SelectedForRelease);

            Assert.IsFalse(requestDocDetails2.SelectedForRelease.Value);

            enc1.IsReleased = true;
            Assert.IsTrue(updateRequest.Patients[details.Key].HasReleasedPage());
            
            Assert.AreEqual(details.Key, updateRequest.AddPatient(details).Key);
        }

        /// <summary>
        /// Test Case for Delete Request.
        /// </summary>
       [Test]
        public void Test17DeleteRequest()
        {
            requestController.DeleteRequest(id);
            Assert.IsTrue(true);
        }

        /// <summary>
        /// Test case for retrieving non existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test18RetrieveNonExistingRequestId()
        {
            RequestDetails requests = requestController.RetrieveRequest(0, false);
        }

        /// <summary>
        /// Test case for deleting non existing request id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test19DeleteNonExistingRequestId()
        {
            requestController.DeleteRequest(0);
        }

        /// <summary>
        /// Test Case for RequestPatientDetails Sorter.
        /// </summary>
        [Test]
        public void Test20RequestPatientDetailsSorter()
        {
            RequestPatientDetails patient1 = new RequestPatientDetails();
            patient1.FullName = "John Smith";
            patient1.FacilityCode = "A";
            patient1.EPN = "EPN12345";
            patient1.Gender = "Male";

            RequestPatientDetails patient2 = new RequestPatientDetails();
            patient2.FullName = "Andrew Smith";
            patient2.FacilityCode = "B";
            patient2.EPN = "EPN12456";
            patient2.Gender = "Male";

            Assert.AreEqual(1, RequestPatientDetails.Sorter.Compare(patient1, patient2));
        }

        /// <summary>
        /// Test Case for RequestPatientDetails Sorter.
        /// </summary>
        [Test]
        public void Test21ReleasedPatientDetailsSorter()
        {
            ReleasedPatientDetails patient1 = new ReleasedPatientDetails();
            patient1.FullName = "John Smith";
            patient1.FacilityCode = "A";
            patient1.EPN = "EPN12345";
            patient1.Gender = "Male";

            ReleasedPatientDetails patient2 = new ReleasedPatientDetails();
            patient2.FullName = "Andrew Smith";
            patient2.FacilityCode = "B";
            patient2.EPN = "EPN12456";
            patient2.Gender = "Male";

            Assert.AreEqual(1, ReleasedPatientDetails.Sorter.Compare(patient1, patient2));
        }

        #endregion

        #region Properties
        public RequestDetails RequestInfo
        {
            get
            {
                return newRequest;
            }
        }

        /// <summary>
        /// Test Case for PatientSeq.
        /// </summary>
        [Test]
        public void TestPatientSeq()
        {
            ReleasedPatientDetails patient1 = new ReleasedPatientDetails();
            patient1.PatientSeq = 12345;

            ReleasedPatientDetails patient2 = new ReleasedPatientDetails();
            patient2.PatientSeq = 12345;

            Assert.AreEqual(1, ReleasedPatientDetails.Sorter.Compare(patient1, patient2));
        }

        /// <summary>
        /// Test Case for Attachment.
        /// </summary>
        [Test]
        public void TestAttachment()
        {
            ReleasedPatientDetails patient2 = new ReleasedPatientDetails();
            patient2.Attachment = new RequestAttachments();

            Assert.NotNull(patient2);
        }

        /// <summary>
        /// Test Case for ReleasedItems.
        /// </summary>
        [Test]
        public void TestReleasedItems()
        {
            ReleasedPatientDetails patient1 = new ReleasedPatientDetails();
            patient1.IsHpf = true;
            RequestPatientDetails reqPatient1 = patient1.ReleasedItems;
             

            ReleasedPatientDetails patient2 = new ReleasedPatientDetails();
            patient1.IsHpf = false;
            RequestPatientDetails reqPatient2 = patient2.ReleasedItems;

            Assert.AreNotEqual(reqPatient1, reqPatient2);
        }

        /// <summary>
        /// Test Case for SupplementalId.
        /// </summary>
        [Test]
        public void TestSupplementalId()
        {
            ReleasedPatientDetails Input = new ReleasedPatientDetails();
            Input.SupplementalId = 123456;

            ReleasedPatientDetails Output = new ReleasedPatientDetails();
            Output.SupplementalId = Input.SupplementalId;

            Assert.AreEqual(Input, Output);
        }

        /// <summary>
        /// Test Case for RequestorId.
        /// </summary>
        [Test]
        public void TestRequestorId()
        {
            ReleasedPatientDetails Input = new ReleasedPatientDetails();
            Input.RequestorId = 123456;

            ReleasedPatientDetails Output = new ReleasedPatientDetails();
            Output.RequestorId = Input.RequestorId;

            Assert.AreEqual(Input, Output);
        }

        /// <summary>
        /// Test Case for RequestorId.
        /// </summary>
        [Test]
        public void TestRequestId()
        {
            ReleasedPatientDetails Input = new ReleasedPatientDetails();
            Input.RequestId = 123456;

            ReleasedPatientDetails Output = new ReleasedPatientDetails();
            Output.RequestId = Input.RequestId;

            Assert.AreEqual(Input, Output);
        }

        /// <summary>
        /// Test Case for PageStatus.
        /// </summary>
        [Test]
        public void TestPageStatus()
        {
            ReleasedPatientDetails Input = new ReleasedPatientDetails();
            Dictionary<long,bool> test = Input.PageStatus;
            Assert.NotNull(Input);
        }

        #endregion
    }
}
