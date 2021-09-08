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

import com.mckesson.eig.utility.testing.UnitTest;

public class TestConversionUtilities extends UnitTest {
    private static final int POSITIVE_INT1 = 3;
    private static final int POSITIVE_INT2 = 128;
    private static final int POSITIVE_INT3 = 134;
    private static final int CONVERT_POSITIVE = -122;
    private static final int BOOLEAN_CONST1 = 5;
    private static final int CONVERT_INT1 = 55;
    private static final int POSITIVE_INT4 = 98;
    private static final int CONVERT_INT2 = 99;
    private static final int POSITIVE_INT5 = 999;
    private static final double CONVERT_BOOLEAN = 7.5;
    private static final double CONVERT_BOOLEAN1 = 9.9;
    private static final double DOUBLE_CONST = 99.9;

    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(ConversionUtilities.class));
        ReflectionUtilities.callPrivateConstructor(ConversionUtilities.class);
    }

    public void testCharValueToBoolean() {
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean('Y'));
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean('y'));
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean('T'));
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean('t'));
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean('1'));

        assertEquals(Boolean.FALSE, ConversionUtilities.toBoolean('F'));
        assertEquals(Boolean.FALSE, ConversionUtilities.toBoolean('f'));
        assertEquals(Boolean.FALSE, ConversionUtilities.toBoolean('N'));
        assertEquals(Boolean.FALSE, ConversionUtilities.toBoolean('n'));
        assertEquals(Boolean.FALSE, ConversionUtilities.toBoolean('0'));

        assertNull(ConversionUtilities.toBoolean('x'));
    }

    public void testCharacterToBoolean() {
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean(new Character(
                'Y')));
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean(new Character(
                'y')));
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean(new Character(
                'T')));
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean(new Character(
                't')));
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean(new Character(
                '1')));

        assertEquals(Boolean.FALSE, ConversionUtilities
                .toBoolean(new Character('F')));
        assertEquals(Boolean.FALSE, ConversionUtilities
                .toBoolean(new Character('f')));
        assertEquals(Boolean.FALSE, ConversionUtilities
                .toBoolean(new Character('N')));
        assertEquals(Boolean.FALSE, ConversionUtilities
                .toBoolean(new Character('n')));
        assertEquals(Boolean.FALSE, ConversionUtilities
                .toBoolean(new Character('0')));

        assertNull(ConversionUtilities.toBoolean(new Character('x')));
        assertNull(ConversionUtilities.toBoolean((Character) null));
    }

    public void testBooleanToBoolean() {
        assertTrue(ConversionUtilities.toBooleanValue(Boolean.TRUE));
        assertFalse(ConversionUtilities.toBooleanValue(Boolean.FALSE));
        assertTrue(ConversionUtilities.toBooleanValue((Boolean) null, true));
        assertFalse(ConversionUtilities.toBooleanValue((Boolean) null, false));
    }

    public void testCharacterToBooleanValue() {
        assertTrue(ConversionUtilities.toBooleanValue(new Character('Y')));
        assertTrue(ConversionUtilities.toBooleanValue(new Character('y')));
        assertTrue(ConversionUtilities.toBooleanValue(new Character('T')));
        assertTrue(ConversionUtilities.toBooleanValue(new Character('t')));
        assertTrue(ConversionUtilities.toBooleanValue(new Character('1')));

        assertFalse(ConversionUtilities.toBooleanValue(new Character('F')));
        assertFalse(ConversionUtilities.toBooleanValue(new Character('f')));
        assertFalse(ConversionUtilities.toBooleanValue(new Character('N')));
        assertFalse(ConversionUtilities.toBooleanValue(new Character('n')));
        assertFalse(ConversionUtilities.toBooleanValue(new Character('0')));

        assertFalse(ConversionUtilities.toBooleanValue(new Character('x')));
        assertFalse(ConversionUtilities.toBooleanValue((Character) null));
    }

    public void testIntToBooleanValue() {
        assertFalse(ConversionUtilities.toBooleanValue(0));
        assertTrue(ConversionUtilities.toBooleanValue(1));
        assertTrue(ConversionUtilities.toBooleanValue(CONVERT_INT2));
    }

    public void testIntegerToBooleanValue() {
        // assertFalse(ConversionUtilities.toBooleanValue(null));
        assertFalse(ConversionUtilities.toBooleanValue(new Integer(0)));
        assertTrue(ConversionUtilities.toBooleanValue(new Integer(1)));
        assertTrue(ConversionUtilities
                .toBooleanValue(new Integer(CONVERT_INT2)));
    }

    public void testStringToInteger() {
        assertEquals(new Integer(CONVERT_INT2), ConversionUtilities
                .toInteger("99"));
        assertEquals(null, ConversionUtilities.toInteger("5.7"));
        assertEquals(null, ConversionUtilities.toInteger((String) null));
    }

    public void testStringToIntegerWithDefault() {
        assertEquals(new Integer(CONVERT_INT2), ConversionUtilities
                .toInteger("99"));
        assertEquals(new Integer(CONVERT_INT1), ConversionUtilities.toInteger(
                "5.7", new Integer(CONVERT_INT1)));
        assertEquals(new Integer(CONVERT_INT1), ConversionUtilities.toInteger(
                (String) null, new Integer(CONVERT_INT1)));
    }

    public void testStringToIntValueWithDefault() {
        assertEquals(CONVERT_INT2, ConversionUtilities.toIntValue("99"),
                CONVERT_INT1);
        assertEquals(CONVERT_INT1, ConversionUtilities.toIntValue("5.7",
                CONVERT_INT1));
        assertEquals(CONVERT_INT1, ConversionUtilities.toIntValue("foo",
                CONVERT_INT1));
        assertEquals(CONVERT_INT1, ConversionUtilities.toIntValue(
                (String) null, CONVERT_INT1));
    }

    public void testStringToIntValue() {
        assertEquals(POSITIVE_INT4, ConversionUtilities.toIntValue("98"));
        assertEquals(0, ConversionUtilities.toIntValue("5.7"));
        assertEquals(0, ConversionUtilities.toIntValue("foo"));
        assertEquals(0, ConversionUtilities.toIntValue((String) null));
    }

    public void testStringToLong() {
        assertEquals(new Long(CONVERT_INT2), ConversionUtilities.toLong("99"));
        assertEquals(null, ConversionUtilities.toLong("5.7"));
        assertEquals(null, ConversionUtilities.toLong((String) null));
    }

    public void testStringToLongWithDefault() {
        assertEquals(new Long(CONVERT_INT2), ConversionUtilities.toLong("99"));
        assertEquals(new Long(CONVERT_INT1), ConversionUtilities.toLong("5.7",
                new Long(CONVERT_INT1)));
        assertEquals(new Long(CONVERT_INT1), ConversionUtilities.toLong(
                (String) null, new Long(CONVERT_INT1)));
    }

    public void testShortToShort() {
        assertEquals(0, ConversionUtilities.toShort((Short) null));
        assertEquals(CONVERT_INT1, ConversionUtilities.toShort(new Short(
                (short) CONVERT_INT1)));
    }

    public void testShortToShortWithDefault() {
        assertEquals(CONVERT_INT2, ConversionUtilities.toShort((Short) null),
                CONVERT_INT2);
        assertEquals(CONVERT_INT1, ConversionUtilities.toShort(new Short(
                (short) CONVERT_INT1)));
    }

    public void testBooleanToInteger() {
        assertEquals(new Integer(1), ConversionUtilities
                .toInteger(Boolean.TRUE));
        assertEquals(new Integer(0), ConversionUtilities
                .toInteger(Boolean.FALSE));
        assertEquals(null, ConversionUtilities.toInteger((Boolean) null));
    }

    public void testBooleanToIntegerWithDefault() {
        assertEquals(new Integer(1), ConversionUtilities.toInteger(
                Boolean.TRUE, new Integer(CONVERT_INT2)));
        assertEquals(new Integer(0), ConversionUtilities.toInteger(
                Boolean.FALSE, new Integer(CONVERT_INT2)));
        assertEquals(new Integer(CONVERT_INT2), ConversionUtilities.toInteger(
                (Boolean) null, new Integer(CONVERT_INT2)));
    }

    public void testIntToBoolean() {
        assertTrue(ConversionUtilities.toBooleanValue(1));
        assertTrue(ConversionUtilities.toBooleanValue(BOOLEAN_CONST1));
        assertFalse(ConversionUtilities.toBooleanValue(0));
    }

    public void testNumberToShort() {
        assertEquals(Short.MAX_VALUE, ConversionUtilities.toShort(new Short(
                Short.MAX_VALUE)));
        assertEquals(Short.MAX_VALUE, ConversionUtilities.toShort(new Long(
                Short.MAX_VALUE)));
        assertEquals(POSITIVE_INT5, ConversionUtilities.toShort(new Float(
                POSITIVE_INT5)));
        assertEquals(POSITIVE_INT5, ConversionUtilities.toShort(new Double(
                POSITIVE_INT5)));
        assertEquals(0, ConversionUtilities.toShort((Number) null));
    }

    public void testNumberToShortWithDefault() {
        assertEquals(Short.MAX_VALUE, ConversionUtilities.toShort(new Integer(
                Short.MAX_VALUE), (short) CONVERT_INT1));
        assertEquals(Short.MAX_VALUE, ConversionUtilities.toShort(new Long(
                Short.MAX_VALUE), (short) CONVERT_INT1));
        assertEquals(POSITIVE_INT5, ConversionUtilities.toShort(new Float(
                POSITIVE_INT5), (short) CONVERT_INT1));
        assertEquals(POSITIVE_INT5, ConversionUtilities.toShort(new Double(
                POSITIVE_INT5), (short) CONVERT_INT1));
        assertEquals(CONVERT_INT1, ConversionUtilities.toShort((Number) null,
                (short) CONVERT_INT1));
    }

    public void testNumberToInt() {
        assertEquals(Integer.MAX_VALUE, ConversionUtilities.toInt(new Integer(
                Integer.MAX_VALUE)));
        assertEquals(Integer.MAX_VALUE, ConversionUtilities.toInt(new Long(
                Integer.MAX_VALUE)));
        assertEquals(POSITIVE_INT5, ConversionUtilities.toInt(new Float(
                POSITIVE_INT5)));
        assertEquals(POSITIVE_INT5, ConversionUtilities.toInt(new Double(
                POSITIVE_INT5)));
        assertEquals(0, ConversionUtilities.toInt((Number) null));
    }

    public void testNumberToIntWithDefault() {
        assertEquals(Integer.MAX_VALUE, ConversionUtilities.toInt(new Integer(
                Integer.MAX_VALUE), CONVERT_INT1));
        assertEquals(Integer.MAX_VALUE, ConversionUtilities.toInt(new Long(
                Integer.MAX_VALUE), CONVERT_INT1));
        assertEquals(POSITIVE_INT5, ConversionUtilities.toInt(new Float(
                POSITIVE_INT5), CONVERT_INT1));
        assertEquals(POSITIVE_INT5, ConversionUtilities.toInt(new Double(
                POSITIVE_INT5), CONVERT_INT1));
        assertEquals(CONVERT_INT1, ConversionUtilities.toInt((Number) null,
                CONVERT_INT1));
    }

    public void testBooleanToIntWithDefault() {
        assertEquals(1, ConversionUtilities.toInt(Boolean.TRUE));
        assertEquals(0, ConversionUtilities.toInt(Boolean.FALSE));
        assertEquals(0, ConversionUtilities.toInt((Boolean) null));
    }

    public void testBooleanToInt() {
        assertEquals(1, ConversionUtilities.toInt(Boolean.TRUE), CONVERT_INT1);
        assertEquals(0, ConversionUtilities.toInt(Boolean.FALSE), CONVERT_INT1);
        assertEquals(CONVERT_INT1, ConversionUtilities.toInt((Boolean) null,
                CONVERT_INT1));
    }

    public void testNumberToBooleanValue() {
        assertTrue(ConversionUtilities.toBooleanValue(new Integer(1)));
        assertTrue(ConversionUtilities.toBooleanValue(new Integer(
                BOOLEAN_CONST1)));
        assertTrue(ConversionUtilities.toBooleanValue(new Long(BOOLEAN_CONST1)));
        assertTrue(ConversionUtilities.toBooleanValue(new Double(
                CONVERT_BOOLEAN1)));
        assertTrue(ConversionUtilities
                .toBooleanValue(new Float(CONVERT_BOOLEAN)));
        assertFalse(ConversionUtilities.toBooleanValue(new Integer(0)));
        assertFalse(ConversionUtilities.toBooleanValue(new Long(0)));
        assertFalse(ConversionUtilities.toBooleanValue(new Double(0.0)));
        assertFalse(ConversionUtilities.toBooleanValue(new Float(0.0)));
        assertFalse(ConversionUtilities.toBooleanValue((Number) null));
    }

    public void testCharacterToChar() {
        assertEquals('C', ConversionUtilities.toChar(new Character('C'), 'd'));
        assertEquals('d', ConversionUtilities.toChar((Character) null, 'd'));
        assertEquals('&', ConversionUtilities.toChar(new Character('&'), 'd'));
    }

    public void testStringToFloat() {
        assertEquals(new Float(DOUBLE_CONST), ConversionUtilities
                .toFloat("99.9"));
        assertEquals(null, ConversionUtilities.toFloat("a99"));
        assertEquals(null, ConversionUtilities.toFloat(null));
    }

    public void testStringToFloatWithDefault() {
        assertEquals(new Float(DOUBLE_CONST), ConversionUtilities.toFloat(
                "99a", new Float(DOUBLE_CONST)));
    }

    public void testStringToDouble() {
        assertEquals(new Double(DOUBLE_CONST), ConversionUtilities
                .toDouble("99.9"));
        assertEquals(new Double(Double.MAX_VALUE), ConversionUtilities
                .toDouble("99a", new Double(Double.MAX_VALUE)));
        assertEquals(null, ConversionUtilities.toFloat("a99"));
        assertEquals(null, ConversionUtilities.toFloat(null));
    }

    public void testStringToDoubleWithDefault() {
        assertEquals(new Double(Double.MAX_VALUE), ConversionUtilities
                .toDouble("99a", new Double(Double.MAX_VALUE)));
        assertEquals(new Double(DOUBLE_CONST), ConversionUtilities.toDouble(
                "99a", new Double(DOUBLE_CONST)));
    }

    public void testByteToPositiveInt() {
        assertEquals(POSITIVE_INT1, ConversionUtilities
                .toPositiveInt((byte) POSITIVE_INT1));
        assertEquals(POSITIVE_INT2, ConversionUtilities
                .toPositiveInt((byte) POSITIVE_INT2));
        assertEquals(POSITIVE_INT3, ConversionUtilities
                .toPositiveInt((byte) CONVERT_POSITIVE));
    }

    public void testToHexByte() {
        assertEquals("3", ConversionUtilities.toHexByte((byte) POSITIVE_INT1));
        assertEquals("80", ConversionUtilities.toHexByte((byte) POSITIVE_INT2));
        assertEquals("86", ConversionUtilities
                .toHexByte((byte) CONVERT_POSITIVE));
    }

    public void testToZeroPaddedHexByte() {
        assertEquals("03", ConversionUtilities
                .toZeroPaddedHexByte((byte) POSITIVE_INT1));
        assertEquals("80", ConversionUtilities
                .toZeroPaddedHexByte((byte) POSITIVE_INT2));
        assertEquals("86", ConversionUtilities
                .toZeroPaddedHexByte((byte) CONVERT_POSITIVE));
    }

    public void testBooleanToCharacterUpperYN() {
        assertEquals(ConversionUtilities.UPPER_Y, ConversionUtilities
                .toCharacterUpperYN(Boolean.TRUE));
        assertEquals(ConversionUtilities.UPPER_N, ConversionUtilities
                .toCharacterUpperYN(Boolean.FALSE));
        assertNull(ConversionUtilities.toCharacterUpperYN(null));
    }

    public void testBooleanToCharacterUpperTF() {
        assertEquals(ConversionUtilities.UPPER_T, ConversionUtilities
                .toCharacterUpperTF(Boolean.TRUE));
        assertEquals(ConversionUtilities.UPPER_F, ConversionUtilities
                .toCharacterUpperTF(Boolean.FALSE));
        assertNull(ConversionUtilities.toCharacterUpperTF(null));
    }

    public void testBooleanToCharacterLowerYN() {
        assertEquals(ConversionUtilities.LOWER_Y, ConversionUtilities
                .toCharacterLowerYN(Boolean.TRUE));
        assertEquals(ConversionUtilities.LOWER_N, ConversionUtilities
                .toCharacterLowerYN(Boolean.FALSE));
        assertNull(ConversionUtilities.toCharacterLowerYN(null));
    }

    public void testBooleanToCharacterLowerTF() {
        assertEquals(ConversionUtilities.LOWER_T, ConversionUtilities
                .toCharacterLowerTF(Boolean.TRUE));
        assertEquals(ConversionUtilities.LOWER_F, ConversionUtilities
                .toCharacterLowerTF(Boolean.FALSE));
        assertNull(ConversionUtilities.toCharacterLowerTF(null));
    }

    public void testBooleanValueToCharYN() {
        assertEquals('Y', ConversionUtilities.toCharUpperYN(true));
        assertEquals('N', ConversionUtilities.toCharUpperYN(false));
    }

    public void testBooleanValueToBoolean() {
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean(true));
        assertEquals(Boolean.FALSE, ConversionUtilities.toBoolean(false));
    }

    public void testStringToBoolean() {
        verifyToBooleanValue("true", false, true);
        verifyToBooleanValue("yes", false, true);
        verifyToBooleanValue("on", false, true);
        verifyToBooleanValue("enabled", false, true);
        verifyToBooleanValue("enable", false, true);
        verifyToBooleanValue("t", false, true);
        verifyToBooleanValue("y", false, true);
        verifyToBooleanValue("Y", false, true);
        verifyToBooleanValue("1", false, true);

        verifyToBooleanValue("false", true, false);
        verifyToBooleanValue("no", true, false);
        verifyToBooleanValue("off", true, false);
        verifyToBooleanValue("disabled", true, false);
        verifyToBooleanValue("disable", true, false);
        verifyToBooleanValue("f", true, false);
        verifyToBooleanValue("n", true, false);
        verifyToBooleanValue("N", true, false);
        verifyToBooleanValue("0", true, false);

        verifyToBooleanValue("test", true, true);
        verifyToBooleanValue("test", false, false);
        verifyToBooleanValue((String) null, true, true);
        verifyToBooleanValue((String) null, false, false);

        assertNull(ConversionUtilities.toBoolean((String) null));
        assertNull(ConversionUtilities.toBoolean("foo"));
        assertEquals(Boolean.TRUE, ConversionUtilities.toBoolean("T"));
        assertEquals(Boolean.FALSE, ConversionUtilities.toBoolean("F"));
    }

    private void verifyToBooleanValue(String value, boolean defaultValue,
            boolean expected) {
        assertEquals(expected, ConversionUtilities.toBooleanValue(value,
                defaultValue));
    }

    public void testToYesNoFlag() {
        assertNull(ConversionUtilities.toYesNoFlag(null));
        assertEquals("Y", ConversionUtilities.toYesNoFlag(Boolean.TRUE));
        assertEquals("N", ConversionUtilities.toYesNoFlag(Boolean.FALSE));
    }

    public void testGetStringFromBoolean() {
        assertEquals("true", ConversionUtilities.toString(true));
        assertEquals("false", ConversionUtilities.toString(false));
    }

    public void testGetYesNoFlagFromBoolean() {
        assertEquals("Y", ConversionUtilities.toYesNoFlag(true));
        assertEquals("N", ConversionUtilities.toYesNoFlag(false));
    }
}
