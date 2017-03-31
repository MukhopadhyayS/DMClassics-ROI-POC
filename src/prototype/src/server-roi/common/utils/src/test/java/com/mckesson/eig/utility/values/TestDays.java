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

public class TestDays extends UnitTest {
    private static final int COUNT_PERIOD1 = 3;
    private static final int COUNT_PERIOD2 = 5;
    private static final int COUNT_PERIOD3 = 6;
    private static final int COUNT_PERIOD4 = 12;
    private static final int COUNT_PERIOD5 = 24;
    private static final int COUNT_PERIOD6 = 60;
    private static final int CONST_MILLISECS = 1000;
    private static final long COUNT_PERIODL = 3L;
    private static final Number COUNT_PERIODN = 3;


    private Period _period;
    private Period _other;

    public TestDays(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestDays.class, Days.class);
    }

    public void testGetters() {
        Period ten = new Days(COUNT_PERIOD1);
        assertEquals(COUNT_PERIOD1, ten.getCount());
        assertEquals("day", ten.getName());

        Period two = new Days(COUNT_PERIODL);
        assertEquals(COUNT_PERIODL, two.getCount());
        assertEquals("day", two.getName());

        Period num = new Days(COUNT_PERIODN);
        assertEquals(COUNT_PERIOD1, num.getCount());
        assertEquals("day", num.getName());
    }

    public void testToMillis() {
        _period = new Days(COUNT_PERIOD2);
        assertEquals(COUNT_PERIOD2 * COUNT_PERIOD5 * COUNT_PERIOD6
                * COUNT_PERIOD6 * CONST_MILLISECS, _period.toMillis());
    }

    public void testAddTo() {
        _period = new Days(COUNT_PERIOD4);
        DateTime start = new DateTime("11/15/02 3:06 pm");
        DateTime end = new DateTime("11/27/02 3:06 pm");
        assertEquals(end, _period.addTo(start));
    }

    public void testSubtractFrom() {
        _period = new Days(new Integer(COUNT_PERIOD4));
        DateTime start = new DateTime("11/27/02 3:06 pm");
        DateTime end = new DateTime("11/15/02 3:06 pm");
        assertEquals(end, _period.subtractFrom(start));
    }

    public void testEquals() {
        _period = new Days(COUNT_PERIOD1);
        _other = new Days(COUNT_PERIOD1);

        assertEquals(_period, _other);
        assertEquals(_other, _period);

        _period = new Days(COUNT_PERIOD2);
        _other = new Days(COUNT_PERIOD3);

        assertFalse(_period.equals(_other));
        assertFalse(_other.equals(_period));

        assertFalse(_period.equals(null));
    }

    public void testCompareTo() {
        _period = new Days(COUNT_PERIOD1);
        _other = new Days(COUNT_PERIOD1);

        assertEquals(ZERO, _period.compareTo(_other));
        assertEquals(ZERO, _other.compareTo(_period));

        _period = new Days(COUNT_PERIOD2);
        _other = new Days(COUNT_PERIOD3);

        assertEquals(NEG_ONE, _period.compareTo(_other));
        assertEquals(ONE, _other.compareTo(_period));
    }

    public void testToString() {
        assertEquals("1 day", new Days(ONE).toString());
        assertEquals("2 days", new Days(TWO).toString());
    }
    public void testCreateDays() {

        assertEquals(new Days(FOUR), Days.create("4"));
        final int days = 100;
        assertEquals(new Days(days), Days.create("100"));
    }
}
