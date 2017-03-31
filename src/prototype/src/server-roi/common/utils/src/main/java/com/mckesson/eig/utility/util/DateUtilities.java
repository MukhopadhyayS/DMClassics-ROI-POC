/**
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
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.values.DateTime;
import com.mckesson.eig.utility.values.DateTimeRange;

/**
 * A set of utilities that help with date and calendar manipulations.
 */

public final class DateUtilities {

    public static final int CALENDER_SECS = 60000;

    public static final int CALENDER_HOURS = 60;

    public static final int CALENDER_INT_ADD = 5;
    
    public static final int CALENDAR_DAY = 24;
    
    public static final int CALENDAR_MONTH = 30;
    
    public static final int CALENDAR_YEAR = 365;

    public static final DateTimeRange VALID_SMALL_DATE_RANGE =
                                 new DateTimeRange(
            new DateTime("01/01/1900", "MM/dd/yyyy"), new DateTime(
                    "06/06/2079", "MM/dd/yyyy"));

    public static final DateTimeRange VALID_DATE_RANGE = new DateTimeRange(
            new DateTime("01/01/1753", "MM/dd/yyyy"), new DateTime(
                    "12/31/9999", "MM/dd/yyyy"));

    /**
     * Private default constructor since all methods are static and we want to
     * prevent unnecessary creation of objects.
     */
    private DateUtilities() {
    }

    /**
     * Get a calendar for the current time based on the given Calendar's {@link
     * TimeZone}
     * 
     * @param prototype
     *            We use the time zone of this argument to create the new date.
     * @return Calendar
     */
    public static Calendar getCalendar(Calendar prototype) {
        return getCalendar(prototype.getTimeZone());
    }

    /**
     * Create a new Calendar based on a given {@link TimeZone} for the current
     * time.
     * 
     * @param timeZone
     *            TimeZone to get the calendar for.
     * @return Calendar
     */
    public static Calendar getCalendar(TimeZone timeZone) {
        return Calendar.getInstance(timeZone);
    }

    /**
     * Create a new Calendar base on a given {@link Locale} for the current
     * time.
     * 
     * @param locale
     * @return Calendar
     */
    public static Calendar getCalendar(Locale locale) {
        return Calendar.getInstance(locale);
    }

    /**
     * Create a calendar based on a given {@link Locale} for the date passed in.
     * 
     * @param l
     *            locale to use.
     * @param f
     *            format of date coming in.
     * @param date
     *            date to get a calendar for.
     * @return Calendar
     */
    public static Calendar getCalendar(Locale l, DateFormat f, String date) {
        Calendar calendar = getCalendar(l);
        calendar.setTime(parseDate(f, date));
        return calendar;
    }

    /**
     * Get a calendar of the string passed in parsing in short date format.
     * 
     * @param locale
     *            locale to retrieve short date format
     * @param date
     *            string representation of short date
     * @return Calendar
     */
    public static Calendar parseShortDate(Locale locale, String date) {
        return getCalendar(locale, getShortDateFormat(locale), date);
    }

    /**
     * Get a calendar of the string passed in parsing in short date time format.
     * 
     * @param locale
     *            locale to retrieve short date format
     * @param date
     *            string representation of short date time
     * @return Calendar
     */
    public static Calendar parseShortDateTime(Locale locale, String date) {
        return getCalendar(locale, getShortDateTimeFormat(locale), date);
    }

    /**
     * Get a string representing the short date format from the calendar for the
     * given locale.
     * 
     * @param calendar
     *            date to retrieve.
     * @param locale
     *            locale of short date format
     * @return String Date of calendar in short date format
     */
    public static String formatShortDate(Calendar calendar, Locale locale) {
        DateFormat format = getShortDateFormat(locale);
        return format.format(calendar.getTime());
    }

    /**
     * Get a calendar of the string passed in parsing in medium date format.
     * 
     * @param locale
     *            locale to retrieve medium date format
     * @param date
     *            string representation of a medium size date
     * @return Calendar
     */
    public static Calendar parseMediumDate(Locale locale, String date) {
        return getCalendar(locale, getMediumDateFormat(locale), date);
    }

    /**
     * Get a calendar of the string passed in parsing in long date format.
     * 
     * @param locale
     *            locale to retrieve long date format
     * @param date
     *            string representation of a long date
     * @return Calendar
     */
    public static Calendar parseLongDate(Locale locale, String date) {
        return getCalendar(locale, getLongDateFormat(locale), date);
    }

    /**
     * Return the short date format for the given {@link Locale}
     * 
     * @param locale
     * @return DateFormat {@link DateFormat.SHORT}
     */
    public static DateFormat getShortDateFormat(Locale locale) {
        return getDateFormat(DateFormat.SHORT, locale);
    }

