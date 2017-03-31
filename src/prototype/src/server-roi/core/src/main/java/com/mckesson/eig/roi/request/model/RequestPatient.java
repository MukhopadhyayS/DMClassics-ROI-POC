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

/**
 * @author OFS
 * @date   JUL 03, 2012
 */
public class RequestPatient 
extends BaseModel {

    private static final long serialVersionUID = 1L;
    
    // Patient Details
    private long    _patientSeq;
    private long    _requestId;
    private String  _mrn;
    private String  _name;
    private String  _facility;
    private String  _freeformFacility;
    private Date  _dob;
    private String  _gender;
    private String  _epn;
    private String  _ssn;
    private boolean  _patientLocked;
    private boolean  _encounterLocked;
    private boolean  _isVip;
    private List<RequestEncounter> _roiEncounters;
    private List<RequestDocument> _globalDocuments;
    private String _firstName;
    private String _lastName;
    private String _address1;
    private String _address2;
    private String _address3;
    private String _city;
    private String _state;
    private String _zip;
    private String _homePhone;
    private String _workPhone;

    //for supplemental patients
    private long    _supplementalId;
    private boolean _isHpf;

    // for letter template Use
    private long    _invoiceId;
    
    // for non hpf documents and attachments
    private List<RequestSupplementalAttachment> _attachments;
    private List<RequestSupplementalDocument> _nonHpfDocuments;
    
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
