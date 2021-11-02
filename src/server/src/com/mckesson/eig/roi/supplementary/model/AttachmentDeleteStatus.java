/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.supplementary.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachmentDeleteStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachmentDeleteStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachmentId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="deleteLog" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deleted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachmentDeleteStatus", propOrder = {
    "_attachmentId",
    "_deleteLog",
    "_isDeleted"
})
public class AttachmentDeleteStatus 
implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="attachmentId")
    private long _attachmentId;
    
    @XmlElement(name="deleteLog", required = true)
    private String _deleteLog;
    
    @XmlElement(name="deleted")
    private boolean _isDeleted;
    
    /**
     * getter and setter for attachmentId
     */
    public long getAttachmentId() { return _attachmentId; }
    public void setAttachmentId(long attachmentId) { _attachmentId = attachmentId; }

    /**
     * getter and setter for deleteLog
     */
    public String getDeleteLog() { return _deleteLog; }
    public void setDeleteLog(String deleteLog) { _deleteLog = deleteLog; }
    
    /**
     * getter and setter for deleted
     */
    public void setDeleted(boolean isDeleted) { _isDeleted = isDeleted; }
    public boolean isDeleted() { return _isDeleted; }

}
