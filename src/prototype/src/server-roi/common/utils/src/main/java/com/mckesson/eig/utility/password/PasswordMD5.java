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

import com.mckesson.eig.utility.encoders.HexEncoder;
import com.mckesson.eig.utility.encryption.MD5Encryptor;
import com.mckesson.eig.utility.io.Stub;

/**
 * MD5 password encryption with hex string encoding.
 */
public class PasswordMD5 extends PasswordEncryptor {

	private static final long serialVersionUID = 1L;

	/**
     * called super class constructor.
     */
    public PasswordMD5() {
        super(new MD5Encryptor(), new HexEncoder());
    }

    /**
     * @return object of type <code>PasswordMD5</code>.
     */
    @Override
	public Password newInstance() {
        return new PasswordMD5();
    }

    /**
     * Some of the underlying JDK classes are not serializable, so avoid a
     * NotSerializableException on this class or classes that use this class.
     * @return object of type <code>Stub</code>.
     */
    private Object writeReplace() {
        return new Stub(getClass().getName());
    }
}
