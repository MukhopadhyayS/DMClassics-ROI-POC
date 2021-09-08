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

import java.util.Iterator;

/**
 * Provides methods to iterate through <code>List</code> of elements.Call to
 * <code>hasNext()</code> returns <code>true</code> if the iteration has
 * more elements. successive call to <code>next()</code> returns the elemens
 * in the underlying collection. Further it allows the calls to remove elements
 * from the underlying collection during the iteration with well-defined
 * semantics.
 */
public class OwnedIterator<O, T> implements Iterator<T> {
    /**
     * object used for iterating.
     */
    private Iterator<T> _iterator;

    /**
     * Holds the instance of ListOwner.
     */
    private ListOwner<O, T> _owner;

    /**
     * The object used for unassociation.
     */
    private T _last;

    /**
     * Sole constructor.
     */
    protected OwnedIterator() {
    }

    /**
	 * Constructs a command capable of Iterating and manipulating this objects.
	 *
	 * @param owner
	 *            instance of ListOwner.
	 * @param i
	 *            instance of Iterator.
	 */
	public OwnedIterator(ListOwner<O, T> owner, Iterator<T> i) {
	    _owner = owner;
	    _iterator = i;
	}

	/**
     * Returns the object of type ListOwner.
     *
     * @return lstOwner
     */
    protected ListOwner<O, T> getLstOwner() {
        return _owner;
    }

    /**
     * Sets the ListOwner object.
     *
     * @param lstOwner
     *            object of type ListOwner.
     */
    protected void setLstOwner(ListOwner<O, T> lstOwner) {
        _owner = lstOwner;
    }

    /**
     * Returns the Object which has to be unassociated.
     *
     * @return objLast
     */
    protected T getObjLast() {
        return _last;
    }

    /**
     * Sets the object <code>objLast</code>.
     *
     * @param objLast
     *            Object which has to be unassociated.
     */
    protected void setObjLast(T objLast) {
        _last = objLast;
    }

    /**
     * Returns <code>true</code> if the iteration has more elements. (In other
     * words, returns <code>true</code> if <code>next</code> would return an
     * element rather than throwing an exception.)
     *
     * @return <code>true</code> if the iterator has more elements.<code>false</code>Otherwise.
     */
    public boolean hasNext() {
        return _iterator.hasNext();
    }

    /**
     * Returns the next element in the iteration. Calling this method repeatedly
     * until the <code>hasNext()</code> method returns false will return each
     * element in the underlying collection exactly once.
     *
     * @return the next element in the iteration.
     */
    public T next() {
        _last = _iterator.next();
        return _last;
    }

    /**
     * Removes from the underlying collection the last element returned by the
     * iterator . This method can be called only once per call to
     * <code>next</code>.
     */
    public void remove() {
        _iterator.remove();
        _owner.unassociate(_last);
        _last = null;
    }
}
