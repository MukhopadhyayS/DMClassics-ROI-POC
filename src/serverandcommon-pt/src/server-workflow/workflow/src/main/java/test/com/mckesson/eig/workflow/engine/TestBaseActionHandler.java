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

package com.mckesson.eig.workflow.engine;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.def.TaskMgmtDefinition;

import com.mckesson.eig.utility.util.ReflectionUtilities;
import com.mckesson.eig.workflow.api.WorkflowEngineException;
import junit.framework.TestCase;

/**
 *
 */
public class TestBaseActionHandler extends TestCase {

    private class TempBaseActionHandler extends BaseActionHandler {

        public TempBaseActionHandler() {
            super();
        }

        public void validate() {
        }

        public void executeAction(final ExecutionContext context) {
        }

        public void execute(final ExecutionContext context) {
        }
    }

    private TempBaseActionHandler _handler;

    protected void setUp() throws Exception {
        super.setUp();
        _handler = new TempBaseActionHandler();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetJBPMTask() {
        ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
           "<process-definition>"
           + "<start-state>"
           + "<transition to='sendEmail' />"
           + "</start-state>"
           + "<task-node name='sendEmail'>"
           + "<task name='sendEmailTask' swimlane='initiator'>"
           + "<event type='task-assign'>"
           + "<action class='com.mckesson.eig.workflow.engine.api.alert.SendEmailActionHandler'/>"
           + "</event></task></task-node>"
           + "<transition to='end'>"
           + "</transition>"
           + "<end-state name='end' />"
           + "</process-definition>"
        );

        //start a new execution for the process definition.
        ProcessInstance processInstance = new ProcessInstance(processDefinition);
        assertNotNull(processInstance);

        ContextInstance contextInstance = processInstance.getContextInstance();
        assertNotNull(contextInstance);

        String tmpVariable = "Literal1";
        contextInstance.setVariable(tmpVariable, "Literal_1_Value");

        assertEquals("Literal_1_Value", contextInstance.getVariable(tmpVariable));
        ExecutionContext executionContext = new ExecutionContext(processInstance.getRootToken());
        TaskInstance taskInstance = null;
        try {
            TaskMgmtDefinition tmd = (TaskMgmtDefinition) executionContext.
                getDefinition(TaskMgmtDefinition.class);
             Task task = tmd.getTask("sendEmail");
             TaskMgmtInstance tmi = executionContext.getTaskMgmtInstance();
             tmi.createTaskInstance(task, executionContext.getToken());
             taskInstance = _handler.getJBPMTask(executionContext);

        } catch (WorkflowEngineException e) {
            fail("testGetJBPMTask() should not throw WorkflowException(). "
                    + e.toString());
        }

        assertNotNull(taskInstance);
    }

    public void testDeleteProcessVariable() {
        ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
          "<process-definition>"
          + "<start-state>"
          + "<transition to='sendEmail' />"
          + "</start-state>"
          + "<state name='sendEmail'>"
          + "<transition to='end'>"
          + "<action class='com.mckesson.eig.workflow.engine.api.alert.SendEmailActionHandler'/>"
          + "</transition>"
          + "</state>"
          + "<end-state name='end' />"
          + "</process-definition>"
        );
        //start a new execution for the process definition.
        ProcessInstance processInstance = new ProcessInstance(processDefinition);
        assertNotNull(processInstance);

        ContextInstance contextInstance = processInstance.getContextInstance();
        assertNotNull(contextInstance);

        String tmpVariable = "Literal1";
        contextInstance.setVariable(tmpVariable, "Literal_1_Value");

        assertEquals("Literal_1_Value", contextInstance.getVariable(tmpVariable));
        ExecutionContext executionContext = new ExecutionContext(processInstance.getRootToken());
        try {
            _handler.deleteProcessVariable(executionContext, tmpVariable);

        } catch (WorkflowEngineException e) {
            fail("testDeleteProcessVariable() should not throw WorkflowException(). "
                    + e.toString());
        }

