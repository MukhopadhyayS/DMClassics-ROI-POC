package com.mckesson.eig.audit.dao;

import junit.framework.TestCase;

import com.mckesson.eig.audit.AuditException;

/**
 * Validates <code>AuditException</code>. Methods are provided for checking
 * exception with message and throwable cause.
 * 
 */
public class TestAuditException extends TestCase {

    /**
     * Message for testing.
     */
    private String _message = "This is our Test Message";

    /**
     * Instance holding an throwable <code>NullPointerException</code> with
     * cause.
     */
    private Throwable _throwable = new NullPointerException(_message);

    /**
     * Costructor.
     * 
     * @param name
     *            Test name.
     */
    public TestAuditException(String name) {
        super(name);
    }

    /**
     * Validates this default Exception.
     */
    public void testException() {
        try {
            throwAuditException();
            fail("Exception is thrown");
        } catch (AuditException e) {
            assertException(e, null, e);
        }
    }

    /**
     * Validates this Exception with a brief Message.
     */
    public void testExceptionString() {
        try {
            throwAuditException(_message);
            fail("Exception thrown inside testExceptionString() ");
        } catch (AuditException e) {
            assertException(e, _message, e);
        }
    }

    /**
     * Validates this Exception with <code>Throwable</code>.
     */
    public void testExceptionThrowable() {
        try {
            throwAuditException(_throwable);
            fail("Exception thrown inside testExceptionThrowable()");
        } catch (AuditException e) {
            assertException(e, _message, _throwable);
        }
    }

    /**
     * Validates this Exception with <code>Throwable</code> and </code>cause</code>.
     */
    public void testStringAndThrowable() {
        try {
            throwAuditException(_message, _throwable);
            fail("Exception thrown inside testStringAndThrowable()");
        } catch (AuditException e) {
            assertException(e, _message, _throwable);
        }
    }

    /**
     * Throws this Exception with <code>Throwable</code> and Message.
     * 
     * @param message
     *            Details
     * @param throwable
     *            cause
     */
    public void throwAuditException(String message, Throwable throwable) {
        throw createException(message, throwable);
    }

    /**
     * Returns this Exception.
     * 
     * @param message
     *            Detail
     * @param throwable
     *            cause
     * @return <code>AuditException</code>.
     */
    public AuditException createException(String message, Throwable throwable) {
        return new AuditException(message, throwable);
    }

    /**
     * Throws this Exception.
     * 
     * @param throwable
     *            cause
     */
    public void throwAuditException(Throwable throwable) {
        throw createException(throwable);
    }

    /**
     * Returns this Exception.
     * 
     * @param throwable
     *            cause.
     * @return AuditException.
     */
    public AuditException createException(Throwable throwable) {
        return new AuditException(throwable);
    }

    /**
     * Throws this Exception with a brief message.
     * 
     * @param message
     *            Detail.
     */
    public void throwAuditException(String message) {
        throw createException(message);
    }

    /**
     * Returns this Exception with a brief message.
     * 
     * @param message
     * @return AuditException.
     */
    public AuditException createException(String message) {
        throw new AuditException(message);
    }

    /**
     * Throws this default Exception.
     */
    public void throwAuditException() {
        throw createException();
    }

    /**
     * Returns this Exception.
     * 
     * @return AuditException.
     */
    public AuditException createException() {
        return new AuditException();
    }

    /**
     * Asserts Whether the exception throws the right cause and message.
     * 
     * @param e
     *            AuditException Detail.
     * @param message
     *            Message
     * @param cause
     *            Cause of this Throwable.
     */
    public void assertException(AuditException e, String message,
            Throwable cause) {
        assertEquals(message, e.getMessage());
        assertEquals(cause.toString(), e.getExtendedCause().toString());
    }
}
