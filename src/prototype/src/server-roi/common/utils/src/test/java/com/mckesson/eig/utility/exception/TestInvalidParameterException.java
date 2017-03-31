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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.mckesson.eig.utility.testing.ObjectVerifier;
import junit.framework.TestCase;
/**
  * Test case to test the class ApplicationMessageException.
  * 
 *
 */
public class TestInvalidParameterException extends TestCase {
    /**
     * Test value.
     */
    private static final int TEST_VALUE = 30;
    
    /**
     * Test Message.
     */
    private String _testMessage = "This is our test Message";
    
    /**
     * NullPointerException message.
     */
    private String _testNpeMessage = "This is our NPE Message";
    
    /**
     * New NullPointerException.
     */
     private Throwable _npe = new NullPointerException(_testNpeMessage);
     
     /**
      * Constructs the test case with the given name.
      * @param name
      * Name of the test case
      */
    public TestInvalidParameterException(String name) {
        super(name);
    }
    
    /**
     * Tests the InvalidParameterException.
     */
    public void testException() {
        try {
            throwInvalidParameterException();
            fail("Exception should have been thrown");
        } catch (InvalidParameterException e) {
            assertException(e, null, e);
        }
    }
    
    /**
     * Tests the InvalidParameterExceptionusing string.
     */
    public void testExceptionUsingString() {
        try {
            throwInvalidParameterException(_testMessage);
            fail("Exception should have been thrown");
        } catch (InvalidParameterException e) {
            assertException(e, _testMessage, e);
        }
    }
    
    /**
     * Tests the InvalidParameterException with throwable.
     */
    public void testExceptionUsingThrowable() {
        try {
            throwInvalidParameterException(_npe);
            fail("Exception should have been thrown");
        } catch (InvalidParameterException e) {
            assertException(e, _testNpeMessage, _npe);
        }
    }
    
    /**
     * Tests the InvalidParameterException with string and throwable.
     */
    public void testExceptionUsingStringAndThrowable() {
        try {
            throwInvalidParameterException(_testMessage, _npe);
            fail("Exception should have been thrown");
        } catch (InvalidParameterException e) {
            assertException(e, _testMessage, _npe);
        }
    }
    
    /**
     * Tests the InvalidParameterException with string and nested throwable.
     */
    public void testExceptionUsingStringAndNestedThrowable() {
        Throwable t = new Exception(_npe);
        try {
            throwNestedInvalidParameterException(t);
            fail("Exception should have been thrown");
        } catch (InvalidParameterException e) {
            assertException(e, _testMessage, new Exception(_npe));
        }
    }
   
    /**
     * Tests the NestedException.
     */
    public void testNestedException() {
        try {
            throwNestedInvalidParameterException(_npe);
            assertTrue("Exception should have been thrown", false);
        } catch (InvalidParameterException e) {
            assertException(e, _testMessage, _npe);
        }
    }
    
    /**
     * Tests  PrintStackTraceWithPrintWriter.
     */
    public void testPrintStackTraceWithPrintWriter() {

        try {
            throwInvalidParameterException(_testMessage);
        } catch (InvalidParameterException e) {
            StringWriter expected = new StringWriter(TEST_VALUE);
            e.printStackTrace(new PrintWriter(expected));
            assertNotNull("StringWriter should exist", expected);
            String string = expected.toString();
            assertFalse("Test Message wasn't found", string
                    .indexOf(_testMessage) == -1);
        }
    }
   
    /**
     * Tests  NestedPrintStackTraceWithPrintWriter.
     */
    public void testNestedPrintStackTraceWithPrintWriter() {
        try {
            throwInvalidParameterException(_npe);
            fail("Exception should have been thrown");
        } catch (InvalidParameterException e) {
            StringWriter expected = new StringWriter();
            StringWriter received = new StringWriter();
            PrintWriter pw = new PrintWriter(expected);
            pw.println(e.toString());
            _npe.printStackTrace(pw);
            e.printStackTrace(new PrintWriter(received));
            assertEquals(expected.toString(), received.toString());
        }
    }
    
