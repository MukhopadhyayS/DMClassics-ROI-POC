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

/**
 * Provides method which Compares its two arguments for order.It compares with
 * respect to the objects property set and its a signed comparision.Returns a
 * negative integer, zero, or a positive integer as the first argument is less
 * than, equal to, or greater than the second.
 *
 */
public class IntegerPropertyComparator<T> implements Comparator<T> {
    /**
     *
     * This objects property.
     */
    private String _property;

    /**
     * No argument constructor.
     */
    protected IntegerPropertyComparator() {
    }

    /**
     * Constructor used for making an <code>Integer</code> Comparision.
     *
     * @param strProperty
     *            objects property.
     */
    protected IntegerPropertyComparator(String strProperty) {
        _property = strProperty;
    }

    /**
     * Returns the objects property.
     *
     * @return property.
     */
    protected String getStrProperty() {
        return _property;
    }

    /**
     * Sets the property of an object.
     *
     * @param property
     *            objects property .
     */
    protected void setStrProperty(String property) {
        _property = property;
    }

    /**
     * Compares its two arguments for order.It makes an signed Comparision.It
     * casts the Object into <code>Integer</code> before Comaparing.Returns a
     * negative integer, zero, or a positive integer as the first argument is
     * less than, equal to, or greater than the second.
     *
     * @param o1
     *            first object whose property has to to be compared.
     * @param o2
     *            second object whose property has to to be compared.
     * @return the value <code>0</code> if this <code>Integer</code> is
     *         equal to the argument <code>Integer</code>; a value less than
     *         <code>0</code> if this <code>Integer</code> is numerically
     *         less than the argument <code>Integer</code>; and a value
     *         greater than <code>0</code> if this <code>Integer</code> is
     *         numerically greater than the argument <code>Integer</code>
     *         (signed comparison).
     */
    public int compare(T o1, T o2) {
        Integer s1 = (Integer) BeanUtilities.getProperty(o1, _property);
        Integer s2 = (Integer) BeanUtilities.getProperty(o2, _property);
        return compare(s1, s2);
    }

	/**
	 * @param s1
	 * @param s2
	 * @return
	 */
	protected int compare(Integer s1, Integer s2)
	{
		return s1.compareTo(s2);
	}
}
