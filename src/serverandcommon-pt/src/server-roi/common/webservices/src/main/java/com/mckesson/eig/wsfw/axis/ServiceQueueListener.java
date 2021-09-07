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

import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * This is a Message Driven Bean that will listen on an ActiveMQ queue for a
 * soap message. When it receives the SOAP message it will send it through a
 * local copy of axis to process the request on the server.
 * 
 * There are two env-entry parameters that must be specified in the ejb-jar.xml
 * for each copy of the ServiceQueueListener that is deployed:
 * <UL>
 * <LI> axis-server-config = the server-config.wsdd file to use for loading the
 * service definitions </LI>
 * <LI> target-service = the target web service name from the axis WSDD file
 * </LI>
 * <UL>
 * 
 */
public class ServiceQueueListener implements MessageDrivenBean, 
            MessageListener {

    /**
     * LOG - Instance of Logger used to log messages.
     */
    private static final OCLogger LOG = new OCLogger(ServiceQueueListener.class);

    private DirectServiceCaller _caller;

    /**
     * Message driven context for the MDB
     */
    private MessageDrivenContext _context;

    /**
     * Receives messages from the JMS queue for processing via Axis.
     */
    public void onMessage(javax.jms.Message msg) {
        try {
            _caller.callService((TextMessage) msg);
        } catch (Throwable t) {
            _context.setRollbackOnly();
        }
    }

    /**
     * Method invoked by the container to create the Bean.
     */
    public void ejbCreate() {
        String serverConfigFile = null;
        String targetService = null;
        try {
            Context initCtx = new InitialContext();
            Context myEnv = (Context) initCtx.lookup("java:comp/env");
            serverConfigFile = (String) myEnv.lookup("axis-server-config");
            targetService = (String) myEnv.lookup("target-service");
        } catch (NamingException e) {
            LOG.error("Error in MDB configuration: axis-server-config and/or "
                    + "target service not defined in ejb-jar.xml.", e);
            return;
        }
        _caller = new DirectServiceCaller(serverConfigFile, targetService);
    }

    /**
     * Method called by the container to remove this Bean.
     */
    public void ejbRemove() {
    }

    /**
     * @param ctx
     *            the MessageDrivenContext for the bean.
     */
    public void setMessageDrivenContext(MessageDrivenContext ctx) {
        _context = ctx;
    }

}
