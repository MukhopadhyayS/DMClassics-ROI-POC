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
import java.util.Date;

import com.mckesson.eig.roi.base.model.BaseModel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestCoreChargesShipping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreChargesShipping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="modifiedDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="requestCoreChargesSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="shippingCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="addressType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shippingUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shippingWeight" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="trackingNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="countryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="countryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newCountry" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="willReleaseShipped" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="shippingMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shippingMethodId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="nonPrintableAttachmentQueue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreChargesShipping", propOrder = {
    "_id",
    "_createdBy",
    "_modifiedBy",
    "_createdDt",
    "_modifiedDt",
    "_recordVersion",
    "_requestCoreChargesSeq",
    "_shippingCharge",
    "_postalCode",
    "_addressType",
    "_state",
    "_shippingUrl",
    "_address1",
    "_address2",
    "_shippingWeight",
    "_trackingNumber",
    "_address3",
    "_city",
    "_countryCode",
    "_countryName",
    "_newCountry",
    "_willReleaseShipped",
    "_shippingMethod",
    "_outputMethod",
    "_shippingMethodId",
    "_nonPrintableAttachmentQueue"
})
public class RequestCoreChargesShipping extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="requestCoreChargesSeq")
    private long _requestCoreChargesSeq;
    
    @XmlElement(name="shippingCharge")
    private double _shippingCharge;
    
    @XmlElement(name="postalCode", required = true)
    private String _postalCode;
    
    @XmlElement(name="addressType", required = true)
    private String _addressType;
    
    @XmlElement(name="state", required = true)
    private String _state;
    
    @XmlElement(name="shippingUrl", required = true)
    private String _shippingUrl;
    
    @XmlElement(name="address1", required = true)
    private String _address1;
    
    @XmlElement(name="address2", required = true)
    private String _address2;
    
    @XmlElement(name="shippingWeight")
    private double _shippingWeight;
    
    @XmlElement(name="trackingNumber", required = true)
    private String _trackingNumber;
    
    @XmlElement(name="address3", required = true)
    private String _address3;
    
    @XmlElement(name="city", required = true)
    private String _city;
    
    @XmlElement(name="countryCode", required = true)
    private String _countryCode;
    
    @XmlElement(name="countryName", required = true)
    private String _countryName;
    
    @XmlElement(name="newCountry")
    private boolean _newCountry;
    
    @XmlElement(name="willReleaseShipped")
    private boolean _willReleaseShipped;
    
    @XmlElement(name="shippingMethod", required = true)
    private String _shippingMethod;
    
    @XmlElement(name="outputMethod", required = true)
    private String _outputMethod;
    
    @XmlElement(name="shippingMethodId")
    private long _shippingMethodId;
    
    @XmlElement(name="nonPrintableAttachmentQueue", required = true)
    private String _nonPrintableAttachmentQueue;
    
    

    @XmlElement(name="id")
    private long _id;
    
    @XmlElement(name="createdBy")
    private long _createdBy;
    
    @XmlElement(name="modifiedBy")
    private long _modifiedBy;
    
    @XmlElement(name="createdDt", required = true, nillable = true)
    private Date _createdDt;
    
    @XmlElement(name="modifiedDt", required = true, nillable = true)
    private Date _modifiedDt;
    
    @XmlElement(name="recordVersion")
    private int _recordVersion; 


    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public long getCreatedBy() {
        return _createdBy;
    }

    public void setCreatedBy(long createdBy) {
        _createdBy = createdBy;
    }

    public long getModifiedBy() {
        return _modifiedBy;
    }

    public void setModifiedBy(long modifiedBy) {
        _modifiedBy = modifiedBy;
    }

    public Date getCreatedDt() {
        return _createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        _createdDt = createdDt;
    }

    public Date getModifiedDt() {
        return _modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        _modifiedDt = modifiedDt;
    }

    public int getRecordVersion() {
        return _recordVersion;
    }

    public void setRecordVersion(int recordVersion) {
        _recordVersion = recordVersion;
    }
    
    
    
    
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
