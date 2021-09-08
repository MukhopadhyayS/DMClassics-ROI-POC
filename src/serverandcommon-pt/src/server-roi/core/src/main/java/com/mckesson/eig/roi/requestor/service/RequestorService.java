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
public interface RequestorService {

    /**
     * This method is to find the requestors based on the given search criteria
     * @param searchCriteria Requestor search criteria to be validated
     * @return RequestorSearchResult
     */
    RequestorSearchResult findRequestor(RequestorSearchCriteria searchCriteria);

    /**
     * This method creates new requestor
     * @param requestor Requestor to be created
     * @return id of the created requestor
     */
    long createRequestor(Requestor requestor);

    /**
     * This method retrieves requestor based on the requestorId
     * @param requestorId id of the requestor to be retrieved.
     * @param isSearchRetrieve
     * @return Requestor details
     */
    Requestor retrieveRequestor(long requestorId, boolean isSearchRetrieve);

    /**
     * This method updates the requestor
     * @param requestor Requestor to be updated
     * @return Updated requestor
     */

    Requestor updateRequestor(Requestor requestor);

    /**
     * This method deletes the requestor based on the requestorId
     * @param requestorId id of requestor to be deleted.
     */
    void deleteRequestor(long requestorId);

    /**
     * This method is to check the duplicate requestor name
     * @param id Requestor id
     * @param name Requestor name
     * @return true if the name is duplicate else false
     */
    boolean checkDuplicateRequestorName(long id, String name);

    /**
     * This method retrieves the matching requestors that matches with MatchCriteria
     * @param mcl MatchCriteria list
     * @return RequestorSearchResult
     */
    RequestorSearchResult searchMatchingRequestors(MatchCriteriaList mcl);

    /**
     * This method lists the letters for a requestor.
     * @param requestorId
     * @return RequestorLetterHistoryList
     */
    RequestorLetterHistoryList retrieveRequestorLetterHistory(long requestorId);

    /**
     * This service can be used in case of viewing Invoice Summary Letter History
     *
     * @param invoiceSummaryLetterId
     *            invoice id
     * @param docType
     * @return DocInfo details
     */
    DocInfo viewRequestorLetter(long invoiceSummaryLetterId, String docType);

    /**
     * This method update the requestor letter
     * @param regeneratedInvoiceInfo
     * @return RequestorLetterHistory
     */
    RequestorLetterHistory createRequestorLetterEntry(RegeneratedInvoiceInfo regInvoiceInfo);

    /**
     * This method retrieve the list of invoices for a requestor
     * @param requestorId
     * @return RequestorInvoicesList
     */
    RequestorInvoicesList retrieveRequestorInvoices(long requestorId);

    /**
     * This method is used to retrieve the AdjustmentInfo
     * @param requestorId
     * @return AdjustmentInfo
     */
    AdjustmentInfo retrieveAdjustmentInfo(long requestorId);

    /**
     * This method is used to insert AdjustmentInfo
     * @param adjustmentInfo
     * @return
     */
    void saveAdjustmentInfo(AdjustmentInfo adjustmentInfo);

    /**
     * This method creates an entries into RequestorPayment Table
     * and apply for invoice.
     * @param RequestorPaymentList paymentInfoList
     */
    void createRequestorPayment(RequestorPaymentList paymentInfoList);

    /**
     * This method updates the invoice and payment details.
     * @param RequestorPaymentList paymentInfoList
     */
    void updateRequestorPayment(RequestorPaymentList paymentInfoList);

    /**
     * This method retrieves all the fee information for a specific adjustment
     * @param adjustmentId
     * @param requestorId
     * @return AdjustmentInfo
     */
    AdjustmentInfo retrieveAdjustmentInfoByAdjustmentId(long adjustmentId, long requestorId);

    /**
     * Retrieves the list of all past invoices for the given requestor Id
     *
     * @param requestorId
     * @return list of past invoices
     */
    PastInvoiceList retrieveRequestorPastInvoices(long requestorId);

    /**
     * This method will load the Requestor History
     * @param requestorId
     * @return RequestorHistory
     */
    RequestorHistoryList retrieveRequestorSummaries(long requestorId);

    /**
     * Retrieves the list of all past invoices for the given requestor Id
     *
     * @param statementCriteria
     * @return DocInfoList
     */
    DocInfoList generateRequestorStatement(RequestorStatementCriteria statementCriteria);

    /**
     * creates requestor statements details entry in RequestorLetterCore table.
     * @param statementCriteria RequestorStatementCriteria object.
     */
    long createRequestorStatement(RequestorStatementCriteria statementCriteria);

    /**
     * This method creates an Invoice/PreBill/Letters with all the details persisted earlier
     * This service can be used in case of viewing Requestor History
     *
     * @param invoiceId
     * @param requestId
     * @param docType
     * @param retrieverType
     * @param letterType
     * @return DocInfo details
     */
    DocInfo viewRequestorDetails(long invoiceId, long requestId,
                                 String docType, String retrieverType,
                                 String letterType);

    /**
     * Refunds amount to the requestor
     * @param requestorRefund
     * @return
     */
    DocInfoList createRequestorRefund(RequestorRefund requestorRefund);

    /**
     * This methos is used to retrieve the unapplied amount details for a particular requestor
     * @param requestId
     * @return RequestorUnappliedAmountDetailsList
     */
    RequestorUnappliedAmountDetailsList retrieveUnappliedAmountDetails(long requestId);

    /**
     * This method view the requestor Refund details
     * @param requestorRefund
     * @return DocInfoList
     */
    DocInfoList viewRequestorRefund(RequestorRefund requestorRefund);
    
    /**
     * This method delete the RequestorPayment.
     * @param paymentId 
     */
    void deleteRequestorPayment(long paymentId, String requestorName);
    
}
