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
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.Attribute;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.worklist.api.CreatedTasksCriteria;
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
public class TestGetCreatedTasks
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
    
    private static CreatedTasksCriteria _createTasksCriteria;
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
        
        _createTasksCriteria = new CreatedTasksCriteria(_worklistID, STATUS_ID, _user);
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
   }
       
    /**
     * This method is used to test get created tasks functionality with a non
     * existing actor.
     */
    public void testGetCreatedTasks() {

        final int startValue = 0;
        final int validCount = 10;

        SortOrder sortOrder = new SortOrder(new Attribute("priorityID"));
        TaskList tl = _taskService.getCreatedTasks(_createTasksCriteria,
                                               startValue,
                                               validCount,
                                               sortOrder);
        assertEquals(1, tl.getTasks().size());
    }
    
    /**
     * This method is used to test get created tasks functionality with a non
     * existing actor.
     */
    public void testGetCreatedTasksWithStatusIDSortOrder() {

        final int startValue = 0;
        final int validCount = 10;

        SortOrder sortOrder = new SortOrder(new Attribute("statusID"));
        TaskList tl = _taskService.getCreatedTasks(_createTasksCriteria,
                                               startValue,
                                               validCount,
                                               sortOrder);
        assertEquals(1, tl.getTasks().size());
    }
    
    /**
     * This method is used to test get created tasks functionality with a non
     * existing actor.
     */
    public void testGetCreatedTasksWithOtherSortOrder() {

        final int startValue = 0;
        final int validCount = 10;

        SortOrder sortOrder = new SortOrder(new Attribute("taskName"));
        TaskList tl = _taskService.getCreatedTasks(_createTasksCriteria,
                                               startValue,
                                               validCount,
                                               sortOrder);
        assertEquals(1, tl.getTasks().size());
    }

    /**
     * This method is used to test get created tasks functionality with a non
     * existing sort order attribute.
     */
    public void testGetCreatedTasksWithNonExistingSortOrderAttribute() {

        final int startValue = 0;
        final int validCount = 10;
        SortOrder order = new SortOrder(new Attribute("taskN"));

        try {
            _taskService.getCreatedTasks(_createTasksCriteria,
                                         startValue,
                                         validCount,
                                         order);
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_SORTORDER, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get created tasks functionality with a
     * priorityID as sort order attribute.
     */
    public void testGetCreatedTasksWithPrioritySortOrderAttribute() {

        final int startValue = 0;
        final int validCount = 10;
        SortOrder order = new SortOrder(new Attribute("priorityID"));
        order.setIsDesc(true);

        TaskList tl = _taskService.getCreatedTasks(_createTasksCriteria,
                                               startValue,
                                               validCount,
                                               order);
        assertEquals(1, tl.getTasks().size());
    }

    /**
     * This method is used to test get created tasks functionality with a non
     * existing actor.
     */
    public void testGetCreatedTasksForNonExistingActor() {

        final int startValue = 0;
        final int validCount = 10;
        final long entityType = System.currentTimeMillis();

        Actor actor = new Actor(APP_ID, ET_USER, entityType);
        CreatedTasksCriteria createTasksCriteria =
                        new CreatedTasksCriteria(_worklistID, STATUS_ID, actor);

        TaskList tl = _taskService.getCreatedTasks(createTasksCriteria,
                                               startValue,
                                               validCount,
                                               new SortOrder(new Attribute("statusID")));
        assertEquals(0, tl.getTasks().size());
    }

    /**
     * This method is used to test get created Tasks functionality with null actor.
     */
    public void testGetCreatedTasksWithNullActor() {

        try {

            SortOrder order = new SortOrder(new Attribute("taskName"));
            final int validStartValue = 0;
            final int validCount = 10;

            CreatedTasksCriteria createTasksCriteria =
                                    new CreatedTasksCriteria(_worklistID, STATUS_ID, null);

            _taskService.getCreatedTasks(createTasksCriteria, validStartValue, validCount, order);

            fail("Should have thrown EC_NULL_ACTOR exception");
        } catch (WorklistException we) {
            assertEquals(WorkflowEC.EC_NULL_ACTOR, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get created tasks functionality
     * with negative start value.
     */
    public void testGetCreatedTasksWithNegativeStart() {

        try {

            SortOrder order = new SortOrder(new Attribute("statusID"));
            final int negativeStartValue = -1;
            final int validCount = 10;

            _taskService.getCreatedTasks(_createTasksCriteria, 
                                         negativeStartValue, 
                                         validCount, 
                                         order);
            fail("Should have thrown EC_INVALID_START_COUNT exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get created tasks functionality
     * with zero count value.
     */
    public void testGetCreatedTasksWithZeroCount() {

       try {

           SortOrder order = new SortOrder(new Attribute("statusID"));
           final int validStartValue = 0;
           final int zeroCount = 0;

           _taskService.getCreatedTasks(_createTasksCriteria, validStartValue, zeroCount, order);

           fail("Should have thrown EC_INVALID_START_COUNT exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get created tasks functionality
     * with negative count value.
     */
    public void testGetCreatedTasksWithNegativeCount() {

       try {

           SortOrder order = new SortOrder(new Attribute("statusID"));
           final int validStartValue = 2;
           final int negativeCount = -5;

           _taskService.getCreatedTasks(_createTasksCriteria, 
                                        validStartValue, 
                                        negativeCount, 
                                        order);

           fail("Should have thrown EC_INVALID_START_COUNT exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get created tasks functionality with
     * invalid  Worklist ID.
     */
    public void testGetCreatedTasksWithInvalidWorkListID() {

       try {

           final long invalidWorklistID = -5;
           SortOrder order = new SortOrder(new Attribute("statusID"));
           final int validStartValue = 2;
           final int count = 5;

           CreatedTasksCriteria createdTasksCriteria =
                            new CreatedTasksCriteria(invalidWorklistID,
                                                     STATUS_ID,
                                                     _user);
           _taskService.getCreatedTasks(createdTasksCriteria,
                                    validStartValue,
                                    count,
                                    order);

           fail("Should have thrown EC_INVALID_WORKLIST_ID exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_WORKLIST_ID,
               we.getErrorCode());
       }
    }
    
    /**
     * This method is used to test get created tasks functionality with null criteria.
     */
    public void testGetCreatedTasksWithNullCriteria() {

        try {

            SortOrder order = new SortOrder(new Attribute("statusID"));
            final int validStartValue = 0;
            final int validCount = 5;

            _taskService.getCreatedTasks(null, validStartValue, validCount, order);

            fail("Should have thrown EC_NULL_CRITERIA exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_NULL_CRITERIA, we.getErrorCode());
        }
    }
    
    /**
     * This method is used to test get created tasks functionality with invalid actor.
     */
    public void testGetCreatedTasksWithInvalidActor() {

        try {
            
            Actor user = new Actor(APP_ID, ET_USER, -1);
            CreatedTasksCriteria createTasksCriteria 
                     = new CreatedTasksCriteria(_worklistID, STATUS_ID, user);
            
            SortOrder order = new SortOrder(new Attribute("statusID"));
            final int validStartValue = 0;
            final int validCount = 5;

            _taskService.getCreatedTasks(createTasksCriteria, validStartValue, validCount, order);

            fail("Should have thrown EC_INVALID_ACTOR exception");
        } catch (WorklistException we) {
            assertEquals(WorkflowEC.EC_INVALID_ACTOR, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get created tasks functionality with non
     * existing Worklist ID.
     */
    public void testGetCreatedTasksWithNonExistingWorkListID() {

       final long nonExistingWorklistID = Long.MAX_VALUE;
       SortOrder order = new SortOrder(new Attribute("statusID"));
       final int validStartValue = 2;
       final int count = 10;

       CreatedTasksCriteria createdTasksCriteria =
                        new CreatedTasksCriteria(nonExistingWorklistID,
                                                 STATUS_ID,
                                                 _user);
       try {
           _taskService.getCreatedTasks(createdTasksCriteria, validStartValue, count, order);
       } catch (WorklistException e) {
           assertEquals(WorklistEC.EC_WL_NT_AVAILABLE, e.getErrorCode());
       }
    }
}
