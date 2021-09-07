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


import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;


/**
 * @author kayalvizhik
 * @date   Feb 20, 2009
 * @since  HECM 2.0; Feb 20, 2009
 */

public class TestProcessInfo extends TestCase {


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
     * tests the Process constructors
     */
    public void testConstructors() {

    	ProcessInfo processInfo = new ProcessInfo();
        assertNotNull(processInfo);
        assertEquals(0L, processInfo.getProcessId());
    }

    /**
     * tests the getter and setter methods
     */
    public void testGettersAndSetters() {

        final int entityType = 3;
        Actor actor = new Actor(1, entityType, 1);
        ProcessInfo processInfo = new ProcessInfo();
        Set<Actor> actors = new HashSet<Actor>();
        actors.add(actor);
        processInfo.setOwnerActors(actors);
        processInfo.setOwners(new Actors(actors));
        processInfo.setProcessId(1);

        ProcessActorACLS paACLs = new ProcessActorACLS();
        processInfo.setAssigned(paACLs);
        assertNotNull(processInfo.getAssigned());

        Set<ProcessActorACL> paACLSet = new HashSet<ProcessActorACL>();
        ProcessActorACL paACL = new ProcessActorACL();
        paACL.setProcessId(1);
        paACL.setActor(actor);
        paACL.setPermissionName("Assigned");
        paACLSet.add(paACL);
        processInfo.setAssignedActors(paACLSet);
        assertTrue(processInfo.getAssignedActors().size() > 0);

        Set<ProcessVersionInfo> pviSet = new HashSet<ProcessVersionInfo>();
        ProcessVersionInfo pvi = new ProcessVersionInfo();
        pviSet.add(pvi);
        processInfo.setProcessVersionInfo(pviSet);
        assertTrue(processInfo.getProcessVersionInfo().size() > 0);

        assertEquals(processInfo.getProcessId(), 1);
        assertTrue(processInfo.getOwners().getActors().size() > 0);
        assertTrue(processInfo.getOwnerActors().size() > 0);
        
        processInfo.setOwners(null);
        processInfo.setOwnerActors(null);
        assertNotNull(processInfo.getOwnerActors());
        assertNotNull(processInfo.getOwners());

        processInfo.setAssigned(null);
        assertNotNull(processInfo.getAssigned());

        processInfo.setAssignedActors(null);
        assertNotNull(processInfo.getAssignedActors());
    }

    /**
     * tests the getter and setter methods
     */
    public void testToString() {
        ProcessInfo testProcessInfo = new ProcessInfo();

        assertEquals("ProcessInfo[processId=0, "
            + "owners=set of Actors, "
            + "actors=set of Actors]", testProcessInfo.toString());

        testProcessInfo.setProcessId(1);

        Set<Actor> actorSet = new HashSet<Actor>(0);
        actorSet.add(new Actor());
        Actors actors = new Actors(actorSet);
        testProcessInfo.setOwnerActors(actorSet);
        testProcessInfo.setOwners(actors);
        assertEquals("ProcessInfo[processId=1, "
                + "owners=set of Actors, actors=set of Actors]", testProcessInfo.toString());
    }
}
