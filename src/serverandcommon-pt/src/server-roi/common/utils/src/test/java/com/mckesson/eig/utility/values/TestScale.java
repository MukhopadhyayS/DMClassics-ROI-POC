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
package com.mckesson.eig.utility.values;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestScale extends TestCase {
    public static final int SIZE_HORIZON = 5;

    public TestScale(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestScale.class, Scale.class);
    }

    public void testDefaultConstructor() {
        Scale scale = new Scale();
        assertNotNull(scale);
        assertEquals(0, scale.getHorizontalSize());
        assertEquals(0, scale.getVerticalSize());
    }

    public void testConstructorWithParameters() {
        Scale scale = new Scale(1, SIZE_HORIZON);
        assertNotNull(scale);
        assertEquals(SIZE_HORIZON, scale.getHorizontalSize());
        assertEquals(1, scale.getVerticalSize());
    }
}
