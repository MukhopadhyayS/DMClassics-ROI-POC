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

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

public class TestWeeks extends UnitTest {

    private Period _period;
    private Period _other;
    public static final int SECS_IN_MIN = 60;
    public static final int MINS_IN_HOUR = 60;
    public static final int HOURS_IN_DAY = 24;
    public static final int DAYS_IN_WEEK = 7;
    public static final int MILLISEC_IN_SEC = 1000;
    public static final int MILLISEC_IN_MIN = MILLISEC_IN_SEC * SECS_IN_MIN;
    public static final int MILLISEC_IN_HOUR = MILLISEC_IN_MIN * MINS_IN_HOUR;
    public static final int MILLISEC_IN_DAY = MILLISEC_IN_HOUR * HOURS_IN_DAY;
    public static final int MILLISEC_IN_WEEK = MILLISEC_IN_DAY * DAYS_IN_WEEK;

    public TestWeeks(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestWeeks.class, Weeks.class);
    }

    public void testToMillis() {
        int numWeeks = THREE;
        Period period = new Weeks(numWeeks);
        assertEquals(numWeeks * MILLISEC_IN_WEEK, period
                .toMillis());
    }

    public void testAddTo() {
        _period = new Weeks(TEN);
        DateTime start = new DateTime("11/15/02 12:00 am");
        DateTime end = new DateTime("01/24/03 12:00 am");
        assertEquals(end, _period.addTo(start));
    }

    public void testSubtractFrom() {
        _period = new Weeks(TEN);
        DateTime start = new DateTime("01/24/03 12:00 am");
        DateTime end = new DateTime("11/15/02 12:00 am");
        assertEquals(end, _period.subtractFrom(start));
    }

    public void testEquals() {
        _period = new Weeks(THREE);
        _other = new Weeks(THREE);

        assertEquals(_period, _other);
        assertEquals(_other, _period);

        _period = new Weeks(FIVE);
        _other = new Weeks(SIX);

        assertFalse(_period.equals(_other));
        assertFalse(_other.equals(_period));

        assertFalse(_period.equals(null));
    }

    public void testCompareTo() {
        _period = new Weeks(THREE);
        _other = new Weeks(THREE);

        assertEquals(ZERO, _period.compareTo(_other));
        assertEquals(ZERO, _other.compareTo(_period));

        _period = new Weeks(FIVE);
        _other = new Weeks(SIX);

        assertEquals(NEG_ONE, _period.compareTo(_other));
        assertEquals(ONE, _other.compareTo(_period));
    }

    public void testToString() {
        assertEquals("1 week", new Weeks(ONE).toString());
        assertEquals("2 weeks", new Weeks(TWO).toString());
    }
}
