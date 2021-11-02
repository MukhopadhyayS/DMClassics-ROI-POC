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
 * <p>Java class for RequestEncounter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestEncounter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="encounterSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="roiDocuments" type="{urn:eig.mckesson.com}RequestDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isVip" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isLocked" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasDeficiency" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="admitdate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dischargeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestEncounter", propOrder = {
    "_encounterSeq",
    "_patientSeq",
    "_roiDocuments",
    "_name",
    "_mrn",
    "_facility",
    "_patientType",
    "_patientService",
    "_isVip",
    "_isLocked",
    "_hasDeficiency",
    "_admitdate",
    "_dischargeDate"
})
public class RequestEncounter extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //Encounter Details
    
    @XmlElement(name="encounterSeq")
    private long _encounterSeq;
    
    @XmlElement(name="patientSeq")
    private long _patientSeq;
    
    @XmlElement(name="roiDocuments")
    private List<RequestDocument> _roiDocuments;
    
    @XmlElement(name="name", required = true)
    private String  _name;
    
    @XmlElement(name="facility", required = true)
    private String  _facility;
    
    @XmlElement(name="patientType", required = true)
    private String  _patientType;
    
    @XmlElement(name="patientService", required = true)
    private String  _patientService;
    
    @XmlElement(name="isVip")
    private boolean  _isVip;
    
    @XmlElement(name="isLocked")
    private boolean  _isLocked;
    
    @XmlElement(name="hasDeficiency")
    private boolean  _hasDeficiency;
    
    @XmlElement(name="admitdate", required = true, nillable = true)
    private Date  _admitdate;
    
    @XmlElement(name="dischargeDate", required = true, nillable = true)
    private Date  _dischargeDate;
    
    // for patient insertion
    @XmlElement(name="mrn", required = true)
    private String _mrn;
    
    @XmlTransient
    private String _patientFacility;
    
    
    public long getEncounterSeq() { return _encounterSeq; }
    public void setEncounterSeq(long encounterSeq) { _encounterSeq = encounterSeq; }


    public List<RequestDocument> getRoiDocuments() { return _roiDocuments; }
    public void setRoiDocuments(List<RequestDocument> roiDocuments) {
        _roiDocuments = roiDocuments;
    }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }

    public String getPatientType() { return _patientType; }
    public void setPatientType(String patientType) { _patientType = patientType; }

    public String getPatientService() { return _patientService; }
    public void setPatientService(String patientService) {
        _patientService = patientService; }

    public Date getAdmitdate() { return _admitdate; }
    public void setAdmitdate(Date admitdate) { _admitdate = admitdate; }

    public Date getDischargeDate() { return _dischargeDate; }
    public void setDischargeDate(Date dischargeDate) { _dischargeDate = dischargeDate; }
    
    public boolean isVip() { return _isVip; }
    public void setVip(boolean isVip) { _isVip = isVip; }

    public boolean isLocked() { return _isLocked; }
    public void setLocked(boolean isLocked) { _isLocked = isLocked;}
  
    public boolean isHasDeficiency() { return _hasDeficiency; }
    public void setHasDeficiency(boolean hasDeficiency) { _hasDeficiency = hasDeficiency; }
    
    public void setPatientSeq(long patientSeq) { _patientSeq = patientSeq; }
    public long getPatientSeq() { return _patientSeq; }
    
    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }
    
    @Override
    public String toString() {
        
        return new StringBuffer()
                    .append("Encounter Seq:")
                    .append(_encounterSeq)
                    .append(" , RequestPatient Seq:")
                    .append(_patientSeq)
                    .append(" , Encounter:")
                    .append(_name)
                    .append(" , Patient Type:")
                    .append(_patientType)
                    .append(" , MRN:")
                    .append(" , Facility:")
                    .append(_facility)
                .toString();
    }

    public void setPatientFacility(String patientFacility) { _patientFacility = patientFacility; }
    public String getPatientFacility() { return _patientFacility; }

}
