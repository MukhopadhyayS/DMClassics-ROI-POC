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

package com.mckesson.eig.utility.log.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import com.mckesson.dm.core.common.util.sanitize.codecs.HtmlCustomCodec;


import com.mckesson.eig.utility.log.Log;

public class Log4JWrapper implements Log {

	private static final long serialVersionUID = 2538822683600604799L;
	private static HtmlCustomCodec htmlCustomCodec = new HtmlCustomCodec();

	private final Logger _log;

    public Log4JWrapper(String loggerName) {
        this(getLoggerRepository().getLogger(loggerName));
    }

    public Log4JWrapper(Class<?> type) {
        this(getLoggerRepository().getLogger(type.getName()));
    }

    public Log4JWrapper(Logger logger) {
        _log = logger;
    }

    /**
     * Logs a message with a priority of debug.
     *
     * @param message
     *            The message to be logged.
     */
    public void debug(Object message) {
        _log.debug(encodeMessage(message));
    }

    /**
     * This method logs the <code>Throwable</code> with a priority of debug.
     * The message output is a combination of the message passed in and the
     * stack trace of the <code>Throwable</code> object.
     *
     * @param exception
     *            The exception we will log
     * @param message
     *            Additional information for clarity in the log
     *
     */
    public void debug(Object message, Throwable exception) {
        _log.debug(encodeMessage(message), exception);
    }
	/**
     *encodes the messages before loging.
     *
     * @param message
     *            The message to be logged.
	 * @return String
	 *            Encoded message
     */
    public String encodeMessage(Object message){
    	String replace = message.toString().replace( '\n', '_' ).replace( '\r', '_' );
    	replace = htmlCustomCodec.encode(replace);
    	return replace;
    }

    /**
     * Logs a message with a priority of info.
     *
     * @param message
     *            The message to be logged.
     */
    public void info(Object message) {
        _log.info(encodeMessage(message));
    }

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
    public void info(Object message, Throwable exception) {
        _log.info(encodeMessage(message), exception);
    }

    /**
     * Logs the message with a priority level of warn.
     *
     * @param message
     *            The message to log
     */
    public void warn(Object message) {
        _log.warn(encodeMessage(message));
    }

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
    public void warn(Object message, Throwable exception) {
        _log.warn(encodeMessage(message), exception);
    }

    /**
     * Logs a message with a priority of error.
     *
     * @param message
     *            The message to be logged.
     */
    public void error(Object message) {
        _log.error(encodeMessage(message));
    }

    /**
     * This method logs the <code>Throwable</code> with a priority of error.
     * The message output is a combination of the message passed in and the
     * stack trace of the <code>Throwable</code> object.
     *
     * @param t
     *            The exception we will log
     * @param message
     *            Additional information for clarity in the log
     *
     */
    public void error(Object message, Throwable t) {
        _log.error(encodeMessage(message), t);
    }

    /**
     * Logs the message with a priority level of fatal.
     *
     * @param message
     *            The message to log
     */
    public void fatal(Object message) {
        _log.fatal(message);
    }

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
    public void fatal(Object message, Throwable exception) {
        _log.fatal(message, exception);
    }

    /**
     * Check whether this logger is enabled for the debug priority.<br>
     * See also {@link #isInfoEnabled}<br>
     *
     * @return boolean - <code>true</code> if this category is enabled for
     *         priority debug, <code>false</code> otherwise.
     */
    public boolean isDebugEnabled() {
        return _log.isDebugEnabled();
    }

    /**
     * Check whether this logger is enabled for the info priority.<br>
     * See also {@link #isDebugEnabled}.<br>
     *
     * @return boolean - <code>true</code> if this category is enabled for
     *         priority info, <code>false</code> otherwise.
     */
    public boolean isInfoEnabled() {
        return _log.isInfoEnabled();
    }

    /**
     * Check whether this logger is enabled for the WARN priority.<br>
     * This is here for unit testing purposes only and that is why it is a
     * package level function
     *
     * @return boolean - <code>true</code> if this logger is enabled for
     *         priority WARN. <code>false</code> otherwise.
     */
    public boolean isWarnEnabled() {
        return isEnabledFor(Level.WARN);
    }

    /**
     * Check whether this logger is enabled for the ERROR priority.<br>
     * This is here for unit testing purposes only and that is why it is a
     * package level function
     *
     * @return boolean - <code>true</code> if this logger is enabled for
     *         priority ERROR. <code>false</code> otherwise.
     */
    public boolean isErrorEnabled() {
        return isEnabledFor(Level.ERROR);
    }

    /**
     * Check whether this logger is enabled for the FATAL priority.<br>
     * This is here for unit testing purposes only and that is why it is a
     * package level function
     *
     * @return boolean - <code>true</code> if this logger is enabled for
     *         priority FATAL. <code>false</code> otherwise.
     */
    public boolean isFatalEnabled() {
        return isEnabledFor(Level.FATAL);
    }

    /**
     * Check whether this logger is enabled for a given priority
     *
     * @return boolean - <code>true</code> if this logger is enabled for
     *         priority ERROR. <code>false</code> otherwise.
     */
    boolean isEnabledFor(Level level) {
        return _log.isEnabledFor(level);
    }

    /**
     * This is a package access level for use in unit testing. All loggers are
     * stored statically. If two loggers are the same in Log4J then there
     * classes will be equal.
     */
    boolean isEqual(Logger logger) {
        return logger == _log;
    }

    private static LoggerRepository getLoggerRepository() {
        return LogManager.getLoggerRepository();
    }
}
