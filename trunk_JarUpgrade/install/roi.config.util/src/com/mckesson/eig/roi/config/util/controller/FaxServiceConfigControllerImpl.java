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
 * @date   May 25, 2009
 * @since  HPF 13.1 [ROI]; Mar 12, 2009
 */
public class FaxServiceConfigControllerImpl
extends ConfigurationControllerImpl {

    private static final Logger LOG = Logger.getLogger(FaxServiceConfigControllerImpl.class);

    private String _faxSerName = "//configuration/appSettings/add[@key='FaxServerName']";
    private String _faxQueueName = "//configuration/appSettings/add[@key='FaxQueueName']";
    private String _faxPassword = "//configuration/appSettings/add[@key='FaxPassword']";
    private String _faxServerType = "//configuration/appSettings/add[@key='FaxServerType']";

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController#loadConfigParams()
     */
    public Map<String, String> load() {

        final String logSM = "loadFaxServiceConfigParams()";
        LOG.debug(logSM + ">>Start:");

        try {
        	
            Document doc = getXMLDocument(ConfigProps.FAX_SERVICE_CONFIG_FILE);
            Map<String, String> configParams = new HashMap<String, String>();

            configParams.put(ConfigProps.FAX_SERVER_NAME, getAppConfigValue(doc, _faxSerName));
            configParams.put(ConfigProps.FAX_QUEUE_NAME, getAppConfigValue(doc, _faxQueueName));
            configParams.put(ConfigProps.FAX_PASSWORD, getAppConfigValue(doc, _faxPassword));
            configParams.put(ConfigProps.FAX_SERVER_TYPE, getAppConfigValue(doc, _faxServerType));

            LOG.debug(logSM + "<<End:Existing FaxService Config Details: "
                              + faxServiceconfigDetails(configParams));

            return configParams;
            
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.read.fax.service.config"),
                                                          e.getCause());

        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController
     * #saveConfigParams(java.lang.Object)
     */
    public void save(Object params) {

        final String logSM = "saveFaxServiceConfigParams(configParams)";
        @SuppressWarnings("unchecked") //Not supported by 3rd party API
        Map<String, String> configParams = (Map<String, String>) params;
        LOG.debug(logSM + ">>Start: New FaxService Config Details: "
                  + faxServiceconfigDetails(configParams));

        try {

            ConfigurationValidatorImpl validator = new ConfigurationValidatorImpl();
            validator.validateFaxServiceParams(configParams);

            Document doc = getXMLDocument(ConfigProps.FAX_SERVICE_CONFIG_FILE);
            updateAppSettingsValue(_faxSerName, doc, configParams.get(ConfigProps.FAX_SERVER_NAME));
            updateAppSettingsValue(_faxQueueName, doc, configParams.get(ConfigProps.FAX_QUEUE_NAME));
            updateAppSettingsValue(_faxPassword, doc, configParams.get(ConfigProps.FAX_PASSWORD));
            updateAppSettingsValue(_faxServerType, doc, configParams.get(ConfigProps.FAX_SERVER_TYPE));

            updateXMLDocument(doc, ConfigProps.FAX_SERVICE_CONFIG_FILE);

            LOG.debug(logSM + "<<End:");
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.save.fax.service.config"),
                                                          e.getCause());
        }
    }

    public String faxServiceconfigDetails(Map<String, String> configParams) {

        return new StringBuffer().append(", Fax ServerName : ")
                                 .append(configParams.get(ConfigProps.FAX_SERVER_NAME))
                                 .append(", Fax QueueName : ")
                                 .append(configParams.get(ConfigProps.FAX_QUEUE_NAME))
                                 .append(", Fax Password : ")
                                 .append(configParams.get(ConfigProps.FAX_PASSWORD))
                                 .append(", Fax Server Type : ")
                                 .append(configParams.get(ConfigProps.FAX_SERVER_TYPE))
                                 .toString();

    }

	@Override
	protected TabSource getTabSource() {
		return TabSource.FAX;
	}
}
