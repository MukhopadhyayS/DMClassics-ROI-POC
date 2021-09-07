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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.Variable;
import com.mckesson.eig.workflow.api.VariableList;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.process.ProcessListService;
import com.mckesson.eig.workflow.process.api.ProcessEC;
import com.mckesson.eig.workflow.process.api.ProcessInfoList;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author McKesson
 * @date   Feb 19, 2009
 * @since  HECM 2.0; Feb 19, 2009
 */

public class TestGetOwnedProcesses extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static ProcessListService _processListService;
    private static VariableList _variableList;
    private static Actors _owners;
    private static Actor _owner;
    private static Actor _invalidDomain;
    private static Actors _invalidOwners;

    private static final int APP_ID = 1;
    private static final int ET_DOMAIN = 1;

    public void testSetup() {
        final int domainID = 5001;
        long seed = System.currentTimeMillis();
        init();
        _variableList = new VariableList();
        List<Variable> variables = new ArrayList<Variable>();
        Variable v1 = new Variable();
        v1.setKey("PROCESS_TYPE");
        v1.setValue("BOTH");
        variables.add(v1);

        Variable v2 = new Variable();
        v2.setKey("PROCESS_TYPE");
        v2.setValue("SYSTEM");
        variables.add(v2);

        _variableList.setVariables(variables);

        _owner = new Actor(APP_ID, ET_DOMAIN, domainID);
        _invalidDomain = new Actor(APP_ID, -1, (seed + 1));

        Set<Actor> ownerSet = new HashSet<Actor>(1);
        ownerSet.add(_owner);
        _owners = new Actors(ownerSet);

        Set<Actor> invalidOwnerSet = new HashSet<Actor>(1);
        invalidOwnerSet.add(_invalidDomain);
        _invalidOwners = new Actors(invalidOwnerSet);

        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(new Long(1));
        _processListService = (ProcessListService) getManager(PROCESS_LIST_MANAGER);
    }

    public void testGetOwnedProcessesWithExpiredProcesses() {

        ProcessInfoList processList = _processListService.getOwnedProcesses(1,
                true, _owners, _variableList);
        assertTrue(processList.getProcessList().size() > 0);
    }

    public void testGetOwnedProcessesWithOutExpiredProcesses() {
        ProcessInfoList processList = _processListService.getOwnedProcesses(1,
                false, _owners, _variableList);
        assertTrue(processList.getProcessList().size() == 0);
    }

    public void testGetOwnedProcessesWithInvalidApplicationID() {

        try {
            _processListService.getOwnedProcesses(-1, true, _owners, _variableList);
            fail("Should have thrown EC_INVALID_APPLICATION_ID exception");
        } catch (WorkflowException we) {
            assertEquals(ProcessEC.EC_INVALID_APPLICATION_ID, we.getErrorCode());
        }
    }

    public void testGetOwnedProcessesWithInvalidProcessType() {

        List<Variable> variables = new ArrayList<Variable>();

        Variable v1 = new Variable();
        v1.setKey("PROCESS_TYPE");
        v1.setValue("test");
        variables.add(v1);

        _variableList.setVariables(variables);
        try {

            _processListService.getOwnedProcesses(1, true, _owners, _variableList);
            fail("Should have thrown EC_INVALID_PROCESS_TYPE exception");
        } catch (WorkflowException we) {
            assertEquals(ProcessEC.EC_INVALID_PROCESS_TYPE, we.getErrorCode());
        }
    }

    public void testGetOwnedProcessesWithNullProcessType() {

        try {
            _processListService.getOwnedProcesses(1, true, _owners, null);
            fail("Should have thrown EC_PROCESS_TYPE_NOT_SPECIFIED exception");
        } catch (WorkflowException we) {
            assertEquals(ProcessEC.EC_PROCESS_TYPE_NOT_SPECIFIED, we
                    .getErrorCode());
        }

        VariableList variableList = new VariableList();
        variableList.setVariables(new ArrayList<Variable>());
        try {
            _processListService.getOwnedProcesses(1, true, _owners, variableList);
            fail("Should have thrown EC_PROCESS_TYPE_NOT_SPECIFIED exception");
        } catch (WorkflowException we) {
            assertEquals(ProcessEC.EC_PROCESS_TYPE_NOT_SPECIFIED, we
                    .getErrorCode());
        }
    }

    public void testGetOwnedProcessesWithNullOwners() {
        try {
            _processListService.getOwnedProcesses(1, true, null, _variableList);
            fail("Should have thrown EC_NULL_ACTORS exception");
        } catch (WorkflowException we) {
            assertEquals(WorklistEC.EC_NULL_ACTORS, we.getErrorCode());
        }

    }

    public void testGetOwnedProcessesWithInvalidOwners() {
        try {
            _processListService.getOwnedProcesses(1, true,
                    _invalidOwners, _variableList);
            fail("Should have thrown EC_INVALID_ACTOR exception");
        } catch (WorkflowException we) {
            assertEquals(WorklistEC.EC_INVALID_ACTOR, we.getErrorCode());
        }

    }
}
