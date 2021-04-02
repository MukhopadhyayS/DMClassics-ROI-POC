/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.inuse.base.api;

import com.mckesson.eig.utility.exception.ApplicationException;


/**
 *
 * @author OFS
 * @date   Nov 05, 2008
 * @since  HPF 13.1 [INUSE]; Nov 05, 2008
 */
public class InUseException
extends ApplicationException {

    /**
     * Default constructor
     */
    public InUseException() {
        super();
    }

    /**
     * Construct with errorCode
     *
     * @param errorCode
     *          Error Code enum for the exception
     */
    public InUseException(InUseClientErrorCodes errorCode) {
        setErrorCode(errorCode.toString());
    }

     /**
     * Construct with cause and errorCode
     *
     * @param cause
     *          Actual cause of the exception
     * @param errorCode
     *          Error Code enum for the exception
     */
    public InUseException(Throwable cause, InUseClientErrorCodes errorCode) {
        super(cause, errorCode.toString());
    }

    /**
     * Construct with errorCode and errorData
     *
     * @param errorCode
     *          Error Code enum for the exception
     * @param errorData
     *          Error Data causing exception
     */
    public InUseException(InUseClientErrorCodes errorCode, String errorData) {
        super(null, errorCode.toString(), errorData);
    }


    /**
     * Construct with cause,errorCode and errorData
     *
     * @param cause
     *          Actual cause of the exception
     * @param errorCode
     *          Error Code enum for the exception
     * @param errorData
     *          Error Data causing exception
     */
    public InUseException(Throwable cause, InUseClientErrorCodes errorCode, String errorData) {
        super(null, cause, errorCode.toString(), errorData);
    }
}
