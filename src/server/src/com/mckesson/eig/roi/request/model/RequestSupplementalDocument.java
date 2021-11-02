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

import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.supplementary.model.ROIDocumentCommon;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestSupplementalDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestSupplementalDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="documentCoreSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="supplementalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="billingTierId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="freeformfacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateOfService" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isSelectedForRelease" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isReleased" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestSupplementalDocument", propOrder = {
    "_documentSeq",
    "_documentCoreSeq",
    "_supplementalId",
    "_patientSeq",
    "_billingTierId",
    "_mrn",
    "_facility",
    "docName",
    "encounter",
    "docFacility",
    "freeformfacility",
    "department",
    "subtitle",
    "pageCount",
    "dateOfService",
    "comment",
    "_isSelectedForRelease",
    "_isReleased"
})
public class RequestSupplementalDocument extends BaseModel{

    private static final long serialVersionUID = 1L;

    @XmlElement(name="documentSeq")
    private Long _documentSeq;
    
    @XmlElement(name="documentCoreSeq")
    private Long _documentCoreSeq;
    
 // Suppplemental Patients sequence
    @XmlElement(name="supplementalId")
    private Long _supplementalId;
    
    // HPF patients Sequence
    @XmlElement(name="patientSeq")
    private Long _patientSeq;
    
    @XmlElement(name="billingTierId")
    private Long _billingTierId;
    
    @XmlElement(name="mrn", required = true)
    private String _mrn;
    
    @XmlElement(name="facility", required = true)
    private String _facility;
    
    @XmlElement(name="isSelectedForRelease")
    private boolean  _isSelectedForRelease;
    
    @XmlElement(name="isReleased")
    private boolean  _isReleased;
    
    
    @XmlElement(required = true)
    protected String docName;
    @XmlElement(required = true)
    protected String encounter;
    @XmlElement(required = true)
    protected String docFacility;
    @XmlElement(required = true)
    protected String freeformfacility;
    @XmlElement(required = true)
    protected String department;
    @XmlElement(required = true)
    protected String subtitle;
    @XmlElement(required = true)
    protected String pageCount;
    @XmlElement(required = true, nillable = true)
    protected Date dateOfService;
    @XmlElement(required = true)
    protected String comment;
    
    
    public String getDocName() {
        return docName;
    }
    public void setDocName(String docName) {
        this.docName = docName;
    }
    public String getEncounter() {
        return encounter;
    }
    public void setEncounter(String encounter) {
        this.encounter = encounter;
    }
    public String getDocFacility() {
        return docFacility;
    }
    public void setDocFacility(String docFacility) {
        this.docFacility = docFacility;
    }
    public String getFreeformfacility() {
        return freeformfacility;
    }
    public void setFreeformfacility(String freeformfacility) {
        this.freeformfacility = freeformfacility;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public String getPageCount() {
        return pageCount;
    }
    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }
    public Date getDateOfService() {
        return dateOfService;
    }
    public void setDateOfService(Date dateOfService) {
        this.dateOfService = dateOfService;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Long getDocumentCoreSeq() { return _documentCoreSeq; }
    public void setDocumentCoreSeq(Long documentCoreSeq) { _documentCoreSeq = documentCoreSeq; }
    
    public Long getDocumentSeq() { return _documentSeq; }
    public void setDocumentSeq(Long documentSeq) { _documentSeq = documentSeq; }
    
    public Long getPatientSeq() { return _patientSeq; }
    public void setPatientSeq(Long patientSeq) { _patientSeq = patientSeq; }
   
    public Long getSupplementalId() { return _supplementalId; }
    public void setSupplementalId(Long supplementalId) { _supplementalId = supplementalId; }
   
    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }
   
    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }
    
    public Long getBillingTierId() { return _billingTierId; }
    public void setBillingTierId(Long billingTierId) { _billingTierId = billingTierId; }
    
    public void setSelectedForRelease(boolean isSelectedForRelease) {
        _isSelectedForRelease = isSelectedForRelease; }
    public boolean isSelectedForRelease() { return _isSelectedForRelease; }

    public void setReleased(boolean isReleased) { _isReleased = isReleased; }
    public boolean isReleased() { return _isReleased; }
    
    public String toString() {
        return new StringBuffer()
                        .append("PatientSeq:")
                        .append(_patientSeq)
                        .append(", DocumentSeq:")
                        .append(_documentSeq)
                        .append(", DocName:")
                        .append(getDocName())
                        .append(", DocEncounter:")
                        .append(getEncounter())
                        .append(", BillingTierId:")
                        .append(_billingTierId)
                        .append(", Mrn:")
                        .append(_mrn)
                        .append(", Facility:")
                        .append(_facility)
                        .toString();
    }
    
}
