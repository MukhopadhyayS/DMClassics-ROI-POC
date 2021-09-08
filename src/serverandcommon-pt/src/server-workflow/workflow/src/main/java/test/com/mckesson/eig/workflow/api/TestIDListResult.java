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

package com.mckesson.eig.workflow.api;

import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
 * Test class for IDListResult. It tests the methods of IDListResult class
 * 
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestIDListResult 
extends junit.framework.TestCase {

    /**
     * Reference of type <code>IDListResult</code>
     */    
    private IDListResult _idListResult;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestIDListResult.class);
    
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() 
    throws Exception {
        
        super.setUp();
        _idListResult = new IDListResult();
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
     * Test method, tests whether the object of type <code>IDListResult</code>
     * is null or not
     */
    public void testIDListResult() {

        LOG.debug("IDListResult: " + _idListResult.toString());
        assertNotNull(_idListResult);
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>IDListResult</code>
     */
    public void testProcessed() {

        List processed = new ArrayList();
        _idListResult.setProcessed(processed);
        assertEquals(processed, _idListResult.getProcessed());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>IDListResult</code>
     */
    public void testProcessedIDs() {
        
        List processedIDs = new ArrayList();
        _idListResult.setProcessedIDs(processedIDs);
        assertEquals(processedIDs, _idListResult.getProcessedIDs());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>IDListResult</code>
     */
    public void testFailed() {
        
        List failed = new ArrayList();
        _idListResult.setFailed(failed);
        assertEquals(failed, _idListResult.getFailed());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>IDListResult</code>
     */
    public void testFailedIDs() {
        
        List failedIDs = new ArrayList();
        _idListResult.setFailedIDs(failedIDs);
        assertEquals(failedIDs, _idListResult.getFailedIDs());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>IDListResult</code>
     */
    public void testErrorCodes() {
        
        List errorCodes = new ArrayList();
        _idListResult.setErrorCodes(errorCodes);
        assertEquals(errorCodes, _idListResult.getErrorCodes());
    }
}
