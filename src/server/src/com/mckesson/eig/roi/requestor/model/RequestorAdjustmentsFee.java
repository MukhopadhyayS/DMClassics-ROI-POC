package com.mckesson.eig.roi.requestor.model;

import com.mckesson.eig.roi.base.model.BaseModel;

public class RequestorAdjustmentsFee extends BaseModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String feeName;
    private double amount;
    private double salestaxAmount;
    private String feeType;
    private boolean isTaxable;
   
    public String getFeeName() {
        return feeName;
    }
    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getSalestaxAmount() {
        return salestaxAmount;
    }
    public void setSalestaxAmount(double salestaxAmount) {
        this.salestaxAmount = salestaxAmount;
    }
    public String getFeeType() {
        return feeType;
    }
    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }
    public boolean getIsTaxable() {
        return isTaxable;
    }
    public void setIsTaxable(boolean isTaxable) {
        this.isTaxable = isTaxable;
    }
   
    
   
}
