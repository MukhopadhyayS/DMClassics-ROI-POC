/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.password;

public class PasswordPassThru implements Password {

	private static final long serialVersionUID = 1L;

	/**
     * @param password
     *            Passed as an argument of type <code>String</code>.
     *            This password returns by this method.
     * @return object whatever passed.
     */
    public String encrypt(String password) {
        return password;
    }

    /**
     * @return object of type <code>PasswordThru</code>.
     *        Returns object of <code>PasswordThru</code> class.
     */
    public Password newInstance() {
        return new PasswordPassThru();
    }
}
