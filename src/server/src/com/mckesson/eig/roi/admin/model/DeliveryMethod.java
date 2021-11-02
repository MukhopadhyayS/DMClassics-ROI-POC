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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeliveryMethod complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeliveryMethod">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="default" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "DeliveryMethod", propOrder = {
    "id",
    "name",
    "desc",
    "url",
    "isDefault",
    "recordVersion"
})
public class DeliveryMethod
implements Serializable {

    protected long id;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(name="description")
    protected String desc;
    @XmlElement(required = true)
    protected String url;
    @XmlElement(name = "default")
    protected boolean isDefault;
    protected int recordVersion;
    @XmlTransient
    private long createdBy;
    @XmlTransient
    private long modifiedBy;
    @XmlTransient
    private Date modifiedDt;
    @XmlTransient
    private long orgId;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.DELIVERY_METHOD_NAME_IS_BLANK,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.DELIVERY_METHOD_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DELIVERY_METHOD_NAME_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.DELIVERY_METHOD_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) { this.name = name; }

    public String getDesc() { return desc; }

    @ValidationParams (
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.DELIVERY_METHOD_DESC_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.DELIVERY_METHOD_DESC_LENGTH_EXCEEDS_LIMIT)
    public void setDesc(String desc) { this.desc = desc; }

    public String getUrl() { return url; }

    @ValidationParams (
            pattern = ROIConstants.URL,
            misMatchErrCode = ROIClientErrorCodes.DELIVERY_METHOD_URL_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DELIVERY_METHOD_URL_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.DELIVERY_METHOD_URL_LENGTH_EXCEEDS_LIMIT)
    public void setUrl(String url) { this.url = url; }

    public long getCreatedBy() { return createdBy; }
    public void setCreatedBy(long by) { this.createdBy = by; }

    public long getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(long by) { this.modifiedBy = by; }

    public Date getModifiedDt() { return modifiedDt; }
    public void setModifiedDt(Date date) {  this.modifiedDt = date; }

    public long getOrgId() {  return orgId; }
    public void setOrgId(long id) { this.orgId = id; }

    public boolean getIsDefault() {   return isDefault; }
    public void setIsDefault(boolean default1) { this.isDefault = default1; }

    public int getRecordVersion() { return recordVersion; }
    public void setRecordVersion(int version) { this.recordVersion = version; }

    @Override
    public String toString() {

        return "Delivery Method ID = " + id + " Delivery Method Name = " + name;
    }

    /**
     * This method creates the audit comments for create DeliveryMethod
     *
     * @param userFullName Full name of the user
     * @return the audit comments for delivery method creation
     */
    public String toCreateAudit(String userFullName) {

        return "The ROI user " + userFullName + " created a new delivery method " + name;
    }

    /**
     * This method creates the audit comments for delete delivery method
     *
     * @param userFullName Full name of the user
     * @return the audit comments for delivery method deletion
     */
    public String toDeleteAudit(String userFullName) {

        return "The ROI user " + userFullName + " deleted the delivery method " + name;
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
                                 .append(name)
                                 .append(" - ")
                                 .append(desc)
                                 .append(" - ")
                                 .append(url)
                                 .append(" - ")
                                 .append(isDefault).toString();
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
