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

import javax.xml.soap.SOAPException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.transport.http.HTTPConstants;
import org.springframework.beans.factory.BeanFactory;


import com.mckesson.eig.ConfigurationPortType;
import com.mckesson.eig.ConfigurationServiceLocator;
import com.mckesson.eig.iws.security.Ticket;
import com.mckesson.eig.roi.hpf.dao.UserSecurityHibernateDao;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.security.AuthenticationStrategy;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author OFS
 * @date   Mar 16, 2009
 * @since  HPF 13.1 [ROI]; Jun 19, 2008
 */
public class HPFAuthenticationStrategy
implements AuthenticationStrategy {

    private static final BeanFactory BEAN_FACTORY = SpringUtilities.getInstance().getBeanFactory();
    private UserSecurityHibernateDao _userSecurityDao;
    private String _authenticationUrl;
//    private static final int MAX_LOGON_ATTEMPTS = 500;
    protected static final String AUTHENTICATED_ROI_USER = "authenticated_roi_user";

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
        
        // to retain user information from during business service calls
        User user = (User)
                    WsSession.getSessionData(HPFAuthenticationStrategy.AUTHENTICATED_ROI_USER);

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
         // user.validateLogin(password, MAX_LOGON_ATTEMPTS);
        if (!user.isValid()) { // account locked or invalid password
            result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
            return result;
        }

        // get Epn_Enabled and Epn_Prefix values
        Object[] params = _userSecurityDao.getSysParams();
        if (params != null) {

            user.setEpnEnabled("Y".equalsIgnoreCase(params[0].toString()));
            user.setEpnPrefix((String) params[1]);
        }

        result.setState(AuthenticatedResult.AUTHENTICATED);
        result.setTicket(Ticket.getTicket(userId));

        WsSession.setSessionData(AUTHENTICATED_ROI_USER, user);
        WsSession.setSessionData(WsSession.USER_NAME, userId);
        WsSession.setSessionData(WsSession.TICKET, result.getTicket());

        return result;
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
