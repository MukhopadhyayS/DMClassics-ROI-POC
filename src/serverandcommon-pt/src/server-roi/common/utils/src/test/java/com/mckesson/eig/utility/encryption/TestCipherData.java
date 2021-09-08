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

 
public class TestCipherData extends TestCase {
    
    private static final int TEST_LENGTH = 20;
    
    public TestCipherData(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    public void testCipherDataConstructors() {
        CipherData cData = new CipherData();        
        assertNotNull(cData.getInitializationVector());
        assertNull(cData.getCipherText());
        
        cData = new CipherData(TEST_LENGTH);
        assertNotNull(cData.getInitializationVector());
        assertNotNull(cData.getCipherText());
        assertEquals(TEST_LENGTH, cData.getCipherText().length);
        
        String initVector = new String("0123456789abcdef");
        String cipherText = new String("def");
        
        cData = new CipherData(initVector.getBytes(), cipherText.getBytes());
        assertNotNull(cData.getInitializationVector());
        assertNotNull(cData.getCipherText());
        
        String combined = initVector + cipherText;
        cData = new CipherData(combined.getBytes());
        assertNotNull(cData.getInitializationVector());
        assertNotNull(cData.getCipherText());
        
        
        
    }
}
