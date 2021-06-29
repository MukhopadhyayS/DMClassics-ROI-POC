/*
 * Copyright 2007-2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.config.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mckesson.eig.config.model.MailInfo;
import com.mckesson.eig.config.model.NotificationInfo;
import com.mckesson.eig.config.test.AbstractConfigurationTestCase;
import com.mckesson.eig.utility.util.ReflectionUtilities;

/**
 * @author OFS
 *
 * @date Mar 27, 2009
 * @since HECM 1.0.3; Mar 27, 2009
 */
public class TestEmailSender extends AbstractConfigurationTestCase {

	/**
	 * Holds instance of valid email address.
	 */
	private static final String VALID_EMAIL_ID = "nsk@ofs.com";

	/**
	 * Holds instance of valid email address.
	 */
	private static final String INVALID_EMAIL_ID = "nskofs.com";

	/**
	 * Holds instance of mail subject.
	 */
	private static final String SUBJECT = "Test Config Server Mail";

	/**
	 * Holds instance of email body information.
	 */
	private static final String EMAIL_BODY = "Hi All, \n \t Test E-Mail Send "
			+ "from ConfigServer \n " 
			+ "Regards, \n Config Mail Server.";

	/**
	 * Holds instance of valid email address of recipient.
	 */
	private List<String> _recipientAddress = null;

	/**
	 * Holds instance of valid email address of sender.
	 */
	private String _senderEmailAddress = "admin@mckesson.com";

    /**
     * setUp method for the test case to initialize required Object.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void testSetup() throws Exception {
        super.init();
    }

	public void testSendNegative() {

		MailInfo mailInfo = new MailInfo();
		mailInfo.setEmailBody(EMAIL_BODY);
		_recipientAddress = new ArrayList<String>();
		_recipientAddress.add(INVALID_EMAIL_ID);
		mailInfo.setRecipientAddress(_recipientAddress);
		
		mailInfo.setSubject(SUBJECT);

		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setMailInfo(mailInfo);
		notificationInfo.setSendEmail(true);

		HashMap<String, String> smtpConfig = new HashMap<String, String>();
		smtpConfig.put(EmailSender.KEY_SMTP_HOST, "139.177.6.114");
		smtpConfig.put(EmailSender.KEY_SMTP_PORT, "25");
		HashMap<String, HashMap<String, String>> appHasMap = 
                new HashMap<String, HashMap<String, String>>();
		appHasMap.put(EmailSender.EMAIL_SERVER_SETTING, smtpConfig);

		try {
		    EmailSender.send(notificationInfo.getMailInfo(), appHasMap);
		} catch (Exception e) {
		    assert (true);
		}
	}

	public void testSend() {

		MailInfo mailInfo = new MailInfo();
		mailInfo.setEmailBody(EMAIL_BODY);
		_recipientAddress = new ArrayList<String>();
		_recipientAddress.add(VALID_EMAIL_ID);
		mailInfo.setRecipientAddress(_recipientAddress);
		mailInfo.setSenderEmailAddress(_senderEmailAddress);
		mailInfo.setSubject(SUBJECT);

		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setMailInfo(mailInfo);
		notificationInfo.setSendEmail(true);

		HashMap<String, String> smtpConfig = new HashMap<String, String>();
		smtpConfig.put(EmailSender.KEY_SMTP_HOST, "139.177.6.114");
		smtpConfig.put(EmailSender.KEY_SMTP_PORT, "25");
		HashMap<String, HashMap<String, String>> appHasMap = 
                new HashMap<String, HashMap<String, String>>();
		appHasMap.put(EmailSender.EMAIL_SERVER_SETTING, smtpConfig);

		EmailSender.send(notificationInfo.getMailInfo(), appHasMap);
	}

	public void testInvokePrivateConstructor() {
		ReflectionUtilities.callPrivateConstructor(EmailSender.class);
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
