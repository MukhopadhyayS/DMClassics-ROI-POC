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
package com.mckesson.eig.wsfw.test.axis;

import com.mckesson.eig.wsfw.axis.CastorContextException;

import junit.framework.TestCase;

/**
 * Tests the CastorContextException. Provides Methods for testing String and
 * Throwable exceptions also.
 * 
 */
public class TestCastorContextException extends TestCase {

    /**
     * Message for testing nested exception.
     */
    private String _nestedMessage = "This is our Nested Message";

    /**
     * Throws an NullpointerException.
     */
    private Throwable _throwable = new NullPointerException(_nestedMessage);

    /**
     * Exception message.
     */
    private String _testMessage = "This exception is always thrown";

    /**
     * Sets up the data essential for testing CastorContextException.
     * 
     * @throws Exception
     *             General Exception.
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        UnitSpringInitialization.init();
    }

    /**
     * Tests this default Exception.
     */
    public TestCastorContextException() {
        try {
            throwCastorContextException();
            fail("Exception should have been thrown");
        } catch (CastorContextException e) {
            assertException(e, null, e);
        }
    }

    /**
     * Tests this exception which gives a brief message.
     */
    public void testExceptionUsingString() {
        try {
            throwCastorContextException(_testMessage);
            fail("Exception should have been thrown");
        } catch (CastorContextException e) {
            assertException(e, _testMessage, e);
        }
    }

    /**
     * Tests this exception with String and Throwable.
     */
    public void testExceptionUsingStringAndThrowable() {
        try {
            throwCastorContextException(_testMessage, _throwable);
            fail("Exception should have been thrown");
        } catch (CastorContextException e) {
            assertException(e, _testMessage, _throwable);
        }
    }

    /**
     * Tests this exception with Throwable.
     */
    public void testExceptionUsingThrowable() {
        try {
            throwCastorContextException(_throwable);
            fail("Exception should have been thrown");
        } catch (CastorContextException e) {
            assertException(e, _nestedMessage, _throwable);
        }
    }

    /**
     * Returns CastorContextException with a brief message.
     * 
     * @param message
     *            description.
     * @return CastorContextException
     */
    public CastorContextException createException(String message) {
        return new CastorContextException(message);
    }

    /**
     * Returns CastorContextException with a cause.
     * 
     * @param t
     *            cause.
     * @return CastorContextException
     */

    public CastorContextException createException(Throwable t) {
        return new CastorContextException(t);
    }

    /**
     * Returns CastorContextException with a message and throwable cause.
     * 
     * @param message
     *            Description.
     * @param t
     *            cause.
     * @return CastorContextException
     */
    public CastorContextException createException(String message, Throwable t) {
        return new CastorContextException(message, t);
    }

    /**
     * Returns CastorContextException.
     * 
     * @return CastorContextException
     */
    public CastorContextException createException() {
        return new CastorContextException();
    }

    /**
     * Creates and throws an Exception.
     */
    public void throwCastorContextException() {
        throw createException();
    }

    /**
     * Throw this exception with a message.
     * 
     * @param message
     *            Detail.
     */
    public void throwCastorContextException(String message) {
        throw createException(message);
    }

    /**
     * Creates an throwable exception.
     * 
     * @param t
     *            cause.
     */
    public void throwCastorContextException(Throwable t) {
        throw createException(t);
    }

    /**
     * Creates this exception with String and Throwable.
     * 
     * @param message
     *            Detail
     * @param t
     *            Cause.
     */
    public void throwCastorContextException(String message, Throwable t) {
        throw createException(message, t);
    }

    /**
     * Tests Nested Exception.
     * 
     * @param t
     *            cause.
     */
    public void throwNestedCastorContextException(Throwable t) {
        throw createException(createException(_testMessage, t));
    }

    /**
     * Asserts Whether the exception throws the right cause and message.
     * 
     * @param e
     *            CastorContextException Detail.
     * @param message
     *            Message
     * @param cause
     *            Cause of this Throwable.
     */
    public void assertException(CastorContextException e, String message,
            Throwable cause) {
        assertEquals(message, e.getMessage());
        assertEquals(cause.toString(), e.getExtendedCause().toString());
    }
}
