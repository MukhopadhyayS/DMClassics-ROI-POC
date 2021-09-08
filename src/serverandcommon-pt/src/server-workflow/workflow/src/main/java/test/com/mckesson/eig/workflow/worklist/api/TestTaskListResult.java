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

import java.util.ArrayList;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.workflow.api.IDListResult;

/**
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestTaskListResult 
extends junit.framework.TestCase {
    
    private TaskListResult _taskListResult;
    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TaskListResult.class);
    
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _taskListResult = new TaskListResult();
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
     * Test method, tests whether the object of type <code>TaskListResult</code>
     * is null or not
     */
    public void testTaskListResult() {

        LOG.debug("TaskList: " + _taskListResult.toString());
        assertNotNull(_taskListResult);
    }
    
    /**
     * Test method, tests TaskListResult Constructor and the getter methods 
     */
    public void testTaskListResultWithIDListResult() {
        
        _taskListResult = new TaskListResult();
        _taskListResult.setIdListResult(new IDListResult());
        
        assertNotNull(_taskListResult.getIdListResult());
    }
    
    /**
     * Test method, tests whether setter methods of tasks and size. 
     */
    public void testGetTaskList() {
        
        TaskListResult taskListResult = new TaskListResult();
        taskListResult.setProcessedTasksList(new ArrayList());
        taskListResult.setUnProcessedTasksList(new ArrayList());
        assertNotNull(taskListResult.getProcessedTasksList());
        assertNotNull(taskListResult.getUnProcessedTasksList());
    }
}
