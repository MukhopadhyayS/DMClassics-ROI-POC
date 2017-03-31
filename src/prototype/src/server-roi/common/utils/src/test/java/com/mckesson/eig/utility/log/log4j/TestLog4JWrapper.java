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

import org.apache.log4j.Logger;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.testing.UnitTest;

public class TestLog4JWrapper extends UnitTest {

    public TestLog4JWrapper(String name) {
        super(name);
    }

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        String fileName = "com/mckesson/eig/utility/log/log4j/data/log4j.config";
        assertNotNull(LogFactory.getLogInitializer().getLoggerRepository());
        LogFactory.getLogInitializer().setByProperties(fileName);
    }

    public void testLog4JFatalUsingName() {
        Log log = areLoggersEqual(getClass().getName());

        assertFalse(log.isErrorEnabled());
        assertTrue(log.isFatalEnabled());
    }

    public void testLog4JFatalUsingClass() {
        Log log = areLoggersEqual(getClass());

        assertEquals(false, log.isErrorEnabled());
        assertEquals(true, log.isFatalEnabled());
    }

    public void testLog4JErrorUsingName() {
        String category = "com.mckesson.eig.utility.context";

        Log log = areLoggersEqual(category);

        assertEquals(false, log.isWarnEnabled());
        assertEquals(true, log.isErrorEnabled());
    }

    public void testLog4JErrorUsingClass() {
        String category = "com.mckesson.eig.utility.context";

        Log log = areLoggersEqual(category);

        assertEquals(false, log.isWarnEnabled());
        assertEquals(true, log.isErrorEnabled());
    }

    public void testLog4JWarnUsingName() {
        String category = "com.mckesson.eig.utility.configuration";

        Log log = areLoggersEqual(category);

        assertEquals(false, log.isInfoEnabled());
        assertEquals(true, log.isWarnEnabled());
    }

    public void testLog4JDebug() {
        String category = "com.mckesson.eig.utility.exception";

        Log log = areLoggersEqual(category);

        assertEquals(false, log.isDebugEnabled());
        assertEquals(true, log.isInfoEnabled());
    }

    public void testIsEquals() {
        assertFalse(new Log4JWrapper(getClass()).isEqual(null));
    }

    public void testLog4JOff() {
        String category = "com.mckesson.eig.utility.service";
        Log log = areLoggersEqual(category);
        assertEquals(false, log.isFatalEnabled());
    }

    private Log areLoggersEqual(String category) {
        Log4JWrapper log = new Log4JWrapper(category);
        Logger logger = LogFactory.getLogInitializer().getLoggerRepository()
                .getLogger(category);
        assertTrue("log should be equal", log.isEqual(logger));
        return log;
    }

    private Log areLoggersEqual(Class< ? > c) {
        Log4JWrapper log = new Log4JWrapper(c);
        Logger logger = LogFactory.getLogInitializer().getLoggerRepository()
                .getLogger(c.getName());
        assertTrue("log should be equal", log.isEqual(logger));
        return log;
    }

    public void testLogging() {
        Log4JWrapper log = new Log4JWrapper(getClass());
        log.debug("foo");
        log.debug("foo", new Exception("foo"));
        log.fatal("foo");
        log.fatal("foo", new Exception("foo"));
        log.warn("foo");
        log.warn("foo", new Exception("foo"));
        log.info("foo");
        log.info("foo", new Exception("foo"));
        log.error("foo");
        log.error("foo", new Exception("foo"));
        log.error(new Exception("foo"));
        log.error(new ApplicationException("foo"));
        log.error(new ApplicationException("app msg", new Exception("sys msg")));
    }
}
