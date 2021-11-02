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

package com.mckesson.eig.roi.request.model;

import java.io.Serializable;
import java.util.List;

import com.mckesson.eig.audit.model.AuditEvent;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuditAndEventList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuditAndEventList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="events" type="{urn:eig.mckesson.com}RequestEvent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="auditEvent" type="{urn:eig.mckesson.com}AuditEvent" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuditAndEventList", propOrder = {
    "_requestEvent",
    "_auditEvent"
})
public class AuditAndEventList 
implements Serializable {
    
    private static final long serialVersionUID = -3742766980629480216L;
    
    @XmlElement(name="events")
    private List<RequestEvent> _requestEvent;
    
    @XmlElement(name="auditEvent")
    private List<AuditEvent> _auditEvent;
    
    
    public List<RequestEvent> getRequestEvent() { return _requestEvent; }
    public void setRequestEvent(List<RequestEvent> requestEvent) { _requestEvent = requestEvent; }
    
    public List<AuditEvent> getAuditEvent() { return _auditEvent; }
    public void setAuditEvent(List<AuditEvent> auditEvent) { _auditEvent = auditEvent; }
}
