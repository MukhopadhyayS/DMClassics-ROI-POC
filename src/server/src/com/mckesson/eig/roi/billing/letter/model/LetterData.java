/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.billing.letter.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.utils.SecureStringAccessor;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Sep 28, 2009
 * @since  HPF 13.1 [ROI]; Feb 19, 2009
 */
public class LetterData {

    private static final OCLogger LOG = new OCLogger(LetterData.class);

    public static final String DATEFORMAT = "MM/dd/yyyy";
    public static final String DATEFORMAT1 = "EEEE, MMMMM dd, yyyy";
    public static final String DATEFORMAT2 = "MMMMM dd, yyyy";
    public static final SimpleDateFormat DATEFORMATTER = new SimpleDateFormat(DATEFORMAT);

    private boolean _epnEnabled;
    private String _invoicePrebillDate = StringUtilities.EMPTYSTRING;
    private String _invoicePrebillId = StringUtilities.EMPTYSTRING;
    private String _requestDt = StringUtilities.EMPTYSTRING;
    private BillingInfo _billingInfo;
    private RequestorInfo _requestor;
    private List<RequestItem> _patientsList;
    private List<Note> _notesList;
    private List<Note> _reasonsList;
    private String _requestStatus = StringUtilities.EMPTYSTRING;
    private String _requestId = StringUtilities.EMPTYSTRING;
    private String _templateName = StringUtilities.EMPTYSTRING;
    private String _templateFileId = StringUtilities.EMPTYSTRING;
    private String _salesTaxPercentage = StringUtilities.EMPTYSTRING;
    private String _outputMethod = StringUtilities.EMPTYSTRING;
    private SecureStringAccessor _queuePassword = null;
    private boolean _hasNotes;

    //Invoice DueDate
    private String _invoiceDueDate = StringUtilities.EMPTYSTRING;
    private String _resendDate = StringUtilities.EMPTYSTRING;
    private boolean _overwriteDueDate;

    // Request Details
    private String _requestBalanceDue = StringUtilities.EMPTYSTRING;
    private String _requestOriginalBalance = StringUtilities.EMPTYSTRING;
    private String _requestPaymentAmount = StringUtilities.EMPTYSTRING;
    private String _requestCreditAmount = StringUtilities.EMPTYSTRING;
    private String _requestDebitAmount = StringUtilities.EMPTYSTRING;
    private String _requestSalesTax = StringUtilities.EMPTYSTRING;
    private String _requestorGroupingKey = StringUtilities.EMPTYSTRING;

    private String _userName = StringUtilities.EMPTYSTRING;
    private String _refundAmount = StringUtilities.EMPTYSTRING;

    public boolean isEpnEnabled() { return _epnEnabled; }
    public void setEpnEnabled(boolean enabled) { _epnEnabled = enabled; }

    public String getInvoicePrebillDate() { return _invoicePrebillDate; }
    public void setInvoicePrebillDate(String prebillDate) { _invoicePrebillDate = StringUtilities.safe(prebillDate); }

    public String getInvoicePrebillId() { return _invoicePrebillId; }
    public void setInvoicePrebillId(String prebillId) { _invoicePrebillId = StringUtilities.safe(prebillId); }

    public String getBillDateFmt1() {
        if (_invoicePrebillDate != null) {
            return formatDate(parseDate(_invoicePrebillDate), DATEFORMAT1);
        }
        return "";
    }

    public String getBillDateFmt2() {
        if (_invoicePrebillDate != null) {
            return formatDate(parseDate(_invoicePrebillDate), DATEFORMAT2);
        }
        return "";
    }

    public String getInvoiceId() { return _invoicePrebillId; }
    public void setInvoiceId(String id) { _invoicePrebillId = StringUtilities.safe(id); }

    public String getRequestDt() { return _requestDt; }
    public void setRequestDt(String dt) { _requestDt = StringUtilities.safe(dt); }

    public String getRequestDtFmt1() {
        if (_requestDt != null) {
            return formatDate(parseDate(_requestDt), DATEFORMAT1);
        }
        return "";
    }

    public String getRequestDtFmt2() {
        if (_requestDt != null) {
            return formatDate(parseDate(_requestDt), DATEFORMAT2);
        }
        return "";
    }

    public BillingInfo getBillingInfo() { return _billingInfo; }
    public void setBillingInfo(BillingInfo info) { _billingInfo = info; }

