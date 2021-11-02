package com.mckesson.eig.roi.billing.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;

import com.mckesson.eig.roi.admin.dao.LetterTemplateDAO;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.LetterDataRetriever;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.billing.dao.ReleaseHistoryDAO;
import com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO;
import com.mckesson.eig.roi.billing.letter.model.LetterData;
import com.mckesson.eig.roi.billing.model.ChargeHistoryList;
import com.mckesson.eig.roi.billing.model.CoverLetterCore;
import com.mckesson.eig.roi.billing.model.DocInfo;
import com.mckesson.eig.roi.billing.model.DocInfoList;
import com.mckesson.eig.roi.billing.model.InvoiceAndLetterOutputProperties;
import com.mckesson.eig.roi.billing.model.InvoiceHistory;
import com.mckesson.eig.roi.billing.model.InvoiceOrPrebillAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.LetterInfo;
import com.mckesson.eig.roi.billing.model.PastInvoice;
import com.mckesson.eig.roi.billing.model.PastInvoiceList;
import com.mckesson.eig.roi.billing.model.PostPaymentReportDetails;
import com.mckesson.eig.roi.billing.model.PrebillReportDetails;
import com.mckesson.eig.roi.billing.model.ReleaseAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.ReleaseCore;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryItem;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryList;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryPatient;
import com.mckesson.eig.roi.billing.model.RequestCoreDelivery;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryCharges;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesAdjustmentPayment;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesBilling;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesDocument;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesFee;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesShipping;
import com.mckesson.eig.roi.journal.service.JournalService;
import com.mckesson.eig.roi.request.dao.RequestCoreChargesDAO;
import com.mckesson.eig.roi.request.dao.RequestCoreDAO;
import com.mckesson.eig.roi.request.dao.RequestCorePatientDAO;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestEvent;
import com.mckesson.eig.roi.request.model.RequestEvent.TYPE;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.requestor.dao.RequestorDAO;
import com.mckesson.eig.roi.requestor.dao.RequestorStatementDAO;
import com.mckesson.eig.roi.requestor.model.AdjustmentInfo;
import com.mckesson.eig.roi.requestor.model.AdjustmentType;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustment;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustmentsPayments;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.roi.requestor.model.RequestorInvoice;
import com.mckesson.eig.roi.requestor.model.RequestorPayment;
import com.mckesson.eig.roi.requestor.model.RequestorPaymentList;
import com.mckesson.eig.roi.requestor.model.RequestorPrebill;
import com.mckesson.eig.roi.requestor.model.RequestorPrebillsList;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorStatementInfo;
import com.mckesson.eig.roi.requestor.model.RequestorUnappliedAmountDetails;
import com.mckesson.eig.roi.requestor.model.RequestorUnappliedAmountDetailsList;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

