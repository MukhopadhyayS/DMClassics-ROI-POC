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

package com.mckesson.eig.utility.testing;

public class TestDynamicTestSuiteBuilder extends UnitTest {
    private static final int COMPARE_VALUE = 3;

    private DynamicTestSuiteBuilder _finder;

    public TestDynamicTestSuiteBuilder(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        _finder = new DynamicTestSuiteBuilder(this.getClass());
    }

    protected void tearDown() throws Exception {
        _finder = null;
        super.tearDown();
    }

    public void testGetTests() {
        assertTrue(_finder.getTests().size() >= COMPARE_VALUE);
    }

    protected class ThisIsHereToTestInternalClassInFinder {
    }
}
