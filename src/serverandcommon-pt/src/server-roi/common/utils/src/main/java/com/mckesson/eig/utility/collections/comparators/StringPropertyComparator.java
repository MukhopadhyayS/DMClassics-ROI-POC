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

package com.mckesson.eig.utility.collections.comparators;

import java.util.Comparator;

import com.mckesson.eig.utility.util.BeanUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * Provides methods which compares two objects or strings and returns a negative
 * integer, zero, or a positive integer as the first argument is less than,
 * equal to, or greater than the second.
 */
public class StringPropertyComparator<T> implements Comparator<T> {
    /**
     * String to be parsed.
     */
    private final String _property;

    /**
     * Constructor used for comparing either objects or strings.
     *
     * @param property
     *            property which is to be compared.
     */
    public StringPropertyComparator(String property) {
        _property = property;
    }

    /**
     * Compares its two arguments for order. Returns a negative integer, zero,
     * or a positive integer as the first argument is less than, equal to, or
     * greater than the second.
     *
     * @param o1
     *            the first object to be compared.
     * @param o2
     *            the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first
     *         argument is less than, equal to, or greater than the second.
     */
    public int compare(T o1, T o2) {
        String s1 = (String) BeanUtilities.getProperty(o1, _property);
        String s2 = (String) BeanUtilities.getProperty(o2, _property);
        return compare(s1, s2);
    }

    /**
     * Compares its two arguments for order. Returns a negative integer, zero,
     * or a positive integer as the first argument is less than, equal to, or
     * greater than the second.
     *
     * @param s1
     *            the first string to be compared.
     * @param s2
     *            the second string to be compared.
     * @return a negative integer, zero, or a positive integer as the first
     *         argument is less than, equal to, or greater than the second.
     */
    protected int compare(String s1, String s2) {
        return StringUtilities.compare(s1, s2);
    }
}
