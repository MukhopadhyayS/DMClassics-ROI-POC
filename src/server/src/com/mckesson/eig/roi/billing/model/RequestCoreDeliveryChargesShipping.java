package com.mckesson.eig.roi.billing.model;

import com.mckesson.eig.roi.base.model.BaseModel;

public class RequestCoreDeliveryChargesShipping extends BaseModel {

    private static final long serialVersionUID = 1L;
    private long _requestCoreDeliveryChargesId;
    private double _shippingCharge;
    private String _postalCode;
    private String _addressType;
    private String _state;
    private String _shippingUrl;
    private String _address1;
    private String _address2;
    private double _shippingWeight;
    private String _trackingNumber;
    private String _address3;
    private String _city;
    private boolean _willReleaseShipped;
    private String _shippingMethod;
    private String _outputMethod;
    private long _shippingMethodId;
    private String _nonPrintableAttachmentQueue;
        
    private String _countryName;
    private String _countryCode;
    
    public String getNonPrintableAttachmentQueue() {
        return _nonPrintableAttachmentQueue;
    }
    public void setNonPrintableAttachmentQueue(String nonPrintableAttachmentQueue) {
        _nonPrintableAttachmentQueue = nonPrintableAttachmentQueue;
    }
    public long getRequestCoreDeliveryChargesId() {
        return _requestCoreDeliveryChargesId;
    }
    public void setRequestCoreDeliveryChargesId(long requestCoreDeliveryChargesId) {
        _requestCoreDeliveryChargesId = requestCoreDeliveryChargesId;
    }
    public double getShippingCharge() {
        return _shippingCharge;
    }
    public void setShippingCharge(double shippingCharge) {
        _shippingCharge = shippingCharge;
    }
    public String getPostalCode() {
        return _postalCode;
    }
    public void setPostalCode(String postalCode) {
        _postalCode = postalCode;
    }
    public String getAddressType() {
        return _addressType;
    }
    public void setAddressType(String addressType) {
        _addressType = addressType;
    }
    public String getState() {
        return _state;
    }
    public void setState(String state) {
        _state = state;
    }
    public String getShippingUrl() {
        return _shippingUrl;
    }
    public void setShippingUrl(String shippingUrl) {
        _shippingUrl = shippingUrl;
    }
    public String getAddress1() {
        return _address1;
    }
    public void setAddress1(String address1) {
        _address1 = address1;
    }
    public String getAddress2() {
        return _address2;
    }
    public void setAddress2(String address2) {
        _address2 = address2;
    }
    public double getShippingWeight() {
        return _shippingWeight;
    }
    public void setShippingWeight(double shippingWeight) {
        _shippingWeight = shippingWeight;
    }
    public String getTrackingNumber() {
        return _trackingNumber;
    }
    public void setTrackingNumber(String trackingNumber) {
        _trackingNumber = trackingNumber;
    }
    public String getAddress3() {
        return _address3;
    }
    public void setAddress3(String address3) {
        _address3 = address3;
    }
    public String getCity() {
        return _city;
    }
    public void setCity(String city) {
        _city = city;
    }
    public boolean isWillReleaseShipped() {
        return _willReleaseShipped;
    }
    public void setWillReleaseShipped(boolean willReleaseShipped) {
        _willReleaseShipped = willReleaseShipped;
    }
    public String getShippingMethod() {
        return _shippingMethod;
    }
    public void setShippingMethod(String shippingMethod) {
        _shippingMethod = shippingMethod;
    }
    public String getOutputMethod() {
        return _outputMethod;
    }
    public void setOutputMethod(String outputMethod) {
        _outputMethod = outputMethod;
    }
    public long getShippingMethodId() {
        return _shippingMethodId;
    }
    public void setShippingMethodId(long shippingMethodId) {
        _shippingMethodId = shippingMethodId;
    }
    
    public String getCountryCode() {
        return _countryCode;
    }
    public void setCountryCode(String countryCode) {
        _countryCode = countryCode;
    }
    
    public String getCountryName() {
        return _countryName;
    }
    public void setCountryName(String countryName) {
        _countryName = countryName;
    }
    
}
