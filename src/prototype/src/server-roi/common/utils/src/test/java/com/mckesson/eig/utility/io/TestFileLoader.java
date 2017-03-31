/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.utility.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.utility.util.ClassUtilities;
import com.mckesson.eig.utility.util.ReflectionUtilities;

public class TestFileLoader extends UnitTest {

    private static final String TEST_RELATIVE = "com/mckesson/eig/utility/io/test.txt";
    private static final String TEST_JARRED = "java/lang/String.class";
    private static final String TEST_INVALID = "file/does/not/exist.txt";
    private static final String TEST_JPEG = TEST_RELATIVE;
    private static final int TEST_VALUE = 25;
    private static final int TEST_ASSERT = 20;
    public TestFileLoader(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestFileLoader.class, FileLoader.class);
    }

    public void testConstructor() {
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(FileLoader.class));
        assertTrue(ClassUtilities.areAllConstructorsPrivate(FileLoader.class));
    }

    /**
     * getResourceAsUrl()
     */
    public void testGetResourceAsUrlFailure() {
        assertFileDoesNotExist(TEST_INVALID);
    }

    public void testGetResourceAsUrlRelativePath() {
        assertUrlExists(TEST_RELATIVE);
    }

    public void testGetResourceAsUrlJarredUp() {
        assertUrlExists(TEST_JARRED);
    }

    public void testGetResourceAsUrlFailureWithClassLoader() {
        assertUrlDoesNotExist(this.getClass().getClassLoader(), TEST_INVALID);
    }

    public void testGetResourceAsUrlRelativePathWithClassLoader() {
        assertUrlExists(this.getClass().getClassLoader(), TEST_RELATIVE);
    }

    public void testGetResourceAsUrlWithNullClassLoader() {
        assertUrlExists(null, TEST_RELATIVE);
    }

    public void testGetResourceAsUrlJarredUpWithClassLoader() {
        assertUrlExists(this.getClass().getClassLoader(), TEST_JARRED);
    }

    /**
     * getResourceAsInputStream()
     */
    public void testGetResourceAsInputStreamFailure() {
        assertInputStreamDoesNotExist(TEST_INVALID);
    }

    public void testGetResourceAsInputStream() {
        assertInputStreamExists(TEST_RELATIVE);
    }

    public void testGetResourceAsInputStreamWithClassLoader() {
        assertInputStreamDoesNotExist(this.getClass().getClassLoader(),
                TEST_INVALID);
    }

    public void testGetResourceAsInputStreamWithNullClassLoader() {
        assertInputStreamDoesNotExist(null, TEST_INVALID);
    }

    public void testGetResourceAsFileRelative() {
        File file = FileLoader.getResourceAsFile(TEST_RELATIVE);
        assertNotNull("File should not be null", file);
    }

    public void testGetResourceAsFileWithSource() {
        assertNotNull(FileLoader.getResourceAsFile(this, "test.txt"));
        assertNotNull(FileLoader
                .getResourceAsFile((Object) null, TEST_RELATIVE));
    }

    public void testGetResourceBundleWithSourceObject() {
        assertNotNull(FileLoader.getResourceBundle(this, "test"));
    }

    public void testGetResourceAsFileFailure() {
        assertFileDoesNotExist(TEST_INVALID);
    }

    public void testGetResourceAsFileWithNullClassLoader() {
        assertEquals(null, FileLoader.getResourceAsFile((Object) null,
                TEST_INVALID));
    }

    /**
     * getResourceAsIcon()
     */
    public void testGetResourceAsIcon() {
        assertNotNull(FileLoader.getResourceAsIcon(TEST_JPEG));
    }

    public void testGetResourceAsIconDoesNotExist() {
        assertEquals(null, FileLoader.getResourceAsIcon(TEST_INVALID));
    }

    public void testLoadLocalIconWithNullUrl() {
        assertEquals(null, FileLoader.loadLocalIcon(null));
    }

    public void testLoadIconFromClasspath() {
        URL url = FileLoader.getResourceAsUrl(TEST_JPEG);
        assertNotNull(FileLoader.loadLocalIcon(url));
    }

    public void testGetResourceAsFileWithStringRelative() {
        assertNotNull(FileLoader.getResourceAsFile(TEST_RELATIVE));
    }

    public void testLoadFileFromClasspathInvalid() {
        assertNull(FileLoader.getResourceAsFile(TEST_INVALID));
    }

    public void testGetResourceAsFileReaderInvalid() {
        assertNull(FileLoader.getResourceAsReader(TEST_INVALID));
    }

    public void testGetFileReaderNull() {
        try {
            FileLoader.getReader(null);

        } catch (NullPointerException e) {
            e.printStackTrace();
            
        }
    }

    public void testGetFileReaderWhenResourceIsNotThere() {
        assertNull(FileLoader.getReader(new File(TEST_INVALID)));
    }

    public void testGetFileReaderValid() {
        File file = FileLoader.getResourceAsFile(TEST_RELATIVE);
        assertNotNull(FileLoader.getReader(file));
    }

    public void testGetResourceAsFileReaderOfNull() {
        assertNull(FileLoader.getResourceAsReader(null));
    }

    public void testGetResourceAsFileReaderRelative() {
        assertNotNull(FileLoader.getResourceAsReader(TEST_RELATIVE));
    }

    public void testGetResourceAsFileReader() {
        assertNotNull(FileLoader.getResourceAsReader(TEST_JPEG));
    }

    public void testGetClassLoader() {
        assertNotNull(FileLoader.getClassLoader());
    }

    public void assertFileDoesNotExist(String file) {
        assertEquals(null, FileLoader.getResourceAsFile(file));
    }

    public void assertFileDoesNotExist(ClassLoader cl, String file) {
        assertEquals(null, FileLoader.getResourceAsFile(cl, file));
    }

    public void assertInputStreamDoesNotExist(String file) {
        assertEquals(null, FileLoader.getResourceAsInputStream(file));
    }

    public void assertInputStreamDoesNotExist(ClassLoader cl, String file) {
        assertEquals(null, FileLoader.getResourceAsInputStream(cl, file));
    }

    public void assertUrlExists(String file) {
        URL url = FileLoader.getResourceAsUrl(file);

        assertNotNull("url should be valid", url);
        assertTrue("url should end with file", url.getPath().endsWith(file));
    }

    public void assertUrlDoesNotExist(ClassLoader cl, String file) {
        URL url = FileLoader.getResourceAsUrl(cl, file);
        assertNull("url should not exist", url);
    }

    public void assertUrlExists(ClassLoader cl, String file) {
        URL url = FileLoader.getResourceAsUrl(cl, file);

        assertNotNull("url should be valid", url);
        assertTrue("url should end with file", url.getPath().endsWith(file));
    }

    // Check our file test.file which has 20 characters in it.
    public void assertInputStreamExists(String stream) {
        InputStream input = FileLoader.getResourceAsInputStream(stream);

        assertNotNull("Input stream should be valid", input);

        byte[] bytes = new byte[TEST_VALUE];

        try {
            assertTestFileSize(input.read(bytes));
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    public void assertTestFileSize(int size) {
        assertEquals(TEST_ASSERT, size);
    }
}
