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

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * Test class for ListACLs. It tests the methods of ListACLs class
 * 
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestListACLS
extends junit.framework.TestCase {
    
    private ListACLs _listACLS;
    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(ListACLs.class);
    
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _listACLS = new ListACLs();
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
     * Test method, tests whether the object of type <code>ListACLs</code>
     * is null or not
     */
    public void testListACLs() {

        LOG.debug("ListACLs: " + _listACLS.toString());
        assertNotNull(_listACLS);
    }
    
    /**
     * Test method, tests ListACLs Constructor and the getter methods 
     */
    public void testListACLsConstructor() {
        
        _listACLS = new ListACLs(new ArrayList());
        assertNotNull(_listACLS.getACLs());
    }
}
