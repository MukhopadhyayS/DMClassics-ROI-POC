package com.mckesson.eig.roi.billing.model;

import java.util.Set;


public class RequestCoreDelivery{
    
    private long _requestCoreId;
    //private boolean _isReleased;
        
    Set<RequestCoreDeliveryChargesInvoice> _requestCoreDeliveryChargesInvoice;
    private RequestCoreDeliveryChargesShipping _requestCoreDeliveryChargesShipping;
    private RequestCoreDeliveryChargesBilling _requestCoreDeliveryChargesBilling;
    private RequestCoreDeliveryCharges _requestCoreDeliveryCharges;
    
    
    public RequestCoreDeliveryChargesShipping getRequestCoreDeliveryChargesShipping() {
        return _requestCoreDeliveryChargesShipping;
    }
    public void setRequestCoreDeliveryChargesShipping(RequestCoreDeliveryChargesShipping requestCoreDeliveryChargesShipping) {
        _requestCoreDeliveryChargesShipping = requestCoreDeliveryChargesShipping;
    }
    public RequestCoreDeliveryChargesBilling getRequestCoreDeliveryChargesBilling() {
        return _requestCoreDeliveryChargesBilling;
    }
    public void setRequestCoreDeliveryChargesBilling(RequestCoreDeliveryChargesBilling requestCoreDeliveryChargesBilling) {
        _requestCoreDeliveryChargesBilling = requestCoreDeliveryChargesBilling;
    }
    public RequestCoreDeliveryCharges getRequestCoreDeliveryCharges() {
        return _requestCoreDeliveryCharges;
    }
    public void setRequestCoreDeliveryCharges(RequestCoreDeliveryCharges requestCoreDeliveryCharges) {
        _requestCoreDeliveryCharges = requestCoreDeliveryCharges;
    }
    public Set<RequestCoreDeliveryChargesInvoice> getRequestCoreDeliveryChargesInvoice() {
        return _requestCoreDeliveryChargesInvoice;
    }
    public void setRequestCoreDeliveryChargesInvoice(Set<RequestCoreDeliveryChargesInvoice> requestCoreDeliveryChargesInvoice) {
        _requestCoreDeliveryChargesInvoice = requestCoreDeliveryChargesInvoice;
    }
    
    public long getRequestCoreId() { return _requestCoreId; }
    public void setRequestCoreId(long requestCoreId) { _requestCoreId = requestCoreId; }
    
    /*public boolean getIsReleased() { return _isReleased; }
    public void setIsReleased(boolean isReleased) { _isReleased = isReleased; }*/
    
}
