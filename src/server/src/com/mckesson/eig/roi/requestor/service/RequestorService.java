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

package com.mckesson.eig.roi.requestor.service;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.ObjectFactory;

import com.mckesson.eig.roi.base.model.MatchCriteriaList;
import com.mckesson.eig.roi.billing.model.DocInfo;
import com.mckesson.eig.roi.billing.model.DocInfoList;
import com.mckesson.eig.roi.billing.model.PastInvoiceList;
import com.mckesson.eig.roi.billing.model.RegeneratedInvoiceInfo;
import com.mckesson.eig.roi.billing.model.RequestorLetterHistory;
import com.mckesson.eig.roi.billing.model.RequestorLetterHistoryList;
import com.mckesson.eig.roi.requestor.model.AdjustmentInfo;
import com.mckesson.eig.roi.requestor.model.Requestor;
import com.mckesson.eig.roi.requestor.model.RequestorHistoryList;
import com.mckesson.eig.roi.requestor.model.RequestorInvoicesList;
import com.mckesson.eig.roi.requestor.model.RequestorPaymentList;
import com.mckesson.eig.roi.requestor.model.RequestorRefund;
import com.mckesson.eig.roi.requestor.model.RequestorSearchCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorSearchResult;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorUnappliedAmountDetailsList;


/**
 * This is the Requestor services to perform all Requestor operations.
 *
 * @author ranjithr
 * @date   Jun 24, 2008
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */

@WebService(targetNamespace = "urn:eig.mckesson.com", name = "RequestorService")
@XmlSeeAlso({org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0.ObjectFactory.class, org.w3._2000._09.xmldsig_.ObjectFactory.class, org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.ObjectFactory.class, ObjectFactory.class})
public interface RequestorService {

