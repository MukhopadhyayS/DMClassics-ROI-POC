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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.mckesson.eig.utility.exception.InvalidStateException;
import com.mckesson.eig.utility.testing.ObjectVerifier;
import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.utility.util.ConversionUtilities;
import com.mckesson.eig.utility.util.DateUtilities;

public class TestDateTime extends UnitTest {

    private DateTime _date;
    private DateTime _minMaxDateTime;

    public TestDateTime(String name) {
        super(name);
    }

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        _minMaxDateTime = new DateTime();
    }

    @Override
	protected void tearDown() throws Exception {
        _minMaxDateTime = null;
        super.tearDown();
    }

    public void testRound() {
        verifyRound(createCalendar("01/01/1998", "23:59:59.999"),
                createCalendar("01/02/1998", "00:00:00.000"));
        verifyRound(createCalendar("01/01/1998", "23:59:59.995"),
                createCalendar("01/01/1998", "23:59:59.997"));
        verifyRound(createCalendar("01/01/1998", "23:59:59.996"),
                createCalendar("01/01/1998", "23:59:59.997"));
        verifyRound(createCalendar("01/01/1998", "23:59:59.997"),
                createCalendar("01/01/1998", "23:59:59.997"));
        verifyRound(createCalendar("01/01/1998", "23:59:59.998"),
                createCalendar("01/01/1998", "23:59:59.997"));
        verifyRound(createCalendar("01/01/1998", "23:59:59.992"),
                createCalendar("01/01/1998", "23:59:59.993"));
        verifyRound(createCalendar("01/01/1998", "23:59:59.993"),
                createCalendar("01/01/1998", "23:59:59.993"));
        verifyRound(createCalendar("01/01/1998", "23:59:59.994"),
                createCalendar("01/01/1998", "23:59:59.993"));
        verifyRound(createCalendar("01/01/1998", "23:59:59.990"),
                createCalendar("01/01/1998", "23:59:59.990"));
        verifyRound(createCalendar("01/01/1998", "23:59:59.991"),
                createCalendar("01/01/1998", "23:59:59.990"));
    }

    private Calendar createCalendar(int year, int month, int date, int hour,
            int minute, int second, int millisecond) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, date, hour, minute, second);
        c.set(Calendar.MILLISECOND, millisecond);
        return c;
    }

    private Calendar createCalendar(String date, String time) {
        int month = ConversionUtilities.toIntValue(date.substring(ZERO, TWO));
        int day = ConversionUtilities.toIntValue(date.substring(THREE, FIVE));
        int year = ConversionUtilities.toIntValue(date.substring(SIX));
        int hour = ConversionUtilities.toIntValue(time.substring(ZERO, TWO));
        int minute = ConversionUtilities.toIntValue(time.substring(THREE, FIVE));
        int second = ConversionUtilities.toIntValue(time.substring(SIX, EIGHT));
        int millisecond = ConversionUtilities.toIntValue(time.substring(NINE));
        return createCalendar(year, month, day, hour,
                minute, second, millisecond);
    }

    private void verifyRound(Calendar first, Calendar second) {
        Calendar rounded = new DateTime().round(first);
        assertNotSame(first, second);
        assertEquals(second, rounded);
    }

    private Calendar createCalendar() {
        Calendar c = Calendar.getInstance();
        return new DateTime().round(c);
    }

    public void testConstructWithCalendar() {
        Calendar c = createCalendar();
        _date = new DateTime(c);
        assertNotSame(c, _date.toCalendar());
        assertEquals(c, _date.toCalendar());
        c.setTime(new Date(ONE));
        assertFalse(c.equals(_date.toCalendar()));
    }

    public void testConstructWithDate() {
        Date first = createCalendar().getTime();
        _date = new DateTime(first);
        assertNotSame(first, _date.toDate());
        assertEquals(first, _date.toDate());
    }

    public void testConstructWithString() {
        String s = "11/16/02 7:00 am";
        Calendar c = DateUtilities.parseShortDateTime(Locale.getDefault(), s);
        _date = new DateTime(s);
        assertEquals(c, _date.toCalendar());
    }

    public void testConstructWithStringDateAndFormat() {
        String s = "11/16/02";
        Calendar c = DateUtilities.parseShortDate(Locale.getDefault(), s);
        _date = new DateTime(s, "M/d/y");
        assertEquals(c, _date.toCalendar());
        assertEquals("11/16/02", _date.toString());
    }

    public void testToString() {
        Date d = new Timestamp(createCalendar().getTime().getTime());
        DateTime dt = new DateTime(d);
        assertEquals(d.toString(), dt.toString());
    }

    public void testConstructWithNull() {
        try {
            _date = new DateTime((Calendar) null);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            _date = new DateTime((Date) null);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            _date = new DateTime((String) null);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testDefaultConstructor() {
        Calendar current = createCalendar();
        _date = new DateTime();
        assertTrue(current.equals(_date.toCalendar())
                || current.before(_date.toCalendar()));
    }

    public void testConstructWithMilliseconds() {
        long ms = createCalendar().getTime().getTime();
        _date = new DateTime(ms);
        assertEquals(new Date(ms), _date.toDate());
        assertEquals(ms, _date.toMilliseconds());
    }

    public void testEquals() {
        Calendar c = createCalendar();
        _date = new DateTime(c);
        Object first = new DateTime(c);
        Object second = new DateTime(c);

        assertEquals(first, second);
        assertEquals(second, first);

        c.add(Calendar.MINUTE, ONE);
        second = new DateTime(c);

        assertFalse(first.equals(second));
        assertFalse(second.equals(first));

        assertFalse(first.equals("foo"));
        assertFalse(first.equals(null));

        assertFalse(_date.equals((DateTime) null));
    }

    public void testCompareTo() {
        Calendar c = createCalendar();
        DateTime first = new DateTime(c);
        DateTime second = new DateTime(c);

        assertEquals(ZERO, first.compareTo(second));
        assertEquals(ZERO, second.compareTo(first));

        c.add(Calendar.SECOND, ONE);
        second = new DateTime(c);

        assertEquals(NEG_ONE, first.compareTo(second));
        assertEquals(ONE, second.compareTo(first));
    }

    public void testHashCode() {
        Calendar c = createCalendar();
        _date = new DateTime(c);
        assertEquals(_date.hashCode(), _date.hashCode());
        assertEquals(_date.hashCode(), new DateTime(c).hashCode());

        c.add(Calendar.HOUR_OF_DAY, ONE);
        assertTrue(_date.hashCode() != new DateTime(c).hashCode());
    }

    public void testClone() {
        _date = new DateTime();
        verifyClone(_date);

        _date = new DateTime("11/16/02", "M/d/y");
        verifyClone(_date);

        try {
            new DateTime() {
                @Override
				protected Object defaultClone()
                        throws CloneNotSupportedException {
                    throw new CloneNotSupportedException();
                }
            } .clone();
            fail();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }

    private void verifyClone(DateTime dt) {
        DateTime clone = (DateTime) dt.clone();
        DateTime copy = dt.copy();
        assertNotSame(dt, clone);
        assertNotSame(dt, copy);
        assertNotSame(clone, copy);
        assertEquals(dt, clone);
        assertEquals(dt, copy);
        assertEquals(copy, clone);
    }

    public void testAddPeriod() {
        _date = new DateTime("2/3/02 12:01 pm");
        DateTime before = new DateTime("2/3/02 12:01 pm");
        DateTime after = new DateTime("2/4/02 12:01 pm");
        assertEquals(after, before.add(new Days(ONE)));
        assertNotSame(after, before.add(new Days(ONE)));
        assertEquals(_date, before);
    }

    public void testSubtractPeriod() {
        _date = new DateTime("2/3/03 12:01 pm");
        DateTime before = new DateTime("2/3/03 12:01 pm");
        DateTime after = new DateTime("2/2/03 12:01 pm");
        assertEquals(after, before.subtract(new Days(ONE)));
        assertNotSame(after, before.subtract(new Days(ONE)));
        assertEquals(_date, before);
    }

    public void testDaysInPast() {
        _date = DateTime.inPast(new Days(ONE));
        assertEquals(new Days(ONE), _date.daysInPast());
    }

    public void testAfter() {
        _date = new DateTime("2/3/02 12:01 pm");

        DateTime earlier = new DateTime("2/3/02 12:00 pm");
        assertFalse(_date.equals(earlier));

        assertTrue(_date.after(earlier));
        assertFalse(earlier.after(_date));

        assertFalse(_date.before(earlier));
        assertTrue(earlier.before(_date));

        DateTime same = new DateTime("2/3/02 12:01 pm");
        assertEquals(_date, same);

        assertFalse(_date.after(same));
        assertFalse(same.after(_date));

        assertFalse(_date.before(same));
        assertFalse(same.before(_date));
    }

    public void testIsFuture() {
        _date = new DateTime().add(new Hours(ONE));
        assertTrue(_date.isFuture());
        assertFalse(_date.isPast());

        _date = new DateTime().add(new Hours(NEG_ONE));
        assertFalse(_date.isFuture());
        assertTrue(_date.isPast());
    }

    public void testCreate() {
        Date date = createCalendar().getTime();
        DateTime dt = DateTime.create(date);
        assertEquals(date, dt.toDate());

        assertNull(DateTime.create(null));
    }

    public void testStaticToDate() {
        Date date = createCalendar().getTime();
        DateTime dt = DateTime.create(date);
        assertEquals(date, DateTime.toDate(dt));

        assertNull(DateTime.toDate(null));
    }

    public void testFormat() {
        _date = new DateTime("2/3/02 12:01 pm");
        assertEquals("12:01 PM 2/3/02", _date.format("hh:mm a M/d/yy"));
        assertEquals(_date.toString(), _date.format((DateFormat) null));
        assertEquals(_date.toString(), _date.format((String) null));
    }

    public void testMidnight() {
        DateTime midnight = new DateTime().asMidnight();
        assertEquals(midnight, DateTime.createMidnight());
        DateTime now = new DateTime();
        assertEquals(now.format("M/d/yy"), midnight.format("M/d/yy"));
        assertEquals("00:00:00.0", midnight.format("HH:mm:ss.S"));
    }

    public void testTomorrowsMidnight() {
        DateTime midnight = DateTime.createTomorrowsMidnight();
        DateTime now = new DateTime().add(new Days(1));
        assertEquals(now.format("MM/dd/yyyy"), midnight.format("MM/dd/yyyy"));
        assertEquals("00:00:00.0", midnight.format("HH:mm:ss.S"));
    }

    public void testDifferenceInYears() {
        DateTime reference = new DateTime("2/27/2003", "MM/dd/yyyy");

        DateTime higherMonth = new DateTime("6/18/1957", "MM/dd/yyyy");
        DateTime lowerMonth = new DateTime("1/18/1957", "MM/dd/yyyy");
        DateTime higherDay = new DateTime("2/28/1957", "MM/dd/yyyy");
        DateTime lowerDay = new DateTime("2/26/1957", "MM/dd/yyyy");
        DateTime equalDay = new DateTime("2/27/1957", "MM/dd/yyyy");

        final int higher = 45;
        final int lower = 46;
        assertEquals(new Years(higher), reference
                .differenceInYears(higherMonth));
        assertEquals(new Years(higher), higherMonth
                .differenceInYears(reference));

        assertEquals(new Years(lower), reference
                .differenceInYears(lowerMonth));
        assertEquals(new Years(lower), lowerMonth
                .differenceInYears(reference));

        assertEquals(new Years(higher), reference
                .differenceInYears(higherDay));
        assertEquals(new Years(higher), higherDay
                .differenceInYears(reference));

        assertEquals(new Years(lower), reference
                .differenceInYears(lowerDay));
        assertEquals(new Years(lower), lowerDay
                .differenceInYears(reference));

        assertEquals(new Years(lower), reference
                .differenceInYears(equalDay));
        assertEquals(new Years(lower), equalDay
                .differenceInYears(reference));
    }

    public void testSerialization() {
        DateTime expected = new DateTime();
        DateTime returned = (DateTime) ObjectVerifier.serialize(expected);
        assertEquals(expected, returned);
    }

    public void testMinimumDate() {
        Calendar calendar = Calendar.getInstance();
        final int year = 1753;
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, ZERO);
        calendar.set(Calendar.DAY_OF_MONTH, ONE);
        calendar.set(Calendar.HOUR_OF_DAY, ZERO);
        calendar.set(Calendar.MINUTE, ZERO);
        calendar.set(Calendar.SECOND, ZERO);
        calendar.set(Calendar.MILLISECOND, ZERO);
        assertEquals(calendar.getTime().getTime(), _minMaxDateTime.getMin());

        calendar.add(Calendar.MINUTE, NEG_ONE);
        assertTrue(calendar.getTime().getTime() != _minMaxDateTime.getMin());
        assertEquals(new DateTime(calendar).toMilliseconds(), _minMaxDateTime
                .getMin());
        calendar.add(Calendar.MINUTE, ONE);
        assertEquals(calendar.getTime().getTime(), _minMaxDateTime.getMin());

        calendar.add(Calendar.SECOND, NEG_ONE);
        assertTrue(calendar.getTime().getTime() != _minMaxDateTime.getMin());
        assertEquals(new DateTime(calendar).toMilliseconds(), _minMaxDateTime
                .getMin());
        calendar.add(Calendar.SECOND, ONE);
        assertEquals(calendar.getTime().getTime(), _minMaxDateTime.getMin());

        calendar.add(Calendar.MILLISECOND, NEG_ONE);
        assertTrue(calendar.getTime().getTime() != _minMaxDateTime.getMin());
        assertEquals(new DateTime(calendar).toMilliseconds(), _minMaxDateTime
                .getMin());
        calendar.add(Calendar.MILLISECOND, ONE);
        assertEquals(calendar.getTime().getTime(), _minMaxDateTime.getMin());
    }

    public void testMaximumDate() {
        Calendar calendar = Calendar.getInstance();
        final int year = 9999;
        final int month = 11;
        final int day = 31;
        final int hour = 23;
        final int minute = 59;
        final int second = 59;

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, ZERO);
        assertEquals(calendar.getTime().getTime(), _minMaxDateTime.getMax());

        calendar.add(Calendar.MINUTE, ONE);
        assertTrue(calendar.getTime().getTime() > _minMaxDateTime.getMax());
        assertEquals(new DateTime(calendar).toMilliseconds(), _minMaxDateTime
                .getMax());
        calendar.add(Calendar.MINUTE, NEG_ONE);
        assertEquals(calendar.getTime().getTime(), _minMaxDateTime.getMax());

        calendar.add(Calendar.SECOND, ONE);
        assertTrue(calendar.getTime().getTime() > _minMaxDateTime.getMax());
        assertEquals(new DateTime(calendar).toMilliseconds(), _minMaxDateTime
                .getMax());
        calendar.add(Calendar.SECOND, NEG_ONE);
        assertEquals(calendar.getTime().getTime(), _minMaxDateTime.getMax());

        calendar.add(Calendar.MILLISECOND, ONE);
        assertTrue(calendar.getTime().getTime() > _minMaxDateTime.getMax());
        assertEquals(new DateTime(calendar).toMilliseconds(), _minMaxDateTime
                .getMax());
        calendar.add(Calendar.MILLISECOND, NEG_ONE);
        assertEquals(calendar.getTime().getTime(), _minMaxDateTime.getMax());
    }
}
