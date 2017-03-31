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

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.utility.util.DateUtilities;

public class TestSmallDateTime extends UnitTest {
    private static final int CAL_YEAR_VAL = 2079;
    private static final int CAL_YEAR_VAL1 = 1900;
    private static final int CAL_MONTH_VAL = 5;
    private static final int CAL_MONTHDAY_VAL = 6;
    private static final int CAL_DAYHOUR_VAL = 23;
    private static final int CAL_MINS_VAL = 59;
    private static final int CAL_SECS_VAL = 0;
    private static final int CAL_MILLISECS_VAL = 0;
    private static final int CAL_CONST_SECS1 = 25;
    private static final int CAL_CONST_SECS2 = 29;
    private static final int CAL_CONST_SECS3 = 30;
    private static final int CAL_CONST_SECS4 = 45;
    private static final int CAL_CONST_MILLISECS1 = 500;
    private static final int CAL_CONST_MILLISECS2 = 567;
    private static final int CAL_CONST_MILLISECS3 = 998;
    private static final int CAL_CONST_MILLISECS4 = 999;

    private DateTime _date;
    private DateTime _minMaxSmallDateTime;

    public TestSmallDateTime(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        _date = null;
        _minMaxSmallDateTime = new SmallDateTime();
    }

    protected void tearDown() throws Exception {
        _date = null;
        _minMaxSmallDateTime = null;
        super.tearDown();
    }

    public void testConstructWithCalendar() {
        verifyConstructWithCalendar(0, 0);
        verifyConstructWithCalendar(1, 0);
        verifyConstructWithCalendar(CAL_CONST_SECS1, CAL_CONST_MILLISECS1);
        verifyConstructWithCalendar(CAL_CONST_SECS2, CAL_CONST_MILLISECS3);
        verifyConstructWithCalendar(CAL_CONST_SECS2, CAL_CONST_MILLISECS4);
        verifyConstructWithCalendar(CAL_CONST_SECS3, 0);
        verifyConstructWithCalendar(CAL_CONST_SECS4, CAL_CONST_MILLISECS2);

        Calendar c = round(Calendar.getInstance());
        _date = new SmallDateTime(c);
        assertEquals(c, _date.toCalendar());
        c.setTime(new Date(1));
        assertFalse(c.equals(_date.toCalendar()));
    }

