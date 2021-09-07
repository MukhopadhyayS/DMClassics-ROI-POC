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
 * @date   Sep 27, 2007
 * @since  HECM 1.0; Sep 27, 2007
 */
public class TestGetWorklists
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
    
    private static long _worklistID;
    private static Actor    _user;
    
    private static String   _wlName;
    private static String   _wlDesc;

    private static Actors  _owners;
    private static Actors  _users;
    private static Actor _owner;
    
    private static TaskACL  _acl;
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
        
        createWorklists();
        createTask();
    }

    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklists() {

        //create
       Worklist wl1 = new Worklist();
       wl1.setName(_wlName);
       wl1.setDesc(_wlDesc);
       wl1.setOwners(_owners);
       wl1.setAssignedTo(_acls);
       
       _worklistID = _worklistService.createWorklist(wl1);
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
    
    public void testGetWorklists() {

        long count = _worklistService.getWorklistsCount(_owner);

        ListWorklist lwl = _worklistService.getWorklists(_owner, 0, (int) count,
                new SortOrder(new Attribute("desc"), true));
        List worklists = lwl.getWorklists();

        assertEquals(count, worklists.size());
        Worklist wl;
        for (int i = worklists.size(); --i >= 0;) {

            wl = (Worklist) worklists.get(i);
            assertEquals(0, wl.getAcls().size());
            assertEquals(0, wl.getOwnerActors().size());
            assertEquals(1, wl.getTotalTaskCount());
        }
    }

    /**
     * This method is used to test get worklists functionality with a non
     * existing actor.
     */
    public void testGetWorklistsWithNonExistingActor() {

        long seed = System.currentTimeMillis();
        Actor domain = new Actor(APP_ID, ET_DOMAIN, (seed + 2));
        final int startValue = 0;
        final int validCount = 5;
        SortOrder sortOrder = new SortOrder(new Attribute("name"));
        ListWorklist wl = _worklistService.getWorklists(domain, startValue, validCount, sortOrder);
        assertEquals(0, wl.getWorklists().size());
    }

    /**
     * This method is used to test get worklists functionality with negative
     * start value.
     */
    public void testGetWorklistsWithNegativeStart() {

        try {

            Attribute attribute = new Attribute();
            attribute.setName("name");
            SortOrder order = new SortOrder();
            order.setAttr(attribute);
            order.setIsDesc(true);
            final int negativeStartValue = -1;
            final int validCount = 5;

            _worklistService.getWorklists(_owner, negativeStartValue, validCount, order);
            fail("Should have thrown EC_INVALID_START_COUNT exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get worklists functionality with zero
     * count value.
     */
    public void testGetWorklistsWithZeroCount() {

        try {
            SortOrder order = new SortOrder(new Attribute("name"));
            final int validStartValue = 0;
            final int zeroCount = 0;
            _worklistService.getWorklists(_owner, validStartValue, zeroCount, order);
            fail("Should have thrown EC_INVALID_START_COUNT exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get worklists functionality with negative
     * count value.
     */
    public void testGetWorklistsWithNegativeCount() {

        try {
            SortOrder order = new SortOrder(new Attribute("name"));
            final int validStartValue = 0;
            final int negativeCount = -5;
            _worklistService.getWorklists(_owner, validStartValue, negativeCount, order);
            fail("Should have thrown EC_INVALID_START_COUNT exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get worklists functionality with null actor.
     */
    public void testGetWorklistsWithNullActor() {

        try {
            SortOrder order = new SortOrder(new Attribute("name"));
            final int validStartValue = 0;
            final int validCount = 5;
            _worklistService.getWorklists(null, validStartValue, validCount, order);
            fail("Should have thrown EC_NULL_ACTOR exception");
        } catch (WorklistException we) {
            assertEquals(WorkflowEC.EC_NULL_ACTOR, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get worklists functionality with invalid actor.
     */
    public void testGetWorklistsWithInvalidActor() {

        try {
            SortOrder order = new SortOrder(new Attribute("name"));
            final int validStartValue = 0;
            final int validCount = 5;
            Actor invalidActor = new Actor(-1, -1, -1);
            _worklistService.getWorklists(invalidActor, validStartValue, validCount, order);
            fail("Should have thrown EC_INVALID_ACTOR exception");
        } catch (WorklistException we) {
            assertEquals(WorkflowEC.EC_INVALID_ACTOR, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get worklists count functionality with non
     * existing actor.
     */
    public void testGetWorklistsCountWithNonExistingActor() {

        long seed = System.currentTimeMillis();
        Actor domain = new Actor(APP_ID, ET_DOMAIN, (seed + 2));
        long count = _worklistService.getWorklistsCount(domain);
        assertEquals(0, count);
    }

    /**
     * This method is used to test get worklists count functionality
     * with null actor.
     */
    public void testGetWorklistsCountWithNullActor() {

        try {
            _worklistService.getWorklistsCount(null);
            fail("Should have thrown EC_NULL_ACTOR exception");
        } catch (WorklistException we) {
            assertEquals(WorkflowEC.EC_NULL_ACTOR, we.getErrorCode());
        }
    }
}
