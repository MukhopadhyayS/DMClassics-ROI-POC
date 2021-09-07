/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. All
 * Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of McKesson
 * Information Solutions and is protected under United States and international
 * copyright and other intellectual property laws. Use, disclosure,
 * reproduction, modification, distribution, or storage in a retrieval system in
 * any form or by any means is prohibited without the prior express written
 * permission of McKesson Information Solutions.
 */
package com.mckesson.eig.workflow.worklist.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.worklist.api.Task;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskACLs;
import com.mckesson.eig.workflow.worklist.api.TaskListResult;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.service.TaskService;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 *
 * @author sahuly
 * @date   Dec 14, 2007
 * @since  HECM 1.0
 */
public class TestDisownTasks
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

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

    private static long[] _taskIDs;
    private static final long INVALID_TASKID = -200;
    private static final int SIZE = 2;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();
        _owner    = new Actor(APP_ID, ET_DOMAIN, (seed));
        _user     = new Actor(APP_ID, ET_USER,   (seed + 1));

        _anotherUser     = new Actor(APP_ID, ET_USER,   (seed + 2));

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
        _taskName           = "Process a Task" + seed;
        _taskDescription    = "Functionality - own and disown" + seed;
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
        _wlName = "disown.test.3" + seed;
        _wlDesc = "disown.test.3" + seed;

       Worklist wl = new Worklist();
       wl.setName(_wlName);
       wl.setDesc(_wlDesc);
       wl.setOwners(_owners);
       wl.setAssignedTo(_acls);

       _worklistID = _worklistService.createWorklist(wl);
       assertTrue(_worklistID > 0);

       resolveTaskAclsByActors();
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

       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(_user);
       task.setPriorityID(_priorityID);
       task.setStatusID(_statusID);
       task.setTaskName(_taskName);
       task.setTaskDescription(_taskDescription);
       task.setComments(_comments);
       task.setCanStartEarly(_canStartEarly);
       task.setStartDate(_startDate);
       task.setEndDate(_endDate);

       _taskID = _taskService.createTask(task);
       assertTrue(_taskID > 0);
   }

    //POSITIVE SCENARIOS
   /**
    * All tasks are successfully disowned, no error
    */
    public void testAllTasksDisownSuccessfully() {

        _taskIDs = new long[SIZE];

        createWorklist();

        createTask();
        _taskIDs[0] = _taskID;

        createTask();
        _taskIDs[1] = _taskID;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());

        taskListResult = _taskService.disownTasks(_taskIDs);
        errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());
    }

    /**
     * Few tasks which has in progress status are successfully disowned, rest failed
     */
    public void testFewTasksWithOtherStatus() {

        _taskIDs = new long[SIZE];

        createWorklist();

        _statusID           = "draft";
        createTask();
        long completedTask  = _taskID;

        _statusID           = "new";
        createTask();
        _taskIDs[0] = _taskID;

        createTask();
        _taskIDs[1] = _taskID;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());

        long[] tempTasks = new long[SIZE + 1];

        tempTasks[0] = _taskIDs[0];
        tempTasks[1] = _taskIDs[1];
        tempTasks[2] = completedTask;

        taskListResult = _taskService.disownTasks(tempTasks);
        errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
    }

    /**
     * Few tasks which matches the worklist are successfully owned, rest failed
     */
    public void testFewTasksOwnedByOtherUser() {

        _taskIDs = new long[SIZE];

        createWorklist();
        createTask();
        _taskIDs[0] = _taskID;

        createTask();
        _taskIDs[1] = _taskID;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());

        createTask();
        long[] tempTasks = new long[SIZE - 1];
        tempTasks[0] = _taskID;
        
        WsSession.setSessionData(KEY_ACTOR, _anotherUser);
        taskListResult = _taskService.ownTasks(_worklistID, tempTasks);
        errorTasksIDs  = taskListResult.getIdListResult().getErrorCodes();
        
        WsSession.setSessionData(KEY_ACTOR, _user);
        assertEquals(0, errorTasksIDs.size());
        tempTasks = new long[SIZE + 1];

        tempTasks[0] = _taskIDs[0];
        tempTasks[1] = _taskIDs[1];
        tempTasks[2] = _taskID;

        taskListResult = _taskService.disownTasks(tempTasks);
        errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_INVALID_ACTOR_FOR_OPER, (String) errorTasksIDs.get(0));
    }

    /**
     * Few tasks which exist are successfully owned, rest failed
     */
    public void testFewTasksNotExists() {

        _taskIDs = new long[SIZE];

        createWorklist();
        createTask();
        _taskIDs[0] = _taskID;

        createTask();
        _taskIDs[1] = _taskID;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());

        long[] tempTasks = new long[SIZE + 1];

        tempTasks[0] = _taskIDs[0];
        tempTasks[1] = _taskIDs[1];
        tempTasks[2] = Long.MAX_VALUE;

        taskListResult = _taskService.disownTasks(tempTasks);
        errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_TASK_NOT_EXIST, (String) errorTasksIDs.get(0));
    }

    /**
     * No Tasks is owned, an exception will be thrown
     */
    public void testWithInvalidTasks() {
        _taskIDs = new long[SIZE];

        createWorklist();
        createTask();
        _taskIDs[0] = _taskID;

        createTask();
        _taskIDs[1] = _taskID;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());

        long[] tempTasks = new long[SIZE + 1];

        tempTasks[0] = _taskIDs[0];
        tempTasks[1] = _taskIDs[1];
        tempTasks[2] = INVALID_TASKID;

        taskListResult = _taskService.disownTasks(tempTasks);
        errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_TASK_NOT_EXIST, (String) errorTasksIDs.get(0));
    }

    //NEGATIVE SCENARIOS
    /**
     * No Tasks is owned, an exception will be thrown
     */
    public void testWithNullTaskIDs() {
        try {
            _taskIDs = null;

            _taskService.disownTasks(_taskIDs);
            fail("Should have thrown EC_NULL_TASK_IDS exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_NULL_TASK_IDS, we.getErrorCode());
        }
    }
    
    /**
     * No Tasks is owned, an exception will be thrown
     */
    public void testWithInvalidPrivilege() {
        
        try {
            
            _taskIDs = new long[SIZE];

            createWorklistWithInvalidPrivilege();
            createTask();
            _taskIDs[0] = _taskID;

            createTask();
            _taskIDs[1] = _taskID;

            _taskService.disownTasks(_taskIDs);
            fail("Should have thrown EC_INVALID_PRIVILEGE exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INSUFFICIENT_PRIVILEGE, we.getErrorCode());
        }
    }
}
