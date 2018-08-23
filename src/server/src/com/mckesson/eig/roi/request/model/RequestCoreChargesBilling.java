package com.mckesson.eig.roi.request.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class RequestCoreChargesBilling {
    private Set<RequestCoreChargesFee> _requestCoreChargesFee = new LinkedHashSet<RequestCoreChargesFee>();
    private Set<RequestCoreChargesDocument> _requestCoreChargesDocument;
    
    public Set<RequestCoreChargesFee> getRequestCoreChargesFee() {
        return _requestCoreChargesFee;
    }
    public void setRequestCoreChargesFee(LinkedHashSet<RequestCoreChargesFee> requestCoreChargesFee) {
        _requestCoreChargesFee = requestCoreChargesFee;
    }
    public Set<RequestCoreChargesDocument> getRequestCoreChargesDocument() {
        return _requestCoreChargesDocument;
    }
    public void setRequestCoreChargesDocument(Set<RequestCoreChargesDocument> requestCoreChargesDocument) {
        _requestCoreChargesDocument = requestCoreChargesDocument;
    }
   
}