@WebService(serviceName="BillingCoreService", endpointInterface="com.mckesson.eig.roi.billing.service.BillingCoreService",
targetNamespace="urn:eig.mckesson.com", portName="billingCorePort", name="BillingCoreServiceImpl")
public class BillingCoreServiceImpl
extends AbstractBillingService
implements BillingCoreService {

    private static final OCLogger LOG = new OCLogger(BillingCoreServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static BillingCoreServiceHelper _helper = new BillingCoreServiceHelper();
   

    @Override
    public DocInfo createInvoiceOrPrebillAndPreview(
                                InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo) {

        final String logSM = "createInvoiceOrPrebillAndPreview(invoiceOrPrebillAndPreviewInfo)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invOrPrebillPreviewInfo);
        }

        try {

            BillingCoreServiceValidator validator = new BillingCoreServiceValidator();
            if (!validator.validateInvAndPrebillInfo(invOrPrebillPreviewInfo)) {
                throw validator.getException();
            }

            RequestCoreDeliveryDAO rCDeliveryDAO =
                    (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

            RequestCoreChargesDAO rCChargesDAO =
                    (RequestCoreChargesDAO) getDAO(DAOName.REQUEST_CORE_CHARGES);

            RequestCorePatientDAO rCPatientDAO =
                    (RequestCorePatientDAO) getDAO(DAOName.REQUEST_PATIENT_DAO);
            
            long requestCoreId = invOrPrebillPreviewInfo.getRequestCoreId();
            
            RequestCoreCharges requestCoreCharges = new RequestCoreCharges();
            
            if("PreBill".equalsIgnoreCase(invOrPrebillPreviewInfo.getLetterType()))
            {
				//US16834 changes to Include requests in the pre-bill status on the payments/adjustments popup.
                // (Calculating balance due for prebill when there is change in base charge)
                invOrPrebillPreviewInfo.setAmountToApply(invOrPrebillPreviewInfo.getAmountpaid());
                double totalPostPrebillPayments =  rCDeliveryDAO.totalPostPrebillPayments(invOrPrebillPreviewInfo);
                double totalPostPrebillAdjustments =  rCDeliveryDAO.totalPostPrebillAdjustments(invOrPrebillPreviewInfo);
                // Convert the applied payments on past prebills for the request to unapplied payment
                appliedToUnappliedPaymentPrebill (totalPostPrebillPayments, totalPostPrebillAdjustments, requestCoreId);
                rCChargesDAO.updateRequestReleaseCost(requestCoreId, invOrPrebillPreviewInfo.getBaseCharge(), rCChargesDAO.getDate(), getUser().getInstanceId());
                // Retrieve all the data from Rough Draft tables.
                requestCoreCharges = rCChargesDAO.retrieveRequestCoreBillingPaymentInfo(requestCoreId);
                requestCoreCharges.setBalanceDue(requestCoreCharges.getBalanceDue() - invOrPrebillPreviewInfo.getAmountpaid());
            } else {
                requestCoreCharges = rCChargesDAO.retrieveRequestCoreBillingPaymentInfo(requestCoreId);
            }
            
            RequestCoreDelivery rCoreDelivery = new RequestCoreDelivery();

            RequestCoreDeliveryChargesBilling rCDeliveryChargesBilling =
                                                        new RequestCoreDeliveryChargesBilling();

            rCoreDelivery.setRequestCoreDeliveryChargesBilling(rCDeliveryChargesBilling);

            RequestCoreDeliveryCharges rCDeliveryCharges = new RequestCoreDeliveryCharges();
            rCoreDelivery.setRequestCoreDeliveryCharges(rCDeliveryCharges);

            RequestCoreDeliveryChargesShipping rCDeliveryChargesShipping =
                                                        new RequestCoreDeliveryChargesShipping();
            rCoreDelivery.setRequestCoreDeliveryChargesShipping(rCDeliveryChargesShipping);

            rCoreDelivery.setRequestCoreId(requestCoreId);
            Timestamp date = rCPatientDAO.getDate();

            if (null != requestCoreCharges) {
                // Set the values from Rough Draft tables to Delivery model
                // class object
                _helper.setvaluesFromRequestCoreChargesToRequestCoreDelivery(requestCoreCharges,
                                                                     rCoreDelivery,
                                                                     rCDeliveryDAO.getDate());
            }

            RequestCoreDeliveryCharges requestCoreDeliveryCharges =
                                                rCoreDelivery.getRequestCoreDeliveryCharges();
            _helper.setvaluesFromBillingInfotoRequestCoreDelivery(invOrPrebillPreviewInfo,
                                                          requestCoreDeliveryCharges);

            // creating the RequestCoreDeliveryCharges for requestCoreDeliveryId
            requestCoreDeliveryCharges.setRequestCoreId(rCoreDelivery.getRequestCoreId());
            populatePrebillBalancesAndStatus(requestCoreDeliveryCharges);

            setDefaultDetails(requestCoreDeliveryCharges, date, true);
            long requestCoreDeliveryChargesId =
                    rCDeliveryDAO.createRequestCoreDeliveryCharges(requestCoreDeliveryCharges);
            
            //CR-388283
            if(invOrPrebillPreviewInfo.getAmountToApply() > 0.00) {
               autoApplyToInvoice(rCoreDelivery.getRequestCoreId(), requestCoreDeliveryChargesId);
            }
           //CR-388283

            RequestCoreDeliveryChargesShipping shippingCharges =
                                            rCoreDelivery.getRequestCoreDeliveryChargesShipping();
            if (requestCoreDeliveryChargesId > 0) {

                // creating the RequestCoreDeliveryChargesDocument for
                // requestCoreDeliveryChargesId
                _helper.createInvoiceDocumentCharge(rCoreDelivery, date,
                                                    requestCoreDeliveryChargesId);

                // creating the RequestCoreDeliveryChargesFee for
                // requestCoreDeliveryChargesId
                _helper.createInvoiceFeeCharges(rCoreDelivery, requestCoreDeliveryChargesId);

                // creating the RequestCoreDeliveryChargesShipping for
                // requestCoreDeliveryChargesId
                shippingCharges.setRequestCoreDeliveryChargesId(requestCoreDeliveryChargesId);
                shippingCharges.setWillInvoiceShipped(invOrPrebillPreviewInfo.isWillInvoiceShipped());
                rCDeliveryDAO.createRequestCoreDeliveryChargesShipping(shippingCharges);

                _helper.createInvoicePatients(invOrPrebillPreviewInfo, date,
                                              requestCoreDeliveryChargesId);

                /*_helper.createInvoiceAutoAdjustments(invOrPrebillPreviewInfo.getAutoAdjustments(),
                                             date,
                                             requestCoreDeliveryChargesId);*/
                // clear the request release cost only if invoice is created
                if("Invoice".equalsIgnoreCase(invOrPrebillPreviewInfo.getLetterType())){
                   rCChargesDAO.clearRequestReleaseCost(requestCoreId);
				   //US16834 changes to Include requests in the pre-bill status on the payments popup.(making all the prebill payments and adjustments as invoice payments and adjustments once invoice is generated.)
                   rCDeliveryDAO.updatePrebillPaymentsToInvoice(invOrPrebillPreviewInfo);
                   rCDeliveryDAO.updatePrebillAdjustmentsToInvoice(invOrPrebillPreviewInfo);
                }
            }

            List<Long> invoiceIdList = new ArrayList<Long>();
            invoiceIdList.add(requestCoreDeliveryChargesId);

            long invoiceTemplateFileId = invOrPrebillPreviewInfo.getLetterTemplateFileId();
            List<DocInfo> docinfoList = createInvoiceInfo(invoiceTemplateFileId,
                                                        invOrPrebillPreviewInfo.getRequestCoreId(),
                                                        invOrPrebillPreviewInfo.getType(),
                                                        invOrPrebillPreviewInfo.getNotes(),
                                                        invoiceIdList,
                                                        invOrPrebillPreviewInfo.getLetterType());
            DocInfo docInfo = null;
            if (CollectionUtilities.hasContent(docinfoList)) {
                docInfo = docinfoList.get(0);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return docInfo;

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Exception occured in createInvoiceOrPrebillAndPreview",e);
            throw new ROIException(e,
                        ROIClientErrorCodes.CREATE_REQUEST_CORE_DELIVERY_INVOICE_OPERATION_FAILED);
        }
    }

    /**
     * @param invOrPrebillPreviewInfo
     * @param rCDeliveryDAO
     * @param requestCoreId
     * @param invoice
     */
    private void populatePrebillBalancesAndStatus(RequestCoreDeliveryCharges invoice) {

        RequestCoreDeliveryDAO rCDeliveryDAO =
                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

        long requestCoreId = invoice.getRequestCoreId();
        double baseCharge = invoice.getBaseCharge();
        double releaseCost = 0.0;
        double totalRequestCost = 0.0;
        double balanceDue = invoice.getInvoiceBalanceDue();
        boolean prebillPaymentExists = false;
        boolean prebillAdjustmentExists = false;
        if ("Prebill".equalsIgnoreCase(invoice.getType())) {

           invoice.setPrebillStatus("Active");
        } else {
			//US16834 changes to Include requests in the pre-bill status on the payments popup.(wrote a seperate case when invoice is generated after prebill payments)
            prebillPaymentExists = rCDeliveryDAO.prebillPaymentExists(requestCoreId);
            prebillAdjustmentExists = rCDeliveryDAO.prebillAdjustmentExists(requestCoreId);
            if(prebillPaymentExists || prebillAdjustmentExists)
            {
                invoice.setInvoiceBalanceDue(balanceDue);
                invoice.setPrebillStatus("InActive");
                rCDeliveryDAO.updatePrebillStatusInvoice(invoice);
                return;
            }

            List<RequestCoreDeliveryCharges> rcdcPrebillList =
                    rCDeliveryDAO.retrieveRequestCoreDeliveryChargesPrebill(requestCoreId);
            if (rcdcPrebillList.size() > 0) {

                RequestCoreDeliveryCharges rcdc = rcdcPrebillList.get(0);
                baseCharge = rcdc.getBaseCharge();

                // invoices which is created while release may contains some base charges, balances
                // so we have to consider that base charges, balances too.

                for (RequestCoreDeliveryCharges rcdcPrebill : rcdcPrebillList) {

                   if ("Active".equalsIgnoreCase(rcdcPrebill.getPrebillStatus())) {
                       releaseCost += rcdcPrebill.getReleaseCost();
                       totalRequestCost += rcdcPrebill.getTotalRequestCost();
                       balanceDue += rcdcPrebill.getInvoiceBalanceDue();
                   }
                }
            }
            //invoice.setBaseCharge(baseCharge);
            //invoice.setReleaseCost(releaseCost);
            //invoice.setTotalRequestCost(totalRequestCost);
            invoice.setInvoiceBalanceDue(balanceDue);

            invoice.setPrebillStatus("InActive");
            rCDeliveryDAO.updatePrebillStatusInvoice(invoice);

        }
    }


    @Override
    public void cancelInvoiceOrPrebillAndPreview(long invoiceId) {

        final String logSM = "cancelInvoiceOrPrebillAndPreview(invoiceId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoiceId);
        }

        try {

            if (invoiceId < 0) {
               throw new ROIException(ROIClientErrorCodes.INVALID_INVOICE_ID);
            }

            RequestCoreDeliveryDAO requestCoreDeliveryDAO =
                                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

            RequestCoreChargesDAO chargesDao =
                                    (RequestCoreChargesDAO) getDAO(DAOName.REQUEST_CORE_CHARGES);
            RequestorDAO requestorDAO = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);

            Timestamp date = requestorDAO.getDate();

            //Retrieve the DeliveryCharges object to get the DeliveryCharges Seq.
            RequestCoreDeliveryCharges requestCoreDeliveryCharges =
                    requestCoreDeliveryDAO.retrieveDeliveryChargesUsingInvoiceId(invoiceId);
            
            List<RequestorAdjustmentsPayments> amtList = requestorDAO
                    .retrieveRequestorAdjAndPayDetailsForCancelReq(invoiceId);
            
            // unMapPaymentsAdjustmentsDoneByDialog(requestCoreDeliveryDAO, requestCoreDeliveryCharges.getRequestCoreId());
            
            // To add back the amount to Requestor Account
            revertInvoiceAppliedAmount(amtList, invoiceId, requestCoreDeliveryCharges.getRequestCoreId(), date, requestCoreDeliveryDAO);
            
            // reverts the request level charges for the invoices
            // the Release Cost is the base charge of the newly created invoice
            chargesDao.revertRequestReleaseCost(requestCoreDeliveryCharges.getRequestCoreId(),
                                                requestCoreDeliveryCharges.getBaseCharge());

            // Delete the Documents for the corresponding DeliveryCharges Seq.
            requestCoreDeliveryDAO.deleteRequestCoreDeliveryChargesDocument(invoiceId);

            // Delete the Fee for the corresponding DeliveryCharges Seq.
            requestCoreDeliveryDAO.deleteRequestCoreDeliveryChargesFee(invoiceId);

            // Delete the Shipping for the corresponding DeliveryCharges Seq.
            requestCoreDeliveryDAO.deleteRequestCoreDeliveryChargesShipping(invoiceId);

            // Delete the AdjustmentPayment for the corresponding DeliveryCharges Seq.
            //requestCoreDeliveryDAO.deleteRequestCoreDeliveryChargesAdjustmentPayment(invoiceId);

            // Delete the Patients for the corresponding DeliveryCharges Seq.
            requestCoreDeliveryDAO.deleteRequestCoreDeliveryChargesInvoicePatients(invoiceId);
            
            // Revert the payments applied on invoice to latest active prebill
            requestCoreDeliveryDAO.updateInvoicePaymentsToPrebill(requestCoreDeliveryCharges.getRequestCoreId(), invoiceId);
                
            // Revert the adjustments applied on invoice to latest active prebill
            requestCoreDeliveryDAO.updateInvoiceAdjustmentsToPrebill(requestCoreDeliveryCharges.getRequestCoreId(), invoiceId);
            
            // Activate Latest Prebill Status to active since invoice request is canceled
            requestCoreDeliveryDAO.activateLatestPrebill(requestCoreDeliveryCharges.getRequestCoreId());
            
            // Update the Un-applied payments to applied payments to latest active prebill
            //requestCoreDeliveryDAO.updateUnappliedToAppliedPaymentsToPrebill(requestCoreDeliveryCharges.getRequestCoreId());
            
            // Update the Un-applied adjustments to applied payments to latest active prebill
            //requestCoreDeliveryDAO.updateUnappliedToAppliedAdjustmentsToPrebill(requestCoreDeliveryCharges.getRequestCoreId());
            
            // Delete all the applied adjustment to the invoices
            requestCoreDeliveryDAO.deleteAllMappedAdjustmentInvoicesByInvoiceId(invoiceId);

            // Delete all the applied payment to the invoices
            requestCoreDeliveryDAO.deleteAllMappedPaymentInvoicesByInvoiceId(invoiceId);
            
            //Delete the Charges for the corresponding RqeuestCore Seq.
            requestCoreDeliveryDAO.deleteRequestCoreDeliveryCharges(invoiceId);
            
            // deletes the invoice auto adjustment event
            //requestCoreDeliveryDAO.deleteInvoiceAutoAdjEvent(invoiceId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Exception occured in cancelInvoiceOrPrebillAndPreview",e);
            throw new ROIException(e,
                               ROIClientErrorCodes.CANCEL_REQUEST_CORE_DELIVERY_OPERATION_FAILED);
        }
    }
    
    /*private void unMapPaymentsAdjustmentsDoneByDialog(RequestCoreDeliveryDAO requestCoreDeliveryDAO, long requestCoreId) {
        long adjustmentId = requestCoreDeliveryDAO.retrieveAdjustmentDetailsFromDialog(requestCoreId);
        
        List<Long> adjustmentsNewList = new ArrayList<Long>();
        if (null != adjustmentsList && adjustmentsList.size() >= 1) {
            for (long adjSeq : adjustmentsList) {
                 if (!adjustmentsNewList.contains(adjSeq)) {
                     adjustmentsNewList.add(adjSeq);
                 }
            }
            if (adjustmentsNewList.size() == 1) {
                requestCoreDeliveryDAO.unmapAdjustmentsFromInvoiceFromDialog(adjustmentsNewList.get(0).longValue()); 
            }
        }
        
        List<Long> paymentsList = requestCoreDeliveryDAO.retrievePaymentDetailsFromDialog(requestCoreId);
        List<Long> paymentsNewList = new ArrayList<Long>();
        if (null != paymentsList && paymentsList.size() >= 1) {
            for (long paySeq : paymentsList) {
                if (!paymentsNewList.contains(paySeq)) {
                    paymentsNewList.add(paySeq);
                }
            }
            if (paymentsNewList.size() == 1) {
                requestCoreDeliveryDAO.unmapPaymentsFromInvoiceFromDialog(paymentsNewList.get(0).longValue()); 
            }
        } 
    }*/
    


    /**
     * reverts the invoice applied amouont and add the balance to the requestor account
     * @param invoiceId
     * @param requestorDAO
     */
    @SuppressWarnings({"unchecked"})
    private void revertInvoiceAppliedAmount(List<RequestorAdjustmentsPayments> amtList, long invoiceId, long requestCoreId, Timestamp date, RequestCoreDeliveryDAO requestCoreDeliveryDAO) {
        RequestorDAO requestorDAO = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
        List<RequestorAdjustmentsPayments> filteredPrebillPayAdjList = null;
        List<RequestorAdjustmentsPayments> prebillAmtList = filterPrebillPaymentsAdjustments(amtList);
        if (!CollectionUtilities.isEmpty(prebillAmtList)) {
            filteredPrebillPayAdjList = new ArrayList<RequestorAdjustmentsPayments>(prebillAmtList);
        } else {
            filteredPrebillPayAdjList = new ArrayList<RequestorAdjustmentsPayments>(amtList); 
        }
        for (RequestorAdjustmentsPayments req : filteredPrebillPayAdjList) {
            if (req.getTxnType().equalsIgnoreCase("Adjustment")) {
                requestorDAO.updateRequestorAdjustmentDetails(req.getId(),
                                                                  req.getAmount(),
                                                                  req.getUnAppliedAmt().doubleValue(),
                                                                  date,
                                                                  getUser());
            } else if (req.getTxnType().equalsIgnoreCase("Payment")) {
                    requestorDAO.updateRequestorPaymentDetails(req.getId().longValue(),
                                                           req.getUnAppliedAmt().doubleValue(),
                                                           date,
                                                           getUser());
            }
        }
        List<RequestorAdjustmentsPayments> filteredInvoicePayAdjList = (List<RequestorAdjustmentsPayments>) CollectionUtils.subtract(amtList, filteredPrebillPayAdjList);
        if (filteredInvoicePayAdjList != null && filteredInvoicePayAdjList.size() == 0) {
            filteredInvoicePayAdjList = new ArrayList<RequestorAdjustmentsPayments>(amtList);
        }
        // Revert the payments and adjustments done through payment and adjustment dialog
        unmapPaymentsAdjustmentsFromInvoiceFromDialog(filteredInvoicePayAdjList, requestCoreId, date, requestCoreDeliveryDAO, requestorDAO);
    }
    
    private List<RequestorAdjustmentsPayments> filterPrebillPaymentsAdjustments (List<RequestorAdjustmentsPayments> amtList) {
         List<RequestorAdjustmentsPayments> filteredList = new ArrayList<RequestorAdjustmentsPayments>();
         for (RequestorAdjustmentsPayments req : amtList) {
             if (req.isPrebillPaymentsAdjustments()) {
                 filteredList.add(req);
             }
         }
         return filteredList;
    }
    
    private void unmapPaymentsAdjustmentsFromInvoiceFromDialog(List<RequestorAdjustmentsPayments> filteredInvoicePayAdjList,
            long requestCoreId, Timestamp date, RequestCoreDeliveryDAO requestCoreDeliveryDAO, RequestorDAO requestorDAO) {
        for (RequestorAdjustmentsPayments req : filteredInvoicePayAdjList) {
             if (req.getTxnType().equalsIgnoreCase("Adjustment")) {
                 if (!req.isPrebillPaymentsAdjustments()) {
                     requestorDAO.updateRequestorAdjustmentDetails(req.getId(),
                                req.getAmount(),
                                req.getUnAppliedAmt().doubleValue(),
                                date,
                                getUser());
                     long adjustmentId = requestCoreDeliveryDAO.retrieveAdjustmentDetailsFromDialog(requestCoreId);
                     if (adjustmentId == req.getId().longValue()) {
                         requestCoreDeliveryDAO.unmapAdjustmentsFromInvoiceFromDialog(adjustmentId);
                     }
                  }
             }
             else if (req.getTxnType().equalsIgnoreCase("Payment")) {
                    if (!req.isPrebillPaymentsAdjustments()) {
                        requestorDAO.updateRequestorPaymentDetails(req.getId(), req.getUnAppliedAmt().doubleValue(), date, getUser());
                        long paymentId = requestCoreDeliveryDAO.retrievePaymentDetailsFromDialog(requestCoreId);
                        if (paymentId == req.getId().longValue()) {
                            requestCoreDeliveryDAO.unmapPaymentsFromInvoiceFromDialog(paymentId);
                        }
                    }
             }
        }
    }


    @Override
    public ReleaseHistoryList retrieveReleaseHistoryList(long requestId) {

        final String logSM = "retrievReleaseHistoryList(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: ");
        }

        BillingCoreServiceValidator validator = new BillingCoreServiceValidator();

        if (!validator.validateRequestId(requestId)) {
            throw validator.getException();
        }

        ReleaseHistoryDAO dao = (ReleaseHistoryDAO) getDAO(DAOName.RELEASE_HISTORY_DAO);

        List<ReleaseHistoryPatient> releasedPatients =  dao.retrievePatients(requestId);
        List<ReleaseHistoryItem> history = dao.retrieveReleaseHistory(requestId);

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>End; releasedPatients: "
                    + releasedPatients.size() + "; historySize: "
                    + history.size());
        }

        return new ReleaseHistoryList(releasedPatients,
                history);
    }

    @Override
    public ReleaseAndPreviewInfo createReleaseAndPreviewInfo(ReleaseCore releaseCore,
                                                             boolean amtAppliedFlag,
                                                             double appliedAmount) {

        final String logSM = "createReleaseAndPreviewInfo(releaseAndPreviewInfo, "
                                                        + "amtAppliedFlag, appliedAmount)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + releaseCore);
        }

        try {

            BillingCoreServiceValidator validator = new BillingCoreServiceValidator();
            if (!validator.validateReleaseCore(releaseCore)) {
                throw validator.getException();
            }

            // create release

            long requestId = releaseCore.getRequestId();
            RequestCoreDeliveryDAO requestCoreDeliveryDAO =
                            (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

            // create Release entry into the database
            long releaseId = requestCoreDeliveryDAO.createRequestCoreDelivery(requestId);
            _helper.createReleaseDocuments(releaseCore, releaseId);

            getMUUpdateROIOutboundServiceCore().updateROIOutboundStatistics(releaseCore);


            List<DocInfo> listOfDocInfos = new ArrayList<DocInfo>();
            // creates the Release invoice and Release letter
            long invoiceId = createReleaseLetterAndInvoice(releaseCore,
                                                           listOfDocInfos,
                                                           amtAppliedFlag,
                                                           appliedAmount);

            _helper.updateUnReleasedInvoiceToReleased(releaseCore, releaseId);

            // creates the mapping between the invoice and the requestdelivery.
            requestCoreDeliveryDAO.createRequestCoreDeliveryToInvoice(releaseId, invoiceId);


            // generate the past invoices
            List<DocInfo> pastDueInvoiceDocInfos =
                        generatePastInvoices(releaseCore.getPastDueInvoices());

            listOfDocInfos.addAll(pastDueInvoiceDocInfos);

            //updates the requestCoreCharges to released
            RequestCoreChargesDAO chargesDAO =
                            (RequestCoreChargesDAO) getDAO(DAOName.REQUEST_CORE_CHARGES);
            chargesDAO.updateRequestCoreChargesAsReleased(releaseCore.getRequestId());

            DocInfoList docInfoList = mergeDocInfos(listOfDocInfos, ROIConstants.LETTER_FILE);
            ReleaseAndPreviewInfo response = new ReleaseAndPreviewInfo();
            response.setReleaseId(releaseId);
            response.setInvoiceId(invoiceId);
            response.setDocInfoList(docInfoList);

            return response;
        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Exception occured in createReleaseAndPreviewInfo",e);
            throw new ROIException(e,
                        ROIClientErrorCodes.CREATE_REQUEST_CORE_DELIVERY_INVOICE_OPERATION_FAILED);
        }
    }


    /**
     * @param releaseCore
     * @param requestCoreDeliveryDAO
     * @param listOfDocInfos
     * @return
     */
    private long createReleaseLetterAndInvoice(ReleaseCore releaseCore,
                                               List<DocInfo> listOfDocInfos,
                                               boolean amtAppliedFlag,
                                               double appliedAmount) {

        // Generate Letter
        if (releaseCore.isCoverLetterRequired()) {

            String[] coverLetterNotes = releaseCore.getCoverLetterNotes() == null
                    ? null : releaseCore.getCoverLetterNotes().toArray(new String[0]);
            DocInfo letterDocInfo = createLetterInfo(releaseCore.getCoverLetterFileId(),
                    releaseCore.getRequestId(), 
                    coverLetterNotes,
                    ROIConstants.TEMPLATE_FILE_TYPE, true);
            if (letterDocInfo != null) {
                listOfDocInfos.add(letterDocInfo);
            }
        }

        long invoiceId = 0;
        DocInfo invoiceDocInfo = null;
        if (releaseCore.isInvoiceRequired()) {

         // call create invoice method.
            InvoiceOrPrebillAndPreviewInfo invoiceInfo =
                                releaseCore.getInvoiceOrPrebillAndPreviewInfo();

            List<String> notes = releaseCore.getNotes();
            ROIDAO dao = getDAO(DAOName.REQUESTOR_STATEMENT_DAO);

            invoiceInfo.setReleasedDate(dao.getDate());
            invoiceInfo.setReleased(true);
            invoiceInfo.setNotes((null == notes) ? null
                                              : releaseCore.getNotes().toArray(new String[0]));
            // sets unapplied amount to Invoice
            invoiceInfo.setAmountToApply(appliedAmount);
            invoiceInfo.setApplyUnappliedAmount(amtAppliedFlag);
            invoiceDocInfo = createInvoiceOrPrebillAndPreview(invoiceInfo);
        }

        RequestorStatementCriteria statementCriteria = releaseCore.getStatementCriteria();
        if (statementCriteria != null) {

            RequestorStatementDAO dao =
                            (RequestorStatementDAO) getDAO(DAOName.REQUESTOR_STATEMENT_DAO);

            RequestorStatementInfo requestorInfo =
                                            retrieveRequestorStatementInfo(statementCriteria);

            LetterDataRetriever retriever = getLetterDataRetriever(ROIConstants.REQUESTORLETTER);
            LetterData letterData =
                            (LetterData) retriever.constructTemplateDataModel(requestorInfo);

            String fileName = generateLetter(statementCriteria.getRequestorId(),
                                             ROIConstants.TEMPLATE_FILE_TYPE,
                                             ROIConstants.REQUESTORLETTER,
                                             letterData,
                                             dao.getDate());
            // Checkmarx: ROI - Java - Heap_Inspection - Clear the queue password value post processing 
            if (null != letterData) {
                letterData.setQueuePassword(null);
            }

            listOfDocInfos.add(new DocInfo(0,
                                           fileName,
                                           ROIConstants.REQUESTORLETTER));
        }

        // To show the invoice after the requestor letter, docinfo is added after the request leter
        // is created.
        if (invoiceDocInfo != null) {

            listOfDocInfos.add(invoiceDocInfo);
            invoiceId = invoiceDocInfo.getId();
        }

        return invoiceId;
    }

    @Override
    public void cancelRelease(long releaseId, long invoiceId) {

        final String logSM = "cancelRelease(releaseId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + releaseId);
        }

        if (releaseId < 0) {
            throw new ROIException(ROIClientErrorCodes.INVALID_RELEASE);
        }

        try {

            RequestCoreDeliveryDAO requestCoreDeliveryDAO =
                    (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

            long requestId =
                    requestCoreDeliveryDAO.retrieveRequestIdForRequestCoreDelivery(releaseId);

            RequestCoreChargesDAO chargesDao =
                    (RequestCoreChargesDAO) getDAO(DAOName.REQUEST_CORE_CHARGES);
            chargesDao.updateRequestCoreChargesAsUnReleased(requestId);

            // delete mapping to pages for this release
            requestCoreDeliveryDAO.deleteRequestCoreDeliveryToPages(releaseId);
            requestCoreDeliveryDAO.deleteRequestCoreDeliveryToSupplementalAttachments(releaseId);
            requestCoreDeliveryDAO.deleteRequestCoreDeliveryToSupplementalDocuments(releaseId);
            requestCoreDeliveryDAO.deleteRequestCoreDeliveryToSupplementarityAttachments(releaseId);
            requestCoreDeliveryDAO.deleteRequestCoreDeliveryToSupplementarityDocuments(releaseId);

            // update invoice isReleased=false
            List<Long> invoiceIds = requestCoreDeliveryDAO.retrieveInvoicesForRelease(releaseId);
            if (invoiceIds != null) {
                for (Long invoice : invoiceIds) {
                    requestCoreDeliveryDAO.updateInvoiceReleased(invoice, false);
                }
            }

            // delete mapping to invoices for this release
            requestCoreDeliveryDAO.deleteRequestCoreDeliveryToInvoice(releaseId);

            if (invoiceId > 0) {
                cancelInvoiceOrPrebillAndPreview(invoiceId);
            }

            // delete release
            requestCoreDeliveryDAO.deleteRequestCoreDelivery(releaseId);

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Exception occured in cancelRelease",e);
            throw new ROIException(e,
                        ROIClientErrorCodes.CREATE_REQUEST_CORE_DELIVERY_INVOICE_OPERATION_FAILED);
        }
    }


    private List<DocInfo> createInvoiceInfo(long letterTemplateId,
                                            long requestId,
                                            String type,
                                            String[] notes,
                                            List<Long> invoiceIdList,
                                            String letterType) {

        final String logSM = "createInvoiceInfo(letterTempId, requestId, type, notes)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            LetterTemplateDAO letterTemplateDAO =
                                    (LetterTemplateDAO) getDAO(DAOName.LETTER_TEMPLATE_DAO);

            BillingCoreServiceValidator validator = new BillingCoreServiceValidator();
            if (!validator.validateLetterDetails(letterTemplateId, letterTemplateDAO)) {
                throw validator.getException();
            }

            List<DocInfo> docInfoList = new ArrayList<DocInfo>();

            for (Long invoiceId : invoiceIdList) {

                List<String> retVal = generateLetter(invoiceId,
                                                     requestId,
                                                     type,
                                                     "Invoice",
                                                     letterType,
                                                     letterTemplateId,
                                                     notes);

                long invoiceIdVal =
                        (retVal != null && retVal.size() == 3) ? Long.valueOf(retVal.get(2)) : 0;

                String fileName = (retVal != null && retVal.size() == 3) ? retVal.get(0) : "";
                PdfUtilities.encrypt(getCacheFileName(fileName));
                DocInfo docInfo = new DocInfo(invoiceIdVal, fileName, ROIConstants.INVOICE_FILE);
                docInfoList.add(docInfo);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Letter Template Id : " + letterTemplateId);
            }

            return docInfoList;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in createInvoiceInfo",e);
            throw new ROIException(e, ROIClientErrorCodes.LETTER_CREATION_FAILED);
        }
    }

    @Override
    public DocInfo createLetterAndPreview(LetterInfo letterInfo) {

        return createLetterInfo(letterInfo.getLetterTemplateId(),
                                letterInfo.getRequestId(),
                                letterInfo.getNotes(),
                                letterInfo.getType(),
                                true);
    }

    private DocInfo createLetterInfo(long letterTemplateId,
                                     long requestId,
                                     String[] notes,
                                     String type,
                                     boolean encrypt) {

        final String logSM = "createLetterInfo(letterTempId, requestId, notes, docType, encrypt)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            RequestCoreDeliveryDAO requestCoreDeliveryDao =
                            (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

            LetterTemplateDAO letterTemplateDAO =
                            (LetterTemplateDAO) getDAO(DAOName.LETTER_TEMPLATE_DAO);

            BillingCoreServiceValidator validator = new BillingCoreServiceValidator();
            if (!validator.validateLetterDetails(letterTemplateId, letterTemplateDAO)) {
                throw validator.getException();
            }

            CoverLetterCore coverLetterCore =
                       _helper.constructCoverLetterModel(letterTemplateId, requestId, notes, type);
            long coverLetterId = requestCoreDeliveryDao.createLetterCore(coverLetterCore);

            List<String> retVal = generateLetter(coverLetterId,
                                                 requestId,
                                                 type,
                                                 "ROILetters",
                                                 "ROILetters",
                                                 letterTemplateId,
                                                 notes);

            if (encrypt) {
                PdfUtilities.encrypt(getCacheFileName(retVal.get(0)));
            }

            // To create a event for type letter sent
            //createLetterSentEvent(retVal.get(1), notes, requestId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Letter Template Id : " + letterTemplateId);
            }

            return new DocInfo(coverLetterId, retVal.get(0), ROIConstants.LETTER_FILE);

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in createLetterInfo",e);
            throw new ROIException(e, ROIClientErrorCodes.LETTER_CREATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.service.BillingCoreService
     * #cancelCoverLetter(long)
     */
    @Override
    public void cancelCoverLetter(long requestId, long coverLetterId) {

        final String logSM = "cancelCoverLetter(requestId, coverLetterId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId:" + requestId + ", CoverLetterId" + coverLetterId);
        }

        try {

            if (coverLetterId <= 0) {
                throw new ROIException(ROIClientErrorCodes.INVALID_COVER_LETTER_ID);
            }

            RequestCoreDeliveryDAO requestCoreDeliveryDao =
                    (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

            deleteLatestRequestEvent(requestId, RequestEvent.TYPE.LETTER_SENT);
            requestCoreDeliveryDao.deleteCoverLetter(coverLetterId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in cancelCoverLetter",e);
            throw new ROIException(e, ROIClientErrorCodes.LETTER_CREATION_FAILED);
        }

    }

    /**
     * This method will load the Invoice History
     * @param requestId
     * @return InvoiceHistory
     */
    @Override
    public InvoiceHistory retrieveInvoiceSummaries(long requestCoreId) {

        final String logSM = "retrieveInvoiceSummaries(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + requestCoreId);
        }

        try {

            RequestCoreDeliveryDAO rcDeliveryDao =
                                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);
            BillingCoreServiceValidator validator = new BillingCoreServiceValidator();

            if (!validator.validateRequestId(requestCoreId)) {
                throw validator.getException();
            }

            InvoiceHistory history = rcDeliveryDao.retrieveInvoiceSummaries(requestCoreId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return history;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in retrieveInvoiceSummaries",e);
            throw new ROIException(e,
                                   ROIClientErrorCodes.RETRIEVE_INVOICE_HISTROY_OPERATION_FAILED);
        }
    }

    /**
     * This method creates an Invoice with the invoice details persisted earlier
     * This service can be used in case of viewing Invoice History
     *
     * @param invoiceId invoice id
     * @param docType
     * @return DocInfo details
     */
    @Override
    public DocInfo viewInvoice(long invoiceId, String docType) {

        final String logSM = "viewInvoice(invoiceId, docType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Invoice Id : " + invoiceId);
        }

        try {

            docType = StringUtilities.isEmpty(docType) ? ROIConstants.TEMPLATE_FILE_TYPE : docType;
            List<String> retVals = generateLetter(invoiceId, 0, docType, "Invoice", "Invoice",
                                                  0,  null);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Invoice File Name : " + retVals.get(0));
            }
            PdfUtilities.encrypt(getCacheFileName(retVals.get(0)));
            return new DocInfo(invoiceId, retVals.get(0), ROIConstants.INVOICE_FILE);
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in viewInvoice",e);
            throw new ROIException(e, ROIClientErrorCodes.VIEW_INVOICE_OPERATION_FAILED);
        }
    }

    /**
     * retrieves list of all invoices with their subdetails by using the given invoice Ids
     * @see com.mckesson.eig.roi.billing.service.BillingCoreService
     * #retrieveAllInvoicesByIds(java.util.List)
     *
     */
    @Override
    @WebMethod(exclude = true)
    public List<RequestCoreDeliveryCharges> retrieveAllInvoicesByIds(List<Long> invoiceIds) {

        final String logSM = "retrieveInvoicesByIds(List<Long> invoiceIds)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Invoice Ids : " + invoiceIds);
        }

        try {

            RequestCoreDeliveryDAO rcDeliveryDao =
                                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

            RequestCorePatientDAO rcPatientDAO =
                                (RequestCorePatientDAO) getDAO(DAOName.REQUEST_PATIENT_DAO);

            List<RequestCoreDeliveryCharges> rdInvoices =
                                rcDeliveryDao.retrieveAllDeliveryChargesUsingInvoiceIds(invoiceIds);

            List<RequestCoreDeliveryChargesDocument> rdDocumentcharges =
                                rcDeliveryDao.retrieveAllDeliveryChargesDocument(invoiceIds);

            List<RequestCoreDeliveryChargesFee> rdFeeCharges =
                                rcDeliveryDao.retrieveAllDeliveryChargesFeeByInvoice(invoiceIds);

            List<RequestCoreDeliveryChargesAdjustmentPayment> rdAdjPayment =
                                rcDeliveryDao.retrieveAllAdjustmentPaymentByInvoiceIds(invoiceIds);

            List<RequestPatient> requestPatients =
                                rcPatientDAO.retrieveAllInvoicePatientsByInvoiceIds(invoiceIds);

            List<RequestCoreDeliveryChargesShipping> shippingDetails =
                           rcDeliveryDao.retrieveAllRequestCoreDeliveryChargesShipping(invoiceIds);

            RequestCoreDAO requestCoreDAO = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
            List<RequestorCore> requestors =
                                    requestCoreDAO.retrieveAllRequestorByInvoiceIds(invoiceIds);

            for (RequestCoreDeliveryCharges invoice : rdInvoices) {

                _helper.setInvoiceShippingDetails(shippingDetails, invoice);
                _helper.setInvoiceDocumentCharges(rdDocumentcharges, invoice);
                _helper.setInvoiceFeeCharge(rdFeeCharges, invoice);
                _helper.setInvoiceAdjustPayment(rdAdjPayment, invoice);
                _helper.setInvoicePatients(requestPatients, invoice);
                _helper.setInvoiceRequestor(requestors, invoice);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:size Of retrieved Invoice:" + rdInvoices.size());
            }
            return rdInvoices;

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in retrieveAllInvoicesByIds",e);
            throw new ROIException(e, ROIClientErrorCodes.RETRIEVE_INVOICE_OPERATION_FAILED);
        }
    }

    /**
     * regenerates letter for the Output Server
     * @param id
     * @param fileType
     * @param type
     * @return generated FileName
     */
    @Override
    @WebMethod(exclude = true)
    public String regenerateLetter(long id, String fileType, String type) {

        final String logSM = "regenerateLetter(lovId, fileType, file)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
        String fileName = generateLetter(id, fileType, type, null, dao.getDate());

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return fileName;
    }

    /**
     * This method retrieves the past invoices for a request.
     * @param requestId
     * @return PastInvoiceList
     */
    @Override
    public PastInvoiceList retrievePastInvoices(long requestId) {

        final String logSM = "retrievePastInvoices(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }

        if (0 == requestId) {
            throw new ROIException(ROIClientErrorCodes.INVALID_REQUEST_ID);
        }

        try {

            RequestCoreDeliveryDAO dao =
                            (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

            List<PastInvoice> pastInvoices = dao.retrievePastInvoices(requestId);


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + pastInvoices.size());
            }

            return new PastInvoiceList(pastInvoices);

        } catch (Throwable e) {

            LOG.error("Exception occured in retrievePastInvoices",e);
            throw new ROIException(ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
        }
    }

    @Override
    public ChargeHistoryList retrieveChargeHistory(long requestId) {

        final String logSM = "retrieveChargeHistory(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Request Id : " + requestId);
        }

        try {

            RequestCoreDeliveryDAO requestCoreDeliveryDao =
                                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

            BillingCoreServiceValidator validator = new BillingCoreServiceValidator();

            if (!validator.validateRequestId(requestId)) {
                throw validator.getException();
            }

            ChargeHistoryList chargeHistory =
                                         requestCoreDeliveryDao.retrieveChargeHistory(requestId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:No. of Retrieved Charge History : " + chargeHistory);
            }
            return chargeHistory;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            LOG.error("Exception occured in retrieveChargeHistory",e);
            throw new ROIException(ROIClientErrorCodes.RELEASE_OPERATION_FAILED);
        }
    }

    @Override
    public void updateInvoiceOutputProperties(InvoiceAndLetterOutputProperties outputProperties) {

        final String logSM = "updateInvoiceOutputProperties(outputProperties)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:outputProperties : " + outputProperties);
        }
       
        try {
            
            RequestCoreDeliveryDAO requestCoreDeliveryDao =
                                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);
            //Add Corresponding Journal entries for the Invoice
            JournalService journalService =
                                        (JournalService) getService(ServiceName.JOURNEL_SERVICE);
            BillingCoreServiceValidator validator = new BillingCoreServiceValidator();

            if (!validator.validateOutputProperties(outputProperties)) {
                throw validator.getException();
            }

            requestCoreDeliveryDao.updateInvoiceOutputLetter(outputProperties);

            if (outputProperties.isForRelease()) {
                requestCoreDeliveryDao.updateReleaseOutputProperties(outputProperties);
            }
            List<RequestCoreDeliveryChargesAdjustmentPayment> invoicePayAdj = requestCoreDeliveryDao.retrieveRequestCoreDeliveryChargesPrebillAdjustmentPayment(outputProperties.getInvoiceId());
            
            if (outputProperties.isForRelease()) {
                if (CollectionUtilities.hasContent(invoicePayAdj)) {
                    for (RequestCoreDeliveryChargesAdjustmentPayment payAdj : invoicePayAdj) {
                         if("Payment".equalsIgnoreCase(payAdj.getTransactionType())) {
                            journalService.createRemovePrebillPaymentJE(payAdj.getId());
                         }
                    }
                }
                journalService.createSendInvoiceJE(outputProperties.getInvoiceId());
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: of updateInvoiceOutputProperties ");
            }
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            LOG.error("Exception occured in updateInvoiceOutputProperties",e);
            throw new ROIException(ROIClientErrorCodes.VIEW_INVOICE_OPERATION_FAILED);
        }
    }

    /**
     * This method make the DisplayBilling Info field to true,
     * in which enables the DisplayBillingPayment  info screen in the application
     *
     * the Billing payment info screen disable by default while releasing the document
     *
     * @param requestId
     * @param displayBillingInfo
     */
    @Override
    public void setDisplayBillingInfo(long requestId, boolean displayBillingInfo) {

        final String logSM = "setDisplayBillingInfo(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId : " + requestId);
        }

        try {

            RequestCoreChargesDAO requestCoreChargesDao =
                            (RequestCoreChargesDAO) getDAO(DAOName.REQUEST_CORE_CHARGES);
            if (displayBillingInfo) {
                requestCoreChargesDao.updateRequestCoreChargesAsUnReleased(requestId);
            } else {
                requestCoreChargesDao.updateRequestCoreChargesAsReleased(requestId);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: Enabled the BillingInfo for RequestId:" + requestId);
            }

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            LOG.error("Exception occured in setDisplayBillingInfo",e);
            throw new ROIException(ROIClientErrorCodes.FAILED_TO_MARK_REQUEST_AS_DRAFT_RELEASE);
        }
    }

    /**
     *  This method is used to retrieve details for the prebill report with requestor name
     *
     *  @param facList,reqType,reqName,reqStatus,aging,fromDt,toDt,balance,balancecriterion,resultType
     *  @return list
     */
    @Override
    @WebMethod(exclude = true)
    public List<Object[]> retrievePrebillReportDetailsWithRequestorName(String[] facList,String[] reqType,String reqName,String[] reqStatus,String fromDt,String toDt,String balance,String balanceCriterion,String resultType)
    {
        final String logSM = "retrievePrebillReportDetailsWithRequestorName()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        try {
            RequestCoreDeliveryDAO requestCoreDeliveryDao =
                    (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);
            List<Object[]> results = new ArrayList<Object[]>();
            int flag = 0;
            Double balanceAmt = 0.0;
            if(balanceCriterion != null && !"".equalsIgnoreCase(balanceCriterion))
               balanceAmt = Double.parseDouble(balance);
            List<PrebillReportDetails> rptList = requestCoreDeliveryDao.retrievePrebillReportDetailsWithRequestorName(facList, reqType, reqName, reqStatus, fromDt, toDt, balanceAmt, balanceCriterion,resultType);
            for(PrebillReportDetails prebillReportDetails : rptList)
            {
               Object[] reportArray = { prebillReportDetails.getFacility().trim(),
                       prebillReportDetails.getRequestorType(),
                       prebillReportDetails.getRequestorName(),
                       prebillReportDetails.getRequestorPhone(),
                       prebillReportDetails.getRequestId(),
                       prebillReportDetails.getPrebillNumber(),
                       prebillReportDetails.getPrebillDate(),
                       prebillReportDetails.getPrebillAmount(),
                       prebillReportDetails.getAging()};
               results.add(flag,reportArray);
               flag++;
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" );
            }
            return results;
        }catch (ROIException e) {
            throw e;
        }
    }

    /**
     *  This method is used to retrieve details for the prebill report without requestor name
     *
     *  @param facList,reqType,reqName,reqStatus,aging,fromDt,toDt,balance,balancecriterion,resultType
     *  @return list
     */
    @Override
    @WebMethod(exclude = true)
    public List<Object[]> retrievePrebillReportDetailsWithoutRequestorName(String[] facList,String[] reqType,String[] reqStatus,String fromDt,String toDt,String balance,String balanceCriterion,String resultType)
    {
        final String logSM = "retrievePrebillReportDetailsWithoutRequestorName()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        try {
            RequestCoreDeliveryDAO requestCoreDeliveryDao =
                    (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);
            List<Object[]> results = new ArrayList<Object[]>();
            int flag = 0;
            Double balanceAmt = 0.0;
            if(balanceCriterion != null && !"".equalsIgnoreCase(balanceCriterion))
               balanceAmt = Double.parseDouble(balance);
            List<PrebillReportDetails> rptList = requestCoreDeliveryDao.retrievePrebillReportDetailsWithoutRequestorName(facList, reqType, reqStatus, fromDt, toDt, balanceAmt, balanceCriterion,resultType);
            for(PrebillReportDetails prebillReportDetails : rptList)
            {
               Object[] reportArray = { prebillReportDetails.getFacility().trim(),
                       prebillReportDetails.getRequestorType(),
                       prebillReportDetails.getRequestorName(),
                       prebillReportDetails.getRequestorPhone(),
                       prebillReportDetails.getRequestId(),
                       prebillReportDetails.getPrebillNumber(),
                       prebillReportDetails.getPrebillDate(),
                       prebillReportDetails.getPrebillAmount(),
                       prebillReportDetails.getAging()};
               results.add(flag,reportArray);
               flag++;
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" );
            }
            return results;
        }catch (ROIException e) {
            throw e;
        }
    }

    /**
     *  This method is used to retrieve details for the post payment report
     *
     *  @param facList,reqType,reqName,reqStatus,aging,fromDt,toDt,resultType
     *  @return list
     */
    @Override
    @WebMethod(exclude = true)
    public List<Object[]> retrievePostPaymentReportDetails(String[] facList,List<String> userName,String[] reqType,Date  fromDt,Date toDt,String resultType)
    {
        final String logSM = "retrievePostPaymentReportDetails()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        try {
            RequestCoreDeliveryDAO requestCoreDeliveryDao =
                    (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);
            List<Object[]> results = new ArrayList<Object[]>();
            int flag = 0;
            List<PostPaymentReportDetails> rptList =
                    requestCoreDeliveryDao.retrievePostPaymentReportDetails(facList,userName,reqType,fromDt,toDt,resultType);
            if (rptList != null && rptList.size() > 0){
                for(int i = 0; i < rptList.size(); i++ ){
                    Object[] reportArray = {
                            rptList.get(i).getFacility() != null ? rptList.get(i).getFacility().trim() : "",
                            rptList.get(i).getCreatedDate(),
                            rptList.get(i). getUserName(),
                            rptList.get(i).getRequestorType(),
                            rptList.get(i). getRequestorName(),
                            rptList.get(i). getRequestId(),
                            rptList.get(i).getMrn() != null ? rptList.get(i).getMrn().trim() : "",
                            rptList.get(i).getInvoiceNumber() ,
                            rptList.get(i). getPaymentMethod(),
                            rptList.get(i).getPaymentDetails(),
                            rptList.get(i).getPaymentId(),
                            rptList.get(i). getPaymentAmount()};
                    results.add(flag, reportArray);
                    flag++;
                }
            }
             return results;
        } catch (ROIException e) {
            throw e;
        }
    }

    /**
     * apply the unapplied amount to the invoice
     *
     * @param requestId
     * @param invoiceId
     */
    @Override
    public void autoApplyToInvoice(long requestId, long invoiceId) {

        final String logSM = "autoApplyToInvoice()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + "RequestID: " + requestId + "Invoice ID: " + invoiceId);
        }
        
        BillingCoreServiceValidator validator = new BillingCoreServiceValidator();
        if (!validator.validateAutoApplyInvoice(requestId, invoiceId)) {
            throw validator.getException();
        }
        

        try {            

            RequestCoreDeliveryDAO rCDeliveryDAO =
                    (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);
            
            Timestamp date  = rCDeliveryDAO.getDate();
            RequestCoreDeliveryCharges invoiceInfo = 
                                    rCDeliveryDAO.retrieveDeliveryChargesUsingInvoiceId(invoiceId);
            
            if (null == invoiceInfo) {
                throw new ROIException(ROIClientErrorCodes.INVALID_INVOICE_ID, 
                                       "Invoice Id is invalid and it does not have any invoice");
            }
            
            //To calculate Unapplied adjustment and Unapplied Payment
            RequestorDAO requestorDAO = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            RequestorUnappliedAmountDetailsList unAppliedAmounts =
                                        requestorDAO.retrieveUnappliedAmountDetails(requestId);
            //US16834 changes to Include requests in the pre-bill status on the payments popup.
            if (CollectionUtilities.isEmpty(unAppliedAmounts.getRequestorUnappliedAmountDetails())) {
                
                LOG.debug("No Unapplied Amount OR Invoice Balance is Zero");
                return;
            }
            
            double totalUnAppliedAmount = 0;
            double amountToApply = 0;
            
            
            List<RequestorAdjustment> reqAdjList = 
                                        requestorDAO.retrieveRequestorAdjustmentDetails(requestId);
            
            List<RequestorAdjustmentsPayments> reqPayList = 
                                        requestorDAO.retrieveRequestorPaymentDetails(requestId);
            
            if (CollectionUtilities.isEmpty(reqAdjList) && CollectionUtilities.isEmpty(reqPayList)) {
                return;
            }
            
            //To calculate the total unApplied amount
            for (RequestorUnappliedAmountDetails unAppliedAmountDetails : unAppliedAmounts
                    .getRequestorUnappliedAmountDetails()) {                
                totalUnAppliedAmount += unAppliedAmountDetails.getAmount();                
            }
            
            //To calculate the total applied amount to invoice
			//US16834 changes to Include requests in the pre-bill status on the payments popup(Wrote a seperate case for prebill).
            if (totalUnAppliedAmount >= invoiceInfo.getInvoiceBalanceDue() && !"Prebill".equalsIgnoreCase(invoiceInfo.getType())) {
                
                amountToApply = invoiceInfo.getInvoiceBalanceDue();
                invoiceInfo.setInvoiceBalanceDue(0.00);
            } else if ("Prebill".equalsIgnoreCase(invoiceInfo.getType())) {
                amountToApply = totalUnAppliedAmount;
              //invoiceInfo.setInvoiceBalanceDue(0.00);
                invoiceInfo.setInvoiceBalanceDue(invoiceInfo.getBalanceDue());
            }
            else {
                amountToApply = totalUnAppliedAmount;
                invoiceInfo.setInvoiceBalanceDue(invoiceInfo.getInvoiceBalanceDue() - amountToApply);
            }
            
            LOG.debug("Total UnApplied Amount : " + totalUnAppliedAmount);
            LOG.debug("Total Applied Amount To Invoice : " + amountToApply);
            
            //Applying the unapplied adjustments to invoice
            amountToApply = applyAdjustmentsToInvoice(reqAdjList, amountToApply, date, invoiceInfo);
                      
            if (amountToApply > 0.0) {    
                
                //Applying the unapplied payments to invoice
                applyPaymentsToInvoice(reqPayList, amountToApply, date, invoiceInfo);               
            }
            
            rCDeliveryDAO.updateInvoiceBalance(invoiceId, invoiceInfo.getInvoiceBalanceDue(),
                                               date, getUser());
            
            //Event the Closed Invoice operation
            if (invoiceInfo.getInvoiceBalanceDue() == 0) {
                
                LOG.debug("Invoice is closed");
                createRequestEvent(TYPE.INVOICE_CLOSED.name(), "Invoice " + invoiceId + " closed.", 
                                   invoiceInfo.getRequestCoreId());
            }
            
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" );
            }
            
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            LOG.error("Exception occured in autoApplyToInvoice",e);
            throw new ROIException(ROIClientErrorCodes.FAILED_TO_CREATE_AUTO_APPLY_TO_INVOICE);
        }
    }
    
    /**
     * Update Invoice Balance
     *
     * @param invoiceId
     * @param invoiceBalance
     */
    @Override
    public void updateInvoiceBalance(long invoiceId, double invoiceBalance) {
        final String logSM = "updateInvoiceBalance()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + "invoiceBalance: " + invoiceBalance + "Invoice ID: " + invoiceId);
        }
       
        try {  
            RequestCoreDeliveryDAO rCDeliveryDAO =
                    (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);
            
            rCDeliveryDAO.updateInvoiceBalance(invoiceId, invoiceBalance,
                    rCDeliveryDAO.getDate(), getUser());
            
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" );
            }
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            LOG.error("Exception occured in updateInvoiceBalance",e);
            throw new ROIException(ROIClientErrorCodes.FAILED_TO_UPDATE_INVOICE_BALANCE);
        }
    }

    /**
     * This method is to create event details
     *
     * @param eventType
     *            Event Type Name
     * @param comment
     *            The Event Description
     * @param requestId
     *            request id
     */
    private void createRequestEvent(String eventType, String comment, long requestId) {

        final String logSM = "createRequestEvent(eventType, comment, requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Event Type : = " + eventType);
        }

        RequestCoreDAO requestDAO = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
        Timestamp date = requestDAO.getDate();
        RequestEvent event = new RequestEvent();
        event.setRequestId(requestId);
        setDefaultDetails(event, date, true);
        event.setType(eventType);
        event.setDescription(comment);
        requestDAO.createRequestEvent(event);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Event Type : = " + eventType);
        }
    }
    
    /**
     * This method will apply adjustments to invoice
     *
     * @param reqAdjList
     *            List of requestor adjustments
     * @param amountToApply
     *            The total applied amount to invoice
     * @param date
     *            The date of applying to invoice
     * @param invoiceInfo
     *            The invoice information
     */
    private double applyAdjustmentsToInvoice(
            List<RequestorAdjustment> reqAdjList, double amountToApply,
            Timestamp date, RequestCoreDeliveryCharges invoiceInfo) {
        
        double adjUnAppliedAmt = 0;
        double amtApplied = 0;
        long adjMappingId = 0;
        String billingLocation = StringUtilities.safeTrim(invoiceInfo.getInvoiceBillinglocName());
        
        AdjustmentInfo adjustmentInfo = new AdjustmentInfo();
        RequestorDAO requestorDAO = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
        JournalService journalService = (JournalService) getService(ServiceName.JOURNEL_SERVICE);
        
        if (CollectionUtilities.isEmpty(reqAdjList)) {
            return amountToApply;
        }
        
        for (RequestorAdjustment reqAdj : reqAdjList) {
            
            if (amountToApply > 0.0) {
                
                adjUnAppliedAmt = reqAdj.getUnappliedAmount();
                if (adjUnAppliedAmt > amountToApply) {
                    
                    amtApplied = amountToApply;
                    reqAdj.setUnappliedAmount(adjUnAppliedAmt - amtApplied);
                    reqAdj.setAmount(reqAdj.getAmount());
                    amountToApply = 0.0;
                } else {
                    
                    amtApplied = adjUnAppliedAmt;
                    reqAdj.setUnappliedAmount(0.0);
                    reqAdj.setAmount(reqAdj.getAmount());
                    amountToApply -= amtApplied;
                }
                
                requestorDAO.updateRequestorAdjustmentDetails(reqAdj.getInvoiceSeq(),
                        reqAdj.getAmount(),
                        reqAdj.getUnappliedAmount(),
                        date,
                        getUser());
                
                reqAdj.setCreatedBy(getUser().getInstanceId());
                reqAdj.setCreatedDt(date);
                reqAdj.setModifiedBy(getUser().getInstanceId());
                reqAdj.setModifiedDt(date);
                adjustmentInfo.setRequestorAdjustment(reqAdj);
                adjMappingId = requestorDAO.saveDeliveryChargesMapping(adjustmentInfo,
                                                   amtApplied, invoiceInfo.getId(), reqAdj.getInvoiceSeq());
                
                RequestorInvoice requestorInvoice = new RequestorInvoice();
                requestorInvoice.setRequestId(invoiceInfo.getRequestCoreId());
                requestorInvoice.setAppliedAmount(amtApplied);
                requestorInvoice.setApplyAmount(amtApplied);
                requestorInvoice.setId(invoiceInfo.getId());
                
                //Audits the Requestor Apply Adjustment operation
                AdjustmentType adjustmentType = reqAdj.getAdjustmentType();
                doAudit(requestorInvoice.constructApplyAdjustmentAuditComment(
                        reqAdj.getRequestorSeq(), getUser().getFullName(),
                        adjustmentType.toString()),
                        getUser().getInstanceId(), date,
                        ROIConstants.AUDIT_ACTION_CODE_ROI_APPLY_ADJ,
                        billingLocation, null, null);
                
                //Event the Requestor Apply Adjustment operation
                createRequestEvent(TYPE.ADJUSTMENT_APPLIED.name(), requestorInvoice
                        .constructApplyAdjustmentEventComment(adjustmentType.toString()),
                        invoiceInfo.getRequestCoreId());
                
                //Adjustment applied - Add Corresponding Journal entries for the invoice
                journalService.createApplyAdjustmentToInvoiceJE(adjMappingId);
                
            }
        }
        
        return amountToApply;
    }
    
    /**
     * This method will apply adjustments to invoice
     *
     * @param reqPayList
     *            List of requestor payments
     * @param amountToApply
     *            The total applied amount to invoice
     * @param date
     *            The date of applying to invoice
     * @param invoiceInfo
     *            The invoice information
     */
    private double applyPaymentsToInvoice(
            List<RequestorAdjustmentsPayments> reqPayList, double amountToApply,
            Timestamp date, RequestCoreDeliveryCharges invoiceInfo) {
        
        double amtApplied = 0;
        long payMappingId = 0;
        String billingLocation = StringUtilities.safeTrim(invoiceInfo.getInvoiceBillinglocName());
        
        RequestorDAO requestorDAO = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
        JournalService journalService = (JournalService) getService(ServiceName.JOURNEL_SERVICE);
        
        if (CollectionUtilities.isEmpty(reqPayList)) {
            return amountToApply;
        }
        
        double payUnAppliedAmt;
        for (RequestorAdjustmentsPayments reqPay : reqPayList) {
            
            if (amountToApply > 0.0) {
                
                payUnAppliedAmt = reqPay.getUnAppliedAmt();
                if (payUnAppliedAmt > amountToApply) {
                    
                    amtApplied = amountToApply;
                    reqPay.setUnAppliedAmt(payUnAppliedAmt - amtApplied);
                    amountToApply = 0.0;
                } else {
                    
                    amtApplied = payUnAppliedAmt;
                    reqPay.setUnAppliedAmt(0.0);
                    amountToApply -= amtApplied;
                }
                
                requestorDAO.updateRequestorPaymentDetails(reqPay.getId().longValue(),
                        reqPay.getUnAppliedAmt(),
                        date,
                        getUser());
                RequestorPayment requestorPay = new RequestorPayment();
                requestorPay.setRequestCoreDeliveryChargesId(invoiceInfo.getId());
                requestorPay.setPaymentId(reqPay.getId());
                requestorPay.setCreatedBy(getUser().getInstanceId());
                requestorPay.setCreatedDt(date);
                requestorPay.setModifiedBy(getUser().getInstanceId());
                requestorPay.setModifiedDt(date);
                requestorPay.setLastAppliedAmount(amtApplied);
                requestorPay.setTotalAppliedAmount(amtApplied);
                requestorPay.setRequestId(invoiceInfo.getRequestCoreId());
                if ("Prebill".equalsIgnoreCase(invoiceInfo.getType())) {
                    requestorPay.setPrebillPayment(true);
                } 
                payMappingId = requestorDAO.createInvoiceToPayment(requestorPay);
                
                //Audits the Requestor Apply Payment operation
                doAudit(requestorPay.constructApplyPaymentAuditComment(
                        reqPay.getPaymentMethod(), 
                        invoiceInfo.getRequestCoreId()),
                        getUser().getInstanceId(), date,
                        ROIConstants.AUDIT_ACTION_CODE_ROI_APPLY_PYMT,
                        billingLocation, null, null);
                
                //Event the Requestor Apply Payment operation
                createRequestEvent(TYPE.PAYMENT_APPLIED.name(),
                                   requestorPay.constructApplyPaymentEventComment(
                                                                reqPay.getPaymentMethod(), 
                                                                getUser().getFullName()), 
                                                                invoiceInfo.getRequestCoreId());
                
                //Payment applied - Add Corresponding Journal entries for the invoice
				//US16834 changes to Include requests in the pre-bill status on the payments popup.
                if(!"Prebill".equalsIgnoreCase(invoiceInfo.getType()))
                {
                    journalService.createApplyPaymentToInvoiceJE(payMappingId);
                }
            }
        }
        
        return amountToApply;
    }
    
    private void appliedToUnappliedPaymentPrebill (double totalPostPrebillPayments, double totalPostPrebillAdjustments, long requestCoreId) {
        RequestorDAO requestorDAO = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
        RequestCoreDeliveryDAO rCDeliveryDAO =
                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);
        RequestorPrebillsList reqPrebillList = new RequestorPrebillsList();
        JournalService journalService = (JournalService) getService(ServiceName.JOURNEL_SERVICE);
        if (totalPostPrebillPayments != 0.0 || totalPostPrebillAdjustments != 0.0) {
            reqPrebillList = requestorDAO.retrieveRequestorPrebills(requestCoreId);
        }
        Timestamp date = requestorDAO.getDate();
        if (null != reqPrebillList) {
            List<RequestorPrebill> requestorPrebill = reqPrebillList.getRequestorPrebills();
            if (CollectionUtilities.hasContent(requestorPrebill)) {
                for (RequestorPrebill reqPrebill : requestorPrebill) {
                     double invoiceBalance = 0.0;
                     invoiceBalance += reqPrebill.getBalance();
                     List<RequestorAdjustmentsPayments> reqAdjPayList = reqPrebill.getRequestorAdjPay();
                     for (RequestorAdjustmentsPayments reqAdjPay : reqAdjPayList) {
                          if ("Payment".equalsIgnoreCase(reqAdjPay.getTxnType())) {
                              RequestorPaymentList paymentInfoList = new RequestorPaymentList();
                              RequestorPayment paymentInfo = new RequestorPayment();
                              paymentInfoList.setPaymentAmount(reqAdjPay.getAppliedAmount());
                              paymentInfoList.setUnAppliedAmount(reqAdjPay.getAppliedAmount());
                              paymentInfoList.setPaymentMode(reqAdjPay.getPaymentMethod());
                              paymentInfoList.setDescription(reqAdjPay.getDescription());
                              paymentInfoList.setPaymentDate(date);  
                              paymentInfoList.setRequestorId(reqAdjPay.getRequestorId());
                              paymentInfoList.setPaymentId(reqAdjPay.getId());
                              setDefaultDetails(paymentInfoList, date, true);
                              paymentInfo.setRequestCoreDeliveryChargesId(reqPrebill.getId());
                              paymentInfo.setPaymentId(reqAdjPay.getId());
                              requestorDAO.deleteInvoiceToPayment(paymentInfo);
                              long paymentId = requestorDAO.createRequestorPayment(paymentInfoList);
                              invoiceBalance += reqAdjPay.getAppliedAmount();
                              journalService.createDeletePrebillPaymentJE(paymentId);
                          } else {
                              RequestorAdjustment adjustmentInfo = new RequestorAdjustment();
                              adjustmentInfo.setAmount(reqAdjPay.getAppliedAmount());
                              adjustmentInfo.setUnappliedAmount(reqAdjPay.getAppliedAmount());
                              if (AdjustmentType.CUSTOMER_GOODWILL_ADJUSTMENT.toString().equalsIgnoreCase(reqAdjPay.getPaymentMethod())) {
                                  adjustmentInfo.setAdjustmentType(AdjustmentType.CUSTOMER_GOODWILL_ADJUSTMENT);
                              } else if (AdjustmentType.BAD_DEBT_ADJUSTMENT.toString().equalsIgnoreCase(reqAdjPay.getPaymentMethod())) {
                                  adjustmentInfo.setAdjustmentType(AdjustmentType.BAD_DEBT_ADJUSTMENT);
                              } else {
                                  adjustmentInfo.setAdjustmentType(AdjustmentType.BILLING_ADJUSTMENT);
                              }
                              adjustmentInfo.setAdjustmentDate(date);  
                              adjustmentInfo.setRequestorSeq(reqAdjPay.getRequestorId());
                              adjustmentInfo.setDelete(false);
                              adjustmentInfo.setId(reqAdjPay.getId());
                              adjustmentInfo.setNote(reqAdjPay.getDescription());
                              setDefaultDetails(adjustmentInfo, date, true);
                              requestorDAO.deleteMappedInvoicesByAdjustmentAndInvoiceId(reqAdjPay.getId(), reqPrebill.getId());
                              //requestorDAO.createRequestorPayment(adjustmentInfo);
                              requestorDAO.saveAdjustmentInfo(adjustmentInfo);
                              invoiceBalance += reqAdjPay.getAppliedAmount();
                             /* createJournalEntryForAdjustment(
                                      ROIConstants.UNAPPLY_ADJ_FROM_INVOICE, adjustmentInfo.getAdjustmentType(), reqAdjPay.getId());
                              createJournalEntryForAdjustment(
                                      ROIConstants.CREATE_ADJUSTMENT, adjustmentInfo.getAdjustmentType(), reqAdjPay.getId());*/
                          }
                     }
                     rCDeliveryDAO.updateInvoiceBalance(reqPrebill.getId(), invoiceBalance, date, getUser());
                }
            }
            
        }
    }
}
