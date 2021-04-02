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

public final class Base64Utilities {

    private static final char[] BASE_64_CHARS = {'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '+', '/'};
    private static final int INT_NUMBER1 = 3;
    private static final int INT_NUMBER2 = 4;
    private static final int INT_NUMBER3 = 6;
    private static final int INT_NUMBER4 = 8;
    private static final int INT_NUMBER5 = 10;
    private static final int INT_NUMBER6 = 12;
    private static final int INT_NUMBER7 = 16;
    private static final int INT_NUMBER8 = 18;
    private static final int BYTE_NUMBER = 128;
    private static final byte HEX_BYTE_NUM1 = 0xf;
    private static final byte HEX_BYTE_NUM2 = 0x3;

    public static final int HEX_INT_NUM1 = 0xf0;
    public static final int HEX_INT_NUM2 = 0xfc;
    public static final int HEX_INT_NUM3 = 0xff;
    public static final int HEX_INT_NUM4 = 0x3f;
    public static final int HEX_INT_NUM5 = 0xc0;

    private static final char BASE_64_PAD_CHAR = '=';

    private static final byte[] DECODE_TABLE = new byte[BYTE_NUMBER];

    static {
        for (int i = 0; i < DECODE_TABLE.length; i++) {
            DECODE_TABLE[i] = Byte.MAX_VALUE; // 127
        }
        for (int i = 0; i < BASE_64_CHARS.length; i++) {
            DECODE_TABLE[BASE_64_CHARS[i]] = (byte) i; // 0 to 63
        }
    }

    private Base64Utilities() {
    }

    private static int decode(char[] ibuf, byte[] obuf, int wp) {
        int outlen = INT_NUMBER1;
        if (ibuf[INT_NUMBER1] == BASE_64_PAD_CHAR) {
            outlen = 2;
        }
        if (ibuf[2] == BASE_64_PAD_CHAR) {
            outlen = 1;
        }
        int b0 = DECODE_TABLE[ibuf[0]];
        int b1 = DECODE_TABLE[ibuf[1]];
        int b2 = DECODE_TABLE[ibuf[2]];
        int b3 = DECODE_TABLE[ibuf[INT_NUMBER1]];

        if (outlen == INT_NUMBER1) {
            obuf[wp++] = (byte) (b0 << 2 & HEX_INT_NUM2 | b1 >> INT_NUMBER2
                    & HEX_BYTE_NUM2);
            obuf[wp++] = (byte) (b1 << INT_NUMBER2 & HEX_INT_NUM1 | b2 >> 2
                    & HEX_BYTE_NUM1);
            obuf[wp] = (byte) (b2 << INT_NUMBER3 & HEX_INT_NUM5 | b3
                    & HEX_INT_NUM4);
            return INT_NUMBER1;
        } else if (outlen == 2) {
            obuf[wp++] = (byte) (b0 << 2 & HEX_INT_NUM2 | b1 >> INT_NUMBER2
                    & HEX_BYTE_NUM2);
            obuf[wp] = (byte) (b1 << INT_NUMBER2 & HEX_INT_NUM1 | b2 >> 2
                    & HEX_BYTE_NUM1);
            return 2;
        } else {
            obuf[wp] = (byte) (b0 << 2 & HEX_INT_NUM2 | b1 >> INT_NUMBER2
                    & HEX_BYTE_NUM2);
            return 1;
        }
    }

    public static byte[] decode(char[] data, int off, int len) {
        char[] ibuf = new char[INT_NUMBER2];
        int ibufcount = 0;
        byte[] obuf = new byte[len / INT_NUMBER2 * INT_NUMBER1 + INT_NUMBER1];
        int obufcount = 0;
        for (int i = off; i < off + len; i++) {
            char ch = data[i];
            if (ch == BASE_64_PAD_CHAR || ch < DECODE_TABLE.length
                    && DECODE_TABLE[ch] != Byte.MAX_VALUE) {
                ibuf[ibufcount++] = ch;
                if (ibufcount == ibuf.length) {
                    ibufcount = 0;
                    obufcount += decode(ibuf, obuf, obufcount);
                }
            }
        }
        if (obufcount == obuf.length) {
            return obuf;
        }
        byte[] ret = new byte[obufcount];
        System.arraycopy(obuf, 0, ret, 0, obufcount);
        return ret;
    }

    public static byte[] decode(String data) {
        char[] ibuf = new char[INT_NUMBER2];
        int ibufcount = 0;
        byte[] obuf = new byte[data.length() / INT_NUMBER2 * INT_NUMBER1
                + INT_NUMBER1];
        int obufcount = 0;
        for (int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            if (ch == BASE_64_PAD_CHAR || ch < DECODE_TABLE.length
                    && DECODE_TABLE[ch] != Byte.MAX_VALUE) {
                ibuf[ibufcount++] = ch;
                if (ibufcount == ibuf.length) {
                    ibufcount = 0;
                    obufcount += decode(ibuf, obuf, obufcount);
                }
            }
        }
        if (obufcount == obuf.length) {
            return obuf;
        }
        byte[] ret = new byte[obufcount];
        System.arraycopy(obuf, 0, ret, 0, obufcount);
        return ret;
    }

    /**
     * Returns base64 representation of specified byte array.
     */

    public static String encode(byte[] data) {
        return encode(data, 0, data.length);
    }

    /**
     * Returns base64 representation of specified byte array.
     */

    public static String encode(byte[] data, int off, int len) {
        if (len <= 0) {
            return "";
        }
        char[] out = new char[len / INT_NUMBER1 * INT_NUMBER2 + INT_NUMBER2];
        int rindex = off;
        int windex = 0;
        int rest = len - off;
        while (rest >= INT_NUMBER1) {
            int i = ((data[rindex] & HEX_INT_NUM3) << INT_NUMBER7)
                    + ((data[rindex + 1] & HEX_INT_NUM3) << INT_NUMBER4)
                    + (data[rindex + 2] & HEX_INT_NUM3);
            out[windex++] = BASE_64_CHARS[i >> INT_NUMBER8];
            out[windex++] = BASE_64_CHARS[(i >> INT_NUMBER6) & HEX_INT_NUM4];
            out[windex++] = BASE_64_CHARS[(i >> INT_NUMBER3) & HEX_INT_NUM4];
            out[windex++] = BASE_64_CHARS[i & HEX_INT_NUM4];
            rindex += INT_NUMBER1;
            rest -= INT_NUMBER1;
        }
        if (rest == 1) {
            int i = data[rindex] & HEX_INT_NUM3;
            out[windex++] = BASE_64_CHARS[i >> 2];
            out[windex++] = BASE_64_CHARS[(i << INT_NUMBER2) & HEX_INT_NUM4];
            out[windex++] = BASE_64_PAD_CHAR;
            out[windex++] = BASE_64_PAD_CHAR;
        } else if (rest == 2) {
            int i = ((data[rindex] & HEX_INT_NUM3) << INT_NUMBER4)
                    + (data[rindex + 1] & HEX_INT_NUM3);
            out[windex++] = BASE_64_CHARS[i >> INT_NUMBER5];
            out[windex++] = BASE_64_CHARS[(i >> INT_NUMBER2) & HEX_INT_NUM4];
            out[windex++] = BASE_64_CHARS[(i << 2) & HEX_INT_NUM4];
            out[windex++] = BASE_64_PAD_CHAR;
        }
        return new String(out, 0, windex);
    }

}
