package com.mckesson.eig.utility.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionTestHelper {
    private static MessageDigest _messageDigest;

    public EncryptionTestHelper() {
        try {
            _messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(
                    "Could not find encryption algorithm: MD5");
        }
    }
    
    public byte[] encrypt(String data) {
        _messageDigest.update(data.getBytes());
        return _messageDigest.digest();
    }

    public byte[] encrypt(String data, String key) {
        _messageDigest.update(data.getBytes());
        return _messageDigest.digest(key.getBytes());
    }
}
