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

package com.mckesson.eig.wsfw.test;


import com.mckesson.eig.iws.security.Ticket;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.exception.InvalidUserException;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.security.AuthenticationStrategy;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * This is stubbed out authentication strategy for use in the unit tests. It is
 * just returns hardcoded responses based on the userid passed in.
 * 
 */
public class MockAuthenticationStrategy implements AuthenticationStrategy {

    public static final int INACTIVITY_PERIOD = 25;

    /**
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy#
     *      authenticate(java.lang.String, java.lang.String, int)
     */
    public AuthenticatedResult authenticate(String user, String password) {
        if (StringUtilities.equalsIgnoreCase("system", user)) {
            return getSystemUserResult(password);
        }

        // If the user is not recognized, just throw an exception.
        throw new InvalidUserException(
                "Authentication Failed. Invalid User Credentials",
                ClientErrorCodes.SYSTEM_COULD_NOT_LOG_YOU_ON);
    }

    /**
     * Nothing special here.  Just do a normal authentication.
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy
     *      #login(java.lang.String, java.lang.String, int)
     */
    public AuthenticatedResult login(String user, String password) {
        return authenticate(user, password);
    }

    /**
     * Authenticate the user without a password check.  Used for 
     * services that do not require a password.
     * 
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy
     *      #authenticate(java.lang.String)
     */
    public void authenticate(String userName) {
        if (StringUtilities.equalsIgnoreCase("system", userName)) {
            WsSession.setSessionData(WsSession.USER_NAME, userName);
            return;
        }

        // If the user is not recognized, just throw an exception.
        throw new InvalidUserException(
                "Authentication Failed. User not found",
                ClientErrorCodes.SYSTEM_COULD_NOT_LOG_YOU_ON);
    }

    /**
     * Returns a canned response for the system user.
     * 
     * @return
     */
    private AuthenticatedResult getSystemUserResult(String clearTextPwd) {
        AuthenticatedResult result = new AuthenticatedResult();
        if (StringUtilities.equalsIgnoreCase(clearTextPwd, "admin")) {
            result.setTicket(Ticket.getTicket("system"));
            result.setState(AuthenticatedResult.AUTHENTICATED);
            WsSession.setSessionData(WsSession.TICKET, result.getTicket());         
        } else {
            result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
        }
        return result;
    }
}
