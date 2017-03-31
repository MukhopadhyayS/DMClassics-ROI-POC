/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.config.model;

import junit.framework.TestCase;

/**
 * @author OFS
 *
 * @date Mar 27, 2009
 * @since HECM 1.0.3; Mar 27, 2009
 */
public class TestFaxInfo extends TestCase {

	/**
	 * Holds instance of message.
	 */
	private static final String MESSAGE = "Fax Info Message";

	/**
	 * Holds instance of fax info to.
	 */
	private static final String TO = "FaxInfo To";

	/**
	 * Holds instance of FaxInfo.
	 */
	private FaxInfo _faxInfo   = null; 

	/**
	 * setUp method for the test case to initialize required Object
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {

		super.setUp();
		_faxInfo = new FaxInfo();
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>FaxInfo</code>
	 */
	public void testfaxInfoMessage() {

		_faxInfo.setMessage(MESSAGE);
		assertEquals(MESSAGE, _faxInfo.getMessage());
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>FaxInfo</code>
	 */
	public void testfaxInfoTo() {

		_faxInfo.setTo(TO);
		assertEquals(TO, _faxInfo.getTo());
	}

	/**
	 * Removes the data initialized as a part of the setUp.
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