    @WebMethod(action = "urn:eig.mckesson.com/viewRequestorLetter")
    @RequestWrapper(localName = "viewRequestorLetter", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.ViewRequestorLetter")
    @ResponseWrapper(localName = "viewRequestorLetterResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.ViewRequestorLetterResponse")
    @WebResult(name = "docInfo", targetNamespace = "urn:eig.mckesson.com")
    public DocInfo viewRequestorLetter(
        @WebParam(name = "requestorLetterId", targetNamespace = "urn:eig.mckesson.com")
        long requestorLetterId,
        @WebParam(name = "docType", targetNamespace = "urn:eig.mckesson.com")
        String docType
    );

    @WebMethod(action = "urn:eig.mckesson.com/retrieveRequestorPastInvoices")
    @RequestWrapper(localName = "retrieveRequestorPastInvoices", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.RetrieveRequestorPastInvoices")
    @ResponseWrapper(localName = "retrieveRequestorPastInvoicesResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.RetrieveRequestorPastInvoicesResponse")
    @WebResult(name = "PastInvoiceList", targetNamespace = "urn:eig.mckesson.com")
    public PastInvoiceList retrieveRequestorPastInvoices(
        @WebParam(name = "requestorId", targetNamespace = "urn:eig.mckesson.com")
        long requestorId
    );

    @WebMethod(action = "urn:eig.mckesson.com/checkDuplicateRequestorName")
    @RequestWrapper(localName = "checkDuplicateRequestorName", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.CheckDuplicateRequestorName")
    @ResponseWrapper(localName = "checkDuplicateRequestorNameResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.CheckDuplicateRequestorNameResponse")
    @WebResult(name = "available", targetNamespace = "urn:eig.mckesson.com")
    public boolean checkDuplicateRequestorName(
        @WebParam(name = "id", targetNamespace = "urn:eig.mckesson.com")
        long id,
        @WebParam(name = "lastName", targetNamespace = "urn:eig.mckesson.com")
        String lastName
    );

    @WebMethod(action = "urn:eig.mckesson.com/viewRequestorDetails")
    @RequestWrapper(localName = "viewRequestorDetails", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.ViewRequestorDetails")
    @ResponseWrapper(localName = "viewRequestorDetailsResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.ViewRequestorDetailsResponse")
    @WebResult(name = "docInfo", targetNamespace = "urn:eig.mckesson.com")
    public DocInfo viewRequestorDetails(
        @WebParam(name = "invoiceId", targetNamespace = "urn:eig.mckesson.com")
        long invoiceId,
        @WebParam(name = "requestId", targetNamespace = "urn:eig.mckesson.com")
        long requestId,
        @WebParam(name = "docType", targetNamespace = "urn:eig.mckesson.com")
        String docType,
        @WebParam(name = "retrieverType", targetNamespace = "urn:eig.mckesson.com")
        String retrieverType,
        @WebParam(name = "letterType", targetNamespace = "urn:eig.mckesson.com")
        String letterType
    );

    @WebMethod(action = "urn:eig.mckesson.com/deleteRequestorPayment")
    @RequestWrapper(localName = "deleteRequestorPayment", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.DeleteRequestorPayment")
    @ResponseWrapper(localName = "deleteRequestorPaymentResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.DeleteRequestorPaymentResponse")
    public void deleteRequestorPayment(
        @WebParam(name = "paymentId", targetNamespace = "urn:eig.mckesson.com")
        long paymentId,
        @WebParam(name = "requestorName", targetNamespace = "urn:eig.mckesson.com")
        String requestorName
    );

    @WebMethod(action = "urn:eig.mckesson.com/retrieveUnappliedAmountDetails")
    @RequestWrapper(localName = "retrieveUnappliedAmountDetails", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.RetrieveUnappliedAmountDetails")
    @ResponseWrapper(localName = "retrieveUnappliedAmountDetailsResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.RetrieveUnappliedAmountDetailsResponse")
    @WebResult(name = "requestorUnappliedAmountDetailsList", targetNamespace = "urn:eig.mckesson.com")
    public RequestorUnappliedAmountDetailsList retrieveUnappliedAmountDetails(
        @WebParam(name = "requestId", targetNamespace = "urn:eig.mckesson.com")
        long requestId
    );

    @WebMethod(action = "urn:eig.mckesson.com/createRequestor")
    @RequestWrapper(localName = "createRequestor", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.CreateRequestor")
    @ResponseWrapper(localName = "createRequestorResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.CreateRequestorResponse")
    @WebResult(name = "requestorId", targetNamespace = "urn:eig.mckesson.com")
    public long createRequestor(
        @WebParam(name = "requestor", targetNamespace = "urn:eig.mckesson.com")
        Requestor requestor
    );

    @WebMethod(action = "urn:eig.mckesson.com/updateRequestor")
    @RequestWrapper(localName = "updateRequestor", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.UpdateRequestor")
    @ResponseWrapper(localName = "updateRequestorResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.UpdateRequestorResponse")
    @WebResult(name = "requestor", targetNamespace = "urn:eig.mckesson.com")
    public Requestor updateRequestor(
        @WebParam(name = "requestor", targetNamespace = "urn:eig.mckesson.com")
        Requestor requestor
    );

    @WebMethod(action = "urn:eig.mckesson.com/createRequestorPayment")
    @RequestWrapper(localName = "createRequestorPayment", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.CreateRequestorPayment")
    @ResponseWrapper(localName = "createRequestorPaymentResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.CreateRequestorPaymentResponse")
    public void createRequestorPayment(
        @WebParam(name = "requestorPaymentList", targetNamespace = "urn:eig.mckesson.com")
        RequestorPaymentList requestorPaymentList
    );

    @WebMethod(action = "urn:eig.mckesson.com/retrieveAdjustmentInfo")
    @RequestWrapper(localName = "retrieveAdjustmentInfo", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.RetrieveAdjustmentInfo")
    @ResponseWrapper(localName = "retrieveAdjustmentInfoResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.RetrieveAdjustmentInfoResponse")
    @WebResult(name = "adjustmentInfo", targetNamespace = "urn:eig.mckesson.com")
    public AdjustmentInfo retrieveAdjustmentInfo(
        @WebParam(name = "requestorId", targetNamespace = "urn:eig.mckesson.com")
        long requestorId
    );

    @WebMethod(action = "urn:eig.mckesson.com/retrieveRequestorLetterHistory")
    @RequestWrapper(localName = "retrieveRequestorLetterHistory", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.RetrieveRequestorLetterHistory")
    @ResponseWrapper(localName = "retrieveRequestorLetterHistoryResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.RetrieveRequestorLetterHistoryResponse")
    @WebResult(name = "requestorLetterHistoryList", targetNamespace = "urn:eig.mckesson.com")
    public RequestorLetterHistoryList retrieveRequestorLetterHistory(
        @WebParam(name = "requestorId", targetNamespace = "urn:eig.mckesson.com")
        long requestorId
    );

    @WebMethod(action = "urn:eig.mckesson.com/saveAdjustmentInfo")
    @RequestWrapper(localName = "saveAdjustmentInfo", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.SaveAdjustmentInfo")
    @ResponseWrapper(localName = "saveAdjustmentInfoResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.SaveAdjustmentInfoResponse")
    public void saveAdjustmentInfo(
        @WebParam(name = "adjustmentInfo", targetNamespace = "urn:eig.mckesson.com")
        AdjustmentInfo adjustmentInfo
    );

    @WebMethod(action = "urn:eig.mckesson.com/findRequestor")
    @RequestWrapper(localName = "findRequestor", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.FindRequestor")
    @ResponseWrapper(localName = "findRequestorResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.FindRequestorResponse")
    @WebResult(name = "requestorSearchResult", targetNamespace = "urn:eig.mckesson.com")
    public RequestorSearchResult findRequestor(
        @WebParam(name = "requestorSearchCriteria", targetNamespace = "urn:eig.mckesson.com")
        RequestorSearchCriteria requestorSearchCriteria
    );

    @WebMethod(action = "urn:eig.mckesson.com/retrieveRequestorInvoices")
    @RequestWrapper(localName = "retrieveRequestorInvoices", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.RetrieveRequestorInvoices")
    @ResponseWrapper(localName = "retrieveRequestorInvoicesResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.RetrieveRequestorInvoicesResponse")
    @WebResult(name = "requestorInvoicesList", targetNamespace = "urn:eig.mckesson.com")
    public RequestorInvoicesList retrieveRequestorInvoices(
        @WebParam(name = "requestorId", targetNamespace = "urn:eig.mckesson.com")
        long requestorId
    );

    @WebMethod(action = "urn:eig.mckesson.com/searchMatchingRequestors")
    @RequestWrapper(localName = "searchMatchingRequestors", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.SearchMatchingRequestors")
    @ResponseWrapper(localName = "searchMatchingRequestorsResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.SearchMatchingRequestorsResponse")
    @WebResult(name = "requestorSearchResult", targetNamespace = "urn:eig.mckesson.com")
    public RequestorSearchResult searchMatchingRequestors(
        @WebParam(name = "matchCriteriaList", targetNamespace = "urn:eig.mckesson.com")
        MatchCriteriaList matchCriteriaList
    );

    @WebMethod(action = "urn:eig.mckesson.com/generateRequestorStatement")
    @RequestWrapper(localName = "generateRequestorStatement", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.GenerateRequestorStatement")
    @ResponseWrapper(localName = "generateRequestorStatementResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.GenerateRequestorStatementResponse")
    @WebResult(name = "docInfoList", targetNamespace = "urn:eig.mckesson.com")
    public DocInfoList generateRequestorStatement(
        @WebParam(name = "RequestorStatementCriteria", targetNamespace = "urn:eig.mckesson.com")
        RequestorStatementCriteria requestorStatementCriteria
    );

    @WebMethod(action = "urn:eig.mckesson.com/retrieveAdjustmentInfoByAdjustmentId")
    @RequestWrapper(localName = "retrieveAdjustmentInfoByAdjustmentId", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.RetrieveAdjustmentInfoByAdjustmentId")
    @ResponseWrapper(localName = "retrieveAdjustmentInfoByAdjustmentIdResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.RetrieveAdjustmentInfoByAdjustmentIdResponse")
    @WebResult(name = "adjustmentInfo", targetNamespace = "urn:eig.mckesson.com")
    public AdjustmentInfo retrieveAdjustmentInfoByAdjustmentId(
        @WebParam(name = "adjustmentId", targetNamespace = "urn:eig.mckesson.com")
        long adjustmentId,
        @WebParam(name = "requestorId", targetNamespace = "urn:eig.mckesson.com")
        long requestorId
    );

    @WebMethod(action = "urn:eig.mckesson.com/createRequestorLetterEntry")
    @RequestWrapper(localName = "createRequestorLetterEntry", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.CreateRequestorLetterEntry")
    @ResponseWrapper(localName = "createRequestorLetterEntryResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.CreateRequestorLetterEntryResponse")
    @WebResult(name = "requestorLetterHistory", targetNamespace = "urn:eig.mckesson.com")
    public RequestorLetterHistory createRequestorLetterEntry(
        @WebParam(name = "regeneratedInvoiceInfo", targetNamespace = "urn:eig.mckesson.com")
        RegeneratedInvoiceInfo regeneratedInvoiceInfo
    );

    @WebMethod(action = "urn:eig.mckesson.com/deleteRequestor")
    @RequestWrapper(localName = "deleteRequestor", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.DeleteRequestor")
    @ResponseWrapper(localName = "deleteRequestorResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.DeleteRequestorResponse")
    public void deleteRequestor(
        @WebParam(name = "requestorId", targetNamespace = "urn:eig.mckesson.com")
        long requestorId
    );

    @WebMethod(action = "urn:eig.mckesson.com/retrieveRequestorSummaries")
    @RequestWrapper(localName = "retrieveRequestorSummaries", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.RetrieveRequestorSummaries")
    @ResponseWrapper(localName = "retrieveRequestorSummariesResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.RetrieveRequestorSummariesResponse")
    @WebResult(name = "requestorHistoryList", targetNamespace = "urn:eig.mckesson.com")
    public RequestorHistoryList retrieveRequestorSummaries(
        @WebParam(name = "requestorId", targetNamespace = "urn:eig.mckesson.com")
        long requestorId
    );

    @WebMethod(action = "urn:eig.mckesson.com/createRequestorStatement")
    @RequestWrapper(localName = "createRequestorStatement", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.CreateRequestorStatement")
    @ResponseWrapper(localName = "createRequestorStatementResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.CreateRequestorStatementResponse")
    @WebResult(name = "requestorStatementId", targetNamespace = "urn:eig.mckesson.com")
    public long createRequestorStatement(
        @WebParam(name = "RequestorStatementCriteria", targetNamespace = "urn:eig.mckesson.com")
        RequestorStatementCriteria requestorStatementCriteria
    );

    @WebMethod(action = "urn:eig.mckesson.com/updateRequestorPayment")
    @RequestWrapper(localName = "updateRequestorPayment", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.UpdateRequestorPayment")
    @ResponseWrapper(localName = "updateRequestorPaymentResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.UpdateRequestorPaymentResponse")
    public void updateRequestorPayment(
        @WebParam(name = "requestorPaymentList", targetNamespace = "urn:eig.mckesson.com")
        RequestorPaymentList requestorPaymentList
    );

    @WebMethod(action = "urn:eig.mckesson.com/createRequestorRefund")
    @RequestWrapper(localName = "createRequestorRefund", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.CreateRequestorRefund")
    @ResponseWrapper(localName = "createRequestorRefundResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.CreateRequestorRefundResponse")
    @WebResult(name = "docInfoList", targetNamespace = "urn:eig.mckesson.com")
    public DocInfoList createRequestorRefund(
        @WebParam(name = "requestorRefund", targetNamespace = "urn:eig.mckesson.com")
        RequestorRefund requestorRefund
    );

    @WebMethod(action = "urn:eig.mckesson.com/retrieveRequestor")
    @RequestWrapper(localName = "retrieveRequestor", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.RetrieveRequestor")
    @ResponseWrapper(localName = "retrieveRequestorResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.RetrieveRequestorResponse")
    @WebResult(name = "requestor", targetNamespace = "urn:eig.mckesson.com")
    public Requestor retrieveRequestor(
        @WebParam(name = "requestorId", targetNamespace = "urn:eig.mckesson.com")
        long requestorId,
        @WebParam(name = "isSearchRetrieve", targetNamespace = "urn:eig.mckesson.com")
        boolean isSearchRetrieve
    );

    @WebMethod(action = "urn:eig.mckesson.com/viewRequestorRefund")
    @RequestWrapper(localName = "viewRequestorRefund", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper.ViewRequestorRefund")
    @ResponseWrapper(localName = "viewRequestorRefundResponse", targetNamespace = "urn:eig.mckesson.com", className = "com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper.ViewRequestorRefundResponse")
    @WebResult(name = "docInfoList", targetNamespace = "urn:eig.mckesson.com")
    public DocInfoList viewRequestorRefund(
        @WebParam(name = "requestorRefund", targetNamespace = "urn:eig.mckesson.com")
        RequestorRefund requestorRefund
    );
}
