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
* <p><code>Authenticator</code> is used to perform the validation of
* user with the specified user name or password or even it can be
* by the ticket.</p>
*
*
* @author N.Shah Ghazni
* @date   Dec 15, 2007
* @since  HECM 1.0
*/
public interface Authenticator {

    /**
     * Constant name that denotes the <code>Authenticator</code>
     * in spring context
     */
    String NAME = "authenticator";

    /**
     * Constant name that denotes the ticket in the request
     */
    String KEY_TICKET  = "AuthTicket";
    
    /**
     * Constant name that denotes the transaction id in the request
     */
    String KEY_TRANSACTION_ID = "TransactionID";

    /**
     * Constant name that denotes the user id in the request
     */
    String KEY_USERNAME = "USERNAME";
    String KEY_USERNAME_ALT = "UserName";

    /**
     * Constant name that denotes the password in the request
     */
    String KEY_PASSWORD = "PASSWORD";
    String KEY_PASSWORD_ALT  = "Password";

    String KEY_TIMESTAMP = "TIMESTAMP";

    /**
     * This method validates the username, password and ticket id and checks for
     * authentication. if ticket id is valid then the request goes to the further
     * process, otherwise it authenticate the user.
     *
     */
    void validate();

    /**
     *
     * Gets the name of the application to which this authenticator belongs to.
     */
    String getAppID();
}
