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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.api.TabSource;


/**
 *
 * @author OFS
 * @date   Mar 18, 2009
 * @since  HPF 16.0 [ROI]; FEB 13, 2013
 */
public class ClientInstallerConfigControllerImpl
extends ConfigurationControllerImpl {

    private static final Logger LOG = Logger.getLogger(ClientInstallerConfigControllerImpl.class);

    private String _http = "http://";
    private String _https = "https://";
    private String _roiServices = "/roi/services/";
    private String _alertServerPath = "//configuration/appSettings/add[@key='AlertServer']";
    //private String _auditServerPath = "//configuration/appSettings/add[@key='AuditServer']";
    private String _hpfServices = "/portal/eig/iservices/";
    private String _contentServiceUrlPath = "//configuration/appSettings/add[@key='ContentServiceUrl']";
    private String _portXPath = "//Server/Service/Connector/@port";
    private String serverIp;

    private String _servicesPath =
        "//configuration/applicationSettings/McK.EIG.ROI.Client.Properties.Settings"
            + "/setting/value";

    private String _inUseServices = "/inuse/services/";

    private String _auditServices = "/audit/services/";

    private String _uploadServletUrlPath =
        "//configuration/appSettings/add[@key='FileUploadServletUrl']";

    private String _attachUploadServletUrlPath =
        "//configuration/appSettings/add[@key='AttachmentUploadServletUrl']";

    private String _downloadServletUrlPath =
        "//configuration/appSettings/add[@key='FileDownloadServletUrl']";

    private String _reportServletUrlPath =
        "//configuration/appSettings/add[@key='ReportServletURL']";

    private String _helpUrlPath = "//configuration/appSettings/add[@key='HelpUrl']";

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController
     * #loadClientConfigParams()
     */
	@Override
	public Map<String, String> load() {

        final String logSM = "loadClientConfigParams()";
        LOG.debug(logSM + ">>Start:");

        try {

            Map<String, String> configParams = new HashMap<String, String>();
            Document document = getXMLDocument(ConfigProps.CLIENT_CONFIG_FILE);
            String roiServiceAddress = getMatchedElementValue(document,
                                                              _servicesPath,
                                                              _roiServices);

            String protocol = "http";
            if(roiServiceAddress.indexOf(_http)!= -1) {

            	protocol = "http";
                serverIp = roiServiceAddress.substring(roiServiceAddress.indexOf(_http)
                                                             + ConfigProps.IP_POSITION,
                                                         roiServiceAddress.indexOf(_roiServices));
           } else if(roiServiceAddress.indexOf(_https) != -1) {

        	   	protocol = "https";
                serverIp = roiServiceAddress.substring(roiServiceAddress.indexOf(_https)
                                                             + ConfigProps.HTTPS_IP_POSITION,
                                                         roiServiceAddress.indexOf(_roiServices));
           }

            String[] values = serverIp.split(":");
            String serverName = setServerName(values[0]);
            configParams.put(ConfigProps.IP_ADDRESS, serverName);
            configParams.put(ConfigProps.PROTOCOL, protocol);
            
            document = getXMLDocument(ConfigProps.JBOSS_SERVER_SERVER_XML_FILE);

            // If SSLEnabled is true then that specific Connector Element port
            // is fetched otherwise the default HTTP port is fetched
            
//            configParams.put(ConfigProps.PORT_NO,
//            		getConditionedAttributeValue(document, _portXPath));
            // Modified to show the default client port number as 443
            configParams.put(ConfigProps.PORT_NO, ConfigProps.CLIENT_ROI_PORT_NO);

            LOG.debug(logSM + "<<End: Existing Client Config Details: "
                      + configDetails(configParams));

            return configParams;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.
                                              getMessage("unable.read.client.installer.config"),
                                              e.getCause());
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController
     * #saveClientConfigParams(java.util.Map)
     */
	@Override
	public void save(Object params) {

        final String logSM = "saveClientConfigParams(configParams)";
        @SuppressWarnings("unchecked") //Not supported by 3rd party API
        Map<String, String> configParams = (Map<String, String>) params;
        LOG.debug(logSM + ">>Start: New Client Config Details: " + configDetails(configParams));

        try {

            ConfigurationValidatorImpl validator = new ConfigurationValidatorImpl();
            validator.validateDBConnectionParams(configParams);

            Document document = getXMLDocument(ConfigProps.CLIENT_CONFIG_FILE);

            updateServiceDetails(_roiServices, configParams, document);
            updateServiceDetails(_inUseServices, configParams, document);
            updateServiceDetails(_auditServices, configParams, document);
            updateServiceDetails(_hpfServices, configParams, document);

            // For updating alert server details
            updateAppSettingsValue(_alertServerPath,
                                   document,
                                   configParams.get(ConfigProps.IP_ADDRESS));

           //For updating file upload, File download, Report servlet urls
            updateServletUrlDetails(document, configParams, _uploadServletUrlPath);
            updateServletUrlDetails(document, configParams, _downloadServletUrlPath);
            updateServletUrlDetails(document, configParams, _reportServletUrlPath);
            updateServletUrlDetails(document, configParams, _attachUploadServletUrlPath);

            // updates HPFW configuration
            updateContentUrlDetails(document, configParams, _contentServiceUrlPath);

            //For updating HelpUrl
            updateServletUrlDetails(document, configParams, _helpUrlPath);

            updateXMLDocument(document, ConfigProps.CLIENT_CONFIG_FILE);

            LOG.debug(logSM + "<<End:");

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.
                                              getMessage("unable.save.client.installer.config"),
                                          e.getCause());
        }
    }


	@Override
	protected TabSource getTabSource() {
		return TabSource.CLIENT_ROI;
	}
}
