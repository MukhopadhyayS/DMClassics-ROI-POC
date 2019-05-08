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
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Configuration;
using System.Globalization;
using System.Reflection;
using System.Resources;
using System.Xml;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.Controller;

using McK.EIG.ROI.Client.Web_References.ROIRequestCoreWS;
using McK.EIG.ROI.Client.Web_References.BillingCoreWS;
using RequestCore = McK.EIG.ROI.Client.Web_References.ROIRequestCoreWS;
using BillingCore = McK.EIG.ROI.Client.Web_References.BillingCoreWS;
using McK.EIG.ROI.Client.Web_References.MedicalRecordWS;

namespace McK.EIG.ROI.Client.Request.Controller
{
    /// <summary>
    /// Request Level Controller.
    /// </summary>
    public partial class RequestController : ROIController
    {
     
        #region Fields

        private static object syncRoot = new Object();

        //private static variable of RequestController
        private static volatile RequestController requestController;

        private RequestCoreServiceWse requestCoreService;

        private BillingCoreServiceWse billingCoreService;

        private Dictionary<long, bool> pageStatus;

        private Dictionary<long, bool> nonHPFDocumentStatus;

        private Dictionary<long, bool> attachmentStatus;

        private MedicalRecordServiceWse medicalRecordService;

        #endregion

        #region Constructor

        /// <summary>
        /// Initilize the Request Service.
        /// </summary>
        private RequestController()
        {   
            requestCoreService = new RequestCoreServiceWse();
            billingCoreService = new BillingCoreServiceWse();
            medicalRecordService = new MedicalRecordServiceWse();
        }

        #endregion

        #region Service Methods

        public FindRequestResult FindRequest(FindRequestCriteria searchCriteria, PaginationDetails paginationInfo)
        {            
            if (searchCriteria.IsSearch)
            {
                searchCriteria.Normalize();
            }

            RequestValidator validator = new RequestValidator();
            //Added for special character validation.
            if (!validator.ValidateSearchFields(searchCriteria) ||
                McK.EIG.ROI.Client.Request.View.FindRequest.RequestSearchUI.IsError)
            {
                throw validator.ClientException;
            }

            RequestCoreSearchCriteria serverSearchCriteria = MapModel(searchCriteria);
            RequestCore.PaginationData serverData = null;
            if (paginationInfo != null)
            {
                serverData = MapModel(paginationInfo);
            }
            serverSearchCriteria.paginationData = (RequestCore.PaginationData)serverData;            
            object[] roiRequestParams = new object[] { serverSearchCriteria };
            object roiResponse = ROIHelper.Invoke(requestCoreService, "searchRequest", roiRequestParams);

            Collection<string> objectIds = new Collection<string>();
            FindRequestResult requestResult = MapModel((RequestCoreSearchResultList)roiResponse, objectIds);       

            //Check whether the requests are used by another user
            if (objectIds.Count > 0)
            {
                SortedList<string, InUseRecordDetails> inUseRecords = RetrieveInUseRecords(ROIConstants.RequestDomainType, objectIds);
                if (inUseRecords == null) return requestResult;

                int index;
                foreach (InUseRecordDetails inUseRec in inUseRecords.Values)
                {
                    index = objectIds.IndexOf(inUseRec.ObjectId);
                    requestResult.RequestSearchResult[index].IsLocked = true;
                    requestResult.RequestSearchResult[index].InUseRecord = inUseRec;
                }
            }
            return requestResult;
        }

        /// <summary>
        /// Retrieves request count.
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        public long RetrieveRequestCount(FindRequestCriteria searchCriteria)
        {
            RequestCoreSearchCriteria requestCoreSearchCriteria = MapModel(searchCriteria);
            object[] requestParams = new object[] { requestCoreSearchCriteria };
            object response = ROIHelper.Invoke(requestCoreService, "getRequestCount", requestParams);
            
            return (long)response;
        }

        /// <summary>
        /// Add new commet.
        /// </summary>
        /// <param name="commentDetails">CommentDetails</param>
        /// <returns>CommentDetails</returns>
        public CommentDetails CreateComment(CommentDetails commentDetails)
        {
            commentDetails.Normalize();
            RequestValidator validator = new RequestValidator();
            if (!validator.ValidateComment(commentDetails))
            {
                throw validator.ClientException;
            }
            RequestEvent serverEvent = MapModel(commentDetails);
            object[] requestParams = new object[] { serverEvent };
            ROIHelper.Invoke(requestCoreService, "addEvent", requestParams);
            return MapModelForComment(requestParams[0] as RequestEvent);
        }

        /// <summary>
        /// Add new comment list
        /// </summary>
        /// <param name="commentDetails"></param>
        /// <returns></returns>
        public void CreateCommentsList(Collection<CommentDetails> commentDetails)
        {
            AuditAndEventList serverAuditAndEventList = new AuditAndEventList();
            List<RequestEvent> serverEventList = new List<RequestEvent>();

            foreach (CommentDetails details in commentDetails)
            {
                details.Normalize();
                RequestValidator validator = new RequestValidator();

                if (!validator.ValidateComment(details))
                {
                    throw validator.ClientException;
                }

                RequestEvent serverEvent = MapModel(details);
                serverEventList.Add(serverEvent);
            }
            serverAuditAndEventList.events = serverEventList.ToArray();

            object[] requestParams = new object[] { serverAuditAndEventList };
            ROIHelper.Invoke(requestCoreService, "addAuditAndEvent", requestParams);            
        }

        
        
        /// <summary>
        /// Method retrieve comment detail for the Specfied request id.
        /// </summary>
        /// <param name="requestId">RequestId</param>
        /// <returns>CommentDetails Collection</returns>
        public Collection<CommentDetails> RetrieveComments(long requestId)
        {
            object[] requestParams = new object[] { requestId };
            object response = ROIHelper.Invoke(requestCoreService, "retrieveComments", requestParams);
            return MapModel(response as Comments);
        }

        /// <summary>
        /// Method retrieve event history detail for the Specfied request id.
        /// </summary>
        /// <param name="requestId">RequestId</param>
        /// <returns>EventHistoryDetails Collection</returns>
        public Collection<RequestEventHistoryDetails> RetrieveEventHistory(long requestId)
        {
            object[] requestParams = new object[] { requestId };
            object response = ROIHelper.Invoke(requestCoreService, "getEventHistory", requestParams);
            return MapModel(response as RequestEvents);
        }


        /// <summary>
        /// Retrieves all events
        /// </summary>
        /// <param name="requestId"></param>
        /// <returns></returns>
        public string[] RetrieveRequestEvents()
        {
            object[] requestParams = new object[0];
            object response = ROIHelper.Invoke(requestCoreService, "retrieveAllEventTypes", requestParams);
            return response as string[];
        }

        /// <summary>
        /// Retrieve request level password for PDF queue.
        /// </summary>
        /// <returns></returns>
        public string RetrieveGeneratedPassword()
        {
            object[] requestParams = new object[0];
            object response = ROIHelper.Invoke(requestCoreService, "getGeneratedPassword", requestParams);
            return response as string;
        }

        /// <summary>
        /// Creates a new request
        /// </summary>
        /// <param name="requestDetails"></param>
        /// <returns></returns>
        public RequestDetails CreateRequest(RequestDetails requestDetails)
        {
            RequestValidator requestValidator = new RequestValidator();
            if (!requestValidator.ValidateRequest(requestDetails))
            {
                throw requestValidator.ClientException;
            }

            //Since we followed genralization mechanism in Request,
            //we are holding the requestor to update it in the request object after making service call. 
            RequestorDetails requestor = requestDetails.Requestor;

            RequestCore.Request serverRequest = MapModel(requestDetails);
            object[] requestParams = new object[] { serverRequest };
            ROIHelper.Invoke(requestCoreService, "createRequest", requestParams);
            Collection<TaxPerFacilityDetails> taxPerFacilityDetails = BillingAdminController.Instance.RetrieveAllTaxPerFacilities();
            RequestPatients requestPatients = new RequestPatients();
            if (requestDetails.Patients.Count > 0)
            {
                UpdateRequestPatients updateRequestPatients = new UpdateRequestPatients();
                DeleteList deleteList = new DeleteList();
                updateRequestPatients.DeleteList = deleteList;
                foreach (RequestPatientDetails patient in requestDetails.Patients.Values)
                {
                    updateRequestPatients.RequestPatients.Add(patient);
                }
                //Naved Commented - Ex version
                SaveRequestPatientsEx(updateRequestPatients, ((RequestCore.Request)requestParams[0]).id);
            }
            requestDetails = MapModel((RequestCore.Request)requestParams[0], true, taxPerFacilityDetails);            
            foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
            {
                requestDetails.Patients.Add(requestPatientDetails.Key, requestPatientDetails);                
            }
            requestDetails.Requestor = requestor;

            return requestDetails;
        }

        /// <summary>
        /// Updates an existing request
        /// </summary>
        /// <param name="requestDetails"></param>
        /// <returns></returns>
        public RequestDetails UpdateRequest(RequestDetails requestDetails)
        {
            RequestValidator requestValidator = new RequestValidator();
            if (!requestValidator.ValidateRequest(requestDetails))
            {
                throw requestValidator.ClientException;
            }
            //Since we followed genralization mechanism in Request,
            //we are holding the requestor to update it in the request object after making service call. 
            RequestorDetails requestor = requestDetails.Requestor;

            RequestCore.Request serverRequest = MapModel(requestDetails);
            if (requestDetails.HasDraftRelease && requestDetails.DeleteRelease)
            {
                //serverRequest.deleteRelease = true;
                //serverRequest.releaseId = requestDetails.DraftRelease.Id;
            }            
            object[] requestParams = new object[] { serverRequest };
            object obj = ROIHelper.Invoke(requestCoreService, "updateRequest", requestParams);
            Collection<TaxPerFacilityDetails> taxPerFacilityDetails = BillingAdminController.Instance.RetrieveAllTaxPerFacilities();            
            requestDetails = MapModel((RequestCore.Request)requestParams[0], true, taxPerFacilityDetails);
            requestDetails.Requestor = requestor;
            return requestDetails;
        }

        /// <summary>
        /// Delete an existing request
        /// </summary>
        /// <param name="id"></param>
        public void DeleteRequest(long id)
        {
            object[] requestParams = new object[] { id };            
            ROIHelper.Invoke(requestCoreService, "deleteRequest", requestParams);
        }

        /// <summary>
        /// Retrieve request.
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public RequestDetails RetrieveRequest(long id, bool hasViewRequest)
        {
            object[] requestParams = new object[] { id, hasViewRequest };            
            object response = ROIHelper.Invoke(requestCoreService, "retrieveRequest", requestParams);
            Collection<TaxPerFacilityDetails> taxPerFacilityDetails = BillingAdminController.Instance.RetrieveAllTaxPerFacilities();
            return MapModel((RequestCore.Request)response, true, taxPerFacilityDetails);
        }

        /// <summary>
        /// Retrieve the request patients for the request.
        /// </summary>
        /// <param name="requestId"></param>
        /// <returns></returns>
        public RequestPatients RetrieveRequestPatients(long requestId)
        {
            if (RequestPatientsCache.IsKeyExist(requestId))
            {
                return RequestPatientsCache.GetReqPatients(requestId);

            }
            else
            {
                object[] requestParams = new object[] { requestId };
                object response = ROIHelper.Invoke(requestCoreService, "retrieveRequestPatient", requestParams);
                RequestPatients reqPat =  MapModel(response as RequestPatientsList);
                RequestPatientsCache.AddData(requestId, reqPat);
                return reqPat;
            }
        }

        /// <summary>
        /// Save the request patients for the request.
        /// </summary>
        /// <param name="requestPatients"></param>
        /// <param name="requestId"></param>
        /// <returns></returns>
        public RequestPatients SaveRequestPatients(UpdateRequestPatients requestPatients, long requestId)
        {
            if (RequestPatientsCache.IsKeyExist(requestId))
            {
                RequestPatientsCache.RemoveKey(requestId);
            }

            SaveRequestPatientList serverRequestPatients = MapModel(requestPatients);
            serverRequestPatients.requestId = requestId;
            object[] requestParams = new object[] { serverRequestPatients };
            object response = ROIHelper.Invoke(requestCoreService, "saveRequestPatient", requestParams);
            RequestPatients reqPat = MapModel(response as RequestPatientsList);
            RequestPatientsCache.AddData(requestId, reqPat);
            return reqPat;

        }

        public void SaveRequestPatientsEx(UpdateRequestPatients requestPatients, long requestId)
        {
			if (RequestPatientsCache.IsKeyExist(requestId))
            {
                RequestPatientsCache.RemoveKey(requestId);
            }

            SaveRequestPatientList serverRequestPatients = MapModel(requestPatients);
            serverRequestPatients.requestId = requestId;
            object[] requestParams = new object[] { serverRequestPatients };
            object response = ROIHelper.Invoke(requestCoreService, "saveRequestPatient", requestParams);            
        }

