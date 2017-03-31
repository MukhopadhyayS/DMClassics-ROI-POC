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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import com.mckesson.eig.utility.util.BeanUtilities;

public final class GenericTestHelper {
    private static final int LIST_COUNT = 3;
    private static final int OBJ_LONG = 5;
    private static final int OBJ_LONG1 = 99999999;

    private static final String FOO = "foo";
    private static final Character Z = new Character('Z');
    private static final BigDecimal BIG_DECIMAL_1 = new BigDecimal("1.23");
    private static final BigDecimal BIG_DECIMAL_2 = new BigDecimal("1.23");
    private static final int INT_VALUE = 99999;
    private static final short SHORT_VALUE = 99;

    private GenericTestHelper() {
    }

    public static void assertStringWithInitialNull(Object bean, String property) {
        assertString(bean, property, null);
    }

    public static void assertStringWithInitialEmpty(Object bean, String property) {
        assertString(bean, property, "");
    }

    public static void assertString(Object bean, String property, String initial) {
        Assert.assertEquals(initial, (String) BeanUtilities.getProperty(bean, property));
        assertString(bean, property);
    }

    public static void assertString(Object bean, String property) {
        BeanUtilities.setProperty(bean, property, FOO);
        Assert.assertEquals(FOO, (String) BeanUtilities.getProperty(bean, property));
    }

    public static void assertCharacterWithInitialNull(Object bean, String property) {
        assertCharacter(bean, property, null);
    }

    public static void assertCharacter(Object bean, String property, Character initial) {
        assertEquals(bean, property, initial);
        assertCharacter(bean, property);
    }

    public static void assertCharacter(Object bean, String property) {
        assertCharacter(bean, property, Z, Z);
    }

    public static void assertCharacter(Object bean, String property, Character in, Character out) {
        BeanUtilities.setProperty(bean, property, in);
        assertEquals(bean, property, out);
    }

    public static void assertBooleanWithInitialNull(Object bean, String property) {
        assertNull(bean, property);
        assertBoolean(bean, property, Boolean.TRUE, Boolean.TRUE);
    }

    public static void assertBooleanWithInitialTrue(Object bean, String property) {
        assertEquals(bean, property, Boolean.TRUE);
        assertBoolean(bean, property, Boolean.FALSE, Boolean.FALSE);
    }

    public static void assertBooleanWithInitialFalse(Object bean,
            String property) {
        assertEquals(bean, property, Boolean.FALSE);
        assertBoolean(bean, property, Boolean.TRUE, Boolean.TRUE);
    }

    public static void assertBoolean(Object bean, String property, Boolean in, Boolean out) {
        BeanUtilities.setProperty(bean, property, in);
        assertEquals(bean, property, out);
    }

    public static void assertIsBooleanWithCharacterSetter(Object bean,
            String isProperty, String setProperty) {
        assertEquals(bean, isProperty, Boolean.FALSE);
        BeanUtilities.setProperty(bean, setProperty, new Character('Y'));
        assertEquals(bean, isProperty, Boolean.TRUE);
        BeanUtilities.setProperty(bean, setProperty, new Character('N'));
        assertEquals(bean, isProperty, Boolean.FALSE);
    }

    public static void assertDate(Object bean, String property, Date in, Date out) {
        BeanUtilities.setProperty(bean, property, in);
        assertEquals(bean, property, out);
    }

    public static void assertIntegerWithInitialNull(Object bean, String property) {
        assertIntegerWithInitialValue(bean, property, null);
    }

    public static void assertIntegerWithInitialZero(Object bean, String property) {
        assertObjectWithInitialValue(bean, property, new Integer(0),
                new Integer(Integer.MAX_VALUE));
    }

    public static void assertIntegerWithInitialValue(Object bean, String property, Object init) {
        assertObjectWithInitialValue(bean, property, init, new Integer(Integer.MAX_VALUE));
    }

    public static void assertObjectWithInitialValue(Object bean,
            String property, Object init, Object test) {
        assertEquals(bean, property, init);
        assertObject(bean, property, test);
    }

    public static void assertLongWithInitialNull(Object bean, String property) {
        assertLongWithInitialValue(bean, property, null);
    }

    public static void assertLongWithInitialZero(Object bean, String property) {
        assertObjectWithInitialValue(bean, property, new Long(0), new Long(OBJ_LONG));
    }

    public static void assertLongWithInitialValue(Object bean, String property, Object init) {
        assertObjectWithInitialValue(bean, property, init, new Long(OBJ_LONG1));
    }

    public static void assertArrayListWithInitialEmptyList(Object bean, String property) {
        assertEquals(bean, property, new ArrayList<Integer>());
        assertObject(bean, property, new ArrayList<Integer>(LIST_COUNT));
    }

    public static void assertObjectWithInitialNull(Object bean, String property, Object o) {
        assertNull(bean, property);
        assertObject(bean, property, o);
    }

    public static void assertObject(Object bean, String property, Object o) {
        BeanUtilities.setProperty(bean, property, o);
        assertEquals(bean, property, o);
    }

    // public static void assertIntValue(Object bean, String property, int
    // value) {
    // assertObject(bean, property, new Integer(value));
    // }

    public static void assertIntValue(Object bean, String property) {
        assertObject(bean, property, new Integer(INT_VALUE));
    }

    public static void assertIntValueWithInitialZero(Object bean, String property) {
        assertEquals(bean, property, new Integer(0));
        assertIntValue(bean, property);
    }

    public static void assertShortValue(Object bean, String property) {
        assertObject(bean, property, new Short(SHORT_VALUE));
    }

    public static void assertShortValueWithInitialZero(Object bean, String property) {
        assertEquals(bean, property, new Short((short) 0));
        assertShortValue(bean, property);
    }

    public static void assertBigDecimalWithInitialNull(Object bean, String property) {
        assertBigDecimal(bean, property, null);
    }

    public static void assertBigDecimal(Object bean, String property, BigDecimal initial) {
        assertEquals(bean, property, initial);
        assertBigDecimal(bean, property, BIG_DECIMAL_1, BIG_DECIMAL_2);
    }

    public static void assertBigDecimal(Object bean, String property,
            BigDecimal in, BigDecimal out) {
        BeanUtilities.setProperty(bean, property, in);
        assertEquals(bean, property, out);
    }

    public static void assertEquals(Object bean, String property, BigDecimal expected) {
        // force cast
        BigDecimal value = (BigDecimal) getProperty(bean, property);
        Assert.assertEquals(expected, value);
    }

    public static void assertEquals(Object bean, String property, Object expected) {
        Assert.assertEquals(expected, getProperty(bean, property));
    }

    private static void assertEquals(Object bean, String property, Boolean expected) {
        // force cast
        Boolean value = (Boolean) getProperty(bean, property);
        Assert.assertEquals(expected, value);
    }

    private static void assertEquals(Object bean, String property, Character expected) {
        // force cast
        Character value = (Character) getProperty(bean, property);
        Assert.assertEquals(expected, value);
    }

    private static void assertNull(Object bean, String property) {
        Assert.assertNull(getProperty(bean, property));
    }

    private static Object getProperty(Object bean, String property) {
        return BeanUtilities.getProperty(bean, property);
    }

    public static void assertListSizeAndValue(List< ? > list, int size, List< ? > testList) {
        Assert.assertNotNull(list);
        Assert.assertEquals(size, list.size());
        Assert.assertEquals(testList, list);
    }
}
