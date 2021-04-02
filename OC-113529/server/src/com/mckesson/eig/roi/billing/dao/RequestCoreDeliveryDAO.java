package com.mckesson.eig.roi.billing.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.billing.model.ChargeHistoryList;
import com.mckesson.eig.roi.billing.model.CoverLetterCore;
import com.mckesson.eig.roi.billing.model.InvoiceAndLetterOutputProperties;
import com.mckesson.eig.roi.billing.model.InvoiceHistory;
import com.mckesson.eig.roi.billing.model.InvoiceOrPrebillAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.PastInvoice;
import com.mckesson.eig.roi.billing.model.PostPaymentReportDetails;
import com.mckesson.eig.roi.billing.model.PrebillReportDetails;
import com.mckesson.eig.roi.billing.model.ReleasePages;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryCharges;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesAdjustmentPayment;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesDocument;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesFee;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesShipping;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryInvoicePatients;
import com.mckesson.eig.roi.billing.model.SalesTaxReason;
import com.mckesson.eig.roi.hpf.model.User;

public interface RequestCoreDeliveryDAO
extends ROIDAO {

    /**
     * This method will create RequestCoreDelivery
     * @param requestCoreId
     * @return long requestCoreDeliverySeq
     */
    long createRequestCoreDelivery(long requestCoreId);

    /**
     * @param requestCoreDeliveryId
     */
    void deleteRequestCoreDelivery(long requestCoreDeliveryId);

    /**
     * This method will create ROI_RequestCoreDeliverytoROI_Pages
     * @param requestCoreDeliveryId
     * @param roiPagesSeq
     */
    void createRequestCoreDeliveryToPages(long requestCoreDeliveryId, ReleasePages roiPagesSeq);

    /**
     * @param requestCoreDeliveryId
     */
    void deleteRequestCoreDeliveryToPages(long requestCoreDeliveryId);

    /**
     * @param requestCoreDeliveryId
     * @param supplementalAttachmentSeq
     */
    void createRequestCoreDeliveryToSupplementalAttachments(long requestCoreDeliveryId,
                                                            Long supplementalAttachmentSeq);

    /**
     * @param requestCoreDeliveryId
     */
    void deleteRequestCoreDeliveryToSupplementalAttachments(long requestCoreDeliveryId);

    /**
     * @param requestCoreDeliveryId
     * @param supplementalDocumentSeq
     */
    void createRequestCoreDeliveryToSupplementalDocuments(long requestCoreDeliveryId,
                                                          Long supplementalDocumentSeq);

    /**
     * @param requestCoreDeliveryId
     */
    void deleteRequestCoreDeliveryToSupplementalDocuments(long requestCoreDeliveryId);

    /**
     * @param requestCoreDeliveryId
     * @param supplementarityAttachmentSeq
     */
    void createRequestCoreDeliveryToSupplementarityAttachments(long requestCoreDeliveryId,
                                                               Long supplementarityAttachmentSeq);

    /**
     * @param requestCoreDeliveryId
     */
    void deleteRequestCoreDeliveryToSupplementarityAttachments(long requestCoreDeliveryId);

    /**
     * @param requestCoreDeliveryId
     * @param supplementarityDocumentSeq
     */
    void createRequestCoreDeliveryToSupplementarityDocuments(long requestCoreDeliveryId,
                                                             Long supplementarityDocumentSeq);

    /**
     * @param requestCoreDeliveryId
     */
    void deleteRequestCoreDeliveryToSupplementarityDocuments(long requestCoreDeliveryId);

    /**
     * This method will create ROI_RequestCoreDeliverytoROI_RequestCoreDeliveryCharges
     * @param requestCoreDeliveryId
     * @param requestCoreDeliveryChagesId
     */
    void createRequestCoreDeliveryToInvoice(long requestCoreDeliveryId,
                                            long requestCoreDeliveryChagesId);

    /**
     * @param requestCoreDeliveryId
     */
    void deleteRequestCoreDeliveryToInvoice(long requestCoreDeliveryId);

    /**
     * This method will create RequestCoreDeliveryCharges
     * @param requestCoreDeliveryCharges
     * @return long requestCoreDeliveryChargesSeq
     */
    long createRequestCoreDeliveryCharges(RequestCoreDeliveryCharges requestCoreDeliveryCharges);

    /**
     * @param requestCoreId
     * @return list of all non released invoices
     */
    List<Long> retrieveNonReleasedInvoices(long requestCoreId);

    /**
     * @param releaseId
     * @return list of all invoices for this release
     */
    List<Long> retrieveInvoicesForRelease(long releaseId);

    /**
     * @param invoiceId
     * @param isReleased
     */
    void updateInvoiceReleased(long invoiceId, boolean isReleased);

    /**
     * This method will create RequestCoreDeliveryChargesDocument
     * @param requestCoreDeliveryChargesDocument
     * @param requestCoreDeliveryChargesBilling
     */
    void createRequestCoreDeliveryChargesDocument(
                            RequestCoreDeliveryChargesDocument requestCoreDeliveryChargesDocument);

    /**
     * This method will create RequestCoreDeliveryChargesFee
     * @param requestCoreDeliveryChargesFee
     * @param requestCoreDeliveryChargesBilling
     */
    void createRequestCoreDeliveryChargesFee(
                                       RequestCoreDeliveryChargesFee requestCoreDeliveryChargesFee);

    /**
     * This method will create RequestCoreDeliveryChargesShipping
     * @param requestCoreDeliveryChargesShipping
     */
    void createRequestCoreDeliveryChargesShipping(
                            RequestCoreDeliveryChargesShipping requestCoreDeliveryChargesShipping);


    /**
     * This method will retrieve retrieveRequestCoreDeliveryCharges Info.
     * @param long requestCoreId
     * @return RequestCoreDeliveryCharges requestCoreDeliveryCharges
     */
    List<RequestCoreDeliveryCharges> retrieveRequestCoreDeliveryCharges(long requestCoreId);

    /**
     * This method will retrieve RequestCoreDeliveryChargesAdjustmentPayment Info.
     * @param long requestCoreDeliveryChargesId
     * @return List<RequestCoreDeliveryChargesAdjustmentPayment>
     */
    List<RequestCoreDeliveryChargesAdjustmentPayment>
             retrieveRequestCoreDeliveryChargesAdjustmentPayment(long requestCoreDeliveryChargesId);

    /**
     * This method will delete the RequestCoreDeliveryCharges Document Fee
     * Shipping Payment for the particular request.
     * @param requestCoreId
     */
    void deleteRequestCoreDeliveryCharges(long  requestCoreDeliveryChargesId);

    /**
     * This method will delete the RequestCoreDeliveryChargesFee for the particular request.
     * @param requestCoreDeliveryChargesId
     */
    void deleteRequestCoreDeliveryChargesFee(long  requestCoreDeliveryChargesId);

    /**
     * This method will delete the RequestCoreDeliveryChargesShipping for the particular request.
     * @param requestCoreDeliveryChargesId
     */
    void deleteRequestCoreDeliveryChargesShipping(long  requestCoreDeliveryChargesId);

    /**
     * This method will delete the RequestCoreDeliveryChargesDocument for the particular request.
     * @param requestCoreDeliveryChargesId
     */
    void deleteRequestCoreDeliveryChargesDocument(long  requestCoreDeliveryChargesId);

    /**
     * This method will delete the RequestCoreDeliveryChargesInvoice Patients for the particular
     * request.
     * @param requestCoreDeliveryChargesId
     */
    void deleteRequestCoreDeliveryChargesInvoicePatients(long  requestCoreDeliveryChargesId);
    /**
     * This method will save the patient details as part of invoice creation
     * @param requestCoreDeliveryInvoicePatients
     */
    void createInvoicePatients(RequestCoreDeliveryInvoicePatients
                                                                requestCoreDeliveryInvoicePatients);

    /**
     * This method will retrieve RequestCoreDeliveryChargesShipping Info.
     * @param long requestCoreDeliveryChargesId
     * @return RequestCoreDeliveryChargesShipping
     */
    RequestCoreDeliveryChargesShipping retrieveRequestCoreDeliveryChargesShipping(
                                                                 long requestCoreDeliveryChargesId);
    /**
     * This method will save the sales tax reason details
     * @param SalesTaxReason salesTaxReason
     *
     */
    void createSalesTaxReason(SalesTaxReason salesTaxReason);
    /**
     * This method will retrieve the sales tax reason details
     * @param requestCoreChargesSeq
     * @return List<SalesTaxReason>
     */
    List<SalesTaxReason> retrieveSalesTaxReason(long requestCoreChargesSeq);

    /**
     * This method will load the Invoice History
     * @param requestId
     * @return InvoiceHistory
     */
    InvoiceHistory retrieveInvoiceSummaries(long requestCoreId);

    /**
     * This method will retrieve retrieveRequestCoreDeliveryCharges Info based on Invoice Id.
     * @param long requestCoreId
     * @return RequestCoreDeliveryCharges requestCoreDeliveryCharges
     */
    RequestCoreDeliveryCharges retrieveDeliveryChargesUsingInvoiceId(long rcdChargesSeq);

    /**
     * This method retrieves the RequestCoreCharges values for Document
     *
     * @param rcdChargesId
     * @return Set<RequestCoreDeliveryChargesDocument>
     */
    Set<RequestCoreDeliveryChargesDocument> retrieveDeliveryChargesDocument(Long rcdChargesId);

    /**
     * This method retrieves the RequestCoreCharges values for Fee
     *
     * @param rcdChargesId
     * @return Set<RequestCoreDeliveryChargesFee>
     */
    Set<RequestCoreDeliveryChargesFee>  retrieveDeliveryChargesFee(long rcdChargesId);

    /**
     * This method deletes the sales tax reason for this particular requestCoreChargesId
     * @param requestCoreChargesId
     */
    void deleteSalesTaxReason(long  requestCoreChargesId);

    /**
     * This method retrieves the list of invoice patients
     *
     * @param List<Long> invoiceIds
     * @return List<RequestCoreDeliveryInvoicePatients>
     */
    List<RequestCoreDeliveryInvoicePatients> retrieveInvoicePatients(List<Long> invoiceIds);

    /**
     * retrieves the list of all the adjustment for all the invoice Ids
     * @param requestCoreDeliveryChargesId
     * @return list of RequestCoreDeliveryAdjustmentPayment
     */
    List<RequestCoreDeliveryChargesAdjustmentPayment> retrieveAllAdjustmentPaymentByInvoiceIds(
                                                           List<Long> requestCoreDeliveryChargesId);

    /**
     * Retrieves the list of all the requestCoreDeliveryCaharges based on the invoice Ids
     * @param rcdChargesSeq
     * @return list of RequestCoreDeliveryCharges
     */
    List<RequestCoreDeliveryCharges> retrieveAllDeliveryChargesUsingInvoiceIds(
                                                                          List<Long> rcdChargesSeq);

    /**
     * Retrieves the list of all the document charges for the given invoice Ids
     * @param rcdChargesId
     * @return list of RequestCoreDeliveryChargesDocument
     */
    List<RequestCoreDeliveryChargesDocument> retrieveAllDeliveryChargesDocument(
                                                                           List<Long> rcdChargesId);
    /**
     * Retrieves the list of all the document charges for the given invoice Ids
     * @param rcdChargesId
     * @return list of RequestCoreDeliveryChargesDocument
     */
    List<RequestCoreDeliveryChargesDocument> retrieveAllDeliveryChargesDistinctDocument(
                                                                           List<Long> rcdChargesId);

    /**
     * Retrieves the list of all the fee charges for the given invoice Ids
     * @param rcdChargesId
     * @return list of all RequestCoreDeliveryFeeCharges
     */
    List<RequestCoreDeliveryChargesFee>  retrieveAllDeliveryChargesFeeByInvoice(
                                                                           List<Long> rcdChargesId);

    /**
     * Retrieves the list of all the fee charges for the given invoice Ids
     * @param rcdChargesId
     * @return list of all RequestCoreDeliveryFeeCharges
     */
    List<RequestCoreDeliveryChargesFee>  retrieveAllDeliveryChargesDistinctFeeByInvoice(
                                                                           List<Long> rcdChargesId);

    /**
     * Retrieves the list of all the invoice shipping details for the given invoice Ids
     * @param requestCoreDeliveryChargesId
     * @return list of shipping details for the invoiceIds
     */
    List<RequestCoreDeliveryChargesShipping> retrieveAllRequestCoreDeliveryChargesShipping(
                                                          List<Long> requestCoreDeliveryChargesId);

    /**
     * This method is used to update the RequestCoreDeliveryCharges
     * @param requestCoreDeliveryChargesId
     * @param updatedInvoicedAmount
     */
    /*void updateRequestCoreDeliveryCharges(long requestCoreDeliveryChargesId,
                                          double updatedInvoicedAmount,
                                          double paymentAmount,
                                          double creditAdjAmount,
                                          double debitAdjAmount);*/

    /**
     * creates the given cover letter into the database
     *
     * @param coverLetterCore
     * @return created cover letter Id
     */
    long createLetterCore(CoverLetterCore coverLetterCore);

    /**
     * retrieves the cover letter for the given cover letterId
     * @param coverLetterId
     * @return retrieved coverletter
     */
    CoverLetterCore retrieveLetterCore(long coverLetterId);

    /**
     * deletes the cover letter for the given cover letterId
     * @param coverLetterId
     */
    void deleteCoverLetter(long coverLetterId);
    /**
     * This method will retrieve data from Regenerated table based on Regenerated Id.
     * @param regInvoiceId
     * @return
     */
//    RegeneratedInvoiceDetails retrieveRegeneratedInvoiceUsingRegeneratedId(long regInvoiceId);

    /**
     * retrieves the requestId for the given releaseId
     *
     * @param requestCoreDeliveryId
     * @return retrieved requestId
     */
    long retrieveRequestIdForRequestCoreDelivery(long requestCoreDeliveryId);

    /**
     * retrieves the list of past invoices i.e regenerated invoices
     *
     * @param requestId
     * @return list of past invoices
     */
    List<PastInvoice> retrievePastInvoices(long requestId);

	/**
     * This method fetches the released request info details for the specified request id
     * @param id RequestId
     * @param allRelease true/false
     * @return ChargeHistoryList
     */
    ChargeHistoryList retrieveChargeHistory(long requestId);

    /**
     * This method updates the Invoice & output letter properties
     * @param InvoiceAndLetterOutputProperties outputProperties
     */
     void updateInvoiceOutputLetter(InvoiceAndLetterOutputProperties outputProperties);

     /**
      * This method updates the Invoice & output letter properties
      * @param InvoiceAndLetterOutputProperties outputProperties
      */
     void updateReleaseOutputProperties(InvoiceAndLetterOutputProperties outputProperties);

     /**
      * This method deletes the invoice auto adjustment and payment which is made
      * during creating invoices,
      * this method would be called while cancelling invoices
      * @param invoiceId
      */
     void deleteInvoiceAutoAdjEvent(long invoiceId);

     /**
      * This method retrieves the boolean value whether the request contains the
      * non released invoices
      * @param requestId
      */
     boolean isRequestContainsNonReleasedInvoices(long requestCoreId);

     /**
      * Retrieves the request charges such as balancedue,
      * credit adjustmenttotal, debit adjustment total and payment amount
      * for the given list of invoicesIds
      *
      * @param invoiceIds
      * @return list of requestcoreCharges - which contains only the above specified charges amount
      */
//     List<RequestCoreCharges> retrieveRequestCharges(List<Long> invoiceIds);

/*     *//**
      * Updates the request charges such as balancedue,
      * credit adjustmenttotal, debit adjustment total and payment amount
      * for the given list of RequestCoreCharges
      *
      * @param requestCharges
      *//*
     void updateRequestCharges(List<RequestCoreCharges> requestCharges);*/

	/**
      * This method is used to update the invoice balance
      *
      * @param invoiceId
      * @param invoiceBalance
      * @param creditAdjAmt
      * @param adjPayTotal
      * @param date
      * @param user
      *
      */
     void updateInvoiceBalance(long invoiceId, double invoiceBalance, Date date, User user);

     /**
      * This method is used to update the Prebill Status of Invoice
      *
      * @param invoice
      *
      */
     void updatePrebillStatusInvoice(RequestCoreDeliveryCharges invoice);

     /**
      * This method will retrieve Prebill records from RequestCoreDeliveryCharges
      * @param long requestCoreDeliveryId
      * @return RequestCoreDeliveryCharges retrieveRequestCoreDeliveryCharges
      */
     List<RequestCoreDeliveryCharges> retrieveRequestCoreDeliveryChargesPrebill(
                                                                               long requestCoreId);

     /**
      *  This method is used to retrieve details for the prebill report with requestor name
      *
      *  @param facList,reqType,reqName,reqStatus,aging,fromDt,toDt,balance,
      *  balanceCriterion,resultType
      *  @return list
      */
     List<PrebillReportDetails> retrievePrebillReportDetailsWithRequestorName(String[] facility,
                                                                       String[] requestorTypeName,
                                                                       String requestorName,
                                                                       String[] reqStatus,
                                                                       String fromDt,
                                                                       String toDt,
                                                                       Double balance,
                                                                       String balanceCriterion,
                                                                       String resultType);

     /**
      *  This method is used to retrieve details for the prebill report without requestor name
      *
      *  @param facList,reqType,reqName,reqStatus,aging,fromDt,toDt,balance,
      *  balanceCriterion,resultType
      *  @return list
      */
     List<PrebillReportDetails> retrievePrebillReportDetailsWithoutRequestorName(String[] facility,
                                                         String[] requestorTypeName,
                                                         String[] reqStatus,
                                                         String fromDt,
                                                         String toDt,
                                                         Double balance,
                                                         String balanceCriterion,
                                                         String resultType);

     /**
      *  This method is used to retrieve details for the PostPayment report
      *
      *  @param facList,reqType,userName,fromDt,toDt,resultType
      *  @return list
      */
     List<PostPaymentReportDetails> retrievePostPaymentReportDetails(
             String[] facility, List<String> userName, String[] requestorTypeName,
            Date fromDt, Date toDt, String resultType);

     void deleteAllMappedAdjustmentInvoicesByInvoiceId(long invoiceId);

     void deleteAllMappedPaymentInvoicesByInvoiceId(long invoiceId);
     //US16834 changes to Include requests in the pre-bill status on the payments popup.(added new functions)
     void updatePrebillPaymentsToInvoice(
             InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo);
     
     void updatePrebillAdjustmentsToInvoice(InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo);
     
     void updateInvoicePaymentsToPrebill(long requestId, long invoiceId);
     
     void updateInvoiceAdjustmentsToPrebill(long requestId, long invoiceId);
     
    /* void updateUnappliedToAppliedPaymentsToPrebill(long requestId);
     
     void updateUnappliedToAppliedAdjustmentsToPrebill(long requestId);*/

     boolean prebillPaymentExists(long requestId);

     double totalPostPrebillPayments(
            InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo);
     
     boolean prebillAdjustmentExists(long requestId);

     double totalPostPrebillAdjustments(
            InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo);
     
     void activateLatestPrebill(long requestId);
     
     long retrievePaymentDetailsFromDialog(long requestId);
     
     long retrieveAdjustmentDetailsFromDialog(long requestId);
     
     void unmapPaymentsFromInvoiceFromDialog(long paymentId);
     
     void unmapAdjustmentsFromInvoiceFromDialog(long adjustmentId);
     
     /**
      * This method will retrieve RequestCoreDeliveryChargesAdjustmentPayment Info for prebill records.
      * @param long requestCoreDeliveryChargesId
      * @return List<RequestCoreDeliveryChargesAdjustmentPayment>
      */
     List<RequestCoreDeliveryChargesAdjustmentPayment> retrieveRequestCoreDeliveryChargesPrebillAdjustmentPayment(long requestCoreDeliveryChargesId);
     
}
