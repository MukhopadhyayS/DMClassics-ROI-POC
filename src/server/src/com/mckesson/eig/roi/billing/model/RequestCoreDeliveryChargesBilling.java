package com.mckesson.eig.roi.billing.model;

import java.util.LinkedHashSet;
import java.util.Set;


public class RequestCoreDeliveryChargesBilling {
    private Set<RequestCoreDeliveryChargesFee> _requestCoreDeliveryChargesFee = new LinkedHashSet<RequestCoreDeliveryChargesFee>();
    private Set<RequestCoreDeliveryChargesDocument> _requestCoreDeliveryChargesDocument;
    
    public Set<RequestCoreDeliveryChargesFee> getRequestCoreDeliveryChargesFee() {
        return _requestCoreDeliveryChargesFee;
    }
    public void setRequestCoreDeliveryChargesFee(Set<RequestCoreDeliveryChargesFee> requestCoreDeliveryChargesFee) {
        _requestCoreDeliveryChargesFee = requestCoreDeliveryChargesFee;
    }
    public Set<RequestCoreDeliveryChargesDocument> getRequestCoreDeliveryChargesDocument() {
        return _requestCoreDeliveryChargesDocument;
    }
    public void setRequestCoreDeliveryChargesDocument(Set<RequestCoreDeliveryChargesDocument> requestCoreDeliveryChargesDocument) {
        _requestCoreDeliveryChargesDocument = requestCoreDeliveryChargesDocument;
    }
}
