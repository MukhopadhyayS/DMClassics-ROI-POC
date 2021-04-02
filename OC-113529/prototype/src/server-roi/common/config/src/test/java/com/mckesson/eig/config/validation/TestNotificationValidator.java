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
package com.mckesson.eig.config.validation;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.mckesson.eig.config.constants.ConfigurationEC;
import com.mckesson.eig.config.exception.ConfigurationException;
import com.mckesson.eig.config.model.MailInfo;
import com.mckesson.eig.config.model.NotificationInfo;

/**
 * @author OFS
 *
 * @date Mar 27, 2009
 * @since HECM 1.0.3; Mar 27, 2009
 */
public class TestNotificationValidator extends TestCase {

	/**
	 * Holds instance of valid email address.
	 */
	private static final String VALID_EMAIL_ID = "recipient@mckesson.com";

	/**
	 * Holds instance of invalid email address.
	 */
	private static final String INVALID_EMAIL_ID = "recipient @mckesson.com";

	/**
	 * Holds instance of mail subject.
	 */
	private static final String SUBJECT = "Mail Subject";

	/**
	 * Holds instance of email body information.
	 */
	private static final String EMAIL_BODY = "MailBodyInformation";

	/**
	 * Holds instance of NotificationValidator.
	 */
	private NotificationValidator _notificationValidator; 

	/**
	 * setUp method for the test case to initialize required Object.
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {

		super.setUp();
		_notificationValidator = new NotificationValidator();
	}

	public void testValidateNotificationInfoNull() {

		try {

			_notificationValidator.validateNotificationInfo(null);
			fail("NotificationInfo can not be Null");
		} catch (ConfigurationException ne) {
			assertEquals(ConfigurationEC.EC_NOTIFICATION_IS_NULL, ne.getErrorCode());
		}
	}

	public void testValidateNotificationInfoNotAvailable() {

		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setSendEmail(false);
		notificationInfo.setSendFax(false);
		notificationInfo.setSendSms(false);
		try {

			_notificationValidator.validateNotificationInfo(notificationInfo);
			fail("Atleast one notification info must exist.");
		} catch (ConfigurationException ce) {
            assertEquals(ConfigurationEC.EC_NOTIFICATION_NOT_AVAILABLE, ce.getErrorCode());
		}
	}

	public void testValidateNotificationInfowithoutMail() {

		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setSendFax(true);
		try {

            _notificationValidator.validateNotificationInfo(notificationInfo);
		} catch (ConfigurationException ce) {
            fail("testvalidateNotificationInfowithoutMail must pass");
		}
	}

	public void testValidateMailInfoNull() {

		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setSendEmail(true);
		try {

			_notificationValidator.validateNotificationInfo(notificationInfo);
			fail("NotificationInfo.mailInfo can not be null");
		} catch (ConfigurationException ce) {
            assertEquals(ConfigurationEC.EC_MAIL_INFO_IS_NULL, ce.getErrorCode());
		}
	}

	public void testValidateMailInfo() {

		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setSendEmail(true);

		MailInfo mailInfo = new MailInfo();
		notificationInfo.setMailInfo(mailInfo);
		try {

			_notificationValidator.validateNotificationInfo(notificationInfo);
			fail("NotificationInfo.mailInfo.senderEmaiAddress can not be null");
		} catch (ConfigurationException ce) {

			assertEquals(ConfigurationEC.EC_EMAIL_BODY_IS_NULL, ce.getErrorCode());
			ce = (ConfigurationException) ce.getNestedCause();
			assertEquals(ConfigurationEC.EC_EMAIL_SUBJECT_IS_NULL, ce.getErrorCode());
			ce = (ConfigurationException) ce.getNestedCause();
			assertEquals(ConfigurationEC.EC_EMAIL_RECIPIENT_IS_NULL, ce.getErrorCode());
		}
	}

	public void testValidateMailInfoInvalidSenderEmailId() {

		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setSendEmail(true);

		MailInfo mailInfo = new MailInfo();
		mailInfo.setSenderEmailAddress(INVALID_EMAIL_ID);
		notificationInfo.setMailInfo(mailInfo);
		try {

			_notificationValidator.validateNotificationInfo(notificationInfo);
			fail("NotificationInfo.mailInfo.senderEmaiAddress must be invalid");
		} catch (ConfigurationException ce) {

			assertEquals(ConfigurationEC.EC_EMAIL_BODY_IS_NULL, ce.getErrorCode());
			ce = (ConfigurationException) ce.getNestedCause();
			assertEquals(ConfigurationEC.EC_EMAIL_SUBJECT_IS_NULL, ce.getErrorCode());
			ce = (ConfigurationException) ce.getNestedCause();
			assertEquals(ConfigurationEC.EC_EMAIL_RECIPIENT_IS_NULL, ce.getErrorCode());
			ce = (ConfigurationException) ce.getNestedCause();
			assertEquals(ConfigurationEC.EC_INVALID_EMAIL_ID, ce.getErrorCode());
		}
	}

	public void testValidateMailInfoInvalidRecipientEmailId() {

		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setSendEmail(true);

		MailInfo mailInfo = new MailInfo();
		mailInfo.setSenderEmailAddress(VALID_EMAIL_ID);
		List<String> recipientIds = new ArrayList<String>();
		recipientIds.add(INVALID_EMAIL_ID);
		mailInfo.setRecipientAddress(recipientIds);
		notificationInfo.setMailInfo(mailInfo);
		try {

			_notificationValidator.validateNotificationInfo(notificationInfo);
			fail("NotificationInfo.mailInfo.recipientAddress must be invalid");
		} catch (ConfigurationException ce) {

			assertEquals(ConfigurationEC.EC_EMAIL_BODY_IS_NULL, ce.getErrorCode());
			ce = (ConfigurationException) ce.getNestedCause();
			assertEquals(ConfigurationEC.EC_EMAIL_SUBJECT_IS_NULL, ce.getErrorCode());
			ce = (ConfigurationException) ce.getNestedCause();
			assertEquals(ConfigurationEC.EC_INVALID_EMAIL_ID, ce.getErrorCode());
		}
	}

	public void testValidateNotificationInfo() {

		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setSendEmail(true);

		MailInfo mailInfo = new MailInfo();
		mailInfo.setSenderEmailAddress(VALID_EMAIL_ID);
		List<String> recipientIds = new ArrayList<String>();
		recipientIds.add(VALID_EMAIL_ID);
		mailInfo.setRecipientAddress(recipientIds);
		mailInfo.setSubject(SUBJECT);
		mailInfo.setEmailBody(EMAIL_BODY);
		notificationInfo.setMailInfo(mailInfo);
		try {                                     

            _notificationValidator.validateNotificationInfo(notificationInfo);
		} catch (ConfigurationException ce) {
			fail("testvalidateNotificationInfo should pass");
		}
	}

	public void testValidateMailServerInfo() {

	    
        try {
            _notificationValidator.validateMailServer("127.0.0.1", "25");
            _notificationValidator.validateMailServer("", "-1");
            fail("testValidateMailServerInfo should have to throw an exception");
        } catch (ConfigurationException ce) {

            assertEquals(ConfigurationEC.MSG_INVALID_PORT_NUMBER + " [-1]", ce.getErrorCode());
            ce = (ConfigurationException) ce.getNestedCause();
            assertEquals(ConfigurationEC.MSG_INVALID_HOSTNAME + " []", ce.getErrorCode());
            ce = (ConfigurationException) ce.getNestedCause();
        }
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
