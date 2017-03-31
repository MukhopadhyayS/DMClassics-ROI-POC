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

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Based on Martin Fowler's Money class:
 * http://www.martinfowler.com/isa/money.html
 * <p>
 * Needs a supporting Currency class when internationalization is desired.
 * java.util.Currency is available in JDK 1.4+. Assumes USD for now.
 */
public class Money implements Comparable<Money>, Serializable {

	private static final long serialVersionUID = -5854984962383241489L;

	public static final int CODE_CONST = 32;

    private static final int[] CENTS = new int[]{1, 10, 100, 1000};

    private final long _amount;

    /**
     * The centFactor() method loses the fractional cents to prevent rounding
     * problems. All constructors use this approach.
     */
    public Money(double amount) {
        _amount = Math.round(amount * centFactor());
    }

    public Money(BigDecimal amount) {
        this(amount.doubleValue());
    }

    public static Money create(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        return new Money(amount);
    }

    public BigDecimal amountAsBigDecimal() {
        return BigDecimal.valueOf(_amount, getDefaultFractionDigits());
    }

    public double amountAsDouble() {
        return amountAsBigDecimal().doubleValue();
    }

    @Override
	public boolean equals(Object other) {
        return (other instanceof Money) && equals((Money) other);
    }

    public boolean equals(Money other) {
        // need to also compare currency when internationalized
        return _amount == other._amount;
    }

    public int compareTo(Money other) {
        // Need to also consider currency when internationalized.
        // Maybe "normalize" by converting both currencies to the same type,
        // taking into consideration exchange rates.
        return (_amount < other._amount ? -1 : (_amount == other._amount
                ? 0
                : 1));
    }

    @Override
	public int hashCode() {
        return (int) (_amount ^ (_amount >>> CODE_CONST));
    }


    @Override
	public String toString() {
        return getFormat().format(amountAsDouble());
    }

    private NumberFormat getFormat() {
        return NumberFormat.getCurrencyInstance();
    }

    private int getDefaultFractionDigits() {
        // normally delegate to _currency.getDefaultFractionDigits()
        // assume USD for now
        return 2;
    }

    /**
     * For USD this will always be 10, but some currencies have different
     * fractional parts.
     */
     private int centFactor() {
        return CENTS[getDefaultFractionDigits()];
    }
}
