/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.wsfw.axis;

import java.lang.reflect.Method;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.utility.jms.JmsConfig;
import com.mckesson.eig.utility.jms.JmsException;
import com.mckesson.eig.utility.jms.JmsService;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * This class is an Axis handler class that is called when the SOAP message
 * arrives via HTTP transport. It shoves the XML payload from the SOAP message
 * straight on to the JMS queue that is configured for the service. Note that
 * the service should return an xsd:boolean for this to work.
 * 
 */
public class AsynchronousServiceHandler extends BusinessServiceHandler {

    /**
     * Loading the Configuration file for Spring and Hibernate.
     */
    private static final BeanFactory BEAN_FACTORY = SpringUtilities
            .getInstance().getBeanFactory();

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory
            .getLogger(AsynchronousServiceHandler.class);

    /**
     * Default Constructor.
     */
    public AsynchronousServiceHandler() {
        super();
    }

    /**
     * Sends the message to a JMS queue instead of directly invoking a method
     * synchronously.
     * 
     * @param messageContext
     *            MessageContext
     * @param method
     *            the target method.
     * @param obj
     *            the target object
     * @param argValues
     *            the method arguments
     * @return result Object after invoking.
     * @throws Exception
     *             if error ocurrs during invocation.
     */
    protected Object execute(MessageContext messageContext, Method method,
            Object obj, Object[] argValues) throws Exception {
        Object result = sendMessage(messageContext);
        return result;
    }

    /**
     * Shove the SOAP XML on to the JMS queue. Note the name of the spring bean
     * for the jms queue configuration must be provided in the jmsConfigName
     * property for the service in the server-config.wsdd file.
     * 
     * @param messageContext -
     *            The MessageContext object to get the current message from the
     *            queue.
     * @return <code>true</code> - if the message is sent <code>false</code>
     *         otherwise
     */
    public boolean sendMessage(MessageContext messageContext) {

        try {
            Message msg = messageContext.getCurrentMessage();
            String jmsConfigName = (String) messageContext
                    .getProperty("jmsConfigName");
            JmsConfig jmsQConfig = (JmsConfig) BEAN_FACTORY
                    .getBean(jmsConfigName);
            JmsService jmsService = new JmsService(jmsQConfig);
            jmsService.sendMessage(msg.getSOAPPartAsString());

        } catch (Exception e) {
            LOG.error(e);
            throw new JmsException(e);
        }
        return true;
    }

}
