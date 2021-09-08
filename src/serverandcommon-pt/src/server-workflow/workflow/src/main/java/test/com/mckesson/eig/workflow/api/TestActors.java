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

import java.util.HashSet;
import java.util.Set;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
 * Test class for Actors. It tests the methods of Actors class
 * 
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestActors 
extends junit.framework.TestCase {

    /**
     * Reference of type <code>Actors</code>
     */    
    private Actors _actors;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestActors.class);
    
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {
        
        super.setUp();
        _actors = new Actors();
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
     * Test method, tests whether the object of type <code>Actors</code>
     * is null or not
     */
    public void testActors() {

        LOG.debug("Actors: " + _actors.toString());
        assertNotNull(_actors);
    }

    /**
     * Test method, testConstructorWithActors
     */
    public void testConstructorWithActors() {
        
        Set actorsSet = new HashSet();
        Actors actors = new Actors(actorsSet);
        assertEquals(actorsSet, actors.getActors());
    }

    /**
     * Test method, testGetLongIDsWithNullActors
     */
    public void testGetLongIDsWithNullActors() {
        
        _actors.setActors(null);
        assertEquals(0, _actors.getActorIDs().length);
    }
    
    /**
     * Test method, testGetLongIDsWithActos
     */
    public void testGetLongIDsWithActos() {
        
        Set<Actor> actorsSet = new HashSet<Actor>();
        actorsSet.add(new Actor("1.3.21"));
        actorsSet.add(new Actor("1.3.22"));
        _actors.setActors(actorsSet);
        assertEquals(2, _actors.getActorIDs().length);
    }

    /**
     * Test method, testGetAllActorDetailsWithNullActors
     */
    public void testGetAllActorDetailsWithNullActors() {
        
        _actors.setActors(null);
        assertEquals(0, _actors.getAllActorDetails().length);
    }

    /**
     * Test method, testGetAllActorDetailsWithActors
     */
    public void testGetAllActorDetailsWithActors() {
        
        Set<Actor> actorsSet = new HashSet<Actor>();
        actorsSet.add(new Actor("1.3.21"));
        actorsSet.add(new Actor("1.3.22"));
        _actors.setActors(actorsSet);
        assertEquals(2, _actors.getAllActorDetails().length);
    }
    
    /**
     * Test method, testAppendAuditCommentsWithNullActors
     */
    public void testAppendAuditCommentsWithNullActors() {
        
        Actor actor = new Actor(1, 1, 1);
        Set<Actor> actors = new HashSet<Actor>();
        actors.add(actor);
        
        _actors.setActors(actors);
        StringBuffer sb = new StringBuffer();
        _actors.appendAuditComment(sb, "ADMIN");
        assertFalse(sb.length() == 0);
    }
    
    /**
     * Test method, testAppendAuditCommentsWithActos
     */
    public void testAppendAuditCommentsWithActos() {
        
        Set<Actor> actorsSet = new HashSet<Actor>();
        actorsSet.add(new Actor("1.3.21"));
        actorsSet.add(new Actor("1.3.22"));
        _actors.setActors(actorsSet);
        
        StringBuffer sb = new StringBuffer();
        _actors.appendAuditComment(sb, "ADMIN");
        assertFalse(sb.length() == 0);
    }
}
