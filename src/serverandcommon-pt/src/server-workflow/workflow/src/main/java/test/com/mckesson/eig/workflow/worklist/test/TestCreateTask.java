/*
 * Copyright 2007-2009 McKesson Corporation and/or one of its subsidiaries.
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
import java.util.HashSet;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.WorkflowEC;
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
 * @date   Dec 12, 2007
 * @since  HECM 1.0; Dec 12, 2007
 */
public class TestCreateTask
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static WorklistService _worklistService;
    private static TaskService _taskService;
    private static final String KEY_ACTOR = "Key_Actor";
    private static final String KEY_ACTORS = "Actors";
    
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
        WsSession.setSessionData(KEY_ACTORS, _users);

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
     * This method is used to test create worklist functionality.
     */
    public void createPersonalWorklist() {

       _acl = new TaskACL(false, false, false, false, _owner);
       Set<TaskACL> aclSet = new HashSet<TaskACL>(1);
       aclSet.add(_acl);
       _acls = new TaskACLs(aclSet);
       
       Worklist wl = new Worklist();
       _wlName = _wlName + "Personal";
       wl.setName(_wlName);
       wl.setDesc(_wlDesc);
       wl.setOwners(_owners);
       wl.setAssignedTo(_acls);

       _worklistID = _worklistService.createWorklist(wl);
       assertTrue(_worklistID > 0);
       
       resolveTaskAclsByActors();
   }
    
    public void createWorklistWithInvalidPrivilege() {
        
        long seed = System.currentTimeMillis();

        _wlName1 = "name.1." + seed;
        _wlDesc1 = "desc.1." + seed;
        
        Worklist wl = new Worklist();
        wl.setName(_wlName1);
        wl.setDesc(_wlDesc1);
        wl.setOwners(_owners);
        
        _worklistID1 = _worklistService.createWorklist(wl);
        assertTrue(_worklistID1 > 0);
        
        resolveTaskAclsByActors();
    }
    
    private void resolveTaskAclsByActors() {

        //WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        _worklistService.resolveTaskAclsByActors(_users);
    }

   /**
    * This method is used to create a task.
    */
   public void testCreateTaskWithNewStatusID() {

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
    * This method is used to create a task with draft statusID.
    */
   public void testCreateTaskWithDraftStatusID() {

       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(_user);
       task.setPriorityID(_priorityID);
       task.setStatusID("draft");
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
    * This method is used to create a task with draft statusID.
    */
   public void testCreateTaskWithInvalidPrivilege() {
       
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
    * This method is used to test the create task functionality with
    * start date after end date.
    */
   public void testCreateTaskWithEndDateBeforeStartDate() {

       final int year = 2008;
       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(_user);
       task.setPriorityID(_priorityID);
       task.setStatusID("draft");
       task.setTaskName(_taskName);
       task.setTaskDescription(_taskDescription);
       task.setComments(_comments);
       task.setCanStartEarly(_canStartEarly);
       task.setStartDate(new GregorianCalendar(year, Calendar.JANUARY, DAY).getTime());
       task.setEndDate(new GregorianCalendar(year, Calendar.JANUARY, DAY - 1).getTime());

       try {

           _taskID = _taskService.createTask(task);
           fail("Should have thrown EC_INVALID_START_AND_END_DATE exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_START_AND_END_DATE, we.getErrorCode());
       }
   }

   /**
    * This method is used to test the create task functionality with
    * start date after end date.
    */
   public void testCreateTaskWithStartDateAfterEndDate() {

       final int year = 2008;
       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(_user);
       task.setPriorityID(_priorityID);
       task.setStatusID("draft");
       task.setTaskName(_taskName);
       task.setTaskDescription(_taskDescription);
       task.setComments(_comments);
       task.setCanStartEarly(_canStartEarly);
       task.setStartDate(new GregorianCalendar(year, Calendar.JANUARY, DAY + 1).getTime());
       task.setEndDate(new GregorianCalendar(year, Calendar.JANUARY, DAY).getTime());

       try {

           _taskID = _taskService.createTask(task);
           fail("Should have thrown EC_INVALID_START_AND_END_DATE exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_START_AND_END_DATE, we.getErrorCode());
       }
   }

   /**
    * This method is used to test the create task functionality with
    * non existing worklistID.
    */
   public void testCreateTaskWithNonExistingWorklistID() {

       try {

           Task task = new Task();

           task.setWorklistID(Long.MAX_VALUE);
           task.setTaskCreator(_user);
           task.setPriorityID(_priorityID);
           task.setStatusID("draft");
           task.setTaskName(_taskName);
           task.setTaskDescription(_taskDescription);
           task.setComments(_comments);
           task.setCanStartEarly(_canStartEarly);
           task.setStartDate(_startDate);
           task.setEndDate(_endDate);

           _taskID = _taskService.createTask(task);
           fail("Should have thrown EC_WL_NT_AVAILABLE exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_WL_NT_AVAILABLE, we.getErrorCode());
       }
   }

   /**
    * This method is used to test the create task functionality with invalid actor
    */
   public void testCreateTaskWithInvalidWorklistID() {

       Task task = new Task();

       task.setWorklistID(-1);
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
           fail("Should have thrown EC_INVALID_WORKLIST_ID exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_WORKLIST_ID, we.getErrorCode());
       }
   }

   /**
    * This method is used to test the create task functionality with invalid actor
    */
   public void testCreateTaskWithInvalidActor() {

       Actor actor = new Actor(APP_ID, ET_USER, -1);

       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(actor);
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
           fail("Should have thrown EC_INVALID_ACTOR exception");
       } catch (WorklistException we) {
           assertEquals(WorkflowEC.EC_INVALID_ACTOR, we.getErrorCode());
       }
   }

   /**
    * This method is used to test the create task  functionality with null actor
    */
   public void testCreateTaskWithNullActor() {

       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(null);
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
           fail("Should have thrown EC_NULL_ACTOR exception");
       } catch (WorklistException we) {
           assertEquals(WorkflowEC.EC_NULL_ACTOR, we.getErrorCode());
       }
   }

   /**
    * This method is used to test the create task
    * functionality with invalid task description length
    */
   public void testCreateTaskWithInvalidNameLength() {

       final String taskNameMaxLength =
           "Length of the comments has to exceed more than 256 characters";

       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(_user);
       task.setPriorityID(_priorityID);
       task.setStatusID("draft");
       task.setTaskName(_taskDescription + taskNameMaxLength + taskNameMaxLength
                                         + taskNameMaxLength + taskNameMaxLength);
       task.setTaskDescription(_taskDescription);
       task.setComments(_comments);

       task.setCanStartEarly(_canStartEarly);
       task.setStartDate(_startDate);
       task.setEndDate(_endDate);

       try {

           _taskID = _taskService.createTask(task);
           fail("Should have thrown EC_INVALID_TASK_NAME_LEN exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_TASK_NAME_LEN, we.getErrorCode());
       }
   }

   /**
    * This method is used to test the create task
    * functionality with invalid task description length
    */
   public void testCreateTaskWithInvalidDescriptionLength() {

       final String descMaxLength =
           "Length of the comments has to exceed more than 256 characters";

       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(_user);
       task.setPriorityID(_priorityID);
       task.setStatusID("draft");
       task.setTaskName(_taskName);
       task.setComments(_comments);
       task.setTaskDescription(_taskDescription + descMaxLength + descMaxLength + descMaxLength
                                                + descMaxLength + descMaxLength + descMaxLength);

       task.setCanStartEarly(_canStartEarly);
       task.setStartDate(_startDate);
       task.setEndDate(_endDate);

       try {

           _taskID = _taskService.createTask(task);
           fail("Should have thrown EC_INVALID_TASK_DESC_LEN exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_TASK_DESC_LEN, we.getErrorCode());
       }
   }

   /**
    * This method is used to test the create task
    * functionality with invalid comments length
    */
   public void testCreateTaskWithInvalidCommentsLength() {

       final String commentsMaxLength =
           "Length of the comments has to exceed more than 256 characters";

       Task task = new Task();

       task.setWorklistID(_worklistID);
       task.setTaskCreator(_user);
       task.setPriorityID(_priorityID);
       task.setStatusID("draft");
       task.setTaskName(_taskName);
       task.setTaskDescription(_taskDescription);
       task.setComments(_comments + commentsMaxLength + commentsMaxLength + commentsMaxLength
                                  + commentsMaxLength + commentsMaxLength + commentsMaxLength);

       task.setCanStartEarly(_canStartEarly);
       task.setStartDate(_startDate);
       task.setEndDate(_endDate);

       try {

           _taskID = _taskService.createTask(task);
           fail("Should have thrown EC_INVALID_TASK_COMMENTS_LEN exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_TASK_COMMENTS_LEN, we.getErrorCode());
       }
   }

   /**
    * This method is used to create a task with null task .
    */
   public void testCreatetaskWithNullTask() {

       Task task = null;

       try {
           _taskID = _taskService.createTask(task);
           fail("Should have thrown EC_MSG_NULL_TASK exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_NULL_TASK, we.getErrorCode());
       }
   }
   
   /**
    * This method is used to create a task.
    */
   public void testCreateTaskInPersonalWorklist() {

       createPersonalWorklist();
       
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

}
