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

package com.mckesson.eig.utility.collections;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * An object that implements the Enumeration interface generates a series of
 * elements, one at a time. Methods are provided to enumerate through the
 * elements of a Iterator.call to <code>hasMoreElements </code> returns true
 * only if Iterator has one more element to provide.Successive calls to the
 * <code>nextElement</code> method return successive elements of the series.
 *
 * @see Enumeration
 * @see Iterator
 */
public class EnumerationAdapter<T> implements Enumeration<T> {
    /**
     * Name of the Iterator.
     */
    private final Iterator<T> _iterator;

    /**
     * Constructor for Enumerating.
     *
     * @param iterator
     *            An Iterator for manipulating.
     */
    public EnumerationAdapter(Iterator<T> iterator) {
        _iterator = iterator;
    }

    /**
     * Checks if this Iterator contains more elements.
     *
     * @return <code>true</code> if and only if this Iterator object contains
     *         at least one more element to provide; <code>false</code>
     *         otherwise.
     */
    public boolean hasMoreElements() {
        return _iterator.hasNext();
    }

    /**
     * Returns the next element in the iteration. Calling this method repeatedly
     * until the <code>hasNext()</code> method returns false will return each
     * element in the underlying collection exactly once.
     *
     * @return the next element in the iteration.
     */
    public T nextElement() {
        return _iterator.next();
    }
}