        //click of save n bill
        public void SaveRequestCoreCharges(RequestCoreChargeDetails requestCoreCharges)
        {
            if (RequestBillingInfoCache.IsKeyExist(requestCoreCharges.RequestId))
            {
                RequestBillingInfoCache.RemoveKey(requestCoreCharges.RequestId);
            }

            RequestCoreCharges requestCore = MapModel(requestCoreCharges);
            object[] requestParams1 = new object[] { requestCore };
            ROIHelper.Invoke(requestCoreService, "saveRequestCoreCharges", requestParams1);
        }

        //on click of revert
        public RequestBillingInfo RetrieveRequestBillingPaymentInfo(long requestId)
        {
            if (RequestBillingInfoCache.IsKeyExist(requestId))
            {
                return RequestBillingInfoCache.GetRequestBillingInfo(requestId);
            }
            else
            {
                RequestBillingInfo reqbillinfo = new RequestBillingInfo();
                object[] requestParams = new object[] { requestId };
                object roiResponse = ROIHelper.Invoke(requestCoreService, "retrieveRequestCoreCharges", requestParams);

                RequestCoreCharges reqCoreChrBillInfo = (RequestCoreCharges)roiResponse;
                if (reqCoreChrBillInfo.createdDt == null)
                {
                    return null;
                }
                else
                    reqbillinfo = MapModel(reqCoreChrBillInfo);

                RequestBillingInfoCache.AddData(requestId,reqbillinfo);
                return reqbillinfo;
            }
        }

        public InvoiceChargeDetailsList RetrieveInvoicesAndAdjPay(long requestId)
        {
            InvoiceChargeDetailsList reqTranslist = new InvoiceChargeDetailsList();
            object[] requestParams = new object[] { requestId };
            object roiResponse = ROIHelper.Invoke(requestCoreService, "retrieveInvoicesAndAdjPay", requestParams);
            reqTranslist = MapModel((RequestCoreChargesInvoicesList)roiResponse);
            //foreach(RequestCoreChargesInvoiceDetail reqInvoice in 
            //reqbillinfo = MapModel((RequestCoreChargesBillingInfo)roiResponse);

            return reqTranslist;
        }

        //click of Continue on Invoice
        public InvoiceAndDocumentDetails InvoiceOrPrebillPreview(InvoiceOrPrebillPreviewInfo InvoiceDetails)
        {
            InvoiceOrPrebillAndPreviewInfo Invoice = MapModel(InvoiceDetails);
            object[] requestParam = new object[] { Invoice };
            object roiResponse = ROIHelper.Invoke(billingCoreService, "createInvoiceOrPrebillAndPreview", requestParam);
            InvoiceAndDocumentDetails obj = new InvoiceAndDocumentDetails();
            obj = MapModel((DocInfo)roiResponse);            
            return obj;
        }

        /// <summary>
        /// Retrieve the latest released document charge based on the billing tier
        /// </summary>
        /// <param name="requestId"></param>
        /// <returns></returns>
        public List<DocumentChargeDetails> RetrieveReleasedDocumentCharges(long requestId)
        {
            object[] requestParams = new object[] { requestId };
            object response = ROIHelper.Invoke(requestCoreService, "retrieveReleasedDocumentChargesByBillingTier", requestParams);
            return MapModel(response as RequestCoreChargesDocument[]);
        }

        public Collection<RequestInvoiceDetail> RetrieveRequestInvoices(long requestId)
        {
            object[] requestParams = new object[] { requestId };
            object response = ROIHelper.Invoke(requestCoreService, "retrieveRequestInvoices", requestParams);
            Collection<RequestInvoiceDetail> reqInvoiceList = new Collection<RequestInvoiceDetail>();
            reqInvoiceList = MapModel(response as RequestorInvoice[]);
            return reqInvoiceList;
        }

        public bool HasSercuirtyRights()
        {
            if (HasSecurityRights.IsSecurityRightsInvoked())
            {
                return HasSecurityRights.GetSecurityRights();
            }
            else
            {
                bool value = false;
                object response = ROIHelper.Invoke(requestCoreService, "hasSecurityRightsForRelease", new object[0]);

                value = (bool)response;
                HasSecurityRights.UpdateSecurityRights(value);
                return value;
            }
        }

        #endregion

        #region Map Model

        private Collection<RequestInvoiceDetail> MapModel(RequestorInvoice[] reqInvoice)
        {
            Collection<RequestInvoiceDetail> requestorInvoiceList = new Collection<RequestInvoiceDetail>();
            if (reqInvoice != null)
            {
                foreach (RequestorInvoice requestorInvoice in reqInvoice)
                {
                    requestorInvoiceList.Add(MapModel(requestorInvoice));
                }
            }
            return requestorInvoiceList;
        }

        public static RequestInvoiceDetail MapModel(RequestorInvoice requestorInvoice)
        {
            RequestInvoiceDetail reqInvoiceDetail = new RequestInvoiceDetail();
            if (requestorInvoice != null)
            {
                reqInvoiceDetail.Adjustments = -(requestorInvoice.adjustmentAmount);
                if (requestorInvoice.invoiceType == "Unapplied Payment" || requestorInvoice.invoiceType == "Unapplied Adjustment")
                {
                    reqInvoiceDetail.Balance = -(requestorInvoice.balance);
                }
                else
                {
                    reqInvoiceDetail.Balance = requestorInvoice.balance;
                }
                reqInvoiceDetail.Charges = requestorInvoice.charge;
                reqInvoiceDetail.CreatedDate = requestorInvoice.createdDt;
                reqInvoiceDetail.Description = requestorInvoice.description;
                reqInvoiceDetail.Id = requestorInvoice.invoiceId;
                reqInvoiceDetail.InvoiceStatus = requestorInvoice.invoiceStatus;
                reqInvoiceDetail.InvoiceType = requestorInvoice.invoiceType;
                reqInvoiceDetail.Payments = -(requestorInvoice.paymentAmount);
                reqInvoiceDetail.PayAdjTotal = requestorInvoice.adjustmentPaymentTotal;
                reqInvoiceDetail.PaymentDescription = requestorInvoice.paymentDescription;
                reqInvoiceDetail.PaymentMethod = requestorInvoice.paymentMethod;
                reqInvoiceDetail.ReqAdjPay = new List<RequestorAdjustmentsPaymentsDetail>();
                if (requestorInvoice.requestorAdjPay != null)
                {
                    foreach (RequestorAdjustmentsPayments reqAdjPayment in requestorInvoice.requestorAdjPay)
                    {
                        reqInvoiceDetail.ReqAdjPay.Add(MapModel(reqAdjPayment));

                    }
                }
                reqInvoiceDetail.RequestId = requestorInvoice.requestId;
                reqInvoiceDetail.UnBillableAmount = requestorInvoice.unBillableAmount;
            }
            return reqInvoiceDetail;
        }

        public static RequestorAdjustmentsPaymentsDetail MapModel(RequestorAdjustmentsPayments reqAdjPayment)
        {
            RequestorAdjustmentsPaymentsDetail reqAdjPay = new RequestorAdjustmentsPaymentsDetail();
            reqAdjPay.Amount = reqAdjPayment.paymentAmount;
            reqAdjPay.AppliedAmount = reqAdjPayment.appliedAmount;
            reqAdjPay.Date = reqAdjPayment.date.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture);
            reqAdjPay.Id = reqAdjPayment.invoiceId;
            reqAdjPay.TxnType = reqAdjPayment.txnType;
            reqAdjPay.UnAppliedAmt = reqAdjPayment.unAppliedAmt;
            reqAdjPay.PaymentId = reqAdjPayment.id;
            reqAdjPay.PaymentMethod = reqAdjPayment.paymentMethod;
            reqAdjPay.Description = reqAdjPayment.description;
            return reqAdjPay;
        }

        /// <summary>
        /// Convert the client request patients into server request patients
        /// </summary>
        /// <param name="clientRequestPatients"></param>
        /// <returns></returns>
        private static SaveRequestPatientList MapModel(UpdateRequestPatients clientRequestPatients)
        {
            SaveRequestPatientList serverRequestPatients = new SaveRequestPatientList();

            //Convert newly added request patients into server request patients
            List<RequestPatient> serverRequestPatientList = MapModel(clientRequestPatients.RequestPatients);
            serverRequestPatients.updatePatients = serverRequestPatientList.ToArray();
            
            DeletePatientList serverDeletePatient = new DeletePatientList();
            
            serverDeletePatient.patientSeq = clientRequestPatients.DeleteList.DeletedPatients.ToArray();
            serverDeletePatient.darPatientSeq = clientRequestPatients.DeleteList.DeletedPatientList.ToArray();
            serverDeletePatient.encounterSeq = clientRequestPatients.DeleteList.DeletedEncounters.ToArray();
            serverDeletePatient.docSeq = clientRequestPatients.DeleteList.DeletedDocuments.ToArray();
            serverDeletePatient.versionSeq = clientRequestPatients.DeleteList.DeletedVersions.ToArray();
            serverDeletePatient.pageSeq = clientRequestPatients.DeleteList.DeletedPages.ToArray();
            serverDeletePatient.supplementalDocumentSeq = clientRequestPatients.DeleteList.DeletesupplementalDocuments.ToArray();
            serverDeletePatient.supplementalAttachmentSeq = clientRequestPatients.DeleteList.DeleteSupplementalAttachments.ToArray();
            serverDeletePatient.documentSeq = clientRequestPatients.DeleteList.DeleteSupplementaryDocuments.ToArray();
            serverDeletePatient.attachmentSeq = clientRequestPatients.DeleteList.DeleteSupplementaryAttachments.ToArray();
            serverDeletePatient.supplementalPatientSeq = clientRequestPatients.DeleteList.DeletesupplementalPatients.ToArray(); 
            serverDeletePatient.darSuppPatientSeq = clientRequestPatients.DeleteList.DeleteDARSupplementalPatients.ToArray();

            serverRequestPatients.deletePatient = serverDeletePatient;           

            //Converts newly added client encounters to server
            serverRequestPatients.updateEncounters = MapModel(clientRequestPatients.RequestEncounters).ToArray();
            //Converts newly added client documents to server
            serverRequestPatients.updateDocuments = MapModel(clientRequestPatients.RequestDocuments).ToArray();
            //Converts newly added client versions to server
            serverRequestPatients.updateVersions = MapModel(clientRequestPatients.RequestVersions).ToArray();
            //Converts newly added client pages and updated pages ( Select/Unselect pages ) to server
            serverRequestPatients.updatePages = MapModel(clientRequestPatients.RequestPages).ToArray();

            List<RequestSupplementalDocument> requestSupplementalDocuments = new List<RequestSupplementalDocument>();
            int patientCount = 0;
            foreach (RequestNonHpfEncounterDetails nonHpfEncounter in clientRequestPatients.RequestNonHPFEncounters)
            {
                foreach (RequestNonHpfDocumentDetails nonHPFDocument in nonHpfEncounter.GetChildren)
                {
                    requestSupplementalDocuments.Add(MapModel(nonHPFDocument));
                    patientCount++;
                }
            }

            patientCount = 0;
            List<RequestSupplementalAttachment> requestSupplementalAttachments = new List<RequestSupplementalAttachment>();
            foreach (RequestAttachmentEncounterDetails attachmentEncounter in clientRequestPatients.RequestAttachmentEncounterDetails)
            {
                foreach (RequestAttachmentDetails requestAttachment in attachmentEncounter.GetChildren)
                {
                    requestSupplementalAttachments.Add(MapModel(requestAttachment));
                    patientCount++;
                }
            }

            serverRequestPatients.updateSupplementalDocument = requestSupplementalDocuments.ToArray();
            serverRequestPatients.updateSupplementalAttachement = requestSupplementalAttachments.ToArray();

            return serverRequestPatients;
        }

        /// <summary>
        /// Converts the newly added client encounters to server encounters.
        /// </summary>
        /// <param name="clientRequestEncounters"></param>
        /// <returns></returns>        
        private static List<RequestEncounter> MapModel(List<RequestEncounterDetails> clientRequestEncounters)
        {
            List<RequestEncounter> serverRequestEncounters = new List<RequestEncounter>();
            
            if (clientRequestEncounters != null)
            {
                foreach(RequestEncounterDetails clientRequestEncounter in clientRequestEncounters)
                {
                     serverRequestEncounters.Add(MapModel(clientRequestEncounter));                    
                }
            }            
            return serverRequestEncounters;
        }

        /// <summary>
        /// Converts the newly added client documents to server documents.
        /// </summary>
        /// <param name="clientRequestDocuments"></param>
        /// <returns></returns>        
        private static List<RequestDocument> MapModel(List<RequestDocumentDetails> clientRequestDocuments)
        {

            List<RequestDocument> serverRequestDocuments = new List<RequestDocument>();
            
            if (clientRequestDocuments != null)
            {
                foreach (RequestDocumentDetails clientRequestDocument in clientRequestDocuments)
                {
                    serverRequestDocuments.Add(MapModel(clientRequestDocument));
                }
            }

            return serverRequestDocuments;
        }

