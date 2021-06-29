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

/**
 * @author OFS
 * @date   JUL 03, 2012
 */
public class RequestEncounter extends BaseModel
implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //Encounter Details
    private long _encounterSeq;
    private long _patientSeq;
    private List<RequestDocument> _roiDocuments;
    
    private String  _name;
    private String  _facility;
    private String  _patientType;
    private String  _patientService;
    private boolean  _isVip;
    
    private boolean  _isLocked;
    private boolean  _hasDeficiency;
    private Date  _admitdate;
    private Date  _dischargeDate;
    
    // for patient insertion
    private String _mrn;
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
