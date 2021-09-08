/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.workflow.worklist.api;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
 * Test class for WorklistException. It tests the methods of WorklistException class
 * 
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestWorklistException 
extends junit.framework.TestCase {

    /**
     * Reference of type <code>WorklistException</code>
     */    
    private WorklistException _we;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestWorklistException.class);

    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() 
    throws Exception {
        
        super.setUp();
        _we = new WorklistException(new RuntimeException());
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

        LOG.debug("WorklistException: " + _we.toString());
        assertNotNull(_we);
    }
    
    /**
     * Test method, testWorklistExceptionWithMessageAndErrorCode
     */
    public void testWorklistExceptionWithMessageAndErrorCode() {

        final String errorCode = "TEST_ER_01";
        _we = new WorklistException("Unable to process the request.", errorCode);
        assertEquals(errorCode, _we.getErrorCode());
    }
}
