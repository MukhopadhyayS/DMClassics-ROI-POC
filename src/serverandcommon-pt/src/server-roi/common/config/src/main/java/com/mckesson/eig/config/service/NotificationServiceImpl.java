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

import java.util.HashMap;

import javax.jws.WebService;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.config.model.NotificationInfo;
import com.mckesson.eig.config.utils.EmailSender;
import com.mckesson.eig.config.validation.NotificationValidator;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author OFS
 *
 * @date Mar 26, 2009
 * @since HECM 1.0.3; Mar 26, 2009
 */
@WebService(
        name              = "NotificationPortType_v1_0",
        portName          = "notification_v1_0",
        serviceName       = "NotificationService_v1_0",
        targetNamespace   = "http://eig.mckesson.com/wsdl/notification-v1",
        endpointInterface = "com.mckesson.eig.config.service.NotificationService")
public class NotificationServiceImpl implements NotificationService {

	/**
	 * Gets the logger for this class.
	 */
	private static final OCLogger LOG = new OCLogger( NotificationServiceImpl.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

	/** 
	 * This method is used to notify information via E-Mail, Fax or SMS.
	 * 
	 * @see NotificationService
	 * 	#notify(com.mckesson.eig.config.model.NotificationInfo, appId)
	 */
	public void notify(NotificationInfo notificationInfo, long appId) {

	    if (DO_DEBUG) {
	        LOG.debug("notify(notificationInfo, appId)>>Start");
	    }

        new NotificationValidator().validateNotificationInfo(notificationInfo);
        EmailSender.send(notificationInfo.getMailInfo(), getApplicationInfo(appId));

		if (DO_DEBUG) {
		    LOG.debug("notify(notificationInfo, appId)<<End");
		}
	}

    /**
	 * Helper method used to get the application info 
	 *
	 * @param appId Application Id
	 * 
	 * @return Application specific settings as map.
	 */
	private HashMap<String, HashMap<String, String>> getApplicationInfo(long appId) {

	    ApplicationSettingService service = getApplicationSettingService();
        HashMap<String, HashMap<String, String>> settings = service.getApplicationSetting(appId);

	    return (settings == null) ? service.getGlobalSettingInfo() : settings;
	}

	/**
	 * This method is used to get ApplicationSettingService instance from spring context.
	 * 
	 * @return ApplicationSettingService
	 */
	private ApplicationSettingService getApplicationSettingService() {

		return (ApplicationSettingService) 
					SpringUtilities.getInstance()
                                   .getBeanFactory()
                                   .getBean(ApplicationSettingServiceImpl.class.getName());
	}
}
