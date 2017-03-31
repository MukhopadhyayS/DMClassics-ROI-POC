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

package com.mckesson.eig.utility.components.model;

import java.util.ArrayList;

import junit.framework.TestCase;

/**
 * @author Sahul Hameed Y
 * @date   Apr 14, 2008
 * @since  HECM 1.0; Apr 14, 2008
 */
public class TestComponentList extends TestCase {
    
    /**
     * Reference of type <code>DomainList</code>
     */
    private ComponentList _componentList = null;

    /**
     * setUp method for the test case.
     * 
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        
        super.setUp();
        _componentList = new ComponentList();
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
     * Test method, tests DomainDOList Constructor and the getter methods 
     */
    public void testDomainDOList() {
        
        _componentList.setComponents(new ArrayList<ComponentInfo>());
        assertNotNull(_componentList.getComponents());
        
        ComponentList cList = new ComponentList(new ArrayList<ComponentInfo>());
        assertNotNull(cList);
    }
}
