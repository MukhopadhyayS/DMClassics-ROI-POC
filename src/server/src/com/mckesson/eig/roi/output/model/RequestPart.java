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
 * <p>Java class for RequestPart complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestPart">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contentSourceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contentSourceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="properties" type="{urn:eig.mckesson.com}MapModel" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="outputTransforms" type="{urn:eig.mckesson.com}OutputTransform" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestPart", propOrder = {
    "_contentId",
    "_contentSourceName",
    "_contentSourceType",
    "_properties",
    "_outputTransforms"
})
public class RequestPart {

    @XmlElement(name="contentId", required = true)
    private String _contentId;
    
    @XmlElement(name="contentSourceName", required = true)
    private String _contentSourceName;
    
    @XmlElement(name="contentSourceType", required = true)
    private String _contentSourceType;
    
    @XmlElement(name="properties")
    private List<MapModel> _properties;
    
    @XmlElement(name="outputTransforms")
    private List<OutputTransform> _outputTransforms;

    public String getContentId() {
        return _contentId;
    }

    public void setContentId(String contentId) {
        _contentId = contentId;
    }

    public String getContentSourceName() {
        return _contentSourceName;
    }

    public void setContentSourceName(String contentSourceName) {
        _contentSourceName = contentSourceName;
    }

    public String getContentSourceType() {
        return _contentSourceType;
    }

    public void setContentSourceType(String contentSourceType) {
        _contentSourceType = contentSourceType;
    }

    public List<MapModel> getProperties() {
        return _properties;
    }

    public void setProperties(List<MapModel> properties) {
        _properties = properties;
    }

    public List<OutputTransform> getOutputTransforms() {
        return _outputTransforms;
    }

    public void setOutputTransforms(List<OutputTransform> outputTransforms) {
        _outputTransforms = outputTransforms;
    }
    
    
}
