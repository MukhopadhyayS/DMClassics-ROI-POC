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

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.Assert;

import com.mckesson.eig.utility.util.BeanUtilities;
import com.mckesson.eig.utility.values.DateTime;
import com.mckesson.eig.utility.values.Money;

public final class AssertHelper {

    private static final DateTime DATETIME_1 = new DateTime("06/24/1970",
            "MM/dd/yyyy");
    private static final DateTime DATETIME_2 = new DateTime("06/24/1970",
            "MM/dd/yyyy");

    private static final Date DATE1 = new Date(DATETIME_1.toMilliseconds());
    private static final Date DATE2 = new Date(DATETIME_2.toMilliseconds());

    private static final Money MONEY_1 = new Money(12.45d);
    private static final Money MONEY_2 = new Money(12.45d);

    private AssertHelper() {
    }

    public static void assertStringWithInitialNull(Object bean, String property) {
        GenericTestHelper.assertString(bean, property);
    }

    public static void assertString(Object bean, String property, String initial) {
        GenericTestHelper.assertString(bean, property, initial);
    }

    public static void assertString(Object bean, String property) {
        GenericTestHelper.assertString(bean, property);
    }

    public static void assertCharacterWithInitialNull(Object bean,
            String property) {
        GenericTestHelper.assertCharacter(bean, property);
    }

    public static void assertCharacter(Object bean, String property,
            Character initial) {
        GenericTestHelper.assertCharacter(bean, property, initial);
    }

    public static void assertCharacter(Object bean, String property) {
        GenericTestHelper.assertCharacter(bean, property);
    }

    public static void assertCharacter(Object bean, String property,
            Character in, Character out) {
        GenericTestHelper.assertCharacter(bean, property, in, out);
    }

    public static void assertBooleanWithInitialNull(Object bean, String property) {
        GenericTestHelper.assertBooleanWithInitialNull(bean, property);
    }

    public static void assertBooleanWithInitialTrue(Object bean, String property) {
        GenericTestHelper.assertBooleanWithInitialTrue(bean, property);
    }

    public static void assertBooleanWithInitialFalse(Object bean,
            String property) {
        GenericTestHelper.assertBooleanWithInitialFalse(bean, property);
    }

    public static void assertBoolean(Object bean, String property, Boolean in,
            Boolean out) {
        GenericTestHelper.assertBoolean(bean, property, in, out);
    }

    public static void assertIsBooleanWithCharacterSetter(Object bean,
            String isProperty, String setProperty) {
        GenericTestHelper.assertIsBooleanWithCharacterSetter(bean, isProperty,
                setProperty);
    }

    public static void assertDateTimeWithInitialNull(Object bean,
            String property) {
        assertDateTime(bean, property, null);
    }

    public static void assertDateTime(Object bean, String property,
            DateTime initial) {
        assertEquals(bean, property, initial);
        assertDateTime(bean, property, DATETIME_1, DATETIME_2);
    }

    public static void assertDateWithInitialNull(Object bean, String property) {
        assertDate(bean, property, null);
    }

    public static void assertDate(Object bean, String property, Date initial) {
        assertEquals(bean, property, initial);
        assertDate(bean, property, DATE1, DATE2);
    }

    public static void assertDate(Object bean, String property, Date in,
            Date out) {
        BeanUtilities.setProperty(bean, property, in);
        assertEquals(bean, property, out);
    }

    public static void assertDateTime(Object bean, String property,
            DateTime in, DateTime out) {
        BeanUtilities.setProperty(bean, property, in);
        assertEquals(bean, property, out);
    }

    public static void assertMoneyWithInitialNull(Object bean, String property) {
        assertMoney(bean, property, null);
    }

    public static void assertMoney(Object bean, String property, Money initial) {
        assertEquals(bean, property, initial);
        assertMoney(bean, property, MONEY_1, MONEY_2);
    }

    public static void assertMoney(Object bean, String property, Money in,
            Money out) {
        BeanUtilities.setProperty(bean, property, in);
        assertEquals(bean, property, out);
    }

    public static void assertIntegerWithInitialNull(Object bean, String property) {
        GenericTestHelper.assertIntegerWithInitialNull(bean, property);
    }

    public static void assertIntegerWithInitialZero(Object bean, String property) {
        GenericTestHelper.assertIntegerWithInitialZero(bean, property);
    }

    public static void assertIntegerWithInitialValue(Object bean,
            String property, Object init) {
        GenericTestHelper.assertIntegerWithInitialValue(bean, property, init);
    }

    public static void assertIntegerWithInitialValue(Object bean,
            String property, Object init, Object test) {
        GenericTestHelper.assertObjectWithInitialValue(bean, property, init,
                test);
    }

    public static void assertLongWithInitialNull(Object bean, String property) {
        GenericTestHelper.assertLongWithInitialNull(bean, property);
    }

    public static void assertLongWithInitialZero(Object bean, String property) {
        GenericTestHelper.assertLongWithInitialZero(bean, property);
    }

    public static void assertLongWithInitialValue(Object bean, String property,
            Object init) {
        GenericTestHelper.assertLongWithInitialValue(bean, property, init);
    }

    public static void assertObjectWithInitialNull(Object bean,
            String property, Object o) {
        GenericTestHelper.assertObjectWithInitialNull(bean, property, o);
    }

    public static void assertObject(Object bean, String property, Object o) {
        GenericTestHelper.assertObject(bean, property, o);
    }

    public static void assertIntValue(Object bean, String property) {
        GenericTestHelper.assertIntValue(bean, property);
    }

    public static void assertIntValueWithInitialZero(Object bean,
            String property) {
        GenericTestHelper.assertIntValueWithInitialZero(bean, property);
    }

    public static void assertShortValue(Object bean, String property) {
        GenericTestHelper.assertShortValue(bean, property);
    }

    public static void assertShortValueWithInitialZero(Object bean,
            String property) {
        GenericTestHelper.assertShortValueWithInitialZero(bean, property);
    }

    public static void assertBigDecimalWithInitialNull(Object bean,
            String property) {
        GenericTestHelper.assertBigDecimalWithInitialNull(bean, property);
    }

    public static void assertBigDecimal(Object bean, String property,
            BigDecimal initial) {
        GenericTestHelper.assertBigDecimal(bean, property, initial);
    }

    public static void assertBigDecimal(Object bean, String property,
            BigDecimal in, BigDecimal out) {
        BeanUtilities.setProperty(bean, property, in);
        assertEquals(bean, property, out);
    }

    public static void assertEquals(Object bean, String property,
            BigDecimal expected) {
        // force cast
        BigDecimal value = (BigDecimal) getProperty(bean, property);
        Assert.assertEquals(expected, value);
    }

    public static void assertEquals(Object bean, String property,
            Object expected) {
        Assert.assertEquals(expected, getProperty(bean, property));
    }

    private static void assertEquals(Object bean, String property,
            Money expected) {
        // force cast
        Money value = (Money) getProperty(bean, property);
        Assert.assertEquals(expected, value);
    }

    private static void assertEquals(Object bean, String property,
            DateTime expected) {
        // force cast
        DateTime value = (DateTime) getProperty(bean, property);
        Assert.assertEquals(expected, value);
    }

    private static Object getProperty(Object bean, String property) {
        return BeanUtilities.getProperty(bean, property);
    }
}
