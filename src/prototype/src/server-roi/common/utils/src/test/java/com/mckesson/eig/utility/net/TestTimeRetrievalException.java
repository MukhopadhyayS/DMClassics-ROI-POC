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
package com.mckesson.eig.utility.net;

import junit.framework.TestCase;

/**
 * Test case to test the class TimeRetrievalException.
 *
 */
public class TestTimeRetrievalException extends TestCase {
    
    /**
     * Message for the exception .
     */
    private String _testMessage = "This is to test time retrieval exception";
   
    /**
     * Message for the Null pointer exception .
     */
    private String _testNpeMessage = "This is our NPE Message";
   
    /**
     * Creating an instance of a NullPointerException.
     */
    private Throwable _npe = new NullPointerException(_testNpeMessage);
    
    /** Constructs the test case with the given name. 
     * @param name
     *            Test Case Name
     */
    public TestTimeRetrievalException(String name) {
        super(name);
    }
   
    /**
     * Tests the TimeRetrievalException.
     */ 
    public void testException() {
        try {
            throwTimeRetrievalException();
            fail("Exception should have been thrown");
        } catch (TimeRetrievalException e) {
            assertException(e, null, e);
        }
    }
    
    /**
     * Tests the TimeRetrievalException with a text message.
     */
    public void testExceptionUsingString() {
        try {
            throwTimeRetrievalException(_testMessage);
            fail("Exception should have been thrown");
        } catch (TimeRetrievalException e) {
            assertException(e, _testMessage, e);
        }
    }
    
    /**
     * Tests the TimeRetrievalException with throwable.
     */
    public void testExceptionUsingThrowable() {
        try {
            throwTimeRetrievalException(_npe);
            fail("Exception should have been thrown");
        } catch (TimeRetrievalException e) {
            assertException(e, _testNpeMessage, _npe);
        }
    }
    
    /**
     * Tests the TimeRetrievalException using throwable and a message.
     */
    public void testExceptionUsingStringAndThrowable() {
        try {
            throwTimeRetrievalException(_testMessage, _npe);
            fail("Exception should have been thrown");
        } catch (TimeRetrievalException e) {
            assertException(e, _testMessage, _npe);
        }
    }
   
    /**Throws a new  TimeRetrievalException.
     * Calls the method createException that 
     * returns a new TimeRetrievalException.
     */
    public void throwTimeRetrievalException() {
        throw createException();
    }
   
    /**
     * Throws a new  TimeRetrievalException.
     * @param message
     * The message for the exception
     */
    public void throwTimeRetrievalException(String message) {
        throw createException(message);
    }
    
    /**
     * Throws a new  TimeRetrievalException.
     * @param t
     * Throwable cause
     */
    public void throwTimeRetrievalException(Throwable t) {
        throw createException(t);
    }
    
    /**Throws a new  TimeRetrievalException.
     * @param message
     *        Exception message
     * @param t
     *       Exception
     */
    public void throwTimeRetrievalException(String message, Throwable t) {
        throw createException(message, t);
    }
    
    /**
     * Returns a new instance of the TimeRetrievalException.
     * @param message
     *  Exception Message 
     * @return
     * Retuns a TimeRetrievalException
     */
    public TimeRetrievalException createException(String message) {
        return new TimeRetrievalException(message);
    }
   
    /**
     * Returns a new instance of the TimeRetrievalException.
     * @param t 
     * Throwable Exception
     * @return
     * Returns a TimeRetrievalException
     */
    public TimeRetrievalException createException(Throwable t) {
        return new TimeRetrievalException(t);
    }
   
    /**
     * Returns a new instance of the TimeRetrievalException.
     * @param message
     * Exception Message
     * @param t
     * Exception
     * @return
     * Returns a TimeRetrievalException
     */
    public TimeRetrievalException createException(String message, Throwable t) {
        return new TimeRetrievalException(message, t);
    }
    /**
     * Returns a new instance of the  TimeRetrievalException.
     * @return
     * Returns a TimeRetrievalException
     */
    public TimeRetrievalException createException() {
        return new TimeRetrievalException();
    }
   
    /**
     * Asserts if the exception message and the value of the string variable
     * message are equal.
     * @param e
     *  The TimeRetrievalException
     * @param message
     * Message to compare with
     * @param cause
     * Cause for the message
     */
    public void assertException(TimeRetrievalException e, String message,
            Throwable cause) {
        assertEquals(message, e.getMessage());
        assertEquals(cause.toString(), e.getExtendedCause().toString());
    }

}
