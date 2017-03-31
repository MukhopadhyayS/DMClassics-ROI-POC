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

package com.mckesson.eig.utility.collections.comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.util.UtilitiesException;
import com.mckesson.eig.utility.values.DateTime;

public class TestPropertyComparatorDateDescending extends TestCase {
    private PropertyComparatorDateDescending<JustForDateTime> _comparator;

    public TestPropertyComparatorDateDescending(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(
                TestPropertyComparatorDateDescending.class,
                PropertyComparatorDateDescending.class);
    }

    @Override
	protected void setUp() throws Exception {
        _comparator = new PropertyComparatorDateDescending<JustForDateTime>("getDateTime");
    }

    @Override
	protected void tearDown() throws Exception {
        _comparator = null;
    }

    public void testComparator() {
        assertNotNull(_comparator);
        DateTime dt0 = new DateTime("11/02/2004", "MM/dd/yyyy");
        DateTime dt1 = new DateTime("11/02/2004", "MM/dd/yyyy");
        DateTime dt2 = new DateTime("11/02/2003", "MM/dd/yyyy");
        DateTime dt3 = new DateTime("11/02/2002", "MM/dd/yyyy");

        int result = _comparator.compare(new JustForDateTime(dt0), new JustForDateTime(dt1));
        assertEquals(result, 0);

        result = _comparator.compare(new JustForDateTime(dt1), new JustForDateTime(dt2));
        assertTrue(result < 0);

        result = _comparator.compare(new JustForDateTime(dt2), new JustForDateTime(dt3));
        assertTrue(result < 0);

        result = _comparator.compare(new JustForDateTime(dt3), new JustForDateTime(dt1));
        assertTrue(result > 0);

        List<JustForDateTime> list = new ArrayList<JustForDateTime>();
        list.add(new JustForDateTime(dt2));
        list.add(new JustForDateTime(dt1));
        list.add(new JustForDateTime(dt3));

        Collections.sort(list, _comparator);
        boolean isInOrder = true;
        JustForDateTime jfdt1;
        JustForDateTime jfdt2;

        for (int ii = 0; ii < list.size(); ii++) {
            if ((ii + 1) < list.size()) {
                jfdt1 = list.get(ii);
                jfdt2 = list.get(ii + 1);
                isInOrder &= (jfdt1.getDateTime()
                        .compareTo(jfdt2.getDateTime()) > 0);
                assertTrue("list was out of order", isInOrder);
            }
        }
    }

    public void testBadMethodCall() {
        _comparator = new PropertyComparatorDateDescending<JustForDateTime>("getBogusBaby");
        DateTime dt0 = new DateTime("11/02/2004", "MM/dd/yyyy");
        DateTime dt1 = new DateTime("11/02/2004", "MM/dd/yyyy");
        try {
            _comparator.compare(new JustForDateTime(dt0), new JustForDateTime(dt1));
            fail("UtilitiesException should have been thrown");
        } catch (UtilitiesException e) {
            assertTrue(true);
        }
    }

    protected class JustForDateTime {
        private final DateTime _dateTime;

        public JustForDateTime(DateTime dt) {
            _dateTime = dt;
        }

        public DateTime getDateTime() {
            return _dateTime;
        }
    }
}
