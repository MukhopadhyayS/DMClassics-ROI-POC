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
 * Test class for ListViewLogInfo. It tests the methods of ListViewLogInfo class
 *
 */
public class TestListViewLogInfo extends TestCase {

    /**
     * Reference of type <code>ListViewLogInfo</code>.
     */
    private ListViewLogInfo _listViewLogInfo = null;

    /**
     * Holds instance of log file size.
     */
    private static final long LOG_FILE_SIZE = 100;
    /**
     * Holds instance of component name.
     */
    private static final String COMPONENT_NAME = "Component name";
    /**
     * Holds instance of component path.
     */
    private static final String COMPONENT_PATH = "C://Java";
    /**
     * Holds instance of date.
     */
    private static final String DATE = "04/07/2007 23:56 AM";
    /**
     * Holds instance of log file name.
     */
    private static final String LOG_FILE_NAME = "log.txt";
    /**
     * Test method, tests whether the object of type
     * <code>ListViewLogInfo</code> is null or not.
     */
    public void testListViewLogInfo() {
        assertNotNull(_listViewLogInfo);
    }
    /**
     * setUp method for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _listViewLogInfo = new ListViewLogInfo();
    }
    /**
     * Removes the data initialized as a part of the setUp.
     *
     * @throws Exception
     *             On tear down.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        _listViewLogInfo = null;
    }

    /**
     * Test methods, tests the getter and setter methods of
     * <code>componentName</code>
     */
    public void testComponentName() {
        _listViewLogInfo.setComponentName(COMPONENT_NAME);
        assertEquals(COMPONENT_NAME, _listViewLogInfo.getComponentName());
    }

    /**
     * Test methods, tests the getter and setter methods of
     * <code>componentPath</code>
     */
    public void testComponentPath() {
        _listViewLogInfo.setComponentPath(COMPONENT_PATH);
        assertEquals(COMPONENT_PATH, _listViewLogInfo.getComponentPath());
    }

    /**
     * Test methods, tests the getter and setter methods of
     * <code>logFileDate</code>
     */
    public void testLogFileDate() {
        _listViewLogInfo.setLogFileDate(DATE);
        assertEquals(DATE, _listViewLogInfo.getLogFileDate());
    }

    /**
     * Test methods, tests the getter and setter methods of
     * <code>logFileName</code>
     */
    public void testLogFileName() {
        _listViewLogInfo.setLogFileName(LOG_FILE_NAME);
        assertEquals(LOG_FILE_NAME, _listViewLogInfo.getLogFileName());

    }

    /**
     * Test methods, tests the getter and setter methods of
     * <code>logFileSize</code>
     */
    public void testLogFileSize() {
        _listViewLogInfo.setLogFileSize(LOG_FILE_SIZE);
        assertEquals(LOG_FILE_SIZE, _listViewLogInfo.getLogFileSize());
    }
}
