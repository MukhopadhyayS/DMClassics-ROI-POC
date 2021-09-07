/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
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
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.worklist.api.ListWorklist;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskACLs;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author Sahul Hameed Y
 * @date   Jun 25, 2008
 * @since  HECM 1.0; Jun 25, 2008
 */
public class TestGetOwnedWorklists 
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {
    
    private static WorklistService _worklistService;
    
    private static final int APP_ID    = 1;
    private static final int ET_USER   = 3;
    
    private static long _worklistID;
    private static Actor    _user;
    private static Actors    _owners;
    
    private static String   _wlName;
    private static String   _wlDesc;

    private static TaskACL  _acl;
    private static TaskACLs _acls;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();
        _user     = new Actor(APP_ID, ET_USER,   (seed + 1));

        _wlName = "My Worklist";
        _wlDesc = "Personal Worklist";
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
		WsSession.setSessionData("Key_Actor", new Actor(1, Actor.USER_ENTITY_TYPE, 1));
        WsSession.setSessionUserId(_user.getEntityID());
        
        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_user);
        _owners = new Actors(set);

        _acl  = new TaskACL(true, true, true, true, _user);

        Set<TaskACL> aclSet = new HashSet<TaskACL>(1);
        aclSet.add(_acl);
        _acls = new TaskACLs(aclSet);
        
        _worklistService = (WorklistService) getManager(WORKLIST_MANAGER);
        
        createOwnedWorklist();
    }

    /**
     * This method is used to test create worklist functionality.
     */
    public void createOwnedWorklist() {

       Worklist wl1 = new Worklist();
       wl1.setName(_wlName);
       wl1.setDesc(_wlDesc);
       wl1.setOwners(_owners);
       wl1.setAssignedTo(_acls);
       
       _worklistID = _worklistService.createWorklist(wl1);
       assertTrue(_worklistID > 0);
   }
    
    public void testGetOwnedWorklists() {
        
        ListWorklist ownedWorklists = _worklistService.getOwnedWorklists(_user);
        assertEquals(1, ownedWorklists.getWorklists().size());
        Worklist worklist = ownedWorklists.getWorklists().get(0);
        assertEquals(_worklistID, worklist.getWorklistID());
        assertEquals(_wlName, worklist.getName());
    }
}
