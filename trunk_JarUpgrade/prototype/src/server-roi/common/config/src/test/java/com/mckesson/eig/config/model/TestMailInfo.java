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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author OFS
 *
 * @date Mar 27, 2009
 * @since HECM 1.0.3; Mar 27, 2009
 */
public class TestMailInfo extends TestCase {

	/**
	 * Holds instance of email body information.
	 */
	private static final String EMAIL_BODY = "MailBodyInformation";

	/**
	 * Holds instance of recipient email address.
	 */
	private static final String RECIPIENT_ADDRESS = "recipient@mckesson.com";

	/**
	 * Holds instance of sender email address.
	 */
	private static final String SENDER_ADDRESS = "sender@mckesson.com";

	/**
	 * Holds instance of mail subject.
	 */
	private static final String SUBJECT = "Mail Subject";

	/**
	 * Holds instance of MailInfo.
	 */
	private MailInfo _mailInfo; 

	/**
	 * setUp method for the test case to initialize required Object.
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {

		super.setUp();
		_mailInfo = new MailInfo();
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>MailInfo</code>
	 */
	public void testmailInfoEmailBody() {

		_mailInfo.setEmailBody(EMAIL_BODY);
		assertEquals(EMAIL_BODY, _mailInfo.getEmailBody());
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>MailInfo</code>
	 */
	public void testmailInfoRecipientAddress() {

		List<String> recipientAddress = new ArrayList<String>();
		recipientAddress.add(RECIPIENT_ADDRESS);
		_mailInfo.setRecipientAddress(recipientAddress);
		assertEquals(RECIPIENT_ADDRESS, _mailInfo.getRecipientAddress().get(0).toString());
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>MailInfo</code>
	 */
	public void testmailInfosenderEmailAddress() {

		_mailInfo.setSenderEmailAddress(SENDER_ADDRESS);
		assertEquals(SENDER_ADDRESS, _mailInfo.getSenderEmailAddress());
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>MailInfo</code>
	 */
	public void testmailInfoSubject() {

		_mailInfo.setSubject(SUBJECT);
		assertEquals(SUBJECT, _mailInfo.getSubject());
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
