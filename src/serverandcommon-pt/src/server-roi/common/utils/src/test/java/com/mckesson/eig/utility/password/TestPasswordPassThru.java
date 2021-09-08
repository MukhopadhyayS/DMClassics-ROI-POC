/*
 *
 * Copyright (c) 2003 McKesson Corporation and/or one
 * of its subsidiaries. All Rights Reserved. Use of 
 * this material is governed by a license agreement.
 * This material contains confidential, proprietary
 * and trade secret information of McKesson Information
 * Solutions and is protected under United States and
 * international copyright and other intellectual 
 * property laws. Use, disclosure, reproduction, 
 * modification, distribution, or storage in a retrieval
 * system in any form or by any means is prohibited
 * without the prior express written permission of 
 * McKesson Information Solutions. 
 */
 
package com.mckesson.eig.utility.password;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestPasswordPassThru extends TestCase {

	private Password _encryptor;
	
	public TestPasswordPassThru(String arg0) {
		super(arg0);
	}

	public static Test suite() {
		return new CoverageSuite(
			TestPasswordPassThru.class,
			PasswordPassThru.class);
	}

	protected void setUp() throws Exception {
		_encryptor = new PasswordPassThru();
	}

	protected void tearDown() throws Exception {
		_encryptor = null;
	}

	public void testEncryptWhenPasswordIsNull() {
		assertNull(_encryptor.encrypt(null));
	}
    public void testNewInstance() {
        
        assertNotNull(_encryptor.newInstance());
    }
	
	public void testEncrypt() {
		assertEquals("", _encryptor.encrypt(""));
		assertEquals("a", _encryptor.encrypt("a"));
		
		assertNotEquals(" ", "");
		assertNotEquals("a", "b");
	}
	
	public void assertNotEquals(String test, String compare) {
		assertFalse(_encryptor.encrypt(test).equals(compare));
	}
}
