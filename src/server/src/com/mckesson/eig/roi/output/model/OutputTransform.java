/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.roi.output.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OutputTransform complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutputTransform">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="properties" type="{urn:eig.mckesson.com}MapModel" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transformName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transformType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputTransform", propOrder = {
    "_properties",
    "_transformName",
    "_transformType"
})
public class OutputTransform {
    
    @XmlElement(name="properties")
    private List<MapModel> _properties;
    
    @XmlElement(name="transformName", required = true)
    private String _transformName;
    
    @XmlElement(name="transformType", required = true)
    private String _transformType;
    
    
    public List<MapModel> getProperties() {
        return _properties;
    }
    
    public void setProperties(List<MapModel> properties) {
        _properties = properties;
    }
    
    public String getTransformName() {
        return _transformName;
    }
    
    public void setTransformName(String transformName) {
        _transformName = transformName;
    }
    
    public String getTransformType() {
        return _transformType;
    }
    
    public void setTransformType(String transformType) {
        _transformType = transformType;
    }
}
