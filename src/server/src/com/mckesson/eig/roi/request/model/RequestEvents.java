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

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestEvents complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestEvents">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="events" type="{urn:eig.mckesson.com}RequestEvent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="eventsCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestEvents", propOrder = {
    "_events",
    "_count"
})
public class RequestEvents
implements Serializable {

    @XmlElement(name="events")
    private List<RequestEvent> _events;
    
    @XmlElement(name="eventsCount")
    private int _count;
    

    public RequestEvents() { }
    public RequestEvents(List<RequestEvent> events) {

        setEvents(events);
        _count = (events.size() == 0) ?  0 : events.size();
    }
    public int getCount() { return _count; }
    public void setCount(int count) { _count = count; }

    public List <RequestEvent> getEvents() { return _events; }
    public void setEvents(List <RequestEvent> events) { _events = events; }

}
