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
import java.util.List;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.Attribute;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.worklist.api.AssignedWLCriteria;
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
 * @author Pranav Amarasekaran
 * @date   Dec 6, 2007
 * @since  HECM 1.0; Sep 27, 2007
 */
public class TestGetAssignedWorklists
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {
    
    
    private static WorklistService _manager;
    private static TaskService _taskService;
    private static final String KEY_ACTOR = "Key_Actor";
    private static final String TASK_ACLS = "Task_Acls";

    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_USER   = 3;
    
    private static final int YEAR      = 2008;
    private static final int DAY       = 12;

    private static Actor _domain1;

    private static long _worklistID1;
    private static long _worklistID2;
    private static long _worklistID3;

    private static String _wlName1;
    private static String _wlDesc1;
    private static String _wlName2;
    private static String _wlDesc2;
    private static String _wlName3;
    private static String _wlDesc3;

    private static Actor _owner;
    private static Actor _user1;
    private static Actor _user2;

    private static TaskACL _acl1;
    private static TaskACL _acl2;
    private static TaskACL _acl3;

    private static Actors  _owners;
    private static Actors  _users;
    private static Actors _personalOwners;
    private static Actors _assignedActors;

    private static TaskACLs _acls;
    private static TaskACLs _acls1;
    private static TaskACLs _acls2;
    
    private static long     _taskID;
    private static long     _priorityID;
    private static String   _statusID;
    private static String   _taskName;
    private static String   _taskDescription;
    private static String   _comments;
    private static Date     _startDate;
    private static Date     _endDate;
    private static boolean  _canStartEarly;

    private static AssignedWLCriteria _criteria;
    
    public void testSetUp() {
        
        init();

        long seed = System.currentTimeMillis();
        _domain1 = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
        _owner  = _domain1;

        _wlName1 = "name.1." + seed;
        _wlDesc1 = "desc.1." + seed;
        _wlName2 = "name.2." + seed;
        _wlDesc2 = "desc.2." + seed;
        _wlName3 = "name.3." + seed;
        _wlDesc3 = "desc.3." + seed;

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);

        _user1   = new Actor(APP_ID, ET_USER, System.currentTimeMillis());
        Set<Actor> personalOwnerSet = new HashSet<Actor>();
        personalOwnerSet.add(_user1);
        _personalOwners = new Actors(personalOwnerSet);
        
        _user2   = new Actor(APP_ID, ET_USER, System.currentTimeMillis());
        Set<Actor> userSet = new HashSet<Actor>();
        userSet.add(_user2);
        _assignedActors = new Actors(userSet);
        
        _acl1  = new TaskACL(true, true,  true, true,  _user1);
        _acl2  = new TaskACL(false, true,  false, true,  _user2);
        _acl3  = new TaskACL(false, true,  false, true,  _user2);

        Set<TaskACL> aclSet = new HashSet<TaskACL>();
        aclSet.add(_acl1);
        _acls = new TaskACLs(aclSet);

        Set<TaskACL> aclSet1 = new HashSet<TaskACL>();
        aclSet1.add(_acl2);
        _acls1 = new TaskACLs(aclSet1);
        
        Set<TaskACL> aclSet2 = new HashSet<TaskACL>();
        aclSet2.add(_acl3);
        _acls2 = new TaskACLs(aclSet2);
        
        Set<Actor> acts = new HashSet<Actor>();
        acts.add(_user1);
        
        _users = new Actors();
        _users.setActors(acts);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user1.getEntityID());
        WsSession.setSessionData(KEY_ACTOR, _user1);

        _priorityID         = 2;
        _statusID           = "new";
        _taskName           = "Done" + seed;
        _taskDescription    = "follow certain principles" + seed;
        _comments           = "finish" + seed;
        _canStartEarly      = false;
        _startDate          = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 1).getTime();
        _endDate            = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY).getTime();
        
        _manager      = (WorklistService) getManager(WORKLIST_MANAGER);
        _taskService  = (TaskService)     getManager(TASK_MANAGER);
        
        _criteria = new AssignedWLCriteria(_owners, _assignedActors, true);

        createWorklists();
        createTask();
    }
    
    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklists() {

        //create
       Worklist wl1 = new Worklist();
       wl1.setName(_wlName1);
       wl1.setDesc(_wlDesc1);
       wl1.setOwners(_personalOwners);
       wl1.setAssignedTo(_acls);

       _worklistID1 = _manager.createWorklist(wl1);
       assertTrue(_worklistID1 > 0);

       Worklist wl2 = new Worklist();
       wl2.setName(_wlName2);
       wl2.setDesc(_wlDesc2);
       wl2.setOwners(_owners);
       wl2.setAssignedTo(_acls1);

       _worklistID2 = _manager.createWorklist(wl2);
       assertTrue(_worklistID2 > 0);
       
       Worklist wl3 = new Worklist();
       wl3.setName(_wlName3);
       wl3.setDesc(_wlDesc3);
       wl3.setOwners(_owners);
       wl3.setAssignedTo(_acls2);

       _worklistID3 = _manager.createWorklist(wl3);
       assertTrue(_worklistID3 > 0);
       
       resolveTaskAclsByActors();
   }
    
    /**
     * This method is used to create a task.
     */
    public void createTask() {
        
        Task task = new Task();
        
        task.setWorklistID(_worklistID2);
        task.setTaskCreator(_user2);
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
        _manager.resolveTaskAclsByActors(_users);
    }

    /**
     * This method is used to test get personal worklists alone.
     */
    public void testGetPersonalWorklist() {

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = 5;
       
       _criteria = new AssignedWLCriteria(_personalOwners, _personalOwners, true);
       
       ListWorklist wl = _manager.getAssignedWorklists(_criteria, startValue, count, order);
       List wlList = wl.getWorklists();
       
       assertNotNull(wlList.get(0));
       assertEquals(1, wlList.size());
    }

    /**
     * This method is used to test get assigned worklists.
     */
    public void testGetAssignedWorklistsWithShowEmptyWorklists() {

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = 5;

       _criteria = new AssignedWLCriteria(_owners, _assignedActors, true);
       ListWorklist wl = _manager.getAssignedWorklists(_criteria,
                                                       startValue,
                                                       count,
                                                       order);
       List wlList = wl.getWorklists();
       assertEquals(2, wlList.size());
    }

    /**
     * This method is used to test get assigned worklists.
     */
    public void testGetAssignedWorklistsWithoutShowEmptyWorklists() {

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = 5;

       _criteria = new AssignedWLCriteria(_owners, _assignedActors, false);
       ListWorklist wl = _manager.getAssignedWorklists(_criteria,
                                                       startValue,
                                                       count,
                                                       order);
       List wlList = wl.getWorklists();
       assertEquals(1, wlList.size());
    }

    /**
     * This method is used to test get assigned worklists functionality with
     * non existing actors.
     */
    public void testGetAssignedWorklistsWithNonExistingActors() {

       SortOrder order = new SortOrder(new Attribute("name"));
       long seed = System.currentTimeMillis();

       Actor domain = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
       Set<Actor> actorSet = new HashSet<Actor>();
       actorSet.add(domain);
       Actors actors = new Actors(actorSet);

       Actor user = new Actor(APP_ID, ET_USER, (seed + 2));
       Set<Actor> aclActorSet = new HashSet<Actor>();
       aclActorSet.add(user);
       
       Actors aclActors = new Actors(aclActorSet);
       AssignedWLCriteria criteria = new AssignedWLCriteria(actors, aclActors, false);

       final int startValue = 0;
       final int validCount = 5;
       ListWorklist wl = _manager.getAssignedWorklists(criteria, startValue, validCount, order);
       assertEquals(0, wl.getWorklists().size());
    }

    /**
     * This method is used to test get assigend worklists functionality
     * with negative start value.
     */
    public void testGetAssignedWorklistsWithNegativeStart() {

       try {
           
           SortOrder order = new SortOrder(new Attribute("name"));
           final int negativeStartValue = -1;
           final int validCount = 5;
           
           _manager.getAssignedWorklists(_criteria, negativeStartValue, validCount, order);
           
           fail("Should have thrown EC_INVALID_START_COUNT exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigned worklists functionality
     * with zero count value.
     */
    public void testGetAssignedWorklistsWithZeroCount() {

       try {
           SortOrder order = new SortOrder(new Attribute("name"));
           final int validStartValue = 0;
           final int zeroCount = 0;
           
           _manager.getAssignedWorklists(_criteria, validStartValue, zeroCount, order);
           
           fail("Should have thrown EC_INVALID_START_COUNT exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigned worklists functionality
     * with negative count value.
     */
    public void testGetAssignedWorklistsWithNegativeCount() {

       try {
           SortOrder order = new SortOrder(new Attribute("name"));
           final int validStartValue = 0;
           final int negativeCount = -5;
           
           _manager.getAssignedWorklists(_criteria, validStartValue, negativeCount, order);
           
           fail("Should have thrown EC_INVALID_START_COUNT exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigned worklists functionality
     * with null criteria.
     */
    public void testGetAssignedWorklistsWithNullCriteria() {

       try {
           SortOrder order = new SortOrder(new Attribute("name"));
           final int validStartValue = 0;
           final int validCount = 5;

           _manager.getAssignedWorklists(null, validStartValue, validCount, order);

           fail("Should have thrown EC_NULL_CRITERIA exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_NULL_CRITERIA, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigned worklists functionality
     * with null actors in the criteria.
     */
    public void testGetAssignedWorklistsWithNullActors() {

       try {
           SortOrder order = new SortOrder(new Attribute("name"));
           final int validStartValue = 0;
           final int validCount = 5;
           AssignedWLCriteria criteria = new AssignedWLCriteria(null, null, true);

           _manager.getAssignedWorklists(criteria, validStartValue, validCount, order);

           fail("Should have thrown EC_NULL_ACTORS exception");
       } catch (WorklistException we) {
           assertEquals(WorkflowEC.EC_NULL_ACTORS, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigned worklists count functionality
     * with show empty worklists.
     */
    public void testGetAssignedWorklistsCountWithShowEmptyWorklists() {

        AssignedWLCriteria assignedWLCriteria =
                new AssignedWLCriteria(_owners,
                                       _assignedActors,
                                       true);
       long count = _manager.getAssignedWorklistsCount(assignedWLCriteria);
       assertEquals(2, count);
    }

    /**
     * This method is used to test get assigned worklists count functionality
     * with show empty worklists.
     */
    public void testGetAssignedWorklistsCountWithoutShowEmptyWorklists() {

       AssignedWLCriteria assignedWLCriteria =
                        new AssignedWLCriteria(_owners,
                                               _assignedActors,
                                               false);
       long count = _manager.getAssignedWorklistsCount(assignedWLCriteria);
       assertEquals(1, count);
    }

    /**
     * This method is used to test get assigned worklists count functionality
     * with non existing actors.
     */
    public void testGetAssignedWorklistsCountWithNonExistingActors() {

       long seed = System.currentTimeMillis();

       Actor domain = new Actor(APP_ID, ET_DOMAIN, (seed + 2));
       Set<Actor> actorSet = new HashSet<Actor>();
       actorSet.add(domain);
       Actors actors = new Actors(actorSet);

       Actor user = new Actor(APP_ID, ET_USER, (seed + 2));
       Set<Actor> aclActorSet = new HashSet<Actor>();
       aclActorSet.add(user);
       Actors aclActors = new Actors(aclActorSet);
       AssignedWLCriteria criteria = new AssignedWLCriteria(actors, aclActors, true);

       long count = _manager.getAssignedWorklistsCount(criteria);
       assertEquals(0, count);
    }

    /**
     * This method is used to test get assigned worklists count functionality
     * with null actor.
     */
    public void testGetAssignedWorklistsCountWithNullCriteria() {

       try {
           _manager.getAssignedWorklistsCount(null);
           fail("Should have thrown EC_NULL_CRITERIA exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_NULL_CRITERIA, we.getErrorCode());
       }
    }
}
