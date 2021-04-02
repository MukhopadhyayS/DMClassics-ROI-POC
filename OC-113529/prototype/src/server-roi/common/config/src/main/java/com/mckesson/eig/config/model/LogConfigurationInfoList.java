/*
 * Copyright 2007-2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.config.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * The LogConfigurationInfoList class is a place holder for a list of
 * LogConfigurationInfo objects.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogConfigurationInfoList", namespace = EIGConstants.TYPE_NS_V1)
public class LogConfigurationInfoList {

    /**
     * Variable used to hold the list of <code>LogConfigurationInfo</code>.
     */
    private List<LogConfigurationInfo> _logConfigurationInfoList;
    
    /**
     * Instantiates an LogConfigurationInfoList
     */
    public LogConfigurationInfoList() {
    }
    
    /**
     * Instantiates an LogConfigurationInfoList with the list of config info details
     * @param logConfigDetailList
     */
    public LogConfigurationInfoList(List<LogConfigurationInfo> list) {
        _logConfigurationInfoList = list;
    }

    /**
     * Returns this configureLogInfoList.
     * 
     * @return list of LogConfigurationInfo.
     */
    public List<LogConfigurationInfo> getLogConfigurationInfoList() {
        return _logConfigurationInfoList;
    }

    /**
     * Sets this configureLogInfos.
     * 
     * @param configureLogInfoList
     *            list of LogConfigurationInfo objects to be set.
     */
    @XmlElement(name = "logConfigurationInfo", type = LogConfigurationInfo.class)
    public void setLogConfigurationInfoList(List<LogConfigurationInfo> configureLogInfoList) {
        this._logConfigurationInfoList = configureLogInfoList;
    }

    /**
     * This method returns a string of logConfigurationInfoList containing list
     * of logConfigurationInfo object.
     * 
     * @return string representation of LogConfigurationInfo objects.
     */
    public String toString() {
        
        StringBuffer strBuff = new StringBuffer();
        LogConfigurationInfo event = null;
        strBuff.append("LogConfigurationInfo[");
        if (_logConfigurationInfoList != null) {
            int size = _logConfigurationInfoList.size();
            for (int i = 0; i < size; i++) {
                event = _logConfigurationInfoList.get(i);
                strBuff.append(event);
            }
        }
        strBuff.append("]");
        return strBuff.toString();
    }
}
