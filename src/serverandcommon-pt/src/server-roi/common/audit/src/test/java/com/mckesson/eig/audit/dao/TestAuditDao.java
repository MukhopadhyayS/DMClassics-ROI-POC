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

package com.mckesson.eig.audit.dao;

import java.util.Date;

import junit.framework.TestCase;

import com.mckesson.eig.audit.UnitTestSpringInitialization;
import com.mckesson.eig.audit.dao.hecm.AuditDao4Hecm;
import com.mckesson.eig.audit.dao.hecm.AuditTrail;
import com.mckesson.eig.audit.dao.hecm.AuditTrailDetail;
import com.mckesson.eig.audit.model.AuditEvent;

/**
 * Testcase for AuditDAO Class.
 */
public class TestAuditDao extends TestCase {
	/**
	 * Reference of type <code>AuditEvent</code> initialized.
	 */
	private AuditEvent _auditEvent = null;
	/**
	 * Reference of type <code>AuditDao</code> initialized.
	 */
	private AuditDao4Hecm _auditDao = null;
	/**
	 * object of type <code>long</code>.
	 */
	private static final long EVENTID = new Long(2);
	/**
	 * object of type <code>long</code>.
	 */
	private static final long ORGID = new Long(1);
	/**
	 * object of type <code>long</code>.
	 */
	private static final long COMPONENTSEQ = new Long(7);
	/**
	 * object of type <code>long</code>.
	 */
	private static final long USERID = new Long(121);
	/**
	 * object of type <code>String</code>.
	 */
	private static final String AUDITDESC = "TEST_DESCRIPTION";
	/**
	 * object of type <code>long</code>.
	 */
	private static final long EVENTSTATUS = new Long(1);
	/**
	 * object of type <code>String</code>.
	 */
	private static final String LOCATION = "TEST_LOCATION";

	private static final long REVISION1 = new Long(1);
	private static final long REVISION2 = new Long(2);
	private static final long REVISION3 = new Long(3);
	private static final String WORKFLOWREASON = "AAA";

	private static final long IDS = new Long(999999);

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		UnitTestSpringInitialization.init();
		_auditDao = new AuditDao4Hecm();
		_auditEvent = new AuditEvent();
		_auditEvent.setEventId(EVENTID);
		_auditEvent.setDomainId(ORGID);
		_auditEvent.setUserId(USERID);
		_auditEvent.setEventStatus(EVENTSTATUS);
		_auditEvent.setLocation(LOCATION);
		_auditEvent.setComponentId(COMPONENTSEQ);
		_auditEvent.setComment(AUDITDESC);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * This test method checks if entries are inserted into the AUDIT_TRAIL
	 * table.
	 */
	public void testInsertEntry() {
		try {
			assertTrue(_auditDao.insertEntry(_auditEvent));
		} catch (Exception e) {
			//LOG.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * This test method checks if entries are inserted into the
	 * AUDIT_TRAIL_DETAIL table.
	 */
	public void testInsertDetailEntry() {
		try {
			_auditEvent.setRevision1(REVISION1);
			_auditEvent.setRevision2(REVISION2);
			_auditEvent.setRevision3(REVISION3);
			_auditEvent.setWorkflowReason(WORKFLOWREASON);
			assertTrue(_auditDao.insertEntry(_auditEvent));
		} catch (Exception e) {
			//LOG.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	public void testDeleteAuditEntry() {
		try {
			_auditEvent.setRevision1(REVISION1);
			_auditEvent.setRevision2(REVISION2);
			_auditEvent.setRevision3(REVISION3);
			_auditEvent.setWorkflowReason(WORKFLOWREASON);
			AuditTrail auditTrail = new AuditTrail(_auditEvent);
			assertTrue(_auditDao.insertEntry(_auditEvent));
			auditTrail.setAuditTrailSeq(new Long(1));
			AuditTrailDetail auditTrailDetail = new AuditTrailDetail();
			auditTrailDetail.setAuditTrailSeq(auditTrail.getAuditTrailSeq());
			auditTrailDetail.setAuditTrailDetailSeq(new Long(1));
			_auditDao.deleteDetailEntries(auditTrailDetail);
		} catch (Exception e) {
			//LOG.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	public void testGetSetAuditEntry() {
		AuditTrail defaultTrail = new AuditTrail(_auditEvent);

		AuditTrail auditTrail = new AuditTrail();
		Date now = new Date();
		auditTrail.setEventId(EVENTID);
		auditTrail.setDomainId(ORGID);
		auditTrail.setUserId(USERID);
		auditTrail.setEventStatus(EVENTSTATUS);
		auditTrail.setLocation(LOCATION);
		auditTrail.setComponentId(COMPONENTSEQ);
		auditTrail.setComment(AUDITDESC);

		assertEquals(defaultTrail, auditTrail);
		auditTrail.setEventStart(now);
		auditTrail.setEventEnd(now);
		auditTrail.setModifyDateTime(now);
		assertTrue(defaultTrail.equals(auditTrail));

		AuditTrailDetail detail = new AuditTrailDetail();
		detail.setAuditCharVal("aaaa");
		detail.setAuditDateVal(now);
		detail.setAuditVarCharVal("aaaa");
		assertEquals("aaaa", detail.getAuditCharVal());
		assertEquals(now, detail.getAuditDateVal());
		assertEquals("aaaa", detail.getAuditVarCharVal());
	}

	public void testRetrieveAuditEntry() {
		_auditEvent.setRevision1(REVISION1);
		_auditEvent.setRevision2(REVISION2);
		_auditEvent.setRevision3(REVISION3);
		_auditEvent.setWorkflowReason(WORKFLOWREASON);
		assertTrue(_auditDao.insertEntry(_auditEvent));
		assertNull(_auditDao.retrieveEntry(IDS));
		assertNull(_auditDao.retrieveDetailEntry(IDS));
	}
}
