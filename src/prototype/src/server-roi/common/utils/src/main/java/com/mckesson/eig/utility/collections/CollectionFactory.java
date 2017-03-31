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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Factory for various collections.
 * <p>
 * The methods for creating ordered maps and sets could possibly check for and
 * return JDK 1.4+ <code> LinkedHashMap </code> and <code> LinkedHashSet </code>
 * using reflection. We can't code to those directly, because of needing to
 * deploy in 1.3.1.
 * </p>
 *
 * @see com.mckesson.eig.utility.collections.OrderedHashSet
 * @see com.mckesson.eig.utility.collections.OrderedHashMap
 */
public final class CollectionFactory {
    /**
     * a private constructor.
     */
    private CollectionFactory() {
    }
    /**
     * creates an OrderedHashSet.
     *
     * @return Set
     */
    public static <T> Set<T> createOrderedHashSet() {
        return new LinkedHashSet<T>();
    }
    /**
     * creates an OrderedHashSet.
     *
     * @param c
     *            An Collection Object.
     * @return Set
     */
    public static <T> Set<T> createOrderedHashSet(Collection<T> c) {
        return new LinkedHashSet<T>(c);
    }
    /**
     * This method creates an OrderedHashSet with the given initial capacity and
     * load factor.
     *
     * @param initialCapacity
     *            Capacity(No.of buckets)at the time when it is created.
     * @param loadFactor
     *            measure of how full the hash table is allowed to get before
     *            its capacity is automatically increased.
     * @return OrderedHashSet
     */
    public static <T> Set<T> createOrderedHashSet(int initialCapacity, float loadFactor) {
        return new LinkedHashSet<T>(initialCapacity, loadFactor);
    }
    /**
     * This method creates an OrderedHashSet with the given initial capacity.
     *
     * @param initialCapacity
     *            Capacity(No.of buckets)at the time when the HashSet is
     *            created.
     * @return Set
     */
    public static <T> Set<T> createOrderedHashSet(int initialCapacity) {
        return new LinkedHashSet<T>(initialCapacity);
    }
    /**
     * This method creates an OrderedHashMap.
     *
     * @return OrderedHashMap
     */
    public static <K, V> Map<K, V> createOrderedHashMap() {
        return new LinkedHashMap<K, V>();
    }
    /**
     * This method creates an OrderedHashMap with the given Map.
     *
     * @param m
     *            Map that has to be ordered
     * @return OrderedHashMap
     */
    public static <K, V> Map<K, V> createOrderedHashMap(Map<K, V> m) {
        return new LinkedHashMap<K, V>(m);
    }
    /**
     * This method creates an OrderedHashMap with the given capacity and
     * loadfactor.
     *
     * @param initialSize
     *            Capacity(No.of buckets)at the time when it is created.
     * @param loadFactor
     *            measure of how full the hash table is allowed to get before
     *            its capacity is automatically increased
     * @return OrderedHashMap
     */
    public static <K, V> Map<K, V> createOrderedHashMap(int initialSize, float loadFactor) {
        return new LinkedHashMap<K, V>(initialSize, loadFactor);
    }
    /**
     * This method creates an OrderedHashSet with the given initial capacity.
     *
     * @param initialSize
     *            Capacity(No.of buckets)at the time when the HashMap is
     *            created.
     * @return Map
     */
    public static <K, V> Map<K, V> createOrderedHashMap(int initialSize) {
        return new LinkedHashMap<K, V>(initialSize);
    }
}
