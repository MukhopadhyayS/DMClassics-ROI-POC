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
package com.mckesson.eig.wsfw.exception;

import com.mckesson.eig.utility.exception.ApplicationException;

/**
 * Base class for our UsernameToken Exception.
 * 
 */
public class UsernameTokenException extends ApplicationException {
    
    /**
     * Construct with a message only. There is no exception to cascade. In this
     * case this class will act like any other RuntimeException.  
     * 
     * @param message
     *            Exception message
     * @param errorCode
     */
    public UsernameTokenException(String message, String errorCode) {
        super(message, errorCode);
    }
    
    /**
     * Construct with a message, a cause, and an errorcode.
     * 
     * @param message
     *            Message to describe the exception
     * @param cause
     *            the exception we are cascading
     */
    public UsernameTokenException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

}
