/**
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

import java.security.SecureRandom;
import java.util.Random;

public abstract class ByteUtilities {

    private static final int COUNT_END = 4;

    private static final int CONST_INT = 256;

    private static final int BINARY_INT = 8;

    private static final int NUMBER_OF_BYTE = 4;

    private static final int INITIATE_VALUE = 3;

    private static final int HEX_VALUE = 0x000000FF;
    /**
     * Convert a set of bytes to a <code>short</code>
     * 
     * @param bytes
     *            Bytes to convert
     * @return short
     */
    public short bytesToShort(byte[] bytes) {
        return bytesToShort(bytes, 0);
    }

    /**
     * Convert a set of bytes to a <code>short</code>, starting at a
     * specified position
     * 
     * @param bytes
     *            Bytes to convert
     * @param start
     *            Starting point to grab the bytes from
     * @return short
     */
    public short bytesToShort(byte[] bytes, int start) {
        return (short) bytesToNumber(bytes, start, 2);
    }

    /**
     * Convert a set of bytes to an <code>int</code>.
     * 
     * @param bytes
     *            Bytes to convert
     * @return int
     */
    public int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, 0);
    }

    /**
     * Convert a set of bytes to an <code>int</code>, starting at a specified
     * position.
     * 
     * @param bytes
     * @param start
     * 
     * @return int
     */
    public int bytesToInt(byte[] bytes, int start) {
        return (int) bytesToNumber(bytes, start, COUNT_END);
    }

    /**
     * Converts an <code>int</code> to an array of bytes.
     * 
     * @param number
     *            The number to pull the bytes from
     * @return byte[]
     */
    public byte[] intToBytes(int number) {
        byte[] b = new byte[NUMBER_OF_BYTE];
        for (int i = INITIATE_VALUE; i >= 0; i--) {
            b[i] = (byte) (number & HEX_VALUE);
            number = number >>> BINARY_INT;
        }
        return b;
    }

    /**
     * Reads a single byte from an array of bytes.
     * 
     * @param bytes
     *            The array of bytes to pull the byte from.
     * @param position
     *            The starting position.
     * @return byte[]
     */
    public byte getByte(byte[] bytes, int position) {
        return bytes[position];
    }

    /**
     * Reads a range of bytes from an array of bytes.
     * 
     * @param bytes
     *            The array of bytes to pull the range from.
     * @param from
     *            The starting position.
     * @param numBytes
     *            The number of bytes to get.
     * @return byte[]
     */
    public byte[] getByteRange(byte[] bytes, int from, int numBytes) {
        byte[] result = new byte[numBytes];
        for (int i = 0; i < numBytes; i++) {
            result[i] = getByte(bytes, from + i);
        }
        return result;
    }

    /**
     * Converts a set of bytes to a <code>long</code>.
     * 
     * @param bytes
     *            The array of bytes to pull the number from.
     * @param start
     *            The starting position.
     * @param maxBytes
     *            The max number of bytes to use
     * @return long
     */
    private long bytesToNumber(byte[] bytes, int start, int maxBytes) {
        return extractNumber(bytes, start, Math.min(maxBytes, bytes.length
                - start));
    }

    protected abstract long extractNumber(byte[] bytes,
                    int start, int maxBytes);

    /**
     * Since all java numeric primitives are signed, we need to handle the case
     * of an unsigned byte coming from a non-java language.
     * <p>
     * e.g. unsigned 200 = -56 signed.
     * <p>
     * We add 256 and place the number into an integer to preserve the original
     * value.
     * 
     * @param b
     *            The byte to convert
     * @return int
     */
    public int convertToPositiveInt(byte b) {
        return (b < 0) ? b + CONST_INT : b;
    }

    public void copy(String src, byte[] target, int pos, int max) {
        copy(getSafeBytes(src), target, pos, max);
    }

    public void copy(byte[] src, byte[] target, int pos, int max) {
        System.arraycopy(src, 0, target, pos, Math.min(src.length, max));
    }

    public String extractString(byte[] bytes, int start, int size) {
        return new String(bytes, start, size).trim();
    }

    public byte[] getSafeBytes(String src) {
        return StringUtilities.safe(src).getBytes();
    }

    public static final byte[] generateRandomBytes(int count) {
        byte[] result = new byte[count];
        SecureRandom r = new SecureRandom();
        r.nextBytes(result);
        return result;
    }
}
