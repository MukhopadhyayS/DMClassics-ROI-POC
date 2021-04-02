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
package com.mckesson.eig.utility.values;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestDateTimeRange extends TestCase {
    private static final int TIME_LOW = 4;
    private static final int TIME_PERIOD = 5;
    private static final int DATE_CHECK = 10;

    private DateTimeRange _range;

    public static Test suite() {
        return new CoverageSuite(TestDateTimeRange.class, DateTimeRange.class);
    }

    public TestDateTimeRange(String name) {
        super(name);
    }

    protected void setUp() {
        _range = null;
    }

    protected void tearDown() {
        _range = null;
    }

    public void testDateTimeWithPeriod() {
        DateTime dateTime = new DateTime();
        Period period = new Years(1);
        _range = new DateTimeRange(dateTime, period);
        assertEquals(dateTime.subtract(period), _range.getLow());
        assertEquals(dateTime.add(period), _range.getHigh());
    }

    public void testDateTimeWithNullPeriod() {
        DateTime dateTime = new DateTime();
        _range = new DateTimeRange(dateTime, (Period) null);
        assertEquals(dateTime, _range.getLow());
        assertEquals(dateTime, _range.getHigh());
    }

    public void testDateTimeAsLowAndHigh() {
        DateTime low = new DateTime();
        DateTime high = new DateTime();
        _range = new DateTimeRange(low, high);
        assertEquals(low, _range.getLow());
        assertEquals(high, _range.getHigh());
    }

    public void testWithinNearestDay() {
        DateTime low = new DateTime();
        DateTime high = new DateTime();
        _range = new DateTimeRange(low, high);
        _range = _range.withinNearestDay();
        assertEquals(low.asMidnight(), _range.getLow());
        assertEquals(high.asMidnight().add(new Days(1)), _range.getHigh());
    }

    public void testWithin() {
        DateTime dateTime = new DateTime();
        _range = new DateTimeRange(dateTime, new Days(TIME_PERIOD));
        assertTrue(_range.within(dateTime));
        assertFalse(_range.within(dateTime.add(new Days(DATE_CHECK))));
        assertFalse(_range.within(dateTime.subtract(new Days(DATE_CHECK))));
    }

    public void testWithinDateTimeRange() {
        DateTimeRange range = new DateTimeRange(new DateTime(), new Days(
                TIME_PERIOD));
        DateTimeRange rangeWithin = new DateTimeRange(new DateTime(), new Days(
                2));
        DateTimeRange rangeManual = new DateTimeRange(new DateTime()
                .subtract(new Days(TIME_LOW)), new DateTime().add(new Days(
                TIME_PERIOD)));
        assertTrue(range.within(rangeWithin));
        assertFalse(rangeWithin.within(range));
        assertFalse(range.within(rangeManual));
    }
}
