/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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

import java.util.HashMap;

import com.mckesson.eig.config.constants.ConfigurationEC;
import com.mckesson.eig.config.dao.ApplicationSettingDAO;
import com.mckesson.eig.config.exception.ConfigurationException;
import com.mckesson.eig.config.model.ApplicationSetting;
import com.mckesson.eig.config.model.ApplicationSettingList;
import com.mckesson.eig.config.test.AbstractConfigurationTestCase;
import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author sahuly
 * @date   Mar 26, 2009
 * @since  HECM 2.0; Mar 26, 2009
 */
public class TestApplicationSettingsServiceImpl extends AbstractConfigurationTestCase {

    private static ApplicationSettingService _applicationSettingService;
    private static ApplicationSettingDAO _applicationSettingDAO;
    private static ApplicationSettingsData _applicationSettingData;

    public void testSetUp() throws Exception {

        init();
        WsSession.initializeSession();
        WsSession.setSessionUserId(new Long(1));
        _applicationSettingService =
            (ApplicationSettingService) getManager(APPLICATION_SETTING_MANAGER);
        _applicationSettingDAO =
            (ApplicationSettingDAO) getDAO(ApplicationSettingDAO.class.getName());
        _applicationSettingData = new ApplicationSettingsData();
    }

    public void testGetGlobalSetting() {

        ApplicationSetting applicationSetting = _applicationSettingService.getGlobalSetting();
        assertEquals("GLOBAL", applicationSetting.getAppName());
        assertEquals(0, applicationSetting.getAppId());
    }

    public void testUpdateGlobalSetting() {

        ApplicationSetting applicationSetting = _applicationSettingService.getGlobalSetting();
        applicationSetting.setAppName("Test");
        applicationSetting.setSettings(_applicationSettingData.getGlobalSettings());
        applicationSetting = _applicationSettingService.updateGlobalSetting(applicationSetting);
        assertEquals("Test", applicationSetting.getAppName());
        applicationSetting.setAppName("GLOBAL");
        applicationSetting = _applicationSettingService.updateGlobalSetting(applicationSetting);
        assertEquals("GLOBAL", applicationSetting.getAppName());
    }

