package com.mckesson.eig.workflow.processinstance.dao;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mckesson.eig.workflow.processinstance.api.ProcessInstanceHistory;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;
import com.mckesson.eig.wsfw.session.WsSession;

public class TestProcessInstanceDAO extends AbstractWorkflowTestCase {

    private ProcessInstanceDAO _dao = null;
    private ProcessInstanceHistory _processInstanceHistory = null;
    private static final int PROCESS_ID = 100;
    private static Date _date = new Date();

    @Before
    public void setUp() throws Exception {
        init();
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(new Long(1));
        _dao = (ProcessInstanceDAO) this.getDAO(ProcessInstanceDAO.class.getName());
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Tests process instance history creation.
     */
    @Test
    public void testCreateProcessInstanceHistory() {

        _processInstanceHistory = new ProcessInstanceHistory();
        _processInstanceHistory.setProcessId(PROCESS_ID);
        _processInstanceHistory.setVersionId(1);
        _processInstanceHistory.setProcessInstanceId(1);
        _processInstanceHistory.setEventLevel("eventlevel");
        _processInstanceHistory.setEventName("eventname");
        _processInstanceHistory.setEventOriginator("somebody");
        _processInstanceHistory.setEventDatetime(_date);
        _processInstanceHistory.setEventStatus("eventstatus");
        _processInstanceHistory.setEventComments("eventcomments");
        _processInstanceHistory.setEventComments("eventcomments");
        _processInstanceHistory.setModifiedByUserId(1);
        _processInstanceHistory.setModifyDateTime(_date);
        _processInstanceHistory.setCreateDateTime(_date);

        try {
            _dao.createProcessInstanceHistory(_processInstanceHistory);
        } catch (Exception e) {
            fail("Create of process instance history failed");
        }

        log("testCreateProcessInstanceHistory successful");
    }
}
