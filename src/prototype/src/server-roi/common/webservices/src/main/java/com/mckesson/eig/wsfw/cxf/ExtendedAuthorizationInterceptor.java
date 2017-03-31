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

package com.mckesson.eig.wsfw.cxf;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.security.authorization.AuthorizationStrategy;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author N.Shah Ghazni
 * @date   Dec 29, 2008
 */
public class ExtendedAuthorizationInterceptor extends AuthorizationInterceptor {

    /**
     * Delimiter used to form an authorizer key from application name
     * and authorizer name
     */
    private static final String AUTHORIZE_KEY_DEL = ".";

    /**
     * Constant name that denotes the <code>Authorizer</code>
     * in spring configuration file
     */
    private static final String KEY_AUTHORIZER = "authorizer";

    @Override
    protected AuthorizationStrategy getAuthorizationServiceProvider() {

        String appID = (String) WsSession.getSessionData(WsSession.APP_ID);
        if (StringUtilities.hasContent(appID)) {

            return (AuthorizationStrategy) 
                    SpringUtilities.getInstance()   
                                   .getBeanFactory()
                                   .getBean(KEY_AUTHORIZER + AUTHORIZE_KEY_DEL + appID);
        }
        throw new ApplicationException("Unable to find the authorization service provider for key  "
                                       + "'" + KEY_AUTHORIZER + AUTHORIZE_KEY_DEL + appID + "'");
    }
}
