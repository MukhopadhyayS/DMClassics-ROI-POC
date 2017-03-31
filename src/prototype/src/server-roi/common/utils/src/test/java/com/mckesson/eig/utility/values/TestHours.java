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

public class TestHours extends TestCase {
    private static final int COUNT_PERIOD1 = 3;
    private static final int COUNT_PERIOD2 = 5;
    private static final int COUNT_PERIOD3 = 6;
    private static final int CONST_COUNT = 60;
    private static final int CONST_MILLISECS = 1000;

    private Period _period;
    private Period _other;

    public TestHours(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestHours.class, Hours.class);
    }

    public void testToMillis() {
        _period = new Hours(COUNT_PERIOD2);
        assertEquals(COUNT_PERIOD2 * CONST_COUNT * CONST_COUNT
                * CONST_MILLISECS, _period.toMillis());
    }

    public void testAddTo() {
        _period = new Hours(COUNT_PERIOD3);
        DateTime start = new DateTime("11/15/02 12:00 am");
        DateTime end = new DateTime("11/15/02 06:00 am");
        assertEquals(end, _period.addTo(start));
    }

    public void testSubtractFrom() {
        _period = new Hours(new Integer(COUNT_PERIOD3));
        DateTime start = new DateTime("11/15/02 06:00 am");
        DateTime end = new DateTime("11/15/02 12:00 am");
        assertEquals(end, _period.subtractFrom(start));
    }

    public void testEquals() {
        _period = new Hours(COUNT_PERIOD1);
        _other = new Hours(COUNT_PERIOD1);

        assertEquals(_period, _other);
        assertEquals(_other, _period);

        _period = new Hours(COUNT_PERIOD2);
        _other = new Hours(COUNT_PERIOD3);

        assertFalse(_period.equals(_other));
        assertFalse(_other.equals(_period));

        assertFalse(_period.equals(null));
    }

    public void testCompareTo() {
        _period = new Hours(COUNT_PERIOD1);
        _other = new Hours(COUNT_PERIOD1);

        assertEquals(0, _period.compareTo(_other));
        assertEquals(0, _other.compareTo(_period));

        _period = new Hours(COUNT_PERIOD2);
        _other = new Hours(COUNT_PERIOD3);

        assertEquals(-1, _period.compareTo(_other));
        assertEquals(1, _other.compareTo(_period));
    }

    public void testToString() {
        assertEquals("1 hour", new Hours(1).toString());
        assertEquals("2 hours", new Hours(2).toString());
    }
}
