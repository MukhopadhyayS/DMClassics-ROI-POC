package com.mckesson.eig.roi.billing.model;

import java.util.Date;
import java.util.Set;

import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.requestor.model.RequestorCore;

public class RegeneratedInvoiceDetails 
extends BaseModel{
    
    private static final long serialVersionUID = 1L;
    
    private long _requestCoreDeliveryChargesId;
    private Date _resendDt;
    private double _requestCreditAmount;
    private double _requestDebitAmount;
    private double _requestPaymentAmount;
    private double _requestBalanceDue;
    private String _requestStatus;
    private String _letterTemplateName;
    private long _letterTemplateFileId;
    private String _queuePassword;
    private String _outputMethod;
    private String _notes;
    private double _amountPaid;
    private double _balanceDue;
    private double _baseCharge;
    private double _originalBalance;
    
    // added for the construction of the letter data
    private Double _salesTax;
    private String _billingLocation;
    private Date _invoiceDueDate;
    private Date _invoiceDate;
    private Long _requestId;
    private Date _requestDate;
    private Double _previouslyReleasedCost;
    private Double _releaseCost;
    private Date _releaseDate;
    private Long _totalPagesReleased;
    private Long _totalPages;
    private Double _totalRequestCost;
    private Set<RequestCoreDeliveryChargesAdjustmentPayment> _adjustmentsAndPayments;
    private Set<RequestCoreDeliveryChargesDocument> _documentCharges;
    private Set<RequestCoreDeliveryChargesFee> _feeCharge;
    private Set<RequestPatient> _patients;
    private RequestCoreDeliveryChargesShipping _shippingDetails;
    private RequestorCore _requestorCore;
    
    public double getBalanceDue() { return _balanceDue; }
    public void setBalanceDue(double balanceDue) { _balanceDue = balanceDue; }
    
    public double getBaseCharge() { return _baseCharge; }
    public void setBaseCharge(double baseCharge) { _baseCharge = baseCharge; }
    
    public long getRequestCoreDeliveryChargesId() { return _requestCoreDeliveryChargesId; }
    public void setRequestCoreDeliveryChargesId(long requestCoreDeliveryChargesId) {
        _requestCoreDeliveryChargesId = requestCoreDeliveryChargesId;
    }
    
    public Date getResendDt() { return _resendDt; }
    public void setResendDt(Date resendDt) { _resendDt = resendDt; }
    
    public double getRequestCreditAmount() { return _requestCreditAmount; }
    public void setRequestCreditAmount(double requestCreditAmount) {
        _requestCreditAmount = requestCreditAmount;
    }
    
    public double getRequestDebitAmount() { return _requestDebitAmount; }
    public void setRequestDebitAmount(double requestDebitAmount) {
        _requestDebitAmount = requestDebitAmount;
    }
    
    public double getRequestPaymentAmount() { return _requestPaymentAmount; }
    public void setRequestPaymentAmount(double requestPaymentAmount) {
        _requestPaymentAmount = requestPaymentAmount;
    }
    
    public double getRequestBalanceDue() { return _requestBalanceDue; }
    public void setRequestBalanceDue(double requestBalanceDue) {
        _requestBalanceDue = requestBalanceDue;
    }
    
    public String getRequestStatus() { return _requestStatus; }
    public void setRequestStatus(String requestStatus) { _requestStatus = requestStatus; }
    
    public String getLetterTemplateName() { return _letterTemplateName; }
    public void setLetterTemplateName(String letterTemplateName) {
        _letterTemplateName = letterTemplateName;
    }
    
    public long getLetterTemplateFileId() { return _letterTemplateFileId; }
    public void setLetterTemplateFileId(long letterTemplateFileId) {
        _letterTemplateFileId = letterTemplateFileId;
    }
    
    public String getQueuePassword() { return _queuePassword; }
    public void setQueuePassword(String queuePassword) { _queuePassword = queuePassword; }
    
    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }
    
    public double getAmountPaid() { return _amountPaid; }
    public void setAmountPaid(double amountPaid) { _amountPaid = amountPaid; }

    public String getNotes() { return _notes; }
    public void setNotes(String notes) { _notes = notes; }
    
    public Double getSalesTax() { return _salesTax; }
    public void setSalesTax(Double salesTax) { _salesTax = salesTax; }

    public String getBillingLocation() { return _billingLocation; }
    public void setBillingLocation(String billingLocation) { _billingLocation = billingLocation; }
    
    public Date getInvoiceDueDate() { return _invoiceDueDate; }
    public void setInvoiceDueDate(Date invoiceDueDate) { _invoiceDueDate = invoiceDueDate; }
    
    public Long getRequestId() { return _requestId; }
    public void setRequestId(Long requestId) { _requestId = requestId; }

    public Date getInvoiceDate() { return _invoiceDate; }
    public void setInvoiceDate(Date invoiceDate) { _invoiceDate = invoiceDate; }
    
    public Date getRequestDate() { return _requestDate; }
    public void setRequestDate(Date requestDate) { _requestDate = requestDate; }
    
    public Double getPreviouslyReleasedCost() { return _previouslyReleasedCost; }
    public void setPreviouslyReleasedCost(Double previouslyReleasedCost) {
        _previouslyReleasedCost = previouslyReleasedCost;
    }
    
    public Double getReleaseCost() { return _releaseCost; }
    public void setReleaseCost(Double releaseCost) { _releaseCost = releaseCost; }
    
    public Date getReleaseDate() { return _releaseDate; }
    public void setReleaseDate(Date releaseDate) { _releaseDate = releaseDate; }
    
    public Long getTotalPagesReleased() { return _totalPagesReleased; }
    public void setTotalPagesReleased(Long totalPagesReleased) {
        _totalPagesReleased = totalPagesReleased;
    }
    
    public Long getTotalPages() { return _totalPages; }
    public void setTotalPages(Long totalPages) { _totalPages = totalPages; }
    
    public Double getTotalRequestCost() { return _totalRequestCost; }
    public void setTotalRequestCost(Double totalRequestCost) {
        _totalRequestCost = totalRequestCost;
    }
    
    public RequestCoreDeliveryChargesShipping getShippingDetails() { return _shippingDetails; }
    public void setShippingDetails(RequestCoreDeliveryChargesShipping shippingDetails) {
        _shippingDetails = shippingDetails;
    }
    
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
    
    public double getOriginalBalance() {
        return _originalBalance;
    }
    public void setOriginalBalance(double originalBalance) {
        _originalBalance = originalBalance;
    }
    
}
