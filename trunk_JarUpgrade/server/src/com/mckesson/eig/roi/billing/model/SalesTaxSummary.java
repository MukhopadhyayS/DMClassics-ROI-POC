package com.mckesson.eig.roi.billing.model;

import java.util.List;

public class SalesTaxSummary {
    
    private List<SalesTaxReason> _salesTaxReason;

    public List<SalesTaxReason> getSalesTaxReason() {
        return _salesTaxReason;
    }

    public void setSalesTaxReason(List<SalesTaxReason> salesTaxReasonList) {
        this._salesTaxReason = salesTaxReasonList;
    }

}
