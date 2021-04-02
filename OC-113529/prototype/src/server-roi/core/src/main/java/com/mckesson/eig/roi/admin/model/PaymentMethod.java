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
 * @since  HPF 13.1 [ROI]; Apr 16, 2008
 */
public class PaymentMethod
implements Serializable {

    private long    _id;
    private String  _name;
    private String  _desc;
    private boolean _isDisplay;
    private long     _createdBy;
    private long    _modifiedBy;
    private Date    _modifiedDt;
    private long    _orgId;
    private int     _recordVersion;
    private boolean _active;


    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.PAYMENT_METHOD_NAME_IS_BLANK,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.PAYMENT_METHOD_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.PAY_METHOD_NAME_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.PAYMENT_METHOD_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) { _name = name; }

    public String getDesc() { return _desc; }

    @ValidationParams (
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.PAYMENT_METHOD_DESC_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.PAYMENT_METHOD_DESC_LENGTH_EXCEEDS_LIMIT)
    public void setDesc(String desc) { _desc = desc; }

    public boolean getIsDisplay() { return _isDisplay; }
    public void setIsDisplay(boolean display) { _isDisplay = display; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDt() { return _modifiedDt; }
    public void setModifiedDt(Date dt) { _modifiedDt = dt; }

    public long getOrgId() { return _orgId; }
    public void setOrgId(long id) { _orgId = id; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public boolean isActive() { return _active; }
    public void setActive(boolean active) { _active = active; }


    @Override
    public String toString() {

        return "ID " + _id + ", Name " + _name;
    }

    /**
     * This method creates the audit comments for create Payment Method
     *
     * @return Audit comments for Payment Method creation
     */
    public String toCreateAudit() {

        return "ROI Payment Method " + _name + " was added.";
    }

    /**
     * This method creates the audit comments for delete Payment Method
     *
     * @return the audit comments for Payment Method deletion
     */
    public String toDeleteAudit() {

        return "ROI Payment Method " + _name + " was deleted.";
    }


    /**
     * This method creates the audit comments for update payment method
     *
     * @param oldPaymentMemthod old payment method
     * @return audit comments for update payment method
     */
    public String toUpdateAudit(PaymentMethod oldPaymentMethod) {

        return new StringBuffer().append("ROI Payment Method was modified: Old Values: Name ")
                                 .append(oldPaymentMethod.getName())
                                 .append(" Display Form Field ")
                                 .append(oldPaymentMethod.getIsDisplay())
                                 .append(" New Values: Name ")
                                 .append(_name)
                                 .append(" Display Form Field ")
                                 .append(_isDisplay).toString();
    }

    /**
     * This method copies the existing payment method to the newly updated payment method
     * model.
     *
     * @param fromDb Payment Method details to be copied
     */
    public void copyFrom(PaymentMethod fromDb) {

        setCreatedBy(fromDb.getCreatedBy());
        setOrgId(fromDb.getOrgId());
    }
}
