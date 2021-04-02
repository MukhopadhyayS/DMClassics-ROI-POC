package com.mckesson.eig.roi.requestor.model;

import java.util.List;

public class RequestorInvoicesList {

    private List<RequestorInvoice> _requestorInvoices = null;

    public RequestorInvoicesList() { }
    public RequestorInvoicesList(List<RequestorInvoice> requestorInvoices) {
        setRequestorInvoices(requestorInvoices);
    }

    public List<RequestorInvoice> getRequestorInvoices() { return _requestorInvoices; }
    public void setRequestorInvoices(List<RequestorInvoice> requestorInvoices) {
        _requestorInvoices = requestorInvoices;
    }


}
