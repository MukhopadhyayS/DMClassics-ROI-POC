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

package com.mckesson.eig.utility.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import com.mckesson.eig.utility.collections.CollectionFactory;

public final class CollectionUtilities {

    private static final int ADD_INT = 4;

    private static final int MAX_INT = 16;

    private static final float DIV_INT = .75f;

    private CollectionUtilities() {
    }

    public static <T> List<T> safeList(List<T> list) {
        return (list == null) ? new ArrayList<T>() : list;
    }

    public static <T> boolean isEmpty(Collection<T> c) {
        return c == null || c.isEmpty();
    }

    public static <T> boolean hasContent(Collection<T> c) {
        return !isEmpty(c);
    }

    public static <T> void verifyHasContent(Collection<T> c) {
        if (isEmpty(c)) {
            throw new IllegalArgumentException("null or empty collection");
        }
    }

    public static <T> int size(Collection<T> c) {
        return (c == null) ? 0 : c.size();
    }

    public static <K, V> int size(Map<K, V> m) {
        return (m == null) ? 0 : m.size();
    }

    public static <T> int calculateSizeForCopy(Collection<T> c) {
        return calculateSizeForCopy(size(c));
    }

    public static <K, V> int calculateSizeForCopy(Map<K, V> m) {
        return calculateSizeForCopy(size(m));
    }

    private static int calculateSizeForCopy(final int originalSize) {
        return Math.max((int) (originalSize / DIV_INT) + ADD_INT, MAX_INT);
    }

    public static int length(Object[] array) {
        return (array == null) ? 0 : array.length;
    }

    public static <K, V> boolean isEmpty(Map<K, V> m) {
        return (m == null) || m.isEmpty();
    }

    public static <K, V> boolean hasContent(Map<K, V> map) {
        return !isEmpty(map);
    }

    @SuppressWarnings("unchecked")
	public static <K, V> void putAll(Map<K, V> target, ResourceBundle source) {
        if (target != null && source != null) {
            for (Enumeration<String> e = source.getKeys(); e.hasMoreElements();) {
                String key = e.nextElement();
                V object = (V) source.getObject(key.toString());
				target.put((K) key, object);
            }
        }
    }

    public static <T> void addAll(Collection<T> target, T[] source) {
        if (target != null && source != null) {
            for (T toAdd : source) {
                target.add(toAdd);
            }
        }
    }

