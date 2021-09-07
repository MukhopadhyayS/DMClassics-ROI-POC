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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;
import com.mckesson.eig.workflow.worklist.api.Task;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskACLs;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.service.TaskService;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author Sahul Hameed Y
 * @date   Feb 23, 2008
 * @since  HECM 1.0; Feb 23, 2008
 */
public class TestUpdateTask 
extends AbstractWorkflowTestCase {
    
    private static WorklistService _worklistService;
    private static TaskService _taskService;
    private static final String KEY_ACTOR = "Key_Actor";
    private static final String TASK_ACLS = "Task_Acls";

    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_USER   = 3;

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
    private static TaskACL  _acl;
    private static TaskACL  _acl1;

    private static Actors   _owners;
    private static Actors   _users;
    private static TaskACLs _acls;
    private static TaskACLs _acls1;

    private static long     _taskID;
    private static long     _priorityID;
    private static String   _statusID;
    private static String   _taskName;
    private static String   _taskDescription;
    private static String   _comments;
    private static Date     _startDate;
    private static Date     _endDate;
    private static Date     _startDatetoUpdate;
    private static Date     _endDatetoUpdate;
    
    private static boolean  _canStartEarly;
    
    private static Task _task;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();
        _owner    = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
        _user     = new Actor(APP_ID, ET_USER,   (seed + 1));
        _user1    = new Actor(APP_ID, ET_USER,   (seed + 2));

        _wlName = "name.1." + seed;
        _wlDesc = "desc.1." + seed;

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);

        _acl  = new TaskACL(true, true, true, true, _user);

        Set<TaskACL> aclSet = new HashSet<TaskACL>(1);
        aclSet.add(_acl);
        _acls = new TaskACLs(aclSet);
        
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
        _taskName           = "Done" + seed;
        _taskDescription    = "follow certain principles" + seed;
        _comments           = "finish" + seed;
        _canStartEarly      = false;
        _startDate          = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 1).getTime();
        _endDate            = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY).getTime();
        
        _startDatetoUpdate  = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 2).getTime();
        _endDatetoUpdate    = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY + 2).getTime();
        
        _task = new Task();
        createWorklist();
    }
    
    private HashMap getTaskAcls() {
        
        TaskACL taskACL            = new TaskACL(true, true, true, true);
        HashMap<Long, TaskACL> map = new HashMap<Long, TaskACL>();
        map.put(_worklistID, taskACL);
        
        return map;
    }

    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklist() {

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

    
    public void createWorklistWithInvalidPrivilege() {
        
        _acl1  = new TaskACL(false, false, false, false, _user);
        
        long seed = System.currentTimeMillis();

        _wlName1 = "name.1." + seed;
        _wlDesc1 = "desc.1." + seed;
        
        Set<TaskACL> aclSet = new HashSet<TaskACL>(1);
        aclSet.add(_acl1);
        _acls1 = new TaskACLs(aclSet);
        
        Worklist wl = new Worklist();
        wl.setName(_wlName1);
        wl.setDesc(_wlDesc1);
        wl.setOwners(_owners);
        wl.setAssignedTo(_acls1);

        _worklistID1 = _worklistService.createWorklist(wl);
        assertTrue(_worklistID1 > 0);
		
        resolveTaskAclsByActors();
    }
    
   /**
    * This method is used to create a task.
    */
   public void testCreateTaskWithNewStatusID() {

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
    * This method is used to update a task.
    */
   public void testUpdateTask() {
       
       long[] taskIDs = new long[1];
       taskIDs[0]     = _taskID;
       
       _taskService.makeTasksEditable(_worklistID, taskIDs);
       _task = _taskService.getTaskDetails(_taskID);
       
       _task.setPriorityID(1);
       _task.setTaskName("new12");
       _task.setTaskDescription("taskDescription");
       _task.setComments("No wo");
       _task.setCanStartEarly(false);
       _task.setEndDate(_endDatetoUpdate);
       _task.setStartDate(_startDatetoUpdate);
       _task.setReassignReason("noprob");
       
       _taskService.updateTask(_task);
       Task task = _taskService.getTaskDetails(_task.getTaskID());
       assertEquals(_startDatetoUpdate , task.getStartDate());
   }
   
   /**
    * This method is used to update a task.
    */
   public void testUpdateTaskWithNewStatusID() {
       
       long[] taskIDs = new long[1];
       taskIDs[0]     = _taskID;
       
       _taskService.makeTasksEditable(_worklistID, taskIDs);
       _task = _taskService.getTaskDetails(_taskID);
       
       _task.setPriorityID(1);
       _task.setTaskName("new12");
       _task.setTaskDescription("taskDescription");
       _task.setComments("No wo");
       _task.setCanStartEarly(false);
       _task.setEndDate(_endDatetoUpdate);
       _task.setStartDate(_startDatetoUpdate);
       _task.setReassignReason("noprob");
       _task.setStatusID("new");
       
       _taskService.updateTask(_task);
       Task task = _taskService.getTaskDetails(_task.getTaskID());
       assertEquals(_startDatetoUpdate , task.getStartDate());
       assertEquals("new", task.getStatusID());
   }
   
   /**
    * This method is used to update a task with invalid privilege.
    */
   public void testUpdateTaskWithInvalidPrivilege() {
       
       createWorklistWithInvalidPrivilege();
       Task task = new Task();

       task.setWorklistID(_worklistID1);
       task.setTaskCreator(_user);
       task.setPriorityID(_priorityID);
       task.setStatusID("draft");
       task.setTaskName(_taskName);
       task.setTaskDescription(_taskDescription);
       task.setComments(_comments);
       task.setCanStartEarly(_canStartEarly);
       task.setStartDate(_startDate);
       task.setEndDate(_endDate);
       
       try {

           _taskID = _taskService.createTask(task);
           fail("Should have thrown EC_INVALID_PRIVILEGE exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INSUFFICIENT_PRIVILEGE, we.getErrorCode());
       }
   }
   
   /**
    * Test to update the task with invalid actor.
    */
   public void testUpdateTaskWithInvalidActor() {
       
       createWorklistWithInvalidPrivilege();
       WsSession.setSessionData(TASK_ACLS, getTaskAcls());
       WsSession.setSessionData(KEY_ACTOR, _user1);
       
       try {
           _taskService.updateTask(_task);
       } catch (WorkflowException e) {
           assertEquals(WorklistEC.EC_INVALID_ACTOR_FOR_OPER, e.getErrorCode());
       }
   }
   
   /**
    * Test update task with invalid task id
    */
   public void testUpdateTaskWithInvalidTaskID() {
       
       _task.setTaskID(Long.MAX_VALUE);
       _task.setStatusID("draft");
       
       try {
           _taskService.updateTask(_task);
       } catch (WorkflowException e) {
           assertEquals(WorklistEC.EC_TASK_NOT_EXIST, e.getErrorCode());
       }
   }
   
   /**
    * Test update task with with invalid status.
    */
   public void testUpdateTasksWithOtherStatus() {

       testCreateTaskWithNewStatusID();
       WsSession.setSessionData(KEY_ACTOR, _user);
       
       long[] taskIDs = new long[1];
       taskIDs[0] = _task.getTaskID(); 
       
       try {
           _taskService.updateTask(_task);
       } catch (WorkflowException e) {
           assertEquals(WorklistEC.EC_INVALID_TASK_STATUS, e.getErrorCode());
       }
   }
}
