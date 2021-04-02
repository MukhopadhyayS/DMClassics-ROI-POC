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

package com.mckesson.eig.utility.testing;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.mckesson.eig.utility.util.ClassUtilities;
import com.mckesson.eig.utility.util.ReflectionUtilities;
import com.mckesson.eig.utility.util.UtilitiesException;

public final class TestUtilities {

    private static Set<String> _excludes;

    static {
        Set<String> s = new HashSet<String>();
        s.add("java.lang.Object");
        s.add("junit.framework.TestCase");
        s.add("junit.extensions.jfcunit.JFCTestCase");
        _excludes = Collections.unmodifiableSet(s);
    }

    private TestUtilities() {
    }

    public static File getTestSourcePath(Class<?> type) {
        return getSourcePath("test", type);
    }

    public static File getJavaSourcePath(Class<?> type) {
        return getSourcePath("java", type);
    }

    private static File getSourcePath(String which, Class<?> type) {
        // There's got to be an easier / better way to do this...
        String pathToType = ClassUtilities.getDirectory(type).getPath();
        String packageOnlyPath = ClassUtilities.getPackageNameAsDirectoryName(type);
        String unqualified = ClassUtilities.getUnqualifiedOuterClassName(type);
        String almostFullPackagePath = File.separator + "src"
                + File.separator + which + File.separator + packageOnlyPath;
        String javaPath = almostFullPackagePath + File.separator
                + unqualified + ".java";
        int packageIndex = pathToType.indexOf(packageOnlyPath);
        File classesPath = new File(pathToType.substring(0, packageIndex));
        File parent = classesPath;
        do {
            File sourcePath = new File(parent.getPath() + javaPath);
            File packagePath = new File(parent.getPath() + almostFullPackagePath);
            if (sourcePath.exists() && packagePath.exists()
                    && packagePath.isDirectory()) {
                return packagePath;
            }
            parent = parent.getParentFile();
        } while (parent != null);
        return null;
    }

    public static void setAllFieldsToNull(TestCase test) {
        Class<?> c = test.getClass();
        while (shouldSetFieldsToNull(c)) {
            Field[] fields = ReflectionUtilities.getDeclaredFields(c);
            for (Field f : fields)
			{
                if (shouldSetToNull(f)) {
                    setFieldToNull(test, f);
                }
            }
            c = c.getSuperclass();
        }
    }

    private static boolean shouldSetToNull(Field f) {
        return isNameOkForSettingToNull(f) && isTypeOkForSettingToNull(f)
                && areModifiersOkForSettingToNull(f);
    }

    private static boolean areModifiersOkForSettingToNull(Field f) {
        int i = f.getModifiers();
        return (!Modifier.isStatic(i)) && (!Modifier.isFinal(i));
    }

    private static boolean isTypeOkForSettingToNull(Field f) {
        return !f.getType().isPrimitive();
    }

    private static boolean isNameOkForSettingToNull(Field f) {
        String name = f.getName();
        return (!name.startsWith("class$")) && (!name.startsWith("$"));
    }

    private static void setFieldToNull(Object object, Field f) {
        try {
            f.setAccessible(true);
            f.set(object, null);
            f.setAccessible(false);
        } catch (IllegalAccessException e) {
            throw new UtilitiesException(e);
        }
    }

    private static boolean shouldSetFieldsToNull(Class<?> c) {
        return !_excludes.contains(c.getName());
    }
}
