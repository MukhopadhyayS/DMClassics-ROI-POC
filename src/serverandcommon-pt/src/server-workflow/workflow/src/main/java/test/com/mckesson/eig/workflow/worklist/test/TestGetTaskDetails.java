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
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.service.TaskService;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author sahuly
 * @date   Dec 20, 2007
 * @since  HECM 1.0; Dec 20, 2007
 */
public class TestGetTaskDetails
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

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

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();
        _owner    = new Actor(APP_ID, ET_DOMAIN, (seed + 2));
        _user     = new Actor(APP_ID, ET_USER,   (seed + 2));

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user.getEntityID());
        WsSession.setSessionData(KEY_ACTOR, _user);
        
        Set<Actor> acts = new HashSet<Actor>();
        acts.add(_user);
        
        _users = new Actors();
        _users.setActors(acts);
        
        _worklistService = (WorklistService) getManager(WORKLIST_MANAGER);
        _taskService     = (TaskService)     getManager(TASK_MANAGER);

        _priorityID         = 2;
        _statusID           = "new";
        _taskName           = "new task" + seed;
        _taskDescription    = "complete tasks" + seed;
        _comments           = "completed tasks" + seed;
        _canStartEarly      = false;
        _startDate          = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 1).getTime();
        _endDate            = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY).getTime();
        createWorklist();
        createTask();

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
    * This method is used to test the get task details functionality for the given taskID
    */
   public void testgetTaskDetails() {

       Task task = _taskService.getTaskDetails(_taskID);
       assertEquals(_taskID, task.getTaskID());
       assertEquals(_priorityID, task.getPriorityID());
   }

   /**
    * This method is used to test the get task details functionality for non existing task ID
    */
   public void testgetTaskDetailsForNonExistingTaskID() {

       long taskID = Long.MAX_VALUE;

       try {

           _taskService.getTaskDetails(taskID);
           fail("Should have thrown EC_TASK_NOT_EXIST exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_TASK_NOT_EXIST, we.getErrorCode());
       }
   }

   /**
    * This method is used to test the get task details functionality for invalid task ID
    */
   public void testgetTaskDetailsForInvalidTaskID() {

       long taskID = -1;

       try {

           _taskService.getTaskDetails(taskID);
           fail("Should have thrown EC_INVALID_TASK_ID exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_TASK_ID, we.getErrorCode());
       }
   }
}
