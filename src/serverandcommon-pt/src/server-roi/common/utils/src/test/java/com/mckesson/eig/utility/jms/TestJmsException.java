/**
 *
 * Copyright 2002 McKesson Information Solutions
 *
 * The copyright to the computer program(s) herein
 * is the property of McKesson Information Solutions.
 * The program(s) may be used and/or copied only with
 * the written permission of McKesson Information Solutions
 * or in accordance with the terms and conditions 
 * stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */
package com.mckesson.eig.utility.jms;

import junit.framework.Test;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.TestApplicationException;
import com.mckesson.eig.utility.testing.CoverageSuite;

/**
 * <p>
 * <strong>TestUtilitiesException</strong>
 * </p>
 * 
 * 
 * 
 */
public class TestJmsException extends TestApplicationException {
    /**
     * Constructor for ConfigurationExceptionTest.
     * 
     * @param arg0
     */
    public TestJmsException(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestJmsException.class, JmsException.class);
    }

    public ApplicationException createException() {
        return new JmsException();
    }

    /**
     * Creates an applicationException with the passed in String
     * 
     * @param message
     *            Message to create with.
     */
    public ApplicationException createException(String message) {
        return new JmsException(message);
    }

    /**
     * Creates an applicationException with the passed in Throwable
     * 
     * @param t
     *            Throwable to create exception with
     */
    public ApplicationException createException(Throwable t) {
        return new JmsException(t);
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
        return new JmsException(message, t);
    }

}
