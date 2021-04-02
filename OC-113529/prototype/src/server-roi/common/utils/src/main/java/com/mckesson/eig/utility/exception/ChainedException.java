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

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class adds the functionality of chaining exceptions to wrap exceptions
 * from the Java Runtime. The nice thing about extending RuntimeException vs.
 * checked Exceptions is it doesn't force you to add the try...catch code at any
 * particular level. You can catch the exception in some high level exception
 * handler or you can catch it when it happens for graceful exits. Once JDK1.4
 * is accepted as the minimum, we will probably deprecate the internals of this
 * class.
 * <p>
 * Implementation note: This base class was separated from ApplicationException
 * in order to reuse the chaining functionality without any logging
 * dependencies; specifically for CI client libraries where we don't want to
 * require log4j in the classpath.
 * </p>
 *
 * @see ChainableException
 * @see RuntimeException
 */
public class ChainedException extends RuntimeException implements ChainableException {

	private static final long serialVersionUID = 1L;

	/**
     * Default constructor. There is no exception to cascade and no additional
     * message. In this case the class is just like a RuntimeException
     *
     * @see RuntimeException()
     */
    public ChainedException() {
    }

    /**
     * Construct with a message only. There is no exception to cascade. In this
     * case this class will act like any other RuntimeException.
     *
     * @param message
     *            Exception message
     *
     * @see RuntimeException(String)
     */
    public ChainedException(String message) {
        super(message);
    }

    /**
     * Construct with a String and a Throwable. Since there is a chained
     * exception the #getExtendedCause() for this exception will return the
     * cascaded exceptions #getExtendedCause() if it is a ChainableException or
     * the actual exception.
     *
     * @param message
     *            Message to describe the exception
     * @param cause
     *            the exception we are cascading
     */
    public ChainedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns the detail message string of this throwable instance (which may
     * not be <code>null</code>).
     *
     * @param cause
     *            root cause for the <code>Exception</code>.
     */
    public ChainedException(Throwable cause) {
        super(cause != null ? cause.getMessage() : null, cause);
    }

    public Throwable getExtendedCause() {
        Throwable cause = getCause();
		if (cause == null) {
            return this;
        }
        if (cause instanceof ChainableException) {
            return ((ChainableException) cause).getExtendedCause();
        }
        return cause;
    }

    public Throwable getNestedCause() {
        return getCause();
    }

    public List<Throwable> getAllNestedCauses() {
        List<Throwable>  result = new ArrayList<Throwable>();
        Throwable t = getCause();
        while (t != null) {
            result.add(t);
            if (t instanceof ChainableException) {
                t = ((ChainableException) t).getNestedCause();
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Fills in the execution stack trace. This method records within this
     * <code>Throwable</code> object information about the current state of
     * the stack frames for the current thread.
     *
     * @return a reference to this <code>Throwable</code> instance.
     */
    public Throwable fillInAllStackTraces() {
        final Throwable result = super.fillInStackTrace();
        Throwable t = getCause();
        while (t != null) {
            t.fillInStackTrace();
            if (t instanceof ChainableException) {
                t = ((ChainableException) t).getNestedCause();
            } else {
                break;
            }
        }
        return result;
    }

    @Override
	public void printStackTrace() {
        Throwable cause = getCause();
        if (cause == null) {
            super.printStackTrace();
        } else {
            // Make sure we don't lose our own error message and exception
            // type in the output.
            System.err.println(toString());
            // Print the chained exception and it's stack trace. Generally
            // this exception will share a similar stack with the chained.
            cause.printStackTrace();
        }
    }
    @Override
	public void printStackTrace(PrintStream ps) {
        Throwable cause = getCause();
        if (cause == null) {
            super.printStackTrace(ps);
        } else {
            // Make sure we don't lose our own error message and exception
            // type in the output.
            ps.println(toString());
            // Print the chained exception and it's stack trace. Generally
            // this exception will share a similar stack with the chained.
            cause.printStackTrace(ps);
        }
    }
    @Override
	public void printStackTrace(PrintWriter pw) {
        Throwable cause = getCause();
        if (cause == null) {
            super.printStackTrace(pw);
        } else {
            // Make sure we don't lose our own error message and exception
            // type in the output.
            pw.println(toString());
            // Print the chained exception and it's stack trace. Generally
            // this exception will share a similar stack with the chained.
            cause.printStackTrace(pw);
        }
    }
}
