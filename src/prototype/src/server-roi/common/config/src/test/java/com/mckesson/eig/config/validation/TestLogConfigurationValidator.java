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
package com.mckesson.eig.config.validation;

import junit.framework.TestCase;

import com.mckesson.eig.config.exception.ConfigureLogException;
import com.mckesson.eig.config.model.LogConfigurationInfo;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
/**
 * This class tests the validations that are tested in the
 * LogConfigurationValidator class.
 *
 */
public class TestLogConfigurationValidator extends TestCase {

    /**
     * Reference of type <code>LogConfigurationValidator</code>.
     */
    private LogConfigurationValidator _logConfigurationValidator = new LogConfigurationValidator();

    /**
     * Reference of type <code>LogConfigurationInfo</code>.
     */
    private LogConfigurationInfo _logConfigurationInfo = null;
    /**
     * Initializes the log path information.
     */
    private static final String LOG_PATH = "WEB-INF/hecm.logging.xml";

    /**
     * Initializes data that are required for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _logConfigurationValidator = new LogConfigurationValidator();
        _logConfigurationInfo = new LogConfigurationInfo();
    }

    /**
     * Removes the data that are initialized for the test case in the setUp
     * method.
     *
     * @throws Exception
     *             On tear down.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method checks if the tested function returns true and false when a
     * valid and invalid source name is provided respectively.
     */
    public void testComponentInstalled() {
        assertTrue(_logConfigurationValidator.isComponentInstalled(LOG_PATH));
        assertFalse(_logConfigurationValidator.isComponentInstalled(null));
    }
    
    /**
     * Test method checks if the tested function throws a Security Exception
     * when an invalid source name is provided.
     */
    public void testEmptyFilePath() {
        try {
            assertFalse(_logConfigurationValidator.isComponentInstalled(""));
        } catch (ConfigureLogException exp) {
            fail();
        }
    }

    /**
     * Test method checks if the tested function returns true when a valid
     * Configuration information is provided.
     */
    public void testValidConfigInfo() {
        String componentName = "Valid Component Name";
        String loggingLevel = "debug";
        String maxLogFile = "100";
        String maxLogSize = "100KB";

        _logConfigurationInfo.setComponentName(componentName);
        _logConfigurationInfo.setLoggingLevel(loggingLevel);
        _logConfigurationInfo.setMaxLogFiles(maxLogFile);
        _logConfigurationInfo.setMaxLogSize(maxLogSize);
        assertTrue(_logConfigurationValidator
                .isValidConfigInfo(_logConfigurationInfo));
    }

    /**
     * Test method checks if the tested function throws an exception when an
     * invalid Configuration information is provided.
     */
    public void testvalidLogConfigInfo() {
        String componentName = "hecm server";
        String loggingLevel = "debug";
        String maxLogFile = "100";
        String maxLogSize = "100KB";
        try {
            _logConfigurationInfo = new LogConfigurationInfo();
            _logConfigurationInfo.setComponentName(componentName);
            _logConfigurationInfo.setLoggingLevel(loggingLevel);
            _logConfigurationInfo.setMaxLogFiles(maxLogFile);
            _logConfigurationInfo.setMaxLogSize(maxLogSize);
            assertTrue(_logConfigurationValidator
                    .isValidConfigInfo(_logConfigurationInfo));
        } catch (Exception exp) {
            fail("Exception occured while testing valid scenario");
        }
    }

    /**
     * Test method checks if the tested function throws an exception when an
     * invalid Configuration information is provided.
     */
    public void testInvalidLoggingLevel() {
        String componentName = "hecm server";
        String loggingLevel = "Invalid Logging Level";
        String maxLogFile = "100";
        String maxLogSize = "100KB";
        try {
            _logConfigurationInfo = new LogConfigurationInfo();
            _logConfigurationInfo.setComponentName(componentName);
            _logConfigurationInfo.setComponentSeq(1);
            _logConfigurationInfo.setLoggingLevel(loggingLevel);
            _logConfigurationInfo.setMaxLogFiles(maxLogFile);
            _logConfigurationInfo.setMaxLogSize(maxLogSize);
            _logConfigurationValidator.isValidConfigInfo(_logConfigurationInfo);
            fail("LogConfigurationException expected for invalid logging level");
        } catch (ConfigureLogException exp) {
            assertEquals(exp.getErrorCode(),
                    ClientErrorCodes.INVALID_LOGGING_LEVEL);
        }
    }

