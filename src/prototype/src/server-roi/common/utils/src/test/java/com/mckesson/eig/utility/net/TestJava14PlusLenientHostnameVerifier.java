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
package com.mckesson.eig.utility.net;

import javax.net.ssl.SSLSession;
import junit.framework.TestCase;

/**
 * Test case to test the class Java14PlusLenientHostnameVerifier.
 */
public class TestJava14PlusLenientHostnameVerifier extends TestCase {

	/**
	 * Holds an instance of type Java14PlusLenientHostnameVerifier.
	 */
	private Java14PlusLenientHostnameVerifier _java14PlusLenientHostVerifier;

	/**
	 * Holds an instance of type SSLSession.
	 */
	private SSLSession _sslSession = null;

	/**
	 * Set up the test. Creates an instance of the class that needs to be
	 * tested.
	 * 
	 * @throws Exception
	 *             if the set up is not made properly.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		_java14PlusLenientHostVerifier = new Java14PlusLenientHostnameVerifier();
	}

	/**
	 * Test method that checks default constructor of the class.
	 */
	public void testJava14PlusLenientHostnameVerifier() {
		Java14PlusLenientHostnameVerifier java14PlusLenientHostNameVerifier = 
				new Java14PlusLenientHostnameVerifier();
		
		assertNotNull(java14PlusLenientHostNameVerifier);
	}

	/**
	 * Test method for verifying the hostname.
	 */
	public void testverify() {
		_java14PlusLenientHostVerifier = new Java14PlusLenientHostnameVerifier();
		final String message = "test for message";
		boolean value = true;
		boolean result = false;

		result = _java14PlusLenientHostVerifier.verify(message, _sslSession);
		assertEquals(result, value);
	}

	/**
	 * Test method for installing the host.
	 */
	public void testinstall() {
		_java14PlusLenientHostVerifier.install();
	}

	/**
	 * This will remove all the data associated with the test.
	 * 
	 * @throws Exception
	 *             if the tear down is not made properly.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