    private void verifyConstructWithCalendar(int seconds, int milliseconds) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.SECOND, seconds);
        c.set(Calendar.MILLISECOND, milliseconds);
        _date = new SmallDateTime(c);
        assertNotSame(c, _date.toCalendar());
        round(c);
        assertEquals(c, _date.toCalendar());
    }

    public void testConstructWithDate() {
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();

        _date = SmallDateTime.create(d);
        assertNotSame(d, _date.toDate());

        d = round(c).getTime();
        assertEquals(d, _date.toDate());

        assertNull(SmallDateTime.convert((Date) null));
    }

    public void testConvertWithDateTime() {
        assertNull(SmallDateTime.convert((DateTime) null));

        DateTime dt = new DateTime();
        _date = SmallDateTime.convert(dt);
        assertNotSame(_date, dt);
        assertEquals(_date, SmallDateTime.convert(dt));

        assertSame(_date, SmallDateTime.convert(_date));
    }

    public void testConstructWithString() {
        String s = "11/16/02 7:00 am";
        Calendar c = DateUtilities.parseShortDateTime(Locale.getDefault(), s);
        _date = new SmallDateTime(s);
        round(c);
        assertEquals(c, _date.toCalendar());
    }

    public void testConstructWithStringDateAndFormat() {
        String s = "11/16/02";
        Calendar c = DateUtilities.parseShortDate(Locale.getDefault(), s);
        _date = new SmallDateTime(s, "M/d/y");
        round(c);
        assertEquals(c, _date.toCalendar());
        assertEquals("11/16/02", _date.toString());
    }

    public void testDefaultConstructor() {
        Calendar c = round(Calendar.getInstance());
        _date = new SmallDateTime();
        assertTrue(c.equals(_date.toCalendar()) || c.before(_date.toCalendar()));
    }

    public void testConstructWithMilliseconds() {
        Calendar c = Calendar.getInstance();
        _date = new SmallDateTime(c.getTime().getTime());

        round(c);
        assertEquals(c.getTime().getTime(), _date.toMilliseconds());
    }

    public void testCopy() {
        _date = new SmallDateTime().copy();
        assertTrue(_date instanceof SmallDateTime);
    }

    public void testAdd() {
        _date = new SmallDateTime().add(new Days(1));
        assertTrue(_date instanceof SmallDateTime);
    }

    public void testNow() {
        _date = new SmallDateTime().now();
        assertTrue(_date instanceof SmallDateTime);
    }

    public void testMinimumDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, CAL_YEAR_VAL1);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertEquals(calendar.getTime().getTime(), _minMaxSmallDateTime
                .getMin());

        calendar.add(Calendar.MINUTE, -1);
        assertTrue(calendar.getTime().getTime() != _minMaxSmallDateTime
                .getMin());
        assertEquals(new SmallDateTime(calendar).toMilliseconds(),
                _minMaxSmallDateTime.getMin());
        calendar.add(Calendar.MINUTE, 1);
        assertEquals(calendar.getTime().getTime(), _minMaxSmallDateTime
                .getMin());

        calendar.add(Calendar.SECOND, -1);
        assertTrue(calendar.getTime().getTime() != _minMaxSmallDateTime
                .getMin());
        assertEquals(new SmallDateTime(calendar).toMilliseconds(),
                _minMaxSmallDateTime.getMin());
        calendar.add(Calendar.SECOND, 1);
        assertEquals(calendar.getTime().getTime(), _minMaxSmallDateTime
                .getMin());

        calendar.add(Calendar.MILLISECOND, -1);
        assertTrue(calendar.getTime().getTime() != _minMaxSmallDateTime
                .getMin());
        assertEquals(new SmallDateTime(calendar).toMilliseconds(),
                _minMaxSmallDateTime.getMin());
        calendar.add(Calendar.MILLISECOND, 1);
        assertEquals(calendar.getTime().getTime(), _minMaxSmallDateTime
                .getMin());
    }

    public void testMaximumDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, CAL_YEAR_VAL);
        calendar.set(Calendar.MONTH, CAL_MONTH_VAL);
        calendar.set(Calendar.DAY_OF_MONTH, CAL_MONTHDAY_VAL);
        calendar.set(Calendar.HOUR_OF_DAY, CAL_DAYHOUR_VAL);
        calendar.set(Calendar.MINUTE, CAL_MINS_VAL);
        calendar.set(Calendar.SECOND, CAL_SECS_VAL);
        calendar.set(Calendar.MILLISECOND, CAL_MILLISECS_VAL);
        assertEquals(calendar.getTime().getTime(), _minMaxSmallDateTime
                .getMax());

        calendar.add(Calendar.MINUTE, 1);
        assertTrue(calendar.getTime().getTime() > _minMaxSmallDateTime.getMax());
        assertEquals(new SmallDateTime(calendar).toMilliseconds(),
                _minMaxSmallDateTime.getMax());
        calendar.add(Calendar.MINUTE, -1);
        assertEquals(calendar.getTime().getTime(), _minMaxSmallDateTime
                .getMax());

        calendar.add(Calendar.SECOND, 1);
        assertTrue(calendar.getTime().getTime() > _minMaxSmallDateTime.getMax());
        assertEquals(new SmallDateTime(calendar).toMilliseconds(),
                _minMaxSmallDateTime.getMax());
        calendar.add(Calendar.SECOND, -1);
        assertEquals(calendar.getTime().getTime(), _minMaxSmallDateTime
                .getMax());

        calendar.add(Calendar.MILLISECOND, 1);
        assertTrue(calendar.getTime().getTime() > _minMaxSmallDateTime.getMax());
        assertEquals(new SmallDateTime(calendar).toMilliseconds(),
                _minMaxSmallDateTime.getMax());
        calendar.add(Calendar.MILLISECOND, -1);
        assertEquals(calendar.getTime().getTime(), _minMaxSmallDateTime
                .getMax());
    }

    private Calendar round(Calendar c) {
        int seconds = c.get(Calendar.SECOND);
        int milliseconds = c.get(Calendar.MILLISECOND);
        if ((seconds >= CAL_CONST_SECS3)
                || ((seconds == CAL_CONST_SECS2) && (milliseconds >= CAL_CONST_MILLISECS4))) {
            c.add(Calendar.MINUTE, 1);
        }
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }
}
