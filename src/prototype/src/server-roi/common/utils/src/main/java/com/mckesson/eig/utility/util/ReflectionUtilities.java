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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JApplet;

import com.mckesson.eig.utility.collections.CollectionFactory;

/**
 * Utilities class for wrapping some of the reflection methods available.
 */
public final class ReflectionUtilities {

    public static final Class<?>[] EMPTY_CLASSES = new Class[0];

    public static final Object[] EMPTY_OBJECTS = new Object[0];

    private static ClassNames _names = new ClassNames();

    /**
     * Construct is private because all methods are static. This keeps people
     * from creating unneeded instances of this class.
     */
    private ReflectionUtilities() {
    }

    /**
     * Intantiates an object using reflection. Class must have a public
     * no-argument constructor
     *
     * @param className
     *            fully qualified class name.
     * @return a newly instantiated Object of type className
     * @throws UtilitiesException
     *             This is thrown if an instance of this class could not be
     *             instantiated.
     */
    public static Object newInstance(String className) {
        return newInstance(getClass(className));
    }

    /**
     * Intantiates an object using reflection. Class must have a public
     * no-argument constructor
     *
     * @param className
     *            fully qualified class name to create *param defaultName fully
     *            qualified class name to create if className is null.
     * @return a newly instantiated Object of type className
     * @throws UtilitiesException
     *             This is thrown if an instance of this class could not be
     *             instantiated.
     */
    public static Object newInstance(String className, String defaultName) {
        return newInstance((className == null) ? defaultName : className);
    }

    /**
     * Instantiate a class using the no-argument constructor.
     *
     * @param c
     *            This is the class we're going to instantiate
     * @return Object a newly instantiated Object of type c.
     * @throws UtilitiesException
     *             This is thrown if an instance of this class could not be
     *             instantiated.
     */
    public static <T> T newInstance(Class<T> c) {
        try {
            return c.newInstance();
            // Catch specific exceptions so that the original exception is
            // propagated if it's un-checked.
        } catch (InstantiationException e) {
            throw new UtilitiesException(e);
        } catch (IllegalAccessException e) {
            throw new UtilitiesException(e);
        }
    }

    /**
     * attempts to find a constructor that is initialized from the Object[]
     * values
     *
     * @return a newly instantiated Object of the type of clazz
     * @param clazz
     *            class to instantiate
     * @param argumentClasses
     *            array of class types in constructor
     * @param arguments
     *            array of instances of arguments for the constructor
     */
    public static <T> T newInstance(Class<T> c, Class<?>[] classes, Object[] arguments) {
        return newInstance(getConstructor(c, classes), arguments);
    }

    /**
     * attempts to find a constructor that is initialized from the Object[]
     * values
     *
     * @param clazz
     *            class to instantiate
     * @param arguments
     *            array of instances of arguments for the constructor
     * @return a newly instantiated Object of the type of clazz
     * @throws UtilitiesException
     *             if instance cannot be instantiated
     */
    public static <T> T newInstance(Class<T> clazz, Object[] arguments) {
        Class<?>[] argumentClasses = new Class[arguments.length];

        for (int i = 0; i < arguments.length; i++) {
            argumentClasses[i] = arguments[i].getClass();
        }

        return newInstance(clazz, argumentClasses, arguments);
    }

    public static Object newInstance(String className, Object[] arguments) {
        return newInstance(getClass(className), arguments);
    }

    public static Object newInstance(String className, Class<?>[] classes, Object[] arguments) {
        return newInstance(getClass(className), classes, arguments);
    }

    public static <T> T newInstance(Constructor<T> c, Object[] objects) {
        try {
            return c.newInstance(objects);
            // Catch specific exceptions so that the original exception is
            // propagated if it's un-checked.
        } catch (InstantiationException e) {
            throw new UtilitiesException(e);
        } catch (IllegalAccessException e) {
            throw new UtilitiesException(e);
        } catch (InvocationTargetException e) {
            rethrow(e.getTargetException());
            // This return won't execute.
            return null;
        }
    }

    public static <T> Constructor<T> getConstructor(Class<T> c, Class<?>[] arguments) {
        try {
            return c.getConstructor(arguments);
        } catch (NoSuchMethodException e) {
            throw new UtilitiesException(e);
        }
    }

    public static <T> Constructor<T> getDeclaredConstructor(Class<T> c, Class<?>[] args) {
        try {
            return c.getDeclaredConstructor(args);
        } catch (NoSuchMethodException e) {
            throw new UtilitiesException(e);
        }
    }

    public static Method getDeclaredMethod(Class<?> c, String s, Class<?>[] args) {
        try {
            return c.getDeclaredMethod(s, args);
        } catch (NoSuchMethodException e) {
            throw new UtilitiesException(e);
        }
    }

    public static Method getMethod(Class<?> c, String s) {
        return getMethod(c, s, EMPTY_CLASSES);
    }

