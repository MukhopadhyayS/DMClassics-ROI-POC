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
import com.mckesson.eig.utility.testing.UnitTest;

//import com.mckesson.eig.utility.log.LogFactory;

public class TestApplicationException extends UnitTest {
    private static final int TEST_VALUE = 30;
    private String _testMessage = "This is our test Message";
    private String _testNpeMessage = "This is our NPE Message";
    private Throwable _npe = new NullPointerException(_testNpeMessage);
    private String _testErrorCode = "Error0999";
    private String _testExtendedCode = "Extended0999";
    public TestApplicationException(String name) {
        super(name);
    }

    public void testExceptionUsingStringAndErrorCode() {
        try {

            throwApplicationException(this._testMessage, this._testErrorCode);
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            assertException(e, _testMessage, e);
            assertEquals(e.getErrorCode(), _testErrorCode);
        }
    }

    private void throwApplicationException(String message, String errorCode) {
        throw createErrorCodeException(message, errorCode);
    }

    public ApplicationException createErrorCodeException(String message, String errorCode) {
        return new ApplicationException(message, errorCode);
    }

    public void testExceptionUsingStringAndErrorCodeAndExtendedCode() {
        try {

            throwApplicationException(this._testMessage, this._testErrorCode,
                    this._testExtendedCode);
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            assertException(e, _testMessage, e);
            assertEquals(_testErrorCode, e.getErrorCode());
            assertEquals(this._testExtendedCode, e.getExtendedCode());
        }
    }

    private void throwApplicationException(String message, String errorCode, String extendedCode) {
        throw createErrorCodeException(message, errorCode, extendedCode);
    }

    public ApplicationException createErrorCodeException(String message, String errorCode,
            String extendedCode) {
        return new ApplicationException(message, errorCode, extendedCode);
    }

    public void testExceptionUsingStringAndThowableAndErrorCode() {
        try {
            throwApplicationException(this._testMessage, this._npe, this._testErrorCode);
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            assertException(e, _testMessage, _npe);
            assertEquals(e.getErrorCode(), _testErrorCode);
            assertEquals(e.getExtendedCode(), null);
        }

    }

    private void throwApplicationException(String message, Throwable npe2, String errorCode) {
        throw createExceptionWithErrorCode(message, npe2, errorCode);
    }

    private ApplicationException createExceptionWithErrorCode(String message, Throwable npe2,
            String errorCode) {
        return new ApplicationException(message, npe2, errorCode);
    }

    public void testExceptionUsingStringAndThowableAndErrorCodeAndExtendedCode() {
        try {
            throwApplicationException(this._testMessage, this._npe, this._testErrorCode,
                    this._testExtendedCode);
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            assertException(e, _testMessage, _npe);
            assertEquals(e.getErrorCode(), _testErrorCode);
            assertEquals(e.getExtendedCode(), _testExtendedCode);
        }
    }

    private void throwApplicationException(String message, Throwable npe2, String errorCode,
            String extendedCode) {
        throw createExceptionWithErrorCode(message, npe2, errorCode, extendedCode);
    }

    public ApplicationException createExceptionWithErrorCode(String message, Throwable npe2,
            String errorCode, String extendedCode) {
        return new ApplicationException(message, npe2, errorCode, extendedCode);
    }

    public void testException() {
        try {
            throwApplicationException();
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            assertException(e, null, e);
        }
    }

    public void testExceptionUsingString() {
        try {
            throwApplicationException(_testMessage);
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            assertException(e, _testMessage, e);
        }

    }

    public void testExceptionUsingThrowable() {
        try {
            throwApplicationException(_npe);
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            assertException(e, _testNpeMessage, _npe);
        }
    }

    public void testExceptionUsingStringAndThrowable() {
        try {
            throwApplicationException(_testMessage, _npe);
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            assertException(e, _testMessage, _npe);
        }
    }

    public void testExceptionUsingStringAndNestedThrowable() {
        Throwable t = new Exception(_npe);
        try {
            throwNestedApplicationException(t);
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            assertException(e, _testMessage, new Exception(_npe));
        }
    }

    public void testNestedException() {
        try {
            throwNestedApplicationException(_npe);
            assertTrue("Exception should have been thrown", false);
        } catch (ApplicationException e) {
            assertException(e, _testMessage, _npe);
        }
    }

    public void testExceptionWithErrorCode() {
        try {
            throwApplicationException(_npe, "T12345");
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            assertException(e, _testNpeMessage, _npe);
            assertEquals(e.getErrorCode(), "T12345");
        }
    }

