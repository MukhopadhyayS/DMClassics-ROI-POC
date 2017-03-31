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
package com.mckesson.eig.wsfw.axis;

import com.mckesson.eig.utility.exception.ApplicationException;

/**
 * Exception Class for our CastorContext,CastorSerialization and for
 * CastorDeserialization.
 */
public class CastorContextException extends ApplicationException {

    /**
     * Default constructor. There is no exception to cascade and no additional
     * message. In this case the class is just like a RuntimeException
     */
    public CastorContextException() {
        super();
    }

    /**
     * Construct with a message only. There is no exception to cascade. In this
     * case this class will act like any other RuntimeException.
     * 
     * @param message
     *            Exception message
     */
    public CastorContextException(String message) {
        super(message);
    }

    /**
     * Construct with a cause.
     * 
     * @param cause
     *            The exception we are cascading.
     */
    public CastorContextException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct with a message and a cause.
     * 
     * @param message
     *            Message to describe the exception
     * @param cause
     *            the exception we are cascading
     */
    public CastorContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
