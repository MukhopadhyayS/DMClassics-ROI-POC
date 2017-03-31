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
package com.mckesson.eig.utility.util.data;

/**
 * Only used for testing reflection code.
 */
public final class MockData {

    public static final String TEST_STRING = "This is a Test.";

    /**
     * Constructor for testing reflection. Calls to methods to satisfy compiler
     * warnings.
     */
    private MockData() {
        this("foo");
        getTestString();
        getStaticString();
        getSameString("foo");
    }

    private MockData(String s) {
    }

    private String getTestString() {
        return TEST_STRING;
    }

    private static String getStaticString() {
        return TEST_STRING;
    }

    private String getSameString(String s) {
        return s;
    }
}
