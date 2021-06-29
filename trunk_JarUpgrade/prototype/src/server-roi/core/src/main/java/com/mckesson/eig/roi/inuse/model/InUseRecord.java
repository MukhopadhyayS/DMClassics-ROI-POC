/**
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.roi.inuse.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author OFS
 * @date   Nov 11, 2008
 * @since  ROI HPF 13.1
 */
public class InUseRecord implements Serializable {

    private long _recordSequence;
    private long _createdBy;
    private Date _createdDate;
    private long _modifiedBy;
    private Date _modifiedDate;
    private int _recordVersion;
    private String _objectType;
    private String _objectID;
    private String _userID;
    private String _applicationID;
    private int _expiresMinutes;

    public long getRecordSequence() { return _recordSequence; }
    public void setRecordSequence(long sequence) { _recordSequence = sequence; }

    public Date getCreatedDate() { return _createdDate; }
    public void setCreatedDate(Date createdDate) { _createdDate = createdDate; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public String getObjectType() { return _objectType; }
    public void setObjectType(String objectType) { _objectType = objectType; }

    public String getObjectID() { return _objectID; }
    public void setObjectID(String objectID) { _objectID = objectID; }

    public String getUserID() { return _userID; }
    public void setUserID(String userID) { _userID = userID; }

    public String getApplicationID() { return _applicationID; }
    public void setApplicationID(String applicationID) { _applicationID = applicationID; }

    public int getExpiresMinutes() { return _expiresMinutes; }
    public void setExpiresMinutes(int expiresMinutes) { _expiresMinutes = expiresMinutes; }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(_recordSequence);
        sb.append(",");
        sb.append(_objectType);
        sb.append(",");
        sb.append(_expiresMinutes);
        sb.append(",");
        sb.append(_applicationID);
        sb.append(",");
        sb.append(_objectID);
        sb.append(",");
        sb.append(_userID);
        sb.append(",");
        sb.append(_createdBy);
        sb.append(",");
        sb.append(_createdDate);
        sb.append(",");
        sb.append(_modifiedBy);
        sb.append(",");
        sb.append(_modifiedDate);
        sb.append(",");
        sb.append(_recordVersion);
        return sb.toString();
    }

}
