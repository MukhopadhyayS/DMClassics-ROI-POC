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

package com.mckesson.eig.config.service;

import junit.framework.TestCase;

import com.mckesson.eig.config.audit.UnitSpringInitialization;
import com.mckesson.eig.utility.components.model.ComponentInfo;
import com.mckesson.eig.utility.components.model.ComponentList;
import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;

/**
 * @author Sahul Hameed Y
 * @date   Apr 10, 2008
 * @since  HECM 1.0; Apr 10, 2008
 */
public class TestComponentServiceImpl extends TestCase {
    
    private static ComponentServiceImpl _componentServiceImpl;
    /**
     * setUp method for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        
        System.setProperty("CONFIG_HOME", "src\\test");
        super.setUp();
        UnitSpringInitialization.init();
        
        _componentServiceImpl = new ComponentServiceImpl();
        
    }
    
    public void testGetAllComponents() {
        
        ComponentList componentList = _componentServiceImpl.getAllComponents();
        assertTrue(componentList.getComponents().size() > 0);
    }
    
    public void testGetComponents() {
        
        String[] componentID = {"audit", "workflow", "hecm", "database", "content.loader"};
        
        for (int i = 0; i < componentID.length; i++) {
            
            ComponentInfo componentInfo = _componentServiceImpl.getComponent(componentID[i]);
            assertEquals(componentInfo.getComponentID(), componentID[i]);
        }
    }
    
    public void testGetNullComponent() {
        
        try {
            _componentServiceImpl.getComponent(null);
        } catch (ApplicationException ae) {
            assertEquals(ClientErrorCodes.NULL_COMPONENT_ID, ae.getErrorCode());
        }
    }
    
    public void testGetInvalidComponentID() {
        
        try {
            _componentServiceImpl.getComponent("test");
        } catch (ApplicationException ae) {
            assertEquals(ClientErrorCodes.INVALID_COMPONENT_ID, ae.getErrorCode());
        }
    }
}
