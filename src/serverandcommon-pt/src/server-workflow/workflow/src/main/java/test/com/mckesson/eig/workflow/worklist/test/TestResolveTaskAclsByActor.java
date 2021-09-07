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
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskACLs;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author Ghazni
 * @date   Feb 4, 2008
 * @since  HECM 1.0; Feb 4, 2008
 */
public class TestResolveTaskAclsByActor 
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
    private static Actors _users;

    private static TaskACL  _acl;
    private static TaskACLs _acls;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();
        _owner    = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
        _user     = new Actor(APP_ID, ET_USER,   (seed + 1));

        _wlName = "name.1." + seed;
        _wlDesc = "desc.1." + seed;
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user.getEntityID());
        WsSession.setSessionData("Key_Actor", _user);

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

    public void testresolveTaskAclsByActors() {

		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        _worklistService.resolveTaskAclsByActors(_users);
    }
}
