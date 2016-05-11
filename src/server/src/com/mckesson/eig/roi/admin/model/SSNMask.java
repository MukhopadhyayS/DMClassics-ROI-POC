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
 * @author OFS
 * @date   Sep 30, 2008
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public class SSNMask
extends AdminLoV
implements Serializable {

    public static final String SSN_MASK_TRUE = "1";
    public static final String SSN_MASK_FALSE = "0";

    private boolean _applySSNMask;

    public boolean getApplySSNMask() {  return _applySSNMask; }
    public void setApplySSNMask(boolean mask) { _applySSNMask = mask; }

    public void setSSNMaskDetails(SSNMask oldSSNMask, long userId, Date modifiedDt) {

        if (getApplySSNMask()) {
            setDescription(SSNMask.SSN_MASK_TRUE);
        } else {
            setDescription(SSNMask.SSN_MASK_FALSE);
        }

        setId(oldSSNMask.getId());
        setName(oldSSNMask.getName());
        setCreatedBy(oldSSNMask.getCreatedBy());
        setActive(oldSSNMask.getActive());
        setStatus(oldSSNMask.getStatus());
        setOrgId(oldSSNMask.getOrgId());
        setModifiedBy(userId);
        setModifiedDt(modifiedDt);
    }

    @Override
    public String toString() {
        return new StringBuffer().append("applySSNMask: " + _applySSNMask).toString();
    }
    public String toUpdateAudit() {
        return new StringBuffer().append("Apply SSN Mask was set to ")
                                 .append(_applySSNMask)
                                 .toString();
    }
}
