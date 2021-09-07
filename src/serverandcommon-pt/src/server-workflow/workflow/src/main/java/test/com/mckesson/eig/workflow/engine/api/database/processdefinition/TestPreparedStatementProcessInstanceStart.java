package com.mckesson.eig.workflow.engine.api.database.processdefinition;

import java.util.Hashtable;
import java.util.Map;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.engine.service.ProcessInstanceEngineImpl;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;

public class TestPreparedStatementProcessInstanceStart extends AbstractWorkflowTestCase {
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

    public void testPreparedStatementProcessDefinition() {

        _jContext = JbpmConfiguration.getInstance().createJbpmContext();

        // set variables map
        Map<String, String> variables = new Hashtable<String, String>();
        variables.put("SelectedContent.0", "5129");
        variables.put("ProcessVersion", "1");

        ProcessDefinition processDefinition = ProcessDefinition
                .parseXmlResource("com/mckesson/eig/workflow/engine/api/database"
                        + "/processdefinition/testpreparedstatement/processdefinition.xml");

        _jContext.deployProcessDefinition(processDefinition);

        _jContext.close();

        _jContext = JbpmConfiguration.getInstance().createJbpmContext();

        // retry after deploying process definition.
        _processInstance = new ProcessInstanceEngineImpl(_userActor, _jContext,
                "201", variables);

        // assert that process instance is not null
        assertNotNull(_processInstance);

        _processInstance.startProcessInstance();

        _jContext.close();
        
        _processInstance.getInstance().end();

        // assert that process instance has ended.
        assertEquals(true, _processInstance.getInstance().hasEnded());
    }

}
