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

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import com.mckesson.eig.utility.io.FileLoader;

public final class ClassUtilities {

    private ClassUtilities() {
    }

    /**
     * returns true if all constructors for the {@link Class} are private.
     */
    public static boolean areAllConstructorsPrivate(Class<?> c) {
        Constructor<?>[] constructors = c.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors)
		{
            if ((constructor.getModifiers() & Modifier.PRIVATE) == 0) {
                return false;
            }
        }
        return true;
    }

    public static String getUnqualifiedName(Class<?> type) {
        return type == null ? "null" : parseUnqualifiedClassName(type.getName());
    }

    public static String getUnqualifiedOuterClassName(Class<?> type) {
        String unqualified = getUnqualifiedName(type);
        return parseOuterClassName(unqualified);
    }

    public static String parseUnqualifiedClassName(String name) {
        if (name == null) {
            return "null";
        }
        int lastIndex = name.lastIndexOf('.');
        if (lastIndex == -1) {
            return name;
        }
        return name.substring(lastIndex + 1);
    }

    public static String parseOuterClassName(String name) {
        if (name == null) {
            return "null";
        }
        int index = name.indexOf('$');
        if (index == -1) {
            return name;
        }
        return name.substring(0, index);
    }

    /**
     * This method takes a {@link Class} and retrieves the directory that this
     * class sits in.
     *
     * @param c
     *            The class whose directory we're trying to locate.
     *
     * @return File The directory this class sits in.
     */
    public static File getDirectory(Class<?> c) {
        return FileLoader.getResourceAsFile(getPackageNameAsPath(c));
    }

    public static String getPackageNameAsDirectoryName(Class<?> c) {
        return getPackageNameAsPath(c, File.separatorChar);
    }

    public static String getPackageNameAsPath(Class<?> c) {
        return getPackageNameAsPath(c, '/');
    }

    public static String getPackageNameAsPath(Class<?> c, char separator) {
        if (c == null) {
            return null;
        }
        return getNameAsPath(c.getPackage(), separator);
    }

    public static String getNameAsPath(Package p, char separator) {
        if (p == null) {
            return null;
        }
        if (StringUtilities.isEmpty(p.getName())) {
            return "";
        }
        return p.getName().replace('.', separator);
    }

    /**
     * Returns true if this {@link Class} is in a jar file as opposed to being
     * in a directory on disk.
     */
    public static boolean isInJar(Class<?> c) {
        return getDirectory(c) == null;
    }

    /**
     * Throws an exception if the checked class is not correct
     *
     * @param o
     *            The object to check
     * @param expectedClass
     *            The class we were ecpecting
     * @throws NullPointerException
     *             if o is null
     * @throws ClassCastException
     *             if the class is wrong
     */
    public static void checkForSpecificClass(Object o, Class<?> expectedClass) {
        if (!o.getClass().equals(expectedClass)) {
            throw new ClassCastException("Expected:  " + expectedClass);
        }
    }
}
