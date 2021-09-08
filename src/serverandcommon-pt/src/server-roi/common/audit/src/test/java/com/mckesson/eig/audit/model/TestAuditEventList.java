/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */
package com.mckesson.eig.audit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.mckesson.eig.audit.dao.hecm.hibernate.AuditTrailHibernateDao;

/**
 * Test class for AuditEventList.
 * 
 */
public class TestAuditEventList extends TestCase {

    /**
     * Reference of type <code>AuditEventList</code> initialized.
     */
    private AuditEvent _audit = null;
    private AuditEventList _auditEventList = null;
    private final long _componentId = 10;
    
    /**
     * Reference of type <code>AuditTrialHibernateDao</code> initialized.
     */
    private AuditTrailHibernateDao _auditTrialHibernateDao = null;

    /**
     * SetUp method for the test case.Initializes data which are required to run
     * this test case.
     * 
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _auditTrialHibernateDao = new AuditTrailHibernateDao();
        _audit = _auditTrialHibernateDao.create();
    }

    /**
     * Method tearDown()removes the data associated with this test case.
     * 
     * @throws Exception
     *             of type Exception.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method cheks the object of type <code>AuditEventList</code> is not
     * null.
     */
    public void testAuditEventList() {
        _auditEventList = new AuditEventList();
        assertNotNull(_auditEventList);
    }

    /**
     * Test method checks event with <code>AuditEventList</code>.
     */
    public void testEventList() {
        
    	_audit = initializeAuditEvent();
    	List<AuditEvent> auditEvents = new ArrayList<AuditEvent>();
    	auditEvents.add(_audit);
    	_auditEventList = new AuditEventList();
    	_auditEventList.setAuditEvent(auditEvents);
    	assertEquals(1, _auditEventList.getAuditEvent().size());
    } 

    private AuditEvent initializeAuditEvent() {
    	
    	 AuditEvent audit = new AuditEvent();
         audit.setComment("COMMENT");
         audit.setComponentId(_componentId);
         audit.setEventEnd(new Date());
         audit.setEventStart(new Date());
         audit.setEventStatus(10L);
         audit.setLocation("127.0.0.1");
         audit.setObjectId(20L);
         audit.setRevision1(1l);
         audit.setRevision2(2l);
         audit.setUserId(10L);
         audit.setWorkflowReason("WORKFLOWREASON");
         return audit;
	}
}
