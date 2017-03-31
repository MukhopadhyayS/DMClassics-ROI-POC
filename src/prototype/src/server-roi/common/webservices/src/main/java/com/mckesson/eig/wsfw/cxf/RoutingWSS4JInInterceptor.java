/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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

import java.util.Iterator;

import javax.xml.soap.Node;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.w3c.dom.NodeList;

import com.mckesson.eig.utility.util.StringUtilities;

/**
 *
 * @author  Srini Paduri
 * @Date    Jan 16th 2009
 * @since   HECM Release 2.0
 *
 * Overrides and implements BinarySecurityToken along with UsernameToken for security
 * based on WS-SE. Depends on WSS4J framework.
 */
public class RoutingWSS4JInInterceptor extends WSS4JInInterceptor {
    private static final String BINARY_SECURITY_TOKEN = "BinarySecurityToken";
    private SAAJInInterceptor _saajIn = new SAAJInInterceptor();

    /**
     *
     * @param msg
     * @return
     *
     * Converts CXF SoapMessage to java extension defined SOAPMessage
     */
    private SOAPMessage getSOAPMessage(SoapMessage msg) {
        SOAPMessage doc = msg.getContent(SOAPMessage.class);
        if (doc == null) {
            _saajIn.handleMessage(msg);
            doc = msg.getContent(SOAPMessage.class);
        }
        return doc;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor#handleMessage(org.apache.cxf.binding
     * .soap.SoapMessage)
     *
     * Implements handleMessage invoked by CXF framework. It compensates for lack of ability in
     * WSS4JInInterceptor to handle one or the other.
     * NOTE: Since password call back handler is not invoked for BinarySecurityToken, this method
     * may need to compensate for WSSession logic activities performed in password call back
     * handlers.
     */
    public void handleMessage(SoapMessage msg) {

        SOAPMessage doc = getSOAPMessage(msg);
        SoapVersion version = msg.getVersion();

        Node usernameToken = null;
        try {

            usernameToken = getNode(doc.getSOAPHeader(), WSHandlerConstants.USERNAME_TOKEN);
        } catch (SOAPException e) {
            throw new SoapFault("SOAP Exception in RoutingWSS4JInInterceptor",
                    e, version.getSender());
        }

        String action = null; 
        if (usernameToken != null) {
            action = WSHandlerConstants.USERNAME_TOKEN;
        }

        Node timestampToken = null;
        try {
            
            timestampToken = getNode(doc.getSOAPHeader(), WSHandlerConstants.TIMESTAMP);
        } catch (SOAPException e) {
            throw new SoapFault("SOAP Exception in RoutingWSS4JInInterceptor",
                    e, version.getSender());
        }

        if (timestampToken != null) {

            if (action != null) {
                action += " ";
            }
            action += WSHandlerConstants.TIMESTAMP;
        }

        if (StringUtilities.hasContent(action)) {

            setProperty(WSHandlerConstants.ACTION, action);
            super.handleMessage(msg);
            return;
        }

        Node binarySecurityToken = null;
        try {
            binarySecurityToken = getNode(doc.getSOAPHeader(),
                    BINARY_SECURITY_TOKEN);
        } catch (SOAPException e) {
            throw new SoapFault("SOAP Exception in RoutingWSS4JInInterceptor",
                    e, version.getSender());
        }

        if (binarySecurityToken != null) {
            setProperty(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE);
            removeAuthorizationInterceptors(msg);
            super.handleMessage(msg);
            return;
        }

        throw new SoapFault("No UsernameToken or BinaryToken found in request",
                new WSSecurityException(
                        "No UsernameToken or BinaryToken found in request"),
                version.getSender());
    }

    /*
     * Finds a node is security header with in SOAPHeader as requested by nodeName parameter.
     */
    private Node getNode(SOAPHeader header, String nodeName)
            throws SOAPException {

        Node resultNode = null;
        Iterator<Node> iter = header.getChildElements();

        Node securityNode = null;
        while (iter.hasNext()) {
            securityNode = (Node) iter.next();
            if (securityNode.getNodeName().endsWith("Security")) {
                break;
            }
        }
        Node tempNode = null;
        if (securityNode != null) {
            NodeList secNodeList = securityNode.getChildNodes();
            if (secNodeList != null) {
                for (int i = 0; i < secNodeList.getLength(); i++) {
                    tempNode = (Node) secNodeList.item(i);
                    if (tempNode.getNodeName().endsWith(nodeName)) {
                        resultNode = tempNode;
                        break;
                    }
                }
            }
        }

        return resultNode;
    }

    /**
     * remove the AuthorizationInterceptors from the incoming interceptor chain.
     * 
     * @param message
     */
    public static void removeAuthorizationInterceptors(Message message) {

        InterceptorChain chain = message.getInterceptorChain();
        for (Iterator<Interceptor< ? extends Message>> itr = chain.iterator(); itr.hasNext();) {

            Interceptor< ? extends Message> i = itr.next();
            if (i instanceof AuthorizationInterceptor) {

                chain.remove(i);
                break;
            }
        }
    }
}
