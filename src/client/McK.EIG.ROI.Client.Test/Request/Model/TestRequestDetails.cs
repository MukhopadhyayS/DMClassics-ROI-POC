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
using System.ComponentModel;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Base.View.Common;


namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for RequestDetails 
    /// </summary>
    [TestFixture]
    public class TestRequestDetails
    {
        private RequestDetails requestDetails;

        [SetUp]
        public void Initialize()
        {
            requestDetails = new RequestDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestDetails = null;
        }

        #region Test Methods

        /// <summary>
        /// Test case for Request Id.
        /// </summary>
        [Test]
        public void TestRequestId()
        {
            long inputRequestId = 10;
            requestDetails.Id = inputRequestId;
            long outputRequestId = requestDetails.Id;
            Assert.AreEqual(inputRequestId, outputRequestId);
        }

        /// <summary>
        /// Test case for Request Reason.
        /// </summary>
        [Test]
        public void TestRequestReason()
        {
            string inputRequestReason = "Logged";
            requestDetails.RequestReason = inputRequestReason;
            string outputRequestReason = requestDetails.RequestReason;
            Assert.AreEqual(inputRequestReason, outputRequestReason);
        }

        /// <summary>
        /// Test case for Request Receipt Date.
        /// </summary>
        [Test]
        public void TestRequestReceiptDate()
        {
            Nullable<DateTime> inputRequestReceiptDate = DateTime.Now;
            requestDetails.ReceiptDate = inputRequestReceiptDate;
            Nullable<DateTime> outputRequestReceiptDate = requestDetails.ReceiptDate;
            Assert.AreEqual(inputRequestReceiptDate, outputRequestReceiptDate.Value);
        }

        /// <summary>
        /// Test case for Date Created.
        /// </summary>
        [Test]
        public void TestDateCreated()
        {
            Nullable<DateTime> inputDateCreated = DateTime.Now;
            requestDetails.DateCreated = inputDateCreated;
            Nullable<DateTime> outputDateCreated = requestDetails.DateCreated;
            Assert.AreEqual(inputDateCreated, outputDateCreated);
        }

        /// <summary>
        /// Test case for Last Updated.
        /// </summary>
        [Test]
        public void TestLastUpdated()
        {
            Nullable<DateTime> inputLastUpdated = DateTime.Now;
            requestDetails.LastUpdated = inputLastUpdated;
            Nullable<DateTime> outputLastUpdated = requestDetails.LastUpdated;
            Assert.AreEqual(inputLastUpdated, outputLastUpdated);
        }

        /// <summary>
        /// Test case for UpdatedBy.
        /// </summary>
        [Test]
        public void TestUpdatedBy()
        {
            string inputUpdatedBy = "Test User";
            requestDetails.UpdatedBy = inputUpdatedBy;
            string outputUpdatedBy = requestDetails.UpdatedBy;
            Assert.AreEqual(inputUpdatedBy, outputUpdatedBy);
        }
        
        /// <summary>
        /// Test case for Request Requestor Id.
        /// </summary>
        [Test]
        public void TestRequestRequestorId()
        {
            long inputRequestRequestorId = 10;
            requestDetails.RequestorId = inputRequestRequestorId;
            long outputRequestRequestorId = requestDetails.RequestorId;
            Assert.AreEqual(inputRequestRequestorId, outputRequestRequestorId);
        }

        /// <summary>
        /// Test case for Requestor Name.
        /// </summary>
        [Test]
        public void TestRequestorName()
        {
            string inputRequestorName = "Requestor 1";
            requestDetails.RequestorName = inputRequestorName;
            string outputRequestorName = requestDetails.RequestorName;
            Assert.AreEqual(inputRequestorName, outputRequestorName);
        }

        /// <summary>
        /// Test case for Requestor Type.
        /// </summary>
        [Test]
        public void TestRequestorTypeName()
        {
            string inputRequestorType = "Patient";
            requestDetails.RequestorTypeName = inputRequestorType;
            string outputRequestorType = requestDetails.RequestorTypeName;
            Assert.AreEqual(inputRequestorType, outputRequestorType);
        }

        /// <summary>
        /// Test case for Requestor Type.
        /// </summary>
        [Test]
        public void TestRequestorType()
        {
            requestDetails.RequestorType = RequestorDetails.PatientRequestorType;
            Assert.AreEqual(RequestorDetails.PatientRequestorType, requestDetails.RequestorType);
        }


        /// <summary>
        /// Test case for Request Home Phone.
        /// </summary>
        [Test]
        public void TestHomePhone()
        {
            string inputHomePhone = "45456457215";
            requestDetails.RequestorHomePhone = inputHomePhone;
            string outputHomePhone = requestDetails.RequestorHomePhone;
            Assert.AreEqual(inputHomePhone, outputHomePhone);
        }

        /// <summary>
        /// Test case for Requestor Work Phone.
        /// </summary>
        [Test]
        public void TestWorkPhone()
        {
            string inputWorkPhone = "2016585182";
            requestDetails.RequestorWorkPhone = inputWorkPhone;
            string outputWorkPhone = requestDetails.RequestorWorkPhone;
            Assert.AreEqual(inputWorkPhone, outputWorkPhone);
        }

        /// <summary>
        /// Test case for Requestor Cell Phone.
        /// </summary>
        [Test]
        public void TestCellPhone()
        {
            string inputCellPhone = "2089765432";
            requestDetails.RequestorCellPhone = inputCellPhone;
            string ouputCellPhone = requestDetails.RequestorCellPhone;
            Assert.AreEqual(inputCellPhone, ouputCellPhone);
        }

        /// <summary>
        /// Test case for Requestor Fax.
        /// </summary>
        [Test]
        public void TestFax()
        {
            string inputFax = "022-5678903";
            requestDetails.RequestorFax = inputFax;
            string outputFax = requestDetails.RequestorFax;
            Assert.AreEqual(inputFax, outputFax);
        }

        /// <summary>
        /// Test case for Requestor Contact Name.
        /// </summary>
        [Test]
        public void TestContactName()
        {
            string inputContactName = "John";
            requestDetails.RequestorContactName = inputContactName;
            string outputContactName = requestDetails.RequestorContactName;
            Assert.AreEqual(inputContactName, outputContactName);
        }

        /// <summary>
        /// Test case for Requestor Contact Phone.
        /// </summary>
        [Test]
        public void TestContactPhone()
        {
            string inputContactPhone = "8602777790";
            requestDetails.RequestorContactPhone = inputContactPhone;
            string outputContactPhone = requestDetails.RequestorContactPhone;
            Assert.AreEqual(inputContactPhone, outputContactPhone);
        }

        /// <summary>
        /// Test case for document Released
        /// </summary>
        [Test]
        public void TestIsReleased()
        {
            requestDetails.IsReleased = true;
            bool inputIsReleased = requestDetails.IsReleased;
            bool outputIsReleased = inputIsReleased;
            Assert.AreEqual(inputIsReleased, outputIsReleased);
        }

        /// <summary>
        /// Test case for Status Reason.
        /// </summary>
        [Test]
        public void TestStatusReason()
        {
            requestDetails.StatusReason = "Test Reason";
            string inputStatusReason = requestDetails.StatusReason;
            string outputStatusReason = inputStatusReason;
            Assert.AreEqual(inputStatusReason, outputStatusReason);
        }

        /// <summary>
        /// Test reasons
        /// </summary>
        [Test]
        public void TestReasons()
        {
            requestDetails.StatusReason = "Test Reason";
            Assert.IsNotEmpty(requestDetails.Reasons);
        }

        /// <summary>
        /// Test case for Request Status.
        /// </summary>
        [Test]
        public void TestRequestStatus()
        {
            RequestStatus status = RequestStatus.Logged;
            requestDetails.Status = status;
            Assert.AreEqual(status, requestDetails.Status);
        }

        /// <summary>
        /// Test case for Requestor. 
        /// </summary>
        [Test]
        public void TestRequestorDetail()
        {
            requestDetails.Requestor = new RequestorDetails();
            Assert.IsInstanceOfType(typeof(RequestorDetails), requestDetails.Requestor);
        }


        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test01Validator()
        {
            RequestDetails requestInfo = new RequestDetails();
            requestInfo.DateCreated = null;
            requestInfo.Id = 1;
            requestInfo.IsReleased = true;
            requestInfo.LastUpdated = null;
            requestInfo.ReceiptDate = null;
            RequestorDetails requestorInfo = new RequestorDetails(); 
            requestorInfo.Name = null;
            requestInfo.Requestor = requestorInfo;
            requestInfo.Status = RequestStatus.Canceled;
            requestInfo.RequestReason = "Migration";
            RequestValidator validator = new RequestValidator();
            if (!validator.ValidateRequestInfo(requestInfo))
            {
                throw validator.ClientException;
            }
        }

        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test02Validator()
        {
            RequestDetails requestInfo = new RequestDetails();
            requestInfo.DateCreated = null;
            requestInfo.Id = 1;
            requestInfo.IsReleased = true;
            requestInfo.LastUpdated = null;
            requestInfo.ReceiptDate = DateTime.Now.AddDays(2);
            RequestorDetails requestorInfo = new RequestorDetails();
            requestorInfo.Name = null;
            requestInfo.Requestor = requestorInfo;
            requestInfo.Status = RequestStatus.Canceled;
            requestInfo.RequestReason = "Migration";
            RequestValidator validator = new RequestValidator();
            if (!validator.ValidateRequestInfo(requestInfo))
            {
                throw validator.ClientException;
            }
        }

        [Test]
        public void TestIsReasonRequired() 
        {
            Assert.AreEqual(true, RequestDetails.IsReasonRequired(RequestStatus.Denied));
        }

        [Test]
        public void TestPatientDetails()
        {
            RequestDetails details = new RequestDetails();
            Assert.IsInstanceOfType(typeof(SortedList<string, RequestPatientDetails>), details.Patients);
        }

        [Test]
        public void TestSortPatient()
        {
            RequestPatientDetails details1 = new RequestPatientDetails();
            details1.Id = 100;
            details1.FullName = "JohnSmith";
            details1.FacilityCode = "xxx";
            details1.Gender = "Male";
            details1.MRN = "123";
            requestDetails.Patients.Add("1", details1);
            RequestPatientDetails details2 = new RequestPatientDetails();
            details2.Id = 200;
            details2.FullName = "John";
            details2.FacilityCode = "xxx";
            details2.Gender = "Male";
            details2.MRN = "123";
            requestDetails.Patients.Add("2", details2);
            Assert.IsNotNull(requestDetails);
        }

        [Test]
        public void TestSorter()
        {
            Collection<RequestDetails> requests = new Collection<RequestDetails>();

            RequestDetails requestDetails1 = new RequestDetails();
            requestDetails1.Id = 2;
            requestDetails1.ReceiptDate = DateTime.Now;
            requestDetails1.Status = RequestStatus.Logged;
            requestDetails1.LastUpdated = DateTime.Now;

            RequestDetails requestDetails2 = new RequestDetails();
            requestDetails2.Id = 1;
            requestDetails2.ReceiptDate = DateTime.Now;
            requestDetails2.Status = RequestStatus.Completed;
            requestDetails2.LastUpdated = DateTime.Now;

            requests.Add(requestDetails1);
            requests.Add(requestDetails2);

            ComparableCollection<RequestDetails> list = new ComparableCollection<RequestDetails>(requests, new RequestDetailsComparer());
            list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestDetails))["ReceiptDate"], ListSortDirection.Ascending);
            Assert.AreEqual(1, list[0].Id);

            ComparableCollection<RequestDetails> list1 = new ComparableCollection<RequestDetails>(requests, new RequestDetailsComparer());
            list1.ApplySort(TypeDescriptor.GetProperties(typeof(RequestDetails))["Status"], ListSortDirection.Ascending);
            Assert.AreEqual(1, list1[0].Id);

            ComparableCollection<RequestDetails> list2 = new ComparableCollection<RequestDetails>(requests, new RequestDetailsComparer());
            list2.ApplySort(TypeDescriptor.GetProperties(typeof(RequestDetails))["LastUpdated"], ListSortDirection.Ascending);
            Assert.AreEqual(1, list2[0].Id);
            list2.ApplySort(TypeDescriptor.GetProperties(typeof(RequestDetails))["RequestorName"], ListSortDirection.Ascending);
        }

        /// <summary>
        /// Test case for Bill to contact name first
        /// </summary>
        [Test]
        public void TestBillToContactNameFirst()
        {
            requestDetails.BillToContactNameFirst = "John";
            string inputName = requestDetails.BillToContactNameFirst;
            string outputName = inputName;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test case for Bill to contact name first
        /// </summary>
        [Test]
        public void TestBillToContactNameLast()
        {
            string lastName = "Smith";
            requestDetails.BillToContactNameLast = lastName;
            Assert.AreEqual(lastName, requestDetails.BillToContactNameLast);
        }

        /// <summary>
        /// Test case for patient name.
        /// </summary>
        [Test]
        public void TestPatientName()
        {
            requestDetails.PatientNames = "John";
            string inputName = requestDetails.PatientNames;
            string outputName = inputName;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test case for requestor's first name.
        /// </summary>
        [Test]
        public void TestRequestorFirstName()
        {
            requestDetails.RequestorFirstName = "John";
            string inputName = requestDetails.RequestorFirstName;
            string outputName = inputName;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test case for BillingAddressLine.
        /// </summary>
        [Test]
        public void TestBillingAddressLine()
        {
            requestDetails.BillToAddressLine = "test";
            string inputBillToAddressLinee = requestDetails.BillToAddressLine;
            string outputBillToAddressLine = inputBillToAddressLinee;
            Assert.AreEqual(inputBillToAddressLinee, outputBillToAddressLine);
        }

        /// <summary>
        /// Test case for BillingToCity.
        /// </summary>
        [Test]
        public void TestBillToCity()
        {
            requestDetails.BillToCity = "test";
            string inputBillToCity = requestDetails.BillToCity;
            string outputBillToCity = inputBillToCity;
            Assert.AreEqual(inputBillToCity, outputBillToCity);
        }

        /// <summary>
        /// Test case for BillToState.
        /// </summary>
        [Test]
        public void TestBillToState()
        {
            requestDetails.BillToState = "test";
            string inputbillToState = requestDetails.BillToState;
            string outputbillToState = inputbillToState;
            Assert.AreEqual(inputbillToState, outputbillToState);
        }


        /// <summary>
        /// Test case for BillToState.
        /// </summary>
        [Test]
        public void TestBillToPostalCode()
        {
            requestDetails.BillToPostalCode = "test";
            string inputBillToPostalCode = requestDetails.BillToPostalCode;
            string outputBillToPostalCode = inputBillToPostalCode;
            Assert.AreEqual(inputBillToPostalCode, outputBillToPostalCode);
        }

        /// <summary>
        /// Test case for FreeFormReasons.
        /// </summary>
        [Test]
        public void TestFreeFormReasons()
        {
            requestDetails.FreeformReasons = "test";
            string inputFreeFormReasons = requestDetails.FreeformReasons;
            string outputFreeFormReasons = inputFreeFormReasons;
            Assert.AreEqual(inputFreeFormReasons, outputFreeFormReasons);
        }

        
        /// <summary>
        /// Test case for Balance.
        /// </summary>
        [Test]
        public void TestBalance()
        {
            requestDetails.BalanceDue = 9.0;
            double inputBalance = requestDetails.BalanceDue;
            double outputBalance = inputBalance;
            Assert.AreEqual(inputBalance, outputBalance);
        }

        /// <summary>
        /// Test case for Original Balance.
        /// </summary>
        [Test]
        public void TestOriginalBalance()
        {
            requestDetails.OriginalBalance = 10.0;
            double inputBalance = requestDetails.OriginalBalance;
            double outputBalance = inputBalance;
            Assert.AreEqual(inputBalance, outputBalance);
        }

        /// <summary>
        /// Test case for Payment Amount.
        /// </summary>
        [Test]
        public void TestPaymentAmount()
        {
            requestDetails.PaymentAmount = 10.0;
            double inputBalance = requestDetails.PaymentAmount;
            double outputBalance = inputBalance;
            Assert.AreEqual(inputBalance, outputBalance);
        }

        /// <summary>
        /// Test case for Credit Amount.
        /// </summary>
        [Test]
        public void TestCreditAmount()
        {
            requestDetails.CreditAmount = 10.0;
            double inputBalance = requestDetails.CreditAmount;
            double outputBalance = inputBalance;
            Assert.AreEqual(inputBalance, outputBalance);
        }

        /// <summary>
        /// Test case for Debit Amount.
        /// </summary>
        [Test]
        public void TestDebitAmount()
        {
            requestDetails.DebitAmount = 10.0;
            double inputBalance = requestDetails.DebitAmount;
            double outputBalance = inputBalance;
            Assert.AreEqual(inputBalance, outputBalance);
        }

        /// <summary>
        /// Test case for SalesTax Amount.
        /// </summary>
        [Test]
        public void TestSalesTaxAmount()
        {
            requestDetails.SalesTaxAmount = 10.0;
            double inputBalance = requestDetails.SalesTaxAmount;
            double outputBalance = inputBalance;
            Assert.AreEqual(inputBalance, outputBalance);
        }

        /// <summary>
        /// Test case for CertificationLetterRequired.
        /// </summary>
        [Test]
        public void TestCertificationLetterRequired()
        {
            requestDetails.CertificationLetterRequired = true;
            Assert.IsTrue(requestDetails.CertificationLetterRequired);
        }

        /// <summary>
        /// Test case for RequestorActive.
        /// </summary>
        [Test]
        public void TestRequestorActive()
        {
            requestDetails.RequestorActive = true;
            Assert.IsTrue(requestDetails.RequestorActive);
        }

        /// <summary>
        /// Test case for RequestorFrequent.
        /// </summary>
        [Test]
        public void TestRequestorFrequent()
        {
            requestDetails.RequestorFrequent = true;
            Assert.IsTrue(requestDetails.RequestorFrequent);
        }

        /// <summary>
        /// Test case for PrepayRequired.
        /// </summary>
        [Test]
        public void TestPrepayRequired()
        {
            requestDetails.PrepayRequired = true;
            Assert.IsTrue(requestDetails.PrepayRequired);
        }

        /// <summary>
        /// Test case for RequestorIsPatient.
        /// </summary>
        [Test]
        public void TestRequestorIsPatient()
        {
            requestDetails.RequestorIsPatient = true;
            Assert.IsTrue(requestDetails.RequestorIsPatient);
        }


        /// <summary>
        /// Test Case for Encounters
        /// </summary>
        [Test]
        public void TestEncounters()
        {
            Collection<string> encounters = requestDetails.Encounters;
        }

        /// <summary>
        /// Test Case for Comparer
        /// </summary>
        [Test]
        public void TestComparer()
        {
            RequestDetails x = new RequestDetails();
            x.UpdatedBy = "xx";
            RequestDetails y = new RequestDetails();
            x.UpdatedBy = "yy";
            int result = RequestDetails.Sorter.Compare(x, y);
            Assert.AreEqual(1, result);
        }

        [Test]
        public void TestDraftRelease()
        {
            requestDetails.DraftRelease = new ReleaseDetails();
            Assert.IsTrue(requestDetails.HasDraftRelease);
            Assert.IsNotNull(requestDetails.DraftRelease);
        }

        [Test]
        public void TesIsLocked()
        {
            requestDetails.IsLocked = true;
            Assert.IsTrue(requestDetails.IsLocked);

        }

        /// <summary>
        /// Test case for InUse Image.
        /// </summary>
        [Test]
        public void TestInUseImage()
        {
            requestDetails.InUseImage = null;
            Assert.IsNull(requestDetails.InUseImage);
        }

        /// <summary>
        /// Test case for RequestReasonAttribute.
        /// </summary>
        [Test]
        public void TestRequestReasonAttribute()
        {
            string attributeName = "TPO";
            requestDetails.RequestReasonAttribute = attributeName;
            Assert.AreEqual(attributeName, requestDetails.RequestReasonAttribute);
        }

        /// <summary>
        /// Test case for Status Changed
        /// </summary>
        [Test]
        public void TestStatusChanged()
        {
            Nullable<DateTime> inputStatusChanged = DateTime.Now;
            requestDetails.StatusChanged = inputStatusChanged;
            Nullable<DateTime> outputStatusChanged = requestDetails.StatusChanged;
            Assert.AreEqual(inputStatusChanged, outputStatusChanged);
        }

        /// <summary>
        /// Test case for Auth request
        /// </summary>
        [Test]
        public void TestAuthRequest()
        {
            string authRequest = "AuthRequest";
            requestDetails.AuthRequest = authRequest;
            Assert.AreEqual(authRequest, requestDetails.AuthRequest);
        }

        /// <summary>
        /// Test case for locked Image.
        /// </summary>
        [Test]
        public void TestLockedImage()
        {
            requestDetails.LockedImage = null;
            Assert.IsNull(requestDetails.LockedImage);
        }

        /// <summary>
        /// Test case for VIP Image.
        /// </summary>
        [Test]
        public void TestVIPImage()
        {
            requestDetails.VipImage = null;
            Assert.IsNull(requestDetails.VipImage);
        }

        /// <summary>
        /// Test case for DeleteRelease.
        /// </summary>
        [Test]
        public void TestDeleteRelease()
        {
            requestDetails.DeleteRelease = false;
            Assert.IsFalse(requestDetails.DeleteRelease);
        }

        /// <summary>
        /// Test case for HasVipPatient.
        /// </summary>
        [Test]
        public void TestHasVipPatient()
        {
            requestDetails.HasVipPatient = false;
            Assert.IsFalse(requestDetails.HasVipPatient);
        }

        /// <summary>
        /// Test case for HasBlockedRequestFacility
        /// </summary>
        [Test]
        public void TestHasBlockedRequestFacility()
        {
            requestDetails.HasBlockedRequestFacility = false;
            Assert.IsFalse(requestDetails.HasBlockedRequestFacility);
        }

        /// <summary>
        /// Test case for HasMaskedRequestFacility.
        /// </summary>
        [Test]
        public void TestHasMaskedRequestFacility()
        {
            requestDetails.HasMaskedRequestFacility = false;
            Assert.IsFalse(requestDetails.HasMaskedRequestFacility);
        }

        /// <summary>
        /// Test case for UnauthorizedRequestCount.
        /// </summary>
        [Test]
        public void TestUnauthorizedRequest()
        {
            int unauthorizedRequestCount = 0;
            requestDetails.UnauthorizedRequest = unauthorizedRequestCount;
            Assert.AreEqual(unauthorizedRequestCount, requestDetails.UnauthorizedRequest);
        }

        /// <summary>
        /// Test case for HasLockedPatient.
        /// </summary>
        [Test]
        public void TestHasLockedPatient()
        {
            requestDetails.HasLockedPatient = false;
            Assert.IsFalse(requestDetails.HasLockedPatient);
        }

        [Test]
        public void TestPartaillyReelased()
        {
            RequestPatientDetails patient = new RequestPatientDetails();
            patient.FullName = "Andrew Clarke";
            patient.Id = 2;
            patient.MRN = "123";
            patient.SSN = "56";
            patient.IsHpf = false;

            requestDetails.Patients.Add(patient.Key, patient);

            Assert.IsFalse(requestDetails.IsPartiallyReleased);

        }

        /// <summary>
        /// Test case for Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {            
            string inputFacility = "IE";
            requestDetails.Facility = inputFacility;
            string outputFacility = requestDetails.Facility;
            Assert.AreEqual(inputFacility, outputFacility);

            requestDetails.Id = 1;
            Assert.IsNotNull(requestDetails.RequestFacility);
        }

        /// <summary>
        /// Test case for Request Completed Date.
        /// </summary>
        [Test]
        public void TestRequestCompletedDate()
        {
            Nullable<DateTime> inputRequestCompletedDate = DateTime.Now;
            requestDetails.CompletedDate = inputRequestCompletedDate;
            Nullable<DateTime> outputRequestCompletedDate = requestDetails.CompletedDate;
            Assert.AreEqual(inputRequestCompletedDate, outputRequestCompletedDate.Value);
        }

        [Test]
        public void TestReceiptDateText()
        {
            requestDetails.ReceiptDateText = "01/01/2010";
            Assert.AreEqual("01/01/2010", requestDetails.ReceiptDateText);
        }

        #endregion
    }
}
