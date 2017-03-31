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

package com.mckesson.eig.utility.values;

import java.math.BigDecimal;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestMoney extends TestCase {
    private static final double AMOUNT = 0.0001;
    private static final double AMOUNT_VAL1 = 23.45;
    private static final double AMOUNT_VAL2 = 23.56;
    private static final double AMOUNT_VAL3 = 123.12;
    private static final double AMOUNT_VAL4 = 123.56;
    private static final double AMOUNT_VAL = 2500.56;

    private double _amountAsDouble;
    private BigDecimal _amountAsBigDecimal;
    private Money _money;

    private Money _first;
    private Money _second;
    private Money _third;

    public TestMoney(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestMoney.class, Money.class);
    }

    @Override
	protected void setUp() {
        _amountAsDouble = AMOUNT_VAL;
        _amountAsBigDecimal = new BigDecimal("2500.56");
        _money = new Money(_amountAsBigDecimal);

        _first = new Money(AMOUNT_VAL4);
        _second = new Money(AMOUNT_VAL4);
        _third = new Money(AMOUNT_VAL2);
    }

    public void testCreateWithNullDecimal() {
        assertNull(null, Money.create(null));
    }

    public void testCreate() {
        Money test = Money.create(new BigDecimal(AMOUNT_VAL4));
        assertEquals(_first, test);
    }

    public void testConstructionWithDouble() {
        _money = new Money(_amountAsDouble);
        assertEquals(_amountAsBigDecimal, _money.amountAsBigDecimal());
        assertEquals(_amountAsDouble, _money.amountAsDouble(), AMOUNT);
    }

    public void testConstructionWithBigDecimal() {
        assertEquals(_amountAsBigDecimal, _money.amountAsBigDecimal());
        assertEquals(_amountAsDouble, _money.amountAsDouble(), AMOUNT);
    }

    public void testEquals() {
        assertEquals(_first, _second);
        assertEquals(_second, _first);

        assertTrue(!_second.equals(_third));
        assertTrue(!_third.equals(_second));

        assertTrue(!_first.equals(_third));
        assertTrue(!_third.equals(_first));

        assertFalse(_first.equals(new BigDecimal(AMOUNT_VAL1)));

        assertFalse(_first.equals(new Money(AMOUNT_VAL3)));
        assertFalse(_first.equals((Object) new Money(AMOUNT_VAL3)));

    }

    public void testCompareTo() {
        assertEquals(0, _first.compareTo(_second));
        assertEquals(0, _second.compareTo(_first));

        assertEquals(1, _first.compareTo(_third));
        assertEquals(-1, _third.compareTo(_first));
    }

    public void testHashCode() {
        assertEquals(_first.hashCode(), _second.hashCode());
        assertTrue(_second.hashCode() != _third.hashCode());
        assertTrue(_first.hashCode() != _third.hashCode());
    }

    public void testToString() {
        assertEquals("$2,500.56", _money.toString());
    }
}
