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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.mckesson.eig.config.model.NotificationInfo;

/**
 * @author OFS
 *
 * @date Mar 26, 2009
 * @since HECM 1.0.3; Mar 26, 2009
 */
@WebService(name        = "NotificationPortType_v1_0",
        targetNamespace = "http://eig.mckesson.com/wsdl/notification-v1")
@SOAPBinding(style      = Style.DOCUMENT,
         use            = Use.LITERAL,
         parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface NotificationService extends ConfigService {

    /**
     * @param notificationInfo
     * @param value
     */
    @WebMethod(operationName = "notify",
            action           = "http://eig.mckesson.com/wsdl/notification-v1/notify")
	void notify(@WebParam(name = "notificationInfo") NotificationInfo notificationInfo, 
				@WebParam(name = "appId") long appId);
}
