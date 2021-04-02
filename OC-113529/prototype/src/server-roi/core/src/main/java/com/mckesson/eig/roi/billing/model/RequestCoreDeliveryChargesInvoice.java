package com.mckesson.eig.roi.billing.model;

import java.util.Set;

public class RequestCoreDeliveryChargesInvoice {
    private Set<RequestCoreDeliveryChargesAdjustmentPayment> _requestCoreDeliveryChargesAdjustmentPayment;

    public Set<RequestCoreDeliveryChargesAdjustmentPayment> getRequestCoreDeliveryChargesAdjustmentPayment() {
        return _requestCoreDeliveryChargesAdjustmentPayment;
    }

    public void setRequestCoreDeliveryChargesAdjustmentPayment(Set<RequestCoreDeliveryChargesAdjustmentPayment> requestCoreDeliveryChargesAdjustmentPayment) {
        _requestCoreDeliveryChargesAdjustmentPayment = requestCoreDeliveryChargesAdjustmentPayment;
    }
    
}
