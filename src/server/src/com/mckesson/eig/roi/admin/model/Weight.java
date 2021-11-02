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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Weight complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Weight">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="unitPerMeasure" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Weight", propOrder = {
    "id",
    "recordVersion",
    "unitPerMeasure"
})
public class Weight
implements Serializable {

    private long id;
    private int recordVersion;
    private long unitPerMeasure;
    @XmlTransient
    private long _modifiedBy;
    @XmlTransient
    private Date _modifiedDate;
    

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public int getRecordVersion() { return recordVersion; }
    public void setRecordVersion(int version) { this.recordVersion = version; }

    public long getUnitPerMeasure() { return unitPerMeasure; }

    @ValidationParams (
                       isMandatory = true,
                       isMandatoryErrCode = ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE,
                       pattern = ROIConstants.PAGE,
                       misMatchErrCode = ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE,
                       maxLength = ROIConstants.WEIGHT_MAX_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE)
    public void setUnitPerMeasure(long upm) { this.unitPerMeasure = upm; }

    @Override
    public String toString() {
        return "ID " + id;
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
                                 .append(unitPerMeasure)
                                 .append(".").toString();
    }
}
