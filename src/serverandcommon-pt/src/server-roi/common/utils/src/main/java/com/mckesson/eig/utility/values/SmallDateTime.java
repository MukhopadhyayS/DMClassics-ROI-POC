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
package com.mckesson.eig.utility.values;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class represent DateTime information.
 * 
 */

public class SmallDateTime extends DateTime {

    private static final int GREGCAL_YEAR1 = 1900;

    private static final int GREGCAL_YEAR2 = 2079;

    private static final int GREGCAL_MONTH = 5;

    private static final int GREGCAL_DATE = 6;

    private static final int GREGCAL_HOUR = 23;

    private static final int GREGCAL_MINS = 59;

    private static final long CONST_LONG = 29998L;

    private static final long MIN = new GregorianCalendar(GREGCAL_YEAR1, 0, 1,
            0, 0, 0).getTime().getTime();

    private static final long MAX = new GregorianCalendar(GREGCAL_YEAR2,
            GREGCAL_MONTH, GREGCAL_DATE, GREGCAL_HOUR, GREGCAL_MINS, 0)
            .getTime().getTime();

    private static final long ONE_MINUTE = Milliseconds.MINUTE;

    /**
     * Passed object of type <code>Date</code> and calling implicitely method
     * conver with Date object then it is casting <code>Date</code> object to
     * <code>SmallDateTime</code>.
     * 
     * @param date
     *            Passed as an argument of type <code>Date</code>.
     * @return <code>SmallDateTime</code> Type object.
     */
    public static DateTime create(Date date) {
        return convert(date);
    }

    /**
     * Passed argument of type <code>Date</code> and implicit calling method
     * SmallDateTime.Checks object of <code>DateTime</code>,Ultimately it
     * returns Object of type <code>SmallDateTime</code>.
     * 
     * @param date
     *            Its the <code>Date</code> which has to be converted.
     * @return <code>SmallDateType</code> object.
     * 
     */
    public static SmallDateTime convert(Date date) {
        if (date == null) {
            return null;
        }
        return new SmallDateTime(date);
    }

    /**
     * Passed argument of type <code>DateTime</code> and implicit calling
     * method SmallDateTime.Checks object of <code>DateTime</code>,Ultimately
     * it returns Object of type <code>SmallDateTime</code>.
     * 
     * @param other
     *            Passed as an argument of <code>DateTime</code> type.
     * @return Date Type object.
     */

    public static SmallDateTime convert(DateTime other) {
        if (other == null) {
            return null;
        }
        if (other instanceof SmallDateTime) {
            return (SmallDateTime) other;
        }
        return new SmallDateTime(other.toMilliseconds());
    }

    /**
     * Default constructor.
     */
    public SmallDateTime() {
        super();
    }

    /**
     * Passing object of type <code>Calendar</code> and called super class
     * constructor.
     * 
     * @param calendar
     *            Passed as an argument of type <code>Calendar</code>.
     */
    public SmallDateTime(Calendar calendar) {
        super(calendar);
    }

    /**
     * Passed argument of type <code>Date</code> and calling super class
     * <code>DateTime</code> constructor.
     * 
     * @param date
     *            Passed as an argument of type <code>Date</code>.
     */
    public SmallDateTime(Date date) {
        super(date);
    }

    /**
     * Passed argument of type <code>long</code> and calling super class
     * <code>DateTime</code> constructor.
     * 
     * @param milliseconds
     *            Passed as an argument of type <code>long</code>.
     */
    public SmallDateTime(long milliseconds) {
        super(milliseconds);
    }

    /**
     * Passed as an argument of type <code>String</code> and calling super
     * class constructor <code>DateTime</code>.
     * 
     * @param date
     *            Passed as an argument of type <code>String</code>.
     */
    public SmallDateTime(String date) {
        super(date);
    }

    /**
     * Passed arguments of type <code>String</code> and calling super class
     * <code>DateTime</code> constructor.
     * 
     * @param date
     *            Passed as an argument as a <code>string</code> object.
     * @param format
     *            Passed as an argument as a <code>string</code> object.
     */
    public SmallDateTime(String date, String format) {
        super(date, format);
    }

    /**
     * @return <code>SmallDateTime</code> object.
     */
    public DateTime now() {
        return new SmallDateTime();
    }

    /**
     * @param millisecondsSinceEpoch
     *            Passed as an argument of type <code>long</code>.
     * @return SmallDateTime object.
     */
    protected long calculateMillisecondFudge(
            final long millisecondsSinceEpoch) {
        // SQL Server rounds smalldatetimes to the nearest minute.
        final long seconds = millisecondsSinceEpoch % ONE_MINUTE;
        // Magic number 29998 taken from SQL Server documentation.
        if (seconds > CONST_LONG) {
            return ONE_MINUTE - seconds;
        }
        return -seconds;
    }

    /**
     * 
     * @return <code>SmallDatTime</code> object.
     */
    protected long getMin() {
        return MIN;
    }

    /**
     * 
     * @return <code>SmallDateTime</code> object.
     */
    protected long getMax() {
        return MAX;
    }
}
