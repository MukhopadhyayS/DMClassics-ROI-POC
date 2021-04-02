/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.config.service;

import java.util.List;

import com.mckesson.eig.config.audit.UnitSpringInitialization;
import com.mckesson.eig.config.exception.ConfigureLogException;
import com.mckesson.eig.config.model.InfoDetailList;
import com.mckesson.eig.config.model.ListViewLogDetail;
import com.mckesson.eig.config.model.ListViewLogDetailList;
import com.mckesson.eig.config.model.ListViewLogInfo;
import com.mckesson.eig.config.model.ListViewLogInfoList;
import com.mckesson.eig.config.model.LogConfigurationInfo;
import com.mckesson.eig.config.model.LogConfigurationInfoList;
import com.mckesson.eig.utility.exception.ClientErrorCodes;

/**
 * @author Sahul Hameed Y
 * @date   Feb 12, 2008
 * @since  HECM 1.0; Feb 12, 2008
 */
public class TestLogConfigurationService 
extends junit.framework.TestCase {
    
    /**
     * Holds the instance of ListViewLogService.
     */
    private ConfigureLogService _logConfigurationService = null;
    
    /**
     * Initializes the logging level.
     */
    private static final String LOGGING_LEVEL = "debug";
    
    /**
     * Holds instance of component name.
     */
    private static final String COMPONENT_NAME = "hecm server";
    
    /**
     * Holds the instance of ListViewLogDetail.
     */
    private ListViewLogDetail _listViewLogDetail = null;
    
    /**
     * Holds the instance of component sequence.
     */
    private static final long COMPONENT_SEQUENCE = 1;
    
    /**
     * Holds the instance of ListViewLogInfoList.
     */
    private ListViewLogInfoList _listViewLogInfoList = null;
    
    /**
     * Holds the instance of ListViewLogInfo.
     */
    private ListViewLogInfo _listViewLogInfo = null;
    
    /**
     * Holds the instance of log file name.
     */
    private static final String LOG_FILE_NAME = "eig.log";
    
    /**
     * setUp method for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        
        super.setUp();
        UnitSpringInitialization.init();
        _logConfigurationService = new ConfigureLogService();
    } 
    
    /**
     * test method for to get all the server components config info.
     */
    public void testGetAllServerComponentsConfigInfo() {
        
      LogConfigurationInfoList logConfigurationInfoList = 
                              _logConfigurationService.getAllServerComponentsConfigInfo();
      assertNotNull(logConfigurationInfoList);
    }
    
    /**
     * test method for to get all the server components  with null config info.
     */
    public void testSaveServerComponentConfiginfoWithInfo() {
      
        try {
            _logConfigurationService.saveServerComponentConfigInfo(null);
        } catch (ConfigureLogException e) {
          assertEquals(ClientErrorCodes.EMPTY_LOG_INFORMATION, e.getErrorCode());
        }
    }
    
    /**
     * test method for to get all the server components config info.
     */
    public void testSaveServerComponentConfiginfo() {
      
        boolean saveServerResult = false;
        LogConfigurationInfo  logConfigurationInfo = new LogConfigurationInfo();
        logConfigurationInfo.setLoggingLevel(LOGGING_LEVEL);
        logConfigurationInfo.setMaxLogFiles("4");
        logConfigurationInfo.setMaxLogSize("4MB");
        logConfigurationInfo.setComponentName(COMPONENT_NAME);
        logConfigurationInfo.setComponentSeq(1);
        saveServerResult = 
            _logConfigurationService.saveServerComponentConfigInfo(logConfigurationInfo);
        assertEquals(saveServerResult, true);
    }
    
    /**
     * This test method tests the getAllServerComponentList method of the
     * ListViewLogService class.
     */
    public void testAllServerComponentList() {
        
        ListViewLogDetailList listView = _logConfigurationService.getAllServerComponentList();
        assertNotNull(_logConfigurationService);
        List listViewLogDetailList = listView.getListViewLogDetailList();
        _listViewLogDetail = (ListViewLogDetail) listViewLogDetailList.get(0);
        assertEquals(_listViewLogDetail.getComponentName(), COMPONENT_NAME);
        assertEquals(_listViewLogDetail.getComponentSeq(), COMPONENT_SEQUENCE);
    }

    /**
     * This test method tests the getServerComponentLogList of the
     * ListViewLogService class for a valid Component Sequence.
     */
    public void testServerComponentLogList() {
        
        _listViewLogInfoList = _logConfigurationService.getServerComponentLogList(1);
        assertNotNull(_listViewLogInfoList);
        _listViewLogInfo = _listViewLogInfoList.getListViewLogInfoList().get(1);
        assertEquals(_listViewLogInfo.getComponentName(), COMPONENT_NAME);
        assertEquals(_listViewLogInfo.getLogFileName(), LOG_FILE_NAME);
    }

    /**
     * This test method tests the getAllServerComponentLogList of the
     * ListViewLogService class for an invalid Component Sequence.
     */
    public void testInvalidAllServerComponentLogList() {
        try {
            _listViewLogInfoList = _logConfigurationService.getServerComponentLogList(-1);
            fail("Exception not thrown");
        } catch (ConfigureLogException exp) {
            
            assertNotNull(exp);
            assertNotNull(exp.getMessage());
            assertEquals(exp.getErrorCode(), ClientErrorCodes.INVALID_COMPONENT_SEQ);
        }
    }

    /**
     * This test method tests the getAllServerComponentLogList method of the
     * ListViewLogService class.
     */
    public void testAllServerComponentLogList() {
        
        InfoDetailList infoDetailList = null;
        ListViewLogInfoList listViewLogInfoList = null;
        ListViewLogInfo listViewLogInfo = null;
        
        infoDetailList = _logConfigurationService.getAllServerComponentLogList();
        
        assertNotNull(infoDetailList);
        
        List infoList = infoDetailList.getListViewLogInfoList();
        listViewLogInfoList = (ListViewLogInfoList) infoList.get(0);
        listViewLogInfo = listViewLogInfoList.getListViewLogInfoList().get(1);
        assertEquals(listViewLogInfo.getComponentName(), COMPONENT_NAME);
        assertEquals(listViewLogInfo.getComponentSequence(), COMPONENT_SEQUENCE);
        assertEquals(listViewLogInfo.getLogFileName(), LOG_FILE_NAME);
    }
}
