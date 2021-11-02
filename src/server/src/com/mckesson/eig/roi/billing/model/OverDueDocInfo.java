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

package com.mckesson.eig.roi.billing.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OverDueDocInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OverDueDocInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorGroupingKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OverDueDocInfo", propOrder = {
    "_id",
    "_name",
    "_type",
    "_requestorGroupingKey"
})
public class OverDueDocInfo {

    @XmlElement(name="id")
    private long _id;
    
    @XmlElement(name="name", required = true)
    private String _name;
    
    @XmlElement(name="type", required = true)
    private String _type;
    
    @XmlElement(name="requestorGroupingKey", required = true)
    private String _requestorGroupingKey;
    
    
    public OverDueDocInfo() { }

    public OverDueDocInfo(long id, String name, String type, 
            String requestorGroupingKey) {

        setId(id);
        setName(name);
        setType(type);
        setRequestorGroupingKey(requestorGroupingKey);
    }
    
    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }
    

    public void setRequestorGroupingKey(String requestorGroupingKey) {
        _requestorGroupingKey = requestorGroupingKey;
    }

    public String getRequestorGroupingKey() {
        return _requestorGroupingKey;
    }

}
