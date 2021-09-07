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
 * Test case to test the class DaemonThreadFactory.
 */
public class TestDaemonThreadFactory extends TestCase {

    /**
     * Instance of the class DaemonThreadFactory.
     */
    private DaemonThreadFactory _dt;

    /**
     * Constructs the test case with the given name.
     * 
     * @param name
     *            Test Case Name
     */
    public TestDaemonThreadFactory(String name) {
        super(name);
    }

    /**
     * Creates a suite that tests one class and with one test.
     * 
     * @return A test suite to test the given class
     */
    public static Test suite() {
        return new CoverageSuite(TestDaemonThreadFactory.class,
                DaemonThreadFactory.class);
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
        _dt = new DaemonThreadFactory();
    }

    /**
     * This will remove all the data associated with this test.
     * 
     * @throws Exception
     *             if the tear down is not made properly.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        _dt = null;
    }
    
    /**
     * Tests the constructor of class DaemonThreadFactory.
     */
    public void testConstructor() {
        assertNotNull(new DaemonThreadFactory());
    }
    
    /**
     * Tests the method makeLowerPriority().
     */
    public void testmakeLowerPriority() {
        assertNotNull(DaemonThreadFactory.makeLowerPriority());
    }
    
    /**
     * Tests the method <code>makeMinPriority</code>.
     */
    public void testMakeMinPriority() {
        assertNotNull(DaemonThreadFactory.makeMinPriority());
    }
    
    /**
     * Tests the method <code>newThread</code>.
     */
    public void testNewThread() {
        Runnable cmd = new MockClass();
        assertNotNull(_dt.newThread(cmd));
        assertNotSame(_dt.newThread(cmd), _dt.newThread(cmd));
        assertEquals(_dt.newThread(cmd).getClass(), _dt.newThread(cmd).getClass());
    }
}
