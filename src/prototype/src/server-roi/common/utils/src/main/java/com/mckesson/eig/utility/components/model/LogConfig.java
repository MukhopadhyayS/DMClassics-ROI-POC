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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author sahuly
 * @date   Jan 8, 2009
 * @since  HECM 1.0; Jan 8, 2009
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "logConfig")
@XmlType(name = "logConfig")
public class LogConfig {
    
    private String _logFilePath;
    private String[] _appenders;
    private String[] _categories;

    public String getLogFilePath() {
        return _logFilePath;
    }

    @XmlElement(name = "file")
    public void setLogFilePath(String filePath) {
        _logFilePath = filePath;
    }

    public String[] getAppenders() {
        return _appenders;
    }

    @XmlElementWrapper(name = "appenders") 
    @XmlElement(name = "appender")
    public void setAppenders(String[] appenders) {
        this._appenders = appenders;
    }

    public String[] getCategories() {
        return _categories;
    }

    @XmlElementWrapper(name = "categories") 
    @XmlElement(name = "category")
    public void setCategories(String[] categories) {
        this._categories = categories;
    }
}
