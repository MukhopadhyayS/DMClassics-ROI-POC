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

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

/**
 *Test case to test the class Years.
 */
public class TestYears extends TestCase {

    /**
     * Private variable of type Period.
     * Represents time period.
     */
    private Period _period;
    /**
     * Private variable of type Period.
     * Represents time period.
     */
    private Period _other;
    /**
     * Private variable of type int.
     */
    public static final int COUNT_YEARS6 = 6;
    /**
     * Private variable of type int.
     */
    public static final int COUNT_YEARS = 10;
    /**
     * Private variable of type int.
     */
    public static final int COUNT_MILLISECS = 600000;
    /**
     * Private variable of type long.
     */
    public static final long COUNT_YEARSL = 2000L;
    /**
     * Private variable of type Number.
     */
    public static final Number COUNT_YEARSN = new Integer(6);
    /**
     * Constructs the class.
     * @param name
     * name of the test case.
     */
    public TestYears(String name) {
        super(name);
    }

    /**
     * @return
     * CoverageSuite
     */
    public static Test suite() {
        return new CoverageSuite(TestYears.class, Years.class);
    }
    /**
     * Test the get methods.
     */
    public void testGetters() {
        Period ten = new Years(COUNT_YEARS);
        assertEquals(COUNT_YEARS, ten.getCount());
        assertEquals("year", ten.getName());

        Period two = new Years(COUNT_YEARSL);
        assertEquals(COUNT_YEARSL, two.getCount());
        assertEquals("year", two.getName());

        Period num = new Years(COUNT_YEARSN);
        assertEquals(COUNT_YEARS6, num.getCount());
        assertEquals("year", num.getName());
     }
    /**
     * Tests the addTo() method.
     */
    public void testAddTo() {
        _period = new Years(COUNT_YEARS6);
        DateTime start = new DateTime("05/15/02 3:06 am");
        DateTime end = new DateTime("05/15/08 3:06 am");
        assertEquals(end, _period.addTo(start));
    }
    /**
     * Tests the subtractFrom() method.
     */
    public void testSubtractFrom() {
        _period = new Years(new Integer(COUNT_YEARS6));
        DateTime start = new DateTime("05/15/08 3:06 am");
        DateTime end = new DateTime("05/15/02 3:06 am");
        assertEquals(end, _period.subtractFrom(start));
    }
    /**
     * Tests the equal instances.
     */
    public void testEquals() {
        _period = new Years(COUNT_YEARS6);
        _other = new Years(COUNT_YEARS6);

        assertEquals(_period, _other);
        assertEquals(_other, _period);

        _period = new Years(COUNT_YEARS6);
        _other = new Years(COUNT_YEARS);

        assertFalse(_period.equals(_other));
        assertFalse(_other.equals(_period));

        assertFalse(_period.equals(null));
    }

    /**
     * Tests compareTo().
     */
    public void testCompareTo() {
        _period = new Years(COUNT_YEARS);
        _other = new Years(COUNT_YEARS);

        assertEquals(0, _period.compareTo(_other));
        assertEquals(0, _other.compareTo(_period));

        _period = new Years(COUNT_YEARS);
        _other = new Years(COUNT_YEARS6);

        assertEquals(1, _period.compareTo(_other));
        assertEquals(-1, _other.compareTo(_period));
    }

    /**
     * Tests toString().
     */
    public void testToString() {
        assertEquals("1 year", new Years(1).toString());
        assertEquals("2 years", new Years(2).toString());
    }
    /**
     * Tests create().
     */
    public void testCreateYears() {

        assertEquals(new Years(COUNT_YEARS6), Years.create("6"));
        assertEquals(new Years(COUNT_YEARS), Years.create("10"));
    }
}
