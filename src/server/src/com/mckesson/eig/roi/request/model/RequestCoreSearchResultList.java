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

package com.mckesson.eig.roi.request.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestCoreSearchResultList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreSearchResultList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestCoreSearchResult" type="{urn:eig.mckesson.com}RequestCoreSearchResult" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="maxCountExceeded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreSearchResultList", propOrder = {
    "_requestCoreSearchResult",
    "_maxCountExceeded"
})
public class RequestCoreSearchResultList 
implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="requestCoreSearchResult")
    private List<RequestCoreSearchResult> _requestCoreSearchResult;
    
    @XmlElement(name="maxCountExceeded")
    private boolean _maxCountExceeded; 
    
    
    
    public RequestCoreSearchResultList() { }
    public RequestCoreSearchResultList(List<RequestCoreSearchResult> searchResult) {
        setRequestCoreSearchResult(searchResult);
    }
    
    public boolean isMaxCountExceeded() { return _maxCountExceeded; }
    public void setMaxCountExceeded(boolean maxCountExceeded) { 
        _maxCountExceeded = maxCountExceeded;
    }
    
    public List<RequestCoreSearchResult> getRequestCoreSearchResult() {
        return _requestCoreSearchResult;
    }
    public void setRequestCoreSearchResult(List<RequestCoreSearchResult> requestCoreSearchResult) {
        _requestCoreSearchResult = requestCoreSearchResult;
    }

}
