package com.mckesson.eig.roi.billing.service;

import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.billing.model.ChargeHistoryList;
import com.mckesson.eig.roi.billing.model.DocInfo;
import com.mckesson.eig.roi.billing.model.InvoiceAndLetterOutputProperties;
import com.mckesson.eig.roi.billing.model.InvoiceHistory;
import com.mckesson.eig.roi.billing.model.InvoiceOrPrebillAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.LetterInfo;
import com.mckesson.eig.roi.billing.model.PastInvoiceList;
import com.mckesson.eig.roi.billing.model.ReleaseAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.ReleaseCore;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryList;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryCharges;

public interface BillingCoreService {

    /**
     * This method creates a release.
     * @param releaseCore
     * @return ReleaseAndPreviewInfo
     */

    ReleaseAndPreviewInfo createReleaseAndPreviewInfo(ReleaseCore releaseCore, boolean flag ,double appliedAmount);

    /**
     * @param releaseId
     * @param invoiceId
     */
    void cancelRelease(long releaseId, long invoiceId);

    /**
     * This method deletes entries from  RequestCoreDeliveryTables based on Request core id.
     * @param RequestCoreId requestCoreId
     *
     */
    void cancelInvoiceOrPrebillAndPreview(long requestCoreId);

    /**
     * This method creates a entries into RequestCoreDeliveryTables
     * by fetching the data from RequestCoreChargesTables.
     * @param InvoiceOrPrebillAndPreviewInfo invoiceOrPrebillAndPreviewInfo
     * @return DocInfo
     */
    DocInfo createInvoiceOrPrebillAndPreview(InvoiceOrPrebillAndPreviewInfo invoiceOrPrebillInfo);

	/**
	 * This method will generate the letter
	 * @param letterInfo
	 * @return DocInfo
	 */
	DocInfo createLetterAndPreview(LetterInfo letterInfo);

	/**
	 * This method will cancel the generated the letter
	 * and deletes the entry which is made into the database while we preview the coverletter
	 *
	 * @param letterInfo
	 */
	void cancelCoverLetter(long requestId, long coverLetterId);


	/**
     * This method will fetch release history list
     * @param releaseId
     * @return ReleaseHistoryList
     */
    ReleaseHistoryList retrieveReleaseHistoryList(long releaseId);

    /**
     * This method will load the Invoice History
     * @param requestId
     * @return InvoiceHistory
     */
    InvoiceHistory retrieveInvoiceSummaries(long requestCoreId);

    /**
     * This method creates an Invoice with the invoice details persisted earlier
     * This service can be used in case of viewing Invoice History
     *
     * @param invoiceId invoice id
     * @param docType
     * @return DocInfo details
     */
    DocInfo viewInvoice(long invoiceId, String docType);

    /**
     * This method retrieves the list of requestCoreDeliveryCharges for the given invoiceIds
     * along with the document charge, feecharge, adjustment and payment
     * @param invoiceIds
     * @return list of all requestcoreDeliveryCharges
     */
    List<RequestCoreDeliveryCharges> retrieveAllInvoicesByIds(List<Long> invoiceIds);

    /**
     * This method creates the letter details
     * @param lovId
     * @return letter file name
     */
    String regenerateLetter(long lovId, String fileType, String file);

    /**
     * retrieves the list of all past invoices for the given requestId
     *
     * @param requestId
     * @return list of past invoices
     */
    PastInvoiceList retrievePastInvoices(long requestId);

	/**
     * This method fetches the released info details
     * @param requestId - Request id
     * @return ChargeHistoryList
     */
    ChargeHistoryList retrieveChargeHistory(long requestId);

    /**
     * This method updates the invoice & output info details
     * @param InvoiceAndLetterOutputProperties - outputProperties
     */
    void updateInvoiceOutputProperties(InvoiceAndLetterOutputProperties outputProperties);

    /**
     * This method marks the current release of the request as draft release
     * @param requestId
     * @param displayBillingInfo
     */
    void setDisplayBillingInfo(long requestId, boolean displayBillingInfo);

    /**
     *  This method is used to retrieve details for the prebill report with requestor name
     *
     *  @param facList,reqType,reqName,reqStatus,aging,fromDt,toDt,balance,balanceCriterion,resultType
     *  @return list
     */
    public List<Object[]> retrievePrebillReportDetailsWithRequestorName(String[] facList,String[] reqType,String reqName,String[] reqStatus,String fromDt,String toDt,String balance,String balanceCriterion,String resultType);

    /**
     *  This method is used to retrieve details for the prebill report without requestor name
     *
     *  @param facList,reqType,reqName,reqStatus,aging,fromDt,toDt,balance,balanceCriterion,resultType
     *  @return list
     */
    public List<Object[]> retrievePrebillReportDetailsWithoutRequestorName(String[] facList,String[] reqType,String[] reqStatus,String fromDt,String toDt,String balance,String balanceCriterion,String resultType);

    /**
     *  This method is used to retrieve details for the post payment report
     *
     *  @param facList,reqType,reqName,reqStatus,aging,fromDt,toDt,resultType
     *  @return list
     */
    public List<Object[]> retrievePostPaymentReportDetails(String[] facList,List<String> userName,String[] reqType,Date  fromDt,Date toDt,String resultType);
    
    /**
     * apply the unapplied amount to the invoice
     *
     * @param requestId
     * @param invoiceId
     */
    public void autoApplyToInvoice(long requestId, long invoiceId);
    
    /**
     * Update Invoice Balance
     *
     * @param invoiceId
     * @param invoiceBalance
     */
    public void updateInvoiceBalance(long invoiceId, double invoiceBalance);
    
}
