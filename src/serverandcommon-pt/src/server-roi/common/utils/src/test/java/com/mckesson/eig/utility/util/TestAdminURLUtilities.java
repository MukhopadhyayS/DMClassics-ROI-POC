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
import junit.framework.Test;
import junit.framework.TestCase;
import com.mckesson.eig.utility.testing.CoverageSuite;

/**

 * Test case for the class AdminURLUtilities.
 */
public class TestAdminURLUtilities extends TestCase {
    /**Constructs the class.
     * @param name
     * Name of the test case.
     */
    public TestAdminURLUtilities(String name) {
        super(name);
    }
    /**
     * @return
     * CoverageSuite
     */
    public static Test suite() {
        return new CoverageSuite(TestAdminURLUtilities.class,
                AdminURLUtilities.class);
    }
    /**
     * Tests Constructor.
     */
    public void testConstructor() {
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(AdminURLUtilities.class));
    }
    /**
     * Tests if constructor is private.
     */
    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities.
                areAllConstructorsPrivate(AdminURLUtilities.class));
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(AdminURLUtilities.class));
        ReflectionUtilities
        .callPrivateConstructor(AdminURLUtilities.class);
    }
/**
 * Positive Scenario to test the valid URL.
 */
public void testValidateURL() {
    final String path = "http://foo.bar:8080";
    final URL url = URLUtilities.create(path);
        try  {
        assertTrue(AdminURLUtilities.validateURL(url));
        assertTrue(AdminURLUtilities
                .validateURL(new URL("http://foo.bar:8080")));
        } catch (Exception e) {
            fail();
        }
}
/**
 * Negative Scenario -Passing invalid URL.
 */
public void testInvalidURL() {
    try {
      AdminURLUtilities.validateURL(new URL("foo.bar:8080"));
      fail();
} catch (Exception e) {        
      assertEquals(e.getMessage(), "unknown protocol: foo.bar");
}
}
/**
 * Tests getProtocol().
 */
public void testGetProtocol() {
 
    assertEquals("http", AdminURLUtilities.getProtocol(URLUtilities.
            create("http://foo.bar:8080")));
    assertEquals("https", AdminURLUtilities.getProtocol(URLUtilities.
            create("https://foo.bar:8080")));
    assertEquals("ftp", AdminURLUtilities.getProtocol(URLUtilities.
            create("ftp://foo.bar:8080")));
    }
/**
 * Tests for invalid Protocol.
 */
public void testInvalidProtocol() {
    try  {
        AdminURLUtilities.validateURL(new URL("htts://foo.bar:8080"));
        fail();
    } catch (Exception e) { 
        assertEquals(e.getMessage(), "unknown protocol: htts");
    }
}
/**
 * Tests Exception for invalid protocol of length 5.
 */
public void testInvalidProtocolOfLengthFive() {
    try  {
       AdminURLUtilities.validateURL(new URL("httsp://foo.bar:8080"));
        fail();
    } catch (Exception e) {  
     assertEquals(e.getMessage(), "unknown protocol: httsp");
    }
}
/**
 * Tests Exception for the unsupported protocol length. 
 */
public void testUnsupportedProtocolLength() {
    try  {
         AdminURLUtilities.validateURL(new URL("httpps://foo.bar:8080"));
        fail();
    } catch (Exception e) {
      assertEquals(e.getMessage(), "unknown protocol: httpps");
    }
}
/**
 * Tests null URL.
 */
public void testNullURL() {
    try {
       AdminURLUtilities.validateURL(new URL(""));
        fail();
    } catch (Exception e) {
       assertEquals(e.getMessage(), "no protocol: ");
    }
}
/**
 * Tests URL without protocol.
 */
public void testURLWithoutProtocol() {
    try {
       AdminURLUtilities.validateURL(new URL("www.google.com"));
        fail();
    } catch (Exception e) {
       assertEquals(e.getMessage(), "no protocol: www.google.com");
    }
}
/**
 * Tests Exception for  urlwithout host.
 */
public void testNullHostURL() {
    try   {
        AdminURLUtilities.validateURL(new URL("http://"));
        fail();
    } catch (Exception e) {  
       assertEquals(e.getMessage(), "URL - name is zero bytes");
    }
}
/**
 * Tests null name URL.
 */
public void testNullNameURL() {
    try  {
       AdminURLUtilities.validateURL(new URL("https://"));
        fail();
    } catch (Exception e)  {     
      assertEquals(e.getMessage(), "URL - name is zero bytes");
    }
}
/**
 * Tests getName().
 */
public void testGetName() {
    assertEquals(AdminURLUtilities.getName(URLUtilities.
            create("http://foo.bar:8080")), "foo.bar");
    assertEquals(AdminURLUtilities.getName(URLUtilities.
            create("http://")), "");
    assertEquals(AdminURLUtilities.getName(URLUtilities.
            create("http://www.google.com")), "www.google.com");
}
/**
 * Tests getPort().
 */
public void testGetPort() {
    assertEquals(AdminURLUtilities.getPort(URLUtilities.
            create("http://foo.bar:8080")), "8080");
    assertEquals(AdminURLUtilities.getPort(URLUtilities.
            create("http://foo.bar")), "-1");

}
}
