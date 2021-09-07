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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.Attribute;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.worklist.api.ListWorklist;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskACLs;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;


/**
 * @author sahuly
 * @date   Dec 27, 2007
 * @since  HECM 1.0; Sep 27, 2007
 */
public class TestGetAllAssignedWorklists
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {


    private static WorklistService _manager;

    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_USER   = 3;

    private static Actor _domain1;

    private static long _worklistID1;

    private static String _wlName1;
    private static String _wlDesc1;

    private static Actor _owner;
    private static Actor _user2;

    private static TaskACL _acl2;

    private static Actors  _owners;

    private static TaskACLs _acls1;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();
        _domain1 = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
        _owner  = _domain1;

        _wlName1 = "name.1." + seed;
        _wlDesc1 = "desc.1." + seed;

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);

        _user2   = new Actor(APP_ID, ET_USER, System.currentTimeMillis());

        _acl2  = new TaskACL(true, true,  true, true, _user2);

        Set<TaskACL> aclSet1 = new HashSet<TaskACL>();
        aclSet1.add(_acl2);
        _acls1 = new TaskACLs(aclSet1);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user2.getEntityID());
        WsSession.setSessionData("Key_Actor",_user2);
        _manager      = (WorklistService) getManager(WORKLIST_MANAGER);

        createWorklists();
    }

    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklists() {

       Worklist wl1 = new Worklist();
       wl1.setName(_wlName1);
       wl1.setDesc(_wlDesc1);
       wl1.setOwners(_owners);
       wl1.setAssignedTo(_acls1);

       _worklistID1 = _manager.createWorklist(wl1);
       assertTrue(_worklistID1 > 0);
   }

    /**
     * This method is used to test get assigned worklists.
     */
    public void testGetAssignedWorklists() {

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = 5;

       ListWorklist wl = _manager.getAllAssignedWorklists(_user2,
                                                       startValue,
                                                       count,
                                                       order);
       List wlList       = wl.getWorklists();
       Worklist worklist = wl.getWorklists().get(0);
       TaskACL taskAcl   = worklist.getAssignedTo().getACLs().iterator().next();

       assertEquals(1, wlList.size());

       assertEquals(true, taskAcl.getCanCreate());
       assertEquals(true, taskAcl.getCanReassign());
       assertEquals(true, taskAcl.getCanDelete());
       assertEquals(true, taskAcl.getCanComplete());
    }

    /**
     * This method is used to test get assigned worklists functionality with
     * non existing actors.
     */
    public void testGetAssignedWorklistsWithNonExistingActors() {

       SortOrder order = new SortOrder(new Attribute("name"));
       long seed = System.currentTimeMillis();

       Actor user = new Actor(APP_ID, ET_USER, (seed + 2));
       Set<Actor> aclActorSet = new HashSet<Actor>();
       aclActorSet.add(user);

       final int startValue = 0;
       final int validCount = 5;
       ListWorklist wl = _manager.getAllAssignedWorklists(user, startValue, validCount, order);
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

           _manager.getAllAssignedWorklists(_user2, negativeStartValue, validCount, order);

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

           _manager.getAllAssignedWorklists(_user2, validStartValue, zeroCount, order);

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

           _manager.getAllAssignedWorklists(_user2, validStartValue, negativeCount, order);

           fail("Should have thrown EC_INVALID_START_COUNT exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
       }
    }

    /**
     * This method is used to test get assigned worklists count functionality
     * with show empty worklists.
     */
    public void testGetAssignedWorklistsCount() {

       long count = _manager.getAllAssignedWorklistsCount(_user2);
       assertEquals(1, count);
    }

    /**
     * This method is used to test get assigned worklists count functionality
     * with non existing actors.
     */
    public void testGetAssignedWorklistsCountWithNonExistingActors() {

       long seed = System.currentTimeMillis();

       Actor user = new Actor(APP_ID, ET_USER, (seed + 2));

       long count = _manager.getAllAssignedWorklistsCount(user);
       assertEquals(0, count);
    }
}
