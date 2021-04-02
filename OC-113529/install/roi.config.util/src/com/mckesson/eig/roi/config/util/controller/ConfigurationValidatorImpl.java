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

import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.model.SourceType;

/**
 * @author OFS
 * @date   May 25, 2009
 * @since  HPF 13.1 [ROI]; Sep 9, 2008
 */
public class ConfigurationValidatorImpl
implements ConfigurationValidator {

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationValidator
     * #validateJbossParams(java.util.Map)
     */
    @Override
	public void validateJbossParams(Map<String, String> configParams) {

        if ((configParams.get(ConfigProps.PORT_NO).length() == 0)
            || (configParams.get(ConfigProps.MIN_MEMORY).length() == 0)
            || (configParams.get(ConfigProps.MAX_MEMORY).length() == 0)
            || (configParams.get(ConfigProps.PERM_SIZE).length() == 0)) {

            throw new ConfigUtilException(ConfigUtilMessages.getMessage("enter.all.fields"));
        }

        validateNumericField(ConfigProps.PORTNUM, configParams.get(ConfigProps.PORT_NO));
        validateNumericField(ConfigProps.MINMEMORY, configParams.get(ConfigProps.MIN_MEMORY));
        validateNumericField(ConfigProps.MAXMEMORY, configParams.get(ConfigProps.MAX_MEMORY));
        validateNumericField(ConfigProps.PERMSIZE, configParams.get(ConfigProps.PERM_SIZE));

    }

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationValidator
     * #validateDBConnectionParams(java.util.Map)
     */
    @Override
	public void validateDBConnectionParams(Map<String, String> configParams) {

        if ((configParams.get(ConfigProps.IP_ADDRESS).length() == 0)
            || (configParams.get(ConfigProps.PORT_NO).length() == 0)
            ) {

            throw new ConfigUtilException(ConfigUtilMessages.getMessage("enter.all.fields"));
        }

        validateNumericField(ConfigProps.PORTNUM, configParams.get(ConfigProps.PORT_NO));
    }

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationValidator
     * #validateDBConnectionParams(java.util.Map)
     */
    @Override
    public void validateDBCredentials(Map<String, String> configParams) {

    	if ((configParams.get(ConfigProps.USER_ID).length() == 0)
    			|| (configParams.get(ConfigProps.PASSWORD).length() == 0)) {

    		throw new ConfigUtilException(ConfigUtilMessages.getMessage("enter.all.fields"));
    	}
    }

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationValidator
     * #validateConfigParams(java.util.Map)
     */
    @Override
	public void validateConfigParams(Map<String, String> configParams) {

        if ((configParams.get(ConfigProps.USER_ID).length() == 0)
            || (configParams.get(ConfigProps.DB_NAME).length() == 0)
            || (configParams.get(ConfigProps.PASSWORD).length() == 0)) {

            throw new ConfigUtilException(ConfigUtilMessages.getMessage("enter.all.fields"));
        }

    }

    /**
    *
    * @see com.mckesson.eig.roi.config.util.controller.ConfigurationValidator
    * #validateConfigParams(java.util.Map)
    */
	public void validateSMBConfigParams(Map<String, String> configParams) {

       if ((configParams.get(ConfigProps.USER_ID).length() == 0)
           || (configParams.get(ConfigProps.IP_ADDRESS).length() == 0)
           || (configParams.get(ConfigProps.PASSWORD).length() == 0)) {

           throw new ConfigUtilException(ConfigUtilMessages.getMessage("enter.all.fields"));
       }

   }

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationValidator
     * #validateFaxServiceParams(java.util.Map)
     */
    @Override
	public void validateFaxServiceParams(Map<String, String> configParams) {

        if ((configParams.get(ConfigProps.FAX_SERVER_NAME).length() == 0)
            || (configParams.get(ConfigProps.FAX_QUEUE_NAME).length() == 0)) {

            throw new ConfigUtilException(ConfigUtilMessages.getMessage("enter.all.fields"));
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationValidator
     * #validateSourceTypeSelection(java.util.List)
     */
    @Override
	public void validateSourceTypeSelection(List<SourceType> sourceTypes) {

        boolean isOneSelected = false;

        for (SourceType srcType : sourceTypes) {
            if (srcType.getEnabled()) {
                isOneSelected = true;
                break;
            }
            isOneSelected = false;
        }
        if (!isOneSelected) {
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("select.one.source.type"));
        }
    }

    public void validateNumericField(String fName, String fValue) {

        if ((!fValue.matches(ConfigProps.NUMERIC_REGEX))) {
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("enter.valid." + fName));
        }
    }

}
