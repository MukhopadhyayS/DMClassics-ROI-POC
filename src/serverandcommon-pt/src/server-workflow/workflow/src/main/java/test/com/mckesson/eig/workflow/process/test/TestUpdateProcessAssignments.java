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

import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.workflow.api.Actor;
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
public class TestUpdateProcessAssignments
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static ProcessListService _processListService;

    private static final int APP_ID    = 1;
    private static final int ET_USER   = 3;

    private static Actor _assignedActor;
    private static List<Long> _processListIds;

    public void testSetup() {

        init();

        _assignedActor  = new Actor(APP_ID, ET_USER, 1);
        _processListIds = new ArrayList<Long>();

        _processListIds.add(new Long(1));
        _processListIds.add(new Long(2));

        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(new Long(1));
        _processListService = (ProcessListService) getManager(PROCESS_LIST_MANAGER);
    }

    public void testUpdateProcessAssignments() {


        _processListService.updateProcessAssignments(_assignedActor, _processListIds);
        ProcessInfoList processList = _processListService.getAssignedProcesses(1, _assignedActor);
        assertEquals(2, processList.getProcessList().size());

        //unassign process
        _processListIds.remove(0);
        final int processID = 3;
        _processListIds.add(new Long(processID));
        _processListService.updateProcessAssignments(_assignedActor, _processListIds);
        processList = _processListService.getAssignedProcesses(1, _assignedActor);
        assertEquals(2, processList.getProcessList().size());
    }

    public void testUpdateProcessAssignmentsWithInvalidProcessID() {

        try {

            List<Long> processListIds = new ArrayList<Long>();
            processListIds.add(new Long(-1));
            _processListService.updateProcessAssignments(_assignedActor, processListIds);
            fail("Should have thrown EC_INVALID_PROCESS_ID exception");
        } catch (WorkflowException we) {
            assertEquals(ProcessEC.EC_INVALID_PROCESS_ID, we.getErrorCode());
        }
    }

    public void testUpdateProcessAssignmentsWithUnavailableProcess() {

        try {

            List<Long> processListIds = new ArrayList<Long>();
            processListIds.add(new Long(Long.MAX_VALUE));
            _processListService.updateProcessAssignments(_assignedActor, processListIds);
            fail("Should have thrown EC_PROCESS_NOT_AVAILABLE exception");
        } catch (WorkflowException we) {
            assertEquals(ProcessEC.EC_PROCESS_NOT_AVAILABLE, we.getErrorCode());
        }
    }
}
