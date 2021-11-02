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

package com.mckesson.eig.roi.requestor.model;


import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorSearchResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorSearchResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="searchResults" type="{urn:eig.mckesson.com}Requestor" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
@XmlType(name = "RequestorSearchResult", propOrder = {
    "_searchResults",
    "_requestorType",
    "_maxCountExceeded"
})
public class RequestorSearchResult
implements Serializable {
    
    @XmlElement(name="searchResults")
    private List<Requestor> _searchResults;
    
    @XmlElement(name="maxCountExceeded")
    private boolean _maxCountExceeded;
    
    @XmlElement(name="requestorType")
    private long _requestorType;

    public List<Requestor> getSearchResults() { return _searchResults; }
    public void setSearchResults(List<Requestor> results) { _searchResults = results; }

    public boolean isMaxCountExceeded() { return _maxCountExceeded; }
    public void setMaxCountExceeded(boolean countExceeded) { _maxCountExceeded = countExceeded; }

    public long getRequestorType() { return _requestorType; }
    public void setRequestorType(long type) { _requestorType = type; }
}
