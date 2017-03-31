/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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

import com.mckesson.eig.utility.exception.ClientErrorCodes;
/**
 * This class tests the LogConfigurationException.
 */
public class TestLogConfigurationException extends TestCase {

    /**
     * Exception message of type string.
     */
    private String _testMessage;

    /**
     * Initializes data for testing.
     * 
     * @throws Exception
     *             when initialization fails.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _testMessage = "test";

    }

    /**
     * Destroys the data created for testing.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests the <code>LogConfigurationException</code> with Message.
     */
    public void testLogConfigurationExceptionStr() {
        try {
            throw new ConfigureLogException(_testMessage);
        } catch (ConfigureLogException e) {
            assertEquals(e.getMessage(), _testMessage);
        }
    }

    /**
     * Tests the <code>LogConfigurationException</code> with Throwable.
     */
    public void testLogConfigurationExceptionTrw() {
        try {
            throw new ConfigureLogException(new NullPointerException());
        } catch (ConfigureLogException e) {
            assertNull(e.getMessage());
        }
    }

    /**
     * Tests default LogConfigurationException.
     */
    public void testLogConfigurationException() {
        try {
            throw new ConfigureLogException();
        } catch (ConfigureLogException e) {
            assertNull(e.getMessage());
            assertNull(e.getCause());
        }
    }

    /**
     * Tests the LogConfigurationException with Exception and errorcode.
     */
    public void testLogConfigurationExceptionWithExcepAndErrorCode() {
        try {
            throw new ConfigureLogException(new NullPointerException(
                    _testMessage), ClientErrorCodes.EMPTY_LOG_INFORMATION);
        } catch (ConfigureLogException e) {
            assertEquals(e.getMessage(), _testMessage);
            assertEquals(e.getErrorCode(),
                    ClientErrorCodes.EMPTY_LOG_INFORMATION);
        }
    }
    
    /**
     * Tests the ListViewLogException with Exception and errorcode.
     */
    public void testLogConfigurationExceptionWithExceptionAndCause() {
        try {
            throw new ConfigureLogException(ClientErrorCodes.EMPTY_LOG_INFORMATION, 
                                                new NullPointerException(_testMessage));
        } catch (ConfigureLogException e) {
            assertEquals(e.getErrorCode(), null);
        }
    }
    
    /**
     * Tests the ListViewLogException with Exception and errorcode.
     */
    public void testLogConfigurationExceptionWithMessageAndErrorCode() {
        try {
            throw new ConfigureLogException("empty log info",
                                                ClientErrorCodes.EMPTY_LOG_INFORMATION);
        } catch (ConfigureLogException e) {
            assertEquals(e.getErrorCode(), ClientErrorCodes.EMPTY_LOG_INFORMATION);
        }
    }

}
