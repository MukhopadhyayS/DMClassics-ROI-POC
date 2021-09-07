package com.mckesson.eig.roi.requestor.model;




public class GeneralFeeTypes {
    
    private String _feeType;
    private boolean _salesTax;
    
    public boolean getSalesTax() {
        return _salesTax;
    }

    public void setSalesTax(boolean salesTax) {
        this._salesTax = salesTax;
    }

    public String getFeeType() {
        return _feeType;
    }

    public void setFeeType(String feeType) {
        this._feeType = feeType;
    }
    
}