    /**
     * Tests  PrintStackTraceWithPrintStream.
     */
    public void testPrintStackTraceWithPrintStream() {
        try {
            throwInvalidParameterException(_testMessage);
        } catch (InvalidParameterException e) {
            OutputStream expected = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(expected));
            assertNotNull("StringWriter should exist", expected);
            String string = expected.toString();
            assertFalse("Test Message wasn't found", string
                    .indexOf(_testMessage) == -1);
        }
    }
    
    /**
     * Tests  NestedPrintStackTraceWithPrintStream.
     */
    public void testNestedPrintStackTraceWithPrintStream() {
        try {
            throwInvalidParameterException(_npe);
            fail("Exception should have been thrown");
        } catch (InvalidParameterException e) {
            OutputStream expected = new ByteArrayOutputStream();
            OutputStream received = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(expected);
            ps.println(e.toString());
            _npe.printStackTrace(ps);
            e.printStackTrace(new PrintStream(received));
            assertEquals(expected.toString(), received.toString());
        }
    }
    
    /**
     * Tests  PrintStackTrace.
     */
    public void testPrintStackTrace() {

        try {
            throwInvalidParameterException(_testMessage);
        } catch (InvalidParameterException e) {
            OutputStream expected = new ByteArrayOutputStream();
            System.setErr(new PrintStream(expected));
            e.printStackTrace();
            assertNotNull("StringWriter should exist", expected);
            String string = expected.toString();
            assertFalse("Test Message wasn't found", string
                    .indexOf(_testMessage) == -1);
        }
    }
    
    /**
     * Tests  NestedPrintStackTrace.
     */
    public void testNestedPrintStackTrace() {
        try {
            throwInvalidParameterException(_npe);
            fail("Exception should have been thrown");
        } catch (InvalidParameterException e) {
            OutputStream received = new ByteArrayOutputStream();
            PrintStream rps = new PrintStream(received);
            System.setErr(rps);
            System.setOut(rps);
            OutputStream expected = new ByteArrayOutputStream();
            PrintStream eps = new PrintStream(expected);
            eps.println(e.toString());
            _npe.printStackTrace(eps);
            e.printStackTrace();
            // Should not log twice.
            e.log();
            assertEquals(expected.toString(), received.toString());
        }
    }
    
    /**
     * Tests  FillInStackTrace.
     */
    public void testFillInStackTrace() {
        // just calling for now...
        Throwable one = new InvalidParameterException().fillInStackTrace();
        new InvalidParameterException(one).fillInAllStackTraces();
    }
    
    /**
     * Tests  Serialization.
     */
    public void testSerialization() {
        InvalidParameterException one = createException(_npe);
        InvalidParameterException two = (InvalidParameterException) ObjectVerifier
                .serialize(one);
        assertEquals(one.toString(), two.toString());
        assertEquals(one.getExtendedCause().toString(), two.getExtendedCause()
                .toString());
        two.log();
    }
    
    /**
     * Tests  ToStringOnMessage.
     */
    public void testToStringOnMessage() {
        InvalidParameterException e = new InvalidParameterException();
        Object badObject = new Object() {
            public String toString() {
                return null;
            }
        };
        assertEquals("foo", e.toString("foo"));
        assertEquals(" bar\n", e.toString(" bar\n"));
        assertEquals("", e.toString(""));
        assertEquals("  ", e.toString("  "));
        assertEquals("", e.toString(null));
        assertEquals("", e.toString(badObject));
    }
    
    /**
     * Returns an instance of InvalidParameterException.
     * @param message Exception message
     * @return InvalidParameterException
     */
    public InvalidParameterException createException(String message) {
        return new InvalidParameterException(message);
    }
    
    /**
     * Returns an instance of InvalidParameterException.
     * @param t Throwable
     * @return InvalidParameterException
     */
    public InvalidParameterException createException(Throwable t) {
        return new InvalidParameterException(t);
    }
    
    /**
     * Returns an instance of InvalidParameterException.
     * @param message Exception message
     * @param t Throwable
     * @return InvalidParameterException
     */
    public InvalidParameterException createException(String message, Throwable t) {
        return new InvalidParameterException(message, t);
    }
    
    /**
     * Returns an instance of InvalidParameterException.
     * @return InvalidParameterException
     */
    public InvalidParameterException createException() {
        return new InvalidParameterException();
    }
    
    /**
     * Throws InvalidParameterException.
     */
    public void throwInvalidParameterException() {
        throw createException();
    }
    
    /**
     * Throws InvalidParameterException with message.
     * @param message Exception message
     */
    public void throwInvalidParameterException(String message) {
        throw createException(message);
    }
    
    /**
     * Throws InvalidParameterException .
     * @param t Throwable Exception
     */
    public void throwInvalidParameterException(Throwable t) {
        throw createException(t);
    }
    
    /**
     * Throws InvalidParameterException with message and throwable.
     * @param message Exception message
     * @param t Throwable Exception
     */
    public void throwInvalidParameterException(String message, Throwable t) {
        throw createException(message, t);
    }
    
    /**
     * Throws InvalidParameterException .
     * @param t  Throwable Exception
     */
    public void throwNestedInvalidParameterException(Throwable t) {
        throw createException(createException(_testMessage, t));
    }
    
    /**Asserts if the exception message and the value of the string variable
     * message are equal.
     * @param e ApplicationMessageException
     * @param message message to be compared with 
     * @param cause Exception message
     */
    public void assertException(InvalidParameterException e, String message,
            Throwable cause) {
        assertEquals(message, e.getMessage());
        assertEquals(cause.toString(), e.getExtendedCause().toString());
    }
}