    /**
     * Test method checks if the tested function throws an exception when an
     * invalid Configuration information is provided.
     */
    public void testInvalidLogFiles() {
        String componentName = "hecm server";
        String loggingLevel = "debug";
        String maxLogFile = "$$$$";
        String maxLogSize = "100KB";
        try {
            _logConfigurationInfo = new LogConfigurationInfo();
            _logConfigurationInfo.setComponentName(componentName);
            _logConfigurationInfo.setComponentSeq(1);
            _logConfigurationInfo.setLoggingLevel(loggingLevel);
            _logConfigurationInfo.setMaxLogFiles(maxLogFile);
            _logConfigurationInfo.setMaxLogSize(maxLogSize);
            _logConfigurationValidator.isValidConfigInfo(_logConfigurationInfo);
            fail("LogConfigurationException expected for invalid logging level");
        } catch (ConfigureLogException exp) {
            assertEquals(exp.getErrorCode(), ClientErrorCodes.INVALID_MAX_FILES);
        }
    }
    
    /**
     * Test method checks if the tested function throws an exception when an
     * invalid Configuration information is provided.
     */
    public void testInvalidLogFileName() {
        
        try {
            
            _logConfigurationValidator.isValidFileSize("ABBBMB");
            fail("Should have thrown LogConfigurationException");
        } catch (ConfigureLogException exp) {
            assertEquals(exp.getErrorCode(), ClientErrorCodes.INVALID_FILE_SIZE);
        }
    }

    /**
     * Test method checks if the tested function throws an exception when an
     * invalid Configuration information is provided.
     */
    public void testInvalidLogSize() {
        String componentName = "hecm server";
        String loggingLevel = "debug";
        String maxLogFile = "100";
        String maxLogSize = "1mbps";
        try {
            _logConfigurationInfo = new LogConfigurationInfo();
            _logConfigurationInfo.setComponentName(componentName);
            _logConfigurationInfo.setComponentSeq(1);
            _logConfigurationInfo.setLoggingLevel(loggingLevel);
            _logConfigurationInfo.setMaxLogFiles(maxLogFile);
            _logConfigurationInfo.setMaxLogSize(maxLogSize);
            _logConfigurationValidator.isValidConfigInfo(_logConfigurationInfo);
            fail("LogConfigurationException expected for invalid logging level");
        } catch (ConfigureLogException exp) {
            assertEquals(exp.getErrorCode(), ClientErrorCodes.INVALID_FILE_SIZE);
        }
    }

    /**
     * Test method validates <code>isValidconfigInfo</code> by providing empty
     * log information.
     */
    public void testEmptyLogInformation() {
        String componentName = "hecm server";
        String loggingLevel = "debug";
        String maxLogFile = "10";
        String maxLogSize = "";
        try {
            _logConfigurationInfo = new LogConfigurationInfo();
            _logConfigurationInfo.setComponentName(componentName);
            _logConfigurationInfo.setComponentSeq(1);
            _logConfigurationInfo.setLoggingLevel(loggingLevel);
            _logConfigurationInfo.setMaxLogFiles(maxLogFile);
            _logConfigurationInfo.setMaxLogSize(maxLogSize);
            assertFalse(_logConfigurationValidator.isValidConfigInfo(_logConfigurationInfo));

        } catch (ConfigureLogException exp) {
            assertEquals(exp.getErrorCode(), ClientErrorCodes.EMPTY_LOG_INFORMATION);
        }
    }

    /**
     * Test method validates <code>isValidconfigInfo</code> by providing empty
     * componentName.
     */
    public void testEmptyComponentName() {
        String componentName = "";
        String loggingLevel = "debug";
        String maxLogFile = "10";
        String maxLogSize = "10MB";
        try {
            _logConfigurationInfo = new LogConfigurationInfo();
            _logConfigurationInfo.setComponentName(componentName);
            _logConfigurationInfo.setComponentSeq(1);
            _logConfigurationInfo.setLoggingLevel(loggingLevel);
            _logConfigurationInfo.setMaxLogFiles(maxLogFile);
            _logConfigurationInfo.setMaxLogSize(maxLogSize);
            assertFalse(_logConfigurationValidator.isValidConfigInfo(_logConfigurationInfo));

        } catch (ConfigureLogException exp) {
            assertEquals(exp.getErrorCode(), ClientErrorCodes.EMPTY_LOG_INFORMATION);
        }
    }

}