        assertNull(contextInstance.getVariable(tmpVariable));
    }

    public void testRender() {
        ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
           "<process-definition>"
           + "<start-state>"
           + "<transition to='sendEmail' />"
           + "</start-state>"
           + "<state name='sendEmail'>"
           + "<transition to='end'>"
           + "<action class='com.mckesson.eig.workflow.engine.api.alert.SendEmailActionHandler'/>"
           + "</transition>"
           + "</state>"
           + "<end-state name='end' />"
           + "</process-definition>"
         );
        //start a new execution for the process definition.
        ProcessInstance processInstance = new ProcessInstance(processDefinition);
        assertNotNull(processInstance);

        ContextInstance contextInstance = processInstance.getContextInstance();
        assertNotNull(contextInstance);

        contextInstance.setVariable("Literal1", "Literal_1_Value");
        contextInstance.setVariable("Literal2", "Literal_2_Value");

        ExecutionContext executionContext = new ExecutionContext(processInstance.getRootToken());
        String designTimeValue = "Detail: we want to replace ${Literal1} and ${Literal2}";
        String testValue = null;
        try {
            testValue = _handler.render(executionContext, designTimeValue);
         } catch (WorkflowEngineException e) {
            fail("testRender() should not throw WorkflowException(). " + e.toString());
        }

        assertEquals("Detail: we want to replace Literal_1_Value and Literal_2_Value",
                testValue);
    }

    public void testReplaceLiterals() {
        ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
          "<process-definition>"
          + "<start-state>"
          + "<transition to='sendEmail' />"
          + "</start-state>"
          + "<state name='sendEmail'>"
          + "<transition to='end'>"
          + "<action class='com.mckesson.eig.workflow.engine.api.alert.SendEmailActionHandler'/>"
          + "</transition>"
          + "</state>"
          + "<end-state name='end' />"
          + "</process-definition>"
        );
        //start a new execution for the process definition.
        ProcessInstance processInstance = new ProcessInstance(processDefinition);
        assertNotNull(processInstance);

        ContextInstance contextInstance = processInstance.getContextInstance();
        assertNotNull(contextInstance);

        contextInstance.setVariable("Literal1", "Literal_1_Value");
        contextInstance.setVariable("Literal2", "Literal_2_Value");

        ExecutionContext executionContext = new ExecutionContext(processInstance.getRootToken());
        String designTimeValue = "Detail: we want to replace ${Literal1} and ${Literal2}";
        List <String> literals = new ArrayList();
        literals.add("Literal1");
        literals.add("Literal2");

        Class[] argType = {ExecutionContext.class, String.class, List.class};
        Object[] argValue = {executionContext, designTimeValue, literals};
        String testValue = null;
        try {
            testValue = (String) ReflectionUtilities
            .callPrivateMethod(BaseActionHandler.class, _handler, "replaceLiterals",
                    argType, argValue);
        } catch (WorkflowEngineException e) {
            fail("testReplaceLiterals() should not throw WorkflowException(). " + e.toString());
        }

        assertEquals("Detail: we want to replace Literal_1_Value and Literal_2_Value",
                testValue);
    }

    public void testExtractLiterals() {
        String designTimeValue = "Detail: we want to replace ${Literal1} and ${Literal2}";

        Class[] argType = {String.class};
        Object[] argValue = {designTimeValue};
        List<String> testValue = null;
        try {
            testValue = (List<String>) ReflectionUtilities
            .callPrivateMethod(BaseActionHandler.class, _handler, "extractLiterals",
                    argType, argValue);
        } catch (WorkflowEngineException e) {
            fail("testExtractLiterals() should not throw WorkflowException(). " + e.toString());
        }
        final int two = 2;
        assertTrue(testValue.size() == two);
        assertEquals("Literal1", testValue.get(0));
        assertEquals("Literal2", testValue.get(1));
    }
}
