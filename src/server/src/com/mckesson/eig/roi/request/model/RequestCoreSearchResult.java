/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestCoreSearchResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreSearchResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="receiptDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="requestStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastUpdated" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="updatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="patients" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="encounters" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientLocked" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="vip" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreSearchResult", propOrder = {
    "_requestId",
    "_receiptDate",
    "_requestStatus",
    "_requestorName",
    "_requestorType",
    "_requestorTypeName",
    "_subtitle",
    "_lastUpdated",
    "_updatedBy",
    "_balance",
    "_patients",
    "_encounters",
    "_facility",
    "_patientLocked",
    "_vip"
})
public class RequestCoreSearchResult 
implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="requestId")
    private long _requestId;
    
    @XmlElement(name="receiptDate", required = true, nillable = true)
    private Date _receiptDate;
    
    @XmlElement(name="requestStatus", required = true)
    private String _requestStatus;
    
    @XmlElement(name="requestorName", required = true)
    private String _requestorName;
    
    @XmlElement(name="requestorType")
    private long _requestorType;
    
    @XmlElement(name="requestorTypeName", required = true)
    private String _requestorTypeName;
    
    @XmlElement(name="lastUpdated", required = true, nillable = true)
    private Date _lastUpdated;
    
    @XmlElement(name="updatedBy", required = true)
    private String _updatedBy;
    
    @XmlElement(name="patients")
    private List<String> _patients;
    
    @XmlElement(name="subtitle", required = true)
    private String _subtitle;
    
    @XmlElement(name="balance")
    private Double _balance;
    
    @XmlElement(name="patientLocked")
    private boolean _patientLocked;
    
    @XmlElement(name="vip")
    private boolean _vip;
    
    // patient request history screen
    @XmlElement(name="facility", required = true)
    private String _facility;
    
    @XmlElement(name="encounters")
    private List<String> _encounters;
    
    
    
    public long getRequestId() { return _requestId; }
    public void setRequestId(long requestId) { _requestId = requestId; }

    public Date getReceiptDate() { return _receiptDate; }
    public void setReceiptDate(Date receiptDate) { _receiptDate = receiptDate; }

    public String getRequestStatus() { return _requestStatus; }
    public void setRequestStatus(String requestStatus) { _requestStatus = requestStatus; }

    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }

    public long getRequestorType() { return _requestorType; }
    public void setRequestorType(long requestorType) { _requestorType = requestorType; }

    public String getRequestorTypeName() { return _requestorTypeName; }
    public void setRequestorTypeName(String requestorTypeName) {
        _requestorTypeName = requestorTypeName;
    }

    public String getSubtitle() { return _subtitle; }
    public void setSubtitle(String subtitle) { _subtitle = subtitle; }

    public Date getLastUpdated() { return _lastUpdated; }
    public void setLastUpdated(Date lastUpdated) { _lastUpdated = lastUpdated; }

    public String getUpdatedBy() { return _updatedBy; }
    public void setUpdatedBy(String updatedBy) { _updatedBy = updatedBy; }

    public Double getBalance() { return _balance; }
    public void setBalance(Double balance) { _balance = balance; }

    public List<String> getPatients() { return _patients; }
    public void setPatients(List<String> patients) { _patients = patients; }
    
    public String getPatientsString() {
        
        if (null == _patients) {
            return null;
        }
        String string = _patients.toString();
        return string.substring(1, string.length() - 1);
    }
    public void setPatientsString(String patients) {
        
        if (null == patients) {

            _patients = null;
            return;
        }
        String[] split = patients.split(", ~@~");
        _patients = Arrays.asList(split);
    }

    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }
    
    public boolean isPatientLocked() { return _patientLocked; }
    public void setPatientLocked(boolean patientLocked) { _patientLocked = patientLocked; }
    
    public boolean isVip() { return _vip; }
    public void setVip(boolean vip) { _vip = vip; }
    
    public List<String> getEncounters() { return _encounters; }
    public void setEncounters(List<String> encounters) { _encounters = encounters; }

    public String getEncounterString() { 
        
        if (null == _encounters) {
            return null;
        }
        String string = _encounters.toString();
        return string.substring(1, string.length() - 1);
    }
    public void setEncounterString(String encounterString) {
        
        if (null == encounterString) {
            _encounters = null;
            return;
        }
        
        String[] split = encounterString.split(", ~@~");
        _encounters = Arrays.asList(split);
    }
    
    @Override
    public String toString() {
        return new StringBuffer()
                        .append("RequestId:")
                        .append(_requestId)
                        .append(", RequestorName:")
                        .append(_requestorName)
                        .append(", RequestorTypeName:")
                        .append(_requestorTypeName)
                        .append(", Balance:")
                        .append(_balance)
                        .toString();
    }

}
