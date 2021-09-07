/*
 * Copyright 2009-2010 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.wsfw.cxf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.ws.BindingProvider;

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mckesson.eig.wsfw.EIGConstants;
import com.mckesson.eig.wsfw.security.encryption.EncryptionHandler;
import com.mckesson.eig.wsfw.security.encryption.PasswordEncryptionStrategy;
import com.mckesson.eig.wsfw.session.WsSession;


/**
 * <code>WorkflowWSUtil</code> contains the utility methods in order to access web services.
 *
 * @author N.Shah Ghazni
 * @date   Feb 16, 2008
 * @since  HECM 1.0; Feb 16, 2008
 */
public final class WSSecurityUtil {

    private static final String EMPTY_STRING = "";

    /**
     * Creates a new Instance of <code>HecmUtil</code>
     */
    private WSSecurityUtil() {
    }

    /**
     * Set the headers for the specified service proxy
     *
     * @param proxy
     *        indicates the client proxy  
     * 
     * @throws SOAPException
     */
    public static void addHeaderInformation(Client clientProxy, String userName, String password) {
    	addHeaderInformation(clientProxy, userName, password, false);
    }

    /**
     * Set the headers for the specified service proxy
     *
     * @param proxy
     *        indicates the client proxy
     *  
     * @param useSecondarySessionID true if need to use secondary session id, otherwise false
     * 
     * @throws SOAPException
     */
    public static void addHeaderInformation(Client clientProxy,
                                            String userName,
                                            String password,
                                            boolean useSecondarySessionID) {

        if (userName != null && !userName.equals(EMPTY_STRING)) {
            WsSession.setSessionData("USERNAME", userName);
            WsSession.setSessionData(WsSession.MESSAGE_TIMESTAMP, getTimestamp(new Date()));
        }
        
        if (password != null && !password.equals(EMPTY_STRING)) {
            WsSession.setSessionData("PASSWORD", password);
        }
        
        List<Header> headers = addHeaderInfo(clientProxy);
        setSessionInfo(clientProxy, headers, useSecondarySessionID);
    }

    /**
     * Helper method used to add the header info.
     * 
     * @param clientProxy
     *
     * @return Header List
     */
    private static List<Header> addHeaderInfo(Client clientProxy) {

        if (clientProxy == null) {
            throw new RuntimeException("Service proxy cannot be null.");
        }
        addWsSecurityOutInterceptors(clientProxy);

        List<Header> headers = new ArrayList<Header>();
        Document doc = null;
        try {
            doc = DOMUtils.newDocument();
        } catch (Exception e) {
            throw new RuntimeException("DOM configuration problem", e);
        }
       
        //HPF 15.2 - if exists, use APP_ID set in session
        String appId = "1"; //backward compatibility
        if ( WsSession.getSessionData(WsSession.APP_ID) !=  null) {
        	appId = (String) WsSession.getSessionData(WsSession.APP_ID);
        }     
        
        headers.add(createHeaderElement(doc, 
                                        WsSession.APP_ID,
                                        appId));

        headers.add(createHeaderElement(doc, 
                                        WsSession.TXN_ID, 
                                        (String) WsSession.getSessionData(WsSession.TXN_ID)));
        return headers;
    }

    /**
     * Helper method used to set the session info
     *
     * @param clientProxy Instance of client proxy
     * @param headers Header List
     * @param useSecondarySessionID true if need to create secondary id, otherwise false
     */
    private static void setSessionInfo(Client clientProxy,
                                       List<Header> headers,
                                       boolean useSecondarySessionID) {

        Map<String, Object> requestContext = clientProxy.getRequestContext();
        requestContext.put(Header.HEADER_LIST, headers);
        requestContext.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, Boolean.TRUE);

        Map<String, Object> requestHeaders =
            (Map<String, Object>) requestContext.get(Message.PROTOCOL_HEADERS);

        if (requestHeaders == null) {

           requestHeaders = new HashMap<String, Object>();
           requestContext.put(Message.PROTOCOL_HEADERS, requestHeaders);
        }

        List<String> cookies = new ArrayList<String>();
        cookies.add("JSESSIONID=" + WsSession.getSessionId());
        requestHeaders.put("Cookie", cookies);
    }

    /**
     * 
     * @param doc
     * @param name
     * @param value
     * @return
     */
    private static Header createHeaderElement(Document doc, String name, String value) {

        QName qName = new QName(EIGConstants.TYPE_NS_V1, name, "eig");
        Element ele = doc.createElement(name.toString());
        //Element ele = XMLUtils.createElementNS(doc, qName);
        ele.setTextContent(value);

        return new Header(qName, ele);
    }

    /**
     * 
     * @param client
     */
    private static void addWsSecurityOutInterceptors(Client client) {

        Endpoint cxfEndpoint = client.getEndpoint();

        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps.put(WSHandlerConstants.ACTION, 
                WSHandlerConstants.USERNAME_TOKEN + "  " + WSHandlerConstants.TIMESTAMP);
        outProps.put(WSHandlerConstants.USER, "DEFAULT");
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, StubPasswordProvider.class.getName());

        WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
        cxfEndpoint.getOutInterceptors().add(wssOut);
        cxfEndpoint.getOutInterceptors().add(new SAAJOutInterceptor());

    }

    /**
     * Helper method used to encrypt the password
     * 
     * @param userName
     * @param password
     * @return Encrypted Password
     */
    public static String encryptPassword(String userName, String password) {

    	EncryptionHandler handler = EncryptionHandler.getInstance();
        String timestamp = (String) WsSession.getSessionData(WsSession.MESSAGE_TIMESTAMP);
        return handler.encryptText(userName, password, timestamp);
    }
    
    /**
     * Helper method is used to get timestamp.
     *
     * @param date
     */
    private static String getTimestamp(Date date) {

        if (date == null) { return null; }
        try {

            SimpleDateFormat formatter = new SimpleDateFormat(PasswordEncryptionStrategy.DATE_TIME_FORMAT);
            return formatter.format(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
