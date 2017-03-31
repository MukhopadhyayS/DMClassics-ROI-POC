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


/**
 * @author OFS
 *
 * @date Mar 27, 2009
 * @since HECM 1.0.3; Mar 27, 2009
 * 
 * This class is a sub class of ConfigurationException and this Exception
 * is thrown when an exception occurs in the ConfigServer module level.
 */
public class NotificationException extends ConfigurationException {

    /**
     * Serial Version ID for this Serializable.
     */
	private static final long serialVersionUID = -1710732214375862575L;

	/**
	 * Instantiates a NotificationException.
	 */
	public NotificationException() {
		super();
	}

	/**
     * Instantiates a NotificationException with the specified throwable.
     * 
     * @param cause
     */
    public NotificationException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a NotificationException with the specified throwable
     * and error code.
     * 
     * @param cause
     * @param errorCode
     */
    public NotificationException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

    /**
     * Instantiates a NotificationException with the specified message
     * and error code.
     * 
     * @param message
     * @param errorCode
     */
    public NotificationException(String message, String errorCode) {
        super(message, errorCode);
    }
}
