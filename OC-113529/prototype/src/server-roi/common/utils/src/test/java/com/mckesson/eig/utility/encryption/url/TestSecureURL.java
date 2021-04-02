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

package com.mckesson.eig.utility.encryption.url;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestSecureURL extends TestCase {

    public TestSecureURL(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestSecureURL.class, SecureURL.class);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public void testSecureURL() {
        assertNotNull(new SecureURL());
    }

    public void testEncode() {
        String data1 = "admin20050310093728";
        String key1 = "mckesson";
        String expect1 = "61479AAABA28430A7F303DBDE40067A2";
        assertEquals(expect1, SecureURL.encode(data1, key1));

        String data2 = "admin20050310093728";
        String key2 = "portal";
        String expect2 = "9CD2616072C80475A9920892FCF7FDEF";
        assertEquals(expect2, SecureURL.encode(data2, key2));

        String data3 = "michaelyeo20050310193728";
        String key3 = "portal";
        String expect3 = "F2BE9C394D5FABD4B5BE6D8CAE945C96";
        assertEquals(expect3, SecureURL.encode(data3, key3));

        String data4 = "porta003529Al20050310093728";
        String key4 = "mckesson";
        String expect4 = "0FA3F4395163C352D361F30C167C0172";
        assertEquals(expect4, SecureURL.encode(data4, key4));

    }

    public void testEncodeDataNull() {
        String data = null;
        String key = "mckesson";
        try {
            SecureURL.encode(data, key);
            fail("data cann't be null");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testEncodeKeyNull() {
        String data = "admin20050309123426";
        String key = null;
        try {
            SecureURL.encode(data, key);
            fail("key cann't be null");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
