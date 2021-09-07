/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.encryption;

public final class Base64Utils {

    private static final int BYTE_TABLE_LENGTH = 128;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int HEX_FF = 0xFF;
    private static final int HEX_3F = 0x3F;
    private static final int HEX_FC = 0xfc;
    private static final int HEX_F0 = 0xf0;
    private static final int HEX_C0 = 0xc0;
    private static final byte HEX_F = 0xf;
    private static final byte HEX_3 = 0x3;
    private static final int SIXTEEN = 16;
    private static final int EIGHT = 8;
    private static final int EIGHTTEEN = 18;
    private static final int TWELVE = 12;
    private static final int SIX = 6;
    private static final int TEN = 10;

 

    private static final char[] S_BASE64CHAR = {'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '+', '/'};
    private static final char S_BASE64PAD = '=';
    private static final byte[] S_DECODETABLE = new byte[BYTE_TABLE_LENGTH];
    static {
        for (int i = 0; i < S_DECODETABLE.length; i++) {
            S_DECODETABLE[i] = Byte.MAX_VALUE; // 127
        }
        for (int i = 0; i < S_BASE64CHAR.length; i++) { // 0 to 63
            S_DECODETABLE[S_BASE64CHAR[i]] = (byte) i;
        }
    }

    private Base64Utils() {
    }
    
    
    public static byte[] decode(char[] data, int off, int len) throws Exception {
        char[] ibuf = new char[FOUR];
        int ibufcount = 0;
        byte[] obuf = new byte[len / FOUR * THREE + THREE];
        int obufcount = 0;
        for (int i = off; i < off + len; i++) {
            char ch = data[i];
            if (ch == S_BASE64PAD || ch < S_DECODETABLE.length
                    && S_DECODETABLE[ch] != Byte.MAX_VALUE) {
                ibuf[ibufcount++] = ch;
                if (ibufcount == ibuf.length) {
                    ibufcount = 0;
                    obufcount += decode0(ibuf, obuf, obufcount);
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
     * 
     */
    public static byte[] decode(String stringData) throws Exception {
        char[] data = stringData.toCharArray();
        
        return decode(data, 0, data.length);
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
        char[] out = new char[len / THREE * FOUR + FOUR];
        int rindex = off;
        int windex = 0;
        int rest = len - off;
        while (rest >= THREE) {
            int i = ((data[rindex] & HEX_FF) << SIXTEEN)
                    + ((data[rindex + 1] & HEX_FF) << EIGHT)
                    + (data[rindex + 2] & HEX_FF);
            out[windex++] = S_BASE64CHAR[i >> EIGHTTEEN];
            out[windex++] = S_BASE64CHAR[(i >> TWELVE) & HEX_3F];
            out[windex++] = S_BASE64CHAR[(i >> SIX) & HEX_3F];
            out[windex++] = S_BASE64CHAR[i & HEX_3F];
            rindex += THREE;
            rest -= THREE;
        }
        if (rest == 1) {
            int i = data[rindex] & HEX_FF;
            out[windex++] = S_BASE64CHAR[i >> 2];
            out[windex++] = S_BASE64CHAR[(i << FOUR) & HEX_3F];
            out[windex++] = S_BASE64PAD;
            out[windex++] = S_BASE64PAD;
        } else if (rest == 2) {
            int i = ((data[rindex] & HEX_FF) << EIGHT) + (data[rindex + 1] & HEX_FF);
            out[windex++] = S_BASE64CHAR[i >> TEN];
            out[windex++] = S_BASE64CHAR[(i >> FOUR) & HEX_3F];
            out[windex++] = S_BASE64CHAR[(i << 2) & HEX_3F];
            out[windex++] = S_BASE64PAD;
        }
        return new String(out, 0, windex);
    }

    private static int decode0(char[] ibuf, byte[] obuf, int wp)
            throws Exception {
        int outlen = THREE;
        if (ibuf[THREE] == S_BASE64PAD) {
            outlen = 2;
        }
        if (ibuf[2] == S_BASE64PAD) {
            outlen = 1;
        }
        int b0 = S_DECODETABLE[ibuf[0]];
        int b1 = S_DECODETABLE[ibuf[1]];
        int b2 = S_DECODETABLE[ibuf[2]];
        int b3 = S_DECODETABLE[ibuf[THREE]];
        switch (outlen) {
            case 1 :
                obuf[wp] = (byte) (b0 << 2 & HEX_FC | b1 >> FOUR & HEX_3);
                return 1;
            case 2 :
                obuf[wp++] = (byte) (b0 << 2 & HEX_FC | b1 >> FOUR & HEX_3);
                obuf[wp] = (byte) (b1 << FOUR & HEX_F0 | b2 >> 2 & HEX_F);
                return 2;
            case THREE :
                obuf[wp++] = (byte) (b0 << 2 & HEX_FC | b1 >> FOUR & HEX_3);
                obuf[wp++] = (byte) (b1 << FOUR & HEX_F0 | b2 >> 2 & HEX_F);
                obuf[wp] = (byte) (b2 << SIX & HEX_C0 | b3 & HEX_3F);
                return THREE;
            default :
                throw new Exception("internalError00");
        }
    }

}
