/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.decoders;

import java.io.ByteArrayOutputStream;

public class HexDecoder implements Decoder {
	private static final long serialVersionUID = 1L;

	/**
     * value used for decoding.
     */
    private static final int ADD_VALUE = 10;

    /**
     * value used for decoding.
     */
    private static final int MULTIPLY_VALUE = 16;

    /**
     * Error message if the <code>digits</code> is odd.
     */
    public static final String ERROR_ODD_NUMBER_OF_DIGITS = "Odd number of digits in hex string";

    /**
     * Error message if the <code>digits</code> have insufficient characters.
     */
    public static final String ERROR_BAD_CHARACTER_IN_HEX_STRING = "Bad character"
            + " or insufficient number of characters in hex string";

    /**
     * Convert a String of hexadecimal digits into the corresponding byte array
     * by encoding each two hexadecimal digits as a byte.
     *
     * @param digits
     *            Hexadecimal digits representation
     *
     * @exception IllegalArgumentException
     *                if an invalid hexadecimal digit is found, or the input
     *                string contains an odd number of hexadecimal digits
     */
    public byte[] decode(String digits) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                (digits.length() + 1) / 2);
        for (int i = 0; i < digits.length(); i += 2) {
            char c1 = digits.charAt(i);
            if ((i + 1) >= digits.length()) {
                throw new IllegalArgumentException(ERROR_ODD_NUMBER_OF_DIGITS);
            }
            char c2 = digits.charAt(i + 1);
            byte b = 0;
            b = charDecode(c1);
            if ((c2 >= '0') && (c2 <= '9')) {
                b += (c2 - '0');
            } else if ((c2 >= 'a') && (c2 <= 'f')) {
                b += (c2 - 'a' + ADD_VALUE);
            } else if ((c2 >= 'A') && (c2 <= 'F')) {
                b += (c2 - 'A' + ADD_VALUE);
            } else {
                throw new IllegalArgumentException(
                        ERROR_BAD_CHARACTER_IN_HEX_STRING);
            }
            baos.write(b);
        }
        return (baos.toByteArray());
    }

    private byte charDecode(char c) {
        byte b = 0;
        if ((c >= '0') && (c <= '9')) {
            b += ((c - '0') * MULTIPLY_VALUE);
        } else if ((c >= 'a') && (c <= 'f')) {
            b += ((c - 'a' + ADD_VALUE) * MULTIPLY_VALUE);
        } else if ((c >= 'A') && (c <= 'F')) {
            b += ((c - 'A' + ADD_VALUE) * MULTIPLY_VALUE);
        } else {
            throw new IllegalArgumentException(
                    ERROR_BAD_CHARACTER_IN_HEX_STRING);
        }
        return b;
    }
}
