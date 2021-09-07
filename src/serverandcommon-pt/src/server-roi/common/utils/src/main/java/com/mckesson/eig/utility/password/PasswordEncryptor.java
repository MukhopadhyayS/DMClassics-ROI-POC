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

import com.mckesson.eig.utility.encoders.Encoder;
import com.mckesson.eig.utility.encryption.Encryptor;

public class PasswordEncryptor implements Password {

	private static final long serialVersionUID = 865620661582292398L;

	private final Encryptor _encryptor;

	private final Encoder _encoder;

    /**
     * This implementation needs an encryptor and an encoder for it to function.
     */
    /**
     * @param encryptor
     *            Passed as an argument of type <code>Encryptor</code>.
     * @param encoder
     *            Passed as an argument of type <code>Encoder</code>.
     */
    public PasswordEncryptor(Encryptor encryptor, Encoder encoder) {
        if (encryptor == null) {
            throw new NullPointerException("Encryptor cannot be null");
        }
        if (encoder == null) {
            throw new NullPointerException("Encoder cannot be null");
        }
        _encryptor = encryptor;
        _encoder = encoder;
    }

    /**
     * This implementation uses the encryptor to encrypt the password into bytes
     * and then uses the encoder to encode the data into a String.
     *
     * @param password
     *            The password to encrypt
     * @return String null if the data passed in is null or the encrypted
     *         password.
     */
    public String encrypt(String password) {
        if (password == null) {
            return null;
        }
        return _encoder.encode(_encryptor.encrypt(password));
    }

    /**
     * @return object of type <code>PasswordEncryptor</code>. returns object
     *         of <code>PasswordEncryptor</code> class.
     */
    public Password newInstance() {
        return new PasswordEncryptor(_encryptor.newInstance(), _encoder.newInstance());
    }
}
