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

package com.mckesson.eig.utility.log;

import java.io.File;

import junit.framework.TestCase;

import org.apache.log4j.Hierarchy;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.RootLogger;

import com.mckesson.eig.utility.io.FileLoader;

/**
 * Series of tests to validate logging levels.
 */
public class TestLog extends TestCase {
    private static final Hierarchy HIERARCHY = new Hierarchy(new RootLogger(Level.FATAL));
    private final Logger _log = HIERARCHY.getRootLogger();

    private final String _trace = "com/mckesson/eig/utility/log/log4j/data/log4j.trace";
    private final String _debug = "com/mckesson/eig/utility/log/log4j/data/log4j.debug";
    private final String _info = "com/mckesson/eig/utility/log/log4j/data/log4j.info";
    private final String _warn = "com/mckesson/eig/utility/log/log4j/data/log4j.warn";
    private final String _error = "com/mckesson/eig/utility/log/log4j/data/log4j.error";
    private final String _fatal = "com/mckesson/eig/utility/log/log4j/data/log4j.fatal";
    private final String _off = "com/mckesson/eig/utility/log/log4j/data/log4j.off";
    private final String _levels = "com/mckesson/eig/utility/log/log4j/data/log4j.config";

    /**
     * Constructs the test case with the given name.
     *
     * @param name
     *            Test case name.
     */
    public TestLog(String name) {
        super(name);
    }

    /**
     * Tests the <code>TRACE</code> level.
     */
    public void testRootTrace() {
        performTest(_trace, true, true, true, true, true, true);
    }

    /**
     * Tests the <code>DEBUG</code> level.
     */
    public void testRootDebug() {
        performTest(_debug, false, true, true, true, true, true);
    }

    /**
     * Tests the <code>INFO</code> level.
     */
    public void testRootInfo() {
        performTest(_info, false, false, true, true, true, true);
    }

    /**
     * Tests the <code>WARN</code> level.
     */
    public void testRootWarn() {
        performTest(_warn, false, false, false, true, true, true);
    }

    /**
     * Tests the <code>ERROR</code> level.
     */
    public void testRootError() {
        performTest(_error, false, false, false, false, true, true);
    }

    /**
     * Tests the <code>FATAL</code> level.
     */
    public void testRootFatal() {
        performTest(_fatal, false, false, false, false, false, true);
    }

    /**
     * Tests the <code>OFF</code> level.
     */
    public void testRootOff() {
        performTest(_off, false, false, false, false, false, false);
    }

    /**
     * Reads the configuration file and validates the possible level for the
     * packages as described in the file.
     */
    public void testLevels() {
        configure(_levels);

        Logger log;

        log = HIERARCHY.getLogger("com.mckesson.eig.utility.log");
        assertEquals(false, isErrorEnabled(log));
        assertEquals(true, isFatalEnabled(log));

        log = HIERARCHY.getLogger("com.mckesson.eig.utility.log.log4j");
        assertEquals(false, isErrorEnabled(log));
        assertEquals(true, isFatalEnabled(log));

        log = HIERARCHY.getLogger("com.mckesson.eig.utility.context");
        assertEquals(false, isWarnEnabled(log));
        assertEquals(true, isErrorEnabled(log));

        log = HIERARCHY.getLogger("com.mckesson.eig.utility.configuration");
        assertEquals(false, log.isInfoEnabled());
        assertEquals(true, isWarnEnabled(log));

        log = HIERARCHY.getLogger("com.mckesson.eig.utility.exception");
        assertEquals(false, log.isDebugEnabled());
        assertEquals(true, log.isInfoEnabled());

        log = HIERARCHY.getLogger("com.mckesson.eig.utility.service");
        assertEquals(false, isFatalEnabled(log));

        log = HIERARCHY.getLogger("com.mckesson.eig.utility.collections");
        assertEquals(true, log.isDebugEnabled());

        log = HIERARCHY.getLogger("com.mckesson.eig.utility.crypto");
        assertEquals(true, isFatalEnabled(log));
    }

    /**
     * It configures the required file and validates the specified level for
     * this file.
     *
     * @param fileName
     *            Name of the file.
     * @param trace
     *            <code>boolean</code> value for validating <code>TRACE</code>
     *            level.
     * @param debug
     *            <code>boolean</code> value for validating <code>DEBUG</code>
     *            level.
     * @param info
     *            <code>boolean</code> value for validating <code>INFO</code>
     *            level.
     * @param warn
     *            <code>boolean</code> value for validating <code>WARN</code>
     *            level.
     * @param error
     *            <code>boolean</code> value for validating <code>ERROR</code>
     *            level.
     * @param fatal
     *            <code>boolean</code> value for validating <code>FATAL</code>
     *            level.
     */
    public void performTest(String fileName, boolean trace, boolean debug,
            boolean info, boolean warn, boolean error, boolean fatal) {

        configure(fileName);
        assertEquals(debug, _log.isDebugEnabled());
        assertEquals(info, isInfoEnabled(_log));
        assertEquals(warn, isWarnEnabled(_log));
        assertEquals(error, isErrorEnabled(_log));
        assertEquals(fatal, isFatalEnabled(_log));
    }

    /**
     * Determines if logging is enabled for the Info level.
     *
     * @param log
     *            this logger.
     * @return <code>true</code> if its enabled <code>false</code>
     *         otherwise.
     */
    private boolean isInfoEnabled(Logger log) {
        return log.isEnabledFor(Level.INFO);
    }

    /**
     * Determines if logging is enabled for the Warn level.
     *
     * @param log
     *            this logger.
     * @return <code>true</code> if its enabled <code>false</code>
     *         otherwise.
     */
    private boolean isWarnEnabled(Logger log) {
        return log.isEnabledFor(Level.WARN);
    }

    /**
     * Determines if logging is enabled for the Error level.
     *
     * @param log
     *            this logger.
     * @return <code>true</code> if its enabled <code>false</code>
     *         otherwise.
     */
    private boolean isErrorEnabled(Logger log) {
        return log.isEnabledFor(Level.ERROR);
    }

    /**
     * Determines if logging is enabled for the Fatal level.
     *
     * @param log
     *            this logger.
     * @return <code>true</code> if its enabled <code>false</code>
     *         otherwise.
     */
    private boolean isFatalEnabled(Logger log) {
        return log.isEnabledFor(Level.FATAL);
    }

    /**
     * Tests for configuration.
     */
    public void testReloadOfConfiguration() {

        configure(_debug);

        assertEquals(true, _log.isDebugEnabled());

        configure(_fatal);

        assertEquals(false, _log.isDebugEnabled());
    }

    /**
     * Reads configuration from a file. The existing configuration is not
     * cleared nor reset.
     *
     * @param fileName
     *            file which has to be configured.
     */
    public void configure(String fileName) {
        String absolute = getAbsolutePath(fileName);

        assertNotNull("could not find file:  " + fileName, absolute);

        PropertyConfigurator prop = new PropertyConfigurator();
        prop.doConfigure(absolute, HIERARCHY);
    }

    /**
     * This returns the pathname string for the specified file.
     *
     * @param fileName
     *            Name of an file whose path is required.
     * @return The string form of the required pathname.
     */
    public String getAbsolutePath(String fileName) {
        File file = FileLoader.getResourceAsFile(fileName);

        if (file == null) {
            return null;
        }

        return file.getPath();
    }
}
