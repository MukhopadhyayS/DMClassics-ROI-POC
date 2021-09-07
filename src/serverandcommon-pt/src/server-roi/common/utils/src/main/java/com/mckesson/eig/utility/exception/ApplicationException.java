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

import java.io.IOException;
import java.io.ObjectInputStream;

import com.mckesson.eig.utility.log.LoggedRuntimeException;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * Base class for our applications exceptions.
 *
 * @see ChainableException
 * @see ChainedException
 * @see RuntimeException
 */
public class ApplicationException extends ChainedException
        implements LoggedRuntimeException {

	private static final long serialVersionUID = -4172990034455170158L;

	private transient OCLogger _log = new OCLogger(getClass());

    private transient boolean _logged = false;

    /**
     * Holds a meaningful error code as a string.  These codes are not informative
     * in themselves, only as lookup keys.
     */
    private String _errorCode = null;

    private String _extendedCode = null;

    /**
     * Default constructor. There is no exception to cascade and no additional
     * message. In this case the class is just like a RuntimeException
     */
    public ApplicationException() {
    }

    /**
     * Construct with a message only. There is no exception to cascade. In this
     * case this class will act like any other RuntimeException.
     *
     * @param message
     *            Exception message
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Construct with a message and an errorCode. There is no exception to cascade. In this
     * case this class will act like any other RuntimeException.
     *
     * @param message
     *            Exception message
     */
    public ApplicationException(String message, String errorCode) {
        super(message);
        _errorCode = errorCode;
    }

    public ApplicationException(String message, String errorCode, String extendedCode) {
        super(message);
        _errorCode = errorCode;
        _extendedCode = extendedCode;
    }

    /**
     * Construct with a cause.
     *
     * @param cause
     *            The exception we are cascading.
     */
    public ApplicationException(Throwable cause) {
        super(cause);

        // Log automatically as a chained throwable is usually unexpected or
        // a system error.
        log();
    }

    /**
     * Construct with a cause and an errorcode.
     *
     * @param cause
     *            The exception we are cascading.
     */
    public ApplicationException(Throwable cause, String errorCode) {
        super(cause);
        _errorCode = errorCode;

        // Log automatically as a chained throwable is usually unexpected or
        // a system error.
        log();
    }

    /**
     * Construct with a message and a cause.
     *
     * @param message
     *            Message to describe the exception
     * @param cause
     *            the exception we are cascading
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);

        // Log automatically as a chained throwable is usually unexpected or
        // a system error.
        log();
    }

    /**
     * Construct with a message, a cause, and an errorcode.
     *
     * @param message
     *            Message to describe the exception
     * @param cause
     *            the exception we are cascading
     */
    public ApplicationException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        _errorCode = errorCode;

        // Log automatically as a chained throwable is usually unexpected or
        // a system error.
        log();
    }

    public ApplicationException(String message, Throwable cause, String errorCode,
            String extCode) {
        super(message, cause);
        _errorCode = errorCode;
        _extendedCode = extCode;

        // Log automatically as a chained throwable is usually unexpected or
        // a system error.
        log();
    }

    /**
     * Fills in the execution stack trace. This method records within this
     * <code>Throwable</code> object information about the current state of
     * the stack frames for the current thread.
     *
     * @return a reference to this <code>Throwable</code> instance.
     */
    @Override
	public Throwable fillInAllStackTraces() {
        _logged = false;
        return super.fillInAllStackTraces();
    }

    /**
     * Returns <code>true</code> if its logged.
     *
     * @return <code>true</code> if its logged <code>false</code> otherwise.
     */
    public boolean wasLogged() {
        return _logged;
    }

    /**
     * Returns the log when an
     */
    public RuntimeException log() {
        log("");
        return this;
    }

    public RuntimeException log(Object message) {
        return doLog(_log, message);
    }

    public RuntimeException log(OCLogger logImpl, Object message) {
    	
        OCLogger logToUse = ((logImpl == null) ? _log : logImpl);
        return doLog(logToUse, message);
    }

    public String getErrorCode() {
        return _errorCode;
    }

    public void setErrorCode(String errCode) {
        _errorCode = errCode;
    }

    public String getExtendedCode() {
        return _extendedCode;
    }

    public void setExtendedCode(String extCode) {
        _extendedCode = extCode;
    }

    /**
     * ** This method logs the <code>Throwable</code> with a priority of
     * error. The message output is a combination of the message passed in and
     * the stack trace of the <code>Throwable</code> object.
     *
     * @param logImpl
     *            instance of <code>Log</code>.
     * @param message
     *            Additional information for clarity in the log.
     * @return
     */

    private RuntimeException doLog(OCLogger logImpl, Object message) {
        // Since we log automatically in some cases, try to avoid duplicate
        // log entries.
        if (!_logged) {
            logImpl.error("Exception occurred in doLog ",message);
            _logged = true;
        } else if (message != null && toString(message).trim().length() > 0) {
            // Might have a new message that we don't want to lose.
            // Already logged this stack trace, so just log the new message.
            logImpl.error("Exception occurred in doLog ",message);
        }
        return this;
    }

    protected String toString(Object message) {
        String s = ((message == null) ? "" : message.toString());
        return (s == null) ? "" : s;
    }

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        _log = new OCLogger(getClass());
        // We may be in a new VM where we have not been logged.
        _logged = false;
    }
}
