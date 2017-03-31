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

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.exception.UsernameTokenException;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * This callback class is called by Axis and WSS4J to allow us to validate a
 * username that are passed in the SOAP Header according to the WS-Security
 * standard.
 */
public class LogoffCallbackHandler extends AbstractBaseCallbackHandler {

    /**
     * Gets the logger for this class.
     */
    private static final Log LOG = LogFactory
            .getLogger(LogoffCallbackHandler.class);

    /**
     * Gets the beanfactory that is set as a part of initialization from
     * webcontext.
     */
    private static final BeanFactory BEAN_FACTORY = SpringUtilities
            .getInstance().getBeanFactory();

    /**
     * This method is called to do the processing of WSPasswordCallback object.
     * 
     * @param pc
     *            WSPasswordCallback.
     */
    protected void handlePasswords(WSPasswordCallback pc) {
        String userId = pc.getIdentifer();
        validateHeader(userId, null);
        processCallback(userId, null);
    }

    /**
     * WSE Security header value validation, over written of the standard
     * validation handler in the AbstractBaseCallback Handler
     * 
     * Only validates that the userid is in the header, challenge services do
     * not have the password available.
     * 
     * @param userId
     *            String
     * @param password
     *            String
     */
    protected void validateHeader(String userName, String password) {
        LOG.debug("Validating the WSE Security Header for Challenge Services");
        if (StringUtilities.isEmpty(userName)) {
            throw new UsernameTokenException("Username is NULL!!",
                    ClientErrorCodes.SECURITY_TOKEN_MISSING_USERNAME);
        }
    }

    /**
     * Puts the User Name from the WSE header into the session.
     * 
     * @param userId
     *            String
     * @param password
     *            String
     */
    protected void processCallback(String userName, String password) {
        LOG.debug("LogoffCallbackHandler processing.");

        AuthenticationStrategy strategy = (AuthenticationStrategy) BEAN_FACTORY
                .getBean("AuthenticationStrategy");
        WsSession.setSessionData(WsSession.USER_NAME, userName);
    }

}
