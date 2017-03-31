
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
 *Test Case to test the class ConcurrencyTimeoutException
 */
public class TestConcurrencyTimeoutException extends TestCase {
   
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
     * Test Case Name
     */
    public  TestConcurrencyTimeoutException(String name) {
        super(name);
    }
    
    /**
     * Tests the ConcurrencyTimeoutException.
     */
    public void testException() {
        try {
            throwConcurrencyTimeoutException();
            fail("Exception should have been thrown");
        } catch (ConcurrencyTimeoutException e) {
            assertException(e, null, e);
        }
    }
   
    /**
     * Tests the ConcurrencyTimeoutException with a text message.
     */
    public void testExceptionUsingString() {
        try {
            throwConcurrencyTimeoutException(_testMessage);
            fail("Exception should have been thrown");
        } catch (ConcurrencyTimeoutException e) {
            assertException(e, _testMessage, e);
        }
    }
    
    /**
     * Tests the ConcurrencyTimeoutException with throwable.
     */
    public void testExceptionUsingThrowable() {
        try {
            throwConcurrencyTimeoutException(_npe);
            fail("Exception should have been thrown");
        } catch (ConcurrencyTimeoutException e) {
            assertException(e, _testNpeMessage, _npe);
        }
    }
    
    /**
     * Tests the ConcurrencyTimeoutException with throwable and a message.
     */
    public void testExceptionUsingStringAndThrowable() {
        try {
            throwConcurrencyTimeoutException(_testMessage, _npe);
            fail("Exception should have been thrown");
        } catch (ConcurrencyTimeoutException e) {
            assertException(e, _testMessage, _npe);
        }
    }
    
    /**
     * Calls the method createException that 
     * returns a new ConcurrencyTimeoutException.
     */
    public void throwConcurrencyTimeoutException() {
        throw createException();
    }
    
    /**
     * Throws ConcurrencyTimeoutException.
     * @param message
     * The message for the exception
     */
    public void throwConcurrencyTimeoutException(String message) {
        throw createException(message);
    }
    
    /**Throws ConcurrencyTimeoutException .
     * @param t
     * Throwable cause
     */
    public void throwConcurrencyTimeoutException(Throwable t) {
        throw createException(t);
    }
    
    /**
     * Throws ConcurrencyTimeoutException.
     * @param message
     *  Exception message
     * @param t
     * Exception
     */
    public void throwConcurrencyTimeoutException(String message, Throwable t) {
        throw createException(message, t);
    }
   
    /**
     * Retuns a ConcurrencyTimeoutException.
     * @param message
     * Exception message
     * @return
     * Retuns a ConcurrencyTimeoutException
     */
    public ConcurrencyTimeoutException createException(String message) {
        return new ConcurrencyTimeoutException(message);
    }
   
    /**
     * Returns a ConcurrencyTimeoutException.
     * @param t
     *  Throwable Exception
     * @return
     * Returns a ConcurrencyTimeoutException
     */
    public ConcurrencyTimeoutException createException(Throwable t) {
        return new ConcurrencyTimeoutException(t);
    }
    
    /**
     * Retuns a ConcurrencyTimeoutException.
     * @param message
     * Exception Message
     * @param t
     * Throwable Exception
     * @return
     * Retuns a ConcurrencyTimeoutException
     */
    public ConcurrencyTimeoutException createException(String message, Throwable t) {
        return new ConcurrencyTimeoutException(message, t);
    }
    /**
     * Retuns a ConcurrencyTimeoutException.
     * @return
     * Retuns a ConcurrencyTimeoutException
     */
    public ConcurrencyTimeoutException createException() {
        return new ConcurrencyTimeoutException();
    }
    /**
     *  Asserts if the exception message and the value of the string variable
     * message are equal.
     * @param e
     * The ConcurrencyTimeoutException
     * @param message
     * Message to compare with
     * @param cause
     * Cause for the message
     */
    public void assertException(ConcurrencyTimeoutException e, String message,
            Throwable cause) {
        assertEquals(message, e.getMessage());
        assertEquals(cause.toString(), e.getExtendedCause().toString());
    }
}


