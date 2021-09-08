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
package com.mckesson.eig.utility.net;
import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.utility.util.ClassUtilities;
import com.mckesson.eig.utility.util.ReflectionUtilities;

/**
 * Test case to test the class HttpConstants.
 */
public class TestHttpConstants extends UnitTest {

    /**
     * Constructs the test Case.
     * 
     * @param name
     *            Name of the test case
     */
    public TestHttpConstants(String name) {
        super(name);
    }

    /**
     * Tests Constructor is private.
     */
    public void testConstructorIsPrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(HttpConstants.class));
        ReflectionUtilities.callPrivateConstructor(HttpConstants.class);
    }

    /**
     * Tests the constant CONTENT_TYPE.Valid value is Content-Type.
     */
    public void testContentType() {

        assertEquals("Content-Type", HttpConstants.CONTENT_TYPE);
    }

    /**
     * Tests the constant CACHE_CONTROL.Valid value is Cache-Control.
     */
    public void testCacheControl() {
        assertEquals("Cache-Control", HttpConstants.CACHE_CONTROL);
    }
    
    /**
     * Tests the constant PRAGMA.Valid value is Pragma.
     */
    public void testPragma() {
        assertEquals("Pragma", HttpConstants.PRAGMA);
    }
    
    /**
     * Tests the constant NO_CACHE.Valid value is no-cache.
     */
    public void testnocache() {
        assertEquals("no-cache", HttpConstants.NO_CACHE);
    }
    
    /**
     * Tests the constant EXPIRES.Valid value is Expires.
     */
    public void testExpires() {
        assertEquals("Expires", HttpConstants.EXPIRES);
    }

}
