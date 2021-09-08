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

package com.mckesson.eig.workflow.worklist.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;

/**
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestWorklist 
extends junit.framework.TestCase {
    
    private static Worklist _worklist;
    
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _worklist = new Worklist();
    }
    
    /**
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown()
    throws Exception {
        super.tearDown();
    }
    
    /**
     * Test Methods, test the getter and setter of the worklist.
     */
    public void testSetAndGetWorklist() {
        
        _worklist.setName("New");
        _worklist.setDesc("New");
        _worklist.setAssignedTo(new TaskACLs());
        _worklist.setOwnerActors(new HashSet());
        _worklist.setOwners(new Actors());
        HashMap<String, Long> statusMap = new HashMap<String, Long>();
        statusMap.put("key", new Long(1));
        _worklist.setStatusMap(statusMap);
        _worklist.setTasks(new HashSet());
        _worklist.setTotalTaskCount(1);
         List<StatusCountPair> statusCountList = new ArrayList<StatusCountPair>();
         statusCountList.add(new StatusCountPair("key", new Long(1)));
        _worklist.setStatusList(statusCountList);
        _worklist.setAcls(new HashSet<TaskACL>());

        _worklist.setVersion(1);
        _worklist.setWorklistID(1);
        
        assertNotNull(_worklist.getAcls());
        assertNotNull(_worklist.getAssignedTo());
        assertNotNull(_worklist.getDesc());
        assertNotNull(_worklist.getName());
        assertNotNull(_worklist.getOwnerActors());
        assertNotNull(_worklist.getOwners());
        assertNotNull(_worklist.getStatusMap());
        assertNotNull(_worklist.getTasks());
        assertNotNull(_worklist.getActiveTaskCount());
        assertNotNull(_worklist.getOwnerID());
        assertNotNull(_worklist.getTotalTaskCount());
        assertNotNull(_worklist.getVersion());
        assertNotNull(_worklist.getWorklistID());
        assertNotNull(_worklist.getStatusList());
    }
    
    public void testWorklistOwnerActorsAndStatusCount() {

        Worklist wl = new Worklist();
        wl.setStatusMap(new HashMap<String, Long>());
        List<StatusCountPair> statusList = new ArrayList<StatusCountPair>();
        statusList.add(new StatusCountPair("key", new Long(1)));
        wl.setStatusList(statusList);
        wl.getStatusList();
        wl.setStatusList(statusList);
        wl.setOwnerActors(new HashSet<Actor>());
        assertNotNull(wl.getStatusMap());
        assertNotNull(wl.getAcls());
        assertNotNull(wl.getOwnerActors());
    }
    
    /**
     * Test Methods, test the audit comment.
     */
    public void testAuditComment() {
        
        String auditComment = _worklist.toAuditComment();
        assertNotNull(auditComment);
    }
    
    /**
     * Test Methods, test the toString , hascode and equals methods.
     */
    public void testWorklist() {
        
        Worklist worklist = new Worklist();
        worklist.setWorklistID(1);
        
        assertNotNull(_worklist.toString());
        assertNotNull(_worklist.hashCode());
        assertNotNull(_worklist.equals(_worklist));
        
        Worklist worklist1 = new Worklist();
        worklist1.setWorklistID(-1);
        
        assertNotNull(_worklist.toString());
        assertNotNull(_worklist.hashCode());
        assertNotNull(_worklist.equals(_worklist));
    }
    
    /**
     * Test Methods, test with empty and null ownerActors.
     */
    public void testEmptyAndNullOWnerActors() {
        
        Worklist worklist = new Worklist();
        worklist.setOwnerActors(null);
        worklist.getOwnerID();
        assertNotNull(worklist.getOwnerActors());
        
        Set<Actor> ownerActors = new HashSet<Actor>();
        ownerActors.add(new Actor(1, 1, 1));
        worklist.setOwnerActors(ownerActors);
        worklist.getOwnerID();
        
        assertNotNull(worklist);
    }
    
    /**
     * Test Methods, test with null acls.
     */
    public void testNullACLS() {
        
        Worklist worklist = new Worklist();
        worklist.setAcls(null);
        assertNotNull(worklist.getAcls());
    }
    
    /**
     * Test Methods, test the possible validation.
     */
    public void testValidation() {
        
        String name = "Never find such a person in the world, Never find such a person "
                    + "in the world, Never find such a person in the world, Never find "
                    + "such a person in the world,Never find such a person in the world, "
                    + "Never find such a person in the world, Never find such a person in "
                    + "the world, Never find such a person in the world, Never find such a "
                    + "person in the world, Never find such a person in the world, Never find"
                    + "such a person in the world";
        
        Worklist wl = new Worklist();
        wl.setName(null);
        wl.validate();
        
        wl.setName("");
        wl.validate();
        
        wl.setName("new");
        wl.setDesc(null);
        wl.validate();
        
        wl.setName("new");
        wl.setDesc("");
        wl.validate();
        
        wl.setName(name);
        wl.setDesc("new");
        wl.validate();
        
        wl.setName("name");
        wl.setDesc(name);
        wl.validate();
        
        wl.setName("name");
        wl.setDesc("name");
        wl.setOwnerActors(new HashSet());
        wl.validate();
        
        wl.setName("name");
        wl.setDesc("name");
        Set<Actor> ownerActors = new HashSet<Actor>(1);
        ownerActors.add(new Actor());
        wl.setOwnerActors(ownerActors);
        wl.validate();
    }
}

