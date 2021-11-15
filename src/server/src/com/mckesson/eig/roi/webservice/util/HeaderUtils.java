package com.mckesson.eig.roi.webservice.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mckesson.dm.core.common.util.sanitize.EncoderUtilities;
import com.mckesson.eig.roi.common.config.BootstrapConfiguration;
import com.mckesson.eig.roi.webservice.util.rest.security.SecurityConstants;
import com.mckesson.eig.utility.util.SecureStringAccessor;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.session.CxfWsSession;

public final class HeaderUtils {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderUtils.class);

    /** Tenant Id constant. */
    public static final String KEY_TENANT_ID = "TenantId";

    /** Constant for the Authorization header. */
    public static final String KEY_AUTHORIZATION = "Authorization";

    /** Constant for extra headers not set normally. */
    public static final String EXTRA_HEADERS_ATTR = "ExtraHeaders";

    /** The Constant CLIENT_IP_KEY. */
    public static final String CLIENT_IP_KEY = "ClientIP";

    /** The Constant FORWARDED_IP_KEY. */
    public static final String FORWARDED_IP_KEY = "x-forwarded-for";

    public static final String USER_SESSION = "UserSession";

    public static final String USER_BROWSER_NAME = "UserBrowserName";

    public static final String USER_OS_NAME = "UserOSName";

    public static final String USER_INSTANCE_ID = "UserInstanceID";

    public static final String SERVER_MACHINE_NAME = "ServerMachineName";

    public static final String CLIENT_TYPE = "ClientType";

    public static final String PD_ENCRYPTED = "pwdEncrypted";

    public static final String CORE_SESSION_ID = "JSESSIONID";

    public static final String CONTENT_CONTEXT = "Content-Context";
    
    public static final String COOKIE = "Cookie";

    public static final String MODULE = "module";
    
	private HeaderUtils() {
	}

    /**
     * This method copies the headers on the integration tier request
     * to the client request that will be sent to the core server.
     * @param request
     * @param coreProxy
     */
    public static void copyHeaders(HttpServletRequest request, Object coreProxy) {
        
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            
            String headerName = (String) headerNames.nextElement();
            if ("content-type".equalsIgnoreCase(headerName)
                    || "content-length".equalsIgnoreCase(headerName)) {
                continue;
            }
          WebClient.client(coreProxy).header(headerName, request.getHeader(headerName));
        }
        
        setClientHeaders(coreProxy);
        setCookieInCoreProxy(request, coreProxy);
        setAuthorizationHeaderFromParameters(request, coreProxy);
        
        String serverTime = getHeaderFromAttributesParameters(request, SecurityConstants.SERVER_TIME, null);
        if (serverTime != null && !serverTime.isEmpty()) {
            WebClient.client(coreProxy).header(SecurityConstants.SERVER_TIME, serverTime);
        }
    }

    /**
     * set the webclient header parameters such as transactionId, clientIp 
     * @param coreProxy
     */
    public static void setClientHeaders(Object coreProxy) {
        
        //set transaction id to the header
        String transactionId = (String) CxfWsSession.getSessionData(CxfWsSession.TXN_ID);
        if (transactionId != null) {
            WebClient.client(coreProxy).header("TransactionId", transactionId);
        }

        // attempt to get client ip from header param.  If it is not passed, get it from request.
        String clientIp = (String) CxfWsSession.getSessionData(CxfWsSession.CLIENT_IP);
        if (clientIp != null) {
            WebClient.client(coreProxy).header(CLIENT_IP_KEY, clientIp);
        }
        
        String securitytoken = (String) CxfWsSession.getSessionData(CxfWsSession.TICKET);
        if (securitytoken != null && !securitytoken.isEmpty()) {
            WebClient.client(coreProxy).header(SecurityConstants.SECURITY_TOKEN, securitytoken);
        }

        setTenantInCoreProxy(coreProxy);
    }
    /**
     *encodes the messages before loging.
     *
     * @param message
     *            The message to be logged.
     * @return String
     *            Encoded message
     */
    public static String encodeMessage(Object message){
        String replace = message.toString().replace( '\n', '_' ).replace( '\r', '_' );
        replace = EncoderUtilities.encodeForHTML(replace);
        return replace;
    }
    
    /**
     * 
     * @param request
     * @param coreProxy
     * getting MPFTOKEN from integration request first, if null or blank then take it form Integration UserSession
     */
    private static void setCookieInCoreProxy(HttpServletRequest request, Object coreProxy) {
        
        // simply copy core sessionid and mpftoken if it exists in usersession
        String sessionId = null;
        String mpfToken = null;
        if (request.getSession(false) != null) {

            sessionId = CxfWsSession.getSessionId();
            if (sessionId != null) {
                mpfToken = (String) CxfWsSession.getSessionData(CxfWsSession.TICKET);
            }
        }
        
        if (StringUtilities.hasContent(sessionId)) {
            
            WebClient.client(coreProxy).header("Cookie", "JSESSIONID=" + sessionId + "; MPFTOKEN=" + mpfToken);
            LOGGER.debug(encodeMessage("CORE_SESSION_ID=") + encodeMessage(sessionId));
        } else {
            LOGGER.debug(encodeMessage("CORE_SESSION_ID is not set!!!!"));
        }
    }
    
    
    /**
     * 
     * @param coreProxy
     */
    private static void setTenantInCoreProxy(Object coreProxy) {

        String tenantId = BootstrapConfiguration.getInstance().getTenantId();
        if (tenantId != null && !tenantId.isEmpty()) {
            
            WebClient.client(coreProxy).header(KEY_TENANT_ID, tenantId);
            LOGGER.debug("TENANT_ID=" + tenantId);
        } else {
            
            LOGGER.debug("TENANT_ID is not configured in ROI properties, So Tenant Id is not set");
        }
    }

    /**
     * Gets the client ip.
     *
     * @param request the request
     * @return the client ip
     */
    public static String getClientIp(HttpServletRequest request) {
        
        String result = request.getHeader(FORWARDED_IP_KEY);
        if (result == null || result.isEmpty()) {
            result = (String) request.getAttribute(CLIENT_IP_KEY);
        }
        if (result == null || result.isEmpty()) {
            result = getHeaderFromAttributes(request, CLIENT_IP_KEY);
        }
        if (result == null || result.isEmpty()) {
            result = request.getRemoteAddr();
        }
        return result;
    }

    /**
     * Gets a header value either from the request headers or from an optional
     * map set in the request attributes or request parameters map.
     *
     * @param request
     *            the request to get the values from.
     *
     * @param headerName
     *            the header name to look for.
     *
     * @param defaultValue
     *            default value to return if header does not exist in request headers, attributes, or parameters
     *
     * @return the value of the header.
     */
    public static String getHeaderFromAttributesParameters(HttpServletRequest request, String headerName, String defaultValue) {
        String value = getHeaderFromAttributes(request, headerName);

        if (value == null) {
            value = getHeaderFromParameters(request, headerName, defaultValue);
        }
        return value;
    }

    /**
     * Gets a header value either from the request headers or from an optional
     * map set in the request attributes or request parameters map.
     *
     * @param request
     *            the request to get the values from.
     *
     * @param paramName
     *            the request parameter name to look for.
     *
     * @param defaultValue
     *            default value to return if header does not exist in request headers, attributes, or parameters
     *
     * @return the value of the header.
     */
    public static String getHeaderFromParameters(HttpServletRequest request, String paramName, String defaultValue) {
        String value = (request.getParameter(paramName) != null && !request.getParameter(paramName).equalsIgnoreCase("null")) 
                ? request.getParameter(paramName) : defaultValue;

        return value;
    }

    /**
     * Gets a header value either from the request headers or from an optional
     * map set in the request attributes.
     *
     * @param request
     *            the request to get the values from.
     *
     * @param headerName
     *            the header name to look for.
     *
     * @return the value of the header.
     */
    public static String getHeaderFromAttributes(HttpServletRequest request, String headerName) {
        
        String result = request.getHeader(headerName);
        if (result == null || result.isEmpty()) {
            @SuppressWarnings("unchecked")
            Map<String, String> extras = (Map<String, String>) request.getAttribute(EXTRA_HEADERS_ATTR);
            if (extras != null && !extras.isEmpty()) {
                result = extras.get(headerName);
            }
        }
        return result;
    }

    /**
     * Build and set the authorization header value from the request parameter map
     * if it does not exists in request header.
     *
     * @param request
     *            the request to get the values from.
     *
     * @param coreProxy
     *            the core proxy interface object
     *
     */
    public static void setAuthorizationHeaderFromParameters(HttpServletRequest request, Object coreProxy) {
        
        String authHeader = request.getHeader(SecurityConstants.AUTHORIZATION);
        if (authHeader == null) {
            authHeader = HeaderUtils.getHeaderFromAttributes(request, SecurityConstants.AUTHORIZATION);     
            if (authHeader != null) {
                WebClient.client(coreProxy).header(SecurityConstants.AUTHORIZATION, authHeader.toString()); 
            }
        }
        
        if (authHeader == null) {  
            
            SecureStringAccessor securedPassword = (SecureStringAccessor)CxfWsSession.getSessionData(CxfWsSession.PD);
            
            StringBuilder builder = new StringBuilder();
             securedPassword.DoHylandAccess((chars, tempStr) -> {
                 builder.append(chars);
             });
             
             String password = builder.toString();
            
            String authorizationHeader = 
                    getAuthorizationString((String) CxfWsSession.getSessionData(CxfWsSession.USER_NAME),
                                           password);
            
            WebClient.client(coreProxy).header(SecurityConstants.AUTHORIZATION, authorizationHeader.toString());
            WebClient.client(coreProxy).header(SecurityConstants.PD_ENCRYPTED, Boolean.TRUE);
        }
    }

    /**
     * constructs the authorization string using the given username and password 
     * @param user
     * @param pd
     * @return
     */
    public static String getAuthorizationString(String user, String pd) {
        
        StringBuilder authorizationHeader = new StringBuilder();
        
        /**
        * Here userName would be like "Domain name\User name" if authentication is
        * ladap authentication.
        */
         StringTokenizer st = new StringTokenizer(user, "\\");
         String userName = "";
         String domain = "";
         String domainUserName = "";

         if (st.countTokens() == 2) {

           domain = st.nextToken();
           String[] userString = st.nextToken().split("~");
           domainUserName = userString[0];
           userName = userString[1];
         } else {
           userName = user;
         }
        
        authorizationHeader.append(userName);
        authorizationHeader.append(SecurityConstants.AUTHORIZATION_DELIMETER);
        authorizationHeader.append(pd);
        authorizationHeader.append(SecurityConstants.AUTHORIZATION_DELIMETER);
        // Ping is not available in the ROI, hence we set it as empty
        authorizationHeader.append("");
        authorizationHeader.append(SecurityConstants.AUTHORIZATION_DELIMETER);
        authorizationHeader.append(domain);
        authorizationHeader.append(SecurityConstants.AUTHORIZATION_DELIMETER);
        authorizationHeader.append(domainUserName);
        authorizationHeader.append(SecurityConstants.AUTHORIZATION_DELIMETER);
        authorizationHeader.append(userName);
        authorizationHeader.append(SecurityConstants.AUTHORIZATION_DELIMETER);
        authorizationHeader.append(pd);
        authorizationHeader.append(SecurityConstants.AUTHORIZATION_DELIMETER);
        authorizationHeader.append("ROI");
        
        return authorizationHeader.toString();
    }
}
