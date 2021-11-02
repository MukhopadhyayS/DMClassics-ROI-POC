/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.roi.onecontent.service;

import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import com.mckesson.eig.roi.onecontent.security.rest.webservice.proxy.SecurityWebserviceInterface;
import com.mckesson.eig.roi.onecontent.service.model.MPFUserSession;
import com.mckesson.eig.roi.webservice.util.HeaderUtils;
import com.mckesson.eig.roi.webservice.util.rest.RestWebClientPool;
import com.mckesson.eig.roi.webservice.util.rest.security.SecurityConstants;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.security.AuthenticationStrategy;
import com.mckesson.eig.wsfw.session.CxfWsSession;

/**
 * Authentication strategy used to authenticate the ROI with the OneContent
 * 
 * @author ais
 *
 */
public class OneContentAuthenticationStrategy 
implements AuthenticationStrategy {
    
    public static final String AUTHENTICATED_MPF_USER_SESSION = "mpf.user.session"; 
    
    private List<?> _providers;
    private WebClient client;
    
    public void setProviders(List<?> providers) {
        _providers = providers;
    }

    /* (non-Javadoc)
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy#authenticate(java.lang.String, java.lang.String)
     */
    @Override
    public AuthenticatedResult authenticate(String user, String password) {
        

        AuthenticatedResult result = new AuthenticatedResult();
        try {
        
            // to retain user information from during business service calls
            MPFUserSession sessionData = 
                    (MPFUserSession) CxfWsSession.getSessionData(OneContentAuthenticationStrategy.AUTHENTICATED_MPF_USER_SESSION);
            
            if (sessionData == null) {
                
                SecurityWebserviceInterface serviceProxy = getSecurityServiceProxy(user, password);
                MPFUserSession mpfUserSession = serviceProxy.login();
                CxfWsSession.setSessionData(OneContentAuthenticationStrategy.AUTHENTICATED_MPF_USER_SESSION, mpfUserSession);
                
                result.setState(AuthenticatedResult.AUTHENTICATED);
            }

        } catch (Exception e) {
            result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
        }

        return result;
    }

    /**
     * using the given username and password, gets the service proxy
     * and initializes the service header with the required details
     * 
     * @param user
     * @param password
     * @return
     */
    private SecurityWebserviceInterface getSecurityServiceProxy(String user, String password) {
        
        String coreserverUrl = RestWebClientPool.getCoreServerUrl(null);
        
        if (client == null) {
            client = WebClient.create(coreserverUrl, _providers);
        }
        
        SecurityWebserviceInterface serviceProxy = JAXRSClientFactory.fromClient(client, SecurityWebserviceInterface.class);
        
        String authorization = HeaderUtils.getAuthorizationString(user, password);
        HeaderUtils.setClientHeaders(serviceProxy);
        
        String mpfToken = (String) CxfWsSession.getSessionData(CxfWsSession.TICKET);
        WebClient.client(serviceProxy).header("Cookie", "JSESSIONID=" + CxfWsSession.getSessionId() + "; MPFTOKEN=" + mpfToken);
        WebClient.client(serviceProxy).header(SecurityConstants.AUTHORIZATION, authorization);
        return serviceProxy;
    }

    /* (non-Javadoc)
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy#login(java.lang.String, java.lang.String)
     */
    @Override
    public AuthenticatedResult login(String user, String password) {
        return authenticate(user, password);
    }

    /* (non-Javadoc)
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy#authenticate(java.lang.String)
     */
    @Override
    public void authenticate(String userName) {
        // TODO Auto-generated method stub
    }

}
