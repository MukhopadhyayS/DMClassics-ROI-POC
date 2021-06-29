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

import junit.framework.TestCase;

/**
 * @author Kenneth Partlow
 * 
 */
public class TestBase64Utilities extends TestCase {

    private String _string1 = "OzeowT7xLLnpzUOM2vdikhUBKhWrnT7v";
    private String _string2 = "KennyPartlow";
    private String _string3 = "DanLindberg=";
    private String _string4 = "AAECAwQFBgcICQo=";
    private String _string5 = "CwwNDg8QERITFA==";
    private String _string6 = "";
    private String _string7 = "P0BBQkNERUZHSA==";
    private String _string8 = "P0ERUZHSfw==";

    public TestBase64Utilities(String name) {
        super(name);
    }

    public void testConstructorIsPrivate() {
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(Base64Utilities.class));
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(Base64Utilities.class));
    }

    public void testDecodeEncode() {
        verifyString(_string1);
        verifyString(_string2);
        verifyString(_string3);
        verifyString(_string4);
        verifyString(_string5);
        verifyString(_string6);
        verifyString(_string7);
        verifyString(_string8);
    }

    public void verifyString(String expected) {
        byte[] bytes = Base64Utilities.decode(expected);
        
        String string = Base64Utilities.encode(bytes);
        assertEquals(expected, string);

        byte[] back = Base64Utilities.decode(string);

        assertEquals(bytes.length, back.length);
        for (int i = 0; i < bytes.length; i++) {
            assertEquals(bytes[i], back[i]);
        }
    }
}
