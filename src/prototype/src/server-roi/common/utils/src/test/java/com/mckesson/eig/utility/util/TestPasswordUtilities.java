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

package com.mckesson.eig.utility.util;

import java.util.Random;

import junit.framework.TestCase;

public class TestPasswordUtilities extends TestCase {

    protected static final int ZERO = 0;

    protected static final int TWO_HUNDRED = 200;

    protected static final int SIX = 6;

    protected static final int TWELVE = 12;

    public TestPasswordUtilities(String name) {
        super(name);
    }

    public void testLittleFish() {
        Random generator = new Random();

        assertTrue(isInRange(PasswordUtilities.randomInt(generator, ZERO, TWO_HUNDRED),
                ZERO, TWO_HUNDRED));
        assertTrue(isInRange(PasswordUtilities.randomChar(generator),
                PasswordUtilities.START_UPPER_ALPHA, PasswordUtilities.END_LOWER_ALPHA));
        assertTrue(isInRange(PasswordUtilities.randomUpperCaseChar(generator),
                PasswordUtilities.START_UPPER_ALPHA, PasswordUtilities.END_UPPER_ALPHA));
        assertTrue(isInRange(PasswordUtilities.randomLowerCaseChar(generator),
                PasswordUtilities.START_LOWER_ALPHA, PasswordUtilities.END_LOWER_ALPHA));
        assertTrue(isInRange(PasswordUtilities.randomSpecial(generator),
                PasswordUtilities.START_SPECIAL, PasswordUtilities.END_SPECIAL));
        assertTrue(isInRange(PasswordUtilities.randomNumeric(generator),
                PasswordUtilities.START_NUMERIC, PasswordUtilities.END_NUMERIC));

    }

    public void testPasswordGenerator() {
        byte[] testPassword = PasswordUtilities.generateRandomPassword(SIX, TWELVE);

        assertNotNull(testPassword);
        assertTrue(testPassword.length >= SIX);
        assertTrue(testPassword.length <= TWELVE);

        assertTrue(atLeastOneExistsInRange(testPassword,
                PasswordUtilities.START_UPPER_ALPHA, PasswordUtilities.END_UPPER_ALPHA));
        assertTrue(atLeastOneExistsInRange(testPassword,
                PasswordUtilities.START_LOWER_ALPHA, PasswordUtilities.END_LOWER_ALPHA));
        assertTrue(atLeastOneExistsInRange(testPassword,
                PasswordUtilities.START_SPECIAL, PasswordUtilities.END_SPECIAL));
        assertTrue(atLeastOneExistsInRange(testPassword,
                PasswordUtilities.START_NUMERIC, PasswordUtilities.END_NUMERIC));
    }

    public void testisSpecialChar() {
        assertTrue(PasswordUtilities.isSpecialChar('!'));
        assertTrue(PasswordUtilities.isSpecialChar(':'));
        assertTrue(PasswordUtilities.isSpecialChar('['));
        assertTrue(PasswordUtilities.isSpecialChar('{'));
        assertFalse(PasswordUtilities.isSpecialChar('\''));
        assertFalse(PasswordUtilities.isSpecialChar('\"'));
        assertFalse(PasswordUtilities.isSpecialChar('\\'));
        assertFalse(PasswordUtilities.isSpecialChar('/'));
        assertFalse(PasswordUtilities.isSpecialChar('%'));
        assertFalse(PasswordUtilities.isSpecialChar('&'));
        assertFalse(PasswordUtilities.isSpecialChar(','));
        assertFalse(PasswordUtilities.isSpecialChar('<'));
        assertFalse(PasswordUtilities.isSpecialChar('='));
        assertFalse(PasswordUtilities.isSpecialChar('_'));
    }

    protected boolean isInRange(int candidate, int min, int max) {
        return (candidate >= min) && (candidate <= max);
    }

    protected boolean atLeastOneExistsInRange(byte[] candidates, int min, int max) {
        boolean exists = false;
        for (byte candidate : candidates) {
            if (isInRange(candidate, min, max)) {
                exists = true;
                break;
            }
        }
        return exists;
    }
}
