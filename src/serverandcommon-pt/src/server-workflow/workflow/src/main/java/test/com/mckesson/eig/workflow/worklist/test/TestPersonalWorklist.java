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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.Attribute;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.worklist.api.AssignedWLCriteria;
import com.mckesson.eig.workflow.worklist.api.ListWorklist;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskACLs;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;


/**
 * @author Pranav Amarasekaran
 * @date   Oct 11, 2007
 * @since  HECM 1.0; Oct 11, 2007
 */
public class TestPersonalWorklist
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static WorklistService _manager;

    private static long _worklistID;

    private static final int APP_ID    = 1;
    private static final int ET_USER   = 3;
    private static final String TASK_ACLS = "Task_Acls";

    private static Actor _user1;
    private static Actor _user2;

    private static Actor _owner;
    private static Actors  _owners;
    private static Actors  _personalOwners;

    private static TaskACLs _acls;
    private static TaskACL _acl1;

    private static String _wlName1;
    private static String _wlDesc1;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();

        _wlName1 = "My Worklist";
        _wlDesc1 = "Personal Worklist";

        _user1 = new Actor(APP_ID, ET_USER, (seed + 1));
        _user2 = new Actor(APP_ID, ET_USER, (seed + 1));
        _acl1 = new TaskACL(true, true, true, true, _user1);

        _owner  = _user1;

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);

        Set<Actor> set1 = new HashSet<Actor>(1);
        set1.add(_user2);
        _personalOwners = new Actors(set1);

        Set<TaskACL> set2 = new HashSet<TaskACL>(1);
        set2.add(_acl1);
        _acls = new TaskACLs(set2);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
		WsSession.setSessionData("Key_Actor", _user1);
        WsSession.setSessionUserId(_user1.getEntityID());

        _manager = (WorklistService) getManager(WORKLIST_MANAGER);
    }

    /**
     * This method is used to test create personal worklist functionality.
     */
    public void testCreatePersonalWorklist() {

        //create
       Worklist wl = new Worklist();
       wl.setName(_wlName1);
       wl.setDesc(_wlDesc1);
       wl.setOwners(_owners);
       wl.setAssignedTo(_acls);

       _worklistID = _manager.createWorklist(wl);
       assertTrue(_worklistID > 0);
       log("created");
       WsSession.setSessionData(TASK_ACLS, getUserTaskAcls());
   }
    
    private HashMap getUserTaskAcls() {
        return null;// _manager.getTaskAclsByActor(_owners);
    }

    /**
     * This method is used to test fetch personal worklists alone.
     */
    public void testGetPersonalWorklist() {

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = 2;
       AssignedWLCriteria criteria = new AssignedWLCriteria(_owners, _personalOwners, true);
       ListWorklist wl = _manager.getAssignedWorklists(criteria, startValue, count, order);
       List wlList = wl.getWorklists();
       assertNotNull(wlList.get(0));
       assertEquals(1, wlList.size());
       assertEquals(_wlName1, ((Worklist) wlList.get(0)).getName());
    }

    /**
     * This method is used to test fetch ShowOnlyOwnerWL.
     */
    public void testShowOnlyOwnerWL() {

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = 2;
       AssignedWLCriteria criteria = new AssignedWLCriteria(_owners, _personalOwners, true);
       criteria.setShowOnlyOwnerWL(true);
       ListWorklist wl = _manager.getAssignedWorklists(criteria, startValue, count, order);
       List<Worklist> wlList = wl.getWorklists();
       assertNotNull(wlList.get(0));
       assertEquals(1, wlList.size());
       assertEquals(_wlName1, ((Worklist) wlList.get(0)).getName());
    }
}