        /// <summary>
        /// Converts the newly added client versions to server versions.
        /// </summary>
        /// <param name="clientRequestVersions"></param>
        /// <returns></returns>        
        private static List<RequestVersion> MapModel(List<RequestVersionDetails> clientRequestVersions)
        {
            List<RequestVersion> serverRequestVersions = new List<RequestVersion>();;
           
            if (clientRequestVersions != null)
            {  
                foreach (RequestVersionDetails clientRequestVersion in clientRequestVersions)
                {
                   serverRequestVersions.Add(MapModel(clientRequestVersion));
                }
            }

            return serverRequestVersions;
        }

        /// <summary>
        /// Converts the newly added client pages to server pages as well as pages 
        /// which are the selected/Unselected ( Only if any changes against value in DB ) in DSR tree
        /// </summary>
        /// <param name="clientRequestPages"></param>
        /// <returns></returns>        
        private static List<RequestPage> MapModel(List<RequestPageDetails> clientRequestPages)
        {   
            List<RequestPage> serverRequestPages = new List<RequestPage>();
            
            if (clientRequestPages != null)
            {               
                foreach (RequestPageDetails clientRequestPage in clientRequestPages)
                {
                    serverRequestPages.Add(MapModel(clientRequestPage));
                }
            }

            return serverRequestPages;
        }

        /// <summary>
        /// Converts the client request patients into server request patients 
        /// </summary>
        /// <param name="clientRequestPatients"></param>
        /// <returns></returns>
        private static List<RequestPatient> MapModel(List<RequestPatientDetails> clientRequestPatients)
        {
            List<RequestPatient> serverRequestPatientList = new List<RequestPatient>();

            foreach (RequestPatientDetails clientRequestPatient in clientRequestPatients)
            {
                serverRequestPatientList.Add(MapModel(clientRequestPatient));
            }
            return serverRequestPatientList;
        }

        /// <summary>
        /// Converts the client request patients into server request patients 
        /// </summary>
        /// <param name="clientRequestPatient"></param>
        /// <returns></returns>
        private static RequestPatient MapModel(RequestPatientDetails clientRequestPatient)
        {
            RequestPatient serverRequestPatient = new RequestPatient();
            serverRequestPatient.name = clientRequestPatient.FullName;
            switch (clientRequestPatient.Gender)
            {
                case "Male": serverRequestPatient.gender = "M"; break;
                case "Female": serverRequestPatient.gender = "F"; break;
                case "Unknown": serverRequestPatient.gender = "U"; break;
            }            
            serverRequestPatient.epn = clientRequestPatient.EPN;
            serverRequestPatient.mrn = clientRequestPatient.MRN;
            serverRequestPatient.lastName = clientRequestPatient.LastName;
            serverRequestPatient.firstName = clientRequestPatient.FirstName;
            serverRequestPatient.address1 = clientRequestPatient.Address1;
            serverRequestPatient.address2 = clientRequestPatient.Address2;
            serverRequestPatient.address3 = clientRequestPatient.Address3;
            serverRequestPatient.city = clientRequestPatient.City;
            serverRequestPatient.state = clientRequestPatient.State;
            serverRequestPatient.zip = clientRequestPatient.PostalCode;
            serverRequestPatient.homePhone = clientRequestPatient.HomePhone;
            serverRequestPatient.workPhone = clientRequestPatient.WorkPhone;
            if (!clientRequestPatient.IsHpf)
            {
                if (clientRequestPatient.IsFreeformFacility)
                {
                    serverRequestPatient.freeformFacility = clientRequestPatient.FacilityCode;
                }
                else
                {
                    serverRequestPatient.facility = clientRequestPatient.FacilityCode;
                }
            }
            else
            {
                serverRequestPatient.facility = clientRequestPatient.FacilityCode;
            }            
            serverRequestPatient.ssn = clientRequestPatient.SSN;
            serverRequestPatient.isVip = clientRequestPatient.IsVip;
            serverRequestPatient.patientLocked = clientRequestPatient.IsLockedPatient;
            serverRequestPatient.hpf = clientRequestPatient.IsHpf;
            serverRequestPatient.supplementalId = clientRequestPatient.Id;
            if (clientRequestPatient.DOB.HasValue)
            {
                serverRequestPatient.dob = clientRequestPatient.DOB.Value; 
            }
            List<RequestEncounter> serverRequestEncounters = new List<RequestEncounter>();

            //For HPF Patient's request global documents.
            List<RequestDocument> serverRequestGlobalDocuments = new List<RequestDocument>();

            foreach (RequestDocumentDetails clientRequestDocument in clientRequestPatient.GlobalDocument.GetChildren)
            {   
                serverRequestGlobalDocuments.Add(MapModel(clientRequestDocument));
            }

            foreach (RequestEncounterDetails encounter in clientRequestPatient.GetChildren)
            {
                encounter.Mrn = clientRequestPatient.MRN;
                serverRequestEncounters.Add(MapModel(encounter));
            }

            List<RequestSupplementalDocument> requestSupplementalDocuments = new List<RequestSupplementalDocument>();
            foreach (RequestNonHpfEncounterDetails nonHpfEncounter in clientRequestPatient.NonHpfDocument.GetChildren)
            {
                foreach (RequestNonHpfDocumentDetails nonHPFDocument in nonHpfEncounter.GetChildren)
                {
                    nonHPFDocument.IsPatientFreeFormFacility = clientRequestPatient.IsFreeformFacility;
                    requestSupplementalDocuments.Add(MapModel(nonHPFDocument));
                }
            }

            List<RequestSupplementalAttachment> requestSupplementalAttachments = new List<RequestSupplementalAttachment>();
            foreach (RequestAttachmentEncounterDetails attachmentEncounter in clientRequestPatient.Attachment.GetChildren)
            {
                foreach (RequestAttachmentDetails requestAttachment in attachmentEncounter.GetChildren)
                {
                    requestAttachment.IsPatientFreeFormFacility = clientRequestPatient.IsFreeformFacility;
                    requestSupplementalAttachments.Add(MapModel(requestAttachment));
                }
            }
            serverRequestPatient.globalDocuments = serverRequestGlobalDocuments.ToArray();
            serverRequestPatient.roiEncounters = serverRequestEncounters.ToArray();
            serverRequestPatient.nonHpfDocuments = requestSupplementalDocuments.ToArray();
            serverRequestPatient.attachments = requestSupplementalAttachments.ToArray();

            return serverRequestPatient;
        }

        /// <summary>
        /// Converst the client request patient encounter into server request patient encounter.
        /// </summary>
        /// <param name="clientRequestEncounter"></param>
        /// <returns></returns>
        private static RequestEncounter MapModel(RequestEncounterDetails clientRequestEncounter)
        {
            RequestEncounter serverRequestEncounter = new RequestEncounter();

            serverRequestEncounter.patientSeq = clientRequestEncounter.PatientSeq;
            serverRequestEncounter.facility = clientRequestEncounter.Facility;
            serverRequestEncounter.patientType = clientRequestEncounter.PatientType;
            serverRequestEncounter.patientService = clientRequestEncounter.PatientService;
            serverRequestEncounter.isVip = clientRequestEncounter.IsVip;
            serverRequestEncounter.isLocked = clientRequestEncounter.IsLocked;
            serverRequestEncounter.hasDeficiency = clientRequestEncounter.HasDeficiency;
            serverRequestEncounter.name = clientRequestEncounter.EncounterId;
            serverRequestEncounter.mrn = clientRequestEncounter.Mrn;
            if (clientRequestEncounter.AdmitDate.HasValue)
            {
                serverRequestEncounter.admitdate = clientRequestEncounter.AdmitDate.Value;
            }

            if (clientRequestEncounter.DischargeDate.HasValue)
            {
                serverRequestEncounter.dischargeDate = clientRequestEncounter.DischargeDate.Value;
            }

            List<RequestDocument> serverRequestDocuments = new List<RequestDocument>();

            foreach (RequestDocumentDetails clientRequestDocument in clientRequestEncounter.GetChildren)
            {
                clientRequestDocument.Encounter = clientRequestEncounter.Name;
                clientRequestDocument.Facility = clientRequestEncounter.Facility;
                clientRequestDocument.Mrn = clientRequestEncounter.Mrn;
                serverRequestDocuments.Add(MapModel(clientRequestDocument)); ;
            }

            serverRequestEncounter.roiDocuments = serverRequestDocuments.ToArray();

            return serverRequestEncounter;
        }

        /// <summary>
        /// Converts the client request patient document into server request patient document.
        /// </summary>
        /// <param name="clientRequestDocument"></param>
        /// <returns></returns>
        private static RequestDocument MapModel(RequestDocumentDetails clientRequestDocument)
        {   
            RequestDocument serverRequestDocument = new RequestDocument();
            serverRequestDocument.encounterSeq = clientRequestDocument.EncounterSeq;
            serverRequestDocument.patientSeq = clientRequestDocument.PatientSeq;
            serverRequestDocument.name = clientRequestDocument.DocType;
            serverRequestDocument.subtitle = clientRequestDocument.Subtitle;
            serverRequestDocument.chartOrder = clientRequestDocument.ChartOrder;
            serverRequestDocument.dateTime = clientRequestDocument.DocumentDateTime;
            serverRequestDocument.docId = clientRequestDocument.DocumentId;
            serverRequestDocument.docTypeId = clientRequestDocument.DocTypeId;
            serverRequestDocument.globalDocument = clientRequestDocument.IsGlobalDocument;
            serverRequestDocument.patientSeq = clientRequestDocument.PatientSeq;
            serverRequestDocument.encounter = clientRequestDocument.Encounter;
            serverRequestDocument.facility = clientRequestDocument.Facility;
            serverRequestDocument.mrn = clientRequestDocument.Mrn;
            

            List<RequestVersion> serverRequestVersions = new List<RequestVersion>();

            foreach (RequestVersionDetails requestVersionDetails in clientRequestDocument.GetChildren)
            {
                requestVersionDetails.DocumentId = clientRequestDocument.DocumentId;
                serverRequestVersions.Add(MapModel(requestVersionDetails));
            }

            serverRequestDocument.roiVersions = serverRequestVersions.ToArray();

            return serverRequestDocument;
        }

        /// <summary>
        /// Convert the client request version into server request version.
        /// </summary>
        /// <param name="clientRequestVersion"></param>
        /// <returns></returns>
        private static RequestVersion MapModel(RequestVersionDetails clientRequestVersion)
        {
            RequestVersion serverRequestVersion = new RequestVersion();
            serverRequestVersion.versionNumber = clientRequestVersion.VersionNumber == 9999999 ? 0 : clientRequestVersion.VersionNumber;
            serverRequestVersion.docId = clientRequestVersion.DocumentId;
            serverRequestVersion.documentSeq = clientRequestVersion.DocumentSeq;
            serverRequestVersion.globalDocument = clientRequestVersion.IsGlobalDocumentVersion;

            List<RequestPage> serverRequestPages = new List<RequestPage>();

            foreach (RequestPageDetails clientRequestPage in clientRequestVersion.GetChildren)
            {
                serverRequestPages.Add(MapModel(clientRequestPage));
            }

            serverRequestVersion.roiPages = serverRequestPages.ToArray();

            return serverRequestVersion;
        }

        /// <summary>
        /// Converts the client request page into server request page.
        /// </summary>
        /// <param name="clientRequestPage"></param>
        /// <returns></returns>
        private static RequestPage MapModel(RequestPageDetails clientRequestPage)
        {
            RequestPage serverRequestPage = new RequestPage();

            serverRequestPage.versionSeq = clientRequestPage.VersionSeq;
            serverRequestPage.pageSeq = clientRequestPage.PageSeq;
            serverRequestPage.pageNumber = clientRequestPage.PageNumber;
            serverRequestPage.pageNumberRequested = clientRequestPage.PageNumberRequested;
            serverRequestPage.imnetId = clientRequestPage.IMNetId;
            serverRequestPage.contentCount = clientRequestPage.ContentCount;
            serverRequestPage.isSelectedForRelease = Convert.ToBoolean(clientRequestPage.SelectedForRelease);
            serverRequestPage.isReleased = clientRequestPage.IsReleased;
            serverRequestPage.globalDocument = clientRequestPage.IsGlobalDocumentPage;

            return serverRequestPage;
        }

