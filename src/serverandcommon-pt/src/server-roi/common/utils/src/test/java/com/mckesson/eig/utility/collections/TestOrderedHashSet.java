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

import com.mckesson.eig.utility.testing.ObjectVerifier;
import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.utility.util.ClassUtilities;
import com.mckesson.eig.utility.util.ReflectionUtilities;

public class TestOrderedHashSet extends UnitTest {

    private OrderedHashSet<Integer> _set;

    @SuppressWarnings("unchecked")
	public void testBasics() {
        Integer one = new Integer(1);
        Integer two = new Integer(2);
        Integer oneAgain = new Integer(1);

        _set = new OrderedHashSet<Integer>();

        assertEquals(ZERO, _set.size());
        assertTrue(_set.isEmpty());

        assertTrue(_set.add(one));
        assertTrue(_set.add(two));
        assertFalse(_set.add(oneAgain));

        assertEquals(TWO, _set.size());
        assertFalse(_set.isEmpty());

        Iterator<Integer> i = _set.iterator();
        assertSame(one, i.next());
        assertSame(two, i.next());
        assertFalse(i.hasNext());

        assertTrue(_set.contains(one));
        assertTrue(_set.contains(oneAgain));

        OrderedHashSet<Integer> clone = (OrderedHashSet) _set.clone();

        assertEquals(_set, clone);
        assertNotSame(_set, clone);

        ObjectVerifier.verifySerialization(_set);

        assertTrue(_set.remove(one));
        assertFalse(_set.remove(one));

        assertEquals(ONE, _set.size());

        _set.clear();

        assertEquals(ZERO, _set.size());
        assertTrue(_set.isEmpty());

        Collection<Integer> arr = new ArrayList<Integer>();
        arr.add(one);
        arr.add(two);
        arr.add(oneAgain);
        _set = new OrderedHashSet<Integer>(arr);
        assertEquals(TWO, _set.size());
        assertEquals(THREE, arr.size());
        final double testVal = 0.7;
        _set = new OrderedHashSet<Integer>(TWO, (float) testVal);
        _set.add(one);
        _set.add(two);
        _set.add(new Integer(THREE));
        _set.add(new Integer(FOUR));
        _set.add(new Integer(FIVE));
        _set.add(new Integer(SIX));
        _set.add(new Integer(SIX));
        assertEquals(_set.size(), SIX);
        _set.clear();
        assertEquals(ZERO, _set.size());
        assertTrue(_set.isEmpty());

    }

    public void testConstructorIsPrivate() {
        assertTrue(ClassUtilities.areAllConstructorsPrivate(CollectionFactory.class));
        ReflectionUtilities.callPrivateConstructor(CollectionFactory.class);
    }
}
