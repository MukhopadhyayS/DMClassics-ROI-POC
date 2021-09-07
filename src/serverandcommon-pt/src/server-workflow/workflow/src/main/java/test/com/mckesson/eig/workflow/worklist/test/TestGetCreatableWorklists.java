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
import com.mckesson.eig.workflow.worklist.api.CreateWLCriteria;
import com.mckesson.eig.workflow.worklist.api.ListWorklist;
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
 * @date   Dec 5, 2007
 * @since  HECM 1.0
 */
public class TestGetCreatableWorklists
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

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
    private static long     _worklistID2;
    private static String   _wlName;
    private static String   _wlDesc;

    private static Actor _domain1;
    private static Actor _user1;
    private static Actor _user2;
    private static TaskACL  _acl;
    private static TaskACL  _acl1;

    private static Actor _owner;

    private static Actors _owners;
    private static Actors _users;
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
    private static boolean  _canStartEarly;

    private static CreateWLCriteria _createWLCriteria;

    public void setUp() {

        init();

        long seed = System.currentTimeMillis();
        _domain1 = new Actor(APP_ID, ET_DOMAIN, seed);
        _owner = _domain1;

        _user1 = new Actor(APP_ID, ET_USER, seed);

        _wlName = "name.1." + seed;
        _wlDesc = "desc.1." + seed;

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);

        _owners = new Actors(set);

        _acl  = new TaskACL(true, true, true, true, _user1);
        _acl1 = new TaskACL(true, true, false, true, _user1);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user1.getEntityID());
        WsSession.setSessionData(KEY_ACTOR, _user1);

        Set<TaskACL> aclSet = new HashSet<TaskACL>(1);
        aclSet.add(_acl);
        _acls = new TaskACLs(aclSet);

        Set<TaskACL> acl1Set = new HashSet<TaskACL>(1);
        acl1Set.add(_acl1);
        _acls1 = new TaskACLs(acl1Set);

        Set<Actor> userSet = new HashSet<Actor>(1);
        userSet.add(_user1);

        _users = new Actors(userSet);

        _priorityID         = 2;
        _statusID           = "new";
        _taskName           = "Done" + seed;
        _taskDescription    = "follow certain principles" + seed;
        _comments           = "finish" + seed;
        _canStartEarly      = false;
        _startDate          = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 1).getTime();
        _endDate            = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY).getTime();

        _worklistService    = (WorklistService) getManager(WORKLIST_MANAGER);
        _taskService        = (TaskService)     getManager(TASK_MANAGER);

        createWorklists();
        createPersonalWorklist();
        createTask(_worklistID);

        _createWLCriteria = new CreateWLCriteria(_owners, _users, true);
    }
    
    public void createPersonalWorklist() {
        
        _user2 = new Actor(1, ET_USER, System.currentTimeMillis());
        TaskACL taskACL = new TaskACL(true, false, false, false, _user2);
        
        HashSet<Actor> actorSet = new HashSet<Actor>();
        actorSet.add(_user2);
        
        HashSet<TaskACL> aclSet = new HashSet<TaskACL>();
        aclSet.add(taskACL);
        
        TaskACLs owners = new TaskACLs(aclSet);
        Actors actors = new Actors(actorSet);
        
        Worklist wl1 = new Worklist();
        wl1.setName("My Worklist");
        wl1.setDesc("Personal Worklist");
        wl1.setOwners(actors);
        wl1.setAssignedTo(owners);
        
        _worklistID2 = _worklistService.createWorklist(wl1);
        assertTrue(_worklistID2 > 0);
        createTask(_worklistID2);
    }

    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklists() {

       Worklist wl = new Worklist();
       wl.setName(_wlName);
       wl.setDesc(_wlDesc);
       wl.setOwners(_owners);
       wl.setAssignedTo(_acls);

       _worklistID = _worklistService.createWorklist(wl);
       assertTrue(_worklistID > 0);

       Worklist wl1 = new Worklist();
       wl1.setName(_wlName + _wlName);
       wl1.setDesc(_wlDesc + _wlDesc);
       wl1.setOwners(_owners);
       wl1.setAssignedTo(_acls1);

       _worklistID1 = _worklistService.createWorklist(wl1);
       assertTrue(_worklistID1 > 0);

       resolveTaskAclsByActors();
   }

   /**
    * This method is used to create a task.
    */
   public void createTask(long worklistID) {

       Task task = new Task();

       task.setWorklistID(worklistID);
       task.setTaskCreator(_user1);
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
   
   
   
   private void resolveTaskAclsByActors() {
	   WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
       _worklistService.resolveTaskAclsByActors(_users);
   }

    /**
     * This method is used to test get creatable worklists with show
     * empty worklist functionality
     */
    public void testGetCreatableWorklistWithShowEmptyWorklist() {

        SortOrder order = new SortOrder(new Attribute("name"));
        final int startIndex = 0;
        final int count = 5;
        final int size = 3;

        ListWorklist listWorklist = _worklistService.getCreatableWorklists(_createWLCriteria,
                                                                           startIndex,
                                                                           count,
                                                                           order);
        assertEquals(size, listWorklist.getWorklists().size());
    }

    /**
     * This method is used to test get creatable worklists
     * without show empty worklist functionality
     */
    public void testGetCreatableWorklistWithoutShowEmptyWorklist() {

        SortOrder order = new SortOrder(new Attribute("name"));
        final int startIndex = 0;
        final int count = 5;
        CreateWLCriteria createWLCriteria = new CreateWLCriteria(_owners, _users, false);

        
        ListWorklist listWorklist = _worklistService.getCreatableWorklists(createWLCriteria,
                                                                           startIndex,
                                                                           count,
                                                                           order);
        assertEquals(2, listWorklist.getWorklists().size());
    }

    /**
     * This method is used to test get creatable worklists functionality woth null sort order
     */
    public void testGetCreatableWorklistWithNullSortOrder() {

        final int startIndex = 0;
        final int count = 5;

        try {
            _worklistService.getCreatableWorklists(_createWLCriteria,
                                                   startIndex,
                                                   count,
                                                   null);
        } catch (WorklistException e) {
            assertEquals(WorklistEC.EC_INVALID_SORTORDER, e.getErrorCode());
        }
    }

    /**
     * This method is used to test get creatable worklists functionality with
     * non existing actors.
     */
    public void testGetCreatableWorklistWithNonExistingActor() {

        long seed = System.currentTimeMillis();

        Actor domain = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
        Set<Actor> domainSet = new HashSet<Actor>();
        domainSet.add(domain);
        Actors owners = new Actors(domainSet);

        Actor createdBy = new Actor(APP_ID, ET_USER, (seed + 2));
        Set<Actor> actorSet = new HashSet<Actor>();
        actorSet.add(createdBy);
        Actors creators = new Actors(actorSet);

        CreateWLCriteria createWLCriteria = new CreateWLCriteria(owners, creators, true);
        SortOrder sortOrder = new SortOrder(new Attribute("name"));
        final int startValue = 0;
        final int validCount = 5;

        ListWorklist wl = _worklistService.getCreatableWorklists(createWLCriteria,
                                                                 startValue,
                                                                 validCount,
                                                                 sortOrder);
        assertEquals(0, wl.getWorklists().size());
    }

    /**
     * This method is used to test get creatable worklists functionality with negative
     * start value.
     */
    public void testGetCreatableWorklistsWithNegativeStart() {

        try {

            SortOrder order = new SortOrder(new Attribute("name"));
            final int negativeStartValue = -1;
            final int validCount = 5;

            _worklistService.getCreatableWorklists(_createWLCriteria,
                                                   negativeStartValue,
                                                   validCount,
                                                   order);

            fail("Should have thrown EC_INVALID_START_COUNT exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get worklists functionality with zero
     * count value.
     */
    public void testGetCreatableWorklistsWithZeroCount() {

        try {

            SortOrder order = new SortOrder(new Attribute("name"));
            final int validStartValue = 0;
            final int zeroCount = 0;

            _worklistService.getCreatableWorklists(_createWLCriteria,
                                                    validStartValue,
                                                    zeroCount,
                                                    order);

            fail("Should have thrown EC_INVALID_START_COUNT exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get worklists functionality with negative
     * count value.
     */
    public void testGetCreatableWorklistsWithNegativeCount() {

        try {

            SortOrder order = new SortOrder(new Attribute("name"));
            final int validStartValue = 0;
            final int negativeCount = -5;

            _worklistService.getCreatableWorklists(_createWLCriteria,
                                                    validStartValue,
                                                    negativeCount,
                                                    order);

            fail("Should have thrown EC_INVALID_START_COUNT exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get creatable worklists functionality with null actors.
     */
    public void testGetCreatableWorklistsWithNullActors() {

        try {

            CreateWLCriteria createWLCriteria = new CreateWLCriteria(null, null, true);
            SortOrder order = new SortOrder(new Attribute("name"));
            final int validStartValue = 0;
            final int validCount = 5;

            _worklistService.getCreatableWorklists(createWLCriteria,
                                                    validStartValue,
                                                    validCount,
                                                    order);

            fail("Should have thrown EC_NULL_ACTORS exception");
        } catch (WorklistException we) {
            assertEquals(WorkflowEC.EC_NULL_ACTORS, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get creatable worklists functionality with null criteria.
     */
    public void testGetCreatableWorklistsWithNullCriteria() {

        try {

            SortOrder order = new SortOrder(new Attribute("name"));
            final int validStartValue = 0;
            final int validCount = 5;

            _worklistService.getCreatableWorklists(null, validStartValue, validCount, order);

            fail("Should have thrown EC_NULL_CRITERIA exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_NULL_CRITERIA, we.getErrorCode());
        }
    }
}