    public RequestorInfo getRequestor() { return _requestor; }
    public void setRequestor(RequestorInfo requestor) { _requestor = requestor; }

    public List<RequestorTransaction> getRequestorTxns() {

        if (null != _requestor && CollectionUtilities.hasContent(_requestor.getTransactions())) {
            return _requestor.getTransactions();
        }

        ArrayList<RequestorTransaction> requestortxns = new ArrayList<RequestorTransaction>();
        requestortxns.add(new RequestorTransaction());
        return requestortxns;

    }

    public List<RequestItem> getPatients() {

        if ((_patientsList != null) && (_patientsList.size() > 0)
                && (_patientsList.get(0).getRequestItems() != null)) {
            return _patientsList.get(0).getRequestItems();
        }

        List<RequestItem> patients = new ArrayList<RequestItem>();
        patients.add(new RequestItem());
        return patients;
    }
    public void setPatientsList(List<RequestItem> patients) { _patientsList = patients; }
    public List<RequestItem> getPatientsList() { return _patientsList; }

    public List<Note> getNotes() {

        if ((_notesList != null) && (_notesList.size() > 0)
                && (_notesList.get(0).getChildItems() != null)) {
            return _notesList.get(0).getChildItems();
        }

        List<Note> notes = new ArrayList<Note>();
        notes.add(new Note());
        return notes;
    }

    public String getNotesSize() {

        if (_notesList != null) {
            return new Integer(_notesList.size()).toString();
        }
        return "0";
    }

    public void setNotesList(List<Note> notes) { _notesList = notes; }
    public List<Note> getNotesList() { return _notesList; }

    public List<Note> getReasons() {

        if ((_reasonsList != null) && (_reasonsList.size() > 0)
            && (_reasonsList.get(0).getChildItems() != null)) {
            return _reasonsList.get(0).getChildItems();
        }

        List<Note> reasons = new ArrayList<Note>();
        reasons.add(new Note());
        return reasons;
    }

    public List<Note> getReasonsList() {  return _reasonsList; }
    public void setReasonsList(List<Note> reasons) {  _reasonsList = reasons; }

    public ShippingInfo getShippingInfo() {
        if (_billingInfo != null) {
            return _billingInfo.getShippingInfo();
        }
        return new ShippingInfo();
    }

    public List<Charge> getDocCharges() {

        List<Charge> charges = new ArrayList<Charge>();
        if ((_billingInfo != null) && (_billingInfo.getCharges() != null)) {
            for (Charge charge : _billingInfo.getCharges()) {
                if (charge.getDocCharge() != null) {
                    charges.addAll(populateDocCharge(charge.getDocCharge()));
                }
            }
        }
        if (charges.size() == 0) {
            charges.add(initializeDocCharges(new Charge()));
        }

        return charges;
    }

    private List<Charge> populateDocCharge(List<Charge> docCharge) {

        List<Charge> docCharges = new ArrayList<Charge>();
        for (Charge charge : docCharge) {
            docCharges.add(initializeDocCharges(charge));
        }
        return docCharges;
    }
    /**
     * @param charge
     * @return
     */
    private Charge initializeDocCharges(Charge charge) {

        if (null == charge.getDocumentChargeTotal()) {
            charge.setDocumentChargeTotal("$0.00");
        }

        if (null == charge.getDocumentChargeTaxTotal()) {
            charge.setDocumentChargeTaxTotal("$0.00");
        }
        return charge;
    }

    public List<ChargeItem> getDocChargeDetails() {

        List<ChargeItem> items = new ArrayList<ChargeItem>();
        if ((_billingInfo != null) && (_billingInfo.getCharges() != null)) {
            for (Charge charge : _billingInfo.getCharges()) {
                if (charge.getDocCharge() != null) {
                    for (Charge docCharge : charge.getDocCharge()) {
                        if (docCharge != null) {
                            items.addAll(docCharge.getChargeItems());
                        }
                    }
                }
            }
        }
        if (items.size() == 0) {
            items.add(new ChargeItem());
        }

        return items;
    }

    public List<Charge> getFeeCharges() {

        List<Charge> charges = new ArrayList<Charge>();
        if ((_billingInfo != null) && (_billingInfo.getCharges() != null)) {
            for (Charge charge : _billingInfo.getCharges()) {
                if (charge.getFeeCharge() != null) {
                    charges.addAll(populateFeeCharge(charge.getFeeCharge()));
                }
            }
        }
        if (charges.size() == 0) {
            charges.add(initializefeeCharge(new Charge()));
        }

        return charges;
    }

