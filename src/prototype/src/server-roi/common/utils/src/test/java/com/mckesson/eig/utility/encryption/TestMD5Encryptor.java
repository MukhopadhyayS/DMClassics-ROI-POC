/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.utility.encryption;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestMD5Encryptor extends TestEncryptor {

    public TestMD5Encryptor(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestMD5Encryptor.class, MD5Encryptor.class);
    }

    public Encryptor getEncryptor() {
        return new MD5Encryptor();
    }
    
    /**
     * @see com.mckesson.eig.utility.encryption.TestEncryptor#getNewInstance()
     * @return new instance of the encryptor
     */
    public Encryptor getNewInstance() {
         MD5Encryptor md5Encryptor = new MD5Encryptor();
         return (md5Encryptor.newInstance());
         
    }
}
