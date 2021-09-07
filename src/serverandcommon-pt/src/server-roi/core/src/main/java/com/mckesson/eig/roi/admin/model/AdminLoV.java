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
 * @author OFS
 * @date   Mar 16, 2009
 * @since  HPF 13.1 [ROI]; Sep 12, 2008
 */
public class AdminLoV
implements Serializable {

    private static final long serialVersionUID = 1L;

    public static enum Type { notes, ssnmask; }

    private long    _id;
    private String  _type;
    private int     _status;
    private long    _createdBy;
    private long    _modifiedBy;
    private Date    _modifiedDt;
    private long    _orgId;
    private boolean _active;
    private boolean _tpo;
    private boolean _nonTpo;
    private int     _recordVersion;
    private String  _lovType;
    private String _name;
    private String _description;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getType() { return _type; }
    public void setType(Type type) {

        _type = type.toString();
        _lovType = _type;
    }

    public int getStatus() {

        _status = 1;
        return _status;
    }
    public void setStatus(int status) { _status = status; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDt() { return _modifiedDt; }
    public void setModifiedDt(Date dt) { _modifiedDt = dt; }

    public long getOrgId() { return _orgId; }
    public void setOrgId(long id) { _orgId = id; }

    public boolean getActive() {

        _active = true;
        return _active;
    }
    public void setActive(boolean active) { _active = active; }

    public boolean getTpo() {

        _tpo = false;
        return _tpo;
    }
    public void setTpo(boolean tpo) { _tpo = tpo; }

    public boolean getNonTpo() {

        _nonTpo = false;
        return _nonTpo;
    }
    public void setNonTpo(boolean nontpo) { _nonTpo = nontpo; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public String getLovType() { return _lovType; }
    public void setLovType(String type) { _lovType = type; }

    @Override
    public String toString() {

        return new StringBuffer().append("Name: ")
                                 .append(getName())
                                 .append(" ID : ")
                                 .append(_id)
                                 .toString();
    }
    public String getName() { return _name;  }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.NOTE_NAME_IS_NULL,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.NOTE_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.NOTE_NAME_DEFAULT_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.NOTE_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) {  _name = name; }

    public String getDescription() { return _description; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.NOTE_DISPLAY_TEXT_IS_NULL,
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.NOTE_DISPLAY_TEXT_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.NOTE_DISPLAY_TEXT_LENGTH_EXCEEDS_LIMIT)
    public void setDescription(String description) { _description = description; }

    /**
     * This method create the audit comment for to delete the note
     * @param name name of note deleted
     * @return string
     */
    public String toDeleteAudit(String name) {
        return "Note " + name + " deleted";
    }

}
