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
package com.mckesson.eig.config.utils;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.mckesson.eig.config.exception.ConfigureLogException;
import com.mckesson.eig.config.model.LogConfigurationDetail;
import com.mckesson.eig.config.model.LogConfigurationInfo;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
/**
 * This class tests the LogConfigurationPersistance class with various
 * scenarios.
 * 
 */
public class TestLogConfigurationPersistance extends TestCase {

    /**
     * Initializes the logging level.
     */
    private static final String LOGGING_LEVEL = "debug";

    /**
     * Initializes the file size.
     */
    private static final String FILE_SIZE = "4MB";

    /**
     * Variable holding instance for backup index.
     */
    private static final String MAX_FILES = "4";

    /**
     * Initializes the component name.
     */
    private static final String COMPONENT_NAME = "hecm server";

    /**
     * Member variable to hold the instance of LogConfigurationPersistence.
     * 
     */
    private LogConfigurationPersistence _logConfigurationPersistence;

    /**
     * Member variable to hold the instance of LogConfigurationDetail.
     */
    private LogConfigurationDetail _logConfigurationDetail = new LogConfigurationDetail();

    /**
     * Member variable to hold the log path information.
     */
    private static final String LOG_PATH = "WEB-INF/hecm.logging.xml";
    
    /**
     * Member variable to hold the log path information.
     */
    private static final String INVALID_LOG_PATH = "WEB-INF/Invalid.logging.xml";
    
    /**
     * Member variable to hold the log path information.
     */
    private static final String EMPTY_VALUE_PATH = "WEB-INF/EmptyValues.logging.xml";

    /**
     * Member variable to hold the list of appenders.
     */
    private List _appenderList = null;

    /**
     * Member variable to hold the list of categories.
     */
    private List _categoryList = null;

    /**
     * Member variable to hold the instance of LogConfigurationInfo.
     * 
     */
    private LogConfigurationInfo _logConfigurationInfo = null;

    /**
     * Initializes data for testing.
     * 
     * @throws Exception
     *             when initialization fails.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _appenderList = new ArrayList();
        _categoryList = new ArrayList();
        _appenderList.add("eigLogAppender");
        _categoryList.add("com.quickstream");
        _categoryList.add("com.mckesson.hecm");
        _categoryList.add("com.mckesson.eig");

        _logConfigurationDetail.setComponentName("hecm server");
        _logConfigurationDetail.setComponentSeq("1");
        _logConfigurationDetail.setLogFile(LOG_PATH);
        _logConfigurationDetail.setAppenderList(_appenderList);
        _logConfigurationDetail.setCategoryList(_categoryList);

        _logConfigurationPersistence = new LogConfigurationPersistence();
    }

    /**
     * Removes data initialized for testing.
     * 
     * @throws Exception
     *             when initialization fails.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * This method tests the ReadComponentConfigInfo with various scenarios.
     * 
     */
    public void testReadComponentConfigInfo() {

        _logConfigurationInfo = _logConfigurationPersistence
                .readComponentConfigInfo(_logConfigurationDetail);
        assertNotNull(_logConfigurationInfo);
        assertEquals(_logConfigurationInfo.getComponentName(),
                _logConfigurationDetail.getComponentName());
        assertEquals(_logConfigurationInfo.getComponentSeq(), Long
                .parseLong(_logConfigurationDetail.getComponentSeq()));
        assertNotNull(_logConfigurationInfo.getLoggingLevel());
        assertEquals(LOGGING_LEVEL, _logConfigurationInfo.getLoggingLevel());
        assertEquals(MAX_FILES, _logConfigurationInfo.getMaxLogFiles());
        assertEquals(FILE_SIZE, _logConfigurationInfo.getMaxLogSize());
    }
    
    /**
     * This testcase validates getComponent with empty Log4J elements in the configuration file.
     */
    public void testInvalidReadComponent() {
        try {
            _logConfigurationDetail.setLogFile(INVALID_LOG_PATH);
            _logConfigurationInfo = _logConfigurationPersistence
                    .readComponentConfigInfo(_logConfigurationDetail);
            fail("Exception should have occured in testInvalidReadComponent");
        } catch (ConfigureLogException e) {
            assertEquals(e.getErrorCode(),
                    ClientErrorCodes.UNABLE_TO_RETRIEVE_LOG_INFORMATION);
        }
    }
    
