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
import com.mckesson.eig.workflow.api.Actors;


/**
 * Test class for AssignedWLCriteria. It tests the methods of AssignedWLCriteria class
 * 
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestAssignedWLCriteria 
extends junit.framework.TestCase {

    /**
     * Reference of type <code>AssignedWLCriteria</code>
     */    
    private AssignedWLCriteria _awlc;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestAssignedWLCriteria.class);

    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _awlc = new AssignedWLCriteria();
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
    public void testAssignedWLCriteria() {

        LOG.debug("AssignedWLCriteria: " + _awlc.toString());
        assertNotNull(_awlc);
    }

    public void testAssignedWLCriteriaConstructor() {
        
        Actors actors = new Actors();
        _awlc = new AssignedWLCriteria(actors, actors, true);
        assertNotNull(_awlc);
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>AssignedWLCriteria</code>
     */
    public void testOwners() {
        
        Actors owners = new Actors();
        _awlc.setOwners(owners);
        assertEquals(owners, _awlc.getOwners());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>AssignedWLCriteria</code>
     */
    public void testAssignedTo() {
        
        Actors assignedTo = new Actors();
        _awlc.setAssignedTo(assignedTo);
        assertEquals(assignedTo, _awlc.getAssignedTo());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>AssignedWLCriteria</code>
     */
    public void testShowEmptyWorklists() {

        _awlc.setShowEmptyWorklists(true);
        assertEquals(true, _awlc.getShowEmptyWorklists());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>AssignedWLCriteria</code>
     */
    public void testShowOnlyOwnerWL() {
    	
    	_awlc.setShowOnlyOwnerWL(true);
    	assertEquals(true, _awlc.getShowOnlyOwnerWL());
    }
}
