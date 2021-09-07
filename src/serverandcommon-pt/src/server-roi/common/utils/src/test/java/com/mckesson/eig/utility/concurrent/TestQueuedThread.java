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
import com.mckesson.eig.utility.values.Minutes;
import junit.framework.Test;
import junit.framework.TestCase;
/**
 * Test Case to test the class QueuedThread.
 */
public class TestQueuedThread extends TestCase {

    /**
     * Holds an instance of type DaemonThreadFactory.
     */
    private DaemonThreadFactory _dth = null;

    /**
     * Holds an instance of type Minutes.
     */
    private Minutes _min = null;

    /**
     * Constructs the test case with the given name.
     * 
     * @param name
     *            Test Case Name
     */
    public TestQueuedThread(String name) {
        super(name);
    }

    /**
     * Creates a suite that tests one class and with one test.
     * 
     * @return Coverage Suite
     */
    public static Test suite() {
        return new CoverageSuite(TestQueuedThread.class, QueuedThread.class);
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
        final int num = 3;
        _min = new Minutes(num);
        _dth = new DaemonThreadFactory();
    }
    
    /**
     * This will remove all the data associated with the test.
     * 
     * @throws Exception
     *             if the tear down is not made properly.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        _dth = null;
    }
   
    /**
     * Tests the constructor.
     */
    public void testConstructor() {
        assertNotNull(new QueuedThread());
    }
   
    /**
     * Tests the Constructor with value for keepAlive.
     */
    public void testConstructorWithKeepAlive() {
        assertNotNull(new QueuedThread(_min));
    }
    
    /**
     * Tests the Constructor with keepAlive and ThreadFactory.
     */
    public void testConstructorWithKeepAliveAndThreadFactory() {
        assertNotNull(new QueuedThread(_min, _dth));

    }
}