    @SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<T> collection, Class<T> type) {
        T[] array = (T[]) Array.newInstance(type, collection.size());
        return collection.toArray(array);
    }

    public static <K, V> Map<K, V> toMapKeys(K[] array) {
        int length = length(array);
        Map<K, V> result = CollectionFactory.createOrderedHashMap(length + MAX_INT);
        for (int i = 0; i < length; ++i) {
            result.put(array[i], null);
        }
        return result;
    }

    public static <T> Set<T> toOrderedSet(T[] array) {
        int initialCapacity = MAX_INT;
        if (array != null) {
        	initialCapacity += array.length;
        }
		Set<T> result = CollectionFactory.createOrderedHashSet(initialCapacity);
		if (null != array) {
	        for (T toAdd : array) {
	            result.add(toAdd);
	        }
		}
        return result;
    }

    public static <K, V> List<V> toListOfValues(Map<K, V> map) {
        if (map == null || map.size() < 1) {
            return new ArrayList<V>();
        }
        List<V> result = new ArrayList<V>(map.values());
        return result;
    }

    public static <K, V> List<V> ensureListExistsForKey(Map<K, List<V>> map, K key) {
    	List<V> result = map.get(key);
        if (result == null) {
            result = new ArrayList<V>();
            map.put(key, result);
        }
        return result;
    }

    public static <K, V> Set<V> ensureOrderedSetExistsForKey(Map<K, Set<V>> map, K key) {
    	Set<V> result = map.get(key);
        if (result == null) {
            result = CollectionFactory.createOrderedHashSet();
            map.put(key, result);
        }
        return result;
    }

    public static Set<String> buildStringSet(String string, String separator) {
        return buildStringSet(new TreeSet<String>(), string, separator);
    }

    public static Set<String> buildStringSet(Set<String> set, String string, String separator) {
        return buildStringSet(set, createTokenizer(string, separator));
    }

    public static Set<String> buildStringSet(Set<String> set, StringTokenizer tokenizer) {
        return addStrings(set, tokenizer);
    }

    public static List<String> buildStringList(String string, String separator) {
        return buildStringList(new ArrayList<String>(), string, separator);
    }

    public static List<String> buildStringList(List<String> list, String string,
            String separator) {
        return buildStringList(list, createTokenizer(string, separator));
    }

    public static List<String> buildStringList(List<String> list, StringTokenizer tokenizer) {
        addStrings(list, tokenizer);
        return list;
    }

    public static StringTokenizer createTokenizer(String string,
            String separator) {
        return new StringTokenizer(string, separator);
    }

    public static <T extends Collection<String>> T addStrings(T c, StringTokenizer t) {
        while (t.hasMoreElements()) {
            c.add(t.nextToken().toLowerCase());
        }
        return c;
    }

    public static boolean equals(Collection<?> first, Collection<?> second) {
        if (first == null || second == null) {
            return first == second;
        }
        return first.equals(second);
    }

    public static <T> List<T> copyToList(Enumeration<T> e) {
        List<T> result = new ArrayList<T>();
        if (e != null) {
            while (e.hasMoreElements()) {
                result.add(e.nextElement());
            }
        }
        return result;
    }

    public static <T> Iterator<T> copyToIterator(Enumeration<T> e) {
        return copyToList(e).iterator();
    }

    public static <T> int sortAndSearch(List<T> list, T o, Comparator<T> c) {
        Collections.sort(list, c);
        return Collections.binarySearch(list, o, c);
    }

    public static <T> List<T> uniqueList(Collection<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<T>();
        }
        // Give the set a little extra space to prevent resizes.
        Set<T> set = new HashSet<T>(calculateSizeForCopy(list));
        // Result list shouldn't need any extra space, but + 1
        // just in case...
        List<T> result = new ArrayList<T>(list.size() + 1);
        for (T obj : list) {
            if (set.add(obj)) {
                result.add(obj);
            }
        }
        return result;
    }

    public static <T> Iterator<T> advance(Iterator<T> iterator, int count) {
        for (int i = 0; i < count; i++) {
            iterator.next();
        }
        return iterator;
    }

    /**
     * Creates and returns a new Collection holding elements start through
     * finish, both inclusive, in the order defined by the passed Collection's
     * iterator.
     */

    public static <T> Collection<T> extractRange(final Collection<T> collection,
            final int start, final int finish) {
        final int total = finish - start + 1;
        final List<T> result = new ArrayList<T>(total);
        final Iterator<T> iterator = advance(collection.iterator(), start);
        for (int i = 0; i < total; i++) {
            result.add(iterator.next());
        }
        return result;
    }

    /**
     * Same implementation as Collections.swap() which is not available in JDK
     * 1.3.
     */

    public static <T> void swap(List<T> list, int x, int y) {
        list.set(x, list.set(y, list.get(x)));
    }

    public static <T> List<T> createList(T element) {
        List<T> result = new ArrayList<T>();
        result.add(element);
        return result;
    }

    /**
     * Creates and returns a comma delimited String of all the elements contained in
     * the List<String> object
     */
    public static String extractCommaDelimitedString(final Collection<String> list) {
       StringBuilder delimitedString = new StringBuilder();
       for (String string : list) {
           delimitedString.append(string).append(',');
       }
       if (delimitedString.length() > 0) {
           delimitedString.deleteCharAt(delimitedString.length() - 1);
       }
       return delimitedString.toString();
    }
}
