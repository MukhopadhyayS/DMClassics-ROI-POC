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

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestBeanUtilities extends UnitTest {
    private static final int CONST_INTNUM = 5;

    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities.areAllConstructorsPrivate(BeanUtilities.class));
        ReflectionUtilities.callPrivateConstructor(BeanUtilities.class);
    }

    public void testGetProperty() {
        Object bean = new Exception("foo");
        assertEquals("foo", BeanUtilities.getProperty(bean, "message"));
    }

    public void testExtractProperties() {
        Collection<FeatureDescriptor> beans = new ArrayList<FeatureDescriptor>();

        FeatureDescriptor one = new FeatureDescriptor();
        FeatureDescriptor two = new FeatureDescriptor();
        FeatureDescriptor three = new FeatureDescriptor();

        one.setName("foo");
        two.setName("bar");
        three.setName("bar");

        beans.add(one);
        beans.add(two);
        beans.add(three);

        Serializable[] properties = BeanUtilities.extractUniquePropertyArray(
                beans, "name");

        assertEquals(2, properties.length);
        assertEquals("foo", properties[0]);
        assertEquals("bar", properties[1]);

        assertEquals(0, BeanUtilities.extractUniquePropertyArray(
                (Collection<FeatureDescriptor>) null, "foo").length);
        assertEquals(0, BeanUtilities.extractUniquePropertyArray(
                new ArrayList<FeatureDescriptor>(), "foo").length);

        assertTrue(BeanUtilities.extractPropertySet((Object[]) null, "foo").isEmpty());
        assertTrue(BeanUtilities.extractPropertySet(new Object[]{}, "foo").isEmpty());
    }

    public void testExtractPropertySetWithFilter() {
        Collection<Object> beans = new ArrayList<Object>();

        FeatureDescriptor one = new FeatureDescriptor();
        FeatureDescriptor two = new FeatureDescriptor();
        FeatureDescriptor three = new FeatureDescriptor();
        FeatureDescriptor four = new FeatureDescriptor();
        FeatureDescriptor five = new FeatureDescriptor();

        one.setName("foo");
        two.setName("bar");
        three.setName("bar");
        four.setName(" ");
        five.setName(null);

        beans.add(one);
        beans.add(two);
        beans.add(three);
        beans.add(four);
        beans.add(five);

        beans.add(new NotStringFromGetNameObject());

        Set<Object> set = BeanUtilities.extractPropertySet(beans, "name",
                BeanUtilities.STRINGSWITHCONTENTPROPERTYFILTER);

        assertEquals(2, set.size());
        Iterator<Object> i = set.iterator();
        assertEquals("foo", i.next());
        assertEquals("bar", i.next());
        assertFalse(i.hasNext());

        assertTrue(BeanUtilities.extractPropertySet(null, "name",
                BeanUtilities.STRINGSWITHCONTENTPROPERTYFILTER).isEmpty());
        assertTrue(BeanUtilities.extractPropertySet(new LinkedList<Object>(), "name",
                BeanUtilities.STRINGSWITHCONTENTPROPERTYFILTER).isEmpty());
        assertTrue(BeanUtilities.extractPropertySet(new HashSet<Object>(), "name",
                BeanUtilities.STRINGSWITHCONTENTPROPERTYFILTER).isEmpty());
    }

    public static class NotStringFromGetNameObject {
        public Integer getName() {
            return new Integer(1);
        }
    }

    public void testMapByProperty() {
        Map<Object, Set<Object>> first = BeanUtilities.mapByProperty(null, "foo");
        assertEquals(0, first.size());

        Map<Object, Set<Object>> second = BeanUtilities.mapByProperty(new ArrayList<Object>(), "foo");
        assertEquals(0, second.size());

        Exception e1 = new Exception("foo");
        Exception e2 = new Exception("foo");
        Exception e3 = new Exception("bar");

        Collection<Exception> beans = new LinkedList<Exception>();
        beans.add(e1);
        beans.add(e2);
        beans.add(e3);

        Map<Object, Set<Exception>> map = BeanUtilities.mapByProperty(beans, "message");
        assertEquals(2, map.size());

        Set<Exception> foos = map.get("foo");
        assertEquals(2, foos.size());
        Iterator<Exception> fi = foos.iterator();
        assertSame(e1, fi.next());
        assertSame(e2, fi.next());

        Set<Exception> bars = map.get("bar");
        assertEquals(1, bars.size());
        Iterator<Exception> bi = bars.iterator();
        assertSame(e3, bi.next());
    }

    public void testGetPropertyDescriptor() {
        Object bean = new Exception("foo");

        PropertyDescriptor pd = BeanUtilities.getPropertyDescriptor(bean, "message");
        assertEquals("message", pd.getName());
        assertEquals(String.class, pd.getPropertyType());

        assertNull(BeanUtilities.getPropertyDescriptor(bean, "foo"));

        try {
            Object result = BeanUtilities.getPropertyDescriptor(null, "fakeMethod");
            fail("Should have thrown UtilitiesException, returned: " + result);
        } catch (UtilitiesException e) {
            e.printStackTrace();
        }
    }

    public void testGetPropertyDescriptors() {
        Object bean = new Exception("foo");
        PropertyDescriptor[] pds = BeanUtilities.getPropertyDescriptors(bean);
        assertEquals(CONST_INTNUM, pds.length);
        Set<String> set = BeanUtilities.extractPropertySet(pds, "name");
        assertEquals(CONST_INTNUM, set.size());
        assertTrue(set.contains("cause"));
        assertTrue(set.contains("class"));
        assertTrue(set.contains("localizedMessage"));
        assertTrue(set.contains("message"));
        assertTrue(set.contains("stackTrace"));
    }

    public void testSetProperty() {
        Object bean = new FeatureDescriptor();
        BeanUtilities.setProperty(bean, "name", "foo");
        assertEquals("foo", ((FeatureDescriptor) bean).getName());
    }

    public void testSafeSetProperty() {
        Object bean = new FeatureDescriptor();
        BeanUtilities.safeSetProperty(bean, "name", "foo");
        assertEquals("foo", ((FeatureDescriptor) bean).getName());
        BeanUtilities.safeSetProperty(bean, "blahblahblah", "foo");
        BeanUtilities.safeSetProperty(null, "blahblahblah", "foo");
    }

    public void testDescribe() {
        Object bean = new Exception("foo");
        Map<String, Object> map = BeanUtilities.describe(bean);
        assertEquals(CONST_INTNUM, map.size());
        assertEquals("foo", map.get("message"));
        assertEquals(Exception.class, map.get("class"));
    }

    public void testArePropertiesEqual() {
        Object first = new FeatureDescriptor();
        Object second = new FeatureDescriptor();
        Object third = new FeatureDescriptor();

        ((FeatureDescriptor) first).setName("foo");
        ((FeatureDescriptor) second).setName("foo");
        ((FeatureDescriptor) third).setName("bar");

        assertTrue(BeanUtilities.arePropertiesEqual(first, first));
        assertTrue(BeanUtilities.arePropertiesEqual(null, null));
        assertFalse(BeanUtilities.arePropertiesEqual(null, first));
        assertFalse(BeanUtilities.arePropertiesEqual(first, null));
        assertTrue(BeanUtilities.arePropertiesEqual(first, second));
        assertFalse(BeanUtilities.arePropertiesEqual(first, third));
    }

    public void testNoSuchMethodExceptionOnGet() {
        try {
            Object none = BeanUtilities.getProperty("fakeBean", "fakeMethod");
            fail("should have thrown UtilitiesException, none = " + none);
        } catch (UtilitiesException e) {
            e.printStackTrace();
        }
    }

    public void testNoSuchMethodExceptionOnSet() {
        try {
            BeanUtilities.setProperty("fakeBean", "fakeMethod", "fakeValue");
            fail("Should have thrown UtilitiesException.");
        } catch (UtilitiesException e) {
            e.printStackTrace();
        }
    }

    public void testExceptionOnSafeSet() {
        try {
            Object bad = new Foo();
            BeanUtilities.safeSetProperty(bad, "foo", "bar");
            fail("Should have thrown UtilitiesException.");
        } catch (UtilitiesException e) {
            e.printStackTrace();
        }
    }

    public void testExceptionThrownDuringCopy() {
        try {
            BeanUtilities.copyProperties(null, new Integer(0));
            fail("Should have thrown UtiltiiesException.");
        } catch (UtilitiesException e) {
            e.printStackTrace();
        }
    }

    public void testExceptionInDescribe() {
        try {
            BeanUtilities.describe(null);
            fail();
        } catch (UtilitiesException e) {
            e.printStackTrace();
        }
    }

    public void testGetSafePropertyWithNullBean() {
        assertEquals("", BeanUtilities.getSafeProperty(null, null));
    }

    public void testGetSafeProperty() {
        Exception bean = new Exception("foo");
        assertEquals("foo", BeanUtilities.getSafeProperty(bean, "message"));
    }

    public void testGestSafeNestedProperty() {
        Exception bean = new Exception("foo", new Exception("bar"));
        assertEquals("bar", BeanUtilities.getSafeNestedProperty(bean,
                "cause.message"));
    }

    public void testGestSafeNestedPropertyWithNullProperty() {
        Exception bean = new Exception("foo");
        assertNull(BeanUtilities.getSafeNestedProperty(bean, "cause.message"));
    }

    public class Foo {
        public void setFoo(String foo) {
            throw new NullPointerException();
        }
    }
}
