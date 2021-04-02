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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestIntegerPropertyComparitor extends TestCase {
    private static final int TEST_VAL_3 = 3;
    private static final int TEST_VAL_4 = 4;
    private static final int TEST_VAL_5 = 5;
    private List<WithInteger> _list1;

    public TestIntegerPropertyComparitor() {
        super();
    }

    public static Test suite() {
        Class<TestIntegerPropertyComparitor> class1 = TestIntegerPropertyComparitor.class;
		return new CoverageSuite(
            CoverageSuite.buildTestCaseArray(class1),
            new Class< ? >[]{
                IntegerPropertyComparator.class,
                IntegerPropertyComparatorDescending.class});
    }

    @Override
	protected void setUp() throws Exception {
        _list1 = new ArrayList<WithInteger>();
        _list1.add(new WithInteger(new Integer(TEST_VAL_3)));
        _list1.add(new WithInteger(new Integer(2)));
        _list1.add(new WithInteger(new Integer(1)));
        _list1.add(new WithInteger(new Integer(TEST_VAL_4)));
        _list1.add(new WithInteger(new Integer(0)));
    }

    @Override
	protected void tearDown() throws Exception {
        _list1.clear();
        _list1 = null;
    }

    public void testAscendingComparator() {
        IntegerPropertyComparator<WithInteger> comp =
        	new IntegerPropertyComparator<WithInteger>("sortOrder");
        Collections.sort(_list1, comp);
        Iterator<WithInteger> iter = _list1.iterator();
        for (int ii = 0; ii < TEST_VAL_5; ii++) {
            WithInteger withInt = iter.next();
            assertEquals(withInt.getSortOrder(), new Integer(ii));
        }
    }

    public void testDecendingComparator() {
        IntegerPropertyComparatorDescending<WithInteger> comp =
        	new IntegerPropertyComparatorDescending<WithInteger>("sortOrder");
        Collections.sort(_list1, comp);
        Iterator<WithInteger> iter = _list1.iterator();
        for (int ii = TEST_VAL_4; ii >= 0; ii--) {
            WithInteger withInt = iter.next();
            assertEquals(withInt.getSortOrder(), new Integer(ii));
        }
    }
    public void testSetStrproperty() {
        IntegerPropertyComparator<WithInteger> comp =
        	new IntegerPropertyComparator<WithInteger>();
        comp.setStrProperty("sortOrder");
        assertEquals("sortOrder", comp.getStrProperty());
        Collections.sort(_list1, comp);
        Iterator<WithInteger> iter = _list1.iterator();
        for (int ii = 0; ii < TEST_VAL_5; ii++) {
            WithInteger withInt = iter.next();
            assertEquals(withInt.getSortOrder(), new Integer(ii));
        }
        }

    public class WithInteger {
        private final Integer _sortOrder;

        public WithInteger(Integer val) {
            _sortOrder = val;
        }

        public Integer getSortOrder() {
            return _sortOrder;
        }
    }
}
