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

public final class NumberUtilities {

    /**
     * Private default constructor since all methods are static and we want to
     * prevent unnecessary creation of objects.
     */
    private NumberUtilities() {
    }

    /**
     * Attempts to parse <code>value</code> as an int. Returns
     * <code>defaultValue</code> if the parsing fails.
     * <p>
     * Note that it is safe to pass a null value to this method, in that case
     * you will get back <code>defaultValue</code>.
     * 
     * @param value
     *            The String to parse. If this value is null or unparseable then
     *            <code>defaultValue</code> is returned.
     * 
     * @param defaultValue
     *            The value to return if <code>value</code> is unparseable or
     *            null.
     * 
     * @return int
     */
    public static int parse(String value, int defaultValue) {
        return ConversionUtilities.toIntValue(value, defaultValue);
    }

    /**
     * Attempts to parse <code>value</code> as a long. Returns
     * <code>defaultValue</code> if the parsing fails.
     * <p>
     * Note that it is safe to pass a null value to this method, in that case
     * you will get back <code>defaultValue</code>.
     * 
     * @param value
     *            The String to parse. If this value is null or unparseable then
     *            <code>defaultValue</code> is returned.
     * 
     * @param defaultValue
     *            The value to return if <code>value</code> is unparseable or
     *            null.
     * 
     * @return long
     */
    public static long parse(String value, long defaultValue) {
        return ConversionUtilities.toLongValue(value, defaultValue);
    }

    /**
     * Attempts to parse <code>value</code> as a float. Returns
     * <code>defaultValue</code> if the parsing fails.
     * <p>
     * Note that it is safe to pass a null value to this method, in that case
     * you will get back <code>defaultValue</code>.
     * @param value
     * @param defaultValue
     * @return
     */    
    public static float parse(String value, float defaultValue) {
        return ConversionUtilities.toFloatValue(value, defaultValue);
    }

    /**
     * Attempts to parse <code>value</code> as a double. Returns
     * <code>defaultValue</code> if the parsing fails.
     * <p>
     * Note that it is safe to pass a null value to this method, in that case
     * you will get back <code>defaultValue</code>.
     */
    public static double parse(String value, double defaultValue) {
        return ConversionUtilities.toDoubleValue(value, defaultValue);
    }

    /**
     * @param first of type <code>Integer</code>.
     * @param second of type <code>Integer</code>.
     * @return negetive Integer if first<second or 0,1 depends on 
     * arguments.
     */
    public static int compare(int first, int second) {
        return (first < second ? -1 : (first == second ? 0 : 1));
    }

    /**
     * @param first
     * @param second
     * @return 0 if first object value is equal to compare second object value,
     *         1 if first object value grater then second onject, -1 if first
     *         object value is less than second object value.
     */
    public static int compare(long first, long second) {
        return (first < second ? -1 : (first == second ? 0 : 1));
    }

    /**
     * @param i Passed as an <code>Integer</code>.
     * @return negitive Integer or positive Integer, depends on the value of 
     * argument i, passed as an argument.
     */
    public static int valueOf(Integer i) {
        return (i == null) ? -1 : i.intValue();
    }

    /**
     * @param first of type <code>short</code>.
     * @param second of type <code>short</code>.
     * @return negetive Integer or 1 or 0, depends on values of arguments. 
     */
    public static int compare(short first, short second) {
        return (first < second ? -1 : (first == second ? 0 : 1));
    }

    /**
     * @param s of type <code>Short</code>.
     * @return negetive Integer or <code>Short</code> depends on argument.
     */
    public static short valueOf(Short s) {
        return (s == null) ? -1 : s.shortValue();
    }
    
    /**
     * @param number of type <code>int</code>.
     * @param bottomRange of type <code>int</code>.
     * @param topRange of type <code>int</code>.
     * @return <code>boolean</code> true if number within range otherwise returns false
     */
    public static boolean isInRange(int number, int bottomRange, int topRange) {
        if (number < bottomRange || number > topRange) {
            return false;
        }
        return true;
    }
}
