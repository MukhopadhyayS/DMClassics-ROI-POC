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
 * <p>Java class for OutputRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutputRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestParts" type="{urn:eig.mckesson.com}RequestPart" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="destId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="destName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="properties" type="{urn:eig.mckesson.com}MapModel" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="labels" type="{urn:eig.mckesson.com}MapModel" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="submitInfo" type="{urn:eig.mckesson.com}SubmitInfo"/>
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
@XmlType(name = "OutputRequest", propOrder = {
    "_requestParts",
    "_destId",
    "_destName",
    "_destType",
    "_properties",
    "_labels",
    "_requestId",
    "_submitInfo",
    "_outputTransforms"
})
public class OutputRequest {

    @XmlElement(name="requestParts")
    private List<RequestPart> _requestParts;

    @XmlElement(name="destId")
    private int _destId;
    
    @XmlElement(name="destName", required = true)
    private String _destName;

    @XmlElement(name="destType", required = true)
    private String _destType;

    @XmlElement(name="properties")
    private List<MapModel> _properties;

    @XmlElement(name="labels")
    private List<MapModel> _labels;

    @XmlElement(name="requestId", required = true)
    private String _requestId;
    
    @XmlElement(name="submitInfo", required = true)
    private SubmitInfo _submitInfo;
    
    @XmlElement(name="outputTransforms")
    private List<OutputTransform> _outputTransforms;
    
    public List<RequestPart> getRequestParts() {
        return _requestParts;
    }
    
    public void setRequestParts(List<RequestPart> requestParts) {
        _requestParts = requestParts;
    }
    
    public int getDestId() {
        return _destId;
    }
    
    public void setDestId(int destId) {
        _destId = destId;
    }
    
    public String getDestName() {
        return _destName;
    }
    
    public void setDestName(String destName) {
        _destName = destName;
    }
    
    public String getDestType() {
        return _destType;
    }
    
    public void setDestType(String destType) {
        _destType = destType;
    }
    
    public List<MapModel> getProperties() {
        return _properties;
    }
    
    public void setProperties(List<MapModel> properties) {
        _properties = properties;
    }
    
    public List<MapModel> getLabels() {
        return _labels;
    }
    
    public void setLabels(List<MapModel> labels) {
        _labels = labels;
    }
    
    public String getRequestId() {
        return _requestId;
    }
    
    public void setRequestId(String requestId) {
        _requestId = requestId;
    }
    
    public SubmitInfo getSubmitInfo() {
        return _submitInfo;
    }
    
    public void setSubmitInfo(SubmitInfo submitInfo) {
        _submitInfo = submitInfo;
    }
    
    public List<OutputTransform> getOutputTransforms() {
        return _outputTransforms;
    }
    
    public void setOutputTransforms(List<OutputTransform> outputTransforms) {
        _outputTransforms = outputTransforms;
    }
    
}
