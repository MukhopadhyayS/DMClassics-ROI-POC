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

 
import com.mckesson.eig.utility.encryption.AESEncryptor;
import com.mckesson.eig.utility.encryption.Base64Utils;
import com.mckesson.eig.utility.encryption.CipherData;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.exception.UsernameTokenException;

/**
 * 
 * Uses the AES algorithms to encrypt and decrypt text.  
 * Encryptors use the user’s login name and a timestamp to generate encrypted passwords; 
 * decryptors use the user’s login name and the same timestamp to decrypt the password.  
 * 
 */
public class AESStrategy implements PasswordEncryptionStrategy {
	
	private static final String KEY_SEPERATOR = "^?^";
    
    /**
     * Translate encrypted password into clear text
     * 
     * @param username    user id of the user
     * @param compoundEncryptedPassword    password of the user
     * @param timestamp   timestamp used to encrypt password
     * 
     * @return decrypted password
     * 
     * To decrypt:
     *
     *	1.	Split incoming compound encryption into timestamp and Base64 text
     *	2.	Decrypt base64 text using username and timestamp
     *	3.	Return decrypted text     
	 *
     */
    public String decryptPassword(String username, String compoundEncryptedPassword, 
            String timestamp) {
        String decryptedPassword = null;
                         
        try {
            
        	// split incoming password field
            String passwordTimestamp = getTimestampPartOfPass(compoundEncryptedPassword);
            String encryptedPasswordPart  = getEncryptedPartOfPass(compoundEncryptedPassword,
                                                                   passwordTimestamp.length());
            
            // set key
            String passPhrase = username + passwordTimestamp;
            
            byte[] rawPassword = Base64Utils.decode(encryptedPasswordPart);
            CipherData data = new CipherData(rawPassword);

            AESEncryptor decryptor = new AESEncryptor();
            decryptor.setIv(data.getInitializationVector());
            decryptor.setKey(passPhrase);
            decryptedPassword = decryptor.decryptToString(data.getCipherText());
            
        } catch (Exception ex) {
            // nope, just plain old didn't work
            throw new UsernameTokenException(
                      "Unable to decrypt password from client using AESStrategy", 
                      ex, ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
          
         }  
         
        return decryptedPassword;
    }

    /**
     *      
     * Encrypt password
     * 
     * @param username    user id of the user
     * @param password    password of the user
     * @param timestamp   timestamp used to encrypt password
     * 
     * @return decrypted password
     *
     * To encrypt:
	 *
	 *  1.	Set encryption key = username + timestamp (as UTZ string)
	 *  2.	Encrypt password using key
	 *  3.	Create Base64 text version of encrypted key
	 *  4.	Return compound string of timestamp + Base64 text 
	 *
     */
    public String encryptPassword(String username, String password, String timestamp) {

        String compoundCypheredPassword = null;
        
        try {
          
            AESEncryptor encryptor = new AESEncryptor();
            
            // set key for encryption
            encryptor.setKey(username + timestamp);

            byte[] encryptedItem = encryptor.encrypt(password);
            byte[] initVector = encryptor.getIv();
            byte[] vectorAndItem = new byte[encryptedItem.length + initVector.length];
                        
            System.arraycopy(initVector, 0, vectorAndItem, 0, initVector.length);

            System.arraycopy(encryptedItem, 
                             0, 
                             vectorAndItem, 
                             initVector.length, 
                             encryptedItem.length);

            compoundCypheredPassword = Base64Utils.encode(vectorAndItem);

            // add the timestamp to use to decryption time
            compoundCypheredPassword = timestamp + KEY_SEPERATOR + compoundCypheredPassword;

        } catch (Exception ex) {

            throw new UsernameTokenException("Unable to encrypt password using AESStrategy", ex, 
                                             ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
        } 
        
        return compoundCypheredPassword;
    }
    
    protected String getTimestampPartOfPass(String extendedPassword) {

        if (extendedPassword == null) {
            return StringUtilities.EMPTYSTRING;
        }

        /* This is only applied to the C# code for the encrypted password.
         * The timestamp which is used to encrypt the password can be as follows.
         * 
         *  Format 1 : yyyy-mm-ddThh:mm:ssZ     -> 2009-02-16T09:49:19Z     (20 chars)
         *  
         *  Format 2 : YYYY-MM-DDThh:mm:ss.sTZD -> 2009-02-16T09:50:48.507Z (24 chars)
         * 
         *  During stub calls the timestamp format which CXF uses is Format 2, and this is parsed
         *  using first occurrence of 'Z'
         * 
         *  In Java implementation, we encrypt the password to put some special characters between
         *  the time stamp and the encrypted password. 
         */
        int length = extendedPassword.indexOf(KEY_SEPERATOR);
        if (length < 0 ) {
        	length = (extendedPassword.length() > ENC_PWD_LENGTH)
                     ? extendedPassword.indexOf('Z') 
                     : PasswordEncryptionStrategy.UTZ_FORMAT_LENGTH - 1;
        }
        String timestamp = extendedPassword.substring(0, length + 1);
        return timestamp;
    }

    protected String getEncryptedPartOfPass(String extendedPassword, int startIndex) {

        String encrypted = extendedPassword.substring(startIndex);
        return encrypted;        
    }
}
