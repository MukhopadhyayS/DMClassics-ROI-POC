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

package com.mckesson.eig.wsfw.security;

import org.springframework.beans.factory.BeanFactory;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.session.WsSession;
/**
 * This callback class is called by Axis and WSS4J to allow us to validate a
 * username and password are passed in the SOAP Header according to the
 * WS-Security standard.
 * 
 * This callback class is used for any of the Logon services where we want to
 * gather additional information to pass back to the calling application.
 */
public class LogonCallbackHandler extends AbstractBaseCallbackHandler {
    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(LogonCallbackHandler.class);

    /**
     * Gets the beanfactory that is set as a part of initialization from
     * webcontext.
     */
    private static final BeanFactory BEAN_FACTORY = SpringUtilities
            .getInstance().getBeanFactory();

    /**
     * Logon processing pushes the authentication result object into the session
     * so that the service call can use the information to build the userAccount
     * transport object.
     * 
     * @param userId
     *            String
     * @param password
     *            String
     */
    protected void processCallback(String userName, String password) {
        LOG.debug("LogonCallbackHandler processing.");
        AuthenticationStrategy authenticationStrategy = (AuthenticationStrategy)
                BEAN_FACTORY.getBean("AuthenticationStrategy");
        AuthenticatedResult result = authenticationStrategy.login(
                userName, password);

        WsSession.setSessionData(WsSession.AUTHRESULT, result);
    }

}
