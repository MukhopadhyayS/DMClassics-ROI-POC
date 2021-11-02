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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.base.model.BaseModel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestPatient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestPatient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="freeformFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="epn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientLocked" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="encounterLocked" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isVip" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hpf" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="roiEncounters" type="{urn:eig.mckesson.com}RequestEncounter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supplementalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="attachments" type="{urn:eig.mckesson.com}RequestSupplementalAttachment" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nonHpfDocuments" type="{urn:eig.mckesson.com}RequestSupplementalDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="globalDocuments" type="{urn:eig.mckesson.com}RequestDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="zip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="homePhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="workPhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestPatient", propOrder = {
    "_patientSeq",
    "_requestId",
    "_mrn",
    "_name",
    "_facility",
    "_freeformFacility",
    "_dob",
    "_gender",
    "_epn",
    "_ssn",
    "_patientLocked",
    "_encounterLocked",
    "_isVip",
    "_isHpf",
    "_roiEncounters",
    "_supplementalId",
    "_attachments",
    "_nonHpfDocuments",
    "_globalDocuments",
    "_firstName",
    "_lastName",
    "_address1",
    "_address2",
    "_address3",
    "_city",
    "_state",
    "_zip",
    "_homePhone",
    "_workPhone"
})
public class RequestPatient extends BaseModel {

    private static final long serialVersionUID = 1L;
    
    // Patient Details
    @XmlElement(name="patientSeq")
    private long    _patientSeq;
    
    @XmlElement(name="requestId")
    private long    _requestId;
    
    @XmlElement(name="mrn", required = true)
    private String  _mrn;
    
    @XmlElement(name="name", required = true)
    private String  _name;
    
    @XmlElement(name="facility", required = true)
    private String  _facility;
    
    @XmlElement(name="freeformFacility", required = true)
    private String  _freeformFacility;
    
    @XmlElement(name="dob", required = true, nillable = true)
    private Date  _dob;
    
    @XmlElement(name="gender", required = true)
    private String  _gender;
    
    @XmlElement(name="epn", required = true)
    private String  _epn;
    
    @XmlElement(name="ssn", required = true)
    private String  _ssn;
    
    @XmlElement(name="patientLocked")
    private boolean  _patientLocked;
    
    @XmlElement(name="encounterLocked")
    private boolean  _encounterLocked;
    
    @XmlElement(name="isVip")
    private boolean  _isVip;
    
    @XmlElement(name="roiEncounters")
    private List<RequestEncounter> _roiEncounters;
    
    @XmlElement(name="globalDocuments")
    private List<RequestDocument> _globalDocuments;
    
    @XmlElement(name="firstName", required = true)
    private String _firstName;
    
    @XmlElement(name="lastName", required = true)
    private String _lastName;
    
    @XmlElement(name="address1", required = true)
    private String _address1;
    
    @XmlElement(name="address2", required = true)
    private String _address2;
    
    @XmlElement(name="address3", required = true)
    private String _address3;
    
    @XmlElement(name="city", required = true)
    private String _city;
    
    @XmlElement(name="state", required = true)
    private String _state;
    
    @XmlElement(name="zip", required = true)
    private String _zip;
    
    @XmlElement(name="homePhone", required = true)
    private String _homePhone;
    
    @XmlElement(name="workPhone", required = true)
    private String _workPhone;

    
    
    //for supplemental patients
    @XmlElement(name="supplementalId")
    private long    _supplementalId;
    
    @XmlElement(name="hpf")
    private boolean _isHpf;
    
    
    // for non hpf documents and attachments
    @XmlElement(name="attachments")
    private List<RequestSupplementalAttachment> _attachments;
    
    @XmlElement(name="nonHpfDocuments")
    private List<RequestSupplementalDocument> _nonHpfDocuments;
    
    // for letter template Use
    @XmlTransient
    private long    _invoiceId;
    
    
    public void setPatientSeq(long patientId) { _patientSeq = patientId; }
    public long getPatientSeq() { return _patientSeq; }
    
    public long getRequestId() { return _requestId; }
    public void setRequestId(long requestId) { _requestId = requestId; }

