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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.mckesson.eig.utility.exception.InvalidParameterException;
import com.mckesson.eig.utility.exception.InvalidStateException;
import com.mckesson.eig.utility.util.ObjectUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * Immutable value class to wrap java.util.Date and java.util.Calendar. Do not
 * add methods that change state, return new or cloned objects instead (like
 * java.lang.String).
 */
public class DateTime implements Cloneable, Comparable<DateTime>, Serializable {

    private static final int GREGCAL_YEAR1 = 1753;

    private static final int GREGCAL_YEAR2 = 9999;

    private static final int GREGCAL_MONTH = 11;

    private static final int GREGCAL_DATE = 31;

    private static final int GREGCAL_HOUR = 23;

    private static final int GREGCAL_MINS = 59;

    private static final int GREGCAL_SECS = 59;

    private static final int CONST_TIMESTAMP = 123456789;

    private static final int CONST_DATE = 1000000;

    private static final int CONST_MILLISEC = 10;

    private static final int CASE_NUM3 = 4;

    private static final int CASE_NUM4 = 8;

    private static final int CASE_NUM5 = 6;

    private static final int CASE_NUM6 = 9;

    private static final int CASE_NUM7 = 5;

    private static final long MIN = new GregorianCalendar(GREGCAL_YEAR1, 0, 1,
            0, 0, 0).getTime().getTime();

    private static final long MAX = new GregorianCalendar(GREGCAL_YEAR2,
            GREGCAL_MONTH, GREGCAL_DATE, GREGCAL_HOUR, GREGCAL_MINS,
            GREGCAL_SECS).getTime().getTime();

    private static final double ONE_DAY = Milliseconds.DAY;

    private static final boolean BROKEN_TIMESTAMP = new Timestamp(
            CONST_TIMESTAMP).getTime() != CONST_TIMESTAMP;

    private Date _date;

    private Calendar _calendar;

    private DateFormat _format;

