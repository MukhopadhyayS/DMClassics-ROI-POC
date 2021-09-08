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
package com.mckesson.eig.wsfw.security.service.test;

import com.mckesson.eig.wsfw.security.service.Authorizer;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author N.Shah Ghazni
 * @date   Feb 4, 2008
 * @since  HECM 1.0; Feb 4, 2008
 */
public class MockAuthorizer implements Authorizer {

    /**
     * @see com.mckesson.eig.security.service.Authorizer#validate()
     */
    public void validate() {
    }

    /**
     * @see com.mckesson.eig.security.service.Authorizer#getAppID()
     */
    public String getAppID() {
        return WsSession.getSessionData(WsSession.APP_ID).toString();
    }
}
