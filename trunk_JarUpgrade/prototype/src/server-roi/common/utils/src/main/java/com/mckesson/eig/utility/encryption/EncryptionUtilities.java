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

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * @author Kenneth Partlow
 * 
 */
public final class EncryptionUtilities {

    /**
     * Sole constructor.
     */
    private EncryptionUtilities() {
    }

    public static KeyGenerator getKeyGenerator(String algorithm) {
        try {
            return KeyGenerator.getInstance(algorithm);
        } catch (Throwable t) {
            throw new EncryptionException(t);
        }
    }

    public static Cipher getCipher(String algorithm) {
        try {
            return Cipher.getInstance(algorithm);
        } catch (Throwable t) {
            throw new EncryptionException(t);
        }
    }

    public static byte[] encrypt(Cipher cipher, Key key, byte[] data) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Throwable t) {
            throw new EncryptionException(t);
        }
    }

    public static byte[] decrypt(Cipher cipher, Key key, byte[] data) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Throwable t) {
            throw new EncryptionException(t);
        }
    }
}
