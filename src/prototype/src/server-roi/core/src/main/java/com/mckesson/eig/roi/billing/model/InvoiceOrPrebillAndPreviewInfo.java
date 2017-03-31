package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class InvoiceOrPrebillAndPreviewInfo
implements Serializable {

    private static final long serialVersionUID = 1L;

    //request Id
    private long _requestCoreId;

    // invoiceduedays
    private long _invoiceDueDays;

    //type RTF or PDF
    private String _type;
    private String _letterTemplateName;
    private String _requestStatus;
    private Date _requestDate;
    private long _letterTemplateFileId;
    private Date _invoicePrebillDate;
    private Date _invoiceDueDate;
    private Date _resendDate;
    private String _outputMethod;
    private String _queuePassword;
    private boolean _overwriteDueDate;
    private double _invoiceSalesTax;
    private double _baseCharge;
    private double _paymentAmount;
    private double _adjustmentAmount;
    private String _invoiceBillingLocCode;
    private String _invoiceBillinglocName;
    private double _invoiceBalanceDue;
    private double _amountpaid;
    private String[] _notes;
    private String _letterType;
    private String _prebillStatus;
    private List<RequestCoreDeliveryChargesAdjustmentPayment> _autoAdjustments;

    private boolean _isReleased;
    private Date _releasedDate;

    /*property defined for applying unapplied amount to the invoice */
    private boolean _applyUnappliedAmount;
    private double _amountToApply;

    public String getLetterType() { return _letterType; }
    public void setLetterType(String letterType) { _letterType = letterType; }

    public String[] getNotes() { return _notes; }
    public void setNotes(String[] notes) { _notes = notes; }

    public String getPrebillStatus() { return _prebillStatus; }
    public void setPrebillStatus(String prebillStatus) { _prebillStatus = prebillStatus; }

    public double getInvoiceSalesTax() { return _invoiceSalesTax; }
    public void setInvoiceSalesTax(double invoiceSalesTax) { _invoiceSalesTax = invoiceSalesTax; }

    public double getBaseCharge() { return _baseCharge; }
    public void setBaseCharge(double baseCharge) { _baseCharge = baseCharge; }

    public String getInvoiceBillingLocCode() { return _invoiceBillingLocCode; }
    public void setInvoiceBillingLocCode(String invoiceBillingLocCode) {
        _invoiceBillingLocCode = invoiceBillingLocCode;
    }

    public String getInvoiceBillinglocName() { return _invoiceBillinglocName; }
    public void setInvoiceBillinglocName(String invoiceBillinglocName) {
        _invoiceBillinglocName = invoiceBillinglocName;
    }

    public double getInvoiceBalanceDue() { return _invoiceBalanceDue; }
    public void setInvoiceBalanceDue(double invoiceBalanceDue) {
        _invoiceBalanceDue = invoiceBalanceDue;
    }

    public double getAmountpaid() { return _amountpaid; }
    public void setAmountpaid(double amountpaid) { _amountpaid = amountpaid; }

    public String getLetterTemplateName() { return _letterTemplateName; }
    public void setLetterTemplateName(String letterTemplateName) {
        _letterTemplateName = letterTemplateName;
    }

    public String getRequestStatus() { return _requestStatus; }
    public void setRequestStatus(String requestStatus) { _requestStatus = requestStatus; }

    public Date getRequestDate() { return _requestDate; }
    public void setRequestDate(Date requestDate) { _requestDate = requestDate; }

    public long getLetterTemplateFileId() { return _letterTemplateFileId; }
    public void setLetterTemplateFileId(long letterTemplateFileId) {
        _letterTemplateFileId = letterTemplateFileId;
    }

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public Date getInvoicePrebillDate() { return _invoicePrebillDate; }
    public void setInvoicePrebillDate(Date invoicePrebillDate) {
        _invoicePrebillDate = invoicePrebillDate;
    }

    public Date getInvoiceDueDate() { return _invoiceDueDate; }
    public void setInvoiceDueDate(Date invoiceDueDate) { _invoiceDueDate = invoiceDueDate; }

    public Date getResendDate() { return _resendDate; }
    public void setResendDate(Date resendDate) { _resendDate = resendDate; }

    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }

    public boolean getOverwriteDueDate() { return _overwriteDueDate; }
    public void setOverwriteDueDate(boolean overwriteDueDate) {
        _overwriteDueDate = overwriteDueDate;
    }

    public String getQueuePassword() { return _queuePassword; }
    public void setQueuePassword(String queuePassword) { _queuePassword = queuePassword; }

    public long getRequestCoreId() { return _requestCoreId; }
    public void setRequestCoreId(long requestCoreId) { _requestCoreId = requestCoreId; }

    public long getInvoiceDueDays() { return _invoiceDueDays; }
    public void setInvoiceDueDays(long invoicedueDays) { _invoiceDueDays = invoicedueDays; }

    public List<RequestCoreDeliveryChargesAdjustmentPayment> getAutoAdjustments() {
        return _autoAdjustments;
    }
    public void setAutoAdjustments(List<RequestCoreDeliveryChargesAdjustmentPayment> autoAdjpays) {
        _autoAdjustments = autoAdjpays;
    }

    public boolean isApplyUnappliedAmount() { return _applyUnappliedAmount; }
    public void setApplyUnappliedAmount(boolean applyUnappliedAmount) {
        _applyUnappliedAmount = applyUnappliedAmount;
    }

    public double getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { _paymentAmount = paymentAmount; }

    public double getAdjustmentAmount() { return _adjustmentAmount; }
    public void setAdjustmentAmount(double adjustmentAmount) {
        _adjustmentAmount = adjustmentAmount;
    }

    public boolean isReleased() { return _isReleased; }
    public void setReleased(boolean isReleased) { _isReleased = isReleased; }

    public Date getReleasedDate() { return _releasedDate; }
    public void setReleasedDate(Date releasedDate) { _releasedDate = releasedDate; }

    public double getAmountToApply() { return _amountToApply; }
    public void setAmountToApply(double amountToApply) { _amountToApply = amountToApply; }

}
