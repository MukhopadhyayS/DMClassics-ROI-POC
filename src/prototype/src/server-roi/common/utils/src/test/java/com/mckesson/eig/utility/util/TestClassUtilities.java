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
import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestClassUtilities extends UnitTest {

    public void testConstructor() {
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(ClassUtilities.class));
    }

    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(ClassUtilities.class));
        assertFalse(ClassUtilities.areAllConstructorsPrivate(String.class));
    }

    public void testParseOuterClassName() {
        assertEquals(TestClassUtilities.class.getName(), ClassUtilities
                .parseOuterClassName(SomeInnerClass.class.getName()));
    }

    public void testGetUnqualifiedOuterClassName() {
        assertEquals(ClassUtilities
                .getUnqualifiedName(TestClassUtilities.class), ClassUtilities
                .getUnqualifiedOuterClassName(SomeInnerClass.class));
    }

    public class SomeInnerClass {
    }

    public void testGetDirectory() {
        File file = ClassUtilities.getDirectory(ClassUtilities.class);
        assertNotNull(file);
        assertTrue(file.isDirectory());

        assertNull(ClassUtilities.getDirectory(String.class));
    }

    public void testGetDirectoryName() {
        String directory = ClassUtilities.getPackageNameAsDirectoryName(ClassUtilities.class);
        assertEquals("com" + File.separatorChar + "mckesson"
                + File.separatorChar + "eig" + File.separatorChar + "utility"
                + File.separatorChar + "util", directory);
    }

    public void testIsInJar() {
        assertTrue(ClassUtilities.isInJar(String.class));
    }

    public void testIsNotInJar() {
        assertFalse(ClassUtilities.isInJar(ClassUtilities.class));
    }

    public void testCheckforSpecificClassSuccess() {
        try {
            ClassUtilities.checkForSpecificClass("", String.class);
        } catch (ClassCastException e) {
            fail();
        }
    }

    public void testCheckforSpecificClassFailure() {
        try {
            ClassUtilities.checkForSpecificClass(new ArrayList<Object>(), List.class);
            fail();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
