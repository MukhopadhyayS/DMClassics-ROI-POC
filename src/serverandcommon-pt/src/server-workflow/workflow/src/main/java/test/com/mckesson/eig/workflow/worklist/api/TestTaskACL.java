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

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.workflow.api.Actor;



/**
 * Test class for TaskACL. It tests the methods of TaskACL class
 * 
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */

public class TestTaskACL 
extends junit.framework.TestCase {

    /**
     * Reference of type <code>TaskACL</code>
     */    
    private TaskACL _taskACL;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestTaskACL.class);

    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _taskACL = new TaskACL();
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
     * Test method, tests whether the object of type <code>TaskACL</code>
     * is null or not
     */
    public void testTaskACL() {

        _taskACL.setActor(new Actor());
        LOG.debug("TaskACL: " + _taskACL.toString());
        assertNotNull(_taskACL);
    }
    
    /**
     * Test method, tests the getter and setter methods of
     * <code>TaskACL</code>
     */
    public void testTaskACLID() {

        final long aclId = 1000;
        _taskACL.setTaskACLID(aclId);
        assertEquals(aclId, _taskACL.getTaskACLID());
    }
    
    /**
     * Test method, tests the getter and setter methods of
     * <code>TaskACL</code>
     */
    public void testActor() {

        final long actorId = 100;
        Actor actor = new Actor();
        actor.setActorID(actorId);
        _taskACL.setActor(actor);
        assertEquals(actorId, _taskACL.getActor().getActorID());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>TaskACL</code>
     */
    public void testWorklist() {

        Worklist wl = new Worklist();
        _taskACL.setWorklist(wl);
        assertEquals(wl, _taskACL.getWorklist());
    }
    
    /**
     * Test method, tests the getter and setter methods of
     * <code>TaskACL</code>
     */
    public void testCanComplete() {

        _taskACL.setCanComplete(true);
        assertEquals(true, _taskACL.getCanComplete());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>TaskACL</code>
     */
    public void testCanCreate() {

        _taskACL.setCanCreate(true);
        assertEquals(true, _taskACL.getCanCreate());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>TaskACL</code>
     */
    public void testCanReassign() {

        _taskACL.setCanReassign(true);
        assertEquals(true, _taskACL.getCanReassign());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>TaskACL</code>
     */
    public void testCanDelete() {

        _taskACL.setCanDelete(true);
        assertEquals(true, _taskACL.getCanDelete());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>TaskACL</code>
     */
    public void testDoEmailAlert() {
    	
    	_taskACL.setDoEmailAlert(true);
    	assertEquals(true, _taskACL.getDoEmailAlert());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>TaskACL</code>
     */
    public void testHasMerged() {
    	
    	_taskACL.setHasMerged(true);
    	assertEquals(true, _taskACL.getHasMerged());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>TaskACL</code>
     */
    public void testVersion() {

        final int version = 1;
        _taskACL.setVersion(version);
        assertEquals(version, _taskACL.getVersion());
    }

    /**
     * Test method, testHashCodeWithNullWorklistOrActor
     */
    public void testHashCodeWithNullWorklistOrActor() {
        assertEquals(0, _taskACL.hashCode());
    }

    /**
     * Test method, testHashCodeWithWorklistAndActor
     */
    public void testHashCodeWithWorklistAndActor() {

        _taskACL.setActor(new Actor());
        _taskACL.setWorklist(new Worklist());
        assertNotNull(_taskACL.hashCode());
    }

    /**
     * Test method, testAppendAuditComment
     */
    public void testAppendAuditComment() {

        StringBuffer sb = new StringBuffer();
        _taskACL.appendAuditComment(sb);
        assertTrue(sb.length() > 0);
    }

    /**
     * Test method, testTaskACLEquals
     */
    public void testTaskACLEquals() {

        final long aclId = 1000;
        TaskACL taskAcl = new TaskACL();
        taskAcl.setTaskACLID(aclId);
        _taskACL.setTaskACLID(aclId);
        assertTrue(_taskACL.equals(taskAcl));
    }

    /**
     * Test method, testTaskACLEqualsWithException
     */
    public void testTaskACLEqualsWithException() {

        final long aclId = 1000;
        Integer taskAcl = 1;
        _taskACL.setTaskACLID(aclId);
        assertFalse(_taskACL.equals(taskAcl));
    }

    /**
     * Test method, testConstructorWithACLs
     */
    public void testConstructorWithACLs() {

        _taskACL = new TaskACL(true, false, true, false);
        assertTrue(_taskACL.getCanComplete());
    }

    /**
     * Test method, testConstructorWithACLsAndActor
     */
    public void testConstructorWithACLsAndActor() {
        
        Actor actor = new Actor("1.2.21");
        _taskACL = new TaskACL(true, false, true, false, actor);
        assertEquals(actor.getEntityID(), _taskACL.getActor().getEntityID());
    }
    
    /**
     * Test method, testTaskACLComparator
     */
    public void testTaskACLComparatorEqual() {
        
        Actor actor = new Actor("1.2.21");
        Worklist wl = new Worklist();
        _taskACL = new TaskACL(true, false, true, false, actor);
        _taskACL.setWorklist(wl);

        TaskACL taskACL = new TaskACL(true, false, true, false, actor);
        taskACL.setWorklist(wl);
        
        assertEquals(0, TaskACL.COMPARATOR.compare(_taskACL, taskACL));
    }

    /**
     * Test method, testTaskACLComparator
     */
    public void testTaskACLComparatorNotEqual() {
        
        Actor actor = new Actor("1.2.21");
        Worklist wl = new Worklist();
        _taskACL = new TaskACL(true, false, true, false, actor);
        _taskACL.setWorklist(wl);

        TaskACL taskACL = new TaskACL(true, true, false, false, actor);
        taskACL.setWorklist(wl);
        
        assertEquals(-1, TaskACL.COMPARATOR.compare(_taskACL, taskACL));
    }

    /**
     * Test method, testTaskACLEquals
     */
    public void testTaskACLAcls() {

        final long aclId = 1001;
        TaskACL taskAcl = new TaskACL();
        taskAcl.setTaskACLID(aclId);
        _taskACL.setTaskACLID(aclId);
        assertTrue(_taskACL.equals(taskAcl));
    }
}
