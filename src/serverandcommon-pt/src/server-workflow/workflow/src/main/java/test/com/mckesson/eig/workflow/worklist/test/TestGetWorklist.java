/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.workflow.worklist.test;

import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.service.WorklistService;


/**
 * @author Pranav Amarasekaran
 * @date   Sep 27, 2007
 * @since  HECM 1.0; Sep 27, 2007
 */
public class TestGetWorklist
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static WorklistService _manager;

    public void testSetUp() {

        init();
        _manager = (WorklistService) getManager(WORKLIST_MANAGER);
    }

    /**
     * This method is used to test get worklist functionality with invalid ID.
     */
    public void testGetWorklistWithInvalidID() {

       try {
           _manager.getWorklist(-1);
           fail("Should have thrown EC_INVALID_WORKLIST_ID exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_WORKLIST_ID,
               we.getErrorCode());
       }
    }

    /**
     * This method is used to test get worklist functionality with non
     * existing ID.
     */
    public void testGetWorklistWithNonExistingID() {

       try {
           _manager.getWorklist(Long.MAX_VALUE);
           fail("Should have thrown EC_WL_NT_AVAILABLE exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_WL_NT_AVAILABLE,
               we.getErrorCode());
       }
    }
}
