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
 * @author Pranav Amarasekaran
 * @date   Sep 27, 2007
 * @since  HECM 1.0; Sep 27, 2007
 */
public class TestGetAllWorklists
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static WorklistService _worklistService;

    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_USER   = 3;

    private static long _worklistID;
    private static Actor    _user;

    private static String   _wlName;
    private static String   _wlDesc;

    private static Actors  _owners;
    private static Actor _owner;

    private static TaskACL  _acl;
    private static TaskACLs _acls;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();
        _owner    = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
        _user     = new Actor(APP_ID, ET_USER,   1);

        _wlName = "name.1." + seed;
        _wlDesc = "desc.1." + seed;

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);

        _acl  = new TaskACL(true, true, true, true, _user);

        Set<TaskACL> aclSet = new HashSet<TaskACL>(1);
        aclSet.add(_acl);
        _acls = new TaskACLs(aclSet);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user.getEntityID());
        WsSession.setSessionData("Key_Actor",_user);
        _worklistService = (WorklistService) getManager(WORKLIST_MANAGER);

        createWorklists();
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
   }

    public void testGetWorklists() {

        long count = _worklistService.getAllWorklistsCount();

        ListWorklist lwl = _worklistService.getAllWorklists(0, (int) count,
                new SortOrder(new Attribute("desc"), true));
        List worklists = lwl.getWorklists();

        assertEquals(count, worklists.size());
    }

    /**
     * This method is used to test get all worklists functionality with negative
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

            _worklistService.getAllWorklists(negativeStartValue, validCount, order);
            fail("Should have thrown EC_INVALID_START_COUNT exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get all worklists functionality with zero
     * count value.
     */
    public void testGetWorklistsWithZeroCount() {

        try {
            SortOrder order = new SortOrder(new Attribute("name"));
            final int validStartValue = 0;
            final int zeroCount = 0;
            _worklistService.getAllWorklists(validStartValue, zeroCount, order);
            fail("Should have thrown EC_INVALID_START_COUNT exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
        }
    }

    /**
     * This method is used to test get all worklists functionality with negative
     * count value.
     */
    public void testGetWorklistsWithNegativeCount() {

        try {
            SortOrder order = new SortOrder(new Attribute("name"));
            final int validStartValue = 0;
            final int negativeCount = -5;
            _worklistService.getAllWorklists(validStartValue, negativeCount, order);
            fail("Should have thrown EC_INVALID_START_COUNT exception");
        } catch (WorklistException we) {
            assertEquals(WorklistEC.EC_INVALID_START_COUNT, we.getErrorCode());
        }
    }


    /**
     * This method is used to test get all worklists count functionality
     */
    public void testGetAllWorklistsCount() {

        long count = _worklistService.getAllWorklistsCount();
        assertNotNull(count);
    }
}
