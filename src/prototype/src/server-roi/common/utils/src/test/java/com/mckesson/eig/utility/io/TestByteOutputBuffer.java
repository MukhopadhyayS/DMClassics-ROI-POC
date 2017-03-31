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

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

public class TestByteOutputBuffer extends UnitTest {

    private static final int TEST_VALUE_1 = 32;
    private static final int TEST_VALUE_2 = 64;
    private ByteOutputBuffer _buffer;

    public TestByteOutputBuffer(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestByteOutputBuffer.class,
                ByteOutputBuffer.class);
    }

    protected void setUp() throws Exception {
        _buffer = new ByteOutputBuffer();
    }

    protected void tearDown() throws Exception {
        _buffer = null;
    }

    public void testConstructor() {
        assertNotNull(_buffer);
        assertEquals(TEST_VALUE_1, _buffer.release().length);
    }

    public void testConstructorThatTakesAnInt() {
        _buffer = new ByteOutputBuffer(TEST_VALUE_2);
        assertNotNull(_buffer);
        assertEquals(TEST_VALUE_2, _buffer.release().length);
    }

    public void testReleaseReinitializesTheLengthOfTheBuffer() {
        assertEquals(TEST_VALUE_1, _buffer.release().length);
        assertEquals(1, _buffer.release().length);
    }
}
