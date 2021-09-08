/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.workflow.process.api;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;

/**
 * @author sahuly
 * @date   Feb 28, 2009
 * @since  HECM 2.0; Feb 28, 2009
 */
public class TestProcess extends TestCase {
    
    /**
     * setUp method for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
     }

    /**
     * Method tearDown() removes the data from Database.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * tests the ProcessVersion constructors
     */
    public void testConstructors() {

        Process process = new Process();
        assertNotNull(process);
        assertEquals(0L, process.getProcessId());
    }
    
    public void testGettersAndSetters() {

        Date currentDate = new Date();
        Process process = new Process();
        process.setProcessId(1);
        process.setCreateDateTime(currentDate);
        process.setModifiedDateTime(currentDate);
        process.setModifiedUserId(new Long(1));

        Set<ProcessVersion> processVersions = new HashSet<ProcessVersion>();
        ProcessVersion pv = new ProcessVersion();
        processVersions.add(pv);
        process.setProcessVersions(processVersions);

        Set<Actor> ownerActors = new HashSet<Actor>();
        Actor actor = new Actor();
        actor.setActorID(1);
        ownerActors.add(actor);

        Actors actors = new Actors(ownerActors);
        process.setOwnerActors(ownerActors);
        process.setOwners(actors);

        ProcessActorACL processActorACL = new ProcessActorACL();
        Set<ProcessActorACL> processActorACLS = new HashSet<ProcessActorACL>();
        processActorACLS.add(processActorACL);
        process.setAssignedActors(processActorACLS);

        ProcessActorACLS processActorACLs = new ProcessActorACLS(processActorACLS);
        process.setProcessACLS(processActorACLs);

        assertFalse(process.getProcessVersions().isEmpty());
        assertFalse(process.getOwnerActors().isEmpty());
        assertFalse(process.getProcessACLS().getProcessActorACLS().isEmpty());
        assertFalse(process.getOwners().getActors().isEmpty());
        assertFalse(process.getAssignedActors().isEmpty());
        assertEquals(1, process.getProcessId());
        assertEquals(currentDate, process.getModifiedDateTime());
        assertEquals(currentDate, process.getCreateDateTime());
        assertEquals(new Long(1), process.getModifiedUserId());
        
        process.setOwnerActors(null);
        process.setAssignedActors(null);
        assertNotNull(process.getOwnerActors());
        assertNotNull(process.getAssignedActors());
    }
}
