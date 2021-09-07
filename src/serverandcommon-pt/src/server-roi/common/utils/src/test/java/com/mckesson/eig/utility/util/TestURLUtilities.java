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
package com.mckesson.eig.utility.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestURLUtilities extends UnitTest {

    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities.areAllConstructorsPrivate(URLUtilities.class));
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(URLUtilities.class));
    }

    /**
     * @deprecated
     *
     */
    @Deprecated
	public void testEncode() {
        assertEquals("foo+bar+%24", URLUtilities.encode("foo bar $"));
    }

    /**
     * @deprecated
     *
     */
    @Deprecated
	public void testTryCatchOfEncode() {
        try {
            String test = URLUtilities.encode(null);
            fail("Should have thrown UtilitiesException." + test);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testBuildServerURLAsString() throws Exception {
        assertEquals("http://foo", URLUtilities.buildServerURLAsString(new URL(
                "http://foo/portal/")));
        assertEquals("http://bar", URLUtilities.buildServerURLAsString(new URL(
                "http://bar:80/portal/")));
        assertEquals("http://foo.bar:8080", URLUtilities
                .buildServerURLAsString(new URL("http://foo.bar:8080/portal/")));

        assertEquals("https://baz", URLUtilities
                .buildServerURLAsString(new URL("https://baz/some/path")));
        assertEquals("https://blah.blah", URLUtilities
                .buildServerURLAsString(new URL(
                        "https://blah.blah:443/another/file.txt")));
        assertEquals("https://blah.blah:7001", URLUtilities
                .buildServerURLAsString(new URL(
                        "https://blah.blah:7001/another/file.txt")));
    }

    public void testIsStandardHTTPPort() throws Exception {
        assertTrue(URLUtilities.isStandardHTTPPort(new URL("http://foo/")));
        assertTrue(URLUtilities.isStandardHTTPPort(new URL("http://foo:80/")));
        assertTrue(URLUtilities.isStandardHTTPPort(new URL("https://foo/")));
        assertTrue(URLUtilities.isStandardHTTPPort(new URL("https://foo:443/")));

        assertFalse(URLUtilities
                .isStandardHTTPPort(new URL("http://foo:8080/")));
        assertFalse(URLUtilities
                .isStandardHTTPPort(new URL("https://foo:7001/")));
    }

    public void testIsProtocol() throws Exception {
        assertTrue(URLUtilities.isProtocol(new URL("http://foo/"), "http"));
        assertTrue(URLUtilities.isProtocol(new URL("https://bar/"), "https"));

        assertFalse(URLUtilities.isProtocol(new URL("http://foo/"), "https"));
        assertFalse(URLUtilities.isProtocol(new URL("https://bar/"), "http"));

        assertTrue(URLUtilities.isProtocol(new URL("http://baz/"), " HTTP "));
    }

    public void testEncodeMap() {
        Map<String, String> aContainer = null;

        assertNull(URLUtilities.concat(null, null));

        aContainer = new HashMap<String, String>();
        assertEquals("foo bar $", URLUtilities.concat("foo bar $", aContainer));

        aContainer.put(null, new String("A"));
        assertEquals("foo bar $?", URLUtilities.concat("foo bar $", aContainer));

        aContainer.put(" ", new String("A"));
        assertEquals("foo bar $?", URLUtilities.concat("foo bar $", aContainer));

        aContainer.put("A", new String("A"));
        assertEquals(" ?A=A", URLUtilities.concat(" ", aContainer));

        try {
            URLUtilities.concat(null, aContainer);
            fail("Should have throwen java.lang.NullPointerException");
        } catch (java.lang.NullPointerException npe) {
            npe.printStackTrace();
        }

        assertEquals("foo bar?A=A", URLUtilities.concat("foo bar?", aContainer));

        assertEquals("foo bar?=abc&A=A", URLUtilities.concat("foo bar?=abc",
                aContainer));

        aContainer.put("B", new String("B"));
        assertEquals("foo bar?A=A&B=B", URLUtilities.concat("foo bar",
                aContainer));
    }

    public void testCreateWithMalformedUrl() {
        try {
            URLUtilities.create("foo.bar");
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testCreate() {
        final String path = "http://www.google.com";
        final URL url = URLUtilities.create(path);
        assertEquals(path, url.toString());
    }
}
