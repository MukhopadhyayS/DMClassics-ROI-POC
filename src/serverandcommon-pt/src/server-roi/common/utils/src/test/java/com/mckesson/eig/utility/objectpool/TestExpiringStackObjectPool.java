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

package com.mckesson.eig.utility.objectpool;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test ExpiringStackObjectPool.
 * 
 * @author Ronnie Andrews, Jr.
 */
public class TestExpiringStackObjectPool implements PoolableObject {
	
	/**
	 * Instance to use for testing.
	 */
	private ExpiringStackObjectPool _instance;
	
	/**
	 * Setup.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this._instance = new ExpiringStackObjectPool(
				new ClassPoolableObjectFactory(TestExpiringStackObjectPool.class));
	}

	/**
	 * Tear down.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
		if (this._instance != null) {
			
			this._instance.clear();
			this._instance.close();
			this._instance = null;
		}
	}

	/**
     * Test borrowObject.
     * 
     * @throws java.lang.Exception
     */
	@Test
    public void testBorrowObject() throws Exception {
		
		TestExpiringStackObjectPool obj = (TestExpiringStackObjectPool) 
			this._instance.borrowObject();
		
		Assert.assertNotNull(obj);
    }

	/**
     * Test returnObject.
     * 
     * @throws java.lang.Exception
     */
	@Test
    public void testReturnObject() throws Exception {
		
		TestExpiringStackObjectPool obj = (TestExpiringStackObjectPool) 
			this._instance.borrowObject();
		
		Assert.assertNotNull(obj);
		
		this._instance.returnObject(obj);
    }
	
	public void resetObject() {
	    this._instance = null;
    }
}
