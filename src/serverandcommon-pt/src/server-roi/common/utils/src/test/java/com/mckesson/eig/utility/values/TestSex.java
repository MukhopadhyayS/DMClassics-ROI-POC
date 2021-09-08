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

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestSex extends TestCase {

    private final String[][] _sexData = {{"M", "Male"}, {"F", "Female"}, {" ", "All"}};

    private List<Sex> _sexes;

    /**
     * Constructor for TestSex.
     *
     * @param arg0
     */
    public TestSex(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestSex.class, Sex.class);
    }

    @Override
	protected void setUp() throws Exception {
        _sexes = new ArrayList<Sex>();

        for (String[] element : _sexData)
		{
            _sexes.add(new Sex(element[0], element[1]));
        }
    }

    public void testSex() {
        assertEquals(_sexData.length, _sexes.size());
    }

    public void testGetAndSetCode() {

        for (int i = 0; i < _sexes.size(); i++) {
            Sex sex = _sexes.get(i);
            assertEquals(_sexData[i][0], sex.getCode());
            sex.setCode("XXXXX");
            assertEquals("XXXXX", sex.getCode());
        }
    }

    public void testGetAndSetName() {
        for (int i = 0; i < _sexes.size(); i++) {
            Sex sex = _sexes.get(i);
            assertEquals(_sexData[i][1], sex.getName());
            sex.setName("XXXXX");
            assertEquals("XXXXX", sex.getName());
        }
    }

    public Sex createSex(String code, String name) {
        return new Sex(code, name);
    }

    public void testEquals() {
        assertEquals(createSex("foo", "bar"), createSex("foo", "bar"));
    }

    public void testEqualsWithDifferentNames() {
        assertFalse(createSex("foo", "bar").equals(createSex("foo", "foo")));
    }

    public void testEqualsWithDifferentCodes() {
        assertFalse(createSex("foo", "bar").equals(createSex("bar", "bar")));
    }

    public void testHashCode() {
        assertTrue(createSex("foo", "bar").hashCode() == createSex("foo", "bar").hashCode());
        assertFalse(createSex("foo", "bar").hashCode() == createSex("bar", "bar").hashCode());
        assertTrue(createSex(null, "bar").hashCode() == createSex(null, "bar").hashCode());
        assertFalse(createSex(null, "bar").hashCode() == createSex("foo", "bar").hashCode());
        assertTrue(createSex("foo", null).hashCode() == createSex("foo", null).hashCode());
        assertFalse(createSex("foo", null).hashCode() == createSex("foo", "bar").hashCode());
        assertFalse(createSex("foo", null).hashCode() == createSex(null, "bar").hashCode());
    }
}
