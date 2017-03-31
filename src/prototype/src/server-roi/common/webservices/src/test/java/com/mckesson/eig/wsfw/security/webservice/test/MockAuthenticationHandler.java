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

package com.mckesson.eig.wsfw.security.webservice.test;

import com.mckesson.eig.wsfw.security.AbstractBaseCallbackHandler;
import com.mckesson.eig.wsfw.session.WsSession;


/**
 * @author N.Shah Ghazni
 * @date   Feb 6, 2008
 * @since  HECM 1.0; Feb 6, 2008
 */
public class MockAuthenticationHandler extends AbstractBaseCallbackHandler  {
    
    /**
     * 
     * @see com.mckesson.eig.wsfw.security.AbstractBaseCallbackHandler#processCallback
     *                                                          (java.lang.String, java.lang.String)
     */
    @Override
    protected void processCallback(String userName, String password) {
        WsSession.setSessionData(WsSession.APP_ID, null);
    }
}
