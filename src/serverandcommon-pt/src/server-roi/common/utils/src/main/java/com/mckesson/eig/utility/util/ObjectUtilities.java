/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.util;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class ObjectUtilities {

    private static final Map<String, Map<String, String>> ASSOCIATIONS = new HashMap<String, Map<String, String>>();

    private static final Map<String, Map<String, String>> PLURAL_ASSOCIATIONS = new HashMap<String, Map<String, String>>();

    private ObjectUtilities() {
    }

    public static boolean equals(Object x, Object y) {
        if (x == null || y == null) {
            return x == y;
        }
        return x.equals(y);
    }

    public static int hashCode(Object x) {
        return (x == null) ? 0 : x.hashCode();
    }

    public static <T extends Comparable<T>> int compare(T x, T y) {
        if ((x == null) && (y == null)) {
            return 0;
        }
        if (x == null) {
            return 1;
        }
        if (y == null) {
            return -1;
        }
        return x.compareTo(y);
    }

    public static boolean areClassesEqual(Object x, Object y) {
        if (x == null || y == null) {
            return false;
        }
        return x.getClass().equals(y.getClass());
    }

    public static void verifyNotNull(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
    }

    /**
     * @param object
     * @return
     */
    public static String getUnqualifiedClassName(Object object) {
        return object == null ? "null" : getUnqualifiedName(object.getClass());
    }

    public static String getUnqualifiedName(Class<?> type) {
        return ClassUtilities.getUnqualifiedName(type);
    }

    public static String getClassName(Object object) {
        return object == null ? "null" : getName(object.getClass());
    }

    public static String getName(Class<?> type) {
        return type == null ? "null" : type.getName();
    }

    public static String parseUnqualifiedClassName(String name) {
        return ClassUtilities.parseUnqualifiedClassName(name);
    }

    public static String toString(Object object) {
        return object == null ? "" : object.toString();
    }

    /**
     * A null-safe implementation of isAssignableFrom().
     *
     * @see java.lang.Class#isAssignableFrom(Class)
     * @param c
     *            Class used on the left-hand-side of the assignment check
     * @param o
     *            Object used on the right-hand-side of the assignment check
     * @return true if object is not null and 'assignable' to the passed in
     *         class.
     */
    public static boolean isAssignable(Class<?> c, Object o) {
        return (o == null) ? false : c.isAssignableFrom(o.getClass());
    }

    public static boolean isEmptyAsString(Object o) {
        return (o == null) || StringUtilities.isEmpty(o.toString());
    }

    public static synchronized <O, T> String getAssociationName(O from, T to) {
        return resolveAssociationProperty(from, to, false, ASSOCIATIONS);
    }

    public static synchronized String getPluralAssociationName(Object from, Object to) {
        return resolveAssociationProperty(from, to, true, PLURAL_ASSOCIATIONS);
    }

    private static <O, T> String resolveAssociationProperty(O from, T to,
            boolean canBePlural, Map<String, Map<String, String>> cache) {
        if (from == null || to == null) {
            return null;
        }
        String fromKey = from.getClass().getName();
        String toKey = to.getClass().getName();
        if (isAssociationInCache(cache, fromKey, toKey)) {
            return getAssociationFromCache(cache, fromKey, toKey);
        }
        String result = resolveAssociationProperty(from, to, canBePlural);
        putAssociationInCache(cache, fromKey, toKey, result);
        return result;
    }

    private static boolean isAssociationInCache(Map<String, Map<String, String>> cache, String from,
            String to) {
        Map<String, String> nested = cache.get(from);
        if (nested == null) {
            return false;
        }
        return nested.containsKey(to);
    }

    protected static String getAssociationFromCache(Map<String, Map<String, String>> cache, String from,
            String to) {
        Map<String, String> nested = cache.get(from);
        if (nested == null) {
            return null;
        }
        return nested.get(to);
    }

    protected static void putAssociationInCache(Map<String, Map<String, String>> cache, String from,
            String to, String name) {
        Map<String, String> nested = cache.get(from);
        if (nested == null) {
            nested = new HashMap<String, String>();
            cache.put(from, nested);
        }
        nested.put(to, name);
    }

    private static String resolveAssociationProperty(Object from, Object to,
            boolean canBePlural) {
        Class<?>[] types = ReflectionUtilities.getAllTypes(to);
        for (int i = 0; i < types.length; ++i) {
            String name = getUnqualifiedName(types[i]);
            String result = null;
            if (canBePlural) {
                result = resolveAssociationProperty(from, Collection.class,
                        name, canBePlural);
            } else {
                result = resolveAssociationProperty(from, types[i], name,
                        canBePlural);
            }
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static String resolveAssociationProperty(Object from, Class<?> type,
            String name, boolean canBePlural) {
        name = StringUtilities.decapitalize(name);
        if (isAssociationMatch(from, type, name, canBePlural)) {
            return name;
        }
        if (canBePlural) {
            String plural = StringUtilities.plural(name);
            if (isAssociationMatch(from, type, plural, canBePlural)) {
                return plural;
            }
        }
        for (int i = 0; i < name.length(); ++i) {
            if (Character.isUpperCase(name.charAt(i))) {
                return resolveAssociationProperty(from, type,
                        name.substring(i), canBePlural);
            }
        }
        return null;
    }

    private static boolean isAssociationMatch(Object from, Class<?> type,
            String name, boolean canBePlural) {
        PropertyDescriptor pd = getPropertyDescriptor(from, name);
        if (pd == null) {
            return false;
        }
        if (canBePlural) {
            return type.isAssignableFrom(pd.getPropertyType());
        }
        return pd.getPropertyType().isAssignableFrom(type);
    }

    private static PropertyDescriptor getPropertyDescriptor(Object bean,
            String propertyName) {
        // Not using BeanUtilities.getPropertyDescriptor() directly, because
        // it can cause a security exception in an applet when a property
        // is not found (due to a getDeclaredMethods() call).
        PropertyDescriptor[] descriptors = BeanUtilities.getPropertyDescriptors(bean);
        if (descriptors != null) {
            for (PropertyDescriptor descriptor : descriptors) {
                if (propertyName.equals(descriptor.getName())) {
                    return descriptor;
                }
            }
        }
        return null;
    }
}
