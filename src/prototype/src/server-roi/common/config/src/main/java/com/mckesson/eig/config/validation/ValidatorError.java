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
package com.mckesson.eig.config.validation;

/**
 * This class holds the information for Error messages and codes.
 */
public class ValidatorError {
    /**
     * The message string of the Error to pass to the client
     */
    private String _message;

    /**
     * The error code to pass to the client
     */
    private String _errorCode;
    
    /**
     * Extended error information i.e. content name
     */
    private String _extError;
    
    /**
     * Constructor that takes the error Code and the error Message
     * 
     * @param code
     *            Error code from the ClientErrorCodes class.
     *        message             
     *            Message string about the error  
     */
    public ValidatorError(String code, String message) {
        setErrorCode(code);
        setMessage(message);
        setExtError("");
    }

    public ValidatorError(String code, String message, String extError) {
        setErrorCode(code);
        setMessage(message);
        setExtError(extError);
       
    }

    /**
     * Get the error message.
     * 
     * @return String
     */
    public String getMessage() {
        return _message.toString();
    }
    
    /**
     * Set the error message.
     * 
     * @param message -
     *            Error message string.
     */
    public void setMessage(String message) {
        _message = message;
    }
    
    /**
     * Set the error Code.
     * 
     * @param errorCode -
     *            Error code from the ClientErrorCodes class.
     */
    public void setErrorCode(String errorCode) {
        _errorCode = errorCode;
    }
    
    /**
     * Get the error code.
     * 
     * @return String
     */
    public String getErrorCode() {
        return _errorCode;
    }

    public String getExtError() {
        return _extError;
    }

    public void setExtError(String error) {
        _extError = error;
    }
}
