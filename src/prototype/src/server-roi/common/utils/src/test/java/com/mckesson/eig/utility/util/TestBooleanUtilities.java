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
package com.mckesson.eig.utility.util;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestBooleanUtilities extends TestCase {

    public TestBooleanUtilities(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestBooleanUtilities.class,
                BooleanUtilities.class);
    }

    public void testValueOf() {
        assertEquals(Boolean.TRUE, BooleanUtilities.valueOf(true));
        assertEquals(Boolean.FALSE, BooleanUtilities.valueOf(false));
    }
    /**
     * Tests the constructor is private.
     */
    public void testConstructorIsPrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(BooleanUtilities.class));
        ReflectionUtilities.callPrivateConstructor(BooleanUtilities.class);
    }
    public void testValueOfThatTakesString() {
        assertTrue(BooleanUtilities.valueOf("true"));
        assertTrue(BooleanUtilities.valueOf("t"));
        assertTrue(BooleanUtilities.valueOf("TRUE"));
        assertTrue(BooleanUtilities.valueOf("Y"));
        assertFalse(BooleanUtilities.valueOf("false"));
        assertFalse(BooleanUtilities.valueOf("f"));
        assertFalse(BooleanUtilities.valueOf("FALSE"));
        assertFalse(BooleanUtilities.valueOf("N"));
        assertFalse(BooleanUtilities.valueOf("foo"));
    }

    public void testValueOfThatTakesStringAndDefault() {
        assertTrue(BooleanUtilities.valueOf("true", false));
        assertTrue(BooleanUtilities.valueOf("t", false));
        assertFalse(BooleanUtilities.valueOf("FALSE", true));
        assertFalse(BooleanUtilities.valueOf("N", true));
        assertTrue(BooleanUtilities.valueOf("foo", true));
    }

}
