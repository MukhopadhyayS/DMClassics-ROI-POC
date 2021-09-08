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

/**
 *
 */
public class TestPassthroughEncryptionStrategy extends TestCase {
    
    private static final String PASSWORD = "swordfish";
    private static final String PASSWORD_2 = "harpo";
    
    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    public void testStrategy() {
        PassthroughStrategy strategy = new PassthroughStrategy();
        assertNotNull(strategy);
        String result = strategy.decryptPassword(null, PASSWORD, null);
        assertEquals(PASSWORD, result);
        result = strategy.decryptPassword(PASSWORD, PASSWORD, PASSWORD);
        assertEquals(PASSWORD, result);
        result = strategy.decryptPassword(null, PASSWORD_2, null);
        assertEquals(PASSWORD_2, result);
        result = strategy.encryptPassword(null, PASSWORD, result);
        assertEquals(PASSWORD, result);
    }
}
