/*
 * Copyright 2008-2009 McKesson Corporation and/or one of its subsidiaries. 
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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class holds the detailed information about a single component and its updation history.
 * 
 * @author Sahul Hameed Y
 * @date   Apr 7, 2008
 * @since  Utils; Apr 7, 2008
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "componentInfo")
@XmlType(name = "ComponentInfo")
public class ComponentInfo {
    
    private String _componentID;
    private String _componentDisplayName;
    private ApplicationInfo _applicationInfo;
    private String _configurationInfo;
	private LogConfig _logConfig;

    private List<UpdateHistory> _updatedHistories;
    
    public ComponentInfo() {
    }
    
    public String getComponentID() {
        return _componentID;
    }

    @XmlAttribute(name = "componentID")
    public void setComponentID(String componentid) {
        _componentID = componentid;
    }
    
    public String getComponentDisplayName() {
        return _componentDisplayName;
    }

    @XmlAttribute(name = "componentDisplayName")
    public void setComponentDisplayName(String displayName) {
        _componentDisplayName = displayName;
    }
    
    public String getConfigurationInfo() {
        return _configurationInfo;
    }

    @XmlElement(name = "configuration-info")
    public void setConfigurationInfo(String info) {
        _configurationInfo = info;
    }

    public List<UpdateHistory> getUpdatedHistories() {
        return _updatedHistories;
    }
 
    @XmlElementWrapper(name = "updateInfo") 
    @XmlElement(name = "update", type = UpdateHistory.class)
    public void setUpdatedHistories(List<UpdateHistory> histories) {
        _updatedHistories = histories;
    }

    public ApplicationInfo getApplicationInfo() {
        return _applicationInfo;
    }

    @XmlElement(name = "applicationInfo")
    public void setApplicationInfo(ApplicationInfo info) {
        _applicationInfo = info;
    }

    public LogConfig getLogConfig() {
        return _logConfig;
    }

    @XmlElement(name = "logConfig")
    public void setLogConfig(LogConfig config) {
        _logConfig = config;
    }
}
