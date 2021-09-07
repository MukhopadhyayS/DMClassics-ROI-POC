/**
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
package com.mckesson.eig.utility.log;

import java.io.Serializable;

/**
 * This class is an interface for our LogXWrapper(s) to implement so that we
 * could theoretically swap logging facilities at some point.
 */
public interface Log extends Serializable {

     /**
     * Logs a message with a priority of debug.
     * 
     * @param message
     *            The message to be logged.
     */
    void debug(Object message);

    /**
     * This method logs the <code>Throwable</code> with a priority of trace.
     * The message output is a combination of the message passed in and the
     * stack trace of the <code>Throwable</code> object.
     * 
     * @param exception
     *            The exception we will log
     * @param message
     *            Additional information for clarity in the log
     * 
     */
    void debug(Object message, Throwable exception);

    /**
     * Logs a message with a priority of info.
     * 
     * @param message
     *            The message to be logged.
     */
    void info(Object message);

    /**
     * This method logs the <code>Throwable</code> with a priority of info.
     * The message output is a combination of the message passed in and the
     * stack trace of the <code>Throwable</code> object.
     * 
     * @param exception
     *            The exception we will log
     * @param message
     *            Additional information for clarity in the log
     * 
     */
    void info(Object message, Throwable exception);

    /**
     * Logs the message with a priority level of warn.
     * 
     * @param message
     *            The message to log
     */
    void warn(Object message);

    /**
     * This method logs the <code>Throwable</code> with a priority of warn.
     * The message output is a combination of the message passed in and the
     * stack trace of the <code>Throwable</code> object.
     * 
     * @param exception
     *            The exception we will log
     * @param message
     *            Additional information for clarity in the log
     * 
     */
    void warn(Object message, Throwable exception);

    /**
     * Logs a message with a priority of error.
     * 
     * @param message
     *            The message to be logged.
     */
    void error(Object message);

    /**
     * This method logs the <code>Throwable</code> with a priority of error.
     * The message output is a combination of the message passed in and the
     * stack trace of the <code>Throwable</code> object.
     * 
     * @param exception
     *            The exception we will log
     * @param message
     *            Additional information for clarity in the log
     * 
     */
    void error(Object message, Throwable exception);

    /**
     * Logs the message with a priority level of fatal.
     * 
     * @param message
     *            The message to log
     */
    void fatal(Object message);

    /**
     * This method logs the <code>Throwable</code> with a priority of fatal.
     * The message output is a combination of the message passed in and the
     * stack trace of the <code>Throwable</code> object.
     * 
     * @param exception
     *            The exception we will log
     * @param message
     *            Additional information for clarity in the log
     */
    void fatal(Object message, Throwable exception);

    /**
     * Check whether this logger is enabled for the debug priority.<br>
     * See also {@link #isInfoEnabled}<br>
     * 
     * @return boolean - <code>true</code> if this category is enabled for
     *         priority debug, <code>false</code> otherwise.
     */
    boolean isDebugEnabled();

     /**
     * Check whether this logger is enabled for the info priority.<br>
     * See also {@link #isDebugEnabled}.<br>
     * 
     * @return boolean - <code>true</code> if this category is enabled for
     *         priority info, <code>false</code> otherwise.
     */
    boolean isInfoEnabled();

    /**
     * Check whether this logger is enabled for the WARN priority.<br>
     * This is here for unit testing purposes only and that is why it is a
     * package level function
     * 
     * @return boolean - <code>true</code> if this logger is enabled for
     *         priority WARN. <code>false</code> otherwise.
     * 
     */
    boolean isWarnEnabled();

    /**
     * Check whether this logger is enabled for the ERROR priority.<br>
     * This is here for unit testing purposes only and that is why it is a
     * package level function
     * 
     * @return boolean - <code>true</code> if this logger is enabled for
     *         priority ERROR. <code>false</code> otherwise.
     * 
     */
    boolean isErrorEnabled();

    /**
     * Check whether this logger is enabled for a given priority
     * 
     * @return boolean - <code>true</code> if this logger is enabled for
     *         priority ERROR. <code>false</code> otherwise.
     * 
     */
    boolean isFatalEnabled();
}
