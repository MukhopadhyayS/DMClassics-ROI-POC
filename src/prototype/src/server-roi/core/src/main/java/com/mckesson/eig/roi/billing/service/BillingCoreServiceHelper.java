/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.billing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO;
import com.mckesson.eig.roi.billing.model.CoverLetterCore;
import com.mckesson.eig.roi.billing.model.InvoiceOrPrebillAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.ReleaseCore;
import com.mckesson.eig.roi.billing.model.ReleasePages;
import com.mckesson.eig.roi.billing.model.RequestCoreDelivery;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryCharges;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesAdjustmentPayment;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesBilling;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesDocument;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesFee;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesShipping;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryInvoicePatients;
import com.mckesson.eig.roi.request.dao.RequestCoreDAO;
import com.mckesson.eig.roi.request.dao.RequestCorePatientDAO;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesBilling;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreChargesFee;
import com.mckesson.eig.roi.request.model.RequestCoreChargesShipping;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author Karthik Easwaran
 * @date   Nov 27, 2012
 * @since  Nov 27, 2012
 */
public class BillingCoreServiceHelper
extends BaseROIService {

    private static final Log LOG = LogFactory.getLogger(BillingCoreServiceHelper.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * create the feecharges related to the invoices
     * @param rCoreDelivery
     * @param requestCoreDeliveryChargesId
     */
    public void createInvoiceFeeCharges(RequestCoreDelivery rCoreDelivery,
                                         long requestCoreDeliveryChargesId) {

        Set<RequestCoreDeliveryChargesFee> feeCharges =
                                    rCoreDelivery.getRequestCoreDeliveryChargesBilling()
                                                 .getRequestCoreDeliveryChargesFee();
        if (CollectionUtilities.isEmpty(feeCharges)) {
            return;
        }

        RequestCoreDeliveryDAO rCDeliveryDAO =
                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

        Iterator<RequestCoreDeliveryChargesFee> itFee = feeCharges.iterator();
        RequestCoreDeliveryChargesFee rCDeliveryChargesFee = null;
        while (itFee.hasNext()) {

            rCDeliveryChargesFee = itFee.next();
            rCDeliveryChargesFee.setRequestCoreDeliveryChargesId(requestCoreDeliveryChargesId);
            rCDeliveryDAO.createRequestCoreDeliveryChargesFee(rCDeliveryChargesFee);
        }
    }

    /**
     * creates the docuemnt charges related to the invoice
     * @param rCoreDelivery
     * @param date
     * @param requestCoreDeliveryChargesId
     */
    public void createInvoiceDocumentCharge(RequestCoreDelivery rCoreDelivery,
                                             Date date,
                                             long requestCoreDeliveryChargesId) {

        RequestCoreDeliveryDAO rCDeliveryDAO =
                                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

        Set<RequestCoreDeliveryChargesDocument> documentCharges = rCoreDelivery
                                                           .getRequestCoreDeliveryChargesBilling()
                                                           .getRequestCoreDeliveryChargesDocument();

        if (CollectionUtilities.isEmpty(documentCharges)) {
            return;
        }

        Iterator<RequestCoreDeliveryChargesDocument> itDocument = documentCharges.iterator();
        RequestCoreDeliveryChargesDocument rCDeliveryChargesDoc = null;
        while (itDocument.hasNext()) {

            rCDeliveryChargesDoc = itDocument.next();
            rCDeliveryChargesDoc.setRequestCoreDeliveryChargesId(requestCoreDeliveryChargesId);
            setDefaultDetails(rCDeliveryChargesDoc, date, true);
            rCDeliveryDAO.createRequestCoreDeliveryChargesDocument(rCDeliveryChargesDoc);
        }
    }

    /**
     * Creates the invoice patients
     * @param invOrPrebillPreviewInfo
     * @param date
     * @param requestCoreDeliveryChargesId
     */
    public void createInvoicePatients(InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo,
                                       Date date,
                                       long requestCoreDeliveryChargesId) {

        RequestCoreDeliveryDAO rCDeliveryDAO =
                                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

        RequestCorePatientDAO rCPatientDAO =
                                (RequestCorePatientDAO) getDAO(DAOName.REQUEST_PATIENT_DAO);

        // creating the invoice patient info for
        // requestCoreDeliveryChargesId
        List<RequestPatient> requestPatients = rCPatientDAO.retrieveAllPatientForRequest(
                                                    invOrPrebillPreviewInfo.getRequestCoreId());

        RequestCoreDeliveryInvoicePatients rCDeliveryInvPatients  = null;
        for (RequestPatient requestPatient : requestPatients) {

            rCDeliveryInvPatients = new RequestCoreDeliveryInvoicePatients();
            rCDeliveryInvPatients.setRequestCoreDeliveryChargesId(requestCoreDeliveryChargesId);

            setDefaultDetails(rCDeliveryInvPatients, date, true);
            long patientSeq = requestPatient.getPatientSeq();
            if (requestPatient.isHpf()) {

                rCDeliveryInvPatients.setRequestHpfPatientsId(patientSeq);
                rCDeliveryInvPatients.setRequestNonHpfPatientsId(null);
            } else {

                rCDeliveryInvPatients.setRequestNonHpfPatientsId(patientSeq);
                rCDeliveryInvPatients.setRequestHpfPatientsId(null);
            }

            rCDeliveryDAO.createInvoicePatients(rCDeliveryInvPatients);
        }
    }

    /**
     * creates the auto adjustment if any
     *
     * @param invOrPrebillPreviewInfo
     * @param date
     * @param requestCoreDeliveryChargesId
     */
    public void createInvoiceAutoAdjustments(
                                List<RequestCoreDeliveryChargesAdjustmentPayment> autoAdjustments,
                                Date date,
                                long requestCoreDeliveryChargesId) {


        if (CollectionUtilities.isEmpty(autoAdjustments)) {
            return;
        }

        /*RequestCoreDeliveryDAO rCDeliveryDAO =
                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

        for (RequestCoreDeliveryChargesAdjustmentPayment adjPay : autoAdjustments) {

            adjPay.setRequestCoreDeliveryChargesId(requestCoreDeliveryChargesId);
            setDefaultDetails(adjPay, date, true);
            rCDeliveryDAO.createRequestCoreDeliveryChargesAdjustmentPayment(adjPay);
        }*/
    }


    public void setvaluesFromBillingInfotoRequestCoreDelivery(
                                                InvoiceOrPrebillAndPreviewInfo invoiceInfo,
                                                RequestCoreDeliveryCharges rCDeliveryCharges) {

        rCDeliveryCharges.setLetterTemplateName(invoiceInfo.getLetterTemplateName());
        rCDeliveryCharges.setOutputMethod(invoiceInfo.getOutputMethod());
        rCDeliveryCharges.setRequestStatus(invoiceInfo.getRequestStatus());
        rCDeliveryCharges.setRequestDate(invoiceInfo.getRequestDate());
        rCDeliveryCharges.setQueuePassword(invoiceInfo.getQueuePassword());
        rCDeliveryCharges.setOverwriteDueDate(invoiceInfo.getOverwriteDueDate());
        rCDeliveryCharges.setType(invoiceInfo.getLetterType());
        rCDeliveryCharges.setInvoiceDueDate(invoiceInfo.getInvoiceDueDate());
        rCDeliveryCharges.setInvoicePrebillDate(invoiceInfo.getInvoicePrebillDate());
        rCDeliveryCharges.setAmountpaid(invoiceInfo.getAmountpaid());
        rCDeliveryCharges.setBaseCharge(invoiceInfo.getBaseCharge());
        rCDeliveryCharges.setInvoiceBalanceDue(invoiceInfo.getInvoiceBalanceDue());
        rCDeliveryCharges.setInvoiceSalesTax(invoiceInfo.getInvoiceSalesTax());
        rCDeliveryCharges.setResendDate(invoiceInfo.getResendDate());
        rCDeliveryCharges.setInvoiceDueDays(invoiceInfo.getInvoiceDueDays());
        rCDeliveryCharges.setInvoiceBillingLocCode(invoiceInfo.getInvoiceBillingLocCode());
        rCDeliveryCharges.setInvoiceBillinglocName(invoiceInfo.getInvoiceBillinglocName());
        rCDeliveryCharges.setLetterTemplateFileId(invoiceInfo.getLetterTemplateFileId());
        rCDeliveryCharges.setCreditAdjustmentAmount(invoiceInfo.getAdjustmentAmount());
        rCDeliveryCharges.setPaymentAmount(invoiceInfo.getPaymentAmount());
        rCDeliveryCharges.setRequestCoreId(invoiceInfo.getRequestCoreId());
        rCDeliveryCharges.setReleaseDate(invoiceInfo.getReleasedDate());
        rCDeliveryCharges.setIsReleased(invoiceInfo.isReleased());
        String note = getNoteDescriptions(invoiceInfo.getNotes());
        rCDeliveryCharges.setNotes(note);


        List<RequestCoreDeliveryChargesAdjustmentPayment> autoAdjustments =
                                                      invoiceInfo.getAutoAdjustments();
        if (CollectionUtilities.isEmpty(autoAdjustments)) {
            return;
        }

        double creditAdj = 0.00;
        double debitAdj = 0.00;
        for (RequestCoreDeliveryChargesAdjustmentPayment adjustment : autoAdjustments) {

            if (adjustment.getIsDebit()) {
                debitAdj += adjustment.getInvoiceAppliedAmount();
            } else {
                creditAdj += adjustment.getInvoiceAppliedAmount();
            }
        }

        rCDeliveryCharges.setCreditAdjustmentAmount(creditAdj);
        rCDeliveryCharges.setDebitAdjustmentAmount(debitAdj);

    }

    public void setvaluesFromRequestCoreChargesToRequestCoreDelivery(RequestCoreCharges rCCharges,
                                                           RequestCoreDelivery requestCoreDelivery,
                                                           Date date) {

         RequestCoreChargesBilling requestCoreChargesBilling =
                                                 rCCharges.getRequestCoreChargesBilling();

         if (requestCoreChargesBilling == null) {
             return;
         }

         Set<RequestCoreDeliveryChargesDocument> rCDeliveryChargesDocSet =
                                                new HashSet<RequestCoreDeliveryChargesDocument>();
         RequestCoreDeliveryChargesDocument rCDeliveryChargesDoc = null;

         Set<RequestCoreChargesDocument> requestCoreChargesDocument =
                                     requestCoreChargesBilling.getRequestCoreChargesDocument();

        Iterator<RequestCoreChargesDocument> it = requestCoreChargesDocument.iterator();

         while (it.hasNext()) {

             RequestCoreChargesDocument rCChargesDoc = it.next();

             rCDeliveryChargesDoc = new RequestCoreDeliveryChargesDocument();
             rCDeliveryChargesDoc.setAmount(rCChargesDoc.getAmount());
             rCDeliveryChargesDoc.setBillingtierId(rCChargesDoc.getBillingtierId());
             rCDeliveryChargesDoc.setBillingTierName(rCChargesDoc.getBillingTierName());
             rCDeliveryChargesDoc.setCopies(rCChargesDoc.getCopies());
             rCDeliveryChargesDoc.setPages(rCChargesDoc.getPages());
             rCDeliveryChargesDoc.setReleaseCount(rCChargesDoc.getReleaseCount());
             rCDeliveryChargesDoc.setTotalPages(rCChargesDoc.getTotalPages());
             rCDeliveryChargesDoc.setHasSalesTax(rCChargesDoc.getHasSalesTax());
             rCDeliveryChargesDoc.setSalesTaxAmount(rCChargesDoc.getSalesTaxAmount());

             setDefaultDetails(rCDeliveryChargesDoc, date, true);

             rCDeliveryChargesDocSet.add(rCDeliveryChargesDoc);
         }

         RequestCoreDeliveryChargesBilling reqDeliveryChargesBilling =
                                        requestCoreDelivery.getRequestCoreDeliveryChargesBilling();

        reqDeliveryChargesBilling.setRequestCoreDeliveryChargesDocument(rCDeliveryChargesDocSet);

         RequestCoreChargesFee rCChargesFee = null;
         Set<RequestCoreDeliveryChargesFee> rCDeliveryChargesFeeSet =
                                             new HashSet<RequestCoreDeliveryChargesFee>();

         Set<RequestCoreChargesFee> requestCoreChargesFee =
                                         requestCoreChargesBilling.getRequestCoreChargesFee();
         Iterator<RequestCoreChargesFee> itFee = requestCoreChargesFee.iterator();
         while (itFee.hasNext()) {

             rCChargesFee = itFee.next();
             RequestCoreDeliveryChargesFee rCDeliveryChargesFee =
                                                             new RequestCoreDeliveryChargesFee();

             rCDeliveryChargesFee.setHasSalesTax(rCChargesFee.getHasSalesTax());
             rCDeliveryChargesFee.setSalesTaxAmount(rCChargesFee.getSalesTaxAmount());
             rCDeliveryChargesFee.setAmount(rCChargesFee.getAmount());
             rCDeliveryChargesFee.setFeeType(rCChargesFee.getFeeType());
             rCDeliveryChargesFee.setId(rCChargesFee.getId());
             rCDeliveryChargesFee.setIsCustomFee(rCChargesFee.getIsCustomFee());

             setDefaultDetails(rCDeliveryChargesFee, date, true);
             rCDeliveryChargesFeeSet.add(rCDeliveryChargesFee);
         }

         reqDeliveryChargesBilling.setRequestCoreDeliveryChargesFee(rCDeliveryChargesFeeSet);

         RequestCoreDeliveryChargesShipping rCDCShipping =
                                     requestCoreDelivery.getRequestCoreDeliveryChargesShipping();

         RequestCoreChargesShipping rCCShipping = rCCharges.getRequestCoreChargesShipping();

         rCDCShipping.setAddress1(rCCShipping.getAddress1());
         rCDCShipping.setAddress2(rCCShipping.getAddress2());
         rCDCShipping.setAddress3(rCCShipping.getAddress3());
         rCDCShipping.setAddressType(rCCShipping.getAddressType());
         rCDCShipping.setCity(rCCShipping.getCity());
         rCDCShipping.setCountryCode(rCCShipping.getCountryCode());
         rCDCShipping.setCountryName(rCCShipping.getCountryName());
         rCDCShipping.setOutputMethod(rCCShipping.getOutputMethod());
         rCDCShipping.setPostalCode(rCCShipping.getPostalCode());
         rCDCShipping.setShippingCharge(rCCShipping.getShippingCharge());
         rCDCShipping.setShippingMethod(rCCShipping.getShippingMethod());
         rCDCShipping.setShippingMethodId(rCCShipping.getShippingMethodId());
         rCDCShipping.setShippingUrl(rCCShipping.getShippingUrl());
         rCDCShipping.setShippingWeight(rCCShipping.getShippingWeight());
         rCDCShipping.setState(rCCShipping.getState());
         rCDCShipping.setTrackingNumber(rCCShipping.getTrackingNumber());
         rCDCShipping.setNonPrintableAttachmentQueue(rCCShipping
                                                         .getNonPrintableAttachmentQueue());
         setDefaultDetails(rCDCShipping, date, true);

         RequestCoreDeliveryCharges rCDeliveryCharges =
                                            requestCoreDelivery.getRequestCoreDeliveryCharges();

         setDefaultDetails(rCDeliveryCharges, date, true);
         rCDeliveryCharges.setBillingType(rCCharges.getBillingType());
         rCDeliveryCharges.setBalanceDue(rCCharges.getBalanceDue());
         rCDeliveryCharges.setPreviouslyReleasedCost(rCCharges.getPreviouslyReleasedCost());
         rCDeliveryCharges.setTotalRequestCost(rCCharges.getTotalRequestCost());
         rCDeliveryCharges.setTotalPagesReleased(rCCharges.getTotalPagesReleased());
         rCDeliveryCharges.setTotalPages(rCCharges.getTotalPages());
         rCDeliveryCharges.setSalesTaxAmount(rCCharges.getSalesTaxAmount());
         rCDeliveryCharges.setSalesTaxPercentage(rCCharges.getSalesTaxPercentage());
         rCDeliveryCharges.setOriginalBalance(rCCharges.getOriginalBalance());
         rCDeliveryCharges.setApplySalesTax(rCCharges.getApplySalesTax());
         rCDeliveryCharges.setReleaseDate(rCCharges.getReleaseDate());
         rCDeliveryCharges.setReleaseCost(rCCharges.getReleaseCost());
         rCDeliveryCharges.setUnbillable(rCCharges.getUnbillable());
         rCDeliveryCharges.setUnbillableAmount(rCCharges.getUnbillableAmount());
     }

    /**
     * This method is to get the note names to set this value in event history
     *
     * @param String[] notes
     * @return note names
     */
    protected String getNoteDescriptions(String[] notes) {

        List<String> notesList = new ArrayList<String>();
        if ((notes != null) && (notes.length != 0)) {

            for (String note : notes) {
                if ((note != null) && (note.length() != 0)) {
                    notesList.add(note);
                }
            }
        }

        return getNoteNamesAsCSV(notesList);
    }

    /**
     * This method is to get the note names in csv format
     *
     * @param noteList
     *            List of note names
     * @return note names in csv format
     */
    private String getNoteNamesAsCSV(List<String> noteList) {

        String notes = "";

        if ((noteList != null) && (noteList.size() != 0)) {

            for (String note : noteList) {
                notes = notes + note + ROIConstants.FIELD_SEPERATOR; //NOTES_SAPERATOR;
            }
        }

        if ((notes != null) && (notes.lastIndexOf(ROIConstants.FIELD_SEPERATOR) != -1)) {
            notes = notes.substring(0, notes.lastIndexOf(ROIConstants.FIELD_SEPERATOR));
        }

        return notes;
    }

    /**
     * @param releaseCore
     * @param requestCoreDeliveryDAO
     * @param requestCoreDeliveryId
     */
    public void updateUnReleasedInvoiceToReleased(ReleaseCore releaseCore,
                                                long requestCoreDeliveryId) {

        RequestCoreDeliveryDAO requestCoreDeliveryDAO =
                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

        // determine if there are any invoices which are not attached to a release
        List<Long> nonReleasedInvoiceIds =
                requestCoreDeliveryDAO.retrieveNonReleasedInvoices(releaseCore.getRequestId());

        // add these invoices to release_invoice mapping table and
        // update invoice isReleased=true
        if (nonReleasedInvoiceIds == null) {
            return;
        }

        for (Long nonReleasedInvoiceId : nonReleasedInvoiceIds) {

            requestCoreDeliveryDAO.createRequestCoreDeliveryToInvoice(requestCoreDeliveryId,
                                                                      nonReleasedInvoiceId);
            requestCoreDeliveryDAO.updateInvoiceReleased(nonReleasedInvoiceId, true);
        }
    }

    /**
     * @param releaseCore
     * @param requestCoreDeliveryDAO
     * @param releaseId
     */
    public void createReleaseDocuments(ReleaseCore releaseCore, long releaseId) {

        RequestCoreDeliveryDAO requestCoreDeliveryDAO =
                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

        // add pages to release
        if (releaseCore.getRoiPages() != null) {

            for (ReleasePages roiPage : releaseCore.getRoiPages()) {

                requestCoreDeliveryDAO.createRequestCoreDeliveryToPages(releaseId,
                                                                        roiPage);
            }
        }

        List<Long> supplementarityAttachmentsSeq =
                                            releaseCore.getSupplementarityAttachmentsSeq();
        if (supplementarityAttachmentsSeq != null) {

            for (Long supplementarityAttachmentSeq : supplementarityAttachmentsSeq) {

                requestCoreDeliveryDAO.createRequestCoreDeliveryToSupplementarityAttachments(
                                                            releaseId,
                                                            supplementarityAttachmentSeq);
            }
        }

        if (releaseCore.getSupplementarityDocumentsSeq() != null) {

            for (Long supplementarityDocumentSeq : releaseCore.getSupplementarityDocumentsSeq()) {
                requestCoreDeliveryDAO.createRequestCoreDeliveryToSupplementarityDocuments(
                                                                        releaseId,
                                                                        supplementarityDocumentSeq);
            }
        }

        if (releaseCore.getSupplementalAttachmentsSeq() != null) {

            for (Long supplementalAttachmentSeq : releaseCore.getSupplementalAttachmentsSeq()) {
                requestCoreDeliveryDAO.createRequestCoreDeliveryToSupplementalAttachments(
                                                                       releaseId,
                                                                       supplementalAttachmentSeq);
            }
        }

        if (releaseCore.getSupplementalDocumentsSeq() != null) {

            for (Long supplementalDocumentSeq : releaseCore.getSupplementalDocumentsSeq()) {

                requestCoreDeliveryDAO.createRequestCoreDeliveryToSupplementalDocuments(
                                                                        releaseId,
                                                                        supplementalDocumentSeq);
            }
        }
    }


    /**
     * @param letterTemplateId
     * @param requestId
     * @param notes
     * @param type
     * @return
     */
    public CoverLetterCore constructCoverLetterModel(long letterTemplateId,
                                                     long requestId,
                                                     String[] notes,
                                                     String type) {

        CoverLetterCore coverLetterCore = new CoverLetterCore();
        coverLetterCore.setLetterTemplateId(letterTemplateId);
        coverLetterCore.setLetterType(type);
        coverLetterCore.setRequestId(requestId);
        coverLetterCore.setNotes(getNoteDescriptions(notes));

        RequestCoreDAO requestCoreDAO = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
        RequestorCore requestor = requestCoreDAO.retrieveRequestor(requestId);
        if (requestor != null) {
            coverLetterCore.setRequestorId(requestor.getId());
        }

        RequestCorePatientDAO rcPatientDAO =
                                    (RequestCorePatientDAO) getDAO(DAOName.REQUEST_PATIENT_DAO);
        List<RequestPatient> patients = rcPatientDAO.retrieveAllPatientForRequest(requestId);

        List<RequestPatient> requestHpfPatients = new ArrayList<RequestPatient>();
        List<RequestPatient> requestSupplementalPatients = new ArrayList<RequestPatient>();
        for (RequestPatient patient : patients) {

            if (patient.isHpf()) {
                requestHpfPatients.add(patient);
            } else {
                requestSupplementalPatients.add(patient);
            }
        }
        coverLetterCore.setRequestHpfPatients(requestHpfPatients);
        coverLetterCore.setRequestSupplementalPatients(requestSupplementalPatients);
        setDefaultDetails(coverLetterCore, rcPatientDAO.getDate(), true);

        return coverLetterCore;
    }

    /**
     * From the list of requestors, the requestor belonging to the invoice is
     * set, once the requestor to the invoice is set, the the iteration is stopped
     * @param requestors
     * @param invoice
     */
    public void setInvoiceRequestor(List<RequestorCore> requestors,
                                     RequestCoreDeliveryCharges invoice) {

        if (null == requestors || requestors.isEmpty()) {
            return;
        }

        Iterator<RequestorCore> iterator = requestors.iterator();
        while (iterator.hasNext()) {

            RequestorCore next = iterator.next();
            if (next.getRequestId() == invoice.getRequestCoreId()) {

                invoice.setRequestorCore(next);
                iterator.remove();
                break;
            }
        }
    }

    /**
     * From the list of request patients, the paients for the invoice is iterated
     * and set to the invoices
     *
     * @param requestPatients
     * @param invoice
     */
    public void setInvoicePatients(List<RequestPatient> requestPatients,
                                    RequestCoreDeliveryCharges invoice) {

        if (null == requestPatients || requestPatients.isEmpty()) {
            return;
        }

        Iterator<RequestPatient> iterator = requestPatients.iterator();
        while (iterator.hasNext()) {

            RequestPatient next = iterator.next();
            if (next.getInvoiceId() == invoice.getId()) {

                invoice.addRequestPatient(next);
                iterator.remove();
            }
        }
    }

    /**
     * From the list of adjustment and payment, the object belonging to the
     * invoice is iterated and set to object
     *
     * @param rdAdjPay
     * @param invoice
     */
    public void setInvoiceAdjustPayment(List<RequestCoreDeliveryChargesAdjustmentPayment> rdAdjPay,
                                         RequestCoreDeliveryCharges invoice) {

        if (null == rdAdjPay || rdAdjPay.isEmpty()) {
            return;
        }

        Iterator<RequestCoreDeliveryChargesAdjustmentPayment> iterator = rdAdjPay.iterator();
        while (iterator.hasNext()) {

            RequestCoreDeliveryChargesAdjustmentPayment next = iterator.next();
            if (next.getRequestCoreDeliveryChargesId() == invoice.getId()) {

                invoice.addAdjustmentAndPayment(next);
                iterator.remove();
            }
        }
    }


    /**
     * From the list of fee charges, the fee charge for the invoice is set
     *
     * @param rdFeeCharges
     * @param invoice
     */
    public void setInvoiceFeeCharge(List<RequestCoreDeliveryChargesFee> rdFeeCharges,
                                     RequestCoreDeliveryCharges invoice) {

        if (null == rdFeeCharges || rdFeeCharges.isEmpty()) {
            return;
        }

        Iterator<RequestCoreDeliveryChargesFee> iterator = rdFeeCharges.iterator();
        while (iterator.hasNext()) {

            RequestCoreDeliveryChargesFee next = iterator.next();
            if (next.getRequestCoreDeliveryChargesId() == invoice.getId()) {

                invoice.addFeeCharge(next);
                iterator.remove();
            }
        }
    }

    /**
     * From the list of document charges, the document charge for the invoice is set
     * @param rdDocCharges
     * @param invoice
     */
    public void setInvoiceDocumentCharges(List<RequestCoreDeliveryChargesDocument> rdDocCharges,
                                           RequestCoreDeliveryCharges invoice) {

        if (null == rdDocCharges || rdDocCharges.isEmpty()) {
            return;
        }

        Iterator<RequestCoreDeliveryChargesDocument> iterator = rdDocCharges.iterator();
        while (iterator.hasNext()) {

            RequestCoreDeliveryChargesDocument next = iterator.next();
            if (next.getRequestCoreDeliveryChargesId() == invoice.getId()) {

                invoice.addDocumentCharge(next);
                iterator.remove();
            }
        }
    }

    /**
     * From the list of shipping details, the shipping details for the invoice is set
     * @param shippingDetails
     * @param invoice
     */
    public void setInvoiceShippingDetails(List<RequestCoreDeliveryChargesShipping> shippingDetails,
                                           RequestCoreDeliveryCharges invoice) {

        if (null == shippingDetails || shippingDetails.isEmpty()) {
            return;
        }

        Iterator<RequestCoreDeliveryChargesShipping> iterator = shippingDetails.iterator();
        while (iterator.hasNext()) {

            RequestCoreDeliveryChargesShipping next = iterator.next();
            if (next.getRequestCoreDeliveryChargesId() == invoice.getId()) {

                invoice.setShippingDetails(next);
                iterator.remove();
                break;
            }
        }
    }
}
