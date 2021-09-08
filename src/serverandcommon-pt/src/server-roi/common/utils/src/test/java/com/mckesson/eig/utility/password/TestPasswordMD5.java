/*
 *
 * Copyright (c) 2003 McKesson Corporation and/or one
 * of its subsidiaries. All Rights Reserved. Use of 
 * this material is governed by a license agreement.
 * This material contains confidential, proprietary
 * and trade secret information of McKesson Information
 * Solutions and is protected under United States and
 * international copyright and other intellectual 
 * property laws. Use, disclosure, reproduction, 
 * modification, distribution, or storage in a retrieval
 * system in any form or by any means is prohibited
 * without the prior express written permission of 
 * McKesson Information Solutions. 
 */

package com.mckesson.eig.utility.password;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestPasswordMD5 extends TestCase {

    private Password _password;

    private static final String OOPS = "OOPS";

    private static final String ENCRYPTED_OOPS = "C32552C05CAE0CE003B7C1550B0140AE";
    private static final String ENCRYPTED_LOWER_OOPS = "02C77002A0C646684B3325959FE147B2";
    private static final String ENCRYPTED_ADMIN = "73ACD9A5972130B75066C82595A1FAE3";
    private static final String ENCRYPTED_LOWER_ADMIN = "21232F297A57A5A743894A0E4A801FC3";
    private static final String ENCRYPTED_CLD = "FA6A84BC8B662A705F3F9C77B084287A";
    private static final String ENCRYPTED_NDXUP = "52EF7F86532652DC6CC596BB7AB98613";

    private static final String LOWER_OOPS = "oops";

    private static final String ADMIN = "ADMIN";

    private static final String LOWER_ADMIN = "admin";

    private static final String CLD = "CLD";

    private static final String NDXUP = "NDXUP";

    private static final String QCINDEX = "QCINDEX";
    private static final String ENCRYPTED_QCINDEX = "45B27A34C3C15EB9EEC775D658158B33";

    private static final String SCAN = "SCAN";
    private static final String ENCRYPTED_SCAN = "E0740ABFBC61BC2BE0BFA78AF61106AE";

    private static final String TESTDOC1 = "TESTDOC1";
    private static final String ENCRYPTED_TESTDOC1 = "2998AF3600C809D2AB4AB033E7481AB4";

    private static final String TESTDOC2 = "TESTDOC2";
    private static final String ENCRYPTED_TESTDOC2 = "78238262002AC9331E786E55D4B05676";

    private static final String TESTDOC3 = "TESTDOC3";
    private static final String ENCRYPTED_TESTDOC3 = "97AFDD5F175E437E10B698B3B4AEADBA";

    private static final String TESTDOC4 = "TESTDOC4";
    private static final String ENCRYPTED_TESTDOC4 = "04DC5670903E9ED04965F9E73BE6F504";

    private static final String PIN1234 = "1234";
    private static final String ENCRYPTED_PIN1234 = "81DC9BDB52D04DC20036DBD8313ED055";

    public TestPasswordMD5(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestPasswordMD5.class, PasswordMD5.class);
    }

    protected void setUp() throws Exception {
        _password = new PasswordMD5();
    }

    protected void tearDown() throws Exception {
        _password = null;
    }

    public void testConstructor() {
        assertNotNull(_password);
    }

    public void testEncrypt() {
        assertEquals(ENCRYPTED_OOPS, _password.encrypt(OOPS));
        assertEquals(ENCRYPTED_LOWER_OOPS, _password.encrypt(LOWER_OOPS));
        assertEquals(ENCRYPTED_ADMIN, _password.encrypt(ADMIN));
        assertEquals(ENCRYPTED_LOWER_ADMIN, _password.encrypt(LOWER_ADMIN));
        assertEquals(ENCRYPTED_CLD, _password.encrypt(CLD));
        assertEquals(ENCRYPTED_NDXUP, _password.encrypt(NDXUP));
        assertEquals(ENCRYPTED_QCINDEX, _password.encrypt(QCINDEX));
        assertEquals(ENCRYPTED_SCAN, _password.encrypt(SCAN));
        assertEquals(ENCRYPTED_TESTDOC1, _password.encrypt(TESTDOC1));
        assertEquals(ENCRYPTED_TESTDOC2, _password.encrypt(TESTDOC2));
        assertEquals(ENCRYPTED_TESTDOC3, _password.encrypt(TESTDOC3));
        assertEquals(ENCRYPTED_TESTDOC4, _password.encrypt(TESTDOC4));
        assertEquals(ENCRYPTED_PIN1234, _password.encrypt(PIN1234));
    }
    public void testNewInstance() {

        assertNotNull(_password.newInstance());
    }
    public void testWriteReplace() {
        try {
            File fMD5 = new File("PasswordMD5.tst");
            FileOutputStream fos = new FileOutputStream(fMD5);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_password);
            oos.close();
            fMD5.delete();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}
