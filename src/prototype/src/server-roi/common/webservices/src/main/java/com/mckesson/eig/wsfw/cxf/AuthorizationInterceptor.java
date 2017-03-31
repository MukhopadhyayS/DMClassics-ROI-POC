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

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.metric.TimedMetric;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.model.authorization.AuthorizationException;
import com.mckesson.eig.wsfw.security.authorization.AuthorizationStrategy;

/**
 * @author N.Shah Ghazni
 * @date   Dec 24, 2008
 */
public class AuthorizationInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final String ACCESS_TO_SERVICE_DENIED       = "Access to service denied.";
    private static final String INVALID_SERVICE_NAME           = "Service Name is invalid";
    private static final String INVALID_OPERATION_NAME         = "Operation Name is invalid";
    private static final String BEAN_ID_AUTHORIZATION_STRATEGY = "AuthorizationStrategy";

    private static final String KEY_WSDL_PORT_QNAME = "javax.xml.ws.wsdl.port";

    /**
     * Gets the logger for this class.
     */
    private static final Log LOG = LogFactory.getLogger(AuthorizationInterceptor.class);

    public AuthorizationInterceptor() {

        super(Phase.PRE_INVOKE);
        getAfter().add(WSS4JInInterceptor.class.getName());
    }

    public void handleMessage(Message message) {

        LOG.debug("**** Enter AuthorizationInvoker ****");

        TimedMetric tm = TimedMetric.start();
        TransactionSignatureManager.addTransactionSignatureOnceForHandlers(message);

        Exchange exchange  = message.getExchange();
        String serviceName = ((QName) exchange.get(KEY_WSDL_PORT_QNAME)).getLocalPart();
        if (StringUtilities.isEmpty(serviceName)) {

            LOG.debug(INVALID_SERVICE_NAME);
            throw new AuthorizationException(INVALID_SERVICE_NAME);
        }

        String operationName = getOperationName(exchange);
        if (StringUtilities.isEmpty(operationName)) {
            LOG.debug(INVALID_OPERATION_NAME);
        }

        /*
         * Create the bean for SecurityAuthorization - Spring config defines 
         * "AuthorizationStrategy" 
         */
        AuthorizationStrategy authorizationStrategy = getAuthorizationServiceProvider();

        // Gets the parameter array, for security purpose.
        List< ? > params = message.getContent(List.class);

        /*
         * Permit or Deny the user access to the service
         */
        if (!authorizationStrategy.authorize(serviceName, operationName, params)) {
            tm.logMetric("Authorize Deny Web Service Operation: " + serviceName
                    + '.' + operationName + "()");
            throw new AuthorizationException(ACCESS_TO_SERVICE_DENIED);
        }
        tm.logMetric("Authorize Allow Web Service Operation: " + serviceName
                                                               + '.' + operationName + "()");

        LOG.debug("**** Leave AuthorizationInvoker ****");
    }

    protected AuthorizationStrategy getAuthorizationServiceProvider() {
        return (AuthorizationStrategy) SpringUtilities.getInstance()
                                                      .getBeanFactory()
                                                      .getBean(BEAN_ID_AUTHORIZATION_STRATEGY);
    }

    /**
     * Helper method used to get the operation name
     * 
     * @param exchange - Instance of <code>Message</code>
     * 
     * @return Operation Name
     */
    protected String getOperationName(Exchange exchange) {

    	return ((OperationInfo) 
    				exchange.get(OperationInfo.class.getName())).getName().getLocalPart(); 
    }
}
