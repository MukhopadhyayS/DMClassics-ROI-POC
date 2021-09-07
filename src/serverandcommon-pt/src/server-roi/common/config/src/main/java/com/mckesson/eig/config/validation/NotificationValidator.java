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

import java.util.List;

import com.mckesson.eig.config.constants.ConfigurationEC;
import com.mckesson.eig.config.exception.NotificationException;
import com.mckesson.eig.config.model.MailInfo;
import com.mckesson.eig.config.model.NotificationInfo;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.utility.util.net.MailValidationUtilities;

/**
 * @author OFS
 *
 * @date Mar 26, 2009
 * @since HECM 1.0.3; Mar 26, 2009
 */
public class NotificationValidator extends BaseValidator {

    /**
     * Initializes the logger.
     */
    private static final Log LOG = LogFactory.getLogger(ApplicationSettingValidator.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

	/**
	 * This method is used to validate notification information.
	 * 
	 * @param notificationInfo
	 * 
	 */
	public void validateNotificationInfo(NotificationInfo notificationInfo) {

        if (DO_DEBUG) {
            LOG.debug("Validating Notification Fields");
        }

		if (notificationInfo == null) {

			addError(ConfigurationEC.EC_NOTIFICATION_IS_NULL,
					 ConfigurationEC.MSG_NOTIFICATION_IS_NULL);
		} else if (!notificationInfo.isSendEmail() 
			&& !notificationInfo.isSendFax() 
			&& !notificationInfo.isSendSms()) {

			addError(ConfigurationEC.EC_NOTIFICATION_NOT_AVAILABLE,
					 ConfigurationEC.MSG_NOTIFICATION_NOT_AVAILABLE);
		} else if (notificationInfo.isSendEmail()) {
			validateMailInfo(notificationInfo.getMailInfo());
		}

		throwExceptionIfErrorExist();
	}

    /**
     * This method is used to validate the Mail Server attributes.
     */
    public void validateMailServer(String mailServerName, String mailServerPort) {

        LOG.debug("SendEmailActionHandler:validateMailServer >> start");

        if (!MailValidationUtilities.isValidMailServerName(mailServerName)) {
            addError(ConfigurationEC.MSG_INVALID_HOSTNAME + " [" + mailServerName + "]", 
                     ConfigurationEC.EC_INVALID_HOSTNAME);
        }
        if (!MailValidationUtilities.isValidMailServerPort(Integer.parseInt(mailServerPort))) {
            addError(ConfigurationEC.MSG_INVALID_PORT_NUMBER + " [" + mailServerPort + "]", 
                     ConfigurationEC.EC_INVALID_PORT_NUMBER);
        }

        throwExceptionIfErrorExist();

        LOG.debug("SendEmailActionHandler:validateMailServer >> end");
    }

    
    /**
     * A Helper method to validate an E-Mail ID list.
     *
     * @param List<String> - list of email ids to be validated.
     *
     * @return A boolean value that indicates the result of the validation
     */
    private boolean validateEmailAddresses(List<String> emailIds) {

    	for (int i = emailIds.size(); --i >= 0;) {

    		if (!MailValidationUtilities.isValidMailId(emailIds.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to validate the email BODY attribute.
     * 
     * @param mailInfo
     */
    private void validateBody(String emailBody) {

    	if (StringUtilities.isEmpty(emailBody)) {
    		addError(ConfigurationEC.EC_EMAIL_BODY_IS_NULL,
    				 ConfigurationEC.MSG_EMAIL_BODY_IS_NULL);
        }
    }

    /**
     * This method is used to validate the email SUBJECT attribute.
     * 
     * @param mailInfo
     */
    private void validateSubject(String subject) {

    	if (StringUtilities.isEmpty(subject)) {
    		addError(ConfigurationEC.EC_EMAIL_SUBJECT_IS_NULL,
    				 ConfigurationEC.MSG_EMAIL_SUBJECT_IS_NULL);
        }
    }

    /**
     * This method is used to validate the email TO attribute.
     * 
     * @param mailInfo
     */
    private void validateTo(List<String> recipientEmailIds) {

    	if (CollectionUtilities.isEmpty(recipientEmailIds)) {

    		addError(ConfigurationEC.EC_EMAIL_RECIPIENT_IS_NULL,
    				 ConfigurationEC.MSG_EMAIL_RECIPIENT_IS_NULL);
        } else if (!validateEmailAddresses(recipientEmailIds)) {

        	addError(ConfigurationEC.EC_INVALID_EMAIL_ID,
        			 ConfigurationEC.MSG_INVALID_EMAIL_ID);
        }
    }

    /**
     * This method is used to validate the email Reply To email, if exist.
     * 
     * @param emailId Reply To email address
     */
    private void validateReplyTo(String emailId) {
        
        if (!StringUtilities.isEmpty(emailId) 
                && !MailValidationUtilities.isValidMailId(emailId)) {

            addError(ConfigurationEC.EC_INVALID_REPLY_TO_EMAIL_ID,
                     ConfigurationEC.MSG_INVALID_REPLY_TO_EMAIL_ID);
        }
    }

    /**
     * This method is used to validate the email FROM attribute.
     * 
     * @param mailInfo
     */
    private void validateFrom(String senderEmailId) {

    	if (!StringUtilities.isEmpty(senderEmailId) 
    	        && !MailValidationUtilities.isValidMailId(senderEmailId)) {

            addError(ConfigurationEC.EC_INVALID_EMAIL_ID,
            		 ConfigurationEC.MSG_INVALID_EMAIL_ID);
        }
    }

    /**
     * This method is used to validate the email attributes.
     */
    private void validateMailInfo(MailInfo mailInfo) {

        if (mailInfo == null) {

        	addError(ConfigurationEC.EC_MAIL_INFO_IS_NULL,
        			 ConfigurationEC.MSG_MAIL_INFO_IS_NULL);
        	return;
        }
    	validateFrom(mailInfo.getSenderEmailAddress());
    	validateTo(mailInfo.getRecipientAddress());
    	validateReplyTo(mailInfo.getReplyTo());
    	validateSubject(mailInfo.getSubject());
    	validateBody(mailInfo.getEmailBody());
    }

    /**
     * Helper method used to throw the exception it error occurs.
     */
    private void throwExceptionIfErrorExist() {

        if (errorsExist()) {
            throw createException(new NotificationException());
        }
    }
}
