package com.mckesson.eig.roi.billing.model;

import com.mckesson.eig.roi.base.model.BaseModel;

public class RequestCoreDeliveryChargesDocument extends BaseModel{
    private long _requestCoreDeliveryChargesId;
    private double _amount;
    private int _copies;
    private String _billingTierName;
    private int _totalPages;
    private int _pages;
    private String _billingtierId;
    private int _releaseCount;
    private boolean _isElectronic;
    private boolean _removeBaseCharge;
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
    public int getCopies() {
        return _copies;
    }
    public void setCopies(int copies) {
        _copies = copies;
    }
    public String getBillingTierName() {
        return _billingTierName;
    }
    public void setBillingTierName(String billingtierName) {
        _billingTierName = billingtierName;
    }
    public int getTotalPages() {
        return _totalPages;
    }
    public void setTotalPages(int totalPages) {
        _totalPages = totalPages;
    }
    public int getPages() {
        return _pages;
    }
    public void setPages(int pages) {
        _pages = pages;
    }
    public String getBillingtierId() {
        return _billingtierId;
    }
    public void setBillingtierId(String billingtierId) {
        _billingtierId = billingtierId;
    }
    public int getReleaseCount() {
        return _releaseCount;
    }
    public void setReleaseCount(int releaseCount) {
        _releaseCount = releaseCount;
    }
    public boolean getIsElectronic() {
        return _isElectronic;
    }
    public void setIsElectronic(boolean isElectronic) {
        _isElectronic = isElectronic;
    }
    public boolean getRemoveBaseCharge() {
        return _removeBaseCharge;
    }
    public void setRemoveBaseCharge(boolean removeBasecharge) {
        _removeBaseCharge = removeBasecharge;
    }

}
