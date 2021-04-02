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
import java.io.OutputStream;
import java.util.Random;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestDiagnosticOutputStream extends UnitTest {

    private DiagnosticOutputStream _outStream;
    private static final int TEST_VALUE_1 = 1024;
    private static final int TEST_VALUE_2 = 512;
    private static final int TEST_SIZE = 8;
    private static final int BUFFER_SIZE = 524288;
    // This dummy outputstream class just dumps stuff into the bit bucket
    private final class DummyOutputStream extends OutputStream {

        public void write(int b) throws IOException {
        }

        public void flush() throws IOException {
            throw new IOException("Flush Not Supported");
        }
    }

    public TestDiagnosticOutputStream(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        _outStream = new DiagnosticOutputStream(new DummyOutputStream());
    }

    protected void tearDown() throws Exception {
        _outStream.close();
        super.tearDown();
    }

    public void testSingleByteWrite() throws Exception {
        _outStream.write(2);
        assertEquals(1, _outStream.getByteCount());
    }

    public void testBufferWrite() throws Exception {
        byte[] buffer = new byte[TEST_VALUE_1];
        _outStream.write(buffer);
        assertEquals(buffer.length, _outStream.getByteCount());
    }

    public void testBufferOffsetWrite() throws Exception {
        byte[] buffer = new byte[TEST_VALUE_1];
        _outStream.write(buffer, 0, TEST_VALUE_2);
        _outStream.write(buffer, TEST_VALUE_2, TEST_VALUE_2);
        assertEquals(TEST_VALUE_1, _outStream.getByteCount());
    }

    public void testInnerStreamException() {
        try {
            _outStream.flush(); // Inner stream is hardcoded to make this fail
            fail("IOException expected in TestDiagnosticStream : testInnerStreamException");
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private byte[] initBuffer(int bufferSize) {
        Random rnd = new Random();
        byte[] buffer = new byte[bufferSize];
        rnd.nextBytes(buffer);
        return buffer;
    }

    public void testStreamOverhead() throws Exception {

        long[] diagnosticScores = new long[TEST_SIZE];
        long[] dummyScores = new long[TEST_SIZE];
        DummyOutputStream dummy = new DummyOutputStream();

        // Log DiagnosticOutputStream times
        for (int x = 0; x < TEST_SIZE; x++) {
            byte[] buf = initBuffer(BUFFER_SIZE);
            long sTime = System.currentTimeMillis();
            _outStream.write(buf);
            diagnosticScores[x] = (System.currentTimeMillis() - sTime);
        }

        // Log DummyOutputStream times
        for (int x = 0; x < TEST_SIZE; x++) {
            byte[] buf = initBuffer(BUFFER_SIZE);
            long sTime = System.currentTimeMillis();
            dummy.write(buf);
            dummyScores[x] = (System.currentTimeMillis() - sTime);
        }

        // Output the times
        for (int x = 0; x < TEST_SIZE; x++) {
            System.out.println("#" + (x + 1) + " Diagnostic: "
                    + diagnosticScores[x] + "ms, Dummy: " + dummyScores[x]
                    + "ms");
        }
    }
}
