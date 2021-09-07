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
public class TestNotificationInfo extends TestCase {

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
	 * Holds instance of fax info to.
	 */
	private static final String TO   = "FaxInfo";

	/**
	 * Holds instance of message.
	 */
	private static final String MESSAGE = "Message Information";

	/**
	 * Holds instance of NotificationInfo.
	 */
	private NotificationInfo _notificaationInfo;

	/**
	 * setUp method for the test case to initialize required Object.
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {

		super.setUp();
		_notificaationInfo = new NotificationInfo();
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>NotificationInfo</code>
	 */
	public void testnotificationInfoFaxInfo() {

		FaxInfo faxInfo = new FaxInfo();
		faxInfo.setMessage(MESSAGE);
		faxInfo.setTo(TO);
		_notificaationInfo.setFaxInfo(faxInfo);
		assertNotNull(faxInfo);
		assertEquals(MESSAGE, _notificaationInfo.getFaxInfo().getMessage());
		assertEquals(TO, _notificaationInfo.getFaxInfo().getTo());
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>NotificationInfo</code>
	 */
	public void testnotificationInfoSmsInfo() {

		SmsInfo smsInfo = new SmsInfo();
		smsInfo.setMessage(MESSAGE);
		smsInfo.setTo(TO);
		_notificaationInfo.setSmsInfo(smsInfo);
		assertNotNull(smsInfo);
		assertEquals(MESSAGE, _notificaationInfo.getSmsInfo().getMessage());
		assertEquals(TO, _notificaationInfo.getSmsInfo().getTo());
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>NotificationInfo</code>
	 */
	public void testnotificationInfoMailInfo() {

		MailInfo mailInfo = new MailInfo();
		mailInfo.setEmailBody(EMAIL_BODY);		
		List<String> recipientAddress = new ArrayList<String>();
		recipientAddress.add(RECIPIENT_ADDRESS);
		mailInfo.setRecipientAddress(recipientAddress);
		mailInfo.setSenderEmailAddress(SENDER_ADDRESS);
		mailInfo.setSubject(SUBJECT);
		_notificaationInfo.setMailInfo(mailInfo);
		assertNotNull(mailInfo);
		assertEquals(EMAIL_BODY, _notificaationInfo.getMailInfo().getEmailBody());
		assertEquals(RECIPIENT_ADDRESS, 
                _notificaationInfo.getMailInfo().getRecipientAddress().get(0).toString());
		assertEquals(SENDER_ADDRESS, 
				_notificaationInfo.getMailInfo().getSenderEmailAddress());
		assertEquals(SUBJECT, _notificaationInfo.getMailInfo().getSubject());
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>NotificationInfo</code>
	 */
	public void testnotificationInfoSendEmail() {

		_notificaationInfo.setSendEmail(true);
		assertTrue(_notificaationInfo.isSendEmail());
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>NotificationInfo</code>
	 */
	public void testnotificationInfoSendFax() {

		_notificaationInfo.setSendFax(true);
		assertTrue(_notificaationInfo.isSendFax());
	}

	/**
	 * Test method, tests the getter and setter methods of
     * <code>NotificationInfo</code>
	 */
	public void testnotificationInfoSendSms() {

		_notificaationInfo.setSendSms(true);
		assertTrue(_notificaationInfo.isSendSms());
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
