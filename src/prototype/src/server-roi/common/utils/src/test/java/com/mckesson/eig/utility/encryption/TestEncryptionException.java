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
package com.mckesson.eig.utility.encryption;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.mckesson.eig.utility.testing.ObjectVerifier;

import junit.framework.TestCase;

/**
 * Test Case to test the class EncryptionException.
 */
public class TestEncryptionException extends TestCase {

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
     * New instance of NullPointerException.
     */
    private Throwable _npe = new NullPointerException(_testNpeMessage);

    /**
     * Constructs the test case with the given name.
     * 
     * @param name
     *            Name of the test case
     */
    public TestEncryptionException(String name) {
        super(name);
    }

    /**
     * Tests the EncryptionException.
     */
    public void testException() {
        try {
            throwEncryptionException();
            fail("Exception should have been thrown");
        } catch (EncryptionException e) {
            assertException(e, null, e);
        }
    }

    /**
     * Tests the ConcurrencyException with a text message. The control should go
     * the catch block.
     */
    public void testExceptionUsingString() {
        try {
            throwEncryptionException(_testMessage);
            fail("Exception should have been thrown");
        } catch (EncryptionException e) {
            assertException(e, _testMessage, e);
        }
    }

    /**
     * Tests the ConcurrencyException with throwable.
     */
    public void testExceptionUsingThrowable() {
        try {
            throwEncryptionException(_npe);
            fail("Exception should have been thrown");
        } catch (EncryptionException e) {
            assertException(e, _testNpeMessage, _npe);
        }
    }

    /**
     * Tests the ConcurrencyException using throwable and a message.
     */
    public void testExceptionUsingStringAndThrowable() {
        try {
            throwEncryptionException(_testMessage, _npe);
            fail("Exception should have been thrown");
        } catch (EncryptionException e) {
            assertException(e, _testMessage, _npe);
        }
    }

    /**
     * Tests the ConcurrencyException using Nested throwable and a message.
     */
    public void testExceptionUsingStringAndNestedThrowable() {
        Throwable t = new Exception(_npe);
        try {
            throwNestedEncryptionException(t);
            fail("Exception should have been thrown");
        } catch (EncryptionException e) {
            assertException(e, _testMessage, new Exception(_npe));
        }
    }

    /**
     * Tests nested Exception.
     */
    public void testNestedException() {
        try {
            throwNestedEncryptionException(_npe);
            assertTrue("Exception should have been thrown", false);
        } catch (EncryptionException e) {
            assertException(e, _testMessage, _npe);
        }
    }

    /**
     * Tests PrintStackTraceWithPrintWriter.
     */
    public void testPrintStackTraceWithPrintWriter() {

        try {
            throwEncryptionException(_testMessage);
        } catch (EncryptionException e) {
            StringWriter expected = new StringWriter(TEST_VALUE);
            e.printStackTrace(new PrintWriter(expected));
            assertNotNull("StringWriter should exist", expected);
            String string = expected.toString();
            assertFalse("Test Message wasn't found", string
                    .indexOf(_testMessage) == -1);
        }
    }

