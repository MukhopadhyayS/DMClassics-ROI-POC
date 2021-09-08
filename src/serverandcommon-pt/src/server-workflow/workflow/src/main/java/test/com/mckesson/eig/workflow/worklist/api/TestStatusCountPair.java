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

package com.mckesson.eig.workflow.worklist.api;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

import junit.framework.TestCase;

/**
 * @author sahuly
 * @date   Jan 12, 2009
 * @since  HECM 1.0; Jan 12, 2009
 */
public class TestStatusCountPair extends TestCase {
    
    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(StatusCountPair.class);
    
    private StatusCountPair _statusCountPair;

    private static final String KEY = "Check"; 
    private static final long VALUE = 1; 
    
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _statusCountPair = new StatusCountPair();
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
     * Test method, tests whether the object of type <code>AssignedWLCriteria</code>
     * is null or not
     */
    public void testStatusCountPair() {

        LOG.debug("StatusCountPair: " + _statusCountPair.toString());
        assertNotNull(_statusCountPair);
    }
    
    public void testStatusCountPairConstructor() {

        StatusCountPair stausCountPair = new StatusCountPair(KEY, VALUE);
        assertNotNull(_statusCountPair);
    }
    
    public void testSetAndGetStatusCountPair() {
        
        _statusCountPair.setKey(KEY);
        _statusCountPair.setValue(VALUE);
        assertNotNull(_statusCountPair.getKey());
        assertNotNull(_statusCountPair.getValue());
    }
}
