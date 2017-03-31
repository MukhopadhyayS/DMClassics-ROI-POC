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

public class TestNumberUtilities extends TestCase {
    private static final int CMP_FIRSTNUM = 4;
    private static final int CMP_SECONDNUM = 5;
    private static final int CMP_THIRDNUM = 8;
    private static final int CMP_FOURTHNUM = 10;
    private static final int VERIFY_INT1 = 100;
    private static final int VERIFY_INT2 = -100;
    private static final float VERIFY_FLAOT1 = 100.0f;
    private static final float VERIFY_FLOAT2 = 100.5f;
    private static final float VERIFY_FLAOT3 = -100.0f;
    private static final float VERIFY_FLOAT4 = -100.5f;
    private static final double VERIFY_DOUBLE1 = 100.0;
    private static final double VERIFY_DOUBLE2 = 100.5;
    private static final double VERIFY_DOUBLE3 = -100.0;
    private static final double VERIFY_DOUBLE4 = -100.5;

    private static final int INT_VALUE = 8000;
    private static final long LONG_VALUE = 9000;
    private static final double DOUBLE_VALUE = 800.567;
    private static final float FLOAT_VALUE = 500.871f;

    public TestNumberUtilities(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestNumberUtilities.class,
                NumberUtilities.class);
    }

    public void testConstructor() {
        Object o = ReflectionUtilities
                .callPrivateConstructor(NumberUtilities.class);
        assertNotNull("o should not be null", o);
    }

    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(NumberUtilities.class));
    }

    /**
     * Test NumberUtilities.parseInt()
     * 
     * Tests:
     * 
     * 1) Test that positive number comes back correctly 2) Test that negative
     * number comes back correctly
     */
    public void testParseIntValidValue() {
        verifyInt(VERIFY_INT1, "100", INT_VALUE);
        verifyInt(VERIFY_INT2, "-100", INT_VALUE);
    }

    /**
     * Test NumberUtilities.parseInt()
     * 
     * Test:
     * 
     * 1) Test that empty string returns the default value
     */
    public void testParseIntEmptyString() {
        verifyInt(INT_VALUE, "", INT_VALUE);
    }

    /**
     * Test NumberUtilities.parseInt()
     * 
     * Test:
     * 
     * 1) Test that null value returns the default value
     */
    public void testParseIntNullValue() {
        verifyInt(INT_VALUE, null, INT_VALUE);
    }

    /**
     * Test NumberUtilities.parseInt()
     * 
     * Test:
     * 
     * 1) Test that a double value returns the default value
     */
    public void testParseIntDoubleValue() {
        verifyInt(INT_VALUE, "1.5", INT_VALUE);
    }

    /**
     * Test NumberUtilities.parseInt()
     * 
     * Test:
     * 
     * 1) Test that invalid string returns the default value
     */
    public void testParseIntString() {
        verifyInt(INT_VALUE, "XX", INT_VALUE);
    }

    /**
     * Test NumberUtilities.parseLong()
     * 
     * Tests:
     * 
     * 1) Test that positive number comes back correctly 2) Test that negative
     * number comes back correctly
     */
    public void testParseLongValidValue() {
        verifyLong(VERIFY_INT1, "100", LONG_VALUE);
        verifyLong(VERIFY_INT2, "-100", LONG_VALUE);
    }

    /**
     * Test NumberUtilities.parseLong()
     * 
     * Test:
     * 
     * 1) Test that empty string returns the default value
     */
    public void testParseLongEmptyString() {
        verifyLong(LONG_VALUE, "", LONG_VALUE);
    }

    /**
     * Test NumberUtilities.parseLong()
     * 
     * Test:
     * 
     * 1) Test that null value returns the default value
     */
    public void testParseLongNullValue() {
        verifyLong(LONG_VALUE, null, LONG_VALUE);
    }

    /**
     * Test NumberUtilities.parseLong()
     * 
     * Test:
     * 
     * 1) Test that a double value returns the default value
     */
    public void testParseLongDoubleValue() {
        verifyLong(LONG_VALUE, "1.5", LONG_VALUE);
    }

    /**
     * Test NumberUtilities.parseLong()
     * 
     * Test:
     * 
     * 1) Test that invalid string returns the default value
     */
    public void testParseLongString() {
        verifyLong(LONG_VALUE, "XX", LONG_VALUE);
    }

    /**
     * Test NumberUtilities.parseFloat()
     * 
     * Test:
     * 
     * 1) Test that invalid string returns the default value
     */
    public void testParseFloatString() {
        verifyFloat(FLOAT_VALUE, "XX", FLOAT_VALUE);
    }

    /**
     * Test NumberUtilities.parseFloat()
     * 
     * Test:
     * 
     * 1) Test that null returns the default value
     */
    public void testParseFloatNull() {
        verifyFloat(FLOAT_VALUE, null, FLOAT_VALUE);
    }

    /**
     * Test NumberUtilities.parseFloat()
     * 
     * Test:
     * 
     * 1) Test that a double returns a valid double
     */
    public void testParseFloatValid() {
        verifyFloat(VERIFY_FLOAT4, "-100.5", FLOAT_VALUE);
        verifyFloat(VERIFY_FLOAT2, "100.5", FLOAT_VALUE);
        verifyFloat(VERIFY_FLAOT3, "-100", FLOAT_VALUE);
        verifyFloat(VERIFY_FLAOT1, "100", FLOAT_VALUE);
    }

    /**
     * Test NumberUtilities.parseDouble()
     * 
     * Test:
     * 
     * 1) Test that invalid string returns the default value
     */
    public void testParseDoubleString() {
        verifyDouble(DOUBLE_VALUE, "XX", DOUBLE_VALUE);
    }

    /**
     * Test NumberUtilities.parseDouble()
     * 
     * Test:
     * 
     * 1) Test that null returns the default value
     */
    public void testParseDoubleNull() {
        verifyDouble(DOUBLE_VALUE, null, DOUBLE_VALUE);
    }

    /**
     * Test NumberUtilities.parseDouble()
     * 
     * Test:
     * 
     * 1) Test that a double returns a valid double
     */
    public void testParseDoubleValid() {
        verifyDouble(VERIFY_DOUBLE4, "-100.5", DOUBLE_VALUE);
        verifyDouble(VERIFY_DOUBLE2, "100.5", DOUBLE_VALUE);
        verifyDouble(VERIFY_DOUBLE3, "-100", DOUBLE_VALUE);
        verifyDouble(VERIFY_DOUBLE1, "100", DOUBLE_VALUE);
    }

    public void verifyDouble(double exp, String s, double def) {
        assertEquals(exp, NumberUtilities.parse(s, def), 0.0);
    }

    public void verifyFloat(float exp, String s, float def) {
        assertEquals(exp, NumberUtilities.parse(s, def), 0.0);
    }

    public void verifyLong(long exp, String s, long def) {
        assertEquals(exp, NumberUtilities.parse(s, def));
    }

    public void verifyInt(int exp, String s, int def) {
        assertEquals(exp, NumberUtilities.parse(s, def));
    }

    public void testCompareInts() {
        assertEquals(1, NumberUtilities.compare(CMP_THIRDNUM, CMP_SECONDNUM));
        assertEquals(0, NumberUtilities.compare(2, 2));
        assertEquals(-1, NumberUtilities.compare(CMP_FIRSTNUM, CMP_FOURTHNUM));
    }

    public void testValueOf() {
        assertEquals(-1, NumberUtilities.valueOf((Integer) null));
        assertEquals(1, NumberUtilities.valueOf(new Integer(1)));
    }

    public void testCompareShorts() {
        assertEquals(1, NumberUtilities.compare((short) CMP_THIRDNUM,
                (short) CMP_SECONDNUM));
        assertEquals(0, NumberUtilities.compare((short) 2, (short) 2));
        assertEquals(-1, NumberUtilities.compare((short) CMP_FIRSTNUM,
                (short) CMP_FOURTHNUM));
    }

    public void testShortValueOf() {
        assertEquals(-1, NumberUtilities.valueOf((Short) null));
        assertEquals(1, NumberUtilities.valueOf(new Short((short) 1)));
    }

    public void testisInRange() {
        assertFalse(NumberUtilities.isInRange(0, 1, 2));
        assertFalse(NumberUtilities.isInRange(2, 0, 1));
        assertTrue(NumberUtilities.isInRange(1, 0, 2));
    }
}
