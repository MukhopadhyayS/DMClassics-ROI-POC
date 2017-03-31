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

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

import javax.swing.JApplet;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.utility.util.data.MockData;

public class TestReflectionUtilities extends UnitTest {

    private static final String INVALID_STRING_CLASS = "java.util.String";
    private static final String VALID_STRING_CLASS = "java.lang.String";

    private static final String EMPTY_STRING = "";
    private static final String TEST_STRING = "This is a Test.";
    private final Class<?>[] _classes = {String.class};
    private final Object[] _objects = {TEST_STRING};

    public TestReflectionUtilities() {
    }

    public static Test suite() {
        return new CoverageSuite(TestReflectionUtilities.class, ReflectionUtilities.class);
    }

    public void testNewInstanceFail() {
        try {
            ReflectionUtilities.newInstance(Collection.class);
            fail("Should have thrown UtilitiesException");
        } catch (UtilitiesException e) {
            assertTrue(true);
        }
    }

    public void testConstructor() {
        Object o = ReflectionUtilities.callPrivateConstructor(ReflectionUtilities.class);
        assertNotNull("o should not be null", o);
    }

    public void testCallPrivateConstructor() {
        Object o = ReflectionUtilities.callPrivateConstructor(MockData.class);
        assertNotNull("o should not be null", o);
    }

