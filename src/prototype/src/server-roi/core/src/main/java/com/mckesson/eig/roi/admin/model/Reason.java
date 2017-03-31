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
 * @author manikandans
 * @date   Mar 16, 2009
 * @since  HPF 13.1 [ROI]; Apr 28, 2008
 */
public class Reason
implements Serializable {

    public static enum ReasonType { adjustment, request, status; }

    private long    _id;
    private String  _name;
    private String  _displayText;
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

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.REASON_NAME_IS_BLANK,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.REASON_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.REASON_NAME_LENGTH_EXCEEDS)
    public void setName(String name) { _name = name; }

    public String getDisplayText() { return _displayText; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.REASON_DISPLAY_TEXT_IS_BLANK,
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.REASON_DISPLAY_TEXT_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.REASON_DISPLAY_TEXT_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.REASON_DISPLAY_TEXT_LENGTH_EXCEEDS)
    public void setDisplayText(String text) { _displayText = text; }

    public String getType() { return _type; }
    public void setType(String type) {
        _type = Enum.valueOf(ReasonType.class, type.toLowerCase()).toString();
    }

    public int getStatus() { return _status; }
    public void setStatus(int status) { _status = status; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDt() { return _modifiedDt; }
    public void setModifiedDt(Date dt) { _modifiedDt = dt; }

    public long getOrgId() { return _orgId; }
    public void setOrgId(long id) { _orgId = id; }

    public boolean getActive() { return _active; }
    public void setActive(boolean active) { _active = active; }

    public boolean getTpo() { return _tpo; }
    public void setTpo(boolean tpo) { _tpo = tpo; }

    public boolean getNonTpo() { return _nonTpo; }
    public void setNonTpo(boolean nontpo) { _nonTpo = nontpo; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    @Override
    public String toString() {
        return "Reason Id : " + _id + ", Name : " + _name + ", Type : " + _type;
    }

    /**
     * This method creates the audit comments for create Reason
     *
     * @return the audit comments for reason creation
     */
    public String toCreateAudit() {
        return "ROI " + _type + " reason " + _name + " was added.";
    }

    /**
     * This method creates the audit comments for delete the Reason
     *
     * @return the audit comments for reason deletion
     */
    public String toDeleteAudit() {
        return "ROI " + _type + " reason " + _name + " was deleted.";
    }

    /**
     * This method creates the audit comments for update reason
     *
     * @param oldReason old reason
     * @return audit comments for update reason
     */
    public String toUpdateAudit(Reason oldReason) {

        if (ReasonType.status.toString().equalsIgnoreCase(oldReason.getType())) {

            return new StringBuffer()
                .append("ROI reason ")
                .append(oldReason.getName())
                .append(" was modified (to the following depending on the modification made): ")
                .append(oldReason.getName())
                .append(" - ")
                .append(oldReason.getDisplayText())
                .append(" - ")
                .append(oldReason.getStatus())
                .append(" to ")
                .append(_name)
                .append(" - ")
                .append(_displayText)
                .append(" - ")
                .append(_status).toString();
        }

        if (ReasonType.request.toString().equalsIgnoreCase(oldReason.getType())) {

            return new StringBuffer()
                .append("ROI request reason ")
                .append(oldReason.getName())
                .append(" was modified: ")
                .append(oldReason.getName())
                .append(" - ")
                .append(oldReason.getDisplayText())
                .append(" - ")
                .append(oldReason.getAttribute())
                .append(" to ")
                .append(_name)
                .append(" - ")
                .append(_displayText)
                .append(" - ")
                .append(getAttribute()).toString();

        } else {
            return new StringBuffer()
                .append("ROI adjust reason ")
                .append(oldReason.getName())
                .append(" was modified: ")
                .append(oldReason.getName())
                .append(" - ")
                .append(oldReason.getDisplayText())
                .append(" to ")
                .append(_name)
                .append(" - ")
                .append(_displayText).toString();
        }
    }

    /**
     * This method copies the existing reason to the newly updated reason model.
     *
     * @param fromDb reason details to be copied
     */
    public void copyFrom(Reason fromDb) {

        setCreatedBy(fromDb.getCreatedBy());
        setOrgId(fromDb.getOrgId());
    }

    private String getAttribute() {

        if (_tpo && _nonTpo) {
            return "both";
        }

        if (_tpo) {
            return "tpo";
        }

        return "nonTpo";
    }
}
