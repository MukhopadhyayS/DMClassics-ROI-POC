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

package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ValidationParams;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LetterTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LetterTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="letterTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isDefault" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="letterType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uploadFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hasNotes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LetterTemplate", propOrder = {
    "templateId",
    "name",
    "desc",
    "isDefault",
    "letterType",
    "uploadFile",
    "docId",
    "recordVersion",
    "hasNotes"
})
public class LetterTemplate
implements Serializable {

    public static enum LetterType { coverletter, prebill, invoice, requestorstatement, other };
   
    @XmlElement(name="letterTemplateId")
    private long templateId;
    @XmlElement(required = true)
    private String name;
    @XmlElement(name="description", required = true)
    private String desc;
    private boolean isDefault;
    @XmlElement(required = true)
    private String letterType;
    @XmlElement(required = true)
    private String uploadFile;
    private long docId;
    private int recordVersion;
    private boolean hasNotes;

    @XmlTransient
    private boolean _active;
    @XmlTransient
    private long    _createdBy;
    @XmlTransient
    private long    _modifiedBy;
    @XmlTransient
    private Date    _modifiedDate;
    @XmlTransient
    private long    _orgId;
    @XmlTransient
    private LetterTemplateFile _file;

    public long getTemplateId() { return templateId; }
    public void setTemplateId(long id) { this.templateId = id; }

    public String getName() { return name; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.LETTER_TEMPLATE_NAME_IS_BLANK,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.LETTER_TEMPLATE_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.LETTER_TEMPLATE_NAME_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.LETTER_TEMPLATE_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) { this.name = name; }

    public String getDesc() { return desc; }

    @ValidationParams (
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.LETTER_TEMPLATE_DESC_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.LETTER_TEMPLATE_DESC_LENGTH_EXCEEDS_LIMIT)
    public void setDesc(String desc) { this.desc = desc; }

    public String getLetterType() { return letterType; }
    public void setLetterType(String type) {
        this.letterType = type;
    }

    public LetterTemplateFile getFile() { return _file; }
    public void setFile(LetterTemplateFile file) { _file = file; }

    public boolean getIsDefault() { return isDefault; }
    public void setIsDefault(boolean default1) { this.isDefault = default1; }

    public boolean isActive() { return _active; }
    public void setActive(boolean active) { _active = active; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public long getOrgId() { return _orgId; }
    public void setOrgId(long id) { _orgId = id; }

   /* public String getUploadFile() {
		return (_file != null) ? _file.getName() : "";
    }

    public void setUploadFile(String file) {

        if (_file == null) {
        	_file = new LetterTemplateFile();
        }
 		_file.setName(file);
    }

    public long getDocId() {
       return (_file != null) ? _file.getId() : 0;
    }

    public void setDocId(long id) {

        if (_file == null) {
        	_file = new LetterTemplateFile();
        }
        _file.setId(id);
    }*/

    public String getUploadFile() {
        return uploadFile;
    }
    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }
    public long getDocId() {
        return docId;
    }
    public void setDocId(long docId) {
        this.docId = docId;
    }
    
    
    public int getRecordVersion() { return recordVersion; }
    public void setRecordVersion(int version) { this.recordVersion = version; }

    public boolean getHasNotes() { return hasNotes; }
    public void setHasNotes(boolean hasNotes) { this.hasNotes = hasNotes; }

    /**
     * This method transfers the values of old letterTemplate to be persisted into
     * updated letterTemplate
     * @param old letterTemplate details to be copied
     */
    public void copyFrom(LetterTemplate old) {

        if (old != null) {
            _active    = old.isActive();
            _createdBy = old.getCreatedBy();
            _orgId     = old.getOrgId();
        }
    }

    @Override

    public String toString() {

        return new StringBuffer().append(" LetterTemplateId = ")
                                 .append(templateId)
                                 .append(" LetterTemplateName ")
                                 .append(name)
                                 .append(" LetterType ")
                                 .append(letterType).toString();
    }

    /**
     * This method creates the audit comment for creatLetterTEmplate
     * @return audit comments for Letter Template creation
     */
    public String toCreateAudit() {
        return "ROI Letter template " + name + " was added.";
    }

    /**
     * This method creates audit comments foe deleteLetterTemplate
     * @param name name of LetterTemplate was deleted
     * @return audit comments for Letter Template deletion
     */
    public String toDeleteAudit(String name) {
        return  "ROI Letter template " + name + " was deleted.";
    }

    /**
     * This method create audit comment for LetteTemplate update
     * @param old oldLetterTemplate Details
     * @return audit comments for Letter Template update
     */
    public String toUpdateAudit(LetterTemplate old) {

        StringBuffer auditMsg = new StringBuffer()
        .append("ROI letter type ")
        .append(old.getName())
        .append(" name was modified (to the following depending on the modification made) : ")
        .append(old.getName())
        .append(" - ")
        .append(old.getUploadFile())
        .append(" - ")
        .append(old.getLetterType())
        .append(" - ")
        .append(old.getDesc())
        .append(" to ")
        .append(name)
        .append(" - ");

        if ((_file != null) && (_file.getName() != null)) {
            auditMsg.append(_file.getName());
        }
        auditMsg.append(" - ")
                .append(letterType)
                .append(" - ")
                .append(desc);
        return auditMsg.toString();
    }
}
