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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;

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
public class HPFWDatabaseConfigurationControllerImpl
extends ConfigurationControllerImpl {

    private static final Logger LOG = Logger.getLogger(HPFWDatabaseConfigurationControllerImpl.class);

    private String _userNamePath = "//datasources/local-tx-datasource/user-name";
    private String _passwordPath = "//datasources/local-tx-datasource/password";
    private String _connectionPath = "//datasources/local-tx-datasource/connection-url";

   // private String _ipAddressPath = "//context//object//string[1]";
    private String _userId = "//context//object//string[2]";
    private String _password = "//context//object//string[3]";
    
    private static final String OUTPUT_SERVER_USER_KEY = "cws.server.user";
    private static final String OUTPUT_SERVER_PASSWORD_KEY = "cws.server.password";

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

          //To update roi db config, activeMQ file
            if (ConfigProps.ROI_SERVER_INSTALLED) {

            	loadHPFWDatabaseProperty(configParams, ConfigProps.HPFW_DB_CONNECTION_CONFIG_FILE);
                Document roiDBDoc = getXMLDocument(ConfigProps.HPFW_SMB_CONFIG_FILE);
                loadSmbProperty(roiDBDoc, configParams);

            } else {

            	loadHPFWDatabaseProperty(configParams, ConfigProps.OUTPUT_HPFW_DB_CONNECTION_CONFIG_FILE);
            	Document roiDBDoc = getXMLDocument(ConfigProps.OUTPUT_HPFW_SMB_CONFIG_FILE);
            	loadSmbProperty(roiDBDoc, configParams);
            }


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
            validator.validateConfigParams(configParams);

            //To update roi db config, activeMQ file
            if (ConfigProps.ROI_SERVER_INSTALLED) {

                modifyHPFWProperty(configParams, ConfigProps.HPFW_DB_CONNECTION_CONFIG_FILE);
                Document roiDBDoc = getXMLDocument(ConfigProps.HPFW_SMB_CONFIG_FILE);
                Document updatedROIDBDoc = updateCredentialConfig(roiDBDoc, configParams);
                updateXMLDocument(updatedROIDBDoc, ConfigProps.HPFW_SMB_CONFIG_FILE);

            }

            if (ConfigProps.OUTPUT_SERVER_INSTALLED) {

            	modifyHPFWProperty(configParams, ConfigProps.OUTPUT_HPFW_DB_CONNECTION_CONFIG_FILE);
            	Document roiDBDoc = getXMLDocument(ConfigProps.OUTPUT_HPFW_SMB_CONFIG_FILE);
            	Document updatedROIDBDoc = updateCredentialConfig(roiDBDoc, configParams);
            	updateXMLDocument(updatedROIDBDoc, ConfigProps.OUTPUT_HPFW_SMB_CONFIG_FILE);
            	saveOutputSettingsProperties(configParams);
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

    /**
     * This method will update the output settings to output-settings properties
     * @param configParams 
	 */
	private void saveOutputSettingsProperties(Map<String, String> configParams) {
		
		File file = new File(ConfigProps.OUTPUT_JBOSS_HOME,
				ConfigProps.SERVER_EIG_OUTPUT_SETTINGS_FILE);
		saveProperties(file, OUTPUT_SERVER_USER_KEY, configParams.get(ConfigProps.USER_ID));
		saveProperties(file, OUTPUT_SERVER_PASSWORD_KEY, configParams.get(ConfigProps.PASSWORD));
		
	}

	private Document updateCredentialConfig(Document doc, Map<String, String> configParams) {

    	setSMBElementValue(doc, _userId, configParams.get(ConfigProps.SMB_USER_ID));
    	setSMBElementValue(doc, _password, configParams.get(ConfigProps.SMB_PASSWORD));
        return doc;
    }

    //This method updates the HPFW property file
	private void modifyHPFWProperty(Map<String, String> configParams, String file)
			throws IOException, FileNotFoundException, DocumentException {

		Properties props = new Properties();
		props.load(new FileReader(file));

		props.setProperty("hibernate.connection.username", configParams.get(ConfigProps.USER_ID));
		props.setProperty("hibernate.connection.password", configParams.get(ConfigProps.PASSWORD));

		String updatedURL = updateDBName(configParams, props);
		props.setProperty("hibernate.connection.url", updatedURL);
		writeMPFWFileConfigFile(props, file);

	}

	//This method updates the HPFW property file
	private void loadHPFWDatabaseProperty(Map<String, String> configParams, String file)
			throws IOException, FileNotFoundException, DocumentException {

		Properties props = new Properties();
		props.load(new FileReader(file));

		String userId = props.getProperty("hibernate.connection.username");
		if ("$!JDBC_USERID!$".equalsIgnoreCase(userId)) {
			userId = ConfigProps.DEFAULT_MPFW_DB_USER_NAME;
		}
		configParams.put(ConfigProps.USER_ID, userId);

		String password = props.getProperty("hibernate.connection.password");
		if ("$!JDBC_PASSWORD1!$".equalsIgnoreCase(password)) {
			password = ConfigProps.DEFAULT_MPFW_DB_PASSWORD;
		}
		configParams.put(ConfigProps.PASSWORD, password);

		String connectionUrl = props.getProperty("hibernate.connection.url");

		String[] connectionValue = connectionUrl.split("//");
		String[] dbNameDetail = connectionValue[1].split("/");
		configParams.put(ConfigProps.DB_NAME, dbNameDetail[1]);
	}

	private void loadSmbProperty(Document doc, Map<String, String> configParams) {

		String smbUserId = getAppConfigValue(doc, _userId);
		if ("$!SMB_USERID!$".equalsIgnoreCase(smbUserId)) {
			smbUserId = ConfigProps.DEFAULT_DATA;
		}
		configParams.put(ConfigProps.SMB_USER_ID, smbUserId);


		String smbPassword = getAppConfigValue(doc, _password);
		if ("$!SMB_PASSWORD1!$".equalsIgnoreCase(smbPassword)) {
			smbPassword = "";
		}
		configParams.put(ConfigProps.SMB_PASSWORD, smbPassword);
    }


    private String dbConfigDetails(Map<String, String> configParams) {

        return new StringBuffer().append("IP Address : ")
                                 .append(configParams.get(ConfigProps.DB_NAME))
                                 .append(", Port No : ")
                                 .append(configParams.get(ConfigProps.USER_ID))
                                 .append(", Password : ")
                                 .append(configParams.get(ConfigProps.PASSWORD))
                                 .toString();

    }

    //This method constructs the DB name
    private String updateDBName(Map<String, String> configParams, Properties props)
    																	throws DocumentException {

    	String connectionURL = props.getProperty("hibernate.connection.url");
    	String[] connectionValue = connectionURL.split("//");
    	String[] dbNameDetail = connectionValue[1].split("/");
    	String UpdatedUrl = connectionValue[0] + "//" + dbNameDetail[0] + "/"+
    						configParams.get(ConfigProps.DB_NAME);
    	return UpdatedUrl;
    }


	@Override
	protected TabSource getTabSource() {
		return TabSource.HPFWDB;
	}

}
