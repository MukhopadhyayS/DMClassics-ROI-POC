package com.mckesson.eig.roi.requestor.model;

import java.util.Date;

public class RequestorAdjustmentsPayments {

    private Long _id;
    private Date _date;
    private Double _paymentAmount;
    private String _txnType;
    private long _invoiceId;
    private Double _unAppliedAmt;
    private String _description;
    private String _paymentMethod;
    private Double _appliedAmount;
    private Double _refundAmount;
    private Double _amount; 
    private long _requestorId;
    private boolean _prebillPaymentsAdjustments;

    public Double getAmount() {
        return _amount;
    }
    public void setAmount(Double amount) {
        this._amount = amount;
    }
    public Long getId() { return _id; }
    public void setId(Long id) { _id = id; }

    public Date getDate() { return _date; }
    public void setDate(Date date) { _date = date; }

    public String getTxnType() { return _txnType; }
    public void setTxnType(String txnType) { _txnType = txnType; }

    public long getInvoiceId() { return _invoiceId; }
    public void setInvoiceId(long invoiceId) { _invoiceId = invoiceId; }

    public Double getUnAppliedAmt() { return _unAppliedAmt; }
    public void setUnAppliedAmt(Double unAppliedAmt) { _unAppliedAmt = unAppliedAmt; }

    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }

    public String getPaymentMethod() { return _paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { _paymentMethod = paymentMethod; }

    public Double getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(Double paymentAmount) { _paymentAmount = paymentAmount; }

    public Double getAppliedAmount() { return _appliedAmount; }
    public void setAppliedAmount(Double appliedAmount) { _appliedAmount = appliedAmount; }

    public Double getRefundAmount() { return _refundAmount; }
    public void setRefundAmount(Double refundAmount) { _refundAmount = refundAmount; }
    
    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }
    
    public boolean isPrebillPaymentsAdjustments() {
        return _prebillPaymentsAdjustments;
    }
    public void setPrebillPaymentsAdjustments(boolean prebillPaymentsAdjustments) {
        _prebillPaymentsAdjustments = prebillPaymentsAdjustments;
    }
    
    @Override
    public String toString() {
        return new StringBuffer()
                    .append("Transaction Type:")
                    .append(_txnType)
                    .append(", TransactionId:")
                    .append(_id)
                    .toString();
    }

}
