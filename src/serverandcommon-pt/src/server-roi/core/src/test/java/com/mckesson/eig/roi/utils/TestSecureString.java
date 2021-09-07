package com.mckesson.eig.roi.utils;

import junit.framework.TestCase;

public class TestSecureString extends TestCase {

    public void testGetClearText() {
        SecureString secureString = new SecureString("testpassword");
        assertNotNull(secureString.getClearText());
        assertEquals("testpassword", secureString.getClearText());
    }

    public void testGetClearTextForEmptyString() {
        SecureString secureString = new SecureString("");
        assertNotNull(secureString.getClearText());
        assertEquals("", secureString.getClearText());
    }

    public void testGetClearTextForNullString() {
        SecureString secureString = new SecureString(null);
        assertNotNull(secureString.getClearText());
        assertEquals("", secureString.getClearText());
    }

    public void testGetSecretText() {
        SecureString secureString = new SecureString("testpassword");
        assertNotNull(secureString.getSecretText());
        assertTrue(secureString.getSecretText().isReadOnly());
    }
}
