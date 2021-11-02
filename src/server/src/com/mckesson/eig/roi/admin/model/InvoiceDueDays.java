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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="invoiceDueDays" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="customDate" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDue", propOrder = {
    "id",
    "recordVersion",
    "invoiceDueDays",
    "isCustomDate"
})
public class InvoiceDueDays
implements Serializable {

    private long id;
    private int recordVersion;
    private int invoiceDueDays;
    @XmlElement(name="customDate")
    private boolean isCustomDate;
    @XmlTransient
    private String description;
    @XmlTransient
    private Date modifiedDate;
    @XmlTransient
    private int modifiedBy;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Date getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(Date modifiedDate) { this.modifiedDate = modifiedDate; }

    public int getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(int modifiedBy) { this.modifiedBy = modifiedBy;  }

    public int getRecordVersion() { return recordVersion; }
    public void setRecordVersion(int recordVersion) { this.recordVersion = recordVersion; }

    public int getInvoiceDueDays() { return invoiceDueDays; }

    @ValidationParams (isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.INVOICE_INVALID_DUE_DAYS_VALUE,
            pattern = ROIConstants.INVOICE_DUE,
            misMatchErrCode = ROIClientErrorCodes.INVOICE_INVALID_DUE_DAYS_VALUE,
            maxLength = ROIConstants.INVOICE_DUE_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.INVOICE_INVALID_DUE_DAYS_VALUE)
    public void setInvoiceDueDays(int invoiceDue) {
        this.invoiceDueDays = invoiceDue;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "ID " + id 
               + "Invoice Due " + invoiceDueDays;
    }

    public String toUpdateAudit(InvoiceDueDays originalInvoiceDue) {
        return new StringBuffer().append("ROI Invoice Due Days Modified : Old Due Days ")
                                .append(originalInvoiceDue.getInvoiceDueDays())
                                .append(" to New Due Days ")
                                .append(invoiceDueDays)
                                .append(".").toString();
    }

    public void setCustomDate(boolean customDate) { this.isCustomDate = customDate; }
    public boolean isCustomDate() { return isCustomDate; }
}
