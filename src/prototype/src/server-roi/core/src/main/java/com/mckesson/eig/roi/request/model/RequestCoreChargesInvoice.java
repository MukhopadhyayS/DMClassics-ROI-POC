package com.mckesson.eig.roi.request.model;

import java.util.Date;
import java.util.Set;

import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesAdjustmentPayment;

public class RequestCoreChargesInvoice {

    private long _requestCoreDeliveryChargesId;
    private Date _invoiceCreatedDt;
    private double _invoicedAmount;
    private double _paymentAmount;
    private boolean _isInvoice;
    
    private Set<RequestCoreDeliveryChargesAdjustmentPayment> _requestCoreDeliveryChargesAdjustmentPayment;

    public long getRequestCoreDeliveryChargesId() {
        return _requestCoreDeliveryChargesId;
    }

    public void setRequestCoreDeliveryChargesId(
            long _requestCoreDeliveryChargesId) {
        this._requestCoreDeliveryChargesId = _requestCoreDeliveryChargesId;
    }

    public Date getInvoiceCreatedDt() {
        return _invoiceCreatedDt;
    }

    public void setInvoiceCreatedDt(Date _invoiceCreatedDt) {
        this._invoiceCreatedDt = _invoiceCreatedDt;
    }

    public double getInvoicedAmount() {
        return _invoicedAmount;
    }

    public void setInvoicedAmount(double invoicedAmount) {
        this._invoicedAmount = invoicedAmount;
    }

    public double getPaymentAmount() {
        return _paymentAmount;
    }

    public void setPaymentAmount(double _paymentAmount) {
        this._paymentAmount = _paymentAmount;
    }
    public Set<RequestCoreDeliveryChargesAdjustmentPayment> getRequestCoreDeliveryChargesAdjustmentPayment() {
        return _requestCoreDeliveryChargesAdjustmentPayment;
    }

    public void setRequestCoreDeliveryChargesAdjustmentPayment(
            Set<RequestCoreDeliveryChargesAdjustmentPayment> _requestCoreDeliveryChargesAdjPay) {
        _requestCoreDeliveryChargesAdjustmentPayment = _requestCoreDeliveryChargesAdjPay;
    }

    public boolean getIsInvoice() { return _isInvoice; }
    public void setIsInvoice(boolean isInvoiced) { _isInvoice = isInvoiced; }

}
