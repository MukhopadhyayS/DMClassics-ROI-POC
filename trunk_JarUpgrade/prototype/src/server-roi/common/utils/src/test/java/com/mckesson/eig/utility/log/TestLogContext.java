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
package com.mckesson.eig.utility.log;

import com.mckesson.eig.utility.util.ReflectionUtilities;

import junit.framework.TestCase;

public class TestLogContext extends TestCase {
	
	/**
     * Set up the test. Creates an instance of the class that needs to be
     * tested.
     * 
     * @throws Exception
     *             if the set up is not made properly.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    /**
     * Calls the private  constructor.
     */
    public void testConstructor() {
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(LogContext.class));
    }
    
    /**
     * Tests remove method with string value of the class LogContext.
     */
    public void testremovewithoutNull() {
    	String strKey = "String key";
    	LogContext.remove(strKey);
    }
    
    /**
     * Tests remove method with null value of the class LogContext;
     */
    public void testremovewithNull() {
    	String strKey = null;
    	LogContext.remove(strKey);
    }
    
    /**
     * Test the method put of class LogContext.
     */
    public void testput() {
    	String strKey = null;
    	Object object = null;
    	LogContext.put(strKey, object);
    }
    
    /**
     * Test the get method of class LogContext.
     */
    public void testGet() {
        
        String key = "key";
        String object = "value";
        LogContext.put(key, object);
        assertEquals(object, (String) LogContext.get(key));
        LogContext.remove(key);
    }
    
    /**
     * Test the get method with null key.
     */
    public void testGetWithNull() {
        
        String key = null;
        assertEquals(null, LogContext.get(key));
    }
    
    /**
     * This will remove all the data associated with the test.
     * 
     * @throws Exception
     *             if the tear down is not made properly.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
