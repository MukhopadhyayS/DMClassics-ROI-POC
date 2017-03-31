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

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.ConversionUtilities;

public class ValueMap<K, V> implements Map<K, V>, Serializable {
	private static final long serialVersionUID = -8717166794949408486L;

	public static final int INT_VALUE = 10;

    public static final int HEX_VALUE = 16;

    private Map<K, V> _map;

    private final Map<K, V> _defaults;

    public ValueMap() {
        _map = new HashMap<K, V>();
        _defaults = new HashMap<K, V>();
    }

    protected ValueMap(Map<K, V> underlying) {
        _map = underlying;
        _defaults = new HashMap<K, V>();
    }

    public synchronized void putAll(Map<? extends K, ? extends V> map) {
        if (map != null) {
            _map.putAll(map);
        }
    }

    public synchronized void putAll(ResourceBundle bundle) {
        if (bundle != null) {
            putAll(this, bundle);
        }
    }

    public synchronized void setDefaults(Map<? extends K, ? extends V> map) {
        _defaults.clear();
        if (map != null) {
            _defaults.putAll(map);
        }
    }

    public synchronized void setDefaults(ResourceBundle bundle) {
        _defaults.clear();
        if (bundle != null) {
            putAll(_defaults, bundle);
        }
    }

    private void putAll(Map<? extends K, ? extends V> map, ResourceBundle resbundle) {
        CollectionUtilities.putAll(map, resbundle);
    }

    public boolean getBooleanValue(K key, boolean defaultValue) {
        return ConversionUtilities.toBooleanValue(get(key), defaultValue);
    }

    public int getIntValue(K key, int defaultValue) {
        return getIntValue(key, defaultValue, INT_VALUE);
    }

    public int getHexValue(K key, int defaultValue) {
        return getIntValue(key, defaultValue, HEX_VALUE);
    }

    public int getIntValue(K key, int defaultValue, int base) {
        String str = getString(key);
        if (str != null) {
            try {
                return Integer.parseInt(str, base);
            } catch (NumberFormatException e) {
                System.out.println("Number Format Exception "
                        + "while Getting int value in ValueMap.java");
            }
        }
        return defaultValue;
    }

    public Integer getInteger(K key) {
        String str = getString(key);
        return (str == null) ? null : Integer.valueOf(str);
    }

    public String getString(K key) {
        Object value = get(key);
        return (value == null) ? null : value.toString();
    }

    public synchronized V get(Object key) {
        V value = doGet(key);
        if (value != null) {
            return value;
        }
        return getDefault(key);
    }

    public synchronized V set(K key, V value) {
        return doPut(key, value);
    }

    public synchronized V put(K key, V value) {
        return doPut(key, value);
    }

    public synchronized V remove(Object key) {
        return _map.remove(key);
    }

    public synchronized Enumeration<K> keys() {
        return new EnumerationAdapter<K>(keySet().iterator());
    }

    public synchronized void clear() {
        _map.clear();
    }

    public synchronized boolean contains(V value) {
        return containsValue(value);
    }

    public synchronized boolean containsValue(Object value) {
        return doContainsValue(value) || _defaults.containsValue(value);
    }

    public synchronized boolean containsKey(Object key) {
        return _map.containsKey(key) || _defaults.containsKey(key);
    }

    public synchronized V getDefault(Object key) {
        return _defaults.get(key);
    }

    public synchronized Set<Map.Entry<K, V>> entrySet() {
        return _map.entrySet();
    }

    public synchronized boolean isEmpty() {
        return _map.isEmpty();
    }

    public synchronized Set<K> keySet() {
        return _map.keySet();
    }

    public synchronized int size() {
        return _map.size();
    }

    public synchronized Collection<V> values() {
        return _map.values();
    }

    protected V doPut(K key, V value) {
        return _map.put(key, value);
    }

    protected V doGet(Object key) {
        return _map.get(key);
    }

    protected boolean doContainsValue(Object value) {
        return _map.containsValue(value);
    }

    protected synchronized void setUnderlyingMap(Map<K, V> map) {
        _map = map;
    }

    protected synchronized Map<K, V> getUnderlyingMap() {
        return _map;
    }
}
