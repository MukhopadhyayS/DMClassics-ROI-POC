/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
 * Use of this software and related documentation is governed by a license agreement. 
 * This material contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States
 * and international copyright and other intellectual property laws. 
 * Use, disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without the 
 * prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
 */
package com.mckesson.eig.roi.request.model;

import java.io.Serializable;

import com.mckesson.eig.roi.base.model.BaseModel;
/**
 * @author Keane
 * @date July 24, 2012
 */

public class RequestCoreChargesShipping extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private long _requestCoreChargesSeq;
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
    
    private String _countryCode;
    private String _countryName;
    private boolean _newCountry;

    
    public String getNonPrintableAttachmentQueue() {
        return _nonPrintableAttachmentQueue;
    }
    public void setNonPrintableAttachmentQueue(String nonPrintableAttachmentQueue) {
        _nonPrintableAttachmentQueue = nonPrintableAttachmentQueue;
    }
    public long getRequestCoreChargesSeq() {
        return _requestCoreChargesSeq;
    }
    public void setRequestCoreChargesSeq(long requestCoreChargesSeq) {
        _requestCoreChargesSeq = requestCoreChargesSeq;
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
    
    public double getShippingCharge() {
        return _shippingCharge;
    }
    public void setShippingCharge(double shippingCharge) {
        _shippingCharge = shippingCharge;
    }
    public double getShippingWeight() {
        return _shippingWeight;
    }
    public void setShippingWeight(double shippingWeight) {
        _shippingWeight = shippingWeight;
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
    
    public boolean isNewCountry() {
        return _newCountry;
    }
    public void setNewCountry(boolean newCountry) {
        _newCountry = newCountry;
    }
    
}
