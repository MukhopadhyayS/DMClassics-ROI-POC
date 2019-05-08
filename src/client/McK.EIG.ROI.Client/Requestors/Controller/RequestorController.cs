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

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.View.FindRequestor;

using McK.EIG.ROI.Client.Web_References.HPFPatientWS; 
using McK.EIG.ROI.Client.Web_References.ROIRequestorWS;
using McK.EIG.ROI.Client.Web_References.ROISupplementaryWS;
using billingCoreService = McK.EIG.ROI.Client.Web_References.BillingCoreWS;
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Requestors.Controller
{
    public class RequestorController : ROIController
    {
        #region Fields

        private const string documentType = "DOCX";
        
        private RequestorServiceWse requestorService;

        private billingCoreService.BillingCoreServiceWse billingService;

        private ROISupplementaryServiceWse roiSupplementaryService;

        //Private variable to create instance of HPFPatientService 
        private PatientServiceWse hpfPatientService;

        #endregion

        #region Constructor

        private RequestorController()
        {  
            requestorService  = new RequestorServiceWse();
            billingService = new billingCoreService.BillingCoreServiceWse();
        }

        #endregion

        #region Methods

        #region ServiceMethods
        /// <summary>
        /// Get the requestor search result.
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        public FindRequestorResult FindRequestor(FindRequestorCriteria searchCriteria)
        {
            if (!RequestorValidator.ValidateRequestorSearch(searchCriteria))
            {
                throw new ROIException(ROIErrorCodes.InvalidSearch);
            }

            RequestorValidator validator = new RequestorValidator();
            //FindRequestorSearchUI findRequestorSearchUI = new FindRequestorSearchUI();

            //Added for special character validation.
            if (!validator.ValidateSearchCriteria(searchCriteria) ||
                FindRequestorSearchUI.IsError)
            {
                throw validator.ClientException;
            }
            RequestorSearchCriteria serverSearchCriteria = MapModel(searchCriteria);
            object[] roiRequestParams = new object[] { serverSearchCriteria };
            object roiResponse = ROIHelper.Invoke(requestorService, "findRequestor", roiRequestParams);
            FindRequestorResult result = new FindRequestorResult();
            if (roiResponse != null)
            {
                result = MapModel((RequestorSearchResult)roiResponse);
            
                if (result.MaxCountExceeded)
                {
                    result.RequestorSearchResult.RemoveAt(searchCriteria.MaxRecord);
                }
            }
            return result;
        }

        public FindPatientResult SearchMatchingPatients(RequestorDetails searchCriteria)
        {

            object[] hpfRequestParams = new object[] {searchCriteria.FullName, 
                                                       searchCriteria.PatientDob.HasValue ? searchCriteria.PatientDob.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture) : null,
                                                       searchCriteria.PatientSSN, 
                                                       searchCriteria.PatientEPN,
                                                       searchCriteria.PatientMRN,
                                                       searchCriteria.PatientFacilityCode,
                                                       ROIConstants.DateFormat,
                                                      };

            if (hpfPatientService == null)
            {
                hpfPatientService = new PatientServiceWse();
            }
            object hpfResponse = HPFWHelper.Invoke(hpfPatientService, "searchPatientsByExactMatch", PrepareHPFWParams(hpfRequestParams));

            FindPatientResult result = new FindPatientResult();

            if (hpfResponse != null)
            {
                MapModel((facilityPatient[])hpfResponse, result);
            }

            SearchCriteria supplementalSearchCriteria = CreatePatientSearchCriteria(searchCriteria);
            object[] roiRequestParams = new object[] { supplementalSearchCriteria };

            if (roiSupplementaryService == null)
            {
                roiSupplementaryService = new ROISupplementaryServiceWse();
            }
            object roiResponse = ROIHelper.Invoke(roiSupplementaryService, "searchSupplementalPatient", roiRequestParams);

            PatientController.MapModel(roiResponse, result);

            List<PatientDetails> list = new List<PatientDetails>(result.PatientSearchResult);
            list.Sort(PatientDetails.Sorter);
            
            result.PatientSearchResult.Clear();
            list.ForEach(result.PatientSearchResult.Add);

            return result;
        }

        /// <summary>
        /// Method to check for duplicate Requestor name
        /// </summary>
        /// <param name="requestorName"></param>
        /// <returns></returns>
        public bool CheckDuplicateName(RequestorDetails client)
        {
            client.Normalize();
            RequestorValidator validator = new RequestorValidator();
            if (!validator.Validate(client))
            {
                throw validator.ClientException;
            }
            object[] requestParams = new object[] {client.Id, client.FullName};
            object response = ROIHelper.Invoke(requestorService, "checkDuplicateRequestorName", requestParams);
            return (bool)response;
        }

        /// <summary>
        ///  Creates a new Requestor
        /// </summary>
        /// <param name="clientRequestor"></param>
        /// <returns></returns>
        public long CreateRequestor(RequestorDetails client)
        {
            client.Normalize();
            RequestorValidator validator = new RequestorValidator();
            if (!validator.Validate(client))
            {
                throw validator.ClientException;
            }
            Requestor serverRequstor = MapModel(client);
            object[] requestParams = new object[] {serverRequstor};
            object response = ROIHelper.Invoke(requestorService, "createRequestor", requestParams);
            return (long)response;
        }

        /// <summary>
        ///  Updates an existing Requestor
        /// </summary>
        /// <param name="clientRequestor"></param>
        /// <returns></returns>
        public RequestorDetails UpdateRequestor(RequestorDetails client)
        {
            client.Normalize();
            RequestorValidator validator = new RequestorValidator();
            if (!validator.Validate(client)) 
            {
                throw validator.ClientException;
            }
            Requestor server = MapModel(client);
            object[] requestParams = new object[] { server };
            ROIHelper.Invoke(requestorService, "updateRequestor", requestParams);
            client = MapModel((Requestor)requestParams[0]);
            RequestorCache.RemoveKey(client.Id);
            return client;

        }

        public RequestorDetails RetrieveRequestor(long id, bool hasViewRequestor)
        {
            object[] requestParams = new object[] { id, hasViewRequestor };
            object response = ROIHelper.Invoke(requestorService, "retrieveRequestor", requestParams);
            RequestorDetails client = MapModel(response as Requestor);
            return client;
        }

         
        /// <summary>
        /// View the requestor letter
        /// </summary>
        /// <param name="requestorLetterId"></param>
        /// <returns></returns>
        public DocumentInfo ViewRequestorLetter(long requestorLetterId)
        {
            object[] requestParams = new object[] { requestorLetterId, documentType };
            object response = ROIHelper.Invoke(requestorService, "viewRequestorLetter", requestParams);
            return MapModel((DocInfo)response);
        }


        /// <summary>
        ///  Delete an existing Requestor
        /// </summary>
        /// <param name="clientRequestor"></param>
        /// <returns></returns>
        public void DeleteRequestor(long id)
        {
            object[] requestParams = new object[] { id };
            ROIHelper.Invoke(requestorService, "deleteRequestor", requestParams);
            RequestorCache.RemoveKey(id);
        }


        public Collection<RequestInvoiceDetail> RetrieveRequestorInvoices(long requestorId)
        {
            if (RequestorCache.IsKeyExist(requestorId))
            {
                return RequestorCache.GetRequestorInvoDetails(requestorId);
            }
            else
            {
                object[] requestParams = new object[] { requestorId };
                object response = ROIHelper.Invoke(requestorService, "retrieveRequestorInvoices", requestParams);
                Collection<RequestInvoiceDetail> reqInvoiceList = new Collection<RequestInvoiceDetail>();
                reqInvoiceList = MapModel(response as RequestorInvoice[]);
                if (reqInvoiceList.Count > 0)
                {
                    RequestorCache.AddData(requestorId, reqInvoiceList);
                }
                return reqInvoiceList;
            }
        }

        public void CreateInvoicePayment(RequestorInvoiceList requestorInvoiceList)
        {            
            object[] requestParams = new object[] { MapModel(requestorInvoiceList) };
            object response = ROIHelper.Invoke(requestorService, "createRequestorPayment", requestParams);
            RequestorCache.RemoveKey(requestorInvoiceList.RequestorId);
        }

        public void UpdateInvoicePayment(RequestorInvoiceList requestorInvoiceList)
        {
            object[] requestParams = new object[] { MapModel(requestorInvoiceList) };
            object response = ROIHelper.Invoke(requestorService, "updateRequestorPayment", requestParams);
            RequestorCache.RemoveKey(requestorInvoiceList.RequestorId);
        }

        public AdjustmentInfoDetail RetrieveAdjustmentInfo(long requestorId)
        {
            object[] requestParams = new object[] { requestorId };
            object response = ROIHelper.Invoke(requestorService, "retrieveAdjustmentInfo", requestParams);
            AdjustmentInfoDetail adjInfoDetail = new AdjustmentInfoDetail();
            adjInfoDetail = MapModel(response as AdjustmentInfo);
            return adjInfoDetail;
        }

        public void SaveAdjustmentInfo(AdjustmentInfoDetail adjInfoDetail)
        {
            object[] requestParams = new object[] { MapModel(adjInfoDetail) };
            object response = ROIHelper.Invoke(requestorService, "saveAdjustmentInfo", requestParams);
            
        }

        public AdjustmentInfoDetail RetrieveAdjustmentInfoByAdjustmentId(long adjustmentId, long requestorId)
        {
            AdjustmentInfoDetail adjInfoDetail = new AdjustmentInfoDetail();
            object[] requestParams = new object[] { adjustmentId, requestorId };
            object response = ROIHelper.Invoke(requestorService, "retrieveAdjustmentInfoByAdjustmentId", requestParams);
            adjInfoDetail = MapModel(response as AdjustmentInfo);
            return adjInfoDetail;
        }

        /// <summary>
        /// Method to retrieve the past invoice of the requestor
        /// </summary>
        /// <param name="requestorId"></param>
        /// <returns></returns>
        public Collection<PastInvoiceDetails> RetrieveRequestorPastInvoiceDetails(long requestorId)
        {
            object[] requestParams = new object[] { requestorId };
            object response = ROIHelper.Invoke(requestorService, "retrieveRequestorPastInvoices", requestParams);
            return MapModel(response as PastInvoice[]);
        }

        /// <summary>
        /// Method to generate the requestor statement
        /// </summary>
        /// <param name="requestorStatementDetails"></param>
        /// <returns></returns>
        public DocumentInfoList GenerateRequestorStatement(RequestorStatementDetail requestorStatementDetails)
        {
            RequestorStatementCriteria requestorStatementCriteria = MapModel(requestorStatementDetails);
            object[] requestParams = new object[] { requestorStatementCriteria };
            object response = ROIHelper.Invoke(requestorService, "generateRequestorStatement", requestParams);
            return MapModel((DocInfoList)response);
        }

        /// <summary>
        /// Method to create the requestor statement
        /// </summary>
        /// <param name="requestorStatementDetails"></param>
        /// <returns></returns>
        public long CreateRequestorStatement(RequestorStatementDetail requestorStatementDetails)
        {
            RequestorStatementCriteria requestorStatementCriteria = MapModel(requestorStatementDetails);
            object[] requestParams = new object[] { requestorStatementCriteria };
            object response = ROIHelper.Invoke(requestorService, "createRequestorStatement", requestParams);
            return (long)response;
        }



        public List<RequestorUnappliedAmountDetail> RetrieveUnappliedAmountDetails(long requestId)
        {
            List<RequestorUnappliedAmountDetail> adjPayDetail = new List<RequestorUnappliedAmountDetail>();
            object[] requestParams = new object[] {requestId};
            object response = ROIHelper.Invoke(requestorService, "retrieveUnappliedAmountDetails", requestParams);
            adjPayDetail = MapModel(response as RequestorUnappliedAmountDetails[]);
            return adjPayDetail;
        }

        public Collection<RequestorHistoryDetail> RetrieveRequestorSummaries(long requestorId)
        {
            Collection<RequestorHistoryDetail> reqHistory = new Collection<RequestorHistoryDetail>();
            object[] requestParams = new object[] { requestorId };
            object response = ROIHelper.Invoke(requestorService, "retrieveRequestorSummaries", requestParams);
            reqHistory = MapModel(response as RequestorHistory[]);
            return reqHistory;  
        }

        public DocumentInfo ViewRequestorDetails(long invoiceId, long requestId, String docType, String retrieverType, String letterType)
        {
            DocumentInfo docInfo = new DocumentInfo();
            object[] requestParams = new object[] { invoiceId, requestId, docType, retrieverType, letterType };
            object response = ROIHelper.Invoke(requestorService, "viewRequestorDetails", requestParams);
            return MapModel((McK.EIG.ROI.Client.Web_References.ROIRequestorWS.DocInfo)response);
        }

        public DocInfoList CreateRequestorRefund(RequestorRefundDetail requestorRefundDetail, bool isOutputRefundLetter)
        {
            RequestorRefund requestorRefund = MapModel(requestorRefundDetail, isOutputRefundLetter);
            object[] requestParams = new object[] { requestorRefund };
            object response = ROIHelper.Invoke(requestorService, "createRequestorRefund", requestParams);
            return (DocInfoList)response;
        }

        public DocumentInfoList ViewRequestorRefund(RequestorRefundDetail requestorRefundDetail)
        {
            RequestorRefund requestorRefund = MapModel(requestorRefundDetail, true);
            object[] requestParams = new object[] { requestorRefund };
            object response = ROIHelper.Invoke(requestorService, "viewRequestorRefund", requestParams);
            return MapModel((DocInfoList)response);
        }

        /// <summary>
        /// Method to delete the requestor payment
        /// </summary>
        /// <param name="paymentID"></param>
        /// <param name="requestorName"></param>
        public void DeleteRequestorPayment(long paymentID, string requestorName)
        {
            object[] requestParams = new object[] { paymentID, requestorName };
            object response = ROIHelper.Invoke(requestorService, "deleteRequestorPayment", requestParams);
        }

        #endregion

        #region Model Mapping

        private static Collection<RequestorHistoryDetail> MapModel(RequestorHistory[] reqHistoryList)
        {
            Collection<RequestorHistoryDetail> reqHistoryDetailList = new Collection<RequestorHistoryDetail>();
            RequestorHistoryDetail reqHistoryDetail;
            foreach(RequestorHistory reqHistory in reqHistoryList)
            {
                reqHistoryDetail = new RequestorHistoryDetail();
                reqHistoryDetail.CreatedDate = reqHistory.createdDate;
                reqHistoryDetail.CreatorName = reqHistory.creatorName;
                reqHistoryDetail.Aging = reqHistory.aging;
                reqHistoryDetail.Balance = reqHistory.balance;
                reqHistoryDetail.Id = reqHistory.id;
                reqHistoryDetail.InvoiceBalance = reqHistory.invoiceBalance;
                reqHistoryDetail.InvoiceDueDate = reqHistory.invoiceDueDate;
                reqHistoryDetail.PrebillStatus = reqHistory.prebillStatus;
                reqHistoryDetail.QueueSecretWord = reqHistory.queuePassword;
                if (!string.IsNullOrEmpty(reqHistory.queuePassword))
                {
                    reqHistoryDetail.RequestSecretWord = reqHistory.requestPassword;
                }
                reqHistoryDetail.Status = reqHistory.status;
                reqHistoryDetail.Template = reqHistory.template;
                reqHistoryDetail.Type = reqHistory.type;
                reqHistoryDetail.IsMigrated = reqHistory.migrated;
                reqHistoryDetail.RequestId = reqHistory.requestId;
                reqHistoryDetailList.Add(reqHistoryDetail);
            }
            return reqHistoryDetailList;
        }

        private static List<RequestorUnappliedAmountDetail> MapModel(RequestorUnappliedAmountDetails[] reqUnApplied)
        {
            try
            {
                List<RequestorUnappliedAmountDetail> reqUnAppliedAmtList = new List<RequestorUnappliedAmountDetail>();
                foreach (RequestorUnappliedAmountDetails req in reqUnApplied)
                {
                    RequestorUnappliedAmountDetail reqUnAppliedAmt = new RequestorUnappliedAmountDetail();
                    reqUnAppliedAmt.Amount = req.amount;
                    reqUnAppliedAmt.Type = req.type;
                    reqUnAppliedAmtList.Add(reqUnAppliedAmt);
                }

                return reqUnAppliedAmtList;
            }
            catch (Exception ex)
            {
            }

            return null;
            
        }

        private static AdjustmentInfo MapModel(AdjustmentInfoDetail adjInfoDetail)
        {
            AdjustmentInfo adjInfo = new AdjustmentInfo();
            int count = 0;
            if (adjInfoDetail.RequestorInvoicesList != null && adjInfoDetail.RequestorInvoicesList.Count!=0)
            {
            adjInfo.requestorInvoicesList = new RequestorInvoice[adjInfoDetail.RequestorInvoicesList.Count];
            foreach (RequestInvoiceDetail reqInvDetail in adjInfoDetail.RequestorInvoicesList)
            {
                adjInfo.requestorInvoicesList[count] = new RequestorInvoice();
                adjInfo.requestorInvoicesList[count].adjustmentAmount = reqInvDetail.Adjustments;
                adjInfo.requestorInvoicesList[count].adjustmentPaymentTotal = Math.Abs(reqInvDetail.PayAdjTotal);
                adjInfo.requestorInvoicesList[count].balance = reqInvDetail.Balance;
                adjInfo.requestorInvoicesList[count].charge = reqInvDetail.Charges;
                adjInfo.requestorInvoicesList[count].description = reqInvDetail.Description;
                adjInfo.requestorInvoicesList[count].invoiceId = reqInvDetail.Id;
                adjInfo.requestorInvoicesList[count].requestId = reqInvDetail.RequestId;
                adjInfo.requestorInvoicesList[count].invoiceStatus = reqInvDetail.InvoiceStatus;
                adjInfo.requestorInvoicesList[count].invoiceType = reqInvDetail.InvoiceType;
                adjInfo.requestorInvoicesList[count].paymentAmount = reqInvDetail.Payments;
                adjInfo.requestorInvoicesList[count].appliedAmount = reqInvDetail.ApplyAmount + reqInvDetail.AppliedAmount;
                adjInfo.requestorInvoicesList[count].applyAmount = reqInvDetail.ApplyAmount;
                adjInfo.requestorInvoicesList[count].unBillableAmount = reqInvDetail.UnBillableAmount;
                adjInfo.requestorInvoicesList[count].billingLocation = reqInvDetail.Facility;
                count++;
                }
            }
            if (adjInfoDetail.ReqAdjustmentDetails != null)
            {
                adjInfo.requestorAdjustment = new RequestorAdjustment();
                adjInfo.requestorAdjustment.adjustmentDate = Convert.ToDateTime(adjInfoDetail.ReqAdjustmentDetails.AdjustmentDate, CultureInfo.InvariantCulture).Date;
                adjInfo.requestorAdjustment.amount = adjInfoDetail.ReqAdjustmentDetails.Amount;
                adjInfo.requestorAdjustment.note = adjInfoDetail.ReqAdjustmentDetails.Note;
                adjInfo.requestorAdjustment.reason = adjInfoDetail.ReqAdjustmentDetails.Reason;
                adjInfo.requestorAdjustment.requestorSeq = adjInfoDetail.ReqAdjustmentDetails.RequestorSeq;
                adjInfo.requestorAdjustment.unappliedAmount = adjInfoDetail.ReqAdjustmentDetails.UnappliedAmount;
                adjInfo.requestorAdjustment.id = adjInfoDetail.ReqAdjustmentDetails.AdjId;
                adjInfo.requestorAdjustment.requestorName = adjInfoDetail.ReqAdjustmentDetails.RequestorName;
                adjInfo.requestorAdjustment.requestorType = adjInfoDetail.ReqAdjustmentDetails.RequestorType;
                adjInfo.requestorAdjustment.delete = adjInfoDetail.ReqAdjustmentDetails.IsRemoveAdjustment;
                adjInfo.requestorAdjustment.prebillAdjustment = adjInfoDetail.ReqAdjustmentDetails.IsPrebillAdjustment;
                if (!adjInfoDetail.ReqAdjustmentDetails.AdjustmentType.Equals(McK.EIG.ROI.Client.Requestors.Model.AdjustmentType.PleaseSelect))
                {
                    adjInfo.requestorAdjustment.adjustmentType = (McK.EIG.ROI.Client.Web_References.ROIRequestorWS.AdjustmentType)Enum.Parse
                                                                (typeof(McK.EIG.ROI.Client.Web_References.ROIRequestorWS.AdjustmentType), 
                                                                adjInfoDetail.ReqAdjustmentDetails.AdjustmentType.ToString());
                }
            }
            return adjInfo;
        }


        private static ReasonDetails MapModel(Reason serverReason)
        {
            ReasonDetails clientReasons = new ReasonDetails();
            clientReasons.Id = serverReason.id;
            clientReasons.Name = serverReason.name;
            clientReasons.DisplayText = serverReason.displayText;
            clientReasons.RecordVersionId = serverReason.recordVersion;
            clientReasons.Type = (ReasonType)Enum.Parse(typeof(ReasonType),
                                                                   serverReason.type, true);

            if (clientReasons.Type == ReasonType.Status)
            {
                clientReasons.RequestStatus = (RequestStatus)serverReason.status;
            }
            else if (clientReasons.Type == ReasonType.Request)
            {
                if (serverReason.tpo)
                {
                    clientReasons.Attribute = RequestAttr.Tpo;
                }
                else if (serverReason.nonTpo)
                {
                    clientReasons.Attribute = RequestAttr.NonTpo;
                }
            }
            return clientReasons;
        }

        public static Collection<ReasonDetails> MapModel(Reason[] serverReasons)
        {
            if (serverReasons != null)
            {
                Collection<ReasonDetails> clientReasons = new Collection<ReasonDetails>();
                foreach (Reason serverReason in serverReasons)
                {
                    clientReasons.Add(MapModel(serverReason));
                }

                return clientReasons;
            }
            return null;
        }

        private static AdjustmentInfoDetail MapModel(AdjustmentInfo adjInfo)
        {
            AdjustmentInfoDetail adjInfoDetail=new AdjustmentInfoDetail();
            if (adjInfo != null)
            {
                adjInfoDetail.ReasonsList = new Collection<ReasonDetails>();
                adjInfoDetail.ReasonsList = MapModel(adjInfo.reasonsList);
                adjInfoDetail.RequestorInvoicesList = new Collection<RequestInvoiceDetail>();
                adjInfoDetail.RequestorInvoicesList=MapModel(adjInfo.requestorInvoicesList);           

                if(adjInfo.requestorAdjustment!=null)
                {
                    adjInfoDetail.ReqAdjustmentDetails = new RequestorAdjustmentDetails();
                    adjInfoDetail.ReqAdjustmentDetails.AdjustmentDate = adjInfo.requestorAdjustment.adjustmentDate.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture);
                    adjInfoDetail.ReqAdjustmentDetails.Amount = adjInfo.requestorAdjustment.amount;
                    adjInfoDetail.ReqAdjustmentDetails.Note=adjInfo.requestorAdjustment.note;
                    adjInfoDetail.ReqAdjustmentDetails.Reason=adjInfo.requestorAdjustment.reason;
                    adjInfoDetail.ReqAdjustmentDetails.RequestorSeq=adjInfo.requestorAdjustment.requestorSeq;
                    adjInfoDetail.ReqAdjustmentDetails.UnappliedAmount=adjInfo.requestorAdjustment.unappliedAmount;
                    if (adjInfo.requestorAdjustment.adjustmentType.Equals(McK.EIG.ROI.Client.Web_References.ROIRequestorWS.AdjustmentType.BAD_DEBT_ADJUSTMENT))
                    {
                        adjInfoDetail.ReqAdjustmentDetails.AdjustmentType = McK.EIG.ROI.Client.Requestors.Model.AdjustmentType.BAD_DEBT_ADJUSTMENT;
                    }
                    else if (adjInfo.requestorAdjustment.adjustmentType.Equals(McK.EIG.ROI.Client.Web_References.ROIRequestorWS.AdjustmentType.BILLING_ADJUSTMENT))
                    {
                        adjInfoDetail.ReqAdjustmentDetails.AdjustmentType = McK.EIG.ROI.Client.Requestors.Model.AdjustmentType.BILLING_ADJUSTMENT;
                    }
                    else
                    {
                        adjInfoDetail.ReqAdjustmentDetails.AdjustmentType = McK.EIG.ROI.Client.Requestors.Model.AdjustmentType.CUSTOMER_GOODWILL_ADJUSTMENT;
                    }
                }
            }
            return adjInfoDetail;
        }
        
        public static RequestorPaymentList MapModel(RequestorInvoiceList requestorInviceList)
        {
            RequestorPaymentList requestorPaymentList = new RequestorPaymentList();

            requestorPaymentList.paymentAmount = requestorInviceList.PaymentAmount;
            requestorPaymentList.paymentMode = requestorInviceList.PaymentMode;
            requestorPaymentList.paymentDate = requestorInviceList.PaymentDate.Value;
            requestorPaymentList.unAppliedAmount = requestorInviceList.UnAppliedAmount;
            requestorPaymentList.requestorId = requestorInviceList.RequestorId;
            requestorPaymentList.paymentId = requestorInviceList.PaymentId;
            requestorPaymentList.description = requestorInviceList.Description;
            requestorPaymentList.requestorName = requestorInviceList.RequestorName;
            requestorPaymentList.requestorType = requestorInviceList.RequestorType;
            
            List<RequestorPayment> RequestCoreDeliveryChargesPayments = new List<RequestorPayment>();

            foreach (RequestorInvoiceDetails requestorInvoiceDetails in requestorInviceList.RequestorInvoices)
            {
                RequestorPayment requestorPayment = new RequestorPayment();

                requestorPayment.totalAppliedAmount = requestorInvoiceDetails.ApplyAmount;
                requestorPayment.lastAppliedAmount = requestorInvoiceDetails.CurrentAppliedAmount;
                requestorPayment.invoiceAmountPaid = requestorInvoiceDetails.AmountPaid;
                requestorPayment.invoicePaymentTotal = requestorInvoiceDetails.PaymentTotal;
                requestorPayment.requestCoreDeliveryChargesId = requestorInvoiceDetails.InvoiceId;
                requestorPayment.invoicedBaseCharge = requestorInvoiceDetails.BaseCharge;
                requestorPayment.invoiceBalance = requestorInvoiceDetails.Balance;
                requestorPayment.paymentId = requestorInviceList.PaymentId;
                requestorPayment.facility = requestorInvoiceDetails.Facility;
                requestorPayment.requestId = requestorInvoiceDetails.RequestId;
                requestorPayment.prebillPayment = requestorInvoiceDetails.IsPrebillPayment;
                RequestCoreDeliveryChargesPayments.Add(requestorPayment);
            }
            requestorPaymentList.paymentList = RequestCoreDeliveryChargesPayments.ToArray();
            return requestorPaymentList;
        }

        private static Collection<RequestInvoiceDetail> MapModel(RequestorInvoice[] reqInvoice)
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

        private static RequestInvoiceDetail MapModel(RequestorInvoice requestorInvoice)
        {
            RequestInvoiceDetail reqInvoiceDetail = new RequestInvoiceDetail();
            if (requestorInvoice != null)
            {
                reqInvoiceDetail.Facility = requestorInvoice.billingLocation;
                reqInvoiceDetail.Adjustments =reqInvoiceDetail.AdjAmount = -(requestorInvoice.adjustmentAmount);
                reqInvoiceDetail.Payments =reqInvoiceDetail.PayAmount=  -(requestorInvoice.paymentAmount);
                if(requestorInvoice.invoiceType == "Refund")
                {
                    reqInvoiceDetail.PayAmount = requestorInvoice.paymentAmount;
                    reqInvoiceDetail.AdjAmount = requestorInvoice.adjustmentAmount;
                }
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
                reqInvoiceDetail.Unbillable = requestorInvoice.unbillable;
                reqInvoiceDetail.BillableIcon = ("NO".Equals(requestorInvoice.unbillable)) ? ROIImages.BillableIcon : null;
                reqInvoiceDetail.RefundAmount = requestorInvoice.refundAmount;
                //CR#377295
                reqInvoiceDetail.ModifiedDate = requestorInvoice.modifiedDt.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture);

                int unAuthorizedRequestorInvoiceCount = 0;
                if (requestorInvoice.facility != null)
                {
                    foreach (string facility in requestorInvoice.facility)
                    {
						//CR# 382398
                        if (!UserData.Instance.Facilities.Contains(FacilityDetails.GetFacility(ROIViewUtility.OriginalAmpersand(facility), FacilityType.Hpf)))
                        {
                            unAuthorizedRequestorInvoiceCount++;
                        }
                    }

                    if (unAuthorizedRequestorInvoiceCount > 0)
                    {
                        reqInvoiceDetail.HasMaskedRequestorFacility = true;
                    }

                    if (unAuthorizedRequestorInvoiceCount == requestorInvoice.facility.Length)
                    {
                        reqInvoiceDetail.HasBlockedRequestorFacility = true;
                    }
                }

                if (requestorInvoice.invoiceType == "Unapplied Payment")
                {
                    reqInvoiceDetail.PayAmount = -(requestorInvoice.balance);
                }
                else if (requestorInvoice.invoiceType == "Unapplied Adjustment")
                {
                    reqInvoiceDetail.AdjAmount = -(requestorInvoice.balance);
                }
                reqInvoiceDetail.AppliedAmount = requestorInvoice.appliedAmount;
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

        private static RequestorAdjustmentsPaymentsDetail MapModel(RequestorAdjustmentsPayments reqAdjPayment)
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
            reqAdjPay.RefundAmount = reqAdjPayment.refundAmount;
            reqAdjPay.ModifiedDate = reqAdjPayment.date.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture);
            return reqAdjPay; 
        }

        /// <summary>
        /// Convert server address details to client address details.
        /// </summary>
        /// <param name="serverAddress"></param>
        /// <returns></returns>
        private static AddressDetails MapModel(Address server)
        {
            AddressDetails client = new AddressDetails();

            if (server != null)
            {
                client.Address1   = server.address1;
                client.Address2   = server.address2;
                client.Address3   = server.address3;
                client.City       = server.city;
                client.State      = server.state;
                client.PostalCode = server.postalCode;
                client.CountryName = server.countryName;
                client.CountryCode = server.countryCode;
                client.NewCountry = server.newCountry;
                client.CountrySeq = server.countrySeq;
            }
            return client;
        }

        /// <summary>
        /// Convert client address detail to server address detsil.
        /// </summary>
        /// <param name="clientAddress"></param>
        /// <returns></returns>
        private static Address MapModel(AddressDetails client)
        {
            Address server = new Address();

            if (client != null)
            {
                server.address1   = client.Address1;
                server.address2   = client.Address2;
                server.address3   = client.Address3;
                server.city       = client.City;
                server.state      = client.State;
                server.postalCode = client.PostalCode;
                server.countryName = client.CountryName;
                server.countryCode = client.CountryCode;
                server.newCountry = client.NewCountry;
                server.countrySeq = client.CountrySeq;
            }
            return server;
        }


       public static void MapModel(facilityPatient[] searchResult, FindPatientResult appendTo)
        {
            PatientDetails patientDetails;

            //int index;
            foreach (facilityPatient hpfPatient in searchResult)
            {
                //index = 0;
                //index += Convert.ToInt32(searchCriteria.Name == hpfPatient.fullName, System.Threading.Thread.CurrentThread.CurrentUICulture);
                //index += Convert.ToInt32(searchCriteria.PatientSSN  == hpfPatient.ssn, System.Threading.Thread.CurrentThread.CurrentUICulture);
                //index += Convert.ToInt32(searchCriteria.PatientEPN  == hpfPatient.epn, System.Threading.Thread.CurrentThread.CurrentUICulture);
                //if (searchCriteria.PatientDob.HasValue)
                //{
                //    index += Convert.ToInt32(searchCriteria.PatientDob.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture) == hpfPatient.dob);
                //}

                //if (index < 2) continue;

                patientDetails = new PatientDetails();
                if (hpfPatient.dob.Length != 0)
                {
                    patientDetails.DOB = DateTime.ParseExact(hpfPatient.dob, ROIConstants.DateFormat, CultureInfo.InvariantCulture);
                }
                patientDetails.EPN                = hpfPatient.epn;
                patientDetails.FacilityCode       = hpfPatient.facility;
                //patientDetails.IsFreeformFacility = hpfPatient;
                patientDetails.FullName           = hpfPatient.fullName;
                patientDetails.MRN                = hpfPatient.mrn;
                patientDetails.SSN                = hpfPatient.ssn;
                patientDetails.IsVip              = hpfPatient.vip;
                patientDetails.PatientLocked      = hpfPatient.patientLocked;
                patientDetails.EncounterLocked    = hpfPatient.encounterLocked;
                patientDetails.IsHpf             = true;

                appendTo.PatientSearchResult.Add(patientDetails);
            }
        }

        /// <summary>
        /// Convert server search result to client search result.
        /// </summary>
        /// <param name="serverSearchResult"></param>
        /// <returns></returns>
        private static FindRequestorResult MapModel(RequestorSearchResult serverSearchResult)
        {
            FindRequestorResult clientSearchResult = new FindRequestorResult();            
            clientSearchResult.MaxCountExceeded = serverSearchResult.maxCountExceeded;
            
            Requestor[] requestorResult = serverSearchResult.searchResults;
            RequestorDetails requestorDetails;
            if (requestorResult == null)
            {
                return clientSearchResult;
            }

            foreach (Requestor requestor in requestorResult)
            {
                requestorDetails               = new RequestorDetails();

                requestorDetails.Id            = requestor.id;
                requestorDetails.Type          = requestor.type;
                if (requestorDetails.IsPatientRequestor)
                {
                    requestorDetails.LastName = requestor.lastName;
                    requestorDetails.FirstName = requestor.firstName;
                    requestorDetails.Name = requestorDetails.LastName + ", " + requestorDetails.FirstName;
                }
                else
                {
                    requestorDetails.Name = requestor.lastName;
                }
                if (requestor.dob.HasValue)
                {
                    requestorDetails.PatientDob = Convert.ToDateTime(requestor.dob.ToString(), CultureInfo.InvariantCulture).Date;
                }
                requestorDetails.PatientEPN    = requestor.epn;
                requestorDetails.PatientSSN    = requestor.ssn;
                requestorDetails.PatientMRN = requestor.mrn;
                requestorDetails.PatientFacilityCode = requestor.facility;
                requestorDetails.IsFreeformFacility = requestor.freeFormFacility;
                requestorDetails.MainAddress   = MapModel(requestor.mainAddress);
                requestorDetails.AltAddress    = MapModel(requestor.altAddress);
                requestorDetails.HomePhone     = requestor.homePhone;
                requestorDetails.Fax           = requestor.fax;
                requestorDetails.WorkPhone     = requestor.workPhone;
                requestorDetails.IsActive      = requestor.active;

                clientSearchResult.RequestorSearchResult.Add(requestorDetails);
            }
           
            return clientSearchResult;
        }
        
        /// <summary>
        /// Convert Client Requestor Search Criteria to server Requestor Search Criteria.
        /// </summary>
        /// <param name="clientSearchCriteria"></param>
        /// <returns></returns>
        private static RequestorSearchCriteria MapModel(FindRequestorCriteria client)
        {
            RequestorSearchCriteria server = new RequestorSearchCriteria();

            server.maxCount = client.MaxRecord;
            server.type     = client.RequestorTypeId;
            if (!(string.IsNullOrEmpty(client.FirstName)) || !(string.IsNullOrEmpty(client.LastName)))
            {
                server.lastName = client.LastName;
                server.firstName = client.FirstName;
            }
            else
            {
                server.lastName = client.Name;
            }
            
            server.epn      = client.PatientEPN;
            server.ssn      = (client.PatientSSN != null) ? client.PatientSSN.Trim('-', ' ') : null;
            server.mrn      = client.PatientMRN;
            server.facility = client.PatientFacilityCode;
            server.freeFormFacility = client.IsFreeformFacility;
            
            server.typeSpecified = (client.RequestorTypeId != 0);            
            server.allStatus = !client.ActiveRequestor.HasValue;            
            
            if (client.ActiveRequestor.HasValue)
            {
                server.activeRequestors = client.ActiveRequestor.Value;
            }
            
            server.allRequestors = client.AllRequestors;

            if (client.PatientDob.HasValue)
            {
                server.dob = client.PatientDob.Value;
                server.dobSpecified = true;
            }
            server.requestorTypeName = client.RequestorTypeName;
            server.patientRequestor = client.IsPatientRequestor;
            return server;
        }

       
        /// <summary>
        /// Convert client requestor detail to server requestor detail.
        /// </summary>
        /// <param name="clientRequestor"></param>
        /// <returns></returns>
        private static Requestor MapModel(RequestorDetails client)
        {
            Requestor server = new Requestor();            

            server.id                 = client.Id;

            server.lastName           = (client.IsPatientRequestor) ? client.LastName : client.Name;
            server.firstName          = client.FirstName;
            server.type               = client.Type;
            server.requestorType      = client.TypeName;
            server.prePayRequired     = client.PrepaymentRequired;
            server.certLetterRequired = client.LetterRequired;
            server.homePhone          = client.HomePhone;
            server.workPhone          = client.WorkPhone;
            server.cellPhone          = client.CellPhone;
            server.email              = client.Email;
            server.fax                = client.Fax;
            server.contactName        = client.ContactName;
            server.contactPhone       = client.ContactPhone;
            server.contactEmail       = client.ContactEmail;
            if (client.PatientDob.HasValue)
            {
                server.dob          = client.PatientDob.Value;
                server.dobSpecified = true;
            }
            //Added for sales tax
            server.salesTax = client.HasSalesTax ? "Y" : "N";

            server.epn                       = client.PatientEPN;
            server.ssn                       = client.PatientSSN;
            server.mrn                       = client.PatientMRN;
            server.facility                  = client.PatientFacilityCode;
            server.freeFormFacility          = client.IsFreeformFacility;
            server.active                    = client.IsActive;
            server.recordVersion             = client.RecordVersionId;

            server.mainAddress = MapModel(client.MainAddress);
            server.altAddress = MapModel(client.AltAddress);

            return server;
        }


        /// <summary>
        /// Convert Server requestor detail to Client requestor detail.
        /// </summary>
        /// <param name="clientRequestor"></param>
        /// <returns></returns>
        public static RequestorDetails MapModel(Requestor server)
        {
            RequestorDetails client = new RequestorDetails();

            client.Id                 = server.id;
            client.Type               = server.type;

            if (client.IsPatientRequestor)
            {
                client.LastName = server.lastName;
                client.FirstName = server.firstName;
                client.Name = client.LastName + ", " + client.FirstName;
            }
            else
            {
                client.Name = server.lastName;
            }

            client.PrepaymentRequired = server.prePayRequired;
            client.LetterRequired     = server.certLetterRequired;
            client.HomePhone          = server.homePhone;
            client.WorkPhone          = server.workPhone;
            client.CellPhone          = server.cellPhone;
            client.Email              = server.email;
            client.Fax                = server.fax;
            client.ContactName        = server.contactName;
            client.ContactPhone       = server.contactPhone;
            client.ContactEmail       = server.contactEmail;
            if (server.dob.HasValue)
            {
                client.PatientDob = Convert.ToDateTime(server.dob.Value.ToString(), CultureInfo.InvariantCulture).Date;
            }
            client.TypeName           = server.requestorType;

            if (UserData.Instance.EpnEnabled)
            {
                client.PatientEPN = server.epn;
            }

            client.PatientSSN         = server.ssn;
            client.PatientMRN         = server.mrn;
            client.PatientFacilityCode= server.facility;
            client.IsFreeformFacility = server.freeFormFacility;
            client.IsAssociated       = server.associated;
            client.IsActive           = server.active; 
            client.RecordVersionId    = server.recordVersion;
            client.IsAssociated       = server.associated;
            //Added for sales tax
            client.HasSalesTax        = (!string.IsNullOrEmpty(server.salesTax)) ? 
                                         server.salesTax.ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals("Y") : false;

            client.MainAddress = MapModel(server.mainAddress);
            client.AltAddress  = MapModel(server.altAddress);
           
            return client;
        }

        /// <summary>
        /// Converts server model into client.
        /// </summary>
        /// <param name="documentInfo"></param>
        /// <returns></returns>
        private static DocumentInfo MapModel(DocInfo documentInfo)
        {
            DocumentInfo clientDocumentInfo = new DocumentInfo();

            clientDocumentInfo.Id = documentInfo.id;
            clientDocumentInfo.Name = documentInfo.name;
            clientDocumentInfo.Type = documentInfo.type;

            return clientDocumentInfo;
        }

        private static SearchCriteria CreatePatientSearchCriteria(RequestorDetails requestor)
        {  

            SearchCriteria supplementalSearchCriteria = new SearchCriteria();            

            List<SearchCondition> searchConditions = new List<SearchCondition>();
            if (!string.IsNullOrEmpty(requestor.LastName))
            {
                searchConditions.Add(PatientController.CreateSearchCondition(PatientDetails.LastNameKey, requestor.LastName, 
                                     Condition.Equal.ToString()));
            }

            if (!string.IsNullOrEmpty(requestor.FirstName))
            {
                searchConditions.Add(PatientController.CreateSearchCondition(PatientDetails.FirstNameKey, requestor.FirstName,
                                     Condition.Equal.ToString()));
            }

            if (!string.IsNullOrEmpty(requestor.PatientEPN))
            {
                searchConditions.Add(PatientController.CreateSearchCondition(PatientDetails.EPNKey, requestor.PatientEPN, 
                                     Condition.Equal.ToString()));
            }

            if (!string.IsNullOrEmpty(requestor.PatientSSN))
            {
                searchConditions.Add(PatientController.CreateSearchCondition(PatientDetails.SSNKey, requestor.PatientSSN.Trim('-', ' '),
                                     Condition.Equal.ToString()));
            }

            if (!string.IsNullOrEmpty(requestor.PatientMRN))
            {
                searchConditions.Add(PatientController.CreateSearchCondition(PatientDetails.MRNKey, requestor.PatientMRN, 
                                     Condition.Equal.ToString()));
            }

            if (!string.IsNullOrEmpty(requestor.PatientFacilityCode))
            {
                searchConditions.Add(PatientController.CreateSearchCondition(PatientDetails.FacilityKey, requestor.PatientFacilityCode,
                                     Condition.Equal.ToString()));
            }

            if (requestor.PatientDob.HasValue)
            {   
                searchConditions.Add(PatientController.CreateSearchCondition(PatientDetails.DOBKey,
                                       requestor.PatientDob.Value.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture),
                                       Condition.Equal.ToString()));
            }

            supplementalSearchCriteria.conditions = searchConditions.ToArray();
            supplementalSearchCriteria.maxCount = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], 
                                                                  System.Threading.Thread.CurrentThread.CurrentUICulture);

            return supplementalSearchCriteria;            
        }

        /// <summary>
        /// converts server to client model
        /// </summary>
        /// <param name="documentInfos"></param>
        /// <returns></returns>
        private static DocumentInfoList MapModel(DocInfoList documentInfos)
        {
            DocumentInfoList documentInfoList = new DocumentInfoList();
            documentInfoList.Name = documentInfos.name;
            foreach (DocInfo docInfo in documentInfos.docInfos)
            {
                documentInfoList.DocumentInfoCollection.Add(MapModel(docInfo));
            }
            return documentInfoList;
        }

        /// <summary>
        /// converts the client to server model
        /// </summary>
        /// <param name="requestorStatementDetail"></param>
        /// <returns></returns>
        private static RequestorStatementCriteria MapModel(RequestorStatementDetail requestorStatementDetail)
        {
            RequestorStatementCriteria requestorStatementCriteria = new RequestorStatementCriteria();
            requestorStatementCriteria.requestorId = requestorStatementDetail.RequestorId;
            requestorStatementCriteria.templateFileId = requestorStatementDetail.TemplateFileId;
            requestorStatementCriteria.templateName = requestorStatementDetail.TemplateName;
            requestorStatementCriteria.outputMethod = requestorStatementDetail.OutputMethod;
            requestorStatementCriteria.queuePassword = requestorStatementDetail.QueueSecretWord;
            if (requestorStatementDetail.DateRange != McK.EIG.ROI.Client.Requestors.Model.DateRange.None)
            {
                requestorStatementCriteria.dateRange = (McK.EIG.ROI.Client.Web_References.ROIRequestorWS.DateRange)Enum.Parse(typeof(McK.EIG.ROI.Client.Web_References.ROIRequestorWS.DateRange), requestorStatementDetail.DateRange.ToString());
            }
            requestorStatementCriteria.notes = requestorStatementDetail.Notes;
            requestorStatementCriteria.pastInvIds = requestorStatementDetail.PastInvIds;

            return requestorStatementCriteria;
        }

        /// <summary>
        /// Converts the server model into client.
        /// </summary>
        /// <param name="pastInvoices"></param>
        /// <returns></returns>
        private static Collection<PastInvoiceDetails> MapModel(PastInvoice[] pastInvoices)
        {
            Collection<PastInvoiceDetails> pastInvoiceList = new Collection<PastInvoiceDetails>();
            PastInvoiceDetails pastInvoiceDetails;
            foreach (PastInvoice pastInvoice in pastInvoices)
            {
                pastInvoiceDetails = new PastInvoiceDetails();
                pastInvoiceDetails.InvoiceId = pastInvoice.invoiceId;
                pastInvoiceDetails.CreatedDate = null;
                if (!string.IsNullOrEmpty(pastInvoice.createdDate))
                {
                    pastInvoiceDetails.CreatedDate = Convert.ToDateTime(pastInvoice.createdDate, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                pastInvoiceDetails.Amount = pastInvoice.amount;
                pastInvoiceList.Add(pastInvoiceDetails);
            }
            return pastInvoiceList;
        }

        private static RequestorRefund MapModel(RequestorRefundDetail requestorRefundDetail, bool isOutputRefundLetter)
        {
            RequestorRefund requestorRefund = new RequestorRefund();
            requestorRefund.requestorId = requestorRefundDetail.RequestorID;
            requestorRefund.requestorName = requestorRefundDetail.RequestorName;
            requestorRefund.requestorType = requestorRefundDetail.RequestorType;
            requestorRefund.refundAmount = requestorRefundDetail.RefundAmount;
            requestorRefund.note = requestorRefundDetail.Note;
            requestorRefund.refundDate = requestorRefundDetail.RefundDate;
            if (isOutputRefundLetter)
            {
                requestorRefund.notes = requestorRefundDetail.Notes;
                requestorRefund.templateId = requestorRefundDetail.TemplateId;
                requestorRefund.templateName = requestorRefundDetail.TemplateName;
                requestorRefund.outputMethod = requestorRefundDetail.OutputMethod;
                requestorRefund.queuePassword = requestorRefundDetail.QueueSecureWord;
            
                if (requestorRefundDetail.RequestorStatementDetail != null)
                {
                    requestorRefund.statementCriteria = new RequestorStatementCriteria();
                    requestorRefund.statementCriteria.requestorId = requestorRefundDetail.RequestorStatementDetail.RequestorId;
                    requestorRefund.statementCriteria.templateFileId = requestorRefundDetail.RequestorStatementDetail.TemplateFileId;
                    requestorRefund.statementCriteria.templateName = requestorRefundDetail.RequestorStatementDetail.TemplateName;
                    requestorRefund.statementCriteria.outputMethod = requestorRefundDetail.RequestorStatementDetail.OutputMethod;
                    requestorRefund.statementCriteria.queuePassword = requestorRefundDetail.RequestorStatementDetail.QueueSecretWord;
                    if (requestorRefundDetail.RequestorStatementDetail.DateRange != McK.EIG.ROI.Client.Requestors.Model.DateRange.None)
                    {
                        requestorRefund.statementCriteria.dateRange = (McK.EIG.ROI.Client.Web_References.ROIRequestorWS.DateRange)Enum.Parse(typeof(McK.EIG.ROI.Client.Web_References.ROIRequestorWS.DateRange), requestorRefundDetail.RequestorStatementDetail.DateRange.ToString());
                    }
                    requestorRefund.statementCriteria.notes = requestorRefundDetail.RequestorStatementDetail.Notes;
                }

            }
            return requestorRefund;
        }

        #endregion

        #endregion

        #region Properties

        private static object syncRoot = new Object();
        private static volatile RequestorController requestorController;

        /// <summary>
        /// Get the RequestorController Instance
        /// </summary>
        public new static RequestorController Instance
        {
            get
            {
                if (requestorController == null)
                {
                    lock (syncRoot)
                    {
                        if (requestorController == null)
                        {
                            requestorController = new RequestorController();
                        }
                    }
                }

                return requestorController;
            }
        }

        #endregion
        
    }
}
