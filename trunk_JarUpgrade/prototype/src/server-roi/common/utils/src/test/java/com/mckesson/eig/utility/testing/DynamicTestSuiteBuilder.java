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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.mckesson.eig.utility.io.Console;
import com.mckesson.eig.utility.util.ClassUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * <p>
 * <strong>TestSuiteBuilder</strong>
 * </p>
 *
 * This class does exactly what its name says. It builds a
 * <code>junit.framework.TestSuite</code> for you. You should construct this
 * class with a class from the directory that you want to test..
 *
 * For simplicity, this class always recurses directories to add in all files to
 * the TestSuite that extend <code>junit.framework.TestCase</code>.
 *
 * A static helper class called getTests(Class) has been provided to keep your
 * code small.
 *
 * Example Usage:
 *
 * <code>
 * package com;
 *
 * import junit.framework.Test;
 * import junit.framework.TestSuite;
 *
 * public class TestAll extends TestSuite
 * {
 *     public TestAll(String string)
 *     {
 *         super(string);
 *     }
 *
 *     public static Test suite()
 *     {
 *   	   return TestSuiteBuilder.getTests(TestAll.class);
 *     }
 * }
 * </code>
 *
 * The test runner will run all tests in the com directory and and in any
 * subdirectories.
 *
 */
public class DynamicTestSuiteBuilder extends TestSuite {

    /**
     * Storage for our Tests. We'll returns this to you when you call
     * #getTests()
     */
    private final List< Class< ? extends TestCase > > _tests;

    /**
     * Package where we start loading tests from. This is used to convert from
     * absolute paths back to relative to the classpath.
     */
    private String _packageAsDirectoryName;

    /**
     * Builds a test suite based on the directory that your class resides in.
     * This class always recurses directories for simplicity. Call getTests()
     * after construction and you'll have a ready made TestSuite handed back to
     * you.
     *
     * @param c
     *            We use the directory of this class to start building our tests
     *            from.
     */
    public DynamicTestSuiteBuilder(Class< ? > c) {
        _tests = new ArrayList< Class< ? extends TestCase > >();
        setPackageAsDirectoryName(ClassUtilities.getPackageNameAsDirectoryName(c));
        handleDirectory(getDirectory(c));
    }

    /**
     * Returns the list of tests for a given directory.
     *
     * @return List The tests.
     */
    public List< Class< ? extends TestCase > > getTests() {
        return _tests;
    }

    private File getDirectory(Class< ? > c) {
        return ClassUtilities.getDirectory(c);
    }

    private void setPackageAsDirectoryName(String path) {
        _packageAsDirectoryName = path;
    }

    private void handleDirectory(File file) {
        routeFiles(file.listFiles());
    }

    private void routeFiles(File[] files) {
        if (files != null) {
            for (File file : files) {
                routeFile(file);
            }
        }
    }

    private void routeFile(File file) {
        if (file.isDirectory()) {
            handleDirectory(file);
        } else {
            handleFile(file);
        }
    }

    private void handleFile(File file) {
        String className = getClassName(file.getPath());
        if (className == null) {
            return;
        }
        addToTestSuite(className);
    }

    private String getClassName(String absolutePath) {
        String relative = StringUtilities.extractStringStartingWith(
                absolutePath, _packageAsDirectoryName);

        String innerClass = StringUtilities.stripFromEnd(relative, "$");
        if (innerClass != null) {
            return null;
        }

        relative = StringUtilities.stripFromEnd(relative, ".class");

        // check to see if item wasn't a .class file
        if (relative == null) {
            return null;
        }

        // item was a class file so convert file separators
        // to period.
        return StringUtilities.replace(relative, File.separator, ".");
    }

    private void addToTestSuite(String name) {
        try {
            addToTestSuite(Class.forName(name));
        } catch (ClassNotFoundException e) {
            Console.println(e);
        }
    }

    private boolean isTestCase(Class< ? > c) {
        return TestCase.class.isAssignableFrom(c) && isNotAbstract(c);
    }

    @SuppressWarnings("unchecked")
	private void addToTestSuite(Class< ? > c) {
        if (isTestCase(c)) {
            _tests.add((Class< ? extends TestCase >) c);
        }
    }

    private boolean isAbstract(Class< ? > c) {
        return (c.getModifiers() & Modifier.ABSTRACT) != 0;
    }

    private boolean isNotAbstract(Class< ? > c) {
        return !isAbstract(c);
    }
}