        /// <summary>
        /// Converts the client request Non-HPFDocuments into server request Non-HPFDocuments.
        /// </summary>
        /// <param name="clientRequestNonHPFEncounterDetails"></param>
        /// <returns></returns>
        private static RequestSupplementalDocument MapModel(RequestNonHpfDocumentDetails nonHPFDocument)
        {   
            RequestSupplementalDocument serverRequestSupplementalDocument = null;
            //foreach (RequestNonHpfDocumentDetails nonHPFDocument in clientRequestNonHPFEncounterDetails.GetChildren)
            //{
                serverRequestSupplementalDocument = new RequestSupplementalDocument();
                serverRequestSupplementalDocument.patientSeq = nonHPFDocument.PatientSeq;
                serverRequestSupplementalDocument.documentSeq = nonHPFDocument.Id;
                serverRequestSupplementalDocument.documentCoreSeq = nonHPFDocument.DocumentSeq;
                serverRequestSupplementalDocument.supplementalId = nonHPFDocument.PatientID;
                serverRequestSupplementalDocument.docName = nonHPFDocument.Name;
                serverRequestSupplementalDocument.encounter = nonHPFDocument.Encounter;
                serverRequestSupplementalDocument.docFacility = nonHPFDocument.Facility;
                serverRequestSupplementalDocument.department = nonHPFDocument.Department;
                serverRequestSupplementalDocument.dateOfService = nonHPFDocument.DateOfService;
                serverRequestSupplementalDocument.mrn = nonHPFDocument.PatientMRN;
                if (!nonHPFDocument.IsPatientFreeFormFacility)
                {
                    serverRequestSupplementalDocument.facility = nonHPFDocument.PatientFacility;
                }
                serverRequestSupplementalDocument.comment = nonHPFDocument.Comment;
                serverRequestSupplementalDocument.isSelectedForRelease = (bool)nonHPFDocument.SelectedForRelease;
                serverRequestSupplementalDocument.isReleased = nonHPFDocument.IsReleased;                
                serverRequestSupplementalDocument.subtitle = nonHPFDocument.Subtitle;                
                serverRequestSupplementalDocument.pageCount = nonHPFDocument.PageCount.ToString();//TODO - PageCount needs to be int in server model.
                serverRequestSupplementalDocument.billingTierId = nonHPFDocument.BillingTier;
                
            //}
            return serverRequestSupplementalDocument;
        }

        /// <summary>
        /// Converts the client request Attachments into server request Attachments.
        /// </summary>
        /// <param name="clientRequestAttachmentDetails"></param>
        /// <returns></returns>
        private static RequestSupplementalAttachment MapModel(RequestAttachmentDetails requestAttachment)
        {
            RequestSupplementalAttachment serverRequestAttachment = null;

            serverRequestAttachment = new RequestSupplementalAttachment();
            serverRequestAttachment.type = requestAttachment.AttachmentType;
            serverRequestAttachment.patientSeq = requestAttachment.PatientSeq;
            serverRequestAttachment.supplementalId = requestAttachment.PatientID;
            serverRequestAttachment.attachmentSeq = requestAttachment.Id;
            serverRequestAttachment.attachmentCoreSeq = requestAttachment.AttachmentSeq;
            serverRequestAttachment.encounter = requestAttachment.Encounter;
            serverRequestAttachment.docFacility = requestAttachment.Facility;                                
            //requestAttachment.FileId; TODO There is no field called fileid available in serverRequestAttachment.
            serverRequestAttachment.filename = requestAttachment.FileName;
            serverRequestAttachment.filetype = requestAttachment.FileType;
            serverRequestAttachment.fileext = requestAttachment.FileExt;
            serverRequestAttachment.printable = requestAttachment.IsPrintable.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            serverRequestAttachment.attachmentDate = requestAttachment.FileAttachDate;                
            serverRequestAttachment.dateOfService = requestAttachment.DateOfService;
            serverRequestAttachment.comment = requestAttachment.Comment;
            serverRequestAttachment.isSelectedForRelease = (bool) requestAttachment.SelectedForRelease;
            serverRequestAttachment.isReleased = requestAttachment.IsReleased;
            serverRequestAttachment.subtitle = requestAttachment.Subtitle;
            serverRequestAttachment.pageCount = requestAttachment.PageCount.ToString();//TODO - PageCount needs to be int in server model.
            serverRequestAttachment.mrn = requestAttachment.PatientMRN;
            if (!requestAttachment.IsPatientFreeFormFacility)
            {
                serverRequestAttachment.facility = requestAttachment.PatientFacility;
            }
            //requestAttachment.BillingTier TODO Billing Tier needs to added in serverRequestAttachment.
            serverRequestAttachment.billingTierId = requestAttachment.BillingTier;
            serverRequestAttachment.uuid = requestAttachment.Uuid;
            serverRequestAttachment.volume = requestAttachment.Volume;
            serverRequestAttachment.path = requestAttachment.Path;
            serverRequestAttachment.submittedBy = UserData.Instance.UserId;
            serverRequestAttachment.externalSource = requestAttachment.ExternalSource;
            
            return serverRequestAttachment;
        }

        /// <summary>
        /// Method to get self pay encounters
        /// </summary>
        /// <param name="serverRequestPatients"></param>
        /// <returns></returns>
        private ArrayList GetSelfPayEncounterDetails(RequestPatientsList serverRequestPatients)
        {
            ArrayList mrns = new ArrayList();
            RequestPatientDetails client;
            Hashtable encounterDetails = new Hashtable();
            foreach (RequestPatient server in serverRequestPatients.requestPatients)
            {
                client = new RequestPatientDetails();
                if (server.supplementalId > 0)
                {
                    continue;
                }

                if (encounterDetails.ContainsKey(server.facility))
                {
                    mrns = (ArrayList)encounterDetails[server.facility];
                    mrns.Add(server.mrn);
                }
                else
                {
                    mrns = new ArrayList();
                    mrns.Add(server.mrn);
                    encounterDetails.Add(server.facility, mrns);
                }
            }

            ArrayList selfPayencounters = new ArrayList();
            foreach (DictionaryEntry entry in encounterDetails)
            {
                string facility = (string)entry.Key;
                ArrayList mrnDetails = (ArrayList)entry.Value;
                string[] mrnArray = (string[])mrnDetails.ToArray(typeof(string));
                object[] requestParams = new object[] { mrnArray, facility };
                object response = HPFWHelper.Invoke(medicalRecordService, "getSelfPayEncountersForMrns", PrepareHPFWParams(requestParams));
                string[] encounters = (string[])response;
                if (encounters != null)
                {
                    selfPayencounters.AddRange(encounters);
                }
            }

            return selfPayencounters;
        }

