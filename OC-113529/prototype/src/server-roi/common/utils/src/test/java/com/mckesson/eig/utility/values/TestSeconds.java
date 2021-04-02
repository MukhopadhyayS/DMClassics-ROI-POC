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

import com.mckesson.eig.utility.testing.CoverageSuite;

import junit.framework.Test;
import junit.framework.TestCase;
/**
  *Test case to test the class Seconds.
 */
public class TestSeconds extends TestCase {
    /**
     * Private variable of type int.
     * 
     */
    public static final int COUNT_SECONDS6 = 6;
    /**
     * Private variable of type int.
     */
    public static final int COUNT_SECONDS = 10;
    /**
     * Private variable of type int.
     */
    public static final int COUNT_MILLISECS = 10000;
    /**
     * Private variable of type int.
     */
    public static final long COUNT_SECONDSL = 2000L;
    /**
     * Private variable of type int.
     */
    public static final Number COUNT_SECONDSN = new Integer(6);
    /**
     * Constructs the class.
     * @param name
     * name of the test case.
     */
    public TestSeconds(String name) {
        super(name);
    }
    /**
     * @return
     * CoverageSuite
     */
    public static Test suite() {
        return new CoverageSuite(TestSeconds.class, Seconds.class);
    }
    /**
     * Test the get methods.
     */
    public void testGetters() {
        Period ten = new Seconds(COUNT_SECONDS);
        assertEquals(COUNT_SECONDS, ten.getCount());
        assertEquals("second", ten.getName());
        Period two = new Seconds(COUNT_SECONDSL);
        assertEquals(COUNT_SECONDSL, two.getCount());
        assertEquals("second", two.getName());
        Period num = new Seconds(COUNT_SECONDSN);
        assertEquals(COUNT_SECONDS6, num.getCount());
        assertEquals("second", num.getName());
     }

    /**
     * Tests toMillis().
     */
    public void testToMillis() {
        Seconds seconds = new Seconds(COUNT_SECONDS);
        assertEquals(COUNT_MILLISECS, seconds.toMillis());
    }
    /**
     * Tests addTo().
     */
    public void testAddTo() {
        Period period = new Seconds(COUNT_SECONDS);
        DateTime start = new 
        DateTime("11/15/02 12:00:00 am", "M/d/yy h:mm:ss a");
        DateTime end = new 
        DateTime("11/15/02 12:00:10 am", "M/d/yy h:mm:ss a");
        assertEquals(end, period.addTo(start));
    }

}

