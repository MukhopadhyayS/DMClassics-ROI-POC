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

import java.util.ListIterator;

/**
 * provides methods that allows the programmer to traverse the list in either
 * direction, modify the list during iteration, and obtain the iterator's
 * current position in the list.
 *
 * @see com.mckesson.eig.utility.collections.OwnedIterator.
 *
 */
public class OwnedListIterator<O, T> extends OwnedIterator<O, T> implements ListIterator<T> {
    /**
     * ListIterator for traversing.
     */
    private final ListIterator<T> _lstIterator;

    /**
     * constructor capable of manipulating this <code>ListIterator</code> and
     * <code>owner</code>.
     *
     * @param owner
     *            object for manipulation
     * @param i
     *            ListIterator for manipulation.
     */
    public OwnedListIterator(ListOwner<O, T> owner, ListIterator<T> i) {
        super(owner, i);
        _lstIterator = i;
    }

    /**
     * Returns <code>true</code> if this list iterator has more elements when
     * traversing the list in the reverse direction. (In other words, returns
     * <code>true</code> if <code>previous</code> would return an element
     * rather than throwing an exception.)
     *
     * @return <code>true</code> if the list iterator has more elements when
     *         traversing the list in the reverse direction.
     */
    public boolean hasPrevious() {
        return _lstIterator.hasPrevious();
    }

    /**
     * Returns the previous element in the list. This method may be called
     * repeatedly to iterate through the list backwards, or intermixed with
     * calls to <code>next</code> to go back and forth.
     *
     * @return the previous element in the list.
     */
    public T previous() {
        setObjLast(_lstIterator.previous());
        return (getObjLast());
    }

    /**
     * Returns the index of the element that would be returned by a subsequent
     * call to <code>next</code>. (Returns list size if the list iterator is
     * at the end of the list.)
     *
     * @return the index of the element that would be returned by a subsequent
     *         call to <code>next</code>, or list size if list iterator is at
     *         end of list.
     */
    public int nextIndex() {
        return _lstIterator.nextIndex();
    }

    /**
     * Returns the index of the element that would be returned by a subsequent
     * call to <code>previous</code>. (Returns -1 if the list iterator is at
     * the beginning of the list.)
     *
     * @return the index of the element that would be returned by a subsequent
     *         call to <code>previous</code>, or -1 if list iterator is at
     *         beginning of list.
     */
    public int previousIndex() {
        return _lstIterator.previousIndex();
    }

    /**
     * Replaces the last element returned by <code>next</code> or
     * <code>previous</code> with the specified element (optional operation).
     *
     * @param o
     *            the element with which to replace the last element returned by
     *            <code>next</code> or <code>previous</code>.
     */
    public void set(T o) {
        _lstIterator.set(o);
        getLstOwner().unassociate(getObjLast());
        getLstOwner().associate(o);
        setObjLast(o);
    }

    /**
     * Inserts the specified element into the list .
     *
     * @param o
     *            object to be inserted.
     */
    public void add(T o) {
        _lstIterator.add(o);
        getLstOwner().associate(o);
        setObjLast(o);
    }
}