    public List<RequestEncounter> getRoiEncounters() { return _roiEncounters; }
    public void setRoiEncounters(List<RequestEncounter> roiEncounters) { 
        _roiEncounters = roiEncounters; }
    
    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }
    
    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }

    public String getGender() { return _gender; }
    public void setGender(String gender) { _gender = gender; }
    
    public String getEpn() { return _epn; }
    public void setEpn(String epn) { _epn = epn; }
    
    public String getSsn() { return _ssn; }
    public void setSsn(String ssn) { _ssn = ssn; }

    public Date getDob() { return _dob; }
    public void setDob(Date dob) { _dob = dob; }

    public void setPatientLocked(boolean patientLocked) { _patientLocked = patientLocked; }
    public boolean isPatientLocked() { return _patientLocked; }
    
    public void setEncounterLocked(boolean encounterLocked) { _encounterLocked = encounterLocked; }
    public boolean isEncounterLocked() { return _encounterLocked; }
    
    public void setName(String name) { _name = name; }
    public String getName() { return _name; }
    
    public void setVip(boolean isVip) { _isVip = isVip; }
    public boolean isVip() { return _isVip; }

    public long getSupplementalId() { return _supplementalId; }
    public void setSupplementalId(long supplementalId) { _supplementalId = supplementalId; }
    
    public boolean isHpf() { return _isHpf; }
    public void setHpf(boolean isHpf) { _isHpf = isHpf; }
    
    public List<RequestSupplementalAttachment> getAttachments() { return _attachments; }
    public void setAttachments(List<RequestSupplementalAttachment> attachments) {
        _attachments = attachments;
    }
    
    public List<RequestSupplementalDocument> getNonHpfDocuments() { return _nonHpfDocuments; }
    public void setNonHpfDocuments(List<RequestSupplementalDocument> nonHpfDocuments) {
        _nonHpfDocuments = nonHpfDocuments;
    }

    public long getInvoiceId() { return _invoiceId; }
    public void setInvoiceId(long invoiceId) { _invoiceId = invoiceId; }
    
    public String getFreeformFacility() { return _freeformFacility; }
    public void setFreeformFacility(String freeformFacility) {
        _freeformFacility = freeformFacility;
    }
    
    public List<RequestDocument> getGlobalDocuments() { return _globalDocuments; }
    public void setGlobalDocuments(List<RequestDocument> globalDocuments) {
        _globalDocuments = globalDocuments;
    }
    
    public String getFirstName() { return _firstName; }
    public void setFirstName(String firstName) { _firstName = firstName; }
    
    public String getLastName() { return _lastName; }
    public void setLastName(String lastName) { _lastName = lastName; }
    
    public String getAddress1() { return _address1; }
    public void setAddress1(String address1) { _address1 = address1; }
    
    public String getAddress2() { return _address2; }
    public void setAddress2(String address2) { _address2 = address2; }
    
    public String getAddress3() { return _address3; }
    public void setAddress3(String address3) { _address3 = address3; }
    
    public String getCity() { return _city; }
    public void setCity(String city) { _city = city; }
    
    public String getState() { return _state; }
    public void setState(String state) { _state = state; }
    
    public String getZip() { return _zip; }
    public void setZip(String zip) { _zip = zip; }
    
    public String getHomePhone() { return _homePhone; }
    public void setHomePhone(String homePhone) { _homePhone = homePhone; }
    
    public String getWorkPhone() { return _workPhone; }
    public void setWorkPhone(String workPhone) { _workPhone = workPhone; }
    public void addNonHpfDocuments(RequestSupplementalDocument nonHpfDoc) {
        
        if (null == nonHpfDoc) {
            return;
        }
        
        if (null == _nonHpfDocuments) {
            _nonHpfDocuments = new ArrayList<RequestSupplementalDocument>();
        }
        _nonHpfDocuments.add(nonHpfDoc);
    }

    public void addSupplementalAttachments(RequestSupplementalAttachment attachment) {
        
        if (null == attachment) {
            return;
        }
        
        if (null == _attachments) {
            _attachments = new ArrayList<RequestSupplementalAttachment>();
        }
        _attachments.add(attachment);
    }

    @Override
    public String toString() {
        
        return new StringBuffer()
                    .append("Patient:")
                    .append(_patientSeq)
                    .append(" , Request Id:")
                    .append(_requestId)
                    .append(" , Requestor Id:")
                    .append(" , Name:")
                    .append(" , MRN:")
                    .append(_mrn)
                    .append(" , Facility:")
                    .append(_facility)
                .toString();
    }
    
    
}
