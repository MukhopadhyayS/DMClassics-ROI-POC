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

import java.io.IOException;
import java.io.InputStream;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestDiagnosticInputStream extends UnitTest {
    private static final int TEST_VALUE = 1024;
    private DiagnosticInputStream _inStream;

    public TestDiagnosticInputStream(String name) {
        super(name);
    }

    // Dummy input stream to pretend we read stuff
    private final class DummyInputStream extends InputStream {

        public int available() {
            return 1;
        }

        public void reset() throws IOException {
            throw new IOException("Cannot Reset");
        }

        public boolean markSupported() {
            return true;
        }

        public int read() {
            return 0;
        }
    }

    protected void setUp() throws Exception {
        super.setUp();
        _inStream = new DiagnosticInputStream(new DummyInputStream());
    }

    protected void tearDown() throws Exception {
        _inStream.close();
        super.tearDown();
    }

    public void testSingleByteRead() throws Exception {
        int byteRead = _inStream.read();
        assertEquals(0, byteRead);
        assertEquals(1, _inStream.getByteCount());
        assertEquals(1, _inStream.available());
    }

    public void testBufferRead() throws Exception {
        byte[] buffer = new byte[TEST_VALUE];
        int bytesRead = _inStream.read(buffer);
        assertEquals(0, buffer[buffer.length - 1]);
        assertEquals(buffer.length, bytesRead);
        assertEquals(buffer.length, _inStream.getByteCount());
    }

    public void testMiscStreamFunctions() throws Exception {
        assertTrue(_inStream.markSupported());
        _inStream.mark(0);
        _inStream.skip(0);
    }

    public void testInnerStreamException() {
        try {
            _inStream.reset(); // Inner stream is hardcoded to make this fail
            fail("IOException expected");
        } catch (IOException ie) {
            return;
        }
    }
}
