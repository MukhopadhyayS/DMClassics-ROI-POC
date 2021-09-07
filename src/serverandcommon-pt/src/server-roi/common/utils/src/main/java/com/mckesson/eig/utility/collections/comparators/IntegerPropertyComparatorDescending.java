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


/**
 * Provides method which Compares its two arguments for order.It extends
 * <code>IntegerPropertyComparator</code> but it compares second objects
 * property with respect to first .It compares with respect to the objects
 * property set and its a signed comparision.Returns a negative integer, zero,
 * or a positive integer as the first argument is less than, equal to, or
 * greater than the second.
 */
public class IntegerPropertyComparatorDescending<T>
        extends IntegerPropertyComparator<T> {

    /**
     * Constructor used for making a signed comparision with an objects property.
     *
     * @param property
     *            objects property
     */
    public IntegerPropertyComparatorDescending(String property) {
        super(property);
    }

    @Override
    protected int compare(Integer s1, Integer s2) {
        return s2.compareTo(s1);
    }
}
