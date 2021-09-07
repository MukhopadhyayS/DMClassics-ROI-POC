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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.Attribute;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.worklist.api.ListACLs;
import com.mckesson.eig.workflow.worklist.api.ListWorklist;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;


/**
 * @author Pranav Amarasekaran
 * @date   Sep 28, 2007
 * @since  HECM 1.0; Sep 28, 2007
 */
public class TestUpdateWorklistACLs
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static WorklistService _manager;

    private static long _worklistID1;
    private static long _worklistID2;

    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_GROUP  = 2;
    private static final int ET_USER   = 3;

    private static Actor _domain1;

    private static Actor _owner;

    private static Actors  _owners;

    private static TaskACL _acl1;
    private static TaskACL _acl2;

    private static TaskACL _acl3;
    private static TaskACL _acl4;

    private static Actor _group1;
    private static Actor _user1;

    private static String _wlName1;
    private static String _wlDesc1;
    private static String _wlName2;
    private static String _wlDesc2;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();

        _wlName1 = "name.1." + seed;
        _wlDesc1 = "desc.1." + seed;
        _wlName2 = "name.2." + seed;
        _wlDesc2 = "desc.2." + seed;

        _domain1 = new Actor(APP_ID, ET_DOMAIN, (seed + 1));

        _owner = _domain1;

        _group1 = new Actor(APP_ID, ET_GROUP, (seed + 1));
        _user1  = new Actor(APP_ID, ET_USER, 1);

        _acl1  = new TaskACL(true,   false, true,  false, _user1);
        _acl2  = new TaskACL(false, true,  false, true,  _user1);

        _acl3  = new TaskACL(true,   false, true,  false, _group1);
        _acl4  = new TaskACL(false, true,  false, true,  _group1);

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", "GHAZ" + String.valueOf(System.currentTimeMillis()));
		WsSession.setSessionData("Key_Actor", _user1);
        WsSession.setSessionUserId(_user1.getEntityID());

        _manager = (WorklistService) getManager(WORKLIST_MANAGER);
        createWorklists();
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

       _worklistID2 = _manager.createWorklist(wl2);
       assertTrue(_worklistID2 > 0);
       log("created");
   }

   public void testUpdateWorklistACLs() {

       // update acls
       long[] worklistIDs = {_worklistID1, _worklistID2};

       List<TaskACL> list = new ArrayList<TaskACL>();
       list.add(_acl1);
       list.add(_acl2);
       ListACLs listACLs = new ListACLs(list);

       _manager.updateWorklistACLs(listACLs, worklistIDs, _user1);

       list.clear();
       list.add(_acl3);
       list.add(_acl4);
       listACLs = new ListACLs(list);

       _manager.updateWorklistACLs(listACLs, worklistIDs, _group1);

       Worklist wList1 = _manager.getWorklist(_worklistID1);
       Worklist wList2 = _manager.getWorklist(_worklistID2);

       assertEquals(2, wList1.getAcls().size());
       assertEquals(2, wList2.getAcls().size());

       log("acls updated successfully");
   }

   public void testUpdateACLWithRemovedWL() {

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = 5;

       ListWorklist wl = _manager.getAllAssignedWorklists(_user1,
                                                       startValue,
                                                       count,
                                                       order);
       List wlList       = wl.getWorklists();

       List<TaskACL> list = new ArrayList<TaskACL>();
       ListACLs listACLs = null;

       int wlCountBeforeRemove = wlList.size();
       int iterCount = 0;
       int numOfWorklistToRemove = 1;

       long[] wlIDs = new long[wlCountBeforeRemove - numOfWorklistToRemove];
       for (Iterator iter = wlList.iterator(); iter.hasNext(); iterCount++) {
           if (iterCount < numOfWorklistToRemove) {
               iter.next();
               continue;
           }
           Worklist worklist = (Worklist) iter.next();
           Iterator i = worklist.getAssignedTo().getACLs().iterator();
           TaskACL acl = (TaskACL) i.next();

           list.add(acl);

           wlIDs[(iterCount - numOfWorklistToRemove)] = worklist.getWorklistID();
       }
       listACLs = new ListACLs(list);
       _manager.updateWorklistACLs(listACLs, wlIDs, _user1);

       wl = _manager.getAllAssignedWorklists(_user1,
               startValue,
               iterCount,
               order);

       int wlUpdatedCount = 0;
       wlList       = wl.getWorklists();
       if (wlList != null) {
           wlUpdatedCount = wlList.size();
       }

       assertEquals(wlCountBeforeRemove, (wlUpdatedCount + numOfWorklistToRemove));

       log("acls updated successfully");
   }

   public void testUpdateWorklistWithAlteredACLs() {

       // update acls
       long[] worklistIDs = {_worklistID1};
       Worklist wList = _manager.getWorklist(_worklistID1);

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = 5;

       ListWorklist wl = _manager.getAllAssignedWorklists(_user1,
                                                          startValue,
                                                          count,
                                                          order);

       List<TaskACL> list = new ArrayList<TaskACL>();
       for (Iterator iterator = wList.getAcls().iterator();
            iterator.hasNext();) {
          TaskACL acl1 = (TaskACL) iterator.next();
           acl1.getActor().setActorID(0);
           acl1.setCanCreate(true);
           list.add(acl1);
       }

       ListACLs listACLs = new ListACLs(list);

       _manager.updateWorklistACLs(listACLs, worklistIDs, _acl1.getActor());

       wl = _manager.getAllAssignedWorklists(_user1,
                                             startValue,
                                             count,
                                             order);
        
        assertNotNull(wl);
        list = new ArrayList<TaskACL>();
        for (Iterator iterator = wList.getAcls().iterator();
        iterator.hasNext();) {
            TaskACL acl1 = (TaskACL) iterator.next();
            assertEquals(true, acl1.getCanCreate());
        }

       log("acls updated successfully");
   }

   public void testUpdateWorklistACLsWithInvalidIDS() {

       // update acls
       long[] worklistIDs = {-1, 0};

       List<TaskACL> list = new ArrayList<TaskACL>();
       list.add(_acl1);
       list.add(_acl2);
       ListACLs listACLs = new ListACLs(list);

       try {
           _manager.updateWorklistACLs(listACLs, worklistIDs, _acl1.getActor());
           fail("Should have thrown EC_INVALID_WORKLIST_ID exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_WORKLIST_ID, we.getErrorCode());
       }

       log("update acls with invalid ids verified successfully");
   }

   public void testUpdateWorklistACLsWithNullIDS() {

       // update acls
       long[] worklistIDs = null;

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = 5;

       List<TaskACL> list = new ArrayList<TaskACL>();
       list.add(_acl1);
       list.add(_acl2);
       ListACLs listACLs = new ListACLs(list);

       _manager.updateWorklistACLs(listACLs, worklistIDs, _acl1.getActor());

       ListWorklist wl = _manager.getAllAssignedWorklists(_user1,
                                                           startValue,
                                                           count,
                                                           order);

       assertEquals(0, wl.getWorklists().size());

       log("update acls with null ids verified successfully");
   }

   public void testUpdateWorklistACLsWithNullListACLs() {

       // update acls
       long[] worklistIDs = {_worklistID1, _worklistID2};
       ListACLs listACLs = null;

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = 5;

       _manager.updateWorklistACLs(listACLs, worklistIDs, _acl1.getActor());

       ListWorklist wl = _manager.getAllAssignedWorklists(_user1,
                                                           startValue,
                                                           count,
                                                           order);

       assertEquals(0, wl.getWorklists().size());

       log("update acls with null ids verified successfully");
   }


}
