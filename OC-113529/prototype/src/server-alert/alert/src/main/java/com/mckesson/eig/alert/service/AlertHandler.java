/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.alert.service;


import java.io.StringReader;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogEvent;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * This is a POJO that will take a JMS Text message
 *
 */
public class AlertHandler {

    /**
     * LOG - Instance of Logger used to log messages.
     */
    private static final Log LOG = LogFactory.getLogger(AlertHandler.class);

    /**
     * Webservice to log the message to the email appender
     */
    public void alert(String message) {

        String logEventXML = null;
        try {
        	
            LogEvent le = 
            		(LogEvent) LogEvent.getJAXBContext().createUnmarshaller().unmarshal(
            												new StringReader(message));

            AlertHandler.LOG.fatal(le.toLog());
            
        } catch (Throwable t) {
        	
            AlertHandler.LOG.debug("Service " + " failed.  Message received: "
                                        + logEventXML, t);
        }
    }
}
