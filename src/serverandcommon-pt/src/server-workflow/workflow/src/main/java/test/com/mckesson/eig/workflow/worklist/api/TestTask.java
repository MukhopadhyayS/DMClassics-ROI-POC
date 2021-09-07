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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.mckesson.eig.workflow.api.Actor;

/**
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestTask 
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {
    
    /**
     * Reference of type <code>Task</code>
     */    
    private static Task _task;
    private static Task _task1; 
    private static TaskInstance _ti;
    
    private static final long INVALID_PRIORITY_ID = 10;
    
    private static boolean _canStartEarly  = false;
    private static long _taskID            = 1;
    private static int _priorityID        = 1;
    private static long _worklistID        = 1;
    private static String _statusID        = "new";
    private static String _taskName        = "high";
    private static String _taskDescription = "follow the principle";
    private static String _comments        = "no comments";
    private static String _reassignReason  = "reason";
    public static final String VK_OWNED_BY = "ownedBy";
    private static long[] _contentIDs      = new long[1];
    private static Date _startDate         = new Date();
    private static Date _endDate           = new Date();
    private static Actor _ownedBy          = new Actor(1, 1, 1);
    private static Actor _taskCreator      = new Actor(1, 1, 2);
    
    public static final String VK_CAN_START_EARLY = "canStartEarly";
    public static final String VK_COMMENTS = "comments";
    public static final String VK_COTENT_IDS = "contentIDs";
    public static final String VK_REASSIGN_REASON = "reassignReason";
    
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        init();
        _task = new Task();
        _ti   = new TaskInstance();
        _contentIDs[0] = 1;
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
     * Test method, tests whether the task values set into the taskinstance object
     */
    public void testTask() {
        
        Node node = new Node();
        node.setName(_statusID);
        Token token = new Token();
        token.setNode(node);
        
        PooledActor pooledActor = new PooledActor();
        pooledActor.setActorId(String.valueOf(_worklistID));
        
        Set<PooledActor> pooledActors = new HashSet<PooledActor>();
        pooledActors.add(pooledActor);
        
        
        _ti.setActorId(_taskCreator.toString());
        _ti.setId(_taskID);
        _ti.setName(_taskName);
        _ti.setDescription(_taskDescription);
        _ti.setPriority(_priorityID);
        _ti.setVariable(VK_CAN_START_EARLY, _canStartEarly);
        _ti.setVariable(VK_COMMENTS, _comments);
        _ti.setVariable(VK_COTENT_IDS, _contentIDs);
        _ti.setVariable(VK_REASSIGN_REASON, _reassignReason);
        _ti.setVariable(VK_OWNED_BY, _ownedBy.toString());
        _ti.setStart(_startDate);
        _ti.setDueDate(_endDate);
        _ti.setToken(token);
        _ti.setPooledActors(pooledActors);
        _task1 = new Task(_ti);
        assertNotNull(_task);
    }
    
    /**
     * Test method, tests whether the valid data are found in task object.
     */
    public void testGetTask() {
        
        assertEquals(_taskCreator.toString(), _task1.getTaskCreator().toString());
        assertEquals(_taskID,                 _task1.getTaskID());
        assertEquals(_taskName,               _task1.getTaskName());
        assertEquals(_taskDescription,        _task1.getTaskDescription());
        assertEquals(_priorityID,             _task1.getPriorityID());
        assertEquals(_canStartEarly,          _task1.isCanStartEarly());
        assertEquals(_comments,               _task1.getComments());
        assertEquals(_reassignReason,         _task1.getReassignReason());
        assertEquals(_ownedBy.toString(),     _task1.getOwnedBy().toString());
        assertEquals(_endDate,                _task1.getEndDate());
        assertEquals(_statusID,               _task1.getStatusID());
        assertEquals(_worklistID,             _task1.getWorklistID());
        assertNull(_task1.getStartDate());
    }
    
    /**
     * Test method, test the audit comment.
     */
    public void testAuditComment() {
        
        String createAuditComment          = _task.toCreateTaskAuditComment();
        String createProcessAuditComment   = _task.toProcessAuditComment(_statusID);
        String createDeleteAuditComment    = _task.toDeleteTaskAuditComment();
        String createUpdateAuditComment    = _task.toUpdateTaskAuditComment();
        String createReassignAuditComment  = _task.toReassignTaskAuditComment(_worklistID);
        
        assertNotNull(createAuditComment);
        assertNotNull(createProcessAuditComment);
        assertNotNull(createDeleteAuditComment);
        assertNotNull(createReassignAuditComment);
        assertNotNull(createUpdateAuditComment);
    }
    
    public void testValidate() {
        
        String taskName = "Never find such a person in the world, Never find such a person "
                         + "in the world, Never find such a person in the world, Never find "
                         + "such a person in the world,Never find such a person in the world, "
                         + "Never find such a person in the world, Never find such a person in "
                         + "the world, Never find such a person in the world, Never find such a "
                         + "person in the world, Never find such a person in the world, Never find"
                         + "such a person in the world";
        
        Task task = new Task();
        task.setTaskName(null);
        task.validate();
        
        task.setTaskName("");
        task.validate();
        
        task.setTaskName(_taskName);
        task.setPriorityID(-1);
        task.validate();
        
        task.setPriorityID(INVALID_PRIORITY_ID);
        task.validate();
        
        task = new Task();
        task.setTaskName(_taskName);
        task.setPriorityID(_priorityID);
        task.setComments(_taskName);
        task.setStartDate(null);
        task.validate();
        
        task = new Task();
        task.setTaskName(_taskName);
        task.setPriorityID(_priorityID);
        task.setTaskDescription(_taskDescription);
        task.setComments(_taskName);
        task.setStartDate(new Date());
        task.setEndDate(null);
        task.validate();
        
        task = new Task();
        task.setTaskName(_taskName);
        task.setComments(_taskName);
        task.setTaskDescription(_taskDescription);
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setPriorityID(_priorityID);
        task.validate();
        
        task = new Task();
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setPriorityID(_priorityID);
        task.setTaskName(taskName);
        task.setTaskDescription(_taskDescription);
        task.validate();
        
        task = new Task();
        task.setTaskName(_taskName);
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setPriorityID(_priorityID);
        task.setTaskDescription(taskName);
        task.validate();
        
        task = new Task();
        task.setTaskName(_taskName);
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setPriorityID(_priorityID);
        task.setTaskDescription(_taskDescription);
        task.setComments(taskName);
        task.validate();
        
        assertNotNull(task);
    }
}
