/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.components.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author sahuly
 * @date   Jan 8, 2009
 * @since  HECM 1.0; Jan 8, 2009
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "applicationInfo")
@XmlType(name = "applicationInfo")
public class ApplicationInfo {
    
    private String _componentName;
    private String _productName;
    private String _productType;
    private String _version;
    private String _applicationPath;

    public String getComponentName() {
        return _componentName;
    }

    @XmlElement(name = "componentName")
    public void setComponentName(String name) {
        _componentName = name;
    }

    public String getProductName() {
        return _productName;
    }

    @XmlElement(name = "productName")
    public void setProductName(String name) {
        _productName = name;
    }

    public String getProductType() {
        return _productType;
    }

    @XmlElement(name = "productType")
    public void setProductType(String type) {
        _productType = type;
    }

    public String getVersion() {
        return _version;
    }

    @XmlElement(name = "version")
    public void setVersion(String version) {
        this._version = version;
    }

    public String getApplicationPath() {
        return _applicationPath;
    }

    @XmlElement(name = "path")
    public void setApplicationPath(String path) {
        _applicationPath = path;
    }
}
