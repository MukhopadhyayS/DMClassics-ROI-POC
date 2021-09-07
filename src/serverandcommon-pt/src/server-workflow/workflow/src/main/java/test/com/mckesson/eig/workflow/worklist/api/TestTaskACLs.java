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

import java.util.HashSet;
import java.util.Set;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.workflow.api.Actor;

/**
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestTaskACLs 
extends junit.framework.TestCase {

    /**
     * Reference of type <code>TaskACLs</code>
     */    
    private TaskACLs _taskACLs;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestTaskACLs.class);

    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _taskACLs = new TaskACLs();
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
     * Test method, tests whether the object of type <code>TaskACLs</code>
     * is null or not
     */
    public void testTaskACLs() {

        LOG.debug("TaskACLs: " + _taskACLs.toString());
        assertNotNull(_taskACLs);
    }

    /**
     * Test method, testConstructorWithTaskACLs
     */
    public void testConstructorWithTaskACLs() {

        Set acls = new HashSet();
        _taskACLs = new TaskACLs(acls);
        assertEquals(acls, _taskACLs.getACLs());
    }

    /**
     * Test method, testAppentAuditcommentNull
     */
    public void testAppentAuditcommentNull() {

        StringBuffer sb = new StringBuffer();
        _taskACLs.appendAuditComment(sb);
        assertEquals(0, sb.length());
    }

    /**
     * Test method, testAppentAuditcomment
     */
    public void testAppentAuditcomment() {

        TaskACL acl = new TaskACL(true, false, true, false, new Actor("1.2.21"));
        Set<TaskACL> taskACLs = new HashSet<TaskACL>();
        taskACLs.add(acl);
        _taskACLs.setACLs(taskACLs);
        StringBuffer sb = new StringBuffer();
        _taskACLs.appendAuditComment(sb);
        assertTrue(sb.length() > 0);
    }
}
