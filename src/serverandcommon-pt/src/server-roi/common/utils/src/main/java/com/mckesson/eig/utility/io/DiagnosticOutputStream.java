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

import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * Provides methods for performing OutputStream operations.
 * 
 */
public class DiagnosticOutputStream extends OutputStream {
    
    /**
     * Logs the message.
     */
    private static final OCLogger LOG = new OCLogger(DiagnosticOutputStream.class);

    /**
     * instance of <code>OutputStream</code>.
     */
    private OutputStream _stream;

    /**
     * used as a count for no.of bytes to be written.
     */
    private long _byteCount;

    /**
     * Constructs a command capable of performing the write operations.
     * 
     * @param stream
     *            instance of <code>OutputStream</code>.
     */
    public DiagnosticOutputStream(OutputStream stream) {
        super();
        _stream = stream;
    }

    /**
     * Writes the specified byte to this output stream.
     * 
     * @param b
     *            the <code>byte</code>.
     * @exception IOException
     *                if an I/O error occurs. In particular, an
     *                <code>IOException</code> may be thrown if the output
     *                stream has been closed.
     */
    public void write(int b) throws IOException {
        _stream.write(b);
        _byteCount++;
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array starting at
     * offset <code>off</code> to this output stream.
     * 
     * @param b
     *            the data.
     * @param ofs
     *            the start offset in the data.
     * @param len
     *            the number of bytes to write.
     * @exception IOException
     *                if an I/O error occurs. In particular, an
     *                <code>IOException</code> is thrown if the output stream
     *                is closed.
     */
    public void write(byte[] b, int ofs, int len) throws IOException {
        _stream.write(b, ofs, len);
        _byteCount += len;
    }

    /**
     * writes some number of bytes .
     * 
     * @param b
     *            the buffer into which the data has to be written..
     * 
     * @exception IOException
     *                if an I/O error occurs.
     */
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    /**
     * Flushes this output stream and forces any buffered output bytes to be
     * written out.
     * 
     * @exception IOException
     *                if an I/O error occurs.
     */
    public void flush() throws IOException {
        _stream.flush();
    }

    /**
     * Closes this output stream and releases any system resources associated
     * with this stream.It also checks whether this logger is enabled for the
     * debug priority and Logs the message.
     * 
     * @exception IOException
     *                if an I/O error occurs.
     */
    public void close() throws IOException {
        _stream.close();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Closing: " + getByteCount() + " total bytes written");
        }
    }

    /**
     * Returns the number of <code>byte</code> written.
     * 
     * @return long Number of bytes.
     */
    public long getByteCount() {
        return _byteCount;
    }
}
