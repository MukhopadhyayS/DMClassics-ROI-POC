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

import org.junit.Test;

/**
 * Test EIGObjectPool.
 * 
 * @author Ronnie Andrews, Jr.
 */
public class TestEIGObjectPool {

    /**
     * Test borrowObject with null.
     */
	@Test(expected = IllegalArgumentException.class)
    public void testBorrowObjectWithNull() {
		EIGObjectPool.getInstance().borrowObject(null);                
    }
	
	/**
     * Test borrowObject.
     */
	@Test
    public void testBorrowObject() {
		
		LocalTestPoolableObject obj = (LocalTestPoolableObject) 
			EIGObjectPool.getInstance().borrowObject(LocalTestPoolableObject.class);
		
		Assert.assertNotNull(obj);
    }
	
	/**
     * Test clearPools.
     */
	@Test
    public void testClearPools() throws Exception {
		
		EIGObjectPool.getInstance().clearPools();
		
		//ensure you can still get an object after clear
		LocalTestPoolableObject obj = (LocalTestPoolableObject) 
			EIGObjectPool.getInstance().borrowObject(LocalTestPoolableObject.class);
	
		Assert.assertNotNull(obj);
    }
	
	/**
     * Test closePools.
     */
	@Test
    public void testClosePools() throws Exception {
		
		EIGObjectPool.getInstance().closePools();
		
		//ensure you can still get an object after close
		LocalTestPoolableObject obj = (LocalTestPoolableObject) 
			EIGObjectPool.getInstance().borrowObject(LocalTestPoolableObject.class);
	
		Assert.assertNotNull(obj);
    }
	
	/**
     * Test returnObject with null.
     */
	@Test(expected = IllegalArgumentException.class)
    public void testReturnObjectWithNull() throws Exception {
		EIGObjectPool.getInstance().returnObject(null);                
    }
	
	/**
     * Test returnObject.
     */
	@Test
    public void testReturnObject() {
		
		LocalTestPoolableObject obj = (LocalTestPoolableObject) 
			EIGObjectPool.getInstance().borrowObject(LocalTestPoolableObject.class);
	
		Assert.assertNotNull(obj);
		
		EIGObjectPool.getInstance().returnObject(obj);
    }
}

/**
 * Test class for pooling.
 * 
 * @author Ronnie Andrews, Jr.
 */
class LocalTestPoolableObject implements PoolableObject {
	
	/**
	 * Default value.
	 */
	public static final long DEFAULT_VALUE = 0;
	
	/**
	 * Test value.
	 */
	private long _value = DEFAULT_VALUE;

	/**
	 * Reset value to clean state.
	 */
	public void resetObject() {
		this._value = DEFAULT_VALUE;
	}
	
	/**
	 * Get value.
	 * 
	 * @return
	 */
	public long getValue() {
		return this._value;
	}
	
	/**
	 * Set value.
	 * 
	 * @param value
	 */
	public void setValue(long value) {
		this._value = value;
	}	
}
