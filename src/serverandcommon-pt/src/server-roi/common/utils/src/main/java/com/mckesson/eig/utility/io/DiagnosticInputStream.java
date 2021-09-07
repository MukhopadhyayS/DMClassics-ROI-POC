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

import com.mckesson.dm.core.common.logging.OCLogger;
/**
 * Provides method for reading the next byte or some number of bytes of data
 * from the input stream.It also have methods for closing and releasing the
 * system resource in use.The <code>mark,reset</code> operations are also
 * performed.
 * 
 */
public class DiagnosticInputStream extends InputStream {
    /**
     * Logs the message.
     */
    private static final OCLogger LOG = new OCLogger(DiagnosticInputStream.class);
    /**
     * Holds the instance of <code>InputStream</code>.
     */
    private InputStream _stream;
    /**
     * used as a count for no.of bytes read.
     */
    private long _byteCount;
    /**
     * Constructor used for read and write operations on
     * <code>InputStream</code>.
     * 
     * @param stream
     *            instance of InputStream.
     */
    public DiagnosticInputStream(InputStream stream) {
        super();
        _stream = stream;
    }
    /**
     * Reads the count.
     * 
     * @return Number of byte read.
     */
    public long getByteCount() {
        return _byteCount;
    }
    /**
     * Reads the next byte of data from the input stream. The value byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the
     * stream has been reached, the value <code>-1</code> is returned.
     * 
     * @return result The value byte is returned as an <code>int</code> in the
     *         range <code>0</code> to <code>255</code>.
     * @exception IOException
     *                if an I/O error occurs.
     */
    public int read() throws IOException {
        int result = _stream.read();
        _byteCount++;
        return result;
    }
    /**
     * Reads some number of bytes from the input stream and stores them into the
     * buffer array <code>b</code>. The number of bytes actually read is
     * returned as an integer.
     * 
     * @param b
     *            the buffer into which the data is read.
     * 
     * 
     * @return the total number of bytes read into the buffer, or
     *         <code>-1</code> if there is no more data because the end of the
     *         stream has been reached.
     * @exception IOException
     *                if an I/O error occurs.
     */
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }
    /**
     * Reads up to <code>len</code> bytes of data from the input stream into
     * an array of bytes. An attempt is made to read as many as <code>len</code>
     * bytes, but a smaller number may be read. The number of bytes actually
     * read is returned as an integer.
     * 
     * @param b
     *            the buffer into which the data is read.
     * @param ofs
     *            the start offset in array <code>b</code> at which the data
     *            is written.
     * @param len
     *            the maximum number of bytes to read.
     * @return the total number of bytes read into the buffer, or
     *         <code>-1</code> if there is no more data because the end of the
     *         stream has been reached.
     * @exception IOException
     *                if an I/O error occurs.
     */
    public int read(byte[] b, int ofs, int len) throws IOException {
        int result = _stream.read(b, ofs, len);
        _byteCount += result;
        return result;
    }
    /**
     * Returns the number of bytes that can be read (or skipped over) from this
     * input stream without blocking by the next caller of a method for this
     * input stream.
     * 
     * @return the number of bytes that can be read from this input stream
     *         without blocking.
     * @exception IOException
     *                if an I/O error occurs.
     */
    public int available() throws IOException {
        return _stream.available();
    }
    /**
     * Closes this input stream and releases any system resources associated
     * with the stream.It also checks whether this logger is enabled for the
     * debug priority and Logs the message.
     * 
     * @exception IOException
     *                if an I/O error occurs.
     */
    public void close() throws IOException {
        _stream.close();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Closing: " + getByteCount() + " total bytes read");
        }
    }
    /**
     * Repositions this stream to the position at the time the <code>mark</code>
     * method was last called on this input stream.
     * 
     * @exception IOException
     *                if an I/O error occurs.
     */
    public void reset() throws IOException {
        _stream.reset();
    }
    /**
     * Skips over and discards <code>n</code> bytes of data from this input
     * stream.
     * 
     * @param bytes
     *            the number of bytes to be skipped.
     * @return the actual number of bytes skipped.
     * @exception IOException
     *                if an I/O error occurs.
     * 
     */
    public long skip(long bytes) throws IOException {
        return _stream.skip(bytes);
    }
    /**
     * Tests if this input stream supports the <code>mark</code> and
     * <code>reset</code> methods. Whether or not <code>mark</code> and
     * <code>reset</code> are supported is an invariant property of a
     * particular input stream instance.
     * 
     * @return <code>true</code> if this stream instance supports the mark and
     *         reset methods; <code>false</code> otherwise.
     */
    public boolean markSupported() {
        return _stream.markSupported();
    }
    /**
     * Marks the current position in this input stream.
     * 
     * @param arg0
     *            the maximum limit of bytes that can be read before the mark
     *            position becomes invalid.
     */
    public void mark(int arg0) {
        _stream.mark(arg0);
    }
}
