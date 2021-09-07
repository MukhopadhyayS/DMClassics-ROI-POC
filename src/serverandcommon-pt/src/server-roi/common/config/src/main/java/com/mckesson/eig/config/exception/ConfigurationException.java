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

package com.mckesson.eig.config.exception;

import com.mckesson.eig.utility.exception.ApplicationException;

/**
 * Basic Exception class for wrapping all the exceptions thrown
 * by the underlying ApplicationSettingService.
 *
 * @author kayalvizhik
 * @date   Mar 24, 2009
 * @since  HECM 2.0; Mar 24, 2009
 */

public class ConfigurationException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1248608687896335631L;

	/**
     * Instantiates a ConfigurationException.
     */
	public ConfigurationException() {
		super();
	}

	/**
     * Instantiates a ConfigurationException with the specified throwable.
     */
    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a ConfigurationException with the specified throwable
     * and error code.
     */
    public ConfigurationException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

    /**
     * Instantiates a ConfigurationException with the specified message
     * and error code.
     */
    public ConfigurationException(String message, String errorCode, String extendedCode) {
        super(message, errorCode, extendedCode);
    }

    public ConfigurationException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ConfigurationException(String message, Throwable cause, String errCode, String extCode) {
        super(message, cause, errCode, extCode);
    }
}
