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

package com.mckesson.eig.utility.testing;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.mckesson.eig.utility.util.ObjectUtilities;

public class CoverageSuite extends TestSuite {

    /**
     * Constructor for creating a suite that tests one class and with one test.
     *
     * @param test
     *            This is the test
     * @param classToTest
     *            This is the class you are testing.
     */
    @SuppressWarnings("unchecked")
	public CoverageSuite(Class< ? extends TestCase > test, Class< ? > classToTest) {
        this(new Class[]{test}, new Class[]{classToTest});
    }

    /**
     * Constructor for creating a suite that tests more than one class using
     * more than one test class.
     *
     * @param tests
     *            These are the tests.
     * @param classesToTest
     *            These are the classes that we'll test for coverage.
     */
    public CoverageSuite(Class< ? extends TestCase >[] tests, Class< ? >[] classesToTest) {
        // super(classesToTest);
        StringBuffer buffer = new StringBuffer();
        buffer.append("Coverage Suite - ");
        for (int i = 0; i < tests.length; i++) {
            Class< ? extends TestCase > clazz = tests[i];
			addTestSuite(clazz);
            buffer.append(ObjectUtilities.getUnqualifiedName(clazz));
            if (i < (tests.length - 1)) {
                buffer.append(" ");
            }
        }
        setName(buffer.toString());
    }

    @SuppressWarnings("unchecked")
	public static < T extends TestCase > Class< T >[] buildTestCaseArray(Class< T > clazz) {
    	return new Class[] { clazz };
    }

    @Override
	public String toString() {
        if (getName() != null) {
            return getName();
        }
        return super.toString();
    }
}
