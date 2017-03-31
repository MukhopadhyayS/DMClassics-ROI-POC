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


import com.mckesson.eig.iws.security.Ticket;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.exception.NotAuthenticatedException;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * This callback class is called by Axis and WSS4J to allow us to validate a
 * username that are passed in the SOAP Header according to the WS-Security
 * standard.
 * 
 * This callback class is used for all not specialized services and validates
 * the current ticket and with authenticate/reauthenticate user if the ticket is
 * invalid. This also enables the service to be called without having to logon
 * to the system first.
 */
public class PasswordCallbackHandler extends AbstractBaseCallbackHandler {

    /**
     * Gets the logger for this class.
     */
    private static final Log LOG = LogFactory
            .getLogger(PasswordCallbackHandler.class);

    /**
     * Gets the beanfactory that is set as a part of initialization from
     * webcontext.
     */
    private static final BeanFactory BEAN_FACTORY = SpringUtilities
            .getInstance().getBeanFactory();

    /**
     * General service call processing valids the ticket and authentication user
     * and password if the ticket is missing or invalid.
     * 
     * @param userId
     *            String
     * @param password
     *            String
     */
    protected void processCallback(String userName, String password) {
        LOG.debug("PasswordCallbackHandler processing.");
        WsSession.setSessionData(WsSession.PD, password);
        if (!isTicketValid((String) WsSession.getSessionData(WsSession.TICKET),
                userName)) {
            AuthenticationStrategy strategy = (AuthenticationStrategy) 
                    BEAN_FACTORY.getBean("AuthenticationStrategy");
            AuthenticatedResult result = strategy.authenticate(userName,
                    password);
            if (!result.isAuthenticated()) {
                throw new NotAuthenticatedException(
                        "Authentication Failed.  User account is not "
                                + "authenticated (ex: locked, expired, etc)",
                        ClientErrorCodes.SYSTEM_COULD_NOT_LOG_YOU_ON);
            }
        }
    }

    /**
     * Validate the ticket that is stored in the session.
     * 
     * @param ticket
     *            String
     * @param userName
     *            String
     */
    private boolean isTicketValid(String ticket, String userName) {
        try {
            return Ticket.isValid(ticket, userName);
        } catch (Exception e) {
            return false;
        }
    }
}
