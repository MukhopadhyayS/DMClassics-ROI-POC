/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.workflow.process.test;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.process.ProcessListService;
import com.mckesson.eig.workflow.process.api.ProcessEC;
import com.mckesson.eig.workflow.process.api.ProcessInfoList;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author McKesson
 * @date   Feb 11, 2009
 * @since  HECM 2.0; Feb 11, 2009
 */
public class TestGetAssignedProcesses
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static ProcessListService _processListService;

    private static final int APP_ID    = 1;
    private static final int ET_USER   = 3;

    private static Actor _assignedActor;

    public void testSetup() {

        init();

        _assignedActor = new Actor(APP_ID, ET_USER, 1);
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(new Long(1));
        _processListService = (ProcessListService) getManager(PROCESS_LIST_MANAGER);
    }

    public void testGetAssignedProcesses() {

        ProcessInfoList processList = 
            	_processListService.getAssignedProcesses(APP_ID, _assignedActor);
        assertTrue(processList.getProcessList().size() > 0);
    }

    public void testGetAssignedProcessesWithNullActor() {

        try {

            ProcessInfoList processList = _processListService.getAssignedProcesses(APP_ID, null);
            assertTrue(processList.getProcessList().size() > 0);
            fail("Should have thrown EC_NULL_ACTOR exception");
        } catch (WorkflowException we) {
            assertEquals(WorkflowEC.EC_NULL_ACTOR, we.getErrorCode());
        }
    }

    public void testGetAssignedProcessesWithInvalidApplicationID() {

        try {

            ProcessInfoList processList = 
                _processListService.getAssignedProcesses(-1, _assignedActor);
            assertTrue(processList.getProcessList().size() > 0);
            fail("Should have thrown EC_INVALID_APPLICATION_ID exception");
        } catch (WorkflowException we) {
            assertEquals(ProcessEC.EC_INVALID_APPLICATION_ID, we.getErrorCode());
        }
    }
}
