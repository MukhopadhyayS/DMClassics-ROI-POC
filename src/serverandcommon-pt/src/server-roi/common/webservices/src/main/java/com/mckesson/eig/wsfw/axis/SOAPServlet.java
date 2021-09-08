/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.wsfw.axis;

import org.apache.axis.AxisFault;
import org.apache.axis.transport.http.AxisServlet;

import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * Servlet used for our service which not only creates an instance but also
 * calls a routine whenever an axis fault is called.
 * 
 */
public class SOAPServlet extends AxisServlet {

    /**
     * Gets the Logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(SOAPServlet.class);

    /**
     * Creates a new Servlet instance and logs the message.
     */
    public SOAPServlet() {
        super();
        LOG.debug("SOAPServlet: loading");
    }
    
    /**
     * Routine called whenever an axis fault is caught; where they are logged.
     * 
     * @param fault
     *            what went wrong.
     */
    protected void processAxisFault(AxisFault fault) {
        LOG.error("Exception occurred in processAxisFault ",fault);
        super.processAxisFault(fault);
    }
}
