package com.mckesson.eig.audit.dao;

import java.util.Date;

import junit.framework.TestCase;

import com.mckesson.eig.audit.UnitTestSpringInitialization;
import com.mckesson.eig.audit.dao.hpf.AuditDao4Hpf;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

public class TestAuditDaoHpf extends TestCase {

    private AuditEvent _auditEvent = null;
    private AuditDao4Hpf _auditDao = null;
    private static final Log LOG = LogFactory.getLogger(TestAuditDaoHpf.class);
    
    private static final Long USER = new Long(1);
    private static final String ACTION_CODE = "D";
    private static final String MRN = "MRN";
    private static final String ENCOUNTER = "ENCOUNTER";
    private static final String FACILITY = "A";
    private static final String COMMENT = "COMMENT";

    protected void setUp() throws Exception {
        super.setUp();
        UnitTestSpringInitialization.initRoi();
        _auditDao = new AuditDao4Hpf();
        _auditEvent = new AuditEvent();
        _auditEvent.setUserId(USER);
        _auditEvent.setActionCode(ACTION_CODE);
        _auditEvent.setMrn(MRN);
        _auditEvent.setEncounter(ENCOUNTER);
        _auditEvent.setFacility(FACILITY);
        _auditEvent.setComment(COMMENT);
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testInsertEntry() {
        try {
             assertTrue(_auditDao.insertEntry(_auditEvent));
        } catch (Exception e) {
            LOG.error(e.getMessage());
            fail(e.getMessage());
        }
    }

    public void testInsertEntryWithGlobal() {
        try {
            Date now = new Date();
            _auditEvent.setFacility(null);
            _auditEvent.setEventStart(now);
             assertTrue(_auditDao.insertEntry(_auditEvent));
        } catch (Exception e) {
            LOG.error(e.getMessage());
            fail(e.getMessage());
        }
    }
}
