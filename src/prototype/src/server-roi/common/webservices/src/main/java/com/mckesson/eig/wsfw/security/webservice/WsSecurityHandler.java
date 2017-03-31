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
package com.mckesson.eig.wsfw.security.webservice;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.ws.axis.security.WSDoAllReceiver;
import org.apache.ws.security.WSConstants;
import org.w3c.dom.NodeList;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.wsfw.axis.FaultHandler;
import com.mckesson.eig.wsfw.exception.InvalidUserException;
import com.mckesson.eig.wsfw.exception.UsernameTokenException;
import com.mckesson.eig.wsfw.session.WsSession;


/**
 * <code>WsSecurityHandler</code> handles all the workflow request in order
 * to implement the security for web service operations. This handler also gets
 * the application id to which the request belongs to in order to use that in
 * application specific authenticator or authorizer.
 *
 *
 * @author N.Shah Ghazni
 * @date   Jan 3, 2008
 * @since  HECM 1.0
 */
public class WsSecurityHandler extends WSDoAllReceiver {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 1L;   

    /**
     * Name that denotes the application id in soap header
     */
    private static final String NODE_APPID = "applicationId";
    
	private static final int USER_NAME_TOKEN_TYPE = 1;
	private static final int BINARY_SECURED_TOKEN_TYPE = 2;     

    /**
     * Instantiates the <code>FaultHandler</code> class.
     */
    private final FaultHandler _faultHandler = new FaultHandler();

    /**
     * This method overrides the invoke method in WSDoAllReceiver
     *
     * @param msgContext
     *            message context.
     * @throws AxisFault
     */
    public void invoke(MessageContext msgContext)
    throws AxisFault {

        try {

            initializeSession(msgContext);
            if ("true".equalsIgnoreCase((String) getOption("routableRequest"))) {
            	
            	invokeRoutingProcess(msgContext);
            	return;
            }

            processUNTokenAuthentication(msgContext);
        } catch (ApplicationException e) {
            throw _faultHandler.createFault(msgContext, e);
        } catch (Throwable t) {
            ApplicationException e = new ApplicationException(
                    "Authentication failed.", t);
            throw _faultHandler.createFault(msgContext, e);
        }
    }

    private void invokeRoutingProcess(MessageContext msgContext) 
    throws AxisFault {
    	
    	try {
			SOAPHeader header = msgContext.getCurrentMessage()
								.getSOAPEnvelope().getHeader();
			int headerType = identifyHeaderType(header);
			routProcess(headerType, msgContext);
	        super.invoke(msgContext);		
		} catch (SOAPException e) {
	            throw new InvalidUserException(
	                    "Unable to extract WSSE header", 
	                    ClientErrorCodes.INVALID_SOAP_MESSAGE);
		}    	
	}

	/**
     * Initializes the <code>WsSession</code> for the specified context
     *
     * @param context
     */
    private void initializeSession(MessageContext context) {
        HttpServletRequest request = (HttpServletRequest) context
                .getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
        if (request != null) {
            WsSession.initializeSession(request.getSession());
        } else {
            WsSession.initializeSession();
        }
    }

    /**
     * Gets the applicationId from the header
     *
     * @param context
     * @throws AxisFault
     * @throws SOAPException
     *
     * @return Application ID
     */
    private String getApplicationID(MessageContext context)
    throws AxisFault, SOAPException {

        SOAPHeader header = context.getCurrentMessage().getSOAPEnvelope().getHeader();
        NodeList nodeList = header.getChildNodes();
        String appId = null;
        for (int i = 0, count = nodeList.getLength(); i < count; i++) {

            SOAPHeaderElement elem = (SOAPHeaderElement) nodeList.item(i);
            if (NODE_APPID.equals(elem.getName()) && elem.getFirstChild() != null) {
                
                appId = elem.getFirstChild().getNodeValue();
                break;
            }
        }

        return appId;
    }
    
