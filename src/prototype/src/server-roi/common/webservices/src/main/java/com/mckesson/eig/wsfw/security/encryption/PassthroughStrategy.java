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
public class PassthroughStrategy implements PasswordEncryptionStrategy {

    /* (non-Javadoc)
     * 
     */
    public String decryptPassword(String username, String encryptedPassword, String timestamp) {
        // TODO Auto-generated method stub
        return encryptedPassword;
    }

    /**
     * 
     * @see com.mckesson.eig.wsfw.security.encryption.PasswordEncryptionStrategy#
     *      encryptPassword(java.lang.String, java.lang.String, java.lang.String)
     */
    public String encryptPassword(String username, String password, String timestamp) {
        return password;
    }
}
