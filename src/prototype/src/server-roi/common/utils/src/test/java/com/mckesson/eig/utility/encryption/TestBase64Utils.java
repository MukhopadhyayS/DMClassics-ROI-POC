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
public class TestBase64Utils extends TestCase {

    private static final String PASSWORD = "swordfish";
    private static final String PASSWORD_2 = "harpo";
    private static final String PASSWORD_3 = "groucho";
    
    
    public TestBase64Utils(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    public void testEncoderDecoder() {
         
        String base64VersionOfPassword = Base64Utils.encode(PASSWORD.getBytes());
        try {
            byte[] rawPassword = Base64Utils.decode(base64VersionOfPassword);
            String reformedPassword = new String(rawPassword);
            assertEquals(PASSWORD, reformedPassword);
        } catch (Exception ex) {
            fail("should not have thrown exception: " + ex.getLocalizedMessage());
        }
       
        base64VersionOfPassword = Base64Utils.encode(PASSWORD_2.getBytes());
        try {
            byte[] rawPassword = Base64Utils.decode(base64VersionOfPassword);
            String reformedPassword = new String(rawPassword);
            assertEquals(PASSWORD_2, reformedPassword);
        } catch (Exception ex) {
            fail("should not have thrown exception: " + ex.getLocalizedMessage());
        }

        base64VersionOfPassword = Base64Utils.encode(PASSWORD_3.getBytes());
        try {
            byte[] rawPassword = Base64Utils.decode(base64VersionOfPassword);
            String reformedPassword = new String(rawPassword);
            assertEquals(PASSWORD_3, reformedPassword);
        } catch (Exception ex) {
            fail("should not have thrown exception: " + ex.getLocalizedMessage());
        }
    }
}
