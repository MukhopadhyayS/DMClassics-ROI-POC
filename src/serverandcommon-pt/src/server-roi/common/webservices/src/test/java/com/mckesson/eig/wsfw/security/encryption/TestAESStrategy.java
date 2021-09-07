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

package com.mckesson.eig.wsfw.security.encryption;

import junit.framework.TestCase;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.wsfw.exception.UsernameTokenException;

/**
 *
 */
public class TestAESStrategy extends TestCase {
    
    private static final String PASSWORD = "swordfish";
    private static final String USERNAME = "harpo";
    private static final String TIMESTAMP = "2008-03-06T22:19:56Z";
    private static final String TIMESTAMP_2 = "2009-02-16T09:50:48.507Z";
    private static final String MESSAGE = "Something bad happened.";
    
    private byte[] _latestIV = null;
    
    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public void testStrategyForTimeStampFormatWithMilliSeconds() {

        AESStrategy strategy = new AESStrategy();
        assertNotNull(strategy);
        String encryptedPW = encryptIt(PASSWORD, USERNAME, TIMESTAMP_2);
        assertNotNull(encryptedPW);
        String result = strategy.decryptPassword(USERNAME, encryptedPW, TIMESTAMP_2);
        assertEquals(PASSWORD, result);

        try {
            String noResult = strategy.decryptPassword(null, encryptedPW, TIMESTAMP_2);
            fail("Should have thrown an exception");
        } catch (UsernameTokenException ute) {
            assertTrue("Expected this exception " + ute.getMessage(), true);
        } catch (Exception ex) {
            fail("Should have thrown an UsernameTokenException, not " 
                    + ex.getMessage());            
        }
    }

    public void testEncryptedPasswordWithNull() {

        try {
            new AESStrategy().decryptPassword(USERNAME, null, TIMESTAMP_2);
            fail("Should have thrown an exception");
        } catch (UsernameTokenException ute) {
            assertTrue("Expected this exception " + ute.getMessage(), true);
        }
    }

    public void testStrategy() {
        AESStrategy strategy = new AESStrategy();
        assertNotNull(strategy);
        String encryptedPW = encryptIt(PASSWORD, USERNAME, TIMESTAMP);
        assertNotNull(encryptedPW);
        String result = strategy.decryptPassword(USERNAME, encryptedPW, TIMESTAMP);
        assertEquals(PASSWORD, result);
        
        try {
            String noResult = strategy.decryptPassword(null, encryptedPW, TIMESTAMP);
            fail("Should have thrown an exception");
        } catch (UsernameTokenException ute) {
            assertTrue("Expected this exception " + ute.getMessage(), true);
        } catch (Exception ex) {
            fail("Should have thrown an UsernameTokenException, not " 
                    + ex.getMessage());            
        }
    }
    
    public void testPasswordEncryption() {

        AESStrategy strategy = new AESStrategy();
        assertNotNull(strategy);
        String encryptedPW = strategy.encryptPassword(USERNAME, PASSWORD, TIMESTAMP);
        assertNotNull(encryptedPW);
        String result = strategy.decryptPassword(USERNAME, encryptedPW, TIMESTAMP);
        assertEquals(PASSWORD, result);

        try {
            strategy.encryptPassword(USERNAME, null, TIMESTAMP);
            fail("Should have thrown an exception");
        } catch (UsernameTokenException ute) {
            assertTrue("Expected this exception " + ute.getMessage(), true);
        }
    }

    public void testUsernameTokenException() {
        UsernameTokenException unte = 
            new UsernameTokenException(MESSAGE, ClientErrorCodes.VALIDATION_FAILED);
        assertNotNull(unte);
        
        ApplicationException ae = new ApplicationException();
        unte = 
            new UsernameTokenException(MESSAGE, ae, ClientErrorCodes.VALIDATION_FAILED);
        assertNotNull(unte);
    }
    
    private String encryptIt(String itemToEncrypt, String key1, String key2) {
        String encrypted = null;
        try {
            AESStrategy strategy = new AESStrategy();
        	encrypted = strategy.encryptPassword(key1, itemToEncrypt, key2);        	
        } catch (Exception ex) {
            fail("encryption for test setup should not have failed : " + ex.getMessage());
        }            
        return encrypted;
            
    }
}
