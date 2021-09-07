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
 * Test class for AssignedTasksCriteria. It tests the methods of AssignedTasksCriteria class
 * 
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestAssignedTasksCriteria 
extends junit.framework.TestCase {

    /**
     * Reference of type <code>AssignedTasksCriteria</code>
     */    
    private AssignedTasksCriteria _atc;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestAssignedTasksCriteria.class);

    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _atc = new AssignedTasksCriteria();
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
     * Test method, tests whether the object of type <code>AssignedTasksCriteria</code>
     * is null or not
     */
    public void testAssignedTasksCriteria() {

        LOG.debug("AssignedTasksCriteria: " + _atc.toString());
        assertNotNull(_atc);
    }
    
    /**
     * Test method, testAssignedTasksCriteriaConstructor
     */
    public void testAssignedTasksCriteriaConstructor() {
        
        final long worklistID = 100;
        String[] statusIDs = {"100", "200", "300" };
        _atc = new AssignedTasksCriteria(worklistID, statusIDs);
        assertNotNull(_atc);
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>Actor</code>
     */
    public void testWorklistID() {
        
        final long worklistID = 1000;
        _atc.setWorklistID(worklistID);
        assertEquals(worklistID, _atc.getWorklistID());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>Actor</code>
     */
    public void testStatusIDs() {
        
        String[] statusIDs = {"100", "200", "300" };
        _atc.setStatusIDs(statusIDs);
        assertEquals(statusIDs, _atc.getStatusIDs());
    }
}
