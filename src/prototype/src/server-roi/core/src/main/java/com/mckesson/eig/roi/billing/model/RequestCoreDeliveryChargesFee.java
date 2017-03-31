package com.mckesson.eig.roi.billing.model;

import com.mckesson.eig.roi.base.model.BaseModel;

public class RequestCoreDeliveryChargesFee extends BaseModel {
    
    private static final long serialVersionUID = 2686943502602764003L;
    private long _requestCoreDeliveryChargesId;
    private double _amount;
    private boolean _isCustomFee;
    private String _feeType;
    private boolean _hasSalesTax;
    private double _salesTaxAmount;
    
    public boolean getHasSalesTax() {
        return _hasSalesTax;
    }
    public void setHasSalesTax(boolean hasSalesTax) {
        _hasSalesTax = hasSalesTax;
    }
    public double getSalesTaxAmount() {
        return _salesTaxAmount;
    }
    public void setSalesTaxAmount(double salesTaxAmount) {
        _salesTaxAmount = salesTaxAmount;
    }
    public long getRequestCoreDeliveryChargesId() {
        return _requestCoreDeliveryChargesId;
    }
    public void setRequestCoreDeliveryChargesId(long requestCoreDeliveryChargesId) {
        _requestCoreDeliveryChargesId = requestCoreDeliveryChargesId;
    }
    
    public double getAmount() {
        return _amount;
    }
    public void setAmount(double amount) {
        _amount = amount;
    }
    public boolean getIsCustomFee() {
        return _isCustomFee;
    }
    public void setIsCustomFee(boolean isCustomFee) {
        _isCustomFee = isCustomFee;
    }
    public String getFeeType() {
        return _feeType;
    }
    public void setFeeType(String feeType) {
        _feeType = feeType;
    }
      
}
