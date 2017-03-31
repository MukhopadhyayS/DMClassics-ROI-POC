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
package com.mckesson.eig.wsfw.test;

/**
 * This class is the Base class for all Validators that want to use the ValidatorError class such
 * that the Exception creation can contain all ValidatorError created.
 * 
 * Currently this class only maintains the last error and creates and exception from that single
 * error. Long term this could be changed to store a collection of error and create the exception
 * with such list.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mckesson.eig.utility.exception.ApplicationException;

public class BaseValidator {
    
    private List<ValidatorError> _validatorErrors = new ArrayList<ValidatorError>();

    public List<ValidatorError> getErrors() {
        return _validatorErrors;
    }
    /**
     * Add the error code and message to the Validator.
     * 
     * @param code -
     *            Client side error code (ClientErrorCodes class) message - Error message to store
     *            with Error
     */
    public void addError(String code, String message) {
        addError(code, message, null);
    }
    
    public void addError(String code, String message, String extError) {
        _validatorErrors.add(new ValidatorError(code, message, extError));
    }

    /**
     * The method will create a chain of ApplicationExceptions that contains the correct error code
     * and message.
     * 
     * @param code -
     *            Client side error code (ClientErrorCodes class) message - Error message to store
     *            with Error
     */
    public ApplicationException createException() {
        return (createException(null));
    }
    
    public ApplicationException createException(ApplicationException exception) {
        Iterator<ValidatorError> iter = _validatorErrors.iterator();
        while (iter.hasNext()) {
            ValidatorError error = iter.next();
            exception = buildExceptionChain(exception, error);
        }
        clearErrors();
        return exception;
    }

    private ApplicationException buildExceptionChain(ApplicationException exception,
            ValidatorError error) {
        if (exception == null) {
            return new ApplicationException(error.getMessage(), error.getErrorCode(),
                    error.getExtError());
        }
        return new ApplicationException(error.getMessage(), exception, error.getErrorCode(),
                error.getExtError());
    }

    public boolean errorsExist() {
        return _validatorErrors.size() > 0;
    }
    
    public void clearErrors() {
        _validatorErrors.clear();
    }
}
