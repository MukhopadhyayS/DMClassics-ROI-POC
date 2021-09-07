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

public class TestPropertyComparator extends TestCase {
    private static final int ADD_VAL_3 = 3;
    private static final int ADD_VAL_4 = 4;
    private static final int ADD_VAL_5 = 5;
    private final String[] _letters = {"a", "B", "b", "C", "c", "Z"};

    private PropertyComparator<MyTestStringObject> _stringComparator;

    private List<MyTestStringObject> _list;

    public class MyTestStringObject {
        private final String _name;

        MyTestStringObject(String name) {
            _name = name;
        }

        public String getName() {
            return _name;
        }
    }

    private MyTestStringObject getObject(String value) {
        return new MyTestStringObject(value);
    }

    public TestPropertyComparator() {
    }

    public static Test suite() {
        return new CoverageSuite(TestPropertyComparator.class, PropertyComparator.class);
    }

    @Override
	protected void setUp() throws Exception {
        _stringComparator = new PropertyComparator<MyTestStringObject>("getName");
        _list = new ArrayList<MyTestStringObject>();

        _list.add(getObject(_letters[ADD_VAL_3]));
        _list.add(getObject(_letters[2]));
        _list.add(getObject(_letters[ADD_VAL_5]));
        _list.add(getObject(_letters[0]));
        _list.add(getObject(_letters[ADD_VAL_4]));
        _list.add(getObject(_letters[1]));
    }

    @Override
	protected void tearDown() throws Exception {
        _stringComparator = null;
    }

    public void testCompareGreaterThan() {
        assertTrue(_stringComparator.compare(getObject("B"), getObject("A")) > 0);
    }

    public void testCompareLessThan() {
        assertTrue(_stringComparator.compare(getObject("A"), getObject("B")) < 0);
    }

    public void testCompareEquals() {
        assertEquals(0, _stringComparator.compare(getObject("A"), getObject("A")));
    }


    public void testAlphaList() {
        Collections.sort(_list, _stringComparator);
        for (int i = 0; i < _list.size(); i++) {
            System.out.println((_list.get(i)).getName());
            assertEquals(_letters[i], (_list.get(i)).getName());
        }
    }
    public void testSetStrproperty() {
        PropertyComparator<MyTestStringObject> comp = new PropertyComparator<MyTestStringObject>();
        comp.setMethodName("getName");
        assertEquals("getName", comp.getMethodName());
        Collections.sort(_list, comp);
        for (int ii = 0; ii < _list.size(); ii++) {
            assertEquals(_letters[ii], (_list.get(ii)).getName());
        }
    }
}
