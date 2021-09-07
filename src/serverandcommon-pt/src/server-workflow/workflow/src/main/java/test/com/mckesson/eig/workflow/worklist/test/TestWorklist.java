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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.Attribute;
import com.mckesson.eig.workflow.api.IDListResult;
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
 * @date   Sep 09, 2007
 * @since  HECM 1.0
 */
public class TestWorklist
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static WorklistService _manager;

    private static long _worklistID;

    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_GROUP  = 2;
    private static final int ET_USER   = 3;

    private static Actor _domain1;

    private static Actor _owner;

    private static Actor _group1;
    private static Actor _group2;

    private static Actor _user1;
    private static Actor _user2;
    private static Actor _user3;

    private static Actors  _owners;

    private static TaskACLs _aclSet1;
    private static TaskACLs _aclSet2;

    private static TaskACL _acl1;
    private static TaskACL _acl2;
    private static TaskACL _acl3;
    private static TaskACL _acl4;
    private static TaskACL _acl5;

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

        _owner  = _domain1;

        _group1  = new Actor(APP_ID, ET_GROUP, (seed + 1));
        _group2  = new Actor(APP_ID, ET_GROUP, (seed + 2));

        _user1   = new Actor(APP_ID, ET_USER, (seed + 1));
        _user2   = new Actor(APP_ID, ET_USER, (seed + 2));
        _user3   = new Actor(APP_ID, ET_USER, (seed + 1 + 2));

        _acl1 = new TaskACL(true,   false, true,  false, _group1);
        _acl2  = new TaskACL(false, true,  false, true,  _user1);
        _acl3 = new TaskACL(false,  false, true,  true,  _group2);
        _acl4  = new TaskACL(true,  true,  false, false, _user2);

        _acl5  = new TaskACL(true,  true,  false, false, _user3);

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);

        Set<TaskACL> set1 = new HashSet<TaskACL>(2);
        set1.add(_acl1);
        set1.add(_acl2);
        _aclSet1 = new TaskACLs(set1);

        set1 = new HashSet<TaskACL>(2);
        set1.add(_acl3);
        set1.add(_acl4);
        _aclSet2 = new TaskACLs(set1);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user1.getEntityID());

        _manager = (WorklistService) getManager(WORKLIST_MANAGER);
    }

    public void testCreateWorklist() {

         //create
        Worklist wl = new Worklist();
        wl.setName(_wlName1);
        wl.setDesc(_wlDesc1);
        wl.setOwners(_owners);

        _worklistID = _manager.createWorklist(wl);
        assertTrue(_worklistID > 0);
        log("created");
        // verify
        wl = null;
        wl = _manager.getWorklist(_worklistID);

        assertNotNull(wl);
        assertEquals(_worklistID, wl.getWorklistID());
        assertEquals(_wlName1, wl.getName());
        assertEquals(_wlDesc1, wl.getDesc());

        assertEquals(_owners.getActors(), wl.getOwnerActors());
        assertEquals(0, wl.getAcls().size());
        assertEquals(-1, wl.getActiveTaskCount());
        assertEquals(-1, wl.getTotalTaskCount());
        log("create.verified");
    }

    public void testUpdateWorklistPlain() {

        // update
        Worklist wl = _manager.getWorklist(_worklistID);
        assertNotNull(wl);
        assertEquals(_worklistID, wl.getWorklistID());

        wl.setName(_wlName2);
        wl.setDesc(_wlDesc2);

        _manager.updateWorklist(wl);
        log("updated");

        // verify
        wl = null;
        wl = _manager.getWorklist(_worklistID);

        assertNotNull(wl);
        assertEquals(_worklistID, wl.getWorklistID());
        assertEquals(_wlName2, wl.getName());
        assertEquals(_wlDesc2, wl.getDesc());

        assertEquals(_owners.getActors(), wl.getOwnerActors());
        assertEquals(0, wl.getAcls().size());
        log("update.verified");
    }

    public void testUpdateWorklistAssignActors() {

        // assign actors
        Worklist wl = _manager.getWorklist(_worklistID);
        assertNotNull(wl);
        assertEquals(_worklistID, wl.getWorklistID());

        wl.setAssignedTo(_aclSet1);

        _manager.updateWorklist(wl);
        log("assigned");

        // verify
        wl = null;
        wl = _manager.getWorklist(_worklistID);

        assertNotNull(wl);
        assertEquals(_worklistID, wl.getWorklistID());
        assertEquals(_wlName2, wl.getName());
        assertEquals(_wlDesc2, wl.getDesc());

        assertEquals(_owners.getActors(), wl.getOwnerActors());
        assertTrue(compareACLs(_aclSet1.getACLs(),  wl.getAcls()));
        log("assign.verified");
    }

    public void testUpdateWorklist() {

        // update
        Worklist wl = _manager.getWorklist(_worklistID);
        assertNotNull(wl);
        assertEquals(_worklistID, wl.getWorklistID());
        log("selected.worklist.for.updated.all");

        wl.setName(_wlName1);
        wl.setDesc(_wlDesc1);
        wl.setAssignedTo(_aclSet2);

        _manager.updateWorklist(wl);
        log("updated.all");

        // verify
        wl = null;
        wl = _manager.getWorklist(_worklistID);

        assertNotNull(wl);
        assertEquals(_worklistID, wl.getWorklistID());
        assertEquals(_wlName1,   wl.getName());
        assertEquals(_wlDesc1,   wl.getDesc());

        assertEquals(_owners.getActors(), wl.getOwnerActors());
        assertTrue(compareACLs(_aclSet2.getACLs(),  wl.getAcls()));
        log("update.all.verified");
    }

    @SuppressWarnings("unchecked")
    public void testUpdateWorklistAllCasesWithACL() {

        // update
        Worklist wl = _manager.getWorklist(_worklistID);
        assertNotNull(wl);
        assertEquals(_worklistID, wl.getWorklistID());
        log("selected.worklist.for.updated.with.acl.update");

        Set acls = wl.getAcls();
        TaskACL acl1ToUpdate = (TaskACL) acls.iterator().next();
        acl1ToUpdate.setCanComplete(true);
        acl1ToUpdate.setCanCreate(true);
        acl1ToUpdate.setCanDelete(true);
        acl1ToUpdate.setCanReassign(true);

        Set newACLs = new HashSet(2);
        newACLs.add(acl1ToUpdate); // update existing one
        newACLs.add(_acl5); // add a new one

        wl.setAcls(newACLs);

        _manager.updateWorklist(wl);
        log("updated.worklist.with all.types.of.acl.updates");

        // verify
        wl = null;
        wl = _manager.getWorklist(_worklistID);

        assertNotNull(wl);
        assertEquals(_worklistID, wl.getWorklistID());
        assertEquals(_wlName1,   wl.getName());
        assertEquals(_wlDesc1,   wl.getDesc());

        assertEquals(_owners.getActors(), wl.getOwnerActors());
        assertTrue(compareACLs(newACLs,  wl.getAcls()));
        log("selected.worklist.for.updated.with.acl.update.verified");
    }

    public void testGetWorklists() {

        long count = _manager.getWorklistsCount(_owner);

        assertEquals(1, count);

        ListWorklist lwl = _manager.getWorklists(_owner, 0, (int) count,
                new SortOrder(new Attribute("desc"), true));
        List worklists = lwl.getWorklists();

        assertEquals(count, worklists.size());
        Worklist wl;
        for (int i = worklists.size(); --i >= 0;) {

            wl = (Worklist) worklists.get(i);
            assertEquals(0, wl.getAcls().size());
            assertEquals(0, wl.getOwnerActors().size());
        }
    }

    public void testRemoveAllACLs() {

        Worklist wl = _manager.getWorklist(_worklistID);
        assertNotNull(wl);
        assertEquals(_worklistID, wl.getWorklistID());

        wl.setAcls(null);

        _manager.updateWorklist(wl);
        log("removed.all.acls");

        // verify
        wl = null;
        wl = _manager.getWorklist(_worklistID);

        assertNotNull(wl);
        assertEquals(_worklistID, wl.getWorklistID());
        assertEquals(_wlName1,   wl.getName());
        assertEquals(_wlDesc1,   wl.getDesc());

        assertEquals(_owners.getActors(), wl.getOwnerActors());
        assertEquals(0, wl.getAcls().size());
        log("remove.all.acls.verified");
    }

    public void testDeleteWorklists() {

        IDListResult idl = _manager.deleteWorklists(new long[] {_worklistID});
        log("deleted");
        // verify
        assertEquals(0, idl.getFailedIDs().size());
        try {
            _manager.getWorklist(_worklistID);
            fail("Should have thrown Worklist does not exist exception");
        } catch (WorklistException e) {
            assertEquals(WorklistEC.EC_WL_NT_AVAILABLE, e.getErrorCode());
        }
        log("delete.verified");
    }

    @SuppressWarnings("unchecked")
    private boolean compareACLs(Collection uno, Collection dhos) {

        if (uno.size() != dhos.size()) {
            return false;
        }

        TreeSet set1 = new TreeSet(TaskACL.COMPARATOR);
        set1.addAll(uno);

        for (Iterator i = dhos.iterator(); i.hasNext();) {
            if (!set1.contains(i.next())) {
                return false;
            }
        }
        return true;
    }
}