        /// <summary>
        /// Converts the server request patients to client model.
        /// </summary>
        /// <param name="server"></param>
        /// <returns></returns>
        public RequestPatients MapModel(RequestPatientsList serverRequestPatients)
        {
            List<RequestPatientDetails> requestPatients = new List<RequestPatientDetails>();
            pageStatus = new Dictionary<long, bool>();
            nonHPFDocumentStatus = new Dictionary<long, bool>();
            attachmentStatus = new Dictionary<long, bool>();

            RequestPatients clientRequestPatients = new RequestPatients();
            clientRequestPatients.RequestPatientList = requestPatients;
            clientRequestPatients.PageStatus = pageStatus;
            clientRequestPatients.NonHpfDocumentStatus = nonHPFDocumentStatus;
            clientRequestPatients.AttachmentStatus = attachmentStatus;
            ArrayList selfPayEncounters = new ArrayList();
            if (serverRequestPatients.requestPatients != null)
            {
                selfPayEncounters = GetSelfPayEncounterDetails(serverRequestPatients);
            }
            if (serverRequestPatients != null)
            {
                RequestPatientDetails client;
                if (serverRequestPatients.requestPatients != null)
                {
                    foreach (RequestPatient server in serverRequestPatients.requestPatients)
                    {
                        client = new RequestPatientDetails();                        
                        client.IsHpf = server.supplementalId == 0 ? true : false;
                        client.PatientSeq = server.patientSeq;
                        if (!string.IsNullOrEmpty(server.name))
                        {
                            client.FullName = server.name.TrimEnd();
                        }
                        client.MRN = server.mrn;
                        if (!string.IsNullOrEmpty(server.lastName))
                        {
                            client.LastName = server.lastName.TrimEnd();
                        }
                        if (!string.IsNullOrEmpty(server.firstName))
                        {
                            client.FirstName = server.firstName.TrimEnd();
                        }
                        client.Address1 = server.address1;
                        client.Address2 = server.address2;
                        client.Address3 = server.address3;
                        client.City = server.city;
                        client.State = server.state;
                        client.PostalCode = server.zip;
                        client.HomePhone = server.homePhone;
                        client.WorkPhone = server.workPhone;
                        if (string.IsNullOrEmpty(server.facility))
                        {
                            client.FacilityCode = server.freeformFacility;
                            client.FacilityType = FacilityType.NonHpf;
                        }
                        else
                        {
                            client.FacilityType = FacilityType.Hpf;
                            client.FacilityCode = server.facility;
                        }
                        if (!string.IsNullOrEmpty(server.gender))
                        {
                            switch (server.gender.ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture))
                            {
                                case "M": client.Gender = EnumUtilities.GetDescription(Gender.Male); break;
                                case "F": client.Gender = EnumUtilities.GetDescription(Gender.Female); break;
                                case "U": client.Gender = EnumUtilities.GetDescription(Gender.Unknown); break;
                            }
                        }
                        client.SSN = server.ssn;
                        client.EPN = server.epn;
                        if (server.dob.HasValue)
                        {
                            //DE7989:Fix for ROI DOB issue(For a particular Date of birth i.e 03/24/1943,the date on Patient Information screen was being displayed as 03/23/1943)
                            client.DOB = Convert.ToDateTime(server.dob.Value.AddHours(1));
                        }
                        client.Id = server.supplementalId;

                        if (server.globalDocuments != null)
                        {
                            MapModel(client, server.globalDocuments);
                        }
                        if (server.roiEncounters != null)
                        {
                            MapModel(client, server.roiEncounters, selfPayEncounters);
                        }
                        if (server.nonHpfDocuments != null)
                        {
                            MapModel(client, server.nonHpfDocuments);
                        }
                        if (server.attachments != null)
                        {
                            MapModel(client, server.attachments);
                        }
                        SetReleasedStatus(client);
                        SetReleasedStatus(client.GlobalDocument);
                        SetReleasedStatus(client.NonHpfDocument);
                        SetReleasedStatus(client.Attachment);
                        SetDeletedStatus(client);
                        SetDeletedStatus(client.GlobalDocument);
                        requestPatients.Add(client);
                    }
                }
            }
            return clientRequestPatients;
        }

        private void SetReleasedStatus(BaseRequestItem item)
        {
            foreach (BaseRequestItem child in item.GetChildren)
            {
                child.IsReleased = child.IsReleased;
                SetReleasedStatus(child);
            }
        }

        private void SetDeletedStatus(BaseRequestItem item)
        {
            foreach (BaseRequestItem child in item.GetChildren)
            {
                child.IsDeleted = child.IsDeleted;
                SetDeletedStatus(child);
            }
        }

        /// <summary>
        /// Converts server to client model.
        /// </summary>
        /// <param name="serverRequestEncounters"></param>
        /// <returns></returns>
        public void MapModel(RequestPatientDetails requestPatientDetails, RequestEncounter[] serverRequestEncounters, ArrayList selfPayEncounters)
        {   
            RequestEncounterDetails client;
            foreach (RequestEncounter server in serverRequestEncounters)
            {
                client = new RequestEncounterDetails();
                client.EncounterSeq = server.encounterSeq;

                if (selfPayEncounters.Contains(server.name))
                {
                    client.IsSelfPay = true;
                }
               
                //client.IsSelfPay = server.isSelfPay;
                Assembly assembly = GetType().Assembly;
                string baseName = assembly.GetName().Name;
                ResourceManager rm = new ResourceManager(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                       "{0}{1}", baseName, ConfigurationManager.AppSettings["roi.messages.resource"]),
                                                       assembly);
                client.SelfPayEncounterID = server.name + rm.GetString("Self Pay Encounter");
                client.EncounterId = server.name;
                client.PatientSeq = server.patientSeq;
                client.Facility = server.facility;
                client.PatientType = server.patientType;
                client.IsVip = server.isVip;
                client.IsLocked = server.isLocked;
                client.PatientService = server.patientService;
                client.Mrn = server.mrn;
                if (server.admitdate != null)
                {
                    client.AdmitDate = server.admitdate;
                }
                if (server.dischargeDate != null)
                {
                    client.DischargeDate = server.dischargeDate;
                }
                requestPatientDetails.AddChild(client);
               
                if (server.roiDocuments != null)
                {
                    MapModel(client, server.roiDocuments);
                }
            }
        }

        /// <summary>
        /// Converts server into client model for the GLOBAL DOCUMENTS.
        /// </summary>
        /// <param name="requestPatientDetails"></param>
        /// <param name="serverRequestDocuments"></param>
        public void MapModel(RequestPatientDetails requestPatientDetails, RequestDocument[] serverRequestDocuments)        
        {
            RequestDocumentDetails client;
            foreach (RequestDocument server in serverRequestDocuments)
            {
                client = MapModel(server);
                requestPatientDetails.GlobalDocument.AddChild(client);
                if (server.roiVersions != null)
                {
                    MapModel(client, server.roiVersions);
                }
            }
        }

        /// <summary>
        /// Converts server to client model for the ENCOUNTER DOCUMENTS.
        /// </summary>
        /// <param name="serverRequestDocuments"></param>
        /// <returns></returns>
        public void MapModel(RequestEncounterDetails requestEncounterDetails, RequestDocument[] serverRequestDocuments)
        {
            RequestDocumentDetails client;
            foreach (RequestDocument server in serverRequestDocuments)
            {
                client = MapModel(server);
                requestEncounterDetails.AddChild(client);
                if (server.roiVersions != null)
                {
                    MapModel(client, server.roiVersions);
                }
            }            
        }

        /// <summary>
        /// Converts server to client model
        /// </summary>
        /// <param name="server"></param>
        /// <returns></returns>
        public RequestDocumentDetails MapModel(RequestDocument server)
        {
            RequestDocumentDetails client = new RequestDocumentDetails();
            if (!string.IsNullOrEmpty(server.name))
            {
                client.DocType = server.name.Trim();
            }
            if (!string.IsNullOrEmpty(server.subtitle))
            {
                client.Subtitle = server.subtitle.Trim();
            }
            client.EncounterSeq = server.encounterSeq;
            client.DocumentSeq = server.documentSeq;
            client.DocTypeId = server.docTypeId;
            client.DocumentId = server.docId;
            client.Encounter = server.encounter;
            client.Facility = server.facility;
            client.Mrn = server.mrn;
            client.PatientSeq = server.patientSeq;
            if (!string.IsNullOrEmpty(server.chartOrder))
            {
                client.ChartOrder = server.chartOrder.Trim();
            }
            client.DocumentDateTime = server.dateTime;
            client.DateOfService = server.dateTime; //Need to clarify
            return client;
        }

        /// <summary>
        /// Converts server to client model.
        /// </summary>
        /// <param name="server"></param>
        /// <returns></returns>
        public void MapModel(RequestDocumentDetails requestDocumentDetails, RequestVersion[] serverRequestVersions)
        {  
            RequestVersionDetails client;
            foreach (RequestVersion server in serverRequestVersions)
            {
                client = new RequestVersionDetails();
                client.DocumentId = server.docId;
                client.DocumentSeq = server.documentSeq;
                client.VersionSeq = server.versionSeq;
                client.VersionNumber = server.versionNumber;
                requestDocumentDetails.AddChild(client);
                if (server.roiPages != null)
                {
                    MapModel(client, server.roiPages);
                }                
            }            
        }

        /// <summary>
        /// Converts server to client model
        /// </summary>
        /// <param name="server"></param>
        /// <returns></returns>
        public void MapModel(RequestVersionDetails requestVersionDetails, RequestPage[] serverRequestPages)
        {  
            RequestPageDetails client;
            foreach (RequestPage server in serverRequestPages)
            {
                client = new RequestPageDetails();
                client.IMNetId = server.imnetId;
                client.VersionSeq = server.versionSeq;
                client.PageSeq = server.pageSeq;
                client.PageNumber = server.pageNumber;
                client.PageNumberRequested = server.pageNumberRequested;
                client.SelectedForRelease = server.isSelectedForRelease;
                client.IsReleased = server.isReleased;
                client.IsDeleted = server.deleted;
                client.ContentCount = server.contentCount;
                requestVersionDetails.AddChild(client);
                if (!pageStatus.ContainsKey(server.pageSeq))
                {
                    pageStatus.Add(server.pageSeq, server.isSelectedForRelease);
                }
            }           
        }

        /// <summary>
        /// Converts server request Non-HPF documents to client request Non-HPF documents
        /// </summary>
        /// <param name="requestPatientDetails"></param>
        /// <param name="serverRequestSupplementalDocument"></param>
        public void MapModel(RequestPatientDetails requestPatientDetails, RequestSupplementalDocument[] serverRequestSupplementalDocument)
        {
            RequestNonHpfDocumentDetails client;
            foreach (RequestSupplementalDocument server in serverRequestSupplementalDocument)
            {
                client = new RequestNonHpfDocumentDetails();
                client.DocumentSeq = server.documentCoreSeq;
                client.PatientSeq = server.patientSeq;
                client.PatientID = server.supplementalId;
                client.SupplementalID = server.supplementalId;
                client.Id = server.documentSeq;
                client.IsHPF = requestPatientDetails.IsHpf;                
                client.Encounter = server.encounter;
                client.DocType = server.docName;
                client.PatientFacility = server.facility;
                client.Facility = server.docFacility;
                client.Department = server.department;
                if (server.dateOfService != null)
                {
                    client.DateOfService = server.dateOfService;
                }
                client.Comment = server.comment;
                client.SelectedForRelease = server.isSelectedForRelease;
                client.IsReleased = server.isReleased;               
                client.Subtitle = server.subtitle;
                client.BillingTier = server.billingTierId;
                client.BillingTierStatus = server.billingTierId;
                client.PageCount = Convert.ToInt32(server.pageCount);
                requestPatientDetails.NonHpfDocument.AddDocument(client);
                if (!nonHPFDocumentStatus.ContainsKey(server.documentCoreSeq))
                {
                    nonHPFDocumentStatus.Add(server.documentCoreSeq, server.isSelectedForRelease);
                }
            }
        }

        /// <summary>
        /// Converts server request Attachments to client request Attachments
        /// </summary>
        /// <param name="requestPatientDetails"></param>
        /// <param name="serverRequestAttachment"></param>
        public void MapModel(RequestPatientDetails requestPatientDetails, RequestSupplementalAttachment[] serverRequestAttachment)
        {
            RequestAttachmentDetails client;
            foreach (RequestSupplementalAttachment server in serverRequestAttachment)
            {
                client = new RequestAttachmentDetails();
                
                client.AttachmentType = server.type;                
                client.Encounter = server.encounter;
                client.AttachmentSeq = server.attachmentCoreSeq;
                client.Id = server.attachmentSeq;
                client.PatientSeq = server.patientSeq;
                client.IsHPF = requestPatientDetails.IsHpf;
				//CR 374865
                client.PatientID = server.supplementalId;
                //client.PatientID = requestPatientDetails.SupplementalId;
                client.SupplementalID = server.supplementalId;
                client.FileId = server.uuid;                
                client.DateReceived = server.attachmentDate; //TODO
                client.FileName = server.filename;
                client.FileType = server.filetype;
                client.FileExt = server.fileext;
                /*
                 * commented to prevent attachments from outputting to printer or fax
                 * currently all attachments are identified as being printable
                 * TODO:  have to cleanup existing entries and truly mark what is printable in the future

                    client.IsPrintable = server.printable == "0" ? false : true;
                 */
                client.IsPrintable = false;

                client.Facility = server.facility;
                if (server.dateOfService != null)
                {
                    client.DateOfService = server.dateOfService;
                }
                if (server.attachmentDate != null)
                {
                    client.FileAttachDate = server.attachmentDate;
                }
                client.Comment = server.comment;
                client.Subtitle = server.subtitle;
                client.SelectedForRelease = server.isSelectedForRelease;
                client.IsReleased = server.isReleased;
                client.BillingTier = server.billingTierId;
                client.BillingTierStatus = server.billingTierId;
                client.PageCount = Convert.ToInt32(server.pageCount);
                client.Uuid = server.uuid;
                client.Path = server.path;
                client.Volume = server.volume;
                requestPatientDetails.Attachment.AddDocument(client);
                if (!attachmentStatus.ContainsKey(server.attachmentCoreSeq))
                {
                    attachmentStatus.Add(server.attachmentCoreSeq, server.isSelectedForRelease);
                }
            }
        }

        /// <summary>
        /// Converts the model from client to server
        /// </summary>
        /// <param name="client"></param>
        /// <returns></returns>
        private static RequestCore.Request MapModel(RequestDetails client)
        {
            RequestCore.Request server = new RequestCore.Request();
            server.id = client.Id;            
            //TODO server.balanceDue = Convert.ToInt64(client.BalanceDue);
            server.receiptDate = client.ReceiptDate;
            if (client.CompletedDate.HasValue)
            {
                server.completedDate = client.CompletedDate;
            }
            server.requestReasonAttribute = client.RequestReasonAttribute;            
            server.requestReason = client.RequestReason;
            //server.requestType = client.RequestReasonAttribute;
            server.statusChangedDt = client.StatusChanged;
            server.requestPassword = client.RequestSecretWord;
            server.requestorDetail = PrepareRequestor(client);
            server.requestorDetail.requestorId = client.Requestor.Id;
            server.status = Convert.ToString(client.Status, System.Threading.Thread.CurrentThread.CurrentUICulture);
            //server.lov = PrepareRequestLovs(client);
            server.statusReason = client.StatusReason;
            return server;
        }

        /// <summary>
        /// Model to prepare the Requestor for the Request
        /// </summary>
        /// <param name="RequestDetails"></param>
        /// <returns></returns>
        private static Requestor PrepareRequestor(RequestDetails request)
        {
            RequestorDetails requestor = request.Requestor;
            Requestor serverRequestor = new Requestor();
            if (requestor.TypeName.Equals("Patient"))
            {
                serverRequestor.firstName = requestor.FirstName;
                serverRequestor.lastName = requestor.LastName;
            }
            else
            {
                serverRequestor.lastName = requestor.Name;
            }
            serverRequestor.requestorType = Convert.ToInt32(requestor.Type);
            serverRequestor.requestorTypeName = requestor.TypeName;
            serverRequestor.cellPhone = request.RequestorCellPhone;
            serverRequestor.workPhone = request.RequestorWorkPhone;
            serverRequestor.homePhone = request.RequestorHomePhone;
            serverRequestor.fax = request.RequestorFax;
            serverRequestor.contactName = request.RequestorContactName;
            serverRequestor.contactPhone = request.RequestorContactPhone;
            return serverRequestor;
        }

        /// <summary>
        /// Converts the model from server to client
        /// </summary>
        /// <param name="server"></param>
        /// <param name="fetchChildren"></param>
        /// <returns></returns>
        private static RequestDetails MapModel(RequestCore.Request server, bool fetchChildren, Collection<TaxPerFacilityDetails> taxPerFacilityDetails)
        {           
            RequestDetails client = new RequestDetails();
            client.DefaultFacility = new TaxPerFacilityDetails();
            client.DefaultFacility.FacilityCode = server.defaultFacilityCode;
            client.DefaultFacility.FacilityName = server.defaultFacility;
            client.Id = server.id;
            if (server.status != null)
            {
                if (server.status.Equals(EnumUtilities.GetDescription(RequestStatus.AuthReceived)))
                {
                    client.Status = RequestStatus.AuthReceived;
                }
                else
                {
                    client.Status = Enum.IsDefined(typeof(RequestStatus), server.status) ? (RequestStatus)Enum.Parse(typeof(RequestStatus), server.status)
                                                                                             : RequestStatus.Unknown;
                }
            }
            else
            {
                client.Status = RequestStatus.Unknown;
            }
            client.StatusReason = server.statusReason;            
            client.DateCreated = server.createdDate;
            client.UpdatedBy = server.modifiedByUser;
            client.BalanceDue = server.balanceDue;            
            client.ReceiptDate = server.receiptDate;
            if (server.completedDate.HasValue)
            {
                client.CompletedDate = server.completedDate;
            }

            for (int x = 0; x < taxPerFacilityDetails.Count; x++)
            {
                if (server.defaultFacilityCode == taxPerFacilityDetails[x].FacilityCode)
                    client.DefaultFacility.TaxPercentage = taxPerFacilityDetails[x].TaxPercentage;
            }
            client.RequestSecretWord = server.requestPassword;            
            client.RequestReason = server.requestReason;
            client.RequestReasonAttribute = server.requestReasonAttribute;
            client.StatusChanged = server.statusChangedDt;
            if (server.requestorDetail != null)
            {
                client.Requestor = RetrieveRequestor(server.requestorDetail);
                client.RequestorId = server.requestorDetail.requestorId;
                client.RequestorCellPhone = server.requestorDetail.cellPhone;
                client.RequestorContactName = server.requestorDetail.contactName;
                client.RequestorContactPhone = server.requestorDetail.contactPhone;
                client.RequestorFax = server.requestorDetail.fax;
                client.RequestorHomePhone = server.requestorDetail.homePhone;
                client.RequestorWorkPhone = server.requestorDetail.workPhone;
                client.RequestorType = server.requestorDetail.requestorType;
            }
            if (server.modifiedDate != null)
                client.LastUpdated = server.modifiedDate;

            client.ReleaseCount = server.releaseCount;
            client.HasDraftRelease = server.hasDraftRelease;

            client.IsReleased = (server.releaseCount > 0);

            client.AuthRequest = server.authDoc;
            client.AuthRequestDocumentName = server.authDocName;
            client.AuthRequestSubtitle = server.authDocSubtitle;
            client.AuthRequestDocumentDateTime = Convert.ToString(server.authDocDateTime, System.Threading.Thread.CurrentThread.CurrentUICulture);
            client.AuthRequestConversionSource = server.conversionSource;

            return client;
        }

        /// <summary>
        /// Model to Retrieve the Requestor for the Request
        /// </summary>
        /// <param name="Requestor"></param>
        /// <returns></returns>
        private static RequestorDetails RetrieveRequestor(Requestor retrieveRequestor)
        {
            RequestorDetails requestor = new RequestorDetails();
            requestor.Id = retrieveRequestor.requestorId;
            requestor.Name = retrieveRequestor.firstName;
            requestor.LastName = retrieveRequestor.lastName;
            requestor.Type = Convert.ToInt32(retrieveRequestor.requestorType);
            requestor.WorkPhone = retrieveRequestor.workPhone;
            requestor.CellPhone = retrieveRequestor.cellPhone;
            requestor.HomePhone = retrieveRequestor.homePhone;
            requestor.ContactName = retrieveRequestor.contactName;
            requestor.ContactPhone = retrieveRequestor.contactPhone;
            requestor.Fax = retrieveRequestor.fax;
            return requestor;
        }

        /// <summary>
        /// Convert client to server pagination data.
        /// </summary>
        /// <param name="clientSearchCriteria"></param>
        /// <returns></returns>
        private static RequestCore.PaginationData MapModel(PaginationDetails paginationInfo)
        {
            RequestCore.PaginationData server = new RequestCore.PaginationData();

            server.count = paginationInfo.Size;
            server.isDesc = paginationInfo.IsDescending;
            server.sortBy = paginationInfo.SortColumn;
            server.startIndex = paginationInfo.StartFrom;
            
            return server;
        }

        /// <summary>
        /// Convert the Server CommentCollection to Client Comment details Collection.
        /// </summary>
        /// <param name="comments"></param>
        /// <returns></returns>
        private static Collection<CommentDetails> MapModel(RequestCore.Comments comments)
        {
            Collection<CommentDetails> commentList = new Collection<CommentDetails>();
            if (comments.comment !=  null)
            {
                foreach (Comment serverdetails in comments.comment)
                {
                    commentList.Add(MapModel(serverdetails));
                }
            }
            return commentList;
        }

        /// <summary>
        /// Convert Client comment details to server comment
        /// </summary>
        /// <param name="client"></param>
        /// <returns></returns>
        private static RequestCore.RequestEvent MapModel(CommentDetails client)
        {
            RequestEvent requestEvent       = new RequestEvent();
            requestEvent.requestId          =  client.RequestId;
            requestEvent.requestIdSpecified = true;
            requestEvent.description        = client.EventRemarks;
            requestEvent.type               = EnumUtilities.GetDescription(client.EventType);
            return requestEvent;
        }

        /// <summary>
        /// Convert server comment to Client comment details.
        /// </summary>
        /// <param name="client"></param>
        /// <returns></returns>
        private static CommentDetails MapModel(RequestCore.Comment serverdetails)
        {
            CommentDetails clientDetails = new CommentDetails();

            clientDetails.EventRemarks = serverdetails.description;
            clientDetails.EventDate    = serverdetails.createdDate;
            clientDetails.Originator   = serverdetails.originator;
            clientDetails.RequestId    = serverdetails.requestId;
            return clientDetails;
        }

        /// <summary>
        /// Convert server request event to Client comment details.
        /// </summary>
        /// <param name="client"></param>
        /// <returns></returns>
        private static CommentDetails MapModelForComment(RequestCore.RequestEvent serverdetails)
        {
            CommentDetails clientDetails = new CommentDetails();
            clientDetails.EventRemarks   = serverdetails.description;
            clientDetails.EventDate      = serverdetails.modifiedDate;
            clientDetails.Originator     = serverdetails.originator;
            clientDetails.RequestId      = serverdetails.requestId;
            return clientDetails;
        }

        /// <summary>
        /// Converts the Server event history list into client event history list
        /// </summary>
        /// <param name="comments"></param>
        /// <returns></returns>
        private static Collection<RequestEventHistoryDetails> MapModel(RequestCore.RequestEvents serverEventHistoryList)
        {
            Collection<RequestEventHistoryDetails> eventHistoryList = new Collection<RequestEventHistoryDetails>();
            if (serverEventHistoryList.events != null)
            {
                foreach (RequestEvent serverEventHistory in serverEventHistoryList.events)
                {
                    eventHistoryList.Add(MapModel(serverEventHistory));
                }
            }
            return eventHistoryList;
        }

        /// <summary>
        /// Convert server event history to Client event history
        /// <param name="client"></param>
        /// <returns></returns>
        private static RequestEventHistoryDetails MapModel(RequestCore.RequestEvent serverEventHistory)
        {
            RequestEventHistoryDetails clientEventHistory = new RequestEventHistoryDetails();

            clientEventHistory.EventDate = serverEventHistory.modifiedDate;
            clientEventHistory.RequestEvent = serverEventHistory.name;
            clientEventHistory.Originator = serverEventHistory.originator;
            clientEventHistory.EventRemarks = serverEventHistory.description;
            clientEventHistory.RequestId = serverEventHistory.requestId;

            return clientEventHistory;
        }      

        /// <summary>
        /// Converts the client to server Request Search Criteria.
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        public static RequestCoreSearchCriteria MapModel(FindRequestCriteria searchCriteria)
        {
            RequestCoreSearchCriteria requestCoreSearchCriteria = new RequestCoreSearchCriteria();

            if (requestCoreSearchCriteria != null)
            {
                if (!string.IsNullOrEmpty(searchCriteria.RequestId))
                {
                    //CR# 378891
                    requestCoreSearchCriteria.requestId = Convert.ToInt64(searchCriteria.RequestId, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }

                requestCoreSearchCriteria.patientLastName = searchCriteria.PatientLastName;
                requestCoreSearchCriteria.patientFirstName = searchCriteria.PatientFirstName;
                requestCoreSearchCriteria.mrn = searchCriteria.MRN;
                requestCoreSearchCriteria.patientSsn = searchCriteria.PatientSsn;
                requestCoreSearchCriteria.patientEpn = searchCriteria.EPN;
                requestCoreSearchCriteria.patientDob = searchCriteria.Dob;
                requestCoreSearchCriteria.facility = searchCriteria.Facility;
                requestCoreSearchCriteria.patientId = searchCriteria.NonHpfPatientId;

                if ((searchCriteria.RequestStatus !=null) && (!searchCriteria.RequestStatus.Equals("None")))
                {
                    requestCoreSearchCriteria.requestStatus = searchCriteria.RequestStatus;
                }
                requestCoreSearchCriteria.requestReason = searchCriteria.RequestReason;
                if (!string.IsNullOrEmpty(searchCriteria.InvoiceNumber))
                {
                    requestCoreSearchCriteria.invoiceNumber = Convert.ToInt64(searchCriteria.InvoiceNumber);
                }
                requestCoreSearchCriteria.balanceDue = Convert.ToDouble(searchCriteria.BalanceDue);
                if ((searchCriteria.BalanceDueOperator != null) && (searchCriteria.BalanceDueOperator.Equals("AtLeast")))
                {
                    searchCriteria.BalanceDueOperator = ">";
                }
                else if ((searchCriteria.BalanceDueOperator != null) && (searchCriteria.BalanceDueOperator.Equals("AtMost")))
                {
                    searchCriteria.BalanceDueOperator = "<";
                }
                else if ((searchCriteria.BalanceDueOperator != null) && (searchCriteria.BalanceDueOperator.Equals("Equal")))
                {
                    searchCriteria.BalanceDueOperator = "=";
                }

                if (searchCriteria.BalanceDueOperator != null)
                {
                    requestCoreSearchCriteria.balanceDueOperator = searchCriteria.BalanceDueOperator;
                }
                else
                {
                    requestCoreSearchCriteria.balanceDueOperator = null;
                }

                requestCoreSearchCriteria.requestorName = searchCriteria.RequestorName;
                requestCoreSearchCriteria.requestorType = searchCriteria.RequestorType;
                requestCoreSearchCriteria.requestorId = searchCriteria.RequestorId;
                requestCoreSearchCriteria.requestorTypeName = searchCriteria.RequestorTypeName;
                if (!string.IsNullOrEmpty(searchCriteria.CompletedFromDate))
                {
                    requestCoreSearchCriteria.completedDateFrom = Convert.ToDateTime(searchCriteria.CompletedFromDate);
                }
                if (!string.IsNullOrEmpty(searchCriteria.CompletedToDate))
                {
                    requestCoreSearchCriteria.completedDateTo = Convert.ToDateTime(searchCriteria.CompletedToDate);
                }
                if (!string.IsNullOrEmpty(searchCriteria.ReceiptFromDate))
                {
                    requestCoreSearchCriteria.receiptDateFrom = Convert.ToDateTime(searchCriteria.ReceiptFromDate);
                }
                if (!string.IsNullOrEmpty(searchCriteria.ReceiptToDate))
                {
                    requestCoreSearchCriteria.receiptDateTo = Convert.ToDateTime(searchCriteria.ReceiptToDate);
                }
                requestCoreSearchCriteria.maxCount = searchCriteria.MaxRecord;
                requestCoreSearchCriteria.encounter = searchCriteria.Encounter;
            }
            return requestCoreSearchCriteria;
        }

        /// <summary>
        /// Converts the server request result to client.
        /// </summary>
        /// <param name="serverSearchResult"></param>
        /// <param name="objectIds"></param>
        /// <param name="taxPerFacilityDetails"></param>
        /// <returns></returns>
        private static FindRequestResult MapModel(RequestCoreSearchResultList serverSearchResult, Collection<string> objectIds)
        {
            FindRequestResult clientSearchResult = new FindRequestResult();
            RequestDetails requestDetails;
            string encounterID;
            if (serverSearchResult.requestCoreSearchResult != null)
            {
                foreach (RequestCoreSearchResult server in serverSearchResult.requestCoreSearchResult)
                {
                    requestDetails = new RequestDetails();
                    int unAuthorizedRequestCount = 0;
                    if (server.facility != null)
                    {
                        string[] facilityArray = server.facility.Split(new char[] { ',' });
                        foreach (string facility in facilityArray)
                        {
                            if (!UserData.Instance.Facilities.Contains(FacilityDetails.GetFacility(ROIViewUtility.OriginalAmpersand(facility), FacilityType.Hpf)))
                            {
                                unAuthorizedRequestCount++;
                            }
                        }
                    }
                    requestDetails.StatusReason = server.requestStatus;
                    requestDetails.ReceiptDate = server.receiptDate;
                    requestDetails.RequestorName = server.requestorName;
                    requestDetails.RequestorType = server.requestorType;
                    requestDetails.RequestorTypeName = server.requestorTypeName;
                    requestDetails.Id = server.requestId;
                    requestDetails.VipImage = server.vip ? ROIImages.VipIcon : null;
                    requestDetails.LockedImage = server.patientLocked ? ROIImages.LockedIcon : null;
                    requestDetails.ReceiptDate = server.receiptDate;
                    if (server.patients != null)
                    {
                        requestDetails.PatientNames = RetrievePatients(server.patients);
                        requestDetails.CompletePatientNames = requestDetails.PatientNames;
                        if (server.patients.Length > 20)
                        {
                            string[] patientsArray = requestDetails.CompletePatientNames.Split(new char[] { ':' });
                            requestDetails.FirstTwentyPatientNames = string.Join(":", patientsArray, 0, 20) + "...";
                        }
                        else
                        {
                            requestDetails.FirstTwentyPatientNames = requestDetails.CompletePatientNames;
                        }
                    }
                    requestDetails.LastUpdated = server.lastUpdated;
                    requestDetails.AuthRequestSubtitle = server.subtitle;
                    requestDetails.UpdatedBy = server.updatedBy;
                    requestDetails.Facility = server.facility;
                    if (server.requestStatus != null)
                    {
                        if (server.requestStatus.Equals(EnumUtilities.GetDescription(RequestStatus.AuthReceived)))
                        {
                            requestDetails.Status = RequestStatus.AuthReceived;
                        }
                        else
                        {
                            requestDetails.Status = Enum.IsDefined(typeof(RequestStatus), server.requestStatus) ? (RequestStatus)Enum.Parse(typeof(RequestStatus), server.requestStatus)
                                                                                             : RequestStatus.Unknown;
                        }
                    }
                    else
                    {
                        requestDetails.Status = RequestStatus.Unknown;
                    }
                    requestDetails.BalanceDue = server.balance;
                    if (server.encounters != null)
                    {
                        foreach (string encounter in server.encounters)
                        {
                            encounterID = encounter + ROIConstants.Delimiter + server.facility;
                            if (!requestDetails.Encounters.Contains(encounterID))
                            {
                            requestDetails.Encounters.Add(encounterID);
                            }
                        }
                    }
                    
                    if (server.patients != null)
                    {
                        if (unAuthorizedRequestCount == server.patients.Length)
                        {
                            requestDetails.HasBlockedRequestFacility = true;
                        }
                        else if (unAuthorizedRequestCount > 0)
                        {
                            requestDetails.HasMaskedRequestFacility = true;
                        }
                    }
                    if (!requestDetails.HasBlockedRequestFacility)
                    {
                        objectIds.Add(Convert.ToString(requestDetails.Id, System.Threading.Thread.CurrentThread.CurrentUICulture));
                        clientSearchResult.RequestSearchResult.Add(requestDetails);
                    }
                }
            }
            return clientSearchResult;
        }


        private static string RetrievePatients(string[] patients)
        {
            string patientName = null;
            if (patients != null)
            {
                bool isFirstPatient = true;
                foreach (string patient in patients)
                {
                    if (isFirstPatient)
                    {
                        patientName = patient;
                        isFirstPatient = false;
                    }
                    else
                    {
                        patientName += ":" + patient;
                    }
                }
            }
            return patientName;
        }

        /// <summary>
        /// converts the server to client
        /// </summary>
        /// <param name="requestCoreChargesInvoicesList"></param>
        /// <returns></returns>
        private InvoiceChargeDetailsList MapModel(RequestCoreChargesInvoicesList requestCoreChargesInvoicesList)
        {
            bool isInvoice = requestCoreChargesInvoicesList.isInvoiced;
            InvoiceChargeDetailsList reqTranslist = MapModel(requestCoreChargesInvoicesList.requestCoreChargesInvoice, isInvoice);
            return reqTranslist;
        }

        private static InvoiceChargeDetailsList MapModel(RequestCoreChargesInvoice[] server, bool isInvoice)
        {
            InvoiceChargeDetailsList list = new InvoiceChargeDetailsList();
            List<RequestCoreChargesInvoiceDetail> reqTranslist = new List<RequestCoreChargesInvoiceDetail>();
            if(server!=null)
            reqTranslist = new List<RequestCoreChargesInvoiceDetail>(server.Length);

            if (server != null)
            {
                RequestCoreChargesInvoiceDetail reqInvoiceDetail = new RequestCoreChargesInvoiceDetail();
                RequestTransaction reqTrans = new RequestTransaction();
                foreach (RequestCoreChargesInvoice reqInvoiceCharge in server)
                {
                    reqInvoiceDetail = new RequestCoreChargesInvoiceDetail();
                    reqInvoiceDetail.invoiceCreatedDt = reqInvoiceCharge.invoiceCreatedDt;
                    reqInvoiceDetail.paymentAmount = reqInvoiceCharge.paymentAmount;
                    reqInvoiceDetail.releaseCost = reqInvoiceCharge.invoicedAmount;
                    reqInvoiceDetail.requestCoreDeliveryChargesId = reqInvoiceCharge.requestCoreDeliveryChargesId;
                    reqInvoiceDetail.IsInvoiced = reqInvoiceCharge.isInvoice;

                    reqInvoiceDetail.test = reqInvoiceCharge.test;

                    if (reqInvoiceCharge.requestCoreDeliveryChargesAdjustmentPayment != null)
                    {
                        foreach (RequestCore.RequestCoreDeliveryChargesAdjustmentPayment reqAjPay in reqInvoiceCharge.requestCoreDeliveryChargesAdjustmentPayment)
                        {
                            RequestTransaction requestTrans = new RequestTransaction();
                            requestTrans.Amount = reqAjPay.invoiceAppliedAmount;
                            requestTrans.Description = reqAjPay.description;
                            requestTrans.CreatedDate = reqAjPay.paymentDate;
                            if (reqAjPay.transactionType.Equals(EnumUtilities.GetDescription(TransactionType.AutoAdjustment)))
                            {
                                requestTrans.TransactionType = TransactionType.AutoAdjustment;
                            }
                            else
                            {
                                requestTrans.TransactionType = (TransactionType)Enum.Parse(typeof(TransactionType), reqAjPay.transactionType);
                            }
                            requestTrans.Id = reqInvoiceCharge.requestCoreDeliveryChargesId;
                            requestTrans.IsDebit = reqAjPay.isDebit;
                            requestTrans.PaymentMode = reqAjPay.paymentMode;
                            //requestTrans.IsNewlyAdded = reqAjPay.
                            reqInvoiceDetail.ReqTransaction.Add(requestTrans);
                        }
                    }
                    reqTranslist.Add(reqInvoiceDetail);
                }
            }

            List<InvoiceChargeDetails> invoiceChargesList = new List<InvoiceChargeDetails>();
            InvoiceChargeDetails invoiceCharges = new InvoiceChargeDetails();
            List<RequestTransaction> reqTransList = new List<RequestTransaction>();

            foreach (RequestCoreChargesInvoiceDetail reqCoreDetail in reqTranslist)
            {

                invoiceCharges = new InvoiceChargeDetails();
                invoiceCharges.BalanceDue = reqCoreDetail.releaseCost;

                invoiceCharges.CreatedDate = ((DateTime)reqCoreDetail.invoiceCreatedDt).Date;
                invoiceCharges.IsInvoiced = reqCoreDetail.IsInvoiced;
                invoiceCharges.Id = reqCoreDetail.requestCoreDeliveryChargesId;

                foreach (RequestTransaction reqTransaction in reqCoreDetail.ReqTransaction)
                {
                    RequestTransaction reqTrans = new RequestTransaction();

                    reqTrans.IsNewlyAdded = reqTransaction.IsNewlyAdded;
                    reqTrans.ReasonName = reqTransaction.ReasonName;
                    reqTrans.AdjustmentPaymentType = reqTransaction.AdjustmentPaymentType;
                    reqTrans.Id = reqTransaction.Id;
                    reqTrans.Amount = reqTransaction.Amount;
                    reqTrans.CreatedDate = reqTransaction.CreatedDate;
                    reqTrans.Description = reqTransaction.Description;
                    reqTrans.IsDebit = reqTransaction.IsDebit;
                    reqTrans.TransactionType = reqTransaction.TransactionType;
                    reqTrans.PaymentMode = reqTransaction.PaymentMode;
                    invoiceCharges.RequestTransactions.Add(reqTrans);
                }
                invoiceChargesList.Add(invoiceCharges);
            }
            list.InvoiceCharges = invoiceChargesList;
            return list;
        }

        private static RequestBillingInfo MapModel(RequestCoreCharges server)
        {
            RequestBillingInfo client = new RequestBillingInfo();
            client.IsReleased = server.released;
            client.ApplySalesTax = server.applySalesTax;
            client.BalanceDue = server.balanceDue;
            client.BillingLocCode = server.billingLocCode;
            client.BillingLocName = server.billingLocName;
            client.SalestaxPercentageForBillingLoc = server.salesTaxPercentage;
            client.BillingType = server.billingType;
            client.CreditAdjustmentAmount = Math.Abs(server.creditAdjustmentAmount);
            client.DebitAdjustmentAmount = server.debitAdjustmentAmount;
            client.OriginalBalance = server.originalBalance;
            client.PaymentAmount = server.paymentAmount;
            client.PreviouslyReleasedCost = server.previouslyReleasedCost;
            client.ReleaseDate = server.releaseDate;
            client.ReleasedCost = server.releaseCost;
            client.RequestId = server.requestCoreSeq;
            client.SalestaxTotalAmount = server.salesTaxAmount;
            client.TotalPages = server.totalPages;
            client.TotalPagesReleased = server.totalPagesReleased;
            client.TotalRequestCost = server.totalRequestCost;
            client.IsInvoiced = server.hasInvoices;
            client.InvoicesBalanceDue = server.invoicesBalance;
            client.InvoicesSalesTaxAmount = server.invoicesSalesTaxAmount;
            client.IsUnbillable = server.unbillable;
            //client.IsUnbillable = UserData.Instance.IsChecked;
            client.UnAppliedAmount = server.totalUnappliedAmount;

            if (server.requestCoreChargesBilling.requestCoreChargesDocument != null)
            {
                List<DocumentChargeDetails> docChargeList = new List<DocumentChargeDetails>(server.requestCoreChargesBilling.requestCoreChargesDocument.Length);
                DocumentChargeDetails docCharges;
                long billingTierID = -2;
                foreach (RequestCoreChargesDocument reqDocCharge in server.requestCoreChargesBilling.requestCoreChargesDocument)
                {
                    docCharges = new DocumentChargeDetails();
                    docCharges.Amount = reqDocCharge.amount;
                    docCharges.BillingTier = reqDocCharge.billingTierName;
                    bool b = long.TryParse(reqDocCharge.billingtierId, out billingTierID);
                    docCharges.BillingTierId = billingTierID;
                    docCharges.Copies = reqDocCharge.copies;
                    docCharges.HasSalesTax = reqDocCharge.hasSalesTax;
                    docCharges.IsElectronic = reqDocCharge.isElectronic;
                    docCharges.Pages = reqDocCharge.pages;
                    docCharges.ReleaseCount = reqDocCharge.releaseCount;
                    docCharges.RemoveBaseCharge = reqDocCharge.removeBaseCharge;
                    docCharges.TaxAmount = reqDocCharge.salesTaxAmount;
                    docCharges.TotalPages = reqDocCharge.totalPages;
                    docCharges.HasSalesTax = reqDocCharge.hasSalesTax;
                    docChargeList.Add(docCharges);
                }

                client.BillingCharge.DocumentCharge = docChargeList;
            }
            if (server.requestCoreChargesBilling.requestCoreChargesFee != null)
            {
                List<FeeChargeDetails> feeChargeList = new List<FeeChargeDetails>(server.requestCoreChargesBilling.requestCoreChargesFee.Length);
                foreach (RequestCoreChargesFee reqFeeCharge in server.requestCoreChargesBilling.requestCoreChargesFee)
                {
                    FeeChargeDetails feeCharges = new FeeChargeDetails();
                    feeCharges.Amount = reqFeeCharge.amount;
                    feeCharges.FeeType = reqFeeCharge.feeType;
                    feeCharges.HasSalesTax = reqFeeCharge.hasSalesTax;
                    feeCharges.IsCustomFee = reqFeeCharge.isCustomFee;
                    feeCharges.TaxAmount = reqFeeCharge.salesTaxAmount;
                    feeCharges.HasSalesTax = reqFeeCharge.hasSalesTax;
                    feeChargeList.Add(feeCharges);
                }

                client.BillingCharge.FeeCharge = feeChargeList;
            }

            if (server.salesTaxSummary.salesTaxReason != null)
            {
                List<SalesTaxReasons> salesTaxReasonList = new List<SalesTaxReasons>(server.salesTaxSummary.salesTaxReason.Length);

                foreach (SalesTaxReason salesTaxReason in server.salesTaxSummary.salesTaxReason)
                {
                    SalesTaxReasons taxReason = new SalesTaxReasons();
                    taxReason.Id = salesTaxReason.id;
                    taxReason.Reason = salesTaxReason.reason;
                    taxReason.CreatedDate = DateTime.Parse(salesTaxReason.reasonDate.ToString());
                    salesTaxReasonList.Add(taxReason);

                }
                client.SalesTaxReasonsList = salesTaxReasonList;
            }
            client.ShippingInfo.ShippingAddress = new AddressDetails();
            if (server.requestCoreChargesShipping != null)
            {
                if (server.requestCoreChargesShipping.addressType != null)
                {
                    client.ShippingInfo.AddressType = (RequestorAddressType)Enum.Parse(typeof(RequestorAddressType), server.requestCoreChargesShipping.addressType);
                    client.ShippingInfo.ShippingAddress.PostalCode = server.requestCoreChargesShipping.postalCode;
                    client.ShippingInfo.ShippingAddress.Address1 = server.requestCoreChargesShipping.address1;
                    client.ShippingInfo.ShippingAddress.Address2 = server.requestCoreChargesShipping.address2;
                    client.ShippingInfo.ShippingAddress.Address3 = server.requestCoreChargesShipping.address3;
                    client.ShippingInfo.ShippingAddress.State = server.requestCoreChargesShipping.state;
                    client.ShippingInfo.ShippingAddress.City = server.requestCoreChargesShipping.city;
                    client.ShippingInfo.ShippingAddress.CountryCode = server.requestCoreChargesShipping.countryCode;
                    client.ShippingInfo.ShippingAddress.CountryName = server.requestCoreChargesShipping.countryName;
                    client.ShippingInfo.ShippingAddress.NewCountry = server.requestCoreChargesShipping.newCountry;
                }

                client.ShippingInfo.ShippingCharge = server.requestCoreChargesShipping.shippingCharge;

                if (server.requestCoreChargesShipping.shippingUrl != null)
                    client.ShippingInfo.ShippingWebAddress = server.requestCoreChargesShipping.shippingUrl;

                client.ShippingInfo.ShippingWeight = Decimal.Parse(server.requestCoreChargesShipping.shippingWeight.ToString());
                if (server.requestCoreChargesShipping.trackingNumber != null)
                    client.ShippingInfo.TrackingNumber = server.requestCoreChargesShipping.trackingNumber;

                client.ShippingInfo.WillReleaseShipped = server.requestCoreChargesShipping.willReleaseShipped;
                client.ShippingInfo.ShippingMethod = server.requestCoreChargesShipping.shippingMethod;
                client.ShippingInfo.OutputMethod = (OutputMethod)Enum.Parse(typeof(OutputMethod), server.requestCoreChargesShipping.outputMethod);
                client.ShippingInfo.NonPrintableAttachmentQueue = server.requestCoreChargesShipping.nonPrintableAttachmentQueue;

                client.ShippingInfo.ShippingMethodId = server.requestCoreChargesShipping.shippingMethodId;
            }
            client.InvoiceAutoAdjustment = server.invoiceAutoAdjustment;
            client.InvoiceBaseCharge = server.invoiceBaseCharge;

            return client;
        }
        private static RequestCoreCharges MapModel(RequestCoreChargeDetails client)
        {
            RequestCoreCharges server = new RequestCoreCharges();
            server.requestCoreSeq = Int32.Parse(client.RequestId.ToString());
            //server.billingLoc = client.BillingLoc;
            server.previouslyReleasedCost = client.PreviouslyReleasedCost;
            if (client.ReleaseDate != null)
                server.releaseDate = client.ReleaseDate;
            else
                server.releaseDate = null;
            server.releaseCost = client.ReleasedCost;
            server.totalPages = client.TotalPages;
            server.totalRequestCost = client.TotalRequestCost;
            server.balanceDue = client.BalanceDue;
            server.totalPagesReleased = client.TotalPagesReleased;
            //server.salestaxTotalAmount = client.SalestaxTotalAmount;
            server.salesTaxPercentage = client.SalestaxPercentageForBillingLoc;
            server.billingType = client.BillingType;
            server.applySalesTax = client.ApplySalesTax;
            server.billingLocCode = client.BillingLocCode;
            server.billingLocName = client.BillingLocName;
            //requestCore.BillingTypeIdForFeeCharge=
            server.creditAdjustmentAmount = client.CreditAdjustmentAmount;
            server.debitAdjustmentAmount = client.DebitAdjustmentAmount;
            server.originalBalance = client.OriginalBalance;
            server.paymentAmount = client.PaymentAmount;
            server.salesTaxAmount = client.SalestaxTotalAmount;
            server.unbillable = client.IsUnbillable;

            server.requestCoreChargesBilling = new RequestCoreChargesBilling();
            server.requestCoreChargesShipping = new RequestCoreChargesShipping();
            server.requestCoreChargesBilling.requestCoreChargesDocument =
                new RequestCoreChargesDocument[client.BillingCharge.DocumentCharge.Count];
            server.requestCoreChargesBilling.requestCoreChargesFee =
                new RequestCoreChargesFee[client.BillingCharge.FeeCharge.Count];

            for (int count = 0; count < client.BillingCharge.DocumentCharge.Count; count++)
            {
                server.requestCoreChargesBilling.requestCoreChargesDocument[count] = new RequestCoreChargesDocument();
                server.requestCoreChargesBilling.requestCoreChargesDocument[count].amount = client.BillingCharge.DocumentCharge[count].Amount;
                server.requestCoreChargesBilling.requestCoreChargesDocument[count].billingtierId = client.BillingCharge.DocumentCharge[count].BillingTierId.ToString();
                server.requestCoreChargesBilling.requestCoreChargesDocument[count].billingTierName = client.BillingCharge.DocumentCharge[count].BillingTier;
                server.requestCoreChargesBilling.requestCoreChargesDocument[count].copies = client.BillingCharge.DocumentCharge[count].Copies;
                server.requestCoreChargesBilling.requestCoreChargesDocument[count].pages = client.BillingCharge.DocumentCharge[count].Pages;
                server.requestCoreChargesBilling.requestCoreChargesDocument[count].isElectronic = client.BillingCharge.DocumentCharge[count].IsElectronic;
                server.requestCoreChargesBilling.requestCoreChargesDocument[count].totalPages = Int32.Parse(client.BillingCharge.DocumentCharge[count].TotalPages.ToString());
                server.requestCoreChargesBilling.requestCoreChargesDocument[count].removeBaseCharge = client.BillingCharge.DocumentCharge[count].RemoveBaseCharge;
                server.requestCoreChargesBilling.requestCoreChargesDocument[count].releaseCount = client.BillingCharge.DocumentCharge[count].ReleaseCount;
                server.requestCoreChargesBilling.requestCoreChargesDocument[count].salesTaxAmount = client.BillingCharge.DocumentCharge[count].TaxAmount;
                server.requestCoreChargesBilling.requestCoreChargesDocument[count].hasSalesTax = client.BillingCharge.DocumentCharge[count].HasSalesTax;
            }
            for (int count = 0; count < client.BillingCharge.FeeCharge.Count; count++)
            {
                server.requestCoreChargesBilling.requestCoreChargesFee[count] = new RequestCoreChargesFee();
                server.requestCoreChargesBilling.requestCoreChargesFee[count].amount = client.BillingCharge.FeeCharge[count].Amount;
                server.requestCoreChargesBilling.requestCoreChargesFee[count].feeType = client.BillingCharge.FeeCharge[count].FeeType;
                server.requestCoreChargesBilling.requestCoreChargesFee[count].isCustomFee = client.BillingCharge.FeeCharge[count].IsCustomFee;
                server.requestCoreChargesBilling.requestCoreChargesFee[count].salesTaxAmount = client.BillingCharge.FeeCharge[count].TaxAmount;
                server.requestCoreChargesBilling.requestCoreChargesFee[count].hasSalesTax = client.BillingCharge.FeeCharge[count].HasSalesTax;
            }
            if (client.ShippingInfo != null)
            {
                server.requestCoreChargesShipping.shippingCharge = client.ShippingInfo.ShippingCharge;

                if (client.ShippingInfo.ShippingAddress != null)
                {
                    server.requestCoreChargesShipping.postalCode = client.ShippingInfo.ShippingAddress.PostalCode;
                    server.requestCoreChargesShipping.addressType = client.ShippingInfo.AddressType.ToString();
                    server.requestCoreChargesShipping.state = client.ShippingInfo.ShippingAddress.State;
                    server.requestCoreChargesShipping.city = client.ShippingInfo.ShippingAddress.City;
                    server.requestCoreChargesShipping.address3 = client.ShippingInfo.ShippingAddress.Address3;
                    server.requestCoreChargesShipping.address1 = client.ShippingInfo.ShippingAddress.Address1;
                    server.requestCoreChargesShipping.address2 = client.ShippingInfo.ShippingAddress.Address2;
                    server.requestCoreChargesShipping.countryCode = client.ShippingInfo.ShippingAddress.CountryCode;                    
                    server.requestCoreChargesShipping.countryName = client.ShippingInfo.ShippingAddress.CountryName;
                    server.requestCoreChargesShipping.newCountry = client.ShippingInfo.ShippingAddress.NewCountry;
                }

                server.requestCoreChargesShipping.shippingUrl = client.ShippingInfo.ShippingWebAddress;

                server.requestCoreChargesShipping.shippingWeight = Double.Parse(client.ShippingInfo.ShippingWeight.ToString());
                server.requestCoreChargesShipping.trackingNumber = client.ShippingInfo.TrackingNumber;


                server.requestCoreChargesShipping.willReleaseShipped = client.ShippingInfo.WillReleaseShipped;
                server.requestCoreChargesShipping.shippingMethod =  client.ShippingInfo.ShippingMethod;
                server.requestCoreChargesShipping.outputMethod = client.ShippingInfo.OutputMethod.ToString();
                server.requestCoreChargesShipping.shippingMethodId = client.ShippingInfo.ShippingMethodId;
                server.requestCoreChargesShipping.nonPrintableAttachmentQueue = client.ShippingInfo.NonPrintableAttachmentQueue;

                server.salesTaxSummary = new SalesTaxSummary();
                server.salesTaxSummary.salesTaxReason = new SalesTaxReason[client.SalesTaxReasonsList.Count];
                for (int count = 0; count < client.SalesTaxReasonsList.Count; count++)
                {
                    server.salesTaxSummary.salesTaxReason[count] = new SalesTaxReason();
                    server.salesTaxSummary.salesTaxReason[count].id = client.SalesTaxReasonsList[count].Id;
                    server.salesTaxSummary.salesTaxReason[count].reasonDate = client.SalesTaxReasonsList[count].CreatedDate;
                    server.salesTaxSummary.salesTaxReason[count].reason = client.SalesTaxReasonsList[count].Reason;
                }
                //// server.requestCoreChargesBilling.billingType="billing";
                //server.billingType = "billing";
            }
            return server;
        }


        public static BillingCore.InvoiceOrPrebillAndPreviewInfo MapModel(InvoiceOrPrebillPreviewInfo client)
        {
            InvoiceOrPrebillAndPreviewInfo server = new InvoiceOrPrebillAndPreviewInfo();

            server.amountpaid = client.Amountpaid;
            server.baseCharge = client.BaseCharge;
            server.invoiceBalanceDue = client.InvoiceBalanceDue;
            server.invoiceBillingLocCode = client.InvoiceBillingLocCode;
            server.invoiceBillinglocName = client.InvoiceBillinglocName;
            server.invoiceDueDate = client.InvoiceDueDate.AddDays(client.InvoiceDueDays);
            server.invoiceDueDays = client.InvoiceDueDays;
            server.invoicePrebillDate = client.InvoicePrebillDate;
            server.invoiceSalesTax = client.InvoiceSalesTax;
            server.letterTemplateFileId = client.LetterTemplateFileId;
            server.letterTemplateName = client.LetterTemplateName;
            server.notes = client.Notes;
            server.outputMethod = client.OutputMethod;
            server.type = client.Type;
            server.queuePassword = client.QueueSecretWord;
            server.requestCoreId = client.RequestCoreId;
            server.requestStatus = client.RequestStatus;
            server.overwriteDueDate = client.OverwriteDueDate;
            server.requestDate = client.RequestDate;
            server.resendDate = client.ResendDate;
            server.letterType = client.LetterType;
            server.willInvoiceShipped = client.WillInvoiceShipped;

            if (client.RequestTransaction != null)
            {
                List<BillingCore.RequestCoreDeliveryChargesAdjustmentPayment> adjustmentPayments = new List<BillingCore.RequestCoreDeliveryChargesAdjustmentPayment>();
                BillingCore.RequestCoreDeliveryChargesAdjustmentPayment serverAdjPay;
                serverAdjPay = new BillingCore.RequestCoreDeliveryChargesAdjustmentPayment();
                serverAdjPay.description = client.RequestTransaction.Description;
                serverAdjPay.transactionType = EnumUtilities.GetDescription(client.RequestTransaction.TransactionType);
                serverAdjPay.isDebit = client.RequestTransaction.IsDebit;
                serverAdjPay.amount = client.RequestTransaction.Amount;
                serverAdjPay.paymentMode = client.RequestTransaction.PaymentMode;
                serverAdjPay.paymentDate = DateTime.Now;
                serverAdjPay.newlyAdded = client.RequestTransaction.IsNewlyAdded;
                adjustmentPayments.Add(serverAdjPay);
                server.autoAdjustments = adjustmentPayments.ToArray();
            }

            //server.invoicePatients = new RequestCoreDeliveryInvoicePatients[client.InvoicePatients.Count];
            //int i = 0;
            //foreach (RequestCoreDeliveryInvoicePatientsList patient in client.InvoicePatients)
            //{

            //    server.invoicePatients[i] = new RequestCoreDeliveryInvoicePatients();
            //    server.invoicePatients[i].encounterFacility =patient.EncounterFacility;
            //    server.invoicePatients[i].epn = patient.Epn;
            //    server.invoicePatients[i].facility = patient.Facility;
            //    server.invoicePatients[i].isVIP = patient.IsVIP;
            //    server.invoicePatients[i].mrn = patient.Mrn;
            //    server.invoicePatients[i].name = patient.Name;
            //    server.invoicePatients[i].ssn = patient.Ssn;
            //    ++i;
            //}

            return server;
        }


        private static InvoiceAndDocumentDetails MapModel(BillingCore.DocInfo server)
        {
            InvoiceAndDocumentDetails client = new InvoiceAndDocumentDetails();

            //client.InvoiceChargeDetailsInfo.Id = server.id;
            //client.DocumentName = server.name;
            ////client.
            client.DocumentInfos = new DocumentInfoList();
            DocumentInfo docInfo = new DocumentInfo();

            docInfo.Id = server.id;
            docInfo.Name = server.name;
            docInfo.Type = server.type;
            client.DocumentInfos.DocumentInfoCollection.Add(docInfo);

            return client;
        }

        /// <summary>
        /// converts the server document charges to client document charges
        /// </summary>
        /// <param name="requestCoreChargesDocument"></param>
        /// <returns></returns>
        private List<DocumentChargeDetails> MapModel(RequestCoreChargesDocument[] requestCoreChargesDocument)
        {
            List<DocumentChargeDetails> client = new List<DocumentChargeDetails>();
            DocumentChargeDetails documentCharge;
            foreach (RequestCoreChargesDocument server in requestCoreChargesDocument)
            {
                documentCharge = new DocumentChargeDetails();
                documentCharge.Amount = server.amount;
                documentCharge.BillingTierId = Convert.ToInt32(server.billingtierId);
                documentCharge.Copies = server.copies;
                documentCharge.TotalPages = server.totalPages;
                documentCharge.Pages = server.pages;
                client.Add(documentCharge);
            }
            return client;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Get the PatientController Instance
        /// </summary>
        public new static RequestController Instance
        {
            get
            {
                if (requestController == null)
                {
                    lock (syncRoot)
                    {
                        if (requestController == null)
                        {
                            requestController = new RequestController();
                        }
                    }
                }
                return requestController;
            }
        }

        #endregion
    }
}
