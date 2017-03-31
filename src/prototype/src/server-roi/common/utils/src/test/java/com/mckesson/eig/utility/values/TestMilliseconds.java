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

public class TestMilliseconds extends TestCase {
    public static final int COUNT_MILISECONDS6 = 6;
    public static final int COUNT_MILISECONDS = 10;
    public static final int COUNT_MILLISECS = 10000;
    public static final long COUNT_MILISECONDSL = 2000L;
    public static final Number COUNT_MILISECONDSN = new Integer(6);

    public TestMilliseconds(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestMilliseconds.class, Milliseconds.class);
    }

    public void testGetters() {
        Period ten = new Milliseconds(COUNT_MILISECONDS);
        assertEquals(COUNT_MILISECONDS, ten.getCount());
        assertEquals("millisecond", ten.getName());
        Period two = new Milliseconds(COUNT_MILISECONDSL);
        assertEquals(COUNT_MILISECONDSL, two.getCount());
        assertEquals("millisecond", two.getName());
        Period num = new Milliseconds(COUNT_MILISECONDSN);
        assertEquals(COUNT_MILISECONDS6, num.getCount());
        assertEquals("millisecond", num.getName());

    }

    public void testToMillis() {
        Milliseconds milliseconds = new Milliseconds(COUNT_MILISECONDS);
        assertEquals(COUNT_MILISECONDS, milliseconds.toMillis());
    }

    public void testAddTo() {
        Period period = new Milliseconds(COUNT_MILISECONDS);
        DateTime start = new DateTime("11/15/02 12:00:00:00 am",
                "M/d/yy h:mm:ss:SS a");
        DateTime end = new DateTime("11/15/02 12:00:00:10 am",
                "M/d/yy h:mm:ss:SS a");
        assertEquals(end, period.addTo(start));
    }

}
