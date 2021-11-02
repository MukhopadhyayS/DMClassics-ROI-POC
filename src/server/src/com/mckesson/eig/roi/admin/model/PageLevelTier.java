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
* <p>Java class for PageLevelTier complex type.
*
* <p>The following schema fragment specifies the expected content contained within this class.
*
* <pre>
* &lt;complexType name="PageLevelTier">
* &lt;complexContent>
* &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
* &lt;sequence>
* &lt;element name="pageLevelTierId" type="{http://www.w3.org/2001/XMLSchema}long"/>
* &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}int"/>
* &lt;element name="pageCharge" type="{http://www.w3.org/2001/XMLSchema}float"/>
* &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
* &lt;/sequence>
* &lt;/restriction>
* &lt;/complexContent>
* &lt;/complexType>
* </pre>
*
*
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageLevelTier", propOrder = {
"_id",
"_page",
"_pageCharge",
"_recordVersion"
})
public class PageLevelTier
implements Serializable {

    @XmlElement(name="pageLevelTierId")
    private long    _id;
    
    @XmlElement(name="recordVersion")
    private int     _recordVersion;
    
    @XmlElement(name="page")
    private int    _page;
    
    @XmlElement(name="pageCharge")
    private float   _pageCharge;
    
    @XmlTransient
    private long    _createdBy;
    
    @XmlTransient
    private long    _modifiedBy;
    
    @XmlTransient
    private Date    _modifiedDate;


    public int getPage() { return _page; }
    public void setPage(int page) { _page = page; }

    public float getPageCharge() { return _pageCharge; }
    public void setPageCharge(float charge) { _pageCharge = charge; }

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    @Override
    public String toString() {
        return "PageLevelTierId = " + _id + " , "
               + "Page = " + _page + " , "
               + "Page Charge = " +  _pageCharge;
       }
}
