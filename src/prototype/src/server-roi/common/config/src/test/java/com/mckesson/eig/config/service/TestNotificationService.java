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
package com.mckesson.eig.config.service;

import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.config.constants.ConfigurationEC;
import com.mckesson.eig.config.exception.ConfigurationException;
import com.mckesson.eig.config.model.MailInfo;
import com.mckesson.eig.config.model.NotificationInfo;
import com.mckesson.eig.config.test.AbstractConfigurationTestCase;


/**
 * @author OFS
 *
 * @date Mar 26, 2009
 * @since HECM 1.0.3; Mar 26, 2009
 */
public class TestNotificationService extends AbstractConfigurationTestCase {

	/**
	 * Holds the valid email address.
	 */
	private static final String VALID_EMAIL_ID = "nsk@ofs.com";

	private static final String INVALID_EMAIL_ID = "nskofs.com";

	/**
	 * Holds instance of mail subject.
	 */
	private static final String SUBJECT = "Mail Subject";

	/**
	 * Holds the email body information.
	 */
	private static final String EMAIL_BODY = "MailBodyInformation";

	/**
	 * Holds instance of NotificationService.
	 */
	private NotificationService _notificationService;

	private boolean _loadOnce;

	/**
     * setUp method for the test case to initialize required Object.
     * 
     * @see junit.framework.TestCase#setUp()
     */
	@Override
	protected void setUp() throws Exception {

	    if (!_loadOnce) {
	        super.init();
	        _loadOnce = true;
	    }

	    _notificationService = 
	        (NotificationService) getManager(NotificationServiceImpl.class.getName());
	}
	
	public void testNotifyFail() {

		try {
			NotificationInfo notificationInfo = new NotificationInfo();
			notificationInfo.setSendEmail(true);
	
			MailInfo mailInfo = new MailInfo();
			mailInfo.setSenderEmailAddress(INVALID_EMAIL_ID);
			List<String> recipientIds = new ArrayList<String>();
			recipientIds.add(INVALID_EMAIL_ID);
			mailInfo.setRecipientAddress(recipientIds);
			mailInfo.setSubject(SUBJECT);
			mailInfo.setEmailBody(EMAIL_BODY);
			notificationInfo.setMailInfo(mailInfo);
	
			_notificationService.notify(notificationInfo, 0);
		} catch (ConfigurationException ce) {
			assertEquals(ConfigurationEC.EC_INVALID_EMAIL_ID, ce.getErrorCode());
		}
	}

	public void testNotify() {

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

		_notificationService.notify(notificationInfo, 1L);
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
