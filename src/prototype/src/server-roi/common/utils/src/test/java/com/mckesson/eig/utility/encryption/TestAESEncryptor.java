/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.encryption;

import junit.framework.TestCase;

/**
 *
 */
public class TestAESEncryptor extends TestCase {

    private static final String KEY_TEXT             = "MarxBrothers";
    private static final String TOO_LARGE_KEY_TEXT   = "HarpoAndChicoAndGroucho";
    private static final String PASSWORD             = "swordfish";
    
    private static final String SUN_JCE = "SunJCE";
    private static final String BOGUS_JCE = "BogusProvider";

    private AESEncryptor _encryptor = null;
    
    public TestAESEncryptor(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        _encryptor = new AESEncryptor();
    }

    protected void tearDown() throws Exception {
        _encryptor = null;
    }
    
    public void testAESEncryptorAccess() {
        try {
            assertEquals(SUN_JCE, _encryptor.getProvider());
            
            _encryptor.setKey(KEY_TEXT);
            byte[] initVector = _encryptor.getIv();
            assertNull(initVector);
            
        } catch (Exception ex) {
            fail("Should not have seen any exception : " + ex.getMessage());
        }
        
        // deliberate failure
        try {
            _encryptor.setKey(TOO_LARGE_KEY_TEXT);
        } catch (Exception ex) {
            assertTrue(true);
            fail("Should not have thrown exception : " + ex.getMessage());
       }
        
        try {
            _encryptor.setProvider(BOGUS_JCE);
            fail("Should have thrown exception");
        } catch (Exception ex) {
            assertTrue(true);
        }

    }
    
    public void testEncryptDecrypt() {
        _encryptor.setKey(KEY_TEXT);
        try {
            byte[] clearText = PASSWORD.getBytes();
            byte[] cipherText = _encryptor.encrypt(clearText);
            _encryptor.getIv();
            byte[] decryptedText = _encryptor.decrypt(cipherText);
            String decryptedAsString = new String(decryptedText);
            assertEquals(PASSWORD, decryptedAsString);
 
            byte[] encrypted = _encryptor.encrypt(PASSWORD);
            String decrypted = _encryptor.decryptToString(encrypted);            
            assertEquals(PASSWORD, decrypted);

        } catch (Exception ex) {
            fail("No exceptions expected but got : " + ex.getMessage());
        }
    }
}
