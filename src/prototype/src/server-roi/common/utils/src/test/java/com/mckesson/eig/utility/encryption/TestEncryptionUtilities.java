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

package com.mckesson.eig.utility.encryption;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

/**
 * @author Kenneth Partlow
 */
public class TestEncryptionUtilities extends UnitTest {

    public TestEncryptionUtilities() {
    }

    public static Test suite() {
        return new CoverageSuite(TestEncryptionUtilities.class, EncryptionUtilities.class);
    }
    public void testDES3EncryptionWithSecretKey() {
        byte[] keyDES3 = new String("A24ByteTestKey1234567890").getBytes();

        SecretKeySpec skeySpec = new SecretKeySpec(keyDES3, "DESede");

        Cipher cipher = EncryptionUtilities.getCipher("DESede");
        byte[] result = EncryptionUtilities.encrypt(cipher, skeySpec, "Hello World!".getBytes());

        assertEquals("Hello World!", new String(EncryptionUtilities.decrypt(
                cipher, skeySpec, result)));
    }
    public void testDES3EncrytpionWithKeyGenerator() {
        KeyGenerator kg = EncryptionUtilities.getKeyGenerator("DESede");
        Key key = kg.generateKey();

        Cipher cipher = EncryptionUtilities.getCipher("DESede");
        assertNotNull(cipher);

        byte[] data = "Hello World!".getBytes();
        assertEquals("Hello World!", new String(data));

        byte[] result = EncryptionUtilities.encrypt(cipher, key, data);
        System.out.println("Encrypted data: " + new String(result));

        byte[] original = EncryptionUtilities.decrypt(cipher, key, result);
        assertEquals("Hello World!", new String(original));
    }


    public void testGetCipherThatDoesNotExist() {
        try {
            EncryptionUtilities.getCipher("foo");
            fail();
        } catch (EncryptionException e) {
            e.getMessage();
        }
    }

    public void testGetKeyGeneratorThatDoesNotExist() {
        try {
            EncryptionUtilities.getKeyGenerator("foo");
            fail();
        } catch (EncryptionException e) {
            e.getMessage();
        }
    }

    public void testEncryptThrowsException() {
        try {
            EncryptionUtilities.encrypt(null, null, null);
            fail();
        } catch (EncryptionException e) {
            e.getMessage();
        }
    }

    public void testDecryptThrowsException() {
        try {
            EncryptionUtilities.decrypt(null, null, null);
            fail();
        } catch (EncryptionException e) {
            e.getMessage();
        }
    }

    public void verifyBytes(byte[] bytes1, byte[] bytes2) {
        for (byte element : bytes2) {
            System.out.print(element + ", ");
        }
        System.out.println();
        assertEquals(bytes1.length, bytes2.length);
        for (int i = 0; i < bytes1.length; i++) {
            assertEquals(bytes1[i], bytes2[i]);
        }
    }
}
