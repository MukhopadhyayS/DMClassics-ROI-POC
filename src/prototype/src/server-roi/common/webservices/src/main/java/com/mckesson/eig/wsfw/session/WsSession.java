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

package com.mckesson.eig.wsfw.session;

import java.io.Serializable;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * The WsSession holds data that should remain available across multiple web
 * service calls in the same session. It is helpful to cache these data items to
 * improve performance. Examples of things that might be cached include user
 * credentials and connections to quickstream.
 *
 * The Web Services framework will take care of initializing the session. Client
 * code only needs to use the #getSessionData() and #setSessionData() static
 * methods.
 *
 */
public final class WsSession implements Serializable {

    /**
     * Standard key to store/retrieve a security rights for a user in the session.
     */
    private static final String EIG_USER_SECURITY_RIGHTS = "EIG_USER_SECURITY_RIGHTS";

    /**
     * Standard key to store/retrieve a staff_Login_seq in the session.
     */
    private static final String STAFF_LOGIN_SEQ = "StaffLoginSeq";

    /**
     * Standard key to store/retrieve a username in the session.
     */
    public static final String USER_NAME = "UserName";

    /**
     * Standard key to store/retrieve a password in the session.
     */
    //hardcoded password veracode fix changing Password varibale to PD
    public static final String PD = "Password";

    /**
     * Standard key to store/retrieve a ticket in the session.
     */
    public static final String TICKET = "AuthTicket";

    public static final String APP_ID = "applicationId";

    public static final String TXN_ID = "transactionId";

    /**
     * Standard key to store/retrieve client ip address (location)
     */
    public static final String CLIENT_IP = "ClientIP";

    public static final String AUTHRESULT = "AuthResult";

    public static final String BAD_PD_COUNT = "BadPasswordCount";
    public static final String PD_EXPIRE_DATETIME = "PasswordExpireDateTime";
    public static final String PD_CHANGE_REQUIRED = "PasswordChangeRequired";

    public static final String MESSAGE_TIMESTAMP = "MessageTimestamp";
    public static final String SECURITY_HEADER_MAP = "SecurityHeaderMap";

    private static final Log LOG = LogFactory.getLogger(WsSession.class);

    private static final String EIG_WS_SESSION = "eig.wsSession";
    private static final String SESSION_ID = "SESSION_ID";
    private HashMap _map;

    public static final String SESSION_ID_2 = "SESSION_ID_2";

    private WsSession() {
        setMap(new HashMap());
    }

    /**
     * @return Returns the map.
     */
    public HashMap getMap() {
        return _map;
    }

    /**
     *
     * @param map
     *            The map to set.
     */
    public void setMap(HashMap map) {
        _map = map;
    }

    public static WsSession getWsSession(HttpSession httpSession)  {
    	return (WsSession) httpSession.getAttribute(EIG_WS_SESSION);
    }

   /**
     * Initialize the local WsSession for this thread using the Http Session. If
     * no WsSession exists in the HttpSession, a new one will be created.
     *
     * @param httpSession
     * @return
     */
    public static void initializeSession(HttpSession httpSession) {
        WsSession wsSession = getWsSession(httpSession);
        if (wsSession == null) {
            LOG.debug("HttpSession has no WsSession - creating new WsSession");
            wsSession = new WsSession();
            httpSession.setAttribute(EIG_WS_SESSION, wsSession);
        }
        wsSession.getMap().put(SESSION_ID, httpSession.getId());
    }

    /**
     * Initialize the local WsSession for this thread. In this case a new
     * session is always created. For use with non-HTTP web services (e.g. JMS
     * based).
     *
     * @return
     */
    public static void initializeSession() {
    	LOG.debug("Initialize Session - Creating a new WsSession");
    	HttpSession session = getCurrentHttpSession();
        if (session != null) {
    		initializeSession(session);
    	}
    }
    
