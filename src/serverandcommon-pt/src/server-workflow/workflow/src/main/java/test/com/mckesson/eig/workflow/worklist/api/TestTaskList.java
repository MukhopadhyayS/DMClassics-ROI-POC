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

/**
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestTaskList 
extends junit.framework.TestCase {
    
    private TaskList _taskList;
    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TaskList.class);
    
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _taskList = new TaskList();
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
     * Test method, tests whether the object of type <code>TaskList</code>
     * is null or not
     */
    public void testTaskList() {

        LOG.debug("TaskList: " + _taskList.toString());
        assertNotNull(_taskList);
    }
    
    /**
     * Test method, tests TaskList Constructor and the getter methods 
     */
    public void testTaskListConstructor() {
        
        _taskList = new TaskList(new ArrayList());
        assertNotNull(_taskList.getTasks());
        assertNotNull(_taskList.getSize());
    }
    
    /**
     * Test method, tests whether setter methods of tasks and size. 
     */
    public void testListTasks() {
        
        TaskList taskList = new TaskList();
        taskList.setTasks(new ArrayList());
        taskList.setSize(1);
        assertNotNull(taskList.getTasks());
    }

}
