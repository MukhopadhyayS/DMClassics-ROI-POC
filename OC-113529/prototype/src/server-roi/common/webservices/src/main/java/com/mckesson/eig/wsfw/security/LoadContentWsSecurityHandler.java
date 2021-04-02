/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.wsfw.security;

import javax.servlet.http.HttpServletRequest;
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
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.wsfw.axis.FaultHandler;
import com.mckesson.eig.wsfw.exception.UsernameTokenException;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author ec7opip
 * 
 */
public class LoadContentWsSecurityHandler extends WSDoAllReceiver {
    private static final Log LOG = LogFactory
            .getLogger(LoadContentWsSecurityHandler.class);

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
    public void invoke(MessageContext msgContext) throws AxisFault {
        try {
            initializeSession(msgContext);
            String timestamp = getUsernameTokenTimestamp(msgContext);
            WsSession.setSessionData(WsSession.MESSAGE_TIMESTAMP, timestamp);
            LOG.debug("UsernameToken UTZ = " + timestamp);
            super.invoke(msgContext);
        } catch (ApplicationException e) {
            LOG.debug("Timestamp not present.  No worries in this case.");
        } catch (Throwable t) {
            ApplicationException e = new ApplicationException(
                    "Authentication failed.", t);
            throw _faultHandler.createFault(msgContext, e);
        }
    }

    private void initializeSession(MessageContext context) {
        HttpServletRequest request = (HttpServletRequest) context
                .getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
        if (request == null) {
            WsSession.initializeSession();
        } else {
            WsSession.initializeSession(request.getSession());
        }
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
                        if (elemLevel2.getName().equals(
                                WSConstants.USERNAME_TOKEN_LN)) {

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

}
