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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attLocation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attLocation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachmentID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="attachmentLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attLocation", propOrder = {
    "attachmentID",
    "attachmentLocation"
})
public class AttachmentLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private long attachmentID;
    @XmlElement(required = true)
    private String attachmentLocation;

    public AttachmentLocation() {
        super();
    }
    
    public AttachmentLocation(long attachmentID, String attachmentLoc) {
        this.attachmentID = attachmentID;
        this.attachmentLocation = attachmentLoc;
    }
    
    public AttachmentLocation(String attachmentLoc) {
          this.attachmentLocation = attachmentLoc;
    }

   
    
    public long getAttachmentID() { return attachmentID; }
    public void setAttachmentID(long attachmentID) { this.attachmentID = attachmentID; }
    
    public String getAttachmentLocation() { return attachmentLocation; }
    public void setAttachmentLocation(String location) { this.attachmentLocation = location; }    
}
