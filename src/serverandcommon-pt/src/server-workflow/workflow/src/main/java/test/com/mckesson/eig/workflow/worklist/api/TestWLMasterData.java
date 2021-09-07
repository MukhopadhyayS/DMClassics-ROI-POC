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
 * @author N.Shah Ghazni
 * @date   Feb 7, 2008
 * @since  HECM 1.0; Feb 7, 2008
 */
public class TestWLMasterData 
extends junit.framework.TestCase {
    
    /**
     * Reference of type <code>WLMasterData</code>
     */    
    private WLMasterData _wlMasterData;
    
    /**
     * Default task duration.
     */
    private String DEFAULT_TASK_DURATION = "10 y";
    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory.getLogger(WLMasterData.class);

    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _wlMasterData = new WLMasterData();
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
     * Test method, tests whether the object of type <code>WLMasterData</code>
     * is null or not
     */
    public void testListWorklist() {

        LOG.debug("WLMasterData: " + _wlMasterData.toString());
        assertNotNull(_wlMasterData);
    }
    
    /**
     * Test method, tests WLMasterData Constructor and the getter methods 
     */
    public void testListWorklistConstructor() {
        
        _wlMasterData = new WLMasterData(new ArrayList(), new ArrayList());
        assertNotNull(_wlMasterData.getPriorityList());
        assertNotNull(_wlMasterData.getTaskStatusList());
    }
    
    /**
     * This method to test the setter and getter methods of 
     */
    public void testSetDefaultTaskDuration() {
       
       _wlMasterData.setDefaultTaskDuration(DEFAULT_TASK_DURATION);
       assertEquals(_wlMasterData.getDefaultTaskDuration(), DEFAULT_TASK_DURATION);
   }
}
