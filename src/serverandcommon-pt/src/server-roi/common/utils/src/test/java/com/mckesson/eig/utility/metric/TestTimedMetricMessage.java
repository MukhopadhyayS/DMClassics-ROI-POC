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
package com.mckesson.eig.utility.metric;
import junit.framework.TestCase;
/**
 * Tests the <code>TimedMetricMessage</code> class. Tests to validate whether
 * the values are appended and returned as a string.
 */
public class TestTimedMetricMessage extends TestCase {
    /**
     * Instance of the testable class.
     */
    private TimedMetricMessage _timedMetricMessage;
    /**
     * Start Time in milliseconds (Random value for testing).
     */
    private static final long START = 1234567890L;
    /**
     * Stop time in milliseconds (Random value for testing).
     */
    private static final long STOP = 1234567895L;
    /**
     * Its the Message that has to be appeneded.
     */
    private static final String OBJECT = "Message For Testing";
    /**
     * Its a test message.
     */
    private static final String MESSAGE = "Start|" + START + "|Stop|" + STOP
            + "|Duration|5|Message For Testing";
    /**
     * Constructs the test case with the given name.
     * 
     * @param testName
     *            Test case name.
     */
    public TestTimedMetricMessage(String testName) {
        super(testName);
    }
    /**
     * Set up the test. Creates an instance of the class that's need to be
     * tested.
     * 
     * @throws Exception
     *             if the set up is not made properly.
     */
    protected void setUp() throws Exception {
        _timedMetricMessage = new TimedMetricMessage(START, STOP, OBJECT);
    }
    /**
     * This will remove all the data associated with the test.
     * 
     * @throws Exception
     *             if the tear down is not made properly.
     */
    protected void tearDown() throws Exception {
        _timedMetricMessage = null;
    }
    /**
     * Tests the constructor of the testable class.It instantiates the class and
     * confirms that its not <code>null</code>.
     */
    public void testTimedMetricMessage() {
        _timedMetricMessage = new TimedMetricMessage(START, STOP, OBJECT);
        assertNotNull(_timedMetricMessage);
    }
    /**
     * Tests the <code>toString</code> method in the testable class.It tests
     * whether the specified values are appened and returned as a
     * <code>String</code>.
     * 
     */
    public void testToString() {
        _timedMetricMessage = new TimedMetricMessage(START, STOP, OBJECT);
        String test = _timedMetricMessage.toString();
        assertEquals(MESSAGE, test);
    }
}
