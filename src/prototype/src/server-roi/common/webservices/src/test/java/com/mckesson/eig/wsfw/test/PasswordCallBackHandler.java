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

package com.mckesson.eig.wsfw.test;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author OFS
 * @date   Dec 13, 2008
 */
public class PasswordCallBackHandler implements CallbackHandler {

    /**
     * @see javax.security.auth.callback.CallbackHandler
     *          #handle(javax.security.auth.callback.Callback[])
     */
    public void handle(Callback[] callbacks)
    throws IOException, UnsupportedCallbackException {

       WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
       if (pc.getIdentifer().equals("system")) {

          pc.setPassword("hecmadmin");
          WsSession.setSessionData(WsSession.USER_NAME, pc.getIdentifer());
       } else {
           throw new ApplicationException("Invalid User Credentials", "T001");
       }
    }
}