    /**
     * Return the short date time format for the given {@link Locale}
     * 
     * @param locale
     * @return DateFormat {@link DateFormat.SHORT}
     */
    public static DateFormat getShortDateTimeFormat(Locale locale) {
        return getDateTimeFormat(DateFormat.SHORT, locale);
    }

    /**
     * Return the medium date time format for the given {@link Locale}
     * 
     * @param locale
     * @return DateFormat {@link DateFormat.MEDIUM}
     */
    public static DateFormat getMediumDateFormat(Locale locale) {
        return getDateFormat(DateFormat.MEDIUM, locale);
    }

    /**
     * Return the long date time format for the given {@link Locale}
     * 
     * @param locale
     * @return DateFormat {@link DateFormat.LONG}
     */
    public static DateFormat getLongDateFormat(Locale locale) {
        return getDateFormat(DateFormat.LONG, locale);
    }

    /**
     * Return the date format for the given style and {@link Locale}
     * 
     * @param style
     *            style of {@link DateFormat} to return
     * @param locale
     * @return DateFormat
     * @see DateFormat
     */
    public static DateFormat getDateFormat(int style, Locale locale) {
        return DateFormat.getDateInstance(style, locale);
    }

    /**
     * Return the date time format for the given style and {@link Locale}
     * 
     * @param style
     *            style of {@link DateFormat} to return
     * @param locale
     * @return DateFormat
     * @see DateFormat
     */
    public static DateFormat getDateTimeFormat(int style, Locale locale) {
        return DateFormat.getDateTimeInstance(style, style, locale);
    }

    /**
     * Parse a given string using the given DateFormat.
     * 
     * @param format
     *            tells us how to parse the string
     * @param date
     *            string to parse
     * @return Date
     * @throws ApplicationException
     */
    public static Date parseDate(DateFormat format, String date) {
        try {
            return format.parse(date);
        } catch (ParseException pe) {
            throw new ApplicationException("Error parsing date " + date, pe);
        }
    }

    /**
     * Calculates the next time a task should run. If the time that the user
     * wants to schedule has already happened today then we schedule it for the
     * correct time on the morrow.
     * 
     * No task can be schedule within 10 seconds of now.
     */
    public static Date calculateNextRun(int hour, int minute, int second) {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hour);
        time.set(Calendar.MINUTE, minute);
        time.set(Calendar.SECOND, second);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, now.get(Calendar.SECOND) + CALENDER_INT_ADD);

        if (time.before(now)) {
            time.set(Calendar.DATE, time.get(Calendar.DATE) + 1);
        }
        System.out.println("Next run will be at:  " + time.getTime());
        return time.getTime();
    }

    /**
     * @return Date The time in GMT
     */
    public static Date getGmtTime() {
        return new Date(getGmtTimeInMillis());
    }

    /**
     * @return Date The time in GMT
     */
    public static long getGmtTimeInMillis() {
        long time = System.currentTimeMillis();
        return time - TimeZone.getDefault().getOffset(time);
    }

    public static String formatISO8601(Date date) {
        DateTime dateTime = new DateTime(date);
        Calendar calendar = dateTime.toCalendar();

        MessageFormat iso8601 = new MessageFormat(
                "{0,time}{1,number,+00;-00}:{2,number,00}");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(calendar.getTimeZone());
        iso8601.setFormat(0, df);

        long zoneOff = calendar.get(Calendar.ZONE_OFFSET)
                + calendar.get(Calendar.DST_OFFSET);
        zoneOff /= CALENDER_SECS; // in minutes
        int zoneHrs = (int) (zoneOff / CALENDER_HOURS);
        int zoneMins = (int) (zoneOff % CALENDER_HOURS);
        if (zoneMins < 0) {
            zoneMins = -zoneMins;
        }
        return (iso8601.format(new Object[]{calendar.getTime(),
                new Integer(zoneHrs), new Integer(zoneMins)}));

    }

    public static Date parseISO8601(String datestring) {

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return df.parse(datestring);

        } catch (ParseException e) {
            try {
                // be more lenient
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(datestring);

            } catch (Exception pe) {
                throw new ApplicationException("Error parsing date "
                        + datestring, pe);
            }
        }

    }

    public static String formatISO8601Cal(Calendar calendar) {

        return formatISO8601(calendar.getTime());
    }

    public static Calendar parseISO8601Cal(String datestring) {
        if (datestring == null) {
            return null;
        }
        return new DateTime(parseISO8601(datestring)).toCalendar();
    }

}
