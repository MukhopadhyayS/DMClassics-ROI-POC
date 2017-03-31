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


/**
 * @author Vidhya.C.S
 * @date   Jul 28, 2009
 * @since  HPF 13.1 [ROI]; May 16, 2008
 */
public class LetterTemplate
implements Serializable {

    public static enum LetterType { coverletter, prebill, invoice, requestorstatement, other };

    private long    _templateId;
    private String  _name;
    private String  _desc;
    private String  _letterType;
    private boolean _isDefault;
    private boolean _active;
    private long    _createdBy;
    private long    _modifiedBy;
    private Date    _modifiedDate;
    private long    _orgId;
    private int     _recordVersion;
    private boolean _hasNotes;
    private LetterTemplateFile _file;

    public long getTemplateId() { return _templateId; }
    public void setTemplateId(long id) { _templateId = id; }

    public String getName() { return _name; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.LETTER_TEMPLATE_NAME_IS_BLANK,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.LETTER_TEMPLATE_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.LETTER_TEMPLATE_NAME_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.LETTER_TEMPLATE_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) { _name = name; }

    public String getDesc() { return _desc; }

    @ValidationParams (
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.LETTER_TEMPLATE_DESC_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.LETTER_TEMPLATE_DESC_LENGTH_EXCEEDS_LIMIT)
    public void setDesc(String desc) { _desc = desc; }

    public String getLetterType() { return _letterType; }
    public void setLetterType(String type) {
        _letterType = type;
    }

    public LetterTemplateFile getFile() { return _file; }
    public void setFile(LetterTemplateFile file) { _file = file; }

    public boolean getIsDefault() { return _isDefault; }
    public void setIsDefault(boolean default1) { _isDefault = default1; }

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

    public String getUploadFile() {
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
    }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public boolean getHasNotes() { return _hasNotes; }
    public void setHasNotes(boolean hasNotes) { _hasNotes = hasNotes; }

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
                                 .append(_templateId)
                                 .append(" LetterTemplateName ")
                                 .append(_name)
                                 .append(" LetterType ")
                                 .append(_letterType).toString();
    }

    /**
     * This method creates the audit comment for creatLetterTEmplate
     * @return audit comments for Letter Template creation
     */
    public String toCreateAudit() {
        return "ROI Letter template " + _name + " was added.";
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
        .append(_name)
        .append(" - ");

        if ((_file != null) && (_file.getName() != null)) {
            auditMsg.append(_file.getName());
        }
        auditMsg.append(" - ")
                .append(_letterType)
                .append(" - ")
                .append(_desc);
        return auditMsg.toString();
    }
}
