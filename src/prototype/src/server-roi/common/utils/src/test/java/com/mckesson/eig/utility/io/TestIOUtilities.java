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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.utility.util.UtilitiesException;

public class TestIOUtilities extends UnitTest {

    private static final String FILE = "test.txt";
    private static final int TEST_VALUE_1 = 3;
    private static final int TEST_VALUE_2 = 9;
    public void testCloseInputStream() {
        IOUtilities.close(getExceptionalInputStream());
        IOUtilities.close(new ByteArrayInputStream(new byte[1]));
        IOUtilities.close((InputStream) null);
    }

    public void testCloseOutputStream() {
        IOUtilities.close(getExceptionalOutputStream());
        IOUtilities.close(new ByteArrayOutputStream());
        IOUtilities.close((OutputStream) null);
    }

    public void testCloseWriter() {
        IOUtilities.close(getExceptionalWriter());
        IOUtilities.close(new StringWriter());
        IOUtilities.close((Writer) null);
    }

    public void testCloseReader() {
        IOUtilities.close(new StringReader("foo"));
        IOUtilities.close((Reader) null);
        IOUtilities.close(getExceptionalReader());
    }

    public void testCloseRandomAccessFile() throws Exception {
        File file = FileLoader.getResourceAsFile(this, FILE);
        IOUtilities.close(getExceptionalRandomAccessFile(file, "r"));
        IOUtilities.close(new RandomAccessFile(file, "r"));
        IOUtilities.close((RandomAccessFile) null);
    }

