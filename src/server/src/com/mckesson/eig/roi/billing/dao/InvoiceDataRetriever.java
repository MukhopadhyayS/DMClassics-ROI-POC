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

package com.mckesson.eig.roi.billing.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.dao.BaseLetterDataRetriever;
import com.mckesson.eig.roi.billing.letter.model.BillingInfo;
import com.mckesson.eig.roi.billing.letter.model.Charge;
import com.mckesson.eig.roi.billing.letter.model.ChargeItem;
import com.mckesson.eig.roi.billing.letter.model.InvoiceCharge;
import com.mckesson.eig.roi.billing.letter.model.LetterData;
import com.mckesson.eig.roi.billing.letter.model.Note;
import com.mckesson.eig.roi.billing.letter.model.ReleaseInfo;
import com.mckesson.eig.roi.billing.letter.model.RequestItem;
import com.mckesson.eig.roi.billing.letter.model.RequestorInfo;
import com.mckesson.eig.roi.billing.letter.model.ShippingInfo;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryCharges;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesAdjustmentPayment;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesDocument;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesFee;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesShipping;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Aug 31, 2009
 * @since  HPF 13.1 [ROI]; Nov 19, 2008
 */
@Transactional
public class InvoiceDataRetriever
extends BaseLetterDataRetriever {

    private static final OCLogger LOG = new OCLogger(InvoiceDataRetriever.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private boolean _prebill;
    protected static final String BILL_DATE_FORMAT = "MM/dd/yyyy";
    

    public boolean isPrebill() {
        return _prebill;
    }

    public void setPrebill(boolean prebill) {
        _prebill = prebill;
    }

    /**
     *  This method is to retrieve the data required to generate invoice or prebill
     *  for the given invoice id
     *
     */
    @Override
    public Object retrieveLetterData(long invoiceId, long requestId) {


        final String logSM = "retrieveLetterData(InvoiceId, RequestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: InvoiceId:" + invoiceId
                            + ", RequestId:" + requestId);
        }
        RequestCoreDeliveryCharges invoiceDetails = retrieveInvoiceDetails(invoiceId);
        Object letterData = constructTemplateDataModel(invoiceDetails);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End");
        }

        return letterData;
    }

    /**
     * @see com.mckesson.eig.roi.base.dao.LetterDataRetriever
     * #constructTemplateDataModel(java.lang.Object)
     *
     */
    @Override
    public Object constructTemplateDataModel(Object requestCoreDeliverCharge) {

        RequestCoreDeliveryCharges invoice = (RequestCoreDeliveryCharges) requestCoreDeliverCharge;
        LetterData letterData = new LetterData();

        // Retrieve all patients for the request
        Set<RequestPatient> patients = invoice.getPatients();
        List<RequestItem> masterPatientsList = new ArrayList<RequestItem>();
        RequestItem reqItm = new RequestItem();
        if (patients != null && patients.size() > 0) {
            List<RequestItem> patientsList = new ArrayList<RequestItem>();
            for (RequestPatient patient : patients) {
                RequestItem reqItem = new RequestItem();
                reqItem.setEpn(patient.getEpn());
                reqItem.setDob(patient.getDob());
                reqItem.setFacility(patient.getFacility());
                reqItem.setMrn(patient.getMrn());
                reqItem.setName(patient.getName());
                reqItem.setSsn(patient.getSsn());
                reqItem.setEncounterFacility(patient.getFacility());
                patientsList.add(reqItem);
            }
            reqItm.setRequestItems(patientsList);
        }
        masterPatientsList.add(reqItm);
        letterData.setPatientsList(masterPatientsList);

        RequestorCore requestor = invoice.getRequestorCore();
        if (requestor != null) {
            RequestorInfo reqInfo = new RequestorInfo();
            reqInfo.setId(String.valueOf(requestor.getId()));

            String name = "";
            if (null != requestor.getLastName()) {
                name = requestor.getLastName();
            }

            if (null != requestor.getFirstName()) {
                name = name + ", " + requestor.getFirstName();
            }
            if (null != requestor.getMiddleName()) {
                name = name + " " + requestor.getMiddleName();

            }
            reqInfo.setName(name);
            reqInfo.setPhone(requestor.getWorkPhone());
            reqInfo.setType(requestor.getRequestorTypeName());
            reqInfo.setAddress1(requestor.getMainAddress1());
            reqInfo.setAddress2(requestor.getMainAddress2());
            reqInfo.setAddress3(requestor.getMainAddress3());
            reqInfo.setCity(requestor.getMainCity());
            reqInfo.setState(requestor.getMainState());
            reqInfo.setPostalCode(requestor.getMainPostalCode());
            reqInfo.setCountry(requestor.getMainCountryName());
            reqInfo.setCountryCode(requestor.getMainCountryCode());
            
            letterData.setRequestor(reqInfo);
        }

        RequestCoreDeliveryChargesShipping rcdChargesShipping = invoice.getShippingDetails();
        ShippingInfo shippingInfo = new ShippingInfo();
        if (null != rcdChargesShipping) {

            shippingInfo.setAddress1(rcdChargesShipping.getAddress1());
            shippingInfo.setAddress2(rcdChargesShipping.getAddress2());
            shippingInfo.setAddress3(rcdChargesShipping.getAddress3());
            shippingInfo.setAddressType(rcdChargesShipping.getAddressType());
            shippingInfo.setCity(rcdChargesShipping.getCity());
            shippingInfo.setCountryCode(rcdChargesShipping.getCountryCode());
            shippingInfo.setOutputMethod(rcdChargesShipping.getOutputMethod());
            shippingInfo.setPostalCode(rcdChargesShipping.getPostalCode());
            shippingInfo.setShippingMethod(rcdChargesShipping.getShippingMethod());
            shippingInfo.setShippingURL(rcdChargesShipping.getShippingUrl());
            shippingInfo.setShippingWt(String.valueOf(rcdChargesShipping.getShippingWeight()));
            shippingInfo.setState(rcdChargesShipping.getState());
            shippingInfo.setTrackingNumber(rcdChargesShipping.getTrackingNumber());
        }

        BillingInfo billingInfo = new BillingInfo();
        billingInfo.setShippingInfo(shippingInfo);

        letterData.setInvoicePrebillDate(formatDate(invoice.getCreatedDt(), BILL_DATE_FORMAT));
        letterData.setInvoiceId(String.valueOf(invoice.getId()));
        letterData.setRequestDt(formatDate(invoice.getRequestDate(), BILL_DATE_FORMAT));

        List<ReleaseInfo> relInfoList = new ArrayList<ReleaseInfo>();
        ReleaseInfo relInfo = new ReleaseInfo();
		//Modified for CR#374866
        if ("PreBill".equalsIgnoreCase(invoice.getType())) {
            relInfo.setBalanceDue(formatToCurrency(invoice.getBaseCharge()));
        } else {
            relInfo.setBalanceDue(formatToCurrency(invoice.getInvoiceBalanceDue()));  
        }
        relInfo.setPreviouslyReleasedCost(formatToCurrency(invoice.getPreviouslyReleasedCost()));
        relInfo.setReleaseCost(formatToCurrency(invoice.getReleaseCost()));

        Date releaseDate = invoice.getReleaseDate();
        relInfo.setReleaseDt((null == releaseDate) ? null : String.valueOf(releaseDate));

        relInfo.setTotalPagesReleased(String.valueOf(invoice.getTotalPagesReleased()));
        relInfo.setTotPages(String.valueOf(invoice.getTotalPages()));
        relInfo.setTotalCost(formatToCurrency(invoice.getTotalRequestCost()));
        relInfoList.add(relInfo);

        InvoiceCharge invCharge = new InvoiceCharge();
        invCharge.setBaseCharge(formatToCurrency(invoice.getBaseCharge()));
        invCharge.setInvoiceBalanceDue(formatToCurrency(invoice.getInvoiceBalanceDue()));
        invCharge.setAmountPaid(formatToCurrency(invoice.getAmountpaid()));
        invCharge.setInvoiceBillingLocation(invoice.getInvoiceBillinglocName());
        invCharge.setInvoiceDate(formatDate(invoice.getCreatedDt(), BILL_DATE_FORMAT));
        invCharge.setInvoiceSalesTax(formatToCurrency(invoice.getInvoiceSalesTax()));
        invCharge.setOverDueDays(String.valueOf(invoice.getInvoiceDueDays()));

        //Document
        List<Charge> docChargeList = new ArrayList<Charge>();
        List<ChargeItem> docItemsList = new ArrayList<ChargeItem>();
        Charge docCharge = new Charge();

        Set<RequestCoreDeliveryChargesDocument> rcdChargesDocument = invoice.getDocumentCharges();
        if (null != rcdChargesDocument) {

            Iterator<RequestCoreDeliveryChargesDocument> it = rcdChargesDocument.iterator();
            while (it.hasNext()) {
                RequestCoreDeliveryChargesDocument rcdcDocument = it.next();
                ChargeItem dcItem = new ChargeItem();
                dcItem.setAmount(formatToCurrency(rcdcDocument.getAmount()));
                dcItem.setCopies(String.valueOf(rcdcDocument.getCopies()));
                dcItem.setName(rcdcDocument.getBillingTierName());
                dcItem.setBillingTierName(rcdcDocument.getBillingTierName());
                dcItem.setPages(String.valueOf(rcdcDocument.getPages()));
                dcItem.setBillingTierId(rcdcDocument.getBillingtierId());
                docItemsList.add(dcItem);
            }
        }
        docCharge.setChargeItems(docItemsList);
        docChargeList.add(docCharge);

        //Fee
        List<Charge> feeChargeList = new ArrayList<Charge>();
        List<ChargeItem> feeItemsList = new ArrayList<ChargeItem>();
        Charge feeCharge = new Charge();
        Set<RequestCoreDeliveryChargesFee> rcdChargesFee = invoice.getFeeCharge();
        if (null != rcdChargesFee) {

            Iterator<RequestCoreDeliveryChargesFee> it = rcdChargesFee.iterator();
            while (it.hasNext()) {
                RequestCoreDeliveryChargesFee rcdcFee = it.next();
                ChargeItem fcItem = new ChargeItem();
                fcItem.setAmount(formatToCurrency(rcdcFee.getAmount()));
                fcItem.setName(rcdcFee.getFeeType());
                fcItem.setFeeType(rcdcFee.getFeeType());
                feeItemsList.add(fcItem);
            }
        }
        feeCharge.setChargeItems(feeItemsList);
        feeChargeList.add(feeCharge);

        //Txn
        List<Charge> txnChargeList = new ArrayList<Charge>();
        List<ChargeItem> txnItemsList = new ArrayList<ChargeItem>();
        Charge txnCharge = new Charge();
        double totalCharge = 0.00;
        double adjTotal = 0.00;
        double payTotal = 0.00;

        Set<RequestCoreDeliveryChargesAdjustmentPayment> rcdAdjPayList
                                                        = invoice.getAdjustmentsAndPayments();
        if (null != rcdAdjPayList) {

            Iterator<RequestCoreDeliveryChargesAdjustmentPayment> it = rcdAdjPayList.iterator();
            while (it.hasNext()) {

                RequestCoreDeliveryChargesAdjustmentPayment rcdcAdjPay = it.next();
                ChargeItem tcItem = new ChargeItem();
                tcItem.setAmount(formatToCurrency(rcdcAdjPay.getInvoiceAppliedAmount()));
                tcItem.setTransactionType(rcdcAdjPay.getTransactionType());
                tcItem.setType(rcdcAdjPay.getTransactionType());
                tcItem.setDebt(String.valueOf(rcdcAdjPay.getIsDebit()));
                tcItem.setPaymentMode(rcdcAdjPay.getPaymentMode());
                tcItem.setDescription(rcdcAdjPay.getDescription());
                tcItem.setDate(formatDate(rcdcAdjPay.getPaymentDate(), BILL_DATE_FORMAT));
                txnItemsList.add(tcItem);
                totalCharge = totalCharge + rcdcAdjPay.getInvoiceAppliedAmount();

                if (ROIConstants.ADJUSTMENT_TYPE
                               .equalsIgnoreCase(rcdcAdjPay.getTransactionType())) {
                    adjTotal = adjTotal + rcdcAdjPay.getInvoiceAppliedAmount();

                } else if (ROIConstants.PAYMENT_TYPE
                                    .equalsIgnoreCase(rcdcAdjPay.getTransactionType())) {

                    payTotal = payTotal + rcdcAdjPay.getInvoiceAppliedAmount();
                }
            }
        }
        txnCharge.setChargeItems(txnItemsList);
        txnCharge.setPaymentTotal(formatToCurrency(payTotal));
        txnCharge.setAdjustmentTotal(formatToCurrency(adjTotal));
        txnCharge.setAdjustmentPaymentTotal(formatToCurrency(totalCharge));
        txnCharge.setCreditAdjustmentTotal(formatToCurrency(invoice.getCreditAdjustmentAmount()));
        txnCharge.setDebitAdjustmentTotal(formatToCurrency(invoice.getDebitAdjustmentAmount()));
        txnChargeList.add(txnCharge);

        //Ship
        List<Charge> shipChargeList = new ArrayList<Charge>();
        List<ChargeItem> sxnItemsList = new ArrayList<ChargeItem>();
        Charge shipCharge = new Charge();

        ChargeItem scItem = new ChargeItem();
        scItem.setAmount((null == rcdChargesShipping) ? "0.00"
                                : formatToCurrency(rcdChargesShipping.getShippingCharge()));
        sxnItemsList.add(scItem);
        shipCharge.setCharges((null == rcdChargesShipping) ? "0.00"
                                : formatToCurrency(rcdChargesShipping.getShippingCharge()));

        shipCharge.setChargeItems(sxnItemsList);

        shipChargeList.add(shipCharge);

        String notestring = invoice.getNotes();
        if (StringUtilities.hasContent(notestring)) {

            String[] notes = notestring.split(ROIConstants.FIELD_SEPERATOR);
            List<Note> notesList = new ArrayList<Note>();
            Note notesTemp = new Note();
            List<Note> noteList = new ArrayList<Note>();
            for (String noteDesc : notes) {

                Note note = new Note();
                note.setDescription(noteDesc);
                noteList.add(note);
            }
            notesTemp.setChildItems(noteList);
            notesList.add(notesTemp);

            letterData.setNotesList(notesList);
        }

        List<Charge> chargeList = new ArrayList<Charge>();
        Charge charge = new Charge();
        charge.setReleases(relInfoList);
        charge.setInvoices(invCharge);
        charge.setDocCharge(docChargeList);
        charge.setFeeCharge(feeChargeList);
        charge.setTxns(txnChargeList);
        charge.setShippingCharge(shipChargeList);
        chargeList.add(charge);
        billingInfo.setCharges(chargeList);

        letterData.setRequestId(String.valueOf(invoice.getRequestCoreId()));
        letterData.setBillingInfo(billingInfo);
        letterData.setTemplateFileId(String.valueOf(invoice.getLetterTemplateFileId()));
        letterData.setTemplateName(invoice.getLetterTemplateName());
        letterData.setInvoiceDueDate(formatDate(invoice.getInvoiceDueDate(), BILL_DATE_FORMAT));
        if(ROIConstants.INVOICE.equalsIgnoreCase(invoice.getType()))
            letterData.setRequestBalanceDue(formatToCurrency(invoice.getInvoiceBalanceDue()));
         else
            letterData.setRequestBalanceDue(formatToCurrency(invoice.getBalanceDue())); 
        letterData.setRequestCreditAmount(formatToCurrency(invoice.getRequestCreditAdjustment()));
        letterData.setRequestDebitAmount(formatToCurrency(invoice.getRequestDebitAdjustment()));
        letterData.setRequestPaymentAmount(formatToCurrency(invoice.getRequestpayment()));
        letterData.setRequestOriginalBalance(formatToCurrency(invoice.getOriginalBalance()));
        return letterData;
    }


    /**
     * This method is to retrieve the invoice details for the given invoice id
     * @param invoiceId
     * @return
     */
    private RequestCoreDeliveryCharges retrieveInvoiceDetails(long invoiceId) {

        final String logSM = "retrieveInvoiceDetails(invoiceId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Invoice Id : " + invoiceId);
        }

        rcDeliveryDAO = getRequestCoreDeliveryDAO();

        // Retrieve data from RequestCoreDeliveryCharges and Shipping Table
        RequestCoreDeliveryCharges rcdCharges =
               rcDeliveryDAO.retrieveDeliveryChargesUsingInvoiceId(invoiceId);

        RequestCoreDeliveryChargesShipping rcdChargesShipping =
               rcDeliveryDAO.retrieveRequestCoreDeliveryChargesShipping(rcdCharges.getId());
        rcdCharges.setShippingDetails(rcdChargesShipping);

        List<RequestCoreDeliveryChargesAdjustmentPayment> rcdAdjPayList =
              rcDeliveryDAO.retrieveRequestCoreDeliveryChargesAdjustmentPayment(rcdCharges.getId());
        rcdCharges.setAdjustmentsAndPayments(
                    new HashSet<RequestCoreDeliveryChargesAdjustmentPayment>(rcdAdjPayList));

        Set<RequestCoreDeliveryChargesDocument> rcdChargesDocument =
               rcDeliveryDAO.retrieveDeliveryChargesDocument(rcdCharges.getId());
        rcdCharges.setDocumentCharges(rcdChargesDocument);

        Set<RequestCoreDeliveryChargesFee> rcdChargesFee =
                                   rcDeliveryDAO.retrieveDeliveryChargesFee(rcdCharges.getId());
        rcdCharges.setFeeCharge(rcdChargesFee);

        List<Long> invoiceIds = new ArrayList<Long>();
        invoiceIds.add(invoiceId);

        // retrieves the list of invoice patients
        rcPatientDAO = getRequestCorePatientDAO();
        List<RequestPatient> patientsList =
                        rcPatientDAO.retrieveAllInvoicePatientsByInvoiceIds(invoiceIds);
        rcdCharges.setPatients(new HashSet<RequestPatient>(patientsList));

        long requestId = rcdCharges.getRequestCoreId();
        requestCoreDAO = getRequestCoreDAO();
        // Retrieve the Requestor Information for the Request
        RequestorCore requestor = requestCoreDAO.retrieveRequestor(requestId);
        rcdCharges.setRequestorCore(requestor);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }

        return rcdCharges;
    }

}