    private String getUsernameTokenTimestamp(MessageContext context)
            throws AxisFault, SOAPException {

        String timeStamp = null;

        try {
            SOAPHeader header = context.getCurrentMessage().getSOAPEnvelope()
                    .getHeader();
            NodeList nodeList = header.getChildNodes();
            for (int ii = 0, count = nodeList.getLength(); ii < count; ii++) {

                SOAPHeaderElement elemLevel1 = (SOAPHeaderElement) nodeList
                        .item(ii);
                if (elemLevel1.getName().equals(WSConstants.WSSE_LN)) {

                    NodeList secondNodeList = elemLevel1.getChildNodes();
                    for (int jj = 0; jj < secondNodeList.getLength(); jj++) {

                        MessageElement elemLevel2 = (MessageElement) secondNodeList
                                .item(jj);
                        if (elemLevel2.getName().equals(WSConstants.USERNAME_TOKEN_LN)
                            || elemLevel2.getName().equals(WSConstants.TIMESTAMP_TOKEN_LN)) {

                            NodeList thirdNodeList = elemLevel2.getChildNodes();
                            for (int kk = 0; kk < thirdNodeList.getLength(); kk++) {
                                MessageElement elemLevel3 = (MessageElement) thirdNodeList
                                        .item(kk);
                                if (elemLevel3.getName().equals(
                                        WSConstants.CREATED_LN)) {
                                    timeStamp = elemLevel3.getValue();
                                    return timeStamp;
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            throw new UsernameTokenException(
                    "Unable to extract Created timestamp from WSSE header", ex,
                    ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
        }

        if (timeStamp == null) {
            throw new UsernameTokenException(
                    "WSSE header Created timestamp was missing.",
                    ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
        }

        return timeStamp;
    }

	/**
     * This message identifies the type of header associated with the passed header
     * and returns the integer representation of the type.
     * 
     * @param header
     * 
     * @return integer representation of the type of header.
	 * @throws AxisFault 
     */
	private int identifyHeaderType(SOAPHeader header) 
	throws AxisFault {
		
		Node usernameToken = null;
        usernameToken = getNode(header, "UsernameToken");
        if (usernameToken != null) {
        	return USER_NAME_TOKEN_TYPE;
        }

        Node binarySecurityToken = null;
        binarySecurityToken = getNode(header, "BinarySecurityToken");
        if (binarySecurityToken != null) {
        	return BINARY_SECURED_TOKEN_TYPE;
        }

        throw new AxisFault("No UsernameToken or BinaryToken found in request");
    }
	
	/**
	 * This message does the routing based on the header type
	 * 
	 * @param headerType
	 * @param msgContext
	 * @throws SOAPException 
	 * @throws AxisFault 
	 * @throws AxisFault 
	 */
	private void routProcess(int headerType, MessageContext msgContext) 
	throws AxisFault, SOAPException {

		switch (headerType) {
			case USER_NAME_TOKEN_TYPE :
				processUNTokenAuthentication(msgContext);
				break;
			case BINARY_SECURED_TOKEN_TYPE :
				processBSTokenAuthentication(msgContext);
				break;
			default :
				processUNTokenAuthentication(msgContext);
				break;
		}
	}	
	
	/**
	 * This method processes the Binary Secured Token Authentication
	 * 
	 * @param msgContext
	 */	
	private void processBSTokenAuthentication(MessageContext msgContext) {
        setProperty(msgContext, "action", "Signature");	
	}

	/**
	 * This method processes the UserName Token Authentication
	 * 
	 * @param msgContext
	 * @throws SOAPException 
	 * @throws AxisFault 
	 * @throws AxisFault 
	 */
	private void processUNTokenAuthentication(MessageContext msgContext) 
	throws AxisFault, SOAPException {
        
		setProperty(msgContext, "action", "UsernameToken Timestamp");
		
        // Sets the application id in order to get corresponding
        // authenticator or authorizer depends on application id
        WsSession.setSessionData(WsSession.APP_ID, getApplicationID(msgContext));

        String timestamp = getUsernameTokenTimestamp(msgContext);
        WsSession.setSessionData(WsSession.MESSAGE_TIMESTAMP, timestamp);

        super.invoke(msgContext);        
	}

	/**
	 * Finds a node in the security header with in SOAPHeader as requested by 
	 * the nodeName parameter.
	 * 
	 * @param header
	 * @param nodeName
	 * 
	 * @return the respective matching node
	 * 
	 */
	@SuppressWarnings("unchecked")
	private Node getNode(SOAPHeader header, String nodeName) {
		
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
    
}
