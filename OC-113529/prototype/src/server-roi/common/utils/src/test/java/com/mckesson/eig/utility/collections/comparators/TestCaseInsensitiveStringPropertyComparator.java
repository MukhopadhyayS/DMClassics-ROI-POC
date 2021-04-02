/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and/or one of its subsidiaries and is protected
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
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
 * Test case to test the class CaseInsensitiveStringPropertyComparator.
 */
public class TestCaseInsensitiveStringPropertyComparator extends TestCase {

    /**
     * A test value.
     */
    private static final int TEST_VAL_3 = 3;

    /**
     * A test value.
     */
    private static final int TEST_VAL_4 = 4;

    /**
     * A test value.
     */
    private static final int TEST_VAL_5 = 5;

    /**
     * List holds a new instance of the type ArrayList.
     */
    private List<WithString> _list;

    /**
     * Array of strings.
     */
    private final String[] _letters = {"A", "C", "H", "i", "v"};

    /**
     * Constructs the test case.
     */
    public TestCaseInsensitiveStringPropertyComparator() {
        super();
    }

    /**
     * Creates a suite that tests one class and with one test.
     *
     * @return A test suite to test the given class
     */
    public static Test suite() {
        return new CoverageSuite(
        	TestCaseInsensitiveStringPropertyComparator.class,
            CaseInsensitiveStringPropertyComparator.class);
    }

    /**
     * Set up the test. Creates an instance of the class that needs to be
     * tested.
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
        CaseInsensitiveStringPropertyComparator<WithString> comp =
        	new CaseInsensitiveStringPropertyComparator<WithString>("sortOrder");
        Collections.sort(_list, comp);
        Iterator<WithString> iter = _list.iterator();
        for (int ii = 0; ii < TEST_VAL_5; ii++) {
            WithString withStr = iter.next();
            assertEquals(withStr.getSortOrder(), _letters[ii]);
        }
    }

    /**
     * Inner class.
     */
    public static class WithString {

        /**
         * Member variable.
         */
        private final String _sortOrder;

        /**
         * Constructor that sets the value of the sortOrder.
         *
         * @param val
         *            Sort order
         */
        public WithString(String val) {
            _sortOrder = val;
        }

        /**
         * Method to return the sort order.
         *
         * @return Returns the sort order
         */
        public String getSortOrder() {
            return _sortOrder;
        }
    }
}