    public void testUpdateGlobalSettingWithNull() {

        try {
            _applicationSettingService.updateGlobalSetting(null);
        } catch (ConfigurationException ce) {
            assertEquals(ConfigurationEC.EC_NULL_GLOBAL_SETTING, ce.getErrorCode());
        } catch (ApplicationException ae) {
            assertEquals(ConfigurationEC.EC_NULL_GLOBAL_SETTING, ae.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testUpdateGlobalSettingWithEmptyHostName() {

        try {

            ApplicationSetting applicationSetting = _applicationSettingService.getGlobalSetting();
            applicationSetting.setSettings(applicationSetting.getSettings()
                    .replaceFirst("ims1.mckesson.com", ""));
            applicationSetting = _applicationSettingService.updateGlobalSetting(applicationSetting);
        } catch (ConfigurationException ce) {
            assertEquals(ConfigurationEC.EC_NULL_HOSTNAME, ce.getErrorCode());
        }
    }

    public void testUpdateGlobalSettingWithInvalidPortNumber() {

        try {

            ApplicationSetting applicationSetting = _applicationSettingService.getGlobalSetting();
            applicationSetting.setSettings(applicationSetting.getSettings()
                    .replaceFirst("1234", "0"));
            applicationSetting = _applicationSettingService.updateGlobalSetting(applicationSetting);
        } catch (ConfigurationException ce) {
            assertEquals(ConfigurationEC.EC_INVALID_PORT_NUMBER, ce.getErrorCode());
        }
    }

    public void testGetApplicationSettings() {

        ApplicationSettingList applicationSettings =
                                            _applicationSettingService.getApplicationSettings();
        assertTrue(applicationSettings.getApplicationSettings().size() > 0);
    }

    public void testUpdateApplicationSettings() {

        ApplicationSetting applicationSetting = _applicationSettingDAO.getApplicationSetting(1);
        applicationSetting.setSettings(_applicationSettingData.getApplicationSettings()
                .replaceFirst("25", "5678"));
        applicationSetting =
                    _applicationSettingService.updateApplicationSetting(applicationSetting);
        ApplicationSetting appSetting = _applicationSettingDAO.getApplicationSetting(1);
        assertFalse(appSetting.getSettings().indexOf("5678") < 0);
        appSetting.setSettings(applicationSetting.getSettings().replaceFirst("5678", "25"));
        _applicationSettingService.updateApplicationSetting(appSetting);
    }

    public void testUpdateApplicationSettingWithNull() {

        try {
            _applicationSettingService.updateApplicationSetting(null);
        } catch (ConfigurationException ce) {
            assertEquals(ConfigurationEC.EC_NULL_APPLICATION_SETTING, ce.getErrorCode());
        }
    }

    public void testUpdateApplicationSettingWithInvalidXML() {

        try {
            ApplicationSetting applicationSetting = _applicationSettingService.getGlobalSetting();
            applicationSetting.setSettings(applicationSetting.getSettings()
                              .replaceFirst("xmlns=\"http://eig.mckesson.com/xsd/2009/01\"", ""));
            _applicationSettingService.updateApplicationSetting(applicationSetting);
        } catch (ConfigurationException ce) {
            assertEquals(ConfigurationEC.EC_INVALID_XML_DOCUMENT, ce.getErrorCode());
        }
    }

    public void testUpdateApplicationSettingWithInvalidApplicationId() {

        try {

            ApplicationSetting applicationSetting =
                _applicationSettingDAO.getApplicationSetting(1);
            applicationSetting.setAppId(-1);
            _applicationSettingService.updateApplicationSetting(applicationSetting);
        } catch (ConfigurationException ce) {
            assertEquals(ConfigurationEC.INVALID_APPLICATION_ID, ce.getErrorCode());
        }
    }

    public void testUpdateApplicationSettingWithNullApplicationName() {

        try {

            ApplicationSetting applicationSetting = _applicationSettingDAO.getApplicationSetting(1);
            applicationSetting.setAppName(null);
            _applicationSettingService.updateApplicationSetting(applicationSetting);
        } catch (ConfigurationException ce) {
            assertEquals(ConfigurationEC.EC_NULL_APPLICATION_NAME, ce.getErrorCode());
        }
    }

    public void testGetUpdateApplicationSettingsWithNullSettings() {

        try {

            ApplicationSetting applicationSetting =
                _applicationSettingDAO.getApplicationSetting(1);
            applicationSetting.setSettings(null);
            applicationSetting =
                        _applicationSettingService.updateApplicationSetting(applicationSetting);
        } catch (ConfigurationException ce) {
            assertEquals(ConfigurationEC.EC_NULL_APPLICATION_SETTING_DATA, ce.getErrorCode());
        }
    }

    public void testGetUpdateApplicationSettingsWithEmptySettings() {

        try {

            ApplicationSetting applicationSetting =
                _applicationSettingDAO.getApplicationSetting(1);
            applicationSetting.setSettings("");
            applicationSetting =
                        _applicationSettingService.updateApplicationSetting(applicationSetting);
        } catch (ConfigurationException ce) {
            assertEquals(ConfigurationEC.EC_INVALID_APPLICATION_SETTING_DATA, ce.getErrorCode());
        }
    }

    public void testGetUpdateApplicationSettingsWithException() {

        try {

            WsSession.initializeSession();
            WsSession.setSessionUserId(null);
            ApplicationSetting applicationSetting = _applicationSettingDAO.getApplicationSetting(1);
            applicationSetting =
                        _applicationSettingService.updateApplicationSetting(applicationSetting);
            assertTrue(false);
        } catch (ConfigurationException ce) {
            assertTrue(true);
        }
    }

    public void testUpdateGlobalSettingsWithException() {

        try {

            WsSession.initializeSession();
            WsSession.setSessionUserId(null);
            ApplicationSetting applicationSetting =
                        _applicationSettingService.getGlobalSetting();
            applicationSetting =
                        _applicationSettingService.updateGlobalSetting(applicationSetting);
            assertTrue(false);
        } catch (ConfigurationException ce) {
            assertTrue(true);
        }
    }

    public void testGetGlobalSettingInfo() {

        HashMap<String, HashMap<String, String>> globalInfo
                            = _applicationSettingService.getGlobalSettingInfo();
        assertNotNull(globalInfo);
    }

    public void testGetApplicationSetting() {

        HashMap<String, HashMap<String, String>> applicationInfo
                                    = _applicationSettingService.getApplicationSetting(1);
        assertNotNull(applicationInfo);
    }

    public void testGetApplicationSettingWithUnAvailableApplicationID() {

        HashMap<String, HashMap<String, String>> applicationInfo
                        = _applicationSettingService.getApplicationSetting(Long.MAX_VALUE);
        assertNull(applicationInfo);
    }

    public void testAllApplicationSettings() {

        HashMap<String, HashMap<String, HashMap<String, String>>> applicationInfo
                                    = _applicationSettingService.getAllApplicationSettings();
        assertNotNull(applicationInfo);
    }
}
