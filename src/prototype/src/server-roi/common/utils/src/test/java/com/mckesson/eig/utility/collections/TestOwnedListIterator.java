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
import java.util.List;
import java.util.ListIterator;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

public class TestOwnedListIterator extends UnitTest {

    private Foo _owner;
    private Bar _first;
    private Bar _second;
    private List<Bar> _list;
    private ListIterator<Bar> _iterator;

    public TestOwnedListIterator(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestOwnedListIterator.class, OwnedListIterator.class);
    }

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        _owner = new Foo();
        _first = new Bar();
        _second = new Bar();

        List<Bar> l = new ArrayList<Bar>();
        l.add(_first);
        l.add(_second);

        _list = new OwnedListImpl<Foo, Bar>(_owner, l);
        _iterator = _list.listIterator();
    }

    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPrevious() {
        assertEquals(_first, _iterator.next());
        assertEquals(_second, _iterator.next());

        assertTrue(_iterator.hasPrevious());
        assertEquals(_second, _iterator.previous());
        assertTrue(_iterator.hasPrevious());
        assertEquals(_first, _iterator.previous());
        assertFalse(_iterator.hasPrevious());
    }

    public void testIndexes() {
        assertEquals(0, _iterator.nextIndex());
        assertEquals(_first, _iterator.next());

        assertEquals(1, _iterator.nextIndex());
        assertEquals(_second, _iterator.next());

        assertEquals(1, _iterator.previousIndex());
        assertEquals(_second, _iterator.previous());

        assertEquals(0, _iterator.previousIndex());
        assertEquals(_first, _iterator.previous());
    }

    public void testRemove() {
        assertEquals(_first, _iterator.next());
        assertEquals(_second, _iterator.next());

        assertEquals(_owner, _first.getFoo());
        assertEquals(_owner, _second.getFoo());

        assertTrue(_iterator.hasPrevious());
        assertEquals(_second, _iterator.previous());

        _iterator.remove();

        assertNull(_second.getFoo());
        assertEquals(_owner, _first.getFoo());

        assertTrue(_iterator.hasPrevious());
        assertEquals(_first, _iterator.previous());

        _iterator.remove();

        assertNull(_second.getFoo());
        assertNull(_first.getFoo());

        assertFalse(_iterator.hasPrevious());
        assertEquals(0, _list.size());
    }

    public void testSet() {
        assertEquals(_first, _iterator.next());
        _iterator.set(_second);

        assertEquals(_second, _iterator.next());
        _iterator.set(_first);

        assertEquals(_owner, _first.getFoo());
        assertEquals(_owner, _second.getFoo());

        assertEquals(2, _list.size());
        assertEquals(_second, _list.get(0));
        assertEquals(_first, _list.get(1));
    }

    public void testAdd() {
        assertEquals(_first, _iterator.next());
        _iterator.remove();

        assertNull(_first.getFoo());
        assertEquals(_owner, _second.getFoo());

        assertEquals(_second, _iterator.next());
        _iterator.add(_first);

        assertEquals(_owner, _second.getFoo());
        assertEquals(_owner, _first.getFoo());

        assertEquals(2, _list.size());
        assertEquals(_second, _list.get(0));
        assertEquals(_first, _list.get(1));
    }

    public void testSetSetRemove() {
        assertEquals(_first, _iterator.next());
        _iterator.remove();

        assertNull(_first.getFoo());
        assertEquals(_owner, _second.getFoo());

        assertEquals(_second, _iterator.next());
        _iterator.set(_first);

        assertNull(_second.getFoo());
        assertEquals(_owner, _first.getFoo());

        assertEquals(1, _list.size());

        _iterator.set(_second);

        assertNull(_first.getFoo());
        assertEquals(_owner, _second.getFoo());

        assertEquals(1, _list.size());

        _iterator.remove();

        assertNull(_first.getFoo());
        assertNull(_second.getFoo());

        assertEquals(0, _list.size());
    }
}