    /**
     * Tests NestedPrintStackTraceWithPrintWriter.
     */
    public void testNestedPrintStackTraceWithPrintWriter() {
        try {
            throwEncryptionException(_npe);
            fail("Exception should have been thrown");
        } catch (EncryptionException e) {
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
     * Tests PrintStackTraceWithPrintStream.
     */
    public void testPrintStackTraceWithPrintStream() {

        try {
            throwEncryptionException(_testMessage);
        } catch (EncryptionException e) {
            OutputStream expected = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(expected));
            assertNotNull("StringWriter should exist", expected);
            String string = expected.toString();
            assertFalse("Test Message wasn't found", string
                    .indexOf(_testMessage) == -1);
        }
    }
    
    /**
     * Tests NestedPrintStackTraceWithPrintStream.
     */
    public void testNestedPrintStackTraceWithPrintStream() {
        try {
            throwEncryptionException(_npe);
            fail("Exception should have been thrown");
        } catch (EncryptionException e) {
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
     * Tests PrintStackTrace.
     */
    public void testPrintStackTrace() {

        try {
            throwEncryptionException(_testMessage);
        } catch (EncryptionException e) {
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
     * Tests NestedPrintStackTrace.
     */
    public void testNestedPrintStackTrace() {
        try {
            throwEncryptionException(_npe);
            fail("Exception should have been thrown");
        } catch (EncryptionException e) {
            OutputStream received = new ByteArrayOutputStream();
            PrintStream rps = new PrintStream(received);
            System.setErr(rps);
            System.setOut(rps);
            OutputStream expected = new ByteArrayOutputStream();
            PrintStream eps = new PrintStream(expected);
            eps.println(e.toString());
            _npe.printStackTrace(eps);
            e.printStackTrace();
            assertEquals(expected.toString(), received.toString());
        }
    }
    
    /**
     * Tests FillInStackTrace.
     */
    public void testFillInStackTrace() {
        // just calling for now...
        Throwable one = new EncryptionException().fillInStackTrace();
        new EncryptionException(one).fillInAllStackTraces();
    }
    
    /**
     * Tests Serialization.
     */
    public void testSerialization() {
        EncryptionException one = createException(_npe);
        EncryptionException two = (EncryptionException) ObjectVerifier
                .serialize(one);
        assertEquals(one.toString(), two.toString());
        assertEquals(one.getExtendedCause().toString(), two.getExtendedCause()
                .toString());
    }
    
    /**
     * Creates an EncryptionException with message.
     * @param message
     *            Exception message
     * @return EncryptionException with message
     */
    public EncryptionException createException(String message) {
        return new EncryptionException(message);
    }
   
    /**
     * Creates EncryptionException with Throwable.
     * @param t
     *            Throwable.
     * @return EncryptionException with Throwable
     */
    public EncryptionException createException(Throwable t) {
        return new EncryptionException(t);
    }
    
    /**
     * EncryptionException with Throwable and message.
     * @param message
     *            Exception message.
     * @param t
     *            Throwable
     * @return EncryptionException with Throwable and message
     */
    public EncryptionException createException(String message, Throwable t) {
        return new EncryptionException(message, t);
    }
    
    /**
     * Returns an EncryptionException.
     * @return EncryptionException
     */
    public EncryptionException createException() {
        return new EncryptionException();
    }
    /**
     * Calls method that creates EncryptionException.
     */
    public void throwEncryptionException() {
        throw createException();
    }
   
    /**
     * Calls method that creates EncryptionException with message.
     * @param message
     *            Exception message
     */
    public void throwEncryptionException(String message) {
        throw createException(message);
    }
    
    /**
     * Calls method that creates EncryptionException with Throwable.
     * @param t
     *            Throwable
     */
    public void throwEncryptionException(Throwable t) {
        throw createException(t);
    }
   
    /**
     * Calls method that creates EncryptionException with Throwable and message.
     * @param message
     *            Exception message
     * @param t
     *            Throwable
     */
    public void throwEncryptionException(String message, Throwable t) {
        throw createException(message, t);
    }

    /**
     * Calls method that creates EncryptionException with Throwable and message.
     * @param t
     *            Throwable.
     */
    public void throwNestedEncryptionException(Throwable t) {
        throw createException(createException(_testMessage, t));
    }
    
    /**
     *  Asserts if the exception message and the value of the string variable
     * message are equal.
     * @param e
     *            The EncryptionException
     * @param message
     *            Message to compare with
     * @param cause
     *            Cause for the message
     */
    public void assertException(EncryptionException e, String message,
            Throwable cause) {
        assertEquals(message, e.getMessage());
        assertEquals(cause.toString(), e.getExtendedCause().toString());
    }
}
