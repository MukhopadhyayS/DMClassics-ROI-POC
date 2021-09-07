/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.audit.model;

import java.util.Date;

import junit.framework.TestCase;

import com.mckesson.eig.audit.dao.hecm.hibernate.AuditTrailHibernateDao;

/**
 * Test class for AuditEvent.
 * 
 */
public class TestAuditEvent extends TestCase {

    /**
     * Reference of type <code>AuditEvent</code> initialized.
     */
    private AuditEvent _audit = null;
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
     * Test method cheks the object of type <code>AuditEvent</code> is not
     * null.
     */
    public void testAuditEvent() {
        AuditEvent auditEvent = new AuditEvent();
        assertNotNull(auditEvent);
    }

    /**
     * Test method checks the object of type <code>AuditEvent</code> is not
     * null.
     */
    public void testAuditEventLongLong() {
        final int eventId = 333;
        final int domainId = 444;
        AuditEvent auditEvent = new AuditEvent(eventId, domainId);
        assertNotNull(auditEvent);
        assertEquals(eventId, auditEvent.getEventId());
        assertEquals(domainId, auditEvent.getDomainId());
    }

    /**
     * Test method checks event with <code>Date</code>.
     */
    public void testEventStart() {
        Date now = new Date();
        _audit.setEventStart(now);
        assertEquals(now, _audit.getEventStart());
    }

    /**
     * Test method checks event with <code>Date</code>.
     */
    public void testEventEnd() {
        Date now = new Date();
        _audit.setEventEnd(now);
        assertEquals(now, _audit.getEventEnd());
    }

    /**
     * Test method checks <code>DomainId</code>.
     */
    public void testDomainId() {
        final long domainId = 10;
        _audit.setDomainId(domainId);
        assertEquals(domainId, _audit.getDomainId());
    }

    /**
     * Test method checks <code>EventId</code>.
     */
    public void testEventId() {
        final long eventId = 10;
        _audit.setEventId(eventId);
        assertEquals(eventId, _audit.getEventId());
    }

    /**
     * Test method checks <code>ObjectId</code>.
     */
    public void testObjectId() {
        final Long objectId = 10L;
        _audit.setObjectId(objectId);
        assertEquals(objectId, _audit.getObjectId());
    }

    /**
     * Test method checks <code>Revision</code>.
     */
    public void testRevision() {
        final Long rev1 = 10L;
        final Long rev2 = 11L;
        final Long rev3 = 12L;
        _audit.setRevision1(rev1);
        _audit.setRevision2(rev2);
        _audit.setRevision3(rev3);
        assertEquals(rev1, _audit.getRevision1());
        assertEquals(rev2, _audit.getRevision2());
        assertEquals(rev3, _audit.getRevision3());
    }

    /**
     * Test method checks <code>Comment</code>.
     */
    public void testComment() {
        String comment = "my comment";
        _audit.setComment(comment);
        assertEquals(comment, _audit.getComment());
    }

    /**
     * Test method checks <code>EventStatus</code>.
     */
    public void testEventStatus() {
        final Long status = 10L;
        _audit.setEventStatus(status);
        assertEquals(status, _audit.getEventStatus());
    }

    /**
     * Test method checks <code>Location</code>.
     */
    public void testLocation() {
        String location = "my location";
        _audit.setLocation(location);
        assertEquals(location, _audit.getLocation());
    }

    /**
     * Test method checks <code>UserId</code>.
     */
    public void testUserId() {
        final Long userId = 10L;
        _audit.setUserId(userId);
        assertEquals(userId, _audit.getUserId());
    }

    /**
     * Test method checks <code>WorkflowReason</code>.
     */
    public void testWorkflowReason() {
        String reason = "my reason";
        _audit.setWorkflowReason(reason);
        assertEquals(reason, _audit.getWorkflowReason());
    }

    /**
     * Test methood checks <code>ComponentId</code>.
     */
    public void testComponentId() {
        final long componentId = 10;
        _audit.setComponentId(componentId);
        assertEquals(componentId, _audit.getComponentId());
    }
}
