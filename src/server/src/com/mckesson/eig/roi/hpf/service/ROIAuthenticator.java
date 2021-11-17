/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SecureStringAccessor;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.security.AuthenticationStrategy;
import com.mckesson.eig.wsfw.security.service.Authenticator;
import com.mckesson.eig.wsfw.session.CxfWsSession;



/**
 * @author OFS
 * @date   April 06, 2009
 * @since  HPF 13.1 [ROI]; April 06, 2009
 */
public class ROIAuthenticator
implements Authenticator {

    /**
     * Initialize the logger.
     */
    private static final OCLogger LOG = new OCLogger(ROIAuthenticator.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

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
            String userName = (String) CxfWsSession.getSessionData(KEY_USERNAME);
            
            SecureStringAccessor securedPassword = (SecureStringAccessor) CxfWsSession
                    .getSessionData(KEY_PASSWORD);     
            StringBuilder builder = new StringBuilder();
            securedPassword.DoHylandAccess((chars, tempStr) -> {
                builder.append(chars);
            });
            String password = builder.toString();

            if (StringUtilities.isEmpty(userName) || StringUtilities.isEmpty(password)) {
                throw new Exception();
            }

            AuthenticatedResult result = doAuthenticate(userName, password);
            if ((result == null) || !result.isAuthenticated()) {
                throw new Exception();
            }

            CxfWsSession.setSessionData(CxfWsSession.AUTHRESULT, result);
            CxfWsSession.setSessionData(CxfWsSession.TICKET, result.getTicket());
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
        } catch (Throwable e) {

            LOG.error("Failed to Authenticate ROI User" + e);
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
                                                      .getBean("AuthenticationStrategy");

            AuthenticatedResult result = authenticationService.authenticate(user, password);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return result;
        } catch (Throwable e) {

            LOG.error("failed to authenticate " + e);
            throw new ROIException(ROIClientErrorCodes.SYSTEM_COULD_NOT_LOG_YOU_ON);
        }
    }

    /**
     *
     * @see com.mckesson.eig.wsfw.security.service.Authenticator#getAppID()
     */
    public String getAppID() {
        return "roi";
    }
}
