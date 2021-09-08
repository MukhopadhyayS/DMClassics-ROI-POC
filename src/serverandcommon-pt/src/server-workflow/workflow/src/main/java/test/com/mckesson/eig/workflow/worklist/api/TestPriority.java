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
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestPriority 
extends junit.framework.TestCase {
    
    /**
     * Reference of type <code>Priority</code>
     */    
    private Priority _priority;
    
    private static long _priorityID = 1;
    private static String _displayNameKey = "high";
    private static int _sortOrder = 1;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(Priority.class);

    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _priority = new Priority();
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
    public void testPriority() {

        LOG.debug("Priority: " + _priority.toString());
        assertNotNull(_priority);
    }
    
    /**
     * Test method, tests the setter and getter method for priorityID
     */
    public void testPriorityID() {
        
        _priority.setPriorityID(_priorityID);
        assertEquals(_priorityID, _priority.getPriorityID());
    }
    
    /**
     * Test method, tests the setter and getter method for displayName
     */
    public void testDisplayName() {
        
        _priority.setDisplayNameKey(_displayNameKey);
        assertEquals(_displayNameKey, _priority.getDisplayNameKey());
    }
    
    /**
     * Test method, tests the setter and getter method for sortOrder
     */
    public void testSortOrder() {
        
        _priority.setSortOrder(_sortOrder);
        assertEquals(_sortOrder, _priority.getSortOrder());
    }
}