    public static Method getMethod(Class<?> c, String s, Class<?>[] args) {
        try {
            return c.getMethod(s, args);
        } catch (NoSuchMethodException e) {
            throw new UtilitiesException(e);
        }
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

    public static Constructor<?> getConstructor(String className, Class<?>[] args) {
        return getConstructor(getClass(className), args);
    }

    public static Constructor<?> getConstructor(String className) {
        return getConstructor(getClass(className));
    }

    public static <T> Constructor<T> getConstructor(Class<T> c) {
        return getConstructor(c, EMPTY_CLASSES);
    }

    public static <T> Constructor<T> getPrivateConstructor(Class<T> c, Class<?>[] args) {
        return getDeclaredConstructor(c, args);
    }

    public static Object callMethod(Class<?> c, Object o, String s, Class<?>[] classes, Object[] args) {
        Method method = getMethod(c, s, classes);
        return callMethod(method, o, args);
    }

    public static Object callMethod(Object o, String s) {
        Method method = getMethod(o.getClass(), s);
        return callMethod(method, o);
    }

    public static Object callMethod(Class<?> c, String s) {
        Method method = getMethod(c, s);
        return callMethod(method);
    }

    public static Object callMethod(Method method) {
        return callMethod(method, (Object) null, EMPTY_OBJECTS);
    }

    public static Object callMethod(Method method, Object instance) {
        return callMethod(method, instance, EMPTY_OBJECTS);
    }

	public static Object callMethod(Method method, Object instance, Object[] args) {
        try {
            return method.invoke(instance, args);
            // Catch specific exceptions so that the original exception is
            // propagated if it's un-checked.
        } catch (IllegalAccessException e) {
            throw new UtilitiesException(e);
        } catch (InvocationTargetException e) {
            rethrow(e.getTargetException());
            // This return won't execute.
            return null;
        }
    }

    private static void rethrow(Throwable t) {
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        if (t instanceof Error) {
            throw (Error) t;
        }
        throw new UtilitiesException(t);
    }

    public static Object callPrivateMethod(Class<?> c, Object o, String s) {
        return callPrivateMethod(c, o, s, EMPTY_CLASSES, EMPTY_OBJECTS);
    }

	public static Object callPrivateMethod(Class<?> c, Object o, String s,
            Class<?>[] classes, Object[] args) {

        Method method = getDeclaredMethod(c, s, classes);
        method.setAccessible(true);

        return callMethod(method, o, args);
    }

    public static <T> T callPrivateConstructor(Class<T> c) {
        return callPrivateConstructor(c, EMPTY_CLASSES, EMPTY_OBJECTS);
    }

    public static <T> T callPrivateConstructor(Class<T> c, Class<?>[] cs, Object[] args) {
        Constructor<T> constructor = getPrivateConstructor(c, cs);
        constructor.setAccessible(true);
        return newInstance(constructor, args);
    }

    public static boolean isAbstract(Class<?> c) {
        return (c.getModifiers() & Modifier.ABSTRACT) != 0;
    }

    public static boolean isNotAbstract(Class<?> c) {
        return !isAbstract(c);
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw couldNotLoadClass(name, e);
        } catch (LinkageError e) {
            throw couldNotLoadClass(name, e);
        }
    }

    private static UtilitiesException couldNotLoadClass(String name, Throwable t) {
        return new UtilitiesException("Could not load class [" + name + "]", t);
    }

    public static boolean isClassAvailable(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static Field[] getPublicStaticFinalFields(Class<?> c) {
        Field[] fields = c.getDeclaredFields();
        List<Field> list = new ArrayList<Field>();
        for (Field field : fields)
		{
            if (isPublicStaticFinal(field.getModifiers())) {
                list.add(field);
            }
        }
        return list.toArray(new Field[list.size()]);
    }

    public static boolean isPublicStaticFinal(int mask) {
        return checkMask(mask, Modifier.PUBLIC | Modifier.FINAL
                | Modifier.STATIC);
    }

    public static boolean checkMask(int mask, int expected) {
        return (mask & expected) == expected;
    }

    public static Field[] getDeclaredFields(Class<?> c) {
        return c.getDeclaredFields();
    }

    public static Object getStaticField(Field field) {
        try {
            return field.get(null);
        } catch (IllegalAccessException e) {
            throw new UtilitiesException(e);
        }
    }

    public static Method[] getDeclaredMethods(Object o) {
        return o.getClass().getDeclaredMethods();
    }

    public static Method findMethodByName(Class<?> c, String methodName) {
        Method[] ms = c.getMethods();
        for (Method element : ms)
		{
            if (element.getName().compareTo(methodName) == 0) {
                return element;
            }
        }
        return null;
    }

    public static Class<?>[] getAllTypes(Object object) {
        if (object == null) {
            return EMPTY_CLASSES;
        }
        return getAllAssignableTypes(object.getClass());
    }

	public static Class<?>[] getAllAssignableTypes(Class<?> type) {
        if (type == null) {
            return EMPTY_CLASSES;
        }
        @SuppressWarnings("unchecked")
        Collection<Class> all = CollectionFactory.createOrderedHashSet();
        while (type != null) {
            all.add(type);
            CollectionUtilities.addAll(all, type.getInterfaces());
            type = type.getSuperclass();
        }
        return CollectionUtilities.toArray(all, Class.class);
    }

    public static Object newInstance(String[] possibleClassNames) {
        if (possibleClassNames == null) {
            return null;
        }
        for (String name : possibleClassNames)
		{
            try {
                return Class.forName(name).newInstance();
            } catch (Throwable t) {
                // ignore and try next
                continue;
            }
        }
        return null;
    }

    public static synchronized Object callJavascriptFunction(JApplet applet,
            String funcName, Object[] args) {

        Class<?> c = getClass(_names.forJavaScriptCalls());

        Method getWindow = findMethodByName(c, "getWindow");
        Object jsObject = callMethod(getWindow, c, new Object[]{applet});

        if (args == null) {
            args = new Object[0];
        }

        Method call = findMethodByName(c, "call");
        return callMethod(call, jsObject, new Object[]{funcName, args});
    }

    public static synchronized ClassNames getNames() {
        return _names;
    }

    public static synchronized void setNames(ClassNames name) {
        _names = name;
    }
}
