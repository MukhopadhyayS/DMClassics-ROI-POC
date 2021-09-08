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
import com.mckesson.eig.workflow.api.Attribute;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.worklist.api.AssignedTasksCriteria;
import com.mckesson.eig.workflow.worklist.api.Task;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskACLs;
import com.mckesson.eig.workflow.worklist.api.TaskList;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.service.TaskService;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author sahuly
 * @date   Dec 4, 2007
 * @since  HECM 1.0
 */
public class TestGetAssignedTasks
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

    private static AssignedTasksCriteria _assignedTaskCriteria;
    private static final String[] STATUS_ID =
        new String[] {"draft", "in progress", "new", "completed"};

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();
        _owner    = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
        _user     = new Actor(APP_ID, ET_USER,   (seed + 1));

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

        createWorklist();
        createTask();

        _assignedTaskCriteria = new AssignedTasksCriteria(_worklistID, STATUS_ID);
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
       
	   resolveTaskAclsByActors();
   }
    
   private void resolveTaskAclsByActors() {
       _worklistService.resolveTaskAclsByActors(_users);
   }

    /**
     * This method is used to test get assigned worklist tasks.
     */
    public void testGetAssignedWorklistTasks() {

       SortOrder sortOrder = new SortOrder(new Attribute("endDate"));
       sortOrder.setIsDesc(true);
       final int startValue = 0;
       final int count = 10;

       TaskList taskList = _taskService.getAssignedTasks(_assignedTaskCriteria,
                                                             startValue,
                                                             count,
                                                             sortOrder);
       List tasks = taskList.getTasks();
       assertEquals(1, tasks.size());
    }

    /**
     * This method is used to test get assigned worklist tasks.
     */
    public void testGetAssignedWorklistWithStatusIDSortOrderTasks() {

       SortOrder sortOrder = new SortOrder(new Attribute("statusID"));
       sortOrder.setIsDesc(true);
       final int startValue = 0;
       final int count = 10;

       TaskList taskList = _taskService.getAssignedTasks(_assignedTaskCriteria,
                                                             startValue,
                                                             count,
                                                             sortOrder);
       List tasks = taskList.getTasks();
       assertEquals(1, tasks.size());
    }

    /**
     * This method is used to test get assigned worklist tasks.
     */
    public void testGetAssignedWorklistwithOtherTasks() {

       SortOrder sortOrder = new SortOrder(new Attribute("taskName"));
       sortOrder.setIsDesc(true);
       final int startValue = 0;
       final int count = 10;

       TaskList taskList = _taskService.getAssignedTasks(_assignedTaskCriteria,
                                                             startValue,
                                                             count,
                                                             sortOrder);
       List tasks = taskList.getTasks();
       assertEquals(1, tasks.size());
    }

    /**
     * This method is used to test get assigned worklist tasks with
     * null sort order.
     */
    public void testGetAssignedWorklistTaskswithNullSortOrder() {

       final int startValue = 0;
       final int count = 10;
       SortOrder sortOrder = new SortOrder(new Attribute("taskName"));
       TaskList taskList = _taskService.getAssignedTasks(_assignedTaskCriteria,
                                                             startValue,
                                                             count,
                                                             sortOrder);
       List tasks = taskList.getTasks();
       assertEquals(1, tasks.size());
    }

    /**
     * This method is used to test get assigned worklist tasks
     * with non existing worklist ID.
     */
    public void testGetAssignedWorklistTaskswithNonExistingWorklistID() {

        SortOrder sortOrder = new SortOrder(new Attribute("taskName"));
        final int startValue = 0;
        final int count = 5;

        AssignedTasksCriteria assignedTasksCriteria =
            new AssignedTasksCriteria(Long.MAX_VALUE, STATUS_ID);

        try {
            _taskService.getAssignedTasks(assignedTasksCriteria, startValue, count, sortOrder);
        } catch (WorklistException e) {
            assertEquals(WorklistEC.EC_WL_NT_AVAILABLE, e.getErrorCode());
        }
    }

    /**
     * This method is used to test get assigned worklist tasks
     * for invalid worklist ID.
     */
    public void testGetAssignedWorklistTaskswithInvalidWorklistID() {

       try {

           SortOrder sortOrder = new SortOrder(new Attribute("taskName"));
           final int startValue = 0;
           final int count = 5;

           AssignedTasksCriteria assignedTasksCriteria =
               new AssignedTasksCriteria(-1, STATUS_ID);

           _taskService.getAssignedTasks(assignedTasksCriteria,
                                                                 startValue,
                                                                 count,
                                                                 sortOrder);
           fail("Should have thrown EC_INVALID_WORKLIST_ID exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_WORKLIST_ID, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigned worklist tasks
     * with non existing status ID.
     */
    public void testGetAssignedWorklistTaskswithNonExistingStatusID() {

       final int startValue = 0;
       final int count = 10;
       long worklistID = 1;
       String[] nonExistingstatusIds = new String[] {"invalid"};
       SortOrder sortOrder = new SortOrder(new Attribute("taskName"));

       AssignedTasksCriteria assignedTasksCriteria =
           new AssignedTasksCriteria(worklistID, nonExistingstatusIds);

       TaskList taskList = _taskService.getAssignedTasks(assignedTasksCriteria,
                                                             startValue,
                                                             count,
                                                             sortOrder);

       assertEquals(0, taskList.getTasks().size());
    }

    /**
     * This method is used to test get assigned worklist tasks
     * with empty status ID.
     */
    public void testGetAssignedWorklistTaskswithEmptyStatusID() {

       final int startValue = 0;
       final int count = 10;
       long worklistID = 1;
       String[] emptyStatusIds = new String[] {};
       SortOrder sortOrder = new SortOrder(new Attribute("priorityID"));

       AssignedTasksCriteria assignedTasksCriteria =
           new AssignedTasksCriteria(worklistID, emptyStatusIds);

       try {

            _taskService.getAssignedTasks(assignedTasksCriteria,
                                          startValue,
                                          count,
                                          sortOrder);
           fail("Should have thrown EC_EMPTY_STATUS_ID exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_EMPTY_STATUS_ID, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigend worklist tasks functionality
     * with negative start value.
     */
    public void testGetAssignedWorklistTasksWithNegativeStart() {

       try {

           SortOrder order = new SortOrder(new Attribute("taskName"));
           final int negativeStartValue = -1;
           final int validCount = 5;
           _taskService.getAssignedTasks(_assignedTaskCriteria,
                                             negativeStartValue,
                                             validCount,
                                             order);

           fail("Should have thrown EC_INVALID_START_COUNT exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigned worklist tasks functionality
     * with zero count value.
     */
    public void testGetAssignedWorklistTasksWithZeroCount() {

       try {

           SortOrder order = new SortOrder(new Attribute("taskName"));
           final int validStartValue = 0;
           final int zeroCount = 0;
           _taskService.getAssignedTasks(_assignedTaskCriteria,
                                             validStartValue,
                                             zeroCount,
                                             order);
           fail("Should have thrown EC_INVALID_START_COUNT exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigned worklist tasks functionality
     * with negative start and negative count value.
     */
    public void testGetAssignedWorklistTasksWithNegativeStartandNegativeCount() {

       try {

           SortOrder order = new SortOrder(new Attribute("taskName"));
           final int validStartValue = -1;
           final int zeroCount = -5;

           _taskService.getAssignedTasks(_assignedTaskCriteria,
                                             validStartValue,
                                             zeroCount,
                                             order);
           fail("Should have thrown EC_INVALID_START_COUNT exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigned worklist tasks functionality
     * with null status ID.
     */
    public void testGetAssignedWorklistTasksWithNullStatusID() {

       try {

           final int validStartValue = 1;
           final int zeroCount        = 5;
           AssignedTasksCriteria assignedTasksCriteria = new AssignedTasksCriteria(1, null);
           SortOrder order = new SortOrder(new Attribute("statusID"));

           _taskService.getAssignedTasks(assignedTasksCriteria,
                                             validStartValue,
                                             zeroCount,
                                             order);
           fail("Should have thrown EC_NULL_STATUS_ID exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_NULL_STATUS_ID, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigned tasks functionality with null criteria.
     */
    public void testGetAssignedTasksWithNullCriteria() {

        try {

            SortOrder order = new SortOrder(new Attribute("statusID"));
            final int validStartValue = 0;
            final int validCount = 5;

            _taskService.getAssignedTasks(null, validStartValue, validCount, order);

            fail("Should have thrown EC_NULL_CRITERIA exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_NULL_CRITERIA, we.getErrorCode());
        }
    }

    public void testGetAssignedTasksInfo() {

        SortOrder sortOrder = new SortOrder(new Attribute("priorityID"));
        final int startValue = 0;
        final int count = 10;

        TaskList taskList = _taskService.getAssignedTasks(_assignedTaskCriteria,
                                                          startValue,
                                                          count,
                                                          sortOrder);
        List tasks = taskList.getTasks();
        assertEquals(1, tasks.size());

        for (int i = 0; i < tasks.size(); i++) {

            Task task = (Task) tasks.get(i);
            assertEquals(task.getPriorityID(),      task.getPriorityID());
            assertEquals(task.getTaskID(),          task.getTaskID());
            assertEquals(task.getWorklistID(),      task.getWorklistID());
            assertEquals(task.getComments(),        task.getComments());
            assertEquals(task.getEndDate(),         task.getEndDate());
            assertEquals(task.getReassignReason(),  task.getReassignReason());
            assertEquals(task.isCanStartEarly(),    task.isCanStartEarly());
            assertEquals(task.getStartDate(),       task.getStartDate());
            assertEquals(task.getStatusID(),        task.getStatusID());
            assertEquals(task.getTaskDescription(), task.getTaskDescription());
            assertEquals(task.getTaskName(),        task.getTaskName());
        }
    }
}
