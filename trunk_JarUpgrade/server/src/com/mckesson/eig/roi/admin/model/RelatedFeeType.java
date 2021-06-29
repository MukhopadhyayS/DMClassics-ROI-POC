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

/**
 *
 * @author ganeshramr
 * @date Apr 28, 2008
 * @since HPF 13.1 [ROI]; Apr 4, 2008
 */
public class RelatedFeeType
implements Serializable {

    private long _id;
    private long _feeTypeId;
    private long _billingTemplateId;
    private long _createdBy;
    private Date _modifiedDate;
    private long _modifiedBy;
    private int _recordVersion;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public long getFeeTypeId() { return _feeTypeId; }
    public void setFeeTypeId(long typeId) { _feeTypeId = typeId; }

    public long getBillingTemplateId() { return _billingTemplateId; }
    public void setBillingTemplateId(long templateId) { _billingTemplateId = templateId; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RelatedFeeType)) {
            return false;
        }

        RelatedFeeType rft = (RelatedFeeType) obj;

        return (getFeeTypeId() == rft.getFeeTypeId());
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getFeeTypeId()).hashCode();
    }
}