    public void testPrintStackTraceWithPrintWriter() {

        try {
            throwApplicationException(_testMessage);
        } catch (ApplicationException e) {
            StringWriter expected = new StringWriter(TEST_VALUE);
            e.printStackTrace(new PrintWriter(expected));
            assertNotNull("StringWriter should exist", expected);
            String string = expected.toString();
            assertFalse("Test Message wasn't found", string.indexOf(_testMessage) == -1);
        }
    }

    public void testNestedPrintStackTraceWithPrintWriter() {
        try {
            throwApplicationException(_npe);
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            StringWriter expected = new StringWriter();
            StringWriter received = new StringWriter();

            PrintWriter pw = new PrintWriter(expected);

            pw.println(e.toString());
            _npe.printStackTrace(pw);

            e.printStackTrace(new PrintWriter(received));

            assertEquals(expected.toString(), received.toString());
        }
    }

    public void testPrintStackTraceWithPrintStream() {

        try {
            throwApplicationException(_testMessage);
        } catch (ApplicationException e) {
            OutputStream expected = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(expected));
            assertNotNull("StringWriter should exist", expected);
            String string = expected.toString();
            assertFalse("Test Message wasn't found", string.indexOf(_testMessage) == -1);
        }
    }

    public void testNestedPrintStackTraceWithPrintStream() {
        try {
            throwApplicationException(_npe);
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
            OutputStream expected = new ByteArrayOutputStream();
            OutputStream received = new ByteArrayOutputStream();

            PrintStream ps = new PrintStream(expected);

            ps.println(e.toString());
            _npe.printStackTrace(ps);

            e.printStackTrace(new PrintStream(received));

            assertEquals(expected.toString(), received.toString());
        }
    }

    public void testPrintStackTrace() {

        try {
            throwApplicationException(_testMessage);
        } catch (ApplicationException e) {
            OutputStream expected = new ByteArrayOutputStream();
            System.setErr(new PrintStream(expected));
            e.printStackTrace();
            assertNotNull("StringWriter should exist", expected);
            String string = expected.toString();
            assertFalse("Test Message wasn't found", string.indexOf(_testMessage) == -1);
        }
    }

    public void testNestedPrintStackTrace() {
        try {
            throwApplicationException(_npe);
            fail("Exception should have been thrown");
        } catch (ApplicationException e) {
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

    public void testFillInStackTrace() {
        // just calling for now...
        Throwable one = new ApplicationException().fillInStackTrace();
        new ApplicationException(one).fillInAllStackTraces();
    }

    public void testSerialization() {
        ApplicationException one = createException(_npe);
        ApplicationException two = (ApplicationException) ObjectVerifier.serialize(one);
        assertEquals(one.toString(), two.toString());
        assertEquals(one.getExtendedCause().toString(), two.getExtendedCause().toString());
        two.log();
    }

    public void testSetErrorCode() { 
        ApplicationException ae = createException(_npe);
        assertNotNull(ae);
        ae.setErrorCode(_testErrorCode);
        assertEquals(ae.getErrorCode(), _testErrorCode);
    } 
    
    public void testSetExtendedCode() { 
        ApplicationException ae = createException(_npe);
        assertNotNull(ae);
        ae.setExtendedCode(_testExtendedCode);
        assertEquals(ae.getExtendedCode(), _testExtendedCode);
    } 
    
    public void testLog() { 
        ApplicationException ae = createException(_npe);
        String logMessage = "Testing the logger";
        assertTrue(ae.log(null, logMessage) instanceof ApplicationException);
        assertTrue(ae.wasLogged());
     }
    
    public void testToStringOnMessage() {
        ApplicationException e = new ApplicationException();
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

    public ApplicationException createException(String message) {
        return new ApplicationException(message);
    }

    public ApplicationException createException(Throwable t) {
        return new ApplicationException(t);
    }

    public ApplicationException createException(String message, Throwable t) {
        return new ApplicationException(message, t);
    }

    public ApplicationException createException(Throwable t, String errorCode) {
        return new ApplicationException(t, errorCode);
    }

    public ApplicationException createException() {
        return new ApplicationException();
    }

    public void throwApplicationException() {
        throw createException();
    }

    public void throwApplicationException(String message) {
        throw createException(message);
    }

    public void throwApplicationException(Throwable t) {
        throw createException(t);
    }

    public void throwApplicationException(String message, Throwable t) {
        throw createException(message, t);
    }

    public void throwApplicationException(Throwable t, String errorCode) {
        throw createException(t, errorCode);
    }

    public void throwNestedApplicationException(Throwable t) {
        throw createException(createException(_testMessage, t));
    }

    public void assertException(ApplicationException e, String message, Throwable cause) {
        assertEquals(message, e.getMessage());
        assertEquals(cause.toString(), e.getExtendedCause().toString());
    }
}
