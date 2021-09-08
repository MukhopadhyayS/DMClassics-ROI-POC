package com.mckesson.eig.roi.billing.model;

import com.mckesson.eig.roi.base.model.BaseModel;

public class RequestCoreDeliveryInvoicePatients extends BaseModel {
    
    private long _requestCoreDeliveryChargesId;
    private Long _requestHpfPatientsId;
    private Long _requestNonHpfPatientsId;
    
    public long getRequestCoreDeliveryChargesId() {
        return _requestCoreDeliveryChargesId;
    }
    public void setRequestCoreDeliveryChargesId(long requestCoreDeliveryChargesId) {
        this._requestCoreDeliveryChargesId = requestCoreDeliveryChargesId;
    }
    public Long getRequestHpfPatientsId() {
        return _requestHpfPatientsId;
    }
    public void setRequestHpfPatientsId(Long requestHpfPatientsId) {
        this._requestHpfPatientsId = requestHpfPatientsId;
    }
    public Long getRequestNonHpfPatientsId() {
        return _requestNonHpfPatientsId;
    }
    public void setRequestNonHpfPatientsId(Long requestNonHpfPatientsId) {
        this._requestNonHpfPatientsId = requestNonHpfPatientsId;
    }
   
}