    public void testFileToByteArray() {
        File file = FileLoader.getResourceAsFile(this, FILE);
        byte[] bytes = IOUtilities.toByteArray(file);
        assertEquals("This is a test file!", new String(bytes));
        try {
            IOUtilities.toByteArray(new File("foobar"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testWithBufferedReader() {
        File file = FileLoader.getResourceAsFile(this, FILE);
        BufferedReader br = IOUtilities.createBufferedReader(file);
        assertEquals("This is a test file!", IOUtilities.readLine(br));
    }

    public void testExceptionInCreateBufferedReader() {
        try {
            IOUtilities.createBufferedReader(new File("foobar"));

        } catch (UtilitiesException e) {
            e.printStackTrace();

        }
    }

    public void testExceptionInReadLine() {
        try {
            IOUtilities.readLine(new BufferedReader(getExceptionalReader()));

        } catch (UtilitiesException e) {
            e.printStackTrace();

        }
    }

    public InputStream createByteInputStream(byte numBytes) {
        byte[] bytes = new byte[numBytes];

        for (byte i = 0; i < numBytes; i++) {
            bytes[i] = i;
        }

        return new ByteArrayInputStream(bytes);
    }

    public InputStream createTenByteInputStream() {
        return createByteInputStream((byte) TEST_VALUE_2);
    }

    public InputStream createEmptyInputStream() {
        return new ByteArrayInputStream(new byte[0]);
    }

    public void testReadFully() {
        DataInputStream in = new DataInputStream(getExceptionalInputStream());
        byte[] bytes = new byte[TEST_VALUE_2];
        try {
            IOUtilities.readFully(in, bytes);

        } catch (UtilitiesException e) {
            e.printStackTrace();

        }
    }

    public void testReadFromInputStream() {
        InputStream in = createTenByteInputStream();
        byte[] bytes = new byte[TEST_VALUE_2];
        IOUtilities.read(in, bytes, 0, TEST_VALUE_2);
        for (byte i = 0; i < TEST_VALUE_2; i++) {
            assertEquals(i, bytes[i]);
        }
    }

    public void testReadFromInputStreamThatThrowsAnException() {
        InputStream in = getExceptionalInputStream();
        byte[] bytes = new byte[TEST_VALUE_2];
        try {
            IOUtilities.read(in, bytes, 0, TEST_VALUE_2);

        } catch (UtilitiesException e) {
            e.printStackTrace();
        }
    }

    public void testReadFullFromInputStream() {
        InputStream in = createTenByteInputStream();
        byte[] bytes = IOUtilities.readFullyAndClose(in, TEST_VALUE_2);
        for (byte i = 0; i < TEST_VALUE_2; i++) {
            assertEquals(i, bytes[i]);
        }
    }

    public void testReadFullFromInputStreamThatReturnsNoData() {
        InputStream in = createEmptyInputStream();

        try {
            IOUtilities.readFullyAndClose(in, TEST_VALUE_2);

        } catch (UtilitiesException e) {
            e.printStackTrace();

        }
    }

    public void testWriteToOutputStreamWithException() {
        try {
            IOUtilities.write(getExceptionalOutputStream(), "foo");

        } catch (UtilitiesException e) {
            e.printStackTrace();

        }
    }

    public void testWritelnToOutputStreamWithException() {
        try {
            IOUtilities.writeln(getExceptionalOutputStream(), "foo");

        } catch (UtilitiesException e) {
            e.printStackTrace();

        }
    }

    public void testWriteToWriterWithException() {
        try {
            IOUtilities.write(getExceptionalWriter(), "foo");

        } catch (UtilitiesException e) {
            e.printStackTrace();

        }
    }

    public void testWritelnToOutputStream() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtilities.writeln(out, "foo");
        assertEquals("foo" + IOUtilities.NEW_LINE, out.toString());
    }

    public void testWriteToWriter() {
        StringWriter sw = new StringWriter();
        IOUtilities.write(sw, "foo");
        assertEquals("foo", sw.toString());
    }

    public void testReadFromReader() {
        Reader reader = new StringReader("foo");
        char[] bytes = new char[TEST_VALUE_1];
        assertEquals(TEST_VALUE_1, IOUtilities.read(reader, bytes, 0,
                TEST_VALUE_1));
        assertEquals("foo", new String(bytes));
    }

    public void testReadFromReaderThatThrowsException() {
        try {
            IOUtilities.read(getExceptionalReader(), new char[TEST_VALUE_1], 0,
                    TEST_VALUE_1);

        } catch (UtilitiesException e) {
            e.printStackTrace();

        }
    }

    public void testFlushThatThrowsException() {
        try {
            IOUtilities.flush(getExceptionalOutputStream());

        } catch (UtilitiesException e) {
            e.printStackTrace();

        }
    }

    public void testReadWriteOneObject() {
        String original = "foo bar";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtilities.writeObjectAndClose(baos, original);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        String copy = (String) IOUtilities.readObjectAndClose(bais);
        assertEquals(original, copy);
    }

    public void testExceptionOnReadWriteOneObject() {
        String original = "foo bar";
        try {
            IOUtilities.writeObjectAndClose(getExceptionalOutputStream(),
                    original);

        } catch (UtilitiesException e) {
            e.printStackTrace();

        }
        try {
            IOUtilities.readObjectAndClose(getExceptionalInputStream());

        } catch (UtilitiesException e) {
            e.printStackTrace();

        }
    }

    protected OutputStream getExceptionalOutputStream() {
        return new OutputStream() {
            public void write(byte[] bytes) throws IOException {
                throw new IOException();
            }

            public void write(int b) throws IOException {
                throw new IOException();
            }

            public void close() throws IOException {
                throw new IOException();
            }

            public void flush() throws IOException {
                throw new IOException();
            }
        };
    }

    protected InputStream getExceptionalInputStream() {
        return new InputStream() {
            public void close() throws IOException {
                throw new IOException();
            }

            public int read() throws IOException {
                throw new IOException();
            }
        };
    }

    protected Reader getExceptionalReader() {
        return new Reader() {
            public void close() throws IOException {
                throw new IOException();
            }

            public int read(char[] chars, int offset, int length)
                    throws IOException {
                throw new IOException();
            }
        };
    }

    protected Writer getExceptionalWriter() {
        return new Writer() {
            public void close() throws IOException {
                throw new IOException();
            }

            public void flush() throws IOException {
                throw new IOException();
            }

            public void write(char[] chars, int offset, int length)
                    throws IOException {
                throw new IOException();
            }
        };
    }

    protected RandomAccessFile getExceptionalRandomAccessFile(File file,
            String mode) throws FileNotFoundException {
        return new RandomAccessFile(file, mode) {
            public void close() throws IOException {
                throw new IOException();
            }
        };
    }
}
