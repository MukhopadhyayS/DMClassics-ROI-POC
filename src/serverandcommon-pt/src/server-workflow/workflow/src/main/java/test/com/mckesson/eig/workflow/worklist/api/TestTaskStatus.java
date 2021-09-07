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


/**
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestTaskStatus 
extends junit.framework.TestCase {
    
    /**
     * Reference of type <code>TaskStatus</code>
     */    
    private TaskStatus _taskStatus;
    
    private static String _statusID       = "new";
    private static String _displayNameKey = "high";
    private static boolean _isActve = true;
    private static int _sortOrder = 1;

    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _taskStatus = new TaskStatus();
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
     * Test method, tests the setter and getter method for statusID
     */
    public void testStatusID() {
        
        _taskStatus.setStatusID(_statusID);
        assertEquals(_statusID, _taskStatus.getStatusID());
    }
    
    /**
     * Test method, tests the setter and getter method for displayName
     */
    public void testDisplayNameKey() {
        
        _taskStatus.setDisplayNameKey(_displayNameKey);
        assertEquals(_displayNameKey, _taskStatus.getDisplayNameKey());
    }
    
    /**
     * Test method, tests the setter and getter method for sortOrder
     */
    public void testSortOrder() {
        
        _taskStatus.setSortOrder(_sortOrder);
        assertEquals(_sortOrder, _taskStatus.getSortOrder());
    }
    
    /**
     * Test method, tests the setter and getter method for isActive
     */
    public void testIsActive() {
        
        _taskStatus.setIsActive(_isActve);
        assertEquals(_isActve, _taskStatus.getIsActive());
    }
}
