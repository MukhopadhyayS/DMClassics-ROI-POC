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

package com.mckesson.eig.roi.billing.model;
        
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ReleaseHistoryPatient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReleaseHistoryPatient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="epn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientLocked" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounterLocked" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReleaseHistoryPatient", propOrder = {
    "patientSeq",
    "name",
    "gender",
    "dob",
    "ssn",
    "facility",
    "mrn",
    "epn",
    "patientLocked",
    "encounterLocked",
    "vip"
})
@SuppressWarnings("serial")
public class ReleaseHistoryPatient
implements Serializable {
    
    private Long patientSeq;
    
    @XmlElement(required = true)
    private String name;
    
    @XmlElement(required = true)
    private String gender;
    
    @XmlElement(required = true)
    private Date dob;
    
    @XmlElement(required = true)
    private String ssn;
    
    @XmlElement(required = true)
    private String facility;
    
    @XmlElement(required = true)
    private String mrn;
    
    @XmlElement(required = true)
    private String epn;
    
    @XmlElement(required = true)
    private boolean  patientLocked;
    
    @XmlElement(required = true)
    private boolean  encounterLocked;
    
    @XmlElement(required = true)
    private String  vip;
 
        
    
    public Long getPatientSeq() {
        return patientSeq;
    }
    
    public void setPatientSeq(Long patientSeq) {
        this.patientSeq = patientSeq;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Date getDob() {
        return dob;
    }
    
    public void setDob(Date dob) {
        this.dob = dob;
    }
    
    public String getSsn() {
        return ssn;
    }
    
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    
    public String getFacility() {
        return facility;
    }
    
    public void setFacility(String facility) {
        this.facility = facility;
    }
    
    public String getMrn() {
        return mrn;
    }
    
    public void setMrn(String mrn) {
        this.mrn = mrn;
    }
    
    public String getEpn() {
        return epn;
    }
    
    public void setEpn(String epn) {
        this.epn = epn;
    }

    public boolean isPatientLocked() {
        return patientLocked;
    }

    public void setPatientLocked(boolean patientLocked) {
        this.patientLocked = patientLocked;
    }

    public boolean isEncounterLocked() {
        return encounterLocked;
    }

    public void setEncounterLocked(boolean encounterLocked) {
        this.encounterLocked = encounterLocked;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

  
    
}
