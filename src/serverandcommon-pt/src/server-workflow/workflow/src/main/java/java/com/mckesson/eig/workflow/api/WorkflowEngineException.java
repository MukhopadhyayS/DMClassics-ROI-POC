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


/**
 * @author Mckesson
 * @date   January 21, 2009
 * @since  HECM 1.2
 *
 * This class is a sub class of WorkflowException and this Exception
 * is thrown when an exception occurs in the workflow engine module level.
 */
public class WorkflowEngineException
extends WorkflowException {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = -882166072108619212L;

    /**
     * Instantiates a WorkflowException with the specified throwable.
     */
    public WorkflowEngineException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a WorkflowException with the specified throwable
     * and error code.
     */
    public WorkflowEngineException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

    /**
     * Instantiates a WorkflowException with the specified message
     * and error code.
     */
    public WorkflowEngineException(String message, String errorCode) {
        super(message, errorCode);
    }
}
