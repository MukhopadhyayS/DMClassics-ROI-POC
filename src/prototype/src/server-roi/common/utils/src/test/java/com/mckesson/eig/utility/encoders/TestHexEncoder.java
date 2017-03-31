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

package com.mckesson.eig.utility.encoders;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestHexEncoder extends TestCase {

    private Encoder _encoder;

    private static final String ENCRYPTED_OOPS = "C32552C05CAE0CE003B7C1550B0140AE";
    private static final String ENCRYPTED_OOPS_1 = "02C77002A0C646684B3325959FE147B2";
    private static final String ENCRYPTED_ADMIN = "73ACD9A5972130B75066C82595A1FAE3";
    private static final String ENCRYPTED_ADMIN_1 = "21232F297A57A5A743894A0E4A801FC3";
    private static final String ENCRYPTED_CLD = "FA6A84BC8B662A705F3F9C77B084287A";
    private static final String ENCRYPTED_NDXUP = "52EF7F86532652DC6CC596BB7AB98613";

    private static final byte[] BYTES_OOPS = {-61, 37, 82, -64, 92, -82, 12,
            -32, 3, -73, -63, 85, 11, 1, 64, -82};

    private static final byte[] BYTES_OOPS_1 = {2, -57, 112, 2, -96, -58, 70,
            104, 75, 51, 37, -107, -97, -31, 71, -78};

    private static final byte[] BYTES_ADMIN = {115, -84, -39, -91, -105, 33,
            48, -73, 80, 102, -56, 37, -107, -95, -6, -29};

    private static final byte[] BYTES_ADMIN_1 = {33, 35, 47, 41, 122, 87, -91,
            -89, 67, -119, 74, 14, 74, -128, 31, -61};

    private static final byte[] BYTES_CLD = {-6, 106, -124, -68, -117, 102, 42,
            112, 95, 63, -100, 119, -80, -124, 40, 122};

    private static final byte[] BYTES_NDXUP = {82, -17, 127, -122, 83, 38, 82,
            -36, 108, -59, -106, -69, 122, -71, -122, 19};

    public TestHexEncoder(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestHexEncoder.class, HexEncoder.class);
    }

    protected void setUp() throws Exception {
        _encoder = new HexEncoder();
    }

    protected void tearDown() throws Exception {
        _encoder = null;
    }

    public void testEncoder() {
        encoderValueTest(_encoder);
    }
    
    public void testEncoderNewInstance() {
        Encoder myencoder = null;
        assertNull(myencoder);
        myencoder = _encoder.newInstance();
        assertNotNull(myencoder);
        encoderValueTest(myencoder);
    }
    
    public void encoderValueTest(Encoder encoder) {
        assertEquals(ENCRYPTED_OOPS, encoder.encode(BYTES_OOPS));
        assertEquals(ENCRYPTED_OOPS_1, encoder.encode(BYTES_OOPS_1));
        assertEquals(ENCRYPTED_ADMIN, encoder.encode(BYTES_ADMIN));
        assertEquals(ENCRYPTED_ADMIN_1, encoder.encode(BYTES_ADMIN_1));
        assertEquals(ENCRYPTED_CLD, encoder.encode(BYTES_CLD));
        assertEquals(ENCRYPTED_NDXUP, encoder.encode(BYTES_NDXUP));
    }
 
}
