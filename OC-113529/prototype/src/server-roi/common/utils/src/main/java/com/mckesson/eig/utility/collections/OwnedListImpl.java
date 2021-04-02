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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.mckesson.eig.utility.util.ObjectUtilities;

/**
 * Provides methods which are used for appending elements to the end of the
 * list, appending at a specified location,appending a collection to the end of
 * the list,appending a collection to the specified location in the
 * list,replacing a element in a particular location and removing elements from
 * the list. Provides methods for initializing <code>List</code> and
 * <code>ListOwner</code>objects.It also have methods which Compares the
 * specified object with a list for equality,methods for checking whether the
 * list have the specified collection of elements.
 *
 */
public class OwnedListImpl<O, T> implements OwnedList<O, T> {
	private static final long serialVersionUID = -8789043391193545337L;

	/**
     * List which is used for operations.
     */
    private final List<T> _list;

    /**
     * Holds the instance of ListOwner.
     */
    private ListOwner<O, T> _owner;

    /**
     * Constructs and initializes a list.
     *
     * @param owner
     *            object which has to be manipulated.
     */
    public OwnedListImpl(O owner) {
        _list = new ArrayList<T>();
        initializeOwner(owner);
    }

    /**
     * Sets the list if its not <code>null</code> and initializes the object
     * <code>owner</code>.
     *
     * @param owner
     *            object which has to be initialized.
     * @param list
     *            List object.
     * @see com.mckesson.eig.utility.util.ObjectUtilities.
     */
    public OwnedListImpl(O owner, List<T> list) {
        ObjectUtilities.verifyNotNull(list);
        _list = list;
        initializeOwner(owner);
    }

    /**
     * Initializes the object <code>owner</code>.
     *
     * @param owner
     *            which has to be initialized.
     * @see com.mckesson.eig.utility.collections.ListOwner.java
     */
    private void initializeOwner(O owner) {
        _owner = new ListOwner<O, T>(owner, _list);
        _owner.ownAll(_list);
    }

    /**
     * Sets the Object.It makes an explicit call to <code>initializeOwner</code>
     * for initializinig.
     *
     * @param owner
     *            object which to be set
     */
    public void setOwner(O owner) {
        if (getOwner() != owner) {
            initializeOwner(owner);
        }
    }

    /**
     * Returns an object of type <code>ListOwner</code>.
     *
     * @return listOwner Object.
     */
    public O getOwner() {
        return _owner.getOwner();
    }

    /**
     * Appends the specified element to the specified position of this list.
     *
     * @param index
     *            index at which to insert first element.
     *
     * @param element
     *            Element which is to be inserted.
     */
    public void add(int index, T element) {
        _list.add(index, element);
        _owner.own(element);
    }

