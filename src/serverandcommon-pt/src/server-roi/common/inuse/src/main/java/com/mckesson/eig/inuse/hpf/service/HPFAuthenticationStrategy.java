/*
 * Copyright 2008-2010 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.inuse.hpf.service;

import javax.xml.soap.SOAPException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.transport.http.HTTPConstants;
import org.springframework.beans.factory.BeanFactory;


import com.mckesson.eig.ConfigurationPortType;
import com.mckesson.eig.ConfigurationServiceLocator;
import com.mckesson.eig.inuse.hpf.dao.UserSecurityHibernateDao;
import com.mckesson.eig.inuse.hpf.model.User;
import com.mckesson.eig.iws.security.Ticket;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.security.AuthenticationStrategy;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 *
 * @author OFS
 * @date   Nov 11, 2008
 * @since  ROI HPF 13.1
 */
public class HPFAuthenticationStrategy
implements AuthenticationStrategy {

    private static final BeanFactory BEAN_FACTORY = SpringUtilities.getInstance().getBeanFactory();

    private UserSecurityHibernateDao _userSecurityDao;

    private String _authenticationUrl;
    protected static final String AUTHENTICATED_INUSE_USER = "authenticated_inuse_user";

    public HPFAuthenticationStrategy() {

        super();
        _userSecurityDao = (UserSecurityHibernateDao)
                            BEAN_FACTORY.getBean(UserSecurityHibernateDao.class.getName());
        }

     /**
     *
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy#authenticate(java.lang.String,
     * java.lang.String)
     */
    public AuthenticatedResult authenticate(String userId, String password) {

        AuthenticatedResult result = new AuthenticatedResult();

        if (!isAuthenticatedWithHPF(userId, password)) {

            result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
            return result;
        }

        // to retain user information from during business service calls
        User user = (User)
                    WsSession.getSessionData(HPFAuthenticationStrategy.AUTHENTICATED_INUSE_USER);

        // user will be null during logon service call
        if (user == null) {

            // Get the HPF user id based on type of authentication.
            int index = userId.indexOf("~");
            String hpfUser = (index > 0) ? userId.substring(index + 1) : userId;

            user = _userSecurityDao.retrieveUser(hpfUser);
            if (user == null) { // if the user id does not exist
                result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
                return result;
            }
        }

        user.setLoginId(user.getLoginId().trim());

        // Since LDAP related authentication is also done through this strategy validate
        // against password is removed.
        //user.validateLogin(password, MAX_LOGON_ATTEMPTS);

        if (!user.isValid()) { // account locked or invalid password
            result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
            return result;
        }

        result.setState(AuthenticatedResult.AUTHENTICATED);
        result.setTicket(Ticket.getTicket(user.getLoginId()));

        WsSession.setSessionData(AUTHENTICATED_INUSE_USER, user);
        WsSession.setSessionData(WsSession.USER_NAME, userId);
        WsSession.setSessionData(WsSession.TICKET, result.getTicket());

        return result;
    }

    private boolean isAuthenticatedWithHPF(String username, String password) {

        try {

            ConfigurationPortType portType = getConfigurationPortType(WsSession.getSessionId());
            System.out.println("Session_ID" + WsSession.getSessionId());
            portType.getConfiguration(username, password);
            return true;
        } catch (Exception e) {
            System.out.println("FAILED");
            return false;
        }
    }

    private ConfigurationPortType getConfigurationPortType(String sessionId)
    throws Exception {

        ConfigurationServiceLocator sl = new ConfigurationServiceLocator();
        sl.setconfigurationEndpointAddress(getAuthenticationUrl());

        ConfigurationPortType portType = sl.getconfiguration();

        Stub stub = (Stub) portType;

        //For Axis to maintain session
        stub.setMaintainSession(true);
        stub._setProperty(HTTPConstants.HEADER_COOKIE, "JSESSIONID=" + sessionId);

        // This trackSession property is used to track session for HPF Server with respect any
        // client application.
        stub.setHeader(createHeaderElement("urn:eig.mckesson.com", "trackSession", "true"));

        return portType;
    }

    /**
     * Creates a new header element
     *
     * @param name
     *          Name of the header element
     *
     * @param value
     *          Value of the header element
     *
     * @return Instance of new <code>SecurityHeaderElement</code>
     *
     * @throws SOAPException
     */
    private static SOAPHeaderElement createHeaderElement(String namespace,
                                                         String name,
                                                         String value)
    throws SOAPException {

        SOAPHeaderElement elem = new SOAPHeaderElement(namespace, name);
        elem.addTextNode(value);
        return elem;
    }

    /**
     *
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy#login(java.lang.String,
     * java.lang.String)
     */
    public AuthenticatedResult login(String userId, String password) {
        return authenticate(userId, password);
    }

    /**
     *
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy#authenticate(java.lang.String)
     */
    public void authenticate(String userName) {
    }

    public String getAuthenticationUrl() { return _authenticationUrl; }
    public void setAuthenticationUrl(String url) { _authenticationUrl = url; }
}
