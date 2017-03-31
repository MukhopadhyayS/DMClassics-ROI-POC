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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import com.mckesson.eig.utility.collections.CollectionFactory;
import com.mckesson.eig.utility.io.FileLoader;
import com.mckesson.eig.utility.testing.UnitTest;

public class TestCollectionUtilities extends UnitTest {
    private static final int LISTCONST_INT1 = 3;
    private static final int LISTLCONST_INT2 = 4;
    private static final int LISTCONST_INT3 = 6;
    private List<Object> _list;
    private Map<Object, Object> _map;

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        _list = new ArrayList<Object>();
        _map = new HashMap<Object, Object>();
    }

    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
    }
    /**
     * Tests the constructor is private.
     */
    public void testConstructorIsPrivate() {
        assertTrue(ClassUtilities.areAllConstructorsPrivate(CollectionUtilities.class));
        ReflectionUtilities.callPrivateConstructor(CollectionUtilities.class);
    }

    public void testIsEmpty() {
        assertTrue(CollectionUtilities.isEmpty((Collection<Object>) null));
        assertTrue(CollectionUtilities.isEmpty((Map<Object, Object>) null));
        assertTrue(CollectionUtilities.isEmpty(_list));
        assertTrue(CollectionUtilities.isEmpty(_map));
        _list.add(new Object());
        assertFalse(CollectionUtilities.isEmpty(_list));
        _map.put(new Object(), new Object());
        assertFalse(CollectionUtilities.isEmpty(_map));
    }

    public void testHasContent() {
        assertFalse(CollectionUtilities.hasContent((Collection<Object>) null));
        assertFalse(CollectionUtilities.hasContent((Map<Object, Object>) null));
        assertFalse(CollectionUtilities.hasContent(_list));
        assertFalse(CollectionUtilities.hasContent(_map));
        _list.add(new Object());
        assertTrue(CollectionUtilities.hasContent(_list));
        _map.put(new Object(), new Object());
        assertTrue(CollectionUtilities.hasContent(_map));
    }

    public void testSize() {
        _list.add(new Object());
        assertEquals(ONE, CollectionUtilities.size(_list));
        assertEquals(ZERO, CollectionUtilities.size((Collection<Object>) null));
        _map.put(new Object(), new Object());
        assertFalse(CollectionUtilities.isEmpty(_map));
        assertEquals(ONE, CollectionUtilities.size(_map));
        assertEquals(ZERO, CollectionUtilities.size((Map<Object, Object>) null));
    }

    public void testLength() {
        assertEquals(TWO, CollectionUtilities.length(new String[]{"foo", "bar"}));
        assertEquals(ZERO, CollectionUtilities.length(new Object[ZERO]));
        assertEquals(ZERO, CollectionUtilities.length((Object[]) null));
    }

    public void testToMapKeys() {
        Map<String, Object> map = CollectionUtilities.toMapKeys(new String[]{"foo", "bar"});
        assertEquals(TWO, map.size());
        assertTrue(map.containsKey("foo"));
        assertTrue(map.containsKey("bar"));
        assertNull(map.get("foo"));
        assertNull(map.get("bar"));

        Map<Object, Object> empty = CollectionUtilities.toMapKeys(new Object[ZERO]);
        assertEquals(ZERO, empty.size());

        Map<Object, Object> fromNull = CollectionUtilities.toMapKeys((Object[]) null);
        assertEquals(ZERO, fromNull.size());
    }

    public void testToOrderedSet() {
        Set<String> set = CollectionUtilities.toOrderedSet(new String[]{"foo", "bar", "foo"});
        assertEquals(TWO, set.size());
        Iterator<String> i = set.iterator();
        assertEquals("foo", i.next());
        assertEquals("bar", i.next());

        Set<Object> empty = CollectionUtilities.toOrderedSet(new Object[ZERO]);
        assertEquals(ZERO, empty.size());

        Set<Object> fromNull = CollectionUtilities.toOrderedSet((Object[]) null);
        assertEquals(ZERO, fromNull.size());
    }

    public void testVerifyHasContentNull() {
        try {
            CollectionUtilities.verifyHasContent(null);
            fail("Should have thrown IllegalArgumentException");
        } catch (Throwable t) {
//            t.printStackTrace();
        }
    }

    public void testVerifyHasContentEmpty() {
        try {
            CollectionUtilities.verifyHasContent(_list);
            fail("Should have thrown IllegalArgumentException");
        } catch (Throwable t) {
//            t.printStackTrace();
        }
    }

    public void testVerifyHasContentNotEmpty() {
        try {
            _list.add(new Object());
            CollectionUtilities.verifyHasContent(_list);
        } catch (IllegalArgumentException i) {
            fail("Should not have thrown IllegalArgumentException");
        }
    }

    public void testCopyResourceBundleToMap() {
        Map<String, String> m = new HashMap<String, String>();
        CollectionUtilities.putAll(m, null);
        assertTrue(m.isEmpty());

        ResourceBundle rb = FileLoader.getResourceBundle(this, "data.test");
        CollectionUtilities.putAll(m, rb);
        assertEquals(ONE, m.size());
        assertEquals("baz", m.get("foobar"));
    }

    public void testSafeList() {
        assertEquals(new ArrayList<Object>(), CollectionUtilities.safeList(null));

        List<Object> list = new ArrayList<Object>();
        assertSame(list, CollectionUtilities.safeList(list));
    }

    public void testPutAllWithNullTarget() {
        try {
            CollectionUtilities.putAll(null, null);
        } catch (Exception e) {
            fail();
        }
    }

    public void testToArray() {
        List<Integer> list = createTestList();
        Object[] array = CollectionUtilities.toArray(list, Integer.class);
        assertEquals(TWO, array.length);
        assertSame(list.get(ZERO), array[ZERO]);
        assertSame(list.get(ONE), array[ONE]);
    }

    public void testAddAll() {
        Integer[] objects = createTestArray();
        List<Integer> list = new ArrayList<Integer>();
        CollectionUtilities.addAll(list, objects);
        assertEquals(TWO, list.size());
        assertEquals(list.get(ZERO), objects[ZERO]);
        assertEquals(list.get(ONE), objects[ONE]);
    }

    public void testAddAllWithNullTarget() {
        try {
            CollectionUtilities.addAll(null, createTestArray());
        } catch (Exception e) {
            fail();
        }
    }

    public void testAddAllWithNullSource() {
        try {
            List<Integer> list = new ArrayList<Integer>();
            CollectionUtilities.addAll(list, null);
            assertEquals(ZERO, list.size());
        } catch (Exception e) {
            fail();
        }
    }

    public void testBuildStringSet() {
        verifyCollection(CollectionUtilities.buildStringSet("a, b, c, d, e, f", ", "));
    }

    public void testBuildStringList() {
        verifyCollection(CollectionUtilities.buildStringList("a, b, c, d, e, f", ", "));
    }

    public void testEquals() {
        Collection<Integer> c1 = createTestList();
        Collection<Integer> c2 = createTestList();
        assertTrue(CollectionUtilities.equals(c1, c2));
        assertFalse(CollectionUtilities.equals(c1, null));
        assertFalse(CollectionUtilities.equals(null, c2));
        assertTrue(CollectionUtilities.equals(null, null));
    }

    public void testSortAndSearch() {
        CollectionUtilities.sortAndSearch(_list, null, null);
    }

    public void testEnsureValueExistsForKey() {
        Map<String, List<String>> map1 = new HashMap<String, List<String>>();
        List<String> list = CollectionUtilities.ensureListExistsForKey(map1, "foo");
        assertSame(list, map1.get("foo"));
        assertSame(list, CollectionUtilities.ensureListExistsForKey(map1, "foo"));

        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        Set<String> fooSet = CollectionUtilities.ensureOrderedSetExistsForKey(map, "foo");
        Set<String> barSet = CollectionUtilities.ensureOrderedSetExistsForKey(map, "bar");
        assertSame(barSet, map.get("bar"));
        assertSame(barSet, CollectionUtilities.ensureOrderedSetExistsForKey(map, "bar"));
        assertNotSame(barSet, fooSet);
        barSet.add("1");
        barSet.add("2");
        barSet.add("2");
        barSet.add("3");
        Iterator<String> i = barSet.iterator();
        assertEquals("1", i.next());
        assertEquals("2", i.next());
        assertEquals("3", i.next());
        assertEquals(LISTCONST_INT1, barSet.size());
        assertEquals(TWO, map.size());
    }

    public void testUniqueList() {
        List<String> list = new ArrayList<String>();
        list.add("abc");
        list.add("abc");
        assertEquals(TWO, list.size());
        List<String> unique = CollectionUtilities.uniqueList(list);
        assertEquals(ONE, unique.size());
        list.add("def");
        list.add("ghi");
        assertEquals(LISTLCONST_INT2, list.size());
        unique = CollectionUtilities.uniqueList(list);
        assertEquals(LISTCONST_INT1, unique.size());
        assertEquals(ZERO, CollectionUtilities.uniqueList(null).size());
        assertEquals(ZERO, CollectionUtilities.uniqueList(new ArrayList<String>()).size());
    }
    public void testcalculateSizeForCopy() {
        final int size = 16;
        _list.add("ghi");
        assertEquals(size, CollectionUtilities.calculateSizeForCopy(_list));
        _list.clear();
        assertEquals(size, CollectionUtilities.calculateSizeForCopy(_list));
        assertEquals(size, CollectionUtilities.calculateSizeForCopy(_map));
        _map.clear();
        assertEquals(size, CollectionUtilities.calculateSizeForCopy(_map));

    }
    public void testtoListOfValues() {
        String o = new String("1");
        _map.put(o, "foo");
        _map.put("2", "bar");
        assertTrue(CollectionUtilities.hasContent(_map));
        List<Object> list = CollectionUtilities.toListOfValues(_map);
        assertEquals(list.size(), _map.size());
        assertEquals(_map.get("2"), list.get(ZERO));
        assertNotNull(CollectionUtilities.toListOfValues(_map));

    }
    public void testcopyToList() {

        Vector<String> v = new Vector<String>();
        v.add("foo");
        v.add("bar");
        Enumeration<String> e = v.elements();
        List<String> l = CollectionUtilities.copyToList(e);
        assertNotNull(l);
        e = v.elements();
        assertEquals(l.get(ZERO), e.nextElement());
        assertEquals(l.get(ONE), e.nextElement());

    }
    public void testcopyToIterator() {
        Vector<String> v = new Vector<String>();
        v.add("foo");
        v.add("bar");
        Enumeration<String> e = v.elements();
        Iterator<String> i = CollectionUtilities.copyToIterator(e);
        e = v.elements();
        assertEquals(i.next(), e.nextElement());
        assertEquals(i.next(), e.nextElement());
    }
    public void testSwap() {
        _list.add("bar");
        _list.add("foo");
        _list.add("car");
        CollectionUtilities.swap(_list, ZERO, ONE);
        System.out.println("list0" + _list.get(ZERO));
        System.out.println("list0" + _list.get(ONE));
        System.out.println("list0" + _list.get(TWO));
        assertEquals("foo", _list.get(ZERO));
        assertEquals("bar", _list.get(ONE));
    }

    public void testCreeteList() {
        List<String> list = CollectionUtilities.createList("foo");
        assertEquals("foo", list.get(ZERO));

    }

    public void testExtractRange() {
        Collection<String> c = CollectionFactory.createOrderedHashSet();
        c.add("1");
        c.add("2");
        c.add("3");
        c.add("4");
        c = Collections.unmodifiableCollection(c);
        Collection<String> r1 = CollectionUtilities.extractRange(c, ONE, LISTCONST_INT1);
        Iterator<String> i1 = r1.iterator();
        assertEquals("2", i1.next());
        assertEquals("3", i1.next());
        assertEquals("4", i1.next());
        assertFalse(i1.hasNext());
        Collection<String> r2 = CollectionUtilities.extractRange(c, ZERO, ZERO);
        Iterator<String> i2 = r2.iterator();
        assertEquals("1", i2.next());
        assertFalse(i2.hasNext());
        Collection<String> r3 = CollectionUtilities.extractRange(c, LISTCONST_INT1, LISTCONST_INT1);
        Iterator<String> i3 = r3.iterator();
        assertEquals("4", i3.next());
        assertFalse(i3.hasNext());
    }

    public static boolean equals(Collection<?> first, Collection<?> second) {
        if (first == null || second == null) {
            return first == second;
        }
        return first.equals(second);
    }

    public void verifyCollection(Collection<String> c) {
        assertEquals(LISTCONST_INT3, c.size());
        assertTrue(c.contains("a"));
        assertTrue(c.contains("b"));
        assertTrue(c.contains("c"));
        assertTrue(c.contains("d"));
        assertTrue(c.contains("e"));
        assertTrue(c.contains("f"));
    }

    public static Integer[] createTestArray() {
        return new Integer[]{new Integer(ONE), new Integer(TWO)};
    }

    public static List<Integer> createTestList() {
        List<Integer> c = new ArrayList<Integer>();
        c.add(new Integer(ONE));
        c.add(new Integer(TWO));
        return c;
    }

}
