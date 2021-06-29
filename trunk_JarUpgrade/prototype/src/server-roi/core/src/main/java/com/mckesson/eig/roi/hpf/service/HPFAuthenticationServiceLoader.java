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


import com.mckesson.eig.Security;
import com.mckesson.eig.SigninPortType;
import com.mckesson.eig.SigninServiceLocator;
import com.mckesson.eig.iws.security.Ticket;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.security.AuthenticationStrategy;
import com.mckesson.eig.wsfw.session.WsSession;

public class HPFAuthenticationServiceLoader
implements AuthenticationStrategy {

    /**
     * Initialize the logger.
     */
    private static final Log LOG = LogFactory.getLogger(HPFAuthenticationServiceLoader.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private String _url;
    private static final String AUTHENTICATED_USER = "authenticated_roi_user";
    public static final String ENTERPRISE = "E_P_R_S";
    private static final String EIG_USER_SECURITY_RIGHTS = "EIG_USER_SECURITY_RIGHTS";

    public HPFAuthenticationServiceLoader(String url) { _url = url; }
    public String getUrl() { return _url; }
    public void setUrl(String url) { _url = url; }

    /**
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy
     * #authenticate(java.lang.String, java.lang.String)
     */
    public AuthenticatedResult authenticate(String user, String password) {

        try {

            final String logSM = "authenticate(user, password)";
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>Start:");
            }

            SigninServiceLocator loc = new SigninServiceLocator();
            loc.setsigninEndpointAddress(getUrl());

            SigninPortType service = loc.getsignin();
            com.mckesson.eig.User usr = service.signin(user, password);

            AuthenticatedResult result = new AuthenticatedResult();
            if (usr.getValidateCode() != 0) { //check valid user
                result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
                return result;
            }
            //load Security rights in session
            for (Security sec : usr.getSecurities()) {

                if (sec.getFacility().equalsIgnoreCase(ENTERPRISE)) {
                    WsSession.setSessionData(EIG_USER_SECURITY_RIGHTS, sec);
                    break;
                }
            }
            //load user object in session
            WsSession.setSessionData(AUTHENTICATED_USER, usr);
            result.setState(AuthenticatedResult.AUTHENTICATED);
            result.setTicket(Ticket.getTicket(usr.getLoginId()));
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return result;
        } catch (Throwable e) {

            LOG.error("Failed To Authenticate:" + e);
            throw new ROIException(e, ROIClientErrorCodes.SYSTEM_COULD_NOT_LOG_YOU_ON);
        }
    }

    public AuthenticatedResult login(String user, String password) {
        return authenticate(user, password);
    }

    public void authenticate(String userName) {
    }
}
