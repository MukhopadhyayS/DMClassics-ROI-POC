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

import com.mckesson.eig.utility.util.ReflectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * Provides method which Compares its two arguments for order.It makes a
 * property(method) comparision and its Case Insensitive.Returns a negative
 * integer, zero, or a positive integer as the first argument is less than,
 * equal to, or greater than the second.
 */
public class PropertyComparator<T> implements Comparator<T> {
    /**
     * Desired method which has to be used as property for comparision.
     */
    private String _methodName;

    /**
     * Constructor used to make a property comparision.
     *
     * @param methodName
     *            property to be compared.
     */
    public PropertyComparator(String methodName) {
        _methodName = methodName;
    }

    /**
     * No argument Constructor.
     */
    protected PropertyComparator() {
    }

    /**
     * Returns a method name.
     *
     * @return desired method name.
     */
    protected String getMethodName() {
        return _methodName;
    }

    /**
     * Sets the method name.
     *
     * @param methodName
     *            desired method name.
     */
    protected void setMethodName(String methodName) {
        this._methodName = methodName;
    }

    /**
     * Compares its two arguments for order.It makes a property(method)
     * comparision and its Case Insensitive.Returns a negative integer, zero, or
     * a positive integer as the first argument is less than, equal to, or
     * greater than the second.
     *
     * @param o1
     *            first object whose property has to be to be compared.
     * @param o2
     *            second object whose property has to be compared.
     * @return a negative integer, zero, or a positive integer as the first
     *         argument is less than, equal to, or greater than the second.
     */
    public int compare(T o1, T o2) {
        String s1 = (String) ReflectionUtilities.callMethod(o1, _methodName);
        String s2 = (String) ReflectionUtilities.callMethod(o2, _methodName);
        return StringUtilities.compareWithoutCaseAndThenWithCase(s1, s2);
    }
}
