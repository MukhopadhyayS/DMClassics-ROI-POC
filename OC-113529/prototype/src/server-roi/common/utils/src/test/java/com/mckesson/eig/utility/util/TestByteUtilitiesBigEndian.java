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
package com.mckesson.eig.utility.util;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

/**
 * Created on Oct 25, 2002
 * 
 * Copyright 2002 McKesson Information Solutions
 * 
 * The copyright to the computer program(s) herein is the property of McKesson.
 * The program(s) may be used and/or copied only with the written permission of
 * McKesson or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 */
public class TestByteUtilitiesBigEndian extends UnitTest {

    private ByteUtilities _byteUtilities;

    public TestByteUtilitiesBigEndian(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestByteUtilitiesBigEndian.class,
                ByteUtilitiesBigEndian.class);
    }

    protected void setUp() throws Exception {
        _byteUtilities = new ByteUtilitiesBigEndian();
    }

    protected void tearDown() throws Exception {
        _byteUtilities = null;
    }

    public void testConstructor() {
        assertNotNull(_byteUtilities);
    }

    public void testOneBytesToInt() {
        byte[] b = {NINE};
        assertEquals(NINE, _byteUtilities.bytesToInt(b));
    }

    public void testOneByteToShort() {
        byte[] b = {NINE};
        assertEquals(NINE, _byteUtilities.bytesToShort(b));
    }

    public void testTwoBytesToInt() {
        byte[] b = {ONE, THREE};
        final int val = 259;
        assertEquals(val, _byteUtilities.bytesToInt(b));
    }

    public void testTwoBytesToShort() {
        byte[] b = {ONE, THREE};
        final int val = 259;
        assertEquals(val, _byteUtilities.bytesToShort(b));
    }

    public void testThreeBytesToInt() {
        byte[] b = {ONE, ONE, ONE};
        final int val = 65793;
        assertEquals(val, _byteUtilities.bytesToInt(b));
    }

    public void testThreeBytesToShort() {
        byte[] b = {ONE, TWO, THREE};
        final int val = 258;
        assertEquals(val, _byteUtilities.bytesToShort(b));
    }

    public void testThreeBytesWithStartIndexOfOne() {
        byte[] b = {ONE, TWO, THREE};
        final int val = 515;
        assertEquals(val, _byteUtilities.bytesToShort(b, ONE));
    }

    public void testThreeBytesWithStartIndexOfTwo() {
        byte[] b = {ONE, TWO, THREE};
        assertEquals(THREE, _byteUtilities.bytesToShort(b, TWO));
    }

    public void testThreeBytesWithStartIndexOfThree() {
        byte[] b = {ONE, TWO, THREE};
        try {
            _byteUtilities.bytesToShort(b, THREE);
            fail("ArrayIndexOutOfBounds should have been thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void testFourBytesToInt() {
        byte[] b = {ONE, TWO, THREE, FOUR};
        final int val = 16909060;
        assertEquals(val, _byteUtilities.bytesToInt(b));
    }

    public void testFourBytesToIntWithStartOfOne() {
        byte[] b = {ONE, TWO, THREE, FOUR};
        final int val = 131844;
        assertEquals(val, _byteUtilities.bytesToInt(b, ONE));
    }

    public void testFourBytesToIntWithStartOfTwo() {
        byte[] b = {ONE, TWO, THREE, FOUR};
        final int val = 772;
        assertEquals(val, _byteUtilities.bytesToInt(b, TWO));
    }

    public void testFourBytesToIntWithStartOfFour() {
        try {
            byte[] b = {ONE, TWO, THREE, FOUR};
            _byteUtilities.bytesToInt(b, FOUR);
            fail("ArrayIndexOutOfBounds should have been thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void testNegativeBytesToInt() {
        byte[] b = {NEG_ONE};
        final int val = 255;
        assertEquals(val, _byteUtilities.bytesToInt(b));
    }

    public void testSmallIntToBytes() {
        assertEquals(FOUR, _byteUtilities.bytesToInt(_byteUtilities
                .intToBytes(FOUR)));
    }

    public void testMediumIntToBytes() {
        final int val = 258;
        assertEquals(val, _byteUtilities.bytesToInt(_byteUtilities
                .intToBytes(val)));
    }

    public void testLargeIntToBytes() {
        final int val = 65794;
        assertEquals(val, _byteUtilities.bytesToInt(_byteUtilities
                .intToBytes(val)));
    }

    public void testExtraLargeIntToBytes() {
        final int val = 16843010;
        assertEquals(val, _byteUtilities.bytesToInt(_byteUtilities
                .intToBytes(val)));
    }

    public void testMaxIntToBytes() {
        assertEquals(Integer.MAX_VALUE, _byteUtilities
                .bytesToInt(_byteUtilities.intToBytes(Integer.MAX_VALUE)));
    }

    public void testNegativeIntToBytes() {
        final int val = -4837;
        assertEquals(val, _byteUtilities.bytesToInt(_byteUtilities
                .intToBytes(val)));
    }

    public void testGetByte() {
        byte[] b = {ONE, NEG_TWO, THREE, FOUR};
        assertEquals(ONE, _byteUtilities.getByte(b, ZERO));
        assertEquals(NEG_TWO, _byteUtilities.getByte(b, ONE));
    }

    public void testGetByteRange() {
        byte[] b = {ZERO, ONE, TWO, THREE, FOUR};
        byte[] b1 = {TWO, THREE};

        assertEquals(b1.length, _byteUtilities.getByteRange(b, TWO, TWO).length);

        for (int i = 0; i < b1.length; i++) {
            assertEquals(b[b1.length + i], b1[i]);
        }
    }

    public void testLittleEndianOrder() {
        _byteUtilities = new ByteUtilitiesLittleEndian();
        final int byte1 = 0x27;
        final int byte2 = 0x43;
        final int byte3 = 0x31;
        final int byte4 = 0x00;
        final int val = 0x314327;
        byte[] buffer = {byte1, byte2, byte3, byte4};
        assertEquals(val, _byteUtilities.bytesToInt(buffer));
    }

    public void testExtractString() {
        final int byte1 = 0x27;
        final int byte2 = 0x43;
        final int byte3 = 0x31;
        final int byte4 = 0x00;
        byte[] b = {byte1, byte2, byte3, byte4};
        String result = _byteUtilities.extractString(b, ONE, TWO);
        assertEquals("C1", result);
    }

    public void testCopy() {
        final int byte1 = 0x43;
        final int byte2 = 0x31;
        byte[] b1 = {byte1, byte2};
        byte[] buffer = new byte[TWO];
        _byteUtilities.copy("C1", buffer, ZERO, TWO);
        for (int i = 0; i < buffer.length; i++) {
            assertEquals(b1[i], buffer[i]);
        }

    }

}
