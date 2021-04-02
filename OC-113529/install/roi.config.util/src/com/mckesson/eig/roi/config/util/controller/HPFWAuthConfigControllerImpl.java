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
 * @date   Mar 12, 2009
 * @since  HPF 16.0 [ROI]; Feb 13, 2013
 */
public class HPFWAuthConfigControllerImpl
extends ConfigurationControllerImpl {

    private static final Logger LOG = Logger.getLogger(HPFWAuthConfigControllerImpl.class);

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController#loadConfigParams()
     */
    @Override
	public Map<String, String> load() {

        final String logSM = "loadOutputConfigParams()";
        LOG.debug(logSM + ">>Start:");

        Map<String, String> configParams = null;

        try {

            Document document = getXMLDocument(ConfigProps.OUTPUT_AUTHENTICATION_CONFIG_FILE);
            String outputAuthDetails = getOutputConfigValue(document);

            if (outputAuthDetails != null) {
                configParams = getIpAddressAndPortDetails(outputAuthDetails);
            }

            LOG.debug(logSM + "<<End:Existing Ouput Authentication Config Details: "
                                  + configDetails(configParams));

            return configParams;
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.read.output.auth.config"),
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

        final String logSM = "saveOutputAuthenticationConfigParams(configParams)";
        @SuppressWarnings("unchecked") //Not supported by 3rd party API
        Map<String, String> configParams = (Map<String, String>) params;
        LOG.debug(logSM + ">>Start:");

        try {

            Document document = getXMLDocument(ConfigProps.OUTPUT_AUTHENTICATION_CONFIG_FILE);
            String outputAuthDetails = getOutputConfigValue(document);
            updateOutputAuthConfigValue(document, configParams);
            updateXMLDocument(document, ConfigProps.OUTPUT_AUTHENTICATION_CONFIG_FILE);

            LOG.debug(logSM + "<<End:");
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.save.output.auth.config"),
                                                          e.getCause());
        }
    }

    public void saveROIConfigParams(Object params) {

        final String logSM = "saveROIAuthenticationConfigParams(configParams)";
        @SuppressWarnings("unchecked") //Not supported by 3rd party API
        Map<String, String> configParams = (Map<String, String>) params;
        LOG.debug(logSM + ">>Start:");

        try {

            ConfigurationValidatorImpl validator = new ConfigurationValidatorImpl();
            validator.validateDBConnectionParams(configParams);

            Document document = getXMLDocument(ConfigProps.ROI_AUTHENTICATION_WSDL_URL_FILE);
            String roiAuthenticationDetails = getROIConfigValue(document);
            updateROIAuthenticationConfigValue(document, configParams);
            updateHPFWAuthConfigValue(document, configParams);
            updateXMLDocument(document, ConfigProps.ROI_AUTHENTICATION_WSDL_URL_FILE);

            LOG.debug(logSM + "<<End:");
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.save.roi.auth.config"),
                                                          e.getCause());
        }
    }

    public void saveInUseConfigParams(Object params) {

        final String logSM = "saveInuseAuthenticationConfigParams(configParams)";
        @SuppressWarnings("unchecked") //Not supported by 3rd party API
        Map<String, String> configParams = (Map<String, String>) params;
        LOG.debug(logSM + ">>Start:");

        try {

            ConfigurationValidatorImpl validator = new ConfigurationValidatorImpl();
            validator.validateDBConnectionParams(configParams);

            Document document = getXMLDocument(ConfigProps.INUSE_AUTHENTICATION_WSDL_URL_FILE);
            String inUseAuthDetails = getInUseConfigValue(document);
            updateInUseAuthenticationConfigValue(document, configParams);
            updateXMLDocument(document, ConfigProps.INUSE_AUTHENTICATION_WSDL_URL_FILE);

            LOG.debug(logSM + "<<End:");
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.save.inuse.auth.config"),
                                                          e.getCause());
        }
    }

	@Override
	protected TabSource getTabSource() {
		return TabSource.HPFW;
	}

  }
