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

package com.mckesson.eig.utility.encryption.url;

import com.mckesson.eig.utility.encoders.Encoder;
import com.mckesson.eig.utility.encoders.HexEncoder;
import com.mckesson.eig.utility.encryption.Encryptor;
import com.mckesson.eig.utility.encryption.MD5Encryptor;

/**
 * Its a <code>final</code> class which instantiates <code>HexEncoder</code>
 * and <code>MD5Encryptor</code> and makes an secured encoding.
 * 
 */
public final class SecureURL {

    /**
     * Sole constructor(package specific).
     */
    protected SecureURL() {

    }

    /**
     * Ensures the exsistance of data before encoding .
     * 
     * @param data
     *            data.
     * @param key
     *            input to be updated before the digest is completed.
     * @return String after encoding .
     */
    public static String encode(String data, String key) {
        if (data == null) {
            throw new NullPointerException("data can't be null");
        }
        if (key == null) {
            throw new NullPointerException("data can't be null");
        }

        Encryptor encryptor = new MD5Encryptor();
        Encoder encoder = new HexEncoder();

        return encoder.encode(encryptor.encrypt(data, key));
    }

}
