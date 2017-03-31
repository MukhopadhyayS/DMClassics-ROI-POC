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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.utility.log.log4j.Log4JWrapper;
import com.mckesson.eig.utility.log.log4j.LogInitializer;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * Currently this class only supports Log4J!
 * Eventually, though, it might wrap org.apache.commons.logging.LogFactory
 */
public final class LogFactory {

    private static LogInitializer _logInitializer;
    /**
     * This is the key referred in spring configuration file.
     */
    private static final String LOG_INITIALIZER = "log_initializer";

    static {
        setUpLog4JInitializer();
    }

    /**
     * private constructor
     */
    private LogFactory() {
    }

    /**
     * Returns the Log4JWrapper object
     * @param category
     * @return Log
     */
    public static Log getLogger(String category) {
        return new Log4JWrapper(category);
    }

    /**
     * Returns the Log4JWrapper object
     * @param Class
     * @return Log
     */
    public static Log getLogger(Class<?> c) {
        return new Log4JWrapper(c);
    }

    /**
     * Returns the Log4JWrapper object
     * @param Logger
     * @return Log
     */
    public static Log getLogger(Logger logger) {
        return new Log4JWrapper(logger);
    }

    /**
     * Returns the LogInitializer object
     * @return LogInitializer
     */
    public static LogInitializer getLogInitializer() {
        return _logInitializer;
    }

    /**
     * Sets the loginitializer object, called only once
     */
    private static void setUpLog4JInitializer() {
        BeanFactory beanFactory = SpringUtilities.getInstance().getBeanFactory();
        if (beanFactory != null) {
           _logInitializer = (LogInitializer) beanFactory.getBean(LOG_INITIALIZER);
        } else {
            System.err.println("log4JInitializer inside logfactory is:" + _logInitializer);
        }
    }
}
