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
package com.mckesson.eig.wsfw.security.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogContext;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.transaction.TransactionId;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.security.encryption.EncryptionHandler;
import com.mckesson.eig.wsfw.security.service.Authenticator;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * <code>AuthenticationFilter</code> validates the user by checking
 * gathering all the request values and pass on it to the <code>Authenticator</code>
 * with respect to the application.
 *
 * @author N.Shah Ghazni
 * @date   Dec 15, 2007
 * @since  HECM 1.0
 */
public class AuthenticationFilter implements Filter {

    /**
     * Name that is used to configure the spring configuration file in web.xml
     */
    private static final String SPRING_CONFIG_FILE = "wsfw.SpringConfigFile";
    private static final String AUTHENTICATE_KEY_DEL = ".";

    private static final Log LOG = LogFactory.getLogger(AuthenticationFilter.class);
    /**
     * Initialize the filter with the configuration in web.xml
     *
     * @throws ServletException se
     */
    public void init(FilterConfig config)
    throws ServletException {

        String springConfigFile = config.getInitParameter(SPRING_CONFIG_FILE);
        if (!StringUtilities.isEmpty(springConfigFile)) {
            loadSpringConfig(springConfigFile);
        }
    }

    /**
     * <p>This method loads the spring configuration file and set the  bean factory
     * in <code>SpringUtilities</code> for further use.</p>
     *
     * @param springConfigFile - Contains the config file path
     */
    private void loadSpringConfig(String springConfigFile) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(springConfigFile);
        SpringUtilities.getInstance().setBeanFactory(beanFactory);
    }

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
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException {

        HttpServletRequest httpReq  = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;

        try {

            WsSession.initializeSession(httpReq.getSession());

            setSessionData(httpReq, Authenticator.KEY_USERNAME);
            setSessionData(httpReq, Authenticator.KEY_TICKET);
            setSessionData(httpReq, Authenticator.KEY_PASSWORD);
            setSessionData(httpReq, Authenticator.KEY_TIMESTAMP);
            WsSession.setSessionData(Authenticator.KEY_PASSWORD, decryptPassword());
            WsSession.setSessionData(WsSession.APP_ID, httpReq.getParameter("AppId"));
            
            Authenticator authenticator = getAuthenticator();
            authenticator.validate();
            
            String transactionID = req.getParameter(Authenticator.KEY_TRANSACTION_ID);
            
            if (transactionID != null && transactionID.trim().length() > 0) {
                LogContext.put("transactionid", new TransactionId(transactionID));
            }

            chain.doFilter(req, res);

        } catch (Exception e) {
            httpRes.sendError(HttpServletResponse.SC_BAD_REQUEST, ESAPI.encoder().encodeForHTML(e.getMessage()));
        }
    }

    /**
     * This method flush all the resources used for filter initialization
     */
    public void destroy() {
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

    /**
     * Gets the bean name of <code>Authenticator</code> defined in
     * spring context
     *
     * @return Name of the <code>Authenticator</code>
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
     *      The application name to which this authenticator belongs to.
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
    
    protected String decryptPassword() {
        String decryptedPassword = null;

        String username = (String) WsSession
                .getSessionData(Authenticator.KEY_USERNAME);
        String password = (String) WsSession
                .getSessionData(Authenticator.KEY_PASSWORD);
        String timestamp = (String) WsSession
                .getSessionData(Authenticator.KEY_TIMESTAMP);

        try {
        	EncryptionHandler handler = EncryptionHandler.getInstance();
            decryptedPassword = handler.decryptText(username, password,
                    timestamp);
        } catch (Exception ex) {
            LOG.debug("Unable to decrypt with configured PasswordEncryptionStrategy context : "
                             + ex.getMessage());
            decryptedPassword = password;
        }
        return decryptedPassword;
    }
}
