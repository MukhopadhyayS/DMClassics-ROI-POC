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

import javax.servlet.http.HttpServletRequest;
import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.ws.axis.security.WSDoAllReceiver;
import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.wsfw.axis.FaultHandler;
import com.mckesson.eig.wsfw.session.WsSession;


public class CiSecurityHandler extends WSDoAllReceiver {

    private static final long serialVersionUID = 1L;   

    private final FaultHandler _faultHandler = new FaultHandler();

    public void invoke(MessageContext msgContext)
    throws AxisFault {

        try {

            initializeSession(msgContext);
        } catch (ApplicationException e) {
            throw _faultHandler.createFault(msgContext, e);
        } catch (Throwable t) {
            ApplicationException e = new ApplicationException(
                    "Authentication failed.", t);
            throw _faultHandler.createFault(msgContext, e);
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
        
}
