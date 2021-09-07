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

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

import com.mckesson.eig.utility.collections.CollectionFactory;

public final class BeanUtilities {

    public static final PropertyFilter NOTNULLPROPERTYFILTER = new NotNullPropertyFilter();

    public static final PropertyFilter STRINGSWITHCONTENTPROPERTYFILTER =
                  new StringsWithContentPropertyFilter();

    private BeanUtilities() {
    }

    public static Object getSafeProperty(Object bean, String property) {
        return getSafeNestedProperty(bean, property);
    }

    public static Object getProperty(Object bean, String property) {
        return getNestedProperty(bean, property);
    }

    public static Object getSafeNestedProperty(Object bean, String property) {
        return (bean == null) ? "" : getNestedProperty(bean, property);
    }

    /**
     * For nested properties, check each parent property for null before going
     * to the child property. Using a Hashtable for synchronization.
     */

    private static final Map<String, List<BeanProperty>> PROPERTYMAP =
    	new Hashtable<String, List<BeanProperty>>();

    private static Object getNestedProperty(Object bean, String property) {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        }
        try {
            String key = bean.getClass().getName() + PropertyUtils.NESTED_DELIM
                    + property;
            List<BeanProperty> list = PROPERTYMAP.get(key);
            if (list == null) {
                return buildExecuteMethodCache(key, bean, property);
            }
            return executeMethodCache(bean, list);
        } catch (Throwable t) {
            throw new UtilitiesException(t);
        }
    }

    private static Object executeMethodCache(Object bean, List<BeanProperty> list)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int i = 0;
        do {
            BeanProperty property = list.get(i);
            bean = property.read(bean);
            i++;
        } while (bean != null && i < list.size());
        return bean;
    }

    private static Object buildExecuteMethodCache(String key, Object bean, String property)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<BeanProperty> list = new ArrayList<BeanProperty>();
        StringTokenizer tokenizer = new StringTokenizer(property, PropertyUtils.NESTED_DELIM + "");
        do {
            BeanProperty beanProperty = getBeanProperty(bean, tokenizer.nextToken());
            list.add(beanProperty);
            bean = beanProperty.read(bean);
        } while (bean != null && tokenizer.hasMoreTokens());

        if (bean != null || !tokenizer.hasMoreTokens()) {
            PROPERTYMAP.put(key, Collections.unmodifiableList(list));
        }
        return bean;
    }

    private static BeanProperty getBeanProperty(Object bean, String name)
            throws NoSuchMethodException {

        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        }
        if (name == null) {
            throw new IllegalArgumentException("No name specified");
        }

        // Handle DynaBean instances specially
        if (bean instanceof DynaBean) {
            DynaProperty descriptor = ((DynaBean) bean).getDynaClass().getDynaProperty(name);
            if (descriptor == null) {
                throw new NoSuchMethodException("Unknown property '" + name + "'");
            }
            return new DynaBeanProperty(name);
        }

        // Retrieve the property getter method for the specified property
        PropertyDescriptor descriptor = getPropertyDescriptor(bean, name);
        if (descriptor == null) {
            throw new NoSuchMethodException("Unknown property '" + name + "'");
        }
        Method readMethod = PropertyUtils.getReadMethod(descriptor);
        if (readMethod == null) {
            throw new NoSuchMethodException("Property '" + name + "' has no getter method");
        }

        return new NormalBeanProperty(readMethod);
    }

    /**
     * Creates a Map of [property values] -> [Set of beans]. In other words, the
     * beans are grouped together in a Set by the value of the specified
     * property. The beans are added to the map and set in the same order as
     * they are found in the collection (an ordered map and set are used, so
     * ordering is preserved).
     */
    public static <T> Map<Object, Set<T>> mapByProperty(Collection<T> beans, String propertyName) {
        Map<Object, Set<T>> map = CollectionFactory.createOrderedHashMap();
        if (beans != null) {
            for (T bean : beans)
			{
                Object key = getProperty(bean, propertyName);
                Set<T> set = CollectionUtilities.ensureOrderedSetExistsForKey(map, key);
                set.add(bean);
            }
        }
        return map;
    }

    public static <B, V> Set<V> extractPropertySet(Collection<B> beans, String propertyName) {
        return extractPropertySet(beans, propertyName, NOTNULLPROPERTYFILTER);
    }

    public static <B, V> Set<V> extractPropertySet(B[] beans, String propertyName) {
        Set<V> set = CollectionFactory.createOrderedHashSet();
        if (beans != null) {
            for (B bean : beans) {
            	@SuppressWarnings("unchecked")
                V value = (V) getProperty(bean, propertyName);
                if (value != null) {
                    set.add(value);
                }
            }
        }
        return set;
    }

    public static <B, V> Set<V> extractPropertySet(final Collection<B> beans,
            final String propertyName, final PropertyFilter filter) {
        final int size = CollectionUtilities.calculateSizeForCopy(beans);
        final Set<V> set = CollectionFactory.createOrderedHashSet(size);
        if (beans != null) {
            for (B bean : beans) {
            	@SuppressWarnings("unchecked")
                final V value = (V) getProperty(bean, propertyName);
                if (filter.keep(bean, propertyName, value)) {
                    set.add(value);
                }
            }
        }
        return set;
    }

    public static <B> Serializable[] extractUniquePropertyArray(Collection<B> beans,
         String propertyName) {
        Set<Serializable> set = extractPropertySet(beans, propertyName);
        return CollectionUtilities.toArray(set, Serializable.class);
    }

    public static <B, V> void setProperty(B bean, String property, V value) {
        try {
            PropertyUtils.setNestedProperty(bean, property, value);
        } catch (Throwable t) {
            throw new UtilitiesException(t);
        }
    }

    public static <B, V> void safeSetProperty(B bean, String property, V value) {
        if (bean == null) {
            return;
        }
        try {
            BeanUtils.setProperty(bean, property, value);
        } catch (Throwable t) {
            throw new UtilitiesException(t);
        }
    }

    public static void copyProperties(Object from, Object to) {
        try {
            BeanUtils.copyProperties(to, from);
        } catch (Throwable t) {
            throw new UtilitiesException(t);
        }
    }

    public static boolean arePropertiesEqual(Object first, Object second) {
        if (first == second) {
            return true;
        }
        if ((first == null) || (second == null)) {
            return false;
        }
        return describe(first).equals(describe(second));
    }

    public static PropertyDescriptor getPropertyDescriptor(Object bean, String propertyName) {
        try {
            return PropertyUtils.getPropertyDescriptor(bean, propertyName);
        } catch (Throwable t) {
            throw new UtilitiesException(t);
        }
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Object bean) {
        return PropertyUtils.getPropertyDescriptors(bean);
    }

    /**
     * Puts all values returned from bean getFoo() methods in the result map,
     * with the key being the property name (foo). Careful, this method will get
     * in an infinite loop if there are circular references through get()
     * methods (as is typical in the models).
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> describe(Object bean) {
        try {
            return PropertyUtils.describe(bean);
        } catch (Throwable t) {
            throw new UtilitiesException(t);
        }
    }
}
