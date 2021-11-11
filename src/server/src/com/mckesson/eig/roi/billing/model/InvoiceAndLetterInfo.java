/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.billing.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceAndLetterInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceAndLetterInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="invoices" type="{urn:eig.mckesson.com}RequestorInvoices" maxOccurs="unbounded"/>
 *         &lt;element name="invoiceTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorLetterTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="reqLetterNotes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="invoiceNotes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pastInvIds" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="isLetter" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isNewInvoice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isPastInvoice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="outputInvoice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="statementCriteria" type="{urn:eig.mckesson.com}RequestorStatementCriteria"/>
 *         &lt;element name="properties" type="{urn:eig.mckesson.com}propertiesMap" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceAndLetterInfo", propOrder = {
    "_invoices",
    "_invoiceTemplateId",
    "_requestorLetterTemplateId",
    "_reqLetterNotes",
    "_invoiceNotes",
    "_pastInvIds",
    "_isLetter",
    "_isNewInvoice",
    "_isPastInvoice",
    "_isOutputInvoice",
    "_statementCriteria",
    "properties"
})
public class InvoiceAndLetterInfo {

    
    @XmlElement(name="invoices", required = true)
    private List<RequestorInvoices> _invoices;
    
    @XmlElement(name="invoiceTemplateId")
    private long _invoiceTemplateId;
    
    @XmlElement(name="requestorLetterTemplateId")
    private long _requestorLetterTemplateId;
    
    @XmlElement(name="reqLetterNotes")
    private List<String> _reqLetterNotes;
    
    @XmlElement(name="invoiceNotes")
    private List<String> _invoiceNotes;
    
    @XmlElement(name="pastInvIds")
    private long[] _pastInvIds;
    
    @XmlElement(name="isLetter")
    private boolean _isLetter;
    
    @XmlElement(name="isNewInvoice")
    private boolean _isNewInvoice;
    
    @XmlElement(name="isPastInvoice")
    private boolean _isPastInvoice;
    
    @XmlElement(name="outputInvoice")
    private boolean _isOutputInvoice;
    
    @XmlElement(name="statementCriteria", required = true)
    private RequestorStatementCriteria _statementCriteria;
    
    protected List<PropertiesMap> properties;
    
    @XmlTransient
    private long _letterTemplateId;
   
    
    
   

    public List<String> getReqLetterNotes() { return _reqLetterNotes; }
    public void setReqLetterNotes(List<String> reqNotes) { _reqLetterNotes = reqNotes; }

    public List<String> getInvoiceNotes() { return _invoiceNotes; }
    public void setInvoiceNotes(List<String> invoiceNotes) { _invoiceNotes = invoiceNotes; }

    public List<RequestorInvoices> getInvoices() { return _invoices; }
    public void setInvoices(List<RequestorInvoices> invoices) { _invoices = invoices; }

    public long getInvoiceTemplateId() { return _invoiceTemplateId; }
    public void setInvoiceTemplateId(long invoiceTemplateId) {
        _invoiceTemplateId = invoiceTemplateId;
    }

    public long getRequestorLetterTemplateId() { return _requestorLetterTemplateId; }
    public void setRequestorLetterTemplateId(long requestorLetterTemplateId) {
        _requestorLetterTemplateId = requestorLetterTemplateId;
    }
    /*public Map<String, String> getProperties() { return _properties; }
    public void setProperties(Map<String, String> properties) { _properties = properties; }*/
    
    public List<PropertiesMap> getProperties() {
        if (properties == null) {
            properties = new ArrayList<PropertiesMap>();
        }
        return this.properties;
    }
    
    public void setProperties(List<PropertiesMap> properties) {
        this.properties = properties;
    }
    
    
    public void setIsLetter(boolean isLetter) { _isLetter = isLetter; }
    public boolean getIsLetter() { return _isLetter; }

    public void setIsNewInvoice(boolean isNewInvoice) { _isNewInvoice = isNewInvoice; }
    public boolean getIsNewInvoice() { return _isNewInvoice; }

    public void setIsPastInvoice(boolean isPastInvoice) { _isPastInvoice = isPastInvoice; }
    public boolean getIsPastInvoice() { return _isPastInvoice; }

    public void setLetterTemplateId(long letterTemplateId) { _letterTemplateId = letterTemplateId; }
    public long getLetterTemplateId() { return _letterTemplateId; }

    public void setPastInvIds(long[] pastInvIds) { _pastInvIds = pastInvIds; }
    public long[] getPastInvIds() { return _pastInvIds; }

    public RequestorStatementCriteria getStatementCriteria() { return _statementCriteria; }
    public void setStatementCriteria(RequestorStatementCriteria statementCriteria) {
        _statementCriteria = statementCriteria;
    }

    public boolean isOutputInvoice() { return _isOutputInvoice; }
    public void setOutputInvoice(boolean isOutputInvoice) { _isOutputInvoice = isOutputInvoice; }
}
