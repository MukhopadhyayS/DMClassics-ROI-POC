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

package com.mckesson.eig.roi.hpf.security.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.dm.core.common.util.sanitize.EncoderUtilities;
import com.mckesson.eig.iws.security.Ticket;
import com.mckesson.eig.utility.log.LogContext;
import com.mckesson.eig.utility.transaction.TransactionId;
import com.mckesson.eig.wsfw.security.service.Authenticator;
import com.mckesson.eig.wsfw.security.servlet.AuthenticationFilter;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * <code>HPFAuthenticationFilter</code> validates the user by checking
 * gathering all the request values and pass on it to the <code>Authenticator</code>
 * with respect to the application.
 *
 * @author OFS
 * @date   April 06, 2009
 * @since  HPF 13.1 [ROI]; April 06, 2009
 */
public class ROIAuthenticationFilter
extends AuthenticationFilter {

    private static final OCLogger LOG = new OCLogger(ROIAuthenticationFilter.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String EIG_WS_SESSION = "eig.wsSession";

    /**
     *
     * <p>The doFilter method of the Filter is called by the container each time
     * a request/response pair is passed through the chain due to a client
     * request for a resource at the end of the chain. and checks whether the
     * user is valid, and also checks the rights to access the webservice or
     *  even the operation in the service.</p>
     *
     * @param req
     *           Instance of servlet request contains the input parameters
     * @param res
     *           Instance of servlet response
     * @param chain
     *           Instance of <code>FilterChain</code>
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException {

        final String logSM = "doFilter(req, res, chain)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        HttpServletRequest httpReq  = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;

        try {
            WsSession session = (WsSession) httpReq.getSession().getAttribute(EIG_WS_SESSION);
            if(session == null) {
                WsSession.initializeSession(httpReq.getSession());
    
                String ticket   = (String) WsSession.getSessionData(httpReq.getSession(), Authenticator.KEY_TICKET);
                String userName = (String) WsSession.getSessionData(httpReq.getSession(), Authenticator.KEY_USERNAME);
    
                if (null != ticket && null != userName && !isTicketValid(ticket, userName)) {
    
                    setSessionData(httpReq, Authenticator.KEY_USERNAME);
                    setSessionData(httpReq, Authenticator.KEY_PASSWORD);
                    setSessionData(httpReq, Authenticator.KEY_TIMESTAMP);
                    WsSession.setSessionData(Authenticator.KEY_PASSWORD, decryptPassword());
                    WsSession.setSessionData(WsSession.APP_ID, httpReq.getParameter("AppId"));
    
                    Authenticator authenticator = getAuthenticator();
                    authenticator.validate();
                }
            }
            String transactionID = req.getParameter(Authenticator.KEY_TRANSACTION_ID);
            if ((transactionID != null) && (transactionID.trim().length() > 0)) {
                LogContext.put("transactionid", new TransactionId(transactionID));
            }

            chain.doFilter(req, res);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
         } catch (Exception e) {

            LOG.error("Authentication Failed for User:" +
                                WsSession.getSessionData(Authenticator.KEY_USERNAME));
            httpRes.sendError(HttpServletResponse.SC_BAD_REQUEST, EncoderUtilities.encodeForHTML(e.getMessage()));
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

            boolean flag = Ticket.isValid(ticket, userName);
            if (flag && DO_DEBUG) {
                LOG.debug("Ticket is valid");
            }
            return flag;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method used to set the parameter values to session
     * in order to use that in <code>Authenticator</code>
     *
     * @param httpReq
     *          Instance of <code>HttpServletRequest</code>, contains the
     *          request information
     *
     * @param key
     *          Key used to store the parameter value in the session
     */
    private void setSessionData(HttpServletRequest httpReq, String key) {
        WsSession.setSessionData(key, httpReq.getParameter(key));
    }
}
