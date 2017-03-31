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

import java.io.Serializable;
import java.util.Calendar;

import com.mckesson.eig.utility.exception.InvalidStateException;
import com.mckesson.eig.utility.util.NumberUtilities;

/**
 * This class represent Time-Period.It is an abstract class.
 *
 */
public abstract class Period implements Cloneable, Comparable<Period>, Serializable {

	private static final long serialVersionUID = 4956352518576064511L;

	/**
     * Define private variable of type Long.
     */
    private Long _count;

    /**
     * Passed as an argument of type <code>integer</code> and passed it to the
     * constructor of <code>Long</code>.
     *
     * @param count
     *            Passed as an argument of type <code>int</code>.
     */
    public Period(int count) {
        _count = new Long(count);
    }

    /**
     * Passed as an argument of type <code>long</code> and passed it to the
     * constructor of <code>Long</code>.
     *
     * @param count
     *            Passed as an argument of type <code>long</code>.
     */
    public Period(long count) {
        _count = new Long(count);
    }

    /**
     * Passed as an argument of type <code>Number</code> and passed it to the
     * constructor of <code>Long</code>.
     *
     * @param count
     *            Passed as an argument of type <code>Number</code>.
     */
    public Period(Number count) {
        _count = new Long(count.longValue());
    }

    /**
     * Local Method that implicitely called method toLongValue().
     *
     * @return return count type <code>long</code>.
     */
    public long getCount() {
        return toLongValue();
    }

    /**
     * Method that returns type <code>Long</code>.
     *
     * @return Actual value is returning by this method to calling method.
     */
    public long toLongValue() {
        return _count.longValue();
    }

    /**
     * Using variable _count of type <code>Long</code> called method
     * intValue(),return type of <code>Long</code>.
     *
     * @return return type <code>Long</code>.
     */
    public int toIntValue() {
        return _count.intValue();
    }

    /**
     * This method calls implicitely in a class and returns object of type
     * <code>Integer<code>.
     * @return Actual  value is returning by this method to calling method.
     */
    public Integer toInteger() {
        return new Integer(toIntValue());
    }

    /**
     * @return corrosponding object value.
     */
    public Long toLong() {
        return _count;
    }

    /**
     * This method first create cloned object and
     *
     * @return <code>cloned</code> object.
     */
    public Period inverse() {
        Period result = copy();
        result._count = new Long(result.toLongValue() * -1);
        return result;
    }

    /**
     * @return name with hashing. Generates hash code for the given object.
     */
    @Override
	public int hashCode() {
        return getName().hashCode() ^ toLong().hashCode();
    }

    /**
     * @param date
     *            passed as an argument of DateTime reference.
     * @return <code>DateTime</code> which is returned by called method.
     */
    public DateTime addTo(DateTime date) {
        return date.add(this);
    }

    /**
     *
     * @param date
     *            passed as an argument of DateTime reference.
     * @return value of type <code>DateTime</code>.
     *
     */
    public DateTime subtractFrom(DateTime date) {
        return date.subtract(this);
    }

    /**
     * Object itself returned, which is already string.
     *
     * @return <code>String</code> object.
     *
     */

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
            .append(toLong())
            .append(' ')
            .append(getName());
        if (getCount() > 1) {
            sb.append('s');
        }
        return sb.toString();
    }

    /**
     * @param other
     *            as an argument of type <code>Object</code>.
     * @return value of type <code>boolean</code>.
     */
    @Override
	public boolean equals(Object other) {
        return (other instanceof Period) && compareTo((Period) other) == 0;
    }

    /**
     * @param other
     *            Passed as an argument of type <code>Period</code>.
     * @return 0,1 or -1,depending result of called method compare.
     */
    public int compareTo(Period other) {
        return NumberUtilities.compare(toMillis(), other.toMillis());
    }

    /**
     * This method used for cloning, that implicitely called method
     * defaultClone().
     *
     * @return <code>clone</code> object Creates and returns a copy of this
     *         object.
     */
    @Override
	public Object clone() {
        try {
            return defaultClone();
        } catch (CloneNotSupportedException e) {
            throw new InvalidStateException(e);
        }
    }

    /**
     * Implicitely called clone() method and returned cloned object.
     *
     * @return copy of object.
     */
    public Period copy() {
        return (Period) clone();
    }

    /**
     * This method create cloned object of type <code>Object</code>.
     *
     * @return <code>cloned</code> object.
     * @throws CloneNotSupportedException
     *             When class not found then throws Exception.
     */
    protected Object defaultClone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * @return number of milliseconds in this period
     */
    public long toMillis() {
        // Overridden in some subclasses to be more efficient.
        DateTime before = new DateTime();
        DateTime after = before.add(this);
        return after.toMilliseconds() - before.toMilliseconds();
    }

    /**
     *
     * @return object of type <code>Milliseconds</code>.
     */
    public Milliseconds toMilliseconds() {
        return new Milliseconds(toMillis());
    }

    /**
     * Abstract method.
     *
     * @param c
     *            Passed as an argument of <code>Calender</code> Type.
     */
    protected abstract void addTo(Calendar c);

    /**
     * Abstract method.
     *
     * @return object as a <code>String</code> Type.
     */
    protected abstract String getName();
}
