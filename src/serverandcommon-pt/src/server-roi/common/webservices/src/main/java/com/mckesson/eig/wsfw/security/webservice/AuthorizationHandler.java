/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.wsfw.security.webservice;

import javax.xml.soap.SOAPException;

import org.apache.axis.AxisFault;
import org.apache.axis.Handler;
import org.apache.axis.MessageContext;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.handlers.BasicHandler;
import org.springframework.beans.factory.BeanFactory;
import org.w3c.dom.Element;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.axis.FaultHandler;
import com.mckesson.eig.wsfw.security.service.Authorizer;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * <code>AuthorizationHandler</code> checks whether the user can perform
 * the invoked web service operation by verifying the rights of the user.
 *
 * @author N.Shah Ghazni
 * @date Dec 18, 2007
 * @since HECM 1.0
 */
public class AuthorizationHandler extends BasicHandler {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(AuthorizationHandler.class);

    /**
     * Gets the beanfactory that is set as a part of initialization from
     * webcontext.
     */
    private static final BeanFactory BEAN_FACTORY = SpringUtilities.getInstance()
                                                                   .getBeanFactory();

    /**
     * Name used to hold the security soap header
     */
    public static final String SECURITY_HEADER = "SecurityHeader";

    /**
     * Delimiter used to form an authorizer key from application name
     * and authorizer name
     */
    private static final String AUTHORIZE_KEY_DEL = ".";

    /**
     * Instantiates the <code>FaultHandler</code> class.
     */
    private final FaultHandler _faultHandler = new FaultHandler();

    /**
     * This method overrides the invoke method in WSDoAllReceiver
     *
     * @param msgContext
     *            message context.
     * @throws AxisFault
     */
    public void invoke(MessageContext msgContext)
    throws org.apache.axis.AxisFault {

       try {

           LOG.debug("**** Enter AuthorizationHandler ****");
           

           // Find the Service by name the user is attempting to access
           String serviceName = getWebServiceName(msgContext);

           // Find the Operation on the webservice being called
           String operationName = getOperationName(msgContext);

           // Sets the service and operation name in session in order to
           // access in Authorization handler
           WsSession.setSessionData(Authorizer.SERVICE_NAME, serviceName);
           WsSession.setSessionData(Authorizer.OPERATION_NAME, operationName);

           WsSession.setSessionData(Authorizer.SECURITY_HEADER, getSoapHeader(msgContext));

           // Calls the Authorizer
           Authorizer authorizer = getAuthorizer();
           authorizer.validate();

       } catch (Exception e) {
           _faultHandler.createFault(msgContext, e);
       }

       LOG.debug("**** Leave AuthorizationHandler ****");
    }

    /**
     * Gets the Authorizer
     *
     * @return Instance of <code>Authorizer</code> implementation
     */
    protected Authorizer getAuthorizer() {
        return (Authorizer) BEAN_FACTORY.getBean(getAuthorizerKey());
    }

    /**
     * Gets the bean name of <code>Authorizer</code> defined in
     * spring context
     *
     * @return Name of the <code>Authorizer</code>
     */
    protected String getAuthorizerKey() {

        String appName = getAppID();
        if (StringUtilities.hasContent(appName)) {
            return Authorizer.NAME + AUTHORIZE_KEY_DEL + appName;
        }
        throw new RuntimeException("Unable to find the authorrizer for key  'authenticator."
                                   + appName + "'");
    }

    /**
     * Used to return the application name to which <code>Authorizer</code> belongs to.
     * This method must be override in order to get the authenticator for different
     * application
     *
     * @return
     *         The application name to which this authenticator belongs to.
     */
    protected String getAppID() {
        return (String) WsSession.getSessionData(WsSession.APP_ID);
    }

    /**
     * Gets the operation name of the service from the <code>MessageContext</code>
     *
     * @param msgContext
     *             Instance of <code>MessageContext</code>
     *
     * @return Name of the webservice operation.
     */
    private String getOperationName(MessageContext msgContext) {

        OperationDesc operation = msgContext.getOperation();
        String operationName = operation.getName();
        LOG.debug("*** operationName :" + operationName);
        return operationName;
    }

    /**
     * Gets the web service name
     *
     * @param msgContext
     *             Instance of the <code>MessageContext</code>
     * @return Name of the Webservice
     */
    private String getWebServiceName(MessageContext msgContext) {

        Handler service = msgContext.getService();
        String serviceName = service.getName();
        LOG.debug("Service Name : " + serviceName);
        return serviceName;
    }

    /**
     * Gets the Security soap header from the <code>MessageContext</code>
     *
     * @param msgContext
     * @return
     *
     * @throws SOAPException, AxisFault
     */
    private Element getSoapHeader(MessageContext msgContext)
    throws SOAPException, AxisFault {
        return msgContext.getCurrentMessage().getSOAPEnvelope().getHeader();
    }
}
