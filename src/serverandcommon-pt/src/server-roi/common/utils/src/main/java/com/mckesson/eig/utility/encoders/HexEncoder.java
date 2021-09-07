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

package com.mckesson.eig.utility.encoders;

/**
 * Provides methods which Convert a byte array into a printable
 * format.(HexaDecimal).
 */
public class HexEncoder implements Encoder {

	private static final long serialVersionUID = 1L;

	/**
     * used for shift operation.
     */
    private static final int RIGHT_SHIFT = 4;

    /**
     * Random value.
     */
    private static final int INT_VALUE = 10;

    /**
     * HexaDecimal value used for encoding.
     */
    private static final byte HEX_VALUE = 0x0f;

    /**
     * Convert a byte array into a printable format containing a String of
     * hexadecimal digit characters (two per byte).
     *
     * @param bytes
     *            Byte array representation
     * @return strring after encoding.
     */
    public String encode(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (byte b : bytes)
		{
            sb.append(convertDigit(b >> RIGHT_SHIFT));
            sb.append(convertDigit(b & HEX_VALUE));
        }
        return sb.toString();
    }

    /**
     * [Private] Convert the specified value (0 .. 15) to the corresponding
     * hexadecimal digit.
     *
     * @param value
     *            Value to be converted
     * @return char character after conversion.
     */
    private char convertDigit(int value) {
        value &= HEX_VALUE;
        if (value >= INT_VALUE) {
            return ((char) (value - INT_VALUE + 'A'));
        }
        return ((char) (value + '0'));
    }

    /**
     * Gets the new instance of this class.
     *
     * @return new instance.
     */
    public Encoder newInstance() {
        return new HexEncoder();
    }
}
