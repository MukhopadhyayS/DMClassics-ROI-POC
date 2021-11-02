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
 * <p>Java class for RelatedBillingTier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelatedBillingTier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="relatedBillingTierId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="billingTierId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="isHPF" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isHECM" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isCEVA" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="isSupplemental" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelatedBillingTier", propOrder = {
    "_id",
    "_billingTierId",
    "_isHPF",
    "_isHECM",
    "_isCEVA",
    "_recordVersion",
    "_isSupplemental"
})
public class RelatedBillingTier
implements Serializable {

    @XmlElement(name="relatedBillingTierId")
    private long    _id;
    @XmlElement(name="billingTierId")
    private long    _billingTierId;
    @XmlElement(name="isHPF")
    private boolean _isHPF;
    @XmlElement(name="isHECM")
    private boolean _isHECM;
    @XmlElement(name="isCEVA")
    private boolean _isCEVA;
    @XmlElement(name="recordVersion")
    private int     _recordVersion;
    @XmlElement(name="isSupplemental")
    private boolean _isSupplemental;
    
    @XmlTransient
    private long    _createdBy;
    @XmlTransient
    private long    _modifiedBy;
    @XmlTransient
    private Date    _modifiedDate;
    
    

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public long getBillingTierId() { return _billingTierId; }
    public void setBillingTierId(long tierId) { _billingTierId = tierId; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public boolean isIsHPF() { return _isHPF; }
    public void setIsHPF(boolean ishpf) { _isHPF = ishpf; }

    public boolean isIsHECM() { return _isHECM; }
    public void setIsHECM(boolean ishecm) { _isHECM = ishecm; }

    public boolean isIsCEVA() { return _isCEVA; }
    public void setIsCEVA(boolean isceva) { _isCEVA = isceva; }

    public boolean isIsSupplemental() { return _isSupplemental; }
    public void setIsSupplemental(boolean supplemental) { _isSupplemental = supplemental; }

}