    /**
     * Appends the specified element to the end of this list .
     * <p>
     * Lists that support this operation may place limitations on what elements
     * may be added to this list. In particular, some lists will refuse to add
     * null elements, and others will impose restrictions on the type of
     * elements that may be added.
     * </p>
     *
     * @param o
     *            Object which has to be appended.
     *
     * @return <code>true</code> if the object is successfully appended.
     *         <code>false</code>otherwise.
     */
    public boolean add(T o) {
        boolean result = _list.add(o);
        _owner.own(o);
        return result;
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator. The behavior of this operation is unspecified if
     * the specified collection is modified while the operation is in progress.
     *
     * @param c
     *            Collection object which has to be added
     * @return <code>true</code> if the object is successfully appended.
     *         <code>false</code>otherwise
     */
    public boolean addAll(Collection<? extends T> c) {
        boolean result = _list.addAll(c);
        _owner.ownAll(c);
        return result;
    }

    /**
     * Inserts all of the elements in the specified collection into this list at
     * the specified position . Shifts the element currently at that position
     * (if any) and any subsequent elements to the right (increases their
     * indices). The new elements will appear in this list in the order that
     * they are returned by the specified collection's iterator.
     *
     * @param index
     *            index at which to insert first element from the specified
     *            collection.
     * @param c
     *            objects to be inserted.
     * @return <code>true</code> if the object is successfully appended.
     *         <code>false</code>otherwise
     */
    public boolean addAll(int index, Collection<? extends T> c) {
        boolean result = _list.addAll(index, c);
        _owner.ownAll(c);
        return result;
    }

    /**
     * Removes all of the elements from this list.This list will be empty after
     * this call returns.
     */
    public void clear() {
        _owner.freeAll(_list);
        _list.clear();
    }
    /**
     * Removes the element at the specified position in this list . Shifts any
     * subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     *
     * @param index
     *            the index of the element to removed.
     * @return the element previously at the specified position.
     */
    public T remove(int index) {
        T result = _list.remove(index);
        _owner.unassociate(result);
        return result;
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index
     *            index of element to replace.
     * @param element
     *            element to be stored at the specified position.
     * @return the element previously at the specified position.
     */
    public T set(int index, T element) {
        T result = _list.set(index, element);
        _owner.unassociate(result);
        _owner.own(element);
        return result;
    }

    /**
     * Returns an OwnedIterator object with <code>lstOwner</code> and
     * iterator.
     *
     * @return OwnedIterator
     */
    public Iterator<T> iterator() {
        return new OwnedIterator<O, T>(_owner, _list.iterator());
    }

    /**
     * Returns an OwnedIterator object with <code>lstOwner</code> and
     * listiterator.
     *
     * @return OwnedIterator
     */
    public ListIterator<T> listIterator() {
        return new OwnedListIterator<O, T>(_owner, _list.listIterator());
    }

    /**
     * Returns an OwnedIterator object with <code>lstOwner</code> and
     * specified position of the listiterator.
     *
     * @param index
     *            index of the element to be pointed.
     * @return OwnedIterator
     */
    public ListIterator<T> listIterator(int index) {
        return new OwnedListIterator<O, T>(_owner, _list.listIterator(index));
    }

    /**
     * Returns <code>true</code> if this list contains the specified element.
     * More formally, returns <code>true</code> if and only if this list
     * contains at least one element
     *
     * @param o
     *            element whose presence in this list is to be tested.
     * @return <code>true</code> if this list contains the specified
     *         element.Otherwise <code>false</code>
     *
     */
    public boolean contains(Object o) {
        return _list.contains(o);
    }

    /**
     * Returns <code>true</code> if this list contains all of the elements of
     * the specified collection.
     *
     * @param c
     *            collection to be checked for containment in this list.
     * @return <code>true</code> if this list contains all of the elements of
     *         the specified collection.
     */
    public boolean containsAll(Collection<?> c) {
        return _list.containsAll(c);
    }

    /**
     * Compares the specified object with this list for equality. Returns
     * <code>true</code> if and only if the specified object is also a list,
     * both lists have the same size, and all corresponding pairs of elements in
     * the two lists are <i>equal</i>. In other words, two lists are defined to
     * be equal if they contain the same elements in the same order.
     *
     * @param o
     *            the object to be compared for equality with this list.
     * @return <code>true</code> if the specified object is equal to this
     *         list.
     */
	@Override
	public boolean equals(Object o) {
        return _list.equals(o);
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index
     *            index of element to return.
     * @return the element at the specified position in this list
     */
    public T get(int index) {
        return _list.get(index);
    }

    /**
     * Returns the <code>hashCode</code> value of this <code>List</code>.
     *
     * @return hashCode value.
     *
     */
    @Override
	public int hashCode() {
        return _list.hashCode();
    }

    /**
     * Returns the index in this list of the first occurrence of the specified
     * element, or -1 if this list does not contain this element.
     *
     * @param o
     *            element to search for.
     * @return the index in this list of the first occurrence of the specified
     *         element, or -1 if this list does not contain this element.
     */
    public int indexOf(Object o) {
        return _list.indexOf(o);
    }

    /**
     * Returns <code>true</code> if this list contains no elements.
     *
     * @return <code>true</code> if this list contains no elements.
     */
    public boolean isEmpty() {
        return _list.isEmpty();
    }

    /**
     * Returns the index in this list of the last occurrence of the specified
     * element, or -1 if this list does not contain this element.
     *
     * @param o
     *            element to search for.
     * @return the index in this list of the last occurrence of the specified
     *         element, or -1 if this list does not contain this element.
     */
    public int lastIndexOf(Object o) {
        return _list.lastIndexOf(o);
    }

    /**
     * Returns the number of elements in this list. If this list contains more
     * than <code>Integer.MAX_VALUE</code> elements, returns
     * <code>Integer.MAX_VALUE</code>.
     *
     * @return the number of elements in this list.
     */
    public int size() {
        return _list.size();
    }

    /**
     * Returns the initialized objects. This method eliminates the need for
     * explicit range operations . Any operation that expects a list can be used
     * as a range operation by passing a subList view instead of a whole list.
     *
     * @param fromIndex
     *            position to start with.
     * @param toIndex
     *            position to end with.
     * @return wrap initialized objects.
     */
    public List<T> subList(int fromIndex, int toIndex) {
        return wrap(getOwner(), _list.subList(fromIndex, toIndex));
    }

    /**
     * Returns the initialized objects.
     *
     * @param owner
     *            object which to be initialized.
     * @param list
     *            object which to be initialized.
     * @return initialized <code>owner,list</code> object.
     */
    protected List<T> wrap(O owner, List<T> list) {
        return new OwnedListImpl<O, T>(owner, list);
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence.
     *
     * @return an array containing the elements of this list.
     *
     */
	public Object[] toArray() {
        return _list.toArray();
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence; the runtime type of the returned array is that of the specified
     * array.
     *
     * @param a
     *            the array into which the elements of this list are to be
     *            stored, if it is big enough; otherwise, a new array of the
     *            same runtime type is allocated for this purpos
     * @return an array containing the elements of this list.
     */
    public <X> X[] toArray(X[] a) {
        return _list.toArray(a);
    }

    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that "textually
     * represents" this object.
     *
     * @return string representation of the object.
     */
    @Override
	public String toString() {
        return _list.toString();
    }

    /**
     * Removes the first occurrence in this list of the specified element . If
     * this list does not contain the element, it is unchanged. More formally,
     * removes the element with the lowest index.
     *
     * @param o
     *            element to be removed from this list, if present.
     * @return <code>true</code> if this list contained the specified element.
     */
    @SuppressWarnings("unchecked")
	public boolean remove(Object o) {
        boolean changed = _list.remove(o);
        if (changed) {
            _owner.unassociate((T) o);
        }
        return changed;
    }

    /**
     * Removes from this list all the elements that are contained in the
     * specified collection.
     *
     * @param c
     *            collection that defines which elements will be removed from
     *            this list.
     * @return <code>true</code> if this list changed as a result of the call.
     */
    @SuppressWarnings("unchecked")
	public boolean removeAll(Collection<?> c) {
        boolean changed = _list.removeAll(c);
        if (changed) {
            _owner.freeAll((Collection<T>) c);
        }
        return changed;
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection. In other words, removes from this list all the
     * elements that are not contained in the specified collection.
     *
     * @param c
     *            collection that defines which elements this set will retain.
     *
     * @return <code>true</code> if this list changed as a result of the call.
     */
    public boolean retainAll(Collection<?> c) {
        _owner.freeAll(_list);
        boolean changed = _list.retainAll(c);
        _owner.ownAll(_list);
        return changed;
    }
}