    private List<Charge> populateFeeCharge(List<Charge> feeCharge) {

        List<Charge> feeCharges = new ArrayList<Charge>();
        for (Charge charge : feeCharge) {

            initializefeeCharge(charge);
            feeCharges.add(charge);
        }
        return feeCharges;
    }
    /**
     * @param charge
     */
    private Charge initializefeeCharge(Charge charge) {

        if (null == charge.getFeeChargeTaxTotal()) {
            charge.setFeeChargeTaxTotal("$0.00");
        }

        if (null == charge.getFeeChargeTotal()) {
            charge.setFeeChargeTotal("$0.00");
        }

        if (null == charge.getCustomFeeChargeTaxTotal()) {
            charge.setCustomFeeChargeTaxTotal("$0.00");
        }
        return charge;
    }

    public List<ChargeItem> getFeeChargeDetails() {

        List<ChargeItem> items = new ArrayList<ChargeItem>();
        if ((_billingInfo != null) && (_billingInfo.getCharges() != null)) {
            for (Charge charge : _billingInfo.getCharges()) {
                if (charge.getFeeCharge() != null) {
                    for (Charge docCharge : charge.getFeeCharge()) {
                        if (docCharge != null) {
                            items.addAll(docCharge.getChargeItems());
                        }
                    }
                }
            }
        }
        if (items.size() == 0) {
            items.add(new ChargeItem());
        }

        return items;
    }

    public List<Charge> getTxnCharges() {

        List<Charge> charges = new ArrayList<Charge>();
        if ((_billingInfo != null) && (_billingInfo.getCharges() != null)) {
            for (Charge charge : _billingInfo.getCharges()) {
                if (charge.getTxns() != null) {
                    charges.addAll(populateTxns(charge.getTxns()));
                }
            }
        }
        if (charges.size() == 0) {
            charges.add(initializeTxns(new Charge()));
        }

        return charges;
    }

    private List<Charge> populateTxns(List<Charge> txns) {

        List<Charge> charges = new ArrayList<Charge>();
        for (Charge charge : txns) {

            initializeTxns(charge);
            charges.add(charge);
        }
        return charges;
    }

    private Charge initializeTxns(Charge charge) {

        if (null == charge.getAdjustmentPaymentTotal()) {
            charge.setAdjustmentPaymentTotal("$0.00");
        }

        if (null == charge.getCreditAdjustmentTotal()) {
            charge.setCreditAdjustmentTotal("$0.00");
        }

        if (null == charge.getDebitAdjustmentTotal()) {
            charge.setDebitAdjustmentTotal("$0.00");
        }

        if (null == charge.getPaymentTotal()) {
            charge.setPaymentTotal("$0.00");
        }
        return charge;
    }

    public List<ChargeItem> getTxns() {

        List<ChargeItem> items = new ArrayList<ChargeItem>();
        if ((_billingInfo != null) && (_billingInfo.getCharges() != null)) {
            for (Charge charge : _billingInfo.getCharges()) {
                if (charge.getTxns() != null) {
                    for (Charge docCharge : charge.getTxns()) {
                        if (docCharge != null && docCharge.getChargeItems() != null) {
                            items.addAll(docCharge.getChargeItems());
                        }
                    }
                }
            }
        }
        if (items.size() == 0) {
            items.add(new ChargeItem());
        }

        return items;
    }

    public List<Charge> getShipCharges() {

        List<Charge> charges = new ArrayList<Charge>();
        if ((_billingInfo != null) && (_billingInfo.getCharges() != null)) {
            for (Charge charge : _billingInfo.getCharges()) {
                if (charge.getShippingCharge() != null) {
                    charges.addAll(charge.getShippingCharge());
                }
            }
        }
        if (charges.size() == 0) {
            charges.add(new Charge());
        }

        return charges;
    }

    public List<ReleaseInfo> getReleases() {

        List<ReleaseInfo> charges = new ArrayList<ReleaseInfo>();
        if ((_billingInfo != null) && (_billingInfo.getCharges() != null)) {
            for (Charge charge : _billingInfo.getCharges()) {
                if (charge.getReleases() != null) {
                    charges.addAll(charge.getReleases());
                }
            }
        }

        if (charges.size() == 0) {
            charges.add(new ReleaseInfo());
        }
        return charges;
    }

