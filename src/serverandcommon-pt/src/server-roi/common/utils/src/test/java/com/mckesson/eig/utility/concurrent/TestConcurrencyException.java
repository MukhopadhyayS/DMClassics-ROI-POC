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
package com.mckesson.eig.utility.concurrent;
import junit.framework.TestCase;

/**
 * Test Case to test the class ConcurrencyException.
 */
public class TestConcurrencyException extends TestCase {
   
    /**
     * Message for the exception.
     */
    private String _testMessage = "This is our test Message";
   
    /**
     * Message for the Null pointer exception .
     */
    private String _testNpeMessage = "This is our NPE Message";
    
    /**
     * Creating an instance of a NullPointerException.
     */
    private Throwable _npe = new NullPointerException(_testNpeMessage);
    
    /**
     * Constructs the test case with the given name.
     * 
     * @param name
     *            Test Case Name
     */
    public TestConcurrencyException(String name) {
        super(name);
    }
    
    /**
     * Tests the ConcurrencyException.
     */
    public void testException() {
        try {
            throwConcurrencyException();
            fail("Exception should have been thrown");
        } catch (ConcurrencyException e) {
            assertException(e, null, e);
        }
    }
    
    /**
     * Tests the ConcurrencyException with a text message.
     */
    public void testExceptionUsingString() {
        try {
            throwConcurrencyException(_testMessage);
            fail("Exception should have been thrown");
        } catch (ConcurrencyException e) {
            assertException(e, _testMessage, e);
        }
    }
    
    /**
     * Tests the ConcurrencyException with throwable.
     */
    public void testExceptionUsingThrowable() {
        try {
            throwConcurrencyException(_npe);
            fail("Exception should have been thrown");
        } catch (ConcurrencyException e) {
            assertException(e, _testNpeMessage, _npe);
        }
    }
   
    /**
     * Tests the ConcurrencyException using throwable and a message.
     */
    public void testExceptionUsingStringAndThrowable() {
        try {
            throwConcurrencyException(_testMessage, _npe);
            fail("Exception should have been thrown");
        } catch (ConcurrencyException e) {
            assertException(e, _testMessage, _npe);
        }
    }
   
    /**
     * Calls the method createException that returns a new Concurrency
     * Exception.
     */
    public void throwConcurrencyException() {
        throw createException();
    }
    
    /**
     * Method that throws a ConcurrencyException.
     * 
     * @param message
     *            The message for the exception
     */
    public void throwConcurrencyException(String message) {
        throw createException(message);
    }
    
    /**
     * Method that throws a ConcurrencyException with Throwable as a parameter.
     * 
     * @param t
     *            Throwable cause
     */
    public void throwConcurrencyException(Throwable t) {
        throw createException(t);
    }
    
    /**
     * Method that throws a ConcurrencyException with Throwable and exception
     * message as a parameter.
     * 
     * @param message
     *            Exception message
     * @param t
     *            Exception
     */
    public void throwConcurrencyException(String message, Throwable t) {
        throw createException(message, t);
    }
    
    /**
     * Creates a new instance of ConcurrencyException with message.
     * 
     * @param message
     *            Exception Message
     * @return Retuns a Concurrency Exception
     */
    public ConcurrencyException createException(String message) {
        return new ConcurrencyException(message);
    }
    
    /**
     * Creates a new instance of ConcurrencyException with Throwable.
     * 
     * @param t
     *            Throwable Exception
     * @return Returns a Concurrency Exception
     */
    public ConcurrencyException createException(Throwable t) {
        return new ConcurrencyException(t);
    }
    
    /**
     * Creates a new instance of ConcurrencyException with message and
     * Throwable.
     * 
     * @param message
     *            Exception Message
     * @param t
     *            Exception
     * @return Returns a Concurrency Exception
     */
    public ConcurrencyException createException(String message, Throwable t) {
        return new ConcurrencyException(message, t);
    }
    
    /**
     * Creates a new instance of the ConcurrencyException.
     * 
     * @return Returns a ConcurrencyException
     */
    public ConcurrencyException createException() {
        return new ConcurrencyException();
    }
   
    /**
     * Asserts if the exception message and the value of the string variable
     * message are equal.
     * 
     * @param e
     *            The Concurrency exception
     * @param message
     *            Message to compare with
     * @param cause
     *            Cause for the message
     */
    public void assertException(ConcurrencyException e, String message,
            Throwable cause) {
        assertEquals(message, e.getMessage());
        assertEquals(cause.toString(), e.getExtendedCause().toString());
    }
}
