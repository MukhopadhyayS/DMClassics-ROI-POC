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

package com.mckesson.eig.roi.requestor.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.utility.util.StringUtilities;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestorSearchCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorSearchCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allRequestors" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="requestorTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="freeFormFacility" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="epn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="maxCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="activeRequestors" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="allStatus" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="patientRequestor" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorSearchCriteria", propOrder = {
    "_allRequestors",
    "_type",
    "_requestorTypeName",
    "_lastName",
    "_firstName",
    "_ssn",
    "_mrn",
    "_facility",
    "_freeFormFacility",
    "_epn",
    "_dob",
    "_maxCount",
    "_activeRequestors",
    "_allStatus",
    "_patientRequestor"
})
public class RequestorSearchCriteria
implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name="allRequestors")
    private boolean _allRequestors;
    
    @XmlElement(name="type")
    private long _type;
    
    @XmlElement(name="requestorTypeName")
    private String _requestorTypeName;
    
    @XmlElement(name="firstName")
    private String _firstName;
    
    @XmlElement(name="lastName")
    private String _lastName;
    
    @XmlElement(name="epn")
    private String _epn;
    
    @XmlElement(name="ssn")
    private String _ssn;
    
    @XmlElement(name="dob")
    private Date _dob;
    
    @XmlElement(name="facility")
    private String _facility;
    
    @XmlElement(name="freeFormFacility")
    private boolean _freeFormFacility;
    
    @XmlElement(name="mrn")
    private String _mrn;
    
    @XmlTransient
    private String _dateOfBirth;
    
    @XmlElement(name="maxCount")
    private int _maxCount;
    
    @XmlTransient
    private Date _recentRequestorDate;
    
    @XmlElement(name="activeRequestors")
    private boolean _activeRequestors;
    
    @XmlElement(name="allStatus")
    private boolean _allStatus;
    
    @XmlElement(name="patientRequestor")
    private boolean _patientRequestor;
    
    @XmlTransient
    private long _patientRequestorTypeId;

    public boolean isAllRequestors() { return _allRequestors; }
    public void setAllRequestors(boolean requestors) { _allRequestors = requestors; }

    public long getType() { return _type; }
    public void setType(long type) { _type = type; }

    public String getLastName() { return _lastName; }
    public void setLastName(String name) { _lastName = name; }

    public String getEpn() { return _epn; }
    public void setEpn(String epn) { _epn = epn; }

    public String getSsn() { return _ssn; }
    public void setSsn(String ssn) { _ssn = ssn; }

    public Date getDob() { return _dob; }
    public void setDob(Date dob) { _dob = dob; }

    public int getMaxCount() { return _maxCount; }
    public void setMaxCount(int count) { _maxCount = count; }

    public Date getRecentRequestorDate() { return _recentRequestorDate; }
    public void setRecentRequestorDate(Date requestorDate) { _recentRequestorDate = requestorDate; }

    public String getDateOfBirth() { return _dateOfBirth; }
    public void setDateOfBirth(String ofBirth) { _dateOfBirth = ofBirth; }

    public boolean isActiveRequestors() { return _activeRequestors; }
    public void setActiveRequestors(boolean active) { _activeRequestors = active; }

    @Override
    public String toString() {

        return "TYPE: " + _type  + ", LastName: " + _lastName + ", FirstName: "
                + _firstName + ", EpnValue: " + _epn + ", SsnValue: " + _ssn + ", DOB: " + _dob;
    }

    /**
     * This method is to set the date for searching recent requestors
     * @param ts timeStamp
     * @param days number of days
     */
    public void setRecentRequestorDate(Timestamp ts, int days) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(ts);
        cal.add(Calendar.DAY_OF_MONTH, days);
        setRecentRequestorDate(cal.getTime());
    }

    /**
     * This method is to convert date into string of the given format
     * @param formatString date format
     */
    public void fomatDateOfBirth(String formatString) {
        setDateOfBirth(new SimpleDateFormat(formatString).format(_dob));
    }

    public void normalize(Timestamp ts) {

        if (!_allRequestors) {
            setRecentRequestorDate(ts, ROIConstants.RECENT_REQUESTOR_NO_OF_DAYS);
        }
        if (getDob() != null) {
            fomatDateOfBirth(ROIConstants.ROI_DATE_FORMAT);
        }
    }

    public boolean isAllStatus() { return _allStatus; }
    public void setAllStatus(boolean status) { _allStatus = status; }

    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }

    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }

    public boolean isFreeFormFacility() { return _freeFormFacility; }
    public void setFreeFormFacility(boolean isFreeFormFacility) {
        _freeFormFacility = isFreeFormFacility;
    }

    public String getFirstName() { return _firstName; }
    public void setFirstName(String name) { _firstName = name; }

    public String getRequestorTypeName() { return _requestorTypeName; }
    public void setRequestorTypeName(String requestorTypeName) {
        _requestorTypeName = requestorTypeName;
    }

    public boolean isPatientRequestor() { return _patientRequestor; }
    public void setPatientRequestor(boolean isPatientRequestor) {
        _patientRequestor = isPatientRequestor;
    }
    
    public long getPatientRequestorTypeId() { return _patientRequestorTypeId; }
    public void setPatientRequestorTypeId(long patientRequestorTypeId) {
        _patientRequestorTypeId = patientRequestorTypeId;
    }
    
    //This method will return user entered search criterias in the format of string.
    public String constructSearchAuditComment() {

        SimpleDateFormat sdf   = new SimpleDateFormat(ROIConstants.ROI_DATE_FORMAT);
        StringBuffer comment = new StringBuffer();

        comment.append(constructAuditString("Filter By",
                                                (isAllRequestors()) ? "All Requestors"
                                                                    : "Recent Requestors"));

        comment.append(constructAuditString("RequestorType",
                                              (0 == getType()) ? "All"
                                                               : getRequestorTypeName()));

        comment.append(constructAuditString("LastName", getLastName()));
        comment.append(constructAuditString("FirstName", getFirstName()));
        if (isActiveRequestors()) {
            comment.append(constructAuditString("RquestorStatus", "Active"));
        } else if (isAllStatus()) {
            comment.append(constructAuditString("RquestorStatus", "All"));
        } else {
            comment.append(constructAuditString("RquestorStatus", "InActive"));
        }


        comment.append(constructAuditString("EPN", getEpn()));
        if (null != getDob()) {
            comment.append(constructAuditString("DateOfBirth", sdf.format(getDob())));
        }


        comment.append(constructAuditString("SSN", getSsn()));
        comment.append(constructAuditString("Facility", (null == getFacility()) ? "All"
                                                                                : getFacility()));

        comment.append(constructAuditString("MRN", getMrn()));
        comment.replace(comment.length() - 2, comment.length() - 1, ROIConstants.SEPERATOR);

        return comment.toString();
    }

    /**
     * @param value
     * @param searchCriterias
     */
    private String constructAuditString(String label, String value) {

        if (StringUtilities.isEmpty(value)) {
            return "";
        }

        String comment = new StringBuffer()
                                .append(label)
                                .append(": ")
                                .append(value)
                                .append(ROIConstants.CSV_DELIMITER)
                                .append(" ")
                                .toString();

        return comment;
    }


}