    public static Date parseDate(String stringDate) {

        if (null == stringDate) {
            return null;
        }

        Date date = null;
        try {
            date = DATEFORMATTER.parse(stringDate);
        } catch (Throwable e) {
            LOG.error("Invalid Date");
        }
        return date;
    }

    public static String formatDate(Date date, String format) {
    	String formattedDate = "";
    	if ( (date!=null) && (format != null)) {
    		formattedDate = new SimpleDateFormat(format).format(date);
    	}
    	return formattedDate;
    }

    public String getRequestStatus() { return _requestStatus; }
    public void setRequestStatus(String status) { _requestStatus = StringUtilities.safe(status); }

    public String getRequestId() { return _requestId; }
    public void setRequestId(String id) { _requestId = StringUtilities.safe(id); }

    public String getTemplateName() { return _templateName; }
    public void setTemplateName(String name) { _templateName = StringUtilities.safe(name); }

    public boolean getHasNotes() {

        _hasNotes = ((getNotesList() != null) && (getNotesList().size() > 0)) ? true : false;
        return _hasNotes;
    }
    public void setHasNotes(boolean hasNotes) { _hasNotes = hasNotes; }

    public String getInvoiceDueDate() { return _invoiceDueDate; }
    public void setInvoiceDueDate(String invoiceDueDate) { _invoiceDueDate = StringUtilities.safe(invoiceDueDate); }

    public String getInvoiceDueDateFmt1() {

        if (_invoiceDueDate != null) {

            Date invoiceDate = parseDate(_invoiceDueDate);
            Date invoiceCreatedDate = parseDate(_invoicePrebillDate);
            if ((null != invoiceDate && null != invoiceCreatedDate)
                    && invoiceDate.equals(invoiceCreatedDate)) {
                 return "Payment due upon receipt";
            }
            return formatDate(invoiceDate, DATEFORMAT1);
        }
        return "";
    }

    public String getInvoiceDueDateFmt2() {
        if (_invoiceDueDate != null) {

            Date invoiceDueDate = parseDate(_invoiceDueDate);
            Date invoiceCreatedDate = parseDate(_invoicePrebillDate);

            if ((null != invoiceDueDate && null != invoiceCreatedDate)
                    && invoiceDueDate.equals(invoiceCreatedDate)) {
                return "Payment due upon receipt";
            }
            return formatDate(invoiceDueDate, DATEFORMAT2);
         }
        return "";
    }

    public boolean isOverwriteDueDate() { return _overwriteDueDate; }
    public void setOverwriteDueDate(boolean overwriteDueDate) {
        _overwriteDueDate = overwriteDueDate;
    }

    public List<ChargeItem> getPayment() {

        List<ChargeItem> txns = getTxns();
        if (null == txns || txns.size() == 0) {
            return new ArrayList<ChargeItem>();
        }
        List<ChargeItem> chrgItem = new ArrayList<ChargeItem>();
        for (ChargeItem item : txns) {

            if (null == item) {
                continue;
            }

            if (ROIConstants.PAYMENT_TYPE.equalsIgnoreCase(item.getTransactionType())
                    || ROIConstants.PAYMENT_TYPE.equalsIgnoreCase(item.getType())) {
                chrgItem.add(item);
            }
        }
        if (chrgItem.isEmpty()) {
            chrgItem.add(new ChargeItem());
        }
        return chrgItem;
    }

    public List<ChargeItem> getCreditAdjustment() {

        List<ChargeItem> txns = getTxns();
        if (null == txns || txns.size() == 0) {
            return new ArrayList<ChargeItem>();
        }

        List<ChargeItem> chrgItem = new ArrayList<ChargeItem>();
        for (ChargeItem item : txns) {

            if (null == item) {
                continue;
            }

            if (ROIConstants.ADJUSTMENT_TYPE.equalsIgnoreCase(item.getTransactionType())
                    || ROIConstants.ADJUSTMENT_TYPE.equalsIgnoreCase(item.getType())) {

                if (ROIConstants.FALSE.equalsIgnoreCase(item.getDebt())) {
                    chrgItem.add(item);
                }
            }
        }

        if (chrgItem.isEmpty()) {
            chrgItem.add(new ChargeItem());
        }
        return chrgItem;
    }

