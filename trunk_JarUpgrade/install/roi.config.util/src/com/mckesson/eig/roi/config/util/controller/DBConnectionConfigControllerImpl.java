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
import org.dom4j.DocumentException;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.api.TabSource;


/**
 *
 * @author OFS
 * @date   May 05, 2009
 * @since  HPF 16.0 [ROI]; Feb 13, 2013
 */
public class DBConnectionConfigControllerImpl
extends ConfigurationControllerImpl {

    private static final Logger LOG = Logger.getLogger(DBConnectionConfigControllerImpl.class);

    private String _connectionPath = "//datasources/local-tx-datasource/connection-url";
    private String _connectionURL1 = "jdbc:jtds:sqlserver://";
    private String _connectionURL2 = ";DatabaseName=cabinet";
    private String _connectionURL3 = "/cabinet";
    private String _hpfServer = "//configuration/appSettings/add[@key='HpfServer']";
    private String _activeMqUrl = "//beans/bean/property[@name='url']";
    private String _faxDBServer = "//configuration/appSettings/add[@key='dbServer']";

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

            if (!ConfigProps.ROI_SERVER_INSTALLED) {
                configParams = readConfigParams(ConfigProps.OUTPUT_DB_CONNECTION_CONFIG_FILE);
            } else {
                configParams = readConfigParams(ConfigProps.DB_CONNECTION_CONFIG_FILE);
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

        String hpfwURL = null;

        try {

            ConfigurationValidatorImpl validator = new ConfigurationValidatorImpl();
            validator.validateDBConnectionParams(configParams);

            String connectionURL = _connectionURL1 + configParams.get(ConfigProps.IP_ADDRESS)
                                                   + ":"
                                                   + configParams.get(ConfigProps.PORT_NO)
                                                   + _connectionURL2;

            //To update roi db config, activeMQ file
            if (ConfigProps.ROI_SERVER_INSTALLED) {

                Document roiDBDoc = getXMLDocument(ConfigProps.DB_CONNECTION_CONFIG_FILE);
                Document updatedROIDBDoc = updateDBConfig(roiDBDoc, connectionURL);
                updateXMLDocument(updatedROIDBDoc, ConfigProps.DB_CONNECTION_CONFIG_FILE);

                Document aMQDoc = getXMLDocument(ConfigProps.ACTIVEMQ_CONFIG_FILE);
                updateAppSettingsValue(_activeMqUrl, aMQDoc, connectionURL);
                updateXMLDocument(aMQDoc, ConfigProps.ACTIVEMQ_CONFIG_FILE);

                Properties props = new Properties();
                props.load(new FileReader(ConfigProps.HPFW_DB_CONNECTION_CONFIG_FILE));

                hpfwURL = _connectionURL1 + configParams.get(ConfigProps.IP_ADDRESS)
                						  + _connectionURL3;
                props.setProperty("hibernate.connection.url", hpfwURL);

                writeMPFWFileConfigFile(props, ConfigProps.HPFW_DB_CONNECTION_CONFIG_FILE);
            }

            //To update output db config, activeMQ file
            if (ConfigProps.OUTPUT_SERVER_INSTALLED) {

                Document opDBDoc = getXMLDocument(ConfigProps.OUTPUT_DB_CONNECTION_CONFIG_FILE);
                Document updatedOpDBDoc = updateDBConfig(opDBDoc, connectionURL);
                updateXMLDocument(updatedOpDBDoc, ConfigProps.OUTPUT_DB_CONNECTION_CONFIG_FILE);

                Document aMQDoc = getXMLDocument(ConfigProps.OUTPUT_ACTIVEMQ_CONFIG_FILE);
                updateAppSettingsValue(_activeMqUrl, aMQDoc, connectionURL);
                updateXMLDocument(aMQDoc, ConfigProps.OUTPUT_ACTIVEMQ_CONFIG_FILE);

                Properties props = new Properties();
                props.load(new FileReader(ConfigProps.OUTPUT_HPFW_DB_CONNECTION_CONFIG_FILE));
                hpfwURL = _connectionURL1 + configParams.get(ConfigProps.IP_ADDRESS)
						                  + _connectionURL3;
                props.setProperty("hibernate.connection.url", hpfwURL);
                writeMPFWFileConfigFile(props, ConfigProps.OUTPUT_HPFW_DB_CONNECTION_CONFIG_FILE);

            }

          //To update db config in the fax service
            if (ConfigProps.FAX_SERVER_INSTALLED) {

            	 Document doc = getXMLDocument(ConfigProps.FAX_SERVICE_CONFIG_FILE);
                 updateAppSettingsValue(_faxDBServer, doc, configParams.get(ConfigProps.IP_ADDRESS));
                 updateXMLDocument(doc, ConfigProps.FAX_SERVICE_CONFIG_FILE);
            }

            //To update the hpfserver ip address
            if (ConfigProps.ROI_CLIENT_INSTALLER_INSTALLED) {

                Document doc = getXMLDocument(ConfigProps.CLIENT_CONFIG_FILE);
                updateAppSettingsValue(_hpfServer, doc, configParams.get(ConfigProps.IP_ADDRESS));
                updateXMLDocument(doc, ConfigProps.CLIENT_CONFIG_FILE);
            }

            LOG.debug(logSM + "<<End:");

            new LogConfigControllerImpl().save(params);

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
                                 .toString();

    }

    private Document updateDBConfig(Document doc,String url) {
        setElementValue(doc, _connectionPath, url);
        return doc;
    }

    private Map<String, String> readConfigParams(String dbConfigFile) throws DocumentException {

        Map<String, String> configParams = new HashMap<String, String>();
        Document document = getXMLDocument(dbConfigFile);

        String connectionURL = getElementValue(document, _connectionPath);
        String connectionValue = connectionURL.substring(connectionURL.indexOf("//") + 2,
                                                         connectionURL.indexOf(";"));
        String[] values = connectionValue.split(":");
        String serverName = setDBServerName(values[0]);
        configParams.put(ConfigProps.IP_ADDRESS, serverName);
        configParams.put(ConfigProps.PORT_NO, values[1]);

        return configParams;
    }


	@Override
	protected TabSource getTabSource() {
		return TabSource.DB;
	}

}
