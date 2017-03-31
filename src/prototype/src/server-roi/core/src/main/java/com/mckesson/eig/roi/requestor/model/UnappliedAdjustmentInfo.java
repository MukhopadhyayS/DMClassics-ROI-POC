package com.mckesson.eig.roi.requestor.model;

import java.util.Date;

public class UnappliedAdjustmentInfo {
    
    private String reason;
    private Double amount;
    private Double unappliedAmount;
    private Date adjustmentDate;
    private Double salesTaxAmount;
    private String note;
    private String facilityCode;
    private String facilityName;
    private String feeName;
    private double feeAmount;
    private double salestaxFeeAmount;
    private String feeType;
    private boolean isTaxable;
   
    public String getFeeName() {
        return feeName;
    }
    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }
    public double getFeeAmount() {
        return feeAmount;
    }
    public void setFeeAmount(double amount) {
        this.feeAmount = amount;
    }
    public double getSalestaxFeeAmount() {
        return salestaxFeeAmount;
    }
    public void setSalestaxFeeAmount(double salestaxAmount) {
        this.salestaxFeeAmount = salestaxAmount;
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
  
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Double getUnappliedAmount() {
        return unappliedAmount;
    }
    public void setUnappliedAmount(Double unappliedAmount) {
        this.unappliedAmount = unappliedAmount;
    }
    public Date getAdjustmentDate() {
        return adjustmentDate;
    }
    public void setAdjustmentDate(Date adjustmentDate) {
        this.adjustmentDate = adjustmentDate;
    }
    public Double getSalesTaxAmount() {
        return salesTaxAmount;
    }
    public void setSalesTaxAmount(Double salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getFacilityCode() {
        return facilityCode;
    }
    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }
    public String getFacilityName() {
        return facilityName;
    }
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

}
