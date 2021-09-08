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

package com.mckesson.eig.utility.encryption;

/**
 * @author dinoch
 * 
 * NOTE To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CipherData {
    
    private static final int INIT_VECTOR_SIZE = 16;

    private byte[] _initializationVector =  null;
    private byte[] _cipherText = null;

    public CipherData() {
        _initializationVector = new byte[INIT_VECTOR_SIZE];
    }
    
    public CipherData(int length) {
        _initializationVector = new byte[INIT_VECTOR_SIZE];
        _cipherText = new byte[length];
    }
    
    public CipherData(byte[] iv, byte[] data) {
        _initializationVector = new byte[INIT_VECTOR_SIZE];
        System.arraycopy(iv, 0, _initializationVector, 0, INIT_VECTOR_SIZE);
        _cipherText = new byte[data.length];
        System.arraycopy(data, 0, _cipherText, 0, data.length);
    }

    public CipherData(byte[] bothSections) {
        _initializationVector = new byte[INIT_VECTOR_SIZE];
        System.arraycopy(bothSections, 0, _initializationVector, 0, INIT_VECTOR_SIZE);
        int textLength = bothSections.length - INIT_VECTOR_SIZE;
        _cipherText = new byte[textLength];
        System.arraycopy(bothSections, INIT_VECTOR_SIZE, _cipherText, 0, textLength);
    }
    
    /**
     * @return Returns the _cipherText.
     */
    public byte[] getCipherText() {
        return _cipherText;
    }
    
    /**
     * @param text
     *            the _cipherText to set
     */
    public void setCipherText(byte[] text) {
        _cipherText = text;
    }
    
    /**
     * @return Returns the _initializationVector.
     */
    public byte[] getInitializationVector() {
        return _initializationVector;
    }
    
    /**
     * @param vector
     *            the _initializationVector to set
     */
    public void setInitializationVector(byte[] vector) {
        _initializationVector = vector;
    }
}
