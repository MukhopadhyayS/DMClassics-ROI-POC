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
 * <p>Java class for DocInfoList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocInfoList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="docInfos" type="{urn:eig.mckesson.com}DocInfo" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "DocInfoList", propOrder = {
    "_docInfos",
    "_name"
})
public class DocInfoList
implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="docInfos")
    private List<DocInfo> _docInfos;
    
    @XmlElement(name="name", required = true)
    private String _name;
    
    

    public DocInfoList() { }

    public DocInfoList(List<DocInfo> docInfos, String name) {

        setDocInfos(docInfos);
        setName(name);
    }

    public DocInfoList(List<DocInfo> infos) { setDocInfos(infos); }

    public List<DocInfo> getDocInfos() { return _docInfos; }
    public void setDocInfos(List<DocInfo> infos) { _docInfos = infos; }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

}
