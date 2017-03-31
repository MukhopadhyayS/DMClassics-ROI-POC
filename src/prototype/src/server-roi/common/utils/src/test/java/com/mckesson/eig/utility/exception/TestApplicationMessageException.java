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
 */
public class TestApplicationMessageException extends TestCase {

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
     * Array of keys.
     */
    private String[] _testMessageKey = {"Key1", "Key2"};

    /**
     * New NullPointerException.
     */
    private Throwable _npe = new NullPointerException(_testNpeMessage);

    /**
     * Constructs the test case with the given name.
     * 
     * @param name
     *            Name of the test case
     */
    public TestApplicationMessageException(String name) {
        super(name);
    }

    /**
     * Tests the ApplicationMessageException.
     */
    public void testException() {
        try {
            throwApplicationMessageException();
            fail("Exception should have been thrown");
        } catch (ApplicationMessageException e) {
            assertException(e, null, e);
        }
    }

    /**
     * Tests the ApplicationMessageException using string.
     */
    public void testExceptionUsingString() {
        try {
            throwApplicationMessageException(_testMessage);
            fail("Exception should have been thrown");
        } catch (ApplicationMessageException e) {
            assertException(e, _testMessage, e);
        }
    }

    /**
     * Tests the ApplicationMessageException with throwable.
     */
    public void testExceptionUsingThrowable() {
        try {
            throwApplicationMessageException(_npe);
            fail("Exception should have been thrown");
        } catch (ApplicationMessageException e) {
            assertException(e, _testNpeMessage, _npe);
        }
    }

    /**
     * Tests the ApplicationMessageException with string and key.
     */
    public void testExceptionUsingStringAndKey() {
        try {
            throwApplicationMessageException(_testMessage, _testMessageKey);
            fail("Exception should have been thrown");
        } catch (ApplicationMessageException e) {
            assertException(e, _testMessage, e);
        }
    }

    /**
     * Tests the ApplicationMessageException with string ,throwable and key.
     */
    public void testExceptionUsingStringAndKeyAndThrowable() {
        try {
            throwApplicationMessageException(_testMessage, _testMessageKey, _npe);
            fail("Exception should have been thrown");
        } catch (ApplicationMessageException e) {
            assertException(e, _testMessage, _npe);
        }
    }

    /**
     * Tests PrintStackTraceWithPrintWriter.
     */
    public void testPrintStackTraceWithPrintWriter() {
        try {
            throwApplicationMessageException(_testMessage);
        } catch (ApplicationMessageException e) {
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
            throwApplicationMessageException(_npe);
            fail("Exception should have been thrown");
        } catch (ApplicationMessageException e) {
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
            throwApplicationMessageException(_testMessage);
        } catch (ApplicationMessageException e) {
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
            throwApplicationMessageException(_npe);
            fail("Exception should have been thrown");
        } catch (ApplicationMessageException e) {
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
            throwApplicationMessageException(_testMessage);
        } catch (ApplicationMessageException e) {
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
            throwApplicationMessageException(_npe);
            fail("Exception should have been thrown");
        } catch (ApplicationMessageException e) {
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
     * Tests FillInStackTrace.
     */
    public void testFillInStackTrace() {
        // just calling for now...
        Throwable one = new ApplicationMessageException().fillInStackTrace();
        new ApplicationMessageException(one).fillInAllStackTraces();
    }
   
    
    /**
     * Tests tGetMessageKeys.
     */
    public void testGetMessageKeys() {

        ApplicationMessageException ame = 
            new ApplicationMessageException(_testNpeMessage, _testMessageKey);
        String [] returnKeys = ame.getMessageKeys();
        assertNotNull(returnKeys);
        assertEquals(returnKeys.length, 2);
        assertEquals(_testMessageKey[0], returnKeys[0]);
        assertEquals(_testMessageKey[1], returnKeys[1]);
    }
    
    /**
     * Tests Serialization.
     */
    public void testSerialization() {
        ApplicationMessageException one = createException(_npe);
        ApplicationMessageException two = (ApplicationMessageException) ObjectVerifier
                .serialize(one);
        assertEquals(one.toString(), two.toString());
        assertEquals(one.getExtendedCause().toString(), two.getExtendedCause()
                .toString());
        two.log();
    }
    
    /**
     * Returns an ApplicationMessageException.
     * @param message
     *            Exception message
     * @return ApplicationMessageException
     */
    public ApplicationMessageException createException(String message) {
        return new ApplicationMessageException(message);
    }
    
    /**
     * Returns an ApplicationMessageException.
     * @param t
     *            Throwable exception
     * @return ApplicationMessageException
     */
    public ApplicationMessageException createException(Throwable t) {
        return new ApplicationMessageException(t);
    }
    
    /**
     * Returns an ApplicationMessageException.
     * @return ApplicationMessageException
     */
    public ApplicationMessageException createException() {
        return new ApplicationMessageException();
    }
   
    /**
     * Returns an ApplicationMessageException.
     * @param message
     *            Exception message
     * @param messageKey
     *            message key
     * @return ApplicationMessageException
     */
    public ApplicationMessageException createException(String message,
            String[] messageKey) {
        return new ApplicationMessageException(message, messageKey);
    }

    /**
     * Returns an ApplicationMessageException.
     * @param message
     *            Exception message
     * @param messageKey
     *            message key
     * @param t
     *            Throwable Exception
     * @return ApplicationMessageException
     */
    public ApplicationMessageException createException(String message,
            String[] messageKey, Throwable t) {
        return new ApplicationMessageException(message, messageKey, t);
    }

    /**
     * Calls method createException().
     */
    public void throwApplicationMessageException() {
        throw createException();
    }

    /**
     * Throws ApplicationMessageException with message.
     * @param message
     *            Exception message. Calls method createException()with message
     */
    public void throwApplicationMessageException(String message) {
        throw createException(message);
    }

    /**
     * Throws ApplicationMessageException with message.
     * @param t
     *            throwable
     */
    public void throwApplicationMessageException(Throwable t) {
        throw createException(t);
    }
    
    /**
     * Throws ApplicationMessageException with message and key.
     * @param message
     *            exception message
     * @param messageKey
     *            message key
     */
    public void throwApplicationMessageException(String message,
            String[] messageKey) {
        throw createException(message, messageKey);
    }

    /**
     * Throws ApplicationMessageException.
     * @param message
     *            exception message
     * @param messageKey
     *            message key
     * @param t
     *            throwable
     */
    public void throwApplicationMessageException(String message,
            String[] messageKey, Throwable t) {
        throw createException(message, messageKey, t);
    }

    /**
     *  Asserts if the exception message and the value of the string variable
     * message are equal.
     * @param e
     *            ApplicationMessageException
     * @param message
     *            message to be compared with
     * @param cause
     *            Exception message
     */
    public void assertException(ApplicationMessageException e, String message,
            Throwable cause) {
        assertEquals(message, e.getMessage());
        assertEquals(cause.toString(), e.getExtendedCause().toString());
    }
}
