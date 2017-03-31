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

package com.mckesson.eig.config.controller;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.mckesson.eig.config.audit.UnitSpringInitialization;
import com.mckesson.eig.config.exception.ConfigureLogException;
import com.mckesson.eig.config.model.LogConfigurationInfo;
import com.mckesson.eig.config.model.LogConfigurationInfoList;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author Sahul Hameed Y
 * @date   Feb 12, 2008
 * @since  HECM 1.0; Feb 12, 2008
 */
public class TestLogConfigurationController 
extends junit.framework.TestCase {
    
    /**
     * Holds the instance of LogConfigurationController.
     */
    private ConfigureLogController _logConfigController = null;
    
    /**
     * Initializes the logging level.
     */
    private static final String LOGGING_LEVEL = "debug";
    
    /**
     * Holds instance of component name.
     */
    private static final String COMPONENT_NAME = "hecm server";
    
    protected void setUp() throws Exception {
        
        super.setUp();
        UnitSpringInitialization.init();
        _logConfigController = new ConfigureLogController();
    }
    
    /**
     * Deletes the default organization and its contents.
     *
     * @throws Exception
     *             On tear down.
     */
    protected void tearDown() throws Exception {
        
        super.tearDown();
        _logConfigController = null;
    }
    
    public void testGetAllServerComponentsConfigInfo() {
        
        LogConfigurationInfoList logInfoList = 
                                         _logConfigController.getAllServerComponentsConfigInfo();
        LogConfigurationInfo logConfigDeatil = logInfoList.getLogConfigurationInfoList().get(0);
        
        assertEquals(logConfigDeatil.getComponentName(), COMPONENT_NAME);
        assertEquals(logConfigDeatil.getComponentSeq(), 1);
    }
    
    public void testSaveServerComponent() {
        
        boolean saveServerResult = false;
        LogConfigurationInfo  logConfigurationInfo = new LogConfigurationInfo();
        logConfigurationInfo.setLoggingLevel(LOGGING_LEVEL);
        logConfigurationInfo.setMaxLogFiles("4");
        logConfigurationInfo.setMaxLogSize("4MB");
        logConfigurationInfo.setComponentName(COMPONENT_NAME);
        logConfigurationInfo.setComponentSeq(1);
        saveServerResult = _logConfigController.saveServerComponent(logConfigurationInfo);
        assertEquals(saveServerResult, true);
    }
    
    public void testSaveInValidComponentName() {
        
        try {
            
            LogConfigurationInfo  logConfigurationInfo = new LogConfigurationInfo();
            logConfigurationInfo.setLoggingLevel(LOGGING_LEVEL);
            logConfigurationInfo.setMaxLogFiles("+4");
            logConfigurationInfo.setMaxLogSize("+4MB");
            logConfigurationInfo.setComponentName("sah");
            logConfigurationInfo.setComponentSeq(1);
            
            _logConfigController.saveServerComponent(logConfigurationInfo);
            fail("Should have thrown an LogConfigurationException");
        } catch (ConfigureLogException e) {
            assertEquals(ClientErrorCodes.INVALID_COMPONENT_NAME, e.getErrorCode());
        }
    }
    
    public void testSaveInValidSequenceNumber() {
        
        try {
            
            LogConfigurationInfo  logConfigurationInfo = new LogConfigurationInfo();
            logConfigurationInfo.setLoggingLevel(LOGGING_LEVEL);
            logConfigurationInfo.setMaxLogFiles("+4");
            logConfigurationInfo.setMaxLogSize("+4MB");
            logConfigurationInfo.setComponentName(COMPONENT_NAME);
            logConfigurationInfo.setComponentSeq(Long.MAX_VALUE);
            
            _logConfigController.saveServerComponent(logConfigurationInfo);
            fail("Should have thrown an ConfigureLogException");
        } catch (ConfigureLogException e) {
            assertEquals(ClientErrorCodes.INVALID_COMPONENT_SEQ, e.getErrorCode());
        }
    }
    
    public void testGetAllServerComponentsConfigInfoWithoutComponent() {
        
        try {
            SpringUtilities.getInstance().setBeanFactory(new XmlBeanFactory(
                    new ClassPathResource("/WEB-INF/testApplicationContextWithoutComponent.xml")));
            _logConfigController.getAllServerComponentsConfigInfo();
        } catch (ConfigureLogException e) {
            assertEquals(ClientErrorCodes.NIL_COMPONENT_INSTALLED, e.getErrorCode());
        }
    }
    
    /**
     * This test method tests the findLogFile method of the
     * ListViewLogController class with an empty file name.
     */
    public void testInvalidFindLogFile() {
        try {
            _logConfigController.findLogFile(null);
            fail("Exception not thrown.");
        } catch (ConfigureLogException exp) {
            
            assertNotNull(exp.getMessage());
            assertEquals(exp.getErrorCode(), ClientErrorCodes.INVALID_FILE_NAME);
        }
    }

    /**
     * This test method tests the serverComponentLogList method of the
     * ListViewLogController class for an invalid filename.
     */
    public void testServerComponentLogListNullComponent() {
        
        try {
            _logConfigController.getServerComponentLogList(-1);
        } catch (ConfigureLogException exp) {
            
            assertNotNull(exp.getMessage());
            assertEquals(exp.getErrorCode(), ClientErrorCodes.NIL_COMPONENT_INSTALLED);
        }
    }
    
    /**
     * This test method tests the serverComponentLogList method of the
     * ListViewLogController class for an invalid filename.
     */
    public void testAllServerComponentLogList() {
        try {
            _logConfigController.getAllServerComponentLogList();
        } catch (ConfigureLogException exp) {
            
            assertNotNull(exp.getMessage());
            assertEquals(exp.getErrorCode(), ClientErrorCodes.NIL_COMPONENT_INSTALLED);
        }
    }
    
    /**
     * This test method tests the serverComponentLogList method of the
     * ListViewLogController class for an invalid filename.
     */
    public void testAllServerComponentLogListWithoutComponent() {
        
        try {
            
            SpringUtilities.getInstance().setBeanFactory(new XmlBeanFactory(
                    new ClassPathResource("/WEB-INF/testApplicationContextWithoutComponent.xml")));
            _logConfigController.getAllServerComponentLogList();
        } catch (ConfigureLogException exp) {
            
            assertNotNull(exp.getMessage());
            assertEquals(exp.getErrorCode(), ClientErrorCodes.NIL_COMPONENT_INSTALLED);
        }
    }
    
    /**
     * This test method tests the serverComponentLogList method of the
     * ListViewLogController class for an invalid filename.
     */
    public void testGetInstalledComponentInfoWithInvalidComponentSeq() {
        
        try {
            
            SpringUtilities.getInstance().setBeanFactory(new XmlBeanFactory(
                    new ClassPathResource("/WEB-INF/testApplicationContextWithoutComponent.xml")));
            
            LogConfigurationInfo  logConfigurationInfo = new LogConfigurationInfo();
            logConfigurationInfo.setLoggingLevel(LOGGING_LEVEL);
            logConfigurationInfo.setMaxLogFiles("+4");
            logConfigurationInfo.setMaxLogSize("+4MB");
            logConfigurationInfo.setComponentName(COMPONENT_NAME);
            logConfigurationInfo.setComponentSeq(Long.MAX_VALUE);
            
            _logConfigController.saveServerComponent(logConfigurationInfo);
            fail("Should have thrown an ConfigureLogException");
        } catch (ConfigureLogException e) {
            assertEquals(ClientErrorCodes.NIL_COMPONENT_INSTALLED, e.getErrorCode());
        }
    }
    
    /**
     * This test method tests the serverComponentLogList method of the
     * ListViewLogController class for an invalid filename.
     */
    public void testGetInstalledComponentWithInvalidLogFileName() {
        
        try {
            
            SpringUtilities.getInstance().setBeanFactory(new XmlBeanFactory(
                    new ClassPathResource("/WEB-INF/ testApplicationContextWithEmptyLog.xml")));
            
            LogConfigurationInfo  logConfigurationInfo = new LogConfigurationInfo();
            logConfigurationInfo.setLoggingLevel(LOGGING_LEVEL);
            logConfigurationInfo.setMaxLogFiles("+4");
            logConfigurationInfo.setMaxLogSize("+4MB");
            logConfigurationInfo.setComponentName(COMPONENT_NAME);
            logConfigurationInfo.setComponentSeq(Long.MAX_VALUE);
            
            _logConfigController.saveServerComponent(logConfigurationInfo);
            fail("Should have thrown an ConfigureLogException");
        } catch (ConfigureLogException e) {
            assertEquals(ClientErrorCodes.INVALID_COMPONENT_SEQ, e.getErrorCode());
        }
    }
}
