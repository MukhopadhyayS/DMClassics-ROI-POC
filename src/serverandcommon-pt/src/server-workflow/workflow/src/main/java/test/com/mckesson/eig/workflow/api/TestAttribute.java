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
 *  Test class for Attribute. It tests the methods of Attribute class
 *  
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestAttribute
extends junit.framework.TestCase {

    /**
     * Reference of type <code>Attribute</code>
     */    
    private Attribute _attribute;

    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(TestAttribute.class);
    
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() 
    throws Exception {
        
        super.setUp();
        _attribute = new Attribute();
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
     * Test method, tests whether the object of type <code>Attribute</code>
     * is null or not
     */
    public void testAttribute() {

        LOG.debug("Attribute: " + _attribute.toString());
        assertNotNull(_attribute);
    }
    
    /**
     * Test method, testConstructorWithAtrribute
     */
    public void testConstructorWithAtrribute() {
    
        final String attribName = "name";
        _attribute = new Attribute(attribName);
        assertEquals(attribName, _attribute.getName());
    }
}
