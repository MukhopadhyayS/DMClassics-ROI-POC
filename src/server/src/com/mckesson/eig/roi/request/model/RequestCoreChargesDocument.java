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
 * <p>Java class for RequestCoreChargesDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreChargesDocument">
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
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="copies" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="billingTierName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="totalPages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="billingtierId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="releaseCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="isElectronic" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="removeBaseCharge" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasSalesTax" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="salesTaxAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreChargesDocument", propOrder = {
    "_id",
    "_createdBy",
    "_modifiedBy",
    "_createdDt",
    "_modifiedDt",
    "_recordVersion",
    "_requestCoreChargesSeq",
    "_amount",
    "_copies",
    "_billingTierName",
    "_totalPages",
    "_pages",
    "_billingtierId",
    "_releaseCount",
    "_isElectronic",
    "_removeBaseCharge",
    "_hasSalesTax",
    "_salesTaxAmount"
})
public class RequestCoreChargesDocument extends BaseModel
        implements
            Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="requestCoreChargesSeq")
    private long _requestCoreChargesSeq;
    
    @XmlElement(name="amount")
    private double _amount;
    
    @XmlElement(name="copies")
    private int _copies;
    
    @XmlElement(name="billingTierName", required = true)
    private String _billingTierName;
    
    @XmlElement(name="totalPages")
    private int _totalPages;
    
    @XmlElement(name="pages")
    private int _pages;
    
    @XmlElement(name="billingtierId", required = true)
    private String _billingtierId;
    
    @XmlElement(name="releaseCount")
    private int _releaseCount;
    
    @XmlElement(name="isElectronic")
    private boolean _isElectronic;
    
    @XmlElement(name="removeBaseCharge")
    private boolean _removeBaseCharge;
    
    @XmlElement(name="hasSalesTax")
    private boolean _hasSalesTax;
    
    @XmlElement(name="salesTaxAmount")
    private double _salesTaxAmount;
    
    
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
    public long getRequestCoreChargesSeq() {
        return _requestCoreChargesSeq;
    }
    public void setRequestCoreChargesSeq(long _requestCoreChargesSeq) {
        this._requestCoreChargesSeq = _requestCoreChargesSeq;
    }
    
    public double getAmount() {
        return _amount;
    }
    public void setAmount(double amount) {
        _amount = amount;
    }
    public String getBillingtierId() {
        return _billingtierId;
    }
    public void setBillingtierId(String billingtierId) {
        _billingtierId = billingtierId;
    }
    public int getCopies() {
        return _copies;
    }
    public void setCopies(int _copies) {
        this._copies = _copies;
    }
    public String getBillingTierName() {
        return _billingTierName;
    }
    public void setBillingTierName(String _billingTierName) {
        this._billingTierName = _billingTierName;
    }
    public int getTotalPages() {
        return _totalPages;
    }
    public void setTotalPages(int _totalPages) {
        this._totalPages = _totalPages;
    }
    public int getPages() {
        return _pages;
    }
    public void setPages(int _pages) {
        this._pages = _pages;
    }
    
    public int getReleaseCount() {
        return _releaseCount;
    }
    public void setReleaseCount(int _releaseCount) {
        this._releaseCount = _releaseCount;
    }
    public boolean isIsElectronic() {
        return _isElectronic;
    }
    public void setIsElectronic(boolean _isElectronic) {
        this._isElectronic = _isElectronic;
    }
    public boolean isRemoveBaseCharge() {
        return _removeBaseCharge;
    }
    public void setRemoveBaseCharge(boolean _removeBaseCharge) {
        this._removeBaseCharge = _removeBaseCharge;
    }

}
