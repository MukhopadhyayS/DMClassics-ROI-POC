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

import junit.framework.Test;
import junit.framework.TestCase;
import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestEncryptor extends TestCase {

    private Encryptor _encryptor;

    private static final String UPPER_CASE_OOPS = "OOPS";
    private static final String LOWER_CASE_OOPS = "oops";
    private static final String UPPER_CASE_ADMIN = "ADMIN";
    private static final String LOWER_CASE_ADMIN = "admin";
    private static final String STRING_LCD = "CLD";
    private static final String STRING_NDXUP = "NDXUP";

    private static byte[] _bytesNDXUP;
    private static byte[] _bytesCLD;
    private static byte[] _bytesLowerCaseADMIN;
    private static byte[] _bytesUpperCaseADMIN;
    private static byte[] _bytesLowerCaseOOPS;
    private static byte[] _bytesUpperCaseOOPS;

    static {
        EncryptionTestHelper md = new EncryptionTestHelper();
   
        _bytesUpperCaseOOPS = md.encrypt("OOPS");
        _bytesLowerCaseOOPS = md.encrypt("oops");
        _bytesUpperCaseADMIN = md.encrypt("ADMIN");
        _bytesLowerCaseADMIN = md.encrypt("admin");
        _bytesCLD = md.encrypt("CLD");
        _bytesNDXUP = md.encrypt("NDXUP");
    }

    private static String[] _stringKEYS = {UPPER_CASE_OOPS, LOWER_CASE_OOPS,
        UPPER_CASE_ADMIN, LOWER_CASE_ADMIN, STRING_LCD, STRING_NDXUP};

    private static byte[][] _byteEXPECTED = {_bytesUpperCaseOOPS,
        _bytesLowerCaseOOPS, _bytesUpperCaseADMIN, _bytesLowerCaseADMIN,
        _bytesCLD, _bytesNDXUP};
    /**
     * Constructor for TestEncryptor.
     * 
     * @param arg0
     */
    public TestEncryptor(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestEncryptor.class, Encryptor.class);
    }

    protected void setUp() throws Exception {
        _encryptor = getEncryptor();
    }

    protected void tearDown() throws Exception {
        _encryptor = null;
    }

    protected Encryptor getEncryptor() {
        return new Encryptor("MD5");
    }

    /**
     * @return new instance of the encryptor
     */
    protected Encryptor getNewInstance() {
        Encryptor newencryptor = getEncryptor();
        return (newencryptor.newInstance());
    }

    protected String[] getKeys() {
        return _stringKEYS;
    }

    protected byte[][] getBytes() {
        return _byteEXPECTED;
    }

    public void testEncryptor() {
        assertNotNull(_encryptor);
    }
    public void testNewInstance() {
        assertNotNull(getNewInstance());
    }

    public void testEncryptorWithFakeAlgorithm() {
        try {
            new Encryptor("foo");
            fail();
        } catch (EncryptionException e) {
            /*
             * e.printStackTrace(); fail("Failed in
             * TestEncryptor:testEncryptorWithFakeAlgorithm");
             */
            assertTrue(true);
        }
    }

    public void testEncrypt() {
        String[] keys = getKeys();
        byte[][] expected = getBytes();

        for (int i = 0; i < keys.length; i++) {
            assertEquals(expected[i], _encryptor.encrypt(keys[i]));
        }
    }

    public void testEncryptOfNull() {
        assertNull(_encryptor.encrypt(null));
    }

    public void assertEquals(byte[] one, byte[] two) {
        assertTrue(java.util.Arrays.equals(one, two));
    }

    public void print(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            System.out.print((int) bytes[i] + ", ");
        }
        System.out.println();
    }

    public void testEncryptDataNull() {
        String key = "any key";
        assertNull(_encryptor.encrypt(null, key));
    }

    public void testEncryptKeyNull() {
        String data = "any key";
        assertNull(_encryptor.encrypt(data, null));
    }

    public void testEncryptWithKey() {
        EncryptionTestHelper md = new EncryptionTestHelper();
        
        String data1 = "admin20050310093728";
        String key1 = "mckesson";
        byte[] expect1 = md.encrypt(data1, key1);
        assertEquals(expect1, _encryptor.encrypt(data1, key1));

        String data2 = "admin20050310093728";
        String key2 = "portal";
        byte[] expect2 = md.encrypt(data2, key2);
        assertEquals(expect2, _encryptor.encrypt(data2, key2));

        String data3 = "michaelyeo20050310193728";
        String key3 = "portal";
        byte[] expect3 = md.encrypt(data3, key3);
        assertEquals(expect3, _encryptor.encrypt(data3, key3));

        String data4 = "porta003529Al20050310093728";
        String key4 = "mckesson";
        byte[] expect4 = md.encrypt(data4, key4);
        assertEquals(expect4, _encryptor.encrypt(data4, key4));
    }

}
