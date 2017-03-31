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
 *Test Case to test the class ConcurrencyInterruptedException
 */
public class TestConcurrencyInterruptedException extends TestCase {
    
    /**
     * Message for the exception .
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
    
    /**Constructs the test case with the given name. 
     * @param name
     *            Test Case Name
     */
    public TestConcurrencyInterruptedException(String name) {
        super(name);
    }
    
    /**
     * Tests the ConcurrencyInterruptedException.
     */
    public void testException() {
        try {
            throwConcurrencyInterruptedException();
            fail("Exception should have been thrown");
        } catch (ConcurrencyInterruptedException e) {
            assertException(e, null, e);
        }
    }
   
    /**
     * Tests the ConcurrencyInterruptedException with a text message.
     */
    public void testExceptionUsingString() {
        try {
            throwConcurrencyInterruptedException(_testMessage);
            fail("Exception should have been thrown");
        } catch (ConcurrencyInterruptedException e) {
            assertException(e, _testMessage, e);
        }
    }
    
    /**
     * Tests the ConcurrencyInterruptedException using throwable.
     */
    public void testExceptionUsingThrowable() {
        try {
            throwConcurrencyInterruptedException(_npe);
            fail("Exception should have been thrown");
        } catch (ConcurrencyInterruptedException e) {
            assertException(e, _testNpeMessage, _npe);
        }
    }
    
    /**
     * Tests the ConcurrencyInterruptedException using throwable and a message.
     */
    public void testExceptionUsingStringAndThrowable() {
        try {
            throwConcurrencyInterruptedException(_testMessage, _npe);
            fail("Exception should have been thrown");
        } catch (ConcurrencyInterruptedException e) {
            assertException(e, _testMessage, _npe);
        }
    }
    
    /**
     * Throws a ConcurrencyInterruptedException.
     * returns a new ConcurrencyInterruptedException.
     */
    public void throwConcurrencyInterruptedException() {
        throw createException();
    }
   
    /**
     * Throws a ConcurrencyInterruptedException with an exception message.
     * @param message
     * The message for the exception
     */
    public void throwConcurrencyInterruptedException(String message) {
        throw createException(message);
    }
    
    /**
     * Throws a ConcurrencyInterruptedException with an instance of type
     * Throwable as a paramter.
     * @param t
     * Throwable cause
     */
    public void throwConcurrencyInterruptedException(Throwable t) {
        throw createException(t);
    }
   
    /**
     * Throws a ConcurrencyInterruptedException with parameters message and Throwable.
     * @param message
     * Exception message
     * @param t
     * Exception
     */
    public void throwConcurrencyInterruptedException(String message, Throwable t) {
        throw createException(message, t);
    }
    
    /**
     * Retuns a ConcurrencyInterruptedException with exception message.
     * @param message
     * Exception Message 
     * @return
     * Retuns a ConcurrencyInterruptedException
     */
    public ConcurrencyInterruptedException createException(String message) {
        return new ConcurrencyInterruptedException(message);
    }
    
    /**
     * Retuns a ConcurrencyInterruptedException. 
     * @param t
     * Throwable Exception
     * @return
     * Returns a ConcurrencyInterruptedException
     */
    public ConcurrencyInterruptedException createException(Throwable t) {
        return new ConcurrencyInterruptedException(t);
    }
    
    /**
     * Retuns a ConcurrencyInterruptedException.
     * @param message
     * Exception Message
     * @param t
     * Throwable Exception
     * @return
     * Returns a ConcurrencyInterruptedException
     */
    public ConcurrencyInterruptedException createException(String message, Throwable t) {
        return new ConcurrencyInterruptedException(message, t);
    }
    
    /**
     * Retuns a ConcurrencyInterruptedException .
     * @return
     * Returns a ConcurrencyInterruptedException
     */
    public ConcurrencyInterruptedException createException() {
        return new ConcurrencyInterruptedException();
    }
    
    /**
     *  Asserts if the exception message and the value of the string variable
     * message are equal.
     * @param e 
     * The ConcurrencyInterruptedException
     * @param message
     * Message to compare with
     * @param cause
     * Cause for the message
     */
    public void assertException(ConcurrencyInterruptedException e, String message,
            Throwable cause) {
        assertEquals(message, e.getMessage());
        assertEquals(cause.toString(), e.getExtendedCause().toString());
    }
}


