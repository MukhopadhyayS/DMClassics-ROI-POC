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

@SuppressWarnings("serial")
public class ReleaseHistoryPatient
implements Serializable {
    
    private Long patientSeq;
    private String name;
    private String gender;
    private Date dob;
    private String ssn;
    private String facility;
    private String mrn;
    private String epn;
    
    private boolean  patientLocked;
    private boolean  encounterLocked;
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
