/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.utility.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Implements the specified algorithm.
 * 
 */
public class Encryptor {

    /**
     * Digest Algorithm.
     */
    private final String _algorithm;
    /**
     * instance of <code>MessageDigest</code>.
     */
    private final MessageDigest _messageDigest;

    /**
     * Generates a MessageDigest object that implements the specified digest
     * algorithm.
     * 
     * @param algorithm
     *            digest algorithm.
     */
    public Encryptor(String algorithm) {
        try {
            _algorithm = algorithm;
            _messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(
                    "Could not find encryption algorithm: " + algorithm, e);
        }
    }

    /**
     * Returns the new instance of this class.
     * 
     * @return new instance of this <code>class</code>.
     */
    public Encryptor newInstance() {
        return new Encryptor(_algorithm);
    }

    /**
     * Updates the digest using the specified array of bytes.
     * 
     * @param data
     *            data.
     * @return The resultant byte array
     */
    public byte[] encrypt(String data) {
        if (data == null) {
            return null;
        }
        _messageDigest.update(data.getBytes());
        return _messageDigest.digest();
    }

    /**
     * Performs a final update on the digest using the specified array of bytes,
     * then completes the digest computation.
     * 
     * @param data
     *            data.
     * @param key
     *            input to be updated before the digest is completed.
     * @return The resultant byte array
     */
    public byte[] encrypt(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        _messageDigest.update(data.getBytes());
        return _messageDigest.digest(key.getBytes());
    }
}
