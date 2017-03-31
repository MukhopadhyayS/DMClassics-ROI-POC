/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria.DateRange;

/**
 * This class  is a persistent model used to store requestor summary Invoices for hibernate.
 * @author OFS
 * @date   Sep 15, 2011
 * @since  HPF 15.2 [ROI]; Sep 15, 2011
 */
@SuppressWarnings("serial")
public class RequestorLetter
implements Serializable {

    private long _requestorLetterId;
    private long _requestTemplateId;
    private int _createdBy;
    private Date _createdDate;
    private int _modifiedBy;
    private Date _modifiedDt;
    private int _recordVersion;
    private long _requestorId;

    private String _requestorName;
    private String _requestorTypeName;
    private String _requestorPhone;
    private String _requestorAddress1;
    private String _requestorAddress2;
    private String _requestorAddress3;
    private String _requestorCity;
    private String _requestorState;
    private String _requestorPostalCode;
    private String _requestorCountry;
    private String _outputMethod;
    private String _queuePassword;
    private String _templateName;
    private Date _resendDate;
    private List<String> _notes;
    private String _notesString;
    private List<RequestorLetterInvoice> _invoices;
//    private String _dateRange;
    private DateRange _dateRange;

    private double _charges;
    private double _paymentAmount;
    private double _creditAdjustmentAmount;
    private double _debitAdjustmentAmount;
    private double _adjustmentAmount;
    private double _balances;
    private double _unAppliedPayment;
    private double _unAppliedAdjustment;
    private double _unAppliedAmount;

    private double _balance30;
    private double _balance60;
    private double _balance90;
    private double _balanceOther;

    public long getRequestorLetterId() { return _requestorLetterId; }
    public void setRequestorLetterId(long reqInvId) {

        _requestorLetterId = reqInvId;
        setRequestorLetterIdToInvoices(reqInvId);
    }

    public int getCreatedBy() { return _createdBy; }
    public void setCreatedBy(int createdBy) { _createdBy = createdBy; }

    public Date getCreatedDate() { return (null == _createdDate) ? new Date() : _createdDate; }
    public void setCreatedDate(Date createdDate) { _createdDate = createdDate; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int recordVersion) { _recordVersion = recordVersion; }

    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }

    public long getRequestTemplateId() { return _requestTemplateId; }
    public void setRequestTemplateId(long requestTemplateId) {
        _requestTemplateId = requestTemplateId;
    }

    public int getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(int modifiedBy) { _modifiedBy = modifiedBy; }

    public void setModifiedDt(Date modifiedDt) { _modifiedDt = modifiedDt; }
    public Date getModifiedDt() { return (null == _modifiedDt) ? new Date() : _modifiedDt; }

    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }

    public String getRequestorPhone() { return _requestorPhone; }
    public void setRequestorPhone(String requestorPhone) { _requestorPhone = requestorPhone; }

    public String getRequestorAddress1() { return _requestorAddress1; }
    public void setRequestorAddress1(String requestorAddress1) {
        _requestorAddress1 = requestorAddress1;
    }

    public String getRequestorAddress2() { return _requestorAddress2; }
    public void setRequestorAddress2(String requestorAddress2) {
        _requestorAddress2 = requestorAddress2;
    }

    public String getRequestorAddress3() { return _requestorAddress3; }
    public void setRequestorAddress3(String requestorAddress3) {
        _requestorAddress3 = requestorAddress3;
    }

    public String getRequestorCity() { return _requestorCity; }
    public void setRequestorCity(String requestorCity) { _requestorCity = requestorCity; }

    public String getRequestorState() { return _requestorState; }
    public void setRequestorState(String requestorState) { _requestorState = requestorState; }

    public String getRequestorPostalCode() { return _requestorPostalCode; }
    public void setRequestorPostalCode(String requestorPostalCode) {
        _requestorPostalCode = requestorPostalCode;
    }

    public String getRequestorCountry() {
        return _requestorCountry;
    }
    
    public void setRequestorCountry(String requestorCountry) {
        _requestorCountry = requestorCountry;
    }
    
    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }

    public Date getResendDate() { return _resendDate; }
    public void setResendDate(Date resendDate) { _resendDate = resendDate; }

    public String getQueuePassword() { return _queuePassword; }
    public void setQueuePassword(String queuePassword) { _queuePassword = queuePassword; }

    public String getTemplateName() { return _templateName; }
    public void setTemplateName(String templateName) { _templateName = templateName; }

    public List<RequestorLetterInvoice> getInvoices() { return _invoices; }
    public void setInvoices(List<RequestorLetterInvoice> invoices) { _invoices = invoices; }

    public String getRequestorTypeName() { return _requestorTypeName; }
    public void setRequestorTypeName(String requestorTypeName) {
        _requestorTypeName = requestorTypeName;
    }

    public void addRequestorLetterInvoice(RequestorLetterInvoice reqLetterInvoice) {

        if (null == _invoices) {
            _invoices = new ArrayList<RequestorLetterInvoice>();
        }

        reqLetterInvoice.setRequestorLetterId(_requestorLetterId);
        _invoices.add(reqLetterInvoice);
    }

    private void setRequestorLetterIdToInvoices(long requestorLetterId) {

        if (null == _invoices) {
            return;
        }

        for (RequestorLetterInvoice invoice : _invoices) {
            invoice.setRequestorLetterId(requestorLetterId);
        }
    }

    public String getNotesString() {

        if (null == getNotes()) {
            return null;
        }


        StringBuffer requestorLetterNotes = new StringBuffer();
        for (String note : getNotes()) {
            requestorLetterNotes.append(note);
            requestorLetterNotes.append(ROIConstants.FIELD_SEPERATOR);
        }
        _notesString = requestorLetterNotes.delete(requestorLetterNotes.length() - 1,
                                                   requestorLetterNotes.length()).toString();

        return _notesString;
    }

    public void setNotesString(String notesString) {

        _notesString = notesString;
        if (null == notesString) {
            return;
        }

        String[] strings = notesString.split(ROIConstants.FIELD_SEPERATOR);
        _notes = Arrays.asList(strings);
    }

    public List<String> getNotes() { return _notes; }
    public void setNotes(List<String> notes) { _notes = notes; }

    public DateRange getDateRange() { return _dateRange; }
    public void setDateRange(DateRange dateRange) { _dateRange = dateRange; }

    public String getDateRangeAsString() { return (null == _dateRange) ? null : _dateRange.name(); }
    public void setDateRangeAsString(String dateRange) {
        _dateRange = DateRange.valueOf(dateRange);
    }

    public double getCharges() { return _charges; }
    public void setCharges(double baseCharge) { _charges = baseCharge; }

    public double getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { _paymentAmount = paymentAmount; }

    public double getCreditAdjustmentAmount() { return _creditAdjustmentAmount; }
    public void setCreditAdjustmentAmount(double creditAdjustmentAmount) {
        _creditAdjustmentAmount = creditAdjustmentAmount;
    }

    public double getDebitAdjustmentAmount() { return _debitAdjustmentAmount; }
    public void setDebitAdjustmentAmount(double debitAdjustmentAmount) {
        _debitAdjustmentAmount = debitAdjustmentAmount;
    }

    public void setAdjustmentAmount(double adjAmount) { _adjustmentAmount = adjAmount; }
    public double getAdjustmentAmount() {

        _adjustmentAmount = getCreditAdjustmentAmount() + getDebitAdjustmentAmount();
        return _adjustmentAmount;
    }

    public double getBalances() { return _balances; }
    public void setBalances(double balances) { _balances = balances; }

    public double getUnAppliedPayment() { return _unAppliedPayment; }
    public void setUnAppliedPayment(double unAppliedPayment) {
        _unAppliedPayment = unAppliedPayment;
    }

    public double getUnAppliedAdjustment() { return _unAppliedAdjustment; }
    public void setUnAppliedAdjustment(double unAppliedAdjustment) {
        _unAppliedAdjustment = unAppliedAdjustment;
    }

    public void setUnAppliedAmount(double unAppliedAmount) { _unAppliedAmount = unAppliedAmount; }
    public double getUnAppliedAmount() {

        _unAppliedAmount = getUnAppliedAdjustment() + getUnAppliedPayment();
        return _unAppliedAmount;
    }

    public double getBalance30() { return _balance30; }
    public void setBalance30(double balance30) { _balance30 = balance30; }

    public double getBalance60() { return _balance60; }
    public void setBalance60(double balance60) { _balance60 = balance60; }

    public double getBalance90() { return _balance90; }
    public void setBalance90(double balance90) { _balance90 = balance90; }

    public double getBalanceOther() { return _balanceOther; }
    public void setBalanceOther(double balanceOther) { _balanceOther = balanceOther; }

}
