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

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

/**
 * @author Kenneth Partlow
 * 
 */
public class TestMinutes extends UnitTest {
    public static final int COUNT_MINUTES6 = 6;
    public static final int COUNT_MINUTES = 10;
    public static final int COUNT_MILLISECS = 600000;
    public static final long COUNT_MINUTESL = 2000L;
    public static final Number COUNT_MINUTESN = new Integer(6);

    public TestMinutes(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestMinutes.class, Minutes.class);
    }

    public void testGetters() {
        Period ten = new Minutes(COUNT_MINUTES);
        assertEquals(COUNT_MINUTES, ten.getCount());
        assertEquals("minute", ten.getName());

        Period two = new Minutes(COUNT_MINUTESL);
        assertEquals(COUNT_MINUTESL, two.getCount());
        assertEquals("minute", two.getName());
        
        Period num = new Minutes(COUNT_MINUTESN);
        assertEquals(COUNT_MINUTES6, num.getCount());
        assertEquals("minute", num.getName());
        
        
    }

    public void testToMillis() {
        Minutes minutes = new Minutes(COUNT_MINUTES);
        assertEquals(COUNT_MILLISECS, minutes.toMillis());
    }

    public void testAddTo() {
        Period period = new Minutes(COUNT_MINUTES6);
        DateTime start = new DateTime("11/15/02 12:00 am");
        DateTime end = new DateTime("11/15/02 12:06 am");
        assertEquals(end, period.addTo(start));
    }

}
