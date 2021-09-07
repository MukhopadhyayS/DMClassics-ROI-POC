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

package com.mckesson.eig.utility.password;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.encoders.HexEncoder;
import com.mckesson.eig.utility.encryption.MD5Encryptor;
import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestPasswordEncryptor extends TestCase {

    private PasswordEncryptor _encryptor;

    private static final String PIN1234 = "1234";
    private static final String ENCRYPTED_PIN1234 =
        "81DC9BDB52D04DC20036DBD8313ED055";

    public TestPasswordEncryptor(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestPasswordEncryptor.class,
                PasswordEncryptor.class);
    }

    protected void setUp() throws Exception {
        _encryptor = new PasswordEncryptor(new MD5Encryptor(), 
                new HexEncoder());
    }

    protected void tearDown() throws Exception {
        _encryptor = null;
    }

    public void testPasswordEncryptor() {
        _encryptor = new PasswordEncryptor(new MD5Encryptor(),
                new HexEncoder());
        assertNotNull(_encryptor);
    }
    public void testPasswordEncryptorNewInstance() {
        _encryptor = new PasswordEncryptor(new MD5Encryptor(),
                new HexEncoder());
        
        assertNotNull(_encryptor.newInstance());
    }

    public void testPasswordEncryptorWithInvalidEncrytptor() {
        try {
            new PasswordEncryptor(null, new HexEncoder());
            fail("PasswordEncryptor cannot be null");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void testPasswordEncryptorWithInvalidEncoder() {
        try {
            new PasswordEncryptor(new MD5Encryptor(), null);
            fail("HexEncoder cannot be null");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void testEncrypt() {
        assertEquals(ENCRYPTED_PIN1234, _encryptor.encrypt(PIN1234));
    }

    public void testEncryptWithNull() {
        assertNull(_encryptor.encrypt(null));
    }

}
