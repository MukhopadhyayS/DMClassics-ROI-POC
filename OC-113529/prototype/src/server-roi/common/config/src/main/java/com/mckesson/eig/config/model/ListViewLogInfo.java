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
 * The ListViewLogInfo class models the details for
 * the log files corresponding to the components.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListViewLogInfo", namespace = EIGConstants.TYPE_NS_V1)
public class ListViewLogInfo {

    /**
     * Member variable of type String holding
     * the name of the component.
     */
    private String _componentName;
    
    /**
     * Member variable of type String holding
     * the name of the log file.
     */
    private String _logFileName;
    
    /**
     * Member variable of type String holding
     * the date of creation of the log file.
     */
    private String _logFileDate;

    /**
     * Member variable of type long holding the
     * size of the log file.
     */
    private long _logFileSize;

    /**
     * Member variable of type String holding the path
     * of the component installed in the machine.
     */
    private String _componentPath;

    /**
     * Member variable of type long holding the sequence
     * of the component installed in the machine.
     */
    private long _componentSequence;

    /**
     * This method obtains the name of the component that is set.
     *
     * @return componentName -name of the component.
     */
    public String getComponentName() {
        return _componentName;
    }

    /**
     * Sets the given component name.
     *
     * @param componentName
     *            Component Name to be set.
     */
    @XmlElement(name = "componentName")
    public void setComponentName(String componentName) {
        this._componentName = componentName;
    }

    /**
     * This method obtains the path of the component that is set.
     *
     * @return componentPath
     *              path of the component.
     */
    public String getComponentPath() {
        return _componentPath;
    }

    /**
     * Sets the given component path.
     *
     * @param componentPath
     *            Component Path to be set.
     */
    public void setComponentPath(String componentPath) {
        this._componentPath = componentPath;
    }

    /**
     * This method obtains the date of the log file.
     *
     * @return logFileDate
     *             date of the log file.
     */
    public String getLogFileDate() {
        return _logFileDate;
    }

    /**
     * Sets the given log file date.
     *
     * @param logFileDate
     *            date of the log file to be set.
     */
    @XmlElement(name = "logFileDate")
    public void setLogFileDate(String logFileDate) {
        this._logFileDate = logFileDate;
    }

    /**
     * This method obtains the name of the log file.
     *
     * @return logFileName
     *             name of the log file.
     */
    public String getLogFileName() {
        return _logFileName;
    }

    /**
     * Sets the given log file name.
     *
     * @param logFileName
     *            name of the log file to be set.
     */
    @XmlElement(name = "logFileName")
    public void setLogFileName(String logFileName) {
        this._logFileName = logFileName;
    }

    /**
     * This method obtains the size of the log file.
     *
     * @return logFileSize
     *              size of the log file.
     */
    public long getLogFileSize() {
        return _logFileSize;
    }

    /**
     * Sets the given log file size.
     *
     * @param logFileSize
     *            size of the log file to be set.
     */
    @XmlElement(name = "logFileSize")
    public void setLogFileSize(long logFileSize) {
        this._logFileSize = logFileSize;
    }

    /**
     * This method obtains the sequence of the component.
     *
     * @return componentSequence
     *               Sequence of the component installed.
     */
    public long getComponentSequence() {
        return _componentSequence;
    }

    /**
     * Sets the given component sequence.
     *
     * @param componentSequence
     *            sequence of the component installed.
     */
    @XmlElement(name = "componentSequence")
    public void setComponentSequence(long componentSequence) {
        this._componentSequence = componentSequence;
    }

}
