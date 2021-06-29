/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
 * @date   Jul 5, 2011
 * @since  HPF 15.2 [ROI] Jul 5, 2011
 */
public class InvoiceDueDays
implements Serializable {

    private long _id;
    private Date _modifiedDate;
    private int _modifiedBy;
    private int _recordVersion;
    private int _invoiceDueDays;
    private String _description;
    private boolean _isCustomDate;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date modifiedDate) { _modifiedDate = modifiedDate; }

    public int getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(int modifiedBy) { _modifiedBy = modifiedBy;  }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int recordVersion) { _recordVersion = recordVersion; }

    public int getInvoiceDueDays() { return _invoiceDueDays; }

    @ValidationParams (isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.INVOICE_INVALID_DUE_DAYS_VALUE,
            pattern = ROIConstants.INVOICE_DUE,
            misMatchErrCode = ROIClientErrorCodes.INVOICE_INVALID_DUE_DAYS_VALUE,
            maxLength = ROIConstants.INVOICE_DUE_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.INVOICE_INVALID_DUE_DAYS_VALUE)
    public void setInvoiceDueDays(int invoiceDue) {
        this._invoiceDueDays = invoiceDue;
    }

    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }

    @Override
    public String toString() {
        return "ID " + _id 
               + "Invoice Due " + _invoiceDueDays;
    }

    public String toUpdateAudit(InvoiceDueDays originalInvoiceDue) {
        return new StringBuffer().append("ROI Invoice Due Days Modified : Old Due Days ")
                                .append(originalInvoiceDue.getInvoiceDueDays())
                                .append(" to New Due Days ")
                                .append(_invoiceDueDays)
                                .append(".").toString();
    }

    public void setCustomDate(boolean customDate) { _isCustomDate = customDate; }
    public boolean isCustomDate() { return _isCustomDate; }
}
