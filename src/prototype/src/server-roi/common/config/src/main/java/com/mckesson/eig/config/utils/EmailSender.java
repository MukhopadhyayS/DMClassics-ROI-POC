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
package com.mckesson.eig.config.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mckesson.eig.config.constants.ConfigurationEC;
import com.mckesson.eig.config.exception.ConfigurationException;
import com.mckesson.eig.config.exception.NotificationException;
import com.mckesson.eig.config.model.MailInfo;
import com.mckesson.eig.config.service.ApplicationSettingService;
import com.mckesson.eig.config.service.ApplicationSettingServiceImpl;
import com.mckesson.eig.config.validation.NotificationValidator;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author OFS
 *
 * @date Mar 26, 2009
 * @since HECM 1.0.3; Mar 26, 2009
 */
public final class EmailSender {

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(EmailSender.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

	/**
     * Private constructor, Utility class cannot be instantiated.
     */
    private EmailSender() {
    }

    /**
     * Holds the Hashmap key value email-server-setting.
     */
    public static final String EMAIL_SERVER_SETTING = "email-server-setting";

    /**
     * Holds the Hashmap key value email-address-setting.
     */
    public static final String EMAIL_ADDRESS_SETTING = "email-address-setting";

	/**
	 * Holds the Hashmap key value Host-address.
	 */
	public static final String KEY_SMTP_HOST  = "host-address";

	/**
	 * Holds the SMTP property key value mail.smtp.host.
	 */
	private static final String MAIL_SMTP_HOST = "mail.smtp.host";

    /**
     * Holds the SMTP property key value mail.smtp.port.
     */
    public static final String KEY_SMTP_PORT  = "port-number";
    
    /**
     * Holds the SMTP property key value mail.smtp.port.
     */
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";

	/**
	 * Holds the SMTP property key value mail.from.
	 */
	private static final String MAIL_FROM = "mail.from";

    /**
     * Holds the Hashmap key value sender email.
     */
	private static final String SENDER_EMAIL = "sender-email";

    /**
     * Holds the Hashmap key value sender name.
     */
	private static final String SENDER_NAME = "sender-name";

    /**
     * Holds the Hashmap key value reply to email.
     */
	private static final String REPLY_TO_EMAIL = "reply-to-email";

    /**
     * This method is used to send mail via SMTP.
     * 
     * @param mailInfo MailInfo
     * @param appHashMap HashMap<String, HashMap<String, String>>
     */
    public static void send(MailInfo mailInfo, 
                            HashMap<String, HashMap<String, String>> appHashMap) {

        LOG.debug("send(mailInfo, appHashMap)>>Start");

    	HashMap<String, String> emailServerMap = appHashMap.get(EMAIL_SERVER_SETTING);
    	HashMap<String, HashMap<String, String>> globalHashMap = 
    	                                 getApplicationSettingService().getGlobalSettingInfo();
    	if (CollectionUtilities.isEmpty(emailServerMap)
    			|| StringUtilities.isEmpty(emailServerMap.get(KEY_SMTP_HOST))
    			|| Long.parseLong(emailServerMap.get(KEY_SMTP_PORT)) == 0) {
    	    emailServerMap = globalHashMap.get(EMAIL_SERVER_SETTING);
    	}

    	HashMap<String, String> emailAddressMap = appHashMap.get(EMAIL_ADDRESS_SETTING);
        if (CollectionUtilities.isEmpty(emailAddressMap)) {
            emailAddressMap = globalHashMap.get(EMAIL_ADDRESS_SETTING);
        }

        String smtpHost = null;
        String smtpPort = null;
        try {

            // Validate SMTP Hostname and SMTP Port.
            smtpHost = emailServerMap.get(KEY_SMTP_HOST);
            smtpPort = emailServerMap.get(KEY_SMTP_PORT);
            new NotificationValidator().validateMailServer(smtpHost, smtpPort);
        } catch (ConfigurationException ce) {
            throw new NotificationException(ce, ConfigurationEC.EC_EMAIL_SENDING_FAILED);
        }

    	Properties mailProperties = new Properties();
        mailProperties.setProperty(MAIL_SMTP_HOST, smtpHost);
        mailProperties.setProperty(MAIL_SMTP_PORT, smtpPort);

        String senderEmail = mailInfo.getSenderEmailAddress();
        if (StringUtilities.isEmpty(senderEmail)) {
            senderEmail = emailAddressMap.get(SENDER_EMAIL);
        }
        mailProperties.setProperty(MAIL_FROM, senderEmail);
        
        String replyToEmail = mailInfo.getReplyTo();
        if (StringUtilities.isEmpty(replyToEmail)) {
            replyToEmail = emailAddressMap.get(REPLY_TO_EMAIL);
        }

        String emailBody = mailInfo.getEmailBody();
        if (!StringUtilities.isEmpty(emailBody)) {
            emailBody = emailBody.replace(SENDER_NAME, emailAddressMap.get(SENDER_NAME));
        }
        
    	String recipientIds = 
    		CollectionUtilities.extractCommaDelimitedString(mailInfo.getRecipientAddress());

    	Session session = Session.getInstance(mailProperties);
    	try {

    		MimeMessage message = new MimeMessage(session);
        	message.setFrom(new InternetAddress(senderEmail));
        	message.setReplyTo(new Address[] { new InternetAddress(replyToEmail)});
        	
        	// As per the usecase send date will be the email application date
            message.setSentDate(new Date());
            message.addRecipients(Message.RecipientType.TO, recipientIds);
            message.setSubject(mailInfo.getSubject());
            message.setText(emailBody);

            Transport.send(message);

            LOG.debug("send(mailInfo, appHashMap)<<End");

        } catch (MessagingException me) {
            throw new NotificationException(me, ConfigurationEC.EC_EMAIL_SENDING_FAILED);
        }
    }

    /**
     * This method is used to get ApplicationSettingService instance from spring context.
     * 
     * @return ApplicationSettingService
     */
    private static ApplicationSettingService getApplicationSettingService() {

        return (ApplicationSettingService) 
                    SpringUtilities.getInstance()
                                   .getBeanFactory()
                                   .getBean(ApplicationSettingServiceImpl.class.getName());
    }
}
