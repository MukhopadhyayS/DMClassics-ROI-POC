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
package com.mckesson.eig.utility.values;

import junit.framework.TestCase;

public class TestMultiplePeriods extends TestCase {
    private static final int COUNT_CONST = 3;

    private Days _days;
    private Hours _hours;
    private Months _months;
    private Weeks _weeks;
    private Years _years;

    public TestMultiplePeriods(String name) {
        super(name);
    }

    protected void setUp() {
        _days = null;
        _hours = null;
        _months = null;
        _weeks = null;
        _years = null;
    }

    protected void tearDown() {
        _days = null;
        _hours = null;
        _months = null;
        _weeks = null;
        _years = null;
    }

    public void testEqualsOnDifferentPeriodsWithSameCount() {
        _days = new Days(COUNT_CONST);
        _hours = new Hours(COUNT_CONST);
        _months = new Months(COUNT_CONST);
        _weeks = new Weeks(COUNT_CONST);
        _years = new Years(COUNT_CONST);

        assertFalse(_days.equals(_hours));
        assertFalse(_days.equals(_months));
        assertFalse(_days.equals(_weeks));
        assertFalse(_days.equals(_years));

        assertFalse(_hours.equals(_months));
        assertFalse(_hours.equals(_weeks));
        assertFalse(_hours.equals(_years));

        assertFalse(_months.equals(_weeks));
        assertFalse(_months.equals(_years));

        assertFalse(_weeks.equals(_years));
    }
    public void testCompareToOnDifferentPeriodsWithSameCount() {
        _days = new Days(COUNT_CONST);
        _hours = new Hours(COUNT_CONST);
        _months = new Months(COUNT_CONST);
        _weeks = new Weeks(COUNT_CONST);
        _years = new Years(COUNT_CONST);

        assertEquals(1, _days.compareTo(_hours));
        assertEquals(-1, _days.compareTo(_months));
        assertEquals(-1, _days.compareTo(_weeks));
        assertEquals(-1, _days.compareTo(_years));

        assertEquals(-1, _hours.compareTo(_months));
        assertEquals(-1, _hours.compareTo(_weeks));
        assertEquals(-1, _hours.compareTo(_years));

        assertEquals(1, _months.compareTo(_weeks));
        assertEquals(-1, _months.compareTo(_years));

        assertEquals(-1, _weeks.compareTo(_years));
    }

    public void testHashCodeOnDifferentPeriodsWithSameCount() {
        _days = new Days(COUNT_CONST);
        _hours = new Hours(COUNT_CONST);
        _months = new Months(COUNT_CONST);
        _weeks = new Weeks(COUNT_CONST);
        _years = new Years(COUNT_CONST);

        assertTrue(_days.hashCode() != _hours.hashCode());
        assertTrue(_days.hashCode() != _months.hashCode());
        assertTrue(_days.hashCode() != _weeks.hashCode());
        assertTrue(_days.hashCode() != _years.hashCode());

        assertTrue(_hours.hashCode() != _months.hashCode());
        assertTrue(_hours.hashCode() != _weeks.hashCode());
        assertTrue(_hours.hashCode() != _years.hashCode());

        assertTrue(_months.hashCode() != _weeks.hashCode());
        assertTrue(_months.hashCode() != _years.hashCode());

        assertTrue(_weeks.hashCode() != _years.hashCode());
    }
}
