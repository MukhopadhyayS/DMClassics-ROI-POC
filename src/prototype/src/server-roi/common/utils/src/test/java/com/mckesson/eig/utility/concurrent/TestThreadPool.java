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
package com.mckesson.eig.utility.concurrent;

import junit.framework.TestCase;

/**
 * Test Case to test the class ThreadPool.
 */
public class TestThreadPool extends TestCase {
   
    /**
     * The maximum pool size.
     */
    private int _max;
    
    /**
     * The minimum pool size.
     */
    private int _min;
    
    /**
     * Holds the refernce of the class ThreadPool class.
     */
    private ThreadPool _threadPool = null; 
    
    /**
     * Constructs the test case with the given name.
     * @param name
     *            Test Case Name
     */
    public TestThreadPool(String name) {
        super(name);
    }
   
    /**
     * Set up the test. Creates an instance of the class that's need to be
     * tested.
     * @throws Exception if the set up is not made properly.
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        final int num1 = 10;
        final int num2 = 5;
        _max = num1;
        _min = num2;
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
    
    /**
     * Tests the constructor.
     */
    public void testGetThreadPool() {
        assertNotNull(new ThreadPool());
    }
    
    /**
     * Tests the constructor with max and min pool size.
     */
    public void testGetThreadPoolWithMax() {
        assertNotNull(new ThreadPool(_min, _max));
    }
    
    /**
     * Tests the method run of the class AbstractPooledRunner class.
     */
    public void testrun() {
    	_threadPool = new ThreadPool();
    	Runnable r = null;
    	_threadPool.run(r);
    }
    
}



