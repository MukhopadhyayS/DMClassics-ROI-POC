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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * 
 * The LogConfigurationInfo class contains the basic 
 * information required to configure the log files.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogConfigurationInfo", namespace = EIGConstants.TYPE_NS_V1)
public class LogConfigurationInfo {

    /**
     * Variable to hold MaxLogSize.
     */
    private String _maxLogSize;

    /**
     * Variable to hold MaxLogFiles.
     */
    private String _maxLogFiles;

    /**
     * Variable holding the logging level.
     */
    private String _loggingLevel;

    /**
     * Variable holding the component name.
     */
    private String _componentName;

    /**
     * Variable holding Components Sequence.
     */
    private long _componentSeq;

    /**
     * Returns this Component Sequence.
     * 
     * @return componentSeq.
     */
    public long getComponentSeq() {
        return _componentSeq;
    }

    /**
     * Sets this Component Sequence.
     * 
     * @param componentSeq
     *            Sequence which is to be set.
     */
    @XmlElement(name = "componentSeq")
    public void setComponentSeq(long componentSeq) {
        this._componentSeq = componentSeq;
    }

    /**
     * Returns this MaxLogFiles.
     * 
     * @return MaxLogFile.
     */
    public String getMaxLogFiles() {
        return _maxLogFiles;
    }

    /**
     * Sets this MaxLogFiles.
     * 
     * @param maxLogFiles
     *            MaxLogFiel to be set.
     */
    @XmlElement(name = "maxLogFiles")
    public void setMaxLogFiles(String maxLogFiles) {
        this._maxLogFiles = maxLogFiles;
    }

    /**
     * Returns this component name.
     * 
     * @return component name.
     */
    public String getComponentName() {
        return _componentName;
    }

    /**
     * Sets this Component Name.
     * 
     * @param componentName
     *            componenet name to be set.
     */
    @XmlElement(name = "componentName")
    public void setComponentName(String componentName) {
        this._componentName = componentName;
    }

    /**
     * Returns this logginglevel
     * 
     * @return loggingLevel.
     */
    public String getLoggingLevel() {
        return _loggingLevel;
    }

    /**
     * Returns this maxLogSize.
     * 
     * @return maxLogSize.
     */
    public String getMaxLogSize() {
        return _maxLogSize;
    }

    /**
     * Sets this loggingLevel
     * 
     * @param level
     *            loggingLevel to be set.
     */
    @XmlElement(name = "loggingLevel")
    public void setLoggingLevel(String level) {
        _loggingLevel = level;
    }

    /**
     * Sets this maxLogSize.
     * 
     * @param logSize
     *            maxLogSize to be set.
     */
    @XmlElement(name = "maxLogSize")
    public void setMaxLogSize(String logSize) {
        _maxLogSize = logSize;
    }
}