    public static DateTime create(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date);
    }

    public static Date toDate(DateTime dt) {
        if (dt == null) {
            return null;
        }
        return dt.toDate();
    }

    public static DateTime createMidnight() {
        return new DateTime().asMidnight();
    }

    public static DateTime createTomorrowsMidnight() {
        return new DateTime().asMidnight().add(new Days(1));
    }

    public static DateTime inPast(Period period) {
        return new DateTime().subtract(period);
    }

    public DateTime() {
        _date = new Date(round(System.currentTimeMillis()));
    }

    public DateTime(Calendar calendar) {
        ObjectUtilities.verifyNotNull(calendar);
        _date = round(calendar.getTime());
    }

    public DateTime(Date date) {
        ObjectUtilities.verifyNotNull(date);
        _date = setupDateFromUntrusted(date);
    }

    public DateTime(long milliseconds) {
        _date = new Date(round(milliseconds));
    }

    public DateTime(String date) {
        ObjectUtilities.verifyNotNull(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        try {
            _date = round(dateFormat.parse(date));
        } catch (ParseException pe) {
            invalidParameter(date, dateFormat.toPattern(), pe);
        }
    }

    public DateTime(String date, String format) {
        ObjectUtilities.verifyNotNull(date);
        _format = createDateFormat(format);
        try {
            _date = round(_format.parse(date));
        } catch (ParseException pe) {
            invalidParameter(date, format, pe);
        }
    }

    protected void invalidParameter(String date, String format,
            ParseException pe) {
        throw new InvalidParameterException("Invalid date/time string [" + date
                + "] for format [" + format + "]", pe);
    }


    public Calendar toCalendar() {
        ensureCalendarInitialized();
        return copy(_calendar);
    }

    public Date toDate() {
        return copy(_date);
    }

    public Timestamp toTimestamp() {
        return new Timestamp(toMilliseconds());
    }

    public long toMilliseconds() {
        return _date.getTime();
    }

    public long toUTCMilliseconds() {
        return toMilliseconds();
    }

    public long toGMTMilliseconds() {
        return toMilliseconds();
    }

    @Override
	public String toString() {
        if (_format == null) {
            return toTimestamp().toString();
        }
        return _format.format(_date);
    }

    public String format() {
        return toString();
    }

    public String format(String format) {
        if (StringUtilities.isEmpty(format)) {
            return toString();
        }
        return format(createDateFormat(format));
    }

    public String format(DateFormat df) {
        if (df == null) {
            return toString();
        }
        return df.format(_date);
    }

    public DateTime applyFormat(String format) {
        return doApply(createDateFormat(format));
    }

    public DateTime apply(DateFormat format) {
        ObjectUtilities.verifyNotNull(format);
        return doApply(copy(format));
    }

    private DateTime doApply(DateFormat format) {
        DateTime result = copy();
        result._format = format;
        return result;
    }

    public DateTime add(Period period) {
        DateTime result = copy();
        result.ensureCalendarInitialized();
        period.addTo(result._calendar);
        result._date = result._calendar.getTime();
        return result;
    }

    public DateTime subtract(Period period) {
        return add(period.inverse());
    }

    public Days daysInPast() {
        double difference = now().toMilliseconds() - toMilliseconds();
        return new Days((int) (Math.ceil(difference / ONE_DAY)));
    }

    public Years differenceInYears(DateTime other) {
        if (other.after(this)) {
            return other.differenceInYears(this);
        }
        ensureCalendarInitialized();
        other.ensureCalendarInitialized();

        int thisYear = _calendar.get(Calendar.YEAR);
        int otherYear = other._calendar.get(Calendar.YEAR);
        int years = thisYear - otherYear;
        int thisMonth = _calendar.get(Calendar.MONTH);
        int otherMonth = other._calendar.get(Calendar.MONTH);
        int thisDay = _calendar.get(Calendar.DAY_OF_MONTH);
        int otherDay = other._calendar.get(Calendar.DAY_OF_MONTH);

        if (thisMonth < otherMonth) {
            return new Years(years - 1);
        }
        if (thisMonth == otherMonth) {
            if (thisDay < otherDay) {
                return new Years(years - 1);
            }
        }
        return new Years(years);
    }

    public DateTime asMidnight() {
        ensureCalendarInitialized();
        DateTime result = copy();
        result._calendar.clear();
        result._calendar.set(Calendar.YEAR, _calendar.get(Calendar.YEAR));
        result._calendar.set(Calendar.MONTH, _calendar.get(Calendar.MONTH));
        result._calendar.set(Calendar.DAY_OF_MONTH, _calendar.get(Calendar.DAY_OF_MONTH));
        result._date = result._calendar.getTime();
        return result;
    }

    public boolean after(DateTime other) {
        return _date.after(other._date);
    }

    public boolean before(DateTime other) {
        return _date.before(other._date);
    }

    public boolean isFuture() {
        return after(now());
    }

    public boolean isPast() {
        return before(now());
    }

    @Override
	public boolean equals(Object other) {
        return (other instanceof DateTime) && equals((DateTime) other);
    }

    public boolean equals(DateTime other) {
        return (other != null) && _date.equals(other._date);
    }

    public int compareTo(DateTime other) {
        return _date.compareTo(other._date);
    }

    @Override
	public int hashCode() {
        return DateTime.class.hashCode() ^ _date.hashCode();
    }

    @Override
	public Object clone() {
        try {
            DateTime result = (DateTime) defaultClone();
            result._calendar = copy(_calendar);
            result._format = copy(_format);
            result._date = copy(_date);
            return result;
        } catch (CloneNotSupportedException e) {
            throw new InvalidStateException(e);
        }
    }

    public DateTime copy() {
        return (DateTime) clone();
    }

    public DateTime now() {
        return new DateTime();
    }

    protected Object defaultClone() throws CloneNotSupportedException {
        return super.clone();
    }

    protected DateFormat copy(DateFormat df) {
        if (df == null) {
            return null;
        }
        return (DateFormat) df.clone();
    }

    protected Calendar copy(Calendar c) {
        if (c == null) {
            return null;
        }
        return (Calendar) c.clone();
    }

    protected Date copy(Date d) {
        if (d == null) {
            return null;
        }
        return (Date) d.clone();
    }

    protected Calendar createCalendar() {
        return Calendar.getInstance();
    }

    protected Calendar createCalendar(Date d) {
        Calendar c = createCalendar();
        c.setTime(d);
        return c;
    }

    protected void ensureCalendarInitialized() {
        if (_calendar == null) {
            _calendar = createCalendar(_date);
        }
    }

    protected DateFormat createDateFormat(String format) {
        StringUtilities.verifyHasContent(format);
        return new SimpleDateFormat(format);
    }

    protected Calendar setupCalendar(Calendar c) {
        return round(copy(c));
    }

    protected Calendar setupCalendar() {
        return round(createCalendar());
    }

    protected Calendar setupCalendar(Date d) {
        Calendar c = createCalendar(d);
        return round(c);
    }

    protected Calendar setupCalendar(long milliseconds) {
        return setupCalendar(new Date(milliseconds));
    }

    protected Calendar setupCalendarFromUntrusted(Date d) {
        return setupCalendar(checkForBrokenTimestamp(d));
    }

    protected Date setupDateFromUntrusted(final Date original) {
        Date result = checkForBrokenTimestamp(original);
        result = round(result);
        if (result == original) {
            // Make sure we have our own copy.
            return copy(original);
        }
        return result;
    }

    protected Date checkForBrokenTimestamp(Date d) {
        if (BROKEN_TIMESTAMP && (d instanceof Timestamp)) {
            Timestamp ts = (Timestamp) d;
            return new Date(ts.getTime() + (ts.getNanos() / CONST_DATE));
        }
        return d;
    }

    protected Calendar round(final Calendar c) {
        final Date original = c.getTime();
        final Date rounded = round(original);
        if (rounded != original) {
            c.setTime(rounded);
        }
        return c;
    }

    protected Date round(final Date d) {
        final long original = d.getTime();
        final long rounded = round(original);
        if (rounded != original) {
            return new Date(rounded);
        }
        return d;
    }

    protected long round(final long millisecondsSinceEpoch) {
        long rounded = roundRange(millisecondsSinceEpoch);
        rounded += calculateMillisecondFudge(rounded);
        return rounded;
    }

    protected long roundRange(final long millisecondsSinceEpoch) {
        if (millisecondsSinceEpoch < getMin()) {
            return getMin();
        } else if (millisecondsSinceEpoch > getMax()) {
            return getMax();
        }
        return millisecondsSinceEpoch;
    }

    protected long calculateMillisecondFudge(
            final long millisecondsSinceEpoch) {
        // SQL Server rounds datetimes to the nearest 0, 3, or 7 milliseconds.
        switch ((int) (millisecondsSinceEpoch % CONST_MILLISEC)) {
            case 1 :
            case CASE_NUM3 :
            case CASE_NUM4 :
                return -1L;

            case 2 :
            case CASE_NUM5 :
            case CASE_NUM6 :
                return 1L;

            case CASE_NUM7 :
                return 2L;
            default :
                break;
        }
        return 0L;
    }

    private void readObject(ObjectInputStream stream) throws IOException {
        _date = new Date(stream.readLong());
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeLong(toMilliseconds());
    }

    protected long getMin() {
        return MIN;
    }

    protected long getMax() {
        return MAX;
    }
}
