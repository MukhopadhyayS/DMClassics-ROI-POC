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
package com.mckesson.eig.utility.util;
import com.mckesson.eig.utility.concurrent.ConcurrencyInterruptedException;
import com.mckesson.eig.utility.testing.UnitTest;

/**
 *Test Case to test the class ThreadUtilities.
 */
public class TestThreadUtilities extends UnitTest {
	
	
    /**
     * private variable.
     */
    private static final int TIME_SLEEP = 3000;
    /**
     * Constructs the class.
     * @param name
     * Name of the test case
     */
    public TestThreadUtilities(String name) {
        super(name);
    }
    
    /**
     * Tests the sleep method of the class ThreadUtilities.
     */
    public void testsleep() {
    	final int ms = 1000;
    	ThreadUtilities.sleep(ms);
    }
    
    /**
     * Calls the private  constructor.
     */
    public void testConstructor() {
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(ThreadUtilities.class));
    }
    
    /**
     * Tests if constructor is private.
     */
    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(ThreadUtilities.class));
        ReflectionUtilities.callPrivateConstructor(ThreadUtilities.class);
    }
        /**
         * tests sleep().
         */
        public void testSleep() {
        MockTestThread thread = new MockTestThread(Thread.currentThread());
        thread.start();
        try {
            ThreadUtilities.sleep(TIME_SLEEP);
        } catch (ConcurrencyInterruptedException e) {
            e.printStackTrace();

        }
    }
}
