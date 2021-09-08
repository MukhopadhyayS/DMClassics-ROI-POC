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

package com.mckesson.eig.workflow.worklist.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;
import com.mckesson.eig.workflow.worklist.api.Task;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskACLs;
import com.mckesson.eig.workflow.worklist.api.TaskList;
import com.mckesson.eig.workflow.worklist.api.TaskListResult;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.service.TaskService;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author Sahul Hameed Y
 * @date   Feb 25, 2008
 * @since  HECM 1.0; Feb 25, 2008
 */
public class TestReassignTasks 
extends AbstractWorkflowTestCase {
    
    private static TaskService _taskService;
    private static WorklistService _worklistService;
    private static final String KEY_ACTOR = "Key_Actor";
    private static final String TASK_ACLS = "Task_Acls";


    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_USER   = 3;

    private static final int DAY       = 12;

    private static long     _worklistID;

    private static String   _wlName;
    private static String   _wlDesc;

    private static Actor    _owner;
    private static Actor    _user;
    private static Actor    _anotherUser;
    private static TaskACL  _acl;

    private static Actors   _owners;
    private static Actors   _users;
    private static TaskACLs _acls;

    private static long     _taskID;
    private static long     _priorityID;
    private static String   _statusID;
    private static String   _taskName;
    private static String   _taskDescription;
    private static String   _comments;
    private static Date     _startDate;
    private static Date     _endDate;
    private static boolean  _canStartEarly;
    
    private static Task     _task;

    private static long[] _taskIDs;
    private static final int SIZE = 2;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();
        _owner    = new Actor(APP_ID, ET_DOMAIN, (seed));
        _user     = new Actor(APP_ID, ET_USER,   (seed + 1));

        _anotherUser = new Actor(APP_ID, ET_USER,   (seed + 2));

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);
        
        Set<Actor> acts = new HashSet<Actor>();
        acts.add(_user);
        
        _users = new Actors();
        _users.setActors(acts);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user.getEntityID());
        WsSession.setSessionData(KEY_ACTOR, _user);

        _worklistService     = (WorklistService) getManager(WORKLIST_MANAGER);
        _taskService         = (TaskService)     getManager(TASK_MANAGER);

        _priorityID         = 2;
        _statusID           = "new";
        _taskName           = "Reassign a Task" + seed;
        _taskDescription    = "Functionality - reassign" + seed;
        _comments           = "About to finish" + seed;
        _canStartEarly      = true;
        GregorianCalendar cal = new GregorianCalendar();
        cal.roll(Calendar.DAY_OF_YEAR, -1);
        _startDate          = cal.getTime();
        cal.roll(Calendar.DAY_OF_YEAR, +2);
        _endDate            = cal.getTime();
    }

    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklist() {

        _acl  = new TaskACL(true, true, true, true, _user);
        TaskACL anotherUserACL = new TaskACL(true, true, true, true, _anotherUser);

        Set<TaskACL> aclSet = new HashSet<TaskACL>(2);
        aclSet.add(_acl);
        aclSet.add(anotherUserACL);
        _acls = new TaskACLs(aclSet);

        long seed = System.currentTimeMillis();
        _wlName = "reassign.test.3" + seed;
        _wlDesc = "reassign.test.3" + seed;

       Worklist wl = new Worklist();
       wl.setName(_wlName);
       wl.setDesc(_wlDesc);
       wl.setOwners(_owners);
       wl.setAssignedTo(_acls);

       _worklistID = _worklistService.createWorklist(wl);
       assertTrue(_worklistID > 0);
       
	   resolveTaskAclsByActors();
   }
    
   private void resolveTaskAclsByActors() {
       WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
       _worklistService.resolveTaskAclsByActors(_users);
   }

    
    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklistWithInvalidPrivilege() {

        _acl  = new TaskACL(false, false, false, false, _user);

        Set<TaskACL> aclSet = new HashSet<TaskACL>(1);
        aclSet.add(_acl);
        _acls = new TaskACLs(aclSet);

        long seed = System.currentTimeMillis();
        _wlName = "own.test.2" + seed;
        _wlDesc = "own.test.2" + seed;

       Worklist wl = new Worklist();
       wl.setName(_wlName);
       wl.setDesc(_wlDesc);
       wl.setOwners(_owners);
       wl.setAssignedTo(_acls);

       _worklistID = _worklistService.createWorklist(wl);
       assertTrue(_worklistID > 0);
       WsSession.setSessionData(TASK_ACLS, getUserTaskAcls());
   }
    
    private HashMap getUserTaskAcls() {
        return null;// _worklistService.getTaskAclsByActor(_users);
    }

   /**
    * This method is used to create a task.
    */
   public void createTask() {
       
       _task = new Task();
       _task.setWorklistID(_worklistID);
       _task.setTaskCreator(_user);
       _task.setPriorityID(_priorityID);
       _task.setStatusID(_statusID);
       _task.setTaskName(_taskName);
       _task.setTaskDescription(_taskDescription);
       _task.setComments(_comments);
       _task.setCanStartEarly(_canStartEarly);
       _task.setStartDate(_startDate);
       _task.setEndDate(_endDate);

       _taskID = _taskService.createTask(_task);
       _task.setTaskID(_taskID);
       
       assertTrue(_taskID > 0);
   }

   /**
    * All tasks are successfully reassigned, no error
    */
    public void testAllTasksReassignedSuccessfully() {

        _taskIDs = new long[SIZE];
        
        TaskList taskList = new TaskList();
        List<Task> tasks = new ArrayList<Task>();
        
        createWorklist();

        createTask();
        tasks.add(_task);
        _task.setReassignReason("test reassign");
        _taskIDs[0] = _taskID;

        createTask();
        tasks.add(_task);
        _taskIDs[1] = _taskID;
        
        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());
        
        createWorklist();
        taskList.setTasks(tasks);
        
        taskListResult = _taskService.reassignTasks(_worklistID, taskList);
        errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());
    }

    /**
     * Few tasks which has in progress status are successfully disowned, rest failed
     */
    public void testFewTasksWithOtherStatus() {
        
        final int size = 3;
        _taskIDs = new long[size];
        
        TaskList taskList = new TaskList();
        List<Task> tasks  = new ArrayList<Task>();
        
        createWorklist();

        createTask();
        tasks.add(_task);
        long completedTask  = _taskID;
        _taskIDs[0] = _taskID;
        
        createTask();
        tasks.add(_task);
        _taskIDs[1] = _taskID;

        createTask();
        tasks.add(_task);
        _taskIDs[2] = _taskID;
        
        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());
        
        TaskList taskList1 = new TaskList();
        List<Task> tasks1 = new ArrayList<Task>();
        Task task = _taskService.getTaskDetails(completedTask);
        tasks1.add(task);
        taskList1.setTasks(tasks1);
        
        taskListResult = _taskService.completeTasks(taskList1);
        errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());
        
        taskList.setTasks(tasks);
        createWorklist();
        
        taskListResult = _taskService.reassignTasks(_worklistID, taskList);
        errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
        
        task = _taskService.getTaskDetails(completedTask);
    }

    /**
     * Few tasks which matches the worklist are successfully owned, rest failed
     */
    public void testFewTasksOwnedByOtherUser() {

        _taskIDs = new long[SIZE];
        TaskList taskList = new TaskList();
        List<Task> tasks  = new ArrayList<Task>();

        createWorklist();
        createTask();
        tasks.add(_task);
        _taskIDs[0] = _taskID;

        createTask();
        tasks.add(_task);
        _taskIDs[1] = _taskID;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());

        createTask();
        tasks.add(_task);
        
        long[] tempTasks = new long[SIZE - 1];
        tempTasks[0] = _taskID;
        
        taskListResult = _taskService.ownTasks(_worklistID, tempTasks);
        errorTasksIDs  = taskListResult.getIdListResult().getErrorCodes();
        
        WsSession.setSessionData(KEY_ACTOR, _user);
        assertEquals(0, errorTasksIDs.size());

        taskList.setTasks(tasks);
        createWorklist();
        
        WsSession.setSessionData(KEY_ACTOR, _anotherUser);
        taskListResult = _taskService.reassignTasks(_worklistID, taskList);
        errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(SIZE + 1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_INVALID_ACTOR_FOR_OPER, (String) errorTasksIDs.get(0));
    }

    /**
     * Few tasks which exist are successfully owned, rest failed
     */
    public void testFewTasksNotExists() {

        _taskIDs = new long[SIZE];
        TaskList taskList = new TaskList();
        List<Task> tasks  = new ArrayList<Task>();

        createWorklist();
        createTask();
        tasks.add(_task);
        _taskIDs[0] = _taskID;

        createTask();
        tasks.add(_task);
        _taskIDs[1] = _taskID;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());

        Task task = new Task();
        task.setTaskID(Long.MAX_VALUE);
        tasks.add(task);
        
        taskList.setTasks(tasks);
        createWorklist();
        
        taskListResult = _taskService.reassignTasks(_worklistID, taskList);
        errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_TASK_NOT_EXIST, (String) errorTasksIDs.get(0));
    }

    public void testReassignToSameWorklist() {

        _taskIDs = new long[SIZE];
        
        TaskList taskList = new TaskList();
        List<Task> tasks = new ArrayList<Task>();
        
        createWorklist();

        createTask();
        tasks.add(_task);
        _taskIDs[0] = _taskID;

        createTask();
        tasks.add(_task);
        _taskIDs[1] = _taskID;
        
        
        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());
        
        taskList.setTasks(tasks);
        
        try {
            taskListResult = _taskService.reassignTasks(_worklistID, taskList);
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_REASSIGN_TO_SAME_WL, we.getErrorCode());
        }
    }
    //NEGATIVE SCENARIOS
    /**
     * No Tasks is owned, an exception will be thrown
     */
    public void testWithNullTaskIDs() {
        
        try {
            _taskService.reassignTasks(_worklistID, null);
            fail("Should have thrown EC_NULL_TASK_IDS exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_NULL_TASK_LIST, we.getErrorCode());
        }
    }
    
    /**
     * No Tasks is owned, an exception will be thrown
     */
    public void testWithInvalidPrivilege() {
        
        try {
            
            _taskIDs = new long[SIZE];
            
            TaskList taskList = new TaskList();
            List<Task> tasks  = new ArrayList<Task>();
            
            createWorklistWithInvalidPrivilege();
            createTask();
            tasks.add(_task);

            createTask();
            _taskIDs[1] = _taskID;
            tasks.add(_task);
            
            taskList.setTasks(tasks);
            _taskService.reassignTasks(_worklistID, taskList);
            fail("Should have thrown EC_INVALID_PRIVILEGE exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INSUFFICIENT_PRIVILEGE, we.getErrorCode());
        }
    }
    
    /**
     * No Tasks is owned, an exception will be thrown
     */
    public void testWithInvalidWorklistID() {
        
        try {
            
            _taskIDs = new long[SIZE];
            
            TaskList taskList = new TaskList();
            List<Task> tasks  = new ArrayList<Task>();
            
            createWorklist();
            createTask();
            tasks.add(_task);

            taskList.setTasks(tasks);
            _taskService.reassignTasks(-1, taskList);
            fail("Should have thrown EC_INVALID_WORKLIST_ID exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_WORKLIST_ID, we.getErrorCode());
        }
    }
    
    public void testWithIncorrectSourceWorklistID() {
        
        _taskIDs = new long[SIZE - 1];
        
        TaskList taskList = new TaskList();
        List<Task> tasks  = new ArrayList<Task>();
        long prevWorklistID = _worklistID;
        createWorklist();
        createTask();
        
        _taskIDs[0] = _taskID;
        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());
        
        createWorklist();
        tasks.add(_task);
        _task.setWorklistID(prevWorklistID);
        taskList.setTasks(tasks);
        
        taskListResult = _taskService.reassignTasks(_worklistID, taskList);
        errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_TASK_NOT_FOUND_IN_WL, (String) errorTasksIDs.get(0));
        
    }
}
