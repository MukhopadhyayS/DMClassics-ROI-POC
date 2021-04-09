package com.mckesson.eig.roi.billing.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.roi.utils.SecureStringAccessor;

public class RequestCoreDeliveryCharges
extends BaseModel {

    private static final long serialVersionUID = 1L;

    private long _requestCoreId;
    private boolean _isReleased;
    private String _billingType;
    private double _previouslyReleasedCost;
    private Date _releaseDate;
    private double _releaseCost;
    private int _totalPages;
    private double _totalRequestCost;
    private double _balanceDue;
    private int _totalPagesReleased;
    private double _salesTaxAmount;
    private double _salesTaxPercentage;

    private double _originalBalance;
    private double _paymentAmount;
    private double _creditAdjustmentAmount;
    private double _debitAdjustmentAmount;
    private boolean _applySalesTax;

    private String _letterTemplateName;
    private String _requestStatus;
    private Date _requestDate;
    private long _letterTemplateFileId;
    private String _type;
    private Date _invoicePrebillDate;
    private Date _invoiceDueDate;
    private Date _resendDate;
    private String _outputMethod;
    private SecureStringAccessor _queuePassword;
    private boolean _overwriteDueDate;
    private double _invoiceSalesTax;
    private double _baseCharge;
    private String _invoiceBillingLocCode;
    private String _invoiceBillinglocName;
    private double _invoiceBalanceDue;
    private double _amountpaid;
    private String _notes;
    private String _creator;
    private long _invoiceDueDays;

    // Unbillable
    private boolean _unbillable;
    private double _unbillableAmount;

    //request level charges
    private Double _requestCreditAdjustment;
    private Double _requestDebitAdjustment;
    private Double _requestBalanceDue;
    private Double _requestpayment;

    // used by the letter template model
    private Set<RequestCoreDeliveryChargesAdjustmentPayment> _adjustmentsAndPayments;
    private Set<RequestCoreDeliveryChargesDocument> _documentCharges;
    private Set<RequestCoreDeliveryChargesFee> _feeCharge;
    private Set<RequestPatient> _patients;
    private RequestCoreDeliveryChargesShipping _shippingDetails;
    private RequestorCore _requestorCore;

    //used by the prebill flow
    private String _prebillStatus;

    public String getPrebillStatus() { return _prebillStatus; }
    public void setPrebillStatus(String prebillStatus) { this._prebillStatus = prebillStatus; }

    public long getInvoiceDueDays() { return _invoiceDueDays; }
    public void setInvoiceDueDays(long invoiceDueDays) { _invoiceDueDays = invoiceDueDays; }

    public String getCreator() { return _creator; }
    public void setCreator(String creator) { _creator = creator; }

    public String getNotes() { return _notes; }
    public void setNotes(String notes) { _notes = notes; }

    public double getSalesTaxAmount() { return _salesTaxAmount; }
    public void setSalesTaxAmount(double salesTaxAmount) { _salesTaxAmount = salesTaxAmount; }

    public double getOriginalBalance() { return _originalBalance; }
    public void setOriginalBalance(double originalBalance) { _originalBalance = originalBalance; }

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

    public boolean getApplySalesTax() { return _applySalesTax; }
    public void setApplySalesTax(boolean applySalesTax) { _applySalesTax = applySalesTax; }

    public boolean getIsReleased() { return _isReleased; }

    public boolean isUnbillable() {
        return _unbillable;
    }
    public void setUnbillable(boolean unbillable) {
        _unbillable = unbillable;
    }

    public double getUnbillableAmount() {
        return _unbillableAmount;
    }

    public void setUnbillableAmount(double unbillableAmount) {
        _unbillableAmount = unbillableAmount;
    }

    public void setIsReleased(boolean isReleased) { _isReleased = isReleased; }

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

    public void setAmountpaid(double amountpaid) { _amountpaid = amountpaid; }
    public double getAmountpaid() {
        return _paymentAmount + _creditAdjustmentAmount + _debitAdjustmentAmount;
    }

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

    public String getQueuePassword() { 
        StringBuilder builder = new StringBuilder();
        _queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setQueuePassword(String queuePassword) {  
        _queuePassword = new SecureStringAccessor(queuePassword.toCharArray());
    }

    public Date getReleaseDate() { return _releaseDate; }
    public void setReleaseDate(Date releaseDate) { _releaseDate = releaseDate; }

    public double getReleaseCost() { return _releaseCost; }
    public void setReleaseCost(double releaseCost) { _releaseCost = releaseCost; }

    public int getTotalPages() { return _totalPages; }
    public void setTotalPages(int totalPages) { _totalPages = totalPages; }

    public double getTotalRequestCost() { return _previouslyReleasedCost + _releaseCost; }
    public void setTotalRequestCost(double totalRequestCost) {
        _totalRequestCost = totalRequestCost;
    }

    public double getBalanceDue() { return _balanceDue; }
    public void setBalanceDue(double balanceDue) { _balanceDue = balanceDue; }

    public int getTotalPagesReleased() { return _totalPagesReleased; }
    public void setTotalPagesReleased(int totalPagesReleased) {
        _totalPagesReleased = totalPagesReleased;
    }

    public double getSalesTaxPercentage() { return _salesTaxPercentage; }
    public void setSalesTaxPercentage(double salestaxPercentage) {
        _salesTaxPercentage = salestaxPercentage;
    }

    public String getBillingType() { return _billingType; }
    public void setBillingType(String billingType) { _billingType = billingType; }

    public long getRequestCoreId() { return _requestCoreId; }
    public void setRequestCoreId(long requestCoreId) { _requestCoreId = requestCoreId; }

    public Set<RequestCoreDeliveryChargesAdjustmentPayment> getAdjustmentsAndPayments() {
        return _adjustmentsAndPayments;
    }
    public void setAdjustmentsAndPayments(
            Set<RequestCoreDeliveryChargesAdjustmentPayment> adjustmentsAndPayments) {

        _adjustmentsAndPayments = adjustmentsAndPayments;
    }
    public Set<RequestCoreDeliveryChargesDocument> getDocumentCharges() { return _documentCharges; }
    public void setDocumentCharges(Set<RequestCoreDeliveryChargesDocument> documentCharges) {
        _documentCharges = documentCharges;
    }

    public Set<RequestCoreDeliveryChargesFee> getFeeCharge() { return _feeCharge; }
    public void setFeeCharge(Set<RequestCoreDeliveryChargesFee> feeCharge) {
        _feeCharge = feeCharge;
    }

    public Set<RequestPatient> getPatients() { return _patients; }
    public void setPatients(Set<RequestPatient> patients) { _patients = patients; }

    public RequestorCore getRequestorCore() { return _requestorCore; }
    public void setRequestorCore(RequestorCore requestorCore) { _requestorCore = requestorCore; }

    public Double getRequestCreditAdjustment() { return _requestCreditAdjustment; }
    public void setRequestCreditAdjustment(Double requestCreditAdjustment) {
        _requestCreditAdjustment = requestCreditAdjustment;
    }

    public Double getRequestDebitAdjustment() { return _requestDebitAdjustment; }
    public void setRequestDebitAdjustment(Double requestDebitAdjustment) {
        _requestDebitAdjustment = requestDebitAdjustment;
    }

    public Double getRequestBalanceDue() { return _requestBalanceDue; }
    public void setRequestBalanceDue(Double requestBalanceDue) {
        _requestBalanceDue = requestBalanceDue;
    }

    public Double getRequestpayment() { return _requestpayment; }
    public void setRequestpayment(Double requestpayment) {
        _requestpayment = requestpayment;
    }

    public RequestCoreDeliveryChargesShipping getShippingDetails() { return _shippingDetails; }
    public void setShippingDetails(RequestCoreDeliveryChargesShipping shippingDetails) {
        _shippingDetails = shippingDetails;
    }

    public void addAdjustmentAndPayment(RequestCoreDeliveryChargesAdjustmentPayment adjPay) {

        if (null == _adjustmentsAndPayments) {
            _adjustmentsAndPayments = new HashSet<RequestCoreDeliveryChargesAdjustmentPayment>(0);
        }
        _adjustmentsAndPayments.add(adjPay);
    }

    public void addDocumentCharge(RequestCoreDeliveryChargesDocument docCharge) {

        if (null == _documentCharges) {
            _documentCharges = new HashSet<RequestCoreDeliveryChargesDocument>(0);
        }
        _documentCharges.add(docCharge);
    }

    public void addFeeCharge(RequestCoreDeliveryChargesFee feeCharge) {

        if (null == _feeCharge) {
            _feeCharge = new HashSet<RequestCoreDeliveryChargesFee>(0);
        }
        _feeCharge.add(feeCharge);
    }

    public void addRequestPatient(RequestPatient patient) {

        if (null == _patients) {
            _patients = new HashSet<RequestPatient>(0);
        }
        _patients.add(patient);
    }

}
