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

package com.mckesson.eig.wsfw.cxf;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.invoker.Factory;

import com.mckesson.eig.utility.jms.JmsException;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.jms.MessagePoster;

/**
 * @author sahuly
 * @date   Dec 15, 2008
 * 
 *
 * This class is an CXF invoker class that is called when the SOAP message
 * arrives via HTTP transport. It push the XML payload from the SOAP message
 * straight on to the JMS queue that is configured for the service. Note that
 * the service should return an xsd:boolean for this to work.
 * 
 */
public class AsynchronousServiceInvoker extends BusinessServiceInvoker {
    
    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(AsynchronousServiceInvoker.class);
    
    public static final String JMS_CONFIG_NAME = "jmsConfigName";
    
    public AsynchronousServiceInvoker(Object bean) {
        super(bean);
    }

    public AsynchronousServiceInvoker(Factory factory) {
        super(factory);
    }
    
    /**
     * 
     * @see com.mckesson.eig.wsfw.cxf.BusinessServiceInvoker
     *      #invoke(org.apache.cxf.message.Exchange, 
     *              java.lang.Object, 
     *              java.lang.reflect.Method, 
     *              java.util.List)
     */
    @Override
    protected Object invoke(Exchange exchange, 
                            Object serviceObject,
                            Method method, 
                            List<Object> params) {
        
        Object result = sendMessage(exchange);
        return result;
    }

    /**
     * Shove the SOAP XML on to the JMS queue. Note the name of the spring bean
     * for the jms queue configuration must be provided in the jmsConfigName
     * property for the service in the cxf config file.
     *
     * @param exchange -
     *            The JMS Config name and Message object is get from this exchange
     *
     * @return <code>true</code> - if the message is sent <code>false</code>
     *         otherwise
     */
    public boolean sendMessage(Exchange exchange) {

        try {

            String jmsConfigName = getJmsConfigName(exchange);
            MessagePoster messagePoster = 
                (MessagePoster) SpringUtilities.getInstance()
                                                  .getBeanFactory()
                                                  .getBean(jmsConfigName);

            InputStream is     = exchange.getInMessage().getContent(InputStream.class);
            String soapMessage = is.toString();
            exchange.getInMessage().setContent(InputStream.class, is);
            is.close();

            messagePoster.sendTextMessage(soapMessage);
        } catch (Exception e) {

            LOG.error("Exception occurred in sendMessage ",e);
            throw new JmsException(e);
        }
        return true;
    }

    /**
     * Get the JMS config name from the exchange
     * 
     * @param exchange
     * 
     * @return jmsConfigName
     *         name of the JMS bean configured in the spring
     */
    private String getJmsConfigName(Exchange exchange) {
        return (String) ((HashMap< ? , ? >) exchange.get(Endpoint.class.getName()))
                                                    .get(JMS_CONFIG_NAME);
    }
}
