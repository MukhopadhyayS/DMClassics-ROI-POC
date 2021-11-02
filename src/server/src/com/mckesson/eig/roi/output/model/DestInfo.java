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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DestInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DestInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serviceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="statusInfo" type="{urn:eig.mckesson.com}StatusInfo"/>
 *         &lt;element name="propertyDefs" type="{urn:eig.mckesson.com}PropertyDef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="jobOptionDefs" type="{urn:eig.mckesson.com}PropertyDef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="properties" type="{urn:eig.mckesson.com}propertyMap" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DestInfo", propOrder = {
    "_serviceId",
    "_statusInfo",
    "_propertyDefs",
    "_jobOptionDefs",
    "_id",
    "_name",
    "_type",
    "_description",
    "properties"
})
public class DestInfo {

    @XmlElement(name="serviceId", required = true)
    private String _serviceId;
    
    @XmlElement(name="statusInfo", required = true)
    private StatusInfo _statusInfo;
    
    @XmlElement(name="propertyDefs")
    private PropertyDef[] _propertyDefs;
    
    @XmlElement(name="jobOptionDefs")
    private PropertyDef[] _jobOptionDefs;
    
    @XmlElement(name="id")
    private int _id;
    
    @XmlElement(name="name", required = true)
    private String _name;
    
    @XmlElement(name="type", required = true)
    private String _type;
    
    @XmlElement(name="description", required = true)
    private String _description;
    
    private List<PropertyMap> properties;
    
    
    /*name
    type
    id
    description
    properties*/

    
    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    /*public Map<String, ? extends Object> getProperties() {
        return _properties;
    }

    public void setProperties(Map<String, ? extends Object> properties) {
        _properties = properties;
    }*/
    public List<PropertyMap> getProperties() {
        if (this.properties == null) {
            this.properties = new ArrayList<PropertyMap>();
        }
        return this.properties;
    }
    
    public void setProperties(List<PropertyMap> properties) {
        this.properties = properties;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public String getServiceId() {
        return _serviceId;
    }

    public void setServiceId(String serviceId) {
        _serviceId = serviceId;
    }

    public StatusInfo getStatusInfo() {
        return _statusInfo;
    }
    
    public void setStatusInfo(StatusInfo statusInfo) {
        _statusInfo = statusInfo;
    }
    
    public PropertyDef[] getPropertyDefs() {
        return _propertyDefs;
    }
    
    public void setPropertyDefs(PropertyDef[] propertyDefs) {
        _propertyDefs = propertyDefs;
    }
    
    public PropertyDef[] getJobOptionDefs() {
        return _jobOptionDefs;
    }
    
    public void setJobOptionDefs(PropertyDef[] jobOptionDefs) {
        _jobOptionDefs = jobOptionDefs;
    }
}
