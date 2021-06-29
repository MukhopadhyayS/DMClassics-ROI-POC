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
package com.mckesson.eig.utility.util;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestObjectUtilities extends UnitTest {
    private static final int TEST_VAL = 3;

    private Nothing _nothing;
    private List<Nothing> _nothings;

    public void testHashCode() {
        assertEquals(0, ObjectUtilities.hashCode(null));
        assertEquals(TEST_VAL, ObjectUtilities.hashCode(new Integer(TEST_VAL)));
    }

    public void testEquals() {
        assertTrue(ObjectUtilities.equals("foo", "foo"));
        assertTrue(ObjectUtilities.equals(null, null));
        assertFalse(ObjectUtilities.equals("foo", null));
        assertFalse(ObjectUtilities.equals(null, "foo"));
        assertFalse(ObjectUtilities.equals("bar", "foo"));

        assertTrue(ObjectUtilities.equals(new Integer(1), new Integer(1)));
        assertTrue(ObjectUtilities.equals(null, null));
        assertFalse(ObjectUtilities.equals(new Integer(1), null));
        assertFalse(ObjectUtilities.equals(null, new Integer(1)));
        assertFalse(ObjectUtilities.equals(new Integer(1), new Integer(2)));
    }

    public void testGetClassName() {
        assertEquals("null", ObjectUtilities.getClassName(null));
        assertEquals("java.lang.String", ObjectUtilities.getClassName("hello"));
    }

    public void testGetName() {
        assertEquals("null", ObjectUtilities.getName(null));
        assertEquals("java.lang.String", ObjectUtilities.getName(String.class));
    }

    public void testGetAssociationFromCache() {
        Map<String, Map<String, String>> map = new TreeMap<String, Map<String, String>>();
        Map<String, String> map2 = new TreeMap<String, String>();
        assertNull(ObjectUtilities.getAssociationFromCache(map, "foo", "bar"));
        map.put("foo", map2);
        assertEquals(null, ObjectUtilities.getAssociationFromCache(map, "foo", "bar"));
        map2.put("bar", "finished");
        assertEquals("finished", ObjectUtilities.getAssociationFromCache(map, "foo", "bar"));
    }

    public void testCompare() {
        Integer one = new Integer(1);
        Integer two = new Integer(2);

        assertEquals(0, ObjectUtilities.compare(one, one));
        assertEquals(1, ObjectUtilities.compare(two, one));
        assertEquals(-1, ObjectUtilities.compare(one, two));

        assertEquals(0, ObjectUtilities.compare((Integer) null, (Integer) null));
        assertEquals(1, ObjectUtilities.compare(null, one));
        assertEquals(-1, ObjectUtilities.compare(two, null));
    }

    public void testAreClassesEqual() {
        assertFalse(ObjectUtilities.areClassesEqual(null, null));
        assertFalse(ObjectUtilities.areClassesEqual("foo", null));
        assertFalse(ObjectUtilities.areClassesEqual(null, "foo"));
        assertTrue(ObjectUtilities.areClassesEqual("foo", "bar"));
        assertFalse(ObjectUtilities.areClassesEqual("foo", new Integer(1)));
    }

    public void testVerifyNotNull() {
        try {
            ObjectUtilities.verifyNotNull(null);
            fail("Object is null.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ObjectUtilities.verifyNotNull(new Object());
        } catch (Exception e) {
            fail("Object is not null.");
        }
    }

    public void testGetShortClassName() {
        assertEquals("Blah", ObjectUtilities.parseUnqualifiedClassName("Blah"));
        assertEquals("Blah", ObjectUtilities
                .parseUnqualifiedClassName("duh.Blah"));
        assertEquals("Blah", ObjectUtilities
                .parseUnqualifiedClassName("com.duh.Blah"));

        assertEquals("", ObjectUtilities.parseUnqualifiedClassName(""));
        assertEquals("", ObjectUtilities.parseUnqualifiedClassName("."));
        assertEquals("", ObjectUtilities.parseUnqualifiedClassName(".."));
        assertEquals("", ObjectUtilities.parseUnqualifiedClassName("..."));

        assertEquals("null", ObjectUtilities.parseUnqualifiedClassName(null));

        assertEquals("TestObjectUtilities", ObjectUtilities
                .getUnqualifiedClassName(this));
        assertEquals("null", ObjectUtilities.getUnqualifiedClassName(null));

        assertEquals("TestObjectUtilities", ObjectUtilities
                .getUnqualifiedName(TestObjectUtilities.class));
        assertEquals("null", ObjectUtilities.getUnqualifiedName(null));
    }

    public void testConstructor() {
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(ObjectUtilities.class));
    }

    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(ObjectUtilities.class));
    }

    public void testToString() {
        assertEquals("", ObjectUtilities.toString(null));
        assertEquals("xyz", ObjectUtilities.toString("xyz"));
    }

    public void testGetAssociationName() {
        assertEquals("nothing", ObjectUtilities.getAssociationName(this,
                new Nothing()));
        assertEquals("nothing", ObjectUtilities.getAssociationName(this,
                new MoreOfNothing()));

        String name = ObjectUtilities.getAssociationName(this, new Zero());
        assertEquals("nothing", name);

        // test caching
        assertSame(name, ObjectUtilities.getAssociationName(this, new Zero()));

        assertNull(ObjectUtilities.getAssociationName(this, new Integer(2)));
        // make sure that a non-existing association (i.e. null) doesn't blow
        // up if it's in the cache
        assertNull(ObjectUtilities.getAssociationName(this, new Integer(
                TEST_VAL)));
    }

    public void testGetPluralAssociationName() {
        assertEquals("nothings", ObjectUtilities.getPluralAssociationName(this,
                new Nothing()));
        assertEquals("nothings", ObjectUtilities.getPluralAssociationName(this,
                new MoreOfNothing()));

        String name = ObjectUtilities
                .getPluralAssociationName(this, new Zero());
        assertEquals("nothings", name);

        // test caching
        assertSame(name, ObjectUtilities.getPluralAssociationName(this,
                new Zero()));

        assertNull(ObjectUtilities.getPluralAssociationName(this,
                new Integer(2)));
        // make sure that a non-existing association (i.e. null) doesn't blow
        // up if it's in the cache
        assertNull(ObjectUtilities.getPluralAssociationName(this, new Integer(
                TEST_VAL)));

        assertNull(ObjectUtilities.getPluralAssociationName(null, new Integer(TEST_VAL)));
        assertNull(ObjectUtilities.getPluralAssociationName(new Integer(TEST_VAL), null));
        assertNull(ObjectUtilities.getPluralAssociationName(null, null));
    }

    public void testIsAssignable() {
        assertFalse(ObjectUtilities.isAssignable(String.class, null));
        assertFalse(ObjectUtilities.isAssignable(Integer.class, "foo"));
        assertFalse(ObjectUtilities.isAssignable(MoreOfNothing.class,
                new Nothing()));

        assertTrue(ObjectUtilities.isAssignable(String.class, "foo"));
        assertTrue(ObjectUtilities.isAssignable(Object.class, "foo"));
    }

    public void testIsEmtpy() {
        assertTrue(ObjectUtilities.isEmptyAsString(null));
        assertTrue(ObjectUtilities.isEmptyAsString(" "));

        assertFalse(ObjectUtilities.isEmptyAsString("foo bar"));
    }

    public Nothing getNothing() {
        return _nothing;
    }

    public void setNothing(Nothing nothing) {
        _nothing = nothing;
    }

    public List<Nothing> getNothings() {
        return _nothings;
    }

    public void setNothings(List<Nothing> nothings) {
        _nothings = nothings;
    }

    public static class Nothing {
    }

    public static class MoreOfNothing extends Nothing {
    }

    public static class Zero extends MoreOfNothing {
    }
}
