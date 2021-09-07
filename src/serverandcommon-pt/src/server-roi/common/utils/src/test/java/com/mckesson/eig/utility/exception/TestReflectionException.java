/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.utility.exception;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;

/**
 * TestInvalidStateException.java
 */
public class TestReflectionException extends TestApplicationException {

    /**
     * Constructor for TestInvalidStateException.
     * 
     * @param arg0
     */
    public TestReflectionException(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestReflectionException.class,
                ReflectionException.class);
    }

    public ApplicationException createException() {
        return new ReflectionException();
    }

    /**
     * Creates an applicationException with the passed in String
     * 
     * @param message
     *            Message to create with.
     */
    public ApplicationException createException(String message) {
        return new ReflectionException(message);
    }

    /**
     * Creates an applicationException with the passed in Throwable
     * 
     * @param t
     *            Throwable to create exception with
     */
    public ApplicationException createException(Throwable t) {
        return new ReflectionException(t);
    }

    /**
     * Creates an applicationException with the passed in String
     * 
     * @param message
     *            Message to create with.
     * @param t
     *            Exception to chain with.
     */
    public ApplicationException createException(String message, Throwable t) {
        return new ReflectionException(message, t);
    }

}
