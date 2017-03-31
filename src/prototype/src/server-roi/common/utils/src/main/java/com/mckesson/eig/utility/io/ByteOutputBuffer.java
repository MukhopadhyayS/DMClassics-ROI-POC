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

import java.io.ByteArrayOutputStream;

/**
 * Provides method for creating a new byte array output stream and for releasing
 * the same.
 * 
 */
public class ByteOutputBuffer extends ByteArrayOutputStream {

    /**
     * Initial buffer capacity.
     */
    private static final int BUFFER_CAPACITY = 32;

    /**
     * Creates a new byte array output stream. The buffer capacity is initially
     * 32 bytes, though its size increases if necessary.
     * 
     */
    public ByteOutputBuffer() {
        this(BUFFER_CAPACITY);
    }

    /**
     * Creates a new byte array output stream. The initial buffer capacity
     * depends upon the <code>size</code> which is passed as parameter, though
     * its size increases if necessary.
     * 
     * @param size
     *            initial buffer capacity.
     */
    public ByteOutputBuffer(int size) {
        super(size);
    }

    /**
     * It releases the <code>byte</code> array by setting the
     * <code>count(Number of valid bytes)</code> to zero.
     * 
     * @return byte uninitialized array.
     */
    public synchronized byte[] release() {
        byte[] result = buf;
        buf = new byte[1];
        count = 0;
        return result;
    }
}
