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

import java.security.Provider;
import java.security.Security;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * @author dinoch
 * 
 *         NOTE To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class AESEncryptor {

    /**
     * LOG - Instance of Logger used to log messages.
     */
    private static final OCLogger LOG = new OCLogger(AESEncryptor.class);

    private static final int BYTE_ARRAY_LENGTH = 16;

    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
 
    private static final String AES_ENCRYPTION_ALGORITHM =  "AES";
   
    private Cipher _aesCipher;
    private String _providerName;
    private byte[] _initializationVector;
    private SecretKeySpec _keySpec;
    private final Map<String, Boolean> _checkedJce = Collections
            .synchronizedMap(new HashMap<String, Boolean>());
    private final Map<String, Boolean> _haveJce = Collections
            .synchronizedMap(new HashMap<String, Boolean>());

    public AESEncryptor() throws Exception {
        setProvider("SunJCE");
    }

    public void setKey(String passPhrase) {
        _keySpec = new SecretKeySpec(bytes(passPhrase), AES_ENCRYPTION_ALGORITHM);
    }
    
    private byte[] bytes(String passPhrase){
    	byte[] keyBytes = new byte[BYTE_ARRAY_LENGTH];
        byte[] b = passPhrase.getBytes();
        int len = b.length;
        if (len > keyBytes.length) {
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        return keyBytes;
    }

    public byte[] getIv() {
        _initializationVector = _aesCipher.getIV();
        return _initializationVector;
    }

    public void setIv(byte[] iv) {
        _initializationVector = new byte[BYTE_ARRAY_LENGTH];
        System.arraycopy(iv, 0, _initializationVector, 0, Math.min(iv.length, BYTE_ARRAY_LENGTH));
    }

    public String getProvider() {
        return _providerName;
    }

    public byte[] encrypt(String plainText) throws Exception {

        byte[] cipherText = encrypt(plainText.getBytes()); // ASCII encoding
        byte[] iVector = getIv();
        setIv(iVector);
        return cipherText;
    }

    public byte[] encrypt(byte[] plainText) throws Exception {
        createCipher();
        _aesCipher.init(Cipher.ENCRYPT_MODE, _keySpec);
        return _aesCipher.doFinal(plainText);
    }
    
    public String decryptToString(byte[] cipherText) throws Exception {

        byte[] decrypted = decrypt(cipherText);
        String plainText = new String(decrypted); // ascii encoding
        return plainText;
    }

    public byte[] decrypt(byte[] cipherText) throws Exception {
        createCipher();
        IvParameterSpec ivSpec = new IvParameterSpec(_initializationVector);
        _aesCipher.init(Cipher.DECRYPT_MODE, _keySpec, ivSpec);
        return _aesCipher.doFinal(cipherText);
    }

    protected void setProvider(String provider) throws Exception {
        if (!provider.equals(_providerName)) {
            _providerName = provider;
            createCipher();
        }
    }

    private void createCipher() throws Exception {
        if (!verifyJceProvider()) {
            throw new Exception("JCE Provider '" + _providerName
                    + "' was not found.");
        }
        _aesCipher = Cipher.getInstance(CIPHER_TRANSFORMATION, _providerName);
    }

    private boolean verifyJceProvider() {

        // Check if provider is installed., eg "IBMJCE", "SunJCE", "BC"
        if (!_checkedJce.containsKey(_providerName)) {
            if (Security.getProvider(_providerName) == null) {
                // Provider is not installed, try installing it.
                try {
                    Security.addProvider((Provider) Class
                            .forName(_providerName).newInstance());
                    _checkedJce.put(_providerName, new Boolean(true));
                    _haveJce.put(_providerName, new Boolean(true));
                } catch (Exception ex) {
                    LOG.error("Cannot install provider: " + ex.getMessage());
                    _checkedJce.put(_providerName, new Boolean(true));
                    _haveJce.put(_providerName, new Boolean(false));
                }
            } else {
                _checkedJce.put(_providerName, new Boolean(true));
                _haveJce.put(_providerName, new Boolean(true));
            }
        }
        return (_haveJce.get(_providerName)).booleanValue();
    }
    
    /**
     * 
     * @param plainText
     * @return
     * @throws Exception
     */
    public String encryptToString(String plainText) throws Exception {

        String cipherText = encryptToString(plainText.getBytes()); // ASCII encoding
        byte[] iVector = getIv();
        setIv(iVector);
        return cipherText;
    }

    /**
     * 
     * @param plainText
     * @return
     * @throws Exception
     */
    public String encryptToString(byte[] plainText) throws Exception {
        
        createCipher();
        if (null != _initializationVector && _initializationVector.length > 0) {
            
            IvParameterSpec ivParameterSpec = new IvParameterSpec(_initializationVector);
            _aesCipher.init(Cipher.ENCRYPT_MODE, _keySpec, ivParameterSpec);
        } else {
            _aesCipher.init(Cipher.ENCRYPT_MODE, _keySpec);
        }
        
        return Base64Utils.encode(_aesCipher.doFinal(plainText));
    }

    /**
     * 
     * @param encryptedText
     * @return
     * @throws Exception
     */
    public String decryptToString(String encryptedText) throws Exception {

        byte[] cipheredBytes = Base64Utils.decode(encryptedText);
        return decryptToString(cipheredBytes);
    }
    
    /**
     * 
     * @param plainPwd
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public String encrypt(String plainPwd, String key, String iv) throws Exception {
        setKey(key);
        setIv(iv.getBytes());
        return encryptToString(plainPwd);
        
    }
    
    /**
     * 
     * @param plainPwd
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public String decrypt(String plainPwd, String key, String iv) throws Exception {
        setKey(key);
        setIv(iv.getBytes());
        return decryptToString(plainPwd);
    }
}
