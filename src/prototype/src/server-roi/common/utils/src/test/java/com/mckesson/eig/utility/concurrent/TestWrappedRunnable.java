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

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;
/**
 * Test Case to test the class WrappedRunnable.
 */
public class TestWrappedRunnable extends UnitTest {
   
    /**
     * Hold an instance  of type WrappedRunnable.
     */
    private WrappedRunnable _wrappedRunnable;
    
    /**
     * Hold an instance of type Runnable.
     */
    private Runnable _r;
    
    /**Constructs the test case with the given name.
     * @param name
     * Test Case Name
     */
    public TestWrappedRunnable(String name) {
        super(name);
    }
    
    /**
     * Creates a suite that tests one class and with one test.
     * @return
     *         Coverage Suite
     */
    public static Test suite() {
        return new CoverageSuite(TestWrappedRunnable.class, WrappedRunnable.class);
    } 
   
    /**
     * Set up the test. Creates an instance of the class that needs to be
     * tested.
     * 
     * @throws Exception
     *             if the set up is not made properly.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _r = new MockClass();
        _wrappedRunnable = new WrappedRunnable(_r); 
     }
   
    /**
     * This will remove all the data associated with the test.
     * 
     * @throws Exception
     *             if the tear down is not made properly.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        _wrappedRunnable = null;
    }
    
    /**
     * Tests the constructor.
     */
    public void testConstructor() {
        try {
            _r = new MockClass();
            _wrappedRunnable = new WrappedRunnable(_r);
            _wrappedRunnable.run();            
        } catch (Error e) {
            e.printStackTrace();
            fail();
        }
    }
}
