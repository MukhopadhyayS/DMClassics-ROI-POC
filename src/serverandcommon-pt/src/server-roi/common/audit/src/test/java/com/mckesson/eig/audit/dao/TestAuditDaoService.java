package com.mckesson.eig.audit.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.mckesson.eig.audit.UnitTestSpringInitialization;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.audit.model.AuditEventList;

/**
 * Validates <code>AuditDaoService</code> by setting all possible values to
 * the <code>AuditEvent</code>.
 * 
 */
public class TestAuditDaoService extends TestCase {
    private AuditEvent _auditEvent = null;
    private AuditEvent _auditEvent1 = null;
    private static final String AUDITDESC = "TEST_DESCRIPTION";
    private static final long COMPONENTSEQ = new Long(7);
    private static final long EVENTID = new Long(2);
    private static final Long OBJECT_ID = new Long(1234);
	private static final long EVENTID1 = new Long(12);
    private static final Long OBJECT_ID1 = new Long(2345);
    private static final String WF_RSN = "Workflow Reason";
    private static final long TIMESTAMP = 1176843143977L;
    private static final long USERID = 1L;
    private static final String ACTION = "P";

    /**
     * Sets up the data required for this test case.
     */
    protected void setUp() throws Exception {
        super.setUp();
        UnitTestSpringInitialization.init();
        _auditEvent = new AuditEvent();
        _auditEvent.setComment(AUDITDESC);
        _auditEvent.setEventId(EVENTID);
        _auditEvent.setComponentId(COMPONENTSEQ);
        _auditEvent.setObjectId(OBJECT_ID);
        _auditEvent.setWorkflowReason(WF_RSN);
        _auditEvent1 = new AuditEvent();
        _auditEvent1.setComment(AUDITDESC);
        _auditEvent1.setEventId(EVENTID1);
        _auditEvent1.setComponentId(COMPONENTSEQ);
        _auditEvent1.setObjectId(OBJECT_ID1);
        _auditEvent1.setWorkflowReason(WF_RSN);
    }

    /**
     * Destroys the data associated with this test case.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Validates <code>createAuditEntry</code> method without setting
     * <code>EventStart</code> and <code>EventEnd</code>.
     */
    public void testCreateAuditEntry() {
        AuditDaoService auditDaoService = new AuditDaoService();
        assertTrue(auditDaoService.createAuditEntry(_auditEvent));
    }
    
    /**
     * Validates <code>createAuditEntryList</code> method without setting
     * <code>EventStart</code> and <code>EventEnd</code>.
     */
    public void testCreateAuditEntryList() {
        AuditDaoService auditDaoService = new AuditDaoService();
        AuditEventList auditEventList = new AuditEventList();
        List<AuditEvent> auditEvents = new ArrayList<AuditEvent>();
        auditEvents.add(_auditEvent);
        auditEvents.add(_auditEvent1);
        auditEventList.setAuditEvent(auditEvents);
        assertTrue(auditDaoService.createAuditEntryList(auditEventList));
    }
    
    /**
     * Validates <code>createAuditEntry</code> method with
     * <code>EventStart</code> and <code>EventEnd</code> set.
     */
    public void testinitializeDate() {
        try {
            Date start = new Date(TIMESTAMP);
            Date end = new Date(TIMESTAMP);
            _auditEvent.setEventStart(start);
            _auditEvent.setEventEnd(end);
            AuditDaoService auditDaoService = new AuditDaoService();
            assertTrue(auditDaoService.createAuditEntry(_auditEvent));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Validates the <code>createAuditEntry</code> method by setting the
     * <code>EventEnd</code> as <code>null</code>.
     */
    public void testinitializeDateStart() {
        try {
            Date now = new Date(TIMESTAMP);
            _auditEvent.setEventStart(now);
            _auditEvent.setEventEnd(null);
            AuditDaoService auditDaoService = new AuditDaoService();
            assertTrue(auditDaoService.createAuditEntry(_auditEvent));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Validates the <code>createAuditEntry</code> method by setting the
     * <code>EventStart</code> as <code>null</code>.
     */
    public void testinitializeDateEnd() {
        try {
            Date now = new Date(TIMESTAMP);
            _auditEvent.setEventEnd(now);
            _auditEvent.setEventStart(null);
            AuditDaoService auditDaoService = new AuditDaoService();
            assertTrue(auditDaoService.createAuditEntry(_auditEvent));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testAuditHpf() {
        try {
            UnitTestSpringInitialization.initRoi();
            _auditEvent = new AuditEvent();
            _auditEvent.setActionCode(ACTION);
            _auditEvent.setUserId(USERID);
            _auditEvent.setComment(AUDITDESC);
            _auditEvent.setEventId(EVENTID);
            _auditEvent.setComponentId(COMPONENTSEQ);
            _auditEvent.setObjectId(OBJECT_ID);
            _auditEvent.setWorkflowReason(WF_RSN);
            AuditDaoService auditDaoService = new AuditDaoService();
            assertTrue(auditDaoService.createAuditEntry(_auditEvent));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
