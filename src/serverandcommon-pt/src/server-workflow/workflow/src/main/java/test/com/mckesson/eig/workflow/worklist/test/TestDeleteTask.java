/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
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
 * @date   Dec 17, 2007
 * @since  HECM 1.0; Dec 17, 2007
 */
public class TestDeleteTask
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static WorklistService _worklistService;
    private static TaskService _taskService;
    private static final String KEY_ACTOR = "Key_Actor";
    private static final String TASK_ACLS = "Task_Acls";

    private static final int  APP_ID               = 1;
    private static final int  ET_DOMAIN            = 1;
    private static final int  ET_USER              = 3;
    private static final long NON_EXISTING_TASK_ID = Long.MAX_VALUE;

    private static final int YEAR      = 2008;
    private static final int DAY       = 12;

    private static long     _worklistID;
    private static long     _worklistID1;

    private static String   _wlName;
    private static String   _wlDesc;

    private static String   _wlName1;
    private static String   _wlDesc1;

    private static Actor    _owner;
    private static Actor    _user;
    private static Actor    _user1;

    private static Actors   _owners;
    private static Actors   _users;
    private static TaskACLs _acls;
    private static TaskACLs _acls1;

    private static long     _taskID;
    private static long     _taskID1;
    private static long     _priorityID;
    private static String   _statusID;
    private static String   _taskName;
    private static String   _taskDescription;
    private static String   _comments;
    private static Date     _startDate;
    private static Date     _endDate;
    private static boolean  _canStartEarly;

    public void testSetUp() {

        init();
        
        long seed = System.currentTimeMillis();
        
        _owner    = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
        _user     = new Actor(APP_ID, ET_USER,   1);
        _user1    = new Actor(APP_ID, ET_USER,   (seed + 2));

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);
        
        Set<Actor> acts = new HashSet<Actor>();
        acts.add(_user);
        
        _users = new Actors();
        _users.setActors(acts);

        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
		WsSession.setSessionData(KEY_ACTOR, _user);
        WsSession.setSessionUserId(_user.getEntityID());

        _worklistService = (WorklistService) getManager(WORKLIST_MANAGER);
        _taskService     = (TaskService)     getManager(TASK_MANAGER);

        _priorityID         = 2;
        _statusID           = "new";
        _taskName           = "delete a task" + seed;
        _taskDescription    = "follow certain principles" + seed;
        _comments           = "finish" + seed;
        _canStartEarly      = false;
        _startDate          = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 1).getTime();
        _endDate            = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY).getTime();

        createWorklist();
        _taskID  = createTask();
        _taskID1 = createTask();
    }

    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklist() {
       
        long seed = System.currentTimeMillis();
        TaskACL acl  = new TaskACL(true, true, true, true, _user);
        TaskACL acl1 = new TaskACL(true, true, true, true, _user);
        
       _wlName = "name.1." + seed;
       _wlDesc = "desc.1." + seed;

       _wlName1 = "name.2." + seed;
       _wlDesc1 = "desc.2." + seed;
       
       Set<TaskACL> aclSet = new HashSet<TaskACL>(1);
       aclSet.add(acl);
       _acls = new TaskACLs(aclSet);

       Set<TaskACL> aclSet1 = new HashSet<TaskACL>(1);
       aclSet1.add(acl1);
       _acls1 = new TaskACLs(aclSet1);
        
       Worklist wl = new Worklist();
       wl.setName(_wlName);
       wl.setDesc(_wlDesc);
       wl.setOwners(_owners);
       wl.setAssignedTo(_acls);

       _worklistID = _worklistService.createWorklist(wl);
       assertTrue(_worklistID > 0);

       Worklist wl1 = new Worklist();
       wl1.setName(_wlName1);
       wl1.setDesc(_wlDesc1);
       wl1.setOwners(_owners);
       wl1.setAssignedTo(_acls1);
       
       _worklistID1 = _worklistService.createWorklist(wl1);
       
       WsSession.setSessionData(KEY_ACTOR, _user);
       
       assertTrue(_worklistID1 > 0);
       
	   resolveTaskAclsByActors();
   }
    
    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklistWithInvalidPrivilege() {

        TaskACL acl  = new TaskACL(false, true, false, false, _user1);
        TaskACL acl1 = new TaskACL(false, true, false, false, _user);
        
        Set<TaskACL> aclSet = new HashSet<TaskACL>(1);
        aclSet.add(acl);
        aclSet.add(acl1);
        TaskACLs acls = new TaskACLs(aclSet);

        long seed = System.currentTimeMillis();
        _wlName = "delete.test.2" + seed;
        _wlDesc = "delete.test.2" + seed;
        
       Worklist wl = new Worklist();
       wl.setName(_wlName);
       wl.setDesc(_wlDesc);
       wl.setOwners(_owners);
       wl.setAssignedTo(acls);
       
       _worklistID = _worklistService.createWorklist(wl);
       assertTrue(_worklistID > 0);
   }

   /**
    * This method is used to create a task.
    */
   public long createTask() {

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
       
       WsSession.setSessionData(KEY_ACTOR, _user);

       return _taskService.createTask(task);
   }
   
   /**
    * This method is used to create a task.
    */
   public long createTaskwithInvalidPrivilege() {

       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(_user1);
       task.setPriorityID(_priorityID);
       task.setStatusID(_statusID);
       task.setTaskName(_taskName);
       task.setTaskDescription(_taskDescription);
       task.setComments(_comments);
       task.setCanStartEarly(_canStartEarly);
       task.setStartDate(_startDate);
       task.setEndDate(_endDate);
       
       WsSession.setSessionData(KEY_ACTOR, _user1);
       //WsSession.setSessionData(TASK_ACLS, getUserTaskAcls());
       return _taskService.createTask(task);
   }
   
   private void resolveTaskAclsByActors() {

       _worklistService.resolveTaskAclsByActors(_users);
   }
   /**
    * This method is used to test a task to be deleted from a particular worklist
    * functionality.
    */
   public void testDeleteTasks() {

       long[] tasks = new long[] {_taskID, _taskID1};
       TaskListResult taskListResult = _taskService.deleteTasks(_worklistID, tasks);
       assertEquals(0, taskListResult.getIdListResult().getFailed().size());
   }

   /**
    * This method is used to test a task to be deleted from a particular worklist
    * functionality with non existing task ids.
    */
   public void testDeleteTasksWithNonExistingTasksIDs() {

       _taskID  = createTask();
       long[] tasks = new long[] {_taskID, NON_EXISTING_TASK_ID};

       TaskListResult taskListResult = _taskService.deleteTasks(_worklistID, tasks);

       assertEquals(1, taskListResult.getUnProcessedTasksList().size());
       assertEquals(NON_EXISTING_TASK_ID, taskListResult.getIdListResult().getFailedIDs().get(0));
       assertEquals(WorklistEC.EC_TASK_NOT_EXIST, taskListResult.getIdListResult()
                                                                .getErrorCodes().get(0));
   }

   /**
    * This method is used to test a task to be deleted from a particular worklist
    * functionality with non existing task ids in the given worklist.
    */
   public void testDeleteTasksWithTasksNotFoundInWorklist() {

       _taskID  = createTask();
       _taskID1 = createTask();
       long[] tasks = new long[] {_taskID, _taskID1};

       TaskListResult taskListResult = _taskService.deleteTasks(_worklistID1, tasks);

       assertEquals(2, taskListResult.getUnProcessedTasksList().size());
       assertEquals(WorklistEC.EC_TASK_NOT_FOUND_IN_WL, taskListResult.getIdListResult()
                                                                      .getErrorCodes().get(1));
   }
   
   /**
    * This method is used to test a task to be deleted from a particular worklist
    * functionality with non existing task ids in the given worklist.
    */
   public void testDeleteTasksWithInvalidPrivilege() {
       
       createWorklistWithInvalidPrivilege();
       
       _taskID  = createTask();
       
       WsSession.setSessionData(KEY_ACTOR, _user1);
       
       _taskID1 = createTaskwithInvalidPrivilege();
       long[] tasks = new long[] {_taskID, _taskID1};

       TaskListResult taskListResult = _taskService.deleteTasks(_worklistID, tasks);

       assertEquals(1, taskListResult.getUnProcessedTasksList().size());
       assertEquals(WorklistEC.EC_INVALID_ACTOR_FOR_OPER, taskListResult.getIdListResult()
                                                                      .getErrorCodes().get(0));
   }

   /**
    * This method is used to test a task to be deleted from a particular worklist
    * functionality with invalid task ids.
    */
   public void testDeleteTasksWithInvalidTasksIDs() {
       
       createWorklist();
       long[] tasks = new long[] {-1 , 0};
       TaskListResult taskListResult =  _taskService.deleteTasks(_worklistID, tasks);

       assertEquals(2, taskListResult.getUnProcessedTasksList().size());
   }
   
   /**
    * This method is used to test a task to be deleted from a particular worklist
    * functionality with non existing task ids.
    */
   public void testDeleteTasksWithNullTaskIDs() {
       try {

           long[] taskIDs = null;
           _taskService.deleteTasks(_worklistID, taskIDs);

           fail("Should have thrown EC_NULL_TASK_IDS exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_NULL_TASK_IDS, we.getErrorCode());
       }
   }

   /**
    * This method is used to test a task to be deleted from a particular worklist
    * functionality with a invalid worklist ID.
    */
   public void testDeleteTaskWithInvalidWLID() {

       try {

           long worklistID = -1;
           long[] taskIDs    = new long[] {_taskID, NON_EXISTING_TASK_ID};

           _taskService.deleteTasks(worklistID, taskIDs);
           fail("Should have thrown EC_INVALID_WORKLIST_ID exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_WORKLIST_ID, we.getErrorCode());
       }
   }
}
