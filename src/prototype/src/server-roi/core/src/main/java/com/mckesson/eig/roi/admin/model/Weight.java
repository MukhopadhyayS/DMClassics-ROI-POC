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
 * @author ranjithr
 * @date Mar 16, 2009
 * @since HPF 13.1 [ROI]; Apr 20, 2008
 */
public class Weight
implements Serializable {

    private long _id;
    private long _modifiedBy;
    private Date _modifiedDate;
    private int _recordVersion;
    private long _unitPerMeasure;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public long getUnitPerMeasure() { return _unitPerMeasure; }

    @ValidationParams (
                       isMandatory = true,
                       isMandatoryErrCode = ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE,
                       pattern = ROIConstants.PAGE,
                       misMatchErrCode = ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE,
                       maxLength = ROIConstants.WEIGHT_MAX_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE)
    public void setUnitPerMeasure(long upm) { _unitPerMeasure = upm; }

    @Override
    public String toString() {
        return "ID " + _id;
    }

    /**
     * This method creates the audit comments for update weight
     *
     * @param oldWt old weight
     * @return audit comments for update weight method
     */
    public String toUpdateAudit(Weight oldWt) {

        return new StringBuffer().append("ROI page weight modified from ")
                                 .append(oldWt.getUnitPerMeasure())
                                 .append(" to ")
                                 .append(_unitPerMeasure)
                                 .append(".").toString();
    }
}
