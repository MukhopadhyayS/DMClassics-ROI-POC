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

package com.mckesson.eig.workflow.api;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
 * Test class for Actor. It tests the methods of Actor class
 * 
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestActor 
extends junit.framework.TestCase {

    /**
     * Reference of type <code>Actor</code>
     */    
    private Actor _actor;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestActor.class);

    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _actor = new Actor();
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
     * Test method, tests whether the object of type <code>Actor</code>
     * is null or not
     */
    public void testActor() {

        LOG.debug("Actor: " + _actor.toString());
        assertNotNull(_actor);
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>Actor</code>
     */
    public void testActorID() {

        final long actorId = 1001;
        _actor.setActorID(actorId);
        assertEquals(_actor.getActorID(), actorId);
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>Actor</code>
     */
    public void testAppID() {

        final int appId = 1;
        _actor.setAppID(appId);
        assertEquals(_actor.getAppID(), appId);
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>Actor</code>
     */
    public void testEntityType() {

        final int entityType = 3;
        _actor.setEntityType(entityType);
        assertEquals(_actor.getEntityType(), entityType);
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>Actor</code>
     */
    public void testEntityID() {

        final int entityId = 3;
        _actor.setEntityID(entityId);
        assertEquals(_actor.getEntityID(), entityId);
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>Actor</code>
     */
    public void testVersion() {

        final int version = 1;
        _actor.setVersion(version);
        assertEquals(_actor.getVersion(), version);
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>Actor</code>
     */
    public void testActorConstructor() {

        Actor actor = new Actor(_actor.getAppID(), _actor.getEntityType(), _actor.getEntityID());
        actor.setActorID(1);
        _actor.setActorID(1);
        assertEquals(true, _actor.equals(actor));
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>Actor</code>
     */
    public void testActorConstructorWithActorInfo() {
        
        Actor actor = new Actor(_actor.toString());
        assertEquals(actor.toString(), _actor.toString());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>Actor</code>
     */
    public void testActorNotEqual() {

        Integer actor = 1;
        _actor.setActorID(1);
        assertEquals(false, _actor.equals(actor));
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>Actor</code>
     */
    public void testHashCode() {

        final int hashCode = _actor.hashCode();
        assertEquals(_actor.hashCode(), hashCode);
    }
}
