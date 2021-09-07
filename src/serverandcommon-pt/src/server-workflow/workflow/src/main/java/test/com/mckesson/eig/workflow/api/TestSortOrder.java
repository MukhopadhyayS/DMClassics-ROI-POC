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
 * Test class for SortOrder. It tests the methods of SortOrder class
 * 
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestSortOrder
extends junit.framework.TestCase {

    /**
     * Reference of type <code>SortOrder</code>
     */    
    private SortOrder _sortOrder;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestSortOrder.class);

    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() 
    throws Exception {
        
        super.setUp();
        _sortOrder = new SortOrder();
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
     * Test method, tests whether the object of type <code>SortOrder</code>
     * is null or not
     */
    public void testSortOrder() {

        LOG.debug("SortOrder: " + _sortOrder.toString());
        assertNotNull(_sortOrder);
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>SortOrder</code>
     */    
    public void testGetAttribute() {

        Attribute attr = new Attribute();
        attr.setName("name");
        _sortOrder.setAttr(attr);
        assertEquals(attr, _sortOrder.getAttr());
    }

    /**
     * Test method, tests the getter and setter methods of
     * <code>SortOrder</code>
     */    
    public void testGetIsDesc() {
        
        _sortOrder.setIsDesc(true);
        assertEquals(true, _sortOrder.getIsDesc());
    }

    /**
     * Test method, testConstructorWithAttrubute
     */
    public void testConstructorWithAttrubute() {

        Attribute attr = new Attribute();
        attr.setName("name");
        SortOrder sortOrder = new SortOrder(attr);
        assertEquals(attr, sortOrder.getAttr());
    }
}
