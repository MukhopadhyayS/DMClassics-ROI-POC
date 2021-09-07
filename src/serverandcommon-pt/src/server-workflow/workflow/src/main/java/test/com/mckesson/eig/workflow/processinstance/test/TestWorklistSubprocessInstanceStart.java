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

package com.mckesson.eig.workflow.processinstance.test;

import java.util.Hashtable;
import java.util.Map;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.WorkflowEngineException;
import com.mckesson.eig.workflow.engine.service.ProcessInstanceEngineImpl;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;

/**
 * Unit test class for testing ProcessInstanceEngineImpl start instance
 * functionality.
 */
public class TestWorklistSubprocessInstanceStart extends AbstractWorkflowTestCase {



    private ProcessInstanceEngineImpl _processInstance = null;
    private JbpmContext _jContext = null;
    private static Actor _userActor;
    private static final int APP_ID = 1;
	private static final int ET_DOMAIN = 1;


    protected void setUp() throws Exception {
    	final int domainID = 5001;
        super.setUp();
        init();
        _userActor = new Actor(APP_ID, ET_DOMAIN, domainID);
    }
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testWlSubprocessExecution() {

        _jContext = JbpmConfiguration.getInstance().createJbpmContext();

        // set variables map
        Map<String, String> variables = new Hashtable<String, String>();
        variables.put("testwlsubprocess.process-state1.worklist.String",
                        "Task Assigned to Actor - worklist Id");
        variables.put("testwlsubprocess.process-state1.taskname.String",
                        "eig workflow task+");
        variables.put("testwlsubprocess.process-state1.dueindays.int",
                        "2");
        variables.put("testwlsubprocess.process-state1.priority.int",
                        "0");
        variables.put("testwlsubprocess.process-state1.canstartearly.boolean",
                        "true");
        variables.put("testwlsubprocess.process-state1.user.String",
                        "Creator of Task");

        // start a new execution for the process definition.
        try {
            _processInstance = new ProcessInstanceEngineImpl(_userActor, _jContext,
                    "testwlsubprocess", variables);
        } catch (WorkflowEngineException e) {
            System.out.println("Process Definition not found: Deploying testwlsubprocess");
        }

        if (_processInstance == null) {
            ProcessDefinition processDefinition = ProcessDefinition.parseXmlResource(
          "com/mckesson/eig/workflow/processinstance/test/testwlsubprocess/processdefinition.xml");

            _jContext.deployProcessDefinition(processDefinition);

            _jContext.close();

            _jContext = JbpmConfiguration.getInstance().createJbpmContext();

            //retry after deploying process definition.
            _processInstance = new ProcessInstanceEngineImpl(_userActor, _jContext,
                    "testwlsubprocess", variables);
        }

        // assert that process instance engine is created successfully.
        assertNotNull(_processInstance);

        _processInstance.startProcessInstance();

        _jContext.close();

        //assert that process instance has not ended.
        assertEquals(false, _processInstance.getInstance().hasEnded());
    }
}
