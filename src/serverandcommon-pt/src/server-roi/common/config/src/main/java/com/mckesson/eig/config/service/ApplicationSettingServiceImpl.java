/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
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
import java.util.List;

import javax.jws.WebService;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.config.audit.ConfigurationAuditManager;
import com.mckesson.eig.config.constants.ConfigurationEC;
import com.mckesson.eig.config.dao.ApplicationSettingDAO;
import com.mckesson.eig.config.exception.ConfigurationException;
import com.mckesson.eig.config.model.ApplicationSetting;
import com.mckesson.eig.config.model.ApplicationSettingList;
import com.mckesson.eig.config.validation.ApplicationSettingValidator;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.XMLUtilities;
import com.mckesson.eig.wsfw.session.WsSession;

@WebService(
name              = "ApplicationSettingPortType_v1_0",
portName          = "ApplicationSettingPort_v1_0",
serviceName       = "ApplicationSettingService_v1_0",
targetNamespace   = "http://eig.mckesson.com/wsdl/applicationsetting-v1",
endpointInterface = "com.mckesson.eig.config.service.ApplicationSettingService")
public class ApplicationSettingServiceImpl
extends AbstractConfigurationService implements ApplicationSettingService {

    private static final String PROPERTY_PATH = "application-settings/item/property";
    private static final String EMAIL_SERVER_SETTING = "email-server-setting";
    private static final String EMAIL_ADDRESS_SETTING = "email-address-setting";
    private static final int GLOBAL_SETTING_ID = 0;

    /**
    * Object represents the Log4JWrapper object.
    */
    private static final Log LOG = LogFactory.getLogger(ApplicationSettingServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
    *
    * @see com.mckesson.eig.config.service.ApplicationSettingService
    *      #getApplicationSettings()
    */

    public ApplicationSettingList getApplicationSettings() {

        final String logSourceMethod = "getApplicationSettings()";
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + ">>Start");
        }

        try {

            ApplicationSettingList appList = getApplicationSettingDAO().getApplicationSettings();
            if (DO_DEBUG) {
                LOG.debug(logSourceMethod + "<<End");
            }
            return appList;
        } catch (Exception e) {

            LOG.error(e);
            throw new ConfigurationException(ConfigurationEC.MSG_OTHER_SERVER_ERROR,
                                             ConfigurationEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
    *
    * @see com.mckesson.eig.config.service.ApplicationSettingService
    *      #updateApplicationSetting(ApplicationSetting)
    */
    public ApplicationSetting updateApplicationSetting(ApplicationSetting applicationSetting) {

        final String logSourceMethod = "updateApplicationSetting()";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            ApplicationSettingValidator.validateApplicationSetting(applicationSetting);
            if (!getApplicationSettingDAO().updateApplicationSetting(applicationSetting)) {
                return applicationSetting;
            }

            applicationSetting = getApplicationSettingDAO().getApplicationSetting(
                                                            applicationSetting.getAppId());
            ConfigurationAuditManager cam = getConfigAuditManager();
            cam.prepareApplicationSettingEvent(0,
                                            AuditEvent.CONTENT_MANAGER,
                                            WsSession.getSessionUserId(),
                                            AuditEvent.UPDATED_CONFIGURATION,
                                            "1",
                                            AuditEvent.SUCCESS);
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + "<<End");
        }
        return applicationSetting;
        } catch (ConfigurationException ce) {
            throw ce;
        } catch (Exception e) {

            LOG.error(e);
            throw new ConfigurationException(ConfigurationEC.MSG_OTHER_SERVER_ERROR,
                                             ConfigurationEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
    *
    * @see com.mckesson.eig.config.service.ApplicationSettingService
    *      #updateGlobalSetting(ApplicationSetting)
    */
    public ApplicationSetting updateGlobalSetting(ApplicationSetting globalSetting) {

        final String logSourceMethod = "updateApplicationSetting()";
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + ">>Start");
        }

        try {

            ApplicationSettingValidator.validateGlobalSetting(globalSetting);
            if (!getApplicationSettingDAO().updateApplicationSetting(globalSetting)) {
                return globalSetting;
            }

            globalSetting = getApplicationSettingDAO().getApplicationSetting(GLOBAL_SETTING_ID);

            ConfigurationAuditManager cam = getConfigAuditManager();
            cam.prepareApplicationSettingEvent(0,
                                               AuditEvent.CONTENT_MANAGER,
                                               WsSession.getSessionUserId(),
                                               AuditEvent.UPDATED_CONFIGURATION,
                                               "1",
                                               AuditEvent.SUCCESS);
        } catch (ConfigurationException ce) {
            throw ce;
        } catch (Exception e) {

            LOG.error(e);
            throw new ConfigurationException(ConfigurationEC.MSG_OTHER_SERVER_ERROR,
                                             ConfigurationEC.EC_OTHER_SERVER_ERROR);
        }
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + "<<End");
        }
        return globalSetting;
    }

    /**
    *
    * @see com.mckesson.eig.config.service.ApplicationSettingService
    *      #getGlobalSetting()
    */
    public ApplicationSetting getGlobalSetting() {

        final String logSourceMethod = "getGlobalConfiguration()";
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + ">>Start");
        }

        try {

            if (DO_DEBUG) {
                LOG.debug(logSourceMethod + "<<End");
            }
            return getApplicationSettingDAO().getApplicationSetting(GLOBAL_SETTING_ID);
        } catch (Exception e) {

            LOG.error(e);
            throw new ConfigurationException(ConfigurationEC.MSG_OTHER_SERVER_ERROR,
                                             ConfigurationEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
    *
    * @see com.mckesson.eig.config.service.ApplicationSettingsService
    * #getAllApplicationSettings()
    */
    public HashMap<String, HashMap<String, HashMap<String, String>>> getAllApplicationSettings() {

        final String logSourceMethod = "getAllApplicationSettings()";
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + ">>Start");
        }

        HashMap<String, HashMap<String, HashMap<String, String>>> allApplicationSettings
            = new HashMap<String, HashMap<String, HashMap<String, String>>>();
        try {

            ApplicationSettingList applicationSettingList =
                                   getApplicationSettingDAO().getApplicationSettings();
            allApplicationSettings = parseApplicationSettingList(applicationSettingList);
        } catch (Exception e) {

            LOG.error(e);
            throw new ConfigurationException(ConfigurationEC.MSG_OTHER_SERVER_ERROR,
                                             ConfigurationEC.EC_OTHER_SERVER_ERROR);
        }

        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + "<<End");
        }
        return allApplicationSettings;
    }

    /**
    *
    * @see com.mckesson.eig.config.service.ApplicationSettingsService
    *      #getApplicationSetting(long)
    */
    public HashMap<String, HashMap<String, String>> getApplicationSetting(long applicationID) {

        final String logSourceMethod = "getApplicationSetting(applicationID)";
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + ">>Start");
        }

        HashMap<String, HashMap<String, String>> applicationSettingValues =
                                    new HashMap<String, HashMap<String, String>>();

        try {

            ApplicationSetting applicationSetting =
                               getApplicationSettingDAO().getApplicationSetting(applicationID);
            applicationSettingValues = parseApplicationSetting(applicationSetting);
        }  catch (ConfigurationException ce) {

            LOG.error(ce);
            throw ce;
        } catch (Exception e) {

            LOG.error(e);
            throw new ConfigurationException(ConfigurationEC.MSG_OTHER_SERVER_ERROR,
                                             ConfigurationEC.EC_OTHER_SERVER_ERROR);
        }

        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + "<<End");
        }
        return applicationSettingValues;
    }

    /**
    *
    * @see com.mckesson.eig.config.service.ApplicationSettingsService
    *      #getGlobalSettingInfo()
    */
    public HashMap<String, HashMap<String, String>> getGlobalSettingInfo() {

        final String logSourceMethod = "getGlobalSettingInfo()";
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + ">>Start");
        }

        HashMap<String, HashMap<String, String>> globalSettingValues
                                        = new HashMap<String, HashMap<String, String>>();
        try {

            ApplicationSetting applcationSetting =
                               getApplicationSettingDAO().getApplicationSetting(GLOBAL_SETTING_ID);
            globalSettingValues = parseApplicationSetting(applcationSetting);
        } catch (Exception e) {

            LOG.error(e);
            throw new ConfigurationException(ConfigurationEC.MSG_OTHER_SERVER_ERROR,
                                             ConfigurationEC.EC_OTHER_SERVER_ERROR);
        }

        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + "<<End");
        }
        return globalSettingValues;
    }

    /**
     * Parse the applicationSettingsList and return them as HashMap
     *
     * @param settingList
     *        applicationSettingList
     *
     * @return applicationSettingsMap
     *
     * @throws Exception
     */
    private HashMap<String, HashMap<String, HashMap<String, String>>>
                parseApplicationSettingList(ApplicationSettingList settingList)
    throws Exception {

        HashMap<String, HashMap<String, HashMap<String, String>>> allApplicationSettings
                        = new HashMap<String, HashMap<String, HashMap<String, String>>>();

        List<ApplicationSetting> applicationSettingList = settingList.getApplicationSettings();
        for (int i = applicationSettingList.size(); --i >= 0;) {

            ApplicationSetting applicationSetting = applicationSettingList.get(i);
            allApplicationSettings.put(applicationSetting.getAppName(),
                                       parseApplicationSetting(applicationSetting));
        }
        return allApplicationSettings;
    }

    /**
     * Parse the applicationSettings and return them as HashMap
     *
     * @param settingList
     *        applicationSettingList
     *
     * @return applicationSettingMap
     *
     * @throws Exception
     */
    private HashMap<String, HashMap<String, String>> parseApplicationSetting(
            ApplicationSetting applicationSetting) throws Exception {

        if (applicationSetting == null) {
            return null;
        }

        HashMap<String, HashMap<String, String>> applicationSettings
                        = new HashMap<String, HashMap<String, String>>();

        applicationSettings.put(EMAIL_SERVER_SETTING,
                                getItemValues(applicationSetting.getSettings(),
                                              EMAIL_SERVER_SETTING,
                                              PROPERTY_PATH));

        applicationSettings.put(EMAIL_ADDRESS_SETTING,
                                getItemValues(applicationSetting.getSettings(),
                                              EMAIL_ADDRESS_SETTING,
                                              PROPERTY_PATH));

        return applicationSettings;
    }

    /**
    * parse the xml document and get the values based on the query provided
    *
    * @param doc
    *            document to be parsed
    *
    * @param typeName
    *            typeName may be email server setting or email address setting
    *
    * @param pathValue
    *            path query to be searched
    *
    * @return propertyValues returns the propertyValues with name as key and
    *         the corresponding value
    */
    public static HashMap<String, String> getItemValues(String xmlElement,
                                                        String typeName,
                                                        String pathValue) {

        if (xmlElement == null) {
            return null;
        }

        HashMap<String, String> propertyValues = new HashMap<String, String>();
        try {

            Document document = XMLUtilities.parse(xmlElement, false);
            NodeList nodeList = XMLUtilities.evaluateXPathAsNodeList(document, pathValue);
            for (int i = nodeList.getLength(); --i >= 0;) {

                Node node = nodeList.item(i);
                NamedNodeMap attributes = node.getParentNode().getAttributes();
                if (typeName.equals(attributes.item(0).getTextContent())) {

                    NamedNodeMap namedNodeMap = node.getAttributes();
                    propertyValues.put(namedNodeMap.item(0).getTextContent(),
                                       namedNodeMap.item(1).getTextContent());
                }
            }
        } catch (Exception e) {

            LOG.error(e);
            throw new ConfigurationException(ConfigurationEC.MSG_OTHER_SERVER_ERROR,
                                             ConfigurationEC.EC_OTHER_SERVER_ERROR);
        }
        return propertyValues;
    }

    /**
    * This method returns the DAO implementation for this business service.
    *
    * @return ApplicationSettingDAO
    */
    private ApplicationSettingDAO getApplicationSettingDAO() {
        return (ApplicationSettingDAO) getDAO(ApplicationSettingDAO.class.getName());
    }

    /**
     * This method returns the ConfigurationAuditManager for this business service.
     * @return configAuditManager
     */
    private ConfigurationAuditManager getConfigAuditManager() {
        return getAuditManager(ConfigurationAuditManager.class.getName());
    }
}

