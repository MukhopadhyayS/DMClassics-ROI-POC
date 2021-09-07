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
import com.mckesson.eig.workflow.api.Actor;

/**
 * Test class for TestCreateWLCriteria. It tests the methods of TestCreateWLCriteria class
 * 
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestCreateWLCriteria 
extends junit.framework.TestCase { 
    
    private static CreatedTasksCriteria _createTaskCriteria; 
    private static String[] _statusIDs = {"new" , "in progress"};
    private static long _worklistID    = 1; 
    private static Actor _actor        = new Actor();
    
    private static final Log LOG = LogFactory.getLogger(CreatedTasksCriteria.class);
    
    /**
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _createTaskCriteria = new CreatedTasksCriteria();
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
     * Test method, tests whether the object of type <code>CreatedTasksCriteria</code>
     * is null or not
     */
    public void testAssignedWLCriteria() {

        LOG.debug("CreatedTasksCriteria: " + _createTaskCriteria.toString());
        assertNotNull(_createTaskCriteria);
    }
    
    /**
     * Test method, tests whether the object of type <code>CreatedTasksCriteria</code>
     * is null or not
     */
    public void testAssignedWLCriteriaConstructor() {
        
        _createTaskCriteria = new CreatedTasksCriteria(_worklistID, _statusIDs, _actor);
        assertNotNull(_createTaskCriteria);
    }

    /**
     * Test method, tests the getter method of
     * <code>CreatedTasksCriteria</code>
     */
    public void testWorklistID() {
        
        CreatedTasksCriteria ctc = new CreatedTasksCriteria(_worklistID, _statusIDs, _actor);
        assertEquals(_worklistID, ctc.getWorklistID());
    }

    /**
     * Test method, tests the getter method of
     * <code>CreatedTasksCriteria</code>
     */
    public void testStatusIDs() {
        
        CreatedTasksCriteria ctc = new CreatedTasksCriteria(_worklistID, _statusIDs, _actor);
        assertEquals(_statusIDs, ctc.getStatusIDs());
    }

    /**
     * Test method, tests the getter method of
     * <code>CreatedTasksCriteria</code>
     */
    public void testActor() {
        
        CreatedTasksCriteria ctc = new CreatedTasksCriteria(_worklistID, _statusIDs, _actor);
        assertEquals(_actor.getActorID(), ctc.getCreatedBy().getActorID());
    }
}
