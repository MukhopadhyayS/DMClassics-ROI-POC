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
package com.mckesson.eig.utility.decoders;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.encryption.EncryptionTestHelper;
import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestHexDecoder extends TestCase {

    private Decoder _decoder;

    private static final String ENCRYPTEDUPPERCASEOOPS = "C32552C05CAE0CE003B7C1550B0140AE";
    private static final String ENCRYPTEDLOWERCASEOOPS = "02C77002A0C646684B3325959FE147B2";
    private static final String ENCRYPTEDUPPERCASEADMIN = "73ACD9A5972130B75066C82595A1FAE3";
    private static final String ENCRYPTEDLOWERCASEADMIN = "21232F297A57A5A743894A0E4A801FC3";
    private static final String ENCRYPTEDCLD = "FA6A84BC8B662A705F3F9C77B084287A";
    private static final String ENCRYPTEDNDXUP = "52EF7F86532652DC6CC596BB7AB98613";

    private static final String ODDCOUNT = "0";
    private static final String INVALIDEDUPPERCASEFIRST = "AG";
    private static final String INVALIDUPPERCASESECOND = "GA";
    private static final String INVALIDLOWERCASEFIRST = "Ag";
    private static final String INVALIDLOWERCASESECOND = "gA";
    private static final String INVALIDBANDFIRST = "!A";
    private static final String INVALIDBANGSECOND = "A!";
    private static final String INVALIDCOLONFIRST = ":A";
    private static final String INVALIDCOLONSECOND = "A:";
    private static final String INVALIDSLASHFIRST = "_A";
    private static final String INVALIDSLASHSECOND = "A_";
    private static final String INVALIDTILDEFIRST = "~A";
    private static final String INVALIDTILDESECOND = "A~";

    private static byte[] _upperCaseOOPS;
    private static byte[] _lowerCaseOOPS;
    private static byte[] _upperCaseADMIN;
    private static byte[] _lowerCaseADMIN;
    private static byte[] _bytesCLD;
    private static byte[] _bytesNDXUP;
    
    static {
        EncryptionTestHelper md = new EncryptionTestHelper();
   
        _upperCaseOOPS = md.encrypt("OOPS");
        _lowerCaseOOPS = md.encrypt("oops");
        _upperCaseADMIN = md.encrypt("ADMIN");
        _lowerCaseADMIN = md.encrypt("admin");
        _bytesCLD = md.encrypt("CLD");
        _bytesNDXUP = md.encrypt("NDXUP");
    }

    public TestHexDecoder(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestHexDecoder.class, HexDecoder.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        _decoder = new HexDecoder();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        _decoder = null;
    }

    public void assertEquals(byte[] one, byte[] two) {
        assertTrue(java.util.Arrays.equals(one, two));
    }

    public void testDecode() {
        assertEquals(_upperCaseOOPS, _decoder.decode(ENCRYPTEDUPPERCASEOOPS));
        assertEquals(_lowerCaseOOPS, _decoder.decode(ENCRYPTEDLOWERCASEOOPS));
        assertEquals(_upperCaseADMIN, _decoder.decode(ENCRYPTEDUPPERCASEADMIN));
        assertEquals(_lowerCaseADMIN, _decoder.decode(ENCRYPTEDLOWERCASEADMIN));
        assertEquals(_bytesCLD, _decoder.decode(ENCRYPTEDCLD));
        assertEquals(_bytesNDXUP, _decoder.decode(ENCRYPTEDNDXUP));

        // _decoder.decode(ENCRYPTED_LOWER);
    }

    public void testOddNumberOfHexBytes() {
        testIllegalArgumentString(ODDCOUNT);
    }

    public void testInvalidUpperCaseGFirst() {
        testIllegalArgumentString(INVALIDEDUPPERCASEFIRST);
    }

    public void testInvalidUpperCaseGSecond() {
        testIllegalArgumentString(INVALIDUPPERCASESECOND);
    }

    public void testInvalidLowerCaseGFirst() {
        testIllegalArgumentString(INVALIDLOWERCASEFIRST);
    }

    public void testInvalidLowerCaseGSecond() {
        testIllegalArgumentString(INVALIDLOWERCASESECOND);
    }

    public void testInvalidBangFirst() {
        testIllegalArgumentString(INVALIDBANDFIRST);
    }

    public void testInvalidBangSecond() {
        testIllegalArgumentString(INVALIDBANGSECOND);
    }

    public void testInvalidColonFirst() {
        testIllegalArgumentString(INVALIDCOLONFIRST);
    }

    public void testInvalidColonSecond() {
        testIllegalArgumentString(INVALIDCOLONSECOND);
    }

    public void testInvalidSlashFirst() {
        testIllegalArgumentString(INVALIDSLASHFIRST);
    }

    public void testInvalidSlashSecond() {
        testIllegalArgumentString(INVALIDSLASHSECOND);
    }

    public void testInvalidTildeFirst() {
        testIllegalArgumentString(INVALIDTILDEFIRST);
    }

    public void testInvalidTildeSecond() {
        testIllegalArgumentString(INVALIDTILDESECOND);
    }

    public void testLowerCase() {
        String lower = ENCRYPTEDUPPERCASEOOPS.toLowerCase();
        assertEquals(_upperCaseOOPS, _decoder.decode(lower));
    }

    public void testIllegalArgumentString(String string) {
        try {
            _decoder.decode(string);
            fail();
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }
}
