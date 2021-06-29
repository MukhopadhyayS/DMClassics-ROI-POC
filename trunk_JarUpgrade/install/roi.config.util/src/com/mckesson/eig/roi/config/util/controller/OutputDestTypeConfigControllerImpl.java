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

import org.apache.log4j.Logger;

import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.api.TabSource;
import com.mckesson.eig.roi.config.util.model.EndPointTypeList;


/**
 *
 * @author OFS
 * @date   Mar 12, 2009
 * @since  HPF 13.1 [ROI]; Mar 12, 2009
 */
public class OutputDestTypeConfigControllerImpl
extends ConfigurationControllerImpl {

    private static final Logger LOG = Logger.getLogger(OutputDestTypeConfigControllerImpl.class);

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController#loadConfigParams()
     */
    public EndPointTypeList loadConfigParams() {

        final String logSM = "loadSourceAndDestinationTypes()";
        LOG.debug(logSM + ">>Start:");

        try {

            EndPointTypeList list = getXMLEndpointDAO().readAllEndpointTypeDefs();

            LOG.debug(logSM + "<<End:");
            return list;
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.load.output.source.dest.types"),
                                                         e.getCause());
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController
     * #saveConfigParams(java.lang.Object)
     */
    public void saveConfigParams(Object endPointTypeList) {

        final String logSM = "saveOutputDestinationTypes(endPointTypeList)";
        LOG.debug(logSM + ">>Start:");

        try {

            EndPointTypeList typeList = (EndPointTypeList) endPointTypeList;
            ConfigurationValidatorImpl validator = new ConfigurationValidatorImpl();
            validator.validateSourceTypeSelection(typeList.getSourceTypes());

            getXMLEndpointDAO().writeEndpointTypeData(typeList);

            LOG.debug(logSM + "<<End:");
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.save.output.source.dest.types"),
                                                          e.getCause());
        }
    }
    
	@Override
	protected TabSource getTabSource() {
		// ignore
		return null;
	}

	@Override
	protected void save(Object object) {
		//ignore
	}

	@Override
	protected Object load() {
		// ignore
		return null;
	}

}
