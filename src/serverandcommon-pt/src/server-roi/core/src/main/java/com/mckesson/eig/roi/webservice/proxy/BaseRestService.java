/*
 * Copyright 2012 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.roi.webservice.proxy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.webservice.util.HeaderUtils;
import com.mckesson.eig.roi.webservice.util.WebClientPool;
import com.mckesson.eig.roi.webservice.util.rest.RestWebClientPool;
import com.mckesson.eig.utility.util.ObjectUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * This class extracts common instance members for all services, like the
 * WebClientPool and getProxy.
 * 
 * @author eie5alv
 */
public class BaseRestService 
extends BaseROIService {
    
    private static final String USER_NAME_COOKIE = "userName";

	private static final String IS_VALID_PIN_COOKIES = "isPinValidated";

	/** Web Client Pool to integrate with core web service. */
    private WebClientPool<?> _webClientPool = null;
    
    private WebClient _webClient = null;
    
    /**
     * Sets the Web Client Pool
     * 
     * @param webClientPool
     *            the pool to use.
     */
    public void setWebClientPool(WebClientPool<?> webClientPool) {
        this._webClientPool = webClientPool;
    }
    
    public WebClientPool<?> getWebClientPool() {
        return _webClientPool;
    }

    /**
     * Creates a proxy client for the WebService defined in the Interface passed
     * as argument.
     * 
     * @param webServiceInterface
     *            The Class object of the interface to use for creating the
     *            client.
     * @param req
     *            The HttpServletRequest from which the headers will be copied.
     * @return
     */
    protected <T> T getProxy(Class<T> webServiceInterface) {
        
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request =
                (HttpServletRequest) context.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
        
        T result = null;
        if (_webClientPool instanceof RestWebClientPool) {
            // Synchronize building the REST request
            _webClient = (WebClient) _webClientPool.getWebClient(request);
            synchronized (_webClient) {
                
                _webClient.resetQuery();
                _webClient.replaceQuery(request.getQueryString());
                this.copyUserNamePinCookies();
                result = JAXRSClientFactory.fromClient(_webClient, webServiceInterface);
                HeaderUtils.copyHeaders(request, result);
                WebClient.client(result).accept("application/json");
            }
            
        } else {
            result = _webClientPool.getWebClient(request, webServiceInterface);
        }
        return result;
    }

    private void copyUserNamePinCookies() {
        
        
        String userName = ObjectUtilities.toString(WsSession.getSessionData(WsSession.USER_NAME));
		if (StringUtilities.hasContent(userName)) {
			_webClient.cookie(new javax.ws.rs.core.Cookie(USER_NAME_COOKIE, userName));
		}

		String password = ObjectUtilities.toString(WsSession.getSessionData(WsSession.PD));
		if (StringUtilities.hasContent(password)) {
		    _webClient.cookie(new javax.ws.rs.core.Cookie(IS_VALID_PIN_COOKIES, password));
		}
			
	}

    /**
     * Returns coreSessionId from underlying Response of webServiceInterface.
     * 
     * @param webServiceInterface
     * @return coreSessionId
     */
    protected <T> String getCoreSessionFromResponse(T webServiceInterface, String sessionId) {
        
        Client webClient = WebClient.client(webServiceInterface);
        Response response = webClient.getResponse();
        MultivaluedMap<String, Object> metaData = response.getMetadata();
        List<Object> cookies = metaData.get("Set-Cookie");
        
        if (cookies != null && cookies.size() > 0) {
            
            for (Object cookieObj : cookies) {
                
                if (cookieObj != null) {
                    
                    String cookie = cookieObj.toString();
                    StringTokenizer st = new StringTokenizer(cookie, ";");

                    if (st.countTokens() > 0) {
                        
                        String sessionNV = st.nextToken();
                        if (sessionNV.startsWith(sessionId)) {
                            String coreSessionId = StringUtils.substringAfter(sessionNV, "=");
                            return coreSessionId;
                        }
                    }
                }
            }
        }
        
        return null;
    }

    /**
     * Copies the properties from one object to a new one of the given class. 
     * Only properties with the same name are copied.
     *
     * @param obj The source object to copy from.
     * @param destClass The class to use for the new object.
     *
     * @return a new instance of the destClass class with the properties copied from obj.
     */
    public <T> T transformObject(Object obj, Class<T> destClass) {
        T result = null;
        if (obj != null) {
            try {
                result = (T) destClass.newInstance();
                BeanUtils.copyProperties(result, obj);
            } catch (InstantiationException e) {
                result = null;
            } catch (IllegalAccessException e) {
                result = null;
            } catch (InvocationTargetException e) {
                result = null;
            }
        }
        return result;
    }
    
    /**
     * Creates a new List containing objects of type destClass, each of those objects is copying
     * the properties from one of the objects comming in the collection.
     *
     * @param coll The collection of source objects. 
     * @param destClass The class to use for the new objects.
     *
     * @return A list of new instances of the destClass class with the properties 
     * copied from objects on collection.
     */
    @SuppressWarnings("rawtypes")
    public <T> List<T> transformCollection(Collection coll, Class<T> destClass) {
        List<T> result = null;
        if (coll != null) {
            result = new ArrayList<T>();
            Iterator it = coll.iterator();
            while (it.hasNext()) {
                T newObj = this.transformObject(it.next(), destClass);
                result.add(newObj);
            }
        }
        return result;
    }

}
