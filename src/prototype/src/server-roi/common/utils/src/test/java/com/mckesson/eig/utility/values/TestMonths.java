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

package com.mckesson.eig.utility.values;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

/**
 *Test case to test the class Months.
 */
public class TestMonths extends TestCase {

    /**
     * Private variable of type int.
     */
    private static final int COUNT_PERIOD1 = 3;

    /**
     * Private variable of type int.
     */
    private static final int COUNT_PERIOD2 = 5;

    /**
     * Private variable of type int.
     */
    private static final int COUNT_PERIOD3 = 6;

    /**
     * Private variable of type long.
     */
    private static final long COUNT_PERIOD4 = 6L;

    /**
     * Private variable of type Period for the time period.
     */
    private Period _period;

    /**
     * Private variable of type Period for the time period.
     */
    private Period _other;

    /**
     * Constructs the class.
     * @param name
     * name of the test case.
     */
    public TestMonths(String name) {
        super(name);
    }

    /**
     * @return
     * CoverageSuite
     */
    public static Test suite() {
        return new CoverageSuite(TestMonths.class, Months.class);
    }

    /**
     * Tests the addTo() method.
     */
    public void testAddTo() {
        _period = new Months(COUNT_PERIOD3);
        DateTime start = new DateTime("11/15/02 3:06 am");
        DateTime end = new DateTime("05/15/03 3:06 am");
        assertEquals(end, _period.addTo(start));
    }

    /**
     * Tests the subtractFrom() method.
     */
    public void testSubtractFrom() {
       _period = new Months(new Integer(COUNT_PERIOD3));
        DateTime start = new DateTime("05/15/03 3:06 am");
        DateTime end = new DateTime("11/15/02 3:06 am");
        assertEquals(end, _period.subtractFrom(start));
    }

    /**
     * Tests the equal instances.
     */
    public void testEquals() {
        _period = new Months(COUNT_PERIOD1);
        _other = new Months(COUNT_PERIOD1);

        assertEquals(_period, _other);
        assertEquals(_other, _period);

        _period = new Months(COUNT_PERIOD2);
        _other = new Months(COUNT_PERIOD3);

        assertFalse(_period.equals(_other));
        assertFalse(_other.equals(_period));

        _period = new Months(COUNT_PERIOD4);
        _other = new Months(COUNT_PERIOD4);

        assertEquals(_period, _other);
        assertEquals(_other, _period);

        assertFalse(_period.equals(null));
    }

    /**
     * Tests compareTo().
     */
    public void testCompareTo() {
        _period = new Months(COUNT_PERIOD1);
        _other = new Months(COUNT_PERIOD1);

        assertEquals(0, _period.compareTo(_other));
        assertEquals(0, _other.compareTo(_period));

        _period = new Months(COUNT_PERIOD2);
        _other = new Months(COUNT_PERIOD3);

        assertEquals(-1, _period.compareTo(_other));
        assertEquals(1, _other.compareTo(_period));
    }

    /**
     * Tests toString().
     */
    public void testToString() {
        assertEquals("1 month", new Months(1).toString());
        assertEquals("2 months", new Months(2).toString());
    }
}
