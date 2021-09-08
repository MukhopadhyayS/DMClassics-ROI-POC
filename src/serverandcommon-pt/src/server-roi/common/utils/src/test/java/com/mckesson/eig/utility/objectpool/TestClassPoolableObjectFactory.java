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
 * Test ClassPoolableObjectFactory.
 * 
 * @author Ronnie Andrews, Jr.
 */
public class TestClassPoolableObjectFactory {
	
	/**
	 * Dummy value.
	 */
	private static final int DUMMY_VALUE = 5;
	
	/**
	 * Instance to use for testing.
	 */
	private ClassPoolableObjectFactory _instance;
	
	/**
	 * Setup.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this._instance = new ClassPoolableObjectFactory(TestPoolableObject.class);
	}

	/**
	 * Tear down.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this._instance = null;
	}

    /**
     * Test constructor with null.
     */
	@Test(expected = IllegalArgumentException.class)
    public void testContructorWithNull() {
    	this._instance = new ClassPoolableObjectFactory(null);                
    }
	
	/**
     * Test constructor with non-poolable class.
     */
	@Test(expected = IllegalArgumentException.class)
    public void testContructorWithNonPoolableClass() {
    	this._instance = new ClassPoolableObjectFactory(Long.class);                
    }
	
	/**
     * Test activateObject with null.
     */
	@Test(expected = IllegalArgumentException.class)
    public void testActivateObjectWithNull() throws Exception {
    	this._instance.activateObject(null);                
    }
	
	/**
     * Test activateObject.
     */
	@Test
    public void testActivateObject() throws Exception {
		
		TestPoolableObject obj = new TestPoolableObject();
    	this._instance.activateObject(obj);
    	Assert.assertEquals(0, obj.getValue());
    }
	
	/**
     * Test destroyObject with null.
     */
	@Test(expected = IllegalArgumentException.class)
    public void testDestroyObjectWithNull() throws Exception {
    	this._instance.destroyObject(null);                
    }
	
	/**
     * Test destroyObject.
     */
	@Test
    public void testDestroyObject() throws Exception {
		
		TestPoolableObject obj = new TestPoolableObject();
		obj.setValue(DUMMY_VALUE);
    	this._instance.destroyObject(obj);
    	Assert.assertEquals(TestPoolableObject.DEFAULT_VALUE, obj.getValue());
    }
	
	/**
     * Test makeObject.
     */
	@Test
    public void testMakeObject() throws Exception {
		
		TestPoolableObject obj = (TestPoolableObject) this._instance.makeObject();
    	Assert.assertNotNull(obj);
    }
	
	/**
     * Test passivateObject with null.
     */
	@Test(expected = IllegalArgumentException.class)
    public void testPassivateObjectWithNull() throws Exception {
    	this._instance.passivateObject(null);                
    }
	
	/**
     * Test passivateObject.
     */
	@Test
    public void testPassivateObject() throws Exception {
		
		TestPoolableObject obj = new TestPoolableObject();
		obj.setValue(DUMMY_VALUE);
    	this._instance.passivateObject(obj);
    	Assert.assertEquals(TestPoolableObject.DEFAULT_VALUE, obj.getValue());
    }
	
	/**
     * Test validateObject with null.
     */
	@Test(expected = IllegalArgumentException.class)
    public void testValidateObjectWithNull() throws Exception {
    	this._instance.validateObject(null);                
    }
	
	/**
     * Test validateObject.
     */
	@Test
    public void testValidateObject() throws Exception {
		
		TestPoolableObject obj = new TestPoolableObject();
		boolean result = this._instance.validateObject(obj);
    	Assert.assertTrue(result);
    }
}

/**
 * Test class for pooling.
 * 
 * @author Ronnie Andrews, Jr.
 */
class TestPoolableObject implements PoolableObject {
	
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
