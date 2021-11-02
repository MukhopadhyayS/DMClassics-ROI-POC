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
import com.mckesson.eig.roi.supplementary.model.ROIAttachmentCommon;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestSupplementalAttachment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestSupplementalAttachment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="supplementalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="attachmentSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="attachmentCoreSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="billingTierId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="freeformfacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isDeleted" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateOfService" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="attachmentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="volume" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filetype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileext" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="printable" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="submittedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isSelectedForRelease" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isReleased" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="externalSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestSupplementalAttachment", propOrder = {
    "_facility",
    "_mrn",
    "_patientSeq",
    "_supplementalId",
    "_attachmentSeq",
    "_attachmentCoreSeq",
    "_billingTierId",
    "type",
    "encounter",
    "docFacility",
    "freeformfacility",
    "subtitle",
    "isDeleted",
    "pageCount",
    "dateOfService",
    "attachmentDate",
    "uuid",
    "volume",
    "path",
    "filename",
    "filetype",
    "fileext",
    "printable",
    "submittedBy",
    "comment",
    "_isSelectedForRelease",
    "_isReleased",
    "externalSource"
})
public class RequestSupplementalAttachment extends BaseModel{

    private static final long serialVersionUID = 1L;

    @XmlElement(name="attachmentCoreSeq")
    private Long _attachmentCoreSeq;
    
    @XmlElement(name="facility", required = true)
    private String _facility;
    
    @XmlElement(name="mrn", required = true)
    private String _mrn;
    
    @XmlElement(name="patientSeq")
    private Long _patientSeq;
    
    @XmlElement(name="attachmentSeq")
    private Long _attachmentSeq;
    
    @XmlElement(name="supplementalId")
    private Long _supplementalId;
    
    @XmlElement(name="billingTierId")
    private Long _billingTierId;
    
    @XmlElement(name="isSelectedForRelease")
    private boolean  _isSelectedForRelease;
    
    @XmlElement(name="isReleased")
    private boolean  _isReleased;
    
    
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String encounter;
    @XmlElement(required = true)
    protected String docFacility;
    @XmlElement(required = true)
    protected String freeformfacility;
    @XmlElement(required = true)
    protected String subtitle;
    @XmlElement(required = true)
    protected String isDeleted;
    @XmlElement(required = true)
    protected String pageCount;
    @XmlElement(required = true, nillable = true)
    protected Date dateOfService;
    @XmlElement(required = true, nillable = true)
    protected Date attachmentDate;
    @XmlElement(required = true)
    protected String uuid;
    @XmlElement(required = true)
    protected String volume;
    @XmlElement(required = true)
    protected String path;
    @XmlElement(required = true)
    protected String filename;
    @XmlElement(required = true)
    protected String filetype;
    @XmlElement(required = true)
    protected String fileext;
    @XmlElement(required = true)
    protected String printable;
    @XmlElement(required = true)
    protected String submittedBy;
    @XmlElement(required = true)
    protected String comment;
    @XmlElement(required = true)
    protected String externalSource;
    
    
    
    public String getExternalSource() {
        return externalSource;
    }
    public void setExternalSource(String externalSource) {
        this.externalSource = externalSource;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public String getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
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
    public Date getAttachmentDate() {
        return attachmentDate;
    }
    public void setAttachmentDate(Date attachmentDate) {
        this.attachmentDate = attachmentDate;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getVolume() {
        return volume;
    }
    public void setVolume(String volume) {
        this.volume = volume;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getFiletype() {
        return filetype;
    }
    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
    public String getFileext() {
        return fileext;
    }
    public void setFileext(String fileext) {
        this.fileext = fileext;
    }
    public String getPrintable() {
        return printable;
    }
    public void setPrintable(String printable) {
        this.printable = printable;
    }
    public String getSubmittedBy() {
        return submittedBy;
    }
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Long getAttachmentCoreSeq() { return _attachmentCoreSeq; }
    public void setAttachmentCoreSeq(Long attachmentCoreSeq) {
        _attachmentCoreSeq = attachmentCoreSeq;
    }
    
    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }
   
    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }
   
    public Long getPatientSeq() { return _patientSeq; }
    public void setPatientSeq(Long patientSeq) { _patientSeq = patientSeq; }
    
    public Long getAttachmentSeq() { return _attachmentSeq; }
    public void setAttachmentSeq(Long attachmentSeq) { _attachmentSeq = attachmentSeq; }

    public Long getBillingTierId() { return _billingTierId; }
    public void setBillingTierId(Long billingTierId) { _billingTierId = billingTierId; }
    
    public Long getSupplementalId() { return _supplementalId; }
    public void setSupplementalId(Long supplementalId) { _supplementalId = supplementalId; }
    
    public void setSelectedForRelease(boolean isSelectedForRelease) {
        _isSelectedForRelease = isSelectedForRelease; }
    public boolean isSelectedForRelease() { return _isSelectedForRelease; }

    public void setReleased(boolean isReleased) { _isReleased = isReleased; }
    public boolean isReleased() { return _isReleased; }
    
    public String toString() {
        return new StringBuffer()
                        .append("PatientSeq:")
                        .append(_patientSeq)
                        .append(", AttachmentSeq:")
                        .append(_attachmentSeq)
                        .append(", UUID:")
                        .append(getUuid())
                        .append(", Attachment Path:")
                        .append(getVolume() + "\\")
                        .append(getPath() + getFilename())
                        .append("." + getFileext())
                        .append(", Mrn:")
                        .append(_mrn)
                        .append(", BillingTierId:")
                        .append(_billingTierId)
                        .append(", Facility:")
                        .append(_facility)
                        .toString();
    }
    
    
}
