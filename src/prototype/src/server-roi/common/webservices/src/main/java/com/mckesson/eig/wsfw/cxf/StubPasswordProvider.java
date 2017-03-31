/*
 * Copyright 2008-2009 McKesson Corporation and/or one of its subsidiaries.
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

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author sahuly
 * @date   Jan 6, 2009
 * @since  HECM 1.0; Jan 6, 2009
 */
public class StubPasswordProvider implements CallbackHandler {

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        String userName = getUserName();
        pc.setIdentifier(userName);
        String encryptedPassword = WSSecurityUtil.encryptPassword(userName, getPassword());
        pc.setPassword(encryptedPassword);
    }

    private static String getPassword() {

        String password = (String) WsSession.getSessionData("PASSWORD");
        if (password == null) {
            password = (String) WsSession.getSessionData("Password");
        }
        return password;
    }
    
    private static String getUserName() {

        String userName = (String) WsSession.getSessionData("USERNAME");
        if (userName == null) {
            userName = (String) WsSession.getSessionData("UserName");
        }
        return userName;
    }
}
