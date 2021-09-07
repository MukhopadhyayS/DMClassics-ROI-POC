/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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
 * by the underlying LogConfigurationService.
 * 
 */
public class ConfigureLogException extends ApplicationException {

    /**
     * Default constructor. There is no exception to cascade and no additional
     * message.
     */
    public ConfigureLogException() {
        super();
    }

    /**
     * Construct with a message only. There is no exception to cascade. 
     * 
     * @param message
     *            Exception message
     */
    public ConfigureLogException(String message) {
        super(message);
    }

    /**
     * Construct with a message and error code. There is no exception to
     * cascade.
     *  
     * @param message
     *            Exception message
     * @param errorCode client error code.
     */
    public ConfigureLogException(String message, String errorCode) {
        super(message, errorCode);
    }

    /**
     * Construct with an exception only.
     * 
     * @param exp
     *            Exception object that would be wrapped.
     */
    public ConfigureLogException(Exception exp) {
        super(exp);
    }

    /**
     * Construct with a message and a cause.
     * 
     * @param message
     *            Message to describe the exception
     * @param cause
     *            the exception we are cascading
     */
    public ConfigureLogException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct with an exception and error code.
     * 
     * @param exp
     *            Exception object that would be wrapped.
     * @param errorCode client error code.
     */
    public ConfigureLogException(Exception exp, String errorCode) {
        super(exp, errorCode);
    }

}
