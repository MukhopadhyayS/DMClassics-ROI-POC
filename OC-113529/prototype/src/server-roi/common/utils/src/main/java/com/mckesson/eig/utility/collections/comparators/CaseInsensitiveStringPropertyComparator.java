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

import com.mckesson.eig.utility.util.StringUtilities;

/**
 * Provides method which makes an Case Insensitive comparision.
 */
public class CaseInsensitiveStringPropertyComparator<T>
        extends StringPropertyComparator<T> {

    /**
     * Constructs used for making a case Insensitive Comparision.
     *
     * @param property
     *            string to be parsed.
     */
    public CaseInsensitiveStringPropertyComparator(String property) {
        super(property);
    }

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.The comparision is
     * case Insensitive.
     *
     * @param s1
     *            the first string to be compared.
     * @param s2
     *            the second string to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     */
    @Override
	protected int compare(String str1, String str2) {
        return StringUtilities.compare(str1, str2);
    }
}
