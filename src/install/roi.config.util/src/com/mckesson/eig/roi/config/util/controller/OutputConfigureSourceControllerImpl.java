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
import com.mckesson.eig.roi.config.util.model.EndPointDefList;


/**
 *
 * @author OFS
 * @date   Mar 12, 2009
 * @since  HPF 13.1 [ROI]; Mar 12, 2009
 */
public class OutputConfigureSourceControllerImpl
extends ConfigurationControllerImpl {

    private static final Logger LOG = Logger.getLogger(OutputConfigureSourceControllerImpl.class);

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController#loadConfigParams()
     */
    public Object load() {

        final String logSM = "loadSourceDefs()";
        LOG.debug(logSM + ">>Start:");

        try {

            EndPointDefList list = getXMLEndpointDAO().readAllEndpointDefs();

            LOG.debug(logSM + "<<End:");
            return list;
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.load.output.dest.defs"),
                                                          e.getCause());
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController
     * #saveConfigParams(java.lang.Object)
     */
    public void save(Object defList) {

        final String logSM = "saveOutputDestinationTypes(endPointTypeList)";
        LOG.debug(logSM + ">>Start:");

        try {

            getXMLEndpointDAO().writeEndpointDefData((EndPointDefList) defList);

            LOG.debug(logSM + "<<End:");
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                         .getMessage("unable.save.output.dest.defs"),
                                                         e.getCause());
        }
    }

	@Override
	protected TabSource getTabSource() {
		// ignore
		return null;
	}
}
