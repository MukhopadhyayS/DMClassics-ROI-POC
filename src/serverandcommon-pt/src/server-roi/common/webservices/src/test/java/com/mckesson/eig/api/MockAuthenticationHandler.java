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
package com.mckesson.eig.api;

import com.mckesson.eig.wsfw.security.AbstractBaseCallbackHandler;

/**
 * @author Sree Sabesh Rajkumar
 * @date Oct 01, 2009
 * @since WSClient 1.0
 */
public class MockAuthenticationHandler extends AbstractBaseCallbackHandler {

    @Override
    protected void processCallback(String userName, String password) {
    	
        /*
         * dummy authentication
         */
        return;
    }
}
