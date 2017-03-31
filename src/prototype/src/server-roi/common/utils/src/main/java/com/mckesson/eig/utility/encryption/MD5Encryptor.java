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

/**
 * Specifies the algorithm for encryption.
 * 
 */
public class MD5Encryptor extends Encryptor {

    /**
     * Digest algorithm.
     */
    private static final String ALGORITHM = "MD5";

    /**
     * Generates a MessageDigest object that implements the specified digest
     * algorithm.
     */
    public MD5Encryptor() {
        super(ALGORITHM);
    }

    /**
     * Returns the new instance of this class.
     * 
     * @return new instance of this class.
     */
    public Encryptor newInstance() {
        return new MD5Encryptor();
    }
}
