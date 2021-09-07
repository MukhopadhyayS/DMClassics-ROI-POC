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
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestCreatedTasksCriteria 
extends junit.framework.TestCase {
    
    /**
     * Reference of type <code>CreatedTasksCriteria</code>
     */    
    private CreatedTasksCriteria _createdTasksCriteria;
    
    private static long _worklistID = 1;
    private static String[] _statusIDs = new String[] {"new"};
    private static Actor _actor = new Actor(1, 1, 1);
    

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestCreatedTasksCriteria.class);

    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _createdTasksCriteria = new CreatedTasksCriteria();
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
    public void testCreatedTasksCriteria() {

        LOG.debug("CreatedTasksCriteria: " + _createdTasksCriteria.toString());
        assertNotNull(_createdTasksCriteria);
    }
    
    /**
     * Test method, tests CreatedTasksCriteria constructor.
     * is null or not
     */
    public void testCreatedTasksCriteriaConstructor() {
        
        _createdTasksCriteria = new CreatedTasksCriteria(_worklistID, _statusIDs, _actor);
        assertEquals(_worklistID, _createdTasksCriteria.getWorklistID());
        assertEquals(_statusIDs, _createdTasksCriteria.getStatusIDs());
        assertEquals(_actor.getActorID(), _createdTasksCriteria.getCreatedBy().getActorID());
    }
}
