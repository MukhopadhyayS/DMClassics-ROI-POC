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
package com.mckesson.eig.utility.testing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import junit.framework.Assert;

import com.mckesson.eig.utility.io.IOUtilities;

public class ObjectVerifier extends Assert {

    protected ObjectVerifier() {
    }

    public static void verifyEquals(Object first, Object second, Object third) {
        assertEquals(first, second);
        assertEquals(second, first);
        assertEquals(first, third);
        assertEquals(second, third);
        assertEquals(first, first);
    }

    public static void verifyNotEquals(Object first, Object second, Object third) {
        assertFalse(first.equals(second));
        assertFalse(second.equals(first));
        assertFalse(first.equals(third));
        assertFalse(second.equals(third));

        verifyNotEquals(first, null);
        verifyNotEquals(first, "foo");

        assertEquals(first, first);
    }

    public static void verifyNotEquals(Object one, Object two) {
        assertFalse(one.equals(two));
    }

    public static Object serialize(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtilities.writeObjectAndClose(baos, object);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return IOUtilities.readObjectAndClose(bais);
    }

    public static void verifySerialization(Object object) {
        Object copy = serialize(object);
        assertEquals(object, copy);
        assertNotSame(object, copy);
    }
}
