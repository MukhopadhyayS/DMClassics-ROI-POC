/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.wsfw.test.axis;

import java.util.List;

import com.mckesson.eig.wsfw.security.authorization.AuthorizationStrategy;

/**
 * A stubbed out authorization strategy for unit testing. Currently this is wide
 * open and allows access for all services.
 * 
 * @author e6l5nl7
 * @date Nov 28, 2007
 * @since HECM 1.0; Nov 28, 2007
 */
public class MockAuthorizationStrategy implements AuthorizationStrategy {

    private boolean _auth = true;
    
    /**
     * always allow access for unit tests
     */
    public boolean authorize(String serviceName, String operationName, List< ? > params) {
        return _auth;
    }

    public boolean getAuthState() {
        return _auth;
    }
    
    public void setAuthState(boolean auth) {
        _auth = auth;
    }
}
