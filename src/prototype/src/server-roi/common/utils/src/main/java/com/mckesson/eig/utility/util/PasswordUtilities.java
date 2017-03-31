/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.utility.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 */
public final class PasswordUtilities {

    public static final int START_UPPER_ALPHA = 'A';
    public static final int END_UPPER_ALPHA = 'Z';
    public static final int START_LOWER_ALPHA = 'a';
    public static final int END_LOWER_ALPHA = 'z';
    public static final int START_NUMERIC = '0';
    public static final int END_NUMERIC = '9';
    public static final int START_SPECIAL = '!';
    public static final int END_SPECIAL = '@';
    public static final String EXCLUDE_PD_CHARS = 
        "\'\"\\/%&,<= _";   //the space is intentional
    
    public static final int NUM_CHOICES = 4;
    
    private PasswordUtilities() {
    }
    
    public static byte[] generateRandomPassword(int minLength, int maxLength) {
        SecureRandom generator = new SecureRandom();
        int pdLength = randomInt(generator, minLength, maxLength);
        byte[] pd = new byte[pdLength];
        
        // try to make sure no "words" will be generated
        for (int ii = 0; ii < pdLength; ii++) {
            byte temp; 
            switch (ii % NUM_CHOICES) {
                case 0 :
                    temp = (byte) randomUpperCaseChar(generator);                    
                    break;
                case 1 :
                    temp = (byte) randomSpecial(generator);                    
                    break;
                case 2 :
                    temp = (byte) randomLowerCaseChar(generator);                    
                    break;
                default :
                    temp = (byte) randomNumeric(generator);                    
                    break;
            }
            
            pd[ii] = temp;
        }        
        
        return pd;
    }
    
    public static int randomInt(Random rn, int low, int high) {
        int range = high - low + 1;
        int ii = Math.abs(rn.nextInt() % range);
        return low + ii;
    }
    
    public static int randomChar(Random rn) {
        return randomInt(rn, START_UPPER_ALPHA, END_LOWER_ALPHA);
    }
    
    public static int randomUpperCaseChar(Random rn) {
        return randomInt(rn, START_UPPER_ALPHA, END_UPPER_ALPHA);
    }
    
    public static int randomLowerCaseChar(Random rn) {
        return randomInt(rn, START_LOWER_ALPHA, END_LOWER_ALPHA);
    }

    public static int randomSpecial(Random rn) {
        int returnChar = 0;
        boolean keepGoing = true;
        
        do {
            returnChar = randomInt(rn, START_SPECIAL, END_SPECIAL);
            keepGoing = isSpecialChar(returnChar);
        } while (!keepGoing);    
        
        return returnChar;
    }

    public static int randomNumeric(Random rn) {
        return randomInt(rn, START_NUMERIC, END_NUMERIC);
    }
    
    protected static boolean isSpecialChar(int candidate) {
        boolean result = false;
        
        // any of these work
        result |= isInRange(candidate, '!', '.');
        result |= isInRange(candidate, ':', '@');
        result |= isInRange(candidate, '[', '`');
        result |= isInRange(candidate, '{', '~');
        
        // some symbols we don't want to mess with as passwords
        if (result) {
            StringBuilder sb = new StringBuilder();
            sb.append((char) candidate);
            result &= !EXCLUDE_PD_CHARS.contains(sb);
        }
        
        return result;
    }
    
    protected static boolean isInRange(int candidate, int min, int max) {
        return (candidate >= min) && (candidate <= max);
    }
}
