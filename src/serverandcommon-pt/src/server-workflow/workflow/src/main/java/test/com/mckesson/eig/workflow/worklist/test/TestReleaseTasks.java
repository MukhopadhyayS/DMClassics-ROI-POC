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
 * @date   Apr 08, 2008
 * @since  HECM 1.0; Apr 08, 2008
 */
public class TestReleaseTasks 
extends AbstractWorkflowTestCase {
    
    private static TaskService _taskService;
    private static WorklistService _worklistService;
    private static final String KEY_ACTOR = "Key_Actor";
    private static final String TASK_ACLS = "Task_Acls";


    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_USER   = 3;

    private static final int YEAR      = 2008;
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
        _statusID           = "draft";
        _taskName           = "Release a Task" + seed;
        _taskDescription    = "Functionality - release" + seed;
        _comments           = "About to finish" + seed;
        _canStartEarly      = false;
        _startDate          = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 1).getTime();
        _endDate            = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY).getTime();
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
        _wlName = "release.test.3" + seed;
        _wlDesc = "release.test.3" + seed;

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
    * All tasks are successfully released, no error
    */
    public void testAllTasksReleasedSuccessfully() {

        TaskList taskList = new TaskList();
        List<Task> tasks = new ArrayList<Task>();
        
        createWorklist();

        createTask();
        tasks.add(_task);

        createTask();
        tasks.add(_task);
        
        taskList.setTasks(tasks);
        
        TaskListResult taskListResult = _taskService.releaseTasks(taskList);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());
    }

    /**
     * Few tasks which has draft status are successfully released, rest failed
     */
    public void testFewTasksWithOtherStatus() {
        
        TaskList taskList = new TaskList();
        List<Task> tasks  = new ArrayList<Task>();
        
        createWorklist();
        
        _statusID = "new";
        createTask();
        tasks.add(_task);
        
        _statusID = "draft";
        createTask();
        tasks.add(_task);

        createTask();
        tasks.add(_task);
        
        taskList.setTasks(tasks);
        
        TaskListResult taskListResult = _taskService.releaseTasks(taskList);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_INVALID_TASK_STATUS, (String) errorTasksIDs.get(0));
    }

    /**
     * Few tasks which matches the created by are successfully released, rest failed
     */
    public void testFewTasksCreatedByOtherUser() {

        
        TaskList taskList = new TaskList();
        List<Task> tasks  = new ArrayList<Task>();

        createWorklist();
        createTask();
        tasks.add(_task);
        
        createTask();
        tasks.add(_task);

        createTask();
        tasks.add(_task);
        
        taskList.setTasks(tasks);
        createWorklist();
        
        WsSession.setSessionData(KEY_ACTOR, _anotherUser);
        TaskListResult taskListResult = _taskService.releaseTasks(taskList);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(SIZE + 1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_INVALID_ACTOR_FOR_OPER, (String) errorTasksIDs.get(0));
    }

    /**
     * Few tasks which exist are successfully released, rest failed
     */
    public void testFewTasksNotExists() {

        TaskList taskList = new TaskList();
        List<Task> tasks  = new ArrayList<Task>();

        WsSession.setSessionData(KEY_ACTOR, _user);
        createWorklist();
        createTask();
        tasks.add(_task);
        
        createTask();
        tasks.add(_task);
        
        Task task = new Task();
        task.setTaskID(Long.MAX_VALUE);
        tasks.add(task);
        
        taskList.setTasks(tasks);
        createWorklist();
        
        TaskListResult taskListResult = _taskService.releaseTasks(taskList);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_TASK_NOT_EXIST, (String) errorTasksIDs.get(0));
    }

    //NEGATIVE SCENARIOS
    /**
     * No Tasks is owned, an exception will be thrown
     */
    public void testWithNullTaskIDs() {
        
        try {
            _taskService.releaseTasks(null);
            fail("Should have thrown EC_NULL_TASK_IDS exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_NULL_TASK_LIST, we.getErrorCode());
        }
    }
    
}
