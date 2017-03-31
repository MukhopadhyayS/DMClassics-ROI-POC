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
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

public class TestOwnedIterator extends UnitTest {

    private Foo _owner;
    private Bar _first;
    private Bar _second;
    private List<Bar> _list;
    private Iterator<Bar> _iterator;

    public TestOwnedIterator(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestOwnedIterator.class, OwnedIterator.class);
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
        _iterator = _list.iterator();
    }

    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testNext() {
        assertTrue(_iterator.hasNext());
        assertEquals(_first, _iterator.next());
        assertTrue(_iterator.hasNext());
        assertEquals(_second, _iterator.next());
        assertFalse(_iterator.hasNext());
    }

    public void testRemove() {
        assertEquals(_owner, _first.getFoo());
        assertEquals(_owner, _second.getFoo());

        assertTrue(_iterator.hasNext());
        assertEquals(_first, _iterator.next());

        _iterator.remove();

        assertNull(_first.getFoo());
        assertEquals(_owner, _second.getFoo());

        assertTrue(_iterator.hasNext());
        assertEquals(_second, _iterator.next());

        _iterator.remove();

        assertNull(_first.getFoo());
        assertNull(_second.getFoo());

        assertFalse(_iterator.hasNext());
    }
    public void testLstOwner() {
        List<Object> original = new ArrayList<Object>();
        OwnedIterator<Object, Object> ownedIterator = new OwnedIterator<Object, Object>();
        ListOwner<Object, Object> listOwner = new ListOwner<Object, Object>(original);
        ownedIterator.setLstOwner(listOwner);
        assertEquals(listOwner, ownedIterator.getLstOwner());

    }
}
