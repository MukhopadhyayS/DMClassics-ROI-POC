/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.hpf.service;


import com.mckesson.eig.iws.security.Ticket;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.security.AuthenticationStrategy;
import com.mckesson.eig.wsfw.security.service.Authenticator;
import com.mckesson.eig.wsfw.session.WsSession;



/**
 * @author OFS
 * @date   Feb 23, 2009
 * @since  HPF 13.1 [ROI]; Feb 17, 2009
 */
public class HPFAuthenticator
implements Authenticator {

    /**
     * Initialize the logger.
     */
    private static final Log LOG = LogFactory.getLogger(HPFAuthenticator.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * Indicates the name of Authentication service locator
     */
    private static final String AUTHENTICATION_SERVICE_LOADER = "HPFAuthenticationServiceLoader";

    /**
     * Name of the application to which this authenticator belongs to.
     */
    private static final String APP_NAME = "roi";

    /**
     * Checks the ticket from request is valid or not. First the authentication
     * is based on ticket id. if the ticket is valid then the user already
     * authenticated, otherwise authentication process starts with the username
     * and password.
     *
     */
    public void validate() {

        try {

            final String logSM = "validate()";
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>Start:");
            }
            String ticket   = (String) WsSession.getSessionData(KEY_TICKET);
            String userName = (String) WsSession.getSessionData(KEY_USERNAME);

            if (isTicketValid(ticket, userName)) {

                if (DO_DEBUG) {

                    LOG.debug("Ticket is valid");
                    LOG.debug(logSM + "<<End:");
                }
                return;
            }

            String password = (String) WsSession.getSessionData(KEY_PASSWORD);
            if (StringUtilities.isEmpty(userName) || StringUtilities.isEmpty(password)) {
                throw new Exception();
            }

            AuthenticatedResult result = doAuthenticate(userName, password);
            if ((result == null) || !result.isAuthenticated()) {
                throw new Exception();
            }

            WsSession.setSessionData(WsSession.AUTHRESULT, result);
            WsSession.setSessionData(WsSession.TICKET, result.getTicket());
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
        } catch (Throwable e) {

            LOG.error("Failed to validate User login" + e);
            throw new ROIException(ROIClientErrorCodes.SYSTEM_COULD_NOT_LOG_YOU_ON);
        }
    }

    /**
     * This method authenticates the user
     *
     * @param user
     *             Indicates the username
     * @param password
     *             Indicates the password
     *
     * @return <code>AuthenticationResult</code> if authentication succeed,
     *              otherwise null
     */
    private AuthenticatedResult doAuthenticate(String user, String password) {


        try {

            final String logSM = "doAuthenticate(user, password)";
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>Start:");
            }
            AuthenticationStrategy authenticationService
            = (AuthenticationStrategy) SpringUtilities.getInstance()
                                                      .getBeanFactory()
                                                      .getBean(AUTHENTICATION_SERVICE_LOADER);

            AuthenticatedResult result = authenticationService.authenticate(user, password);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return result;
        } catch (Throwable e) {

            LOG.error("Failed to Authenticate User:" + user, e);
            throw new ROIException(ROIClientErrorCodes.SYSTEM_COULD_NOT_LOG_YOU_ON);
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

    /**
     * Gets the application id
     *
     */
    public String getAppID() {
        return APP_NAME;
    }
}
