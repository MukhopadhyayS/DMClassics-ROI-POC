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

import com.mckesson.eig.utility.testing.CoverageSuite;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * Test case to test the class SyncTask.
 */
public class TestSyncTask extends TestCase {

    /**
     * Holds an instance of type Thread.
     */
    private Thread _threadOne;

    /**
     * Holds an instance of type Thread.
     */
    private Thread _threadTwo;
    
    /**
     * Holds an instance of type WrappedRunnable.
     */
    private WrappedRunnable _wrappedRunnable;
    
    /**
     * Constructs the test case with the given name.
     * 
     * @param name
     *            Name of the test case
     */
    public TestSyncTask(String name) {
        super(name);
    }
    
    /**
     * Creates a suite that tests one class and with one test.
     * 
     * @return Coverage Suite
     */
    public static Test suite() {
        return new CoverageSuite(TestSyncTask.class, SyncTask.class);
    }
    
    /**
     * Set up the test. Creates an instance of the class that need to be tested.
     * 
     * @throws Exception
     *             if the set up is not made properly.
     */
    protected void setUp() throws Exception {
        super.setUp();
        MockClass mockClass = new MockClass();
        _wrappedRunnable = new WrappedRunnable(mockClass);
        SynchronizationMockClass synchronizationMockClass = new SynchronizationMockClass();
        _threadOne = new Thread(new SyncTask(synchronizationMockClass,
                _wrappedRunnable));
        _threadTwo = new Thread(new SyncTask(synchronizationMockClass,
                _wrappedRunnable));
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
     * Tests the run() method.
     */
    public void testRun() {
        assertNotNull(_threadOne);
        assertNotNull(_threadTwo);
        try {
            _threadOne.run();
            _threadTwo.run();
        } catch (Exception e) {
            fail();
        }
    }
    
    /**
     * Tests SyncTask with task as null.
     */
    public void testRunWithNullTask() {
        SynchronizationMockClass synchronizationMockClass = new SynchronizationMockClass();
        try {
            _threadOne = new Thread(
                    new SyncTask(synchronizationMockClass, null));
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tests SyncTask with all null parameters.
     */
    public void testRunWithNull() {
        try {
            _threadOne = new Thread(new SyncTask(null, null));
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Inner class that implements the interface Synchronization.
     */
    public class SynchronizationMockClass implements Synchronization {
        
        /**
         * Contains an SOP to test if the method is executed. Overrides this
         * method from the interface Synchronization.
         */
        public void acquire() {
            System.out.println("In Acquire==>"
                    + this.getClass().getSimpleName());
        }
       
        /**
         * Contains an SOP to test if the method is executed. Overrides this
         * method from the interface Synchronization.
         */
        public void release() {
            System.out.println("In Release==>"
                    + this.getClass().getCanonicalName());
        }
    }
}
