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

package com.mckesson.eig.roi.request.model;



import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Comment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Comment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Comment", propOrder = {
    "requestId",
    "type",
    "description",
    "originator",
    "createdDate"
})
public class Comment {

    public Comment() {
        setType("COMMENT_ADDED");
    }
    
    protected Long requestId;
    protected String type;
    protected String description;
    protected String originator;
    protected Date createdDate;
    
    @XmlTransient
    private String  _name;
    
    @XmlTransient
    private Date    _modifiedDate;
    
    @XmlTransient
    private long    _id;
    
    @XmlTransient
    private int     _createdBy;
    
    @XmlTransient
    private int     _modifiedBy;
    
    @XmlTransient
    private int     _recordVersion;
    
    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }
    
    public int getCreatedBy() { return _createdBy; }
    public void setCreatedBy(int createdBy) { _createdBy = createdBy; }

    public int getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(int modifiedBy) { _modifiedBy = modifiedBy; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date modifiedBy) { _modifiedDate = modifiedBy; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int recordVersion) { _recordVersion = recordVersion; }


    
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long value) {
        this.requestId = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String value) {
        this.originator = value;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date value) {
        this.createdDate = value;
    }
    
}
