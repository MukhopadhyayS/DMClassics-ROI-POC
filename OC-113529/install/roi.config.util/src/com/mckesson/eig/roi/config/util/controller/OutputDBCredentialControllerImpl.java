/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.config.util.controller;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.api.TabSource;


/**
 *
 * @author OFS
 * @date   May 05, 2009
 * @since  HPF 13.1 [ROI]; Mar 12, 2009
 */
public class OutputDBCredentialControllerImpl
extends ConfigurationControllerImpl {

    private static final Logger LOG = Logger.getLogger(OutputDBCredentialControllerImpl.class);

    private String _userNamePath = "//datasources/local-tx-datasource/user-name";
    private String _passwordPath = "//datasources/local-tx-datasource/password";
    private String _connectionPath = "//datasources/local-tx-datasource/connection-url";
    private String _activeMqUser = "//beans/bean/property[@name='username']";
    private String _activeMqPword = "//beans/bean/property[@name='password']";
    private String _faxDBUser = "//configuration/appSettings/add[@key='roiUser']";
    private String _faxDBPassword = "//configuration/appSettings/add[@key='roiAccess']";

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController#loadConfigParams()
     */
    @Override
	public Map<String, String> load() {

        final String logSM = "loadConfigParams()";
        LOG.debug(logSM + ">>Start:");

        try {

        	Map<String, String> configParams = new HashMap<String, String>();
            Document document = getXMLDocument(ConfigProps.OUTPUT_DB_CONNECTION_CONFIG_FILE);

            configParams.put(ConfigProps.USER_ID, getElementValue(document, _userNamePath));
            configParams.put(ConfigProps.PASSWORD, getElementValue(document, _passwordPath));

            LOG.debug(logSM + "<<End:Existing DB Config Details: " + dbConfigDetails(configParams));
            return configParams;

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.read.db.config"),
                                          e.getCause());
        }
    }


    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController
     * #saveConfigParams(java.lang.Object)
     */
    @Override
	public void save(Object params) {

        final String logSM = "saveDBConnectionConfigParams(configParams)";
        @SuppressWarnings("unchecked") //Not supported by 3rd party API
        Map<String, String> configParams = (Map<String, String>) params;
        LOG.debug(logSM + ">>Start: New DB Config Details: " + dbConfigDetails(configParams));

        try {

            ConfigurationValidatorImpl validator = new ConfigurationValidatorImpl();
            validator.validateDBCredentials(configParams);

            //To update roi db config, activeMQ file
            if (ConfigProps.OUTPUT_SERVER_INSTALLED) {

                Document roiDBDoc = getXMLDocument(ConfigProps.OUTPUT_DB_CONNECTION_CONFIG_FILE);
                Document updatedROIDBDoc = updateDBCredentialConfig(roiDBDoc, configParams);
                updateXMLDocument(updatedROIDBDoc, ConfigProps.OUTPUT_DB_CONNECTION_CONFIG_FILE);

                Document aMQDoc = getXMLDocument(ConfigProps.OUTPUT_ACTIVEMQ_CONFIG_FILE);
                updateAppSettingsValue(_activeMqUser, aMQDoc, configParams.get(ConfigProps.USER_ID));
                updateAppSettingsValue(_activeMqPword, aMQDoc, configParams.get(ConfigProps.PASSWORD));
                updateXMLDocument(aMQDoc, ConfigProps.OUTPUT_ACTIVEMQ_CONFIG_FILE);

                Properties props = new Properties();
                props.load(new FileReader(ConfigProps.OUTPUT_HPFW_DB_CONNECTION_CONFIG_FILE));
                props.setProperty("hibernate.connection.username", configParams.get(ConfigProps.USER_ID));
                props.setProperty("hibernate.connection.password", configParams.get(ConfigProps.PASSWORD));
                writeMPFWFileConfigFile(props, ConfigProps.OUTPUT_HPFW_DB_CONNECTION_CONFIG_FILE);
            }

          //To update db config in the fax service
            if (ConfigProps.FAX_SERVER_INSTALLED) {

            	 Document doc = getXMLDocument(ConfigProps.FAX_SERVICE_CONFIG_FILE);
                 updateAppSettingsValue(_faxDBUser, doc, configParams.get(ConfigProps.USER_ID));
                 updateAppSettingsValue(_faxDBPassword, doc, configParams.get(ConfigProps.PASSWORD));

                 updateXMLDocument(doc, ConfigProps.FAX_SERVICE_CONFIG_FILE);
            }

            LOG.debug(logSM + "<<End:");

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.save.db.config"),
                                          e.getCause());
        }
    }

    private String dbConfigDetails(Map<String, String> configParams) {

        return new StringBuffer().append("IP Address : ")
                                 .append(configParams.get(ConfigProps.IP_ADDRESS))
                                 .append(", Port No : ")
                                 .append(configParams.get(ConfigProps.PORT_NO))
                                 .append(", User ID : ")
                                 .append(configParams.get(ConfigProps.USER_ID))
                                 .append(", Password : ")
                                 .append(configParams.get(ConfigProps.PASSWORD))
                                 .toString();

    }

    private Document updateDBCredentialConfig(Document doc, Map<String, String> configParams) {

        setElementValue(doc, _userNamePath, configParams.get(ConfigProps.USER_ID));
        setElementValue(doc, _passwordPath, configParams.get(ConfigProps.PASSWORD));
        return doc;
    }

   /* private Map<String, String> readConfigParams(String dbConfigFile) throws DocumentException {


        String connectionURL = getElementValue(document, _connectionPath);
        String connectionValue = connectionURL.substring(connectionURL.indexOf("//") + 2,
                                                         connectionURL.indexOf(";"));
        String[] values = connectionValue.split(":");
        String serverName = setDBServerName(values[0]);
        configParams.put(ConfigProps.IP_ADDRESS, serverName);
        configParams.put(ConfigProps.PORT_NO, values[1]);

        return configParams;
    }
*/

	@Override
	protected TabSource getTabSource() {
		return TabSource.OUTPUTDB;
	}

}
