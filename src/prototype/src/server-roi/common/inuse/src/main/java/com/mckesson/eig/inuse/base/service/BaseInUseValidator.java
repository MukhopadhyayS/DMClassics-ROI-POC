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

package com.mckesson.eig.inuse.base.service;


import com.mckesson.eig.inuse.base.api.InUseClientErrorCodes;
import com.mckesson.eig.inuse.base.api.InUseException;

/**
 * @author OFS
 * @date   Nov 11, 2008
 * @since  ROI HPF 13.1
 */
public class BaseInUseValidator {

    private InUseException _exception = null;


    /**
     * This method returns the InUseException
     *
     * @return InUseException with nested cause
     */
    public InUseException getException() {
        return _exception;
    }

    /**
     * This method creates InUseException with error code as nested cause
     *
     * @param errorCode
     */
    protected void addError(InUseClientErrorCodes errorCode) {

        _exception = (_exception == null) ? new InUseException(errorCode)
                                          : new InUseException(_exception, errorCode);
    }

    /**
     * This method creates InUseException with error code , error data and nested cause
     *
     * @param errorCode
     * @param errorData
     */
    protected void addError(InUseClientErrorCodes errorCode, String errorData) {

        _exception = (_exception == null) ? new InUseException(errorCode, errorData)
                                          : new InUseException(_exception,
                                                             errorCode,
                                                             errorData);
    }

    /**
     * This method checks the _exception and return true if the _exception is  empty
     *
     * @return Error status (true/false)
     */
    protected boolean hasNoErrors() {
        return (_exception == null);
    }
}
