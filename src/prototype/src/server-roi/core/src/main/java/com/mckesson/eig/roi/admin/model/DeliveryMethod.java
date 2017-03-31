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
 * @date   Mar 16, 2009
 * @since  HPF 13.1 [ROI]
 */
public class DeliveryMethod
implements Serializable {

    private long    _id;
    private String  _name;
    private String  _desc;
    private String  _url;
    private long    _createdBy;
    private long    _modifiedBy;
    private Date    _modifiedDt;
    private long    _orgId;
    private boolean _isDefault;
    private int     _recordVersion;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.DELIVERY_METHOD_NAME_IS_BLANK,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.DELIVERY_METHOD_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DELIVERY_METHOD_NAME_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.DELIVERY_METHOD_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) { _name = name; }

    public String getDesc() { return _desc; }

    @ValidationParams (
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.DELIVERY_METHOD_DESC_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.DELIVERY_METHOD_DESC_LENGTH_EXCEEDS_LIMIT)
    public void setDesc(String desc) { _desc = desc; }

    public String getUrl() { return _url; }

    @ValidationParams (
            pattern = ROIConstants.URL,
            misMatchErrCode = ROIClientErrorCodes.DELIVERY_METHOD_URL_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DELIVERY_METHOD_URL_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.DELIVERY_METHOD_URL_LENGTH_EXCEEDS_LIMIT)
    public void setUrl(String url) { _url = url; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDt() { return _modifiedDt; }
    public void setModifiedDt(Date date) {  _modifiedDt = date; }

    public long getOrgId() {  return _orgId; }
    public void setOrgId(long id) { _orgId = id; }

    public boolean getIsDefault() {   return _isDefault; }
    public void setIsDefault(boolean default1) { _isDefault = default1; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    @Override
    public String toString() {

        return "Delivery Method ID = " + _id + " Delivery Method Name = " + _name;
    }

    /**
     * This method creates the audit comments for create DeliveryMethod
     *
     * @param userFullName Full name of the user
     * @return the audit comments for delivery method creation
     */
    public String toCreateAudit(String userFullName) {

        return "The ROI user " + userFullName + " created a new delivery method " + _name;
    }

    /**
     * This method creates the audit comments for delete delivery method
     *
     * @param userFullName Full name of the user
     * @return the audit comments for delivery method deletion
     */
    public String toDeleteAudit(String userFullName) {

        return "The ROI user " + userFullName + " deleted the delivery method " + _name;
    }

    /**
     * This method creates the audit comments for update delivery method
     *
     * @param userFullName Name of the user
     * @param oldDM old delivery method
     * @return audit comments for update delivery method
     */
    public String toUpdateAudit(String userFullName, DeliveryMethod oldDM) {

        return new StringBuffer().append("The ROI user ")
                                 .append(userFullName)
                                 .append(" made changes to the ")
                                 .append(oldDM.getName())
                                 .append(" : ")
                                 .append(oldDM.getName())
                                 .append(" - ")
                                 .append(oldDM.getDesc())
                                 .append(" - ")
                                 .append(oldDM.getUrl())
                                 .append(" - ")
                                 .append(oldDM.getIsDefault())
                                 .append(" to ")
                                 .append(_name)
                                 .append(" - ")
                                 .append(_desc)
                                 .append(" - ")
                                 .append(_url)
                                 .append(" - ")
                                 .append(_isDefault).toString();
    }

    /**
     * This method copies the existing delivery method to the newly updated delivery method
     * model.
     *
     * @param fromDb Delivery Method details to be copied
     */
    public void copyFrom(DeliveryMethod fromDb) {

        setCreatedBy(fromDb.getCreatedBy());
        setOrgId(fromDb.getOrgId());
    }
}
