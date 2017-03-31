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

package com.mckesson.eig.utility.collections;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestValueMap extends UnitTest {
    private static final int TEST_HEX = 17;
    private static final int TEST_VALUE = 11;
    private static final int VALUE = -5;
    private ValueMap<String, String> _map;

    public TestValueMap(String name) {
        super(name);
    }

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        _map = createValueMap();
    }

    @Override
	protected void tearDown() throws Exception {
        _map = null;
        super.tearDown();
    }

    protected ResourceBundle getResourceBundle(String baseName) {
        return ResourceBundle.getBundle(buildResourceName(baseName));
    }

    protected String buildResourceName(String baseName) {
        return getClass().getPackage().getName() + '.' + baseName;
    }

    protected ValueMap<String, String> createValueMap() {
        return new ValueMap<String, String>();
    }

    protected ValueMap<String, String> createObjectMap(Map<String, String> m) {
        ValueMap<String, String> map = new ValueMap<String, String>();

        map.putAll(m);
        return map;
    }

    protected ValueMap<String, String> createObjectMap(ResourceBundle bundle) {
        ValueMap<String, String> map = new ValueMap<String, String>();
        map.putAll(bundle);
        return map;
    }

    protected ResourceBundle createResourceBundle(String key, String value) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream pw = new PrintStream(baos);
        pw.print(key);
        pw.print("=");
        pw.println(value);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return new PropertyResourceBundle(bais);
    }

    public void testDefaultConstructor() {
        _map = createValueMap();
        assertNull(_map.get("foo"));
        assertNull(_map.getString("foo"));
    }

    public void testConstructorWithMap() {
        Map<String, String> m = new HashMap<String, String>();
        Map<String, String> orderedHashMap = new OrderedHashMap<String, String>(m);
        orderedHashMap.put("foo", "bar");
        _map = new ValueMap<String, String>(orderedHashMap);
        assertEquals("bar", _map.get("foo"));
        assertEquals("bar", _map.getString("foo"));
    }

    public void testContructorWithResourceBundle() throws Exception {
        _map = createObjectMap(createResourceBundle("foo", "bar"));
        assertEquals("bar", _map.get("foo"));
        assertEquals("bar", _map.getString("foo"));
    }

    public void testGetAndSet() {
        assertNull(_map.get("foo"));
        _map.set("foo", "bar");
        assertEquals("bar", _map.get("foo"));
    }

    public void testGetWhereNameIsNull() {
        assertNull(_map.get(null));
    }

    public void testSetWhereValueIsNull() {
        assertNull(_map.set("foo", null));
    }

    public void testSetWhereNameIsNull() {
        assertNull(_map.set(null, null));
    }

    public void testRemoveWhereNameIsNull() {
        _map.remove(null);
    }

    public void testRemove() {
        assertNull(_map.get("foo"));
        _map.set("foo", "bar");
        assertEquals("bar", _map.get("foo"));
        _map.remove("foo");
        assertNull(_map.get("foo"));
    }

    public void testGetNames() {
        assertNull(_map.get("foo"));
        _map.set("foo", "bar");
        assertEquals("bar", _map.get("foo"));
        _map.set("k", "p");
        assertEquals("p", _map.get("k"));
        Enumeration<String> keys = _map.keys();
        Object key = keys.nextElement();
        // order may not be consistent on hashmaps
        assertTrue("k".equals(key) || "foo".equals(key));
        assertTrue(keys.hasMoreElements());
        key = keys.nextElement();
        assertTrue("k".equals(key) || "foo".equals(key));
    }

    public void testClear() {
        assertNull(_map.get("foo"));
        _map.set("foo", "bar");
        assertEquals("bar", _map.get("foo"));
        _map.set("k", "p");
        assertEquals("p", _map.get("k"));
        _map.clear();
        assertNull(_map.get("foo"));
        assertNull(_map.get("k"));
    }

    public void testGetAndSetDefaults() {
        assertNull(_map.get("foo"));
        assertFalse(_map.contains("bar"));
        assertFalse(_map.containsKey("foo"));

        final double testVal = 0.75;
        Map<String, String> m = new OrderedHashMap<String, String>(FOUR, (float) testVal);
        m.put("foo", "bar");
        _map.setDefaults(m);

        assertEquals("bar", _map.get("foo"));
        assertTrue(_map.contains("bar"));
        assertTrue(_map.containsKey("foo"));
    }

    public void testSetDefaultAndSetCurrentAndGet() throws Exception {
        _map.setDefaults(createResourceBundle("foo", "bar"));
        assertEquals("bar", _map.get("foo"));
        _map.set("foo", "bar2");
        assertEquals("bar2", _map.get("foo"));
        assertTrue(_map.contains("bar2"));
        assertTrue(_map.containsKey("foo"));
        _map.remove("foo");
        assertEquals("bar", _map.get("foo"));
    }

    public void testGetIntValue() {
        assertEquals(VALUE, _map.getIntValue("decimal", VALUE));

        _map.set("decimal", "11");
        assertEquals(TEST_VALUE, _map.getIntValue("decimal", ZERO));

        _map.set("decimal", "x");
        assertEquals(0, _map.getIntValue("decimal", ZERO));
    }

    public void testGetHexValue() {
        _map.set("hexadecimal", "11");
        int hexadecimal = _map.getHexValue("hexadecimal", ZERO);
        assertEquals(TEST_HEX, hexadecimal);

        _map.set("hexadecimal", "x");
        hexadecimal = _map.getHexValue("hexadecimal", ZERO);
        assertEquals(0, hexadecimal);

        _map.set("hexadecimal", null);
        hexadecimal = _map.getHexValue("hexadecimal", ZERO);
        assertEquals(0, hexadecimal);
    }

    public void testGetInteger() {

        assertNull(_map.getInteger("i"));
        _map.set("i", "11");
        assertEquals(new Integer(TEST_VALUE), _map.getInteger("i"));
    }

    public void testSize() {
        assertTrue(_map.isEmpty());
        assertEquals(ZERO, _map.size());
        _map.set("i", "11");
        assertFalse(_map.isEmpty());
        assertEquals(ONE, _map.size());
        assertEquals(_map.entrySet().size(), _map.size());
        assertEquals(_map.values().size(), _map.size());
    }

    public void testNullParameters() {
        Map<String, String> m = null;
        ResourceBundle rb = null;
        _map.putAll(m);
        _map.setDefaults(m);
        _map.setDefaults(rb);
        assertFalse(_map.containsValue(null));
        assertFalse(_map.containsKey(null));
    }

    public void testUnderlyingMap() {
        Map<String, String> m = new HashMap<String, String>();
        m.put("i", "11");
        _map.setUnderlyingMap(m);
        assertEquals(m, _map.getUnderlyingMap());
    }
}
