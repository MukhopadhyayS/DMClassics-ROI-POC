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

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OverDueDocInfoList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OverDueDocInfoList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="overDueDocInfos" type="{urn:eig.mckesson.com}OverDueDocInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OverDueDocInfoList", propOrder = {
    "_overDueDocInfos",
    "_name"
})
@SuppressWarnings("serial")
public class OverDueDocInfoList
implements Serializable {

    
    @XmlElement(name="overDueDocInfos")
    private List<OverDueDocInfo> _overDueDocInfos;
    
    @XmlElement(name="name", required = true)
    private String _name;

    public OverDueDocInfoList() { }

    public OverDueDocInfoList(List<OverDueDocInfo> docInfos, String name) {

        setOverDueDocInfos(docInfos);
        setName(name);
    }

    public OverDueDocInfoList(List<OverDueDocInfo> infos) { setOverDueDocInfos(infos); }

      public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public void setOverDueDocInfos(List<OverDueDocInfo> overDueDocInfos) {
        _overDueDocInfos = overDueDocInfos;
    }

    public List<OverDueDocInfo> getOverDueDocInfos() {
        return _overDueDocInfos;
    }

}
