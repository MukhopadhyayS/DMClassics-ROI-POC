/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.exception;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.ObjectVerifier;

/**
 * Test case to test the class ChainedException.
 */
public class TestChainedException extends TestCase {

    /**
     * Test value.
     */
    private static final int TEST_VALUE = 30;

    /**
     * Test Message.
     */
    private final String _testMessage = "This is our test Message";

    /**
     * NullPointerException message.
     */
    private final String _testNpeMessage = "This is our NPE Message";

    /**
     * New NullPointerException.
     */
    private final Throwable _npe = new NullPointerException(_testNpeMessage);

    /**
     * Constructs the test case with the given name.
     *
     * @param name
     *            Name of the test case
     */
    public TestChainedException(String name) {
        super(name);
    }

    /**
     * Tests the ChainedException.
     */
    public void testException() {
        try {
            throwChainedException();
            fail("Exception should have been thrown");
        } catch (ChainedException e) {
            assertException(e, null, e);
        }
    }

    /**
     * Tests the ChainedException using string.
     */
    public void testExceptionUsingString() {
        try {
            throwChainedException(_testMessage);
            fail("Exception should have been thrown");
        } catch (ChainedException e) {
            assertException(e, _testMessage, e);
        }
    }

    /**
     * Tests the ChainedException with throwable.
     */
    public void testExceptionUsingThrowable() {
        try {
            throwChainedException(_npe);
            fail("Exception should have been thrown");
        } catch (ChainedException e) {
            assertException(e, _testNpeMessage, _npe);
        }
    }

    /**
     * Tests the ChainedException with string ,throwable and key.
     */
    public void testExceptionUsingStringAndThrowable() {
        try {
            throwChainedException(_testMessage, _npe);
            fail("Exception should have been thrown");
        } catch (ChainedException e) {
            assertException(e, _testMessage, _npe);
        }
    }

    public void testGetExtendedCause() {
        ChainedException chainException = new ChainedException("error", _npe);
        assertEquals(chainException.getExtendedCause(), _npe);
    }

    public void testGetNestedCause() {
        ChainedException chainException = new ChainedException("error", _npe);
        assertEquals(chainException.getNestedCause(), _npe);
    }

    public void testGetAllNestedCauses() {
        ChainedException chainException = new ChainedException("error", _npe);
        List<Throwable> l = chainException.getAllNestedCauses();
        assertNotNull(l);
        assertEquals(l.size(), 1);
    }


    /**
     * Tests PrintStackTraceWithPrintWriter.
     */
    public void testPrintStackTraceWithPrintWriter() {
        try {
            throwChainedException(_testMessage);
        } catch (ChainedException e) {
            StringWriter expected = new StringWriter(TEST_VALUE);
            e.printStackTrace(new PrintWriter(expected));
            assertNotNull("StringWriter should exist", expected);
            String string = expected.toString();
            assertFalse("Test Message wasn't found", string.indexOf(_testMessage) == -1);
        }
    }

    /**
     * Tests NestedPrintStackTraceWithPrintWriter.
     */
    public void testNestedPrintStackTraceWithPrintWriter() {
        try {
            throwChainedException(_npe);
            fail("Exception should have been thrown");
        } catch (ChainedException e) {
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
            throwChainedException(_testMessage);
        } catch (ChainedException e) {
            OutputStream expected = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(expected));
            assertNotNull("StringWriter should exist", expected);
            String string = expected.toString();
            assertFalse("Test Message wasn't found", string.indexOf(_testMessage) == -1);
        }
    }

    /**
     * Tests NestedPrintStackTraceWithPrintStream.
     */
    public void testNestedPrintStackTraceWithPrintStream() {
        try {
            throwChainedException(_npe);
            fail("Exception should have been thrown");
        } catch (ChainedException e) {
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
            throwChainedException(_testMessage);
        } catch (ChainedException e) {
            OutputStream expected = new ByteArrayOutputStream();
            System.setErr(new PrintStream(expected));
            e.printStackTrace();
            assertNotNull("StringWriter should exist", expected);
            String string = expected.toString();
            assertFalse("Test Message wasn't found", string.indexOf(_testMessage) == -1);
        }
    }

    /**
     * Tests NestedPrintStackTrace.
     */
    public void testNestedPrintStackTrace() {
        try {
            throwChainedException(_npe);
            fail("Exception should have been thrown");
        } catch (ChainedException e) {
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
        Throwable one = new ChainedException().fillInStackTrace();
        new ChainedException(one).fillInAllStackTraces();
    }

    /**
     * Tests Serialization.
     */
    public void testSerialization() {
        ChainedException one = createException(_npe);
        ChainedException two = (ChainedException) ObjectVerifier.serialize(one);
        assertEquals(one.toString(), two.toString());
        assertEquals(one.getExtendedCause().toString(), two.getExtendedCause().toString());
     }

    /**
     * Returns an ChainedException.
     * @param message
     *            Exception message
     * @return ChainedException
     */
    public ChainedException createException(String message) {
        return new ChainedException(message);
    }

    /**
     * Returns an ChainedException.
     * @param t
     *            Throwable exception
     * @return ChainedException
     */
    public ChainedException createException(Throwable t) {
        return new ChainedException(t);
    }

    /**
     * Returns an ChainedException.
     * @return ChainedException
     */
    public ChainedException createException() {
        return new ChainedException();
    }

    /**
     * Returns an ChainedException.
     * @param message
     *            Exception message
     * @param t
     *            Throwable Exception
     * @return ChainedException
     */
    public ChainedException createException(String message,
            Throwable t) {
        return new ChainedException(message, t);
    }

    /**
     * Calls method createException().
     */
    public void throwChainedException() {
        throw createException();
    }

    /**
     * Throws ChainedException with message.
     * @param message
     *            Exception message. Calls method createException()with message
     */
    public void throwChainedException(String message) {
        throw createException(message);
    }

    /**
     * Throws ChainedException with message.
     * @param t
     *            throwable
     */
    public void throwChainedException(Throwable t) {
        throw createException(t);
    }

    /**
     * Throws ChainedException.
     * @param message
     *            exception message
     * @param t
     *            throwable
     */
    public void throwChainedException(String message, Throwable t) {
        throw createException(message, t);
    }

    /**
     *  Asserts if the exception message and the value of the string variable
     * message are equal.
     * @param e
     *            ChainedException
     * @param message
     *            message to be compared with
     * @param cause
     *            Exception message
     */
    public void assertException(ChainedException e, String message, Throwable cause) {
        assertEquals(message, e.getMessage());
        assertEquals(cause.toString(), e.getExtendedCause().toString());
    }
}
