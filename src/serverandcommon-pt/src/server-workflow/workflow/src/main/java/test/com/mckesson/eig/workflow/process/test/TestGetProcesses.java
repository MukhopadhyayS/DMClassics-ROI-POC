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
 *
 * @author McKesson
 * @date   Feb 11, 2009
 * @since  HECM 2.0; Feb 11, 2009
 *
 */
public class TestGetProcesses extends
        com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static Actors _owners;
    private static Actors _users;
    private static VariableList _variables;
    private static final String PROCESS_TYPE = "PROCESS_TYPE";
    private static final String PROCESS_TYPE_VALUE = "BOTH";
    private static final String INVALID_PROCESS_TYPE = "TRIGGER_TYPE";
    private static final String INVALID_PROCESS_TYPE_VALUE = "xyz";
    private static Actor _invalidDomain;
    private static Actors _invalidOwners;
    private static Variable _invalidVariable;
    private static VariableList _invalidVariables;

    private static Actor _owner;
    private static Actor _user;
    private static Variable _variable;

    private static final int APP_ID = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_USER = 3;

    private static ProcessListService _processListService;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();
        final int domainID = 5001;
        _owner = new Actor(APP_ID, ET_DOMAIN, domainID);
        _user = new Actor(APP_ID, ET_USER, 1);
        _invalidDomain = new Actor(APP_ID, -1, (seed + 1));

        _variable = new Variable();
        _variable.setKey(PROCESS_TYPE);
        _variable.setValue(PROCESS_TYPE_VALUE);

        _invalidVariable = new Variable();
        _invalidVariable.setKey(INVALID_PROCESS_TYPE);
        _invalidVariable.setValue(INVALID_PROCESS_TYPE_VALUE);

        Set<Actor> ownerSet = new HashSet<Actor>(1);
        ownerSet.add(_owner);
        _owners = new Actors(ownerSet);

        Set<Actor> invalidOwnerSet = new HashSet<Actor>(1);
        invalidOwnerSet.add(_invalidDomain);
        _invalidOwners = new Actors(invalidOwnerSet);

        Set<Actor> userSet = new HashSet<Actor>(1);
        userSet.add(_user);
        _users = new Actors(userSet);

        List<Variable> variableList = new ArrayList<Variable>(0);
        variableList.add(_variable);
        _variables = new VariableList();
        _variables.setVariables(variableList);

        List<Variable> invalidVariableList = new ArrayList<Variable>(0);
        invalidVariableList.add(_invalidVariable);
        _invalidVariables = new VariableList();
        _invalidVariables.setVariables(invalidVariableList);

        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user.getEntityID());
        
        WsSession.setSessionData("Actors", _users);
        _processListService = (ProcessListService) getManager(PROCESS_LIST_MANAGER);

    }

    public void testGetProcesses() {

        ProcessInfoList processList = _processListService.getProcesses(_owners, _users, _variables);
        assertTrue(processList.getProcessList().size() > 0);
    }

    public void testWithInvalidOwners() {
        try {
            _processListService.getProcesses(_invalidOwners, _users, _variables);
            fail("Should have thrown EC_INVALID_ACTOR exception");
        } catch (WorkflowException we) {
            assertEquals(WorklistEC.EC_INVALID_ACTOR, we.getErrorCode());
        }

    }

    public void testWithNullDomain() {
        try {
            _processListService.getProcesses(null, _users, _variables);
            fail("Should have thrown EC_NULL_ACTORS exception");
        } catch (WorkflowException we) {
            assertEquals(WorklistEC.EC_NULL_ACTORS, we.getErrorCode());
        }

    }

    public void testWithNullUserAndGroups() {

        try {
            _processListService.getProcesses(_owners, null, _variables);
            fail("Should have thrown EC_NULL_ACTORS exception");
        } catch (WorkflowException we) {
            assertEquals(WorklistEC.EC_NULL_ACTORS, we.getErrorCode());
        }
    }

    public void testWithInvalidProcessType() {
        try {
            _processListService.getProcesses(_owners, _users, _invalidVariables);
            fail("Should have thrown EC_INVALID_PROCESS_TYPE exception");
        } catch (WorkflowException we) {
            assertEquals(ProcessEC.EC_INVALID_PROCESS_TYPE, we.getErrorCode());
        }
    }

    public void testWithNullProcessType() {
        try {
            _processListService.getProcesses(_owners, _users, null);
            fail("Should have thrown EC_PROCESS_TYPE_NOT_SPECIFIED exception");
        } catch (WorkflowException we) {
            assertEquals(ProcessEC.EC_PROCESS_TYPE_NOT_SPECIFIED, we.getErrorCode());
        }
    }
    
    public void testWithInvalidAssignedTo() {
        try {
           
            Actor invalidUser = new Actor(APP_ID, -1, 1);
            Set<Actor> invalidUserSet = new HashSet<Actor>(1);
            invalidUserSet.add(invalidUser);
            Actors invalidUsers = new Actors(invalidUserSet);
            _processListService.getProcesses(_owners, invalidUsers, _variables);
            fail("Should have thrown EC_INVALID_ACTOR exception");
        } catch (WorkflowException we) {
            assertEquals(WorklistEC.EC_INVALID_ACTOR, we.getErrorCode());
        }
    }
    
}
