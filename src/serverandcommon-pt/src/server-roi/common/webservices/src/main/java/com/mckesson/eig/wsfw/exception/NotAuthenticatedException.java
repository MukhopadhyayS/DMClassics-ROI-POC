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
 * Base class for our Invalid User Exception.
 * 
 */
public class NotAuthenticatedException extends ApplicationException {

    /**
     * Construct with a message and an errorCode. There is no exception to
     * cascade. In this case this class will act like any other
     * RuntimeException.
     * 
     * @param message
     *            Exception message
     */
    public NotAuthenticatedException(String message, String errorCode) {
        super(message, errorCode);
    }

}
