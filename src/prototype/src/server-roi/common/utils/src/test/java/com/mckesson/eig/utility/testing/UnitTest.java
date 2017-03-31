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

import junit.framework.TestCase;

// import junit.framework.*;

public class UnitTest extends TestCase {

    public static final int NEG_TWO = -2;
    public static final int NEG_ONE = -1;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int ELEVEN = 11;
    public static final int TWELVE = 12;
    public static final int EIGHTY_EIGHT = 88;
    public static final int HUNDRED = 100;
    public static final int THOUSAND_HUNDRED_ELEVEN = 1111;
    private String _previousThreadName;

    public UnitTest() {
        super();
    }

    public UnitTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();

        // Due to auth changes we need to set application.home
        System.setProperty("application.home", System.getenv("JBOSS_HOME") + "\\server\\default");

        String testName = getName();
        if (testName != null && testName.trim().length() > 0) {
            Thread current = Thread.currentThread();
            _previousThreadName = current.getName();
            current.setName(testName.trim());
        }
        UnitTestSpringInitializer.init();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        if (_previousThreadName != null) {
            Thread.currentThread().setName(_previousThreadName);
        }
        TestUtilities.setAllFieldsToNull(this);
    }
}
