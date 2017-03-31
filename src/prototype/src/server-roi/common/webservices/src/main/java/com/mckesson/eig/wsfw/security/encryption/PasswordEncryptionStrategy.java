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

package com.mckesson.eig.wsfw.security.encryption;

/**
 *
 */
public interface PasswordEncryptionStrategy {
	
	/*
	 * length of the UTZ time string format: yyyy-mm-ddThh:mm:ssZ 
	 * 	e.g. 2008-08-18T19:54:50Z
	 */
    int UTZ_FORMAT_LENGTH = 20;

    /**
     * Indicates the encrypted (UTZ time string format[yyyy-mm-ddThh:mm:ssZ]) password length.
     */
    int ENC_PWD_LENGTH = 64;
    
    public static final String DATE_TIME_FORMAT = "MM/dd/yyyy hh:mm:ss";

    /**
     * Used to decrypt the password 
     * 
     * @param username
     *          Denotes the username who logged-in
     * @param password
     *          Denotes the password
     * @param timestamp
     *          Denotes the header timestamp
     *
     * @return clear text Password
     */
    String decryptPassword(String username, String encryptedPassword, String timestamp);

    /**
     * Used to encrypt the password 
     * 
     * @param username
     *          Denotes the username who logged-in
     * @param password
     *          Denotes the password
     * @param timestamp
     *          Denotes the header timestamp
     *
     * @return Encrypted Password
     */
    String encryptPassword(String username, String password, String timestamp);
}
