/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries. All
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
 * @author sahuly
 * @date   Dec 14, 2007
 * @since  HECM 1.0
 */
public class TestOwnTasks
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static TaskService _taskService;
    private static WorklistService _worklistService;
    private static final String KEY_ACTOR = "Key_Actor";
    private static final String TASK_ACLS = "Task_Acls";

    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_USER   = 3;

    private static final int YEAR      = 2020;
    private static final int DAY       = 12;

    private static long     _worklistID;

    private static String   _wlName;
    private static String   _wlDesc;

    private static Actor    _owner;
    private static Actor    _user;
    private static TaskACL  _acl;

    private static Actors   _owners;
    private static Actors   _users;
    private static TaskACLs _acls;

    private static long     _taskID;
    private static long     _taskID1;
    private static long     _taskID2;
    private static long     _priorityID;
    private static String   _statusID;
    private static String   _taskName;
    private static String   _taskName1;
    private static String   _taskDescription;
    private static String   _comments;
    private static Date     _startDate;
    private static Date     _endDate;
    private static Date     _startDate1;
    private static Date     _endDate1;
    private static boolean  _canStartEarly;

    private static long[] _taskIDs;
    private static final long INVALID_TASKID = -200;
    private static final int SIZE = 2;

    public void testSetUp() {

        init();
        
        long seed = System.currentTimeMillis();
        _owner    = new Actor(APP_ID, ET_DOMAIN, (seed + 2));
        _user     = new Actor(APP_ID, ET_USER,   (seed + 2));

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

        _worklistService = (WorklistService) getManager(WORKLIST_MANAGER);
        _taskService     = (TaskService)     getManager(TASK_MANAGER);

        _priorityID         = 2;
        _statusID           = "new";
        _taskName           = "own tasks" + seed;
        _taskName1          = "asched_own tasks" + seed + 1;
        _taskDescription    = "complete tasks" + seed;
        _comments           = "completed tasks" + seed;
        _canStartEarly      = true;
        _startDate          = new GregorianCalendar(YEAR + 1, Calendar.JANUARY, DAY - 1).getTime();
        _endDate            = new GregorianCalendar(YEAR + 1, Calendar.JANUARY, DAY + 1).getTime();
        _startDate1         = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 2).getTime();
        _endDate1           = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 1).getTime();
    }

    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklist() {

        _acl  = new TaskACL(true, true, true, true, _user);

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
   
   /**
    * This method is used to create a task.
    */
   public void createTaskWithoutEarlyStart() {

       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(_user);
       task.setPriorityID(_priorityID);
       task.setStatusID(_statusID);
       task.setTaskName(_taskName);
       task.setTaskDescription(_taskDescription);
       task.setComments(_comments);
       task.setCanStartEarly(false);
       task.setStartDate(_startDate);
       task.setEndDate(_endDate);

       _taskID = _taskService.createTask(task);
       assertTrue(_taskID > 0);
   }

   /**
    * This method is used to create a task.
    */
   public void createSchedulerTask() {

       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(_user);
       task.setPriorityID(_priorityID);
       task.setStatusID(_statusID);
       task.setTaskName(_taskName1);
       task.setTaskDescription(_taskDescription);
       task.setComments(_comments);
       task.setCanStartEarly(_canStartEarly);
       task.setStartDate(_startDate1);
       task.setEndDate(_endDate1);

       _taskID1 = _taskService.createTask(task);
       assertTrue(_taskID1 > 0);
   }
   
   /**
    * This method is used to create a task.
    */
   public void createOverdueTask() {

       final long ms = 2000;
       Task task = new Task();
       task.setWorklistID(_worklistID);
       task.setTaskCreator(_user);
       task.setPriorityID(_priorityID);
       task.setStatusID(_statusID);
       task.setTaskName(_taskName1);
       task.setTaskDescription(_taskDescription);
       task.setComments(_comments);
       task.setCanStartEarly(_canStartEarly);
       task.setStartDate(new Date());
       try {
           Thread.sleep(ms);
       } catch (InterruptedException e) {
           e.printStackTrace(System.err);
       }
       task.setEndDate(new Date());

       _taskID2 = _taskService.createTask(task);
       assertTrue(_taskID2 > 0);
   }

    //POSITIVE SCENARIOS
   /**
    * All tasks are successfully owned, no error
    * @throws InterruptedException 
    */
    public void testAllTasksOwnSuccessfully() throws InterruptedException {

        final int size = 3;
        final long stopTime = 3 * 1000;
        _taskIDs = new long[size];

        createWorklist();

        createTask();
        _taskIDs[0] = _taskID;

        createTask();
        _taskIDs[1] = _taskID;

        createOverdueTask();
        _taskIDs[2] = _taskID2;

        Thread.sleep(stopTime);

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());
    }

    /**
     * Tes task owning with scheduler
     * @throws InterruptedException
     */
    public void testTasksScheduler() throws InterruptedException {

        _taskIDs = new long[SIZE];

        // Time interval for which the execution of the thread stops.
        final long stopTime = 5 * 1000;

        createWorklist();

        createSchedulerTask();
        _taskIDs[0] = _taskID1;

        createSchedulerTask();
        _taskIDs[1] = _taskID1;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        Thread.sleep(stopTime + stopTime + stopTime);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(0, errorTasksIDs.size());
    }

    /**
     * Few tasks which has new status are successfully owned, rest failed
     */
    public void testFewTasksWithOtherStatus() {

        _taskIDs = new long[SIZE + 1];

        createWorklist();

        _statusID           = "draft";
        createTask();
        _taskIDs[0] = _taskID;

        _statusID           = "new";
        createTask();
        _taskIDs[1] = _taskID;

        createTask();
        _taskIDs[2] = _taskID;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List failedTasksIDs = taskListResult.getIdListResult().getFailedIDs();
        assertEquals(1, failedTasksIDs.size());
    }
    
    public void testTasksOwnWithoutEarlyStart() {

        _taskIDs = new long[SIZE];

        createWorklist();

        createTask();
        _taskIDs[0] = _taskID;

        createTaskWithoutEarlyStart();
        _taskIDs[1] = _taskID;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_INVALID_STAT_FOR_OPER, errorTasksIDs.get(0));
    }

    /**
     * Few tasks which matches the worklist are successfully owned, rest failed
     */
    public void testFewTasksWithOtherWL() {

        _taskIDs = new long[SIZE + 1];

        createWorklist();
        createTask();
        _taskIDs[0] = _taskID;

        createWorklist();
        createTask();
        _taskIDs[1] = _taskID;

        createTask();
        _taskIDs[2] = _taskID;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_TASK_NOT_FOUND_IN_WL, (String) errorTasksIDs.get(0));
    }

    /**
     * Few tasks which exist are successfully owned, rest failed
     */
    public void testFewTasksNotExists() {

        _taskIDs = new long[SIZE + 1];

        createWorklist();
        createTask();
        _taskIDs[0] = _taskID;

        createTask();
        _taskIDs[1] = _taskID;

        _taskIDs[2] = Long.MAX_VALUE;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
        List errorTasksIDs = taskListResult.getIdListResult().getErrorCodes();
        assertEquals(1, errorTasksIDs.size());
        assertEquals(WorklistEC.EC_TASK_NOT_EXIST, (String) errorTasksIDs.get(0));
    }

    /**
     * No Tasks is owned, an exception will be thrown
     */
    public void testWithInvalidTasks() {
        _taskIDs = new long[SIZE + 1];

        createWorklist();
        createTask();
        _taskIDs[0] = _taskID;

        createTask();
        _taskIDs[1] = _taskID;

        _taskIDs[2] = INVALID_TASKID;

        TaskListResult taskListResult = _taskService.ownTasks(_worklistID, _taskIDs);
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
            _taskIDs = null;

            createWorklist();

            _taskService.ownTasks(_worklistID, _taskIDs);
            fail("Should have thrown EC_NULL_TASK_IDS exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_NULL_TASK_IDS, we.getErrorCode());
        }
    }

    /**
     * No Tasks is owned, an exception will be thrown
     */
    public void testWithInvalidWLID() {
        try {
            _taskIDs = new long[SIZE];

            createWorklist();
            createTask();
            _taskIDs[0] = _taskID;

            createTask();
            _taskIDs[1] = _taskID;

            _worklistID = -1;

            _taskService.ownTasks(_worklistID, _taskIDs);
            fail("Should have thrown EC_INVALID_WORKLIST_ID exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_WORKLIST_ID, we.getErrorCode());
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

            _taskService.ownTasks(_worklistID, _taskIDs);
            fail("Should have thrown EC_INSUFFICIENT_PRIVILEGE exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INSUFFICIENT_PRIVILEGE, we.getErrorCode());
        }
    }
}