    /**
     * This testcase validates getComponent with empty values in the configuration file.
     */
    public void testEmptyReadComponent() {
        try {
            _logConfigurationDetail.setLogFile(EMPTY_VALUE_PATH);
            _logConfigurationInfo = _logConfigurationPersistence
                    .readComponentConfigInfo(_logConfigurationDetail);
            fail("Exception should have occured in testInvalidReadComponent");
        } catch (ConfigureLogException e) {
            assertEquals(e.getErrorCode(),
                    ClientErrorCodes.UNABLE_TO_RETRIEVE_LOG_INFORMATION);
        }
    }

    /**
     * This testcase validates getComponent by providing invalid path.
     */
    public void testInvalidPath() {
        try {
            _logConfigurationDetail.setLogFile("INVALID_PATH");
            _logConfigurationPersistence
                    .readComponentConfigInfo(_logConfigurationDetail);
            fail("Expected File Load Exception");
        } catch (ConfigureLogException exp) {
            assertEquals(ClientErrorCodes.UNABLE_TO_FIND_FILE, exp
                    .getErrorCode());
        }
    }

    /**
     * This test case tests for saveServerComponent operation.
     */
    public void testSaveServerComponent() {
        try {
            boolean saveServerResult = false;
            _logConfigurationInfo = new LogConfigurationInfo();
            _logConfigurationInfo.setLoggingLevel(LOGGING_LEVEL);
            _logConfigurationInfo.setMaxLogFiles(MAX_FILES);
            _logConfigurationInfo.setMaxLogSize(FILE_SIZE);
            _logConfigurationInfo.setComponentName(COMPONENT_NAME);
            _logConfigurationInfo.setComponentSeq(1);
            saveServerResult = _logConfigurationPersistence.saveConfigValues(
                    _logConfigurationInfo, _logConfigurationDetail);
            assertTrue(saveServerResult);
        } catch (Exception exp) {
            fail(exp.getMessage());
        }
    }
    
    /**
     * This test case tests for saveServerComponent operation with
     * <code>MaxLogFiles</code> and <code>MaxLogSize</code> values prefixed
     * with plus.
     */
    public void testSaveServerComponentWithPlusPrefix() {
        try {
            boolean saveServerResult = false;
            _logConfigurationInfo = new LogConfigurationInfo();
            _logConfigurationInfo.setLoggingLevel(LOGGING_LEVEL);
            _logConfigurationInfo.setMaxLogFiles("+4");
            _logConfigurationInfo.setMaxLogSize("+4MB");
            _logConfigurationInfo.setComponentName(COMPONENT_NAME);
            _logConfigurationInfo.setComponentSeq(1);
            saveServerResult = _logConfigurationPersistence.saveConfigValues(
                    _logConfigurationInfo, _logConfigurationDetail);
            assertTrue(saveServerResult);
        } catch (Exception exp) {
            fail(exp.getMessage());
        }
    }
    
    /**
     * This test case tests for saveServerComponent operation.
     */
    public void testInvalidSaveServerComponent() {
        try {
            _logConfigurationDetail.setLogFile(INVALID_LOG_PATH);
            _logConfigurationInfo = new LogConfigurationInfo();
            _logConfigurationInfo.setLoggingLevel(LOGGING_LEVEL);
            _logConfigurationInfo.setMaxLogFiles(MAX_FILES);
            _logConfigurationInfo.setMaxLogSize(FILE_SIZE);
            _logConfigurationInfo.setComponentName(COMPONENT_NAME);
            _logConfigurationInfo.setComponentSeq(1);
            _logConfigurationPersistence.saveConfigValues(
                    _logConfigurationInfo, _logConfigurationDetail);
            fail("Exception should have thrown in testInvalidSaveServerComponent");
        } catch (ConfigureLogException exp) {
            assertEquals(exp.getErrorCode() , ClientErrorCodes.INVALID_LOG4J_ELEMENT);
        }
    }
}
