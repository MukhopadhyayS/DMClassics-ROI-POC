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
 * <p>Java class for Reason complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Reason">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="displayText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tpo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="nonTpo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "Reason", propOrder = {
    "id",
    "name",
    "displayText",
    "type",
    "status",
    "tpo",
    "nonTpo",
    "recordVersion"
})
public class Reason
implements Serializable {

    public static enum ReasonType { adjustment, request, status; }

    private long    id;
    @XmlElement(required = true)
    private String  name;
    @XmlElement(required = true)
    private String  displayText;
    @XmlElement(required = true)
    private String  type;
    private int     status;
    private boolean tpo;
    private boolean nonTpo;
    private int     recordVersion;
    
    @XmlTransient
    private long    _createdBy;
    @XmlTransient
    private long    _modifiedBy;
    @XmlTransient
    private Date    _modifiedDt;
    @XmlTransient
    private long    _orgId;
    @XmlTransient
    private boolean _active;
    
    

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.REASON_NAME_IS_BLANK,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.REASON_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.REASON_NAME_LENGTH_EXCEEDS)
    public void setName(String name) { this.name = name; }

    public String getDisplayText() { return displayText; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.REASON_DISPLAY_TEXT_IS_BLANK,
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.REASON_DISPLAY_TEXT_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.REASON_DISPLAY_TEXT_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.REASON_DISPLAY_TEXT_LENGTH_EXCEEDS)
    public void setDisplayText(String text) { this.displayText = text; }

    public String getType() { return type; }
    public void setType(String type) {
        this.type = Enum.valueOf(ReasonType.class, type.toLowerCase()).toString();
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDt() { return _modifiedDt; }
    public void setModifiedDt(Date dt) { _modifiedDt = dt; }

    public long getOrgId() { return _orgId; }
    public void setOrgId(long id) { _orgId = id; }

    public boolean getActive() { return _active; }
    public void setActive(boolean active) { _active = active; }

    public boolean getTpo() { return tpo; }
    public void setTpo(boolean tpo) { this.tpo = tpo; }

    public boolean getNonTpo() { return nonTpo; }
    public void setNonTpo(boolean nontpo) { this.nonTpo = nontpo; }

    public int getRecordVersion() { return recordVersion; }
    public void setRecordVersion(int version) { this.recordVersion = version; }
    @Override
    public String toString() {
        return "Reason Id : " + id + ", Name : " + name + ", Type : " + type;
    }

    /**
     * This method creates the audit comments for create Reason
     *
     * @return the audit comments for reason creation
     */
    public String toCreateAudit() {
        return "ROI " + type + " reason " + name + " was added.";
    }

    /**
     * This method creates the audit comments for delete the Reason
     *
     * @return the audit comments for reason deletion
     */
    public String toDeleteAudit() {
        return "ROI " + type + " reason " + name + " was deleted.";
    }

    /**
     * This method creates the audit comments for update reason
     *
     * @param oldReason old reason
     * @return audit comments for update reason
     */
    public String toUpdateAudit(Reason oldReason) {

        if (ReasonType.status.toString().equalsIgnoreCase(oldReason.getType())) {

            return new StringBuffer()
                .append("ROI reason ")
                .append(oldReason.getName())
                .append(" was modified (to the following depending on the modification made): ")
                .append(oldReason.getName())
                .append(" - ")
                .append(oldReason.getDisplayText())
                .append(" - ")
                .append(oldReason.getStatus())
                .append(" to ")
                .append(name)
                .append(" - ")
                .append(displayText)
                .append(" - ")
                .append(status).toString();
        }

        if (ReasonType.request.toString().equalsIgnoreCase(oldReason.getType())) {

            return new StringBuffer()
                .append("ROI request reason ")
                .append(oldReason.getName())
                .append(" was modified: ")
                .append(oldReason.getName())
                .append(" - ")
                .append(oldReason.getDisplayText())
                .append(" - ")
                .append(oldReason.getAttribute())
                .append(" to ")
                .append(name)
                .append(" - ")
                .append(displayText)
                .append(" - ")
                .append(getAttribute()).toString();

        } else {
            return new StringBuffer()
                .append("ROI adjust reason ")
                .append(oldReason.getName())
                .append(" was modified: ")
                .append(oldReason.getName())
                .append(" - ")
                .append(oldReason.getDisplayText())
                .append(" to ")
                .append(name)
                .append(" - ")
                .append(displayText).toString();
        }
    }

    /**
     * This method copies the existing reason to the newly updated reason model.
     *
     * @param fromDb reason details to be copied
     */
    public void copyFrom(Reason fromDb) {

        setCreatedBy(fromDb.getCreatedBy());
        setOrgId(fromDb.getOrgId());
    }

    private String getAttribute() {

        if (tpo && nonTpo) {
            return "both";
        }

        if (tpo) {
            return "tpo";
        }

        return "nonTpo";
    }
}
