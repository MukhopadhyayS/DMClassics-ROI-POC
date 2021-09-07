package com.mckesson.eig.workflow.process.dao;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.process.api.Process;
import com.mckesson.eig.workflow.process.api.ProcessAttribute;
import com.mckesson.eig.workflow.process.api.ProcessVersion;
import com.mckesson.eig.wsfw.session.WsSession;

public class TestProcessDAO extends BaseProcessTest {

    private static final int THIRTY = 30;
    private ProcessDAO _dao = null;
    private Actor _actor;
    private Long _actorId = null;


    @Before
    public void setUp() throws Exception {
        init();
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(new Long(1));

        _actor = new Actor();
        _actorId  = new Long(THIRTY);
        _actor.setActorID(_actorId);

        //Set up the session for the Tests
        _dao = (ProcessDAO) this.getDAO(ProcessDAO.class.getName());
        HibernateTemplate hTemplate = new HibernateTemplate(
                getHibernateSessionFactory());
        _dao.setHibernateTemplate(hTemplate);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateProcess() {
        String processName = generateUniqueName("ProcessTest");
        Process processDetails = buildProcessObject(_actor, processName, false);
        _actor.setActorID(new Long(THIRTY));
        try {
            this._dao.createProcess(_actor, processDetails);
        } catch (Exception e) {
            fail("Create of process failed");
        }
        long processId = processDetails.getProcessId();
        assertTrue(processId > 0);
        _dao.deleteProcess(_actor, processDetails);
    }

    public void testGetProcessVersion() {
        //Create data for this test and do a get
        String processName = generateUniqueName("ProcessTest");
        Process processDetails = buildProcessObject(_actor, processName, false);

        try {
            this._dao.createProcess(_actor, processDetails);
        } catch (Exception e) {
            fail("Create of process failed");
        }
        assertNotNull(processDetails);
        long processId = processDetails.getProcessId();
        assertTrue(processId > 0);
        assertTrue("primary key assigned", processId > 0);

        ProcessVersion processVersion = _dao.getProcessVersion(processId);
        assertEquals(processVersion.getProcess().getProcessId(), processId);
    }

    public void testGetProcessWithVersions() {
        // Create data for this test and do a get

        // Using the processId=1 from the Datavault

        // Need to a session for the tests for lazy objects
        // else will throw LIE - Lazy Initialization error
        Session session = createAndBindHibernateSession();

        try {
            Process p = _dao.getProcess(_actor, new Long(UnitTest.ONE));
            assertNotNull(p);
            Set<ProcessVersion> pv = p.getProcessVersions();
            assertTrue("There should be only one version", pv.size() == 1);

            Iterator<ProcessVersion> iter = pv.iterator();
            while (iter.hasNext()) {
                ProcessVersion v = iter.next();
                assertNotNull(v);
                Set<ProcessAttribute> pAttribs = v.getProcessAttributes();
                Iterator<ProcessAttribute> iter2 = pAttribs.iterator();
                while (iter2.hasNext()) {
                    ProcessAttribute pAttribute = iter2.next();
                    assertNotNull(pAttribute);
                }
            }
        } catch (Exception e) {
            fail();

        } finally {
            releaseAndUnbindHibernateSession(session);
        }
    }
}
