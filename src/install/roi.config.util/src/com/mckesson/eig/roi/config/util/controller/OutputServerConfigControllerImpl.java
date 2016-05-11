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
 * @since  HPF 13.1 [ROI]; Mar 12, 2009
 */
public class OutputServerConfigControllerImpl
extends ConfigurationControllerImpl {

    private static final Logger LOG = Logger.getLogger(OutputServerConfigControllerImpl.class);

    private String _outputSettingsUrl =
        "//configuration/appSettings/add[@key='ConfigureOutputSettingsUrl']";

    private String _monitorOutputJobUrl =
        "//configuration/appSettings/add[@key='MonitorOutputJobUrl']";

    private String _outputServices = "/output/services/";
    private String _portXPath = "//Server/Service/Connector/@port";

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController#loadConfigParams()
     */
    @Override
	public Object load() {

        final String logSM = "loadOutputServerConfigParams()";
        LOG.debug(logSM + ">>Start:");

        Map<String, String> configParams = null;

        try {

            Document document = getXMLDocument(ConfigProps.CLIENT_CONFIG_FILE);
            String configDetails = getAppConfigValue(document, _outputSettingsUrl);

            if (configDetails != null) {
                configParams = getIpAddressAndPortDetails(configDetails);
            }
            
			//CR 381,988 fix
            document = getXMLDocument(ConfigProps.OUTPUT_JBOSS_SERVER_SERVER_XML_FILE);
            
//            configParams.put(ConfigProps.PORT_NO, getConditionedAttributeValue(document, _portXPath));
            // changed to have the default port as 443 
            configParams.put(ConfigProps.PORT_NO, ConfigProps.CLIENT_OUTPUT_PORT_NO);
            
            LOG.debug(logSM + "<<End:");
            return configParams;
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.read.output.server.config"),
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

        final String logSM = "saveOutputServerConfigParams(configParams)";
        @SuppressWarnings("unchecked") //Not supported by 3rd party API
        Map<String, String> configParams = (Map<String, String>) params;
        LOG.debug(logSM + ">>Start:");

        try {

            ConfigurationValidatorImpl validator = new ConfigurationValidatorImpl();
            validator.validateDBConnectionParams(configParams);

            Document document = getXMLDocument(ConfigProps.CLIENT_CONFIG_FILE);
            updateServletUrlDetails(document, configParams, _monitorOutputJobUrl);
            updateServletUrlDetails(document, configParams, _outputSettingsUrl);

            updateServiceDetails(_outputServices, configParams, document);

            updateXMLDocument(document, ConfigProps.CLIENT_CONFIG_FILE);

            LOG.debug(logSM + "<<End:");
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                         .getMessage("unable.save.output.server.config"),
                                                         e.getCause());
        }
    }

	@Override
	protected TabSource getTabSource() {
		return TabSource.CLIENT_OUTPUT;
	}
}
