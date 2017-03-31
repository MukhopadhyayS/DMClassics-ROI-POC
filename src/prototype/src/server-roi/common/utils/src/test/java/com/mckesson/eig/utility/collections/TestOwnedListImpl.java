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
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.ObjectVerifier;
import com.mckesson.eig.utility.testing.UnitTest;

public class TestOwnedListImpl extends UnitTest {

    private Foo _owner;
    private Bar _first;
    private Bar _second;
    private List<Bar> _list;

    public TestOwnedListImpl(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestOwnedListImpl.class, OwnedListImpl.class);
    }

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        _owner = new Foo();
        _first = new Bar();
        _second = new Bar();
    }

    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
    }

    protected OwnedList<Foo, Bar> create(Foo owner, List<Bar> original) {
        return new OwnedListImpl<Foo, Bar>(owner, original);
    }

    protected OwnedList<Foo, Bar> create(Foo owner) {
        return new OwnedListImpl<Foo, Bar>(owner);
    }

    public void testAccessors() {
        List<Bar> original = new ArrayList<Bar>();
        OwnedList<Foo, Bar> ol = create(_owner, original);
        assertEquals(_owner, ol.getOwner());

        ol.setOwner(_owner);
        assertEquals(_owner, ol.getOwner());

        Foo newOwner = new Foo();
        ol.setOwner(newOwner);
        assertEquals(newOwner, ol.getOwner());
    }

    public void testEquality() {
        List<Bar> original = new ArrayList<Bar>();
        original.add(_first);
        original.add(_second);

        _list = create(_owner, original);

        assertTrue(_list.equals(original));
        assertEquals(original.hashCode(), _list.hashCode());
        assertEquals(original.toString(), _list.toString());

        assertEquals(original.indexOf(_second), _list.indexOf(_second));
        assertEquals(original.lastIndexOf(_first), _list.lastIndexOf(_first));
        assertEquals(original.isEmpty(), _list.isEmpty());

        assertTrue(Arrays.equals(original.toArray(), _list.toArray()));
        assertTrue(Arrays.equals(original.toArray(new Bar[2]), _list
                .toArray(new Bar[2])));

        Collection<Bar> c = new LinkedList<Bar>();
        c.add(_first);

        assertEquals(original.containsAll(c), _list.containsAll(c));
        assertTrue(_list.contains(_first));
        assertTrue(_list.contains(_second));
    }

    public void testAdd() {
        _list = create(_owner);
        assertTrue(_list.add(_first));
        assertEquals(1, _list.size());
        assertEquals(_first, _list.get(0));
        assertEquals(_owner, _first.getFoo());

        assertTrue(_list.add(_second));
        assertEquals(2, _list.size());
        assertEquals(_first, _list.get(0));
        assertEquals(_second, _list.get(1));
        assertEquals(_owner, _first.getFoo());
        assertEquals(_owner, _second.getFoo());
    }

    public void testAddWithIndex() {
        _list = create(_owner);
        _list.add(0, _first);
        assertEquals(1, _list.size());
        assertEquals(_first, _list.get(0));
        assertEquals(_owner, _first.getFoo());

        _list.add(0, _second);
        assertEquals(2, _list.size());
        assertEquals(_second, _list.get(0));
        assertEquals(_first, _list.get(1));
        assertEquals(_owner, _first.getFoo());
        assertEquals(_owner, _second.getFoo());
    }

    public void testAddAll() {
        Collection<Bar> c = new ArrayList<Bar>();
        c.add(_first);
        c.add(_second);

        _list = create(_owner);
        assertTrue(_list.addAll(c));

        assertEquals(2, _list.size());
        assertEquals(_first, _list.get(0));
        assertEquals(_second, _list.get(1));
        assertEquals(_owner, _first.getFoo());
        assertEquals(_owner, _second.getFoo());
    }

    public void testAddAllWithIndex() {
        Collection<Bar> c = new ArrayList<Bar>();
        c.add(_second);

        _list = create(_owner);
        _list.add(_first);

        assertTrue(_list.addAll(0, c));

        assertEquals(2, _list.size());
        assertEquals(_second, _list.get(0));
        assertEquals(_first, _list.get(1));
        assertEquals(_owner, _first.getFoo());
        assertEquals(_owner, _second.getFoo());
    }

    public void testSet() {
        _list = create(_owner);
        _list.add(_first);
        _list.add(_first);

        assertEquals(_first, _list.set(0, _second));

        assertEquals(2, _list.size());
        assertEquals(_second, _list.get(0));
        assertEquals(_first, _list.get(1));
        assertEquals(_owner, _second.getFoo());
        assertEquals(_owner, _first.getFoo());

        assertEquals(_first, _list.set(1, _second));

        assertEquals(2, _list.size());
        assertEquals(_second, _list.get(0));
        assertEquals(_second, _list.get(1));
        assertEquals(_owner, _second.getFoo());
        assertNull(_first.getFoo());
    }

    public void testRemoveWithIndex() {
        _list = create(_owner);
        _list.add(_first);
        _list.add(_second);

        assertEquals(_second, _list.remove(1));

        assertEquals(1, _list.size());
        assertEquals(_first, _list.get(0));
        assertEquals(_owner, _first.getFoo());
        assertNull(_second.getFoo());
    }

    public void testRemoveWithObject() {
        _list = create(_owner);
        _list.add(_first);
        _list.add(_second);

        assertTrue(_list.remove(_first));

        assertEquals(1, _list.size());
        assertEquals(_second, _list.get(0));
        assertEquals(_owner, _second.getFoo());
        assertNull(_first.getFoo());

        assertFalse(_list.remove("Foo"));

        assertEquals(1, _list.size());
        assertEquals(_second, _list.get(0));
    }

    public void testRemoveAll() {
        _list = create(_owner);
        _list.add(_first);
        _list.add(_second);

        Collection<Bar> c = new ArrayList<Bar>();
        c.add(_second);

        assertTrue(_list.removeAll(c));

        assertEquals(1, _list.size());
        assertEquals(_first, _list.get(0));
        assertEquals(_owner, _first.getFoo());
        assertNull(_second.getFoo());

        Collection<String> c2 = new ArrayList<String>();
        c2.add("foo");

        assertFalse(_list.removeAll(c2));

        assertEquals(1, _list.size());
        assertEquals(_first, _list.get(0));
    }

    public void testRetainAll() {
        _list = create(_owner);
        _list.add(_first);
        _list.add(_second);

        Collection<Bar> c = new ArrayList<Bar>();
        c.add(_second);

        assertTrue(_list.retainAll(c));

        assertEquals(1, _list.size());
        assertEquals(_second, _list.get(0));
        assertEquals(_owner, _second.getFoo());
        assertNull(_first.getFoo());
    }

    public void testClear() {
        _list = create(_owner);
        _list.add(_first);
        _list.add(_second);

        assertEquals(2, _list.size());
        assertEquals(_owner, _first.getFoo());
        assertEquals(_owner, _second.getFoo());

        _list.clear();

        assertEquals(0, _list.size());
        assertNull(_first.getFoo());
        assertNull(_second.getFoo());
    }

    public void testConstructorWithList() {
        List<Bar> original = new ArrayList<Bar>();
        original.add(_first);
        original.add(_second);

        _list = create(_owner, original);

        assertEquals(2, _list.size());
        assertEquals(_first, _list.get(0));
        assertEquals(_second, _list.get(1));
        assertEquals(_owner, _first.getFoo());
        assertEquals(_owner, _second.getFoo());
    }

    public void testSubList() {
        _list = create(_owner);
        _list.add(_first);
        _list.add(_second);

        assertEquals(2, _list.size());
        assertEquals(_owner, _first.getFoo());
        assertEquals(_owner, _second.getFoo());

        List<Bar> sub = _list.subList(0, 1);

        assertEquals(1, sub.size());
        assertEquals(_first, sub.get(0));

        sub.clear();

        assertEquals(1, _list.size());
        assertEquals(_second, _list.get(0));
        assertEquals(_owner, _second.getFoo());
        assertNull(_first.getFoo());
    }

    public void testIterators() {
        List<Bar> l = new ArrayList<Bar>();
        l.add(_first);
        l.add(_second);

        _list = create(_owner, l);

        Iterator<Bar> i = _list.iterator();
        assertNotSame(i, _list.iterator());

        ListIterator<Bar> li = _list.listIterator();
        assertNotSame(li, _list.listIterator());

        ListIterator<Bar> lii = _list.listIterator(0);
        assertNotSame(lii, _list.listIterator(0));
    }

    public void testSerializable() {
        _list = create(_owner);
        ObjectVerifier.verifySerialization(_list);
    }
}
