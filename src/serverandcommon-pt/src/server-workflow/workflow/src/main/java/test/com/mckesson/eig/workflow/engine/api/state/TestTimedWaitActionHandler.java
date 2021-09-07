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

package com.mckesson.eig.workflow.engine.api.state;

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
public class TestTimedWaitActionHandler extends AbstractWorkflowTestCase {



    private ProcessInstanceEngineImpl _processInstance = null;
    private JbpmContext _jContext = null;
    private static Actor _userActor;
    private static final int APP_ID = 1;
    private static final int ET_DOMAIN = 1;
    private static final long WAIT_TIME = 2000;

    private static final String PROCESS_ID = "102";

    protected void setUp() throws Exception {
        final int domainID = 5001;
        super.setUp();
        init();
        _userActor = new Actor(APP_ID, ET_DOMAIN, domainID);

    }

    protected void tearDown() throws Exception {
        Thread.sleep(WAIT_TIME);
        super.tearDown();
    }

    public void testTimedWaitExecution() {

        _jContext = JbpmConfiguration.getInstance().createJbpmContext();

        // set variables map
        Map<String, String> variables = new Hashtable<String, String>();
        variables.put("SelectedContent.0", "5024");
        variables.put("ProcessVersion", "1");

        // start a new execution for the process definition.
        try {
            _processInstance = new ProcessInstanceEngineImpl(_userActor, _jContext,
                      PROCESS_ID, variables);
        } catch (WorkflowEngineException e) {
            System.out.println("Process Definition not found: Deploying testtimedwait");
        }

        if (_processInstance == null) {
            ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
                    "<process-definition name='" + PROCESS_ID + "'>"
                    + "<start-state name='start-state1'>"
                    + "<transition to='testTimedWait'/>"
                    + "</start-state>"
                    + "<state name='testTimedWait'>"
                    + "<event type='node-enter'>"
                    + "<action name='TimedWait' "
                    + "class="
                    + "'com.mckesson.eig.workflow.engine.api.state.TimedWaitActionHandler'"
                    + " config-type='bean'>"
                    + "<timerNumber>2</timerNumber>"
                    + "<timerUnitOfMeasurement>Minutes</timerUnitOfMeasurement>"
                    + "</action>"
                    + "</event>"
                    + "<transition to='end' />"
                    + "</state>"
                    + "<end-state name='end' />"
                    + "</process-definition>"
                  );


            _jContext.deployProcessDefinition(processDefinition);

            _jContext.close();

            _jContext = JbpmConfiguration.getInstance().createJbpmContext();

            //retry after deploying process definition.
            _processInstance = new ProcessInstanceEngineImpl(_userActor, _jContext,
                    PROCESS_ID, variables);
        }

        // assert that process instance is not null
        assertNotNull(_processInstance);

        _processInstance.startProcessInstance();

        _jContext.close();

        // assert that process instance has ended.
        assertEquals(false, _processInstance.getInstance().hasEnded());
    }

}
