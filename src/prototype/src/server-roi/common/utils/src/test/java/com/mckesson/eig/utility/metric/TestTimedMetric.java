/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.metric;

import junit.framework.TestCase;

/**
 * Tests the <code>TimedMetric</code> class. Tests to validate the method
 * which returns a new instance of the testable class and method which resets
 * the <code>start</code> value to Current time in milliseconds).
 */
public class TestTimedMetric extends TestCase {
    /**
     * Instance of the testable class.
     */
    private TimedMetric _timedMetric;

    /**
     * Its the Message that has to be appeneded.
     */
    private static final String OBJECT = "Message For Testing";

    /**
     * Constructs the test case with the given name.
     *
     * @param testName
     *            Test case name.
     */
    public TestTimedMetric(String testName) {
        super(testName);
    }

    /**
     * Set up the test. Creates an instance of the class that's need to be
     * tested.
     *
     * @throws Exception
     *             if the set up is not made properly.
     */
    @Override
	protected void setUp() throws Exception {
        _timedMetric = TimedMetric.start();
    }

    /**
     * This will remove all the data associated with the test.
     *
     * @throws Exception
     *             if the tear down is not made properly.
     */
    @Override
	protected void tearDown() throws Exception {
        _timedMetric = null;
    }

    /**
     * Tests the constructor of the testable class.It instantiates the class and
     * confirms that its not <code>null</code>.
     */
    public void testTimedMetric() {
        _timedMetric = TimedMetric.start();
        assertNotNull(_timedMetric);
    }

    /**
     * Testing the <code>start</code> method. Checks whether new instance of
     * the class is returned or not.
     */
    public void testTimedMetricStart() {
        assertNotSame(_timedMetric, TimedMetric.start());
    }

    /**
     * Tests the <code>resetTimer</code> method.Tests whether the method
     * resets the <code>start</code> value or not.
     */
    public void testResetTimer() {
        long startBeforeReset = _timedMetric.getStartTime();
        _timedMetric.resetTimer();
        long startAfterReset = _timedMetric.getStartTime();
        assertNotSame(startBeforeReset, startAfterReset);
    }

    /**
     * Tests the logMetric method.Tests whether the method
     * logs the message or not.
     */
    public void testLogMetric() {
        try {
            _timedMetric.logMetric(OBJECT);
        } catch (Exception e) {
            fail();
        }
    }
 }
