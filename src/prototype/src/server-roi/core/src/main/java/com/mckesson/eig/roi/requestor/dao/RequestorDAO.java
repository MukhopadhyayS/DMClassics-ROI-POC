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

package com.mckesson.eig.roi.requestor.dao;


import java.sql.Timestamp;
import java.util.List;

import com.mckesson.eig.roi.admin.dao.RequestorTypeDAO;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.base.model.MatchCriteriaList;
import com.mckesson.eig.roi.billing.model.RequestorLetterHistory;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.requestor.model.AdjustmentInfo;
import com.mckesson.eig.roi.requestor.model.Requestor;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustment;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustmentsPayments;
import com.mckesson.eig.roi.requestor.model.RequestorCharges;
import com.mckesson.eig.roi.requestor.model.RequestorHistoryList;
import com.mckesson.eig.roi.requestor.model.RequestorInvoice;
import com.mckesson.eig.roi.requestor.model.RequestorInvoicesList;
import com.mckesson.eig.roi.requestor.model.RequestorPayment;
import com.mckesson.eig.roi.requestor.model.RequestorPaymentList;
import com.mckesson.eig.roi.requestor.model.RequestorPrebillsList;
import com.mckesson.eig.roi.requestor.model.RequestorRefund;
import com.mckesson.eig.roi.requestor.model.RequestorSearchCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorUnappliedAmountDetailsList;


