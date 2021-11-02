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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SSNMask complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SSNMask">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applySSNMask" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SSNMask", propOrder = {
    "applySSNMask",
    "recordVersion"
})
public class SSNMask
extends AdminLoV
implements Serializable {

    public static final String SSN_MASK_TRUE = "1";
    public static final String SSN_MASK_FALSE = "0";

    private boolean applySSNMask;
    private int recordVersion;
    
    public boolean getApplySSNMask() {  return applySSNMask; }
    public void setApplySSNMask(boolean mask) { this.applySSNMask = mask; }

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

    public int getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(int value) {
        this.recordVersion = value;
    }
    
    @Override
    public String toString() {
        return new StringBuffer().append("applySSNMask: " + applySSNMask).toString();
    }
    public String toUpdateAudit() {
        return new StringBuffer().append("Apply SSN Mask was set to ")
                                 .append(applySSNMask)
                                 .toString();
    }
}
