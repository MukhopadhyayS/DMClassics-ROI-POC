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

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.mckesson.eig.iws.security.Ticket;
import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.security.AbstractBaseCallbackHandler;
import com.mckesson.eig.wsfw.security.service.Authenticator;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * <code>AuthenticatonHandler</code> validates the ticket from the
 * user. if it is not valid then call the authenticator specific to the
 * application in order to authenticate the user.
 *
 * @author N.Shah Ghazni
 * @date   Dec 17, 2007
 * @since  HECM 1.0
 */
public class AuthenticationHandler extends AbstractBaseCallbackHandler {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(AuthenticationHandler.class);

    /**
     * Delimiter used to form an authenticator key from application name
     * and authenticator name
     */
    private static final String AUTHENTICATE_KEY_DEL = ".";

    /**
     * General service call processing validates the ticket and authentication user
     * and password if the ticket is missing or invalid.
     *
     * @param userId
     *            String
     * @param password
     *            String
     */
    @Override
    protected void processCallback(String userName, String password) {

        LOG.debug("**** Enter AuthenticationHandler ****");
        try {

            if (!isTicketValid((String) WsSession.getSessionData(WsSession.TICKET), userName)) {

                WsSession.setSessionData(Authenticator.KEY_USERNAME, userName);
                WsSession.setSessionData(Authenticator.KEY_PASSWORD, password);

                Authenticator authenticator = getAuthenticator();
                authenticator.validate();
                LOG.debug("**** All was Well AuthenticationHandler ****");
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        LOG.debug("**** Exit AuthenticationHandler ****");
    }

    /**
     * Gets the name of <code>Authenticator</code> defined in
     * spring context
     *
     * @return Name of the <code>Authenticator</code> bean name
     */
    protected String getAuthenticatorKey() {

        String appName = getAppID();
        if (StringUtilities.hasContent(appName)) {
            return Authenticator.NAME + AUTHENTICATE_KEY_DEL + appName;
        }
        throw new RuntimeException("Unable to find the authenticator for key 'authenticator."
                                   + appName + "'");
    }

    /**
     * Used to return the application name to which this authenticator belongs to.
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
     * Gets the Authenticator
     *
     * @return Instance of <code>Authenticator</code> implementation
     */
    protected Authenticator getAuthenticator() {
        try {
            return (Authenticator) SpringUtilities.getInstance()
                                                  .getBeanFactory()
                                                  .getBean(getAuthenticatorKey());
        } catch (NoSuchBeanDefinitionException e) {
            throw new ApplicationException(
                ClientErrorCodes.ERROR_CODE_DESC_MAP.get(ClientErrorCodes.INVALID_APPLICATION_ID),
                ClientErrorCodes.INVALID_APPLICATION_ID);
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
