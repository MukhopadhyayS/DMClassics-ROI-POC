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
package com.mckesson.eig.workflow.api;

import com.mckesson.eig.utility.exception.ApplicationException;


/**
 * @author Pranav Amarasekaran
 * @date   Aug 30, 2007
 * @since  HECM 1.0
 *
 * This class is a sub class of ApplicationException and this Exception
 * is thrown when an exception occurs in the workflow module level.
 */
public class WorkflowPluginException
extends ApplicationException {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = -882166072108090421L;

    /**
     * Instantiates a Workflow Exception with the specified throwable.
     */
    public WorkflowPluginException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a Workflow Exception with the specified throwable
     * and error code.
     */
    public WorkflowPluginException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

    /**
     * Instantiates a Workflow Exception with the specified message
     * and error code.
     */
    public WorkflowPluginException(String message, String errorCode) {
        super(message, errorCode);
    }
}
