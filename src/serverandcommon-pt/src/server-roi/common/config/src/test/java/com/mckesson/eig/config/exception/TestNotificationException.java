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
package com.mckesson.eig.config.exception;

import com.mckesson.eig.config.constants.ConfigurationEC;

import junit.framework.TestCase;

/**
 * @author OFS
 *
 * @date Mar 31, 2009
 * @since HECM 1.0.3; Mar 31, 2009
 */
public class TestNotificationException extends TestCase {

	/**
	 * setUp method for the test case to initialize required Object.
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testNotificationException() {

		NotificationException ne = new NotificationException();
		assertNotNull(ne.getAllNestedCauses());
	}

	public void testNotificationExceptionThrowable() {

		NotificationException ne = new NotificationException(new Throwable());
		assertNotNull(ne.getNestedCause());
	}

	public void testNotificationExceptionThrowablewithErrorCode() {

		NotificationException ne = new NotificationException(
		                                    new Throwable(), 
				                            ConfigurationEC.EC_OTHER_SERVER_ERROR);
		assertEquals(ConfigurationEC.EC_OTHER_SERVER_ERROR, ne.getErrorCode());
	}

	public void testNotificationExceptionMessagewithErrorCode() {

		NotificationException ne = 
			new NotificationException(ConfigurationEC.MSG_NOTIFICATION_NOT_AVAILABLE,
			                          ConfigurationEC.EC_NOTIFICATION_NOT_AVAILABLE);
		assertEquals(ConfigurationEC.EC_NOTIFICATION_NOT_AVAILABLE, ne.getErrorCode());
		assertEquals(ConfigurationEC.MSG_NOTIFICATION_NOT_AVAILABLE, ne.getMessage());
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
