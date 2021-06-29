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

package com.mckesson.eig.config.constants;

import com.mckesson.eig.utility.exception.ClientErrorCodes;

/**
 *
 * It holds the error code information related to Configuration Module
 *
 * @author kayalvizhik
 * @date   Mar 24, 2009
 * @since  HECM 2.0; Mar 24, 2009
 */
public class ConfigurationEC extends ClientErrorCodes {

    public static final String EC_NULL_GLOBAL_SETTING                 = "CS_EC_01";
    public static final String EC_NULL_HOSTNAME                       = "CS_EC_02";
    public static final String EC_INVALID_HOSTNAME                    = "CS_EC_03";
    public static final String EC_INVALID_PORT_NUMBER                 = "CS_EC_04";
    public static final String EC_NULL_APPLICATION_SETTING            = "CS_EC_05";
    public static final String EC_NULL_APPLICATION_SETTING_DATA       = "CS_EC_06";
    public static final String EC_INVALID_APPLICATION_SETTING_DATA    = "CS_EC_07";
    public static final String EC_OTHER_SERVER_ERROR                  = "CS_EC_08";
    public static final String EC_NULL_APPLICATION_NAME               = "CS_EC_09";
    public static final String EC_INVALID_APPLICATION_NAME            = "CS_EC_10";
    public static final String EC_INVALID_GLOBAL_SETTING              = "CS_EC_20";
    public static final String EC_INVALID_XML_DOCUMENT                = "CS_EC_21";

    public static final String MSG_NULL_GLOBAL_SETTING      = "global SETTING is null";
    public static final String MSG_NULL_HOSTNAME            = "hostname/ip is null";
    public static final String MSG_INVALID_HOSTNAME         = "hostname/ip is invalid";
    public static final String MSG_INVALID_PORT_NUMBER      = "port number is invalid";
    public static final String MSG_NULL_APPLICATION_SETTING = "application setting is null";
    public static final String MSG_NULL_APPLICATION_SETTING_DATA
                                                            = "application setting data is null";
    public static final String MSG_INVALID_APPLICATION_SETTING_DATA
                                                            = "application setting data is invalid";
    public static final String MSG_OTHER_SERVER_ERROR
                                                            = "Other Server Error";
    public static final String MSG_NULL_APPLICATION_NAME    = "application name is null";
    public static final String MSG_INVALID_APPLICATION_ID   = "application id is invalid";
    public static final String MSG_INVALID_GLOBAL_SETTING
                                                            = "global setting is invalid";
    public static final String MSG_INVALID_XML_DOCUMENT     = "setting xml is invalid";

	// Email Notification
    public static final String EC_NOTIFICATION_IS_NULL        = "CS_EC_11";
    public static final String EC_NOTIFICATION_NOT_AVAILABLE  = "CS_EC_12";
    public static final String EC_NOTIFICATION_MODE_MUST      = "CS_EC_13";
    public static final String EC_EMAIL_SUBJECT_IS_NULL       = "CS_EC_14";
    public static final String EC_EMAIL_SENDER_IS_NULL        = "CS_EC_15";
    public static final String EC_EMAIL_RECIPIENT_IS_NULL     = "CS_EC_16";
    public static final String EC_EMAIL_BODY_IS_NULL          = "CS_EC_17";
    public static final String EC_INVALID_EMAIL_ID            = "CS_EC_18";
    public static final String EC_MAIL_INFO_IS_NULL           = "CS_EC_19";
    public static final String EC_INVALID_REPLY_TO_EMAIL_ID   = "CS_EC_20";
    public static final String EC_EMAIL_SENDING_FAILED        = "CS_EC_21";
    public static final String EC_EMAIL_EXCEEDS_MAX_LENGTH    = "CS_EC_22";

    public static final String MSG_NOTIFICATION_IS_NULL       = "NotificationInfo cannot be null";
    public static final String MSG_NOTIFICATION_NOT_AVAILABLE = "NotificationInfo not available";
    public static final String MSG_NOTIFICATION_MODE_MUST     = "Notification mode should be set";
    public static final String MSG_EMAIL_SUBJECT_IS_NULL      = "Email subject cannot be null";
    public static final String MSG_EMAIL_SENDER_IS_NULL       = "Sender email cannot be null";
    public static final String MSG_EMAIL_BODY_IS_NULL         = "Email body not available";
    public static final String MSG_INVALID_EMAIL_ID           = "Invalid Email ID";
    public static final String MSG_MAIL_INFO_IS_NULL          = "Mail Info cannot be null";
    public static final String MSG_INVALID_REPLY_TO_EMAIL_ID  = "Reply To email is invalid";
    public static final String MSG_EMAIL_SENDING_FAILED       = "Email sending failed.";
    public static final String MSG_EMAIL_EXCEEDS_MAX_LENGTH   = "Email exceeds the max length";
    public static final String MSG_EMAIL_RECIPIENT_IS_NULL 
                                                      = "Recipient email addresses cannot be null";

}
