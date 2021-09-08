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
import com.mckesson.eig.workflow.engine.service.ProcessInstanceEngineImpl;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;

/**
 * Unit test class for testing ProcessInstanceEngineImpl start instance
 * functionality.
 */
public class TestConditionalWaitActionHandler extends AbstractWorkflowTestCase {

    private ProcessInstanceEngineImpl _processInstance = null;
    private JbpmContext _jContext = null;
    private static Actor _userActor;
    private static final int APP_ID = 1;
    private static final int ET_DOMAIN = 1;
    private static final int DOMAIN_ID = 5001;

    private static final String TRUE_PROCESS_ID = "100";
    private static final String FALSE_PROCESS_ID = "101";

    protected void setUp() throws Exception {
        super.setUp();
        init();
        _userActor = new Actor(APP_ID, ET_DOMAIN, DOMAIN_ID);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testTrueConditionExecution() {

        _jContext = JbpmConfiguration.getInstance().createJbpmContext();

        // set variables map
        Map<String, String> variables = new Hashtable<String, String>();
        variables.put("SelectedContent.0",
                        "5024");
        variables.put("SelectedContent.1",
                        "5025");

        variables.put("ProcessVersion", "1");

        System.out.println("Deploying testconditionalwait true process definition");
        ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
                    "<process-definition name='" + TRUE_PROCESS_ID + "'>"
                    + "<start-state name='start-state1'>"
                    + "<transition to='testConditionalWait' />"
                    + "</start-state>"
                    + "<state name='testConditionalWait'>"
                    + "<event type='node-enter'>"
                    + "<action name='ConditionalWait' "
                    + "class="
                    + "'com.mckesson.eig.workflow.engine.api.state.ConditionalWaitActionHandler'"
                    + " config-type='bean'>"
                    + "<waitCondition>"
                    + "#{contextInstance.variables['SelectedContent.0']==&quot;5024&quot;"
                    + " &amp;&amp; contextInstance.variables['101.testC.test1.Content.islocked"
                    + ":boolean']==&quot;true&quot;"
                    + " &amp;&amp; 4&gt;3}"
                    + "</waitCondition>"
                    + "<retryTimerNumber>5</retryTimerNumber>"
                    + "<retryTimerUnitOfMeasurement>Seconds</retryTimerUnitOfMeasurement>"
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
                    TRUE_PROCESS_ID, variables);

        // assert that process instance is not null
        assertNotNull(_processInstance);

        _processInstance.startProcessInstance();

        _jContext.close();

        // assert that process instance has ended.
        assertEquals(true, _processInstance.getInstance().hasEnded());
    }

    public void testFalseConditionExecution() {

        _jContext = JbpmConfiguration.getInstance().createJbpmContext();

        // set variables map
        Map<String, String> variables = new Hashtable<String, String>();
        variables.put("SelectedContent.0",
                        "5024");
        variables.put("ProcessVersion", "1");

        System.out.println("Deploying testconditionalwait false process definition");
        ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
                    "<process-definition name='" + FALSE_PROCESS_ID + "'>"
                    + "<start-state name='start'>"
                    + "<transition to='testConditionalWait' />"
                    + "</start-state>"
                    + "<state name='testConditionalWait'>"
                    + "<event type='node-enter'>"
                    + "<action name='ConditionalWait' "
                    + "class="
                    + "'com.mckesson.eig.workflow.engine.api.state.ConditionalWaitActionHandler'"
                    + " config-type='bean'>"
                    + "<waitCondition>"
                    + "#{contextInstance.variables['SelectedContent.0']==&quot;5024&quot;"
                    + " &amp;&amp; contextInstance.variables['100.testC.test1.Content.islocked"
                    + ":boolean']==&quot;true&quot;"
                    + " &amp;&amp; 4&lt;3}"
                    + "</waitCondition>"
                    + "<retryTimerNumber>5</retryTimerNumber>"
                    + "<retryTimerUnitOfMeasurement>Seconds</retryTimerUnitOfMeasurement>"
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
                    FALSE_PROCESS_ID, variables);

        // assert that process instance is not null
        assertNotNull(_processInstance);

        _processInstance.startProcessInstance();

        _jContext.close();

        // assert that process instance has ended.
        assertEquals(false, _processInstance.getInstance().hasEnded());
    }
}
