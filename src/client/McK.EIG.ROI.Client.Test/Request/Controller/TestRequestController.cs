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
using System.Globalization;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.Common.Audit.Model;
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.FindRequest;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Request.Controller
{
    /// <summary>
    /// Test case for Request Controller.
    /// </summary>
    [TestFixture]
    [Category("TestRequestController")]
    public class TestRequestController : TestBase
    {
        #region Fields

        private PatientController patientController;
        private RequestController requestController;
        private FindPatientCriteria patientSearchCriteria;
        private PatientDetails hpfPatient; 
        private RequestDetails requestDetails;
        private IFormatProvider theCultureInfo = new System.Globalization.CultureInfo("en-GB", true);

        private const string AppId = "appId";

        #endregion Fields

        #region Methods

        #region NUnit

        [SetUp]
        public void Init()
        {
            patientController = PatientController.Instance;
            requestController = RequestController.Instance;            
        }

        [TearDown]
        public void Dispose()
        {
            requestController = null;
            patientController = null;
        }

        #endregion

        #region Service Methods

        public void Initlogon()
        {
            requestController = RequestController.Instance;
            patientController = PatientController.Instance;
            
            requestController.RetrieveFreeformFacilities();
            //Assert.IsNotNull(UserData.Instance.FreeformFacilities);

            //Retrieve Patient
            patientSearchCriteria = new FindPatientCriteria();
            hpfPatient = new PatientDetails();

            patientSearchCriteria.FirstName = string.Empty;
            patientSearchCriteria.LastName = string.Empty;
            patientSearchCriteria.Dob = null;
            patientSearchCriteria.EPN = string.Empty;
            patientSearchCriteria.FacilityCode = TestBase.PatientFacility;
            patientSearchCriteria.MRN = TestBase.PatientMrn ;
            patientSearchCriteria.SSN = string.Empty;
            patientSearchCriteria.Encounter = string.Empty;
            patientSearchCriteria.MaxRecord = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);
            FindPatientResult result = patientController.FindPatient(patientSearchCriteria);
            foreach (PatientDetails patientDetails in result.PatientSearchResult)
            {
                if (patientDetails.IsHpf)
                {
                    hpfPatient = patientDetails;
                    break;
                }
            }

            TestRequestPatientController requestPatientController = new TestRequestPatientController();
            requestPatientController.Test01CreateRequest();
            requestDetails = requestPatientController.RequestInfo;
            requestPatientController.Test04SaveRequestPatient();
            requestDetails = requestPatientController.RequestInfo;
        }

        /// <summary>
        /// Method for InitLogon
        /// </summary>
        [Test]
        public void Test01InitLogon()
        {
            Initlogon();
        }

        /// <summary>
        /// Test Method to Create Comment.
        /// </summary>
        [Test]
        public void Test02CreateComment()
        {
            CommentDetails details = new CommentDetails();
            details.EventRemarks = "Requested by " + requestDetails.RequestorFirstName;
            details.RequestId = requestDetails.Id;
            details.EventType = EventType.CommentAdded;
            CommentDetails response = requestController.CreateComment(details);
            Assert.IsNotNull(response.Originator);
            Assert.IsNotNull(response.EventDate);
        }


        /// <summary>
        /// Test Method to Retrieve Comment
        /// </summary>
        [Test]
        public void Test03RetrieveComment()
        {
            
            Collection<CommentDetails> commentList = requestController.RetrieveComments(requestDetails.Id);
            Assert.AreNotEqual(0, commentList.Count);
        }

        /// <summary>
        /// Test Method to Create Comment with empty Comment Value;
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test04CreateCommentWithEmptyRemarks()
        {
            CommentDetails details = new CommentDetails();
            details.EventRemarks = string.Empty;
            details.RequestId = requestDetails.Id;
            CommentDetails response = requestController.CreateComment(details);
        }

        /// <summary>
        /// Test Method to Create Comment with empty Comment Value;
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test05CreateCommentWithMaxCharacter()
        {
            CommentDetails details = new CommentDetails();
            string comment = "Comment added to check maximum character ";
            while (comment.Length < 1000)
            {
                comment += comment;
            }
            details.EventRemarks = comment;
            details.RequestId = requestDetails.Id;
            CommentDetails response = requestController.CreateComment(details);
        }

        /// <summary>
        /// Test to find the Request.
        /// </summary>
        [Test]
        public void Test06FindRequestHistory()
        {
            //Retrive Patient Request History.
            FindRequestCriteria requestCriteria = new FindRequestCriteria();
            requestCriteria.PatientFirstName = string.Empty;
            requestCriteria.PatientLastName  = string.Empty;
            requestCriteria.PatientSsn       = string.Empty;
            requestCriteria.Encounter        = string.Empty;
            requestCriteria.EPN              = string.Empty;
            requestCriteria.RequestorName    = string.Empty;
            requestCriteria.Encounter        = string.Empty;
            requestCriteria.IsSearch         = false;
            requestCriteria.Facility         = hpfPatient.FacilityCode;
            requestCriteria.MRN              = hpfPatient.MRN;            

            PaginationDetails pagenationDetails = new PaginationDetails();
            pagenationDetails.IsDescending = false;
            pagenationDetails.Size = 5;            
            pagenationDetails.StartFrom = 1;
            pagenationDetails.SortColumn = ROIConstants.RequestorDomainType + "." + RequestDetails.RequestorNameKey;
            pagenationDetails.DomainType = ROIConstants.RequestorDomainType;
            FindRequestResult findRequestResut = requestController.FindRequest(requestCriteria, pagenationDetails);
            Assert.IsNotNull(findRequestResut);
        }
        

        /// <summary>
        /// Test to find the Request.
        /// </summary>
        [Test]
        public void Test07RetrieveRequestCount()
        {
            //Retrive Patient Request History.
            FindRequestCriteria requestCriteria = new FindRequestCriteria();
            requestCriteria.Facility = hpfPatient.FacilityCode;
            requestCriteria.IsSearch = false;
            requestCriteria.MRN = hpfPatient.MRN;            
            long Count = requestController.RetrieveRequestCount(requestCriteria);            
        }

        /// <summary>
        /// Test Method to Retrieve Event history
        /// </summary>
        [Test]
        public void Test08RetrieveEventHistory()
        {   
            Collection<RequestEventHistoryDetails> requestEventHistoryDetails = requestController.RetrieveEventHistory(requestDetails.Id);
            Assert.AreNotEqual(0, requestEventHistoryDetails.Count);
        }

        /// <summary>
        /// Test Method to retrieve request events
        /// </summary>
        [Test]
        public void Test09RetrieveRequestEvents()
        {
            string[] requestEventList = RequestController.Instance.RetrieveRequestEvents();
            Assert.AreNotEqual(0, requestEventList.Length);
        }

        #region RequestValidator

        /// <summary>
        /// ValidatePrimarySearchFields with requestor type as patient
        /// </summary>
        [Test]
        public void Test10ValidatePrimarySearchFields()
        {
            FindRequestCriteria searchCriteria = new FindRequestCriteria();
            searchCriteria.RequestorType = 0;
            searchCriteria.PatientLastName = null;
            searchCriteria.PatientFirstName = null;
            searchCriteria.PatientSsn = null;
            searchCriteria.MRN = null;
            searchCriteria.EPN = null;
            searchCriteria.Encounter = null;
            searchCriteria.RequestorName = null;
            Assert.IsFalse(RequestValidator.ValidatePrimarySearchFields(searchCriteria));
        }

        /// <summary>
        /// ValidatePrimarySearchFields with requestor type as non patient
        /// </summary>
        [Test]
        public void Test11ValidatePrimarySearchFields()
        {
            FindRequestCriteria searchCriteria = new FindRequestCriteria();
            searchCriteria.RequestorType = 1;
            searchCriteria.PatientLastName = "John";
            searchCriteria.PatientFirstName = "Michal";
            searchCriteria.PatientSsn = "123";
            searchCriteria.MRN = "789";
            searchCriteria.EPN = "898";
            searchCriteria.BalanceDueOperator = BalanceDueOperator.AtMost.ToString();
            searchCriteria.Encounter = "Encounter";
            searchCriteria.RequestorName = "John";
            RequestValidator.ValidatePrimarySearchFields(searchCriteria);
            Assert.IsTrue(RequestValidator.ValidatePrimarySearchFields(searchCriteria));
        }

        /// <summary>
        /// ValidateRequestStatus with requestid = 0 and Request Status = logged.
        /// </summary>
        [Test]        
        public void Test12ValidateRequestStatus()
        {
            RequestValidator validator = new RequestValidator();
            RequestDetails details = new RequestDetails();
            details.Id = 0;
            details.Status = RequestStatus.Pended;
            Assert.IsFalse(validator.ValidateRequestStatus(details));
        }

        #endregion

        [Test]
        public void Test13FindRequestWithEmptyPagination()
        {
            //Retrive Patient Request History.
            FindRequestCriteria requestCriteria = new FindRequestCriteria();

            requestCriteria.PatientFirstName = string.Empty;
            requestCriteria.PatientLastName = string.Empty;
            requestCriteria.PatientSsn = string.Empty;
            requestCriteria.Encounter = string.Empty;
            requestCriteria.EPN = string.Empty;
            requestCriteria.RequestorName = string.Empty;
            requestCriteria.Encounter = string.Empty;
            if (requestDetails.Patients.Count > 0)
            {
                requestCriteria.Facility = requestDetails.Patients.Values[0].FacilityCode;
                requestCriteria.MRN = requestDetails.Patients.Values[0].MRN;
            }
            requestCriteria.IsSearch = false;
            requestCriteria.MaxRecord = 10;

            FindRequestResult findRequestResult = requestController.FindRequest(requestCriteria, null);
            Assert.IsTrue(findRequestResult.RequestSearchResult.Count > 0);
        }

        /// <summary>
        /// Testcase for marking the record as in use
        /// </summary>
        [Test]
        public void Test14CreateRecordInUse()
        {
            Collection<string> ids = new Collection<string>();
            ids.Add(requestDetails.Id.ToString());

            SortedList<string, InUseRecordDetails> inUseRecords = RequestController.Instance.RetrieveInUseRecords(ROIConstants.RequestDomainType, ids);
            Assert.IsNull(inUseRecords);

            requestDetails.InUseRecord = RequestController.Instance.RetrieveInUseRecord(ROIConstants.RequestDomainType, requestDetails.Id);
            Assert.IsNull(requestDetails.InUseRecord);
            if (requestDetails.InUseRecord == null)
            {
                RequestController.Instance.CreateInUseRecord(ROIConstants.RequestDomainType, requestDetails.Id);
            }

            string applicationId = ConfigurationManager.AppSettings[AppId];
            ConfigurationManager.AppSettings[AppId] = "blackhole";

            inUseRecords = RequestController.Instance.RetrieveInUseRecords(ROIConstants.RequestDomainType, ids);
            Assert.IsNotNull(inUseRecords);

            requestDetails.InUseRecord = RequestController.Instance.RetrieveInUseRecord(ROIConstants.RequestDomainType, requestDetails.Id);
            Assert.IsNotNull(requestDetails.InUseRecord);

            ConfigurationManager.AppSettings[AppId] = applicationId;
        }
        
        /// <summary>
        /// Test to find the Request.
        /// </summary>
        [Test]
        public void Test15FindRequest()
        {
            //Retrive Patient Request History.
            FindRequestCriteria requestCriteria = new FindRequestCriteria();
            int index = 0;
            if (requestDetails.Patients.Count > 0)
            {
                index = requestDetails.Patients.Values[0].FullName.IndexOf(",");
                requestCriteria.PatientFirstName = requestDetails.Patients.Values[0].FullName.Substring(index + 1).Trim();
            }
            requestCriteria.PatientLastName = string.Empty;
            requestCriteria.PatientSsn = string.Empty;
            requestCriteria.Encounter = string.Empty;
            requestCriteria.EPN = string.Empty;
            requestCriteria.RequestorName = string.Empty;
            requestCriteria.RequestorId = requestDetails.RequestorId;
            requestCriteria.Encounter = string.Empty;
            requestCriteria.Facility = null;
            requestCriteria.IsSearch = true;
            requestCriteria.MRN = string.Empty;
            requestCriteria.MaxRecord = 500;

            string applicationId = ConfigurationManager.AppSettings[AppId];
            ConfigurationManager.AppSettings[AppId] = "blackhole";
            
            FindRequestResult findRequestResut = requestController.FindRequest(requestCriteria, null);
            ConfigurationManager.AppSettings[AppId] =  applicationId;

            Assert.AreNotEqual(0, findRequestResut.RequestSearchResult.Count);
        }

        /// <summary>
        /// Test to find the Request.
        /// </summary>
        [Test]
        public void Test16FindRequest()
        {
            TestRequestPatientController requestPatientController = new TestRequestPatientController();
            requestPatientController.Test01CreateRequest();

            //Retrive Patient Request History.
            FindRequestCriteria requestCriteria = new FindRequestCriteria();            

            requestCriteria.PatientFirstName = "First";
            requestCriteria.PatientLastName = "Last";
            requestCriteria.PatientSsn = "111-11-1111";
            requestCriteria.Dob = DateTime.Now.Date;
            requestCriteria.Encounter = null;
            requestCriteria.EPN = "1234";
            requestCriteria.RequestStatus = "Logged";
            requestCriteria.StatusReason = "Logged Reason1";
            requestCriteria.RequestReason = "For Insurance";
            requestCriteria.BalanceDue = "0.0";
            requestCriteria.BalanceDueOperator = BalanceDueOperator.AtLeast.ToString();
            requestCriteria.RequestorName = "LastRequestor";
            requestCriteria.RequestorType = -1;
            requestCriteria.Facility = null;
            requestCriteria.MRN = "2222";

            requestCriteria.ReceiptDate = "30days";
            requestCriteria.CustomFromDate = DateTime.Now.Date.AddDays(-30).ToShortDateString();
            requestCriteria.CustomToDate = DateTime.Now.Date.ToShortDateString();

            requestCriteria.InvoiceNumber = null;
            requestCriteria.RequestId = null;
            requestCriteria.MaxRecord = 500;
            requestCriteria.CompletedDate = "Custom Range";
            requestCriteria.CompletedFromDate = DateTime.Now.Date.ToString("MM/dd/yyyy");
            requestCriteria.CompletedToDate = DateTime.Now.Date.ToString("MM/dd/yyyy");
            
            FindRequestResult findRequestResult = requestController.FindRequest(requestCriteria, null);
            Assert.IsNotNull(findRequestResult);
        }

        /// <summary>
        /// Test to find the Request with the invalid data.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test17FindRequestWithInvalidData()
        {  
            FindRequestCriteria requestCriteria = new FindRequestCriteria();

            requestCriteria.PatientFirstName = "John$%";            
            requestCriteria.PatientLastName = String.Empty;
            requestCriteria.PatientSsn = "1-23-3456";
            requestCriteria.Encounter = String.Empty;
            requestCriteria.EPN = String.Empty;
            requestCriteria.MRN = String.Empty;
            requestCriteria.BalanceDueOperator = BalanceDueOperator.AtLeast.ToString();
            requestCriteria.RequestorName = String.Empty;
            requestCriteria.RequestStatus = "Logged";
            requestCriteria.IsSearch = true;
            requestCriteria.BalanceDue = "-1%";
            requestCriteria.MaxRecord = 500;

            FindRequestResult findRequestResult = requestController.FindRequest(requestCriteria, null);            
        }

        /// <summary>
        /// Test case for release the inuse record
        /// </summary>
        [Test]
        public void Test18ReleaseInUseRecord()
        {
            //Release the request from locked status
            RequestController.Instance.ReleaseInUseRecord(ROIConstants.RequestDomainType, requestDetails.Id);

            requestDetails.InUseRecord = RequestController.Instance.RetrieveInUseRecord(ROIConstants.RequestDomainType, requestDetails.Id);
            Assert.IsNull(requestDetails.InUseRecord);
        }

        /// <summary>
        /// Test case for getting auth request from server
        /// </summary>
        [Test]
        public void Test19RetrieveAuthRequest()
        {
            TestRequestPatientController requestPatientController = new TestRequestPatientController();
            requestPatientController.Test01CreateRequest();
            
            RequestDetails request = RequestController.Instance.RetrieveRequest(requestPatientController.GetRequestId(), false);
            Assert.IsNotNull(request.Requestor);
            Assert.IsNotNull(request.AuthRequest);
        }

        [Test]
        public void Test20CreateAuditEntryList()
        {
            List<AuditEvent> auditEvents = new List<AuditEvent>();
            AuditEvent auditEvent = new AuditEvent();
            auditEvent.UserId = UserData.Instance.UserInstanceId;
            auditEvent.EventStart = System.DateTime.Now;
            auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
            auditEvent.EventId = 1;
            auditEvent.ActionCode = "RP";
            auditEvent.Mrn = "FralickM01";
            auditEvent.Encounter = "FralickE5001";
            auditEvent.Facility = "AD";
            auditEvent.Comment = "This audit comment entered for the testcase purpose";
            auditEvents.Add(auditEvent);
            RequestController.Instance.CreateAuditEntryList(auditEvents);
        }

        #endregion

        #endregion
    }
}
