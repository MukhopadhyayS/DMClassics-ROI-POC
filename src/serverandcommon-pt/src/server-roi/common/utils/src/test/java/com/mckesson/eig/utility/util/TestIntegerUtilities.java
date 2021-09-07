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

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

public class TestIntegerUtilities extends UnitTest {
    private static final int BOOL_INTNUM = 5;

    public TestIntegerUtilities(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestIntegerUtilities.class,
                IntegerUtilities.class);
    }

    public void testConstructor() {
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(IntegerUtilities.class));
    }

    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(IntegerUtilities.class));
    }

    public void testDecode() {
        final int test1 = 0xFFFFFF;
        final int test2 = 0xABCDEF;
        assertEquals(test1, IntegerUtilities.decode("#FFFFFF", ZERO));
        assertEquals(ZERO, IntegerUtilities.decode("#ABCDEFGH", ZERO));
        assertEquals(test2, IntegerUtilities.decode("#ABCDEF", ZERO));
    }

    public void testParseBoolean() {
        assertTrue(IntegerUtilities.parseBoolean(ONE));
        assertTrue(IntegerUtilities.parseBoolean(BOOL_INTNUM));
        assertFalse(IntegerUtilities.parseBoolean(ZERO));
    }

    public void testToInteger() {
        assertEquals(new Integer(ZERO), IntegerUtilities.toInteger(false));
        assertEquals(new Integer(ONE), IntegerUtilities.toInteger(true));
    }

    public void testBooleanValue() {
        assertEquals(ONE, IntegerUtilities.toInt(true));
        assertEquals(ZERO, IntegerUtilities.toInt(false));
    }

    public void testParseBooleanWithInteger() {
        assertTrue(IntegerUtilities.parseBoolean(new Integer(ONE)));
        assertTrue(IntegerUtilities.parseBoolean(new Integer(BOOL_INTNUM)));
        assertFalse(IntegerUtilities.parseBoolean(new Integer(ZERO)));
        assertFalse(IntegerUtilities.parseBoolean(null));
    }

    public void testOr() {
        final int test55 = 0x55;
        final int test5 = 0x5;
        final int test50 = 0x50;
        assertEquals(new Integer(test55), IntegerUtilities.or(new Integer(test5),
                new Integer(test50)));
        final int test03 = 0x03;
        final int test1 = 0x1;
        final int test2 = 0x2;
        assertEquals(new Integer(test03), IntegerUtilities.or(new Integer(test1),
                new Integer(test2)));
        final int test01 = 0x01;
        assertEquals(new Integer(test01), IntegerUtilities.or(new Integer(test1),
                new Integer(test1)));
    }
}
