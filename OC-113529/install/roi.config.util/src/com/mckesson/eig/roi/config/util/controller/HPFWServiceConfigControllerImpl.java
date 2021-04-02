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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.api.TabSource;


/**
 *
 * @author OFS
 * @date   May 14, 2009
 * @since  HPF 13.1 [ROI]; Mar 12, 2009
 */
public class HPFWServiceConfigControllerImpl
extends ConfigurationControllerImpl {

    private static final Logger LOG = Logger.getLogger(HPFWServiceConfigControllerImpl.class);

    private String _http = "http://";
    private String _https = "https://";
    private String _hpfServices = "/portal/eig/iservices/";
    private String _servicesPath =
        "//configuration/applicationSettings/McK.EIG.ROI.Client.Properties.Settings"
            + "/setting/value";

    private String _contentServiceUrlPath =
        "//configuration/appSettings/add[@key='ContentServiceUrl']";

    private String serverIp;

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController#loadConfigParams()
     */
    @Override
	public Map<String, String> load() {

        final String logSM = "loadHPFWConfigParams()";
        LOG.debug(logSM + ">>Start: ");

        try {

            Map<String, String> configParams = new HashMap<String, String>();
            Document document = getXMLDocument(ConfigProps.CLIENT_CONFIG_FILE);
            String hpfwServiceAddress = getMatchedElementValue(document,
                                                               _servicesPath,
                                                               _hpfServices);

            if(hpfwServiceAddress.indexOf(_http) != -1) {

                serverIp = hpfwServiceAddress.substring(hpfwServiceAddress.indexOf(_http)
                                                           + ConfigProps.IP_POSITION,
                                                           hpfwServiceAddress.
                                                           indexOf(_hpfServices));
            }else if(hpfwServiceAddress.indexOf(_https) != -1) {
                serverIp = hpfwServiceAddress.substring(hpfwServiceAddress.indexOf(_https)
                                                             + ConfigProps.HTTPS_IP_POSITION,
                                                             hpfwServiceAddress.
                                                             indexOf(_hpfServices));
           }

            String[] values = serverIp.split(":");
            String serverName = setServerName(values[0]);
            configParams.put(ConfigProps.IP_ADDRESS, serverName);
            configParams.put(ConfigProps.PORT_NO, values[1]);

            LOG.debug(logSM + "<<End:Existing HPFW Config Details:" + configDetails(configParams));
            return configParams;
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.read.hpfw.config"),
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

        final String logSM = "saveHPFWConfigParams(configParams)";
        @SuppressWarnings("unchecked") //Not supported by 3rd party API
        Map<String, String> configParams = (Map<String, String>) params;
        LOG.debug(logSM + ">>Start: New HPFW Config Details: " + configDetails(configParams));

        try {

            ConfigurationValidatorImpl validator = new ConfigurationValidatorImpl();
            validator.validateConfigParams(configParams);

            Document document = getXMLDocument(ConfigProps.CLIENT_CONFIG_FILE);
            updateServiceDetails(_hpfServices, configParams, document);
            updateContentServiceUrl(_contentServiceUrlPath, configParams, document);
            updateXMLDocument(document, ConfigProps.CLIENT_CONFIG_FILE);

           LOG.debug(logSM + "<<End:");
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.save.hpfw.config"),
                                          e.getCause());
        }
    }

    @Override
    public String configDetails(Map<String, String> configParams) {

        return new StringBuffer().append("Port No : ")
                                 .append(configParams.get(ConfigProps.PORT_NO))
                                 .append(", Ip Address : ")
                                 .append(configParams.get(ConfigProps.IP_ADDRESS))
                                 .toString();
    }

    public void updateContentServiceUrl(String url, Map<String, String> config, Document document) {

        //Updating content service url
        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<Element> elements = document.selectNodes(url);
        Element urlElement = elements.get(0);

        String existingUrl = urlElement.attribute("value").getText();
        urlElement.attribute("value").setText(getUpdatedContentServiceUrl(existingUrl, config));

    }

    private String getUpdatedContentServiceUrl(String url, Map<String, String> configParams) {

        String[] values         = url.split("//");
//        String[] ipAndPort      = values[1].split(":");
        String updatedUrl       = values[0] + "//"
                                            + configParams.get(ConfigProps.IP_ADDRESS)
                                            + ":"
                                            + configParams.get(ConfigProps.PORT_NO);

        return updatedUrl;
    }

	@Override
	protected TabSource getTabSource() {
		return TabSource.HPFW;
	}

}
