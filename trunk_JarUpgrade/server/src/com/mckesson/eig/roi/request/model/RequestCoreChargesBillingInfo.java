package com.mckesson.eig.roi.request.model;

import java.util.Set;

import com.mckesson.eig.roi.billing.model.SalesTaxSummary;

public class RequestCoreChargesBillingInfo extends RequestCoreCharges{
    Set<RequestCoreChargesInvoice> _requestCoreChargesInvoice;
    private SalesTaxSummary _salesTaxSummary;

    public Set<RequestCoreChargesInvoice> getRequestCoreChargesInvoice() {
        return _requestCoreChargesInvoice;
    }

    public void setRequestCoreChargesInvoice(Set<RequestCoreChargesInvoice> requestCoreChargesInvoice) {
        _requestCoreChargesInvoice = requestCoreChargesInvoice;
    }
    
    public SalesTaxSummary getSalesTaxSummary() {
        return _salesTaxSummary;
    }

    public void setSalesTaxSummary(SalesTaxSummary salesTaxSummary) {
        this._salesTaxSummary = salesTaxSummary;
    }
}
