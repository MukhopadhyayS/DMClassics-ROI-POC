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

/**
 * Test case to test the class StringPropertyComparator.
 */
public class TestStringPropertyComparator extends TestCase {

	/**
     * A test value.
     */
    private static final int TEST_VAL_3 = 3;

    /**
     * A test Value.
     */
    private static final int TEST_VAL_4 = 4;

    /**
     * A test value.
     */
    private static final int TEST_VAL_5 = 5;

    /**
     * List.
     */
    private List<WithString> _list;

    /**
     * Array of strings.
     */
    private final String[] _letters = {"A", "C", "H", "i", "v"};

    /**
     * Constructs the test case with the given name.
     */
    public TestStringPropertyComparator() {
    }

    /**
     * @return Coverage Suite
     */
    public static Test suite() {
        return new CoverageSuite(
                TestStringPropertyComparator.class, StringPropertyComparator.class);
    }

    /**
     * Set up the test. Creates an instance of the class that need to be tested.
     *
     * @throws Exception
     *             if the set up is not made properly.
     */
    @Override
	protected void setUp() throws Exception {
        _list = new ArrayList<WithString>();
        _list.add(new WithString(_letters[TEST_VAL_3]));
        _list.add(new WithString(_letters[2]));
        _list.add(new WithString(_letters[1]));
        _list.add(new WithString(_letters[TEST_VAL_4]));
        _list.add(new WithString(_letters[0]));
    }

    /**
     * This will remove all the data associated with the test.
     *
     * @throws Exception
     *             if the tear down is not made properly.
     */
    @Override
	protected void tearDown() throws Exception {
        _list.clear();
        _list = null;
    }

    /**
     * Tests sorting of the list.
     */
    public void testAscendingComparator() {

        StringPropertyComparator<WithString> comp =
        	new StringPropertyComparator<WithString>("sortOrder");
        Collections.sort(_list, comp);

        Iterator<WithString> iter = _list.iterator();
        for (int ii = 0; ii < TEST_VAL_5; ii++) {
            WithString withStr = iter.next();
            assertEquals(withStr.getSortOrder(), _letters[ii]);
        }
    }

    /**
     * Inner Class.
     */
    public class WithString {
        /**
         * Member variable.
         */
        private final String _sortOrder;

        /**
         * Constructor that sets the SortOrder.
         *
         * @param val
         *            Sort order
         */
        public WithString(String val) {
            _sortOrder = val;
        }

        /**
         * Method to get the SortOrder.
         *
         * @return Returns the sort order
         */
        public String getSortOrder() {
            return _sortOrder;
        }
    }
}
