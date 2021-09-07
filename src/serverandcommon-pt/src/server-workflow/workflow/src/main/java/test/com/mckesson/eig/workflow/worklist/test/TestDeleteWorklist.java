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
import com.mckesson.eig.workflow.api.IDListResult;
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
public class TestDeleteWorklist
extends  com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static WorklistService _manager;
    private static TaskService _taskService;
    
    private static final String KEY_ACTOR = "Key_Actor";
    private static final String TASK_ACLS = "Task_Acls";
    
    private static final int YEAR      = 2008;
    private static final int DAY       = 12;

    private static long _worklistID1;
    private static long _worklistID2;

    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_USER   = 3;

    private static Actor _domain1;

    private static Actor _owner;
    private static Actor    _user;
    private static TaskACL  _acl;

    private static Actors   _owners;
    private static Actors   _users;
    private static TaskACLs _acls;

    private static String _wlName1;
    private static String _wlDesc1;
    private static String _wlName2;
    private static String _wlDesc2;
    
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

        _wlName1 = "name.1." + seed;
        _wlDesc1 = "desc.1." + seed;
        _wlName2 = "name.2." + seed;
        _wlDesc2 = "desc.2." + seed;

        _domain1 = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
        _user     = new Actor(APP_ID, ET_USER,   (seed + 1));
        
        _acl  = new TaskACL(true, true, true, true, _user);
        
        Set<TaskACL> aclSet = new HashSet<TaskACL>(1);
        aclSet.add(_acl);
        _acls = new TaskACLs(aclSet);
        
        Set<Actor> acts = new HashSet<Actor>();
        acts.add(_user);
        
        _users = new Actors();
        _users.setActors(acts);

        _owner  = _domain1;

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);

        _manager     = (WorklistService) getManager(WORKLIST_MANAGER);
        _taskService = (TaskService) getManager(TASK_MANAGER);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user.getEntityID());
        WsSession.setSessionData(KEY_ACTOR, _user);


        _priorityID         = 2;
        _statusID           = "new";
        _taskName           = "Done" + seed;
        _taskDescription    = "follow certain principles" + seed;
        _comments           = "finish" + seed;
        _canStartEarly      = false;
        _startDate          = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 1).getTime();
        _endDate            = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY).getTime();
        
        createWorklists();
        createTaskWithNewStatusID();
    }
    
    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklists() {

        //create
        Worklist wl1 = new Worklist();
        wl1.setName(_wlName1);
        wl1.setDesc(_wlDesc1);
        wl1.setOwners(_owners);

        _worklistID1 = _manager.createWorklist(wl1);
        assertTrue(_worklistID1 > 0);

        Worklist wl2 = new Worklist();
        wl2.setName(_wlName2);
        wl2.setDesc(_wlDesc2);
        wl2.setOwners(_owners);
        wl2.setAssignedTo(_acls);

        _worklistID2 = _manager.createWorklist(wl2);
        assertTrue(_worklistID2 > 0);
        log("created");
       
	   resolveTaskAclsByActors();
   }
    
   private void resolveTaskAclsByActors() {
       _manager.resolveTaskAclsByActors(_users);
   }

    
    /**
     * This method is used to create a task.
     */
    public void createTaskWithNewStatusID() {

        Task task = new Task();

        task.setWorklistID(_worklistID2);
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
     * This method is used to test delete worklist functionality with existing
     * and non existing worklistIDs
     */
    public void testDeleteWorklistWithExistandNonExistingIDs() {

        final long negativeID = -12;
        final long size       = 3;
        long[] ids = new long[] {_worklistID1, _worklistID2, negativeID, Long.MAX_VALUE};
        IDListResult listRes = _manager.deleteWorklists(ids);
        assertEquals(size, listRes.getFailed().size());
    }

    /**
     * This method is used to test get worklist functionality with invalid ID.
     */
    public void testDeleteWorklistWithNullIDs() {

        try {
            _manager.deleteWorklists(null);
            fail("Should have thrown EC_NULL_WL_IDS exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_NULL_WL_IDS, we.getErrorCode());
        }
    }
}
