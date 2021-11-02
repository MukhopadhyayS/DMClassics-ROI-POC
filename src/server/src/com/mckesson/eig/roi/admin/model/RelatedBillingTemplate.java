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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelatedBillingTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelatedBillingTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="relatedBillingTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="billingTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="isDefault" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "RelatedBillingTemplate", propOrder = {
    "_id",
    "_billingTemplateId",
    "_isDefault",
    "_recordVersion"
})
public class RelatedBillingTemplate
implements Serializable {

    @XmlElement(name="relatedBillingTemplateId")
    private long    _id;
    @XmlElement(name="billingTemplateId")
    private long    _billingTemplateId;
    @XmlElement(name="isDefault")
    private boolean _isDefault;
    @XmlElement(name="recordVersion")
    private int     _recordVersion;
    
    @XmlTransient
    private long    _createdBy;
    @XmlTransient
    private long    _modifiedBy;
    @XmlTransient
    private Date    _modifiedDate;

    public long getBillingTemplateId() { return _billingTemplateId; }
    public void setBillingTemplateId(long templateId) { _billingTemplateId = templateId; }

    public boolean isIsDefault() { return _isDefault; }
    public void setIsDefault(boolean default1) { _isDefault = default1; }

    public long getCreatedBy() {  return _createdBy; }
    public void setCreatedBy(long by) {  _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate;  }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) {  _recordVersion = version; }

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

}
