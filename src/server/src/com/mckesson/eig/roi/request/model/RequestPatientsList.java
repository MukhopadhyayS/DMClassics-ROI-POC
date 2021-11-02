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
 * <p>Java class for RequestPatientsList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestPatientsList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestPatients" type="{urn:eig.mckesson.com}RequestPatient" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestPatientsList", propOrder = {
    "_requestId",
    "_requestPatients"
})
public class RequestPatientsList
implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @XmlElement(name="requestId")
    private long _requestId;

    @XmlElement(name="requestPatients")
    private List<RequestPatient> _requestPatients;
    
    
    public RequestPatientsList() { }
    
    public RequestPatientsList(List<RequestPatient> requestPatients) {
        setRequestPatients(requestPatients);
    }
    
    public List<RequestPatient> getRequestPatients() { return _requestPatients; }
    public void setRequestPatients(List<RequestPatient> requestPatients) { 
        _requestPatients = requestPatients;
    }
    
    public void setRequestId(long requestId) { this._requestId = requestId; }
    public long getRequestId() { return _requestId; }

}
