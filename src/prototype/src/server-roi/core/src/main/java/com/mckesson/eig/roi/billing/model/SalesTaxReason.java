package com.mckesson.eig.roi.billing.model;

import java.util.Date;

import com.mckesson.eig.roi.base.model.BaseModel;

public class SalesTaxReason extends BaseModel {
    
    private long _requestCoreChargesSeq;
    private String _reason;
    private Date _reasonDate;
    
    public long getRequestCoreChargesSeq() {
        return _requestCoreChargesSeq;
    }
    public void setRequestCoreChargesSeq(long requestCoreChargesSeq) {
        this._requestCoreChargesSeq = requestCoreChargesSeq;
    }
    
    public String getReason() {
        return _reason;
    }
    public void setReason(String reason) {
        this._reason = reason;
    }
    public Date getReasonDate() {
        return _reasonDate;
    }
    public void setReasonDate(Date reasonDate) {
        this._reasonDate = reasonDate;
    }

}
