/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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

/**
 * LogConfigurationDetail class is used to retrieve the logging information from the context file
 * during run time.
 * 
 */
public class LogConfigurationDetail {

    /**
     * Variable holding logfiles path.
     */
    private String _logFile;

    /**
     * Holds list of appenders defined in the logging.xml.
     */
    private List _appenderList;

    /**
     * Holds list of categories defined in the logging.xml.
     */
    private List _categoryList;

    /**
     * Holds the sequence for a component.
     */
    private String _componentSeq;

    /**
     * Name of the component.
     */
    private String _componentName;

    /**
     * This method obtains the log file path.
     * 
     * @return _logFile 
     *          log file path.
     */
    public String getLogFile() {
        return _logFile;
    }

    /**
     * This method obtains the AppenderList that is set. 
     * 
     * @return appender list.
     */
    public List getAppenderList() {
        return _appenderList;
    }

    /**
     * This method obtains the CategoryList that is set.
     * 
     * @return categories listed in the configuration file.
     */
    public List getCategoryList() {
        return _categoryList;
    }

    /**
     *This method obtains the component name that is set.
     * 
     * @return component name.
     */
    public String getComponentName() {
        return _componentName;
    }

    /**
     * This method obtains the component sequence.
     * 
     * @return component sequence.
     */
    public String getComponentSeq() {
        return _componentSeq;
    }
   
    /**
     * Sets the given log file path.
     * 
     * @param logFile
     *            path to be set.
     */
    public void setLogFile(String logFile) {
        this._logFile = logFile;
    }

    /**
     * Sets the given appender list.
     * 
     * @param appenderList
     *            list to be set.
     */
    public void setAppenderList(List appenderList) {
        this._appenderList = appenderList;
    }

    /**
     * Sets the given category list.
     * 
     * @param categoryList
     *            category list to be set.
     */
    public void setCategoryList(List categoryList) {
        this._categoryList = categoryList;
    }

    /**
     * Sets the given component name.
     * 
     * @param component
     *            name to be set.
     */
    public void setComponentName(String component) {
        this._componentName = component;
    }

    /**
     * Sets the given component sequence.
     * 
     * @param componentSequence
     *            Sequence to be set.
     */
    public void setComponentSeq(String componentSequence) {
        this._componentSeq = componentSequence;
    }

}