    /**
     * Retrieve data from the WsSession for this thread.
     *
     * @param key
     * @return
     */
    public static Object getSessionData(HttpSession httpSession, String key) {
    	WsSession currentSession = getWsSession(httpSession);
        if (currentSession == null) {
            initializeSession(httpSession);
            currentSession = getWsSession(httpSession);
        }
        return currentSession.getMap().get(key);
    }

    public static Object getSessionData(String key) {
    	WsSession currentSession = getCurrentWsSessionFromHttpSession();
        if (currentSession == null) {
            return null;
        }
        return currentSession.getMap().get(key);
    }

    /**
     * Store data in this WsSession.
     *
     * @param key
     * @param value
     */
    public static void setSessionData(String key, Object value) {
    	WsSession currentSession = getCurrentWsSessionFromHttpSession();
        if (currentSession != null) {
            currentSession.getMap().put(key, value);
        }
    }

    public static void setSessionData(HttpSession httpSession, String key, Object value) {
    	WsSession currentSession = getWsSession(httpSession);
        if (currentSession == null) {
            initializeSession(httpSession);
            currentSession = getWsSession(httpSession);
        }
        if (currentSession != null) {
        	currentSession.getMap().put(key, value);
        }
    }

    public static void removeSessionData(HttpSession httpSession, String key) {
    	WsSession currentSession = getWsSession(httpSession);
        if (currentSession != null && currentSession.getMap() != null) {
        	currentSession.getMap().remove(key);
        }
    }
     /**
     * Remove data from this WsSession.
     *
     * #param key
     */
    public static void removeSessionData(String key) {
    	WsSession currentSession = getCurrentWsSessionFromHttpSession();
        if (currentSession != null && currentSession.getMap() != null) {
        	currentSession.getMap().remove(key);
        }
    }

    /*
     * Helper method to get userid in in session
     */

    public static Long getSessionUserId() {
        return (Long) WsSession.getSessionData(WsSession.STAFF_LOGIN_SEQ);
    }

    public static void  setSessionUserId(Long userId) {
       WsSession.setSessionData(getKeyForStaffLoginSeq(), userId);
    }


    public static boolean isUserSecurityRightsLoaded() {
        if (WsSession.getSessionData(getKeyForUserSecurityRights()) == null) {
            return false;
        }
        return true;
    }

    public static void setUserSecurityRights(HashMap rightsMap) {
        WsSession.setSessionData(getKeyForUserSecurityRights(), rightsMap);
    }

    public static HashMap getUserSecurityRights() {
        return (HashMap) WsSession
                .getSessionData(getKeyForUserSecurityRights());
    }

    private static String getKeyForUserSecurityRights() {
        return EIG_USER_SECURITY_RIGHTS;
    }

    private static String getKeyForStaffLoginSeq() {
        return STAFF_LOGIN_SEQ;
    }

    /**
    * Gets the current session id.
    */
    public static String getSessionId() {
        return (String) getSessionData(SESSION_ID);
    }

    public static void removeAllSessionData() {
    	WsSession currentSession = getCurrentWsSessionFromHttpSession();
        if (currentSession != null && currentSession.getMap() != null) {
            currentSession.getMap().clear();
        }

    }
    
    private static HttpSession getCurrentHttpSession() {
    	MessageContext context;
    	try {
    		context = MessageContext.getCurrentContext();
			if(context != null) {
	            HttpServletRequest request = (HttpServletRequest) context
	                    .getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
	            HttpSession session = request.getSession();
	            return session;
			}
    	} catch(IllegalStateException e) {
    		LOG.debug("No HttpServletRequest found");
    	}
    	return null;
    }
    
    public static void invalidateSession() {
    	HttpSession httpSession = getCurrentHttpSession();
    	if  (httpSession != null) {
    		httpSession.invalidate();
    	}
    }

    
    private static WsSession getCurrentWsSessionFromHttpSession() {
    	HttpSession httpSession = getCurrentHttpSession();
    	if  (httpSession != null) {
    		WsSession wsSession = getWsSession(httpSession);
    		return wsSession;
    	}
    	return null;
    }
}