    public void testCallPrivateConstructorWithArgs() {
        Class<?>[] classes = {String.class};
        Object[] args = {"Test"};
        Object o = ReflectionUtilities.callPrivateConstructor(MockData.class, classes, args);
        assertNotNull("o should not be null", o);
    }

    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(ReflectionUtilities.class));
    }
    public void testIsNotAbstract() {
        assertEquals(true, ReflectionUtilities.isNotAbstract(String.class));
        assertEquals(false, ReflectionUtilities.isNotAbstract(AbstractList.class));
    }

    public void testIsAbstract() {
        assertEquals(true, ReflectionUtilities.isAbstract(AbstractList.class));
    }

    public void testNewInstance() {
        String s = (String) ReflectionUtilities.newInstance(VALID_STRING_CLASS);
        testString(EMPTY_STRING, s);

        s = (String) ReflectionUtilities.newInstance(null, VALID_STRING_CLASS);
        testString(EMPTY_STRING, s);

        s = (String) ReflectionUtilities.newInstance(VALID_STRING_CLASS, VALID_STRING_CLASS);
        testString(EMPTY_STRING, s);
    }

    public void testNewInstanceClass() {
        String s = ReflectionUtilities.newInstance(String.class);
        testString(EMPTY_STRING, s);
    }

    public void testNewInstanceClassAndArguments() {
        String s = ReflectionUtilities.newInstance(String.class,
                _objects);
        testString(TEST_STRING, s);
    }

    public void testNewInstanceStringAndArguments() {
        String s = (String) ReflectionUtilities.newInstance(VALID_STRING_CLASS, _objects);
        testString(TEST_STRING, s);
    }

    public void testNewInstanceClassClassesAndObjects() {
        String s = ReflectionUtilities.newInstance(String.class, _classes, _objects);
        testString(TEST_STRING, s);
    }

    public void testNewInstanceStringClassesAndObjects() {
        String s = (String) ReflectionUtilities.newInstance(VALID_STRING_CLASS,
                _classes, _objects);
        testString(TEST_STRING, s);
    }

    public void testNewInstanceInvalidClass() {
        try {
            ReflectionUtilities.newInstance(INVALID_STRING_CLASS);
            fail("UtilitiesException should have been thrown");
        } catch (UtilitiesException e) {
            assertTrue(true);
        }
    }

    public void testNewInstanceInvalidArguments() {
        try {
            Object[] objects = {"First", "Second"};
            ReflectionUtilities.newInstance(VALID_STRING_CLASS, objects);
            fail("UtilitiesException should have been thrown");
        } catch (UtilitiesException e) {
            assertTrue(true);
        }
    }

    public void testNewInstanceInvalidObjects() {
        try {
            Object[] objects = {"First", "Second"};
            ReflectionUtilities.newInstance(VALID_STRING_CLASS, _classes, objects);
            fail("IllegalArguementException should have been thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public void testString(String expected, String received) {
        assertNotNull("String should have been created", received);
        assertEquals(expected, received);
    }

    public void testGetDefaultConstructor() {
        Constructor<String> constructor = ReflectionUtilities.getConstructor(String.class);
        assertNotNull("Constructor should have been created", constructor);

        String s = ReflectionUtilities.newInstance(constructor, ReflectionUtilities.EMPTY_OBJECTS);
        assertEquals("", s);
    }

    public void testGetConstructorViaClass() {
        Constructor<String> constructor = ReflectionUtilities.getConstructor(String.class, _classes);
        assertNotNull("Constructor should have been created", constructor);

        String s = ReflectionUtilities.newInstance(constructor, _objects);
        assertEquals(TEST_STRING, s);
    }

    public void testGetConstructorViaString() {
        Constructor<?> constructor = ReflectionUtilities.getConstructor(VALID_STRING_CLASS, _classes);
        assertNotNull("Constructor should have been created", constructor);

        String s = (String) ReflectionUtilities.newInstance(constructor, _objects);
        assertEquals(TEST_STRING, s);
    }

    public void testGetClassName() {
        ReflectionUtilities.getConstructor(String.class, ReflectionUtilities.EMPTY_CLASSES);
    }

    public void testGetDeclaredMethod() {
        Method method = ReflectionUtilities.getDeclaredMethod(MockData.class,
                "getTestString", ReflectionUtilities.EMPTY_CLASSES);
        method.setAccessible(true);

        try {
            Object o = ReflectionUtilities.callPrivateConstructor(MockData.class);
            assertEquals(MockData.TEST_STRING, (String) method.invoke(o, ReflectionUtilities.EMPTY_OBJECTS));
        } catch (Throwable t) {
            t.printStackTrace();
            fail(t.getMessage());
        }
    }

    public void testGetMethod() {
        Method method = ReflectionUtilities.getMethod(String.class, "length", ReflectionUtilities.EMPTY_CLASSES);

        try {
            String o = TEST_STRING;
            assertEquals(TEST_STRING.length(), ((Integer) method.invoke(o)).intValue());
        } catch (Throwable t) {
            t.printStackTrace();
            fail(t.getMessage());
        }
    }

    public void testGetDeclaredMethodWithStaticMethod() {
        Method method = ReflectionUtilities.getDeclaredMethod(MockData.class,
                "getStaticString", ReflectionUtilities.EMPTY_CLASSES);
        method.setAccessible(true);

        try {
            assertEquals(MockData.TEST_STRING, (String) method.invoke(null, ReflectionUtilities.EMPTY_OBJECTS));
        } catch (Throwable t) {
            t.printStackTrace();
            fail(t.getMessage());
        }
    }

    public void testCallPrivateMethodNoArgs() {
        try {
        	MockData o = ReflectionUtilities.callPrivateConstructor(MockData.class);
            String s = (String) ReflectionUtilities.callPrivateMethod(
                MockData.class, o, "getTestString");
            assertEquals(MockData.TEST_STRING, s);
        } catch (Throwable t) {
            t.printStackTrace();
            fail(t.getMessage());
        }
    }

    public void testCallPrivateMethod() {
        try {
        	MockData o = ReflectionUtilities.callPrivateConstructor(MockData.class);
            String s = (String) ReflectionUtilities.callPrivateMethod(
                MockData.class, o, "getSameString", _classes, _objects);
            assertEquals(TEST_STRING, s);
        } catch (Throwable t) {
            t.printStackTrace();
            fail(t.getMessage());
        }
    }

    public void testCallMethod() {
        String o = TEST_STRING;
        Integer s = (Integer) ReflectionUtilities.callMethod(String.class, o,
                "length", ReflectionUtilities.EMPTY_CLASSES, ReflectionUtilities.EMPTY_OBJECTS);
        assertEquals(TEST_STRING.length(), s.intValue());
    }

    public static String aStaticMethodWithNoParameters() {
        return "foo";
    }

    public void testCallStaticMethodWithNoParameters() {
        assertEquals("foo", ReflectionUtilities.callMethod(
                TestReflectionUtilities.class, "aStaticMethodWithNoParameters"));
    }

    public String anInstanceMethodWithNoParameters() {
        return "1234";
    }

    public void testCallMethodWithNoParameters() {
        assertEquals("1234", ReflectionUtilities.callMethod(this,
                "anInstanceMethodWithNoParameters"));
    }

    public void testCallMethodOnMethodThatDoesNotExist() {
        try {
            Object o = TEST_STRING;
            Method method = ReflectionUtilities.getMethod(String.class, "length", ReflectionUtilities.EMPTY_CLASSES);
            ReflectionUtilities.callMethod(method, o, new Object[]{"hello", "oops"});
            fail();
        } catch (Throwable t) {
            assertTrue(true);
        }
    }

    public void testGetClassInvalid() {
        try {
            ReflectionUtilities.getClass(INVALID_STRING_CLASS);
            fail("UtilitiesException should have been thrown");
        } catch (UtilitiesException e) {
            assertTrue(true);
        }
    }

    public void testGetClassValid() {
        Class<?> c = ReflectionUtilities.getClass(VALID_STRING_CLASS);
        assertEquals(c, String.class);
    }

    public void testIsClassAvailable() {
        assertTrue(ReflectionUtilities.isClassAvailable("java.lang.String"));
        assertFalse(ReflectionUtilities.isClassAvailable("foo.Bar"));
    }

    public void testTryCatchOfGetDeclaredConstructor() {
        try {
            Constructor<String> constructor =
            	ReflectionUtilities.getDeclaredConstructor(String.class, new Class[2]);
            fail("Should have thrown UtilitiesException." + constructor);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    public void testTryCatchOfGetDeclaredMethod() {
        try {
            Method method = ReflectionUtilities.getDeclaredMethod(
                MockData.class, "getFakeMethod", ReflectionUtilities.EMPTY_CLASSES);
            fail("Should have thrown UtilitiesException." + method);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    public void testTryCatchOfGetMethod() {
        try {
            Method method = ReflectionUtilities.getMethod(
                MockData.class, "getFakeMethod", ReflectionUtilities.EMPTY_CLASSES);
            fail("Should have thrown UtilitiesException." + method);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    public void testTryCatchOfCallMethod() {
        try {
            String o = TEST_STRING;
            Integer s = (Integer) ReflectionUtilities.callMethod(
                String.class, o, "fakeMethod", ReflectionUtilities.EMPTY_CLASSES,
                ReflectionUtilities.EMPTY_OBJECTS);
            fail("Should have thrown UtilitiesException." + s);
        } catch (Throwable t) {
            assertTrue(true);
        }
    }

    public void testTryCatchOfCallPrivateMethod() {
        try {
        	MockData o = ReflectionUtilities.callPrivateConstructor(MockData.class);
            String s = (String) ReflectionUtilities.callPrivateMethod(
                MockData.class, o, "fakeMethod", _classes, _objects);
            fail("Should have thrown UtilitiesException." + s);
        } catch (Throwable t) {
            assertTrue(true);
        }
    }

    public void testTryCatchOfCallPrivateConstructor() {
        try {
            String o = ReflectionUtilities.callPrivateConstructor(
                String.class, new Class[2], ReflectionUtilities.EMPTY_OBJECTS);
            fail("Should have thrown UtilitiesException." + o);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    public void testGetMethods() {
        assertEquals(TWO, ReflectionUtilities.getMethods(String.class, "compareTo").length);
        assertEquals(ONE, ReflectionUtilities.getMethods(String.class, "concat").length);
        assertEquals(ZERO, ReflectionUtilities.getMethods(String.class, "foo").length);
        assertEquals(FOUR, ReflectionUtilities.getMethods(String.class, "indexOf").length);
    }

    public void testGetMethodsWhenMispelled() {
        assertEquals(ZERO, ReflectionUtilities.getMethods(String.class, "indexof").length);
    }

    public void testGetDeclaredFields() {
        Field[] fields = ReflectionUtilities.getDeclaredFields(TestClass.class);
        assertEquals(THREE, fields.length);
    }

    public void testGetPublicStaticFinalFields() {
        assertEquals(ONE, ReflectionUtilities.getPublicStaticFinalFields(TestClass.class).length);
    }

    public void testIsStaticFinal() {
        assertFalse(ReflectionUtilities.isPublicStaticFinal(Modifier.FINAL));
        assertFalse(ReflectionUtilities.isPublicStaticFinal(Modifier.STATIC));
        assertFalse(ReflectionUtilities.isPublicStaticFinal(Modifier.PUBLIC));
        assertFalse(ReflectionUtilities.isPublicStaticFinal(ZERO));
        assertTrue(ReflectionUtilities.isPublicStaticFinal(Modifier.PUBLIC
                | Modifier.STATIC | Modifier.FINAL));
    }

    public static Method[] getMethods(Class<?> c, String s) {
        List<Method> list = new ArrayList<Method>();

        Method[] methods = c.getMethods();
        for (Method method : methods)
		{
            if (StringUtilities.equals(method.getName(), s)) {
                list.add(method);
            }
        }

        return list.toArray(new Method[list.size()]);
    }

    public void testGetStaticField() {
        Field[] field = ReflectionUtilities.getPublicStaticFinalFields(TestClass.class);
        assertEquals(ONE, field.length);
        assertEquals("bar", ReflectionUtilities.getStaticField(field[ZERO]));
    }

    public void testGetStaticFieldOnClassWithException() {
        try {
            Field[] field = ReflectionUtilities.getPublicStaticFinalFields(TestClass.class);
            assertEquals(ONE, field.length);
            assertEquals("bar", ReflectionUtilities.getStaticField(field[ZERO]));
        } catch (UtilitiesException e) {
            assertTrue(true);
        }
    }

    public void testGetDeclaredMethods() {
        Method[] methods = ReflectionUtilities.getDeclaredMethods(new TestClass());
        assertEquals(ONE, methods.length);
        assertEquals("getFoo", methods[ZERO].getName());
    }

    public void testFindMethodByName() {
        assertNotNull(ReflectionUtilities.findMethodByName(String.class, "toString"));
        assertNull(ReflectionUtilities.findMethodByName(String.class, "foo"));
    }

    public void testNewInstanceWithInstantiationException() {
        try {
            ReflectionUtilities.newInstance(MockAbstractClass.class, new Object[]{"foo"});
            fail();
        } catch (UtilitiesException e) {
            assertTrue(true);
        }
    }

    @SuppressWarnings("unchecked")
	public void testNewInstanceWithIllegalAccessException() {
        try {
            ReflectionUtilities.newInstance(Class.class);
            fail();
        } catch (UtilitiesException e) {
            assertTrue(true);
        }

        Constructor<Class> c = ReflectionUtilities.getPrivateConstructor(Class.class,
                new Class[]{});
        try {
            ReflectionUtilities.newInstance(c, new Object[]{});
            fail();
        } catch (UtilitiesException e) {
            assertTrue(true);
        }
    }

    public void testCallMethodWithIllegalAccessException() {
        TestClassWithPrivateMethod o = new TestClassWithPrivateMethod();
        Method m = ReflectionUtilities.getDeclaredMethod(
                TestClassWithPrivateMethod.class, "test", new Class[]{});
        try {
            ReflectionUtilities.callMethod(m, o);
            fail();
        } catch (UtilitiesException e) {
            assertTrue(true);
        }
    }

    public void testCallMethodWithInvocationTargetException() {
        try {
            ReflectionUtilities.callMethod(MockTestClass.class,
                    "callInvocationTargetExceptionForError");
            fail();
        } catch (Error e) {
            assertTrue(true);
        }

        try {
            ReflectionUtilities.callMethod(MockTestClass.class,
                    "callInvocationTargetExceptionForRuntimeException");
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        try {
            ReflectionUtilities.callMethod(MockTestClass.class,
                    "callInvocationTargetExceptionForThrowable");
            fail();
        } catch (UtilitiesException e) {
            assertTrue(true);
        }
    }

    public void testGetAllTypes() {
        assertEquals(ZERO, ReflectionUtilities.getAllTypes(null).length);

        Class<?>[] test = ReflectionUtilities.getAllTypes(this);
        assertEquals(SIX, test.length);
        assertEquals(TestReflectionUtilities.class, test[ZERO]);
        assertEquals(UnitTest.class, test[ONE]);
        assertEquals(TestCase.class, test[TWO]);
        assertEquals(Test.class, test[THREE]);
        assertEquals(Assert.class, test[FOUR]);
        assertEquals(Object.class, test[FIVE]);
    }

    public void testGetAllAssignableTypes() {
        assertEquals(ZERO, ReflectionUtilities.getAllAssignableTypes(null).length);

        Class<?>[] serializable = ReflectionUtilities.getAllAssignableTypes(Serializable.class);
        assertEquals(ONE, serializable.length);
        assertEquals(Serializable.class, serializable[ZERO]);

        Class<?>[] list = ReflectionUtilities.getAllAssignableTypes(ArrayList.class);
        assertEquals(NINE, list.length);
        assertEquals(ArrayList.class, list[ZERO]);
        assertEquals(List.class, list[ONE]);
        assertEquals(RandomAccess.class, list[TWO]);
        assertEquals(Cloneable.class, list[THREE]);
        assertEquals(Serializable.class, list[FOUR]);
        assertEquals(AbstractList.class, list[FIVE]);
        assertEquals(AbstractCollection.class, list[SIX]);
        assertEquals(Collection.class, list[SEVEN]);
        assertEquals(Object.class, list[EIGHT]);
    }

    public void testCallJavascriptFunction() {
        ClassNames names = new MockClassNames();
        ReflectionUtilities.setNames(names);
        assertEquals(names, ReflectionUtilities.getNames());

        Object first = ReflectionUtilities.callJavascriptFunction(
                new JApplet(), "foo", new Object[]{"bar"});

        assertEquals(first, "baz");

        Object second = ReflectionUtilities.callJavascriptFunction(
                new JApplet(), "foo", null);

        assertEquals(second, "baz");
    }

    public static class TestClassWithPrivateMethod {
        private void test() {
        }
        public void test1() {
            test();
        }
    }

    public static class TestClass {
        private final String _foo = "foo";
        public static final String BAR = "bar";
        protected static final String BAR2 = "bar2";

        public String getFoo() {
            return _foo;
        }

    }

    public static class MockJSObject {
        public static MockJSObject getWindow(JApplet applet) {
            return new MockJSObject();
        }

        public Object call(String function, Object[] args) {
            assertEquals(function, "foo");
            assertTrue((args.length == ZERO) || (args.length == ONE));
            if (args.length > ZERO) {
                assertEquals(args[ZERO], "bar");
            }
            return "baz";
        }
    }

    public static class MockClassNames extends ClassNames {
        @Override
		protected String forJavaScriptCalls() {
            return "com.mckesson.eig.utility.util.TestReflectionUtilities$MockJSObject";
        }
    }

    public abstract static class MockAbstractClass {
        public MockAbstractClass(String s) {
        }
    }

    public static final class MockTestClass {
        private MockTestClass() throws Exception {
            throw new NullPointerException();
        }

        public static void callInvocationTargetExceptionForError() {
            Error e = new Error();
            throw e;
        }

        public static void callInvocationTargetExceptionForRuntimeException() {
            RuntimeException e = new RuntimeException();
            throw e;
        }

        public static void callInvocationTargetExceptionForThrowable()
                throws Throwable {
            Throwable t = new Throwable();
            throw t;
        }
    }
}
