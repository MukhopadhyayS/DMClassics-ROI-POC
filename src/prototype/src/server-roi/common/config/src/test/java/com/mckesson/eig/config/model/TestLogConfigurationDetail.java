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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
/**
 * This class tests the LogConfigurationDetail.
 */
public class TestLogConfigurationDetail extends TestCase {

    /**
     * Reference of type <code>LogConfigurationDetail</code>.
     */
    private LogConfigurationDetail _logConfigurationDetail;
   
    /**
     * Member variable of type List.
     */
    private List _categoryList;
    
    /**
     * Member variable of type List.
     */
    private List _appenderList;
    
    /**
     * Initializes the component name.
     */
    private static final String COMPONENT_NAME = "hecm server";
    
    /**
     * Initializes the component sequence.
     */
    private static final String COMPONENT_SEQ = "1";
   
    /**
     * Specifies the logging path.
     */
    private static final String LOGGING_PATH = "Test Path";
   
    /**
     * setUp method for the test case.
     * 
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _categoryList = new ArrayList();
        _appenderList = new ArrayList();
        _logConfigurationDetail = new LogConfigurationDetail();
        _logConfigurationDetail.setComponentName(COMPONENT_NAME);
        _logConfigurationDetail.setComponentSeq(COMPONENT_SEQ);
        _logConfigurationDetail.setLogFile(LOGGING_PATH);
        _categoryList.add("Category 1");
        _categoryList.add("Category 2");
        _appenderList.add("Appender 1");
        _appenderList.add("Appender 2");
        _logConfigurationDetail.setAppenderList(_appenderList);
        _logConfigurationDetail.setCategoryList(_categoryList);
    }

    /**
     * Removes the data initialized on setUp.
     * 
     * @throws Exception
     *             On tear down.
     */

    protected void tearDown() throws Exception {
        _logConfigurationDetail = null;
        super.tearDown();
    }

    /**
     * Test method checks all the fields in <code>LogConfigurationDetail</code>.
     */
    
    public void testLogConfigurationDetail() {
        assertEquals(COMPONENT_NAME, _logConfigurationDetail.getComponentName());
        assertEquals(COMPONENT_SEQ, _logConfigurationDetail.getComponentSeq());
        assertEquals(LOGGING_PATH, _logConfigurationDetail.getLogFile());
        assertEquals(_categoryList, _logConfigurationDetail.getCategoryList());
        assertEquals(_appenderList, _logConfigurationDetail.getAppenderList());
    }
}
