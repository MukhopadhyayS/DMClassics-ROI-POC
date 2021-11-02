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
package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorLetterHistoryList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorLetterHistoryList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorLetterHistory" type="{urn:eig.mckesson.com}RequestorLetterHistory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorLetterHistoryList", propOrder = {
    "_requestorLetterHistoryList"
})
public class RequestorLetterHistoryList implements Serializable {
    
    private static final long serialVersionUID = -1528321726194773744L;
    
    @XmlElement(name="requestorLetterHistory")
    private List<RequestorLetterHistory> _requestorLetterHistoryList;

    public List<RequestorLetterHistory> getRequestorLetterHistoryList() {
        return _requestorLetterHistoryList;
    }

    public void setRequestorLetterHistoryList(
            List<RequestorLetterHistory> requestorLetterHistoryList) {
        _requestorLetterHistoryList = requestorLetterHistoryList;
    }
    
}
