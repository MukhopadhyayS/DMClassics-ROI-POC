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
 * @since  HPF 13.1 [ROI]; Feb 14, 2008
 */
public class MediaType
implements Serializable {

    private long    _id;
    private String  _name;
    private String  _description;
    private long    _createdBy;
    private long    _modifiedBy;
    private Date    _modifiedDate;
    private long    _orgId;
    private boolean _isAssociated;
    private int     _recordVersion;

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long bySeq) { _createdBy = bySeq; }

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }

    @ValidationParams (
         isMandatory = true,
         isMandatoryErrCode = ROIClientErrorCodes.MEDIA_TYPE_NAME_IS_BLANK,
         pattern = ROIConstants.NAME,
         misMatchErrCode = ROIClientErrorCodes.MEDIA_TYPE_NAME_CONTAINS_INVALID_CHAR,
         maxLength = ROIConstants.MEDIA_TYPE_NAME_MAX_LENGTH,
         maxLenErrCode = ROIClientErrorCodes.MEDIA_TYPE_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) { _name = name; }

    public String getDescription() { return _description; }

    @ValidationParams (
         pattern = ROIConstants.ALLOW_ALL,
         misMatchErrCode = ROIClientErrorCodes.MEDIA_TYPE_DESC_CONTAINS_INVALID_CHAR,
         maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
         maxLenErrCode = ROIClientErrorCodes.MEDIA_TYPE_DESC_LENGTH_EXCEEDS_LIMIT)
    public void setDescription(String description) { _description = description; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long bySeq) { _modifiedBy = bySeq; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public long getOrgId() { return _orgId; }
    public void setOrgId(long seq) { _orgId = seq; }

    public boolean getIsAssociated() { return _isAssociated; }
    public void setIsAssociated(boolean associated) { _isAssociated = associated; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    @Override
    public String toString() {

        return "ID " + _id + ", Name " + _name;
    }

    /**
     * This method creates the audit comments for create Media Type
     *
     * @param userFullName Full name of the user
     * @return Audit comments for Media Type creation
     */
    public String toCreateAudit(String userFullName) {

        return "ROI media type " + _name + " created by " + userFullName + ".";
    }

    /**
     * This method creates the audit comments for delete media type
     *
     * @param userFullName Full name of the user
     * @return Audit comments for Media Type deletion
     */
    public String toDeleteAudit(String userFullName) {

        return "Media type " + _name + " has been deleted by " + userFullName + ".";
    }

    /**
     * This method creates the audit comments for update Media Type
     *
     * @param userFullName Full name of the user
     * @param newMT new Media Type details
     * @return Audit comments for update Media Type
     */
    public String toUpdateAudit(String userFullName, MediaType newMT) {

        return new StringBuffer().append("by ")
                                .append(userFullName)
                                .append(" made changes to the media type ")
                                .append(_name)
                                .append(" - ")
                                .append(_description)
                                .append(" to ")
                                .append(newMT.getName())
                                .append(" - ")
                                .append(newMT.getDescription()).toString();
    }

    /**
     * This method copies the Media Type details
     *
     * @param from Media Type details to be copied
     */
    public void copyFrom(MediaType from) {

        _createdBy  = from.getCreatedBy();
        _orgId      = from.getOrgId();
    }
}
