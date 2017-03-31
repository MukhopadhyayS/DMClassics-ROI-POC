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
package com.mckesson.eig.utility.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.testing.CoverageSuite;

/**
 * TestDateUtilities
 * 
 */
public class TestDateUtilities extends TestCase {
    private static final int ADD_INT = 20;
    private static final double ARG_CONST = .5;
    private static final int TEST_MONTH1 = 8;
    private static final int TEST_MONTH2 = 10;
    private static final int TEST_DATE1 = 9;
    private static final int TEST_DATE2 = 11;
    private static final int TEST_DATE3 = 19;
    private static final int TEST_YEAR = 2002;
    private static final int TEST_MINS = 15;
    private static final int MILLISEC1 = 19000;
    private static final int MILLISEC2 = 21000;
    private static final int MILLISEC3 = 86403000;
    private static final int MILLISEC4 = 86401000;
    private static final int MILLISEC5 = 86400000;
    private static final int MILLISEC6 = 86398000;
    private static final int MILLISEC7 = 82801000;
    private static final int MILLISEC8 = 82699000;

    private Locale _locale = new Locale("en", "us");

    public TestDateUtilities(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestDateUtilities.class, DateUtilities.class);
    }

    public void testConstructor() {
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(DateUtilities.class));
    }

    public void testAllConstructorsArePrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(DateUtilities.class));
    }

    public void testTodayBasedOn() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("PST"));
        Calendar today = DateUtilities.getCalendar(calendar);
        assertEquals(calendar.getTimeZone(), today.getTimeZone());
    }

    public void testTodayBasedOnTimeZone() {
        TimeZone timeZone = TimeZone.getTimeZone("PST");
        Calendar today = DateUtilities.getCalendar(timeZone);
        assertEquals(timeZone, today.getTimeZone());
    }

    /*
     * public void testValidShortDates() { validShortDateTest("09/19/2002",
     * TEST_MONTH1, TEST_DATE3, TEST_YEAR); validShortDateTest("9/19/2002",
     * TEST_MONTH1, TEST_DATE3, TEST_YEAR); }
     */
    public void testFormatISO8601() {
        try {

            Date date = DateUtilities.parseISO8601("2003-03-01");
            assertEquals("2003-03-01T00:00:00-05:00", DateUtilities
                    .formatISO8601(date));
            assertNotSame(date, DateUtilities.formatISO8601(date));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }
    public void testFormatISO8601Cal() {
        try {
            Calendar cal = DateUtilities.parseISO8601Cal("2003-03-01");
            assertEquals("2003-03-01T00:00:00-05:00", DateUtilities
                    .formatISO8601Cal(cal));
            assertNotSame(cal, DateUtilities.formatISO8601Cal(cal));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testInvalidShortDates() {
        invalidShortDateTest("09-19-2002");
        invalidShortDateTest("Sep 19, 2002");
        invalidShortDateTest("Sept 19, 2002");
        invalidShortDateTest("September 19, 2002");
    }

    public void testValidMediumDates() {
        validMediumDateTest("Sep 19, 2002", TEST_MONTH1, TEST_DATE3, TEST_YEAR);
        validMediumDateTest("September 19, 2002", TEST_MONTH1, TEST_DATE3,
                TEST_YEAR);
    }

    public void testInvalidMediumDates() {
        invalidMediumDateTest("9/19/2002");
        invalidMediumDateTest("Sept 19, 2002");
    }

    public void testValidLongDates() {
        validLongDateTest("September 19, 2002", TEST_MONTH1, TEST_DATE3,
                TEST_YEAR);
        validLongDateTest("Sep 19, 2002", TEST_MONTH1, TEST_DATE3, TEST_YEAR);
    }

    public void testInvalidLongDates() {
        invalidLongDateTest("9/19/2002");
        invalidLongDateTest("Sept 19, 2002");
    }

    public void testGetCalendarWithLocale() {
        Locale locale = new Locale("fr", "fr");
        Calendar calendar = DateUtilities.parseShortDate(locale, "9/11/02");
        assertDate(calendar, TEST_MONTH2, TEST_DATE1, TEST_YEAR);
    }

    public void testParseInvalidShortDate() {
        try {
            DateFormat format = DateUtilities.getShortDateFormat(_locale);
            DateUtilities.parseDate(format, "XX/XX/XXXX");
            fail("ApplicationException should have been thrown");
        } catch (ApplicationException ae) {
            ae.printStackTrace();
        }
    }

    public void testFormatShortDate() {
        String date = "9/11/02";
        Calendar calendar = DateUtilities.parseShortDate(_locale, date);
        assertDate(calendar, TEST_MONTH1, TEST_DATE2, TEST_YEAR);

        String string = DateUtilities.formatShortDate(calendar, _locale);
        assertEquals(date, string);
    }

    public void testParseShortDateTime() {
        validShortDateTimeTest("09/19/2002 12:00 AM", TEST_MONTH1, TEST_DATE3,
                TEST_YEAR, 0, 0);
        validShortDateTimeTest("9/19/2002 1:15 PM", TEST_MONTH1, TEST_DATE3,
                TEST_YEAR, 1, TEST_MINS);
    }

    public void testCalculateNextRunWhenTaskWithin5Seconds() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 2);
        Date time = DateUtilities.calculateNextRun(calendar
                .get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
        // Schedule time should be one day + 20 seconds.

        assertTrue(time.getTime() - Calendar.getInstance().getTimeInMillis() > MILLISEC4);
        assertTrue(time.getTime() - Calendar.getInstance().getTimeInMillis() < MILLISEC3);
    }

    public void testCalculateNextRunWhenTaskInPast() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
        Date time = DateUtilities.calculateNextRun(calendar
                .get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
        // Schedule time should be one day + 1 second.
        int t = (int) (time.getTime() - Calendar.getInstance()
                .getTimeInMillis());
        System.out.println("t" + t);
        System.out.println("t" + MILLISEC7);
        System.out.println("t" + MILLISEC8);
        assertTrue(time.getTime() - Calendar.getInstance().getTimeInMillis() > MILLISEC6);
        assertTrue(time.getTime() - Calendar.getInstance().getTimeInMillis() < MILLISEC5);
    }

    public void testCalculateNextRunWhenTaskInFuture() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + ADD_INT);
        Date time = DateUtilities.calculateNextRun(calendar
                .get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
        // Schedule time should be now + 1 minute.
        assertTrue(time.getTime() - Calendar.getInstance().getTimeInMillis() > MILLISEC1);
        assertTrue(time.getTime() - Calendar.getInstance().getTimeInMillis() < MILLISEC2);
    }

    public void validLongDateTest(String date, int m, int d, int y) {
        Calendar calendar = DateUtilities.parseLongDate(_locale, date);
        assertDate(calendar, m, d, y);
    }

    public void validShortDateTest(String date, int m, int d, int y) {
        Calendar calendar = DateUtilities.parseShortDate(_locale, date);
        assertDate(calendar, m, d, y);
    }

    public void validShortDateTimeTest(String date, int m, int d, int y,
            int hour, int minute) {
        Calendar calendar = DateUtilities.parseShortDateTime(_locale, date);
        assertDate(calendar, m, d, y, hour, minute);
    }

    public void validMediumDateTest(String date, int m, int d, int y) {
        Calendar calendar = DateUtilities.parseMediumDate(_locale, date);
        assertDate(calendar, m, d, y);
    }

    public void invalidLongDateTest(String date) {
        try {
            DateUtilities.parseLongDate(_locale, date);
            fail(date + " should have thrown ApplicationException");
        } catch (ApplicationException ae) {
            ae.printStackTrace();
        }
    }

    public void invalidShortDateTest(String date) {
        try {
            DateUtilities.parseShortDate(_locale, date);
            fail(date + " should have thrown ApplicationException");
        } catch (ApplicationException ae) {
            ae.printStackTrace();
        }
    }

    public void invalidMediumDateTest(String date) {
        try {
            DateUtilities.parseMediumDate(_locale, date);
            fail(date + " should have thrown ApplicationException");
        } catch (ApplicationException ae) {
            ae.printStackTrace();
        }
    }

    public void assertDate(Calendar calendar, int month, int day, int year) {
        assertEquals(month, calendar.get(Calendar.MONTH));
        assertEquals(day, calendar.get(Calendar.DATE));
        assertEquals(year, calendar.get(Calendar.YEAR));
    }

    public void assertDate(Calendar calendar, int month, int day, int year,
            int hour, int minute) {
        assertEquals(month, calendar.get(Calendar.MONTH));
        assertEquals(day, calendar.get(Calendar.DATE));
        assertEquals(year, calendar.get(Calendar.YEAR));
        assertEquals(hour, calendar.get(Calendar.HOUR));
        assertEquals(minute, calendar.get(Calendar.MINUTE));
    }

    public void testGetGmtTime() {
        Date date = DateUtilities.getGmtTime();
        Calendar calendar = Calendar.getInstance();
        Date comp = new Date(calendar.getTimeInMillis()
                - calendar.getTimeZone().getOffset(calendar.getTimeInMillis()));
        assertEquals(date.getTime(), comp.getTime(), ARG_CONST);
    }

}
