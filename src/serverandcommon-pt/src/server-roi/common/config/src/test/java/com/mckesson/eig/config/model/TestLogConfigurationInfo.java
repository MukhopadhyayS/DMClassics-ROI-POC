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

import junit.framework.TestCase;
/**
 * This class tests the various methods of TestLogConfigurationInfo.
 * 
 *
 */
public class TestLogConfigurationInfo extends TestCase {

    /**
     * Reference of type <code>LogConfigurationInfo</code>.
     */

    private LogConfigurationInfo _logConfigurationInfo = null;
    
    /**
     *Initializes the component name.
     */
    private static final String COMPONENT_NAME = "hecm server";
   
    /**
     * Initializes the maximum log files.
     */

    private static final String MAX_LOG_FILE = "22";
    
    /**
     * Initializes the maximum log size.
     */

    private static final String MAX_LOG_SIZE = "22GB";
    
    /**
     * Initializes the component sequence.
     */
    private static final long COMPONENT_SEQ = 1;
    
    /**
     * Initializes the logging level.
     */
    private static final String LOGGING_LEVEL = "debug";

    /**
     * setUp method for the test case.
     * 
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _logConfigurationInfo = new LogConfigurationInfo();
        _logConfigurationInfo.setComponentName(COMPONENT_NAME);
        _logConfigurationInfo.setComponentSeq(COMPONENT_SEQ);
        _logConfigurationInfo.setLoggingLevel(LOGGING_LEVEL);
        _logConfigurationInfo.setMaxLogFiles(MAX_LOG_FILE);
        _logConfigurationInfo.setMaxLogSize(MAX_LOG_SIZE);
    }

    /**
     * Removes the data initialized as a part of the setUp.
     * 
     * @throws Exception
     *             On tear down.
     */

    protected void tearDown() throws Exception {
        _logConfigurationInfo = null;
        super.tearDown();
    }

    /**
     * Test method checks all the fields in <code>LogConfigurationInfo</code>.
     */
    public void testLogConfigurationInfo() {
        assertEquals(_logConfigurationInfo.getComponentName(), COMPONENT_NAME);
        assertEquals(_logConfigurationInfo.getLoggingLevel(), LOGGING_LEVEL);
        assertEquals(_logConfigurationInfo.getMaxLogFiles(), MAX_LOG_FILE);
        assertEquals(_logConfigurationInfo.getMaxLogSize(), MAX_LOG_SIZE);
        assertEquals(_logConfigurationInfo.getComponentSeq(), COMPONENT_SEQ);
    }
}
