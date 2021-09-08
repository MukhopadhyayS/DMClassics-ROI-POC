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

import com.mckesson.eig.workflow.api.Variable;
import com.mckesson.eig.workflow.api.VariableList;
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
public class TestGetAllProcesses extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static ProcessListService _processListService;
    private static VariableList _variableList;

    public void testSetup() {

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

        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(new Long(1));
        _processListService = (ProcessListService) getManager(PROCESS_LIST_MANAGER);
    }

    public void testGetAllProcessesWithExpiredProcesses() {

        ProcessInfoList processList = _processListService.getAllProcesses(1, true, _variableList);
        assertTrue(processList.getProcessList().size() > 0);
    }

    public void testGetAllProcessesWithOutExpiredProcesses() {

        ProcessInfoList processList = _processListService.getAllProcesses(1, false, _variableList);
        assertTrue(processList.getProcessList().size() == 0);
    }

     public void testGetAllProcessesWithInvalidProcessType() {

         List<Variable> variables = new ArrayList<Variable>();

         Variable v1 = new Variable();
         v1.setKey("PROCESS_TYPE");
         v1.setValue("test");
         variables.add(v1);

         _variableList.setVariables(variables);
         try {

             _processListService.getAllProcesses(1, true, _variableList);
             fail("Should have thrown EC_INVALID_PROCESS_TYPE exception");
         } catch (WorkflowException we) {
             assertEquals(ProcessEC.EC_INVALID_PROCESS_TYPE, we.getErrorCode());
         }
     }

     public void testGetAllProcessesWithNullProcessType() {

         try {

             _processListService.getAllProcesses(1, true, null);
             fail("Should have thrown EC_PROCESS_TYPE_NOT_SPECIFIED exception");
         } catch (WorkflowException we) {
             assertEquals(ProcessEC.EC_PROCESS_TYPE_NOT_SPECIFIED, we.getErrorCode());
         }

         VariableList variableList = new VariableList();
         variableList.setVariables(new ArrayList<Variable>());
         try {

             _processListService.getAllProcesses(1, true, variableList);
             fail("Should have thrown EC_PROCESS_TYPE_NOT_SPECIFIED exception");
         } catch (WorkflowException we) {
             assertEquals(ProcessEC.EC_PROCESS_TYPE_NOT_SPECIFIED, we.getErrorCode());
         }
     }

     public void testGetAllProcessesWithInvalidApplicationID() {

         try {

             _processListService.getAllProcesses(-1, true, _variableList);
             fail("Should have thrown EC_INVALID_APPLICATION_ID exception");
         } catch (WorkflowException we) {
             assertEquals(ProcessEC.EC_INVALID_APPLICATION_ID, we.getErrorCode());
         }
     }
}
