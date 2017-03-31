/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.config.exception;

import junit.framework.TestCase;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * @author sahuly
 * @date   Mar 30, 2009
 * @since  HECM 2.0; Mar 30, 2009
 */
public class TestConfigurationException extends TestCase {

    /**
     * Reference of type <code>WorklistException</code>
     */
    private ConfigurationException _ce;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestConfigurationException.class);

    /**
     *
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _ce = new ConfigurationException(new RuntimeException());
    }

    /**
     *
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown()
    throws Exception {
        super.tearDown();
    }

    /**
     * Test method, tests whether the object of type <code>WorklistException</code>
     * is null or not
     */
    public void testWorklistException() {

        LOG.debug("WorklistException: " + _ce.toString());
        assertNotNull(_ce);
    }

    /**
     * Test method, testWorklistExceptionWithMessageAndErrorCode
     */
    public void testWorklistExceptionWithMessageAndErrorCode() {

        final String errorCode = "TEST_ER_01";
        _ce = new ConfigurationException("Unable to process the request.", errorCode);
        assertEquals(errorCode, _ce.getErrorCode());
    }

}