/**
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface RequestorDAO
extends ROIDAO {

    /**
     * This method searches the requestors based on the given search criteria
     * @param searchCriteria search criteria to be validated
     * @return List of requestors
     */
    List <Requestor> findRequestor(final RequestorSearchCriteria searchCriteria);

    /**
     * This method is to create a new requestor
     * @param requestor Reqestor details to be created
     * @return Id of the requestor
     */

    long createRequestor(Requestor requestor);

    /**
     * This method retrieves requestor based on the id
     * @param requestorId id of the requestor to be retrieved
     * @return Requestor requestor details
     */
    Requestor retrieveRequestor(long requestorId);

    /**
     * This method is to get the number of requet which is associated to the specified requestor
     * @param id Requestor id
     * @return number of request associated to requestor
     */
    long getAssociatedRequestCount(long id);

    /**
     * This method is to delete the requestor
     * @param requestorId Requestor id
     * @return Requestor details
     */
    Requestor deleteRequestor(long requestorId);

    /**
     * This method is to update the requestor
     * @param requestor Requestor details
     * @return Updated requestor detail
     */
    Requestor updateRequestor(Requestor requestor);

    /**
     * This method is to retrieve requestor by the requestor name
     * @param name Requestor name
     * @return List of requestor ids
     */
    List <Long> retrieveRequestorIdsByName(String name);

    List <Requestor> getMatchingRequestors(MatchCriteriaList list,
                                                           RequestorTypeDAO rtDAO);
    /**
     * This method retrieves the address types from data base and performs caching
     * @param type -type of address to be processed.
     * @return id of the address type.
     */
    long getAddressTypeId(String type);

    /**
     * This method retrieves the email phone types from data base and performs caching
     * @param type -type of email phone to be processed.
     * @return id of the email phone type.
     */
    long getEmailPhoneTypeId(String type);

    /**
     * This method retrieves the contact types from data base and performs caching
     * @param type -type of contact to be processed.
     * @return id of the contact type.
     */
    long getContactTypeId(String type);

    /**
     * This method retrieves the requestor summary letter history from the database
     * @param requestorId
     * @return List<Object[]>
     */
    List<RequestorLetterHistory> retrieveRequestorLetterHistory(long requstorId);

    /**
     * This method retrieves the Invoices associated to the requestor
     * @param requestorId
     */
    RequestorInvoicesList retrieveRequestorInvoices(long requestorId);

    /**
     * This method is used to retrieve requestor's adjustment info
     * @param requestorId
     * @return
     *//*
    RequestorAdjustmentsFeeList retrieveRequestorAdjustmentFee(long requestorId);*/

    /**
    * This method retrieves only the Invoices associated to the requestor
    * @param requestorId
    * @param adjustmentId
    */
    List<RequestorInvoice> retrieveOnlyRequestorInvoices(long requestorId, long adjustmentId);


    /**This method is used to save requestor's adjustment info
     * @param requestorAdjustment
     * @return id
     */
    long saveAdjustmentInfo(RequestorAdjustment requestorAdjustment);

   /**
    * This method is used to save the details into the mapping table
    * @param adjustmentInfo
    * @param amount
    * @param invoiceId
    * @return adjustmentToInvoiceId
    */
   long saveDeliveryChargesMapping(AdjustmentInfo adjustmentInfo, double amount, long invoiceId);

   /**
    * This method is used to save requestor's adjustment fee info
    * @param adjustmentInfo
    * @param feeName
    * @param feeType
    * @param amount
    * @param salesTaxAmount
    * @param isTaxable
    */
   /* void createAdjustmentFeeInfo(AdjustmentInfo adjustmentInfo, String feeName,
            String feeType, double amount, double salesTaxAmount,
            boolean isTaxable);*/

   /**
    * This method will create RequestorPayment
    * @param paymentInfo
    * @return retrieved requestId
    */
   long createRequestorPayment(RequestorPaymentList paymentInfo);

   /**
    * This method will update RequestorPayment
    * @param paymentInfo
    * @return retrieved requestId
    */
   long updateRequestorPayment(RequestorPaymentList paymentInfo);

   /**
    * This method will retrieve RequestCoreDeliveryChargestoRequestorPayment.
    * @param paymentId
    */
   List<RequestorPayment> retrieveInvoiceToPayment(long paymentId);

   /**
    * This method will update RequestCoreDeliveryCharges
    * @param paymentInfo
    */
   void updateRequestorInvoice(List<RequestorPayment> paymentList);

    /**
     * This method update the requestor adjustment
     * @param requestorAdjustment
     * @param date
     * @param user
     *
     */
    void updateRequestorAdjustment(RequestorAdjustment requestorAdjustment);

    /**
     * This method will all deletes all the records in
     * RequestCoreDeliveryChargestoRequestorAdjustment table by adjustmentId.
     * @param paymentId
     */
    void deleteMappedInvoicesByAdjustmentAndInvoiceId(long adjustmentId, long invoiceId);

    /**
     * Retrieves the invoices for the given adjustmentId Id
     *
     * @param adjustmentId
     * @return list of invoice Ids
     */
    List<RequestorInvoice> retrieveInvoiceIdsForAdjustments(long adjustmentId);

    /**
     * This method will load the Requestor History
     * @param requestorId
     * @return RequestorHistory
     */
    public RequestorHistoryList retrieveRequestorSummaries(long requestorId);

    /**
     * This method is used to retrieve the unapplied amount details for a particular requestor
     * @param requestId
     * @return RequestorUnappliedAmountDetailsList
     */
    public RequestorUnappliedAmountDetailsList retrieveUnappliedAmountDetails(long requestId);

    public List<RequestorAdjustmentsPayments> retrieveRequestorPaymentDetails(long requestId);

    public List<RequestorAdjustment> retrieveRequestorAdjustmentDetails(long requestId);

    /**
     * This method will update RequestorPayment.
     * @param requestorPayId
     * @return paymentId
     */
    public void updateRequestorPaymentDetails(long requestorPayId,double unappliedAmt,
            Timestamp date,User user);

    /**
     * This method will update RequestorAdjustment.
     * @param requestorPayId
     * @return paymentId
     */
    public void updateRequestorAdjustmentDetails(long requestorAdjId,double appliedAmt,double unappliedAmt,
            Timestamp date,User user);

    /**
     * This method retrieves all invoice balance for the given requestor
     * @param requestorId
     * @return RequestorCharges
     */
    RequestorCharges retrieveRequestorCharges(long requestorId);

    /**
     * This method inserts all refund details to the table
     * @param refundDetails
     * @return
     */
    long createRequestorRefund(RequestorRefund refundDetails);


    /**
     * updates the requestor payments details as batch process
     * @param paymentList
     */
    void updateRequestorPayment(List<RequestorPayment> paymentList, final boolean isRefund);

    /**
     * This method Retrieves Requestor payments detail
     * @param requestorId
     * List<RequestorPayment>
     */
    List<RequestorPayment> retrieveRequestorUnappliedPayments( long requestorId);

    /**
     * Method to retrieve the requestor's Adjustment and Payment for cancel request
     *
     * @param invoiceId
     * @return - List<RequestorAdjustmentsPayments>
     */
    List<RequestorAdjustmentsPayments> retrieveRequestorAdjAndPayDetailsForCancelReq(
            long invoiceId);

    /**
     * Retrieve requestor payment.
     * @param paymentId the requestor payment id
     * @return the requestor payment
     */
    RequestorPaymentList retrieveRequestorPayment(long paymentId);

    /**
     * Delete requestor payment.
     * @param paymentId
     */
    void deleteRequestorPayment(long paymentId);

    /**
     * Retrieve requestor adjustment.
     * @param adjustmentId the requestor adjustment id
     * @return the requestor adjustment
     */
    RequestorAdjustment retrieveRequestorAdjustment(long adjustmentId);

    /**
     * Delete requestor adjustment.
     * @param adjustmentId
     */
    void deleteRequestorAdjustment(long adjustmentId);

    /**
     * Retrieve mapped invoices by adjustment and invoice id.
     * @param adjustmentId
     * @param invoiceId
     * @return the adjToInvoice ids
     */
    List<Long> retrieveMappedInvoicesByAdjustmentAndInvoiceId(long adjustmentId, long invoiceId);

    /**
     * Retrieve mapped invoices by payment and invoice id.
     * @param paymentId
     * @param invoiceId
     * @return the payToInvoice ids
     */
    List<Long> retrieveMappedInvoicesByPaymentAndInvoiceId(long paymentId, long invoiceId);

    /**
     * This method is used to save the details into the mapping table
     * @param paymentInfo
     * @return paymentTOInvoiceId
     */
    long createInvoiceToPayment(RequestorPayment paymentInfo);

    /**
     * This method will deletes the records in RequestCoreDeliveryChargestoRequestorPayment
     *  table by paymentId and invoiceId.
     * @param paymentList
     */
    void deleteInvoiceToPayment(RequestorPayment paymentInfo);

	/**
     * retrieves the count of number of adjustment and payment for a given requestor
     * @param adjustmentId
     * @return count of number of adjustment & payment
     */
    long retrieveRequestorAdjPaymentCount(long requestorId);
    
    /**
     * This method retrieves the Prebills associated to the requestor
     * @param requestorId
     */
    RequestorPrebillsList retrieveRequestorPrebills(long requestorId);

 }
