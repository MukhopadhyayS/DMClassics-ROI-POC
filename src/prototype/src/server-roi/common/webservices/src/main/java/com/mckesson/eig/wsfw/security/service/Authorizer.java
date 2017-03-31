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
package com.mckesson.eig.wsfw.security.service;


/**
* <code>Authorizer</code> checks the user has rights to access the
* service operation.
*
* @author N.Shah Ghazni
* @date   Dec 15, 2007
* @since  HECM 1.0
*/
public interface Authorizer {

    /**
     * Constant name that denotes the <code>Authorizer</code>
     * in spring configuration file
     */
    String NAME = "authorizer";

    /**
     * Constant name that used to hold the name of the application
     * which uses the <code>Authorizer</code>
     */
    //String APP_ID = "AppId";

    /**
     * Constant name that used to hold the service name in the session
     *
     */
    String SERVICE_NAME = "ServiceName";

    /**
     * Constant name that used to hold the security header in the session
     *
     */
    String SECURITY_HEADER = "SecurityHeader";

    /**
     * Constant name that used to hold the service name in the session
     */
    String OPERATION_NAME = "OperationName";

    /**
     *
     * Gets the name of the application to which this authenticator belongs to.
     */
    String getAppID();

    /**
     * This method validates the username, password and ticket id and checks for
     * authentication. if ticket id is valid then the request goes to the further
     * process, otherwise it authenticate the user.
     *
     */
    void validate();
}