    public List<ChargeItem> getDebitAdjustment() {

        List<ChargeItem> txns = getTxns();
        if (null == txns || txns.size() == 0) {
            return new ArrayList<ChargeItem>();
        }

        List<ChargeItem> chrgItem = new ArrayList<ChargeItem>();
        for (ChargeItem item : txns) {

            if (null == item) {
                continue;
            }

            if (ROIConstants.ADJUSTMENT_TYPE.equalsIgnoreCase(item.getTransactionType())
                    || ROIConstants.ADJUSTMENT_TYPE.equalsIgnoreCase(item.getType())) {

                if (ROIConstants.TRUE.equalsIgnoreCase(item.getDebt())) {
                    chrgItem.add(item);
                }
            }
        }

        if (chrgItem.isEmpty()) {
            chrgItem.add(new ChargeItem());
        }
        return chrgItem;
    }

    /**
     * This method gets the current date with midnight time
     * (i.e seconds, milliseconds, hour, Am are all set to midnight)
     * @return
     */
    public Date getTodayDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        return calendar.getTime();
    }


    public String getDate() {

        Calendar calendar = Calendar.getInstance();
        return formatDate(calendar.getTime(), DATEFORMAT);
    }

    public String getDateFmt1() {

        Calendar calendar = Calendar.getInstance();
        return formatDate(calendar.getTime(), DATEFORMAT1);
    }

    public String getDateFmt2() {

        Calendar calendar = Calendar.getInstance();
        return formatDate(calendar.getTime(), DATEFORMAT2);
    }

    public InvoiceCharge getInvoiceCharge() {

        if (null == getBillingInfo()) {
            return new InvoiceCharge();
        }

        List<Charge> charge = getBillingInfo().getCharges();

        if (null != charge && charge.size() > 0) {
            return charge.get(0).getInvoices();
        }
        return new InvoiceCharge();
    }


    public String getSalesTaxPercentage() { return _salesTaxPercentage; }
    public void setSalesTaxPercentage(String salesTaxPercentage) {
        _salesTaxPercentage = StringUtilities.safe(salesTaxPercentage);
    }
    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = StringUtilities.safe(outputMethod); }

    public String getQueuePassword() {
        StringBuilder builder = new StringBuilder();
        _queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setQueuePassword(String queuePassword) {
        queuePassword = StringUtilities.safe(queuePassword);
        _queuePassword = new SecureStringAccessor(queuePassword.toCharArray());
    }

    public String getRequestBalanceDue() { return _requestBalanceDue; }
    public void setRequestBalanceDue(String requestBalanceDue) {
        _requestBalanceDue = StringUtilities.safe(requestBalanceDue);
    }
    public String getRequestOriginalBalance() { return _requestOriginalBalance; }
    public void setRequestOriginalBalance(String requestOriginalBalance) {
        _requestOriginalBalance = StringUtilities.safe(requestOriginalBalance);
    }

    public String getRequestPaymentAmount() { return _requestPaymentAmount; }
    public void setRequestPaymentAmount(String requestPaymentAmount) {
        _requestPaymentAmount = StringUtilities.safe(requestPaymentAmount);
    }

    public String getRequestCreditAmount() { return _requestCreditAmount; }
    public void setRequestCreditAmount(String requestCreditAmount) {
        _requestCreditAmount = StringUtilities.safe(requestCreditAmount);
    }

    public String getRequestDebitAmount() { return _requestDebitAmount; }
    public void setRequestDebitAmount(String requestDebitAmount) {
        _requestDebitAmount = StringUtilities.safe(requestDebitAmount);
    }

    public String getRequestSalesTax() { return _requestSalesTax; }
    public void setRequestSalesTax(String requestSalesTax) {
        _requestSalesTax = StringUtilities.safe(requestSalesTax);
    }
    public String getRequestorGroupingKey() { return _requestorGroupingKey; }
    public void setRequestorGroupingKey(String requestorGroupingKey) {
        _requestorGroupingKey = StringUtilities.safe(requestorGroupingKey);
    }
    public String getTemplateFileId() { return _templateFileId; }
    public void setTemplateFileId(String templateFileId) { _templateFileId = StringUtilities.safe(templateFileId); }

    public String getResendDate() { return _resendDate; }
    public void setResendDate(String resendDate) { _resendDate = StringUtilities.safe(resendDate); }

    public String getUserName() { return _userName; }
    public void setUserName(String userName) { _userName = StringUtilities.safe(userName); }

    public String getRefundAmount() { return _refundAmount; }
    public void setRefundAmount(String refundAmount) { _refundAmount = StringUtilities.safe(refundAmount); }

}
